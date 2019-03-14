package info.gabi.datagenerator;

import org.ujorm.tools.jdbc.JdbcBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

public class DatabaseWorker {

    public static void main(String[] args) {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            String filename = "application.properties";
            input = DatabaseWorker.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                System.out.println("Sorry, unable to find " + filename);
                return;
            }

            properties.load(input);
            System.out.println(properties);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally {
            if(input!=null){
                try
                {
                    input.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        try {
            Connection conn = DriverManager.getConnection(properties.getProperty("url"));
            JdbcBuilder sql = new JdbcBuilder()
                    .write("SELECT")
                    .column("*")
                    .write("FROM address");
            for (ResultSet rs : sql.executeSelect(conn)) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                System.out.println(id + " " + name);
            }



        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
