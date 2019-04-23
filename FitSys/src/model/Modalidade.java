package model;

public class Modalidade 
{
    private int cod;
    private String nome;
    private double preco;

    public Modalidade() {
    }

    public Modalidade(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public Modalidade(int cod, String nome, double preco) {
        this.cod = cod;
        this.nome = nome;
        this.preco = preco;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return nome;
    }
}
