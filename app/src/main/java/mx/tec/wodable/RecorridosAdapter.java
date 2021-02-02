package mx.tec.wodable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Hashtable;

public class RecorridosAdapter  extends RecyclerView.Adapter<RecorridoViewHolder>{
    private Hashtable<String, Recorrido> HT_Recorridos;
    private View.OnClickListener listener;
    private boolean clicked=false;

    public RecorridosAdapter(Hashtable<String, Recorrido> HT_Recorridos, View.OnClickListener listener)
    {
        this.HT_Recorridos = HT_Recorridos;
        this.listener = listener;
    }
    @NonNull
    @Override
    public RecorridoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.recorridorow, parent, false);
        v.setOnClickListener(listener);
        RecorridoViewHolder recorridoRow = new RecorridoViewHolder(v);
        return recorridoRow;
    }

    @Override
    public void onBindViewHolder(@NonNull RecorridoViewHolder holder, int position) {
        String pos = String.valueOf(position);
        int p = HT_Recorridos.get(pos).getId_recorrido() + 1;
        holder.id_recorrido.setText(p+"");
        holder.tiempoCarrera.setText(HT_Recorridos.get(pos).getTiempo_carrera());
        holder.distancia.setText(HT_Recorridos.get(pos).getDistancia());
        holder.pasos.setText(HT_Recorridos.get(pos).getPasos());
    }

    @Override
    public int getItemCount() {
        return HT_Recorridos.size();
    }

}