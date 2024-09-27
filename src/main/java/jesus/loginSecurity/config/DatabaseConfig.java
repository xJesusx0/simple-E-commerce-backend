package jesus.loginSecurity.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class DatabaseConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.load();
    }

    @Bean
    DataSource dataSource(Dotenv dotenv){
        return DataSourceBuilder.create()
        .url(dotenv.get("DB_URL"))
        .username(dotenv.get("DB_USERNAME"))
        .password(dotenv.get("DB_PASSWORD"))
        .build();
    }
}
