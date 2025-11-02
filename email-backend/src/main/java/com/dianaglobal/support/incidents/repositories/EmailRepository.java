package com.dianaglobal.support.incidents.repositories;

import com.dianaglobal.support.incidents.models.EmailModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmailRepository extends MongoRepository<EmailModel, String> {
    List<EmailModel> findByEmailTo(String emailTo);
}
