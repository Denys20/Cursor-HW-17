package com.example.IntegrationTesting.web;

import com.example.IntegrationTesting.documents.UserDocument;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserDocumentControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void setup() {
        mongoTemplate.getDb().drop();
        UserDocument userDocument1 = UserDocument.builder().id(1L).firstName("FirstName1").lastName("LastName1").email("email1@gmail.com").age(34).isMarried(false).build();
        UserDocument userDocument2 = UserDocument.builder().id(2L).firstName("FirstName2").lastName("LastName2").email("email2@gmail.com").age(24).isMarried(true).build();
        mongoTemplate.save(userDocument1);
        mongoTemplate.save(userDocument2);
    }

    @Test
    public void createUserDocumentTest() throws Exception {
        UserDocument userDocument = UserDocument.builder().id(3L).firstName("FirstName3").lastName("LastName3").email("email3@gmail.com").age(29).isMarried(true).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonUserDocument = objectMapper.writeValueAsString(userDocument);

        mockMvc.perform(post("/api/userDocuments/users/create")
                .content(jsonUserDocument)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(userDocument.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDocument.getLastName()))
                .andExpect(jsonPath("$.email").value(userDocument.getEmail()))
                .andExpect(jsonPath("$.age").value(userDocument.getAge()))
                .andExpect(jsonPath("$.isMarried").value(userDocument.getIsMarried()));
    }

    @Test
    public void getUsersByFirstNameTest() throws Exception {
        mockMvc.perform(get("/api/userDocuments/firstName/FirstName2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("FirstName2"));
    }

    @Test
    public void getUsersByLastNameTest() throws Exception {
        mockMvc.perform(get("/api/userDocuments/lastName/LastName1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName").value("LastName1"));
    }

    @Test
    public void getUserByEmailTest() throws Exception {
        mockMvc.perform(get("/api/userDocuments/email/email1@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email1@gmail.com"));
    }

    @Test
    public void getUsersByAgeGreaterThanTest() throws Exception {
        mockMvc.perform(get("/api/userDocuments/age/30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].age").value(34));
    }

    @Test
    public void getUsersByIsMarriedTest() throws Exception {
        mockMvc.perform(get("/api/userDocuments/married/true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isMarried").value("true"));
    }

    @Test
    public void deleteUserDocumentTest() throws Exception {
        mockMvc.perform(delete("/api/userDocuments/delete/1"))
                .andExpect(status().isOk());

        assertNull(mongoTemplate.findById(1L, UserDocument.class));
    }
}
