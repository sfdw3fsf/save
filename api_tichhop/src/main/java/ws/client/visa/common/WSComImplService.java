/**
 * WSComImplService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.common;

public interface WSComImplService extends javax.xml.rpc.Service {
    public String getCommonBusinessServicesAddress();

    public WSComImpl getCommonBusinessServices() throws javax.xml.rpc.ServiceException;

    public WSComImpl getCommonBusinessServices(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
