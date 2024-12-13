package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import services.ProducerService;

@RestController
@RequestMapping("/api/orders")
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @PostMapping
    public void sendMessageFromProducerOrder(@RequestParam String message) {
        producerService.sendMessage(message);
    }
}
