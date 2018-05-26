package com.apeny.servletjsp.chapter10;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apeny on 2018/5/26.
 */
public class Validator {

    public static List<String> validate(ProductForm productForm) {
        List<String> errors = new ArrayList<>();
        if (productForm.getName() == null || productForm.getName().trim().isEmpty()) {
            errors.add("Product Name is empty");
        }
        if (productForm.getPrice() == null || productForm.getPrice().trim().isEmpty()) {
            errors.add("Product Price is empty");
        } else {
            try {
                Double.parseDouble(productForm.getPrice());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                errors.add("Product Price Format wrong");
            }

        }
        return errors;
    }
}
