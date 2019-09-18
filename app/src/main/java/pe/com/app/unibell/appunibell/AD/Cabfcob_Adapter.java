package pe.com.app.unibell.appunibell.AD;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.Filter;
import java.util.ArrayList;
import java.util.List;
import pe.com.app.unibell.appunibell.BE.CabfcobBE;

public class Cabfcob_Adapter extends ArrayAdapter<CabfcobBE> implements Filterable {
    public List<CabfcobBE> lstFiltrado;
    public List<CabfcobBE> lst;
    private AdapterFilter adapterFilter;

    public Cabfcob_Adapter(Context context, int resource, List<CabfcobBE> objects) {
        super(context, resource, objects);
        adapterFilter = new AdapterFilter();
        lstFiltrado = new ArrayList<>();
        lstFiltrado.addAll(objects);
        lst = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

    @Override
    public int getCount() {
        return lstFiltrado.size();
    }

    @Override
    public CabfcobBE getItem(int position) {
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
                List<CabfcobBE> localList = new ArrayList<>();
                String[] SSA=constraint.toString().toUpperCase().split(" ");
                for (CabfcobBE cabfcobBE : lst) {
                    String todo = (cabfcobBE.getN_RECIBO_COBRA().toString());
                    Boolean bfind = true;
                    for(String s : SSA){
                        if(!todo.contains(s)){
                            bfind = false;
                        }
                    }
                    if (bfind != false){
                        localList.add(cabfcobBE);
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
