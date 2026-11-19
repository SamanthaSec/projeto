package br.com.jogos.service;

import java.util.List;
import java.util.NoSuchElementException;

import br.com.jogos.dao.JogoDAO;
import br.com.jogos.model.Jogo;

public class JogoService {
    private final JogoDAO dao = new JogoDAO();

    public List<Jogo> listar() {
        return dao.listar();
    }

    public Jogo buscar(int id) {
        Jogo j = dao.buscarPorId(id);
        if (j == null) throw new NoSuchElementException("Jogo não encontrado");
        return j;
    }

    public Jogo criar(Jogo j) {
        validar(j);
        j.setId(dao.inserir(j));
        return j;
    }

    public Jogo atualizar(int id, Jogo j) {
        validar(j);
        j.setId(id);
        if (!dao.atualizar(j)) throw new NoSuchElementException("Jogo não encontrado");
        return j;
    }

    public void excluir(int id) {
        if (!dao.excluir(id)) throw new NoSuchElementException("Jogo não encontrado");
    }

    private void validar(Jogo j) {
        if (j.getTitulo() == null || j.getTitulo().isBlank()) throw new IllegalArgumentException("Informe o título");
        if (j.getPlataforma() == null || j.getPlataforma().isBlank()) throw new IllegalArgumentException("Informe a plataforma");
        if (j.getPreco() < 0) throw new IllegalArgumentException("O preço não pode ser negativo");
        j.setTitulo(j.getTitulo().trim());
        j.setPlataforma(j.getPlataforma().trim());
        j.setGenero(j.getGenero() == null ? "" : j.getGenero().trim());
    }
}