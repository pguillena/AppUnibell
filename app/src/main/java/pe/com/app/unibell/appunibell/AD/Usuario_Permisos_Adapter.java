package pe.com.app.unibell.appunibell.AD;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import pe.com.app.unibell.appunibell.BE.Menu_StringBE;
import pe.com.app.unibell.appunibell.R;

/**
 * Created by rgalvez on 15/10/2016.
 */
public class Usuario_Permisos_Adapter extends ArrayAdapter<Menu_StringBE> implements Filterable {
    public List<Menu_StringBE> lstFiltrado;
    public List<Menu_StringBE> lst;
    private AdapterFilter adapterFilter;

    public Usuario_Permisos_Adapter(Context context, int resource, List<Menu_StringBE> objects) {
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
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_permisos_rol, parent, false);
            final MainHolder mainHolder = new MainHolder();
            mainHolder.mpr_titulo = (TextView) view.findViewById(R.id.mpr_titulo);
            mainHolder.mpr_checkBox = (CheckBox) view.findViewById(R.id.mpr_checkBox);
            mainHolder.mpr_checkBox
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            Menu_StringBE element = (Menu_StringBE)mainHolder.mpr_checkBox.getTag();
                            element.setMNUPERMISO(buttonView.isChecked());
                        }
                    });
            view.setTag(mainHolder);
            mainHolder.mpr_checkBox.setTag(lstFiltrado.get(position));
        } else {
            view = convertView;
            ((MainHolder) view.getTag()).mpr_checkBox.setTag(lstFiltrado.get(position));
        }
        MainHolder holder = (MainHolder) view.getTag();
        holder.mpr_titulo.setText(lstFiltrado.get(position).getMNUDES().toString());
        holder.mpr_checkBox.setChecked(lstFiltrado.get(position).getMNUPERMISO());
        holder.mpr_checkBox.setVisibility(View.GONE);
        if(lstFiltrado.get(position).getMNUACTI()==0) {
            holder.mpr_titulo.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
            holder.mpr_titulo.setTypeface(null, Typeface.BOLD);
            //holder.mpr_titulo.setBackgroundResource(R.color.colorPrimary);
            //holder.mpr_titulo.setTextColor(Color.WHITE);
        }else{
            //holder.mpr_titulo.setBackgroundResource(R.color.background);
            holder.mpr_titulo.setTextColor(Color.BLACK);
            holder.mpr_titulo.setTypeface(null, Typeface.NORMAL);
            holder.mpr_checkBox.setChecked(Boolean.valueOf(lstFiltrado.get(position).getMNUPERMISO().toString()));
            holder.mpr_checkBox.setVisibility(View.VISIBLE);
        }
        return view;
    }

    static class MainHolder {
        TextView mpr_titulo;
        CheckBox mpr_checkBox;
        ImageView mnu_img;
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