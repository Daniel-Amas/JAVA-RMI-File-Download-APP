import java.io.FileOutputStream;
import java.rmi.Naming;

public class FileClient {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java FileClient <fileName> <machineName>");
            return;
        }

        String fileName = args[0];  // The file name to download
        String machineName = args[1];  // The machine name or IP where the server is running

        try {
            // Lookup the server object in the RMI registry on the given machine
            String serverURL = "rmi://" + machineName + "/FileServer";
            FileInterface fileServer = (FileInterface) Naming.lookup(serverURL);

            // Request the file from the server
            byte[] fileData = fileServer.downloadFile(fileName);

            if (fileData != null) {
                // Save the file locally
                FileOutputStream fileOutputStream = new FileOutputStream("downloaded_" + fileName);
                fileOutputStream.write(fileData);
                fileOutputStream.close();

                System.out.println("File " + fileName + " downloaded successfully.");
            } else {
                System.out.println("File " + fileName + " not found on the server.");
            }
        } catch (Exception e) {
            System.out.println("FileClient Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
