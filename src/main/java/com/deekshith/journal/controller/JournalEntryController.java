package com.deekshith.journal.controller;

import com.deekshith.journal.entity.JournalEntity;
import com.deekshith.journal.serivce.JournalEntrySerivce;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntrySerivce journalEntrySerivce;

    // Get all
    @GetMapping
    public List<JournalEntity>  getAll() {
        return journalEntrySerivce.findAll();
    }

    // Get
    @GetMapping("/id/{myId}")
    public JournalEntity getEntryById(@PathVariable ObjectId myId) {
        return journalEntrySerivce.findById(myId).orElse(null);
    }

    // Post -- to add
    @PostMapping
    public boolean postEntryById(@RequestBody JournalEntity myEntry) {
        myEntry.setDate(LocalDateTime.now());
        journalEntrySerivce.saveEntry(myEntry);
        return true;
    }

    // Put -- to update
    @PutMapping("id/{myId}")
    public JournalEntity putEntryById(@RequestBody JournalEntity newEntry, @PathVariable ObjectId myId) {
        JournalEntity old = journalEntrySerivce.findById(myId).orElse(null);
        if (old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
        }
        journalEntrySerivce.saveEntry(old);
        return old;
    }

    // Delete
    @DeleteMapping("id/{myId}")
    public boolean deleteById(@PathVariable ObjectId myId) {
        journalEntrySerivce.deleteById(myId);
        return true;
    }
}
