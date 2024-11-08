package services;

import jakarta.servlet.http.HttpServletRequest;

public interface DeviceService {
    String detectDevice(HttpServletRequest request);
}