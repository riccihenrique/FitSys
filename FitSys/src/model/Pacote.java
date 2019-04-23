package model;

import java.util.List;

public class Pacote 
{
    private int cod;
    private String descricao;
    private int desconto;
    private List<Modalidade> modalidades;
    private double total;

    public Pacote() {
    }

    public Pacote(String descricao, int desconto, List<Modalidade> modalidades, double total) {
        this.descricao = descricao;
        this.desconto = desconto;
        this.modalidades = modalidades;
        this.total = total;
    }

    public Pacote(int cod, String descricao, int desconto, List<Modalidade> modalidades, double total) {
        this.cod = cod;
        this.descricao = descricao;
        this.desconto = desconto;
        this.modalidades = modalidades;
        this.total = total;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDesconto() {
        return desconto;
    }

    public void setDesconto(int desconto) {
        this.desconto = desconto;
    }

    public List<Modalidade> getModalidades() {
        return modalidades;
    }

    public void setModalidades(List<Modalidade> modalidades) {
        this.modalidades = modalidades;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
