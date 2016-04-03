package app.m08yoaviso;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import app.m08yoaviso.BBDD.Aviso;
import app.m08yoaviso.BBDD.ReferenciaBD;

/**
 * A placeholder fragment containing a simple view.
 */
public class F_Grabar extends Fragment implements View.OnClickListener, View.OnLongClickListener
        , LocationListener {

    private Firebase ref;
    private int numAzar;

    public Location lugarActual;
    LocationManager locManager;
    LocationListener locListener;

    private ImageButton btGrabar;
    private ImageButton btEscuchar;
    private ImageButton btParar;
    private Button btGuardar;
    private Button btRepetir;
    private ImageButton btAtras;

    private static String outputFile;
    private String nombreArchivo;
    private MediaRecorder miGrabador;

    public F_Grabar() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View grabarView = inflater.inflate(R.layout.lay_f_grabar, container, false);

        Random rnd = new Random();
        numAzar = rnd.nextInt(100);

        ReferenciaBD app = (ReferenciaBD) getActivity().getApplication();
        ref = app.getRef();

        SimpleDateFormat sdt = new SimpleDateFormat("EEEyyyyMMddhhmmss");
        Date fechaParaAudio = new Date();
        nombreArchivo = sdt.format(fechaParaAudio);

        asignarClickListenersABotones(grabarView);

        //Deshabilitamos los botones antes de grabar para evitar errores
        btParar.setVisibility(View.INVISIBLE);
        btEscuchar.setVisibility(View.INVISIBLE);
        btRepetir.setVisibility(View.INVISIBLE);
        btGuardar.setVisibility(View.INVISIBLE);

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+nombreArchivo+".3gp";
        msgToast(3, "Se guardará en " + "/"+nombreArchivo+".3gp");

        miGrabador = new MediaRecorder();
        miGrabador.setAudioSource(MediaRecorder.AudioSource.MIC);
        miGrabador.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        miGrabador.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        miGrabador.setOutputFile(outputFile);

        return grabarView;
    }

    public void asignarClickListenersABotones(View v) {

        btGrabar = (ImageButton) v.findViewById(R.id.imbtGrabar);
        btGrabar.setOnClickListener(this);
        btGrabar.setOnLongClickListener(this);

        btEscuchar = (ImageButton) v.findViewById(R.id.imbtEscuchar);
        btEscuchar.setOnClickListener(this);
        btEscuchar.setOnLongClickListener(this);

        btParar = (ImageButton) v.findViewById(R.id.imbtParar);
        btParar.setOnClickListener(this);
        btParar.setOnLongClickListener(this);

        btGuardar = (Button) v.findViewById(R.id.bttnGuardar);
        btGuardar.setOnClickListener(this);
        btGuardar.setOnLongClickListener(this);

        btRepetir = (Button) v.findViewById(R.id.bttnRepetir);
        btRepetir.setOnClickListener(this);
        btRepetir.setOnLongClickListener(this);

        btAtras = (ImageButton) v.findViewById(R.id.imbtAtras);
        btAtras.setOnClickListener(this);
        btAtras.setOnLongClickListener(this);

        setLocationListeners(getContext());
    }

//CLICK LISTENERS
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //CUANDO CLICKAMOS EN GRABAR
            case R.id.imbtGrabar:
                try {
                    miGrabador.prepare();
                    miGrabador.start();
                }

                catch (IllegalStateException e) {
                    msgToast(2, "Preparacion de grabar");
                    e.printStackTrace();
                }

                catch (IOException e) {
                    msgToast(2, "Grabando");
                    e.printStackTrace();
                }

                btGrabar.setVisibility(View.INVISIBLE); //Deshabilitamos el boton de grabar mientras grabe
                btParar.setVisibility(View.VISIBLE);   //Habilitamos solo el de parar

                msgToast(1, "Comienza grabación");
                break;

            //CUANDO CLICKAMOS EN PARAR
            case R.id.imbtParar:
                miGrabador.stop();
                miGrabador.release();
                miGrabador = null;

                btParar.setVisibility(View.INVISIBLE);  //deshabilitamos el boton de parar cuando pare de grabar
                btEscuchar.setVisibility(View.VISIBLE);    //Habilitamos el boton de escuchar cuando para la grabacion

                msgToast(1, "Audio guardado");
                break;

            //CUANDO CLICKAMOS EN ESCUCHAR
            case R.id.imbtEscuchar:
                MediaPlayer reproductorAudio = new MediaPlayer();

                try {
                    reproductorAudio.setDataSource(outputFile);
                    reproductorAudio.prepare();
                }

                catch (IOException e) {
                    msgToast(2, "Buscando y preparando archivo");
                    e.printStackTrace();
                }

                reproductorAudio.start();

                //Mostramos las opciones posibles tras grabar la voz
                btGrabar.setVisibility(View.INVISIBLE);
                btParar.setVisibility(View.INVISIBLE);

                btRepetir.setVisibility(View.VISIBLE);
                btGuardar.setVisibility(View.VISIBLE);

                msgToast(1, "Reproduciendo grabacion");
                break;

            case R.id.bttnGuardar:

                Aviso aviso = new Aviso(nombreArchivo+".3gp"
                        , lugarActual.getLatitude()
                        , lugarActual.getLongitude());

                ref.child("Avisos").child("Aviso"+numAzar).setValue(aviso);

                Intent intentGuardar = new Intent(this.getContext(), A_Main.class);
                startActivity(intentGuardar);

                break;

            case R.id.bttnRepetir:

                btParar.setVisibility(View.INVISIBLE);
                btEscuchar.setVisibility(View.INVISIBLE);
                btRepetir.setVisibility(View.INVISIBLE);
                btGuardar.setVisibility(View.INVISIBLE);

                break;

            case R.id.imbtAtras:

                Intent intentAtras = new Intent(this.getContext(), A_Main.class);
                startActivity(intentAtras);

                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
//FIN CLICK LISTENERS

//LOCATION LISTENERS...........................................................................

    public void setLocationListeners(Context context) {
        locManager = (LocationManager) getContext().getSystemService(context.LOCATION_SERVICE);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return ;
        }
        locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

    private int checkSelfPermission(String accessFineLocation) {return 0;}

    @Override
    public void onLocationChanged(Location location) {
        locListener = this;
        if (location!=null) {
            lugarActual = location;
        }else{
            msgToast(2, "No se encuentra Location");
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { msgToast(3, "Red Activada"); }

    @Override
    public void onProviderDisabled(String provider) { msgToast(3, "Red Desactivada"); }

//FIN LOCATION LISTENERS


    public void msgToast(int numTag, String msg) {
        switch (numTag) {
            case 1:
                Toast.makeText(getContext(), "PRUEBA: " + msg, Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(getContext(), "ERROR: " + msg, Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(getContext(), "INFO: " + msg, Toast.LENGTH_LONG).show();
                break;
        }
    }


}
