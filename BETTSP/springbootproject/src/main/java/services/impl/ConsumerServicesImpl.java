package services.impl;

import org.springframework.stereotype.Service;

import services.ConsumerService;

@Service
public class ConsumerServicesImpl implements ConsumerService {

    // @KafkaListener(topics = { "orders" }, groupId = "my-group")
    @Override
    public void consumer(String message) {
        System.out.println("consumed " + message);
    }

}
