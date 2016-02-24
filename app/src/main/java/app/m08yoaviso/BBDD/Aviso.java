package app.m08yoaviso.BBDD;

import org.osmdroid.util.GeoPoint;

/**
 * Created by Moises on 22/02/2016.
 */
public class Aviso {

    private int id;
    private GeoPoint gp;

    public Aviso(int id, GeoPoint gp) {
        this.id = id;
        this.gp = gp;
    }
}

//TODO: implementar los contructores getters y setters cuando se definan todas las variables