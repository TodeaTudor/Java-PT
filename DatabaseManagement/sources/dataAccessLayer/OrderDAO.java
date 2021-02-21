package dataAccessLayer;

import model.Order;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * It extends the AbstractDAO in such a way that it works with Order objects
 * It also contains Order specific queries.
 */

public class OrderDAO extends AbstractDAO<Order> {

    /**
     * Finds the ID of the last inserted order
     * @return
     */
    public int selectLastID() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT MAX(id) as id FROM order_manager.order");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int ID = 0;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query.toString());
            resultSet = statement.executeQuery();
            resultSet.next();
            ID = resultSet.getInt("id");
        } catch (SQLException e) {
            System.out.println("SelectLastID error");
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return ID;
    }

    /**
     * Updates the cost of an order
     * @param id The id of the order
     * @param cost The updated cost
     */
    public void updateCost(int id, double cost) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE order_manager.order SET cost=? WHERE id=?");
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query.toString());
            statement.setDouble(1, cost);
            statement.setInt(2, id);
            statement.execute();


        } catch (SQLException e) {
            System.out.println("Order update error");
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

}
