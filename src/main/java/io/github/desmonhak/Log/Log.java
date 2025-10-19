package io.github.desmonhak.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {

    private String name;
    private TypeLog type_log;
    private boolean dump_file;
    private boolean dump_cli;

    /**
     * Funcion de como imprimir lo datos en la consola
     */
    private DumpFuncStringData output_cli;

    /**
     * Funcion de como imprimir en el archivo
     */
    private DumpFuncStringData output_file;
    private File file;

    /**
     * formateador de fecha con formato "yyyy-MM-dd HH:mm:ss.SSS"
     * Se puede usar de la siguiente manera:
     * String fecha = LocalDateTime.now().format(formatter);
     */
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * metodo default para mostrar el log en la CLI
     */
    public static DumpFuncStringData default_output_cli = (log, data) -> {
        // 22 por que las secuencias ANSI cuentan como caracteres
        System.out.printf("[%-22s:%s] %s", log.type_log, LocalDateTime.now().format(formatter), data);
    };

    /**
     * metodo default para mostrar el log en el File
     */
    public static DumpFuncStringData default_output_file = (log, data) -> {
        FileWriter fw = new FileWriter(log.file, true); // true para el modo append del archivo
        fw.write(String.format("[%-8s:%s] %s", log.type_log.type_error, LocalDateTime.now().format(formatter), data));
        fw.close();
    };

     public void clear_file() throws IOException {
        if (this.dump_file) {
            if (file == null) {
                file = new File(name);
            }
            // en modo no append se sobrescribe dejandolo vacio
            FileWriter fw = new FileWriter(this.file);
            fw.close();
        }
    }

    private void init_default_vals() {
        this.output_file    = default_output_file;
        this.output_cli     = default_output_cli;
    }

    Log(String name) {
        this.name           = name;
        this.dump_file      = true;
        this.dump_cli       = true;
        this.type_log       = TypeLog.INFO;
        init_default_vals();
    }

    public Log(String name, TypeLog type_log) {
        this.name           = name;
        this.dump_file      = true;
        this.dump_cli       = true;
        this.type_log       = type_log;
        init_default_vals();
    }

    public Log(TypeLog type_log) {
        this.name           = null;
        this.dump_file      = false;
        this.dump_cli       = true;
        this.type_log       = type_log;
        init_default_vals();
    }

    public String getName() {
        return name;
    }

    public boolean isDump_cli() {
        return dump_cli;
    }

    public void setDump_cli(boolean dump_cli) {
        this.dump_cli = dump_cli;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setName(String name) {
        if (this.name == null) {
            this.dump_file = true;
        }
        if (name == null) {
            this.dump_file = false;
        }
        this.name = name;
    }

    public TypeLog getType_log() {
        return type_log;
    }

    public void setType_log(TypeLog type_log) {
        this.type_log = type_log;
    }

    /**
     * Permite dumpear la informacion en la consola, en el archivo o en ambos
     * @param msg datos a dumpear
     * @throws IOException error que se puede generar al escribir en el archivo
     */
    public void print_log(String msg) throws IOException {
        if (this.dump_file) {
            if (file == null) {
                file = new File(name);
            }
            this.output_file.printData(this, msg);
        }
        if (this.dump_cli) {
            this.output_cli.printData(this, msg);
        }
    }

    public DumpFuncStringData getOutput_cli() {
        return output_cli;
    }

    public void setOutput_cli(DumpFuncStringData output_cli) {
        this.output_cli = output_cli;
    }
}
