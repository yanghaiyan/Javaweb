package com.javaweb.common;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;

public class examLoggerAppender extends DailyRollingFileAppender {

    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {
        return this.getThreshold().equals(priority);
    }
}
