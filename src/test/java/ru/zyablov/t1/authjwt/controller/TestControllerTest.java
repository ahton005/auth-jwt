package ru.zyablov.t1.authjwt.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TestControllerTest {

    @InjectMocks
    TestController controller;

    @Test
    void helloAdmin_200OK() {
        // given
        String msg = "Hello Admin!!!";
        // when
        var res = controller.helloAdmin();

        // then
        assertEquals(HttpStatusCode.valueOf(200), res.getStatusCode());
        assertEquals(msg, res.getBody());
    }

    @Test
    void helloUser_200OK() {
        // given
        String msg = "Hello User!!!";
        // when
        var res = controller.helloUser();

        // then
        assertEquals(HttpStatusCode.valueOf(200), res.getStatusCode());
        assertEquals(msg, res.getBody());
    }

    @Test
    void helloAnon_200OK() {
        // given
        String msg = "Hello Anon!!!";
        // when
        var res = controller.helloAnon();

        // then
        assertEquals(HttpStatusCode.valueOf(200), res.getStatusCode());
        assertEquals(msg, res.getBody());
    }
}