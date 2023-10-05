package com.example.issuetracker.controller;



import com.example.issuetracker.model.Issues;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final List<Issues> issues = new ArrayList<>();

    public IssueController() {
        issues.add(new Issues(1L, "Issue 1", "Description 1"));
        issues.add(new Issues(2L, "Issue 2", "Description 2"));
        issues.add(new Issues(3L, "Issue 3", "Description 3"));
    }
    // Create a new issue
    @PostMapping
    public String createIssue(@RequestBody Issues issue) {
        issues.add(issue);
        return "Issue created: " + issue.toString();
    }

    // Read all issues
    @GetMapping
    public List<Issues> getAllIssues() {
        return issues;
    }

    // Read a specific issue by ID
    @GetMapping("/{id}")
    public Issues getIssueById(@PathVariable Long id) {
        Optional<Issues> optionalIssue = issues.stream().filter(issue -> issue.getId().equals(id)).findFirst();
        return optionalIssue.orElse(null);
    }

    // Update an existing issue by ID
    @PutMapping("/{id}")
    public String updateIssue(@PathVariable Long id, @RequestBody Issues updatedIssue) {
        Optional<Issues> optionalIssue = issues.stream().filter(issue -> issue.getId().equals(id)).findFirst();

        if (optionalIssue.isPresent()) {
            Issues existingIssue = optionalIssue.get();
            existingIssue.setTitle(updatedIssue.getTitle());
            existingIssue.setDescription(updatedIssue.getDescription());
            return "Issue updated: " + existingIssue.toString();
        } else {
            return "Issue not found with ID: " + id;
        }
    }

    // Delete an issue by ID
    @DeleteMapping("/{id}")
    public String deleteIssue(@PathVariable Long id) {
        Optional<Issues> optionalIssue = issues.stream().filter(issue -> issue.getId().equals(id)).findFirst();

        if (optionalIssue.isPresent()) {
            issues.remove(optionalIssue.get());
            return "Issue deleted with ID: " + id;
        } else {
            return "Issue not found with ID: " + id;
        }
    }
}

