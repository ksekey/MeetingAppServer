package com.ikvant.remotetask.reposirtories;

import com.ikvant.remotetask.entities.AppUser;
import com.ikvant.remotetask.entities.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MeetingRepo extends JpaRepository<Meeting, Long>{
    List<Meeting> findMeetingsByDescriptionOrTitleContaining(String desc, String title);
    List<Meeting> findMeetingsByStartBetween(Date start, Date end);
}
