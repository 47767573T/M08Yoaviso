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

    public Usuario(String pw) {
        this.pw = pw;
    }

}


//TODO: implementar los contructores getters y setters cuando se definan todas las variables
