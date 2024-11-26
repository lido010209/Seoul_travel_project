package com.example.travel.message.repo;

import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
    @Query(value = "SELECT DISTINCT m FROM Message m WHERE "+
            "(m.sender = :user1 AND m.receiver = :user2) OR "+
            "(m.sender = :user2 AND m.receiver = :user1) "+
            "ORDER BY m.createAt ASC")
    List<Message> chatMessages(@Param("user1") UserEntity user1,
                               @Param("user2") UserEntity user2);
    // Count user sending message;
    @Query("SELECT COUNT(DISTINCT m.sender) FROM Message m "+
            "WHERE m.receiver = :user AND m.confirm = false ")
    Integer countUserSendMes(@Param("user") UserEntity user);

    // Count message by sender;
    @Query("SELECT COUNT(DISTINCT m) FROM Message m "+
            "WHERE m.receiver = :receiver AND m.sender = :sender AND m.confirm = false")
    Integer countMes(@Param("receiver") UserEntity receiver, @Param("sender") UserEntity sender);

    List<Message> findBySenderAndReceiver(UserEntity sender, UserEntity receiver);
}
