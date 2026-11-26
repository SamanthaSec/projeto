package br.com.jogos.controller;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.jogos.model.Jogo;
import br.com.jogos.service.JogoService;
import br.com.jogos.util.Json;
import br.com.jogos.util.Sessao;

public class JogoController extends HttpServlet {
    private final JogoService service = new JogoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int uid = Sessao.usuarioId(req);
            Integer id = idDaUrl(req);
            Json.enviar(resp, 200, id == null ? service.listar(uid) : service.buscar(uid, id));
        } catch (Exception e) { Json.tratar(resp, e); }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int uid = Sessao.usuarioId(req);
            Json.enviar(resp, 201, service.criar(uid, Json.ler(req, Jogo.class)));
        } catch (Exception e) { Json.tratar(resp, e); }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int uid = Sessao.usuarioId(req);
            Integer id = idDaUrl(req);
            if (id == null) throw new IllegalArgumentException("Informe o ID na URL");
            Json.enviar(resp, 200, service.atualizar(uid, id, Json.ler(req, Jogo.class)));
        } catch (Exception e) { Json.tratar(resp, e); }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int uid = Sessao.usuarioId(req);
            Integer id = idDaUrl(req);
            if (id == null) throw new IllegalArgumentException("Informe o ID na URL");
            service.excluir(uid, id);
            Json.enviar(resp, 204, null);
        } catch (Exception e) { Json.tratar(resp, e); }
    }

    private Integer idDaUrl(HttpServletRequest req) {
        String p = req.getPathInfo();
        if (p == null || p.equals("/")) return null;
        try {
            return Integer.parseInt(p.substring(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID inválido");
        }
    }
}
