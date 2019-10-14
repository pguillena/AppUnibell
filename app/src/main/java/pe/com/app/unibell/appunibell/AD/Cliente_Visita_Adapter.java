package pe.com.app.unibell.appunibell.AD;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import pe.com.app.unibell.appunibell.BE.VisitaDetBE;
import pe.com.app.unibell.appunibell.Clientes.Activity_EstadoCuenta;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Cliente_Visita_Adapter extends ArrayAdapter<VisitaDetBE> implements Filterable {

    public List<VisitaDetBE> lstFiltrado;
    public List<VisitaDetBE> lst;
    private AdapterFilter adapterFilter;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    public Cliente_Visita_Adapter(Context context, int resource, List<VisitaDetBE> objects) {
        super(context, resource, objects);
        adapterFilter = new AdapterFilter();
        lstFiltrado = new ArrayList<>();
        lstFiltrado.addAll(objects);
        lst = objects;
    }

    static class MainHolder {
        TextView txtVisitaCliente, txtVisitaDireccion, txtVisitaFrecuencia, txtVisitaSituacion, txtVisitaDias, txtVisitaTotalDeuda;
        Button btnVisita;
        ImageView ivMarkerVisita;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainHolder mainHolder = null;
        if (convertView == null || !(convertView.getTag() instanceof MainHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cliente_visita, parent, false);
            mainHolder = new MainHolder();
            mainHolder.txtVisitaCliente = (TextView) convertView.findViewById(R.id.txtVisitaCliente);
            mainHolder.txtVisitaDireccion = (TextView) convertView.findViewById(R.id.txtVisitaDireccion);
            mainHolder.txtVisitaFrecuencia = (TextView) convertView.findViewById(R.id.txtVisitaFrecuencia);
            mainHolder.txtVisitaSituacion = (TextView) convertView.findViewById(R.id.txtVisitaSituacion);
            mainHolder.txtVisitaDias = (TextView) convertView.findViewById(R.id.txtVisitaDias);
            mainHolder.txtVisitaTotalDeuda = (TextView) convertView.findViewById(R.id.txtVisitaTotalDeuda);
            mainHolder.btnVisita = (Button)convertView.findViewById(R.id.btnVisita);
            mainHolder.ivMarkerVisita = (ImageView) convertView.findViewById(R.id.ivMarkerVisita);


            convertView.setTag(mainHolder);
        } else {
            mainHolder = (MainHolder) convertView.getTag();
        }
        final VisitaDetBE visitaDetBE = getItem(position);
        mainHolder.txtVisitaCliente.setText( new Funciones().LetraCapital(visitaDetBE.getC_CLIENTE().toString().trim() +" - "+ visitaDetBE.getRAZON_SOCIAL().toString().trim()));
        mainHolder.txtVisitaDireccion.setText( new Funciones().LetraCapital(visitaDetBE.getDIRECCION().toString().trim() ));
        mainHolder.txtVisitaFrecuencia.setText( new Funciones().LetraCapital(visitaDetBE.getFRECUENCIA_VISITA().toString().trim() ));
        mainHolder.txtVisitaSituacion.setText( new Funciones().LetraCapital(visitaDetBE.getSITUACION().toString().trim() ));
        mainHolder.txtVisitaDias.setText( new Funciones().LetraCapital(visitaDetBE.getDIAS().toString().trim() ));
        mainHolder.txtVisitaTotalDeuda.setText( new Funciones().LetraCapital(String.valueOf(visitaDetBE.getTOTAL_DEUDA())));

        if(visitaDetBE.getVISITADO()>0)
        {
            mainHolder.ivMarkerVisita.setVisibility(View.VISIBLE);
        }
        else
        {
            mainHolder.ivMarkerVisita.setVisibility(View.GONE);
        }

        mainHolder.btnVisita.setTag(position);

        mainHolder.btnVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        mainHolder.txtVisitaCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    notifyDataSetChanged();
                    sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                    editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                    editor_Shared.putString("CODIGO_ANTIGUO", visitaDetBE.getC_CLIENTE().toString());
                    editor_Shared.putString("RAZON_SOCIAL", visitaDetBE.getRAZON_SOCIAL().toString());
                    editor_Shared.commit();

                    Intent intent = new Intent(getContext().getApplicationContext(), Activity_EstadoCuenta.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }catch (Exception ex) {
                }

            }
        });

        return convertView;
    }



    @Override
    public int getCount() {
        return lstFiltrado.size();
    }


    @Override
    public VisitaDetBE getItem(int position) {
        return lstFiltrado.get(position);
    }


    @Override
    public Filter getFilter() {
        if (adapterFilter == null) {
            adapterFilter = new AdapterFilter();
        }
        return adapterFilter;
    }

    private class AdapterFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint == null || constraint.length() <= 0) {
                filterResults.values = new ArrayList<>(lst);
                filterResults.count = lst.size();
            } else {
                List<VisitaDetBE> localList = new ArrayList<>();
                String[] SSA=constraint.toString().toUpperCase().split(" ");
                for (VisitaDetBE visitaDetBE : lst) {
                    String todo = (visitaDetBE.getRAZON_SOCIAL().toUpperCase().replace("  ","").replace(".","")
                            + visitaDetBE.getC_CLIENTE().toUpperCase());
                    Boolean bfind = true;
                    for(String s : SSA){
                        if(!todo.contains(s)){
                            bfind = false;
                        }
                    }
                    if (bfind != false){
                        localList.add(visitaDetBE);
                    }
                }
                filterResults.values = new ArrayList<>(localList);
                filterResults.count = localList.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            lstFiltrado.clear();
            lstFiltrado.addAll((List) results.values);
            if(results != null && results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }


}
