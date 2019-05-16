package com.netcracker.vacations.service;

import com.netcracker.vacations.dto.RequestDTO;

public class DecisionRunnable implements Runnable {
    private final RequestDTO request;

    private RequestService service;

    DecisionRunnable(RequestDTO request, RequestService service) {
        this.request = request;
        this.service = service;
    }

    @Override
    public void run() {
        service.sendRequestDecision(request);
    }
}