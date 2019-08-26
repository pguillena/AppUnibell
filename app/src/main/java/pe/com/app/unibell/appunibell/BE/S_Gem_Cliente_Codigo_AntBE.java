package pe.com.app.unibell.appunibell.BE;

public class S_Gem_Cliente_Codigo_AntBE {

  private Integer  ID_CLIENTE;
  private String  CODIGO_ANTIGUO;
  private Integer FLAG_VIGENCIA;

    public Integer getID_CLIENTE() {
        return ID_CLIENTE;
    }

    public void setID_CLIENTE(Integer ID_CLIENTE) {
        this.ID_CLIENTE = ID_CLIENTE;
    }

    public String getCODIGO_ANTIGUO() {
        return CODIGO_ANTIGUO;
    }

    public void setCODIGO_ANTIGUO(String CODIGO_ANTIGUO) {
        this.CODIGO_ANTIGUO = CODIGO_ANTIGUO;
    }

    public Integer getFLAG_VIGENCIA() {
        return FLAG_VIGENCIA;
    }

    public void setFLAG_VIGENCIA(Integer FLAG_VIGENCIA) {
        this.FLAG_VIGENCIA = FLAG_VIGENCIA;
    }
}
