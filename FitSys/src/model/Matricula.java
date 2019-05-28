
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import util.Banco;

public class Matricula 
{
    int cod;
    LocalDate data;
    Aluno aluno = new Aluno();
    Pacote pacote = new Pacote();

    public Matricula() {
    }

    public Matricula(LocalDate data, Aluno aluno, Pacote pacote) {
        this.data = data;
        this.aluno = aluno;
        this.pacote = pacote;
    }

    public Matricula(int cod, LocalDate data, Aluno aluno, Pacote pacote) {
        this.cod = cod;
        this.data = data;
        this.aluno = aluno;
        this.pacote = pacote;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }
    
    public boolean get(int cod)
    {
        String SQl = "select * from matricula where mat_cod = " + cod;
        
        try
        {
            ResultSet rs = Banco.getCon().consultar(SQl);
            if(rs.next())
            {                
                this.aluno.geti("alu_cpf");
                this.cod = cod;
                this.data = rs.getDate("mat_DTMat").toLocalDate();
                this.pacote.geti(rs.getInt("pct_cod"));
                return true;
            }
        }
        catch(SQLException e)
        {
            return false;
        }
        return false;
    }
    
    public static List<Matricula> getMatriculas(String filtro)
    {
        List<Matricula> l = new ArrayList<>();
        String SQl = "select * from matricula m join aluno a on a.alu_Cpf = m.alu_Cpf";
        
        if(!filtro.isEmpty())
            SQl += " where " + filtro;
        ResultSet rs = Banco.getCon().consultar(SQl);
        
        try
        {
            while(rs.next())
            {
                Aluno alu = new Aluno();
                alu.geti(rs.getString("alu_Cpf"));
                Pacote pac = new Pacote();
                pac.geti(rs.getInt("pct_cod"));
                
                l.add(new Matricula(rs.getInt("mat_Cod"), rs.getDate("mat_DTMat").toLocalDate(), alu, pac));
            }
        }
        catch(Exception e)
        {
        }
        
        return l;
    }
    
    public boolean gravar()
    {
        String sql="insert into matricula (mat_dtmat, alu_cpf, pct_cod) values ('#1', '#2', #3)";

        sql=sql.replaceAll("#1", data.toString());
        sql=sql.replaceAll("#2", aluno.getCpf());
        sql=sql.replaceAll("#3", ""+pacote.getCod());

        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar()
    {
        String sql="update matricula SET mat_dtmat = '#1', alu_cpf = '#2', pct_cod= #3 where mat_cod = " + this.getCod();

        sql=sql.replaceAll("#1", this.getData().toString());
        sql=sql.replaceAll("#2", this.getAluno().getCpf());
        sql=sql.replaceAll("#3", ""+this.getPacote().getCod());

        return Banco.getCon().manipular(sql);
    }
    
    public static boolean deletar(int mat_cod)
    {
        String sql="delete from matricula where mat_cod = " + mat_cod;

        return Banco.getCon().manipular(sql);
    }
}
