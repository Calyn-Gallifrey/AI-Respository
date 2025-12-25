package com.codex.platform;

import com.codex.platform.controller.WelcomeController;
import com.codex.platform.service.WelcomeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WelcomeController.class)
class WelcomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WelcomeService welcomeService;

    @Test
    void shouldReturnWelcomeMessage() throws Exception {
        String message = "欢迎使用Open Ai CodeX 平台服务，世界的边界在这里开始变成无限。";
        given(welcomeService.fetchWelcomeMessage()).willReturn(message);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(message));
    }
}
