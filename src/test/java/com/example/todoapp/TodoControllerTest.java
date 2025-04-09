package com.example.todoapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ModelResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@AutoConfigureMockMvc
@SpringBootTest
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddTodo_withEmptyTask_shouldReturnValidationError() throws Exception{
        mockMvc.perform(post("/add")
                .param("task", "") // からの値を送る
                .with(csrf())) // CSRF対策（form送信には必要）
                .andExpect(status().isOk()) // エラーのときはindexに戻る=200 OK
                .andExpect(view().name("index")) // index.htmlに戻る
                .andExpect(model().attributeHasFieldErrors("todo", "task")); // taskにエラーがある


    }
}
