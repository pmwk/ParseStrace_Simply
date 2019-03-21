package src;

import java.io.*;

public class Configuration implements Serializable{

    private String lastFile;
    private static Configuration configuration;

    public static Configuration getConfiguration() {
        if (configuration == null) {
            initConfiguration();
        }
        return configuration;
    }

    private Configuration() {

    }

    public static void initConfiguration() {
        if (configuration == null) {
            configuration = download();
        }
        if (configuration == null) {
            configuration = new Configuration();
            configuration.lastFile = "!!! ?? !!!";
        }
    }

    public static String getLastFile() {
        return configuration.lastFile;
    }

    public static void setLastFile(String lastFile) {
        configuration.lastFile = lastFile;
        save(configuration);
    }

    public static void save(Configuration configuration) {
        try {
            File file = new File("src/Resource/src.Configuration");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(configuration);
            oos.flush();
            oos.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private static Configuration download() {
        try {
            File file = new File("src/Resource/src.Configuration");
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Configuration configuration = (Configuration) ois.readObject();
                return configuration;
            } else {
                return null;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
