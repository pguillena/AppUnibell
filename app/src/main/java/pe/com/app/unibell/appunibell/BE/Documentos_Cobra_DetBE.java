package pe.com.app.unibell.appunibell.BE;

import pe.com.app.unibell.appunibell.Util.Funciones;

public class Documentos_Cobra_DetBE {

    private Integer ID_COBRANZA;
    private String FPAGO;
    private String TIPDOC;
    private String SERIE_NUM;
    private String NUMERO;
    private Double IMPORTE;
    private String MONEDA;
    private Double SALDO;
    private Double M_COBRANZA;
    private Number ID_EMPRESA;
    private Number ID_LOCAL;
    private Number ESTADO;
    private String FECHA_REGISTRO;
    private String FECHA_MODIFICACION;
    private String USUARIO_REGISTRO;
    private String USUARIO_MODIFICACION;
    private String PC_REGISTRO;
    private String PC_MODIFICACION;
    private String IP_REGISTRO;
    private String IP_MODIFICACION;
    private Integer ID_VENDEDOR;
    private Double SALDO_INICIAL;
    private String VOUCHER;
    private Integer CODUNC_LOCAL;
    private String NOMBRETIPODOC;
    private String FECHA;
    private String F_VENCTO;
    private Integer DIAS;

    private Integer GUARDADO;
    private Integer SINCRONIZADO;

    private String N_SERIE_RECIBO;
    private String N_RECIBO;

    public void setN_SERIE_RECIBO(String n_SERIE_RECIBO) {
        N_SERIE_RECIBO = n_SERIE_RECIBO;
    }

    public void setN_RECIBO(String n_RECIBO) {
        N_RECIBO = n_RECIBO;
    }

    public String getN_SERIE_RECIBO() {
        return N_SERIE_RECIBO;
    }

    public String getN_RECIBO() {
        return N_RECIBO;
    }

    public Integer getID_COBRANZA() {
        return ID_COBRANZA;
    }

    public void setID_COBRANZA(Integer ID_COBRANZA) {
        this.ID_COBRANZA = ID_COBRANZA;
    }

    public String getFPAGO() {
        return FPAGO;
    }

    public void setFPAGO(String FPAGO) {
        this.FPAGO = FPAGO;
    }

    public String getTIPDOC() {
        return TIPDOC;
    }

    public void setTIPDOC(String TIPDOC) {
        this.TIPDOC = TIPDOC;
    }

    public String getSERIE_NUM() {
        return SERIE_NUM;
    }

    public void setSERIE_NUM(String SERIE_NUM) {
        this.SERIE_NUM = SERIE_NUM;
    }

    public String getNUMERO() {
        return NUMERO;
    }

    public void setNUMERO(String NUMERO) {
        this.NUMERO = NUMERO;
    }

    public Double getIMPORTE() {
        return IMPORTE;
    }

    public void setIMPORTE(Double IMPORTE) {
        this.IMPORTE = IMPORTE;
    }

    public String getMONEDA() {
        return MONEDA;
    }

    public void setMONEDA(String MONEDA) {
        this.MONEDA = MONEDA;
    }

    public Double getSALDO() {
        return SALDO;
    }

    public void setSALDO(Double SALDO) {
        this.SALDO = SALDO;
    }

    public Double getM_COBRANZA() {
        return M_COBRANZA;
    }

    public void setM_COBRANZA(Double m_COBRANZA) {
        M_COBRANZA = m_COBRANZA;
    }

    public Number getID_EMPRESA() {
        return ID_EMPRESA;
    }

    public void setID_EMPRESA(Number ID_EMPRESA) {
        this.ID_EMPRESA = ID_EMPRESA;
    }

    public Number getID_LOCAL() {
        return ID_LOCAL;
    }

    public void setID_LOCAL(Number ID_LOCAL) {
        this.ID_LOCAL = ID_LOCAL;
    }

    public Number getESTADO() {
        return ESTADO;
    }

    public void setESTADO(Number ESTADO) {
        this.ESTADO = ESTADO;
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

    public Integer getID_VENDEDOR() {
        return ID_VENDEDOR;
    }

    public void setID_VENDEDOR(Integer ID_VENDEDOR) {
        this.ID_VENDEDOR = ID_VENDEDOR;
    }

    public Double getSALDO_INICIAL() {
        return SALDO_INICIAL;
    }

    public void setSALDO_INICIAL(Double SALDO_INICIAL) {
        this.SALDO_INICIAL = SALDO_INICIAL;
    }

    public String getVOUCHER() {
        return VOUCHER;
    }

    public void setVOUCHER(String VOUCHER) {
        this.VOUCHER = VOUCHER;
    }

    public Integer getCODUNC_LOCAL() {
        return CODUNC_LOCAL;
    }

    public void setCODUNC_LOCAL(Integer CODUNC_LOCAL) {
        this.CODUNC_LOCAL = CODUNC_LOCAL;
    }

    public Integer getGUARDADO() {
        return GUARDADO;
    }

    public void setGUARDADO(Integer GUARDADO) {
        this.GUARDADO = GUARDADO;
    }

    public Integer getSINCRONIZADO() {
        return SINCRONIZADO;
    }

    public void setSINCRONIZADO(Integer SINCRONIZADO) {
        this.SINCRONIZADO = SINCRONIZADO;
    }

    public String getNOMBRETIPODOC() {
        return NOMBRETIPODOC;
    }

    public void setNOMBRETIPODOC(String NOMBRETIPODOC) {
        this.NOMBRETIPODOC = NOMBRETIPODOC;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public String getF_VENCTO() {
        return F_VENCTO;
    }

    public void setF_VENCTO(String f_VENCTO) {
        F_VENCTO = f_VENCTO;
    }

    public Integer getDIAS() {
        return DIAS;
    }

    public void setDIAS(Integer DIAS) {
        this.DIAS = DIAS;
    }
}
