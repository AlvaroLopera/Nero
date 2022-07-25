package com.example.nero;

import org.junit.*;

import PaqueteClasesPrincipales.Comentario;
import PaqueteClasesPrincipales.Usuario;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class TestComentario {
    Comentario coment;
    Usuario user = mock(Usuario.class);

    @Before
    public void init(){
        when(user.getNombre_usuario()).thenReturn("UnNombreDeUsuario");
        coment = new Comentario(1234, user);
    }
    @After
    public void terminate(){
        coment = null;
    }

    @Test
    public void comprobarTextoDeUnComentario(){
        assertEquals(coment.getTexto(),null);
        coment.setTexto("Testeando feeling like a pro");
        assertEquals("Testeando feeling like a pro", coment.getTexto());
        coment.setTexto(null);
        assertEquals(null, coment.getTexto());

    }

    @Test
    public void comprobarEquals(){
        Comentario comentario = mock(Comentario.class);
        when(comentario.getId()).thenReturn(1234);
        assertEquals(true,coment.equals(comentario));
    }

    @Test
    public void comprobarFechaDeUnComentario(){
        assertEquals(coment.getFecha(),null);
        coment.setFecha("11/11/2011");
        assertEquals("11/11/2011",coment.getFecha());
        coment.setFecha(null);
        assertEquals(null,coment.getFecha());
    }

    @Test
    public void comprobarCreador(){

        assertEquals(coment.getCreador(),user);
    }
}
