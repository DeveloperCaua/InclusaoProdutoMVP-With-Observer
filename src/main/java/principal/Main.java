/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package principal;

import model.ProdutoCollection;
import presenter.GerenciadorProduto;

/**
 *
 * @author Cauã teste
 */

public class Main {
    public static void main(String[] args) {
        ProdutoCollection produtos = new ProdutoCollection();
        new GerenciadorProduto(produtos);
    }
}
