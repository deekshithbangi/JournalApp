package com.deekshith.journal.serivce;

import com.deekshith.journal.entity.JournalEntity;
import com.deekshith.journal.respository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntrySerivce {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntity myEntry) {
        journalEntryRepository.save(myEntry);
    }

    public List<JournalEntity> findAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity>  findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }

}