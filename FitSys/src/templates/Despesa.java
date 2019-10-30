package templates;

import model.*;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import util.Banco;

public class Despesa {
    private int cod;
    private double valor;
    private LocalDate vencimento;
    private TipoDespesa tpDespesa;
    private LocalDate pagamento;
    private boolean paga;

    public Despesa() {
    }

    public Despesa(double valor, LocalDate vencimento, TipoDespesa tpDespesa, LocalDate pagamento, boolean paga) {
        this.valor = valor;
        this.vencimento = vencimento;
        this.tpDespesa = tpDespesa;
        this.pagamento = pagamento;
        this.paga = paga;
    }

    public Despesa(int cod, double valor, LocalDate vencimento, TipoDespesa tpDespesa, LocalDate pagamento, boolean paga) {
        this.cod = cod;
        this.valor = valor;
        this.vencimento = vencimento;
        this.tpDespesa = tpDespesa;
        this.pagamento = pagamento;
        this.paga = paga;
    }
    
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public TipoDespesa getTpDespesa() {
        return tpDespesa;
    }

    public void setTpDespesa(TipoDespesa tpDespesa) {
        this.tpDespesa = tpDespesa;
    }

    public LocalDate getPagamento() {
        return pagamento;
    }

    public void setPagamento(LocalDate pagamento) {
        this.pagamento = pagamento;
    }

    public boolean isPaga() {
        return paga;
    }

    public void setPaga(boolean paga) {
        this.paga = paga;
    }
        
    @Override
    public String toString() {
        return tpDespesa.toString();
    }
    
    public static List<Despesa> get(String filtro)
    {
        List<Despesa> l = new ArrayList<>();
        String SQL = "select * from despesa";
        
        if(!filtro.isEmpty())
            SQL += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(SQL);
        try
        {
            while(rs.next())
            {
                TipoDespesa t = new TipoDespesa();
                t.get(rs.getInt("tpd_cod"));
                
                l.add(new Despesa(rs.getInt("desp_cod"), rs.getDouble("desp_valor"),rs.getDate("desp_dtvencimento").toLocalDate(), 
                        t, rs.getDate("desp_dtpagamento") == null ? null: rs.getDate("desp_dtpagamento").toLocalDate(), 
                        rs.getDate("desp_dtpagamento") == null ? false : true));
            }    
        }
        catch(Exception e)
        {
            
        }
        
        return l;
    }
    
    public static boolean apagar(int cod)
    {
        return Banco.getCon().manipular("delete from despesa where desp_cod = " + cod);
    }
    
    public boolean gravar()
    {
        String SQL = "insert into despesa (desp_valor, desp_dtvencimento, tpd_cod, desp_dtpagamento) values(#1, '#2', #3, #4)";
        
        SQL = SQL.replace("#1", "" + valor);
        SQL = SQL.replace("#2", vencimento.toString());
        SQL = SQL.replace("#3", "" + tpDespesa.getCod());
        if(pagamento == null)
            SQL = SQL.replace("#4", "null");
        else
            SQL = SQL.replace("#4", "'" + pagamento.toString() + "'");
        
        return Banco.getCon().manipular(SQL);
    }
    
    public boolean alterar()
    {
        String SQL = "update despesa set desp_valor = #1, desp_dtvencimento = '#2', tpd_cod = #3, desp_dtpagamento = #4 where desp_cod = " + cod;
        SQL = SQL.replace("#1", "" + valor);
        SQL = SQL.replace("#2", vencimento.toString());
        SQL = SQL.replace("#3", "" + tpDespesa.getCod());
        if(pagamento == null)
            SQL = SQL.replace("#4", "null");
        else
            SQL = SQL.replace("#4", "'" + pagamento.toString() + "'");
        
        return Banco.getCon().manipular(SQL);
    }
}
