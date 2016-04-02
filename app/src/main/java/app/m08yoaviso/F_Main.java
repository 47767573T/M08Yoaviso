package app.m08yoaviso;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import app.m08yoaviso.BBDD.ReferenciaBD;
import app.m08yoaviso.BBDD.Yoaviso;

/**
 * A placeholder fragment containing a simple view.
 */
public class F_Main extends Fragment implements LocationListener, View.OnLongClickListener {


    //CONSTRUCTOR
    public F_Main() { }


    //Referencia a BBDD Firebase
    private Firebase ref;
    private String[] filtros;
    private int radioFiltros;

    //Variables de localización
    public Location lugarActual;
    LocationManager locManager;
    LocationListener locListener;

    //Variables para mapa
    private MapView map;
    private IMapController iMapController;
    private MyLocationNewOverlay mlno;
    private RadiusMarkerClusterer agrupacionAvisoMarkers;
    private ScaleBarOverlay scaleBarOverlay;


    //Variables para configuracion del mapa
    private int radioAgrupacion = 25; //metros para agrupar
    private boolean hayControlZoom = true;
    private boolean hayControlMultiTouch = true;
    private boolean hayCentradoInicial = true;
    private boolean hayPrecisionOverlay = true;
    private int zoomInicial = 15; //metros de zoom iniciales




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.lay_f_main, container, false);



        ReferenciaBD app = (ReferenciaBD) getActivity().getApplication();
        ref = app.getRef();
        map = (MapView) mainView.findViewById(R.id.mapView);
        map.setOnLongClickListener(this);
        zoomInicial = 100;


        //Configuraciones iniciales
        setMap();
        setZoom(zoomInicial);
        setOverlays(getContext());
        drawMarkers(getContext());
        ReferenciaBD refYoaviso = (ReferenciaBD) getActivity().getApplication();
        ref = refYoaviso.getRef();


        //Configuracion listeners
        setLocationListeners(getContext());


        //TODO borrar despues de definir los POJOs y comenzaar a grabar
        //Registros añadidos a Firebase para pruebas
        //addAvisosPrueba(10, ref);

        //TODO //fin de los registros de prueba




        return mainView;
    }

    public void setMap(){
        try {
            //Determinamos los tiles del mapa para pintarlo
            map.setTileSource(TileSourceFactory.MAPQUESTOSM);
            map.setTilesScaledToDpi(true);

            //Determinamos otras opciones del mapa
            map.setBuiltInZoomControls(hayControlZoom);
            map.setMultiTouchControls(hayControlMultiTouch);

        }catch(Exception ex){
            ex.printStackTrace();
            msgToast(2, "Dibujando el mapa inicial");
        }
    }

    public void setZoom(int zoom){
        try {
            iMapController = map.getController();
            iMapController.setZoom(zoom);

        }catch (Exception ex){
            ex.printStackTrace();
            iMapController.setZoom(zoomInicial);
            msgToast (2, "Ajustando el Zoom al inicial");
        }
    }

    public void setOverlays(Context context){
        final DisplayMetrics metricaDelMapa = getResources().getDisplayMetrics();
        mlno = new MyLocationNewOverlay(    context
                ,new GpsMyLocationProvider(context)
                ,map
        );

        mlno.enableMyLocation();
        mlno.setDrawAccuracyEnabled(hayPrecisionOverlay);
        mlno.runOnFirstFix(new Runnable() {
            @Override
            public void run() { iMapController.animateTo(mlno.getMyLocation()); }
        });

        //Setear la escala del mapa
        scaleBarOverlay = new ScaleBarOverlay(map);
        scaleBarOverlay.setCentred(hayCentradoInicial);
        scaleBarOverlay.setScaleBarOffset(metricaDelMapa.widthPixels / 2, 10);


        //Añadir los setters al overlay
        map.getOverlays().add(mlno);
        map.getOverlays().add(this.scaleBarOverlay);

    }

    public void drawMarkers (Context context){
        setAgrupacionMarkers(context, radioAgrupacion);

        Firebase notas = ref.child("prueba").child("Notas");
        notas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot notasFireBase : dataSnapshot.getChildren()) {
/*
                    Nota nota = notasFireBase.getValue(Nota.class);
                    Marker notaMarker = new Marker(map);
                    GeoPoint gp = new GeoPoint(nota.getLatitud(), nota.getLongitud());

                    notaMarker.setPosition(gp);
                    notaMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    notaMarker.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_info_details));
                    notaMarker.setTitle(nota.getTitulo());
                    notaMarker.setAlpha(0.7f);
*/
                    //agrupacionNotaMarkers.add(notaMarker);

                }
                agrupacionAvisoMarkers.invalidate();
                map.invalidate();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void setAgrupacionMarkers(Context context, int radio){
        agrupacionAvisoMarkers = new RadiusMarkerClusterer(context);
        map.getOverlays().add(agrupacionAvisoMarkers);

        Drawable clusterIconoDrawable = getResources().getDrawable(android.R.drawable.stat_sys_warning);
        int markerWidth = clusterIconoDrawable.getIntrinsicWidth();
        int markerHeight = clusterIconoDrawable.getIntrinsicHeight();
        clusterIconoDrawable.setBounds(0, markerHeight, markerWidth, 0);
        Bitmap clusterIconBm = ((BitmapDrawable) clusterIconoDrawable).getBitmap();

        agrupacionAvisoMarkers.setIcon(clusterIconBm);
        agrupacionAvisoMarkers.setRadius(radio);
    }

    public void addAvisosPrueba(int numVeces, Firebase firebase){
        for (int i = 0; i < numVeces ; i++) {




        }
    }

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

//METODOS PARA LOCATION LISTENER................................................................
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
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {msgToast(3, "Red Activada");}

    @Override
    public void onProviderDisabled(String provider) {msgToast(3, "Red Desactivada");}
//FIN DE METODOS PARA LOCATION LISTENER.........................................................

//METODOS PARA CLICK LISTENERS................................................................
    @Override
    public boolean onLongClick(View v) {

        Intent intentToGrabar = new Intent(getActivity().getApplication(), A_Grabar.class);
        startActivity(intentToGrabar);

        return false;
    }


}
