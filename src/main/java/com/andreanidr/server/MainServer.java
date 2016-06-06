package com.andreanidr.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class MainServer {

	private Server server;
	private int port;
	private AbstractHandler mainHandler;

	public MainServer(int port) {
		this.port = port;
		server = new Server(port);
	
		
	}

	public int getPort() {
		return port;
	}

	public void setMainHandler(AbstractHandler mainHandler) {
		this.mainHandler = mainHandler;
		this.server.setHandler(mainHandler);
	}

	public void startServer() throws Exception {
		if (this.mainHandler != null) {
			server.start();
			server.join();
		}

	}

	public Server getServer() {
		return this.server;
	}

}
