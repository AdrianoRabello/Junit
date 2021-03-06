package com.tests.junit.services;

import com.tests.junit.daos.LocaocaoDAO;
import com.tests.junit.exceptions.FilmeSemEstoqueException;
import com.tests.junit.exceptions.LocadoraException;
import com.tests.junit.model.Filme;
import com.tests.junit.model.Locacao;
import com.tests.junit.model.Usuario;
import com.tests.junit.utils.DataUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Adriano Rabello 27/05/2022 19:54:18
 **/
public class LocacaoService {

    private LocaocaoDAO locaocaoDAO;

    private SPCService spcService;

    private NotificacaoService notificacaoService;

    public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws LocadoraException, FilmeSemEstoqueException {


        if (Objects.isNull(filmes) || filmes.isEmpty()) {
            throw new LocadoraException("Pelo menos um filme deve ser informado");
        }

        if (filmes.stream().anyMatch(filme -> filme.getEstoque().equals(0))) {
            throw new FilmeSemEstoqueException("O estoque do filme deve ser maior que 0");
        }

        if (Objects.isNull(usuario)) {
            throw new LocadoraException("O usuário deve ser informado");
        }

        if (spcService.possuiNegativacao(usuario)) {
            throw new LocadoraException("O usuário está negativado");
        }

        Locacao locacao = new Locacao().builder()
                .filmes(filmes)
                .usuario(usuario)
                .valor(somarValoresFilmeComDesconto(filmes))
                .dataLocacao(obterData())
                .dataRetorno(criarDataDevolucao())
                .build();

        locaocaoDAO.salvar(locacao);
        return locacao;
    }

    protected Date obterData() {
        return new Date();
    }

    public void notificarLocacoesAtrasadas() {

        List<Locacao> locacoes = locaocaoDAO.buscarLocacoesAtrasadas();
        for (Locacao locacao : locacoes) {
            Usuario usuario = locacao.getUsuario();
            if (locacao.getDataRetorno().before(new Date()))
                notificacaoService.notificarAtraso(usuario);
        }
    }

    private Double somarValoresFilmeComDesconto(List<Filme> filmes) {
        Double valorTotal = 0d;
        for (int i = 0; i < filmes.size(); i++) {
            Filme filme = filmes.get(i);
            Double valorFilme = filme.getPrecoLocacao();

            switch (i) {
                case 2:
                    valorFilme = valorFilme * 0.75;
                    break;
                case 3:
                    valorFilme = valorFilme * 0.50;
                    break;
                case 4:
                    valorFilme = valorFilme * 0.25;
                    break;
                case 5:
                    valorFilme = 0d;
                    break;
            }
            valorTotal += valorFilme;
        }
        return valorTotal;
    }

    protected Date criarDataDevolucao() {
        Date dataAtual = obterData();
        return DataUtils.verificarDiaDaSemana(dataAtual, Calendar.SATURDAY) ? DataUtils.adicionarDias(dataAtual, 2) : DataUtils.adicionarDias(dataAtual, 1);
    }


    public void prorrogarLocacao(Locacao locacao, int dias) {
        Locacao novaLocacao = new Locacao();
        novaLocacao.setUsuario(locacao.getUsuario());
        novaLocacao.setFilmes(locacao.getFilmes());
        novaLocacao.setDataLocacao(new Date());
        novaLocacao.setDataRetorno(DataUtils.obterDataComDiferencaDias(dias));
        novaLocacao.setValor(locacao.getValor());
        novaLocacao.setValor(locacao.getValor() * dias);
        locaocaoDAO.salvar(novaLocacao);
    }


}
