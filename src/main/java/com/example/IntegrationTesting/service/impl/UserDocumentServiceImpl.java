package com.example.IntegrationTesting.service.impl;

import com.example.IntegrationTesting.documents.UserDocument;
import com.example.IntegrationTesting.exception.NotFoundException;
import com.example.IntegrationTesting.repository.UserDocumentRepository;
import com.example.IntegrationTesting.service.UserDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDocumentServiceImpl implements UserDocumentService {
    private final UserDocumentRepository userDocumentRepository;

    @Override
    public UserDocument createUser(UserDocument userDocument) {
        return userDocumentRepository.save(userDocument);
    }

    @Override
    public List<UserDocument> getUsersByFirstName(String firstName) {
        return userDocumentRepository.findUsersByFirstName(firstName).stream().collect(Collectors.toList());
    }

    @Override
    public List<UserDocument> getUsersByLastName(String lastName) {
        return userDocumentRepository.findUsersByLastName(lastName).stream().collect(Collectors.toList());
    }

    @Override
    public UserDocument getUserByEmail(String email) {
        return userDocumentRepository.findUserByEmail(email);
    }

    @Override
    public List<UserDocument> getUsersByAgeGreaterThan(int age) {
        return userDocumentRepository.findByAgeGreaterThan(age).stream().collect(Collectors.toList());
    }

    @Override
    public List<UserDocument> getUsersByIsMarried(Boolean isMarried) {
        return userDocumentRepository.findByIsMarried(isMarried).stream().collect(Collectors.toList());
    }

    @Override
    public void deleteUsersById(Long id) {
        if(userDocumentRepository.existsById(id)) {
            userDocumentRepository.deleteById(id);
        } else {
            throw new NotFoundException("User not found");
        }
    }
}

