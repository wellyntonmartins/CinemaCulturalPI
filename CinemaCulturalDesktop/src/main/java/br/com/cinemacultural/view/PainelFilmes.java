package br.com.cinemacultural.view;

import br.com.cinemacultural.controller.FilmeController;
import br.com.cinemacultural.model.Filme;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class PainelFilmes extends JPanel {

    private final FilmeController controller;
    private final DefaultTableModel modelo;
    private final JTable tabela;
    private final Runnable aoAlterar;

    public PainelFilmes(FilmeController controller, Runnable aoAlterar) {
        this.controller = controller;
        this.aoAlterar = aoAlterar;
        setLayout(new BorderLayout(8, 8));

        modelo = new DefaultTableModel(new Object[]{"ID", "Título", "Gênero", "Ano", "Sinopse"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modelo);
        tabela.setAutoCreateRowSorter(true);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton cadastrar = new JButton("Cadastrar");
        JButton editar = new JButton("Editar");
        JButton excluir = new JButton("Excluir");
        JButton atualizar = new JButton("Atualizar lista");

        cadastrar.addActionListener(event -> cadastrar());
        editar.addActionListener(event -> editar());
        excluir.addActionListener(event -> excluir());
        atualizar.addActionListener(event -> atualizarTabela());

        botoes.add(cadastrar);
        botoes.add(editar);
        botoes.add(excluir);
        botoes.add(atualizar);
        add(botoes, BorderLayout.NORTH);

        atualizarTabela();
    }

    public void atualizarTabela() {
        modelo.setRowCount(0);
        for (Filme filme : controller.listar()) {
            modelo.addRow(new Object[]{filme.getId(), filme.getTitulo(), filme.getGenero(), filme.getAnoLancamento(), filme.getSinopse()});
        }
    }

    private Long idSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um filme.", "Atenção", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        int linhaModelo = tabela.convertRowIndexToModel(linha);
        return ((Number) modelo.getValueAt(linhaModelo, 0)).longValue();
    }

    private void cadastrar() {
        FormularioFilmeDialog.DadosFilme dados = FormularioFilmeDialog.mostrar((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), null);
        if (dados == null) {
            return;
        }
        try {
            controller.cadastrar(dados.titulo(), dados.sinopse(), dados.genero(), dados.anoLancamento());
            atualizarTabela();
            aoAlterar.run();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Dados inválidos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editar() {
        Long id = idSelecionado();
        if (id == null) {
            return;
        }
        try {
            Filme filme = controller.buscar(id);
            FormularioFilmeDialog.DadosFilme dados = FormularioFilmeDialog.mostrar((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), filme);
            if (dados == null) {
                return;
            }
            controller.atualizar(id, dados.titulo(), dados.sinopse(), dados.genero(), dados.anoLancamento());
            atualizarTabela();
            aoAlterar.run();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Não foi possível editar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        Long id = idSelecionado();
        if (id == null) {
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja excluir o filme e suas análises?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            controller.excluir(id);
            atualizarTabela();
            aoAlterar.run();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Não foi possível excluir", JOptionPane.ERROR_MESSAGE);
        }
    }
}
