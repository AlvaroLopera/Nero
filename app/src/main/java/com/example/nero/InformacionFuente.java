package com.example.nero;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import PaqueteClasesPrincipales.Fuente;
import PaqueteClasesPrincipales.Usuario;


public class InformacionFuente extends Fragment {

    //Elementos de pantalla
    private TextView nombreFuente;
    private TextView creador;
    private TextView dispo;
    private TextView txt_reporteFuente;

    //Datos
    private Fuente fuente;
    private Usuario thisUser;

    public InformacionFuente(Fuente font, Usuario esteUsuario) {
        fuente = font;
        thisUser = esteUsuario;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_informacion_fuente, container, false);
        nombreFuente = (TextView) vista.findViewById(R.id.txtRNombreFuente);
        creador = (TextView) vista.findViewById(R.id.txtRCreador);
        dispo = (TextView) vista.findViewById(R.id.txtRDispo);
        txt_reporteFuente = (TextView) vista.findViewById(R.id.txtReporteFuente);

        nombreFuente.setText(fuente.getNombre());
        creador.setText(fuente.getCreador().getNombre_usuario());
        dispo.setText(fuente.getDisponibilidad());

        if(fuente.getCreador().getNombre_usuario().equalsIgnoreCase(thisUser.getNombre_usuario())){
            txt_reporteFuente.setText("Cambiar mi fuente");
        }

        txt_reporteFuente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fuente.getCreador().getNombre_usuario().equalsIgnoreCase(thisUser.getNombre_usuario())){
                    //Nos mandaria a un menu de ajustes
                } else {
                    new MaterialAlertDialogBuilder(txt_reporteFuente.getContext())
                            .setTitle("Realizar reporte")
                            .setMessage("Desea realizar un reporte a esta fuente?")
                            .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //No hacemos nada
                                }
                            })
                            .setPositiveButton(getContext().getResources().getString(R.string.dialog_report_accept), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(getContext(), AnyadirReporteFuente.class);
                                    i.putExtra("PasarEsteUsuario", thisUser);
                                    i.putExtra("PasarEstaFuente", fuente);
                                    getContext().startActivity(i);
                                }
                            })
                            .show();
                }
            }
        });
        return vista;
    }
}
