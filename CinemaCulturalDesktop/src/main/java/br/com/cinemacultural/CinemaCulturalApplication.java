package br.com.cinemacultural;

import br.com.cinemacultural.controller.AnaliseController;
import br.com.cinemacultural.controller.FilmeController;
import br.com.cinemacultural.repository.AnaliseRepository;
import br.com.cinemacultural.repository.FilmeRepository;
import br.com.cinemacultural.repository.memory.AnaliseRepositoryMemoria;
import br.com.cinemacultural.repository.memory.FilmeRepositoryMemoria;
import br.com.cinemacultural.service.AnaliseService;
import br.com.cinemacultural.service.FilmeService;
import br.com.cinemacultural.view.TelaPrincipal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class CinemaCulturalApplication {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        FilmeRepository filmeRepository = new FilmeRepositoryMemoria();
        AnaliseRepository analiseRepository = new AnaliseRepositoryMemoria();
        FilmeService filmeService = new FilmeService(filmeRepository, analiseRepository);
        AnaliseService analiseService = new AnaliseService(analiseRepository, filmeRepository);
        FilmeController filmeController = new FilmeController(filmeService);
        AnaliseController analiseController = new AnaliseController(analiseService, filmeService);

        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal(filmeController, analiseController);
            tela.setVisible(true);
        });
    }
}
