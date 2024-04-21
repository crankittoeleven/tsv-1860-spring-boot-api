package co.anastasiou.tsv1860springbootapi;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigDataSource {
    @Bean
    public static DataSource source() {
        DataSourceBuilder<?> dataSource = DataSourceBuilder.create();
        dataSource.driverClassName("com.mysql.cj.jdbc.Driver");

        dataSource.url("jdbc:mysql://localhost:3306/tsv1860");

        dataSource.username("root");

        dataSource.password("my-secret-pw");

        return dataSource.build();
    }
}