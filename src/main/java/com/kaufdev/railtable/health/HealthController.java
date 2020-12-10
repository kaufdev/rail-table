package com.kaufdev.railtable.health;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
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
