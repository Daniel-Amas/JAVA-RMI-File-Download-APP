import java.io.File;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FileImpl extends UnicastRemoteObject implements FileInterface {

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
}
