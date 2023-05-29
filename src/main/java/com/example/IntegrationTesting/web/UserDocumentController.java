package com.example.IntegrationTesting.web;

import com.example.IntegrationTesting.documents.UserDocument;
import com.example.IntegrationTesting.service.UserDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/userDocuments")
public class UserDocumentController {
    private final UserDocumentService userDocumentService;

    @PostMapping("/users/create")
    public ResponseEntity create(@RequestBody UserDocument userDocument) {
        return new ResponseEntity(userDocumentService.createUser(userDocument), HttpStatus.CREATED);
    }

    @GetMapping("/firstName/{firstName}")
    public ResponseEntity getUsersByFirstName(@PathVariable String firstName) {
        return new ResponseEntity(userDocumentService.getUsersByFirstName(firstName), HttpStatus.OK);
    }

    @GetMapping("/lastName/{lastName}")
    public ResponseEntity getUsersByLastName(@PathVariable String lastName) {
        return new ResponseEntity(userDocumentService.getUsersByLastName(lastName), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity getUserByEmail(@PathVariable String email) {
        return new ResponseEntity(userDocumentService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/age/{age}")
    public ResponseEntity getUsersByAgeGreaterThan(@PathVariable int age) {
        return new ResponseEntity(userDocumentService.getUsersByAgeGreaterThan(age), HttpStatus.OK);
    }

    @GetMapping("/married/{isMarried}")
    public ResponseEntity getUsersByIsMarried(@PathVariable Boolean isMarried) {
        return new ResponseEntity(userDocumentService.getUsersByIsMarried(isMarried), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        userDocumentService.deleteUsersById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
