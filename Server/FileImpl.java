import java.io.File;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.ExportException;

public class FileImpl implements FileInterface {

    protected FileImpl() throws RemoteException {
        super();
    }

    @Override
    public byte[] downloadFile(String fileName) throws RemoteException {
        try {
            File file = new File(fileName);
            byte[] fileData = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(fileData);
            fileInputStream.close();
            return fileData;
        } catch (Exception e) {
            System.out.println("FileImpl Exception: " + e.getMessage());
            e.printStackTrace();
            return null;
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
