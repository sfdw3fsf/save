/**
 * WSAppImplService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.application;

public interface WSAppImplService extends javax.xml.rpc.Service {
    public String getApplicationBusinessServicesAddress();

    public WSAppImpl getApplicationBusinessServices() throws javax.xml.rpc.ServiceException;

    public WSAppImpl getApplicationBusinessServices(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
