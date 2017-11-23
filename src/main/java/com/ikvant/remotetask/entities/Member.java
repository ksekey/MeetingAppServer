package com.ikvant.remotetask.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue
    private long id;

    @JsonIgnore
    @ManyToOne
    private Meeting meeting;

    @ManyToOne
    private AppUser user;

    public long getId() {
        return id;
    }

    public Member(Meeting meeting, AppUser user) {
        this.meeting = meeting;
        this.user = user;
    }

    public Member() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
