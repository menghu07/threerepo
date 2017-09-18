package com.apeny.log;

import org.apache.log4j.xml.DOMConfigurator;

import java.util.logging.Logger;

/**
 * Created by apeny on 2017年09月08日.
 */
public class Log4jMain {
    public static void main(String[] args) {
        DOMConfigurator.configure("src/main/resources/config/log4j.xml");
        Logger jdkLoggerComApeny = Logger.getLogger("com.apeny");
        Logger jdkLoggerComApenyDot = Logger.getLogger("com.apeny.dot");
        Logger jdkLoggerComApenyDotLog4j = Logger.getLogger("com.apeny.dot.log4j");
        System.out.println("comapenylog4j.parent = " + (jdkLoggerComApenyDotLog4j.getParent() == jdkLoggerComApeny));
        jdkLoggerComApeny.info("Com.Apeny");
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("com.apeny");
        logger.info("xyoffff");
    }

}
