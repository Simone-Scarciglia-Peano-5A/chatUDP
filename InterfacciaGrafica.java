/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatudpclient;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;

/**
 *
 * @author Scarciglia Simone
 */
public class InterfacciaGrafica extends JFrame {

    JLabel jLabelTitolo = new JLabel();
    JLabel jLabelUsername = new JLabel();
    JTextField jTextFieldUsername = new JTextField();
    JLabel jLabelMessaggio = new JLabel();
    JTextField jTextFieldMessaggio = new JTextField();
    JButton jButtonInvia = new JButton();
    JButton jButtonCancellaCampi = new JButton();
    JScrollPane jScrollPaneMessaggi = new JScrollPane();
    JTextArea jTextAreaMessaggi = new JTextArea();
        
    private static String IP_address = "127.0.0.1";
    private static int UDP_port = 1077;
    private static InetAddress address;
    private static DatagramSocket datagramSocket;
    
    public InterfacciaGrafica() {
        
        //JLabelTitolo
        jLabelTitolo.setFont(new java.awt.Font("Times New Roman", 0, 24));
        jLabelTitolo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitolo.setText("CHAT UDP");
        
        //JLabelUsername
        jLabelUsername.setText("Username: ");
        
        //JLabelMessaggio
        jLabelMessaggio.setText("Messaggio: ");
        
        //JButton
        jButtonInvia.setText("Invia");
        jButtonCancellaCampi.setText("Cancella Campi");
        
        //JTextAreaMessaggi
        jTextAreaMessaggi.setColumns(20);
        jTextAreaMessaggi.setRows(5);
        
        //JScrollPane
        jScrollPaneMessaggi.setViewportView(jTextAreaMessaggi);
        
        
        //BOTTONE INVIA
        jButtonInvia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String nome = jTextFieldUsername.getText();
                String messaggio= jTextFieldMessaggio.getText();
                    
                try {
                    byte[] buffer;
                    DatagramPacket datagramPacket;
                    String output = nome + ": " + "\n" +
                                    " " + messaggio + "\n";
                    buffer = output.getBytes("UTF-8");
                    datagramPacket = new DatagramPacket(buffer, buffer.length, address, UDP_port);
                    datagramSocket.send(datagramPacket);
                    jTextFieldMessaggio.setText("");
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(InterfacciaGrafica.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(InterfacciaGrafica.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
            
        });
        
        // Thread per la ricezione
        Thread thread = new Thread(){
            public void run(){
                byte[] buffer;
                    String string = null;
                    DatagramPacket datagramPacket;
                    
                    buffer = new byte[500];
                    datagramPacket = new DatagramPacket(buffer, buffer.length);
                     
                    try {
                        while(!Thread.interrupted()){
                        datagramSocket.receive(datagramPacket);
                        string = new String(datagramPacket.getData(), 0, datagramPacket.getLength(), "ISO-8859-1");
                        jTextAreaMessaggi.append('\n' + string);
                        }
                        datagramSocket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(InterfacciaGrafica.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        };
        thread.start();
        
        
        //BOTTONE ELIMINA
        jButtonCancellaCampi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTextFieldUsername.setText("");
                jTextFieldMessaggio.setText("");
            }
        });
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPaneMessaggi)
                            .addComponent(jLabelTitolo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonInvia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonCancellaCampi))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabelMessaggio)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldMessaggio, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabelUsername)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(48, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitolo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUsername)
                    .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMessaggio)
                    .addComponent(jTextFieldMessaggio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonInvia)
                    .addComponent(jButtonCancellaCampi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jScrollPaneMessaggi, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pack();
    }                                                                                                                

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws UnknownHostException, SocketException{
        
        address = InetAddress.getByName(IP_address);
        datagramSocket = new DatagramSocket();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfacciaGrafica().setVisible(true);
            }
        });
    }  
}