package com.autsoft.simpleblog.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

//todo: out of time

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class BlogPostControllerTests {

    private final TestRestTemplate testRestTemplate;

    @LocalServerPort
    private Integer port = 0;

    public BlogPostControllerTests(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    @Test
    public void test_Get_BlogPost(){

    }

    @Test
    public void test_Create_BlogPost(){

    }

    @Test
    public void test_Update_BlogPost(){

    }

    @Test
    public void test_Delete_BlogPost(){

    }

    @Test
    public void test_Assign_Category_To_BlogPost(){

    }

    @Test
    public void test_Remove_Category_To_BlogPost(){

    }

    @Test
    public void test_Paged_Filtered_On_Category_Tags(){

    }
}