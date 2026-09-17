package br.com.jogos.controller;

import java.io.IOException;
import java.util.List;

import br.com.jogos.model.jogo;
import br.com.jogos.service.JogoService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JogoServlet extends HttpServlet {
    private JogoService service;

    @Override
    public void init() throws ServletException {
        super.init();
        this.service = new JogoService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<jogo> listajogos = service.listarTodos();
            request.setAttribute("jogos", listajogos);
            
            request.getRequestDispatcher("/WEB-INF/lista-jogos.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erro ao listar jogos", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String titulo = request.getParameter("titulo");
            String plataforma = request.getParameter("plataforma");
            double preco = Double.parseDouble(request.getParameter("preco"));

            jogo novojogo = new jogo();
            novojogo.setTitulo(titulo);
            novojogo.setPlataforma(plataforma);
            novojogo.setPreco(preco);

            service.salvar(novojogo);

            response.sendRedirect("jogos");
        } catch (Exception e) {
            throw new ServletException("Erro ao salvar o jogo", e);
        }
    }
}