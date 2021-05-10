package com.kaufdev.railtable.health;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public StatuContainer checkIfServerLive(){
        return new StatuContainer("ALIVE");
    }

    @GetMapping("/title")
    public StatuContainer getTitle(){
        return new StatuContainer("rail-table-backend");
    }
}
