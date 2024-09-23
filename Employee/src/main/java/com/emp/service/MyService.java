package com.emp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyService {
    private static final Logger logger = LoggerFactory.getLogger(MyService.class);

    public void doSomething() {
        logger.info("This is an info log message");
        logger.debug("This is a debug log message");
        logger.error("This is an error log message");
    }
}
