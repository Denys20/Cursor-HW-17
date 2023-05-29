package com.example.IntegrationTesting.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class UserDocument {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private Boolean isMarried;
}
