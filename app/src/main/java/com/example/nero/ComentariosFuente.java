package com.example.nero;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import PaqueteClasesPrincipales.Comentario;
import PaqueteClasesPrincipales.Fuente;
import PaqueteClasesPrincipales.Usuario;
import PaqueteRequest.ComentariosRequest;
import PaqueteRequestQueueSingleton.SingletonRequestQueue;

public class ComentariosFuente extends Fragment {

    //Elementos de RecycleView
    private RVAdaptadorComentarios adaptador;
    private RecyclerView recyclerView;
    private List<Comentario> listaComentarios;

    //Otros elementos
    private FloatingActionButton btn_anyadirComentario;

    //Datos
    private Fuente thisFuente;
    private Usuario thisUser;


    public ComentariosFuente(Fuente f, Usuario u){
        thisFuente = f;
        thisUser = u;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_comentarios_fuente, container, false);

        //R
        recyclerView = (RecyclerView) vista.findViewById(R.id.recyclerComentario);
        btn_anyadirComentario = (FloatingActionButton) vista.findViewById(R.id.btn_anyadircomentario);

        //Eventos
        btn_anyadirComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AnyadirComentario.class);
                i.putExtra("PasarEsteUsuario", thisUser);
                i.putExtra("PasarEstaFuente", thisFuente);
                startActivity(i);
            }
        });

        //Ajustes y decoracion del recyclerView
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        DividerItemDecoration dv = new DividerItemDecoration(getContext(),llm.getOrientation());
        recyclerView.addItemDecoration(dv);

        //Inicialización
        listaComentarios = new ArrayList<>();

        //Volley
        obtenerComentarios();
        return vista;
    }

    private void obtenerComentarios(){
        Response.Listener<String> respuesta = new Response.Listener<String>(){
            @Override
            public void onResponse(String respuesta){
                System.out.println("-------------------OBTENEMOS RESPUESTA COMENTARIOS.");
                try{
                    JSONObject jsonRespuesta = new JSONObject(respuesta);

                    JSONArray conjuntoDeComentarios = jsonRespuesta.optJSONArray("comentario");
                    //Bug arreglado
                    if(conjuntoDeComentarios != null){
                        for(int i = 0; i < conjuntoDeComentarios.length(); i++){
                            JSONObject conjuntoIndiv = conjuntoDeComentarios.getJSONObject(i);
                            Usuario usr = new Usuario(conjuntoIndiv.optString("nombre_usuario"));
                            Comentario cmt = new Comentario(conjuntoIndiv.optInt("id"), usr);
                            cmt.setTexto(conjuntoIndiv.optString("texto"));
                            cmt.setFecha(conjuntoIndiv.optString("fecha"));
                            listaComentarios.add(cmt);
                        }
                    }
                    adaptador = new RVAdaptadorComentarios(listaComentarios, thisUser);
                    recyclerView.setAdapter(adaptador);
                } catch (JSONException e){
                    e.getMessage();
                }
            }
        };

        ComentariosRequest cr = new ComentariosRequest(respuesta, thisFuente.getId()+"");
        SingletonRequestQueue.getInstance(getContext()).addToRequestQueue(cr);
    }
}
