package pe.com.app.unibell.appunibell.AD;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pe.com.app.unibell.appunibell.BE.ClientesBE;
import pe.com.app.unibell.appunibell.Clientes.Activity_EstadoCuenta;
import pe.com.app.unibell.appunibell.Cobranza.Activity_Cobranza_Recibo;
import pe.com.app.unibell.appunibell.Cobranza.Activity_Cobranzas;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Clientes_Adapter extends ArrayAdapter<ClientesBE> implements Filterable {
    public List<ClientesBE> lstFiltrado;
    public List<ClientesBE> lst;
    private AdapterFilter adapterFilter;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;


    public Clientes_Adapter(Context context, int resource, List<ClientesBE> objects) {
        super(context, resource, objects);
        adapterFilter = new AdapterFilter();
        lstFiltrado = new ArrayList<>();
        lstFiltrado.addAll(objects);
        lst = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainHolder mainHolder = null;
        if (convertView == null || !(convertView.getTag() instanceof MainHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_clientes, parent, false);
            mainHolder = new MainHolder();
            mainHolder.cl_item2 = (TextView) convertView.findViewById(R.id.cl_item2);
            mainHolder.cl_item3 = (TextView) convertView.findViewById(R.id.cl_item3);
            mainHolder.cl_col3 = (TextView) convertView.findViewById(R.id.cl_col3);

            mainHolder.cl_btn1 = (Button)convertView.findViewById(R.id.cl_btn1);
            mainHolder.cl_btn2 = (Button)convertView.findViewById(R.id.cl_btn2);
            mainHolder.cl_btn3 = (TextView) convertView.findViewById(R.id.cl_btn3);


            convertView.setTag(mainHolder);
        } else {
            mainHolder = (MainHolder) convertView.getTag();
        }
        final ClientesBE clienteBE = getItem(position);
        mainHolder.cl_item2.setText( new Funciones().LetraCapital( clienteBE.getRAZON_SOCIAL().toString().trim()));

        if(Double.valueOf(clienteBE.getM_PAE()) > 0.0 ) {
            mainHolder.cl_item3.setText(clienteBE.getM_PAE().toString().trim());

            mainHolder.cl_item3.setVisibility(View.VISIBLE);
            mainHolder.cl_col3.setVisibility(View.VISIBLE);
        }
        else
        {
            mainHolder.cl_item3.setVisibility(View.GONE);
            mainHolder.cl_col3.setVisibility(View.GONE);

        }

        mainHolder.cl_btn3.setText(clienteBE.getCODIGO_ANTIGUO().toString().trim());

        mainHolder.cl_btn1.setTag(position);
        mainHolder.cl_btn2.setTag(position);
        mainHolder.cl_btn3.setTag(position);

        mainHolder.cl_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                editor_Shared.putString("CODIGO_ANTIGUO", clienteBE.getCODIGO_ANTIGUO().toString());
                editor_Shared.putString("RAZON_SOCIAL", clienteBE.getRAZON_SOCIAL().toString());
                editor_Shared.putString("COBRANZA_EVENTO","0");
                editor_Shared.putString("sN_SERIE_RECIBO", "");
                editor_Shared.putString("sN_RECIBO", "");

                Intent intent = new Intent(getContext().getApplicationContext(), Activity_Cobranza_Recibo.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });


        mainHolder.cl_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                try {
                    sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                    editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();
                    editor_Shared.putString("COBRANZA_EVENTO","0");
                    editor_Shared.putString("CODIGO_ANTIGUO", clienteBE.getCODIGO_ANTIGUO().toString());
                    editor_Shared.putString("RAZON_SOCIAL", clienteBE.getRAZON_SOCIAL().toString());
                    editor_Shared.putString("PAE", clienteBE.getM_PAE().toString());

                    editor_Shared.putString("cpserie","");
                    editor_Shared.putString("cpnumero","");
                    editor_Shared.putString("cpfplanilla", "");
                    editor_Shared.commit();

                    editor_Shared.commit();

                    Intent intent = new Intent(getContext().getApplicationContext(), Activity_Cobranzas.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);

                }catch (Exception ex) {
                }
            }
        });

        mainHolder.cl_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                notifyDataSetChanged();
                sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                editor_Shared.putString("CODIGO_ANTIGUO", clienteBE.getCODIGO_ANTIGUO().toString());
                editor_Shared.putString("RAZON_SOCIAL", clienteBE.getRAZON_SOCIAL().toString());
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

    static class MainHolder {
        TextView cl_item2,cl_item3,cl_btn3, cl_col3;
        Button cl_btn1,cl_btn2;
        LinearLayout cl_ln1,cl_ln2,cl_ln3;

    }

    @Override
    public int getCount() {
        return lstFiltrado.size();
    }

    @Override
    public ClientesBE getItem(int position) {
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
                List<ClientesBE> localList = new ArrayList<>();
                String[] SSA=constraint.toString().toUpperCase().split(" ");
                for (ClientesBE clienteBE : lst) {
                    String todo = (clienteBE.getRAZON_SOCIAL().toUpperCase() + clienteBE.getCODIGO_ANTIGUO().toUpperCase());
                    Boolean bfind = true;
                    for(String s : SSA){
                        if(!todo.contains(s)){
                            bfind = false;
                        }
                    }
                    if (bfind != false){
                        localList.add(clienteBE);
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