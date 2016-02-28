package app.m08yoaviso.BBDD;

import java.util.Date;
import java.util.Map;

/**
 * Created by Moises on 22/02/2016.
 */
public class Usuario {

    private final String pw;
    private Map<Integer, String> favoritos; //<id del aviso, comentario>
    private Map<Integer, Boolean> avisos; //<id del aviso, anonimato>


    //CONSTRUCTOR
    public Usuario(String pw) { this.pw = pw; }


    //GETTERS
    public String getPw() { return pw; }

    public Map<Integer, String> getFavoritos() { return favoritos; }

    public Map<Integer, Boolean> getAvisos() { return avisos; }


    //SETTERS
    public void setFavoritos(Map<Integer, String> favoritos) { this.favoritos = favoritos; }

    public void setAvisos(Map<Integer, Boolean> avisos) { this.avisos = avisos; }
}
