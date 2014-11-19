package com.myoflademo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.service.IServiceCapableConnection;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IStreamAwareScopeHandler;

public class Application extends MultiThreadedApplicationAdapter implements IStreamAwareScopeHandler {

	private List<String> streamsInRoom = new ArrayList<String>();	
	
	@Override
	public boolean appStart(IScope scope) {
		System.out.println("[ appStart ]");
		return true;
	}
	
	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		System.out.println("[ appConnect ]");
		return true;
	}
	
	@Override
	public boolean appJoin(IClient client, IScope scope) {
		System.out.println("[ appJoin ]");
		return true;
	}
	
	@Override
	public void appDisconnect(IConnection conn) {
		System.out.println("[ appDisconnect ]");
	}
	
	public void streamPublishStart(IBroadcastStream stream) {
		System.out.println("[ streamPublishStart ]");
	}
	
	public void streamBroadcastStart(IBroadcastStream stream) {
		
    	System.out.println("[ streamBroadcastStart ]");
    	
    	((IServiceCapableConnection) Red5.getConnectionLocal()).invoke("clientConnectionSuccess", new Object[] {Red5.getConnectionLocal().getClient().getId()});
    	
    	for (String streamName : streamsInRoom) {
    		((IServiceCapableConnection) Red5.getConnectionLocal()).invoke("newStreamInRoom", new Object[] {streamName});
    	}
    	
    	streamsInRoom.add(stream.getPublishedName());
    	
    	// Save stream
		try {
			stream.saveAs(stream.getName(), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	// Notify all other users
    	IClient newClient = Red5.getConnectionLocal().getClient();
    	Iterator<IConnection> itConnections = Red5.getConnectionLocal().getScope().getClientConnections().iterator();
    	while(itConnections.hasNext()) {
    		IConnection conn = itConnections.next();
    		if (!conn.getClient().getId().equals(newClient.getId()) && conn instanceof IServiceCapableConnection) {
    			((IServiceCapableConnection) conn).invoke("newStreamInRoom", new Object[] {stream.getPublishedName()});
    		}
    	}
    	
	}
	
    public void streamBroadcastClose(IBroadcastStream stream) {
    	IConnection conn = Red5.getConnectionLocal();
    	String currentClientId = conn.getClient().getId();
    	
    	System.out.println("[ streamBroadcastClose ] from clientId: " + currentClientId);
    	streamsInRoom.remove(stream.getPublishedName());
	}
    
    @Override
	public boolean roomStart(IScope room) {
    	System.out.println("[ roomStart ]");
    	return true;
	}

	@Override
	public boolean roomConnect(IConnection conn, Object[] params) {
		System.out.println("[ roomConnect ]");
		return true;
	}

	@Override
	public boolean roomJoin(IClient client, IScope room) {
		System.out.println("[ roomJoin ]");
		return true;
	}
    
    @Override
	public void roomLeave(IClient client, IScope room) {
    	System.out.println("[ roomLeave ]");
	}
    
    @Override
	public void roomStop(IScope scope) {
    	System.out.println("[ roomStop ]");
    }
    
    @Override
	public void roomDisconnect(IConnection conn) {
    	System.out.println("[ roomDisconnect ]");
	}
}
