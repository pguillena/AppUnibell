package pe.com.app.unibell.appunibell.BE;

import java.io.ObjectInput;

public class S_Gem_ClienteBE {

   private  Integer ID_CLIENT;
    private String CODIGO;
    private String RAZON_SOCIAL;
    private Integer ID_ZONA;
    private Integer TIPO_GIRO;
    private String TELEFONO;
    private String FAX;
    private String FECHA_INGRES;
    private Integer TIPO_CALIFICACION;
    private Integer ESTADO;
    private String RUC;
    private Integer CLIENTE_AFECTO;
    private String UBICACION;
    private String DNI;
    private Double DIA_VISITA;
    private Double FRC_VISITA;
    private String FLAG_RETENCION;
    private Integer CALIF_VTA;
    private Integer CALIF_CRED;
    private Double PLAZO_PAGO;
    private Integer ID_CANAL;
    private String CORREO;
    private Integer ID_NIVEL;
    private Integer ID_FUERZA_VTA;
    private Integer ID_EMPRESA;
    private Integer ID_LOCAL;
    private Double COND_PAGO;
    private Integer ID_GRUPO;
    private Integer ID_LISTA_DESCUENTO;
    private Integer ID_CANAL_DETALLE;
    private Double LIM_CRED;
    private Double PROM_MAX_DIA_PAGO;
    private Double PORC_MAX_DEUDA;
    private Double MONT_MAX_DEUDA;
    private Integer FLAG_PERCEPCION;
    private String FECHA_REGISTRO;
    private String FECHA_MODIFICACION;
    private String USUARIO_REGISTRO;
    private String USUARIO_MODIFICACION;
    private String PC_REGISTRO;
    private String PC_MODIFICACION;
    private String IP_REGISTRO;
    private String IP_MODIFICACION;
    private Integer CLIENTE_ESPECIAL;
    private Integer ID_COBRADOR;
    private Integer ID_COBRADOR_EXT;
    private Integer CUOTA_SEMANAL;
    private String COD_ALM_CONSIG;
    private Integer FLAG_FE;
    private Double LISTA_PRECIO;

    public Integer getID_CLIENT() {
        return ID_CLIENT;
    }

    public void setID_CLIENT(Integer ID_CLIENT) {
        this.ID_CLIENT = ID_CLIENT;
    }

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getRAZON_SOCIAL() {
        return RAZON_SOCIAL;
    }

    public void setRAZON_SOCIAL(String RAZON_SOCIAL) {
        this.RAZON_SOCIAL = RAZON_SOCIAL;
    }

    public Integer getID_ZONA() {
        return ID_ZONA;
    }

    public void setID_ZONA(Integer ID_ZONA) {
        this.ID_ZONA = ID_ZONA;
    }

    public Integer getTIPO_GIRO() {
        return TIPO_GIRO;
    }

    public void setTIPO_GIRO(Integer TIPO_GIRO) {
        this.TIPO_GIRO = TIPO_GIRO;
    }

    public String getTELEFONO() {
        return TELEFONO;
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }

    public String getFAX() {
        return FAX;
    }

    public void setFAX(String FAX) {
        this.FAX = FAX;
    }

    public String getFECHA_INGRES() {
        return FECHA_INGRES;
    }

    public void setFECHA_INGRES(String FECHA_INGRES) {
        this.FECHA_INGRES = FECHA_INGRES;
    }

    public Integer getTIPO_CALIFICACION() {
        return TIPO_CALIFICACION;
    }

    public void setTIPO_CALIFICACION(Integer TIPO_CALIFICACION) {
        this.TIPO_CALIFICACION = TIPO_CALIFICACION;
    }

    public Integer getESTADO() {
        return ESTADO;
    }

    public void setESTADO(Integer ESTADO) {
        this.ESTADO = ESTADO;
    }

    public String getRUC() {
        return RUC;
    }

    public void setRUC(String RUC) {
        this.RUC = RUC;
    }

    public Integer getCLIENTE_AFECTO() {
        return CLIENTE_AFECTO;
    }

    public void setCLIENTE_AFECTO(Integer CLIENTE_AFECTO) {
        this.CLIENTE_AFECTO = CLIENTE_AFECTO;
    }

    public String getUBICACION() {
        return UBICACION;
    }

    public void setUBICACION(String UBICACION) {
        this.UBICACION = UBICACION;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public Double getDIA_VISITA() {
        return DIA_VISITA;
    }

    public void setDIA_VISITA(Double DIA_VISITA) {
        this.DIA_VISITA = DIA_VISITA;
    }

    public Double getFRC_VISITA() {
        return FRC_VISITA;
    }

    public void setFRC_VISITA(Double FRC_VISITA) {
        this.FRC_VISITA = FRC_VISITA;
    }

    public String getFLAG_RETENCION() {
        return FLAG_RETENCION;
    }

    public void setFLAG_RETENCION(String FLAG_RETENCION) {
        this.FLAG_RETENCION = FLAG_RETENCION;
    }

    public Integer getCALIF_VTA() {
        return CALIF_VTA;
    }

    public void setCALIF_VTA(Integer CALIF_VTA) {
        this.CALIF_VTA = CALIF_VTA;
    }

    public Integer getCALIF_CRED() {
        return CALIF_CRED;
    }

    public void setCALIF_CRED(Integer CALIF_CRED) {
        this.CALIF_CRED = CALIF_CRED;
    }

    public Double getPLAZO_PAGO() {
        return PLAZO_PAGO;
    }

    public void setPLAZO_PAGO(Double PLAZO_PAGO) {
        this.PLAZO_PAGO = PLAZO_PAGO;
    }

    public Integer getID_CANAL() {
        return ID_CANAL;
    }

    public void setID_CANAL(Integer ID_CANAL) {
        this.ID_CANAL = ID_CANAL;
    }

    public String getCORREO() {
        return CORREO;
    }

    public void setCORREO(String CORREO) {
        this.CORREO = CORREO;
    }

    public Integer getID_NIVEL() {
        return ID_NIVEL;
    }

    public void setID_NIVEL(Integer ID_NIVEL) {
        this.ID_NIVEL = ID_NIVEL;
    }

    public Integer getID_FUERZA_VTA() {
        return ID_FUERZA_VTA;
    }

    public void setID_FUERZA_VTA(Integer ID_FUERZA_VTA) {
        this.ID_FUERZA_VTA = ID_FUERZA_VTA;
    }

    public Integer getID_EMPRESA() {
        return ID_EMPRESA;
    }

    public void setID_EMPRESA(Integer ID_EMPRESA) {
        this.ID_EMPRESA = ID_EMPRESA;
    }

    public Integer getID_LOCAL() {
        return ID_LOCAL;
    }

    public void setID_LOCAL(Integer ID_LOCAL) {
        this.ID_LOCAL = ID_LOCAL;
    }

    public Double getCOND_PAGO() {
        return COND_PAGO;
    }

    public void setCOND_PAGO(Double COND_PAGO) {
        this.COND_PAGO = COND_PAGO;
    }

    public Integer getID_GRUPO() {
        return ID_GRUPO;
    }

    public void setID_GRUPO(Integer ID_GRUPO) {
        this.ID_GRUPO = ID_GRUPO;
    }

    public Integer getID_LISTA_DESCUENTO() {
        return ID_LISTA_DESCUENTO;
    }

    public void setID_LISTA_DESCUENTO(Integer ID_LISTA_DESCUENTO) {
        this.ID_LISTA_DESCUENTO = ID_LISTA_DESCUENTO;
    }

    public Integer getID_CANAL_DETALLE() {
        return ID_CANAL_DETALLE;
    }

    public void setID_CANAL_DETALLE(Integer ID_CANAL_DETALLE) {
        this.ID_CANAL_DETALLE = ID_CANAL_DETALLE;
    }

    public Double getLIM_CRED() {
        return LIM_CRED;
    }

    public void setLIM_CRED(Double LIM_CRED) {
        this.LIM_CRED = LIM_CRED;
    }

    public Double getPROM_MAX_DIA_PAGO() {
        return PROM_MAX_DIA_PAGO;
    }

    public void setPROM_MAX_DIA_PAGO(Double PROM_MAX_DIA_PAGO) {
        this.PROM_MAX_DIA_PAGO = PROM_MAX_DIA_PAGO;
    }

    public Double getPORC_MAX_DEUDA() {
        return PORC_MAX_DEUDA;
    }

    public void setPORC_MAX_DEUDA(Double PORC_MAX_DEUDA) {
        this.PORC_MAX_DEUDA = PORC_MAX_DEUDA;
    }

    public Double getMONT_MAX_DEUDA() {
        return MONT_MAX_DEUDA;
    }

    public void setMONT_MAX_DEUDA(Double MONT_MAX_DEUDA) {
        this.MONT_MAX_DEUDA = MONT_MAX_DEUDA;
    }

    public Integer getFLAG_PERCEPCION() {
        return FLAG_PERCEPCION;
    }

    public void setFLAG_PERCEPCION(Integer FLAG_PERCEPCION) {
        this.FLAG_PERCEPCION = FLAG_PERCEPCION;
    }

    public String getFECHA_REGISTRO() {
        return FECHA_REGISTRO;
    }

    public void setFECHA_REGISTRO(String FECHA_REGISTRO) {
        this.FECHA_REGISTRO = FECHA_REGISTRO;
    }

    public String getFECHA_MODIFICACION() {
        return FECHA_MODIFICACION;
    }

    public void setFECHA_MODIFICACION(String FECHA_MODIFICACION) {
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public String getUSUARIO_REGISTRO() {
        return USUARIO_REGISTRO;
    }

    public void setUSUARIO_REGISTRO(String USUARIO_REGISTRO) {
        this.USUARIO_REGISTRO = USUARIO_REGISTRO;
    }

    public String getUSUARIO_MODIFICACION() {
        return USUARIO_MODIFICACION;
    }

    public void setUSUARIO_MODIFICACION(String USUARIO_MODIFICACION) {
        this.USUARIO_MODIFICACION = USUARIO_MODIFICACION;
    }

    public String getPC_REGISTRO() {
        return PC_REGISTRO;
    }

    public void setPC_REGISTRO(String PC_REGISTRO) {
        this.PC_REGISTRO = PC_REGISTRO;
    }

    public String getPC_MODIFICACION() {
        return PC_MODIFICACION;
    }

    public void setPC_MODIFICACION(String PC_MODIFICACION) {
        this.PC_MODIFICACION = PC_MODIFICACION;
    }

    public String getIP_REGISTRO() {
        return IP_REGISTRO;
    }

    public void setIP_REGISTRO(String IP_REGISTRO) {
        this.IP_REGISTRO = IP_REGISTRO;
    }

    public String getIP_MODIFICACION() {
        return IP_MODIFICACION;
    }

    public void setIP_MODIFICACION(String IP_MODIFICACION) {
        this.IP_MODIFICACION = IP_MODIFICACION;
    }

    public Integer getCLIENTE_ESPECIAL() {
        return CLIENTE_ESPECIAL;
    }

    public void setCLIENTE_ESPECIAL(Integer CLIENTE_ESPECIAL) {
        this.CLIENTE_ESPECIAL = CLIENTE_ESPECIAL;
    }

    public Integer getID_COBRADOR() {
        return ID_COBRADOR;
    }

    public void setID_COBRADOR(Integer ID_COBRADOR) {
        this.ID_COBRADOR = ID_COBRADOR;
    }

    public Integer getID_COBRADOR_EXT() {
        return ID_COBRADOR_EXT;
    }

    public void setID_COBRADOR_EXT(Integer ID_COBRADOR_EXT) {
        this.ID_COBRADOR_EXT = ID_COBRADOR_EXT;
    }

    public Integer getCUOTA_SEMANAL() {
        return CUOTA_SEMANAL;
    }

    public void setCUOTA_SEMANAL(Integer CUOTA_SEMANAL) {
        this.CUOTA_SEMANAL = CUOTA_SEMANAL;
    }

    public String getCOD_ALM_CONSIG() {
        return COD_ALM_CONSIG;
    }

    public void setCOD_ALM_CONSIG(String COD_ALM_CONSIG) {
        this.COD_ALM_CONSIG = COD_ALM_CONSIG;
    }

    public Integer getFLAG_FE() {
        return FLAG_FE;
    }

    public void setFLAG_FE(Integer FLAG_FE) {
        this.FLAG_FE = FLAG_FE;
    }

    public Double getLISTA_PRECIO() {
        return LISTA_PRECIO;
    }

    public void setLISTA_PRECIO(Double LISTA_PRECIO) {
        this.LISTA_PRECIO = LISTA_PRECIO;
    }
}
