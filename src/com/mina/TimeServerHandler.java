package com.mina;

import java.net.InetSocketAddress;
import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;


/**用于处理客户端当前请求
 * 	use to service client connections and the requests for the current time. 
		  
 * @author Administrator
 *
 */
public class TimeServerHandler extends IoHandlerAdapter {

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
			
		cause.printStackTrace();
	}

	/** The messageReceived method will receive the data from the client and write back to the client the current time.
	 *
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		 
			String str = message.toString();
			System.out.println("Received message:"+str.trim());
	        if( str.trim().equalsIgnoreCase("quit") ) {
	            session.close();
	            return;
	        }
	        Date date = new Date();
	        session.write( date.toString() );
	        System.out.println("Message written...");
		
		
	}

	/**Invoked when a message written by IoSession.write(Object) is sent out.
	 * 
	 */
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		 System.out.println("messageSent："+message.toString().trim());
	}

	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		 
		System.out.println( session.getId() + "|IDLE " + session.getIdleCount( status ));
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println( "sessionCreated");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println( "sessionOpened");
	}
	
	
	
	/** Invoked when a connection is closed.
	 * 
	 * 
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println( "sessionClosed");
	}

	
	
	
}
