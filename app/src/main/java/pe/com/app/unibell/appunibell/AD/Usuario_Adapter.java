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

import pe.com.app.unibell.appunibell.BE.UsuarioBE;
import pe.com.app.unibell.appunibell.R;

/**
 * Created by rgalvez on 26/10/2016.
 */
public class Usuario_Adapter extends ArrayAdapter<UsuarioBE> implements Filterable {
    public List<UsuarioBE> lstFiltrado;
    public List<UsuarioBE> lst;
    private AdapterFilter adapterFilter;

    public Usuario_Adapter(Context context, int resource, List<UsuarioBE> objects) {
        super(context, resource, objects);
        adapterFilter = new AdapterFilter();
        lstFiltrado = new ArrayList<>();
        lstFiltrado.addAll(objects);
        //this.items.add(objects);
        lst = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainHolder mainHolder = null;
        if (convertView == null || !(convertView.getTag() instanceof MainHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_usuario, parent, false);
            mainHolder = new MainHolder();
            mainHolder.us_itemusuario = (TextView) convertView.findViewById(R.id.us_itemusuario);
            mainHolder.us_itemrol = (TextView) convertView.findViewById(R.id.us_itemrol);
            convertView.setTag(mainHolder);
        } else {
            mainHolder = (MainHolder) convertView.getTag();
        }
        final UsuarioBE item = getItem(position);
        mainHolder.us_itemusuario.setText(item.getUSU_NOMBRE().toString());
        mainHolder.us_itemrol.setText(item.getTIU_NOMBRE().toString());
        return convertView;
    }

    static class MainHolder {
        TextView us_itemusuario,us_itemrol;
    }

    @Override
    public int getCount() {
        return lstFiltrado.size();
    }

    @Override
    public UsuarioBE getItem(int position) {
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
                List<UsuarioBE> localList = new ArrayList<>();
                String[] SSA=constraint.toString().toUpperCase().split(" ");
                for (UsuarioBE usuarioBE : lst) {
                    String todo = (usuarioBE.getUSU_NOMBRE().toString());
                    Boolean bfind = true;
                    for(String s : SSA){
                        if(!todo.contains(s)){
                            bfind = false;
                        }
                    }
                    if (bfind != false){
                        localList.add(usuarioBE);
                    }
                }
                filterResults.values = new ArrayList<>(localList);
                filterResults.count = localList.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
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
