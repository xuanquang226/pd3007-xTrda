package data.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class BaseDTO implements Serializable {
    private static long serialVersionUID = 1L;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDated;

    private String createdBy;
    private String updatedBy;
}