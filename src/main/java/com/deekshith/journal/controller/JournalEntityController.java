package com.deekshith.journal.controller;

import com.deekshith.journal.entity.JournalEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntityController {
    Map<Long, JournalEntity> journalEntries = new HashMap<>();

    // Get all
    @GetMapping
    public List<JournalEntity> getAll() {
        return new ArrayList<>(journalEntries.values());
    }

    // Get
    @GetMapping("/id/{myId}")
    public JournalEntity getEntryById(@PathVariable Long myId) {
           return journalEntries.get(myId);
    }

    // Post -- to add
    @PostMapping
    public boolean postEntryById(@RequestBody JournalEntity myEntry) {
        journalEntries.put(myEntry.getId(), myEntry);
        return true;
    }

    // Put -- to update
    @PutMapping("id/{myId}")
    public JournalEntity putEntryById(@RequestBody JournalEntity myEntry, @PathVariable Long myId) {
        return journalEntries.put(myId, myEntry);
    }

    // Delete
    @DeleteMapping("id/{myId}")
    public JournalEntity deleteById(@PathVariable Long myId) {
        return journalEntries.remove(myId);
    }
}
