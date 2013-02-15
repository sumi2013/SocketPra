package com.mina;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.sincered.pcr.device.AnalyzeReceiceCommand;
import com.sincered.pcr.device.AnalyzeService;

public class ClientSessionHandler extends IoHandlerAdapter {

	private AnalyzeService analyzeService ;

	public ClientSessionHandler() {		
		this.analyzeService = new AnalyzeService();
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String str = message.toString();
	
		System.err.println("Client Received message:"+str);
		System.err.println(	analyzeService.Analyze(str));
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		 System.out.println("Client MessageSent："+message.toString().trim());
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		 System.out.println("sessionClosed");
		super.sessionClosed(session);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		 System.out.println("sessionCreated");
		super.sessionCreated(session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println( " Client " + session.getId() + "|IDLE " + session.getIdleCount( status ));
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		 System.out.println("sessionOpened");
		super.sessionOpened(session);
	}

	
}
