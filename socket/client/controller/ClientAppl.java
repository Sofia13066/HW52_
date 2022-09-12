import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientAppl {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		String serverAddres = "127.0.0.1";
		int port = 9000;

				try (Socket socket = new Socket(serverAddres, port)) {
					InputStream inputStream = socket.getInputStream();
					OutputStream outputStream = socket.getOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(outputStream);
					ObjectInputStream ois = new ObjectInputStream(inputStream);
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					BufferedReader nameEntered = new BufferedReader(new InputStreamReader(System.in));
		
					System.out.println("Please enter your name");
					String username = nameEntered.readLine();
			

				Thread sender = new Thread(new Runnable() {

					@Override
					public void run() {
						System.out.println("Please enter your message, or type exit for quit");
						String message;
						try {
							message = br.readLine();
							while(!"exit".equalsIgnoreCase(message)) {
								try {
									oos.writeObject(message);
									System.out.println("Please enter your message, or type exit for quit");
									message = br.readLine();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}}catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

				Thread reciever = new Thread(new Runnable() {

					@Override
					public void run() {
						String response;
						try {
							// while(sender.isAlive() == true) 
								while(true){
								response = ois.readObject().toString();
							System.out.println(username + ": " + response);
							}
						}
							catch(SocketException e){
								System.out.println("Session closed");
							}
						 catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
						
					}
					
				});

				sender.start();
				reciever.setDaemon(true);
				reciever.start();
				
				
				
				
				sender.join(0);
				Thread.sleep(1000);


			}
			
		}
}

