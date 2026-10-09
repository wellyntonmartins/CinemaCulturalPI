package br.com.cinemacultural.view;

import br.com.cinemacultural.model.Filme;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class FormularioFilmeDialog {

    public static DadosFilme mostrar(JFrame parent, Filme filme) {
        JTextField titulo = new JTextField(filme == null ? "" : filme.getTitulo(), 28);
        JTextField genero = new JTextField(filme == null ? "" : filme.getGenero(), 28);
        JTextField ano = new JTextField(filme == null ? "" : String.valueOf(filme.getAnoLancamento()), 28);
        JTextArea sinopse = new JTextArea(filme == null ? "" : filme.getSinopse(), 5, 28);
        sinopse.setLineWrap(true);
        sinopse.setWrapStyleWord(true);

        JPanel campos = new JPanel(new GridLayout(0, 1, 6, 6));
        campos.add(new JLabel("Título"));
        campos.add(titulo);
        campos.add(new JLabel("Sinopse"));
        campos.add(new JScrollPane(sinopse));
        campos.add(new JLabel("Gênero"));
        campos.add(genero);
        campos.add(new JLabel("Ano de lançamento"));
        campos.add(ano);

        int resultado = JOptionPane.showConfirmDialog(
                parent,
                campos,
                filme == null ? "Cadastrar filme" : "Editar filme",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado != JOptionPane.OK_OPTION) {
            return null;
        }

        try {
            return new DadosFilme(titulo.getText(), sinopse.getText(), genero.getText(), Integer.parseInt(ano.getText().trim()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(parent, "Informe um ano válido.", "Dados inválidos", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public record DadosFilme(String titulo, String sinopse, String genero, int anoLancamento) {
    }
}
