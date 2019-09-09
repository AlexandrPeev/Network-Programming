package fmi.project;

import ca.pfv.spmf.algorithms.frequentpatterns.estDec.AlgoDefMe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Arrays;
import java.util.Map;

public class Server {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server port :" + PORT);

        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("Accepted client");

        }
    }
        writer.println("exit);

        System.out.println("Sent to Client");
    }
}
