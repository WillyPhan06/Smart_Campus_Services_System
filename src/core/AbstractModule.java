package core;

public abstract class AbstractModule {
    // Each module must provide its name
    public abstract String getModuleName();

    // Entry point for the module (called from main menu)
    public abstract void start(java.util.Scanner scanner);
}
