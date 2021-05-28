package com.autsoft.simpleblog.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;

public class EntityNotFoundException extends ResponseStatusException {

    public EntityNotFoundException(@NotNull final String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
