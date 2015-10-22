package org.bmartins.sandbox.curator.jmdns;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

public class ServiceInstance implements ServiceListener {

	private static final String SERVICE_TYPE = "_bmartins._http._tcp.local."; 
	
	public static void main(String[] args) throws Exception {
        JmDNS jmdns = JmDNS.create();
        jmdns.addServiceListener(SERVICE_TYPE, new ServiceInstance());
        
        int port = (int)(64000 * Math.random());
        
        System.out.println("Starting service on port: " + port);
        
        ServiceInfo info = ServiceInfo.create(SERVICE_TYPE, Integer.toString(port), port, 0, 0, "path=index.html");
        jmdns.registerService(info);
        
        System.out.println("\nRegistered Service as "+info);
	}

	public void serviceAdded(ServiceEvent e) {
		if(SERVICE_TYPE.equals(e.getType())) {
			log("service added ", e);	
		}		
	}

	public void serviceRemoved(ServiceEvent e) {
		if(SERVICE_TYPE.equals(e.getType())) {
			log("service removed", e);	
		}		
	}

	public void serviceResolved(ServiceEvent e) {
		if(SERVICE_TYPE.equals(e.getType())) {
			log("service resolved", e);			
		}				
	}
	
	private static void log(String message, ServiceEvent e) {
		System.out.println(message + ": " + e);
	}

}
