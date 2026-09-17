package br.com.jogos.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.jogos.dao.Mapeavel;

public class jogo implements Mapeavel {
    private int id;
    private String titulo;
    private String plataforma;
    private double preco;

    public jogo() {}

    public jogo(int id, String titulo, String plataforma, double preco) {
        this.id = id;
        this.titulo = titulo;
        this.plataforma = plataforma;
        this.preco = preco;
    }

    @Override
    public void preencher(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.titulo = rs.getString("titulo");
        this.plataforma = rs.getString("plataforma");
        this.preco = rs.getDouble("preco");
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
}