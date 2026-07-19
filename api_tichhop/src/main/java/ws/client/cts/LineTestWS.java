/**
 * LineTestWS.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.cts;

public interface LineTestWS extends javax.xml.rpc.Service {
    java.lang.String getLineTestWSSoap12Address();

    ws.client.cts.LineTestWSSoap_PortType getLineTestWSSoap12() throws javax.xml.rpc.ServiceException;

    ws.client.cts.LineTestWSSoap_PortType getLineTestWSSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;

    java.lang.String getLineTestWSSoapAddress();

    ws.client.cts.LineTestWSSoap_PortType getLineTestWSSoap() throws javax.xml.rpc.ServiceException;

    ws.client.cts.LineTestWSSoap_PortType getLineTestWSSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
