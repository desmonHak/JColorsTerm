package io.github.desmonhak.Annotations;


public class ClassTestsAnotation {

    @HookDebug(run = true)
    void metodo1() {
        System.out.println("Hola desde el metodo 1");
    }

    @HookDebug(msg = "Esto es el metodo 2", run = true)
    String metodo2() {
        System.out.println("Hola desde el metodo 2");
        return "Hola desde el metodo 2";
    }

    @HookDebug
    String metodo3(int number) {
        System.out.printf("Hola desde el metodo 3, number : %d\n", number);
        return "Hola desde el metodo 3";
    }

}
