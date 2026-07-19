/**
 * SubscriberManagementLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class SubscriberManagementLocator extends org.apache.axis.client.Service implements SubscriberManagement {

    public SubscriberManagementLocator() {
    }


    public SubscriberManagementLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SubscriberManagementLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SubscriberManagementSoap
    private String SubscriberManagementSoap_address = "https://portal.mytv.vn/servicevtt/SubscriberManagement.asmx";

    public String getSubscriberManagementSoapAddress() {
        return SubscriberManagementSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private String SubscriberManagementSoapWSDDServiceName = "SubscriberManagementSoap";

    public String getSubscriberManagementSoapWSDDServiceName() {
        return SubscriberManagementSoapWSDDServiceName;
    }

    public void setSubscriberManagementSoapWSDDServiceName(String name) {
        SubscriberManagementSoapWSDDServiceName = name;
    }

    public SubscriberManagementSoap_PortType getSubscriberManagementSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SubscriberManagementSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSubscriberManagementSoap(endpoint);
    }

    public SubscriberManagementSoap_PortType getSubscriberManagementSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            SubscriberManagementSoap_BindingStub _stub = new SubscriberManagementSoap_BindingStub(portAddress, this);
            _stub.setPortName(getSubscriberManagementSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSubscriberManagementSoapEndpointAddress(String address) {
        SubscriberManagementSoap_address = address;
    }


    // Use to get a proxy class for SubscriberManagementSoap12
    private String SubscriberManagementSoap12_address = "https://portal.mytv.vn/servicevtt/SubscriberManagement.asmx";

    public String getSubscriberManagementSoap12Address() {
        return SubscriberManagementSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private String SubscriberManagementSoap12WSDDServiceName = "SubscriberManagementSoap12";

    public String getSubscriberManagementSoap12WSDDServiceName() {
        return SubscriberManagementSoap12WSDDServiceName;
    }

    public void setSubscriberManagementSoap12WSDDServiceName(String name) {
        SubscriberManagementSoap12WSDDServiceName = name;
    }

    public SubscriberManagementSoap_PortType getSubscriberManagementSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SubscriberManagementSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSubscriberManagementSoap12(endpoint);
    }

    public SubscriberManagementSoap_PortType getSubscriberManagementSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            SubscriberManagementSoap12Stub _stub = new SubscriberManagementSoap12Stub(portAddress, this);
            _stub.setPortName(getSubscriberManagementSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSubscriberManagementSoap12EndpointAddress(String address) {
        SubscriberManagementSoap12_address = address;
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
            if (SubscriberManagementSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                SubscriberManagementSoap_BindingStub _stub = new SubscriberManagementSoap_BindingStub(new java.net.URL(SubscriberManagementSoap_address), this);
                _stub.setPortName(getSubscriberManagementSoapWSDDServiceName());
                return _stub;
            }
            if (SubscriberManagementSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                SubscriberManagementSoap12Stub _stub = new SubscriberManagementSoap12Stub(new java.net.URL(SubscriberManagementSoap12_address), this);
                _stub.setPortName(getSubscriberManagementSoap12WSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
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
        String inputPortName = portName.getLocalPart();
        if ("SubscriberManagementSoap".equals(inputPortName)) {
            return getSubscriberManagementSoap();
        }
        else if ("SubscriberManagementSoap12".equals(inputPortName)) {
            return getSubscriberManagementSoap12();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "SubscriberManagement");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "SubscriberManagementSoap"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "SubscriberManagementSoap12"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("SubscriberManagementSoap".equals(portName)) {
            setSubscriberManagementSoapEndpointAddress(address);
        }
        else 
if ("SubscriberManagementSoap12".equals(portName)) {
            setSubscriberManagementSoap12EndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
