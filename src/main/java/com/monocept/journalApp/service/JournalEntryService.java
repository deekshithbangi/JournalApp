package com.monocept.journalApp.service;

import com.monocept.journalApp.entity.JournalEntry;

import com.monocept.journalApp.entity.User;
import com.monocept.journalApp.repository.JournalEntryRepository;
import com.monocept.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserRepository userRepository;

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public void saveEntry(JournalEntry journalEntry, String userName) {
        User user = userRepository.findByUserName(userName);
        journalEntry.setDateTime(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userRepository.save(user);
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName) {
        User user = userRepository.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userRepository.save(user);
        journalEntryRepository.deleteById(id);
    }
}
