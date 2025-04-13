package servlet;

import database.ConnectionManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private final static String GET_ALL_USERS_SQL = """
            SELECT name,age
            FROM vk.public.user_info;
            """;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (Connection connection = ConnectionManager.openConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                stringBuilder.append("%s - %d years ".formatted(resultSet.getString("name"),resultSet.getInt("age")));
            }
        } catch (SQLException e) {
            stringBuilder.append("Ошибка на сервере :(");
        }
        try (Writer writer = resp.getWriter()) {
            writer.write(stringBuilder.toString());
        }
    }

}
