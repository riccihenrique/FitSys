package model;

import util.Banco;

public class ExercicioTreino 
{
    Exercicio exercicio;
    Treino treino;
    char tipo;
    int ordem;
    int repeticao;
    int serie;
    int carga; 

    public ExercicioTreino() {
    }
    
    public ExercicioTreino(Exercicio exercicio, Treino treino, char tipo, int ordem, int repeticao, int serie, int carga) {
        this.exercicio = exercicio;
        this.treino = treino;
        this.tipo = tipo;
        this.ordem = ordem;
        this.repeticao = repeticao;
        this.serie = serie;
        this.carga = carga;
    }
    
    public ExercicioTreino(Exercicio exercicio, char tipo, int ordem, int repeticao, int serie, int carga) {
        this.exercicio = exercicio;
        this.tipo = tipo;
        this.ordem = ordem;
        this.repeticao = repeticao;
        this.serie = serie;
        this.carga = carga;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public Treino getTreino() {
        return treino;
    }

    public void setTreino(Treino treino) {
        this.treino = treino;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public int getRepeticao() {
        return repeticao;
    }

    public void setRepeticao(int repeticao) {
        this.repeticao = repeticao;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public int getCarga() {
        return carga;
    }

    public void setCarga(int carga) {
        this.carga = carga;
    }
    
    public boolean gravar()
    {
        String SQL = "insert into Exercicio_Treino values(#1, #2, '#3', #4, #5, #6, #7)";
        
        SQL = SQL.replace("#1", "" + exercicio.getCod());
        SQL = SQL.replace("#2", "" + treino.getCod());
        SQL = SQL.replace("#3", "" + tipo);
        SQL = SQL.replace("#4", "" + repeticao);
        SQL = SQL.replace("#5", "" + serie);
        SQL = SQL.replace("#6", "" + carga);
        SQL = SQL.replace("#7", "" + ordem);
        
        return Banco.getCon().manipular(SQL);
        
    }
}
