package mx.tec.wodable;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/* Clase que se encarga de administrar una vista de renglón */
public class RecorridoViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener */{

    /* Variables que hacen referencia a row.xml */
    protected TextView id_recorrido, tiempoInicialTotal, tiempoFinalTotal, distancia, pasos, tiempoCarrera;
    protected androidx.constraintlayout.widget.ConstraintLayout celda;
   //private ItemClickListener itemClickListener;

  /*  public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }*/

    // Aqui se puede manipular lo del reglon
    public RecorridoViewHolder(@NonNull View itemView) {
        super(itemView);

        /* Se hace referencia a row.xml y sus TextViews */
        /*nombre = itemView.findViewById(R.id.nombre);
        hobby = itemView.findViewById(R.id.hobby);
        edad = itemView.findViewById(R.id.edad);
        telefono = itemView.findViewById(R.id.telefono);
        direccion = itemView.findViewById(R.id.direccion);

        celda = itemView.findViewById(R.id.celda);*/
        //itemView.setOnClickListener(this);

        id_recorrido = itemView.findViewById(R.id.id_recorrido);
        tiempoInicialTotal = itemView.findViewById(R.id.tiempoInicialTotal);
        tiempoFinalTotal = itemView.findViewById(R.id.tiempoFinalTotal);
        distancia = itemView.findViewById(R.id.distancia);
        pasos = itemView.findViewById(R.id.pasos);
        tiempoCarrera = itemView.findViewById(R.id.tiempoCarrera);

    }

    /*
    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }

     */
}