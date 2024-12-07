package com.example.demo.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIError {
    private LocalDateTime timestamp;
    private String message;
    private int status;
}
