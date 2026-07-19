/**
 * ChangeSTBResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangeSTBResponse  implements java.io.Serializable {
    private ChangeSTBVO changeSTBResult;

    public ChangeSTBResponse() {
    }

    public ChangeSTBResponse(
           ChangeSTBVO changeSTBResult) {
           this.changeSTBResult = changeSTBResult;
    }


    /**
     * Gets the changeSTBResult value for this ChangeSTBResponse.
     * 
     * @return changeSTBResult
     */
    public ChangeSTBVO getChangeSTBResult() {
        return changeSTBResult;
    }


    /**
     * Sets the changeSTBResult value for this ChangeSTBResponse.
     * 
     * @param changeSTBResult
     */
    public void setChangeSTBResult(ChangeSTBVO changeSTBResult) {
        this.changeSTBResult = changeSTBResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangeSTBResponse)) return false;
        ChangeSTBResponse other = (ChangeSTBResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.changeSTBResult==null && other.getChangeSTBResult()==null) || 
             (this.changeSTBResult!=null &&
              this.changeSTBResult.equals(other.getChangeSTBResult())));
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
        if (getChangeSTBResult() != null) {
            _hashCode += getChangeSTBResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeSTBResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ChangeSTBResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changeSTBResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeSTBResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeSTBVO"));
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
