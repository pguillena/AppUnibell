package pe.com.app.unibell.appunibell.BE;

public class S_gem_TipoCambioBE {
    
    private  String FECHA;
    private  String TIPO_CAMBIO;
    private  Integer TIPO_MONEDA;
    private  Double IMPORT_CAM;

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public String getTIPO_CAMBIO() {
        return TIPO_CAMBIO;
    }

    public void setTIPO_CAMBIO(String TIPO_CAMBIO) {
        this.TIPO_CAMBIO = TIPO_CAMBIO;
    }

    public Integer getTIPO_MONEDA() {
        return TIPO_MONEDA;
    }

    public void setTIPO_MONEDA(Integer TIPO_MONEDA) {
        this.TIPO_MONEDA = TIPO_MONEDA;
    }

    public Double getIMPORT_CAM() {
        return IMPORT_CAM;
    }

    public void setIMPORT_CAM(Double IMPORT_CAM) {
        this.IMPORT_CAM = IMPORT_CAM;
    }
}
