package service;

import model.Customer;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductDAO {
    public ProductDAO() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo4?useSSL=false", "root", "1234");
        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void add(Product product) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into product(name ,price,categoryId) values (?,?,?)")) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2,product.getPrice());
            preparedStatement.setInt(3,product.getCategoryId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Product findById(int id) {
        Product product = new Product();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM demo4.product where id = ?")) {
            preparedStatement.setInt(1, id);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("name");
                int price =rs.getInt("price");
                int categoryId =rs.getInt("categoryId");
                product=new Product(id,name,price,categoryId);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from product")) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int categoryId = rs.getInt("categoryId");
                products.add(new Product(id, name, price, categoryId));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    @Override
    public List<Product> findByName(String key) {
        List<Product> products = new ArrayList<>();
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select *from product where name like ?")) {
            System.out.println(preparedStatement);
            preparedStatement.setString(1,"%"+key+"%");
            ResultSet rs=preparedStatement.executeQuery();
            while (rs.next()){
                int id = Integer.parseInt(rs.getString("id"));
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int categoryId = rs.getInt("categoryId");
                products.add(new Product(id,name,price,categoryId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        boolean rowDelete;
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from product where id = ?")) {
            preparedStatement.setInt(1,id);
            rowDelete = preparedStatement.executeUpdate()>0;
        }
        return rowDelete;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        boolean rowUpdate= false;
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update product set name = ?, price = ?, categoryId = ? where id = ?")) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2,product.getPrice());
            preparedStatement.setInt(3,product.getCategoryId());
            preparedStatement.setInt(4,product.getId());
            rowUpdate = preparedStatement.executeUpdate()>0;
        }catch (SQLException e ){
            System.out.println(e.getMessage());
        }
        return rowUpdate;
    }
}
