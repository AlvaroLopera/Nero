package com.example.nero;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import PaqueteClasesPrincipales.Comentario;
import PaqueteReporteFactoryMethod.ReporteException;
import PaqueteReporteFactoryMethod.ReporteUsuario;




import PaqueteClasesPrincipales.Fuente;
import PaqueteClasesPrincipales.Ubicacion;
import PaqueteClasesPrincipales.Usuario;
import PaqueteReporteFactoryMethod.ReporteFactoryMethod;
import PaqueteReporteFactoryMethod.ReporteFuente;
import PaqueteReporteFactoryMethod.ReporteUsuario;

import static org.junit.Assert.assertEquals;

public class testReportUsuarios {

    ReporteUsuario ru;

    @Before
    public void init() {
        ru = new ReporteUsuario();
    }
    @After
    public void terminate() {
        ru = null;
    }

    @Test
    public void comprobarListaVacia(){
        Usuario usuario = mock(Usuario.class);
        assertEquals(0, ru.getLista().size());
    }

    @Test(expected = ReporteException.class)
    public void anyadirObjetoDeOtroTipoNoValido(){
        //Probamos con la clase Comentario pero podria probarse tambien con Fuente
        Comentario invalido = mock(Comentario.class);
        ru.anyadirReporte(invalido);
    }

    @Test
    public void anyadirObjetoDeEsteTipoValido(){
        Usuario usuario = mock(Usuario.class);
        try{
            ru.anyadirReporte(usuario);
        }catch(ReporteException ex){
            fail("No deberia lanzar excepcion");
        }
    }

    @Test
    public void comprobarSiEstaReportadoUnReporteValido(){
        Usuario usuario = mock(Usuario.class);
        when(usuario.getNombre_usuario()).thenReturn("NickDeMiUsuario");

        assertEquals(false, ru.estaReportado(usuario));
        ru.anyadirReporte(usuario);
        assertEquals(true, ru.estaReportado(usuario));
    }

    @Test(expected = ReporteException.class)
    public void comprobarSiEstaReportadoUnReporteNoValido(){
        Comentario comentario = mock(Comentario.class);
        ru.anyadirReporte(comentario);
    }

}
