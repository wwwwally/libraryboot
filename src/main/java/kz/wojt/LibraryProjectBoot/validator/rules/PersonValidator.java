package kz.wojt.LibraryProjectBoot.validator.rules;

import kz.wojt.LibraryProjectBoot.models.Person;
import kz.wojt.LibraryProjectBoot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if(peopleService.getPersonByName(person.getName()).isPresent())
            errors.rejectValue("name", "", "Person already exists");
    }
}
