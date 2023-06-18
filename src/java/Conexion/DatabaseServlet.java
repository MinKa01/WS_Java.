
package Conexion;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.json.Json;
import javax.json.JsonObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name = "DatabaseServlet", urlPatterns = {"/DatabaseServlet"})
public class DatabaseServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        JsonObject json = getConnection();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    private JsonObject getConnection() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String user = "root";
        String pass = "";
        String host = "localhost";
        int port = 3306;
        String database = "android_bd";

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pass);
            return Json.createObjectBuilder()
                .add("status", "Conexión exitosa")
                .build();
        } catch (ClassNotFoundException e) {
            return Json.createObjectBuilder()
                .add("status", "Error al cargar el controlador JDBC")
                .add("error", e.getMessage())
                .build();
        } catch (SQLException e) {
            return Json.createObjectBuilder()
                .add("status", "Error al establecer la conexión a la base de datos")
                .add("error", e.getMessage())
                .build();
        }
    }

}
