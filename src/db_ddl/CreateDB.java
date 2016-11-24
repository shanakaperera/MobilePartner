/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_ddl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Shanaka P
 */
public class CreateDB {

    private static CreateDB singletonForm = new CreateDB();

    private CreateDB() {
    }

    public static CreateDB getInstance() {
        return singletonForm;
    }

    public boolean DBExist() {
        Properties dbProps = new Properties();
        dbProps.put("user", "root");
        dbProps.put("password", "nbuser");
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile_partner", dbProps);
            return conn != null;
        } catch (SQLException ex) {
            return false;
        }

    }

    public Connection getConnection() {
        Properties dbProps = new Properties();
        dbProps.put("user", "root");
        dbProps.put("password", "nbuser");
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", dbProps);
            return conn;
        } catch (SQLException e) {
            return null;
        }
    }

//    public static void main(String[] args) throws SQLException, URISyntaxException, IOException {
//        if (!CreateDB.getInstance().DBExist()) {
//            File file = new File(CreateDB.class.getResource("mobile_partner_ddl.sql").toURI());
//            ArrayList<String> createQueries = ReadQuery.createQueries(file.getCanonicalPath());
//            for (String query : createQueries) {
//                try (Connection conn = CreateDB.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
//                    ps.execute();
//                    ps.close();
//                }
//            }
//        }
//
//    }
}
