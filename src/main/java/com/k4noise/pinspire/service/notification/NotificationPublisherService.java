package com.k4noise.pinspire.service.notification;

import com.k4noise.pinspire.adapter.web.dto.request.NotificationRequestDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class NotificationPublisherService {

    RabbitTemplate rabbitTemplate;

    public void sendLikeNotification(NotificationRequestDto likeNotification) {
        rabbitTemplate.convertAndSend("notifications", "like", likeNotification);
    }

    public void sendCommentNotification(NotificationRequestDto commentNotification) {
        rabbitTemplate.convertAndSend("notifications", "comment", commentNotification);
    }
}