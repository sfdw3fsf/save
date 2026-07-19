/**
 * ServicesLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.ivan;

public class ServicesLocator extends org.apache.axis.client.Service implements Services {

    public ServicesLocator() {
    }


    public ServicesLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServicesLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IServices
    private String BasicHttpBinding_IServices_address = "http://gateway.vnpt-bhxh.vn:8080/Services.svc";

    public String getBasicHttpBinding_IServicesAddress() {
        return BasicHttpBinding_IServices_address;
    }

    // The WSDD service name defaults to the port name.
    private String BasicHttpBinding_IServicesWSDDServiceName = "BasicHttpBinding_IServices";

    public String getBasicHttpBinding_IServicesWSDDServiceName() {
        return BasicHttpBinding_IServicesWSDDServiceName;
    }

    public void setBasicHttpBinding_IServicesWSDDServiceName(String name) {
        BasicHttpBinding_IServicesWSDDServiceName = name;
    }

    public IServices getBasicHttpBinding_IServices() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IServices_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IServices(endpoint);
    }

    public IServices getBasicHttpBinding_IServices(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            BasicHttpBinding_IServicesStub _stub = new BasicHttpBinding_IServicesStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IServicesWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IServicesEndpointAddress(String address) {
        BasicHttpBinding_IServices_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (IServices.class.isAssignableFrom(serviceEndpointInterface)) {
                BasicHttpBinding_IServicesStub _stub = new BasicHttpBinding_IServicesStub(new java.net.URL(BasicHttpBinding_IServices_address), this);
                _stub.setPortName(getBasicHttpBinding_IServicesWSDDServiceName());
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
        if ("BasicHttpBinding_IServices".equals(inputPortName)) {
            return getBasicHttpBinding_IServices();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "Services");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IServices"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IServices".equals(portName)) {
            setBasicHttpBinding_IServicesEndpointAddress(address);
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
