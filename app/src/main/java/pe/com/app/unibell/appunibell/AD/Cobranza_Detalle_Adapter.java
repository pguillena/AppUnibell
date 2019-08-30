package pe.com.app.unibell.appunibell.AD;

import android.content.Context;
import android.content.SharedPreferences;
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

import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_CabBE;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_DetBE;
import pe.com.app.unibell.appunibell.BE.FactCobBE;
import pe.com.app.unibell.appunibell.Cobranza.Activity_Cobranzas;
import pe.com.app.unibell.appunibell.DAO.Documentos_Cobra_DetDAO;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

import static android.content.Context.MODE_PRIVATE;

public class Cobranza_Detalle_Adapter extends ArrayAdapter<FactCobBE> implements Filterable{
    public List<FactCobBE> lstFiltrado;
    public List<FactCobBE> lst;
    private AdapterFilter adapterFilter;

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    private Documentos_Cobra_DetDAO documentos_cobra_detDAO = new Documentos_Cobra_DetDAO();

    public Cobranza_Detalle_Adapter(Context context, int resource, List<FactCobBE> objects) {
        super(context, resource, objects);
        adapterFilter = new AdapterFilter();
        lstFiltrado = new ArrayList<>();
        lstFiltrado.addAll(objects);
        lst = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final   MainHolder mainHolder;
        if (convertView == null || !(convertView.getTag() instanceof MainHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cobranza_detalle, parent, false);
            mainHolder = new MainHolder();
            mainHolder.cp_item1 = (TextView) convertView.findViewById(R.id.cp_item1);
            mainHolder.cp_item2 = (TextView) convertView.findViewById(R.id.cp_item2);
            mainHolder.cp_item3 = (TextView) convertView.findViewById(R.id.cp_item3);
            mainHolder.cp_item4 = (TextView) convertView.findViewById(R.id.cp_item4);
            mainHolder.cp_item5 = (TextView) convertView.findViewById(R.id.cp_item5);
            mainHolder.cp_item6 = (TextView) convertView.findViewById(R.id.cp_item6);
            mainHolder.cp_item7 = (TextView) convertView.findViewById(R.id.cp_item7);
            mainHolder.cp_item8 = (TextView) convertView.findViewById(R.id.cp_item8);
            mainHolder.cp_txt1 = (EditText) convertView.findViewById(R.id.cp_txt1);
            mainHolder.cl_btn1 = (Button) convertView.findViewById(R.id.cl_btn1);
            mainHolder.cl_btnanular = (Button) convertView.findViewById(R.id.cl_btnanular);

            convertView.setTag(mainHolder);
        } else {
            mainHolder = (MainHolder) convertView.getTag();
        }
        final FactCobBE factCobBE = getItem(position);

        if (factCobBE.getTIPDOC().toString().trim().equals("A1")) {
            mainHolder.cp_item1.setText("ANTICIPO");
        }else{
            mainHolder.cp_item1.setText(factCobBE.getTIPDOC().toString());
        }

       /* if(factCobBE.getTIPDOC().toString().trim().equals("A1")) {
            convertView.setBackgroundResource(R.color.list_seleccion);
        }else{
            convertView.setBackgroundResource(R.color.background);
        }*/
        mainHolder.cp_item2.setText(factCobBE.getSERIE_NUM().toString() + "-"+ factCobBE.getNUMERO().toString());
        mainHolder.cp_item3.setText(Funciones.FormatDecimal(factCobBE.getIMPORTE().toString()));
        mainHolder.cp_item4.setText(Funciones.FormatDecimal(factCobBE.getSALDO().toString()));
        mainHolder.cp_item5.setText( factCobBE.getCOBRANZA().toString());
        mainHolder.cp_item6.setText(factCobBE.getFECHA().toString() + "-" + factCobBE.getF_VENCTO().toString());
        mainHolder.cp_item7.setText("(" + factCobBE.getDIAS().toString() + ")");

        if(!factCobBE.getVAMORTIZADO().toString().equals("")){
            if(Double.valueOf(factCobBE.getVAMORTIZADO().toString())>0) {
                mainHolder.cp_txt1.setText(Funciones.FormatDecimal(factCobBE.getVAMORTIZADO().toString()));
            }else{
                mainHolder.cp_txt1.setText(Funciones.FormatDecimal(factCobBE.getSALDO().toString()));
            }
        }

        if(Double.valueOf(factCobBE.getCOBRANZA().toString()) >0) {
            mainHolder.cl_btn1.setVisibility(View.GONE);
            mainHolder.cl_btnanular.setVisibility(View.VISIBLE);
            mainHolder.cp_txt1.setEnabled(false);
            mainHolder.cp_txt1.setBackgroundResource(R.color.background_cobranzacab_unibell);
        }else{
            mainHolder.cl_btn1.setVisibility(View.VISIBLE);
            mainHolder.cl_btnanular.setVisibility(View.GONE);
            mainHolder.cp_txt1.setEnabled(true);
            //mainHolder.cp_txt1.setBackgroundResource(R.color.background);
        }

        mainHolder.cl_btnanular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(getContext() instanceof  Activity_Cobranzas){
                        ((Activity_Cobranzas)getContext()).EliminarDetalle(factCobBE);
                    }
                    notifyDataSetChanged();
                }catch (Exception ex) {
                    Toast toastCodigo = Toast.makeText(getContext(),ex.getMessage(), Toast.LENGTH_SHORT);
                    toastCodigo.show();
                }
            }
        });

        mainHolder.cl_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                    editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                if(sharedSettings.getString("iAmortizar_Dialog", "0").trim().equals("1")) {
                    editor_Shared.putString("aDOCUMENTO",factCobBE.getSERIE_NUM().toString() + "-"+ factCobBE.getNUMERO().toString());
                    editor_Shared.putString("aSALDO",Funciones.FormatDecimal(factCobBE.getSALDO().toString()));
                    editor_Shared.commit();

                    if(getContext() instanceof  Activity_Cobranzas){
                        ((Activity_Cobranzas)getContext()).AmortizarDialogTemp(factCobBE);
                    }
                }else{

                    if (mainHolder.cp_txt1.getText().toString().equals("")){
                        Toast toastCodigo = Toast.makeText(getContext(),"Valor Ingresado no es válido", Toast.LENGTH_SHORT);
                        toastCodigo.show();
                        return;
                    }

                    if (Double.valueOf(mainHolder.cp_txt1.getText().toString())<=0){
                        Toast toastCodigo = Toast.makeText(getContext(),"Valor Ingresado no es válido", Toast.LENGTH_SHORT);
                        toastCodigo.show();
                        return;
                    }

                    factCobBE.setVAMORTIZADO(Double.valueOf(mainHolder.cp_txt1.getText().toString()));
                    notifyDataSetChanged();

                    if(getContext() instanceof  Activity_Cobranzas){
                        ((Activity_Cobranzas)getContext()).GuardarCobranzaDet(factCobBE);
                    }
                }


                }catch (Exception ex) {
                    Toast toastCodigo = Toast.makeText(getContext(),ex.getMessage(), Toast.LENGTH_SHORT);
                    toastCodigo.show();
                }
            }
        });

        mainHolder.cp_txt1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final int position = v.getId();
                    if(!mainHolder.cp_txt1.getText().toString().trim().equals("")){
                        factCobBE.setVAMORTIZADO(Double.valueOf(mainHolder.cp_txt1.getText().toString()));
                        notifyDataSetChanged();
                        mainHolder.cp_txt1.requestFocus();
                    }

                }
            }
        });
        return convertView;
    }

    static class MainHolder {
        TextView cp_item1,cp_item2,cp_item3,cp_item4,cp_item5,cp_item6,cp_item7,cp_item8;
        EditText cp_txt1;
        Button cl_btn1,cl_btnanular;
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
