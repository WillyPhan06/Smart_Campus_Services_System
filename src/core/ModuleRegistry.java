package core;

import java.util.ArrayList;
import java.util.List;

public class ModuleRegistry {
    private List<AbstractModule> modules = new ArrayList<>();

    // Register a module
    public void register(AbstractModule module) {
        modules.add(module);
    }

    // Get all modules
    public List<AbstractModule> getModules() {
        return modules;
    }
}
