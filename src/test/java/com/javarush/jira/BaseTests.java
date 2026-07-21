package com.javarush.jira;

import com.javarush.jira.config.TestDataSourceConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Import(TestDataSourceConfig.class)
@ActiveProfiles("test")
abstract class BaseTests {
}
