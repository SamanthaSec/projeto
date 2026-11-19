package br.com.jogos.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.jogos.model.Usuario;

/** Descobre quem é o usuário logado a partir da sessão. */
public final class Sessao {
    private Sessao() {}

    public static int usuarioId(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        Usuario u = s == null ? null : (Usuario) s.getAttribute("usuario");
        if (u == null) throw new SecurityException("Faça login para continuar");
        return u.getId();
    }
}
