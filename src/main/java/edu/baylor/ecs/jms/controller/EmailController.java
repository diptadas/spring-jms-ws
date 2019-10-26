package edu.baylor.ecs.jms.controller;

import edu.baylor.ecs.jms.jms.Sender;
import edu.baylor.ecs.jms.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private Sender sender;

    @PostMapping("/send")
    public void sendEmail(@RequestBody Email email) {
        sender.send(email);
    }
}
