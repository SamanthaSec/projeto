package br.com.jogos.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapeavel {
    void preencher(ResultSet rs) throws SQLException;
}