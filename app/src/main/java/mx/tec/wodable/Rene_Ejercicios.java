package mx.tec.wodable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Rene_Ejercicios extends AppCompatActivity implements View.OnClickListener{

	 RecyclerView recyclerView;

	 String ejercicios[], descripcion[];
	 int image[] = {R.drawable.dance,R.drawable.lunges,R.drawable.pikepushups,R.drawable.pushpress
	 ,R.drawable.pushups,R.drawable.run,R.drawable.situps,R.drawable.skipping,R.drawable.squats,R.drawable.yoga};

	 ConstraintLayout constraintLayout;

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.activity_rene__ejercicios);

		  recyclerView = findViewById(R.id.Rene_recylcerview);

		  ejercicios = getResources().getStringArray(R.array.Ejercicios);
		  descripcion = getResources().getStringArray(R.array.Descripcion);


		  //Rene_MyAdapter rma = new Rene_MyAdapter(this,ejercicios,descripcion,image);

		  Rene_MyAdapter rma = new Rene_MyAdapter(ejercicios,descripcion,image,this);
		  LinearLayoutManager llm = new LinearLayoutManager(this);
		  llm.setOrientation(LinearLayoutManager.VERTICAL);
		  recyclerView.setLayoutManager(llm);
		  recyclerView.setAdapter(rma);
		  //recyclerView.setLayoutManager(new LinearLayoutManager(this));
	 }

	 @Override
	 public void onClick(View v) {
		  int posicion = recyclerView.getChildLayoutPosition(v);
		  Toast.makeText(this,"Añadido a Favoritos: " + ejercicios[posicion],Toast.LENGTH_SHORT).show();
	 }
}