package pe.com.app.unibell.appunibell.AD;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import pe.com.app.unibell.appunibell.BE.Menu_StringBE;
import pe.com.app.unibell.appunibell.R;

/**
 * Created by RENAN on 24/09/2016.
 */
public class Menu_Adapter extends ArrayAdapter<Menu_StringBE> implements Filterable {
    public List<Menu_StringBE> lstFiltrado;
    public List<Menu_StringBE> lst;
    private AdapterFilter adapterFilter;

    public Menu_Adapter(Context context, int resource, List<Menu_StringBE> objects) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_menu, parent, false);
            mainHolder = new MainHolder();
            mainHolder.mnu_titulo = (TextView) convertView.findViewById(R.id.mnu_titulo);
           // mainHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(mainHolder);
        } else {
            mainHolder = (MainHolder) convertView.getTag();
        }
        final Menu_StringBE menu_stringBE = getItem(position);

        mainHolder.mnu_titulo.setText(menu_stringBE.getMNUDES().toString());
        mainHolder.mnu_titulo.setTextColor(getContext().getResources().getColor(R.color.label_blanco_login_unibell));
        mainHolder.mnu_titulo.setTypeface(null, Typeface.NORMAL);

        if (menu_stringBE.getRUTA_ICONO()!=null && !menu_stringBE.getRUTA_ICONO().toString().trim().equals("")){
            try {
                int imageResource = getContext().getResources().getIdentifier(menu_stringBE.getRUTA_ICONO(), null, getContext().getPackageName());
                Drawable imagen = ContextCompat.getDrawable(getContext().getApplicationContext(), imageResource);
                mainHolder.mnu_titulo.setCompoundDrawablesWithIntrinsicBounds(imagen,null,null, null);

            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return convertView;
    }

    static class MainHolder {
        TextView mnu_titulo;
        ImageView mnu_img;
        CheckBox checkBox;
    }

    @Override
    public int getCount() {
        return lstFiltrado.size();
    }

    @Override
    public Menu_StringBE getItem(int position) {
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
                List<Menu_StringBE> localList = new ArrayList<>();
                String[] SSA=constraint.toString().toUpperCase().split(" ");
                for (Menu_StringBE menu_stringBE : lst) {
                    String todo = (menu_stringBE.getMNUDES().toUpperCase());
                    Boolean bfind = true;
                    for(String s : SSA){
                        if(!todo.contains(s)){
                            bfind = false;
                        }
                    }
                    if (bfind != false){
                        localList.add(menu_stringBE);
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