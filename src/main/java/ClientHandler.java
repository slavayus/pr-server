import com.google.gson.Gson;
import data.tcp.model.Request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket client;

    ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try (DataOutputStream out = new DataOutputStream(client.getOutputStream());
             DataInputStream in = new DataInputStream(client.getInputStream())) {

            System.out.println("New client attached");

            while (!client.isClosed()) {
                System.out.println("Server reading from channel");
                String entry = in.readUTF();
                Request request = new Gson().fromJson(entry, Request.class);
                System.out.println("READ from clientDialog message - " + entry);

                if (request.getCommand().equalsIgnoreCase("quit")) {
                    break;
                }

                System.out.println("Server try writing to channel");
                out.writeUTF("Server reply - " + new Gson().toJson(request) + " - OK");
                out.flush();
            }
            client.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
