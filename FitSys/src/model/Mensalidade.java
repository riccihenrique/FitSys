package model;

import java.time.LocalDate;
import util.Banco;

public class Mensalidade 
{
    private int men_cod;
    private double men_valor;
    private LocalDate men_dtvenc;
    private Matricula mat;

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
}
