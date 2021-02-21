package dataAccessLayer;

import model.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * It extends the AbstractDAO in such a way that it works with OrderItem objects
 * It also contains OrderItem specific queries
 */
public class OrderItemDAO extends AbstractDAO<OrderItem> {

    /**
     * Creates a list of objects that belong to a specific order.
     * @param id The id of the desired OrderItem objects
     * @return
     */
    public List<OrderItem> findOrder(int id) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM order_manager.orderItem WHERE orderItem.order_id = ?");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query.toString());
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);

        } catch (SQLException e) {
            System.out.println("OrderItem find error");
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}
