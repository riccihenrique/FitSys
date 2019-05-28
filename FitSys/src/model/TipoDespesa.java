package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Banco;

public class TipoDespesa {
    private int cod;
    private String descricao;

    public TipoDespesa() {
    }

    public TipoDespesa(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
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
    
    @Override
    public String toString()
    {
        return descricao;
    }
    
    public boolean get(int cod)
    {
        String SQL = "select * from tipodespesa where tpd_cod = " + cod;
        
        ResultSet rs = Banco.getCon().consultar(SQL);
        
        try
        {
            if(rs.next())
            {
                descricao = rs.getString("tpd_desc");
                this.cod = rs.getInt("tpd_cod");
                return true;
            }
            return false;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    public static int geti(String filtro) throws SQLException
    {
        String SQL = "select * from tipodespesa";
        
        if(!filtro.isEmpty())
            SQL += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(SQL);
        int a = -1;
        
        if(rs.next())
           a =  rs.getInt("tpd_cod");
        
        return a;
    }
    
    public static List<TipoDespesa> get(String filtro)
    {
        List<TipoDespesa> l = new ArrayList<>();
        String SQL = "select * from tipodespesa";
        if(!filtro.isEmpty())
            SQL += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(SQL);
        
        try
        {
            while(rs.next())
                l.add(new TipoDespesa(rs.getInt("tpd_cod"), rs.getString("tpd_desc")));
        }
        catch(Exception e) 
        {
            System.out.println(e.getMessage());
        }        
        
        return l;
    }
}
