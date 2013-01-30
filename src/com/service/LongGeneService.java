package com.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

import org.apache.log4j.Logger;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;



public class LongGeneService {
	
	private Socket socket=null;
	private InputStream input;
	private OutputStream output;

	private String deviceIP;
	private int devicePort;
	
	public LongGeneService(String deviceIP,int devicePort) {
			this.deviceIP=deviceIP;
			this.devicePort=devicePort;
	}
	
	public String SendCommand(byte[] command) throws Exception{
		System.out.println("sendcommand start~");
		if(command==null){
			throw new Exception("command must not be null");			
		}
	
		this.output.write(command);
				
		System.out.println("sendcommand end~");
		return this.receive();
	}
	
	
	public String SendCommand(String command)throws Exception{
		System.out.println("sendcommand start~");
		if(command==null){
			throw new Exception("command must not be null");
		}		
		
		System.out.println("sendcommand:"+command);
		
		
		this.output.write(command.getBytes());	
					
	//	this.output.write(command.getBytes());	
		
		return this.receive();
	}
	
	public String receive() throws IOException{		
		int count=0;
	
		count=this.input.available();
		byte[] data;
		if(count!=0){
			data=new byte[count];
			this.input.read(data);
			return new String(data);
		}
		return null;
		
	}
	
	public void open() throws UnknownHostException, IOException, ParseException{
		this.socket=new Socket();
		SocketAddress sa = new InetSocketAddress(deviceIP,devicePort);
		socket.connect(sa, 5000);
		this.input=this.socket.getInputStream();
		this.output=this.socket.getOutputStream();
	
	}
	
	public void close(){
		if(this.input!=null)
			try {
				this.input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(this.output!=null)
			try {
				this.output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(this.socket!=null)
			try {
				this.socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
}
