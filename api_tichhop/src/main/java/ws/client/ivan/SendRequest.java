/**
 * SendRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.ivan;

public class SendRequest  implements java.io.Serializable {
    private String token;

    private String userId;

    private String password;

    private String serviceId;

    private String functionCode;

    private String inputParams;

    private byte[] fileAttachment;

    private Object[] outputVal;

    public SendRequest() {
    }

    public SendRequest(
           String token,
           String userId,
           String password,
           String serviceId,
           String functionCode,
           String inputParams,
           byte[] fileAttachment,
           Object[] outputVal) {
           this.token = token;
           this.userId = userId;
           this.password = password;
           this.serviceId = serviceId;
           this.functionCode = functionCode;
           this.inputParams = inputParams;
           this.fileAttachment = fileAttachment;
           this.outputVal = outputVal;
    }


    /**
     * Gets the token value for this SendRequest.
     * 
     * @return token
     */
    public String getToken() {
        return token;
    }


    /**
     * Sets the token value for this SendRequest.
     * 
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }


    /**
     * Gets the userId value for this SendRequest.
     * 
     * @return userId
     */
    public String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this SendRequest.
     * 
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }


    /**
     * Gets the password value for this SendRequest.
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this SendRequest.
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Gets the serviceId value for this SendRequest.
     * 
     * @return serviceId
     */
    public String getServiceId() {
        return serviceId;
    }


    /**
     * Sets the serviceId value for this SendRequest.
     * 
     * @param serviceId
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }


    /**
     * Gets the functionCode value for this SendRequest.
     * 
     * @return functionCode
     */
    public String getFunctionCode() {
        return functionCode;
    }


    /**
     * Sets the functionCode value for this SendRequest.
     * 
     * @param functionCode
     */
    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }


    /**
     * Gets the inputParams value for this SendRequest.
     * 
     * @return inputParams
     */
    public String getInputParams() {
        return inputParams;
    }


    /**
     * Sets the inputParams value for this SendRequest.
     * 
     * @param inputParams
     */
    public void setInputParams(String inputParams) {
        this.inputParams = inputParams;
    }


    /**
     * Gets the fileAttachment value for this SendRequest.
     * 
     * @return fileAttachment
     */
    public byte[] getFileAttachment() {
        return fileAttachment;
    }


    /**
     * Sets the fileAttachment value for this SendRequest.
     * 
     * @param fileAttachment
     */
    public void setFileAttachment(byte[] fileAttachment) {
        this.fileAttachment = fileAttachment;
    }


    /**
     * Gets the outputVal value for this SendRequest.
     * 
     * @return outputVal
     */
    public Object[] getOutputVal() {
        return outputVal;
    }


    /**
     * Sets the outputVal value for this SendRequest.
     * 
     * @param outputVal
     */
    public void setOutputVal(Object[] outputVal) {
        this.outputVal = outputVal;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof SendRequest)) return false;
        SendRequest other = (SendRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.token==null && other.getToken()==null) || 
             (this.token!=null &&
              this.token.equals(other.getToken()))) &&
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.serviceId==null && other.getServiceId()==null) || 
             (this.serviceId!=null &&
              this.serviceId.equals(other.getServiceId()))) &&
            ((this.functionCode==null && other.getFunctionCode()==null) || 
             (this.functionCode!=null &&
              this.functionCode.equals(other.getFunctionCode()))) &&
            ((this.inputParams==null && other.getInputParams()==null) || 
             (this.inputParams!=null &&
              this.inputParams.equals(other.getInputParams()))) &&
            ((this.fileAttachment==null && other.getFileAttachment()==null) || 
             (this.fileAttachment!=null &&
              java.util.Arrays.equals(this.fileAttachment, other.getFileAttachment()))) &&
            ((this.outputVal==null && other.getOutputVal()==null) || 
             (this.outputVal!=null &&
              java.util.Arrays.equals(this.outputVal, other.getOutputVal())));
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
        if (getToken() != null) {
            _hashCode += getToken().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getServiceId() != null) {
            _hashCode += getServiceId().hashCode();
        }
        if (getFunctionCode() != null) {
            _hashCode += getFunctionCode().hashCode();
        }
        if (getInputParams() != null) {
            _hashCode += getInputParams().hashCode();
        }
        if (getFileAttachment() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFileAttachment());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getFileAttachment(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getOutputVal() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOutputVal());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getOutputVal(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SendRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">SendRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("token");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "token"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "userId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "serviceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("functionCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "functionCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inputParams");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "inputParams"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileAttachment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "fileAttachment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outputVal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "outputVal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "anyType"));
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
