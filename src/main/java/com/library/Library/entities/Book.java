package com.library.Library.entities;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Book {


    private Long id;

    private String title;

    private String author;

    private boolean reserved;

}
