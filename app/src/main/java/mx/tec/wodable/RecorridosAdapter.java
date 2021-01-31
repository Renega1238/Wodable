package mx.tec.actividad22;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Hashtable;

public class RecorridosAdapter  extends RecyclerView.Adapter<RecorridoViewHolder>{
    private Hashtable<String, FriendFragment> HT_Recorridos;
    private View.OnClickListener listener;
    private boolean clicked=false;

    public RecorridosAdapter(Hashtable<String, FriendFragment> HT_Recorridos, View.OnClickListener listener)
    {
        this.HT_Recorridos = HT_Recorridos;
        this.listener = listener;
    }
    @NonNull
    @Override
    public RecorridoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        v.setOnClickListener(listener);
        RecorridoViewHolder friendRow = new RecorridoViewHolder(v);
        return friendRow;
    }

    @Override
    public void onBindViewHolder(@NonNull RecorridoViewHolder holder, int position) {
        String pos = String.valueOf((position+1));
        holder.nombre.setText(HT_Recorridos.get(pos).getNombre1());
        holder.hobby.setText(HT_Recorridos.get(pos).getHobby1());

        Log.wtf("Nombre??", HT_Recorridos.get(pos).getNombre1());


        /*holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.wtf("Clickeo desde adapter", position+"");
                if(!clicked){
                    holder.nombre.setText(HT_Recorridos.get(pos).getNombre());
                    holder.hobby.setText(HT_Recorridos.get(pos).getHobby());
                    holder.edad.setText(HT_Recorridos.get(pos).getEdad());
                    holder.telefono.setText(HT_Recorridos.get(pos).getTelefono());
                    holder.direccion.setText(HT_Recorridos.get(pos).getDireccion());
                    //holder.celda.setBackgroundColor(Color.parseColor("#"+String.valueOf(R.color.color_noclick)));
                    String hexColor = String.format("#%06X", (0xFFFFFF & R.color.color_click));
                    //holder.celda.setBackgroundColor(Color.parseColor(hexColor));
                    holder.celda.setBackgroundColor(Color.rgb(76,182,233));
                    clicked=true;
                }else{
                    holder.nombre.setText(HT_Recorridos.get(pos).getNombre());
                    holder.hobby.setText(HT_Recorridos.get(pos).getHobby());
                    holder.edad.setText("");
                    holder.telefono.setText("");
                    holder.direccion.setText("");
                    //holder.celda.setBackgroundColor(Color.parseColor("#"+String.valueOf(R.color.color_click)));
                    Log.wtf("COLOR", R.color.color_click+"");
                    Log.wtf("COLOR", R.color.color_noclick+"");
                    String hexColor = String.format("#%06X", (0xFFFFFF & R.color.color_noclick));
                    holder.celda.setBackgroundColor(Color.rgb(76,205,83));
                    clicked=false;
                }
            }
        });*/
    }
    @Override
    public int getItemCount() {
        return this.HT_Recorridos.size();
    }
}
