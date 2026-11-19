package br.com.jogos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.jogos.config.Conexao;
import br.com.jogos.model.Categoria;

public class CategoriaDAO {

    public List<Categoria> listar(int usuarioId) {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id, nome, cor FROM categorias WHERE usuario_id = ? ORDER BY nome";
        try (Connection c = Conexao.getConexao(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar categorias", e);
        }
        return lista;
    }

    public Categoria buscarPorId(int id, int usuarioId) {
        String sql = "SELECT id, nome, cor FROM categorias WHERE id = ? AND usuario_id = ?";
        try (Connection c = Conexao.getConexao(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar categoria", e);
        }
    }

    public int inserir(Categoria cat) {
        String sql = "INSERT INTO categorias (usuario_id, nome, cor) VALUES (?, ?, ?)";
        try (Connection c = Conexao.getConexao();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, cat.getUsuarioId());
            ps.setString(2, cat.getNome());
            ps.setString(3, cat.getCor());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir categoria", e);
        }
    }

    public boolean atualizar(Categoria cat) {
        String sql = "UPDATE categorias SET nome = ?, cor = ? WHERE id = ? AND usuario_id = ?";
        try (Connection c = Conexao.getConexao(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cat.getNome());
            ps.setString(2, cat.getCor());
            ps.setInt(3, cat.getId());
            ps.setInt(4, cat.getUsuarioId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar categoria", e);
        }
    }

    public boolean excluir(int id, int usuarioId) {
        String sql = "DELETE FROM categorias WHERE id = ? AND usuario_id = ?";
        try (Connection c = Conexao.getConexao(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, usuarioId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir categoria", e);
        }
    }

    private Categoria mapear(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setId(rs.getInt("id"));
        c.setNome(rs.getString("nome"));
        c.setCor(rs.getString("cor"));
        return c;
    }
}
