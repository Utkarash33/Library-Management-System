package com.library.Library.exceptionHandler;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorResposne {

    private String message;
    private String description;
    private LocalDateTime date=LocalDateTime.now();
    public ErrorResposne(String message, String description,LocalDateTime dateTime) {
        super();
        this.message = message;
        this.description = description;
        this.date = dateTime;
    }


}