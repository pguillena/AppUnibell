package pe.com.app.unibell.appunibell.Dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by RENAN on 18/08/2016.
 */


public class Dialogo_Fragment_Fecha extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private NoticeDialogoListener listener;
    private SimpleDateFormat dateFormatter;
    public Integer iMinDate = 0, iMaxDate=0;


    public interface NoticeDialogoListener {
        public void setearFecha(String fecha);
    }

    @Override
    public void onAttach(Activity activity) {
        try {
            super.onAttach(activity);
            this.listener = (NoticeDialogoListener) activity;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        final Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, anio, mes, dia);

        //Aqui seteamos el minimo de dias
        if (iMinDate > 0) {
            calendar.add(Calendar.DATE, -1 * iMinDate);
            dpd.getDatePicker().setMinDate(calendar.getTimeInMillis());
        }
        if (iMaxDate > 0) {
            calendar.add(Calendar.DATE, iMaxDate);
            dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        }

        return dpd;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        try {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            listener.setearFecha(dateFormatter.format(newDate.getTime()));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}