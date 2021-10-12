package com.example.yonseitalk.controller;

import com.example.yonseitalk.exception.NotFoundException;
import com.example.yonseitalk.view.ExampleView;
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
