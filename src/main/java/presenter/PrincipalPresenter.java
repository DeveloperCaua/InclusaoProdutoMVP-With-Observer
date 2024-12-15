/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package presenter;

import model.ProdutoCollection;
import observer.IGerenciadorProdutoObserver;
import view.PrincipalView;

public class PrincipalPresenter implements IGerenciadorProdutoObserver {
    private final PrincipalView principalView;
    private final ProdutoCollection produtos;

    public PrincipalPresenter(ProdutoCollection produtos) {
        this.produtos = produtos;
        this.produtos.adicionarObserver(this);
        this.principalView = new PrincipalView();
        configuraView();
        this.principalView.setVisible(true);
    }

    private void configuraView() {
        principalView.getBtnIncluirProduto().addActionListener(e -> {
            new InsercaoProdutosPresenter(produtos).mostrarView();
        });

        principalView.getBtnListagemProdutos().addActionListener(e -> {
            new ListarProdutosPresenter(produtos).mostrarView();
        });
    }

    @Override
    public void atualizar(ProdutoCollection colecaoProdutos) {
        int totalProdutos = produtos.getProdutos().size();
        principalView.getTxtQuantidadeProdutos().setText(String.valueOf(totalProdutos));
    }
}