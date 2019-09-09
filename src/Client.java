import java.io.*;
import java.net.Socket;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final String TEST_FILE_PATH =
            "src\\logs_BCS37_20181103_UTF-8.txt";
    private static final String RESULT_FILE_PATH =
            "src\\resultdefMe.txt";
    private static final String EXIT_WORD = "exit";

    private static final int PACKET_SIZE = 8192;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(HOST, PORT);
        System.out.println("Connected to server on host <" + HOST +
                "> and port <" + PORT + ">");

        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader fileInputStream = new BufferedReader(new FileReader(TEST_FILE_PATH));

        System.out.println("Sending file to the server");
        String line;
        while (!(line = fileInputStream.readLine()).equals(EXIT_WORD)) {
            writer.println(line);
        }
        writer.println(EXIT_WORD);
        System.out.println("Sent file to server");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        PrintWriter fileWriter = new PrintWriter(
                new FileWriter(RESULT_FILE_PATH), true);

        System.out.println("Receiving result from server");
        while (!(line = reader.readLine()).equals(EXIT_WORD)) {
            fileWriter.println(line);
        }
        System.out.println("All data is written to the designated file");

        socket.close();
        fileWriter.close();
    }
}
