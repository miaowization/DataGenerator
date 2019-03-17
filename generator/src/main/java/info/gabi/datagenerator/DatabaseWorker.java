package info.gabi.datagenerator;

import lombok.extern.slf4j.Slf4j;
import org.ujorm.tools.jdbc.JdbcBuilder;
import org.ujorm.tools.set.LoopingIterator;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
class DatabaseWorker {

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
                log.info("Не нашли файл " + filename);
            } else {
                properties.load(input);
                url = properties.getProperty("url");
                user = properties.getProperty("user");
                password = properties.getProperty("password");
                return !properties.isEmpty();
            }
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
        if (getProperties()) {
            try {
                return DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                try {
                    return DriverManager.getConnection(
                            url + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                            user, password);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    void insertRecords(List<Record> recordList) {
        for (Record record : recordList) {
            int addressId = insertAddress(record);
            log.info("Строки в бд были обновлены: " + insertPerson(record, addressId));
        }
    }

    private int insertAddress(Record record) {
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
            sql = new JdbcBuilder()
                    .write("SELECT LAST_INSERT_ID();")
                    .write("");
            try {
                LoopingIterator<ResultSet> resultSets = sql.executeSelect(connection);
                return resultSets.next().getInt(1);
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }
        return 0;
    }

    private int insertPerson(Record record, int addressId) {
        //если человек с таким фио уже есть то делаем апдейт этой записи
        Connection connection = checkDbConnection();
        if (connection != null) {
            try {
                JdbcBuilder sql = new JdbcBuilder()
                        .write("select id, count(*) FROM persons t WHERE")
                        .andCondition("surname", "=", record.getSurname())
                        .andCondition("name", "=", record.getName())
                        .andCondition("middlename", "=", record.getPatronymic())
                        .write("group by id");
                if (sql.executeSelect(connection).hasNext()) {
                    ResultSet rs = sql.executeSelect(connection).next();
                    int count = rs.getInt(2);
                    if (count > 0) {
                        int id = rs.getInt(1);
                        sql = new JdbcBuilder()
                                .write("UPDATE persons SET")
                                .columnUpdate("birthday", record.getBirthDate())
                                .columnUpdate("gender", record.getGender())
                                .columnUpdate("inn", record.getInn())
                                .columnUpdate("address_id", addressId)
                                .write("WHERE")
                                .andCondition("id", "=", id);
                        return sql.executeUpdate(connection);
                    }
                } else {
                    sql = new JdbcBuilder()
                            .write("INSERT INTO persons (")
                            .columnInsert("surname", record.getSurname())
                            .columnInsert("name", record.getName())
                            .columnInsert("middlename", record.getPatronymic())
                            .columnInsert("birthday", record.getBirthDate())
                            .columnInsert("gender", record.getGender())
                            .columnInsert("inn", record.getInn())
                            .columnInsert("address_id", addressId)
                            .write(")");
                    return sql.executeUpdate(connection);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    List<Record> getPersons(int size) {
        List<Record> records = new ArrayList<>();
        Connection connection = checkDbConnection();
        if (connection != null) {
            try {
                JdbcBuilder sql = new JdbcBuilder()
                        .write("select * FROM persons, address")
                        .andCondition("where address_id", "=", "address.id")
                        .write("limit " + size);
                for (ResultSet rs : sql.executeSelect(connection)) {
                    Record record = new Record();
                    record.setSurname(rs.getString("surname"));
                    record.setName(rs.getString("name"));
                    record.setPatronymic(rs.getString("middlename"));
                    record.setGender(rs.getString("gender"));
                    record.setBirthDate(LocalDate.parse(rs.getString("birthday"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    record.setInn(rs.getString("inn"));
                    record.setZipCode(rs.getString("postcode"));
                    record.setRegion(rs.getString("region"));
                    record.setCity(rs.getString("city"));
                    record.setStreet(rs.getString("street"));
                    record.setBuilding(rs.getString("home"));
                    record.setApartment(rs.getString("flat"));

                    log.info(record.toString());
                    records.add(record);
                }
                return records;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
