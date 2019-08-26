package pe.com.app.unibell.appunibell.BE;

public class S_Sem_UsuarioBE {

    private Integer  ID_PERSONA ;
    private String CREDENCIAL;
    private String CLAVE;
    private Number ESTADO;
    private String RESETEADO;
    private String FECHA_REGISTRO;
    private String FECHA_MODIFICACION;
    private String USUARIO_REGISTRO;
    private String USUARIO_MODIFICACION;
    private String PC_REGISTRO;
    private String PC_MODIFICACION;
    private String IP_REGISTRO;
    private String IP_MODIFICACION;
    private String NOMBRE_CORTO;
    private String PC_USUARIO;

    private String ID_EMPRESA;
    private String ID_LOCAL;
    private String C_PERFIL;
    private String ROL;
    private Integer VALIDA_RECIBO;
    private String NOMBRES;

    public void setNOMBRES(String NOMBRES) {
        this.NOMBRES = NOMBRES;
    }

    public void setAPELLIDO_PATERNO(String APELLIDO_PATERNO) {
        this.APELLIDO_PATERNO = APELLIDO_PATERNO;
    }

    public void setAPELLIDO_MATERNO(String APELLIDO_MATERNO) {
        this.APELLIDO_MATERNO = APELLIDO_MATERNO;
    }

    private String APELLIDO_PATERNO;
    private String APELLIDO_MATERNO;


    public String getNOMBRES() {
        return NOMBRES;
    }

    public String getAPELLIDO_PATERNO() {
        return APELLIDO_PATERNO;
    }

    public String getAPELLIDO_MATERNO() {
        return APELLIDO_MATERNO;
    }




    public Integer getID_PERSONA() {
        return ID_PERSONA;
    }

    public void setID_PERSONA(Integer ID_PERSONA) {
        this.ID_PERSONA = ID_PERSONA;
    }

    public String getCREDENCIAL() {
        return CREDENCIAL;
    }

    public void setCREDENCIAL(String CREDENCIAL) {
        this.CREDENCIAL = CREDENCIAL;
    }

    public String getCLAVE() {
        return CLAVE;
    }

    public void setCLAVE(String CLAVE) {
        this.CLAVE = CLAVE;
    }

    public Number getESTADO() {
        return ESTADO;
    }

    public void setESTADO(Number ESTADO) {
        this.ESTADO = ESTADO;
    }

    public String getRESETEADO() {
        return RESETEADO;
    }

    public void setRESETEADO(String RESETEADO) {
        this.RESETEADO = RESETEADO;
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

    public String getNOMBRE_CORTO() {
        return NOMBRE_CORTO;
    }

    public void setNOMBRE_CORTO(String NOMBRE_CORTO) {
        this.NOMBRE_CORTO = NOMBRE_CORTO;
    }

    public String getPC_USUARIO() {
        return PC_USUARIO;
    }

    public void setPC_USUARIO(String PC_USUARIO) {
        this.PC_USUARIO = PC_USUARIO;
    }

    public String getID_EMPRESA() {
        return ID_EMPRESA;
    }

    public void setID_EMPRESA(String ID_EMPRESA) {
        this.ID_EMPRESA = ID_EMPRESA;
    }

    public String getID_LOCAL() {
        return ID_LOCAL;
    }

    public void setID_LOCAL(String ID_LOCAL) {
        this.ID_LOCAL = ID_LOCAL;
    }

    public String getC_PERFIL() {
        return C_PERFIL;
    }

    public void setC_PERFIL(String c_PERFIL) {
        C_PERFIL = c_PERFIL;
    }

    public String getROL() {
        return ROL;
    }

    public void setROL(String ROL) {
        this.ROL = ROL;
    }

    public Integer getVALIDA_RECIBO() {
        return VALIDA_RECIBO;
    }

    public void setVALIDA_RECIBO(Integer VALIDA_RECIBO) {
        this.VALIDA_RECIBO = VALIDA_RECIBO;
    }
}
