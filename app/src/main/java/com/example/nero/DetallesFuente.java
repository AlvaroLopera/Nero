package com.example.nero;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import PaqueteClasesPrincipales.Fuente;
import PaqueteClasesPrincipales.Usuario;

public class DetallesFuente extends AppCompatActivity {

    //Elementos de la pantalla
    private ViewPager paginas;
    private TabLayout barra;
    //Fragmentos
    private InformacionFuente informacionFuente;
    private ComentariosFuente comentariosFuente;
    //Datos
    private Fuente thisFuente;
    private Usuario thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_fuente);

        //R
        paginas = (ViewPager) findViewById(R.id.pagina);
        barra = (TabLayout) findViewById(R.id.tabs);

        //Obtención de datos de la activity anterior
        obtenerDatos();

        //Fragments y su configuración
        informacionFuente = new InformacionFuente(thisFuente, thisUser);
        comentariosFuente = new ComentariosFuente(thisFuente, thisUser);

        barra.setupWithViewPager(paginas);
        DetallesFuente.ViewPagerAdapter vpa = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        vpa.addFragment(informacionFuente,"Información");
        vpa.addFragment(comentariosFuente, "Comentarios");
        paginas.setAdapter(vpa);


    }
    //Fragments
    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentNombre = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);

        }

        public void addFragment(Fragment fragment, String nombre){
            fragments.add(fragment);
            fragmentNombre.add(nombre);
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentNombre.get(position);
        }
    }

    //-----------OBTENCIÓN DE LOS DATOS DE LA FUENTE DE LA ACTIVITY ANTERIOR----------
    void obtenerDatos(){
        Bundle extras = getIntent().getExtras();
        thisFuente = (Fuente) extras.getSerializable("PasarEstaFuente");
        thisUser = (Usuario) extras.getSerializable("PasarEsteUsuario");
    }
}
