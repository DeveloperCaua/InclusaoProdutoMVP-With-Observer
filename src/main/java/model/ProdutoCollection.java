/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import observer.IGerenciadorProdutoObserver;

/**
 *
 * @author Cauã
 */
public class ProdutoCollection {
    private List<IGerenciadorProdutoObserver> observers = new ArrayList<>();
    private final List<Produto> produtos;

    public ProdutoCollection() {
        produtos = new ArrayList<>();
    }
    
    public void adicionarObserver(IGerenciadorProdutoObserver observer) {
        observers.add(observer);
    }

    public void removerObserver(IGerenciadorProdutoObserver observer) {
        observers.remove(observer);
    }

    private void notificarObservers() {
        for (IGerenciadorProdutoObserver observer : observers) {
            observer.atualizar();
        }
    }

    public void incluir(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Informe um produto válido");
        }
        produtos.add(produto);
        notificarObservers();
    }
    
    public boolean remover(String nome) {
        Optional<Produto> produtoOpt = findProdutoByNome(nome);
        if (produtoOpt.isPresent()) {
            produtos.remove(produtoOpt.get());
            notificarObservers();
            return true;
        }
        return false;
    }
    
    public List<Produto> getProdutos() {
        return produtos;
    }
   
    public Optional<Produto> findProdutoByNome(String nome) {
        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                return Optional.of(produto);
            }
        }
        return Optional.empty();
    }
}
