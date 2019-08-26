package pe.com.app.unibell.appunibell.BE;

/**
 * Created by rgalvez on 7/10/2016.
 */
public class Menu_StringBE {
    private Integer IDCORREL;
    private String MNUNOM;
    private String MNUDES;
    private Integer MNUCHEK;
    private Integer MNUACTI;
    private Integer MENUEST;
    private Integer MNUORDEN;
    private Boolean MNUPERMISO;
    private String RUTA_ICONO;

    public String getRUTA_ICONO() {
        return RUTA_ICONO;
    }

    public void setRUTA_ICONO(String RUTA_ICONO) {
        this.RUTA_ICONO = RUTA_ICONO;
    }


    public Menu_StringBE(){
    }

    public Menu_StringBE(Integer IDCORREL, String MNUNOM, String MNUDES, Integer MNUCHEK, Integer MNUACTI, Integer MENUEST, Integer MNUORDEN, Boolean MNUPERMISO, String RUTA_ICONO) {
        this.IDCORREL = IDCORREL;
        this.MNUNOM = MNUNOM;
        this.MNUDES = MNUDES;
        this.MNUCHEK = MNUCHEK;
        this.MNUACTI = MNUACTI;
        this.MENUEST = MENUEST;
        this.MNUORDEN = MNUORDEN;
        this.MNUPERMISO=MNUPERMISO;
        this.RUTA_ICONO=RUTA_ICONO;
    }

    public Integer getIDCORREL() {
        return IDCORREL;
    }

    public void setIDCORREL(Integer IDCORREL) {
        this.IDCORREL = IDCORREL;
    }

    public String getMNUNOM() {
        return MNUNOM;
    }

    public void setMNUNOM(String MNUNOM) {
        this.MNUNOM = MNUNOM;
    }

    public String getMNUDES() {
        return MNUDES;
    }

    public void setMNUDES(String MNUDES) {
        this.MNUDES = MNUDES;
    }

    public Integer getMNUCHEK() {
        return MNUCHEK;
    }

    public void setMNUCHEK(Integer MNUCHEK) {
        this.MNUCHEK = MNUCHEK;
    }

    public Integer getMNUACTI() {
        return MNUACTI;
    }

    public void setMNUACTI(Integer MNUACTI) {
        this.MNUACTI = MNUACTI;
    }

    public Integer getMENUEST() {
        return MENUEST;
    }

    public void setMENUEST(Integer MENUEST) {
        this.MENUEST = MENUEST;
    }

    public Integer getMNUORDEN() {
        return MNUORDEN;
    }

    public void setMNUORDEN(Integer MNUORDEN) {
        this.MNUORDEN = MNUORDEN;
    }

    public Boolean getMNUPERMISO() {
        return MNUPERMISO;
    }

    public void setMNUPERMISO(Boolean MNUPERMISO) {
        this.MNUPERMISO = MNUPERMISO;
    }
}
