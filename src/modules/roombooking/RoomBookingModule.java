package modules.roombooking;

import core.AbstractModule;
import java.util.Scanner;

public class RoomBookingModule extends AbstractModule {
    @Override
    public String getModuleName() {
        return "Room Booking";
    }

    @Override
    public void start(Scanner scanner) {
        // Fastest integration path:
        // The roombooking package includes a `Main.java` — likely with its own entrypoint.
        try {
            Main.main(new String[0]); // hands off to the existing console flow
        } catch (Throwable t) {
            System.out.println("[Room Booking] Could not launch module: " + t.getMessage());
        }
    }
}
