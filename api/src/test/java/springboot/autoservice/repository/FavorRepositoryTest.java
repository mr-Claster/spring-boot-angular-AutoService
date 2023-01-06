package springboot.autoservice.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import springboot.autoservice.model.Favor;
import java.math.BigDecimal;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FavorRepositoryTest {
    @Container
    static PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:13.1")
            .withDatabaseName("test")
            .withPassword("root")
            .withUsername("root");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
    }

    @Autowired
    private FavorRepository favorRepository;

    @Test
    @Sql("/scripts/init_favor.sql")
    void shouldChangeFavorStatusById() {
        favorRepository.changeFavorStatusById(1L, Favor.Status.PAID);
        Favor expected = new Favor();
        expected.setId(1L);
        expected.setFavorName("favor name");
        expected.setPrice(BigDecimal.valueOf(100).setScale(2));
        expected.setStatus(Favor.Status.PAID);

        Favor actual = favorRepository.findById(1L).get();
        Assertions.assertEquals(expected, actual);
    }
}
