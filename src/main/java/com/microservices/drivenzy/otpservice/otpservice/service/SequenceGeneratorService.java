package com.microservices.drivenzy.otpservice.otpservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


import com.microservices.drivenzy.otpservice.otpservice.modal.DatabaseSequence;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class SequenceGeneratorService {

    private MongoOperations mongoOperations;

    @Autowired
    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public Long getNextSequence(String sequenceName) {
    	DatabaseSequence counter =new DatabaseSequence();
    	try {
    		counter = mongoOperations.findAndModify(
                    new Query(Criteria.where("_id").is(sequenceName)),
                    new Update().inc("seq", 1),
                    options().returnNew(true).upsert(true),DatabaseSequence.class
                   );
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
    	
        
        return counter.getSeq();
    }
}