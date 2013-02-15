package com.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.example.sumup.codec.SumUpProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.sincered.pcr.device.Command;
import com.sincered.pcr.device.NetworkPortCommand;

/**
 * 创建客户端步骤
 * To construct a Client, we need to do following:
	1.Create a Connector
	2.Create a Filter Chain
	3.Create a IOHandler and add to Connector
	4.Bind to Server
 * @author Administrator
 *
 */
public class Client {
	
	private static final String[] HOSTNAME = {"192.168.1.108","192.168.1.111"};
	private static final int[] PORT = {4003,8080};

	private static final long CONNECT_TIMEOUT = 30*1000L; // 30 seconds
	  
	// 设置为false则使用对象序列化,而不是定制的编解码器
	private static final boolean USE_CUSTOM_CODEC = true;

	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Throwable {
		

		  NioSocketConnector con = new NioSocketConnector();
		  
		  con.setConnectTimeoutMillis(CONNECT_TIMEOUT);
		  
		  con.getFilterChain().addLast("codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
		  con.getFilterChain().addLast("logger", new LoggingFilter());
			
		  ClientSessionHandler clientSessionHandler = new ClientSessionHandler();
			
		  con.setHandler(clientSessionHandler);		
			
		 
		
		
			IoSession session=null;  	
		   ConnectFuture future=null;
		
		  try {
			 
			// future = connector.connect(new InetSocketAddress(HOSTNAME,PORT));
			 future = con.connect(new InetSocketAddress(HOSTNAME[0], PORT[0]));
			System.out.println("addr:"+HOSTNAME[0]+":"+ PORT[0]);
			 //阻塞方式 5S
			 future.awaitUninterruptibly(2000);
			 System.out.println("future isConnected:"+future.isConnected());
			//连接成功
			 if(future.isConnected())
				 session = future.getSession();
			 
			 System.out.println("count:"+con.getManagedSessionCount());	
			
		  } catch (RuntimeIoException  e) {
			System.err.println("Failed to connect.");
			e.printStackTrace();
			Thread.sleep(5000);
		  }
		 int i=0;
		 while(i<5){
			 Thread.sleep(2000);
			 i++;
		 }
		  System.out.println("Over~");
		  future.getSession().close(true);
		  future.cancel();		
		  System.out.println("close");
		
		
		  con.dispose(true);
		  System.out.println("dispose");
			
	/*	 NetworkPortCommand command = new NetworkPortCommand();
		 Command status = command.STATUS();
		 
		  if(session.isConnected()==true){
			  System.out.println("isConnected true|"+session.getId());
			  for(int i=0;i<20;i++ ){
				  
				  //发送字节数组
				  session.write(IoBuffer.wrap(status.getSendCommand()));		  
				  Thread.sleep(3000);
			  }
		  }
		 System.out.println("SEND OVER!");
		  session.close(true);
		  session.getCloseFuture().awaitUninterruptibly();
		 
		 // connector.dispose();
		  con.dispose();
		  System.out.println("dispose over");	*/
		 
		  
	}
}
