package com.tests.junit.services;

import com.tests.junit.model.Filme;
import com.tests.junit.model.Locacao;
import com.tests.junit.model.Usuario;
import com.tests.junit.utils.DataUtils;

import java.util.Date;

/**
 * @author Adriano Rabello 27/05/2022 19:54:18
 **/
public class LocadoraService {

    public Locacao alugarFilme(Usuario usuario, Filme filme){

        return new Locacao().builder()
                .filme(new Filme().builder()
                        .nome("Rmabo-IV")
                        .build())
                .usuario(new Usuario().builder()
                        .nome("Adriano")
                        .build())
                .dataLocacao(new Date())
                .dataRetorno(DataUtils.adicionarDias(new Date(),1))
                .build();
    }

}
