servlet

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Servlet_Crud extends jakarta.servlet.http.HttpServlet {
    private Connection connection;

    // Initialize the database connection
    @Override
    public void init() throws ServletException {
        String jdbcURL = "jdbc:mysql://localhost:3306/vash";
        String username = "root";
        String password = "root";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Handle HTTP GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        
        if (requestURI.endsWith("/category")) {
            displayCategoryList(response);
        } else if (requestURI.endsWith("/product")) {
            displayProductList(response);
        }
    }

    // Display the category list
    private void displayCategoryList(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM categories");
            
            while (resultSet.next()) {
                int categoryId = resultSet.getInt("categoryId");
                String categoryName = resultSet.getString("categoryName");
                
                out.println("<p>Category ID: " + categoryId + ", Category Name: " + categoryName + "</p>");
            }
            
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Display the product list
    private void displayProductList(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
            
            while (resultSet.next()) {
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                int categoryId = resultSet.getInt("categoryId");
                
                out.println("<p>Product ID: " + productId + ", Product Name: " + productName +
                            ", Category ID: " + categoryId + "</p>");
            }
            
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Handle HTTP POST requests
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        
        if (requestURI.endsWith("/category")) {
            // Handle category form submissions
            String categoryName = request.getParameter("categoryName");
            
            addCategory(categoryName);
            
            // Redirect back to the Category Master page
            response.sendRedirect("categoryMaster.html");
        } else if (requestURI.endsWith("/product")) {
            // Handle product form submissions
            String productName = request.getParameter("productName");
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            
            addProduct(productName, categoryId);
            
            // Redirect back to the Product Master page
            response.sendRedirect("productMaster.html");
        }
    }

    // Add a new category to the database
    private void addCategory(String categoryName) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO categories (categoryName) VALUES (?)");
            statement.setString(1, categoryName);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a new product to the database
    private void addProduct(String productName, int categoryId) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO products (productName, categoryId) VALUES (?, ?)");
            statement.setString(1, productName);
            statement.setInt(2, categoryId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Clean up resources
    @Override
    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
