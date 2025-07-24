package com.example.demo.Dto;

public record LoginRes(boolean valid,
         String message,
         String token,
         long expiresIn ) {
}
