package app.m08yoaviso.BBDD;

import java.util.Map;

/**
 * Created by Moises on 22/02/2016.
 */
public class Yoaviso {



    private Map<Integer , Aviso> avisos;
    private Map<Integer , Usuario> usuarios;

    private Map<Integer , String> filtrosUsuario;  //<idFiltro, tag del filtro>

    //CONSTRUCTOR
    public Yoaviso(){ }
    public Yoaviso(Map<Integer, Aviso> avisos, Map<Integer, Usuario> usuarios, Map<Integer, String> filtrosUsuario) {
        this.avisos = avisos;
        this.usuarios = usuarios;
        this.filtrosUsuario = filtrosUsuario;
    }

    public Map<Integer, Aviso> getAvisos() {
        return avisos;
    }

    public void setAvisos(Map<Integer, Aviso> avisos) {
        this.avisos = avisos;
    }

    public Map<Integer, Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Map<Integer, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Map<Integer, String> getFiltrosUsuario() {
        return filtrosUsuario;
    }

    public void setFiltrosUsuario(Map<Integer, String> filtrosUsuario) {
        this.filtrosUsuario = filtrosUsuario;
    }
}
