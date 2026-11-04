package com.deekshith.journal.service;

import com.deekshith.journal.entity.JournalEntity;
import com.deekshith.journal.entity.User;
import com.deekshith.journal.respository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userSerivce;

    @Transactional
    public void saveEntry(JournalEntity myEntry, String userName) {
        try {
            User user = userSerivce.findByUserName(userName);
            myEntry.setDate(LocalDateTime.now());
            JournalEntity saved = journalEntryRepository.save(myEntry);
            user.getJournalEntries().add(saved);
            userSerivce.saveEntry(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An Error occured while the entry", e);
        }
    }

    public void updateEntry(JournalEntity journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntity> findAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            User user = userSerivce.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userSerivce.saveEntry(user);
                journalEntryRepository.deleteById(id);
            }
            return removed;
        } catch (Exception e) {
            throw new RuntimeException("An error occured while trying to delete an entry: " + e);
        }
    }

}