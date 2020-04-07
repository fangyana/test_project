package com.f.pro.log.event;

import com.f.pro.domain.SysLog;
import org.springframework.context.ApplicationEvent;

public class SysLogEvent extends ApplicationEvent {

    public SysLogEvent(SysLog sysLog) {
        super(sysLog);
    }
}
