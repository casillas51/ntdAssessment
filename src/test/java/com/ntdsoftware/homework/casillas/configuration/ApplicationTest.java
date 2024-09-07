package com.ntdsoftware.homework.casillas.configuration;

import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.sql.SQLException;

@SpringBootTest
@ContextConfiguration(classes = ApplicationTestConfiguration.class)
@ActiveProfiles("Test")
public interface ApplicationTest {

    @BeforeAll
    static void beforeAll() throws SQLException, InterruptedException {
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
    }
}
