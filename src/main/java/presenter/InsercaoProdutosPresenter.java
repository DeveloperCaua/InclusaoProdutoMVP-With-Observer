/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import javax.swing.JOptionPane;
import model.Produto;
import model.ProdutoCollection;
import view.InsercaoProdutoView;

/**
 *
 * @author Cauã
 */
public class InsercaoProdutosPresenter{
    private final InsercaoProdutoView insercaoProdutoView;
    private final ProdutoCollection produtos;

    public InsercaoProdutosPresenter(ProdutoCollection produtos) {
        this.produtos = produtos;
        this.insercaoProdutoView = new InsercaoProdutoView();
        configuraView();
    }

    private void configuraView() {
        insercaoProdutoView.getBtnInserir().addActionListener(e -> {
            try {
                adicionarProduto();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
    }

    private void adicionarProduto() {
        String nome = insercaoProdutoView.getTxtNomeProduto().getText();
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }

        double preco = Double.parseDouble(insercaoProdutoView.getTxtPrecoProduto().getText());
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }

        int quantidadeEstoque = Integer.parseInt(insercaoProdutoView.getTxtQuantidadeEstoque().getText());
        if (quantidadeEstoque <= 0) {
            throw new IllegalArgumentException("Quantidade em estoque deve ser maior que zero");
        }

        Produto produto = new Produto(nome, preco, quantidadeEstoque);
        produtos.incluir(produto); // Notifica os observers

        JOptionPane.showMessageDialog(insercaoProdutoView, "Produto incluído com sucesso!");
        limparCampos();
    }

    private void limparCampos() {
        insercaoProdutoView.getTxtNomeProduto().setText(null);
        insercaoProdutoView.getTxtPrecoProduto().setText(null);
        insercaoProdutoView.getTxtQuantidadeEstoque().setText(null);
    }

    public void mostrarView() {
        insercaoProdutoView.setVisible(true);
    }
}
