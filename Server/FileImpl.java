import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.ExportException;

public class FileImpl implements FileInterface {

    protected FileImpl() throws RemoteException {
        super();
    }

    @Override
    public byte[] downloadFile(String fileName) throws RemoteException {
        try {
            // Get client host IP address
            String clientHost = RemoteServer.getClientHost();
            System.out.println("Request recieved from client: " + clientHost);
            System.out.println("Client requested file: " + fileName);

            //check if file exists
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println("'"+ fileName + "' does not exist");
                return null;
            }

            //Read the file content
            byte[] fileData = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(fileData);
            fileInputStream.close();
            System.out.println("'" +fileName+ "' downloaded successfully");

            return fileData;
        } catch (Exception e) {
            System.out.println("FileImpl Exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void uploadFile(String fileName, byte[] fileData) throws RemoteException {
        try {
            String clientHost = RemoteServer.getClientHost();
            System.out.println("Upload request recieved from client: " + clientHost);
            System.out.println("Client is uploading file: " + fileName);

            File file = new File(fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(fileData);
            fileOutputStream.close();

            System.out.println("'"+ fileName + "' uploaded successfully");
        } catch (Exception e){
            System.out.println("FileImpl Exception during upload: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Export the object manually
    public void export() throws RemoteException {
        try {
            UnicastRemoteObject.exportObject(this, 0);
        }catch (ExportException e) {
            System.out.println("Error exporting object: " + e.getMessage());
        }
    }
}
