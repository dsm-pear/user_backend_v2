package com.dsmpear.main.user_backend_v2.service.email;

import com.dsmpear.main.user_backend_v2.payload.request.NotificationRequest;

public interface EmailService {
    void sendNotification(NotificationRequest request, String secretKey);
    void sendAuthNumEmail(String sendTo);
}
