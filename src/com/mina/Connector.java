package com.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class Connector {
	
	private String _HOST;
	private int _PORT;	 
	private long CONNECT_TIMEOUT;
	
	private IoHandler _handler;
	private NioSocketConnector _connector; 

	public Connector(){
		this.CONNECT_TIMEOUT = 30*1000L;//  30 seconds
		_connector = new NioSocketConnector();
		_connector.setConnectTimeoutMillis(this.CONNECT_TIMEOUT);	
		_connector.getFilterChain().addLast("codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
		_connector.getFilterChain().addLast("logger", new LoggingFilter());
		 
	}

	public Connector(long connect_timeout){	
		this.CONNECT_TIMEOUT = connect_timeout;
		_connector = new NioSocketConnector();
		_connector.setConnectTimeoutMillis(this.CONNECT_TIMEOUT);	
		_connector.getFilterChain().addLast("codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
		_connector.getFilterChain().addLast("logger", new LoggingFilter());
		 
	}

	public Connector(long connect_timeout,IoFilter codecFilter){
		this.CONNECT_TIMEOUT = connect_timeout;
		_connector = new NioSocketConnector();
		_connector.setConnectTimeoutMillis(this.CONNECT_TIMEOUT);	
		_connector.getFilterChain().addLast("codec", codecFilter);		
		_connector.getFilterChain().addLast("logger", new LoggingFilter());
		 
	}
	
	public void setHandler(IoHandler handler){
		this._handler = handler;
		_connector.setHandler(this._handler);
	}
	
	public ConnectFuture connect(String ip,int port){
		this._HOST = ip;
		this._PORT = port;
		return _connector.connect(new InetSocketAddress(this._HOST,this._PORT));
		
		
	}
	
	public void dispose(boolean i){
		_connector.dispose(i);
	}

	public String get_HOST() {
		return _HOST;
	}

	public void set_HOST(String _HOST) {
		this._HOST = _HOST;
	}

	public int get_PORT() {
		return _PORT;
	}

	public void set_PORT(int _PORT) {
		this._PORT = _PORT;
	}

	public IoHandler get_handler() {
		return _handler;
	}

	public void set_handler(IoHandler _handler) {
		this._handler = _handler;
	}
	
	
}
