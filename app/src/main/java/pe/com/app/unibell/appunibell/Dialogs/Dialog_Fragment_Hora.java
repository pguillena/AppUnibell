package pe.com.app.unibell.appunibell.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by RENAN on 18/08/2016.
 */
public class Dialog_Fragment_Hora extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private NoticeDialogoListener listener;


    public interface NoticeDialogoListener{
        public void setearHora(String hora);
    }
    @Override
    public void onAttach(Activity activity) {
        try{
            super.onAttach(activity);
            this.listener=(NoticeDialogoListener)activity;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar= Calendar.getInstance();
       /* .......List of Themes.......
        THEME_DEVICE_DEFAULT_DARK
        THEME_DEVICE_DEFAULT_LIGHT
        THEME_HOLO_DARK
        THEME_HOLO_LIGHT
        THEME_TRADITIONAL
        */

        int hora=calendar.get(Calendar.HOUR);
        int minuto=calendar.get(Calendar.MINUTE);
        //return new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK,this,hora,minuto, DateFormat.is24HourFormat(getActivity()));
        return new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,this,hora,minuto, true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        try{
            String Hora,Minutos;
            if(hourOfDay <10) {
                Hora = "0" + String.valueOf(hourOfDay);
            }else{
                Hora = String.valueOf(hourOfDay);
            }
            if(minute <10) {
                Minutos = "0" + String.valueOf(minute);
            }else{
                Minutos = String.valueOf(minute);
            }
            listener.setearHora(Hora+ ":" + Minutos);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}