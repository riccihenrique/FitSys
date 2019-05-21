package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import util.Banco;

public class Treino 
{
    int cod;
    LocalDate dataTreino;
    LocalDate dataProximo;
    Matricula matricula = new Matricula();
    Funcionario funcinario = new Funcionario();

    public Treino()
    {
    }

    public Treino(LocalDate dataTreino, LocalDate dataProximo, Matricula matricula, Funcionario funcinario)
    {
        this.dataTreino = dataTreino;
        this.dataProximo = dataProximo;
        this.matricula = matricula;
        this.funcinario = funcinario;
    }

    public Treino(int cod, LocalDate dataTreino, LocalDate dataProximo, Matricula matricula, Funcionario funcinario)
    {
        this.cod = cod;
        this.dataTreino = dataTreino;
        this.dataProximo = dataProximo;
        this.matricula = matricula;
        this.funcinario = funcinario;
    }

    public int getCod()
    {
        return cod;
    }

    public void setCod(int cod)
    {
        this.cod = cod;
    }

    public LocalDate getDataTreino()
    {
        return dataTreino;
    }

    public void setDataTreino(LocalDate dataTreino)
    {
        this.dataTreino = dataTreino;
    }

    public LocalDate getDataProximo()
    {
        return dataProximo;
    }

    public void setDataProximo(LocalDate dataProximo)
    {
        this.dataProximo = dataProximo;
    }

    public Matricula getMatricula()
    {
        return matricula;
    }

    public void setMatricula(Matricula matricula)
    {
        this.matricula = matricula;
    }

    public Funcionario getFuncinario()
    {
        return funcinario;
    }

    public void setFuncinario(Funcionario funcinario)
    {
        this.funcinario = funcinario;
    }
    
    public boolean gravar()
    {
        String SQL = "insert into treino (treino_data, treino_dataprox, fun_cpf, mat_cod) values('#1', '#2', '#3', #4)";
        SQL = SQL.replace("#1", dataTreino.toString());
        SQL = SQL.replace("#2", dataProximo.toString());
        SQL = SQL.replace("#3", funcinario.getCpf());
        SQL = SQL.replace("#4", "" + matricula.getCod());
        
        return Banco.getCon().manipular(SQL);
    }
    
    public boolean alterar()
    {
        String SQL = "update treino set treino_data = '#1', treino_dataprox = '#2', fun_cpf = '#3', mat_cod = #4 where treino_cod = " + cod;
        SQL = SQL.replace("#1", dataTreino.toString());
        SQL = SQL.replace("#2", dataProximo.toString());
        SQL = SQL.replace("#3", funcinario.getCpf());
        SQL = SQL.replace("#4", "" + matricula.getCod());
        
        return Banco.getCon().manipular(SQL);
    }
    
    public static boolean apagar(int c)
    {
        return Banco.getCon().manipular("delete from exercicio_treino where treino_cod = " + c) && Banco.getCon().manipular("delete from treino where treino_cod = " + c);
    }
    
    public static List<Treino> get(String filtro)
    {
        List<Treino> l = new ArrayList<>();
        String SQL = "select * from treino";
        if(!filtro.isEmpty())
            SQL += " where " + filtro;
        
        SQL += " order by treino_data desc";
        
        ResultSet rs = Banco.getCon().consultar(SQL);
        
        try
        {
            while(rs.next())
            {
                Matricula m = new Matricula();
                m.get(rs.getInt("mat_cod"));
                
                Funcionario f = new Funcionario();
                f.getFuncionario(rs.getString("fun_cpf"));
                
                l.add(new Treino(rs.getInt("treino_cod"), rs.getDate("treino_data").toLocalDate(), rs.getDate("treino_dataprox").toLocalDate(), m, f));
            }
        }
        catch(Exception e) 
        {
            System.out.println(e.getMessage());
        }        
        
        return l;
    }
    
    public boolean geti(int cod)
    {
        ResultSet rs = Banco.getCon().consultar("select * from treino where treino_cod = " + cod);
        try
        {
            if(rs != null && rs.next())
            {
                this.cod = cod;
                this.dataProximo = rs.getDate("treino_dataprox").toLocalDate();
                this.dataTreino = rs.getDate("treino_data").toLocalDate();
                this.funcinario.getFuncionario(rs.getString("fun_cpf"));
                this.matricula.get(rs.getInt("mat_cod"));
               
                return true;
            }
        }
        catch(SQLException e){return false;}
        return false;
    }
}
