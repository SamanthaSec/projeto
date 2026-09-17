package br.com.jogos.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlSingleton {
    private static Connection conexao = null;
    
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca_jogos?useTimezone=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = ""; // Insira sua senha do MySQL aqui

    private MysqlSingleton() {}

    public static Connection getConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver do MySQL não encontrado!", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados!", e);
        }
        return conexao;
    }
}