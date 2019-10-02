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
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_MovBE;
import pe.com.app.unibell.appunibell.Cobranza.Activity_Cobranza_Recibo;
import pe.com.app.unibell.appunibell.Cobranza.Activity_Cobranzas;
import pe.com.app.unibell.appunibell.Flujo_Seguimiento.Activity_Flujo_Seguimiento;
import pe.com.app.unibell.appunibell.Planilla.Activity_AprobacionPlanilla;
import pe.com.app.unibell.appunibell.Planilla.Activity_FlujoSeguimiento;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Reportes.Activity_Cobranza_Liquidacion_Rep;
import pe.com.app.unibell.appunibell.Util.Funciones;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Cobranza_Aprobacion_Planilla_Adapter extends ArrayAdapter<Documentos_Cobra_MovBE> implements Filterable {
    public List<Documentos_Cobra_MovBE> lstFiltrado;
    public List<Documentos_Cobra_MovBE> lst;
    private AdapterFilter adapterFilter;
    private Funciones funciones=new Funciones();
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;


    public Cobranza_Aprobacion_Planilla_Adapter(Context context, int resource, List<Documentos_Cobra_MovBE> objects) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cobranza_aprobacion_planilla, parent, false);
            mainHolder = new MainHolder();
            mainHolder.ap_item1 = (TextView) convertView.findViewById(R.id.ap_item1);
            mainHolder.ap_item2 = (TextView) convertView.findViewById(R.id.ap_item2);
            mainHolder.ap_item3 = (TextView) convertView.findViewById(R.id.ap_item3);
            mainHolder.ap_item4 = (TextView) convertView.findViewById(R.id.ap_item4);
            mainHolder.ap_item5 = (TextView) convertView.findViewById(R.id.ap_item5);
            mainHolder.ap_item6 = (TextView) convertView.findViewById(R.id.ap_item6);
            mainHolder.ap_item7 = (TextView) convertView.findViewById(R.id.ap_item7);
            mainHolder.ap_item8 = (TextView) convertView.findViewById(R.id.ap_item8);
            mainHolder.ap_item9 = (TextView) convertView.findViewById(R.id.ap_item9);
            mainHolder.ap_itemreporte = (TextView) convertView.findViewById(R.id.ap_itemreporte);
            mainHolder.lyAprobarRetornar = (LinearLayout)convertView.findViewById(R.id.lyAprobarRetornar);



            mainHolder.ap_btnaprobar = (Button) convertView.findViewById(R.id.ap_btnaprobar);
            mainHolder.ap_btnretornar = (Button) convertView.findViewById(R.id.ap_btnretornar);

            convertView.setTag(mainHolder);
        } else {
            mainHolder = (MainHolder) convertView.getTag();
        }
        final Documentos_Cobra_MovBE documentos_cobra_movBE = getItem(position);
        mainHolder.ap_btnaprobar.setTag(position);
        mainHolder.ap_btnretornar.setTag(position);

        if(documentos_cobra_movBE.getESTADO().equals("40005"))
        {
            mainHolder.lyAprobarRetornar.setVisibility(View.GONE);
        }
        else
            {
            mainHolder.lyAprobarRetornar.setVisibility(View.VISIBLE);
            }


        mainHolder.ap_item1.setText(documentos_cobra_movBE.getCOD_CLIENTE().toString());
        mainHolder.ap_item2.setText(funciones.LetraCapital(documentos_cobra_movBE.getNOMBRECLIENTE().toString()));
        mainHolder.ap_item3.setText(funciones.LetraCapital(documentos_cobra_movBE.getNOMBREFORMAPAGO().toString()));
        mainHolder.ap_item4.setText(funciones.LetraCapital(documentos_cobra_movBE.getNOMBRECOBRADOR().toString()));
        mainHolder.ap_item5.setText(documentos_cobra_movBE.getFECHA().toString());
        if(Double.valueOf(documentos_cobra_movBE.getM_COBRANZA().toString())>0.0){
            mainHolder.ap_item6.setText("S/." + " " + funciones.FormatDecimal(String.valueOf(documentos_cobra_movBE.getM_COBRANZA()).trim().replace(",","")));
        }
        if(Double.valueOf(documentos_cobra_movBE.getM_COBRANZA_D().toString())>0.0){
            mainHolder.ap_item6.setText("U$$" + " " + funciones.FormatDecimal(String.valueOf(documentos_cobra_movBE.getM_COBRANZA_D()).trim().replace(",","")));
        }
        mainHolder.ap_item7.setText(documentos_cobra_movBE.getN_SERIE_RECIBO().toString() + "-"+ documentos_cobra_movBE.getN_RECIBO().toString());
        mainHolder.ap_item8.setText(documentos_cobra_movBE.getDOCUMENTOS());
        mainHolder.ap_item9.setText(documentos_cobra_movBE.getN_PLANILLA().toString());

        //FLUJO sEGUIMIENTO
        mainHolder.ap_item9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();
                editor_Shared.putString("SERIE_PLANILLA", documentos_cobra_movBE.getSERIE_PLANILLA().toString());
                editor_Shared.putString("N_PLANILLA", documentos_cobra_movBE.getN_PLANILLA().toString());
                editor_Shared.putString("ID_DOCUMENTO_MOVIMIENTO", documentos_cobra_movBE.getID_DOCUMENTO_MOVIMIENTO().toString());
                editor_Shared.putString("FECHA_MODIFICACION", documentos_cobra_movBE.getFECHA_MODIFICACION().toString());
                editor_Shared.putString("ID_COBRANZA", documentos_cobra_movBE.getID_COBRANZA().toString());
                editor_Shared.commit();

                try {
                    Intent intent = new Intent(getContext().getApplicationContext(), Activity_Flujo_Seguimiento.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }


        /*
                Intent intent = new Intent(getContext().getApplicationContext(), Activity_FlujoSeguimiento.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
*/
            }
        });
        //REPORTE DE PLANILLA
        mainHolder.ap_itemreporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                editor_Shared.putString("REP_SER_PLANILLA", documentos_cobra_movBE.getSERIE_PLANILLA().toString());
                editor_Shared.putString("REP_NUM_PLANILLA", documentos_cobra_movBE.getN_PLANILLA().toString());

                editor_Shared.putString("IOPCION_REPORTE", "0");

                editor_Shared.commit();

                Intent intent = new Intent(getContext().getApplicationContext(), Activity_Cobranza_Liquidacion_Rep.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);

            }
        });


        //APROBACION DE PLANILLA
        mainHolder.ap_btnaprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();
                editor_Shared.putString("SERIE_PLANILLA", documentos_cobra_movBE.getSERIE_PLANILLA().toString());
                editor_Shared.putString("N_PLANILLA", documentos_cobra_movBE.getN_PLANILLA().toString());
                editor_Shared.putString("ID_DOCUMENTO_MOVIMIENTO", documentos_cobra_movBE.getID_DOCUMENTO_MOVIMIENTO().toString());
                editor_Shared.putString("FECHA_MODIFICACION", documentos_cobra_movBE.getFECHA_MODIFICACION().toString());
                editor_Shared.putString("ID_COBRANZA", documentos_cobra_movBE.getID_COBRANZA().toString());
                editor_Shared.commit();

                if(getContext() instanceof Activity_AprobacionPlanilla){
                    ((Activity_AprobacionPlanilla)getContext()).AprobarPlanilla();
                }
            }
        });
        //RETORNO DE PLANILLA
        mainHolder.ap_btnretornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                editor_Shared.putString("SERIE_PLANILLA", documentos_cobra_movBE.getSERIE_PLANILLA().toString());
                editor_Shared.putString("N_PLANILLA", documentos_cobra_movBE.getN_PLANILLA().toString());
                editor_Shared.putString("ID_DOCUMENTO_MOVIMIENTO", documentos_cobra_movBE.getID_DOCUMENTO_MOVIMIENTO().toString());
                editor_Shared.putString("FECHA_MODIFICACION", documentos_cobra_movBE.getFECHA_MODIFICACION().toString());
                editor_Shared.putString("ID_COBRANZA", documentos_cobra_movBE.getID_COBRANZA().toString());
                editor_Shared.commit();

                if(getContext() instanceof Activity_AprobacionPlanilla){
                    ((Activity_AprobacionPlanilla)getContext()).RetornarPlanilla();
                }
            }
        });

        return convertView;
    }

    static class MainHolder {
        TextView ap_item1,ap_item2,ap_item3,ap_item4,ap_item5,ap_item6,ap_item7,ap_item8,ap_item9,ap_itemreporte;
        Button ap_btnaprobar,ap_btnretornar;
        LinearLayout lyAprobarRetornar;
    }

    @Override
    public int getCount() {
        return lstFiltrado.size();
    }

    @Override
    public Documentos_Cobra_MovBE getItem(int position) {
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
                List<Documentos_Cobra_MovBE> localList = new ArrayList<>();
                String[] SSA=constraint.toString().toUpperCase().split(" ");
                for (Documentos_Cobra_MovBE documentos_cobra_movBE : lst) {
                    String todo = (documentos_cobra_movBE.getSERIE_PLANILLA().toString()+
                                    documentos_cobra_movBE.getN_PLANILLA().toString()+
                                    documentos_cobra_movBE.getNOMBREFORMAPAGO().toString()+
                                    documentos_cobra_movBE.getSERIE_PLANILLA().toString()+
                                    documentos_cobra_movBE.getCOD_CLIENTE().toString()+
                                    documentos_cobra_movBE.getN_RECIBO().toString());
                    Boolean bfind = true;
                    for(String s : SSA){
                        if(!todo.contains(s)){
                            bfind = false;
                        }
                    }
                    if (bfind != false){
                        localList.add(documentos_cobra_movBE);
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