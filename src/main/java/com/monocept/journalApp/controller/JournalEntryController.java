package com.monocept.journalApp.controller;

import com.monocept.journalApp.entity.JournalEntry;

import com.monocept.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    // GET - To See All Entries
    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }

    // GET - To see a Specific Entry
    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId) {
        return journalEntryService.findById(myId).orElse(null);
    }

    // POST - To Entry an Entry
    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry) {
        myEntry.setDateTime(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return true;
    }

    // DELETE - To Remove an Entry
    @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId myId) {
        journalEntryService.deleteById(myId);
        return true;
    }

    // PUT - To Update an Existing Entry
    // PathVariable -> localhost:8080/journal/id
    @PutMapping("/{myId}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        JournalEntry old   = journalEntryService.findById(myId).orElse(null);
        if (old != null) {
            old.setTitle(newEntry.getTitle() != null && newEntry.getTitle() != "" ? newEntry.getTitle() : old.getTitle());
            old.setDescription(newEntry.getDescription() != null && newEntry.getDescription() != "" ? newEntry.getDescription() : old.getDescription());
        }
        journalEntryService.saveEntry(old);
        return old;
    }
}
