package com.apeny.environment;

import java.io.Console;
import java.util.Formatter;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Created by apeny on 2017年09月08日.
 */
public class SystemEnvironment {
    public static void main(String[] args) {
        System.out.println("java properties: -Dmave.test.skiptest=true:");
        Properties properties = System.getProperties();
        System.out.println("mave.test.skiptest = " + properties.get("mave.test.skiptest"));
        for(Object key: properties.keySet()) {
            System.out.println("key: " + key + " = " + properties.get(key));
        }
//        properties.list(System.out);
        Map<String, String> envs = System.getenv();
        for (Map.Entry<String, String> entry : envs.entrySet()) {
            System.out.println("env key: " + entry.getKey() + " = " + entry.getValue());
        }
        Console console = System.console();
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.US);

        // Explicit argument indices may be used to re-order output.
        formatter.format("%4$2s %3$2s %2$2s %1$2s", "a", "b", "c", "d");
        System.out.println(sb);
        System.out.println(console);
        new Integer(102);
//        console.format("%s", "xxx");
        response();
    }

    private static void response() {

        String rs = ".*" + escapeRegexSpecialWords("shibai????").replace("XXX", ".+") + ".*";
        System.out.println("is matching: " + "shibai????".matches(rs));

    }

    private static String escapeRegexSpecialWords(String str) {
        String localStr = str;
        if (str != null) {
            for (String specialWord : REGEX_SPECIAL_WORDS) {
                if (str.contains(specialWord)) {
                    localStr = localStr.replace(specialWord, "\\" + specialWord);
                }
            }
        }
        return localStr;
    }
    // 正则表达式需要转移的特殊字符
    private static final String[] REGEX_SPECIAL_WORDS = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};

}
