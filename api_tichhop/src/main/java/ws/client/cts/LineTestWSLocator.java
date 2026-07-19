/**
 * LineTestWSLocator.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.cts;

public class LineTestWSLocator extends org.apache.axis.client.Service implements ws.client.cts.LineTestWS {

    public LineTestWSLocator() {
    }


    public LineTestWSLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LineTestWSLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LineTestWSSoap12
    private java.lang.String LineTestWSSoap12_address = "https://ctslinetest.vnpt.vn/LineTestWS.asmx";

    public java.lang.String getLineTestWSSoap12Address() {
        return LineTestWSSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LineTestWSSoap12WSDDServiceName = "LineTestWSSoap12";

    public java.lang.String getLineTestWSSoap12WSDDServiceName() {
        return LineTestWSSoap12WSDDServiceName;
    }

    public void setLineTestWSSoap12WSDDServiceName(java.lang.String name) {
        LineTestWSSoap12WSDDServiceName = name;
    }

    public ws.client.cts.LineTestWSSoap_PortType getLineTestWSSoap12() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LineTestWSSoap12_address);
        } catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLineTestWSSoap12(endpoint);
    }

    public ws.client.cts.LineTestWSSoap_PortType getLineTestWSSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ws.client.cts.LineTestWSSoap12Stub _stub = new ws.client.cts.LineTestWSSoap12Stub(portAddress, this);
            _stub.setPortName(getLineTestWSSoap12WSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLineTestWSSoap12EndpointAddress(java.lang.String address) {
        LineTestWSSoap12_address = address;
    }


    // Use to get a proxy class for LineTestWSSoap
    private java.lang.String LineTestWSSoap_address = "https://ctslinetest.vnpt.vn/LineTestWS.asmx";

    public java.lang.String getLineTestWSSoapAddress() {
        return LineTestWSSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LineTestWSSoapWSDDServiceName = "LineTestWSSoap";

    public java.lang.String getLineTestWSSoapWSDDServiceName() {
        return LineTestWSSoapWSDDServiceName;
    }

    public void setLineTestWSSoapWSDDServiceName(java.lang.String name) {
        LineTestWSSoapWSDDServiceName = name;
    }

    public ws.client.cts.LineTestWSSoap_PortType getLineTestWSSoap() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LineTestWSSoap_address);
        } catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLineTestWSSoap(endpoint);
    }

    public ws.client.cts.LineTestWSSoap_PortType getLineTestWSSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ws.client.cts.LineTestWSSoap_BindingStub _stub = new ws.client.cts.LineTestWSSoap_BindingStub(portAddress, this);
            _stub.setPortName(getLineTestWSSoapWSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLineTestWSSoapEndpointAddress(java.lang.String address) {
        LineTestWSSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ws.client.cts.LineTestWSSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                ws.client.cts.LineTestWSSoap12Stub _stub = new ws.client.cts.LineTestWSSoap12Stub(new java.net.URL(LineTestWSSoap12_address), this);
                _stub.setPortName(getLineTestWSSoap12WSDDServiceName());
                return _stub;
            }
            if (ws.client.cts.LineTestWSSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                ws.client.cts.LineTestWSSoap_BindingStub _stub = new ws.client.cts.LineTestWSSoap_BindingStub(new java.net.URL(LineTestWSSoap_address), this);
                _stub.setPortName(getLineTestWSSoapWSDDServiceName());
                return _stub;
            }
        } catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("LineTestWSSoap12".equals(inputPortName)) {
            return getLineTestWSSoap12();
        } else if ("LineTestWSSoap".equals(inputPortName)) {
            return getLineTestWSSoap();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://vnpt.vn/", "LineTestWS");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://vnpt.vn/", "LineTestWSSoap12"));
            ports.add(new javax.xml.namespace.QName("http://vnpt.vn/", "LineTestWSSoap"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

        if ("LineTestWSSoap12".equals(portName)) {
            setLineTestWSSoap12EndpointAddress(address);
        } else if ("LineTestWSSoap".equals(portName)) {
            setLineTestWSSoapEndpointAddress(address);
        } else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
