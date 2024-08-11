package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    //настройку соеденения с БД
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/kataaсademy";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Gjikbljvjq1";


    private static SessionFactory sessionFactory;
//    static {
//        try {
//            Class.forName(DB_DRIVER);
//
//            sessionFactory = new Configuration()
//                    .setProperty("hibernate.connection.driver_class", DB_DRIVER)
//                    .setProperty("hibernate.connection.url", DB_URL)
//                    .setProperty("hibernate.connection.username", DB_USERNAME)
//                    .setProperty("hibernate.connection.password", DB_PASSWORD)
//                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect")
//                    .setProperty("hibernate.show_sql", "true")
//                    .addAnnotatedClass(User.class)
//                    .buildSessionFactory();
//        } catch (ClassNotFoundException | ExceptionInInitializerError e) {
//            e.printStackTrace();
//            throw new ExceptionInInitializerError(e);
//        }
//    }

        public static SessionFactory getSessionFactory() {
            if (sessionFactory == null) {
                try {
                    Configuration configuration = new Configuration();

                    // Hibernate settings equivalent to hibernate.cfg.xml's properties
                    Properties settings = new Properties();
                    settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                    settings.put(Environment.URL, "jdbc:mysql://localhost:3306/kataaсademy");
                    settings.put(Environment.USER, "root");
                    settings.put(Environment.PASS, "Gjikbljvjq1");
                    settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                    settings.put(Environment.SHOW_SQL, "true");

                    settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                    settings.put(Environment.HBM2DDL_AUTO, "");

                    configuration.setProperties(settings);

                    configuration.addAnnotatedClass(User.class);

                    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties()).build();

                    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return sessionFactory;
        }

//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }


    static {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL,DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void executeUpdate(String sql) {
        try (Connection connection = getConnection();
             java.sql.Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
