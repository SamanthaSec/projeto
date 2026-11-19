package br.com.jogos.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Configuração do banco. Pode ser alterada por variáveis de ambiente (DB_URL, DB_USER, DB_PASS). */
public final class Conexao {
    private static final String URL = env("DB_URL", "jdbc:mysql://localhost:3306/biblioteca_jogos?serverTimezone=UTC");
    private static final String USUARIO = env("DB_USER", "root");
    private static final String SENHA = env("DB_PASS", "");

    private Conexao() {}

    private static String env(String chave, String padrao) {
        String v = System.getenv(chave);
        return v == null ? padrao : v;
    }

    /** Cada chamada abre uma conexão nova; quem usa fecha com try-with-resources. */
    public static Connection getConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do MySQL não encontrado", e);
        }
        
        return DriverManager.getConnection(URL, USUARIO, SENHA);

    }
}
