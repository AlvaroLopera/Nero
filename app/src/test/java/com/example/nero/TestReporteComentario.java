package com.example.nero;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import PaqueteClasesPrincipales.Comentario;
import PaqueteClasesPrincipales.Usuario;
import PaqueteReporteFactoryMethod.ReporteComentario;
import PaqueteReporteFactoryMethod.ReporteException;

public class TestReporteComentario {
    ReporteComentario reportComentario;

    @Before
    public void init(){
        reportComentario = new ReporteComentario();
    }
    @After
    public void terminate(){
        reportComentario=null;
    }

    @Test
    public void comprobarSiListaVacia(){
        Comentario comentario = mock(Comentario.class);
        assertEquals(0,reportComentario.getLista().size());
    }

    @Test(expected = ReporteException.class)
    public void anyadirObjetoDeOtroTipoNoValido(){
        //Probamos con la clase Usuario pero podria probarse tambien con Fuente
        Usuario otroTipoDeObjeto = mock(Usuario.class);
        reportComentario.anyadirReporte(otroTipoDeObjeto);
    }

    @Test
    public void anyadirObjetoDeEsteTipoValido(){
        Comentario comentario = mock(Comentario.class);
        try{
            reportComentario.anyadirReporte(comentario);
        }catch(ReporteException ex){
            fail("No deberia lanzar excepcion");
        }
    }

    @Test
    public void comprobarSiEstaReportadoUnReporteValido(){
        Comentario comentario = mock(Comentario.class);
        when(comentario.getId()).thenReturn(200);

        assertEquals(false, reportComentario.estaReportado(comentario));
        reportComentario.anyadirReporte(comentario);
        assertEquals(true, reportComentario.estaReportado(comentario));
    }

    @Test(expected = ReporteException.class)
    public void comprobarSiEstaReportadoUnReporteNoValido(){
        Usuario usuario = mock(Usuario.class);
        reportComentario.anyadirReporte(usuario);
    }
}
