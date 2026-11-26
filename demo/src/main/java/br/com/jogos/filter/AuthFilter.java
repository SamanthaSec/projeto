package br.com.jogos.filter;

import java.io.IOException;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.jogos.util.Json;


public class AuthFilter implements Filter {
    @Override public void init(FilterConfig config) {}
    @Override public void destroy() {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpSession s = ((HttpServletRequest) req).getSession(false);
        if (s != null && s.getAttribute("usuario") != null) {
            chain.doFilter(req, resp);
        } else {
            Json.enviar((HttpServletResponse) resp, 401, Map.of("erro", "Faça login para continuar"));
        }
    }
}
