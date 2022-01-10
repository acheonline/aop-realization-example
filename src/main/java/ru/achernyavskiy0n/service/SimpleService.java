package ru.achernyavskiy0n.service;

import org.springframework.stereotype.Service;
import ru.achernyavskiy0n.exception.SimpleException;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class SimpleService {

    public int param(int p) {
        return ThreadLocalRandom.current().nextInt(1, p + 1)
                + ThreadLocalRandom.current().nextInt(1, p + 1);
    }

    public int add() {
        return param(100);
    }

    public String dummyOutput() {
        return "dummy";
    }

    public String generateException() {
        throw new SimpleException("Generated internal fake exception");
    }
}
