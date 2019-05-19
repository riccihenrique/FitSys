package model;

import java.sql.ResultSet;
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
                cod = cod;
                return true;
            }
            return false;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
