package pe.com.app.unibell.appunibell.AD;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_CabBE;
import pe.com.app.unibell.appunibell.Cobranza.Activity_Cobranzas;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

import static android.content.Context.MODE_PRIVATE;

public class Cobranza_Cabecera_Adapter
        extends ArrayAdapter<Documentos_Cobra_CabBE> implements Filterable {

    public List<Documentos_Cobra_CabBE> lstFiltrado;
    public List<Documentos_Cobra_CabBE> lst;
    private AdapterFilter adapterFilter;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    Funciones funciones = new Funciones();

    public Cobranza_Cabecera_Adapter(Context context, int resource, List<Documentos_Cobra_CabBE> objects) {
        super(context, resource, objects);
        adapterFilter = new AdapterFilter();
        lstFiltrado = new ArrayList<>();
        lstFiltrado.addAll(objects);
        lst = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Integer iposition = 0;
        final MainHolder mainHolder;
        if (convertView == null || !(convertView.getTag() instanceof Cobranza_Detalle_Adapter.MainHolder)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cobranza_cabecera, parent, false);
            mainHolder = new MainHolder();
            mainHolder.cc_item1 = (TextView) convertView.findViewById(R.id.cc_item1);
            mainHolder.cc_item2 = (TextView) convertView.findViewById(R.id.cc_item2);
            mainHolder.cc_item3 = (TextView) convertView.findViewById(R.id.cc_item3);
            mainHolder.cc_item4 = (TextView) convertView.findViewById(R.id.cc_item4);
            mainHolder.cc_item5 = (TextView) convertView.findViewById(R.id.cc_item5);
            mainHolder.cc_lnfecnum = (LinearLayout) convertView.findViewById(R.id.cc_lnfecnum);
            mainHolder.cc_lnfecnum2 = (LinearLayout) convertView.findViewById(R.id.cc_lnfecnum2);
            mainHolder.cc_btn0 = (TextView) convertView.findViewById(R.id.cc_btn0);
            mainHolder.cc_btn1 = (TextView) convertView.findViewById(R.id.cc_btn1);
            mainHolder.cc_NroDoc = (TextView) convertView.findViewById(R.id.cc_NroDoc);


            sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();
            convertView.setTag(mainHolder);
        } else {
            mainHolder = (MainHolder) convertView.getTag();
        }
        final Documentos_Cobra_CabBE documentos_cobra_cabBE = getItem(position);
        mainHolder.cc_item1.setText(funciones.LetraCapital(documentos_cobra_cabBE.getFPAGODESC().toString()));


        if(!documentos_cobra_cabBE.getFPAGO().toString().equals("E")) {
            if (!documentos_cobra_cabBE.getBANCODESC().toString().trim().equals("")) {
                mainHolder.cc_item2.setText(funciones.LetraCapital(documentos_cobra_cabBE.getBANCODESC().toString()));
            } else {
                mainHolder.cc_item2.setText(funciones.LetraCapital(documentos_cobra_cabBE.getNOMCTACORRIENTE().toString()));
            }
        }
        else
        {
            mainHolder.cc_item2.setVisibility(View.GONE);
        }

        if(documentos_cobra_cabBE.getM_COBRANZA()>0) {
            mainHolder.cc_item3.setText("S/ " + documentos_cobra_cabBE.getM_COBRANZA().toString());
        }else{
            mainHolder.cc_item3.setText("$ " + documentos_cobra_cabBE.getM_COBRANZA_D().toString());
        }
        mainHolder.cc_item4.setText("S/ " + documentos_cobra_cabBE.getSALDO().toString());
        editor_Shared.putString("SALDO_CABECERA",documentos_cobra_cabBE.getSALDO().toString());
        editor_Shared.commit();
        String sFPAGO=documentos_cobra_cabBE.getFPAGO().toString();

        mainHolder.cc_lnfecnum.setVisibility(View.GONE);
        mainHolder.cc_lnfecnum2.setVisibility(View.GONE);
        //TARJETAS DE CREDITO
        if (sFPAGO.equals("D") || sFPAGO.equals("V") || sFPAGO.equals("M") || sFPAGO.equals("S") || sFPAGO.equals("I")|| sFPAGO.equals("H")) {
            mainHolder.cc_item5.setText(documentos_cobra_cabBE.getFECHA_DEPOSITO().toString());
            mainHolder.cc_NroDoc.setText( documentos_cobra_cabBE.getN_TARJETA().toString());
            mainHolder.cc_lnfecnum.setVisibility(View.VISIBLE);
            mainHolder.cc_lnfecnum2.setVisibility(View.VISIBLE);
        }
        //DEPOSITO EN BANCO
        if (sFPAGO.equals("P")) {
            mainHolder.cc_item5.setText(documentos_cobra_cabBE.getFECHA_DEPOSITO().toString());
            mainHolder.cc_NroDoc.setText(documentos_cobra_cabBE.getNRO_OPERACION().toString());
            mainHolder.cc_lnfecnum.setVisibility(View.VISIBLE);
            mainHolder.cc_lnfecnum2.setVisibility(View.VISIBLE);
        }
        //CHEQUE
        if (sFPAGO.equals("C")) {
            mainHolder.cc_item5.setText(documentos_cobra_cabBE.getFECCHEQ().toString());
            mainHolder.cc_NroDoc.setText(documentos_cobra_cabBE.getNUMCHEQ().toString());
            mainHolder.cc_lnfecnum.setVisibility(View.VISIBLE);
            mainHolder.cc_lnfecnum2.setVisibility(View.VISIBLE);
        }

        String sID_COBRANZA=sharedSettings.getString("ID_COBRANZA", "0").toString().toString().trim();
        String aID_COBRANZA=documentos_cobra_cabBE.getID_COBRANZA().toString().trim();
        /*
        if(aID_COBRANZA.equals(sID_COBRANZA)) {
            convertView.setBackgroundResource(R.color.list_seleccion);
        }else{
            convertView.setBackgroundResource(R.color.background);
        }
        */

     /*   if(documentos_cobra_cabBE.getSALDO()>0) {
            mainHolder.cc_item5.setBackgroundResource(R.color.colorAccent);
        }else{
            mainHolder.cc_item5.setBackgroundResource(R.color.red_indicator);
        }*/

        mainHolder.cc_btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    lstFiltrado.remove(iposition);
                    notifyDataSetChanged();
                    final CharSequence[] options1 = {"Editar Registro", "Eliminar Registro"};
                    LlamarOpciones(options1,iposition);

                }catch (Exception ex) {
                    Toast toastCodigo = Toast.makeText(getContext(),ex.getMessage(), Toast.LENGTH_SHORT);
                    toastCodigo.show();
                }
            }
        });

        mainHolder.cc_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    lstFiltrado.remove(iposition);
                    notifyDataSetChanged();
                    final CharSequence[] options1 = {"Editar Registro", "Eliminar Registro"};
                    LlamarOpciones(options1,iposition);

                }catch (Exception ex) {
                    Toast toastCodigo = Toast.makeText(getContext(),ex.getMessage(), Toast.LENGTH_SHORT);
                    toastCodigo.show();
                }
            }
        });

        return convertView;
    }

    private void LlamarOpciones(CharSequence[] options1,Integer iposition){
        try {
            final CharSequence[] options =options1;
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            final Integer iposition2=iposition;

            builder.setCancelable(false);
            builder.setTitle("Opciones");
            builder.setIcon(android.R.drawable.ic_dialog_info);
            builder.setCancelable(true);

            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int seleccion) {
                    switch(options[seleccion].toString()) {
                        case "Editar Registro":
                            if(getContext() instanceof Activity_Cobranzas){
                                ((Activity_Cobranzas)getContext()).CobranzaEditar_Adapter(iposition2);
                            }
                            break;
                        case "Eliminar Registro":
                            if(getContext() instanceof Activity_Cobranzas){
                                ((Activity_Cobranzas)getContext()).EliminarCobranzaCab(iposition2);
                            }
                            break;
                    }
                }
            });
            builder.show();
        }catch(RuntimeException e){
            e.printStackTrace();
        }
    }


    static class MainHolder {
        TextView cc_item1,cc_item2,cc_item3,cc_item4,cc_item5, cc_NroDoc;
        EditText cc_txt1,cc_txt2;
        TextView cc_btn0,cc_btn1;
        LinearLayout cc_lnfecnum, cc_lnfecnum2;
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
