package com.deekshith.journal.controller;

import com.deekshith.journal.entity.JournalEntity;
import com.deekshith.journal.entity.User;
import com.deekshith.journal.service.JournalEntryService;
import com.deekshith.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userSerivce;

    // Get all
    @GetMapping("user/{userName}")
    public ResponseEntity<?> getAll(@PathVariable String userName) {
        User user = userSerivce.findByUserName(userName);
        List<JournalEntity> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get - by
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntity> getEntryById(@PathVariable ObjectId myId) {
        Optional<JournalEntity> journalEntity = journalEntryService.findById(myId);
        if (journalEntity.isPresent()) {
            return new ResponseEntity<>(journalEntity.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Post -- journalentry saved in journal_entries and the assigned the journalentry ObjectId to the users JournalEntity ArrayList
    @PostMapping("user/{userName}")
    public ResponseEntity<JournalEntity> postEntryById(@RequestBody JournalEntity myEntry, @PathVariable String userName) {
        try {
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Put -- to update
    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> putEntryById(
            @RequestBody JournalEntity newEntry,
            @PathVariable ObjectId myId,
            @PathVariable String userName
    ) {
        JournalEntity old = journalEntryService.findById(myId).orElse(null);
        if (old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete
    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId, @PathVariable String userName) {
        journalEntryService.deleteById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}