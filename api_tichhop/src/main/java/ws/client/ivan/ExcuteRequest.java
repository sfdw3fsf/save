/**
 * ExcuteRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.ivan;

public class ExcuteRequest  implements java.io.Serializable {
    private String request_id;

    private String client_id;

    private String function_code;

    private String parameters;

    private String secure_code;

    public ExcuteRequest() {
    }

    public ExcuteRequest(
           String request_id,
           String client_id,
           String function_code,
           String parameters,
           String secure_code) {
           this.request_id = request_id;
           this.client_id = client_id;
           this.function_code = function_code;
           this.parameters = parameters;
           this.secure_code = secure_code;
    }


    /**
     * Gets the request_id value for this ExcuteRequest.
     * 
     * @return request_id
     */
    public String getRequest_id() {
        return request_id;
    }


    /**
     * Sets the request_id value for this ExcuteRequest.
     * 
     * @param request_id
     */
    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }


    /**
     * Gets the client_id value for this ExcuteRequest.
     * 
     * @return client_id
     */
    public String getClient_id() {
        return client_id;
    }


    /**
     * Sets the client_id value for this ExcuteRequest.
     * 
     * @param client_id
     */
    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }


    /**
     * Gets the function_code value for this ExcuteRequest.
     * 
     * @return function_code
     */
    public String getFunction_code() {
        return function_code;
    }


    /**
     * Sets the function_code value for this ExcuteRequest.
     * 
     * @param function_code
     */
    public void setFunction_code(String function_code) {
        this.function_code = function_code;
    }


    /**
     * Gets the parameters value for this ExcuteRequest.
     * 
     * @return parameters
     */
    public String getParameters() {
        return parameters;
    }


    /**
     * Sets the parameters value for this ExcuteRequest.
     * 
     * @param parameters
     */
    public void setParameters(String parameters) {
        this.parameters = parameters;
    }


    /**
     * Gets the secure_code value for this ExcuteRequest.
     * 
     * @return secure_code
     */
    public String getSecure_code() {
        return secure_code;
    }


    /**
     * Sets the secure_code value for this ExcuteRequest.
     * 
     * @param secure_code
     */
    public void setSecure_code(String secure_code) {
        this.secure_code = secure_code;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ExcuteRequest)) return false;
        ExcuteRequest other = (ExcuteRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.request_id==null && other.getRequest_id()==null) || 
             (this.request_id!=null &&
              this.request_id.equals(other.getRequest_id()))) &&
            ((this.client_id==null && other.getClient_id()==null) || 
             (this.client_id!=null &&
              this.client_id.equals(other.getClient_id()))) &&
            ((this.function_code==null && other.getFunction_code()==null) || 
             (this.function_code!=null &&
              this.function_code.equals(other.getFunction_code()))) &&
            ((this.parameters==null && other.getParameters()==null) || 
             (this.parameters!=null &&
              this.parameters.equals(other.getParameters()))) &&
            ((this.secure_code==null && other.getSecure_code()==null) || 
             (this.secure_code!=null &&
              this.secure_code.equals(other.getSecure_code())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getRequest_id() != null) {
            _hashCode += getRequest_id().hashCode();
        }
        if (getClient_id() != null) {
            _hashCode += getClient_id().hashCode();
        }
        if (getFunction_code() != null) {
            _hashCode += getFunction_code().hashCode();
        }
        if (getParameters() != null) {
            _hashCode += getParameters().hashCode();
        }
        if (getSecure_code() != null) {
            _hashCode += getSecure_code().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ExcuteRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ExcuteRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("request_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "request_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("client_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "client_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("function_code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "function_code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parameters");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "parameters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("secure_code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "secure_code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
