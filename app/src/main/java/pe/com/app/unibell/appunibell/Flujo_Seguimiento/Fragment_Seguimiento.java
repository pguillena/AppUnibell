package pe.com.app.unibell.appunibell.Flujo_Seguimiento;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import pe.com.app.unibell.appunibell.R;
import static android.content.Context.MODE_PRIVATE;

public class Fragment_Seguimiento extends Fragment {

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seguimiento, container, false);
        try {
            sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

            String myTag = getTag();
            ((Activity_Flujo_Seguimiento) getActivity()).setFragment_seguimiento(myTag);


        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }finally {

        }
        return view;
    }

    public void CargarData(){

    }



}
