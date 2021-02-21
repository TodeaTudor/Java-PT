package dataAccessLayer;

import model.Product;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * It extends the AbstractDAO in such a way that it works with Product objects
 * It also contains Product specific queries
 */
public class ProductDAO extends AbstractDAO<Product> {

    /**
     * It updates the fields of a product
     * @param t Product to be update
     */
    public void updateProduct (Product t) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE order_manager.product SET quantity=?, price=? WHERE name=?");
        Connection connection = null;
        PreparedStatement statement = null;
        Product existing = findByName(t.getName());
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query.toString());
            statement.setInt(1, existing.getQuantity() + t.getQuantity());
            statement.setDouble(2, t.getPrice());
            statement.setString(3, t.getName());
            statement.execute();

        } catch (SQLException e) {
            System.out.println("Product update error");
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

    }


}
