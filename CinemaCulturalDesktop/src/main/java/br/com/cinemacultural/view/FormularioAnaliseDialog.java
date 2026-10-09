package br.com.cinemacultural.view;

import br.com.cinemacultural.model.Analise;
import br.com.cinemacultural.model.Filme;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import java.awt.GridLayout;
import java.util.List;

public class FormularioAnaliseDialog {

    public static DadosAnalise mostrar(PainelAnalises parent, List<Filme> filmes, Analise analise, long filmeIdAtual) {
        if (filmes.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Cadastre um filme antes de adicionar uma análise.", "Nenhum filme cadastrado", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        JComboBox<Filme> filmeCombo = new JComboBox<>(filmes.toArray(new Filme[0]));
        for (Filme filme : filmes) {
            if (filme.getId() != null && filme.getId() == filmeIdAtual) {
                filmeCombo.setSelectedItem(filme);
                break;
            }
        }

        JTextArea texto = new JTextArea(analise == null ? "" : analise.getTexto(), 5, 28);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);

        double notaInicial = analise == null ? 0.0 : analise.getNota();
        JSpinner nota = new JSpinner(new SpinnerNumberModel(notaInicial, 0.0, 10.0, 0.5));

        JPanel campos = new JPanel(new GridLayout(0, 1, 6, 6));
        campos.add(new JLabel("Filme"));
        campos.add(filmeCombo);
        campos.add(new JLabel("Análise"));
        campos.add(new JScrollPane(texto));
        campos.add(new JLabel("Nota (0 a 10)"));
        campos.add(nota);

        int resultado = JOptionPane.showConfirmDialog(
                parent,
                campos,
                analise == null ? "Adicionar análise" : "Editar análise",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado != JOptionPane.OK_OPTION) {
            return null;
        }

        Filme filmeSelecionado = (Filme) filmeCombo.getSelectedItem();
        if (filmeSelecionado == null) {
            return null;
        }

        return new DadosAnalise(filmeSelecionado.getId(), texto.getText(), ((Number) nota.getValue()).doubleValue());
    }

    public record DadosAnalise(long filmeId, String texto, double nota) {
    }
}
