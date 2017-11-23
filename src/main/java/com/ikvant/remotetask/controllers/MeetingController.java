package com.ikvant.remotetask.controllers;

import com.ikvant.remotetask.entities.AppUser;
import com.ikvant.remotetask.entities.Meeting;
import com.ikvant.remotetask.entities.Member;
import com.ikvant.remotetask.reposirtories.MeetingRepo;
import com.ikvant.remotetask.reposirtories.MemberRepo;
import com.ikvant.remotetask.reposirtories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MeetingController {

    @Autowired
    private MeetingRepo meetingRepo;

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/meetings/{id}/join")
    public ResponseEntity<Meeting> join(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        AppUser user = userRepo.findByName(name);
        Meeting meeting = meetingRepo.findOne(id);

        if (user != null && meeting != null) {
            Member member = new Member(meeting, user);
            memberRepo.save(member);
            return ResponseEntity.ok(meetingRepo.findOne(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/meetings")
    public ResponseEntity<List<Meeting>> getAll() {
        return ResponseEntity.ok(meetingRepo.findAll());
    }

    @GetMapping("/meetings/{id}")
    public ResponseEntity<Meeting> getById(@PathVariable("id") Long id) {
        Meeting meeting = meetingRepo.findOne(id);
        if (meeting != null) {
            return ResponseEntity.ok(meeting);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/meetings/{id}")
    public ResponseEntity<Meeting> deleteById(@PathVariable("id") Long id) {
        Meeting meeting = meetingRepo.findOne(id);
        if (meeting != null) {
            memberRepo.delete(id);
            return ResponseEntity.ok(meeting);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/meetings/{id}")
    public ResponseEntity<Meeting> getById(@PathVariable("id") Long id, @RequestParam("title") String tile, @RequestParam("description") String description) {
        Meeting meeting = meetingRepo.findOne(id);
        if (meeting != null) {
            meeting.setDescription(description);
            meeting.setTitle(tile);
            return ResponseEntity.ok(meetingRepo.save(meeting));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/meetings")
    public ResponseEntity<Meeting> getById(@RequestParam("title") String tile, @RequestParam("description") String description) {
        Meeting meeting = new Meeting();
        meeting.setDescription(description);
        meeting.setTitle(tile);
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingRepo.save(meeting));
    }
}
