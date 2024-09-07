package com.ntdsoftware.homework.casillas.configuration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

/**
 * Interface for application tests, providing common configuration and context setup.
 */
@SpringBootTest
@ContextConfiguration(classes = ApplicationTestConfiguration.class)
@ActiveProfiles("Test")
public interface ApplicationTest {

}
