package com.example.nero;

import PaqueteClasesPrincipales.Comentario;
import PaqueteClasesPrincipales.Fuente;
import PaqueteClasesPrincipales.Ubicacion;
import PaqueteClasesPrincipales.Usuario;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.security.KeyStore;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class FuenteTest {
    Fuente fuente;
    Ubicacion ubi = mock(Ubicacion.class);
    @Before
    public void init(){
        fuente = new Fuente(1, ubi);
    }

    @After
    public void terminate(){fuente = null; ubi = null;}

    @Test
    public void comprobarID(){
        fuente.getId();
    }
    @Test
    public void comprobarUbicacion(){
        fuente.getUbicacion();
    }
    @Test
    public void comprobarDisponibilidad(){
        assertEquals(null, fuente.getDisponibilidad());
        fuente.setDisponibilidad("DE TARDE");
        assertEquals("DE TARDE", fuente.getDisponibilidad());
    }
    @Test
    public void comprobarHabilitado(){
        assertEquals(null, fuente.getInhabilitado());
        fuente.setInhabilitado(true);
        assertEquals(true , fuente.getInhabilitado());
        fuente.setInhabilitado(false);
        assertEquals(false, fuente.getInhabilitado());
    }

    @Test
    public void comprobarCreador(){
        assertEquals(null, fuente.getCreador());
        Usuario user = mock(Usuario.class);
        fuente.setCreador(user);
        assertEquals(user, fuente.getCreador());
        fuente.setCreador(null);
        assertEquals(null, fuente.getCreador());
    }
    @Test
    public void comprobarEqual(){
        Ubicacion ubi2 = mock(Ubicacion.class);
        Fuente fuente2 = new Fuente(2, ubi2);
        Boolean res = fuente.equals(fuente2);
        assertEquals(false, res);
        fuente2.setUbicacion(ubi);
        res = fuente.equals(fuente2);
        assertEquals(false, res);
        res = fuente.equals(fuente);
        assertEquals(true, res);
    }
    @Test
    public void comprobarToString(){
        fuente.setDisponibilidad("total");
        fuente.setInhabilitado(false);
        Usuario user = mock(Usuario.class);
        fuente.setCreador(user);
        String cadena = "Fuente en "+ubi.toString()+", con disponibilidad descrita tal que: total, creada por el usuario: "+user;
        cadena += " y que se encuentra habilitada actualmente.";

        assertEquals(cadena, fuente.toString());
    }
    @Test
    public void comprobarComentariosAsociados(){
        Comentario coment = mock(Comentario.class);
        List<Comentario> misComents = fuente.getComentarios();
        assertEquals(true, misComents.isEmpty());
        fuente.anyadirComentario(coment);
        misComents = fuente.getComentarios();
        assertEquals(true, misComents.contains(coment));
    }

}
