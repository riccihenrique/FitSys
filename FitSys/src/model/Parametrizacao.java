package model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import util.Banco;

public class Parametrizacao 
{
    private String razao;
    private String cnpj;
    private String endereco;
    private String cidade;
    private String cep;
    private String uf;
    private String cor_primaria;
    private String cor_secundaria;
    private Image logo;
    private Image back;
    
    public Parametrizacao(){}

    public Parametrizacao(String razao, String cnpj, String endereco, String cidade, String cep, String uf, String cor_primaria, String cor_secundaria, Image logo, Image back) {
        this.razao = razao;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.cidade = cidade;
        this.cep = cep;
        this.uf = uf;
        this.cor_primaria = cor_primaria;
        this.cor_secundaria = cor_secundaria;
        this.logo = logo;
        this.back = back;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCor_primaria() {
        return cor_primaria;
    }

    public void setCor_primaria(String cor_primaria) {
        this.cor_primaria = cor_primaria;
    }

    public String getCor_secundaria() {
        return cor_secundaria;
    }

    public void setCor_secundaria(String cor_secundaria) {
        this.cor_secundaria = cor_secundaria;
    }

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }

    public Image getBack() {
        return back;
    }

    public void setBack(Image back) {
        this.back = back;
    }
    
    public boolean gravar()
    {
        String sql = "insert into parametrizacao values(null, '#1', null, '#2', '#3', '#4', '#5', '#6', '#7', '#8')";
        sql = sql.replace("#1", razao);
        sql = sql.replace("#2", cnpj.replace(".", "").replace("-", "").replace("/", ""));
        sql = sql.replace("#3", cor_primaria);
        sql = sql.replace("#4", cor_secundaria);
        sql = sql.replace("#5", endereco);
        sql = sql.replace("#6", cidade);
        sql = sql.replace("#7", cep.replace("-", ""));
        sql = sql.replace("#8", uf);
        
        if(Banco.getCon().manipular(sql))
        {
            try
            {
                PreparedStatement ps;
                byte[] byteLogo = null, byteBack = null;
                ByteArrayOutputStream baosLogo = new ByteArrayOutputStream(), baosBack = new ByteArrayOutputStream();
                if(logo != null)
                {
                    BufferedImage blogo = SwingFXUtils.fromFXImage(logo, null);
                    ImageIO.write(blogo, "jpg", baosLogo);
                    baosLogo.flush();
                    byteLogo = baosLogo.toByteArray();
                    baosLogo.close();
                    InputStream inLogo = new ByteArrayInputStream(byteLogo);
                    ps = Banco.getCon().getConnection().prepareStatement("UPDATE garcon set logo = ?");
                    ps.setBinaryStream(1, inLogo, baosLogo.toByteArray().length);
                    ps.executeUpdate();
                    ps.close();
                    inLogo.close();
                }
                
                if(back != null)
                {
                    BufferedImage bback = SwingFXUtils.fromFXImage(back, null);
                    ImageIO.write(bback, "jpg", baosBack);                
                    baosBack.flush();                
                    byteBack = baosBack.toByteArray();                
                    baosBack.close();
                    
                    InputStream inBack = new ByteArrayInputStream(byteBack);
                    ps = Banco.getCon().getConnection().prepareStatement("UPDATE garcon set background = ?");
                    ps.setBinaryStream(1, inBack, baosBack.toByteArray().length);
                    ps.executeUpdate();
                    ps.close();
                    inBack.close();
                }
            }
            catch(Exception e)
            {
                return false;
            }
        
            return true;
        }
            
        return false;
    }   
    
    public boolean alterar()
    {
        String sql = "update parametrizacao set logo = null, razao_social = '#1', background = null, cnpj = '#2',"
                + "cor_primaria = '#3', cor_secundaria = '#4', endereco = '#5', cidade = '#6', cep = '#7', uf = '#8'";
        sql = sql.replace("#1", razao);
        sql = sql.replace("#2", cnpj.replace(".", "").replace("-", "").replace("/", ""));
        sql = sql.replace("#3", cor_primaria);
        sql = sql.replace("#4", cor_secundaria);
        sql = sql.replace("#5", endereco);
        sql = sql.replace("#6", cidade);
        sql = sql.replace("#7", cep.replace("-", ""));
        sql = sql.replace("#8", uf);
        
        if(Banco.getCon().manipular(sql))
        {
            try
            {
                byte[] byteLogo = null, byteBack = null;
                ByteArrayOutputStream baosLogo = new ByteArrayOutputStream(), baosBack = new ByteArrayOutputStream();
                if(logo != null)
                {
                    BufferedImage blogo = SwingFXUtils.fromFXImage(logo, null);
                    ImageIO.write(blogo, "jpg", baosLogo);
                    baosLogo.flush();
                    byteLogo = baosLogo.toByteArray();
                    baosLogo.close();
                }
                
                if(back != null)
                {
                    BufferedImage bback = SwingFXUtils.fromFXImage(back, null);
                    ImageIO.write(bback, "jpg", baosBack);                
                    baosBack.flush();                
                    byteBack = baosBack.toByteArray();                
                    baosBack.close();
                }
                
                InputStream inLogo = new ByteArrayInputStream(byteLogo);
                InputStream inBack = new ByteArrayInputStream(byteBack);
                
                PreparedStatement ps = Banco.getCon().getConnection().prepareStatement("UPDATE garcon set logo = ?, background = ?");
                ps.setBinaryStream(1, inLogo, baosLogo.toByteArray().length);
                ps.setBinaryStream(1, inBack, baosBack.toByteArray().length);
                ps.executeUpdate();
                ps.close();
                inLogo.close();
                inBack.close();
            }
            catch(Exception e)
            {
                return false;
            }
        
            return true;
        }
            
        return false;
    }
    
    public boolean busca()
    {
        ResultSet rs = Banco.getCon().consultar("select * from parametrizacao");
        try
        {
             if(rs.next())
            {
                razao = rs.getString("razao_social");
                cnpj = rs.getString("cnpj");
                endereco = rs.getString("endereco");
                cidade = rs.getString("cidade");
                cep = rs.getString("cep");
                uf = rs.getString("uf");
                cor_primaria = rs.getString("cor_primaria");
                cor_secundaria = rs.getString("cor_secundaria");
                
                //Inserir a recuperação da imagem
                InputStream pic = null;
                try
                {
                    PreparedStatement ps = Banco.getCon().getConnection().prepareStatement("SELECT logo, background FROM parametrizacao");
                    rs = ps.executeQuery();
                    if(rs.next())
                    {
                        byte[] imgBytes = rs.getBytes("logo");
                        BufferedImage bimg = ImageIO.read(new ByteArrayInputStream(imgBytes));
                        logo = SwingFXUtils.toFXImage(bimg, null);
                        
                        imgBytes = rs.getBytes("background");
                        bimg = ImageIO.read(new ByteArrayInputStream(imgBytes));
                        back = SwingFXUtils.toFXImage(bimg, null);
                    }

                    ps.close();
                }
                catch(Exception e)
                {
                    logo = null;
                    back = null;
                }

                return true;
            }
        }
        catch(SQLException se)
        {
            return false;
        }       
        
        return false;
    }
}
