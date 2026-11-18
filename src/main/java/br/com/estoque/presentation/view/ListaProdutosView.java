package br.com.estoque.presentation.view;

import br.com.estoque.application.dto.ProdutoDTO;
import br.com.estoque.presentation.controller.ProdutoController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

/** Tela 2: tabela com todos os produtos cadastrados. */
public class ListaProdutosView extends JPanel {

    private static final NumberFormat MOEDA = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));

    private final ProdutoController controller;
    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Nome", "Quantidade", "Valor unitário", "Valor total"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };

    public ListaProdutosView(ProdutoController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(0, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Produtos em Estoque");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));
        add(titulo, BorderLayout.NORTH);

        JTable tabela = new JTable(modelo);
        tabela.setRowHeight(24);
        tabela.setAutoCreateRowSorter(true);
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        for (int i = 1; i <= 3; i++) tabela.getColumnModel().getColumn(i).setCellRenderer(direita);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JButton atualizar = new JButton("Atualizar");
        atualizar.addActionListener(e -> recarregar());
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.add(atualizar);
        add(rodape, BorderLayout.SOUTH);
    }

    public void recarregar() {
        modelo.setRowCount(0);
        for (ProdutoDTO p : controller.listar()) {
            modelo.addRow(new Object[]{
                    p.nome(), p.quantidade(),
                    MOEDA.format(p.valorUnitario()), MOEDA.format(p.valorTotal())});
        }
    }
}
