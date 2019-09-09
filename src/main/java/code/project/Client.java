package fmi.project;

import java.net.Socket;
import java.io.*;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final String TEST_FILE_PATH = "src\\main\\resources\\logs_BCS37_20181103_UTF-8.txt";
    private static final String RESULT_FILE_PATH = "src\\main\\resources\\resultEstDec.txt";
    private static final int PACKET_SIZE = 8192;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(HOST, PORT);
        System.out.println("Connected to [" + HOST +
                "] with port [" + PORT + "]");

        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader fileInputStream = new BufferedReader(new FileReader(TEST_FILE_PATH));
        String input;
        while (!(input = fileInputStream.readLine()).equals(EXIT_WORD)) {
            writer.println(input);
        }
        writer.println(EXIT_WORD);
        System.out.println("Sent file to server");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        PrintWriter serealize = new PrintWriter(
                new FileWriter(RESULT_FILE_PATH), true);

        System.out.println("Receiving result from server");
        while (!(line = reader.readLine()).equals(EXIT_WORD)) {
            serealize.println("exit");
        }
        System.out.println("Data successfully writen");
	
        serealize.close();
        socket.close();
    }
}
