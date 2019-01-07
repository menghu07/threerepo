package com.apeny.servletjsp.guava;

import com.google.common.base.Optional;
import org.junit.Test;

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
}
