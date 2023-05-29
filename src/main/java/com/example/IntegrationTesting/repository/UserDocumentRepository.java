package com.example.IntegrationTesting.repository;

import com.example.IntegrationTesting.documents.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserDocumentRepository extends MongoRepository<UserDocument, Long> {
    List<UserDocument> findUsersByFirstName(String firstName);
    List<UserDocument> findUsersByLastName(String lastName);
    UserDocument findUserByEmail(String email);
    List<UserDocument> findByAgeGreaterThan(int age);
    List<UserDocument> findByIsMarried(Boolean isMarried);
}
