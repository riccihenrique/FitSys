package model;

import java.time.LocalDate;
import util.Banco;

public class Treino 
{
    int cod;
    LocalDate dataTreino;
    LocalDate dataProximo;
    Matricula matricula;
    Funcionario funcinario;

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
        
}
