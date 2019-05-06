package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.Banco;

public class GrupoMuscular {
    int cod;
    String descricao;

    public GrupoMuscular() {
    }

    public GrupoMuscular(int cod, String descricao) {
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
    public String toString() {
        return descricao;
    }
    
    public static List<GrupoMuscular> get(String filtro)
    {
        List<GrupoMuscular> l = new ArrayList<>();
        String SQL = "select * from grupo_muscular";
        
        if(!filtro.isEmpty())
            SQL += " where " + filtro;
        
        try
        {
            ResultSet rs = Banco.getCon().consultar(SQL);
        
            while(rs.next())
                l.add(new GrupoMuscular(rs.getInt("gru_Cod"), rs.getString("gru_Descricao")));
        }
        catch(Exception e)
        {
            
        }
        
        return l;
    }
    
    public boolean get(int cod)
    {
        String SQL = "select * from grupo_muscular where gru_Cod = " + cod;
        try
        {
            ResultSet rs = Banco.getCon().consultar(SQL);
        
            if(rs.next())
            {
                this.cod = rs.getInt("gru_Cod");
                this.descricao = rs.getString("gru_Descricao");
            }
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
