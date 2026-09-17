<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Game Library</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background-color: #f4f4f9; color: #333; }
        h1, h2 { color: #2c3e50; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; background: #fff; }
        th, td { padding: 12px; border: 1px solid #ddd; text-align: left; }
        th { background-color: #2c3e50; color: white; }
        form { background: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); margin-top: 20px; max-width: 400px; }
        div { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input[type="text"], input[type="number"] { width: 100%; padding: 8px; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px; }
        button { background-color: #27ae60; color: white; padding: 10px 15px; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background-color: #219653; }
    </style>
</head>
<body>

    <h1>🎮 Game Library</h1>

    <!-- Tabela de Listagem -->
    <h2>Lista de Jogos</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Título</th>
                <th>Plataforma</th>
                <th>Preço (R$)</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="j" items="${jogos}">
                <tr>
                    <td>${j.id}</td>
                    <td>${j.titulo}</td>
                    <td>${j.plataforma}</td>
                    <td>R$ ${j.preco}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty jogos}">
                <tr>
                    <td colspan="4" style="text-align: center;">Nenhum jogo cadastrado ainda.</td>
                </tr>
            </c:if>
        </tbody>
    </table>

    <!-- Formulário de Cadastro -->
    <h2>Cadastrar Novo Jogo</h2>
    <form action="jogos" method="POST">
        <div>
            <label>Título:</label>
            <input type="text" name="titulo" required>
        </div>
        <div>
            <label>Plataforma:</label>
            <input type="text" name="plataforma" required>
        </div>
        <div>
            <label>Preço:</label>
            <input type="number" step="0.01" name="preco" required>
        </div>
        <button type="submit">Salvar Jogo</button>
    </form>

</body>
</html>