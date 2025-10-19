package io.github.desmonhak.Log;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LogTest {

    @Test
    void print_log() throws IOException {

        Log log = new Log("log.txt");
        log.clear_file();

        log.print_log("Mensaje de ejemplo1\n");

        log.setType_log(TypeLog.WARNING);
        log.print_log("Mensaje de ejemplo2\n");

        log.setType_log(TypeLog.ERROR);
        log.print_log("Mensaje de ejemplo3\n");
    }
}