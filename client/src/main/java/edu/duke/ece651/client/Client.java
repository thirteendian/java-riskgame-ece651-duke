/**
 * Author: Yuxuan Yang AND Xuhui Gong
 */
package edu.duke.ece651.client;


import edu.duke.ece651.shared.ClientJSON;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeUnit; // TODO DELETE


public class Client {

  private Player player;
  private final String host;
  private final int portNum;
  private final InputStream in;
  private final OutputStream out;
  private final OutputStreamWriter writer;
  private final BufferedReader reader;
  private final Socket socket;


  /**
   * Constructor of Client Class by given portNum and host
   * @param portNum
   * @param host
   * @throws IOException
   */
  public Client(int portNum, String host) throws IOException {
    this.portNum = portNum;
    this.host = host;
    this.socket = new Socket(this.host, this.portNum);
    this.in = socket.getInputStream();
    this.out = socket.getOutputStream();
    this.writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
    this.reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
  }

  /**
   * Send a JSON to the server, and it adds a \n automatically
   * @param msg to be send
   * @throws IOException
   */
  public void sendMsg(String msg) throws IOException {
      this.writer.write(msg + "\n");
      this.writer.flush();
  }

  /**
   * Receive messgae from the server
   * @return String
   * @throws IOException
   */
  public String recvMsg() throws IOException {
    return this.reader.readLine();
  }


  /**
   * Client init player. If cilent is first to connect the server then we define this client is host.
   * Host should input the total number of player and then server will be based on that value to
   * let the client join in.
   * @throws IOException
   */
  public void initPlayer() throws IOException {
    String msg = this.recvMsg();
    if(msg.equals("1")){ // If msg indicate that player is the host
      System.out.println("You are Host!, please send number of players: ");

      //TODO user input checker class
      Scanner reader = new Scanner(System.in);
      System.out.println("Enter a number: ");
      int total_player = reader.nextInt();
      System.out.println("You entered " + String.valueOf(total_player));
      this.sendMsg(String.valueOf(total_player));

      // Create a new player object for client
      this.player = new Player(Integer.parseInt(msg), total_player, new BufferedReader(new InputStreamReader(System.in)), System.out);
    }else{ // If msg indicate that player is non-host
      String[] parsedMsg = msg.split("\\s+");

      // Create a new player object for client
      this.player = new Player(Integer.parseInt(parsedMsg[0]), Integer.parseInt(parsedMsg[1]), new BufferedReader(new InputStreamReader(System.in)), System.out);
    }

  }

  public static void main(String[] args) throws IOException, InterruptedException {
    // Init client TCP SOCKET
    Client client = null;
    try {
      client = new Client(1651, "127.0.0.1");
    }
    catch (IOException ioException){
      System.out.println("Cannot connect to the server");
      int EXIT_FAILURE = 1;
      System.exit(EXIT_FAILURE);
    }

    // First handshake, Client recv msg from server and create player object
    client.initPlayer();

    while(true){
      // Client recv msg
      String received_message = client.recvMsg();

      // Client parse the JSON to the map
      System.out.println(received_message);

      // Client play one round: let the user do some actions
      client.player.playOneRound();
      ClientJSON converter = new ClientJSON(client.player.getId(),client.player.ActionList);

      // Client send the actionJSON(ie. ClientJSON) to the server
      client.sendMsg(String.valueOf(converter.convertTo()));

      // TODO DELETE sleep
      TimeUnit.SECONDS.sleep(10);
    }
  }
}
