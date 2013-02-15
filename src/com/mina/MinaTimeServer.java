package com.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.session.IdleStatus;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * 创建一个MINA应用的步骤
 * So, in order to create a MINA based Application, you have to :
	1.Create an I/O service - Choose from already available Services (*Acceptor) or create your own
	2.Create a Filter Chain - Choose from already existing Filters or create a custom Filter for transforming request/response
	3.Create an I/O Handler - Write business logic, on handling different messages
 * 
 * @author Administrator
 *
 */
public class MinaTimeServer {

	//监听端口
	private static final int PORT = 9123;
	public static void main(String[] args) {
		
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		try {
			// This filter will log all information such as newly created sessions, messages received, messages sent, session closed. 
			acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
		   
			//This filter will translate binary or protocol specific data into message object and vice versa. 
			//use an existing TextLine factory because it will handle text base message for you 
			acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
		    
			//use to service client connections and the requests for the current time. 
		    acceptor.setHandler(new TimeServerHandler());
		    
		    //This will allow us to make socket-specific settings for the socket that will be used to accept connections from clients.
		    acceptor.getSessionConfig().setReadBufferSize( 2048 );
		    //BOTH_IDLE equivalent to READER_IDLE & WRITER_IDLE
	        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 5 );
		    
		    //This method will bind to the specified port and start processing of remote clients.
			acceptor.bind(new InetSocketAddress(PORT));
			
			System.out.println("over");
			//acceptor.dispose();
			
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}
}
