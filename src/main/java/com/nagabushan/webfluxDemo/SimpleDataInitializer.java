package com.nagabushan.webfluxDemo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Slf4j
@Component
public class SimpleDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private  final ContactRepository contactRepository;

    public SimpleDataInitializer(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent){

        contactRepository
                .deleteAll()
                .thenMany(
                        Flux.just("Nag","Bushan","Kampli","Nagraj","Welcome")
                        .map(name-> new Contact(UUID.randomUUID().toString(),name,name+"@bushan.com"))
                        .flatMap(contact->contactRepository.save(contact))
                )
                .thenMany(contactRepository.findAll())
                .subscribe(contact -> log.info("Saving"+contact.toString()));
    }

}
