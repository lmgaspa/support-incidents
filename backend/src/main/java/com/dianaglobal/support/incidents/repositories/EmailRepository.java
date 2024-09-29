package com.dianaglobal.support.incidents.repositories;

import com.dianaglobal.support.incidents.models.EmailModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailRepository extends MongoRepository<EmailModel, Long> {
}
