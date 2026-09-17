package br.com.jogos.service;

import java.util.List;

import br.com.jogos.dao.jogoDAO;
import br.com.jogos.model.jogo;

public class JogoService {
    private jogoDAO DAO = new jogoDAO();

    public List<jogo> listarTodos() {
        return DAO.listarTodos();
    }

    public void salvar1(jogo novojogo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void salvar(jogo novojogo) {
        DAO.salvar(novojogo);
    }
}
