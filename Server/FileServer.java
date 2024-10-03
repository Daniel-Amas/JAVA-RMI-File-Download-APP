import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class FileServer {
    public static void main(String[] args) {
        try {
            // Start the RMI registry on port 1099
            LocateRegistry.createRegistry(1099);

            // Create the server object
            FileImpl fileServer = new FileImpl();

            // Bind the server object to the RMI registry
            Naming.rebind("rmi://0.0.0.0/FileServer", fileServer);

            System.out.println("FileServer is ready and waiting for client connections...");
        } catch (Exception e) {
            System.out.println("FileServer Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
