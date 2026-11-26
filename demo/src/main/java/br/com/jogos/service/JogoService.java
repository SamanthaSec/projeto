package br.com.jogos.service;

import java.util.List;
import java.util.NoSuchElementException;

import br.com.jogos.dao.CategoriaDAO;
import br.com.jogos.dao.JogoDAO;
import br.com.jogos.model.Jogo;

/** Regras de negócio dos jogos (validação, dono e categoria). */
public class JogoService {
    private final JogoDAO dao = new JogoDAO();
    private final CategoriaDAO categorias = new CategoriaDAO();

    public List<Jogo> listar(int usuarioId) {
        return dao.listar(usuarioId);
    }

    public Jogo buscar(int usuarioId, int id) {
        Jogo j = dao.buscarPorId(id, usuarioId);
        if (j == null) throw new NoSuchElementException("Jogo não encontrado");
        return j;
    }

    public Jogo criar(int usuarioId, Jogo j) {
        validar(usuarioId, j);
        j.setUsuarioId(usuarioId);
        j.setId(dao.inserir(j));
        return j;
    }

    public Jogo atualizar(int usuarioId, int id, Jogo j) {
        validar(usuarioId, j);
        j.setId(id);
        j.setUsuarioId(usuarioId);
        if (!dao.atualizar(j)) throw new NoSuchElementException("Jogo não encontrado");
        return j;
    }

    public void excluir(int usuarioId, int id) {
        if (!dao.excluir(id, usuarioId)) throw new NoSuchElementException("Jogo não encontrado");
    }

    private void validar(int usuarioId, Jogo j) {
        if (j.getTitulo() == null || j.getTitulo().isBlank()) throw new IllegalArgumentException("Informe o título");
        if (j.getPlataforma() == null || j.getPlataforma().isBlank()) throw new IllegalArgumentException("Informe a plataforma");
        if (j.getPreco() < 0) throw new IllegalArgumentException("O preço não pode ser negativo");
        if (j.getCategoriaId() != null && categorias.buscarPorId(j.getCategoriaId(), usuarioId) == null)
            throw new IllegalArgumentException("Categoria inválida");
        j.setTitulo(j.getTitulo().trim());
        j.setPlataforma(j.getPlataforma().trim());
        j.setGenero(j.getGenero() == null ? "" : j.getGenero().trim());
    }
}
