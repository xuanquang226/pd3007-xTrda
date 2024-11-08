package utils.objects;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoRefreshToken implements Serializable {
    private String refreshToken;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private String status;
    private String ipAddress;
}
