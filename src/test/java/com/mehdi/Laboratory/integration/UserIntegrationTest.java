package com.mehdi.Laboratory.integration;

import com.mehdi.Laboratory.dto.register.AddUserReqDTO;
import com.mehdi.Laboratory.shared.constants.ResponseCodePool;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserIntegrationTest {

    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:11-alpine")
            .withDatabaseName("test")
            .withUsername("admin")
            .withPassword("admin");

    @DynamicPropertySource
    static void setPostgresSQLProperties(DynamicPropertyRegistry dynamicPropertyRegistrar) {
        postgreSQLContainer.start();
        dynamicPropertyRegistrar.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistrar.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistrar.add("spring.datasource.password", postgreSQLContainer::getPassword);
        dynamicPropertyRegistrar.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);


        // to avoid adding hikari-cp configuration in app-test.yml which includes h2 configuration
        dynamicPropertyRegistrar.add("spring.datasource.hikari.maximum-pool-size", () -> "10");
        dynamicPropertyRegistrar.add("spring.datasource.hikari.minimum-idle", () -> "5");
        dynamicPropertyRegistrar.add("spring.datasource.hikari.idle-timeout", () -> "30000");
        dynamicPropertyRegistrar.add("spring.datasource.hikari.connection-timeout", () -> "20000");
    }

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testSuccessCreateUser() throws Exception {

        AddUserReqDTO addUserReqDTO = new AddUserReqDTO("username", "fname", "lname", "strongPass", 1731412313L);

        Response post = given()
                .contentType("application/json")
                .body(addUserReqDTO)
                .when()
                .post("/api/v1/users/register");

        assertThat(post.getStatusCode()).isEqualTo(200);


        assertThat(post.jsonPath())
                .isNotNull()
                .satisfies(s -> {
                    assertThat(s.getString("username")).isEqualTo(addUserReqDTO.getUsername());
                    assertThat(s.getInt("code")).isEqualTo(ResponseCodePool.SUCCESS.getCode());
                    assertThat(s.getString("message")).isEqualTo(ResponseCodePool.SUCCESS.getMessage());
                });

    }
}
