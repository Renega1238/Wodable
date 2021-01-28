package mx.tec.wodable;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;
import androidx.fragment.app.DialogFragment;

public class WeightPickerDialogFragment extends DialogFragment {

    private NumberPicker.OnValueChangeListener valueChangeListener;
    private static final int MIN_INTERVAL = 0; // empieza de 30kg
    private static final int MAX_INTERVAL = 400; // una division de intervalos 230kg en 400
    private static final int PESO_INICIAL = 30; // Inicia de 30kg
    private static  int PESO_DEFAULT = 50; // El scroll inicia en 55 kg con valor en 50
    public static  String[] arr = new String[(MAX_INTERVAL-MIN_INTERVAL)+1];
    public static boolean CANCELAR=false;

    public static String[] intervalos(int low, int max){
        float peso_inicial = PESO_INICIAL;
        float gramos = (float) 0.5;
        String[] arr = new String[(max-low)+1];
        for(int i=0;i<arr.length;i++){
            if(i%2==0){
                arr[i] = String.valueOf(peso_inicial);
            }else{
                arr[i] = String.valueOf(peso_inicial+gramos);
                peso_inicial++;
            }
        }
            return arr;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final  NumberPicker numberPicker = new NumberPicker(getActivity());
        numberPicker.setMinValue(MIN_INTERVAL);
        numberPicker.setMaxValue(MAX_INTERVAL);
        numberPicker.setValue(PESO_DEFAULT);
        CANCELAR = false; // Bandera para indicar que no le dio cancelar al showDialog

        arr = intervalos(MIN_INTERVAL,MAX_INTERVAL); // Arreglo con los valores de kilogramos
        numberPicker.setDisplayedValues(arr); // Armamos el display con el array d valores
        // de strings

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Tu peso: ");
        builder.setMessage("En kilogramos");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                valueChangeListener.onValueChange(numberPicker,
                        numberPicker.getValue(), numberPicker.getValue());

                PESO_DEFAULT =  numberPicker.getValue();
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