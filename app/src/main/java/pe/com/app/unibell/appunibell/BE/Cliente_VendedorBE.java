package pe.com.app.unibell.appunibell.BE;

public class Cliente_VendedorBE {

    private String C_CLIENTE;
    private String C_VENDEDOR;
    private String C_CANAL;
    private Integer C_DIA_VISITA;
    private Integer N_ORD_VISITA;
    private String C_FRC_VISITA;

    public void setC_CLIENTE(String c_CLIENTE) {
        C_CLIENTE = c_CLIENTE;
    }

    public void setC_VENDEDOR(String c_VENDEDOR) {
        C_VENDEDOR = c_VENDEDOR;
    }

    public void setC_CANAL(String c_CANAL) {
        C_CANAL = c_CANAL;
    }

    public void setC_DIA_VISITA(Integer c_DIA_VISITA) {
        C_DIA_VISITA = c_DIA_VISITA;
    }

    public void setN_ORD_VISITA(Integer n_ORD_VISITA) {
        N_ORD_VISITA = n_ORD_VISITA;
    }

    public void setC_FRC_VISITA(String c_FRC_VISITA) {
        C_FRC_VISITA = c_FRC_VISITA;
    }

    public String getC_CLIENTE() {
        return C_CLIENTE;
    }

    public String getC_VENDEDOR() {
        return C_VENDEDOR;
    }

    public String getC_CANAL() {
        return C_CANAL;
    }

    public Integer getC_DIA_VISITA() {
        return C_DIA_VISITA;
    }

    public Integer getN_ORD_VISITA() {
        return N_ORD_VISITA;
    }

    public String getC_FRC_VISITA() {
        return C_FRC_VISITA;
    }
}
