package test.skillspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import test.skillspace.model.Message;
import test.skillspace.model.User;
import test.skillspace.repository.MessageRepository;
import test.skillspace.repository.UserRepository;
import test.skillspace.security.CustomUserDetails;

import java.util.List;

@Controller
public class MessageController {

    @Autowired private MessageRepository messageRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping("/messages/{receiverId}")
    public String chatWithUser(@PathVariable Long receiverId, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User sender = userDetails.getUser();
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("User not found"));

        List<Message> messages = messageRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByCreatedAtAsc(
                sender.getId(), receiver.getId(), sender.getId(), receiver.getId()
        );

        model.addAttribute("receiver", receiver);
        model.addAttribute("messages", messages);
        model.addAttribute("newMessage", new Message());
        return "chat";
    }

    @PostMapping("/messages/send")
    public String sendMessage(Message newMessage, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User sender = userDetails.getUser();
        newMessage.setSender(sender);
        messageRepository.save(newMessage);
        return "redirect:/messages/" + newMessage.getReceiver().getId();
    }
}