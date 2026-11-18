package br.com.estoque.presentation.view;

import br.com.estoque.presentation.controller.ProdutoController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/** Tela 1: cadastro de produto. */
public class CadastroProdutoView extends JPanel {

    private static final NumberFormat MOEDA = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));

    private final ProdutoController controller;
    private final Runnable aoCadastrar;

    private final JTextField campoNome = new JTextField(22);
    private final JTextField campoQuantidade = new JTextField(22);
    private final JTextField campoValorUnitario = new JTextField(22);
    private final JTextField campoValorTotal = new JTextField(22);

    public CadastroProdutoView(ProdutoController controller, Runnable aoCadastrar) {
        this.controller = controller;
        this.aoCadastrar = aoCadastrar;
        montarTela();
    }

    private void montarTela() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        campoValorTotal.setEditable(false);
        campoValorTotal.setText(MOEDA.format(0));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.WEST;

        JLabel titulo = new JLabel("Cadastro de Produto");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        add(titulo, c);

        c.gridwidth = 1;
        adicionarLinha(c, 1, "Nome:", campoNome);
        adicionarLinha(c, 2, "Quantidade:", campoQuantidade);
        adicionarLinha(c, 3, "Valor unitário (R$):", campoValorUnitario);
        adicionarLinha(c, 4, "Valor total:", campoValorTotal);

        JButton botaoSalvar = new JButton("Cadastrar");
        JButton botaoLimpar = new JButton("Limpar");
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botoes.add(botaoLimpar);
        botoes.add(botaoSalvar);
        c.gridx = 0; c.gridy = 5; c.gridwidth = 2; c.anchor = GridBagConstraints.EAST;
        add(botoes, c);

        DocumentListener recalcular = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { atualizarTotal(); }
            public void removeUpdate(DocumentEvent e) { atualizarTotal(); }
            public void changedUpdate(DocumentEvent e) { atualizarTotal(); }
        };
        campoQuantidade.getDocument().addDocumentListener(recalcular);
        campoValorUnitario.getDocument().addDocumentListener(recalcular);

        botaoSalvar.addActionListener(e -> cadastrar());
        botaoLimpar.addActionListener(e -> limpar());
    }

    private void adicionarLinha(GridBagConstraints c, int linha, String rotulo, JComponent campo) {
        c.gridx = 0; c.gridy = linha; c.anchor = GridBagConstraints.WEST;
        add(new JLabel(rotulo), c);
        c.gridx = 1; c.fill = GridBagConstraints.HORIZONTAL;
        add(campo, c);
        c.fill = GridBagConstraints.NONE;
    }

    private void atualizarTotal() {
        BigDecimal total = controller.calcularTotal(campoQuantidade.getText(), campoValorUnitario.getText());
        campoValorTotal.setText(MOEDA.format(total == null ? BigDecimal.ZERO : total));
    }

    private void cadastrar() {
        try {
            controller.cadastrar(campoNome.getText(), campoQuantidade.getText(), campoValorUnitario.getText());
            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
            limpar();
            aoCadastrar.run();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dados inválidos", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limpar() {
        campoNome.setText("");
        campoQuantidade.setText("");
        campoValorUnitario.setText("");
        campoValorTotal.setText(MOEDA.format(0));
        campoNome.requestFocusInWindow();
    }
}
