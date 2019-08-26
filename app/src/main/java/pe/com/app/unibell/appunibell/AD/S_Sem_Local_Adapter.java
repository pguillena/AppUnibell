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

import pe.com.app.unibell.appunibell.BE.S_Sem_LocalBE;
import pe.com.app.unibell.appunibell.BE.Tablas_AuxiliaresBE;
import pe.com.app.unibell.appunibell.R;

public class S_Sem_Local_Adapter extends ArrayAdapter<S_Sem_LocalBE> implements Filterable {
    public List<S_Sem_LocalBE> lstFiltrado;
    public List<S_Sem_LocalBE> lst;
    private AdapterFilter adapterFilter;

    public S_Sem_Local_Adapter(Context context, int resource, List<S_Sem_LocalBE> objects) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_fragment_auxiliar, parent, false);
            mainHolder = new MainHolder();
            mainHolder.dfa_item1 = (TextView) convertView.findViewById(R.id.dfa_item1);
            mainHolder.dfa_item2 = (TextView) convertView.findViewById(R.id.dfa_item2);
            convertView.setTag(mainHolder);
        } else {
            mainHolder = (MainHolder) convertView.getTag();
        }
        final S_Sem_LocalBE s_sem_localBE = getItem(position);
        mainHolder.dfa_item1.setText(s_sem_localBE.getID_LOCAL().toString());
        mainHolder.dfa_item2.setText(s_sem_localBE.getNOMBRE());
        return convertView;
    }

    static class MainHolder {
        TextView dfa_item1,dfa_item2;


    }

    @Override
    public int getCount() {
        return lstFiltrado.size();
    }

    @Override
    public S_Sem_LocalBE getItem(int position) {
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
                List<S_Sem_LocalBE> localList = new ArrayList<>();
                String[] SSA=constraint.toString().toUpperCase().split(" ");
                for (S_Sem_LocalBE s_sem_localBE : lst) {
                    String todo = (s_sem_localBE.getNOMBRE().toUpperCase());
                    Boolean bfind = true;
                    for(String s : SSA){
                        if(!todo.contains(s)){
                            bfind = false;
                        }
                    }
                    if (bfind != false){
                        localList.add(s_sem_localBE);
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