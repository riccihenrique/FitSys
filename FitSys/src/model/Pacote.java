package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Banco;

public class Pacote 
{
    private int cod;
    private String descricao;
    private int desconto;
    private List<Modalidade> modalidades;
    private double total;

    public Pacote() {
        this(0, null, 0, null, 0);
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
    
    public boolean gravar()
    {
        String sql="insert into pacote (pct_desc, pct_porcdesconto, pct_total) values ('#1', #2, #3)";

        sql=sql.replaceAll("#1", getDescricao());
        sql=sql.replaceAll("#2", Integer.toString(desconto));
        sql=sql.replaceAll("#3", Double.toString(total));

        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar()
    {
        String sql="update pacote SET pct_desc = '#1', pct_porcdesconto = #2, pct_total = #3";

        sql=sql.replaceAll("#1", getDescricao());
        sql=sql.replaceAll("#2", Integer.toString(desconto));
        sql=sql.replaceAll("#3", Double.toString(total));

        return Banco.getCon().manipular(sql);
    }
    
    public static List<Pacote> get(String filtro)
    {
        List<Pacote> L = new ArrayList<>();
        String sql = "select * from pacote";
        if(!filtro.isEmpty())
            sql += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
            while(rs.next())
            {
                List<Modalidade> lm = new ArrayList<>();
                String sql_aux = "select * from modalidade inner join pacote_modalidade as pctmod on modalidade.mod_cod = pctmod.mod_cod where pct_cod = " + rs.getInt("pct_cod");
                ResultSet rs_aux = Banco.getCon().consultar(sql_aux);
                while(rs_aux.next())
                    lm.add(new Modalidade(rs_aux.getInt("mod_cod"), rs_aux.getString("mod_nome"), rs_aux.getDouble("mod_preco")));
                    
                L.add(new Pacote(rs.getInt("pct_cod"), rs.getString("pct_desc"), rs.getInt("pct_porcdesconto"), lm, rs.getDouble("pct_total")));
            }
                
        }
        catch(SQLException e){}
        
        return L;
    }
    
    public static boolean deletar(int pct_cod)
    {
        String sql="delete from pacote where pct_cod = " + pct_cod;

        return Banco.getCon().manipular(sql);
    }
}
