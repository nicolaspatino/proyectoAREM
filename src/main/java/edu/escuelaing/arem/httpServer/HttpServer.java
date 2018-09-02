package edu.escuelaing.arem.httpServer;

import java.net.*;
import java.io.*;

/**
 * @author Carlos Andrés Castaneda Lozano
 * 
 */

public class HttpServer {
    
    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Integer PORT;
        try{
            PORT = new Integer(System.getenv("PORT"));
        }catch(Exception e){
            PORT = 35000;
        }
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        while (true) {
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            String resultado;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                try {
                    inputLine = inputLine.split(" ")[1];
                    if (inputLine.endsWith(".html")) {
                        File pagina = new File("./" + inputLine);
                        resultado = " ";
                        try {
                            FileReader fReader = new FileReader(pagina);
                            BufferedReader bReader = new BufferedReader(fReader);
                            String line;
                            while ((line = bReader.readLine()) != null) {
                                resultado += line;
                            }
                            bReader.close();
                        }catch (IOException ex) {
                            System.err.println("Error en la lectura del Buffer");
                            ex.printStackTrace();
                        }
                        if (!in.ready()) {
                            break;
                        }
                        
                        outputLine = "HTTP/1.1 200 OK\r\n"
                                + "Content-Type: text/html\r\n"
                                + "\r\n"
                                + resultado;
                        out.println(outputLine);
                    }

                }catch(java.lang.ArrayIndexOutOfBoundsException e){
                   
                }
                
            }
        }

    }
}
