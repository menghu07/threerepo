package com.apeny.system;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ahu on 2017年09月16日.
 */
public class SystemInit {
    public static Logger LOGGER = null;
    public static void init() {
        DOMConfigurator.configure("src/main/resources/config/log4j.xml");
        LOGGER = LoggerFactory.getLogger("root");
        System.out.println(LOGGER);
    }
}
