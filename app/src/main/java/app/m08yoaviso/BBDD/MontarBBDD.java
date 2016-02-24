package app.m08yoaviso.BBDD;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by 47767573t on 24/02/16.
 */
public class MontarBBDD {

    Yoaviso yoa;

    //Usuario
    Map<Integer,String> favoritoDeUsuarios = new TreeMap<>();
    Map<Integer,String> avisoDeUsuarios = new TreeMap<>();


    public Yoaviso paraTest(){

        //Montamos usuarios
        favoritoDeUsuarios.put(1,"Banco roto");
        


        return yoa;
    }


}
