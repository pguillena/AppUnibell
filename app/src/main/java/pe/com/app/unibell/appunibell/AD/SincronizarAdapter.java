package pe.com.app.unibell.appunibell.AD;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pe.com.app.unibell.appunibell.BE.SincronizarBE;
import pe.com.app.unibell.appunibell.Main.Activity_Sincronizar;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Constants;

public class SincronizarAdapter  extends ArrayAdapter<SincronizarBE> {
    public ArrayList<SincronizarBE> items = null;
    public ArrayList<SincronizarBE> itemsFilter = null;
    public List<SincronizarBE> lstFiltrado;
    public List<SincronizarBE> lst;

    public SincronizarAdapter(Context context, int resource, ArrayList<SincronizarBE> objects) {
        super(context, resource, objects);
        Log.d(Constants.DEBUG_TAG, String.valueOf(objects.size()));
        lstFiltrado = new ArrayList<>();
        lstFiltrado.addAll(objects);
        lst = objects;
    }

    public interface CalledActivity {
        void MostrarResumen();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MainHolder mainHolder = null;
        final SincronizarBE item = getItem(position);

        if (convertView == null || !(convertView.getTag() instanceof MainHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sincronizacion, parent, false);
            mainHolder = new MainHolder();
            mainHolder.sin_lblproceso = (TextView) convertView.findViewById(R.id.sin_lblproceso);
            mainHolder.sin_lblFeha = (TextView) convertView.findViewById(R.id.sin_lblFeha);
            //mainHolder.sin_lblestado = (TextView) convertView.findViewById(R.id.sin_lblestado);
            mainHolder.sin_btntmpforward = (TextView) convertView.findViewById(R.id.sin_btntmpforward);
            convertView.setTag(mainHolder);
        } else {
            mainHolder = (MainHolder) convertView.getTag();
        }
        mainHolder.sin_lblproceso.setText(item.getDESCRIPCION());
        mainHolder.sin_lblFeha.setText(item.getFECHA().toString());
        //mainHolder.sin_lblestado.setText(item.getESTADO().toString());

        if (item.getESTADO().toString().trim().equals("OK")) {
            // mainHolder.sin_lblestado.setTextColor(ContextCompat.getColor(getContext(), R.color.Styletext_green));
            mainHolder.sin_btntmpforward.setText("Actualizado");
            mainHolder.sin_btntmpforward.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.check_bold),null,null,null);


        } else {

            mainHolder.sin_btntmpforward.setText("Actualizar");
            mainHolder.sin_btntmpforward.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.list_sincronizar_small),null,null,null);

        }

        mainHolder.sin_btntmpforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getContext() instanceof Activity_Sincronizar) {
                    ((Activity_Sincronizar) getContext()).Sincronizar(item.getPROCESO());
                }
            }
        });

        return convertView;
    }

    final class MainHolder {
        TextView sin_lblproceso, sin_lblFeha;
        TextView sin_btntmpforward;
    }

}