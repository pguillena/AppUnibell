package pe.com.app.unibell.appunibell.BE;

/**
 * Created by RENAN on 18/08/2016.
 */
public class Usuario_TipoBE {
    private Integer TIU_CORREL;
    private String TIU_NOMBRE;
    private Integer TIU_ESTADO;

    public Usuario_TipoBE(){
    }

    public Usuario_TipoBE(Integer TIU_CORREL, String TIU_NOMBRE, Integer TIU_ESTADO) {
        this.TIU_CORREL = TIU_CORREL;
        this.TIU_NOMBRE = TIU_NOMBRE;
        this.TIU_ESTADO = TIU_ESTADO;
    }

    public Integer getTIU_CORREL() {
        return TIU_CORREL;
    }

    public void setTIU_CORREL(Integer TIU_CORREL) {
        this.TIU_CORREL = TIU_CORREL;
    }

    public String getTIU_NOMBRE() {
        return TIU_NOMBRE;
    }

    public void setTIU_NOMBRE(String TIU_NOMBRE) {
        this.TIU_NOMBRE = TIU_NOMBRE;
    }

    public Integer getTIU_ESTADO() {
        return TIU_ESTADO;
    }

    public void setTIU_ESTADO(Integer TIU_ESTADO) {
        this.TIU_ESTADO = TIU_ESTADO;
    }
}
