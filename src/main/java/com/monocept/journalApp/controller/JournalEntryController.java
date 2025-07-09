package com.monocept.journalApp.controller;

import com.monocept.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    // GET - To See All Entries
    @GetMapping
    public List<JournalEntry> getAll() {
        return new ArrayList<>(journalEntries.values());
    }

    // GET - To see a Specific Entry
    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable Long myId) {
            return journalEntries.get(myId);
    }

    // POST - To Entry an Entry
    @PostMapping
    public boolean addJournalEntryById(@RequestBody JournalEntry myJournalEntry) {
        journalEntries.put(myJournalEntry.getId(), myJournalEntry);
        return true;
    }

    // DELETE - To Remove an Entry
    @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable Long myId) {
        journalEntries.remove(myId);
        return true;
    }

    // PUT - To Update an Existing Entry
    // PathVariable -> localhost:8080/journal/id
    @PutMapping("/{myId}")
    public boolean updateJournalEntryById(@PathVariable Long myId, @RequestBody JournalEntry myJournalEntry) {
        journalEntries.put(myId, myJournalEntry);
        return true;
    }
}
