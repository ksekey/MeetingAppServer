package com.ikvant.remotetask.controllers;

import com.ikvant.remotetask.entities.AppUser;
import com.ikvant.remotetask.entities.Meeting;
import com.ikvant.remotetask.reposirtories.MeetingRepo;
import com.ikvant.remotetask.reposirtories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class MeetingController {

    @Autowired
    private MeetingRepo meetingRepo;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/meetings/{id}/join")
    public ResponseEntity<Meeting> join(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        AppUser user = userRepo.findByName(name);
        Meeting meeting = meetingRepo.findOne(id);

        if (user != null && meeting != null) {
            if (!meeting.getMembers().contains(user)) {
                meeting.getMembers().add(user);
            }
            return ResponseEntity.ok(meetingRepo.save(meeting));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/meetings")
    public ResponseEntity<List<Meeting>> getAll(@RequestParam(name = "text", required = false) String text) {
        if (text != null && !"".equals(text)) {
            return ResponseEntity.ok(meetingRepo.findMeetingsByDescriptionOrTitleContaining(text, text));
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        Date start = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date end = calendar.getTime();
        return ResponseEntity.ok(meetingRepo.findMeetingsByStartBetween(start, end));
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
            meetingRepo.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/meetings/{id}")
    public ResponseEntity<Meeting> getById(@PathVariable("id") Long id, @RequestBody Meeting meeting) {
        Meeting storeMeeting = meetingRepo.findOne(id);
        if (storeMeeting != null) {
            return ResponseEntity.ok(meetingRepo.save(meeting));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/meetings")
    public ResponseEntity<Meeting> getById(@RequestBody Meeting meeting) {
        meeting.setId(0);
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingRepo.save(meeting));
    }
}
