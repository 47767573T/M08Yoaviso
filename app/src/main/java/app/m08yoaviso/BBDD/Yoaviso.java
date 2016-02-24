package app.m08yoaviso.BBDD;

import java.util.Map;

/**
 * Created by Moises on 22/02/2016.
 */
public class Yoaviso {

    public Yoaviso(){ }

    private Map<Integer , Aviso> avisos;
    private Map<Integer , Usuario> usuarios;

    //TODO Los primeros 10 filtros serán basicos para todos y el resto definidos por usuarios
    private Map<Integer , String> filtrosUsuario;  //<idFiltro, tag del filtro>

    public Yoaviso(Map<Integer, Aviso> avisos, Map<Integer, Usuario> usuarios, Map<Integer, String> filtrosUsuario) {
        this.avisos = avisos;
        this.usuarios = usuarios;
        this.filtrosUsuario = filtrosUsuario;
    }
}


//TODO: implementar los contructores getters y setters cuando se definan todas las variables