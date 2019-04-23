package model;

public class Parametrizacao 
{
    private String razao;
    private String cnpj;
    private String endereco;
    private String cidade;
    private String cep;
    private String cor_primaria;
    private String cor_secundaria;

    public Parametrizacao(String razao, String cnpj, String endereco, String cidade, String cep, String cor_primaria, String cor_secundaria) {
        this.razao = razao;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.cidade = cidade;
        this.cep = cep;
        this.cor_primaria = cor_primaria;
        this.cor_secundaria = cor_secundaria;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCor_primaria() {
        return cor_primaria;
    }

    public void setCor_primaria(String cor_primaria) {
        this.cor_primaria = cor_primaria;
    }

    public String getCor_secundaria() {
        return cor_secundaria;
    }

    public void setCor_secundaria(String cor_secundaria) {
        this.cor_secundaria = cor_secundaria;
    }
}
