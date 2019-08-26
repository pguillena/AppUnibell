package pe.com.app.unibell.appunibell.AD;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import pe.com.app.unibell.appunibell.BE.PreferenciasBE;
import pe.com.app.unibell.appunibell.R;

/**
 * Created by rgalvez on 31/07/2017.
 */

public class Preferencias_Adapter
        extends ArrayAdapter<PreferenciasBE> implements Filterable {
    public List<PreferenciasBE> lstFiltrado;
    public List<PreferenciasBE> lst;
    private AdapterFilter adapterFilter;


    public Preferencias_Adapter(Context context, int resource, List<PreferenciasBE> objects) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.items_simple, parent, false);
            mainHolder = new MainHolder();
            mainHolder.is_item = (TextView) convertView.findViewById(R.id.is_item);
            mainHolder.is_valor = (TextView) convertView.findViewById(R.id.is_valor);
            convertView.setTag(mainHolder);
        } else {
            mainHolder = (MainHolder) convertView.getTag();
        }
        final PreferenciasBE preferenciasBE = getItem(position);
        mainHolder.is_item.setText(preferenciasBE.getPRE_KEYNOM().toString());
        mainHolder.is_valor.setText(preferenciasBE.getPRE_KEYVAL());
        return convertView;
    }

    static class MainHolder {
        TextView is_item,is_valor;
    }

    @Override
    public int getCount() {
        return lstFiltrado.size();
    }

    @Override
    public PreferenciasBE getItem(int position) {
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
                List<PreferenciasBE> localList = new ArrayList<>();
                String[] SSA=constraint.toString().toUpperCase().split(" ");
                for (PreferenciasBE preferenciasBE : lst) {
                    String todo = (preferenciasBE.getPRE_KEYNOM().toUpperCase());
                    Boolean bfind = true;
                    for(String s : SSA){
                        if(!todo.contains(s)){
                            bfind = false;
                        }
                    }
                    if (bfind != false){
                        localList.add(preferenciasBE);
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