package com.alish.phonebook.repository;

import com.alish.phonebook.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneBookRepo extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {
}
