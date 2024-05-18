package com.k4noise.pinspire.service.notification;

import com.k4noise.pinspire.adapter.web.dto.request.NotificationRequestDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class NotificationListenerService {
    @RabbitListener(queues = "likes")
    public void handleLikeNotification(NotificationRequestDto likeNotification) {
        log.info("Уведомление для пользователя {}: пользователь {} поставил лайк на пин с названием {} и id {}",
                likeNotification.ownerUsername(),
                likeNotification.actorUsername(),
                likeNotification.pinTitle(),
                likeNotification.pinId()
        );
    }

    @RabbitListener(queues = "comments")
    public void handleCommentNotification(NotificationRequestDto commentNotification) {
        log.info("Уведомление для пользователя {}: пользователь {} оставил комментарий к пину с названием {} и id {}",
                commentNotification.ownerUsername(),
                commentNotification.actorUsername(),
                commentNotification.pinTitle(),
                commentNotification.pinId()
        );
    }
}