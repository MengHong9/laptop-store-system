package org.example.laptopstoresystem.service.schedule;

import org.example.laptopstoresystem.service.security.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenCleanUpSchedule {
    @Autowired
    private RefreshTokenService refreshTokenService;


    @Scheduled(cron = "0 0 0 * * *")
//    @Scheduled(fixedRate = 10000)
    public void deleteExpiredAndRevokeTokens(){
        refreshTokenService.deleteExpiredAndRevokeTokens();
    }

}
