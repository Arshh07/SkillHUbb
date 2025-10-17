package test.skillspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.skillspace.model.Message;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByCreatedAtAsc(Long senderId1, Long receiverId1, Long senderId2, Long receiverId2);
}