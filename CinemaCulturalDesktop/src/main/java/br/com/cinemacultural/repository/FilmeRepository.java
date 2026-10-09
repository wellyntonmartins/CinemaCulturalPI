package br.com.cinemacultural.repository;

import br.com.cinemacultural.model.Filme;

import java.util.List;
import java.util.Optional;

public interface FilmeRepository {

    List<Filme> listar();

    Optional<Filme> buscarPorId(long id);

    Filme salvar(Filme filme);

    void excluir(long id);
}
