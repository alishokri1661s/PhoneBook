package com.alish.phonebook.service;

import com.alish.phonebook.model.Contact;
import com.alish.phonebook.repository.PhoneBookRepo;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhoneBookService {

    private final PhoneBookRepo phoneBookRepo;
    private final JobScheduler jobScheduler;


    @Autowired
    public PhoneBookService(PhoneBookRepo phoneBookRepo, JobScheduler jobScheduler) {
        this.phoneBookRepo = phoneBookRepo;
        this.jobScheduler = jobScheduler;
    }


    public Contact addContact(Contact contact) {
        Contact newContact = phoneBookRepo.save(contact);
        jobScheduler.enqueue(() -> getGithubRepositories(newContact));
        return newContact;
    }

    public List<Contact> getContacts(Specification<Contact> spec) {
        return phoneBookRepo.findAll(spec);
    }

    @Job(retries = 2)
    public void getGithubRepositories(Contact contact) {
        String githubId = contact.getGithubId();
        String url = "https://api.github.com/users/" + githubId + "/repos";

        RestTemplate restTemplate = new RestTemplate();
        Map[] maps;
        maps = restTemplate.getForObject(url, Map[].class);

        assert maps != null;
        List<String> list = Arrays.stream(maps)
                .map((map) -> (String) map.get("name")).collect(Collectors.toList());

        contact.setGithubRepositories(list);
        phoneBookRepo.save(contact);
    }
}
