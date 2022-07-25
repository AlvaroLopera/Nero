package com.example.nero;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;

import org.json.JSONException;
import org.json.JSONObject;

import PaqueteClasesPrincipales.Usuario;
import PaqueteRequest.AnyadirFuenteRequest;
import PaqueteRequestQueueSingleton.SingletonRequestQueue;

public class AnyadirFuente extends FragmentActivity implements OnMapReadyCallback, OnMarkerDragListener, TimePickerDialog.OnTimeSetListener {

    //Constantes
    private static final int MAXPROX = 100000;
    private static final float DEFAULT_ZOOM = 15;

    //Elementos de la pantalla
    private Button boton_AF;
    private Switch disponibilidad;
    private Switch condiciones;
    private GoogleMap mapa;
    private EditText nombreFuente;
    private Button btn_hora_inicio;
    private TextView apertura;
    private TextView cierre;
    private Button botoncierre;
    private Button buttonApertura;
    private boolean timePicker1;

    //Datos
    private Double latitud; // fuente a crear
    private Double longitud; // fuente a crear
    private Double latUser;
    private Double lonUser;
    private Marker fuente;
    private Usuario thisUser;
    private String nomUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Default
        obtenerDatosUsuario();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_fuente);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //final Button button = (Button) findViewById(R.id.button);
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(), "time picker");

            }
        });
        */
        //---------R
        boton_AF = (Button) findViewById(R.id.button2);
        nombreFuente = (EditText) findViewById(R.id.editText);
        disponibilidad = (Switch) findViewById(R.id.switch3);
        condiciones = (Switch) findViewById(R.id.switch2);
        botoncierre = (Button) findViewById(R.id.button3);
        botoncierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker1 = false;
                DialogFragment timePicker2 = new TimePicker();
                timePicker2.show(getSupportFragmentManager(), "time picker");
            }
        });
        apertura = (TextView) findViewById(R.id.textView2);
        cierre = (TextView) findViewById(R.id.textView3);
        buttonApertura = (Button) findViewById(R.id.button);
        buttonApertura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker1 = true;
                DialogFragment timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(), "time picker");

            }
        });

        //----------Eventos
        disponibilidad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(AnyadirFuente.this,"Siempre",Toast.LENGTH_SHORT).show();
                    apertura.setVisibility(View.INVISIBLE);
                    cierre.setVisibility(View.INVISIBLE);
                    buttonApertura.setClickable(false);
                    botoncierre.setClickable(false);
                } else {
                    Toast.makeText(AnyadirFuente.this,"Seleccione horario", Toast.LENGTH_SHORT).show();
                    apertura.setVisibility(View.VISIBLE);
                    cierre.setVisibility(View.VISIBLE);
                    buttonApertura.setClickable(true);
                    botoncierre.setClickable(true);
                }
            }
        });

        boton_AF.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String dispo;
                if (disponibilidad.isChecked()){
                    dispo = "Siempre";
                }else{

                    dispo = "Disponible de: "+apertura.getText().toString()+" a "+cierre.getText().toString(); // Disponibilidad de 00:00 a 17:00.
                }
                if(condiciones.isChecked()){
                    usarVolley(dispo);
                }else{
                    Toast.makeText(AnyadirFuente.this, "Error: Debe aceptar las condiciones", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // Se especifica el texto a mostrar cuando se selecciona hora en el TimePicker

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        if(timePicker1){
            TextView textView = (TextView) findViewById(R.id.textView2);
            if (minute <= 9) {
                textView.setText(hourOfDay + ":0" + minute);
            } else {
                textView.setText(hourOfDay + ":" + minute);
            }
        }else{
            TextView textView = (TextView) findViewById(R.id.textView3);
            if (minute <= 9) {
                textView.setText(hourOfDay + ":0" + minute);
            } else {
                textView.setText(hourOfDay + ":" + minute);
            }
        }

    }

    //-----------MAPA
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Default
        mapa = googleMap;

        //----------LOCALIZACIÓN DEL USUARIO
        LatLng indiv = new LatLng(latUser, lonUser);

        //----------MARKER FUENTE
        fuente = mapa.addMarker(new MarkerOptions().position(indiv).draggable(true));
        googleMap.setOnMarkerDragListener(this);

        //----------CONFIGURACIONES DEL MAPA
        CircleOptions circulo = new CircleOptions();
        circulo.center(indiv);
        circulo.radius(MAXPROX);
        circulo.visible(true);
        mapa.addCircle(circulo);

        mapa.getUiSettings().setMyLocationButtonEnabled(false);
        mapa.setMyLocationEnabled(true); //Se presupone que los permisos ya han sido aceptados
        mapa.setMapType(2); //Tipo de mapa a satelite
        mapa.setMinZoomPreference(15);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(indiv, DEFAULT_ZOOM));
    }

    //-----------VOLLEY(Java -> PHP -> BD) -> (BD -> PHP -> JAVA)
    private void usarVolley(String dispo){
        latitud = fuente.getPosition().latitude;
        longitud = fuente.getPosition().longitude;
        nomUsuario = thisUser.getNombre_usuario();
        
        if(checkProximity(latitud, longitud, latUser, lonUser)){
            Response.Listener<String> respuesta = new Response.Listener<String>(){
                @Override
                public void onResponse(String respuesta){
                    try{
                        JSONObject jsonRespuesta = new JSONObject(respuesta);
                        if(jsonRespuesta.getBoolean("success")){
                            Toast.makeText(AnyadirFuente.this, "Insertado correctamente.", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(AnyadirFuente.this, "Error al insertar la fuente.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e){
                        e.getMessage();
                    }
                }
            };

            String lati = Double.toString(latitud);
            String lon = Double.toString(longitud);

            AnyadirFuenteRequest afr = new AnyadirFuenteRequest(nomUsuario, lati, lon, dispo, nombreFuente.getText().toString(),respuesta);
            SingletonRequestQueue.getInstance(AnyadirFuente.this).addToRequestQueue(afr);
        }else{
            Toast.makeText(AnyadirFuente.this, "Error: Fuera de rango", Toast.LENGTH_SHORT).show();

        }

    }


    //---------CALCULO DE DISTANCIAS ENTRE 2 PUNTOS
    private Boolean checkProximity(double latitud, double longitud, double latUser, double lonUser){
        int dist = FormulaHeversine(latitud, longitud, latUser, lonUser);
        Boolean res = dist < MAXPROX;
        return res;
    }

    private static int FormulaHeversine(double lon1, double lat1,
                                                           double lon2, double lat2) {

        double earthRadius = 6371; // km

        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double dlon = (lon2 - lon1);
        double dlat = (lat2 - lat1);

        double sinlat = Math.sin(dlat / 2);
        double sinlon = Math.sin(dlon / 2);

        double a = (sinlat * sinlat) + Math.cos(lat1)*Math.cos(lat2)*(sinlon*sinlon);
        double c = 2 * Math.asin(Math.min(1.0, Math.sqrt(a)));

        double distanceInMeters = earthRadius * c * 1000;

        return (int) distanceInMeters;
    }


    //---------OBTENCIÓN DE DATOS DE LA ACTIVITY ANTERIOR
    private void obtenerDatosUsuario(){
        Bundle extras = getIntent().getExtras();
        thisUser = (Usuario) extras.getSerializable("PasarEsteUsuario");
        latUser = (Double) extras.getDouble("latitud");
        lonUser = (Double) extras.getDouble("longitud");
    }


    //----------EVENTOS DEL MARKER (fuente)
    @Override
    public void onMarkerDragStart(Marker marker) {
        //Default
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        //Default
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        fuente.setPosition(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude));
    }

}
