package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.Banco;

public class ExercicioTreino 
{
    Exercicio exercicio = new Exercicio();
    Treino treino = new Treino();
    char tipo;
    int ordem;
    int repeticao;
    int serie;
    int carga; 

    public ExercicioTreino() { }
    
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
    
    public static List<ExercicioTreino> getEx(int cod)
    {
        List<ExercicioTreino> et = new ArrayList<>();
        String SQL = "select * from exercicio_treino where treino_cod = " + cod + " order by ext_tipo, ext_ordem";
        
        ResultSet rs = Banco.getCon().consultar(SQL);
        
        try
        {
            while(rs.next())
            {
                Exercicio e = new Exercicio();
                e.geti(rs.getInt("exe_cod"));
                Treino t = new Treino();
                t.geti(rs.getInt("treino_cod"));
                //Exercicio exercicio, Treino treino, char tipo, int ordem, int repeticao, int serie, int carga
                et.add(new ExercicioTreino(e, t,  rs.getString("ext_tipo").charAt(0), rs.getInt("ext_ordem"), 
                        rs.getInt("ext_repeticao"), rs.getInt("ext_serie"), rs.getInt("ext_carga")));
            }
        }
        catch(Exception e) { }
        
        return et;
    }
    
    public static boolean apagar(int cod)
    {
        return Banco.getCon().manipular("delete from exercicio_treino where treino_cod = " + cod);
    }
    
}
