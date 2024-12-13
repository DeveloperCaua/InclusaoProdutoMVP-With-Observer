/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Produto;
import model.ProdutoCollection;
import observer.IGerenciadorProdutoObserver;
import view.ListarProdutosView;

/**
 *
 * @author Cauã
 */
public class ListarProdutosPresenter implements IGerenciadorProdutoObserver {
    private final ListarProdutosView listarProdutosView;
    private final ProdutoCollection produtos;

    public ListarProdutosPresenter(ProdutoCollection produtos) {
        this.produtos = produtos;
        this.produtos.adicionarObserver(this);
        this.listarProdutosView = new ListarProdutosView();
        configuraView();
        atualizarTabela();
    }

    private void configuraView() {
        listarProdutosView.getBtnRemover().addActionListener(e -> {
            int selectedRow = listarProdutosView.getTblProdutos().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                    listarProdutosView,
                    "Nenhum produto selecionado para remoção.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
                );                
            } else{
                int confirmacao = JOptionPane.showConfirmDialog(
                    listarProdutosView,
                    "Você tem certeza que deseja excluir este produto?",
                    "Confirmar exclusão",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );

                if (confirmacao == JOptionPane.YES_OPTION) {
                    removerProduto(selectedRow);
                }
            }
        });
    }

    private void removerProduto(int row) {
        DefaultTableModel model = (DefaultTableModel) listarProdutosView.getTblProdutos().getModel();

        if (row >= 0 && row < model.getRowCount()) {
            String nome = model.getValueAt(row, 0).toString();

            if (produtos.remover(nome)) {
                JOptionPane.showMessageDialog(listarProdutosView, "Produto excluído com sucesso!");
            }
        }
    }

    private void atualizarTabela() {
        DefaultTableModel model = (DefaultTableModel) listarProdutosView.getTblProdutos().getModel();
        model.setRowCount(0);

        for (Produto produto : produtos.getProdutos()) {
            model.addRow(new Object[]{
                produto.getNome(),
                produto.getPreco(),
                produto.getQuantidadeEstoque()
            });
        }
    }

    @Override
    public void atualizar() {
        atualizarTabela();
    }

    public void mostrarView() {
        listarProdutosView.setVisible(true);
    }
}