package cl.api.creausuarios.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

	@Autowired
	private MockMvc mockMvc;

    @Test
    public void testCreateUser() throws Exception {
        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content("{\"name\": \"Jane Smith\", \"email\": \"jane@example.com\"}"))
                .andExpect(status().isCreated());
    }

}
