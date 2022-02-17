/*
 * Server App upon TCP
 * A thread is started to handle every client TCP connection to this server
 * Weiying Zhu
 * Worked on by: Hung Pham, Rigoberto Hinojos
 * 2/11/2021
 * 
 */ 

import java.net.*;
import java.util.*;
import java.io.*;

public class TCPMultiServerThread extends Thread {

    //creates Socket
    private Socket clientTCPSocket = null;
    //Creates date
    Date date = new Date();

    public TCPMultiServerThread(Socket socket) {
        super("TCPMultiServerThread");
        clientTCPSocket = socket;
        
    }

    public void run() 
    {
        try {
            BufferedReader cSocketIn = new BufferedReader(new InputStreamReader(clientTCPSocket.getInputStream()));
            PrintWriter cSocketOut = new PrintWriter(clientTCPSocket.getOutputStream(), true);

            String fromClient;

            while ((fromClient = cSocketIn.readLine()) != null) {
            String get = cSocketIn.readLine();
            String host = cSocketIn.readLine();
            String userAgent = cSocketIn.readLine();
            //prints for server side
            System.out.println("From Client: ");

            System.out.println(get);//print first get
            System.out.println(host);
            System.out.println(userAgent);
            StringTokenizer sTokenizer = new StringTokenizer(get);
            String method = sTokenizer.nextToken();


                if (!method.equals("GET")) {
                    cSocketOut.print("HTTP/1.1 400 Bad Request\r\n");
                    cSocketOut.print("Date: " + date + "\r\n");
                    cSocketOut.print("Server: cs3700a.msudenver.edu\r\n"); 
                    cSocketOut.print("\r\n"); // End of headers 
                } 
                
                String file = sTokenizer.nextToken();
                PrintWriter temp = cSocketOut;

                // Send file
                sendFile(cSocketOut, file);
            }
            cSocketOut.close();
            cSocketIn.close();
            clientTCPSocket.close();
            

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
     * Sends output of connection status. Sends Header
     *  If file not found sends appropriate errors. */
    void sendFile(PrintWriter cSocketOut, String file) 
    {
        File reqFile;
        try {
            reqFile = new File(file.substring(1,file.length()));
            FileInputStream fis = new FileInputStream(reqFile);
            byte[] bufData = new byte[(int) reqFile.length()];
            fis.read(bufData);
            fis.close();
            // Send response
            cSocketOut.print("HTTP/1.1 200 OK \r\n");
            cSocketOut.print("Date: " + date + "\r\n");
            cSocketOut.print("Server: cs3700a.msudenver.edu\r\n");
            cSocketOut.print("\r\n"); // End of headers
            // Send fileData
            cSocketOut.write(new String(bufData));
            //4 Empty lines for 200 ok case
            cSocketOut.print("\r\n\r\n\r\n\r\n");
            cSocketOut.close();
            // cSocketOut.close();
            // clientTCPSocket.close();
        } 
        // If File not found
        catch (IOException e) 
        { 
            cSocketOut.print("HTTP/1.1 Error 404 Not Found\r\n");
            cSocketOut.print("Date: " + date + "\r\n");
            cSocketOut.print("Server: cs3700a.msudenver.edu\r\n");
            cSocketOut.print("\r\n"); // End of headers
        }
    }
}
