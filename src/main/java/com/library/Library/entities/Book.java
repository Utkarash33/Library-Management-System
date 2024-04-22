package com.library.Library.entities;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@RedisHash("books")
public class Book {


    private Long id;

    private String title;

    private String author;

    private boolean reserved;

}
