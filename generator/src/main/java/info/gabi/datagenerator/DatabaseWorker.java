package info.gabi.datagenerator;

import org.ujorm.tools.jdbc.JdbcBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseWorker {

    private static String url;
    private static String user;
    private static String password;

    private boolean getProperties() {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            String filename = "config.properties";
            input = DatabaseWorker.class.getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                System.out.println("Не нашли файл" + filename);
            }
            properties.load(input);
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            return !properties.isEmpty();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private Connection checkDbConnection() {
        if(getProperties()) {
            try {
                return DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void insertNewAddress(Record record) {
        Connection connection = checkDbConnection();
        if (connection != null) {
            JdbcBuilder sql = new JdbcBuilder()
                    .write("INSERT INTO address (")
                    .columnInsert("postcode", record.getZipCode())
                    .columnInsert("country", record.getCountry())
                    .columnInsert("region", record.getRegion())
                    .columnInsert("city", record.getCity())
                    .columnInsert("street", record.getStreet())
                    .columnInsert("house", record.getBuilding())
                    .columnInsert("flat", record.getApartment())
                    .write(")");
            sql.executeUpdate(connection);
        }
    }

    public void insertNewPerson(Record record){
        //если человек с таким фио уже есть то делаем апдейт этой записи
        Connection connection = checkDbConnection();
        if (connection != null) {
            try {
            JdbcBuilder sql = new JdbcBuilder()
                    .write("select id, count (")
                    .column("*")
                    .write(") FROM persons t WHERE")
                    .andCondition("surname", "=", record.getSurname())
                    .andCondition("name", "=", record.getName())
                    .andCondition("middlename", "=", record.getPatronymic());
                for (ResultSet rs : sql.executeSelect(connection)) {
                    int count = rs.getInt(2);
                    if(count>0)
                    {
                        int id = rs.getInt(1);
                        sql = new JdbcBuilder()
                                .write("UPDATE persons SET")
                                .columnUpdate("birthday", "")
                                .columnUpdate("gender", record.getGender())
                                .columnUpdate("inn", record.getInn())
                                .columnUpdate("address_id", '"')
                                .write("WHERE")
                                .andCondition("id", "=", id);
                        sql.executeUpdate(connection);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            JdbcBuilder sql = new JdbcBuilder()
                    .write("INSERT INTO persons (")
                    .columnInsert("surname", record.getSurname())
                    .columnInsert("name", record.getName())
                    .columnInsert("middlename", record.getPatronymic())
                    .columnInsert("birthday", record.getBirthDate())
                    .columnInsert("gender", record.getGender())
                    .columnInsert("inn", record.getInn())
                    .columnInsert("address_id", '"')
                    .write(")");
            sql.executeUpdate(connection);
        }
    }
}
