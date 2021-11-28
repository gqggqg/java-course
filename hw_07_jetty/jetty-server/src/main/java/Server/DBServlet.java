package Server;

import DAO.ProductDAO;
import generated.tables.records.ProductRecord;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jooq.impl.DSL;

@AllArgsConstructor
public class DBServlet extends HttpServlet {

    private final @NotNull String url;
    private final @NotNull String login;
    private final @NotNull String password;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        var productTable = new StringBuilder();
        for (var product : getAllProducts()) {
            productTable
                    .append("<tr><td>")
                    .append(product.getName())
                    .append("</td><td>")
                    .append(product.getManufacturer())
                    .append("</td><td>")
                    .append(product.getQuantity())
                    .append("</td></tr>");
        }

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (var inputStream = classLoader.getResourceAsStream("products.html")) {
            try (var inputStreamReader = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
                try (var reader = new BufferedReader(inputStreamReader)) {
                    String html = reader
                            .lines()
                            .collect(Collectors.joining(System.lineSeparator()))
                            .replace("dynamicProductTable", productTable);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setContentType("text/html;charset=UTF-8");
                    resp.getWriter().println(html);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @NotNull
    private List<ProductRecord> getAllProducts() {
        try (var connection = DriverManager.getConnection(url, login, password)) {
            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);
            return new ProductDAO(context).all();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
