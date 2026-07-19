/**
 * VTNAPIServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vtn;

public class VTNAPIServiceLocator extends org.apache.axis.client.Service implements VTNAPIService {

    public VTNAPIServiceLocator() {
    }


    public VTNAPIServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public VTNAPIServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for VTNAPIPort
    private String VTNAPIPort_address = "http://10.156.4.229:6060/vtn-soap";

    public String getVTNAPIPortAddress() {
        return VTNAPIPort_address;
    }

    // The WSDD service name defaults to the port name.
    private String VTNAPIPortWSDDServiceName = "VTNAPIPort";

    public String getVTNAPIPortWSDDServiceName() {
        return VTNAPIPortWSDDServiceName;
    }

    public void setVTNAPIPortWSDDServiceName(String name) {
        VTNAPIPortWSDDServiceName = name;
    }

    public VTNAPI getVTNAPIPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(VTNAPIPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getVTNAPIPort(endpoint);
    }

    public VTNAPI getVTNAPIPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            VTNAPIPortBindingStub _stub = new VTNAPIPortBindingStub(portAddress, this);
            _stub.setPortName(getVTNAPIPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setVTNAPIPortEndpointAddress(String address) {
        VTNAPIPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (VTNAPI.class.isAssignableFrom(serviceEndpointInterface)) {
                VTNAPIPortBindingStub _stub = new VTNAPIPortBindingStub(new java.net.URL(VTNAPIPort_address), this);
                _stub.setPortName(getVTNAPIPortWSDDServiceName());
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
        if ("VTNAPIPort".equals(inputPortName)) {
            return getVTNAPIPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://soap.crm.telsoft/", "VTNAPIService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://soap.crm.telsoft/", "VTNAPIPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("VTNAPIPort".equals(portName)) {
            setVTNAPIPortEndpointAddress(address);
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
