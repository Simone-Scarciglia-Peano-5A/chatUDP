/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatudpclient;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import chatudpclient.ReceiveFromServerAndPrint;

/**
 *
 * @author Scarciglia Simone
 */
public class ChatUDPclient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException {

        String IP_address = "127.0.0.1";
        InetAddress address = InetAddress.getByName(IP_address);
        int UDP_port = 1077;


        DatagramSocket socket;
        try {

            socket = new DatagramSocket();
            

            //creo il thread che riceve i messaggi dal server e scrive su schermo i messaggi ricevuti
            Thread receiveAndPrint = new Thread(new ReceiveFromServerAndPrint(socket));
            receiveAndPrint.start();
            System.out.println("sono in ascolto...");

            //creo il thread che invia il messaggio digitato da utente verso il server
            Thread sendUserInput = new Thread(new SendUserInputToServer(socket, address, UDP_port));
            sendUserInput.start();
            System.out.println("utente e' invitato di inserire un messaggio da inviare al server...");


            System.out.println("connessione server riuscita");

            //mi metto in attesa che utente voglia terminare digitando "quit" e quindi chiudo baracca e burattini
            sendUserInput.join(); //mi metto in attesa il thread finisca
            receiveAndPrint.interrupt(); //interrompo anche il receive thread
            receiveAndPrint.join();  //aspetto che anche questo thread finisca
            socket.close(); //ora posso chiudere il socket in modo pulito

        } catch (SocketException ex) {
            System.out.println("ERROR: connessione server non riuscita");
        } catch (InterruptedException ex) {
            Logger.getLogger(ChatUDPclient.class.getName()).log(Level.SEVERE, null, ex);
        }

        }
    
}