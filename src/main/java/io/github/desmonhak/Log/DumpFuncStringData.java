package io.github.desmonhak.Log;

import java.io.IOException;

@FunctionalInterface
public interface DumpFuncStringData {
    void printData(Log type_log, String data) throws IOException;
}
