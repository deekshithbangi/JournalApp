package com.deekshith.journal.respository;

import com.deekshith.journal.entity.JournalEntity;
import com.deekshith.journal.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String userName);
}
