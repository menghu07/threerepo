package com.apeny.servletjsp.guava;

import com.google.common.base.Optional;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by monis on 2018/11/23.
 */
public class GuavaTest {

    @Test
    public void testGuavaOptional() {
        Optional<Integer> optional = Optional.of(12);
        if (!optional.isPresent()) {
            System.out.println("optional: " + optional);
        }
        optional.asSet();
    }

    @Test
    public void testIterator() {
        Collection<String> one = new ArrayList<>();
        one.add("122");
        one.add("293");
        one.add("29393");
        String ex = one.iterator().next();
        String ex2 = one.iterator().next();
        System.out.println("first ex: " + ex + ", ex2: " + ex2);
        for (String e : one) {
            System.out.println("hi是的from iterator: " + e);
        }
    }
}
