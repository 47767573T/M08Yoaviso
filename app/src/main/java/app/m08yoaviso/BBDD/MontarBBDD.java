package app.m08yoaviso.BBDD;

import org.osmdroid.util.GeoPoint;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by 47767573t on 24/02/16.
 */
public class MontarBBDD {

    Yoaviso yoa;

    public Yoaviso paraTest(){

        //AVISOS
        Aviso aviso1 = new Aviso (1, new GeoPoint(41.419628, 2.167911));
        Aviso aviso2 = new Aviso (2, new GeoPoint(41.414234, 2.151741));
        Aviso aviso3 = new Aviso (3, new GeoPoint(41.408419, 2.190320));
        Aviso aviso4 = new Aviso (4, new GeoPoint(41.424905, 1.886925));
        Aviso aviso5 = new Aviso (5, new GeoPoint(41.398344, 2.203170));

        //USUARIOS
        //usuario1
        Usuario usuario1 = new Usuario ("user1");
        Map<Integer, String> favoritos1 = new TreeMap<> ();
        favoritos1.put(2, "descripcion Aviso2");
        favoritos1.put(3, "descripcion Aviso3");

        usuario1.setFavoritos(favoritos1);
        Map<Integer, Boolean> avisos1 = new TreeMap<>();
        avisos1.put(1, true);

        //usuario2
        Usuario usuario2 = new Usuario ("user2");
        Map<Integer, String> favoritos2 = new TreeMap<>();
        favoritos2.put(3, "descripcion Aviso3");
        favoritos2.put(4, "descripcion Aviso4");

        usuario2.setFavoritos(favoritos2);
        Map<Integer, Boolean> avisos2 = new TreeMap<>();
        avisos2.put(2, true);
        avisos2.put(3, false);

        //usuario3
        Usuario usuario3 = new Usuario ("user3");
        Map<Integer, String> favoritos3 = new TreeMap<>();
        favoritos3.put(1, "descripcion Aviso1");
        favoritos1.put(5, "descripcion Aviso5");

        usuario3.setFavoritos(favoritos3);
        Map<Integer, Boolean> avisos3 = new TreeMap<>();
        avisos2.put(4, true);
        avisos2.put(5, false);


        //YOAVISO
        Yoaviso yoa = new Yoaviso();

        Map<Integer, Aviso> avisosTotal = new TreeMap<>();
        avisosTotal.put(1, aviso1);
        avisosTotal.put(2, aviso2);
        avisosTotal.put(3, aviso3);
        avisosTotal.put(4, aviso4);
        avisosTotal.put(5, aviso5);

        Map<Integer, Usuario> usuariosTotal = new TreeMap<>();
        usuariosTotal.put(1, usuario1);
        usuariosTotal.put(2, usuario2);
        usuariosTotal.put(3, usuario3);

        return yoa;
    }


}
