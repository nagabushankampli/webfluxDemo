package com.nagabushan.webfluxDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class ContactService {

    private final ApplicationEventPublisher publisher;
    private final ContactRepository contactRepository;

    ContactService(ApplicationEventPublisher publisher,ContactRepository contactRepository){
        this.publisher = publisher;
        this.contactRepository = contactRepository;
    }

    public Flux<Contact> all(){
        return this.contactRepository.findAll();
    }

    public Mono<Contact> get(String id){
        return this.contactRepository.findById(id);
    }

    public Mono<Contact> update(String id,String name,String email){
        return this.contactRepository
                    .findById(id)
                    .map(p->new Contact(p.getId(),name,email))
                    .flatMap(this.contactRepository::save);
    }

    public Mono<Contact> delete(String id){
        return this.contactRepository
                   .findById(id)
                   .flatMap(p->this.contactRepository.deleteById(p.getId()).thenReturn(p));

    }

    public Mono<Contact> create(String name){
        return this.contactRepository
                   .save(new Contact(UUID.randomUUID().toString(),name,name+"@bushan.com"))
                   .doOnSuccess(contact -> this.publisher.publishEvent(new ContactCreatedEvent(contact)));
    }
}
