package ru.achernyavskiy0n.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.achernyavskiy0n.exception.SimpleException;
import ru.achernyavskiy0n.service.SimpleService;

@RestController
@RequestMapping("aop")
public class AopController {

    @Autowired
    private SimpleService service;

    @GetMapping("/add")
    public String getCalculationAop() {
        return "Execution result is: " + service.add() + " \n";
    }

    @GetMapping("/param")
    public String parameterizedCalculationAop(@RequestParam Integer parameter) {
        return "Parameterize calculation result is: " + service.param(parameter);
    }

    @GetMapping("/dummy")
    public String dummy() {
        return service.dummyOutput();
    }

    @GetMapping("/exception")
    public String exception() {
        try {
            return service.generateException();
        } catch (SimpleException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, "Fake comment at controller layer", exception);
        }

    }
}
