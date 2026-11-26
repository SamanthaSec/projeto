package br.com.jogos.controller;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.jogos.model.Usuario;
import br.com.jogos.service.UsuarioService;
import br.com.jogos.util.Json;

/** /api/auth/register, /api/auth/login, /api/auth/logout (POST) e /api/auth/me (GET). */
public class AuthController extends HttpServlet {
    private final UsuarioService service = new UsuarioService();

    private static class Dados { String nome, email, senha; }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String acao = req.getPathInfo() == null ? "" : req.getPathInfo();
            switch (acao) {
                case "/register": {
                    Dados d = Json.ler(req, Dados.class);
                    Usuario u = service.registrar(d.nome, d.email, d.senha);
                    Json.enviar(resp, 201, Map.of("id", u.getId(), "nome", u.getNome(), "email", u.getEmail()));
                    break;
                }
                case "/login": {
                    Dados d = Json.ler(req, Dados.class);
                    Usuario u = service.autenticar(d.email, d.senha);
                    HttpSession antiga = req.getSession(false);
                    if (antiga != null) antiga.invalidate();
                    HttpSession s = req.getSession(true);
                    s.setAttribute("usuario", u);
                    s.setMaxInactiveInterval(30 * 60);
                    Json.enviar(resp, 200, Map.of("nome", u.getNome(), "email", u.getEmail()));
                    break;
                }
                case "/logout": {
                    HttpSession s = req.getSession(false);
                    if (s != null) s.invalidate();
                    Json.enviar(resp, 204, null);
                    break;
                }
                default:
                    Json.enviar(resp, 404, Map.of("erro", "Rota não encontrada"));
            }
        } catch (Exception e) { Json.tratar(resp, e); }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession s = req.getSession(false);
        Usuario u = s == null ? null : (Usuario) s.getAttribute("usuario");
        if (u == null) Json.enviar(resp, 401, Map.of("erro", "Não autenticado"));
        else Json.enviar(resp, 200, Map.of("nome", u.getNome(), "email", u.getEmail()));
    }
}
