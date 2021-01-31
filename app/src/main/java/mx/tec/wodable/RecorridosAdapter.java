/*package mx.tec.wodable;

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

public class FriendsAdapter  extends RecyclerView.Adapter<FriendViewHolder>{
    private Hashtable<String, FriendFragment> HT_Friends;
    private View.OnClickListener listener;
    private boolean clicked=false;

    public FriendsAdapter(Hashtable<String, FriendFragment> HT_Friends, View.OnClickListener listener)
    {
        this.HT_Friends = HT_Friends;
        this.listener = listener;
    }
    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        v.setOnClickListener(listener);
        FriendViewHolder friendRow = new FriendViewHolder(v);
        return friendRow;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        String pos = String.valueOf((position+1));
        holder.nombre.setText(HT_Friends.get(pos).getNombre1());
        holder.hobby.setText(HT_Friends.get(pos).getHobby1());

        Log.wtf("Nombre??", HT_Friends.get(pos).getNombre1());


        /*holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.wtf("Clickeo desde adapter", position+"");
                if(!clicked){
                    holder.nombre.setText(HT_Friends.get(pos).getNombre());
                    holder.hobby.setText(HT_Friends.get(pos).getHobby());
                    holder.edad.setText(HT_Friends.get(pos).getEdad());
                    holder.telefono.setText(HT_Friends.get(pos).getTelefono());
                    holder.direccion.setText(HT_Friends.get(pos).getDireccion());
                    //holder.celda.setBackgroundColor(Color.parseColor("#"+String.valueOf(R.color.color_noclick)));
                    String hexColor = String.format("#%06X", (0xFFFFFF & R.color.color_click));
                    //holder.celda.setBackgroundColor(Color.parseColor(hexColor));
                    holder.celda.setBackgroundColor(Color.rgb(76,182,233));
                    clicked=true;
                }else{
                    holder.nombre.setText(HT_Friends.get(pos).getNombre());
                    holder.hobby.setText(HT_Friends.get(pos).getHobby());
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
        });
    }
    //@Override
    public int getItemCount() {
        return this.HT_Friends.size();
    }
}
*/