package model;

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
                t.get(rs.getInt(""));
                
                l.add(new Despesa(rs.getInt(""), rs.getDouble(""),rs.getDate("").toLocalDate(), 
                        t, rs.getDate("").toLocalDate(), rs.getDate("").toLocalDate() == null ? false : true));
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
}
