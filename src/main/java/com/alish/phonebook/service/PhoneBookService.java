package com.alish.phonebook.service;

import com.alish.phonebook.model.Contact;
import com.alish.phonebook.repository.PhoneBookRepo;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        if (contact.getGithubId() != null)
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
        try {
        ResponseEntity<Map[]> response = restTemplate.getForEntity(url, Map[].class);
        if (response.getStatusCode() != HttpStatus.OK)
            return;

        Map[] maps = response.getBody();

        assert maps != null;
        List<String> list = Arrays.stream(maps)
                .map((map) -> (String) map.get("name")).collect(Collectors.toList());

        contact.setGithubRepositories(list);
        phoneBookRepo.save(contact);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }

    }
}
