package com.example.nero;

import PaqueteClasesPrincipales.Comentario;
import PaqueteClasesPrincipales.Fuente;
import PaqueteClasesPrincipales.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.security.KeyStore;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioTest {
    Usuario user;
    @Before
    public void init(){
        user = new Usuario("Admin","1234");
    }

    @After
    public void terminate(){
        user = null;
    }

    @Test
    public void comprobarBaneado(){
        assertEquals(null, user.getBaneado());
        user.setBaneado(true);
        assertEquals(true, user.getBaneado());
        user.setBaneado(false);
        assertEquals(false, user.getBaneado());
    }
    @Test
    public void comprobarEmail(){
        String nom = "Juan@prueba.com";
        user.setEmail(nom);
        assertEquals("Juan@prueba.com", user.getEmail());
    }
    @Test
    public void comprobarToString(){
        user.setEmail("Juan@prueba.com");
        user.setNombre("Juan");
        user.setBaneado(false);
        String cadena = ("Usuario con nickname: Admin, contrase�a: 1234, email: Juan@prueba.com, nombre real: Juany no baneado");
        assertEquals(cadena, user.toString());
    }

    @Test
    public void comprobarNombre(){
        String nom = "Juan";
        user.setNombre(nom);
        assertEquals("Juan", user.getNombre());
    }


    @Test
    public void comprobarUsuarioIgualIgnoreCase(){
        Usuario user2 = new Usuario("admin","1234");
        Boolean res1 = user2.equals(user);
        assertEquals(true, res1);
        Usuario user3 = new Usuario("Pepe", "raul");
        assertEquals(false, user.equals(user3));
    }

    @Test
    public void comprobarFuenteAsociada(){
        Fuente fuente = mock(Fuente.class);
        List<Fuente> misFuentes = user.getSusFuentes();
        assertEquals(true, misFuentes.isEmpty());
        user.anyadirFuente(fuente);
        misFuentes = user.getSusFuentes();
        assertEquals(true, misFuentes.contains(fuente));
    }

    @Test
    public void comprobarComentariosAsociados(){
        Comentario coment = mock(Comentario.class);
        List<Comentario> misComents = user.getComentarios();
        assertEquals(true, misComents.isEmpty());
        user.anyadirComentario(coment);
        misComents = user.getComentarios();
        assertEquals(true, misComents.contains(coment));
    }

}
