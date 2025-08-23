// File: assignmentplanner/IModule.java
// Description: The common interface required by the main application for integration.

package modules.assignmentplanner;

/**
 * IModule.java
 * This interface must be 'public' so that the main application, which might be in
 * a different package, can see and use it. It acts as a contract, guaranteeing
 * that any module class will have a runnable entry point.
 */
public interface IModule {
    void run();
}
