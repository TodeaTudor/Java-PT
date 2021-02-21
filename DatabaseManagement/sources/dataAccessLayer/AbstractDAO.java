package dataAccessLayer;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Unspecific queries that work on multiple classes
 * Built through reflection
 * @param <T>
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * Creates a generic query to select a field where the constraint is specified by the field argument
     * @param field Constraint field
     * @return
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM order_manager.");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =? AND deleted=0");
        return sb.toString();
    }

    /**
     * Finds an element by name
     * @param name The name of the element
     * @return
     */
    public T findByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("name");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            resultSet.beforeFirst();
            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByName " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Creates a list of objects based on the resultSet
     * @param resultSet The resultSet of a query from which we build the list
     * @return
     */
    public List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();

        try {
            while (resultSet.next()) {
                T instance = type.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Finds all the elements of a table
     * @return
     */
    public List<T> findAll() {
        StringBuilder find = new StringBuilder();
        find.append("SELECT * FROM order_manager." + type.getSimpleName() + " WHERE deleted=0");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(find.toString());
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Creates a generic INSERT query
     * @return
     */
    public String createInsertQuery() {
        StringBuilder insert = new StringBuilder();
        insert.append("INSERT INTO ");
        insert.append("order_manager.");
        insert.append(type.getSimpleName().toLowerCase());
        StringBuilder values = new StringBuilder();
        insert.append(" VALUES (");
        for (Field field : type.getDeclaredFields()) {

            if (values.length() >= 1) {
                values.append(",");
            }
            if (field.getName().equals("id")) {
                values.append("0");
            }else {
                values.append("?");
            }
        }
        values.append(")");

        return (insert.toString() + values.toString());
    }


    /**
     * Inserts an object to its specific table
     * @param t The object to be inserted
     * @return
     */
    public T insert(T t)  {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            int i = 1;
            for (Field field : type.getDeclaredFields()) {
                if (!field.getName().equals("id")) {
                    field.setAccessible(true);
                    Object value = field.get(t);
                    if (value.getClass() == String.class) {
                        statement.setString(i, value.toString());
                    }else if (value.getClass() == Integer.class) {
                        statement.setInt(i , Integer.parseInt(value.toString()));
                        i++;
                        continue;
                    }else if (value.getClass() == Double.class) {
                        statement.setDouble(i, Double.parseDouble(value.toString()));
                    }
                    i++;
                }
            }
            statement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert" + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     * Sets the deleted field of an object to 1
     * This is how deletion is handled in our project
     * @param t Object to be deleted
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public T delete(T t) throws NoSuchFieldException, IllegalAccessException {
        Field field = type.getDeclaredField("name");
        field.setAccessible(true);
        String name = field.get(t).toString();
        StringBuilder query = new StringBuilder();
        query.append("UPDATE " + "order_manager." + type.getSimpleName() + " SET deleted=1 WHERE name=?");
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query.toString());
            statement.setString(1, name);
            statement.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete" + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return t;
    }
}

