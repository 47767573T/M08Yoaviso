package app.m08yoaviso.BBDD;

import org.osmdroid.util.GeoPoint;

/**
 * Created by Moises on 03/04/2016.
 */
public class Aviso {

    private String avisoId;
    private Double latitud;
    private Double longitud;

    public Aviso() { }

    public Aviso(String avisoId, Double latitud, Double longitud) {
        this.avisoId = avisoId;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getAvisoId() {
        return avisoId;
    }

    public void setAvisoId(String avisoId) {
        this.avisoId = avisoId;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}

