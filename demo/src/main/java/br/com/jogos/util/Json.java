package br.com.jogos.util;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

/** Leitura/escrita de JSON e mapeamento de exceções para status HTTP. */
public final class Json {
    private static final Gson GSON = new Gson();

    private Json() {}

    public static <T> T ler(HttpServletRequest req, Class<T> tipo) throws IOException {
        req.setCharacterEncoding("UTF-8");
        T obj = GSON.fromJson(req.getReader(), tipo);
        if (obj == null) throw new IllegalArgumentException("Corpo da requisição vazio");
        return obj;
    }

    public static void enviar(HttpServletResponse resp, int status, Object obj) throws IOException {
        resp.setStatus(status);
        if (obj != null) {
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write(GSON.toJson(obj));
        }
    }

    public static void tratar(HttpServletResponse resp, Exception e) throws IOException {
        int status = 500;
        String msg = "Erro interno do servidor";
        if (e instanceof JsonParseException) { status = 400; msg = "JSON inválido"; }
        else if (e instanceof IllegalArgumentException) { status = 400; msg = e.getMessage(); }
        else if (e instanceof NoSuchElementException) { status = 404; msg = e.getMessage(); }
        else if (e instanceof IllegalStateException) { status = 409; msg = e.getMessage(); }
        else if (e instanceof SecurityException) { status = 401; msg = e.getMessage(); }
        else e.printStackTrace();
        enviar(resp, status, Map.of("erro", msg));
    }
}
