package core;

public class ModuleRegistry {
    private AbstractModule[] modules;
    private int size;

    public ModuleRegistry(int capacity) {
        modules = new AbstractModule[capacity];
        size = 0;
    }

    // Register a module
    public void register(AbstractModule module) {
        if (size < modules.length) {
            modules[size++] = module;
        } else {
            System.out.println("ModuleRegistry is full, cannot register more modules.");
        }
    }

    // Get only the filled modules (safe trimmed array)
    public AbstractModule[] getModules() {
        AbstractModule[] result = new AbstractModule[size];
        for (int i = 0; i < size; i++) {
            result[i] = modules[i];
        }
        return result;
    }

    // Optional helper: get module count
    public int getSize() {
        return size;
    }
}
