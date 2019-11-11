package pe.com.app.unibell.appunibell.AD;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_CabBE;
import pe.com.app.unibell.appunibell.Cobranza.Activity_Cobranza_Recibo;
import pe.com.app.unibell.appunibell.Cobranza.Activity_Cobranzas;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Reportes.Activity_Cobranza_Liquidacion_Rep;
import pe.com.app.unibell.appunibell.Util.Funciones;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Cobranza_Recibo_Adapter  extends ArrayAdapter<Documentos_Cobra_CabBE> implements Filterable {
    public List<Documentos_Cobra_CabBE> lstFiltrado;
    public List<Documentos_Cobra_CabBE> lst;
    private AdapterFilter adapterFilter;
    private Funciones funciones=new Funciones();
    private String sMoneda;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    public Cobranza_Recibo_Adapter(Context context, int resource, List<Documentos_Cobra_CabBE> objects) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cobranza_recibo, parent, false);
            mainHolder = new MainHolder();
            mainHolder.rc_item1 = (TextView) convertView.findViewById(R.id.rc_item1);
            mainHolder.rc_item2 = (TextView) convertView.findViewById(R.id.rc_item2);
            mainHolder.rc_item3 = (TextView) convertView.findViewById(R.id.rc_item3);
            mainHolder.rc_item4 = (TextView) convertView.findViewById(R.id.rc_item4);
            mainHolder.rc_item5 = (TextView) convertView.findViewById(R.id.rc_item5);
            mainHolder.rc_itemPlanilla = (TextView) convertView.findViewById(R.id.rc_itemPlanilla);

            mainHolder.cl_btneditar = (Button) convertView.findViewById(R.id.cl_btneditar);
            convertView.setTag(mainHolder);
        } else {
            mainHolder = (MainHolder) convertView.getTag();
        }
        final Documentos_Cobra_CabBE documentos_cobra_cabBE = getItem(position);
        mainHolder.rc_item1.setText(documentos_cobra_cabBE.getN_SERIE_RECIBO().toString() + "-" + documentos_cobra_cabBE.getN_RECIBO().toString());
        mainHolder.rc_item2.setText(documentos_cobra_cabBE.getFECHA().toString());
        mainHolder.rc_item3.setText(funciones.LetraCapital(documentos_cobra_cabBE.getFPAGODESC().toString()));
        if(Double.valueOf(documentos_cobra_cabBE.getM_COBRANZA().toString())>0.0) {
            mainHolder.rc_item4.setText(documentos_cobra_cabBE.getM_COBRANZA().toString());
        }else{
            mainHolder.rc_item4.setText(documentos_cobra_cabBE.getM_COBRANZA_D().toString());
        }
        mainHolder.rc_item5.setText(funciones.LetraCapital( documentos_cobra_cabBE.getESTADODESC().toString() ));

        mainHolder.rc_itemPlanilla.setText(documentos_cobra_cabBE.getSERIE_PLANILLA().toString() + "-" +  documentos_cobra_cabBE.getN_PLANILLA().toString());


        if(!documentos_cobra_cabBE.getESTADO().toString().trim().equals("40003")) {
            mainHolder.cl_btneditar.setVisibility(View.GONE);
        }else{
            mainHolder.cl_btneditar.setVisibility(View.VISIBLE);
        }

        mainHolder.rc_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                try {
                    sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                    editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                    editor_Shared.putString("REP_SER_RECIBO", documentos_cobra_cabBE.getN_SERIE_RECIBO().toString());
                    editor_Shared.putString("REP_NUM_RECIBO", documentos_cobra_cabBE.getN_RECIBO().toString());
                    editor_Shared.putString("CODIGO_ANTIGUO", documentos_cobra_cabBE.getCOD_CLIENTE().toString());
                    //PARA ABRIR EL DOCUMENTO
                    editor_Shared.putString("IOPCION_REPORTE","1");

                    editor_Shared.commit();

                    if(getContext() instanceof Activity_Cobranza_Recibo){
                        ((Activity_Cobranza_Recibo)getContext()).CargarRecibos();
                    }
                }catch (Exception ex) {
                    Toast toastCodigo = Toast.makeText(getContext(),ex.getMessage(), Toast.LENGTH_SHORT);
                    toastCodigo.show();
                }
            }
        });

        mainHolder.rc_itemPlanilla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                try {
                    sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                    editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                    editor_Shared.putString("REP_SER_PLANILLA", documentos_cobra_cabBE.getSERIE_PLANILLA().toString());
                    editor_Shared.putString("REP_NUM_PLANILLA", documentos_cobra_cabBE.getN_PLANILLA().toString());
                    //PARA ABRIR EL DOCUMENTO
                    editor_Shared.putString("IOPCION_REPORTE", "0");
                    editor_Shared.commit();

                    Intent intent = new Intent(getContext().getApplicationContext(), Activity_Cobranza_Liquidacion_Rep.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);

                }catch (Exception ex) {
                    Toast toastCodigo = Toast.makeText(getContext(),ex.getMessage(), Toast.LENGTH_SHORT);
                    toastCodigo.show();
                }
            }
        });


        mainHolder.cl_btneditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                try {
                    sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                    editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                    editor_Shared.putString("ID_COBRANZA", documentos_cobra_cabBE.getID_COBRANZA().toString());
                    editor_Shared.putString("sN_SERIE_RECIBO", documentos_cobra_cabBE.getN_SERIE_RECIBO().toString());
                    editor_Shared.putString("sN_RECIBO", documentos_cobra_cabBE.getN_RECIBO().toString());
                    editor_Shared.putString("CODUNC_LOCAL", documentos_cobra_cabBE.getCODUNC_LOCAL().toString());
                    editor_Shared.putString("MAX_CODUNICO", documentos_cobra_cabBE.getCODUNC_LOCAL().toString());
                    editor_Shared.putString("dTIPO_CAMBIO", documentos_cobra_cabBE.getT_CAMBIO_TIENDA().toString());
                    editor_Shared.putString("sESTADO", documentos_cobra_cabBE.getESTADO().toString());
                    editor_Shared.putString("sESTADO_CONCILIADO", documentos_cobra_cabBE.getESTADO_CONCILIADO().toString());
                    editor_Shared.putString("COBRANZA_EVENTO","1");
                    editor_Shared.commit();

                    Intent intent = new Intent(getContext().getApplicationContext(), Activity_Cobranzas.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);

                }catch (Exception ex) {
                    Toast toastCodigo = Toast.makeText(getContext(),ex.getMessage(), Toast.LENGTH_SHORT);
                    toastCodigo.show();
                }
            }
        });


        return convertView;
    }

    static class MainHolder {
        TextView rc_item1,rc_item2,rc_item3,rc_item4,rc_item5, rc_itemPlanilla;
        Button cl_btneditar;

    }

    @Override
    public int getCount() {
        return lstFiltrado.size();
    }

    @Override
    public Documentos_Cobra_CabBE getItem(int position) {
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
                List<Documentos_Cobra_CabBE> localList = new ArrayList<>();
                String[] SSA=constraint.toString().toUpperCase().split(" ");
                for (Documentos_Cobra_CabBE documentos_cobra_cabBE : lst) {
                    String todo = (documentos_cobra_cabBE.getCOD_CLIENTE().toString());
                    Boolean bfind = true;
                    for(String s : SSA){
                        if(!todo.contains(s)){
                            bfind = false;
                        }
                    }
                    if (bfind != false){
                        localList.add(documentos_cobra_cabBE);
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