package test.skillspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.skillspace.model.Message;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // Finds messages between two users, ordered by creation time (for the chat page)
    List<Message> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByCreatedAtAsc(Long senderId1, Long receiverId1, Long senderId2, Long receiverId2);

    // Finds ALL messages, ordered by creation time descending (for the admin page) - THIS IS THE MISSING METHOD
    List<Message> findAllByOrderByCreatedAtDesc(); 
}