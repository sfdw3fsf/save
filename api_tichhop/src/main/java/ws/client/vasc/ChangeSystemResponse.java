/**
 * ChangeSystemResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangeSystemResponse  implements java.io.Serializable {
    private ChangeSystemResp changeSystemResult;

    public ChangeSystemResponse() {
    }

    public ChangeSystemResponse(
           ChangeSystemResp changeSystemResult) {
           this.changeSystemResult = changeSystemResult;
    }


    /**
     * Gets the changeSystemResult value for this ChangeSystemResponse.
     * 
     * @return changeSystemResult
     */
    public ChangeSystemResp getChangeSystemResult() {
        return changeSystemResult;
    }


    /**
     * Sets the changeSystemResult value for this ChangeSystemResponse.
     * 
     * @param changeSystemResult
     */
    public void setChangeSystemResult(ChangeSystemResp changeSystemResult) {
        this.changeSystemResult = changeSystemResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangeSystemResponse)) return false;
        ChangeSystemResponse other = (ChangeSystemResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.changeSystemResult==null && other.getChangeSystemResult()==null) || 
             (this.changeSystemResult!=null &&
              this.changeSystemResult.equals(other.getChangeSystemResult())));
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
        if (getChangeSystemResult() != null) {
            _hashCode += getChangeSystemResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeSystemResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ChangeSystemResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changeSystemResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeSystemResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeSystemResp"));
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
