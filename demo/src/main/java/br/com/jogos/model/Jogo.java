package br.com.jogos.model;

public class Jogo {
    private int id;
    private String titulo;
    private String plataforma;
    private String genero;
    private double preco;
    private Integer categoriaId;      
    private transient int usuarioId;  

    public Jogo() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    public Integer getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Integer categoriaId) { this.categoriaId = categoriaId; }
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
}
