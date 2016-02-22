package app.m08yoaviso.BBDD;

import java.util.Map;

/**
 * Created by Moises on 22/02/2016.
 */
public class Usuario {

    private final String pw;
    private Map<Integer , String> favoritos; //<id del aviso, comentario>
    private Map<Integer , Aviso> avisos; //<id del aviso, Objeto aviso>

    public Usuario(String pw) {
        this.pw = pw;
    }

}
