package pe.com.app.unibell.appunibell.BE;

/**
 * Created by RENAN on 18/08/2016.
 */
public class UsuarioBE {
    private Integer USU_CORREL;
    private String USU_NOMBRE;
    private String USU_PASSWO;
    private Integer ROL_CORREL;
    private Integer USU_ESTADO;
    private String TIU_NOMBRE;

    public UsuarioBE(){
    }

    public UsuarioBE(Integer USU_CORREL, String USU_NOMBRE, String USU_PASSWO, Integer ROL_CORREL, Integer USU_ESTADO) {
        this.USU_CORREL = USU_CORREL;
        this.USU_NOMBRE = USU_NOMBRE;
        this.USU_PASSWO = USU_PASSWO;
        this.ROL_CORREL = ROL_CORREL;
        this.USU_ESTADO=USU_ESTADO;
    }

    public UsuarioBE(Integer USU_CORREL, String USU_NOMBRE, String USU_PASSWO, Integer ROL_CORREL, Integer USU_ESTADO, String TIU_NOMBRE) {
        this.USU_CORREL = USU_CORREL;
        this.USU_NOMBRE = USU_NOMBRE;
        this.USU_PASSWO = USU_PASSWO;
        this.ROL_CORREL = ROL_CORREL;
        this.USU_ESTADO=USU_ESTADO;
        this.TIU_NOMBRE=TIU_NOMBRE;
    }

    public Integer getUSU_CORREL() {
        return USU_CORREL;
    }

    public void setUSU_CORREL(Integer USU_CORREL) {
        this.USU_CORREL = USU_CORREL;
    }

    public String getUSU_NOMBRE() {
        return USU_NOMBRE;
    }

    public void setUSU_NOMBRE(String USU_NOMBRE) {
        this.USU_NOMBRE = USU_NOMBRE;
    }

    public String getUSU_PASSWO() {
        return USU_PASSWO;
    }

    public void setUSU_PASSWO(String USU_PASSWO) {
        this.USU_PASSWO = USU_PASSWO;
    }

    public Integer getROL_CORREL() {
        return ROL_CORREL;
    }

    public void setROL_CORREL(Integer ROL_CORREL) {
        this.ROL_CORREL = ROL_CORREL;
    }

    public Integer getUSU_ESTADO() {
        return USU_ESTADO;
    }

    public void setUSU_ESTADO(Integer USU_ESTADO) {
        this.USU_ESTADO = USU_ESTADO;
    }

    public String getTIU_NOMBRE() {
        return TIU_NOMBRE;
    }

    public void setTIU_NOMBRE(String TIU_NOMBRE) {
        this.TIU_NOMBRE = TIU_NOMBRE;
    }
}
