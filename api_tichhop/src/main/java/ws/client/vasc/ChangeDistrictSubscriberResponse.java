/**
 * ChangeDistrictSubscriberResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangeDistrictSubscriberResponse  implements java.io.Serializable {
    private ChangeDistrictSubscriberVO changeDistrictSubscriberResult;

    public ChangeDistrictSubscriberResponse() {
    }

    public ChangeDistrictSubscriberResponse(
           ChangeDistrictSubscriberVO changeDistrictSubscriberResult) {
           this.changeDistrictSubscriberResult = changeDistrictSubscriberResult;
    }


    /**
     * Gets the changeDistrictSubscriberResult value for this ChangeDistrictSubscriberResponse.
     * 
     * @return changeDistrictSubscriberResult
     */
    public ChangeDistrictSubscriberVO getChangeDistrictSubscriberResult() {
        return changeDistrictSubscriberResult;
    }


    /**
     * Sets the changeDistrictSubscriberResult value for this ChangeDistrictSubscriberResponse.
     * 
     * @param changeDistrictSubscriberResult
     */
    public void setChangeDistrictSubscriberResult(ChangeDistrictSubscriberVO changeDistrictSubscriberResult) {
        this.changeDistrictSubscriberResult = changeDistrictSubscriberResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangeDistrictSubscriberResponse)) return false;
        ChangeDistrictSubscriberResponse other = (ChangeDistrictSubscriberResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.changeDistrictSubscriberResult==null && other.getChangeDistrictSubscriberResult()==null) || 
             (this.changeDistrictSubscriberResult!=null &&
              this.changeDistrictSubscriberResult.equals(other.getChangeDistrictSubscriberResult())));
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
        if (getChangeDistrictSubscriberResult() != null) {
            _hashCode += getChangeDistrictSubscriberResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeDistrictSubscriberResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ChangeDistrictSubscriberResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changeDistrictSubscriberResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeDistrictSubscriberResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeDistrictSubscriberVO"));
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
