package com.nagabushan.webfluxDemo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ContactRepository extends ReactiveMongoRepository<Contact,String> {

}
