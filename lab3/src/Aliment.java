public class Aliment {
    private Long dataExp;
    private String[] ingrediente;
    private Double calorii, pret;
    enum Categorii {
        Paine, Lactate, Mezeluri;
    }

    public Aliment(Long dataExp, String[] ingrediente, Double calorii, Double pret) {
        this.dataExp = dataExp;
        this.ingrediente = ingrediente;
        this.calorii = calorii;
        this.pret = pret;
    }

    public Long getDataExp() {
        return dataExp;
    }

    public String[] getIngrediente() {
        return ingrediente;
    }

    public Double getCalorii() {
        return calorii;
    }

    public Double getPret() {
        return pret;
    }

    public void setDataExp(Long dataExp) {
        this.dataExp = dataExp;
    }

    public void setIngrediente(String[] ingrediente) {
        this.ingrediente = ingrediente;
    }

    public void setCalorii(Double calorii) {
        this.calorii = calorii;
    }

    public void setPret(Double pret) {
        this.pret = pret;
    }
}

