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
import java.util.ArrayList;
import java.util.List;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_MovBE;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class Cobranza_Flujo2_Seguimiento_Adapter extends ArrayAdapter<Documentos_Cobra_MovBE> implements Filterable {
    public List<Documentos_Cobra_MovBE> lstFiltrado;
    public List<Documentos_Cobra_MovBE> lst;
    private AdapterFilter adapterFilter;
    private Funciones funciones=new Funciones();
    private String sMoneda;

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    public Cobranza_Flujo2_Seguimiento_Adapter(Context context, int resource, List<Documentos_Cobra_MovBE> objects) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cobranza_flujo2, parent, false);
            mainHolder = new MainHolder();
            mainHolder.fl_item1 = (TextView) convertView.findViewById(R.id.fl_item1);
            mainHolder.fl_item2 = (TextView) convertView.findViewById(R.id.fl_item2);
            mainHolder.fl_item3 = (TextView) convertView.findViewById(R.id.fl_item3);
            mainHolder.fl_item4 = (TextView) convertView.findViewById(R.id.fl_item4);
            mainHolder.fl_item5 = (TextView) convertView.findViewById(R.id.fl_item5);
            convertView.setTag(mainHolder);
        } else {
            mainHolder = (MainHolder) convertView.getTag();
        }
        final Documentos_Cobra_MovBE documentos_cobra_movBE = getItem(position);
        mainHolder.fl_item1.setText(documentos_cobra_movBE.getNOM_ROL_ORIGEN() + "-" + documentos_cobra_movBE.getNOM_ROL_DESTINO());
        mainHolder.fl_item2.setText(documentos_cobra_movBE.getFECHA_MOVIMIENTO());
        mainHolder.fl_item3.setText(documentos_cobra_movBE.getFECHA_RECEPCION());
        mainHolder.fl_item4.setText(documentos_cobra_movBE.getNOM_PERSONA_DESTINO());
        mainHolder.fl_item5.setText(documentos_cobra_movBE.getNOM_ESTADO_MOVIMIENTO());

        return convertView;
    }

    static class MainHolder {
        TextView fl_item1,fl_item2,fl_item3,fl_item4,fl_item5;

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
                    String todo = (documentos_cobra_movBE.getCOD_CLIENTE().toString());
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