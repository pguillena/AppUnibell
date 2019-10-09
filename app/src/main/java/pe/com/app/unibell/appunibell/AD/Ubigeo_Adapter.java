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

import pe.com.app.unibell.appunibell.BE.UbigeoBE;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Ubigeo_Adapter  extends ArrayAdapter<UbigeoBE> implements Filterable {
    public List<UbigeoBE> lstFiltrado;
    public List<UbigeoBE> lst;
    private Ubigeo_Adapter.AdapterFilter adapterFilter;

    public Ubigeo_Adapter(Context context, int resource, List<UbigeoBE> objects) {
        super(context, resource, objects);
        adapterFilter = new Ubigeo_Adapter.AdapterFilter();
        lstFiltrado = new ArrayList<>();
        lstFiltrado.addAll(objects);
        lst = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ubigeo_Adapter.MainHolder mainHolder = null;
        if (convertView == null || !(convertView.getTag() instanceof Ubigeo_Adapter.MainHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_fragment_auxiliar, parent, false);
            mainHolder = new Ubigeo_Adapter.MainHolder();
            mainHolder.dfa_item1 = (TextView) convertView.findViewById(R.id.dfa_item1);
            mainHolder.dfa_item2 = (TextView) convertView.findViewById(R.id.dfa_item2);
            convertView.setTag(mainHolder);
        } else {
            mainHolder = (Ubigeo_Adapter.MainHolder) convertView.getTag();
        }
        final UbigeoBE ubigeoBE = getItem(position);
        mainHolder.dfa_item1.setText(ubigeoBE.getCODUBIGEO().toString());
        mainHolder.dfa_item2.setText(ubigeoBE.getNOMBRE());
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
    public UbigeoBE getItem(int position) {
        return lstFiltrado.get(position);
    }

    @Override
    public Filter getFilter() {
        if (adapterFilter == null) {
            adapterFilter = new Ubigeo_Adapter.AdapterFilter();
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
                List<UbigeoBE> localList = new ArrayList<>();
                String[] SSA=constraint.toString().toUpperCase().split(" ");
                for (UbigeoBE ctaBncoBE : lst) {
                    String todo = (ctaBncoBE.getNOMBRE().toUpperCase());
                    Boolean bfind = true;
                    for(String s : SSA){
                        if(!todo.contains(s)){
                            bfind = false;
                        }
                    }
                    if (bfind != false){
                        localList.add(ctaBncoBE);
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