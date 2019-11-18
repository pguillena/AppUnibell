package pe.com.app.unibell.appunibell.AD;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_CabBE;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;


public class Totales_Liquidacion_Adapter  extends ArrayAdapter<Documentos_Cobra_CabBE> implements Filterable {

    public List<Documentos_Cobra_CabBE> lstFiltrado;
    public List<Documentos_Cobra_CabBE> lst;
    private AdapterFilter adapterFilter;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private Funciones funciones=new Funciones();

    public Totales_Liquidacion_Adapter(Context context, int resource, List<Documentos_Cobra_CabBE> objects) {
        super(context, resource, objects);
        adapterFilter = new Totales_Liquidacion_Adapter.AdapterFilter();
        lstFiltrado = new ArrayList<>();
        lstFiltrado.addAll(objects);
        lst = objects;
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
                    String todo = (documentos_cobra_cabBE.getNRO_OPERACION().toString());
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

    static class MainHolder {
        TextView txtNroOperacionItem, txtMontoDepositoItem, txtFechaOperacionItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Totales_Liquidacion_Adapter.MainHolder mainHolder = null;
        if (convertView == null || !(convertView.getTag() instanceof Totales_Liquidacion_Adapter.MainHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_totales_liquidacion, parent, false);
            mainHolder = new Totales_Liquidacion_Adapter.MainHolder();
            mainHolder.txtNroOperacionItem = (TextView) convertView.findViewById(R.id.txtNroOperacionItem);
            mainHolder.txtMontoDepositoItem = (TextView) convertView.findViewById(R.id.txtMontoDepositoItem);
            mainHolder.txtFechaOperacionItem = (TextView) convertView.findViewById(R.id.txtFechaOperacionItem);
            convertView.setTag(mainHolder);
        } else {
            mainHolder = (Totales_Liquidacion_Adapter.MainHolder) convertView.getTag();
        }
        final Documentos_Cobra_CabBE documentos_cobra_cabBE = getItem(position);

        mainHolder.txtNroOperacionItem.setText(documentos_cobra_cabBE.getNRO_OPERACION().toString());


        if (documentos_cobra_cabBE.getESTADO_CONCILIADO().equals("40025"))
        {
            mainHolder.txtNroOperacionItem.setCompoundDrawablesWithIntrinsicBounds(null,null,ContextCompat.getDrawable(getContext(), R.drawable.alpha_c_circle_outline_activo),null);
        }
        else
        {
            mainHolder.txtNroOperacionItem.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        }


        mainHolder.txtFechaOperacionItem.setText(documentos_cobra_cabBE.getFECHA_DEPOSITO().toString());

        if(Double.valueOf(documentos_cobra_cabBE.getM_COBRANZA().toString())>0.0) {
            mainHolder.txtMontoDepositoItem.setText("S/ " +funciones.FormatDecimal(documentos_cobra_cabBE.getM_COBRANZA().toString().trim().replace(",","")));
        }

        if(Double.valueOf(documentos_cobra_cabBE.getM_COBRANZA_D().toString())>0.0) {
            mainHolder.txtMontoDepositoItem.setText("U$D " +funciones.FormatDecimal(documentos_cobra_cabBE.getM_COBRANZA_D().toString().trim().replace(",","")));
        }

        return convertView;
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
            adapterFilter = new Totales_Liquidacion_Adapter.AdapterFilter();
        }
        return adapterFilter;
    }


}


