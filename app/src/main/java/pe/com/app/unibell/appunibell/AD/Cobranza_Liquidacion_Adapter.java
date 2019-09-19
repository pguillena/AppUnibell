package pe.com.app.unibell.appunibell.AD;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_CabBE;
import pe.com.app.unibell.appunibell.Cobranza.Activity_Cobranzas;
import pe.com.app.unibell.appunibell.Flujo_Seguimiento.Activity_Flujo_Seguimiento;
import pe.com.app.unibell.appunibell.Liquidacion.Activity_Liquidacion;
import pe.com.app.unibell.appunibell.Main.Activity_Login;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Reportes.Activity_Cobranza_Liquidacion_Rep;
import pe.com.app.unibell.appunibell.Reportes.Activity_Cobranza_Recibo_Rep;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Cobranza_Liquidacion_Adapter extends ArrayAdapter<Documentos_Cobra_CabBE> implements Filterable {
    public List<Documentos_Cobra_CabBE> lstFiltrado;
    public List<Documentos_Cobra_CabBE> lst;
    private AdapterFilter adapterFilter;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    Funciones funciones = new Funciones();

    public Cobranza_Liquidacion_Adapter(Context context, int resource, List<Documentos_Cobra_CabBE> objects) {
        super(context, resource, objects);
        adapterFilter = new AdapterFilter();
        lstFiltrado = new ArrayList<>();
        lstFiltrado.addAll(objects);
        lst = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null || !(convertView.getTag() instanceof MainHolder)) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_cobranza_liquidacion, parent, false);
            final MainHolder mainHolder = new MainHolder();
            mainHolder.cl_btn = (Button) view.findViewById(R.id.cl_btn);
            mainHolder.cl_itemCliente = (TextView) view.findViewById(R.id.cl_itemCliente);
            mainHolder.cl_itemDocumentos = (TextView) view.findViewById(R.id.cl_itemDocumentos);
            mainHolder.cl_itemMonto = (TextView) view.findViewById(R.id.cl_itemMonto);
            mainHolder.cl_itemFormaPago = (TextView) view.findViewById(R.id.cl_itemFormaPago);
            mainHolder.cl_itemRecibo = (TextView) view.findViewById(R.id.cl_itemRecibo);
            mainHolder.cl_lblPlanilla = (TextView) view.findViewById(R.id.cl_lblPlanilla);
            mainHolder.cl_itemPlanilla = (TextView) view.findViewById(R.id.cl_itemPlanilla);
            mainHolder.cl_lblRecibo = (TextView) view.findViewById(R.id.cl_lblRecibo);
            mainHolder.lblcPacking = (TextView) view.findViewById(R.id.lblcPacking);
            mainHolder.txtcPacking = (TextView) view.findViewById(R.id.txtcPacking);
            mainHolder.lyRecibo = (LinearLayout) view.findViewById(R.id.lyRecibo);
            mainHolder.ivMarkerConciliado = (ImageView) view.findViewById(R.id.ivMarkerConciliado);
            mainHolder.lyItemLiquidacion = (LinearLayout) view.findViewById(R.id.lyItemLiquidacion);
            mainHolder.lyFondoBtnEditar = (LinearLayout) view.findViewById(R.id.lyFondoBtnEditar);

            mainHolder.txtFechaLiquidacion = (TextView) view.findViewById(R.id.txtFechaLiquidacion);
            mainHolder.cl_itemchk = (CheckBox) view.findViewById(R.id.cl_itemchk);
            mainHolder.cl_itemchk
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            Documentos_Cobra_CabBE element = (Documentos_Cobra_CabBE)mainHolder.cl_itemchk.getTag();
                            element.setCHKMARCADO(buttonView.isChecked());
                            notifyDataSetChanged();
                        }
                    });
            view.setTag(mainHolder);
            mainHolder.cl_itemchk.setTag(lstFiltrado.get(position));
        } else {
            view = convertView;
            ((MainHolder) view.getTag()).cl_itemchk.setTag(lstFiltrado.get(position));
        }

        MainHolder holder = (MainHolder) view.getTag();
        final Documentos_Cobra_CabBE documentos_cobra_cabBE = getItem(position);

        holder.cl_itemRecibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                editor_Shared.putString("iN_SERIE_RECIBO", documentos_cobra_cabBE.getN_SERIE_RECIBO().toString());
                editor_Shared.putString("iN_RECIBO", documentos_cobra_cabBE.getN_RECIBO().toString());
                editor_Shared.putString("IOPCION_REPORTE","0");
                editor_Shared.commit();

                Intent intent = new Intent(getContext().getApplicationContext(), Activity_Cobranza_Recibo_Rep.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);

         }});


//Imprimir en PDF la planillla
        holder.cl_itemPlanilla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] nroPlanilla = documentos_cobra_cabBE.getPLANILLA().split("-");

                sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                editor_Shared.putString("REP_N_PLANILLA", nroPlanilla[1].toString());
                editor_Shared.putString("IOPCION_REPORTE", "0");
                editor_Shared.commit();

                Intent intent = new Intent(getContext().getApplicationContext(), Activity_Cobranza_Liquidacion_Rep.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);

            }
        });


//Ver el seguimiento de la planilla
        holder.cl_lblPlanilla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] nroPlanilla = documentos_cobra_cabBE.getPLANILLA().split("-");

                sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();
                editor_Shared.putString("SERIE_PLANILLA", nroPlanilla[0].toString());
                editor_Shared.putString("N_PLANILLA", nroPlanilla[1].toString());
                editor_Shared.commit();

                try {
                    Intent intent = new Intent(getContext().getApplicationContext(), Activity_Flujo_Seguimiento.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        holder.txtcPacking.setVisibility(View.GONE);
        holder.lblcPacking.setVisibility(View.GONE);

        if(documentos_cobra_cabBE.getC_PACKING() != null && !documentos_cobra_cabBE.getC_PACKING().equals("") && !documentos_cobra_cabBE.getC_PACKING().equals("0"))
        {
            holder.txtcPacking.setText(documentos_cobra_cabBE.getC_PACKING());

            holder.txtcPacking.setVisibility(View.VISIBLE);
            holder.lblcPacking.setVisibility(View.VISIBLE);
        }



        holder.cl_itemCliente.setText(documentos_cobra_cabBE.getCOD_CLIENTE().toString() +" "+funciones.LetraCapital(documentos_cobra_cabBE.getRAZON_SOCIAL().toString()));
        holder.txtFechaLiquidacion.setText(documentos_cobra_cabBE.getFECHA_RECIBO());


        if (documentos_cobra_cabBE.getFPAGO().toString().equals("E"))
        {
            holder.cl_itemFormaPago.setText( funciones.LetraCapital(documentos_cobra_cabBE.getFPAGODESC().toString()));
        }
        else {
            holder.cl_itemFormaPago.setText(funciones.LetraCapital(documentos_cobra_cabBE.getFPAGODESC().toString() )+ " " + funciones.LetraCapital(documentos_cobra_cabBE.getENTIDAD().toString()) + " " + documentos_cobra_cabBE.getCONSTANCIA().toString());
        }

        holder.cl_itemDocumentos.setText(funciones.LetraCapital(documentos_cobra_cabBE.getTIPODOC().toString()) +" "+documentos_cobra_cabBE.getNUMERO().toString());


        if(documentos_cobra_cabBE.getMONEDA().toString().trim().equals("S")) {
            holder.cl_itemMonto.setText("S/ " + Funciones.FormatDecimal(documentos_cobra_cabBE.getM_COBRANZA().toString()));
        }else{
            holder.cl_itemMonto.setText("U$$ " + Funciones.FormatDecimal(documentos_cobra_cabBE.getM_COBRANZA().toString()));
       }

        if(lstFiltrado.get(position).getRECIBO()!=null && !lstFiltrado.get(position).getRECIBO().toString().equals("")) {

            holder.cl_itemRecibo.setText(lstFiltrado.get(position).getRECIBO().toString());
        }else
        {
            holder.cl_itemRecibo.setVisibility(View.GONE);
            holder.cl_itemRecibo.setVisibility(View.GONE);
        }

        if(lstFiltrado.get(position).getPLANILLA()!=null && !lstFiltrado.get(position).getPLANILLA().toString().equals("")&& !lstFiltrado.get(position).getPLANILLA().toString().trim().equals("-"))
        {
            holder.cl_itemPlanilla.setText(lstFiltrado.get(position).getPLANILLA());
            holder.cl_lblPlanilla.setVisibility(View.VISIBLE);
            holder.cl_itemPlanilla.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.cl_lblPlanilla.setVisibility(View.GONE);
            holder.cl_itemPlanilla.setVisibility(View.GONE);
        }

        if ((lstFiltrado.get(position).getPLANILLA() == null || lstFiltrado.get(position).getPLANILLA().toString().equals("")) &&
             lstFiltrado.get(position).getRECIBO()   == null || lstFiltrado.get(position).getRECIBO().toString().equals("") )
        {
            holder.lyRecibo.setVisibility(View.GONE);
        }


        holder.cl_itemchk.setChecked(lstFiltrado.get(position).getCHKMARCADO());


        if(documentos_cobra_cabBE.getESTADO_CONCILIADO().toString().trim().equals("40025")) {
            holder.ivMarkerConciliado.setVisibility(View.VISIBLE);

        }else{
            holder.ivMarkerConciliado.setVisibility(View.GONE);
            /*
            if(lstFiltrado.get(position).getCHKMARCADO()==true) {
                lyFondo.setBackgroundResource(R.color.ubell_color11);
            }else{
                lyFondo.setBackgroundResource(R.color.ubell_color1);
            }
            */
        }

        if(lstFiltrado.get(position).getCHKMARCADO()==true) {
            holder.lyItemLiquidacion.setBackgroundResource(R.drawable.borderradius_activo);
        }else{
            holder.lyItemLiquidacion.setBackgroundResource(R.drawable.borderradius_sin_borde);
        }



        if(!documentos_cobra_cabBE.getESTADO().toString().trim().equals("40003")) {
            holder.cl_btn.setVisibility(View.GONE);
            holder.lyFondoBtnEditar.setVisibility(View.GONE);
        }



        holder.cl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                editor_Shared.putString("ID_COBRANZA", documentos_cobra_cabBE.getID_COBRANZA().toString());
                editor_Shared.putString("CODUNC_LOCAL", documentos_cobra_cabBE.getCODUNC_LOCAL().toString());
                editor_Shared.putString("MAX_CODUNICO", documentos_cobra_cabBE.getCODUNC_LOCAL().toString());

                editor_Shared.putString("sN_SERIE_RECIBO", documentos_cobra_cabBE.getN_SERIE_RECIBO().toString());
                editor_Shared.putString("sN_RECIBO", documentos_cobra_cabBE.getN_RECIBO().toString());
                editor_Shared.putString("dTIPO_CAMBIO", documentos_cobra_cabBE.getT_CAMBIO_TIENDA().toString());
                editor_Shared.putString("sESTADO", documentos_cobra_cabBE.getESTADO().toString());
                editor_Shared.putString("sESTADO_CONCILIADO", documentos_cobra_cabBE.getESTADO_CONCILIADO().toString());
                editor_Shared.putString("RAZON_SOCIAL", documentos_cobra_cabBE.getRAZON_SOCIAL().toString());
                editor_Shared.putString("COBRANZA_EVENTO","1");
                editor_Shared.commit();

                Intent intent = new Intent(getContext().getApplicationContext(), Activity_Cobranzas.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);

            }
        });


        return view;
    }

    static class MainHolder {
        //TextView lc_item1,lc_item2,lc_item3,lc_item4,lc_item5,lc_item6,lc_item7,lc_item8,lc_item9,lc_item10,lc_item11;
        TextView cl_itemCliente, cl_itemDocumentos, cl_itemMonto, cl_itemFormaPago, cl_itemRecibo, cl_itemPlanilla, txtFechaLiquidacion, txtcPacking;
        TextView cl_lblPlanilla, cl_lblRecibo, lblcPacking;
        LinearLayout lyRecibo;
        Button cl_btn;
        CheckBox cl_itemchk;
        View ivMarkerConciliado;
        LinearLayout lyItemLiquidacion, lyFondoBtnEditar;
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
                    String todo = (documentos_cobra_cabBE.getENTIDAD().toString() +
                                   documentos_cobra_cabBE.getRAZON_SOCIAL().toString()+
                                   documentos_cobra_cabBE.getCOD_CLIENTE().toString()+
                                   documentos_cobra_cabBE.getN_RECIBO().toString()+
                                   documentos_cobra_cabBE.getPLANILLA().toString()+
                                   documentos_cobra_cabBE.getFPAGODESC().toString());
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