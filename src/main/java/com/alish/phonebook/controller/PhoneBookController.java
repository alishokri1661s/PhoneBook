package com.alish.phonebook.controller;

import com.alish.phonebook.model.Contact;
import com.alish.phonebook.service.PhoneBookService;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class PhoneBookController {

    private final PhoneBookService service;

    @Autowired
    public PhoneBookController(PhoneBookService phoneBookService) {
        this.service = phoneBookService;
    }

    @Transactional
    @GetMapping()
    public ResponseEntity<List<Contact>> getContacts(
            @And({
                    @Spec(path = "name", spec = Like.class),
                    @Spec(path = "phoneNumber", spec = Like.class),
                    @Spec(path = "email", spec = Like.class),
                    @Spec(path = "company", spec = Like.class),
                    @Spec(path = "githubId", spec = Like.class)
            }) Specification<Contact> spec
    ) {

        List<Contact> list = service.getContacts(spec);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
        Contact newContact = service.addContact(contact);
        return new ResponseEntity<>(newContact, HttpStatus.CREATED);
    }
}
