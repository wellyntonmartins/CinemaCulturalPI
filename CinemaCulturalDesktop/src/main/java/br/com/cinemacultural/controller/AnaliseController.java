package br.com.cinemacultural.controller;

import br.com.cinemacultural.model.Analise;
import br.com.cinemacultural.model.Filme;
import br.com.cinemacultural.service.AnaliseService;
import br.com.cinemacultural.service.FilmeService;

import java.util.List;

public class AnaliseController {

    private final AnaliseService analiseService;
    private final FilmeService filmeService;

    public AnaliseController(AnaliseService analiseService, FilmeService filmeService) {
        this.analiseService = analiseService;
        this.filmeService = filmeService;
    }

    public List<Filme> listarFilmes() {
        return filmeService.listar();
    }

    public List<Analise> listarPorFilme(long filmeId) {
        return analiseService.listarPorFilme(filmeId);
    }

    public Analise buscar(long id) {
        return analiseService.buscar(id);
    }

    public Analise cadastrar(long filmeId, String texto, double nota) {
        return analiseService.cadastrar(filmeId, texto, nota);
    }

    public Analise atualizar(long id, long filmeId, String texto, double nota) {
        return analiseService.atualizar(id, filmeId, texto, nota);
    }

    public void excluir(long id) {
        analiseService.excluir(id);
    }
}
