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

        // Fetch messages between the sender and receiver, ordered by time
        List<Message> messages = messageRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByCreatedAtAsc(
                sender.getId(), receiver.getId(), receiver.getId(), sender.getId() // Corrected order for bidirectional query
        );

        model.addAttribute("receiver", receiver);
        model.addAttribute("messages", messages);
        model.addAttribute("newMessage", new Message()); // Add empty message object for the form
        return "chat";
    }

    @PostMapping("/messages/send")
    public String sendMessage(Message newMessage, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User sender = userDetails.getUser();
        
        // We need to fetch the receiver User object based on the ID submitted by the form
        User receiver = userRepository.findById(newMessage.getReceiver().getId())
                            .orElseThrow(() -> new RuntimeException("Receiver not found"));

        newMessage.setSender(sender);
        newMessage.setReceiver(receiver); // Set the full receiver object
        
        messageRepository.save(newMessage);
        
        // Redirect back to the chat page with the receiver
        return "redirect:/messages/" + receiver.getId();
    }
}