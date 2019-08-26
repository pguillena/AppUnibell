package pe.com.app.unibell.appunibell.BE;

/**
 * Created by RENAN on 18/08/2016.
 */
public class Rol_PermisoBE {
    private Integer ROL_CORREL;
    private String ROL_NOMBRE;
    private Integer ROL_FCION;
    private Integer ROL_VSIBL;
    private Integer ROL_ADMIN;

    public Rol_PermisoBE(){
    }

    public Rol_PermisoBE(Integer ROL_CORREL, String ROL_NOMBRE, Integer ROL_FCION, Integer ROL_VSIBL, Integer ROL_ADMIN) {
        this.ROL_CORREL = ROL_CORREL;
        this.ROL_NOMBRE = ROL_NOMBRE;
        this.ROL_FCION = ROL_FCION;
        this.ROL_VSIBL = ROL_VSIBL;
        this.ROL_ADMIN = ROL_ADMIN;
    }

    public Integer getROL_CORREL() {
        return ROL_CORREL;
    }

    public void setROL_CORREL(Integer ROL_CORREL) {
        this.ROL_CORREL = ROL_CORREL;
    }

    public String getROL_NOMBRE() {
        return ROL_NOMBRE;
    }

    public void setROL_NOMBRE(String ROL_NOMBRE) {
        this.ROL_NOMBRE = ROL_NOMBRE;
    }

    public Integer getROL_FCION() {
        return ROL_FCION;
    }

    public void setROL_FCION(Integer ROL_FCION) {
        this.ROL_FCION = ROL_FCION;
    }

    public Integer getROL_VSIBL() {
        return ROL_VSIBL;
    }

    public void setROL_VSIBL(Integer ROL_VSIBL) {
        this.ROL_VSIBL = ROL_VSIBL;
    }

    public Integer getROL_ADMIN() {
        return ROL_ADMIN;
    }

    public void setROL_ADMIN(Integer ROL_ADMIN) {
        this.ROL_ADMIN = ROL_ADMIN;
    }
}
