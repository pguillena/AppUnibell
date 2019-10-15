package pe.com.app.unibell.appunibell.AD;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import pe.com.app.unibell.appunibell.BE.FactCobBE;
import pe.com.app.unibell.appunibell.Cobranza.Activity_Cobranzas;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Cliente_EstadoCuenta_Adapter extends ArrayAdapter<FactCobBE> implements Filterable {
    public List<FactCobBE> lstFiltrado;
    public List<FactCobBE> lst;
    private AdapterFilter adapterFilter;
    private Funciones funciones=new Funciones();
    private String sMoneda;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    public Cliente_EstadoCuenta_Adapter(Context context, int resource, List<FactCobBE> objects) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_clientes_estadocuenta, parent, false);
            mainHolder = new MainHolder();
            mainHolder.cp_item1 = (TextView) convertView.findViewById(R.id.cp_item1);
            mainHolder.cp_item2 = (TextView) convertView.findViewById(R.id.cp_item2);
            mainHolder.cp_item3 = (TextView) convertView.findViewById(R.id.cp_item3);
            mainHolder.cp_item4 = (TextView) convertView.findViewById(R.id.cp_item4);
            //mainHolder.cp_item5 = (TextView) convertView.findViewById(R.id.cp_item5);
            //mainHolder.cp_item6 = (TextView) convertView.findViewById(R.id.cp_item6);
            mainHolder.cp_item7 = (TextView) convertView.findViewById(R.id.cp_item7);
            mainHolder.cp_item8 = (TextView) convertView.findViewById(R.id.cp_item8);
            //mainHolder.cp_item9 = (TextView) convertView.findViewById(R.id.cp_item9);
            //mainHolder.cp_item10 = (TextView) convertView.findViewById(R.id.cp_item10);
            mainHolder.cp_item11 = (TextView) convertView.findViewById(R.id.cp_item11);
            mainHolder.cp_itemSaldo = (TextView) convertView.findViewById(R.id.cp_itemSaldo);
            mainHolder.cl_btnPDF = (TextView) convertView.findViewById(R.id.cl_btnPDF);
            mainHolder.cl_btnXML = (TextView) convertView.findViewById(R.id.cl_btnXML);
            convertView.setTag(mainHolder);
        } else {
            mainHolder = (MainHolder) convertView.getTag();
        }
        final FactCobBE factCobBE = getItem(position);
        mainHolder.cp_item1.setText(factCobBE.getCOD_VEND_ORIGEN().toString());
        mainHolder.cp_item2.setText(factCobBE.getVENDED().toString());
        mainHolder.cp_item3.setText(factCobBE.getCOND_PAG().toString());
        mainHolder.cp_item4.setText(factCobBE.getTIPDOC().toString() + " - "+ factCobBE.getSERIE_NUM().toString() + " - "+ factCobBE.getNUMERO().toString());
        //mainHolder.cp_item5.setText(factCobBE.getSERIE_NUM().toString());
        //mainHolder.cp_item6.setText(factCobBE.getNUMERO().toString());
        mainHolder.cp_item7.setText(factCobBE.getFECHA().toString());

        if(factCobBE.getDIAS()>0)
        {
            mainHolder.cp_item8.setText(factCobBE.getF_VENCTO().toString() + "  ("+factCobBE.getDIAS().toString()+")");
        }
        else
        {
            mainHolder.cp_item8.setText(factCobBE.getF_VENCTO().toString());
        }




        //mainHolder.cp_item9.setText(factCobBE.getDIAS().toString());
        sMoneda=factCobBE.getMONEDA().toString().trim();
        switch (sMoneda) {
            case "S":
                sMoneda="S/ ";
                break;
        }
        mainHolder.cp_item11.setText(sMoneda + " " + funciones.FormatDecimal(String.valueOf(factCobBE.getIMPORTE()).trim().replace(",","")));
        mainHolder.cp_itemSaldo.setText(sMoneda + " " + funciones.FormatDecimal(String.valueOf(factCobBE.getSALDO()).trim().replace(",","")));


        if(factCobBE.getURL_PDF().toString().trim().equals(""))
        {
            mainHolder.cl_btnPDF.setVisibility(View.GONE);
        }

        if(factCobBE.getURL_XML().toString().trim().equals(""))
        {
            mainHolder.cl_btnXML.setVisibility(View.GONE);
        }


        mainHolder.cp_itemSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notifyDataSetChanged();
                try {
                    sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                    editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();
                    editor_Shared.putString("COBRANZA_EVENTO","0");
                    editor_Shared.putString("CODIGO_ANTIGUO", factCobBE.getCOD_CLIENTE().toString());
                    editor_Shared.putString("PAE", String.valueOf(factCobBE.getSALDO()).trim().replace(",",""));
                    editor_Shared.putString("cpserie","");
                    editor_Shared.putString("cpnumero","");
                    editor_Shared.putString("cpfplanilla", "");
                    editor_Shared.commit();


                    Intent intent = new Intent(getContext().getApplicationContext(), Activity_Cobranzas.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);

                }catch (Exception ex) {
                }
            }
        });


        mainHolder.cl_btnPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                try {
                    String URL_PDF=factCobBE.getURL_PDF().toString().trim();

                    if (!URL_PDF.equals("")) {
                        Uri uri = Uri.parse(factCobBE.getURL_PDF().toString().trim());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(intent);
                    }
                }catch (Exception ex) {
                    Toast toastCodigo = Toast.makeText(getContext(),ex.getMessage(), Toast.LENGTH_SHORT);
                    toastCodigo.show();
                }
            }
        });

        mainHolder.cl_btnXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String URL_XML=factCobBE.getURL_XML().toString().trim();
                    if (!URL_XML.equals("")) {
                        Uri uri = Uri.parse(factCobBE.getURL_XML().toString().trim());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(intent);
                    }
                } catch (Exception ex) {
                    Toast toastCodigo = Toast.makeText(getContext(),ex.getMessage(), Toast.LENGTH_SHORT);
                    toastCodigo.show();
                }
            }
        });


        return convertView;
    }

    static class MainHolder {
        TextView cp_item1,cp_item2,cp_item3,cp_item4,cp_item7,cp_item8,cp_item11,cp_itemSaldo, cl_btnPDF,cl_btnXML;


    }

    @Override
    public int getCount() {
        return lstFiltrado.size();
    }

    @Override
    public FactCobBE getItem(int position) {
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
                List<FactCobBE> localList = new ArrayList<>();
                String[] SSA=constraint.toString().toUpperCase().split(" ");
                for (FactCobBE factCobBE : lst) {
                    String todo = (factCobBE.getCOD_CLIENTE().toString());
                    Boolean bfind = true;
                    for(String s : SSA){
                        if(!todo.contains(s)){
                            bfind = false;
                        }
                    }
                    if (bfind != false){
                        localList.add(factCobBE);
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
