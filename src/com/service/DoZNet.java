package com.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.comm.CommDriver;
import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

import org.apache.log4j.PropertyConfigurator;

import com.sincered.pcr.device.Command;
import com.sincered.pcr.device.NetworkPortCommand;
import com.sincered.pcr.device.ZNetService;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.IntByReference;
import com.sincered.pcr.device.ZNetAdvService;

public class DoZNet {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {		

		URL log4jurl = DoZNet.class.getClassLoader().getResource("log4j.xml");
		PropertyConfigurator.configure(log4jurl);
		ZNetAdvService advService = new ZNetAdvService();
		//LongGeneService longGeneService = new LongGeneService("192.168.1.108", 4003);
		CommandConvertor convertor = new CommandConvertor();
		System.out.println("start!");
	
	
		try {		
			//List a = Arrays.asList(1,2,"STATUS");
			//System.out.println(""+a.contains("STATUS"));
			
			
			//串口通讯
			//ComPortService comPortService = new ComPortService();
			//	comPortService.testComPort();
			
			//网口通讯
		//	longGeneService.open();
			NetworkPortCommand networkPortCommand = new NetworkPortCommand();
			Command GETREV = networkPortCommand.GETREV(1);
			System.out.println("status:"+GETREV.getSendCommand());
			
			String c = "STATUS?";
			System.out.println("R:"+c.contains("status"));
			/*for(int i=0;i<5;i++){
		    try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {			
					e.printStackTrace();
				}
		    	   String cmd = longGeneService.SendCommand(GETREV.getSendCommand());
		    	   System.out.println("sendCommand:'"+cmd+"'");
		    	   System.out.println("============================");
		    	   if(cmd!=null){
			    	   int length = cmd.length();
			    	   System.out.println("length:"+length);
			    	   
			    	   for(int j=0;j<length;j++){
			    		   System.out.print("'"+cmd.charAt(j)+"',");
			    	   }
			    	   System.out.println();
			    	   if(cmd.indexOf("!")!=-1){
				    	   String cutStr = cmd.substring(cmd.indexOf("!")+1, cmd.lastIndexOf("\r"));
				    	   System.out.println("find:"+cutStr);
			    	   }
			    	 
		    	   }
		    	  	
		    	 
		    }*/
			
			
			
			
			
	
			
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//longGeneService.close();
		}
		

		
		/**	TCP方式获取设备详细信息
		 * 
		 */
		
		 
		
	/*	try {
			longGeneService.open();
			String rev = longGeneService.receive();
			System.out.println("length:"+rev.length());
		//	if(rev.length()>0)
			//	System.out.println("sendCommand:"+longGeneService.SendCommand("TEMP 95.0,30 \r \n"));
				
			//System.out.println("sendCommand:"+longGeneService.SendCommand("GETREV 1 \n\r"));
			//System.out.println("sendCommand:"+longGeneService.SendCommand("OK \n\r"));
			//System.out.println("ExitTCP:"+advService.ExitTCP());
			
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			longGeneService.close();
		}
		*/
		
	
		
		
		
	
		
		
	 
		
		System.out.println("end!");
		

	}


}
