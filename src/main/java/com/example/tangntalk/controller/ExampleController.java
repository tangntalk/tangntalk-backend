package com.example.tangntalk.controller;

import com.example.tangntalk.exception.NotFoundException;
import com.example.tangntalk.view.ExampleView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("example")
public class ExampleController {
    @GetMapping("")
    public ExampleView example() {
        return new ExampleView("Sample Message");
    }

    @GetMapping("not-found")
    public void notFound() {
        throw new NotFoundException();
    }
}
