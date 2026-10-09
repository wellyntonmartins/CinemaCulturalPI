package br.com.cinemacultural.view;

import br.com.cinemacultural.controller.AnaliseController;
import br.com.cinemacultural.controller.FilmeController;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

public class TelaPrincipal extends JFrame {

    private final PainelFilmes painelFilmes;
    private final PainelAnalises painelAnalises;

    public TelaPrincipal(FilmeController filmeController, AnaliseController analiseController) {
        super("Cinema Cultural - Filmes e Análises");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        painelAnalises = new PainelAnalises(analiseController);
        painelFilmes = new PainelFilmes(filmeController, painelAnalises::atualizarFilmes);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Filmes", painelFilmes);
        abas.addTab("Análises", painelAnalises);
        abas.addChangeListener(event -> {
            painelFilmes.atualizarTabela();
            painelAnalises.atualizarFilmes();
        });

        add(abas, BorderLayout.CENTER);
    }
}
