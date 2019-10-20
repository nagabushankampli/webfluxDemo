package com.nagabushan.webfluxDemo;

import org.springframework.context.ApplicationEvent;

public class ContactCreatedEvent extends ApplicationEvent {

    public  ContactCreatedEvent(Contact source){
        super(source);
    }
}
