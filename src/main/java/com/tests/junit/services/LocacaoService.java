package com.tests.junit.services;

import com.tests.junit.exceptions.FilmeSemEstoqueException;
import com.tests.junit.exceptions.LocadoraException;
import com.tests.junit.model.Filme;
import com.tests.junit.model.Locacao;
import com.tests.junit.model.Usuario;
import com.tests.junit.utils.DataUtils;
import org.springframework.core.io.buffer.DataBufferUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Adriano Rabello 27/05/2022 19:54:18
 **/
public class LocacaoService {

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

        Locacao locacao  = new Locacao().builder()
                .filmes(filmes)
                .usuario(usuario)
                .valor(somarValoresFilmeComDesconto(filmes))
                .dataLocacao(new Date())
                .dataRetorno(criarDataDevolucao())
                .build();

        return locacao;
    }

    private Double somarValoresFilmeComDesconto(List<Filme> filmes) {


        Double valorTotal = 0d;
        for (int i = 0; i < filmes.size(); i++) {
            Filme filme = filmes.get(i);
            Double valorFilme = filme.getPrecoLocacao();

            /** Com if  */
            if (i == 2) {
                valorFilme = valorFilme * 0.75;
            }

            if (i == 3) {
                valorFilme = valorFilme * 0.50;
            }

            if (i == 4) {
                valorFilme = valorFilme * 0.25;
            }

            if (i == 5) {
                valorFilme = 0d;
            }


            /** Com switch */

//            switch (i) {
//                case 2:
//                    valorFilme = valorFilme * 0.75;
//                    break;
//                case 3:
//                    valorFilme = valorFilme * 0.50;
//                    break;
//                case 4:
//                    valorFilme = valorFilme * 0.25;
//                    break;
//                case 5:
//                    valorFilme = 0d;
//                    break;
//            }


            valorTotal += valorFilme;

        }

        return valorTotal;
    }

    private double somarValoresDosFimes(List<Filme> filmes) {
        return filmes.stream().map(x -> x.getPrecoLocacao())
                .reduce((a, b) -> a + b).orElse(0d);
    }

    private Date criarDataDevolucao() {
        Date dataAtual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataAtual);
        Date dataEntrega  = DataUtils.verificarDiaDaSemana(dataAtual, Calendar.SATURDAY) ? DataUtils.adicionarDias(dataAtual, 2) : DataUtils.adicionarDias(dataAtual, 1);
        return dataEntrega;
    }

}
