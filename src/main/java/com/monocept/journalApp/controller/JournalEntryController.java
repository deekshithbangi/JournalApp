package com.monocept.journalApp.controller;

import com.monocept.journalApp.entity.JournalEntry;
import com.monocept.journalApp.entity.User;
import com.monocept.journalApp.service.JournalEntryService;
import com.monocept.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    // GET - To See All Entries
    @GetMapping("/{userName}")
    public ResponseEntity<?>  getJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // POST - To Entry an Entry
    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry>  createEntry(@PathVariable String userName, @RequestBody JournalEntry myEntry) {
        try {
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // GET - To see a Specific Entry
    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry>  getJournalEntryById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE - To Remove an Entry
    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?>  deleteJournalEntryById(@PathVariable String userName, @PathVariable ObjectId myId) {
        journalEntryService.deleteById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // PUT - To Update an Existing Entry
    // PathVariable -> localhost:8080/journal/id
    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        JournalEntry old   = journalEntryService.findById(myId).orElse(null);
        if (old != null) {
            old.setTitle(newEntry.getTitle() != null && newEntry.getTitle() != "" ? newEntry.getTitle() : old.getTitle());
            old.setDescription(newEntry.getDescription() != null && newEntry.getDescription() != "" ? newEntry.getDescription() : old.getDescription());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
