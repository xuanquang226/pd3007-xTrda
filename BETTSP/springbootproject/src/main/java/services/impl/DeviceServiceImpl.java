package services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import services.DeviceService;
import ua_parser.Client;
import ua_parser.Parser;

@Service
public class DeviceServiceImpl implements DeviceService {
    private Parser uParser = new Parser();

    @Override
    public String detectDevice(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        if (ua == null) {
            return "unknown";
        }
        Client client = uParser.parse(ua);
        String deviceFamily = client.device.family.toLowerCase();
        if (deviceFamily.contains("mobile")) {
            return "mobile";
        }
        if (deviceFamily.contains("tablet")) {
            return "tablet";
        }

        if (deviceFamily.contains("pc") || deviceFamily.contains("desktop")) {
            return "pc";
        }

        return "unknown";
    }
}
