package org.digit.exchange;

import org.digit.exchange.service.AdminInitializationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminSetupRunner implements CommandLineRunner {

    private final AdminInitializationService adminInitializationService;

    public AdminSetupRunner(AdminInitializationService adminInitializationService) {
        this.adminInitializationService = adminInitializationService;
    }

    @Override
    public void run(String... args) {
        adminInitializationService.initializeAdmin();
    }
}
