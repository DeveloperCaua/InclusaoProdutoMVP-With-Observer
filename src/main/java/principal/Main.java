/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package principal;

import model.ProdutoCollection;
import presenter.PrincipalPresenter;

/**
 *
 * @author Cauã
 */

public class Main {
    public static void main(String[] args) {
        ProdutoCollection produtos = new ProdutoCollection();
        new PrincipalPresenter(produtos);
    }
}
