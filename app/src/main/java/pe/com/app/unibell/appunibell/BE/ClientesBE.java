package pe.com.app.unibell.appunibell.BE;

public class ClientesBE {
    private String COD_CLIENTE;
    private String NOMBRE;
    private String DIRECCION;
    private String CIUDAD;
    private String DISTRITO;
    private String PAIS;
    private String ZONA;
    private String VENDEDOR;
    private String COBRADOR;
    private String GIRO;
    private String TELEFONO;
    private String FAX;
    private String FECHA_ING;
    private String COND_PAG;
    private String CALIFICACION;
    private String LIMITE_CRED;
    private Integer ESTADO;
    private String RUC;
    private Number DSCTO_1;
    private Number DSCTO_2;
    private String CTACONTS;
    private String CTACONTD;
    private String CLIENTE_AFECTO;
    private Number DSCTO_MAX;
    private String LISTA_PRECIO;
    private Number NIVEL_CP;
    private Number CRED_USADO;
    private String LETRA_PRT;
    private String CHEQ_DEV;
    private String GRUPO;
    private String DIREC_FACT;
    private String INTERES;
    private String COMI_PREDET;
    private String ALM_CONS;
    private String COD_UBC;
    private String DOCIDEN;
    private String RUC_NEW;
    private String UBIGEO;
    private Number DIA_VISITA;
    private Number ORD_VISITA;
    private String FRC_VISITA;
    private String ANIVERSARIO;
    private String R_CUMPLEANOS;
    private String L_TAMANO;
    private String L_DECORACION;
    private String L_TECNOLOGIA;
    private String NRO_EMPLEADOS;
    private String NRO_EXIBIDOR;
    private String NRO_VISITAS;
    private Number CANEMP;
    private String CEOCLIE;
    private String PROFESO;
    private String CARNET;
    private String REFERENCIA;
    private String COD_STROE;
    private String GIRO_CLI;
    private String R_SEXO;
    private String R_LUGNAC;
    private String R_ESTCIV;
    private String R_HOBBIE;
    private String R_GENERO;
    private String R_REVIST;
    private String R_PERIOD;
    private String RETENCION;
    private String CALIF_VTA;
    private String CALIF_CRED;
    private String PLAZO_PAGO;
    private String I_SITUACION;
    private String C_CANAL;
    private String C_CLIENTE;
    private String C_ETAPA;
    private String FLG_CONSIGNA_VTA;
    private String FLG_CONSIGNA_PROM;
    private String C_COBRADOR_EXT;
    private String C_DIA_VISITA_EXT;
    private String N_ORD_VISITA_EXT;
    private String C_FRC_VISITA_EXT;
    private String C_RUTA_DISTRIBUCION;
    private String E_MAIL;
    private Number N_ARCHIVO;
    private String C_CLIENTE_PAGO;
    private String C_NIVEL;
    private String I_CLIENTE_INT_EXT;
    private Number M_CUOTA_SEMANAL;
    private String C_FRC_COB;
    private String C_SUC_EMP;
    private String C_GRUPO_CANAL_VTA;
    private String C_FUERZA_VTA;
    private String I_DIR_FISCAL;
    private String C_REGION_VTA;
    private String C_COBRADOR;
    private String I_ORIGEN;
    private String I_DI;
    private String I_AGENTE_RET;
    private String I_AGENTE_PER;
    private String I_PERCEPCION;
    private String COD_DPT;
    private String COD_PVC;
    private String DIRECCION_VSTA;
    private String ID_NIVEL;
    private String I_RELACION;
    private String F_FACT_AUTOM;
    private String C_USUARIO;
    private String C_PERFIL;
    private String C_CPU;
    private String FEC_REG;
    private String I_REGISTRO_CASTIGO;
    private String  OBSERVACION;
    private String ID_LOCAL;
    private Integer ID_EMPRESA;
    //SELECT
    private String ID_CLIENTE;
    private String RAZON_SOCIAL;
    private String ID_CANAL;
    private String CODIGO_ANTIGUO;
    private String  M_PAE;
    private String I_CANC_ANTIGUO;

    public String getCOD_CLIENTE() {
        return COD_CLIENTE;
    }

    public void setCOD_CLIENTE(String COD_CLIENTE) {
        this.COD_CLIENTE = COD_CLIENTE;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getDIRECCION() {
        return DIRECCION;
    }

    public void setDIRECCION(String DIRECCION) {
        this.DIRECCION = DIRECCION;
    }

    public String getCIUDAD() {
        return CIUDAD;
    }

    public void setCIUDAD(String CIUDAD) {
        this.CIUDAD = CIUDAD;
    }

    public String getDISTRITO() {
        return DISTRITO;
    }

    public void setDISTRITO(String DISTRITO) {
        this.DISTRITO = DISTRITO;
    }

    public String getPAIS() {
        return PAIS;
    }

    public void setPAIS(String PAIS) {
        this.PAIS = PAIS;
    }

    public String getZONA() {
        return ZONA;
    }

    public void setZONA(String ZONA) {
        this.ZONA = ZONA;
    }

    public String getVENDEDOR() {
        return VENDEDOR;
    }

    public void setVENDEDOR(String VENDEDOR) {
        this.VENDEDOR = VENDEDOR;
    }

    public String getCOBRADOR() {
        return COBRADOR;
    }

    public void setCOBRADOR(String COBRADOR) {
        this.COBRADOR = COBRADOR;
    }

    public String getGIRO() {
        return GIRO;
    }

    public void setGIRO(String GIRO) {
        this.GIRO = GIRO;
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

    public String getFECHA_ING() {
        return FECHA_ING;
    }

    public void setFECHA_ING(String FECHA_ING) {
        this.FECHA_ING = FECHA_ING;
    }

    public String getCOND_PAG() {
        return COND_PAG;
    }

    public void setCOND_PAG(String COND_PAG) {
        this.COND_PAG = COND_PAG;
    }

    public String getCALIFICACION() {
        return CALIFICACION;
    }

    public void setCALIFICACION(String CALIFICACION) {
        this.CALIFICACION = CALIFICACION;
    }

    public String getLIMITE_CRED() {
        return LIMITE_CRED;
    }

    public void setLIMITE_CRED(String LIMITE_CRED) {
        this.LIMITE_CRED = LIMITE_CRED;
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

    public Number getDSCTO_1() {
        return DSCTO_1;
    }

    public void setDSCTO_1(Number DSCTO_1) {
        this.DSCTO_1 = DSCTO_1;
    }

    public Number getDSCTO_2() {
        return DSCTO_2;
    }

    public void setDSCTO_2(Number DSCTO_2) {
        this.DSCTO_2 = DSCTO_2;
    }

    public String getCTACONTS() {
        return CTACONTS;
    }

    public void setCTACONTS(String CTACONTS) {
        this.CTACONTS = CTACONTS;
    }

    public String getCTACONTD() {
        return CTACONTD;
    }

    public void setCTACONTD(String CTACONTD) {
        this.CTACONTD = CTACONTD;
    }

    public String getCLIENTE_AFECTO() {
        return CLIENTE_AFECTO;
    }

    public void setCLIENTE_AFECTO(String CLIENTE_AFECTO) {
        this.CLIENTE_AFECTO = CLIENTE_AFECTO;
    }

    public Number getDSCTO_MAX() {
        return DSCTO_MAX;
    }

    public void setDSCTO_MAX(Number DSCTO_MAX) {
        this.DSCTO_MAX = DSCTO_MAX;
    }

    public String getLISTA_PRECIO() {
        return LISTA_PRECIO;
    }

    public void setLISTA_PRECIO(String LISTA_PRECIO) {
        this.LISTA_PRECIO = LISTA_PRECIO;
    }

    public Number getNIVEL_CP() {
        return NIVEL_CP;
    }

    public void setNIVEL_CP(Number NIVEL_CP) {
        this.NIVEL_CP = NIVEL_CP;
    }

    public Number getCRED_USADO() {
        return CRED_USADO;
    }

    public void setCRED_USADO(Number CRED_USADO) {
        this.CRED_USADO = CRED_USADO;
    }

    public String getLETRA_PRT() {
        return LETRA_PRT;
    }

    public void setLETRA_PRT(String LETRA_PRT) {
        this.LETRA_PRT = LETRA_PRT;
    }

    public String getCHEQ_DEV() {
        return CHEQ_DEV;
    }

    public void setCHEQ_DEV(String CHEQ_DEV) {
        this.CHEQ_DEV = CHEQ_DEV;
    }

    public String getGRUPO() {
        return GRUPO;
    }

    public void setGRUPO(String GRUPO) {
        this.GRUPO = GRUPO;
    }

    public String getDIREC_FACT() {
        return DIREC_FACT;
    }

    public void setDIREC_FACT(String DIREC_FACT) {
        this.DIREC_FACT = DIREC_FACT;
    }

    public String getINTERES() {
        return INTERES;
    }

    public void setINTERES(String INTERES) {
        this.INTERES = INTERES;
    }

    public String getCOMI_PREDET() {
        return COMI_PREDET;
    }

    public void setCOMI_PREDET(String COMI_PREDET) {
        this.COMI_PREDET = COMI_PREDET;
    }

    public String getALM_CONS() {
        return ALM_CONS;
    }

    public void setALM_CONS(String ALM_CONS) {
        this.ALM_CONS = ALM_CONS;
    }

    public String getCOD_UBC() {
        return COD_UBC;
    }

    public void setCOD_UBC(String COD_UBC) {
        this.COD_UBC = COD_UBC;
    }

    public String getDOCIDEN() {
        return DOCIDEN;
    }

    public void setDOCIDEN(String DOCIDEN) {
        this.DOCIDEN = DOCIDEN;
    }

    public String getRUC_NEW() {
        return RUC_NEW;
    }

    public void setRUC_NEW(String RUC_NEW) {
        this.RUC_NEW = RUC_NEW;
    }

    public String getUBIGEO() {
        return UBIGEO;
    }

    public void setUBIGEO(String UBIGEO) {
        this.UBIGEO = UBIGEO;
    }

    public Number getDIA_VISITA() {
        return DIA_VISITA;
    }

    public void setDIA_VISITA(Number DIA_VISITA) {
        this.DIA_VISITA = DIA_VISITA;
    }

    public Number getORD_VISITA() {
        return ORD_VISITA;
    }

    public void setORD_VISITA(Number ORD_VISITA) {
        this.ORD_VISITA = ORD_VISITA;
    }

    public String getFRC_VISITA() {
        return FRC_VISITA;
    }

    public void setFRC_VISITA(String FRC_VISITA) {
        this.FRC_VISITA = FRC_VISITA;
    }

    public String getANIVERSARIO() {
        return ANIVERSARIO;
    }

    public void setANIVERSARIO(String ANIVERSARIO) {
        this.ANIVERSARIO = ANIVERSARIO;
    }

    public String getR_CUMPLEANOS() {
        return R_CUMPLEANOS;
    }

    public void setR_CUMPLEANOS(String r_CUMPLEANOS) {
        R_CUMPLEANOS = r_CUMPLEANOS;
    }

    public String getL_TAMANO() {
        return L_TAMANO;
    }

    public void setL_TAMANO(String l_TAMANO) {
        L_TAMANO = l_TAMANO;
    }

    public String getL_DECORACION() {
        return L_DECORACION;
    }

    public void setL_DECORACION(String l_DECORACION) {
        L_DECORACION = l_DECORACION;
    }

    public String getL_TECNOLOGIA() {
        return L_TECNOLOGIA;
    }

    public void setL_TECNOLOGIA(String l_TECNOLOGIA) {
        L_TECNOLOGIA = l_TECNOLOGIA;
    }

    public String getNRO_EMPLEADOS() {
        return NRO_EMPLEADOS;
    }

    public void setNRO_EMPLEADOS(String NRO_EMPLEADOS) {
        this.NRO_EMPLEADOS = NRO_EMPLEADOS;
    }

    public String getNRO_EXIBIDOR() {
        return NRO_EXIBIDOR;
    }

    public void setNRO_EXIBIDOR(String NRO_EXIBIDOR) {
        this.NRO_EXIBIDOR = NRO_EXIBIDOR;
    }

    public String getNRO_VISITAS() {
        return NRO_VISITAS;
    }

    public void setNRO_VISITAS(String NRO_VISITAS) {
        this.NRO_VISITAS = NRO_VISITAS;
    }

    public Number getCANEMP() {
        return CANEMP;
    }

    public void setCANEMP(Number CANEMP) {
        this.CANEMP = CANEMP;
    }

    public String getCEOCLIE() {
        return CEOCLIE;
    }

    public void setCEOCLIE(String CEOCLIE) {
        this.CEOCLIE = CEOCLIE;
    }

    public String getPROFESO() {
        return PROFESO;
    }

    public void setPROFESO(String PROFESO) {
        this.PROFESO = PROFESO;
    }

    public String getCARNET() {
        return CARNET;
    }

    public void setCARNET(String CARNET) {
        this.CARNET = CARNET;
    }

    public String getREFERENCIA() {
        return REFERENCIA;
    }

    public void setREFERENCIA(String REFERENCIA) {
        this.REFERENCIA = REFERENCIA;
    }

    public String getCOD_STROE() {
        return COD_STROE;
    }

    public void setCOD_STROE(String COD_STROE) {
        this.COD_STROE = COD_STROE;
    }

    public String getGIRO_CLI() {
        return GIRO_CLI;
    }

    public void setGIRO_CLI(String GIRO_CLI) {
        this.GIRO_CLI = GIRO_CLI;
    }

    public String getR_SEXO() {
        return R_SEXO;
    }

    public void setR_SEXO(String r_SEXO) {
        R_SEXO = r_SEXO;
    }

    public String getR_LUGNAC() {
        return R_LUGNAC;
    }

    public void setR_LUGNAC(String r_LUGNAC) {
        R_LUGNAC = r_LUGNAC;
    }

    public String getR_ESTCIV() {
        return R_ESTCIV;
    }

    public void setR_ESTCIV(String r_ESTCIV) {
        R_ESTCIV = r_ESTCIV;
    }

    public String getR_HOBBIE() {
        return R_HOBBIE;
    }

    public void setR_HOBBIE(String r_HOBBIE) {
        R_HOBBIE = r_HOBBIE;
    }

    public String getR_GENERO() {
        return R_GENERO;
    }

    public void setR_GENERO(String r_GENERO) {
        R_GENERO = r_GENERO;
    }

    public String getR_REVIST() {
        return R_REVIST;
    }

    public void setR_REVIST(String r_REVIST) {
        R_REVIST = r_REVIST;
    }

    public String getR_PERIOD() {
        return R_PERIOD;
    }

    public void setR_PERIOD(String r_PERIOD) {
        R_PERIOD = r_PERIOD;
    }

    public String getRETENCION() {
        return RETENCION;
    }

    public void setRETENCION(String RETENCION) {
        this.RETENCION = RETENCION;
    }

    public String getCALIF_VTA() {
        return CALIF_VTA;
    }

    public void setCALIF_VTA(String CALIF_VTA) {
        this.CALIF_VTA = CALIF_VTA;
    }

    public String getCALIF_CRED() {
        return CALIF_CRED;
    }

    public void setCALIF_CRED(String CALIF_CRED) {
        this.CALIF_CRED = CALIF_CRED;
    }

    public String getPLAZO_PAGO() {
        return PLAZO_PAGO;
    }

    public void setPLAZO_PAGO(String PLAZO_PAGO) {
        this.PLAZO_PAGO = PLAZO_PAGO;
    }

    public String getI_SITUACION() {
        return I_SITUACION;
    }

    public void setI_SITUACION(String i_SITUACION) {
        I_SITUACION = i_SITUACION;
    }

    public String getC_CANAL() {
        return C_CANAL;
    }

    public void setC_CANAL(String c_CANAL) {
        C_CANAL = c_CANAL;
    }

    public String getC_CLIENTE() {
        return C_CLIENTE;
    }

    public void setC_CLIENTE(String c_CLIENTE) {
        C_CLIENTE = c_CLIENTE;
    }

    public String getC_ETAPA() {
        return C_ETAPA;
    }

    public void setC_ETAPA(String c_ETAPA) {
        C_ETAPA = c_ETAPA;
    }

    public String getFLG_CONSIGNA_VTA() {
        return FLG_CONSIGNA_VTA;
    }

    public void setFLG_CONSIGNA_VTA(String FLG_CONSIGNA_VTA) {
        this.FLG_CONSIGNA_VTA = FLG_CONSIGNA_VTA;
    }

    public String getFLG_CONSIGNA_PROM() {
        return FLG_CONSIGNA_PROM;
    }

    public void setFLG_CONSIGNA_PROM(String FLG_CONSIGNA_PROM) {
        this.FLG_CONSIGNA_PROM = FLG_CONSIGNA_PROM;
    }

    public String getC_COBRADOR_EXT() {
        return C_COBRADOR_EXT;
    }

    public void setC_COBRADOR_EXT(String c_COBRADOR_EXT) {
        C_COBRADOR_EXT = c_COBRADOR_EXT;
    }

    public String getC_DIA_VISITA_EXT() {
        return C_DIA_VISITA_EXT;
    }

    public void setC_DIA_VISITA_EXT(String c_DIA_VISITA_EXT) {
        C_DIA_VISITA_EXT = c_DIA_VISITA_EXT;
    }

    public String getN_ORD_VISITA_EXT() {
        return N_ORD_VISITA_EXT;
    }

    public void setN_ORD_VISITA_EXT(String n_ORD_VISITA_EXT) {
        N_ORD_VISITA_EXT = n_ORD_VISITA_EXT;
    }

    public String getC_FRC_VISITA_EXT() {
        return C_FRC_VISITA_EXT;
    }

    public void setC_FRC_VISITA_EXT(String c_FRC_VISITA_EXT) {
        C_FRC_VISITA_EXT = c_FRC_VISITA_EXT;
    }

    public String getC_RUTA_DISTRIBUCION() {
        return C_RUTA_DISTRIBUCION;
    }

    public void setC_RUTA_DISTRIBUCION(String c_RUTA_DISTRIBUCION) {
        C_RUTA_DISTRIBUCION = c_RUTA_DISTRIBUCION;
    }

    public String getE_MAIL() {
        return E_MAIL;
    }

    public void setE_MAIL(String e_MAIL) {
        E_MAIL = e_MAIL;
    }

    public Number getN_ARCHIVO() {
        return N_ARCHIVO;
    }

    public void setN_ARCHIVO(Number n_ARCHIVO) {
        N_ARCHIVO = n_ARCHIVO;
    }

    public String getC_CLIENTE_PAGO() {
        return C_CLIENTE_PAGO;
    }

    public void setC_CLIENTE_PAGO(String c_CLIENTE_PAGO) {
        C_CLIENTE_PAGO = c_CLIENTE_PAGO;
    }

    public String getC_NIVEL() {
        return C_NIVEL;
    }

    public void setC_NIVEL(String c_NIVEL) {
        C_NIVEL = c_NIVEL;
    }

    public String getI_CLIENTE_INT_EXT() {
        return I_CLIENTE_INT_EXT;
    }

    public void setI_CLIENTE_INT_EXT(String i_CLIENTE_INT_EXT) {
        I_CLIENTE_INT_EXT = i_CLIENTE_INT_EXT;
    }

    public Number getM_CUOTA_SEMANAL() {
        return M_CUOTA_SEMANAL;
    }

    public void setM_CUOTA_SEMANAL(Number m_CUOTA_SEMANAL) {
        M_CUOTA_SEMANAL = m_CUOTA_SEMANAL;
    }

    public String getC_FRC_COB() {
        return C_FRC_COB;
    }

    public void setC_FRC_COB(String c_FRC_COB) {
        C_FRC_COB = c_FRC_COB;
    }

    public String getC_SUC_EMP() {
        return C_SUC_EMP;
    }

    public void setC_SUC_EMP(String c_SUC_EMP) {
        C_SUC_EMP = c_SUC_EMP;
    }

    public String getC_GRUPO_CANAL_VTA() {
        return C_GRUPO_CANAL_VTA;
    }

    public void setC_GRUPO_CANAL_VTA(String c_GRUPO_CANAL_VTA) {
        C_GRUPO_CANAL_VTA = c_GRUPO_CANAL_VTA;
    }

    public String getC_FUERZA_VTA() {
        return C_FUERZA_VTA;
    }

    public void setC_FUERZA_VTA(String c_FUERZA_VTA) {
        C_FUERZA_VTA = c_FUERZA_VTA;
    }

    public String getI_DIR_FISCAL() {
        return I_DIR_FISCAL;
    }

    public void setI_DIR_FISCAL(String i_DIR_FISCAL) {
        I_DIR_FISCAL = i_DIR_FISCAL;
    }

    public String getC_REGION_VTA() {
        return C_REGION_VTA;
    }

    public void setC_REGION_VTA(String c_REGION_VTA) {
        C_REGION_VTA = c_REGION_VTA;
    }

    public String getC_COBRADOR() {
        return C_COBRADOR;
    }

    public void setC_COBRADOR(String c_COBRADOR) {
        C_COBRADOR = c_COBRADOR;
    }

    public String getI_ORIGEN() {
        return I_ORIGEN;
    }

    public void setI_ORIGEN(String i_ORIGEN) {
        I_ORIGEN = i_ORIGEN;
    }

    public String getI_DI() {
        return I_DI;
    }

    public void setI_DI(String i_DI) {
        I_DI = i_DI;
    }

    public String getI_AGENTE_RET() {
        return I_AGENTE_RET;
    }

    public void setI_AGENTE_RET(String i_AGENTE_RET) {
        I_AGENTE_RET = i_AGENTE_RET;
    }

    public String getI_AGENTE_PER() {
        return I_AGENTE_PER;
    }

    public void setI_AGENTE_PER(String i_AGENTE_PER) {
        I_AGENTE_PER = i_AGENTE_PER;
    }

    public String getI_PERCEPCION() {
        return I_PERCEPCION;
    }

    public void setI_PERCEPCION(String i_PERCEPCION) {
        I_PERCEPCION = i_PERCEPCION;
    }

    public String getCOD_DPT() {
        return COD_DPT;
    }

    public void setCOD_DPT(String COD_DPT) {
        this.COD_DPT = COD_DPT;
    }

    public String getCOD_PVC() {
        return COD_PVC;
    }

    public void setCOD_PVC(String COD_PVC) {
        this.COD_PVC = COD_PVC;
    }

    public String getDIRECCION_VSTA() {
        return DIRECCION_VSTA;
    }

    public void setDIRECCION_VSTA(String DIRECCION_VSTA) {
        this.DIRECCION_VSTA = DIRECCION_VSTA;
    }

    public String getID_NIVEL() {
        return ID_NIVEL;
    }

    public void setID_NIVEL(String ID_NIVEL) {
        this.ID_NIVEL = ID_NIVEL;
    }

    public String getI_RELACION() {
        return I_RELACION;
    }

    public void setI_RELACION(String i_RELACION) {
        I_RELACION = i_RELACION;
    }

    public String getF_FACT_AUTOM() {
        return F_FACT_AUTOM;
    }

    public void setF_FACT_AUTOM(String f_FACT_AUTOM) {
        F_FACT_AUTOM = f_FACT_AUTOM;
    }

    public String getC_USUARIO() {
        return C_USUARIO;
    }

    public void setC_USUARIO(String c_USUARIO) {
        C_USUARIO = c_USUARIO;
    }

    public String getC_PERFIL() {
        return C_PERFIL;
    }

    public void setC_PERFIL(String c_PERFIL) {
        C_PERFIL = c_PERFIL;
    }

    public String getC_CPU() {
        return C_CPU;
    }

    public void setC_CPU(String c_CPU) {
        C_CPU = c_CPU;
    }

    public String getFEC_REG() {
        return FEC_REG;
    }

    public void setFEC_REG(String FEC_REG) {
        this.FEC_REG = FEC_REG;
    }

    public String getI_REGISTRO_CASTIGO() {
        return I_REGISTRO_CASTIGO;
    }

    public void setI_REGISTRO_CASTIGO(String i_REGISTRO_CASTIGO) {
        I_REGISTRO_CASTIGO = i_REGISTRO_CASTIGO;
    }

    public String getOBSERVACION() {
        return OBSERVACION;
    }

    public void setOBSERVACION(String OBSERVACION) {
        this.OBSERVACION = OBSERVACION;
    }

    public String getID_LOCAL() {
        return ID_LOCAL;
    }

    public void setID_LOCAL(String ID_LOCAL) {
        this.ID_LOCAL = ID_LOCAL;
    }

    public String getID_CLIENTE() {
        return ID_CLIENTE;
    }

    public void setID_CLIENTE(String ID_CLIENTE) {
        this.ID_CLIENTE = ID_CLIENTE;
    }

    public String getRAZON_SOCIAL() {
        return RAZON_SOCIAL;
    }

    public void setRAZON_SOCIAL(String RAZON_SOCIAL) {
        this.RAZON_SOCIAL = RAZON_SOCIAL;
    }

    public String getID_CANAL() {
        return ID_CANAL;
    }

    public void setID_CANAL(String ID_CANAL) {
        this.ID_CANAL = ID_CANAL;
    }

    public String getCODIGO_ANTIGUO() {
        return CODIGO_ANTIGUO;
    }

    public void setCODIGO_ANTIGUO(String CODIGO_ANTIGUO) {
        this.CODIGO_ANTIGUO = CODIGO_ANTIGUO;
    }

    public String getM_PAE() {
        return M_PAE;
    }

    public void setM_PAE(String m_PAE) {
        M_PAE = m_PAE;
    }

    public String getI_CANC_ANTIGUO() {
        return I_CANC_ANTIGUO;
    }

    public void setI_CANC_ANTIGUO(String i_CANC_ANTIGUO) {
        I_CANC_ANTIGUO = i_CANC_ANTIGUO;
    }

    public Integer getID_EMPRESA() {
        return ID_EMPRESA;
    }

    public void setID_EMPRESA(Integer ID_EMPRESA) {
        this.ID_EMPRESA = ID_EMPRESA;
    }
}
