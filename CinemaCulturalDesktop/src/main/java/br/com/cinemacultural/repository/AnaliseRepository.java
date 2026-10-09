package br.com.cinemacultural.repository;

import br.com.cinemacultural.model.Analise;

import java.util.List;
import java.util.Optional;

public interface AnaliseRepository {

    List<Analise> listarPorFilme(long filmeId);

    Optional<Analise> buscarPorId(long id);

    Analise salvar(Analise analise);

    void excluir(long id);

    void excluirPorFilme(long filmeId);
}
