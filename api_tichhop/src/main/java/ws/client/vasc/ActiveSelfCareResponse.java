/**
 * ActiveSelfCareResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ActiveSelfCareResponse  implements java.io.Serializable {
    private CommonVO activeSelfCareResult;

    public ActiveSelfCareResponse() {
    }

    public ActiveSelfCareResponse(
           CommonVO activeSelfCareResult) {
           this.activeSelfCareResult = activeSelfCareResult;
    }


    /**
     * Gets the activeSelfCareResult value for this ActiveSelfCareResponse.
     * 
     * @return activeSelfCareResult
     */
    public CommonVO getActiveSelfCareResult() {
        return activeSelfCareResult;
    }


    /**
     * Sets the activeSelfCareResult value for this ActiveSelfCareResponse.
     * 
     * @param activeSelfCareResult
     */
    public void setActiveSelfCareResult(CommonVO activeSelfCareResult) {
        this.activeSelfCareResult = activeSelfCareResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ActiveSelfCareResponse)) return false;
        ActiveSelfCareResponse other = (ActiveSelfCareResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.activeSelfCareResult==null && other.getActiveSelfCareResult()==null) || 
             (this.activeSelfCareResult!=null &&
              this.activeSelfCareResult.equals(other.getActiveSelfCareResult())));
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
        if (getActiveSelfCareResult() != null) {
            _hashCode += getActiveSelfCareResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ActiveSelfCareResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ActiveSelfCareResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activeSelfCareResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ActiveSelfCareResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "CommonVO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
