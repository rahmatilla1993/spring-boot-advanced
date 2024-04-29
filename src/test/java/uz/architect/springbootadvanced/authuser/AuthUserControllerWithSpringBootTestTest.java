package uz.architect.springbootadvanced.authuser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class AuthUserControllerWithSpringBootTestTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createSuccessfulAuthUserTest() throws Exception {
        AuthUserDto dto = AuthUserDto.builder()
                .fullName("Johnny Doe")
                .username("johnny@doe.com")
                .password("123")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/authuser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void createFailedAuthUserTest() throws Exception {
        AuthUserDto dto = AuthUserDto.builder()
                .fullName("Johnny Doe")
                .password("123")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/authuser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void update() throws Exception {
        AuthUser authUser = AuthUser.builder()
                .id(1)
                .fullName("Edited name")
                .username("noreply@mail.com")
                .password("asdf")
                .build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/authuser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals("Successfully Updated - User", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void delete() throws Exception {
        int id = 1;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/authuser/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String message = response.getContentAsString();
        assertEquals("Successfully Deleted - User", message);
    }

    @Test
    void get() throws Exception {
        int id = 1;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/authuser/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        AuthUserDto dto = objectMapper.readValue(json, AuthUserDto.class);
        assertEquals("johnny@doe.com", dto.getUsername());
    }

    @Test
    void list() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/authuser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String jsonArray = mvcResult.getResponse().getContentAsString();
        List<AuthUserDto> list = objectMapper.readValue(jsonArray, new TypeReference<List<AuthUserDto>>() {
        });
        assertEquals(0, list.size());
    }
}