package pe.com.app.unibell.appunibell.BE;

/**
 * Created by RENAN on 18/08/2016.
 */
public class PreferenciasBE {
    private String PRE_KEYNOM;
    private String PRE_KEYVAL;

    public PreferenciasBE(){
    }

    public PreferenciasBE(String PRE_KEYNOM, String PRE_KEYVAL) {
        this.PRE_KEYNOM = PRE_KEYNOM;
        this.PRE_KEYVAL = PRE_KEYVAL;
    }

    public String getPRE_KEYNOM() {
        return PRE_KEYNOM;
    }

    public void setPRE_KEYNOM(String PRE_KEYNOM) {
        this.PRE_KEYNOM = PRE_KEYNOM;
    }

    public String getPRE_KEYVAL() {
        return PRE_KEYVAL;
    }

    public void setPRE_KEYVAL(String PRE_KEYVAL) {
        this.PRE_KEYVAL = PRE_KEYVAL;
    }
}
