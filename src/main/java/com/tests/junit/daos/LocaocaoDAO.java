package com.tests.junit.daos;

import com.tests.junit.model.Locacao;

import java.util.List;

/**
 * @author Adriano Rabello 29/05/2022 16:17:34
 **/

public interface LocaocaoDAO {

    void salvar(Locacao locacao);

    List<Locacao> buscarLocacoesAtrasadas();
}
