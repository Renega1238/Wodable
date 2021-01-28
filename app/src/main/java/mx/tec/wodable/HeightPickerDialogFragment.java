package mx.tec.wodable;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;
import androidx.fragment.app.DialogFragment;

public class HeightPickerDialogFragment extends DialogFragment {

    private NumberPicker.OnValueChangeListener valueChangeListener;
    private static final int MIN_INTERVAL = 0; // Min altura 1.20m
    private static final int MAX_INTERVAL = 110; // Max altura 2.20m
    private static  double ALTURA_INICIAL = 1.20; // ALtura inicial del scrol 1.20 m
    private static  int ALTURA_DEFAULT = 50; // El scroll inicia en 1.70m con valor en 50
    public static  String[] arr = new String[(MAX_INTERVAL-MIN_INTERVAL)+1];

    public static boolean CANCELAR=false;

    public static String[] intervalos(int low, int max){
        double altura_inicial = ALTURA_INICIAL;
        String[] arr = new String[(max-low)+1];
        for(int i=0;i<arr.length;i++){
                arr[i] = String.format("%.2f", altura_inicial);
                altura_inicial = altura_inicial + .01;
        }
        return arr;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final NumberPicker numberPicker = new NumberPicker(getActivity());
        numberPicker.setMinValue(MIN_INTERVAL);
        numberPicker.setMaxValue(MAX_INTERVAL);
        numberPicker.setValue(ALTURA_DEFAULT);
        CANCELAR = false; // Bandera para indicar que no le dio cancelar al showDialog

        arr = intervalos(MIN_INTERVAL,MAX_INTERVAL); // Arreglo con los valores de altura
        numberPicker.setDisplayedValues(arr); // Armamos el display con el array d valores
        // de strings

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Tu altura: ");
        builder.setMessage("En metros");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                valueChangeListener.onValueChange(numberPicker,
                        numberPicker.getValue(), numberPicker.getValue());

                ALTURA_DEFAULT = numberPicker.getValue();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CANCELAR = true;
                dialog.cancel();
            }
        });

        builder.setView(numberPicker);
        //builder.show();
        //Log.wtf("builder.show();", "fallo aqui");
        return builder.create();
    }

    public NumberPicker.OnValueChangeListener getValueChangeListener() {
        return valueChangeListener;
    }

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

}