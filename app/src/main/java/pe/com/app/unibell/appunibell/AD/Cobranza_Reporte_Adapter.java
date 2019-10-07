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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.com.app.unibell.appunibell.BE.CobranzaReporteBE;
import pe.com.app.unibell.appunibell.BE.FactCobBE;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Reportes.Activity_Cobranza_Liquidacion_Rep;
import pe.com.app.unibell.appunibell.Reportes.Activity_Cobranza_Recibo_Rep;
import pe.com.app.unibell.appunibell.Reportes.Activity_Reportes_Detalle;
import pe.com.app.unibell.appunibell.Util.Funciones;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Cobranza_Reporte_Adapter extends ArrayAdapter<CobranzaReporteBE> implements Filterable {
    public List<CobranzaReporteBE> lstFiltrado;
    public List<CobranzaReporteBE> lst;
    private AdapterFilter adapterFilter;
    private Funciones funciones=new Funciones();
    private String sMoneda;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    public Cobranza_Reporte_Adapter(Context context, int resource, List<CobranzaReporteBE> objects) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cobranza_reportes, parent, false);
            mainHolder = new MainHolder();
            mainHolder.cr_lblrazonsocial = (TextView) convertView.findViewById(R.id.cr_lblrazonsocial);
            mainHolder.cr_lblcodigo = (TextView) convertView.findViewById(R.id.cr_lblcodigo);
            mainHolder.cr_lblmonto = (TextView) convertView.findViewById(R.id.cr_lblmonto);
            mainHolder.cr_lblfechacobranza = (TextView) convertView.findViewById(R.id.cr_lblfechacobranza);
            mainHolder.cr_lblmoneda = (TextView) convertView.findViewById(R.id.cr_lblmoneda);
            mainHolder.cr_lblrecibo = (TextView) convertView.findViewById(R.id.cr_lblrecibo);
            mainHolder.cr_lblplanilla = (TextView) convertView.findViewById(R.id.cr_lblplanilla);
            mainHolder.cr_lblrepxrecibo  = (TextView) convertView.findViewById(R.id.cr_lblrepxrecibo);
            mainHolder.cr_lblrepxplanilla = (TextView) convertView.findViewById(R.id.cr_lblrepxplanilla);

            mainHolder.cr_btnverdetalle = (Button) convertView.findViewById(R.id.cr_btnverdetalle);

            convertView.setTag(mainHolder);
        } else {
            mainHolder = (MainHolder) convertView.getTag();
        }
        final CobranzaReporteBE cobranzaReporteBE = getItem(position);
        mainHolder.cr_lblrazonsocial.setText(cobranzaReporteBE.getRAZON_SOCIAL().toString());
        mainHolder.cr_lblcodigo.setText(cobranzaReporteBE.getCOD_CLIENTE().toString());
        mainHolder.cr_lblmonto.setText(cobranzaReporteBE.getM_COBRANZA().toString());
        mainHolder.cr_lblfechacobranza.setText(cobranzaReporteBE.getFECHA().toString());
        mainHolder.cr_lblmoneda.setText(cobranzaReporteBE.getMONEDA().toString());
        mainHolder.cr_lblrecibo.setText(cobranzaReporteBE.getRECIBO().toString());
        mainHolder.cr_lblplanilla.setText(cobranzaReporteBE.getPLANILLA().toString());

        mainHolder.cr_lblrecibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                    editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                    String[] parts = cobranzaReporteBE.getRECIBO().toString().split("-");
                    String SERIE ="0";
                    String NUMERO ="0";
                    if(parts.length>0){
                        SERIE = String.valueOf(Integer.parseInt(parts[0]));
                        NUMERO = parts[1];
                    }

                    editor_Shared.putString("REP_SER_RECIBO",SERIE.trim());
                    editor_Shared.putString("REP_NUM_RECIBO",NUMERO.trim());
                    editor_Shared.putString("IOPCION_REPORTE","0");
                    editor_Shared.commit();

                    Intent intent = new Intent(getContext().getApplicationContext(), Activity_Cobranza_Recibo_Rep.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);

                }catch (Exception ex) {
                    Toast toastCodigo = Toast.makeText(getContext(),ex.getMessage(), Toast.LENGTH_SHORT);
                    toastCodigo.show();
                }
            }
        });



        mainHolder.cr_lblplanilla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                    editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                    String[] parts = cobranzaReporteBE.getPLANILLA().toString().split("-");
                    String SERIE ="0";
                    String NUMERO ="0";
                    if(parts.length>0){
                        SERIE = String.valueOf(Integer.parseInt(parts[0]));
                        NUMERO = parts[1];
                    }

                    editor_Shared.putString("REP_SER_PLANILLA", SERIE);
                    editor_Shared.putString("REP_NUM_PLANILLA", NUMERO);
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


        mainHolder.cr_btnverdetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                try {

                    sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                    editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                    editor_Shared.putString("rep_razonsocial", cobranzaReporteBE.getRAZON_SOCIAL().toString());
                    editor_Shared.putString("rep_codigo", cobranzaReporteBE.getCOD_CLIENTE().toString());
                    editor_Shared.putString("rep_monto", cobranzaReporteBE.getM_COBRANZA().toString());
                    editor_Shared.putString("rep_documento", cobranzaReporteBE.getTIPODOC().toString());
                    editor_Shared.putString("rep_numero", cobranzaReporteBE.getNUMERO().toString());
                    editor_Shared.putString("rep_fpago", cobranzaReporteBE.getFPAGO().toString());
                    editor_Shared.putString("rep_entidad", cobranzaReporteBE.getENTIDAD().toString());
                    editor_Shared.putString("rep_numcheque", cobranzaReporteBE.getCONSTANCIA().toString());
                    editor_Shared.putString("rep_fcobranza", cobranzaReporteBE.getFECHA().toString());
                    editor_Shared.putString("rep_moneda", cobranzaReporteBE.getMONEDA().toString());
                    editor_Shared.putString("rep_recibo", cobranzaReporteBE.getRECIBO().toString());
                    editor_Shared.putString("rep_planilla", cobranzaReporteBE.getPLANILLA().toString());
                    editor_Shared.commit();

                    Intent intent = new Intent(getContext().getApplicationContext(), Activity_Reportes_Detalle.class);
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
        TextView cr_lblrazonsocial,cr_lblcodigo,cr_lblmonto,cr_lblfechacobranza,cr_lblmoneda,cr_lblrecibo,cr_lblplanilla,cr_lblrepxplanilla,cr_lblrepxrecibo;
        Button cr_btnverdetalle;

    }

    @Override
    public int getCount() {
        return lstFiltrado.size();
    }

    @Override
    public CobranzaReporteBE getItem(int position) {
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
                List<CobranzaReporteBE> localList = new ArrayList<>();
                String[] SSA=constraint.toString().toUpperCase().split(" ");

                for (CobranzaReporteBE cobranzaReporteBE : lst) {
                    String todo = (cobranzaReporteBE.getCOD_CLIENTE().toString() +
                                   cobranzaReporteBE.getRAZON_SOCIAL().toString()+
                                   cobranzaReporteBE.getENTIDAD().toString()+
                                   cobranzaReporteBE.getN_RECIBO().toString()+
                                   cobranzaReporteBE.getPLANILLA().toString());
                    Boolean bfind = true;
                    for(String s : SSA){
                        if(!todo.contains(s)){
                            bfind = false;
                        }
                    }
                    if (bfind != false){
                        localList.add(cobranzaReporteBE);
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
