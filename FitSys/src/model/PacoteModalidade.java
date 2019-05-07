package model;

import util.Banco;

public class PacoteModalidade 
{
    private int pct_cod;
    private int mod_cod;

    public PacoteModalidade() {
        this(0, 0);
    }

    public PacoteModalidade(int pct_cod, int mod_cod) {
        this.pct_cod = pct_cod;
        this.mod_cod = mod_cod;
    }

    public int getPct_cod() {
        return pct_cod;
    }

    public void setPct_cod(int pct_cod) {
        this.pct_cod = pct_cod;
    }

    public int getMod_cod() {
        return mod_cod;
    }

    public void setMod_cod(int mod_cod) {
        this.mod_cod = mod_cod;
    }
    
    public boolean gravar()
    {
        String sql="insert into pacote_modalidade (pct_cod, mod_cod) values (#1, #2)";

        sql=sql.replaceAll("#1", ""+getPct_cod());
        sql=sql.replaceAll("#2", ""+getMod_cod());

        return Banco.getCon().manipular(sql);
    }
    
    public static boolean apagaTodos(int pct_cod)
    {
        String sql="delete from pacote_modalidade where pct_cod = " + pct_cod;

        return Banco.getCon().manipular(sql);
    }
}
