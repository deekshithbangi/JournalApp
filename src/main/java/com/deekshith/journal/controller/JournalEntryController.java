package com.deekshith.journal.controller;

import com.deekshith.journal.entity.JournalEntity;
import com.deekshith.journal.serivce.JournalEntrySerivce;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getAll() {
        List<JournalEntity> all = journalEntrySerivce.findAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntity> getEntryById(@PathVariable ObjectId myId) {
        Optional<JournalEntity> journalEntity = journalEntrySerivce.findById(myId);
        if (journalEntity.isPresent()) {
            return new ResponseEntity<>(journalEntity.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Post -- to add
    @PostMapping
    public ResponseEntity<JournalEntity> postEntryById(@RequestBody JournalEntity myEntry) {
        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntrySerivce.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Put -- to update
    @PutMapping("id/{myId}")
    public ResponseEntity<?> putEntryById(@RequestBody JournalEntity newEntry, @PathVariable ObjectId myId) {
        JournalEntity old = journalEntrySerivce.findById(myId).orElse(null);
        if (old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
            journalEntrySerivce.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId) {
        journalEntrySerivce.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
