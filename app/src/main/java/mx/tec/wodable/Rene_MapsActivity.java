package mx.tec.wodable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.protobuf.StringValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import com.google.firebase.firestore.FieldValue;

public class Rene_MapsActivity extends AppCompatActivity implements GoogleMap.OnMapClickListener, SensorEventListener, StepListener {
	 private static final long UPDATE = 10 *1000;
	 private static final long FASTEST = 2 * 1000;
	 private static int tamanio = 0;
	 // Variables
	 SupportMapFragment supportMapFragment;
	 FusedLocationProviderClient client;
	 public Location lastlocation;
	 // Para buscar la camara
	 LatLng userlocation;
	 long horainicio, horafinal;
	 Date horaInicioD, horaFinalD;
	 public GoogleMap mMap;
	 // To do lo del cronometro
	 // Elementos
	 TextView tiempo, pasos, distancia;
	 Button reset, stop, start, exit;
	 Timer timer;
	 TimerTask timerTask;
	 Double time = 0.0;
	 boolean timeStarted = false;
	 // sensores etc step counter
	 private SensorManager sensorManager;
	 private Sensor accel;
	 private StepDetector stepDetector;
	 public int pasosdado = 0;
	 /*
	  *  Variables bases de datos
	  */
	 private String userId;
	 private FirebaseFirestore fStore;
	 private FirebaseAuth mAuth;
	 private boolean timeUnaVez = false;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.activity_rene__maps);
		  // Encontramos el fragment
		  supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
				  .findFragmentById(R.id.google_map);
		  mAuth = FirebaseAuth.getInstance();
		  // Fuses loction
		  client = LocationServices.getFusedLocationProviderClient(this);

		  // Para tener el puntito azul
		  LocationRequest request = new LocationRequest();
		  request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		  request.setInterval(UPDATE);
		  request.setFastestInterval(FASTEST);
		  // Check permission
		  if (ActivityCompat.checkSelfPermission(Rene_MapsActivity.this,
				  Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
		  	 getCurrentLocation();
		  	 LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(request,
					 new LocationCallback(){
		  	 	 public void onLocationResult(LocationResult locationResult){
					lastlocation = locationResult.getLastLocation();
					  Log.wtf("Ubicacion actualizada", locationResult.getLastLocation().toString());
				 }
					 },
					 Looper.myLooper());
		  }
		  else {
		  	 // Cuando es negadoo
			   ActivityCompat.requestPermissions(Rene_MapsActivity.this,
					   new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
		  }
		  // Cronometro
		  timer = new Timer();

		  reset = findViewById(R.id.ReneResetBttn);
		  stop = findViewById(R.id.ReneStopBttn);
		  start = findViewById(R.id.ReneStartBttn);
		  exit = findViewById(R.id.ReneExitBttn);

		  tiempo = findViewById(R.id.ReneTiempo);
		  pasos = findViewById(R.id.RenePasos);
		  distancia = findViewById(R.id.ReneDistancia);

		  sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		  accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		  // Java class
		  stepDetector = new StepDetector();
		  stepDetector.registerListener(this);
		  pasosdado = 0;

		  stop.setOnClickListener(new View.OnClickListener() {
			   @Override
			   public void onClick(View v) {
					// dejar de contar cuando sale el pop
					sensorManager.unregisterListener(Rene_MapsActivity.this);

					if(timerTask == null)return;

					timerTask.cancel();

					float distanciaFinal = Distance(pasosdado);

					AlertDialog.Builder builder = new AlertDialog.Builder(Rene_MapsActivity.this);
					builder.setCancelable(true);
					builder.setTitle("Espera!");
					builder.setMessage("Estas seguro que deseas parar? Has corrido:  " + String.valueOf(distanciaFinal) + " Km");

					// Dialog alert
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						 @Override
						 public void onClick(DialogInterface dialog, int which) {
							  timeStarted = true;
							  start.setText("PAUSE");
							  // seguimos contadno cuando sale
							  sensorManager.registerListener(Rene_MapsActivity.this,accel, SensorManager.SENSOR_DELAY_FASTEST);
							  startTimer();
							  dialog.cancel();
						 }
					});
					builder.setPositiveButton("Seguro!", new DialogInterface.OnClickListener() {
						 @Override
						 public void onClick(DialogInterface dialog, int which) {
							 if(tiempo.getText().toString().equals("00:00:00"))return;
							 horaFinalD = new Date();
						 	 horafinal = horaFinalD.getTime();

							  Log.wtf("HoraFinal", String.valueOf(horafinal));
							  Timestamp horai = new Timestamp(horaFinalD);
							  Log.wtf("HoraFinal", horai.toString());
							  // se detiene
							  sensorManager.unregisterListener(Rene_MapsActivity.this);
							  float f = Distance(pasosdado);

							 actualizarDatos(horaInicioD,
									 horaFinalD, String.valueOf(f), pasos.getText().toString(), tiempo.getText().toString());

							  pasos.setText("0");
							  distancia.setText("0 Km");
							  time = 0.0;
							  horafinal = 0;
							  horainicio = 0;
							  pasosdado = 0;
							  timeStarted = false;
							  // Llamamos a poner el tiempo
							  start.setText("START");
							  tiempo.setText("00:00:00");
						 }
					});
					builder.show();
			   }
		  });
		  // Exit button
		  exit.setOnClickListener(new View.OnClickListener() {
			   @Override
			   public void onClick(View v) {
					finish();
			   }
		  });

		  // Start pause botton
		  start.setOnClickListener(new View.OnClickListener() {
			   @Override
			   public void onClick(View v) {
					if(timeStarted == false){
						 timeStarted = true;
						 start.setText("PAUSE");
						 distancia.setText("Calculando....");
						 // Step counter
						 sensorManager.registerListener(Rene_MapsActivity.this,accel, SensorManager.SENSOR_DELAY_FASTEST);

						 if(tiempo.getText().toString().equals("00:00:00")){
							 horaInicioD = new Date();
							 horainicio = horaInicioD.getTime();
							 Log.wtf("HoraInicio", String.valueOf(horainicio));
							 Timestamp horai = new Timestamp(horaInicioD);
							 Log.wtf("HoraInicio", horai.toString());
						 }
						 startTimer();
					}else{

						 timeStarted = false;
						 start.setText("START");
						 timerTask.cancel();
						 sensorManager.unregisterListener(Rene_MapsActivity.this);

						 float f = Distance(pasosdado);
						 distancia.setText(String.valueOf(f) + "Km");

					}
			   }
		  });

		  reset.setOnClickListener(new View.OnClickListener() {
			   @Override
			   public void onClick(View v) {
					if (timeStarted == true) {
						 sensorManager.unregisterListener(Rene_MapsActivity.this);
						 timerTask.cancel();

						 AlertDialog.Builder resetAlert = new AlertDialog.Builder(Rene_MapsActivity.this);
						 resetAlert.setCancelable(true);
						 resetAlert.setTitle("Reset Timer!");
						 resetAlert.setMessage("¿Seguro que deseas empezar de nuevo?");
						 // Botones en la alerta
						 resetAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
							  @Override
							  public void onClick(DialogInterface dialog, int which) {
								   if (timerTask != null) {
										timerTask.cancel();
										time = 0.0;
										timeStarted = true;
										pasosdado = 0;
										pasos.setText("0");
										distancia.setText("0 Km");
										// Llamamos a poner el tiempo
										start.setText("PAUSE");
										tiempo.setText("00:00:00");
										distancia.setText("Calculando....");
										sensorManager.registerListener(Rene_MapsActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);


										horainicio = horaInicioD.getTime();

										Log.wtf("HoraReset", String.valueOf(horainicio));
										Timestamp horai = new Timestamp(horaInicioD);
										Log.wtf("HoraReset", horai.toString());

										startTimer();
								   }
							  }
						 });
						 resetAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							  @Override
							  public void onClick(DialogInterface dialog, int which) {
								   // no hace nada
								   sensorManager.registerListener(Rene_MapsActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
								   startTimer();
								   dialog.cancel();
							  }
						 });
						 resetAlert.show();
					}
			   }
		  });
	 }

	 @Override
	 protected void onStart() {
		  super.onStart();
		  fStore = FirebaseFirestore.getInstance();
		  if(mAuth.getCurrentUser() == null){
			   FirebaseAuth.getInstance().signOut();
			   FirebaseFirestore.getInstance().terminate();
			   Intent i = new Intent(Rene_MapsActivity.this, LoginActivityMichel.class);
			   startActivity(i);
			   finish();
			   return;
		  }
		  userId = mAuth.getCurrentUser().getUid();
	 }

	 public void actualizarDatos(Date horainicio, Date horafinal, String distancia, String pasos, String tiempo) {
	 	DocumentReference docRef = fStore.collection("recorridos").document(userId);
	 	Log.wtf("UserID: ", userId+ "");

	 	docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
			@Override
			public void onComplete(@NonNull Task<DocumentSnapshot> task) {
				if(task.isSuccessful()){
					DocumentSnapshot document = task.getResult();

					if(document.getData() == null){
						Map<String,Object> recorridos = new HashMap<>();
						recorridos.put("0", Arrays.asList(horainicio,horafinal,distancia,pasos, tiempo));
						fStore.collection("recorridos").document(userId)
								.set(recorridos)
								.addOnSuccessListener(new OnSuccessListener<Void>() {
									@Override
									public void onSuccess(Void aVoid) {
										Log.d("Nuevo documento", "DocumentSnapshot successfully written!");
									}
								})
								.addOnFailureListener(new OnFailureListener() {
									@Override
									public void onFailure(@NonNull Exception e) {
										Log.w("No se pudo crear el documento", "Error writing document", e);
									}
								});
					}else{
						Map<String,Object> lista_recorridos = document.getData();

						Log.wtf("Tamaño de los datos ", lista_recorridos.size() + "");

						for (Map.Entry<String,Object> entry : lista_recorridos.entrySet()){
							Log.wtf("Objetos que tenemos","Key = " + entry.getKey() + ", Value = " + entry.getValue());
						}

						if(document.exists()){
							Map<String,Object> recorridos = document.getData();
							recorridos.put(recorridos.size()+"", Arrays.asList(horainicio,horafinal,distancia,pasos, tiempo));
							docRef.update(recorridos).addOnSuccessListener(new OnSuccessListener<Void>() {
								@Override
								public void onSuccess(Void aVoid) {
									Log.wtf("Usuario creado - ", "onSuccess: user Profile is created for " + userId);

								}
							}).addOnFailureListener(new OnFailureListener() {
								@Override
								public void onFailure(@NonNull Exception e) {
									Log.d("Error usuario:", "onFailure: " + e.toString());
								}
							});
						}

					}

				}
			}
		});
	 }

					  // Nos encontramos
	 private void getCurrentLocation() {
		  // Ya checamos, no hace falta quitar lo rojito
		  Task<Location> task = client.getLastLocation();
		  task.addOnSuccessListener(new OnSuccessListener<Location>() {
			   @Override
			   public void onSuccess(Location location) {
					if(location != null){
						 // Sync map
						 supportMapFragment.getMapAsync(new OnMapReadyCallback() {
							  @Override
							  public void onMapReady(GoogleMap googleMap) {
								   // Empezar
								   userlocation = new LatLng(location.getLatitude(), location.getLongitude());
								   // Marker Options
								   MarkerOptions options = new MarkerOptions().position(userlocation)
										   .title("Your location");
								   // Zoom map
								   googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userlocation, 20));
								   //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userlocation,20));
								   // Add marker
								   googleMap.addMarker(options);
								   //mMap.addMarker(options);
								   // Ya esta verificada, no pasa nada con lo rojito
								   googleMap.setMyLocationEnabled(true);
								   //mMap.setMyLocationEnabled(true);
							  }
						 });
					}
			   }
		  });
	 }

	 @Override
	 public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		  if(requestCode == 44){
			   if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
					// when granted
					Toast.makeText(this, "PERMISO CONCEDIDO", Toast.LENGTH_SHORT).show();
					getCurrentLocation();
			   }
		  }
	 }

	 public void startTimer(){

		  timerTask = new TimerTask() {
			   @Override
			   public void run() {
					runOnUiThread(new Runnable() {
						 @Override
						 public void run() {
							  time++;
							  tiempo.setText(getTimerText(time));
						 }
					});
			   }
		  };
		  //timer.scheduleAtFixedRate(timerTask,0,1000);
		  timer.scheduleAtFixedRate(timerTask,0,1000);
	 }

	 public String getTimerText(double time){

		  int rounded = (int) Math.round(time);

		  int horas = 0;
		  int minutos = 0;
		  int segundos = 0;

		  segundos = ((rounded % 86400) % 3600) % 60;
		  minutos = ((rounded % 86400) % 3600) / 60;
		  horas = ((rounded % 86400) / 3600);

		  String h = String.valueOf(horas);
		  String m = String.valueOf(minutos);
		  String s = String.valueOf(segundos);


		  return formatTime(segundos, minutos, horas);
	 }

	 private String formatTime(int segundos, int minutos, int horas){

		  //String f =
		  return String.format(new Locale("es","MX"), "%02d", horas) + ":" + String.format(new Locale("es","MX"), "%02d", minutos) + ":" + String.format(new Locale("es","MX"), "%02d", segundos);
	 }

	 @Override
	 public void onMapClick(LatLng latLng) {

	 }

	 @Override
	 public void onSensorChanged(SensorEvent event) {
		  if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			   stepDetector.updateAccel(event.timestamp, event.values[0], event.values[1], event.values[2]);
		  }
	 }

	 @Override
	 public void onAccuracyChanged(Sensor sensor, int accuracy) {

	 }

	 @Override
	 public void step(long timeNS) {
		  pasosdado++;
		  pasos.setText(String.valueOf(pasosdado));
	 }

	 public float Distance(int pasosdados){

		  float distancia = 0;

		  // Buscamos las siguientes aproximaciones en Google
		  distancia = (float) (pasosdados*78) / (float) 100000; // es 100000 porque esta en cm y pasamos a km

		  return distancia;
	 }
}