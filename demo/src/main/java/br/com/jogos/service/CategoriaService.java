package br.com.jogos.service;

import java.util.List;
import java.util.NoSuchElementException;

import br.com.jogos.dao.CategoriaDAO;
import br.com.jogos.model.Categoria;

/** Regras das categorias personalizadas de cada usuário. */
public class CategoriaService {
    private final CategoriaDAO dao = new CategoriaDAO();

    public List<Categoria> listar(int usuarioId) {
        return dao.listar(usuarioId);
    }

    public Categoria criar(int usuarioId, Categoria c) {
        validar(usuarioId, c, 0);
        c.setUsuarioId(usuarioId);
        c.setId(dao.inserir(c));
        return c;
    }

    public Categoria atualizar(int usuarioId, int id, Categoria c) {
        if (dao.buscarPorId(id, usuarioId) == null) throw new NoSuchElementException("Categoria não encontrada");
        validar(usuarioId, c, id);
        c.setId(id);
        c.setUsuarioId(usuarioId);
        dao.atualizar(c);
        return c;
    }

    public void excluir(int usuarioId, int id) {
        if (!dao.excluir(id, usuarioId)) throw new NoSuchElementException("Categoria não encontrada");
    }

    private void validar(int usuarioId, Categoria c, int idAtual) {
        if (c.getNome() == null || c.getNome().isBlank()) throw new IllegalArgumentException("Informe o nome da categoria");
        c.setNome(c.getNome().trim());
        if (c.getNome().length() > 40) throw new IllegalArgumentException("Nome muito longo (máximo 40 caracteres)");
        if (c.getCor() == null || !c.getCor().matches("#[0-9a-fA-F]{6}")) c.setCor("#c1121f");
        for (Categoria outra : dao.listar(usuarioId)) {
            if (outra.getId() != idAtual && outra.getNome().equalsIgnoreCase(c.getNome()))
                throw new IllegalStateException("Você já tem uma categoria com esse nome");
        }
    }
}
