
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
    Aluno aluno;
    Pacote pacote;
    Treino treino;

    public Matricula() {
    }

    public Matricula(LocalDate data, Aluno aluno, Pacote pacote, Treino treino) {
        this.data = data;
        this.aluno = aluno;
        this.pacote = pacote;
        this.treino = treino;
    }

    public Matricula(int cod, LocalDate data, Aluno aluno, Pacote pacote, Treino treino) {
        this.cod = cod;
        this.data = data;
        this.aluno = aluno;
        this.pacote = pacote;
        this.treino = treino;
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

    public Treino getTreino() {
        return treino;
    }

    public void setTreino(Treino treino) {
        this.treino = treino;
    } 
    
    public Matricula get(int cod)
    {
        String SQl = "select * from matricula where mat_Cod = " + cod;
        
        try
        {
            ResultSet rs = Banco.getCon().consultar(SQl);
            if(rs.next())
            {
                Aluno alu = new Aluno();
                alu.geti(rs.getString("alu_Cpf"));
                Pacote pac = new Pacote();
                // Falta pacote
                Treino tre = new Treino();
                //Falta treino
                
                return new Matricula(rs.getInt("mat_Cod"), rs.getDate("mat_DTMat").toLocalDate(), 
                       alu, pac, tre);
            }
        }
        catch(SQLException e)
        {
            return null;
        }
        return null;
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
                // Falta pacote
                Treino tre = new Treino();
                //Falta treino
                
                l.add(new Matricula(rs.getInt("mat_Cod"), rs.getDate("mat_DTMat").toLocalDate(), 
                       alu, pac, tre));
            }
            
        }
        catch(Exception e)
        {
        }
        
        return l;
    }
}
