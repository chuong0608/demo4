package controller;

import model.Customer;
import model.Product;
import service.IProductDAO;
import service.ProductDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/products")
public class ProductServlet extends HttpServlet {
    IProductDAO productDAO = new ProductDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("act");
        if(action == null){
            action= "";
        }
        switch (action){
            case "create":{
                showCreateProduct(request,response);
                break;
            }
            case "delete":{
                try {
                    showDeleteProduct(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "update":{
                showUpdateProduct(request,response);
                break;
            }
            case "view":{
                viewProductFrom(request,response);
                break;
            }
            default:{
                try {
                    showListProduct(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void viewProductFrom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("product/viewPro.jsp");
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.findById(id);
        request.setAttribute("sanPham",product);
        requestDispatcher.forward(request,response);
    }

    private void showUpdateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("product/updatePro.jsp");
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.findById(id);
        request.setAttribute("spSua",product);
        requestDispatcher.forward(request,response);

    }

    private void showDeleteProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productDAO.delete(id);
        response.sendRedirect("/products");
    }

    private void showCreateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("product/createPro.jsp");
        requestDispatcher.forward(request,response);
    }

    private void showListProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        RequestDispatcher requestDispatcher =request.getRequestDispatcher("product/listP.jsp");
        List<Product> products = productDAO.findAll();
        String key = request.getParameter("key");
        if (key != null){
            products = productDAO.findByName("%"+ key +"%");
        }
        request.setAttribute("sanPham",products);
        requestDispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("act");
        if (action==null){
            action="";
        }
        switch (action){
            case "create":{
                try {
                    createProduct(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }

            case "update":{
                try {
                    updateProduct(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        Product product = new Product(id,name,price,categoryId);
        productDAO.update(product);
        response.sendRedirect("/products");
    }

    private void createProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        productDAO.add(new Product(name,price,categoryId));
        response.sendRedirect("/products");
    }
}
