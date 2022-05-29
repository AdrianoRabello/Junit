package com.tests.junit.builders;

import com.tests.junit.model.Usuario;

/**
 * @author Adriano Rabello 29/05/2022 14:16:40
 **/
public class UsuarioBuilder {


    private Usuario usuario;
    private UsuarioBuilder(){

    }

    public static UsuarioBuilder umUsuario(){
        UsuarioBuilder builder = new UsuarioBuilder();
        builder.usuario = new Usuario("Usuario 1 ");
        return builder;
    }

    public Usuario agora(){
        return usuario;
    }
}
