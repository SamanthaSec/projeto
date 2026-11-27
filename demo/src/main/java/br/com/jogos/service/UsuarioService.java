package br.com.jogos.service;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import br.com.jogos.dao.UsuarioDAO;
import br.com.jogos.model.Usuario;


public class UsuarioService {
    private final UsuarioDAO dao = new UsuarioDAO();

    public Usuario registrar(String nome, String email, String senha) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Informe o nome");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("E-mail inválido");
        if (senha == null || senha.length() < 6) throw new IllegalArgumentException("A senha deve ter ao menos 6 caracteres");
        email = email.trim().toLowerCase();
        if (dao.buscarPorEmail(email) != null) throw new IllegalStateException("E-mail já cadastrado");

        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        Usuario u = new Usuario();
        u.setNome(nome.trim());
        u.setEmail(email);
        u.setSenhaHash(Base64.getEncoder().encodeToString(salt) + ":" + hash(senha, salt));
        u.setId(dao.inserir(u));
        return u;
    }

    public Usuario autenticar(String email, String senha) {
        if (email == null || senha == null) throw new SecurityException("E-mail ou senha inválidos");
        Usuario u = dao.buscarPorEmail(email.trim().toLowerCase());
        if (u != null) {
            String[] partes = u.getSenhaHash().split(":");
            byte[] salt = Base64.getDecoder().decode(partes[0]);
            if (hash(senha, salt).equals(partes[1])) return u;
        }
        throw new SecurityException("E-mail ou senha inválidos");
    }

    private String hash(String senha, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(senha.toCharArray(), salt, 65536, 256);
            byte[] h = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(h);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar hash da senha", e);
        }
    }
}
