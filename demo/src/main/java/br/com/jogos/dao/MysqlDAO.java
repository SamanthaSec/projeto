package br.com.jogos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.jogos.config.MysqlSingleton;

public abstract class MysqlDAO {

    protected Connection getConnection() {
        return MysqlSingleton.getConexao();
    }

    protected ResultSet executar(String sql, Object... parametros) throws SQLException {
        Connection conexao = getConnection();
        PreparedStatement stmt = conexao.prepareStatement(sql);
        for (int i = 0; i < parametros.length; i++) {
            stmt.setObject(i + 1, parametros[i]);
        }
        return stmt.executeQuery();
    }

    protected int executarUpdate(String sql, Object... parametros) throws SQLException {
        Connection conexao = getConnection();
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < parametros.length; i++) {
                stmt.setObject(i + 1, parametros[i]);
            }
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    protected <T extends Mapeavel> T mapearSimples(ResultSet rs, Class<T> clazz) {
        try {
            T objeto = clazz.getDeclaredConstructor().newInstance();
            objeto.preencher(rs);
            return objeto;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao mapear o objeto: " + clazz.getName(), e);
        }
    }
}