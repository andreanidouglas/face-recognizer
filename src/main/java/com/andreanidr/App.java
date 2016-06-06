package com.andreanidr;

public class App {

		
	public static void main (String args[]) throws Exception
	{
		ImagesApp ia = new ImagesApp();
		ia.init();
		
		System.out.println(ia.matchImage("/home/cmte/face-recognizer/obama.jpg"));
	}
	
	
	
	
	/*public static void main(String args[]){
	
		try{
			MainServer newServer = new MainServer(8080);
			MainHandler newHandler = new MainHandler();
			
			newServer.setMainHandler(newHandler);
			
			newServer.startServer();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}*/

}