package com.apeny.reference;

import com.apeny.serializable.OriginalSerializer;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * Created by monis on 2018/9/2.
 */
public class TestReference {

    public static void main(String[] args) {
        testReference();
    }

    private static void testReference() {
        SoftReference<OriginalSerializer> originalSerializerSoftReference = new SoftReference<>(new OriginalSerializer(), new ReferenceQueue<>());
        System.out.println("before clear by code, referent = " + originalSerializerSoftReference.get());
        originalSerializerSoftReference.clear();
        System.out.println("after clear by code, referent = " + originalSerializerSoftReference.get());
        originalSerializerSoftReference.enqueue();
    }
}
