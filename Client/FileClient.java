import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.Naming;

public class FileClient {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java FileClient <operation> <fileName> <machineName>");
            System.out.println("<operation>: 'd' or 'u'");
            return;
        }

        String operation = args[0]; // Either 'd' for download or 'u' for upload
        String fileName = args[1];  // The file name to download
        String machineName = args[2];  // The machine name or IP where the server is running

        try {
            // Lookup the server object in the RMI registry on the given machine
            String serverURL = "rmi://" + machineName + "/FileServer";
            FileInterface fileServer = (FileInterface) Naming.lookup(serverURL);

            if (operation.equals("d")) {
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
            } else if (operation.equals("u")) {
                // Upload the file to the server
                File file = new File(fileName);
                if(!file.exists()){
                    System.out.println("File " + fileName + " not found on the client.");
                    return;
                }

                //Read the file content into a byte array
                byte[] fileData = new byte[(int) file.length()];
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(fileData);
                fileInputStream.close();

                // Call the server method to upload the file
                fileServer.uploadFile(fileName, fileData);
                System.out.println("File " + fileName + " uploaded successfully.");
            } else {
                System.out.println("Invalid operation. Use 'd' or 'u'.");
            }

        } catch (Exception e) {
            System.out.println("FileClient Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
