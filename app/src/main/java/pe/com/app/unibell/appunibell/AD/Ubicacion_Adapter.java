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

import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import pe.com.app.unibell.appunibell.BE.S_Inv_InventarioBE;

public class Ubicacion_Adapter  extends ArrayAdapter<S_Inv_InventarioBE> implements Filterable {
    public List<S_Inv_InventarioBE> lstFiltrado;
    public List<S_Inv_InventarioBE> lst;
    private Ubicacion_Adapter.AdapterFilter adapterFilter;

    public Ubicacion_Adapter(Context context, int resource, List<S_Inv_InventarioBE> objects) {
        super(context, resource, objects);
        adapterFilter = new Ubicacion_Adapter.AdapterFilter();
        lstFiltrado = new ArrayList<>();
        lstFiltrado.addAll(objects);
        lst = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ubicacion_Adapter.MainHolder mainHolder = null;
        if (convertView == null || !(convertView.getTag() instanceof Ubicacion_Adapter.MainHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_fragment_auxiliar, parent, false);
            mainHolder = new Ubicacion_Adapter.MainHolder();
            mainHolder.dfa_item1 = (TextView) convertView.findViewById(R.id.dfa_item1);
            mainHolder.dfa_item2 = (TextView) convertView.findViewById(R.id.dfa_item2);
            convertView.setTag(mainHolder);
        } else {
            mainHolder = (Ubicacion_Adapter.MainHolder) convertView.getTag();
        }
        final S_Inv_InventarioBE inventarioBE = getItem(position);
        mainHolder.dfa_item1.setText(inventarioBE.getCOD_ALM());
        mainHolder.dfa_item2.setText(inventarioBE.getUBICACION());
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
    public S_Inv_InventarioBE getItem(int position) {
        return lstFiltrado.get(position);
    }

    @Override
    public Filter getFilter() {
        if (adapterFilter == null) {
            adapterFilter = new Ubicacion_Adapter.AdapterFilter();
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
                List<S_Inv_InventarioBE> localList = new ArrayList<>();
                String[] SSA=constraint.toString().toUpperCase().split(" ");
                for (S_Inv_InventarioBE inventarioBE : lst) {
                    String todo = (inventarioBE.getUBICACION().toUpperCase());
                    Boolean bfind = true;
                    for(String s : SSA){
                        if(!todo.contains(s)){
                            bfind = false;
                        }
                    }
                    if (bfind != false){
                        localList.add(inventarioBE);
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
