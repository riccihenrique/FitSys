package templates;

import model.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import util.Banco;

public class Mensalidade 
{
    private int men_cod;
    private double men_valor;
    private LocalDate men_dtvenc;
    private Matricula mat;
    
    public Mensalidade()
    {
        this.men_cod = 0;
        this.men_dtvenc = null;
        this.men_valor = 0;
        this.mat = new Matricula();
    }
    
    public Mensalidade(double men_valor, LocalDate men_dtvenc, Matricula mat) {
        this.men_valor = men_valor;
        this.men_dtvenc = men_dtvenc;
        this.mat = mat;
    }

    public Mensalidade(int men_cod, double men_valor, LocalDate men_dtvenc, Matricula mat) {
        this.men_cod = men_cod;
        this.men_valor = men_valor;
        this.men_dtvenc = men_dtvenc;
        this.mat = mat;
    }

    public int getMen_cod() {
        return men_cod;
    }

    public void setMen_cod(int men_cod) {
        this.men_cod = men_cod;
    }

    public double getMen_valor() {
        return men_valor;
    }

    public void setMen_valor(double men_valor) {
        this.men_valor = men_valor;
    }

    public LocalDate getMen_dtvenc() {
        return men_dtvenc;
    }

    public void setMen_dtvenc(LocalDate men_dtvenc) {
        this.men_dtvenc = men_dtvenc;
    }

    public Matricula getMat() {
        return mat;
    }

    public void setMat(Matricula mat) {
        this.mat = mat;
    }
    
    public boolean gravar()
    {
        String sql="insert into mensalidade (men_valor, men_dtvenc, mat_cod) values (#1, '#2', #3)";

        sql=sql.replaceAll("#1", ""+getMen_valor());
        sql=sql.replaceAll("#2", getMen_dtvenc().toString());
        sql=sql.replaceAll("#3", ""+getMat().getCod());

        return Banco.getCon().manipular(sql);
    }
    
    public boolean get(int mat_cod)
    {
        String SQl = "select * from mensalidade where mat_cod = " + mat_cod;
        
        try
        {
            ResultSet rs = Banco.getCon().consultar(SQl);
            if(rs.next()) 
            {
                this.men_cod = rs.getInt("men_cod");
                this.mat.get(mat_cod);
                this.men_dtvenc = rs.getDate("men_dtvenc").toLocalDate();
                this.men_valor = rs.getDouble("men_valor");    
                
                return true;
            }
        }
        catch(SQLException e)
        {
            return false;
        }
        return false;
    }
    
    public static boolean geraMensalidade(Matricula mat, int dt_venc, boolean flag)
    {
        double valor;
        Mensalidade mens;
        int dias = LocalDate.now().lengthOfMonth() - LocalDate.now().getDayOfMonth() + dt_venc;
        
        // se a flag for = true é a primeira mensalidade (gerar com base na data da matricula)
        if(flag)
            valor = (mat.getPacote().getTotal() / LocalDate.now().lengthOfMonth()) * dias;
        else
            valor = mat.getPacote().getTotal();
        
        mens = new Mensalidade(valor, LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().plus(1), dt_venc), mat);

        mens.gravar();
        
        return true;
    }
    
    public boolean alterar()
    {
        String sql="update mensalidade SET men_valor = #1, men_dtvenc = '#2', mat_cod = #3 where men_cod = " + this.getMen_cod();

        sql=sql.replaceAll("#1", ""+getMen_valor());
        sql=sql.replaceAll("#2", getMen_dtvenc().toString());
        sql=sql.replaceAll("#3", ""+getMat().getCod());

        return Banco.getCon().manipular(sql);
    }
    
    public static boolean deletarTodos(int mat_cod)
    {
        return Banco.getCon().manipular("delete from mensalidade where mat_cod = " + mat_cod);
    }
}
