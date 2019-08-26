package pe.com.app.unibell.appunibell.BE;

import java.io.Serializable;

import pe.com.app.unibell.appunibell.Util.Funciones;

public class Documentos_Cobra_CabBE implements Serializable {
    private Integer ID_COBRANZA;
    private String COD_CLIENTE;
    private String N_RECIBO;
    private String N_SERIE_RECIBO;
    private String FPAGO;
    private Integer ID_COBRADOR;
    private String FECHA;
    private Double M_COBRANZA;
    private Double M_COBRANZA_D;
    private Double SALDO;
    private Double SALDO_INICIAL;

    private String NUMCHEQ;
    private String FECCHEQ;
    private Integer ID_BANCO;
    private String CTACORRIENTE_BANCO;
    private String NRO_OPERACION;
    private String FECHA_DEPOSITO;
    private String COMENTARIO;
    private Integer ID_EMPRESA;
    private Integer ID_LOCAL;
    private String ESTADO;
    private String FECHA_REGISTRO;
    private String FECHA_MODIFICACION;
    private String USUARIO_REGISTRO;
    private String USUARIO_MODIFICACION;
    private String PC_REGISTRO;
    private String PC_MODIFICACION;
    private String IP_REGISTRO;
    private String IP_MODIFICACION;
    private String ITEM;
    private String ESTADO_CONCILIADO;
    private String SERIE_PLANILLA;
    private String N_PLANILLA;
    private String C_PACKING;
    private String ID_MOV_BANCO;
    private String ESTADO_PROCESO;
    private String T_CAMBIO_TIENDA;
    private String N_TARJETA;
    private Integer ID_MOV_BANCO_ABONO;
    private String FECHA_DEPOSITO_ABONO;
    private String LOTE;
    private String FLAG_COBRANZA;
    private Integer CODUNC_LOCAL;
    private Boolean CHKMARCADO;

    private Integer GUARDADO;
    private Integer SINCRONIZADO;

    //PLANILLA
    private String FECHA_PLANILLA;
    private String VOUCHER;
    private String RUC;
    private String CODIGO_FPAGO;
    private String FECHA_RECIBO;
    private String RAZON_SOCIAL;
    private String TIPODOC;
    private String NUMERO;
    private String ENTIDAD;
    private String CONSTANCIA;
    private String MONEDA;
    private String RECIBO;
    private String COBRADOR;
    private String VENDEDOR;
    private String PLANILLA;
    private String FPAGODESC;
    private String BANCODESC;
    private String ESTADODESC;
    private String NOMCTACORRIENTE;
    private String NOMCOBRADOR;
    private String ID_DOCUMENTO_MOVIMIENTO;
    private String MSG;
    private String FECHA_ABONO	;

    //RECIBO
    private String DIRECCION	;
    private String TELEFONO	;
    private String EMPRESA	;
    private String DISTRITO	;
    private String DEPARTAMENTO;

    public Integer getID_COBRANZA() {
        return ID_COBRANZA;
    }

    public void setID_COBRANZA(Integer ID_COBRANZA) {
        this.ID_COBRANZA = ID_COBRANZA;
    }

    public String getCOD_CLIENTE() {
        return COD_CLIENTE;
    }

    public void setCOD_CLIENTE(String COD_CLIENTE) {
        this.COD_CLIENTE = COD_CLIENTE;
    }

    public String getN_RECIBO() {
        return N_RECIBO;
    }

    public void setN_RECIBO(String n_RECIBO) {
        N_RECIBO = n_RECIBO;
    }

    public String getN_SERIE_RECIBO() {
        return N_SERIE_RECIBO;
    }

    public void setN_SERIE_RECIBO(String n_SERIE_RECIBO) {
        N_SERIE_RECIBO = n_SERIE_RECIBO;
    }

    public String getFPAGO() {
        return FPAGO;
    }

    public void setFPAGO(String FPAGO) {
        this.FPAGO = FPAGO;
    }

    public Integer getID_COBRADOR() {
        return ID_COBRADOR;
    }

    public void setID_COBRADOR(Integer ID_COBRADOR) {
        this.ID_COBRADOR = ID_COBRADOR;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public Double getM_COBRANZA() {
        return M_COBRANZA;
    }

    public void setM_COBRANZA(Double m_COBRANZA) {
        M_COBRANZA = m_COBRANZA;
    }

    public Double getM_COBRANZA_D() {
        return M_COBRANZA_D;
    }

    public void setM_COBRANZA_D(Double m_COBRANZA_D) {
        M_COBRANZA_D = m_COBRANZA_D;
    }

    public Double getSALDO() {
        return SALDO;
    }

    public void setSALDO(Double SALDO) {
        this.SALDO = SALDO;
    }

    public String getNUMCHEQ() {
        return NUMCHEQ;
    }

    public void setNUMCHEQ(String NUMCHEQ) {
        this.NUMCHEQ = NUMCHEQ;
    }

    public String getFECCHEQ() {
        return FECCHEQ;
    }

    public void setFECCHEQ(String FECCHEQ) {
        this.FECCHEQ = FECCHEQ;
    }

    public Integer getID_BANCO() {
        return ID_BANCO;
    }

    public void setID_BANCO(Integer ID_BANCO) {
        this.ID_BANCO = ID_BANCO;
    }

    public String getCTACORRIENTE_BANCO() {
        return CTACORRIENTE_BANCO;
    }

    public void setCTACORRIENTE_BANCO(String CTACORRIENTE_BANCO) {
        this.CTACORRIENTE_BANCO = CTACORRIENTE_BANCO;
    }

    public String getNRO_OPERACION() {
        return NRO_OPERACION;
    }

    public void setNRO_OPERACION(String NRO_OPERACION) {
        this.NRO_OPERACION = NRO_OPERACION;
    }

    public String getFECHA_DEPOSITO() {
        return FECHA_DEPOSITO;
    }

    public void setFECHA_DEPOSITO(String FECHA_DEPOSITO) {
        this.FECHA_DEPOSITO = FECHA_DEPOSITO;
    }

    public String getCOMENTARIO() {
        return COMENTARIO;
    }

    public void setCOMENTARIO(String COMENTARIO) {
        this.COMENTARIO = COMENTARIO;
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

    public String getESTADO() {
        return ESTADO;
    }

    public void setESTADO(String ESTADO) {
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

    public String getITEM() {
        return ITEM;
    }

    public void setITEM(String ITEM) {
        this.ITEM = ITEM;
    }

    public String getESTADO_CONCILIADO() {
        return ESTADO_CONCILIADO;
    }

    public void setESTADO_CONCILIADO(String ESTADO_CONCILIADO) {
        this.ESTADO_CONCILIADO = ESTADO_CONCILIADO;
    }

    public String getSERIE_PLANILLA() {
        return SERIE_PLANILLA;
    }

    public void setSERIE_PLANILLA(String SERIE_PLANILLA) {
        this.SERIE_PLANILLA = SERIE_PLANILLA;
    }

    public String getN_PLANILLA() {
        return N_PLANILLA;
    }

    public void setN_PLANILLA(String n_PLANILLA) {
        N_PLANILLA = n_PLANILLA;
    }

    public String getC_PACKING() {
        return C_PACKING;
    }

    public void setC_PACKING(String c_PACKING) {
        C_PACKING = c_PACKING;
    }

    public String getID_MOV_BANCO() {
        return ID_MOV_BANCO;
    }

    public void setID_MOV_BANCO(String ID_MOV_BANCO) {
        this.ID_MOV_BANCO = ID_MOV_BANCO;
    }

    public String getESTADO_PROCESO() {
        return ESTADO_PROCESO;
    }

    public void setESTADO_PROCESO(String ESTADO_PROCESO) {
        this.ESTADO_PROCESO = ESTADO_PROCESO;
    }

    public String getT_CAMBIO_TIENDA() {
        return T_CAMBIO_TIENDA;
    }

    public void setT_CAMBIO_TIENDA(String t_CAMBIO_TIENDA) {
        T_CAMBIO_TIENDA = t_CAMBIO_TIENDA;
    }

    public String getN_TARJETA() {
        return N_TARJETA;
    }

    public void setN_TARJETA(String n_TARJETA) {
        N_TARJETA = n_TARJETA;
    }

    public Integer getID_MOV_BANCO_ABONO() {
        return ID_MOV_BANCO_ABONO;
    }

    public void setID_MOV_BANCO_ABONO(Integer ID_MOV_BANCO_ABONO) {
        this.ID_MOV_BANCO_ABONO = ID_MOV_BANCO_ABONO;
    }

    public String getFECHA_DEPOSITO_ABONO() {
        return FECHA_DEPOSITO_ABONO;
    }

    public void setFECHA_DEPOSITO_ABONO(String FECHA_DEPOSITO_ABONO) {
        this.FECHA_DEPOSITO_ABONO = FECHA_DEPOSITO_ABONO;
    }

    public String getLOTE() {
        return LOTE;
    }

    public void setLOTE(String LOTE) {
        this.LOTE = LOTE;
    }

    public String getFLAG_COBRANZA() {
        return FLAG_COBRANZA;
    }

    public void setFLAG_COBRANZA(String FLAG_COBRANZA) {
        this.FLAG_COBRANZA = FLAG_COBRANZA;
    }

    public Integer getCODUNC_LOCAL() {
        return CODUNC_LOCAL;
    }

    public void setCODUNC_LOCAL(Integer CODUNC_LOCAL) {
        this.CODUNC_LOCAL = CODUNC_LOCAL;
    }

    public Boolean getCHKMARCADO() {
        return CHKMARCADO;
    }

    public void setCHKMARCADO(Boolean CHKMARCADO) {
        this.CHKMARCADO = CHKMARCADO;
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

    public String getFECHA_PLANILLA() {
        return FECHA_PLANILLA;
    }

    public void setFECHA_PLANILLA(String FECHA_PLANILLA) {
        this.FECHA_PLANILLA = FECHA_PLANILLA;
    }

    public String getVOUCHER() {
        return VOUCHER;
    }

    public void setVOUCHER(String VOUCHER) {
        this.VOUCHER = VOUCHER;
    }

    public String getRUC() {
        return RUC;
    }

    public void setRUC(String RUC) {
        this.RUC = RUC;
    }

    public String getCODIGO_FPAGO() {
        return CODIGO_FPAGO;
    }

    public void setCODIGO_FPAGO(String CODIGO_FPAGO) {
        this.CODIGO_FPAGO = CODIGO_FPAGO;
    }

    public String getFECHA_RECIBO() {
        return FECHA_RECIBO;
    }

    public void setFECHA_RECIBO(String FECHA_RECIBO) {
        this.FECHA_RECIBO = FECHA_RECIBO;
    }

    public String getRAZON_SOCIAL() {
        return RAZON_SOCIAL;
    }

    public void setRAZON_SOCIAL(String RAZON_SOCIAL) {
        this.RAZON_SOCIAL = RAZON_SOCIAL;
    }

    public String getTIPODOC() {
        return TIPODOC;
    }

    public void setTIPODOC(String TIPODOC) {
        this.TIPODOC = TIPODOC;
    }

    public String getNUMERO() {
        return NUMERO;
    }

    public void setNUMERO(String NUMERO) {
        this.NUMERO = NUMERO;
    }

    public String getENTIDAD() {
        return ENTIDAD;
    }

    public void setENTIDAD(String ENTIDAD) {
        this.ENTIDAD = ENTIDAD;
    }

    public String getCONSTANCIA() {
        return CONSTANCIA;
    }

    public void setCONSTANCIA(String CONSTANCIA) {
        this.CONSTANCIA = CONSTANCIA;
    }

    public String getMONEDA() {
        return MONEDA;
    }

    public void setMONEDA(String MONEDA) {
        this.MONEDA = MONEDA;
    }

    public String getRECIBO() {
        return RECIBO;
    }

    public void setRECIBO(String RECIBO) {
        this.RECIBO = RECIBO;
    }

    public String getCOBRADOR() {
        return COBRADOR;
    }

    public void setCOBRADOR(String COBRADOR) {
        this.COBRADOR = COBRADOR;
    }

    public String getPLANILLA() {
        return PLANILLA;
    }

    public void setPLANILLA(String PLANILLA) {
        this.PLANILLA = PLANILLA;
    }

    public String getFPAGODESC() {
        return FPAGODESC;
    }

    public void setFPAGODESC(String FPAGODESC) {
        this.FPAGODESC = FPAGODESC;
    }

    public String getBANCODESC() {
        return BANCODESC;
    }

    public void setBANCODESC(String BANCODESC) {
        this.BANCODESC = BANCODESC;
    }

    public String getESTADODESC() {
        return ESTADODESC;
    }

    public void setESTADODESC(String ESTADODESC) {
        this.ESTADODESC = ESTADODESC;
    }

    public String getNOMCTACORRIENTE() {
        return NOMCTACORRIENTE;
    }

    public void setNOMCTACORRIENTE(String NOMCTACORRIENTE) {
        this.NOMCTACORRIENTE = NOMCTACORRIENTE;
    }

    public String getNOMCOBRADOR() {
        return NOMCOBRADOR;
    }

    public void setNOMCOBRADOR(String NOMCOBRADOR) {
        this.NOMCOBRADOR = NOMCOBRADOR;
    }

    public String getID_DOCUMENTO_MOVIMIENTO() {
        return ID_DOCUMENTO_MOVIMIENTO;
    }

    public void setID_DOCUMENTO_MOVIMIENTO(String ID_DOCUMENTO_MOVIMIENTO) {
        this.ID_DOCUMENTO_MOVIMIENTO = ID_DOCUMENTO_MOVIMIENTO;
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public String getFECHA_ABONO() {
        return FECHA_ABONO;
    }

    public void setFECHA_ABONO(String FECHA_ABONO) {
        this.FECHA_ABONO = FECHA_ABONO;
    }

    public Double getSALDO_INICIAL() {
        return SALDO_INICIAL;
    }

    public void setSALDO_INICIAL(Double SALDO_INICIAL) {
        this.SALDO_INICIAL = SALDO_INICIAL;
    }

    public String getVENDEDOR() {
        return VENDEDOR;
    }

    public void setVENDEDOR(String VENDEDOR) {
        this.VENDEDOR = VENDEDOR;
    }

    public String getDIRECCION() {
        return DIRECCION;
    }

    public void setDIRECCION(String DIRECCION) {
        this.DIRECCION = DIRECCION;
    }

    public String getTELEFONO() {
        return TELEFONO;
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }

    public String getEMPRESA() {
        return EMPRESA;
    }

    public void setEMPRESA(String EMPRESA) {
        this.EMPRESA = EMPRESA;
    }

    public String getDISTRITO() {
        return DISTRITO;
    }

    public void setDISTRITO(String DISTRITO) {
        this.DISTRITO = DISTRITO;
    }

    public String getDEPARTAMENTO() {
        return DEPARTAMENTO;
    }

    public void setDEPARTAMENTO(String DEPARTAMENTO) {
        this.DEPARTAMENTO = DEPARTAMENTO;
    }
}
