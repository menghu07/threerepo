package com.apeny.service.infr.impl;

import com.apeny.domain.Person;
import com.apeny.service.infr.PersonService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahu on 2017年09月16日.
 */
public class PersonServiceImpl implements PersonService {

    private static final List<Person> PERSONS = new ArrayList<>();

    static {
        PERSONS.add(new Person(1, "new1", "new york"));
        PERSONS.add(new Person(2, "washing", "washington dc"));
    }

    @Override
    public Person getPerson(int id) {
        if (id > PERSONS.size()) {
            return null;
        }
        return PERSONS.get(id);
    }
}