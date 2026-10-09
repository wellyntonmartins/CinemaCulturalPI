package br.com.cinemacultural.controller;

import br.com.cinemacultural.model.Filme;
import br.com.cinemacultural.service.FilmeService;

import java.util.List;

public class FilmeController {

    private final FilmeService filmeService;

    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    public List<Filme> listar() {
        return filmeService.listar();
    }

    public Filme buscar(long id) {
        return filmeService.buscar(id);
    }

    public Filme cadastrar(String titulo, String sinopse, String genero, int anoLancamento) {
        return filmeService.cadastrar(titulo, sinopse, genero, anoLancamento);
    }

    public Filme atualizar(long id, String titulo, String sinopse, String genero, int anoLancamento) {
        return filmeService.atualizar(id, titulo, sinopse, genero, anoLancamento);
    }

    public void excluir(long id) {
        filmeService.excluir(id);
    }
}
