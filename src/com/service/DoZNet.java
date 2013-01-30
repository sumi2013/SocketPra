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
		//LongGeneService longGeneService = new LongGeneService("192.168.1.108", 3003);
		CommandConvertor convertor = new CommandConvertor();
		System.out.println("start!");
	
	
		try {		
			
			
			
			//串口通讯
			//ComPortService comPortService = new ComPortService();
			//	comPortService.testComPort();
			
			//网口通讯
			//longGeneService.open();
			/*String str = "STATUS?";		
			
		    byte[] scom = convertor.getSendCommandbyNetworkPort(str);
			
		    System.out.println("scom:"+scom+" | "+new String(scom));
			for(byte t:scom){
				System.out.print(t+" ");
			}			
			System.out.println();	
		
			NetworkPortCommand networkPortCommand = new NetworkPortCommand();
			Command Status = networkPortCommand.STATUS();
			System.out.println("cmd:"+Status.getSendCommand());
			for(byte b:Status.getSendCommand()){
				System.out.print(b+" ");
			}
			System.out.println();
			
			String str2 = "PROGRAM \"TEST1\"";		
			byte[] scom2 = convertor.getSendCommandbyNetworkPort(str2);
			System.out.println("scom2:");
			for(byte b:scom2){
				System.out.print(Integer.toHexString(b)+" ");
			}
			Status = networkPortCommand.PROGRAM("TEST1");
			System.out.println("str2:");
			for(byte b:Status.getSendCommand()){
				System.out.print(Integer.toHexString(b)+" ");
			}
			for(int i=0;i<5;i++){
		    try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {			
					e.printStackTrace();
				}
		    	   System.out.println("sendCommand:"+longGeneService.SendCommand(Status.getSendCommand()));					
		    	   System.out.println("***************************");	
		    }
			*/
			
			
			
			
	
			
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//longGeneService.close();
		}
		

		 /**搜索设备 获取设备
		  * 
		  */
		/*	ZNetService netService = new ZNetService();
			List<Map> devlist = netService.GetSearchAllDevice();
			for(Map m:devlist){
				System.out.println("IP:"+m.get("IP"));
				System.out.println("VERSION:"+m.get("VERSION"));
				System.out.println("MAC:"+m.get("MAC"));
				System.out.println("DEVTYPE:"+m.get("DEVTYPE"));
				System.out.println("IPMODE :"+m.get("IPMODE"));
				System.out.println("TCPPORT:"+m.get("TCPPORT"));
			}*/
	/*	try {			
			 System.out.println("SearchAll:"+advService.SearchAll());			
			 Thread.sleep(1000);
			 byte[] szip=new byte[20];//设备IP
			 byte[] szver=new byte[20];//设备固件版本号
			 byte[] szmac=new byte[20];//设备MAC地址
			 ByteByReference pdevtype=new ByteByReference();//设备类型号
			 ByteByReference pipmode=new ByteByReference();//IP获取方式
			 IntByReference ptcpport=new IntByReference();//TCP命令端口
			 System.out.println("GetSearchDev:"+advService.GetSearchDev(szip, szver, szmac,pdevtype, pipmode,ptcpport));
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("********************************");*/
		
		//System.out.println("Cmd:"+advService.SendCmd(new Byte((byte)34), "192.168.1.108", 4001, "pause 0x0a 0x0d", "", new Byte((byte)1)));
		
		
		/**	TCP方式获取设备详细信息
		 * 
		 */
		  //设备属性代号
		  String[] g_strname= {"TYPE","IP","NAME","C1_OP","C1_PORT","C1_BAUD","C1_DATAB","C1_STOPB","C1_PARITY"};
		
		 ZNetService netService = new ZNetService();
		 List<Map> mlist = netService.GetSearchAllDevice();
		 for(Map m:mlist){
			 System.out.println(m.get("IP"));
			 System.out.println(m.get("MAC"));
		 }
		 if(netService.LoginTCP((byte)34, "192.168.1.108",3003, "88888")==1){
			 ZNetAdvService zNetAdvService = new ZNetAdvService();
			 zNetAdvService.ModifyDevConfigTCP("IP", new String("192.168.1.109").getBytes());
			 zNetAdvService.ResetDevTCP();
			 zNetAdvService.ModifyDevConfigTCP("NAME", new String("192.168.1.109").getBytes());
			 zNetAdvService.ResetDevTCP();
			/* Map<String,byte[]> map = new HashMap();
			 map.put("IP", new String("192.168.1.109").getBytes());
			 map.put("NAME", new String("SINCERED").getBytes());
			 System.out.println("MODIFY:"+netService.ModifyDevConfigTCP(map));*/
		 }
		 
		
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
