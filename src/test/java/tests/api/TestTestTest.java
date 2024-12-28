package tests.api;

import models.data.TestData;
import models.data.TestDataConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//import static helpers.datacreator.DataCreator4.PROJECT_TEMPLATES;


public class TestTestTest extends TestBase {


    @Test
    @DisplayName("[API] TEST.")
    void testTestTest() {

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createLabels(true)
                .createProjects(true)
                .createSections(true)
                .build();

        TestData testData = new TestData();
        testData = data.create(0, whatIsCreate);

        System.out.println();
    }

    @Test
    @DisplayName("[API] TEST.")
    void testTestTest2() {

        //TestDataConfig defaultAccount = new TestDataConfig(); // Все поля будут false

        //TestDataConfig allTrueAccount = new TestDataConfig(true); // Все поля будут true

        TestDataConfig account = TestDataConfig.builder()
                .createLabels(true)
                .createProjects(true)
                .build(); // Поля, которые не установлены, будут false

        System.out.println();
    }
}































