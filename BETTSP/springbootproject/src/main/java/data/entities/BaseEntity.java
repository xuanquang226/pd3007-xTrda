package data.entities;

import java.time.LocalDateTime;

public abstract class BaseEntity {
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private String createdBy;
    private String updatedBy;
}
