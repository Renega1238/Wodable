// Esta clase
// edit_info_personal.java se comunica con la BD
// en la collecion de atributos_fisicos

package mx.tec.wodable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class edit_info_personal extends AppCompatActivity implements NumberPicker.OnValueChangeListener{

    private EditText peso,altura,txtDate;
    private Button actualizar, btnDatePicker;
    private static boolean clickedPeso=false,
                           clickedAltura=false;
    private static String gender="";
    private RadioGroup radioGenderGroup;
    private RadioButton radioGenderButton;
    /*
     *  Fecha de nacimiento picker
     */
    private int mYear, mMonth, mDay;
    /*
     *  Variables bases de datos
     */
    private String userId;
    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info_personal);

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        txtDate=(EditText)findViewById(R.id.in_date);
        peso=findViewById(R.id.editTextPeso);
        altura=findViewById(R.id.editTextAltura);
        actualizar=findViewById(R.id.buttonAct);
        radioGenderGroup = (RadioGroup) findViewById(R.id.radioGenderGroup);

        // Referencia a la DB de usuarios autenticados
        mAuth = FirebaseAuth.getInstance();

        peso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPickerForWeight(v);
                edit_info_personal.clickedPeso = true;
            }
        });
        altura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPickerForHeight(v);
                edit_info_personal.clickedAltura = true;
            }
        });
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(v);
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderSelect(v);
                validate(v);
            }
        });
    }

    // Ciclo de android cuando se entra
    // A la pantalla de editar perfil
    @Override
    protected void onStart() {
        super.onStart();
        fStore = FirebaseFirestore.getInstance();
        if(mAuth.getCurrentUser() == null){
            FirebaseAuth.getInstance().signOut();
            FirebaseFirestore.getInstance().terminate();
            Intent i = new Intent(edit_info_personal.this, LoginActivityMichel.class);
            startActivity(i);
            finish();
            return;
        }
        userId = mAuth.getCurrentUser().getUid();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //if(!success || SignUpActivity_Michel.isNewUser())
        //actualizarDatos("70 kg.","1.70 m.", "Hombre", "1-1-1994");
    }

    @Override
    public void onBackPressed(){
        // Bandera para saber si es un nuevo usuario
        // recien logeado en la aplicación
        if(SignUpActivity_Michel.isNewUser()){

            validate();
            return;
        }else{
            // Regresalo a Main Activity profile
            Intent i = new Intent(edit_info_personal.this, ProfileInfoActivity.class);
            startActivity(i);
            finish();
        }
    }


    public void validate(View v){
        validate();
    }

    public void validate(){
        String weight = peso.getText().toString().trim();
        String height = altura.getText().toString().trim();
        String gender  = edit_info_personal.gender; // Hombre o mujer
        String birthday = txtDate.getText().toString().trim();

        // Si está vacío alguno de estos datos no sé actualiza en la base de datos
        if(weight.isEmpty() || height.isEmpty()
                || birthday.isEmpty() || gender.isEmpty()){
            Toast.makeText(edit_info_personal.this,"Llena los campos vacíos",Toast.LENGTH_SHORT).show();
            if(weight.isEmpty()){
                Toast.makeText(edit_info_personal.this,"Llena el campo de peso",Toast.LENGTH_SHORT).show();
            }else if(height.isEmpty()){
                Toast.makeText(edit_info_personal.this,"Llena el campo de altura",Toast.LENGTH_SHORT).show();
            }else if(birthday.isEmpty())
            {
                Toast.makeText(edit_info_personal.this,"Llena la fecha de nacimiento",Toast.LENGTH_SHORT).show();
            }
        }else{
            actualizarDatos(weight,height,gender,birthday);
        }
    }


    private static boolean success = false;
    public void actualizarDatos(String peso,String altura,String genero,String fecha_de_nacimiento){
        DocumentReference documentReference = fStore.collection("atributos_fisicos").document(userId);
        Map<String, Object> atributos_fisicos = new HashMap<>();
        atributos_fisicos.put("peso", peso);
        atributos_fisicos.put("altura", altura);
        atributos_fisicos.put("genero", genero);
        atributos_fisicos.put("fecha_de_nacimiento", fecha_de_nacimiento);
        atributos_fisicos.put("id_usuario", userId);
        documentReference.set(atributos_fisicos).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(edit_info_personal.this,"Datos actualizados",Toast.LENGTH_SHORT).show();
                Log.wtf("Atributos creados (Atributos): ", "Datos actualizados");
                success = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(edit_info_personal.this,"¡Algo pasó!",Toast.LENGTH_SHORT).show();
                Log.d("Algo pasó (Atributos): ", "onFailure: " + e.toString());
                success = false;
            }
        });

        // Cuando se actualicen los datos llevar a otra pantalla
        Intent i = new Intent(edit_info_personal.this, ProfileInfoActivity.class);
        startActivity(i);
        finish();
    }
    public void genderSelect(View v){
        int selectedId = radioGenderGroup.getCheckedRadioButtonId();
        radioGenderButton = (RadioButton) findViewById(selectedId);
        String res = (String) radioGenderButton.getText();
        //Toast.makeText(edit_info_personal.this,res,Toast.LENGTH_SHORT).show();
        Log.wtf("Gender", res +"" );
        gender=res;
    }
    public void pickDate(View v){
            // La fecha inicial
            final Calendar date = Calendar.getInstance();
            // Aqui inicia visualmente en el año 1994 para crear la fecha de nacimiento
            date.set(Calendar.YEAR, 1994);
            date.set(Calendar.DAY_OF_MONTH, 1);
            date.set(Calendar.MONTH, 0);
            mYear = date.get(Calendar.YEAR);
            mMonth = date.get(Calendar.MONTH);
            mDay = date.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            if(year <= 2003){
                                txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }else{
                                Toast.makeText(edit_info_personal.this, "¡Tienes que tener más de 18 años!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, mYear, mMonth, mDay);

            // El número 19 es a partir de 1994 declarado arriba + 19 = 2013
            // para validar mayores a 18
            date.set(Calendar.YEAR, date.get(Calendar.YEAR)+19);
            datePickerDialog.getDatePicker().setMaxDate(date.getTimeInMillis());
            datePickerDialog.show();
    }
    // Función que detecta cuando hay un cambio en el scroll del numberPicker
    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {

        if(edit_info_personal.clickedPeso){
            if(WeightPickerDialogFragment.CANCELAR == false)
            peso.setText(WeightPickerDialogFragment.arr[newVal] + " kg.");
            clickedPeso=false;
        }
        if(edit_info_personal.clickedAltura){
            // Es una bandera para decir si cancelo en showDialog no me sobreescriba datos
            if(HeightPickerDialogFragment.CANCELAR == false)
            altura.setText(HeightPickerDialogFragment.arr[newVal] + " m.");
            clickedAltura=false;
        }
    }
    public void showNumberPickerForWeight(View v){
        WeightPickerDialogFragment newFragment = new WeightPickerDialogFragment();
        newFragment.setValueChangeListener(this);
        newFragment.show(getSupportFragmentManager(), "number picker");
        //Log.wtf("edit_info_personal", "fallo aqui");
    }
    public void showNumberPickerForHeight(View v){
        HeightPickerDialogFragment newFragment = new HeightPickerDialogFragment();
        newFragment.setValueChangeListener(this);
        newFragment.show(getSupportFragmentManager(), "number picker height");
        //Log.wtf("edit_info_personal altura", "fallo aqui altura");
    }


}