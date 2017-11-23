package com.ikvant.remotetask.reposirtories;

import com.ikvant.remotetask.entities.AppUser;
import com.ikvant.remotetask.entities.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepo extends JpaRepository<Meeting, Long>{
}
