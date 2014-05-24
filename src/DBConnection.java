import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBConnection {
    public static java.sql.Connection getConnection() throws SQLException, IOException {
        Properties connectionProps = new Properties();
        connectionProps.load(new FileReader(new File("C:\\Users\\blaze.fun\\IdeaProjects\\DataMapper\\src\\configs\\dbconfig.properties")));
        String userName = connectionProps.getProperty("Username");
        String password = connectionProps.getProperty("Password");
        String dbms = connectionProps.getProperty("DBMS_TYPE");
        String serverName = connectionProps.getProperty("Server_Name");
        String portNumber = connectionProps.getProperty("Port");
        String databaseName = connectionProps.getProperty("DB_Name");

        java.sql.Connection conn = null;


        conn = DriverManager.getConnection(
                "jdbc:" + dbms + "://" +
                        serverName+":"+portNumber+"/"+databaseName,
                userName, password
        );
        return conn;
    }
}

