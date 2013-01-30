package com.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.comm.CommDriver;
import javax.comm.CommPort;
import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

public class ComPortService {

	private CommPortIdentifier portId;

	private SerialPort serialPort;
	private InputStream is;
	private OutputStream os;
	
	
	public ComPortService() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		 System.setSecurityManager(null);
	     String drivername = "com.sun.comm.Win32Driver";
	     CommDriver driver = (CommDriver) Class.forName(drivername).newInstance();
         driver.initialize();
	}

	
	public void initPort() throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException{
		
        this.portId = CommPortIdentifier.getPortIdentifier("COM2");        
        this.serialPort = (SerialPort)portId.open("Serial_Communication", 1000);
        this.serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
        
        this.is = serialPort.getInputStream();
        this.os = serialPort.getOutputStream();      
        System.out.println("initPort complete! port:"+portId.getName());
	}

	 public List listPorts(){
	        List ports = new ArrayList();
	        Enumeration en = CommPortIdentifier.getPortIdentifiers();
	        CommPortIdentifier portId;
	        while (en.hasMoreElements()) {
	            portId = (CommPortIdentifier) en.nextElement();
	            
	            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL){
	                ports.add(portId.getName()+" | CurrentlyOwned:"+portId.isCurrentlyOwned());
	            }
	        }
	        return ports;
	    }
	 
	 public void close(){
		 if(serialPort!=null)
			 serialPort.close();
	 }
	 
	 public String SendCommand(byte[] command)throws Exception{
			if(command==null){
				throw new Exception("command must not be null");
			}		
			System.out.println("sendcommand:"+command);					
		
			this.os.write(command);
			return this.receive();
		}
		
	 public String receive() throws IOException{		
			int count=0;
			 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count=this.is.available();			
			byte[] data=new byte[count];
			this.is.read(data);
			String tem = new String(data);
			System.out.println("tem:"+tem);
			/*if(count!=0){			
				String start = Integer.toHexString(tem.substring(0, 1).getBytes()[0]);
				String end1 = Integer.toHexString(tem.substring(tem.length()-1, tem.length()).getBytes()[0]);
				String end2 = Integer.toHexString(tem.substring(tem.length()-2, tem.length()-1).getBytes()[0]);
				String end3 = Integer.toHexString(tem.substring(tem.length()-3, tem.length()-2).getBytes()[0]);
				String end4 = Integer.toHexString(tem.substring(tem.length()-4, tem.length()-3).getBytes()[0]);
				System.out.println("tem s:"+tem.substring(0, 1).getBytes()[0]);
				System.out.println("tem hex:"+start+"|"+end1+"|"+end2+"|"+end3+"|"+end4);
				//System.out.println("tem:"+tem.charAt(0)+"|"+tem.charAt(tem.length()-1));
			}*/
			
			
			return new String(data);
		}

	public CommPortIdentifier getPortId() {
		return portId;
	}
	public void setPortId(CommPortIdentifier portId) {
		this.portId = portId;
	}
	public SerialPort getSerialPort() {
		return serialPort;
	}
	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}
	public InputStream getIs() {
		return is;
	}
	public void setIs(InputStream is) {
		this.is = is;
	}
	public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}	
	
	/** 测试PCR串口通讯
	 *  TEST PASS!
	 *  
	 * 
	 */
	public void testComPort(){	
		//初始化串口连接	
		try {			
		    this.initPort();		   
		    List ports = this.listPorts();
	        if(ports.size()==0){
	            System.out.println("本地计算机上没有发现串口：!");
	        }else{
	            int y = 50;
	            System.out.println("本地计算机上发现"+ports.size()+"个串口：!");
	            for (int i = 0; i < ports.size(); i++) {
	                String port = (String) ports.get(i);	                
	                System.out.println("port:"+port);	               
	            }
	        }
	        
	 /*     byte[] com = {0x73,0x74,0x61,0x74,0x75,0x73,0x3F,0x0D,0x0A};
	      String t= new String(com);
	      int k =0;
	      for(byte b :com){	    	  
	    	  System.out.print(b+"(HEX:"+Integer.toHexString(b)+") ");
	    	
	      } */
	        
	      //字符串指令
	      String str = "status?\r\n";
	      CommandConvertor convertor = new CommandConvertor();	     
	      byte[] scom = convertor.getSendCommandbySerialPort(str);
	      System.out.println("scom:"+scom);
	      
	    
	      /** 发送指令 
	       *  需多发送几次
	       */
	      System.out.println("\n***************************");	   
	       for(int i=0;i<20;i++){
	    	   System.out.println("sendCmd result:"+this.SendCommand(scom));
	    	   System.out.println("***************************");	
	       }

		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			this.close();
		}
	}
	
	
}
