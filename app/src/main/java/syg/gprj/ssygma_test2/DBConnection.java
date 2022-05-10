package syg.gprj.ssygma_test2;

import java.sql.*;


public class DBConnection
{
    String databaseName ="sygma";
    String databaseUser ="root";
    String databasePassword = "maz3n123";
    String url = "jdbc:mysql:/10.0.2.2:3306/" + databaseName;

    public Connection connection;

    public Connection getConnection ()
    {


        try
        {
            connection = DriverManager.getConnection(url,databaseUser,databasePassword);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return connection;

    }

    public void closeConnection (ResultSet resultSet, PreparedStatement preparedStatement, Connection connectDB)
    {
        if (resultSet!=null)
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        if (preparedStatement!=null)
        {
            try
            {
                preparedStatement.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        if (connectDB!=null)
        {
            try
            {
                connectDB.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

}
