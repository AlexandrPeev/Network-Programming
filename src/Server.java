import ca.pfv.spmf.input.transaction_database_list_integers.TransactionDatabase;
import ca.pfv.spmf.algorithms.frequentpatterns.defme.AlgoDefMe;
import ca.pfv.spmf.patterns.itemset_array_integers_with_tids_bitset.Itemset;
import ca.pfv.spmf.patterns.itemset_array_integers_with_tids_bitset.Itemsets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Server {
    private static final int PORT = 8080;
    private static final String EXIT_WORD = "exit";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started on localhost with port " + PORT);

        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("Client accepted");

            DefMe(client);
        }
    }

    private static void DefMe(Socket client) throws IOException {
        double minsup = 0.4;
        
        AlgoDefMe defMe = new AlgoDefMe();
        TransactionDatabase database = new TransactionDatabase();

        

        BufferedReader inputStream = new BufferedReader(
                new InputStreamReader(client.getInputStream()));

        // Read the first line, effectively skipping it
        inputStream.readLine();

        Map<Integer, String> hashToString = new HashMap<>();

        System.out.println("Receiving data from client");
        String line;
        while (!(line = inputStream.readLine()).equals(EXIT_WORD)) {
            String[] split = line.split(";");
           
            for (String string : split) {
                hashToString.put(string.hashCode(), string);
            }

            int[] hashCodes = Arrays.stream(split).mapToInt(String::hashCode).toArray();
            List<Integer> lint = new ArrayList<Integer>();
            for(Integer num : hashCodes) {
            	lint.add(num);
            }
            database.addTransaction(lint);
        }
        System.out.println("Received all of the data");
        System.out.println("Calculating result");


        PrintWriter writer = new PrintWriter(
                client.getOutputStream(), true);

         Itemsets result1 = defMe.runAlgorithm(null, database, minsup);
         List<List<Itemset>> result2 = result1.getLevels();
         for(List<Itemset> Itemset_list : result2)
         {
        	 for(Itemset itemset : Itemset_list)
        	 {
        		 int[] int_array = itemset.getItems();
        		 for (int item : int_array) 
        		 {
                     writer.print(hashToString.get(item) + "; ");
                 }
        		 writer.println("#SUP: " + itemset.getAbsoluteSupport());
        	 }
         }
         
        writer.println(EXIT_WORD);

        System.out.println("Result sent to client");
    }
}
