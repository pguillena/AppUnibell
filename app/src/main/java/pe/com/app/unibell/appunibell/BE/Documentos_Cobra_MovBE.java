package pe.com.app.unibell.appunibell.BE;

public class Documentos_Cobra_MovBE {

    private Integer ID_DOCUMENTO_MOVIMIENTO;
    private String SERIE_PLANILLA;
    private String N_PLANILLA;
    private Integer ID_USUARIO_REGISTRO;
    private Integer ID_ROL_USUARIO_REGISTRO ;
    private String FECHA_RECEPCION ;
    private Integer ID_USUARIO_DERIVAR ;
    private Integer ID_ROL_USUARIO_DERIVAR ;
    private String FECHA_DERIVAR ;
    private String FECHA_MOVIMIENTO ;
    private String FECHA_ACCION ;
    private Integer ESTADO_MOVIMIENTO ;
    private Integer ID_EMPRESA ;
    private Integer ID_LOCAL;
    private Integer GUARDADO;
    private Integer SINCRONIZADO;
    private String MSG;

    private String ORDEN;
    private String ID_COBRANZA;
    private String COD_CLIENTE;
    private String N_RECIBO;
    private String N_SERIE_RECIBO;
    private String FPAGO;
    private String ID_COBRADOR;
    private String FECHA;
    private String M_COBRANZA;
    private String M_COBRANZA_D;
    private String SALDO;
    private String NUMCHEQ;
    private String FECCHEQ;
    private String ID_BANCO;
    private String CTACORRIENTE_BANCO;
    private String NRO_OPERACION;
    private String FECHA_DEPOSITO;
    private String COMENTARIO;
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
    private String C_PACKING;
    private String ID_MOV_BANCO;
    private String ESTADO_PROCESO;
    private String T_CAMBIO_TIENDA;
    private String N_TARJETA;
    private String ID_MOV_BANCO_ABONO;
    private String NOMBRECLIENTE;
    private String NOMBRECUENTACORRIENTE;
    private String NOMBREBANCO;
    private String NOMBREFORMAPAGO;
    private String NOMBREESTADO;
    private String NOMBRECOBRADOR;

    //FLUJO1
    private String NOM_ROL_ORIGEN;
    private String NOM_ROL_DESTINO;
    private String NOM_ESTADO_MOVIMIENTO;
    private String NOM_PERSONA_ORIGEN;
    private String NOM_PERSONA_DESTINO;

    //FLUJO2
    private String NOMBRE_ROL;
    private String NOMBRE_USUARIO;
    //FLUJO3
    private Integer ID_AUDITORIA;
    private String TIPO_DOC;
    private Integer ID_DOC;
    private String SERIE_DOC;
    private String NRO_DOC;
    private String ACCION;
    private String OBSERVACION;
    private String NOMBREACCION;

    public Integer getID_DOCUMENTO_MOVIMIENTO() {
        return ID_DOCUMENTO_MOVIMIENTO;
    }

    public void setID_DOCUMENTO_MOVIMIENTO(Integer ID_DOCUMENTO_MOVIMIENTO) {
        this.ID_DOCUMENTO_MOVIMIENTO = ID_DOCUMENTO_MOVIMIENTO;
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

    public Integer getID_USUARIO_REGISTRO() {
        return ID_USUARIO_REGISTRO;
    }

    public void setID_USUARIO_REGISTRO(Integer ID_USUARIO_REGISTRO) {
        this.ID_USUARIO_REGISTRO = ID_USUARIO_REGISTRO;
    }

    public Integer getID_ROL_USUARIO_REGISTRO() {
        return ID_ROL_USUARIO_REGISTRO;
    }

    public void setID_ROL_USUARIO_REGISTRO(Integer ID_ROL_USUARIO_REGISTRO) {
        this.ID_ROL_USUARIO_REGISTRO = ID_ROL_USUARIO_REGISTRO;
    }

    public String getFECHA_RECEPCION() {
        return FECHA_RECEPCION;
    }

    public void setFECHA_RECEPCION(String FECHA_RECEPCION) {
        this.FECHA_RECEPCION = FECHA_RECEPCION;
    }

    public Integer getID_USUARIO_DERIVAR() {
        return ID_USUARIO_DERIVAR;
    }

    public void setID_USUARIO_DERIVAR(Integer ID_USUARIO_DERIVAR) {
        this.ID_USUARIO_DERIVAR = ID_USUARIO_DERIVAR;
    }

    public Integer getID_ROL_USUARIO_DERIVAR() {
        return ID_ROL_USUARIO_DERIVAR;
    }

    public void setID_ROL_USUARIO_DERIVAR(Integer ID_ROL_USUARIO_DERIVAR) {
        this.ID_ROL_USUARIO_DERIVAR = ID_ROL_USUARIO_DERIVAR;
    }

    public String getFECHA_DERIVAR() {
        return FECHA_DERIVAR;
    }

    public void setFECHA_DERIVAR(String FECHA_DERIVAR) {
        this.FECHA_DERIVAR = FECHA_DERIVAR;
    }

    public String getFECHA_MOVIMIENTO() {
        return FECHA_MOVIMIENTO;
    }

    public void setFECHA_MOVIMIENTO(String FECHA_MOVIMIENTO) {
        this.FECHA_MOVIMIENTO = FECHA_MOVIMIENTO;
    }

    public String getFECHA_ACCION() {
        return FECHA_ACCION;
    }

    public void setFECHA_ACCION(String FECHA_ACCION) {
        this.FECHA_ACCION = FECHA_ACCION;
    }

    public Integer getESTADO_MOVIMIENTO() {
        return ESTADO_MOVIMIENTO;
    }

    public void setESTADO_MOVIMIENTO(Integer ESTADO_MOVIMIENTO) {
        this.ESTADO_MOVIMIENTO = ESTADO_MOVIMIENTO;
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

    public String getORDEN() {
        return ORDEN;
    }

    public void setORDEN(String ORDEN) {
        this.ORDEN = ORDEN;
    }

    public String getID_COBRANZA() {
        return ID_COBRANZA;
    }

    public void setID_COBRANZA(String ID_COBRANZA) {
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

    public String getID_COBRADOR() {
        return ID_COBRADOR;
    }

    public void setID_COBRADOR(String ID_COBRADOR) {
        this.ID_COBRADOR = ID_COBRADOR;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public String getM_COBRANZA() {
        return M_COBRANZA;
    }

    public void setM_COBRANZA(String m_COBRANZA) {
        M_COBRANZA = m_COBRANZA;
    }

    public String getM_COBRANZA_D() {
        return M_COBRANZA_D;
    }

    public void setM_COBRANZA_D(String m_COBRANZA_D) {
        M_COBRANZA_D = m_COBRANZA_D;
    }

    public String getSALDO() {
        return SALDO;
    }

    public void setSALDO(String SALDO) {
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

    public String getID_BANCO() {
        return ID_BANCO;
    }

    public void setID_BANCO(String ID_BANCO) {
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

    public String getID_MOV_BANCO_ABONO() {
        return ID_MOV_BANCO_ABONO;
    }

    public void setID_MOV_BANCO_ABONO(String ID_MOV_BANCO_ABONO) {
        this.ID_MOV_BANCO_ABONO = ID_MOV_BANCO_ABONO;
    }

    public String getNOMBRECLIENTE() {
        return NOMBRECLIENTE;
    }

    public void setNOMBRECLIENTE(String NOMBRECLIENTE) {
        this.NOMBRECLIENTE = NOMBRECLIENTE;
    }

    public String getNOMBRECUENTACORRIENTE() {
        return NOMBRECUENTACORRIENTE;
    }

    public void setNOMBRECUENTACORRIENTE(String NOMBRECUENTACORRIENTE) {
        this.NOMBRECUENTACORRIENTE = NOMBRECUENTACORRIENTE;
    }

    public String getNOMBREBANCO() {
        return NOMBREBANCO;
    }

    public void setNOMBREBANCO(String NOMBREBANCO) {
        this.NOMBREBANCO = NOMBREBANCO;
    }

    public String getNOMBREFORMAPAGO() {
        return NOMBREFORMAPAGO;
    }

    public void setNOMBREFORMAPAGO(String NOMBREFORMAPAGO) {
        this.NOMBREFORMAPAGO = NOMBREFORMAPAGO;
    }

    public String getNOMBREESTADO() {
        return NOMBREESTADO;
    }

    public void setNOMBREESTADO(String NOMBREESTADO) {
        this.NOMBREESTADO = NOMBREESTADO;
    }

    public String getNOMBRECOBRADOR() {
        return NOMBRECOBRADOR;
    }

    public void setNOMBRECOBRADOR(String NOMBRECOBRADOR) {
        this.NOMBRECOBRADOR = NOMBRECOBRADOR;
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

    public String getNOM_ROL_ORIGEN() {
        return NOM_ROL_ORIGEN;
    }

    public void setNOM_ROL_ORIGEN(String NOM_ROL_ORIGEN) {
        this.NOM_ROL_ORIGEN = NOM_ROL_ORIGEN;
    }

    public String getNOM_ROL_DESTINO() {
        return NOM_ROL_DESTINO;
    }

    public void setNOM_ROL_DESTINO(String NOM_ROL_DESTINO) {
        this.NOM_ROL_DESTINO = NOM_ROL_DESTINO;
    }

    public String getNOM_ESTADO_MOVIMIENTO() {
        return NOM_ESTADO_MOVIMIENTO;
    }

    public void setNOM_ESTADO_MOVIMIENTO(String NOM_ESTADO_MOVIMIENTO) {
        this.NOM_ESTADO_MOVIMIENTO = NOM_ESTADO_MOVIMIENTO;
    }

    public String getNOM_PERSONA_ORIGEN() {
        return NOM_PERSONA_ORIGEN;
    }

    public void setNOM_PERSONA_ORIGEN(String NOM_PERSONA_ORIGEN) {
        this.NOM_PERSONA_ORIGEN = NOM_PERSONA_ORIGEN;
    }

    public String getNOM_PERSONA_DESTINO() {
        return NOM_PERSONA_DESTINO;
    }

    public void setNOM_PERSONA_DESTINO(String NOM_PERSONA_DESTINO) {
        this.NOM_PERSONA_DESTINO = NOM_PERSONA_DESTINO;
    }

    public String getNOMBRE_ROL() {
        return NOMBRE_ROL;
    }

    public void setNOMBRE_ROL(String NOMBRE_ROL) {
        this.NOMBRE_ROL = NOMBRE_ROL;
    }

    public String getNOMBRE_USUARIO() {
        return NOMBRE_USUARIO;
    }

    public void setNOMBRE_USUARIO(String NOMBRE_USUARIO) {
        this.NOMBRE_USUARIO = NOMBRE_USUARIO;
    }

    public Integer getID_AUDITORIA() {
        return ID_AUDITORIA;
    }

    public void setID_AUDITORIA(Integer ID_AUDITORIA) {
        this.ID_AUDITORIA = ID_AUDITORIA;
    }

    public String getTIPO_DOC() {
        return TIPO_DOC;
    }

    public void setTIPO_DOC(String TIPO_DOC) {
        this.TIPO_DOC = TIPO_DOC;
    }

    public Integer getID_DOC() {
        return ID_DOC;
    }

    public void setID_DOC(Integer ID_DOC) {
        this.ID_DOC = ID_DOC;
    }

    public String getSERIE_DOC() {
        return SERIE_DOC;
    }

    public void setSERIE_DOC(String SERIE_DOC) {
        this.SERIE_DOC = SERIE_DOC;
    }

    public String getNRO_DOC() {
        return NRO_DOC;
    }

    public void setNRO_DOC(String NRO_DOC) {
        this.NRO_DOC = NRO_DOC;
    }

    public String getACCION() {
        return ACCION;
    }

    public void setACCION(String ACCION) {
        this.ACCION = ACCION;
    }

    public String getOBSERVACION() {
        return OBSERVACION;
    }

    public void setOBSERVACION(String OBSERVACION) {
        this.OBSERVACION = OBSERVACION;
    }

    public String getNOMBREACCION() {
        return NOMBREACCION;
    }

    public void setNOMBREACCION(String NOMBREACCION) {
        this.NOMBREACCION = NOMBREACCION;
    }



    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }
}
