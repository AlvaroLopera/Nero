package com.example.nero;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import PaqueteClasesPrincipales.Fuente;
import PaqueteClasesPrincipales.Ubicacion;
import PaqueteClasesPrincipales.Usuario;
import PaqueteRequest.MapaRequest;
import PaqueteRequestQueueSingleton.SingletonRequestQueue;

public class  activity_maps extends FragmentActivity implements OnMapReadyCallback, OnMyLocationButtonClickListener, GoogleMap.OnMarkerClickListener {

    //Mapa y localizacion
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

    //Constantes y codigos
    private static final float DEFAULT_ZOOM = 15f;
    private final static int CODIGO_DE_PERMISO_LOCALIZACION = 123;

    //Elementos de la pantalla
    private Button btn_anyadirFuente;

    //Datos
    private Usuario thisUser;
    private Double latitudThisUser;
    private Double longitudThisUser;
    private List<Fuente> fuentes;

    //--------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //---------R
        btn_anyadirFuente = (Button) findViewById(R.id.btn_anyadirfuente);

        //----------Obtencion de datos de la Activity anterior
        obtenerDatosUsuario();
        Toast.makeText(this,"Hola "+thisUser.getNombre(), Toast.LENGTH_LONG).show();


        //----------Inicializacion de localización y de la lista de fuentes
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fuentes = new ArrayList<>();

        //----------Eventos
        btn_anyadirFuente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AnyadirFuente.class);
                intent.putExtra("PasarEsteUsuario", thisUser);
                intent.putExtra("latitud", latitudThisUser);
                intent.putExtra("longitud", longitudThisUser);
                startActivity(intent);
            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Default
        mMap = googleMap;


        //--------------------PERMISOS DE LOCALIZACION-------------------
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //Si ya tenemos los permisos, agregamos las funciones GPS
            agregarFuncionesGPS();
        } else {
            //Si no tenemos los permisos, preguntaremos por los permisos
            //Nota: Una vez el usuario seleccione ACEPTAR o DENEGAR se llama a onRequestPermissionsResult
            //automaticamente (ver abajo)
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //Lo dejamos por Default
            } else {
                //Pedimos los permisos
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        CODIGO_DE_PERMISO_LOCALIZACION);
            }

        }


        //---------------OBTENCIÓN Y COLOCACIÓN DE LAS FUENTES------------
        obtenerFuentes();
        //----------------------------EVENTOS-----------------------
        googleMap.setOnMarkerClickListener(this);
    }


    //------------------------LOCALIZACIÓN Y PERMISOS-----------------------------
    //Permisos de aplicación (En este caso, solo hay de localización)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODIGO_DE_PERMISO_LOCALIZACION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Si los permisos han sido dados, obtenemos la posicion GPS
                    agregarFuncionesGPS();
                } else {
                    Toast.makeText(this, "Error: Permisos denegados.", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }

    //Activación del GPS en el mapa y obtención de la ubicación
    private void agregarFuncionesGPS(){
        mMap.setMyLocationEnabled(true);

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    latitudThisUser = location.getLatitude();
                    longitudThisUser = location.getLongitude();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(location.getLatitude(), location.getLongitude()),
                            DEFAULT_ZOOM));
                }
            }
        });
    }


    //----------------------------OTROS EVENTOS-------------------------------------
    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        boolean salir = false;
        int i = 0;
        while(!salir && i<fuentes.size()){
            if(marker.getPosition().latitude == fuentes.get(i).getUbicacion().getLatitud() &&
               marker.getPosition().longitude == fuentes.get(i).getUbicacion().getLongitud()){
                Intent intent = new Intent(getApplicationContext(), DetallesFuente.class);
                intent.putExtra("PasarEstaFuente", fuentes.get(i));
                intent.putExtra("PasarEsteUsuario", thisUser);
                startActivity(intent);
                salir = true;
            }
            i++;
        }
        return false;
    }


    //------------------------OBTENCIÓN DE DATOS-----------------------------------
    //Obtención de datos del usuario de la Activity anterior
    private void obtenerDatosUsuario(){
        Bundle extras = getIntent().getExtras();
        thisUser = (Usuario) extras.getSerializable("PasarEsteUsuario");
    }

    //Obtención de las fuentes (usando Volley)
    private void obtenerFuentes(){
        Response.Listener<String> respuesta = new Response.Listener<String>(){
            @Override
            public void onResponse(String respuesta){
                try{
                    JSONObject jsonRespuesta = new JSONObject(respuesta);

                    JSONArray conjuntoDeFuentes = jsonRespuesta.optJSONArray("fuente");
                    if(conjuntoDeFuentes != null){
                        for(int i = 0; i < conjuntoDeFuentes.length(); i++){
                            JSONObject conjuntoIndiv = conjuntoDeFuentes.getJSONObject(i);
                            LatLng indivLL = new LatLng(conjuntoIndiv.optDouble("pos_latitud"), conjuntoIndiv.optDouble("pos_longitud"));
                            Fuente fuente = new Fuente(conjuntoIndiv.optInt("id"), new Ubicacion(indivLL.latitude,indivLL.longitude));
                            Usuario creador = new Usuario(conjuntoIndiv.optString("nombre_usuario"));
                            fuente.setCreador(creador);
                            String n = conjuntoIndiv.optString("nombre");
                            if(n.equalsIgnoreCase("null"))
                                n = "Fuente sin nombre";
                            fuente.setNombre(n);
                            fuente.setDisponibilidad(conjuntoIndiv.optString("disponibilidad"));
                            fuentes.add(fuente);
                            mMap.addMarker(new MarkerOptions()
                                    .position(indivLL)
                                    .title(""+(i+1))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                                            /*.fromResource(R.drawable.)));*/
                        }
                    }
                } catch (JSONException e){
                    e.getMessage();
                }
            }
        };

        MapaRequest mr = new MapaRequest(respuesta);
        /*RequestQueue cola = Volley.newRequestQueue(activity_maps.this);
        cola.add(lr);*/
        SingletonRequestQueue.getInstance(activity_maps.this).addToRequestQueue(mr);
    }


}


//INFO:
//ej de la clase abstracta: https://github.com/googlemaps/android-samples/blob/master/ApiDemos/java/app/src/main/java/com/example/mapdemo/PermissionUtils.java
//ej de localizacion API google maps: https://developers.google.com/maps/documentation/android-sdk/location?hl=es-419#runtime-permission
//ASI ES COMO LO HAREMOS .ej de permisos: https://developer.android.com/training/permissions/requesting?hl=es-419