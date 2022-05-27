package controller;

import model.Customer;
import service.CustomerDAO;
import service.ICustomerDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CustomerServlet", urlPatterns = "/customers")
public class CustomerServlet extends HttpServlet {
    ICustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":{
                showCreateForm(request,response);
            }
            case "view":{
                viewCustomerForm(request,response);
            }
            case "delete":{
                try {
                    deleteCustomer(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "edit":{
                showUpdateCustomer(request,response);
                break;
            }
            default: {
                try {
                    showList(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void showUpdateCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher =request.getRequestDispatcher("customer/update.jsp");
        int id = Integer.parseInt(request.getParameter("id"));
        Customer customer = customerDAO.findById(id);
        request.setAttribute("KHCanSua",customer);
        requestDispatcher.forward(request,response);
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        customerDAO.delete(id);
        List<Customer> customer = customerDAO.findAll();
        response.sendRedirect("/customers");
    }

    private void viewCustomerForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("customer/view.jsp");
        int id = Integer.parseInt(request.getParameter("id"));
        Customer customer = customerDAO.findById(id);
        request.setAttribute("danhSach", customer);
        requestDispatcher.forward(request,response);

    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("customer/create.jsp");
        requestDispatcher.forward(request,response);

    }

    private void showList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("customer/list.jsp");
        List<Customer> customers = customerDAO.findAll();
        String key = request.getParameter("key");
        if (key != null){
            customers = customerDAO.findByName("%"+ key +"%");
        }
        request.setAttribute("dsKH",customers);
        requestDispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":{
                try {
                    save(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "edit":{
                try {
                    updateCustomer(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        Customer customer = new Customer(id,name,age);
        customerDAO.update(customer);
        response.sendRedirect("/customers");
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        customerDAO.add(new Customer(name,age));
        response.sendRedirect("/customers");
    }
}
