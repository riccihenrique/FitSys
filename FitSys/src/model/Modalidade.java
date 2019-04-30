package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Banco;

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
    
    public static List<Modalidade> get(String filtro)
    {
        List<Modalidade> L = new ArrayList<>();
        String sql = "select * from modalidade";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
            while(rs.next())
                L.add(new Modalidade(rs.getString("mod_nome"), rs.getDouble("mod_preco")));
        }
        catch(SQLException e){}
        
        return L;
    }
}
