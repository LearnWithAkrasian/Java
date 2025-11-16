package com.imran.domain;

import java.time.LocalDateTime;

// 3 essential columns for an Entity or Domain
public abstract class Domain {
    private Long id;
    private Long version = 0L;
    private LocalDateTime dateCreated
            = LocalDateTime.now();
    private LocalDateTime dateLastUpdated
            = LocalDateTime.now();

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(LocalDateTime dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }
}
