package pe.com.app.unibell.appunibell.AD;
import pe.com.app.unibell.appunibell.BE.S_Inv_InventarioBE;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.com.app.unibell.appunibell.DAO.S_Inv_InventarioDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Confirmar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Scanner;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.ScannerBarcode.Activity_Scanner;
import pe.com.app.unibell.appunibell.ScannerBarcode.Activity_ScannerBarcode;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class Scanner_Adapter extends ArrayAdapter<S_Inv_InventarioBE> implements Filterable {
    public List<S_Inv_InventarioBE> lstFiltrado;
    public List<S_Inv_InventarioBE> lst;
    private AdapterFilter adapterFilter;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private Integer iAccion =0;
    private Dialog_Fragment_Confirmar dialog_fragment_confirmar = null;
    private Dialog_Fragment_Scanner dialog_fragment_scanner = null;

    public Scanner_Adapter(Context context, int resource, List<S_Inv_InventarioBE> objects) {
        super(context, resource, objects);
        adapterFilter = new AdapterFilter();
        lstFiltrado = new ArrayList<>();
        lstFiltrado.addAll(objects);
        lst = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MainHolder mainHolder;
        if (convertView == null || !(convertView.getTag() instanceof MainHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_scanner, parent, false);
            mainHolder = new MainHolder();
            mainHolder.itemUbicacion = (TextView) convertView.findViewById(R.id.itemUbicacion);
            mainHolder.itemCodigoBarra = (TextView) convertView.findViewById(R.id.itemCodigoBarra);
            mainHolder.itemCantidad = (TextView) convertView.findViewById(R.id.itemCantidad);
            mainHolder.itemBtnEditar = (Button) convertView.findViewById(R.id.itemBtnEditar);
            mainHolder.itemBtnBorrar = (Button) convertView.findViewById(R.id.itemBtnBorrar);

            convertView.setTag(mainHolder);
        } else {
            mainHolder = (MainHolder) convertView.getTag();
        }
        final S_Inv_InventarioBE inventarioBE = getItem(position);
        mainHolder.itemBtnEditar.setTag(position);
        mainHolder.itemBtnBorrar.setTag(position);

        mainHolder.itemUbicacion.setText(inventarioBE.getUBICACION().toString());
        mainHolder.itemCantidad.setText(inventarioBE.getCANTIDAD().toString());

        if(inventarioBE.getCOD_ART()!=null && !inventarioBE.equals("null") && !inventarioBE.getCOD_ART().equals(""))
        {
            mainHolder.itemCodigoBarra.setText(inventarioBE.getCODIGO_BARRA().toString() + "\n" +  inventarioBE.getCOD_ART().toString()+ "\n" +  inventarioBE.getDESCRIPCION().toString());
        }
        else
        {
            mainHolder.itemCodigoBarra.setText(inventarioBE.getCODIGO_BARRA().toString());
        }


        mainHolder.itemBtnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if(getContext() instanceof  Activity_Scanner){
                        ((Activity_Scanner)getContext()).EliminarInventario(inventarioBE);
                    }
                    notifyDataSetChanged();
                }catch (Exception ex) {
                    Toast toastCodigo = Toast.makeText(getContext(),ex.getMessage(), Toast.LENGTH_SHORT);
                    toastCodigo.show();
                }


            }
        });



        mainHolder.itemBtnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if(getContext() instanceof  Activity_Scanner){
                        ((Activity_Scanner)getContext()).EditarInventario(inventarioBE);                    }
                    notifyDataSetChanged();
                }catch (Exception ex) {
                    Toast toastCodigo = Toast.makeText(getContext(),ex.getMessage(), Toast.LENGTH_SHORT);
                    toastCodigo.show();
                }

            }
        });




        return convertView;
    }

    static class MainHolder {
        TextView itemUbicacion, itemCodigoBarra, itemCantidad;
        Button itemBtnEditar, itemBtnBorrar;
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
                List<S_Inv_InventarioBE> localList = new ArrayList<>();
                String[] SSA=constraint.toString().toUpperCase().split(" ");
                for (S_Inv_InventarioBE documentos_cobra_cabBE : lst) {
                    String todo = (documentos_cobra_cabBE.getCODIGO_BARRA().toString()+documentos_cobra_cabBE.getUBICACION()+documentos_cobra_cabBE.getCOD_ART()+documentos_cobra_cabBE.getDESCRIPCION());
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



}
