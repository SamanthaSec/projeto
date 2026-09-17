<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <html>

        <head>
            <title>Biblioteca de Jogos</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    margin: 40px;
                }

                table {
                    border-collapse: collapse;
                    width: 100%;
                    margin-top: 20px;
                }

                th,
                td {
                    border: 1px solid #ddd;
                    padding: 10px;
                    text-align: left;
                }

                th {
                    background-color: #f4f4f4;
                }

                form {
                    margin-top: 20px;
                    background: #f9f9f9;
                    padding: 15px;
                    border-radius: 5px;
                    width: 300px;
                }

                input {
                    display: block;
                    margin-bottom: 10px;
                    width: 100%;
                    padding: 8px;
                    box-sizing: border-box;
                }
            </style>
        </head>

        <body>
            <h1>Biblioteca de Jogos</h1>

            <!-- Formulário para cadastrar novo jogo -->
            <form action="jogos" method="POST">
                <h3>Adicionar Novo Jogo</h3>
                <label>Título:</label>
                <input type="text" name="titulo" required />

                <label>Plataforma:</label>
                <input type="text" name="plataforma" required />

                <label>Preço:</label>
                <input type="number" step="0.01" name="preco" required />

                <button type="submit">Salvar Jogo</button>
            </form>

            <!-- Tabela de listagem -->
            <h2>Jogos Cadastrados</h2>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Título</th>
                    <th>Plataforma</th>
                    <th>Preço</th>
                </tr>
                <c:forEach var="j" items="${jogos}">
                    <tr>
                        <td>${j.id}</td>
                        <td>${j.titulo}</td>
                        <td>${j.plataforma}</td>
                        <td>R$ ${j.preco}</td>
                    </tr>
                </c:forEach>
            </table>
        </body>

        </html>