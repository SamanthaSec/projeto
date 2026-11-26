package br.com.jogos.controller;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.jogos.model.Categoria;
import br.com.jogos.service.CategoriaService;
import br.com.jogos.util.Json;
import br.com.jogos.util.Sessao;

/** /api/categorias e /api/categorias/{id} — categorias personalizadas do usuário logado. */
public class CategoriaController extends HttpServlet {
    private final CategoriaService service = new CategoriaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Json.enviar(resp, 200, service.listar(Sessao.usuarioId(req)));
        } catch (Exception e) { Json.tratar(resp, e); }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int uid = Sessao.usuarioId(req);
            Json.enviar(resp, 201, service.criar(uid, Json.ler(req, Categoria.class)));
        } catch (Exception e) { Json.tratar(resp, e); }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int uid = Sessao.usuarioId(req);
            Json.enviar(resp, 200, service.atualizar(uid, idDaUrl(req), Json.ler(req, Categoria.class)));
        } catch (Exception e) { Json.tratar(resp, e); }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int uid = Sessao.usuarioId(req);
            service.excluir(uid, idDaUrl(req));
            Json.enviar(resp, 204, null);
        } catch (Exception e) { Json.tratar(resp, e); }
    }

    private int idDaUrl(HttpServletRequest req) {
        String p = req.getPathInfo();
        if (p == null || p.equals("/")) throw new IllegalArgumentException("Informe o ID na URL");
        try {
            return Integer.parseInt(p.substring(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID inválido");
        }
    }
}
