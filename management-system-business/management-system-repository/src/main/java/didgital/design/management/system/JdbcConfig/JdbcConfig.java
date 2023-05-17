package didgital.design.management.system.JdbcConfig;

import didgital.design.management.system.dao.ProjectJdbcDAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConfig {

    private String url;
    private String username;
    private String password;
    private final Properties props;
    private final InputStream in;

    public JdbcConfig(){
        props = new Properties();
        try{
            in = ProjectJdbcDAO.class.getClassLoader().getResourceAsStream("application.properties");
            props.load(in);

            //Подтягиваем значения из переменнных сред, когда используем docker-compose
            url = System.getenv("SPRING_DATASOURCE_URL");
            username = System.getenv("SPRING_DATASOURCE_USERNAME");
            password = System.getenv("SPRING_DATASOURCE_PASSWORD");
            if (url == null) {
                //Если мы запускаем программу из IDE берем значения из properties
                url = props.getProperty("url");
                username = props.getProperty("username");
                password = props.getProperty("password");
            }
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection(){
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
