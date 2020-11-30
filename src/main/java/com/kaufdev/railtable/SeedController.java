package com.kaufdev.railtable;

import org.springframework.web.bind.annotation.*;

@RestController
public class SeedController {
    private static final String template = "Hello, %s!";

    @GetMapping("/")
    public Greeting getHelloWorld(){
        return new Greeting(String.format("Hello World"));
    }

    @GetMapping("/greeting")
    public Greeting getGreeting(@RequestParam(value = "name", defaultValue = "Wolrd") String name){
        return new Greeting(String.format(template,name));
    }



}
