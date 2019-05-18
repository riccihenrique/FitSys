package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Banco;

public class Exercicio {
    int Cod;
   String Descricao;
   GrupoMuscular grupoMuscular = new GrupoMuscular();

    public Exercicio() {
    }

    public Exercicio(String Descricao, GrupoMuscular grupoMuscular) {
        this.Descricao = Descricao;
        this.grupoMuscular = grupoMuscular;
    }

    public Exercicio(int Cod, String Descricao, GrupoMuscular grupoMuscular) {
        this.Cod = Cod;
        this.Descricao = Descricao;
        this.grupoMuscular = grupoMuscular;
    }

    public int getCod() {
        return Cod;
    }

    public void setCod(int Cod) {
        this.Cod = Cod;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public GrupoMuscular getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(GrupoMuscular grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }
   
   @Override
   public String toString()
   {
       return Descricao;
   }
   
   
   public static List<Exercicio> get(String filtro)
    {
        List<Exercicio> l = new ArrayList<>();
        String SQL = "select * from exercicios";
        
        if(!filtro.isEmpty())
            SQL += " where " + filtro;
        
        try
        {
            ResultSet rs = Banco.getCon().consultar(SQL);
        
            while(rs.next())
            {
                GrupoMuscular g = new GrupoMuscular();
                g.get(rs.getInt("gru_Cod"));
                l.add(new Exercicio(rs.getInt("exe_Cod"), rs.getString("exe_Desc"), g));
            }  
        }
        catch(Exception e)
        {
            
        }
        
        return l;
    }
           
   public boolean geti(int cod)
    {
        ResultSet rs = Banco.getCon().consultar("select * from exercicios where exe_cod = " + cod);
        try
        {
            if(rs != null && rs.next())
            {
                this.Cod = cod;
                this.Descricao = rs.getString("exe_desc");
                this.grupoMuscular.get(rs.getInt("gru_cod"));
                return true;
            }
        }
        catch(SQLException e){return false;}
        return false;
    }
}
