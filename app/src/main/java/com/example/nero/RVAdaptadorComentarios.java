package com.example.nero;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import PaqueteClasesPrincipales.Comentario;
import PaqueteClasesPrincipales.Usuario;
import PaqueteReporteFactoryMethod.ReporteFactoryMethod;

public class RVAdaptadorComentarios extends RecyclerView.Adapter<RVAdaptadorComentarios.ViewHolder> {
    public List<Comentario> listaComentario;
    public static Usuario thisUser;


    public static class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView nombre_usuario, fecha, comentario;
        private ImageView foto;
        private MaterialButton btn_reportar;
        private Comentario thisComentario;

        public ViewHolder(View itemView){
            super(itemView);
            nombre_usuario = (TextView) itemView.findViewById(R.id.txtRNombre_usuario);
            fecha = (TextView) itemView.findViewById(R.id.txtRFecha);
            comentario = (TextView) itemView.findViewById(R.id.txtRComentario);
            foto = (ImageView) itemView.findViewById(R.id.imgRFoto);

            btn_reportar = (MaterialButton) itemView.findViewById(R.id.btn_reportar);

            //Eventos
            btn_reportar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MaterialAlertDialogBuilder(btn_reportar.getContext())
                            .setTitle("Realizar reporte")
                            .setMessage("Desea realizar un reporte a este comentario?")
                            .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //No pasa nada
                                }
                            })
                            .setPositiveButton(btn_reportar.getContext().getResources().getString(R.string.dialog_report_accept), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(btn_reportar.getContext(), AnyadirReporteComentario.class);
                                    i.putExtra("PasarEsteUsuario", RVAdaptadorComentarios.thisUser);
                                    i.putExtra("PasarEsteComentario", thisComentario);
                                    btn_reportar.getContext().startActivity(i);
                                }
                            })
                            .show();
                }
            });

            nombre_usuario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MaterialAlertDialogBuilder(nombre_usuario.getContext())
                            .setTitle("Realizar reporte")
                            .setMessage("Desea realizar un reporte al usuario: "+nombre_usuario.getText().toString())
                            .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //No pasa nada
                                }
                            })
                            .setPositiveButton(nombre_usuario.getContext().getResources().getString(R.string.dialog_report_accept), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(nombre_usuario.getContext(),anyadir_reporte_usuario.class);
                                    i.putExtra("PasarEsteUsuario", RVAdaptadorComentarios.thisUser);
                                    i.putExtra("PasarReportado", new Usuario(nombre_usuario.getText().toString()));
                                    nombre_usuario.getContext().startActivity(i);
                                }
                            })
                            .show();
                }
            });
        }
    }

    public RVAdaptadorComentarios(List<Comentario> listaComentario, Usuario esteUsuario) {
        this.listaComentario = listaComentario;
        thisUser = esteUsuario;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comentario,parent,false);
        ViewHolder viewHolder = new ViewHolder(vista);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nombre_usuario.setText(listaComentario.get(position).getCreador().getNombre_usuario());
        holder.fecha.setText(listaComentario.get(position).getFecha());
        //holder.foto.setImageResource(listaComentario.get(position));
        holder.comentario.setText(listaComentario.get(position).getTexto());
        //Para evitar reportarnos a nosotros mismos
        if(listaComentario.get(position).getCreador().getNombre_usuario().equalsIgnoreCase(thisUser.getNombre_usuario())){
            holder.btn_reportar.setVisibility(View.INVISIBLE);
        }
        holder.thisComentario = listaComentario.get(position);
    }

    @Override
    public int getItemCount() {
        return listaComentario.size();
    }


}
