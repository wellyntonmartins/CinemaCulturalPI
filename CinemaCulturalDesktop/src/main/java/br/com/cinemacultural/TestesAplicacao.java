package br.com.cinemacultural;

import br.com.cinemacultural.model.Analise;
import br.com.cinemacultural.model.Filme;
import br.com.cinemacultural.repository.AnaliseRepository;
import br.com.cinemacultural.repository.FilmeRepository;
import br.com.cinemacultural.repository.memory.AnaliseRepositoryMemoria;
import br.com.cinemacultural.repository.memory.FilmeRepositoryMemoria;
import br.com.cinemacultural.service.AnaliseService;
import br.com.cinemacultural.service.FilmeService;


public class TestesAplicacao {

    public static void main(String[] args) {
        executar();
    }

    public static void executar() {
        FilmeRepository filmeRepository = new FilmeRepositoryMemoria();
        AnaliseRepository analiseRepository = new AnaliseRepositoryMemoria();
        FilmeService filmeService = new FilmeService(filmeRepository, analiseRepository);
        AnaliseService analiseService = new AnaliseService(analiseRepository, filmeRepository);

        Filme filme = filmeService.cadastrar(
                "Interestelar",
                "Uma equipe parte em uma missão espacial em busca de um futuro para a humanidade.",
                "Ficção científica",
                2014
        );
        verificar(filme.getId() != null, "O filme deve receber um ID.");
        verificar(filmeService.listar().size() == 1, "O filme deve aparecer na listagem.");

        Filme atualizado = filmeService.atualizar(
                filme.getId(),
                "Interestelar",
                "Uma equipe viaja pelo espaço procurando um novo lar para a humanidade.",
                "Ficção científica",
                2014
        );
        verificar(atualizado.getSinopse().contains("novo lar"), "A edição do filme deve ser salva.");

        Analise analise = analiseService.cadastrar(filme.getId(), "História envolvente e bons efeitos visuais.", 9.5);
        verificar(analiseService.listarPorFilme(filme.getId()).size() == 1, "A análise deve aparecer no filme correto.");

        Analise analiseAtualizada = analiseService.atualizar(analise.getId(), filme.getId(), "A narrativa e a trilha sonora se destacam.", 10.0);
        verificar(analiseAtualizada.getNota() == 10.0, "A edição da análise deve ser salva.");

        boolean notaInvalidaRejeitada = false;
        try {
            analiseService.cadastrar(filme.getId(), "Nota inválida", 11.0);
        } catch (IllegalArgumentException e) {
            notaInvalidaRejeitada = true;
        }
        verificar(notaInvalidaRejeitada, "Notas fora do intervalo devem ser rejeitadas.");

        filmeService.excluir(filme.getId());
        verificar(filmeService.listar().isEmpty(), "O filme deve ser removido.");
        verificar(analiseRepository.listarPorFilme(filme.getId()).isEmpty(), "As análises do filme excluído também devem ser removidas.");

        System.out.println("Testes concluídos: cadastro, listagem, edição, validação e exclusão de filmes e análises.");
    }

    private static void verificar(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }
}
