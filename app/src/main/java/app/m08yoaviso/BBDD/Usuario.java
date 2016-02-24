package app.m08yoaviso.BBDD;

import java.util.Date;
import java.util.Map;

/**
 * Created by Moises on 22/02/2016.
 */
public class Usuario {

    private final String pw;
    private Map<Integer , String> favoritos; //<id del aviso, comentario>
    private Map<Integer , Boolean> avisos; //<id del aviso, anonimato>

    public Usuario(String pw) { this.pw = pw; }


    public Usuario(String pw, Map<Integer, String> favoritos, Map<Integer, Boolean> avisos) {
        this.pw = pw;
        this.favoritos = favoritos;
        this.avisos = avisos;
    }
}


//TODO: implementar los contructores getters y setters cuando se definan todas las variables
