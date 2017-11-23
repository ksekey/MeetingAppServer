package com.ikvant.remotetask.reposirtories;

import com.ikvant.remotetask.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, Long> {
}
