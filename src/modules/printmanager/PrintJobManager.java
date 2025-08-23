package modules.printmanager;

import core.AbstractModule;
import java.util.Scanner;

// Core logic for managing print jobs
public class PrintJobManager extends AbstractModule {
    private PrintQueue queue;
    private Scanner scanner;

    public PrintJobManager() {
        this.queue = new PrintQueue(10); // default capacity = 10 jobs
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String getModuleName() {
        return "Print Job Manager";
    }

    @Override
    public void start(Scanner scanner) {
        PrintJobUI ui = new PrintJobUI(queue, scanner);
        ui.run();
    }

}
