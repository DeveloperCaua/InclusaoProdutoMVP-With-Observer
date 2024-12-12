package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Produto;
import model.ProdutoCollection;
import view.TelaInsercaoProduto;
import view.TelaListarProdutos;
import view.TelaPrincipal;


public class GerenciadorProduto implements IGerenciadorProdutoObserver{
    private Produto produto;
    private final ProdutoCollection produtos;
    private final TelaPrincipal principalView;
    private final TelaInsercaoProduto insercaoProdutoView;
    private final TelaListarProdutos listarProdutosView;

    public GerenciadorProduto(ProdutoCollection produtos) {
        this.produtos = produtos;
        this.principalView = new TelaPrincipal();
        this.insercaoProdutoView = new TelaInsercaoProduto();
        this.listarProdutosView = new TelaListarProdutos();
        produtos.adicionarObserver(this);
        
        this.principalView.setVisible(false);
        configuraView();
        this.principalView.setVisible(true);
    }
    
    private void configuraView() {
        this.principalView.getBtnIncluirProduto().addActionListener(new ActionListener(){
             @Override
            public void actionPerformed(ActionEvent e) {
                insercaoProdutoView.setVisible(true);
            }
        });
          
        this.principalView.getBtnListagemProdutos().addActionListener(new ActionListener(){
             @Override
            public void actionPerformed(ActionEvent e) {
                listarProdutosView.setVisible(true);
            }
        });
          
        this.insercaoProdutoView.getBtnInserir().addActionListener(new ActionListener(){
             @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    adicionarProduto();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        
        this.listarProdutosView.getBtnRemover().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = listarProdutosView.getTblProdutos().getSelectedRow();
                if (selectedRow != -1) {
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
    
    private void adicionarProduto() {
        String nome = insercaoProdutoView.getTxtNomeProduto().getText();
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }

        double preco = Double.parseDouble(insercaoProdutoView.getTxtPrecoProduto().getText());
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço de custo deve ser maior que zero");
        }

        int quantidadeEstoque = Integer.parseInt(insercaoProdutoView.getTxtQuantidadeEstoque().getText());
        if (quantidadeEstoque <= 0) {
            throw new IllegalArgumentException("Percentual de lucro deve ser maior que zero");
        }

        produto = new Produto(nome, preco, quantidadeEstoque);
        produtos.incluir(produto);
   
        JOptionPane.showMessageDialog(insercaoProdutoView, "Produto incluído com sucesso!");
        limparCampos();
    }
     
    private void limparCampos(){
        insercaoProdutoView.getTxtNomeProduto().setText(null);
        insercaoProdutoView.getTxtPrecoProduto().setText(null);
        insercaoProdutoView.getTxtQuantidadeEstoque().setText(null);
    }
    
    private void atualizarBarraDeStatus() {
        int totalProdutos = produtos.getProdutos().size();
        principalView.getTxtQuantidadeProdutos().setText(String.valueOf(totalProdutos));
    }

    private void atualizarTabela() {
        DefaultTableModel model = (DefaultTableModel) listarProdutosView.getTblProdutos().getModel();
        model.setRowCount(0);

        for (Produto produto : produtos.getProdutos()) {
            model.addRow(new Object[]{
                produto.getNome(),
                produto.getPreco(),
                produto.getQuantidadeEstoque(),
            });
        }
    }

    @Override
    public void atualizar() {
        atualizarBarraDeStatus();
        atualizarTabela();
    }
}