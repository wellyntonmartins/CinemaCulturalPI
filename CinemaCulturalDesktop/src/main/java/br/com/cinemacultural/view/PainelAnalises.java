package br.com.cinemacultural.view;

import br.com.cinemacultural.controller.AnaliseController;
import br.com.cinemacultural.model.Analise;
import br.com.cinemacultural.model.Filme;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class PainelAnalises extends JPanel {

    private final AnaliseController controller;
    private final JComboBox<Filme> filmes;
    private final DefaultTableModel modelo;
    private final JTable tabela;

    public PainelAnalises(AnaliseController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(8, 8));

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filmes = new JComboBox<>();
        filmes.addActionListener(event -> atualizarTabela());
        topo.add(new javax.swing.JLabel("Filme:"));
        topo.add(filmes);
        add(topo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[]{"ID", "Análise", "Nota"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modelo);
        tabela.setAutoCreateRowSorter(true);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton cadastrar = new JButton("Adicionar análise");
        JButton editar = new JButton("Editar");
        JButton excluir = new JButton("Excluir");
        JButton atualizar = new JButton("Atualizar lista");

        cadastrar.addActionListener(event -> cadastrar());
        editar.addActionListener(event -> editar());
        excluir.addActionListener(event -> excluir());
        atualizar.addActionListener(event -> atualizarFilmes());

        botoes.add(cadastrar);
        botoes.add(editar);
        botoes.add(excluir);
        botoes.add(atualizar);
        add(botoes, BorderLayout.SOUTH);

        atualizarFilmes();
    }

    public void atualizarFilmes() {
        Long selecionado = getFilmeSelecionadoId();
        filmes.removeAllItems();
        for (Filme filme : controller.listarFilmes()) {
            filmes.addItem(filme);
            if (selecionado != null && filme.getId().equals(selecionado)) {
                filmes.setSelectedItem(filme);
            }
        }
        atualizarTabela();
    }

    private Long getFilmeSelecionadoId() {
        Filme filme = (Filme) filmes.getSelectedItem();
        return filme == null ? null : filme.getId();
    }

    public void atualizarTabela() {
        modelo.setRowCount(0);
        Long filmeId = getFilmeSelecionadoId();
        if (filmeId == null) {
            return;
        }
        for (Analise analise : controller.listarPorFilme(filmeId)) {
            modelo.addRow(new Object[]{analise.getId(), analise.getTexto(), analise.getNota()});
        }
    }

    private Long idSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma análise.", "Atenção", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        int linhaModelo = tabela.convertRowIndexToModel(linha);
        return ((Number) modelo.getValueAt(linhaModelo, 0)).longValue();
    }

    private void cadastrar() {
        Long filmeId = getFilmeSelecionadoId();
        FormularioAnaliseDialog.DadosAnalise dados = FormularioAnaliseDialog.mostrar(this, controller.listarFilmes(), null, filmeId == null ? -1 : filmeId);
        if (dados == null) {
            return;
        }
        try {
            controller.cadastrar(dados.filmeId(), dados.texto(), dados.nota());
            atualizarFilmes();
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
            Analise analise = controller.buscar(id);
            FormularioAnaliseDialog.DadosAnalise dados = FormularioAnaliseDialog.mostrar(this, controller.listarFilmes(), analise, analise.getFilmeId());
            if (dados == null) {
                return;
            }
            controller.atualizar(id, dados.filmeId(), dados.texto(), dados.nota());
            atualizarFilmes();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Não foi possível editar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        Long id = idSelecionado();
        if (id == null) {
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja excluir esta análise?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            controller.excluir(id);
            atualizarTabela();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Não foi possível excluir", JOptionPane.ERROR_MESSAGE);
        }
    }
}
