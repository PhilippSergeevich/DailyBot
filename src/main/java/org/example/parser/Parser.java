package org.example.parser;

import java.nio.file.Path;

public interface Parser {

    void runParse(int period, Path path);
    void runParse();
}
