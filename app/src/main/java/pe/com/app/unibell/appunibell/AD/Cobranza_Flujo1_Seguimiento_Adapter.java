package pe.com.app.unibell.appunibell.AD;

import android.content.Context;
import android.content.Intent;
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
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_CabBE;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Reportes.Activity_Cobranza_Recibo_Rep;
import pe.com.app.unibell.appunibell.Util.Funciones;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Cobranza_Flujo1_Seguimiento_Adapter extends ArrayAdapter<Documentos_Cobra_CabBE> implements Filterable {
    public List<Documentos_Cobra_CabBE> lstFiltrado;
    public List<Documentos_Cobra_CabBE> lst;
    private AdapterFilter adapterFilter;
    private Funciones funciones=new Funciones();
    private String sMoneda;

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    public Cobranza_Flujo1_Seguimiento_Adapter(Context context, int resource, List<Documentos_Cobra_CabBE> objects) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cobranza_flujo1, parent, false);
            mainHolder = new MainHolder();
            //mainHolder.fj_item1 = (TextView) convertView.findViewById(R.id.fj_item1);
            mainHolder.fj_item2 = (TextView) convertView.findViewById(R.id.fj_item2);
            mainHolder.fj_item3 = (TextView) convertView.findViewById(R.id.fj_item3);
            mainHolder.fj_item4 = (TextView) convertView.findViewById(R.id.fj_item4);
            mainHolder.fj_item5 = (TextView) convertView.findViewById(R.id.fj_item5);
            //mainHolder.fj_item6 = (TextView) convertView.findViewById(R.id.fj_item6);
            mainHolder.fj_item7 = (TextView) convertView.findViewById(R.id.fj_item7);
            mainHolder.fj_item8 = (TextView) convertView.findViewById(R.id.fj_item8);
            mainHolder.fj_item9 = (TextView) convertView.findViewById(R.id.fj_item9);
            mainHolder.fj_item10 = (TextView) convertView.findViewById(R.id.fj_item10);
            convertView.setTag(mainHolder);
        } else {
            mainHolder = (MainHolder) convertView.getTag();
        }



        final Documentos_Cobra_CabBE documentos_cobra_cabBE = getItem(position);
        //mainHolder.fj_item1.setText(documentos_cobra_cabBE.getCOD_CLIENTE());
        mainHolder.fj_item2.setText(documentos_cobra_cabBE.getCOD_CLIENTE() +"-"+funciones.LetraCapital(documentos_cobra_cabBE.getRAZON_SOCIAL()));
        mainHolder.fj_item3.setText(funciones.LetraCapital(documentos_cobra_cabBE.getTIPODOC()) +" "+documentos_cobra_cabBE.getNUMERO());
        //mainHolder.fj_item4.setText(documentos_cobra_cabBE.getNUMERO());
        mainHolder.fj_item5.setText(funciones.LetraCapital((documentos_cobra_cabBE.getENTIDAD())));
        mainHolder.fj_item4.setText(funciones.LetraCapital(documentos_cobra_cabBE.getFPAGO()));

        mainHolder.fj_item7.setText(documentos_cobra_cabBE.getFECHA());
        mainHolder.fj_item8.setText(documentos_cobra_cabBE.getCONSTANCIA());
        if(documentos_cobra_cabBE.getMONEDA().toString().trim().equals("S")) {
            mainHolder.fj_item9.setText("S/ " + documentos_cobra_cabBE.getM_COBRANZA());
        }else{
            mainHolder.fj_item9.setText("US$ " + documentos_cobra_cabBE.getM_COBRANZA());
        }
        mainHolder.fj_item10.setText(documentos_cobra_cabBE.getRECIBO());


     /*   MainHolder holder = (MainHolder) convertView.getTag();

        holder.fj_item10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                editor_Shared.putString("iN_SERIE_RECIBO", documentos_cobra_cabBE.getN_SERIE_RECIBO().toString());
                editor_Shared.putString("iN_RECIBO", documentos_cobra_cabBE.getN_RECIBO().toString());
                editor_Shared.putString("IOPCION_RECIBO","0");
                editor_Shared.commit();

                Intent intent = new Intent(getContext().getApplicationContext(), Activity_Cobranza_Recibo_Rep.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);

            }});
*/

        return convertView;
    }

    static class MainHolder {
        TextView fj_item1,fj_item2,fj_item3,fj_item4,fj_item5,fj_item6,fj_item7,fj_item8,fj_item9,fj_item10;
    }

    @Override
    public int getCount() {
        return lstFiltrado.size();
    }

    @Override
    public Documentos_Cobra_CabBE getItem(int position) {
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
                List<Documentos_Cobra_CabBE> localList = new ArrayList<>();
                String[] SSA=constraint.toString().toUpperCase().split(" ");
                for (Documentos_Cobra_CabBE documentos_cobra_cabBE : lst) {
                    String todo = (documentos_cobra_cabBE.getCOD_CLIENTE().toString());
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
