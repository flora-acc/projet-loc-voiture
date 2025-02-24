package com.accenture.model;

import java.time.LocalDateTime;

public record ErreurMessage(LocalDateTime temporalite, String type, String message) {
}
