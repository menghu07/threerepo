package com.apeny.httprpc;

import com.apeny.httprpc.domain.Person;

/**
 * Created by ahu on 2017年09月16日.
 */
public class HelloService implements BaseService {
    @Override
    public Person getPerson(int id) {
        return new Person(id, "new", "new york");
    }
}
