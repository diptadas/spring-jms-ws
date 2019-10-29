package edu.baylor.ecs.csi5324.service;

import edu.baylor.ecs.csi5324.exception.RecordNotFoundException;
import edu.baylor.ecs.csi5324.model.AgeGroup;
import edu.baylor.ecs.csi5324.model.Person;
import edu.baylor.ecs.csi5324.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public Person save(Person person) {
        log.info("Registering " + person.getName());
        return personRepository.save(person);
    }

    public Person findPersonById(long id) {
        return personRepository.findById(id).orElse(null);
    }

    public List<Person> findAllPersons() {
        return personRepository.findAll();
    }

    public void removePersonById(long id) throws RecordNotFoundException {
        log.info("Removing person by id " + id);

        // remove associations
        Person person = personRepository.findById(id).orElse(null);
        if (person != null) {
            person.removeAssociations();
            personRepository.delete(person);
        } else {
            throw new RecordNotFoundException("Person not found");
        }
    }

    public List<AgeGroup> findAllContestantsGroupByAge() {
        return personRepository.groupContestantByAge();
    }
}
