import com.google.gson.Gson;
import data.db.repository.DictionaryRepository;
import data.tcp.model.Request;

import javax.persistence.NoResultException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket client;
    private final DictionaryRepository repository;

    ClientHandler(Socket client) {
        this.client = client;
        repository = new DictionaryRepository();
    }

    @Override
    public void run() {
        try (DataOutputStream out = new DataOutputStream(client.getOutputStream());
             DataInputStream in = new DataInputStream(client.getInputStream())) {

            System.out.println("New client attached");

            loop:
            while (!client.isClosed()) {
                System.out.println("Server reading from channel");
                String entry = in.readUTF();
                Request request = new Gson().fromJson(entry, Request.class);
                System.out.println("READ from clientDialog message - " + entry);

                String result = "Empty result";
                switch (request.getCommand().toLowerCase()) {
                    case "quit":
                        break loop;
                    case "select":
                        try {
                            result = new Gson().toJson(repository.select(request.getDictionary()));
                        } catch (IllegalArgumentException | NoResultException e) {
                            e.printStackTrace();
                            result = e.getMessage();
                        }
                        break;
                    case "find":
                        try {
                            result = new Gson().toJson(repository.find(request.getDictionary()));
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            result = e.getMessage();
                        }
                        break;
                    case "insert":
                        try {
                            result = new Gson().toJson(repository.insert(request.getDictionary()));
                        } catch (Exception e) {
                            e.printStackTrace();
                            result = e.getMessage();
                        }
                        break;
                    case "update":
                        try {
                            result = new Gson().toJson(repository.update(request.getDictionary()));
                        } catch (Exception e) {
                            e.printStackTrace();
                            result = e.getMessage();
                        }
                        break;
                    case "delete":
                        System.out.println("Server try writing to channel");
                        out.writeUTF("Server reply - " + new Gson().toJson(repository.delete(request.getDictionary())) + " - OK");
                        out.flush();
                        break;
                    default:
                        out.writeUTF("Server reply - incorrect command - OK");
                        out.flush();
                }
                System.out.println("Server try writing to channel");
                out.writeUTF(result);
                out.flush();
            }
            client.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
