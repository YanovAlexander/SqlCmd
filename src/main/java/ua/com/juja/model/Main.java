package ua.com.juja.model;

import java.sql.*;

/**
 * Created by Alexandero on 02.06.2017.
 */
public class Main {
    public static void main(String[] args) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String database = "postgres";
            String user = "postgres";
            String password = "pass";
            Connection connection = getConnection(database, user, password);
//            connection.setAutoCommit(false);
            System.out.println("Opened model successfully");

            //CREATE
            String sql = "CREATE TABLE IF NOT EXISTS PUBLIC.USERS " +
                    "(ID SERIAL PRIMARY KEY     NOT NULL," +
                    " USERNAME          TEXT    NOT NULL, " +
                    " PASSWORD        TEXT NOT NULL)";
            update(connection, sql);


            //INSERT

            sql = "INSERT INTO users (USERNAME,PASSWORD) "
                    + "VALUES ('Allen','pass');";
            insert(connection, sql);


            //UPDATE
            sql = "UPDATE users set PASSWORD = 'passss' where ID=1;";
            update(connection, sql);


            //DELETE
//             sql = "DELETE from users where ID > 10;";
//            update(connection, sql);

            //SELECT
//             sql = "SELECT * FROM users";
//            stmt = select(connection, sql);

            //select table names

            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'");
            while (rs.next()){
                System.out.println(rs.getString("table_name"));
            }

//            connection.commit();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private static Connection getConnection(String database, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/"+ database,
                        user, password);
    }


    private static void update(Connection connection, String sql) throws SQLException {
        Statement stmt;
        stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }


    private static Statement select(Connection connection, String sql) throws SQLException {
        Statement stmt;
        ResultSet rs;
        stmt = connection.createStatement();
        rs = stmt.executeQuery(sql);
        while ( rs.next() ) {
            int id = rs.getInt("ID");
            String  username = rs.getString("USERNAME");
            String  password = rs.getString("PASSWORD");
            System.out.println( "ID = " + id );
            System.out.println( "USERNAME = " + username );
            System.out.println( "PASSWORD = " + password );
            System.out.println();
        }
        rs.close();
        stmt.close();
        return stmt;
    }

    private static void insert(Connection connection, String sql) throws SQLException {
        Statement stmt;
        stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }
}
