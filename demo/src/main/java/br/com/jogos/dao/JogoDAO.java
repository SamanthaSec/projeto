package br.com.jogos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.com.jogos.config.Conexao;
import br.com.jogos.model.Jogo;

public class JogoDAO {
    private static final String COLUNAS = "id, titulo, plataforma, genero, preco, categoria_id";

    public List<Jogo> listar() {
        List<Jogo> lista = new ArrayList<>();
        String sql = "SELECT " + COLUNAS + " FROM jogos ORDER BY titulo";
        try (Connection c = Conexao.getConexao(); PreparedStatement ps = c.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar jogos", e);
        }
        return lista;
    }

    public Jogo buscarPorId(int id) {
        String sql = "SELECT " + COLUNAS + " FROM jogos WHERE id = ? AND usuario_id = ?";
        try (Connection c = Conexao.getConexao(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar jogo", e);
        }
    }

    public int inserir(Jogo j) {
        String sql = "INSERT INTO jogos (usuario_id, titulo, plataforma, genero, preco, categoria_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = Conexao.getConexao();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, j.getUsuarioId());
            ps.setString(2, j.getTitulo());
            ps.setString(3, j.getPlataforma());
            ps.setString(4, j.getGenero());
            ps.setDouble(5, j.getPreco());
            setCategoria(ps, 6, j.getCategoriaId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir jogo", e);
        }
    }

    public boolean atualizar(Jogo j) {
        String sql = "UPDATE jogos SET titulo = ?, plataforma = ?, genero = ?, preco = ?, categoria_id = ? WHERE id = ? AND usuario_id = ?";
        try (Connection c = Conexao.getConexao(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, j.getTitulo());
            ps.setString(2, j.getPlataforma());
            ps.setString(3, j.getGenero());
            ps.setDouble(4, j.getPreco());
            setCategoria(ps, 5, j.getCategoriaId());
            ps.setInt(6, j.getId());
            ps.setInt(7, j.getUsuarioId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar jogo", e);
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM jogos WHERE id = ? AND usuario_id = ?";
        try (Connection c = Conexao.getConexao(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir jogo", e);
        }
    }

    private void setCategoria(PreparedStatement ps, int posicao, Integer categoriaId) throws SQLException {
        if (categoriaId == null) ps.setNull(posicao, Types.INTEGER);
        else ps.setInt(posicao, categoriaId);
    }

    private Jogo mapear(ResultSet rs) throws SQLException {
        Jogo j = new Jogo();
        j.setId(rs.getInt("id"));
        j.setTitulo(rs.getString("titulo"));
        j.setPlataforma(rs.getString("plataforma"));
        j.setGenero(rs.getString("genero"));
        j.setPreco(rs.getDouble("preco"));
        int cat = rs.getInt("categoria_id");
        j.setCategoriaId(rs.wasNull() ? null : cat);
        return j;
    }
}
