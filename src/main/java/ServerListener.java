import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Properties;

class ServerListener {
    private static final String PROP_FILE_NAME = "server.properties";
    private Properties prop;

    void connect() {
        try {
            loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            ServerSocket server = new ServerSocket(Integer.parseInt(prop.getProperty("server.port")), 0, InetAddress.getByName(prop.getProperty("server.address")));
            System.out.println("server is started");
        } catch (Exception e) {
            System.out.println("init error: " + e);
        }
    }

    private void loadProperties() throws IOException {
        prop = new Properties();
        InputStream inputStream = ServerLoader.class.getClassLoader().getResourceAsStream(PROP_FILE_NAME);
        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + PROP_FILE_NAME + "' not found in the classpath");
        }
    }

}
