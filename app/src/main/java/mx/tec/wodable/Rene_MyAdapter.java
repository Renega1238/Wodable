package mx.tec.wodable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class Rene_MyAdapter extends RecyclerView.Adapter<Rene_MyAdapter.ReneViewHolder> {

	 ConstraintLayout constraintLayout;

	 public class ReneViewHolder extends RecyclerView.ViewHolder{
		  TextView ejercico, descripcion;
		  ImageView imagen;


		  public ReneViewHolder(@NonNull View itemView){
		  	 super(itemView);

			   ejercico = itemView.findViewById(R.id.rene_ejercicios);
			   descripcion = itemView.findViewById(R.id.rene_descripcion);
			   imagen = itemView.findViewById(R.id.rene_imagen);
			   constraintLayout = itemView.findViewById(R.id.Rene_constraint);
		  }
	 }


	 // Info a usar
	 String ejercicios[], descripcion[];
	 int imagenes[];
	 private  View.OnClickListener listener;

	 public Rene_MyAdapter(String ejercicios[], String descripcion[], int imagenes[], View.OnClickListener listener){
	 	 this.ejercicios = ejercicios;
	 	 this.descripcion = descripcion;
	 	 this.imagenes = imagenes;
	 	 this.listener = listener;
	 }

	 @NonNull
	 @Override
	 public Rene_MyAdapter.ReneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		  View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.rene_row, parent, false);
		  //View v = inflater.inflate(R.layout.rene_row, parent, false);
		  ReneViewHolder rvh = new ReneViewHolder(v);

		  v.setOnClickListener(listener);
		  //return new MyViewHolder(v);
		  return rvh;
	 }

	 @Override
	 public void onBindViewHolder(@NonNull ReneViewHolder holder, int position) {
	 	 holder.ejercico.setText(ejercicios[position]);
	 	 holder.descripcion.setText(descripcion[position]);
	 	 holder.imagen.setImageResource(imagenes[position]);
	 }

	 @Override
	 public int getItemCount() {
		  return ejercicios.length;
	 }
	 
}
