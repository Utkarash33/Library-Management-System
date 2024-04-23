package com.library.Library.entities;


import org.springframework.data.redis.core.RedisHash;

@RedisHash("count_return_reserves")
public class CountTheIntervals {

    private Integer return_count;
    private  Integer reserve_count;
}
