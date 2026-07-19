/**
 * ChangeContractResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangeContractResponse  implements java.io.Serializable {
    private ChangeContractVO changeContractResult;

    public ChangeContractResponse() {
    }

    public ChangeContractResponse(
           ChangeContractVO changeContractResult) {
           this.changeContractResult = changeContractResult;
    }


    /**
     * Gets the changeContractResult value for this ChangeContractResponse.
     * 
     * @return changeContractResult
     */
    public ChangeContractVO getChangeContractResult() {
        return changeContractResult;
    }


    /**
     * Sets the changeContractResult value for this ChangeContractResponse.
     * 
     * @param changeContractResult
     */
    public void setChangeContractResult(ChangeContractVO changeContractResult) {
        this.changeContractResult = changeContractResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangeContractResponse)) return false;
        ChangeContractResponse other = (ChangeContractResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.changeContractResult==null && other.getChangeContractResult()==null) || 
             (this.changeContractResult!=null &&
              this.changeContractResult.equals(other.getChangeContractResult())));
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
        if (getChangeContractResult() != null) {
            _hashCode += getChangeContractResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeContractResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ChangeContractResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changeContractResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeContractResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeContractVO"));
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
