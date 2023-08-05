package org.data.analysis;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main {

    public static void main(String... args) {
        System.out.println("Iniciando a aplicação...");
        Quarkus.run(args);
    }
}

