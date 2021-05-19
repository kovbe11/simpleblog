package com.autsoft.simpleblog.integration;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class CategoryControllerTests {

    private final TestRestTemplate testRestTemplate;

    @LocalServerPort
    private Integer port = 0;

    CategoryControllerTests(final TestRestTemplate testRestTemplate){
        this.testRestTemplate = testRestTemplate;
    }

    @Test
    public void test_Get_Category(){

    }

    @Test
    public void test_Create_Category(){

    }

    @Test
    public void test_Update_Category(){

    }

    @Test
    public void test_Update_Category_Cleanup(){

    }

    @Test
    public void test_Delete_Category(){

    }

}
