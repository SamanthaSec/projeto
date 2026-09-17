package br.com.jogos.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.jogos.model.jogo;

public class jogoDAO extends MysqlDAO {

    public List<jogo> listarTodos() {
        List<jogo> jogos = new ArrayList<>();
        String sql = "SELECT * FROM jogos";
        
        try (ResultSet rs = executar(sql)) {
            while (rs.next()) {
                jogo jogo = mapearSimples(rs, jogo.class);
                jogos.add(jogo);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar os jogos!", e);
        }
        return jogos;
    }

    public void inserir(jogo jogo) {
        String sql = "INSERT INTO jogos (titulo, plataforma, preco) VALUES (?, ?, ?)";
        try {
            executarUpdate(sql, jogo.getTitulo(), jogo.getPlataforma(), jogo.getPreco());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir o jogo!", e);
        }
    }

    public void inserir1(jogo jogo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void salvar1(jogo novojogo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'salvar'");
    }

    public void salvar1(jogo novojogo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void salvar(jogo novojogo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}