/**
 * ChangeID_STBDiscountResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangeID_STBDiscountResponse  implements java.io.Serializable {
    private ChangeSubscriberVO changeID_STBDiscountResult;

    public ChangeID_STBDiscountResponse() {
    }

    public ChangeID_STBDiscountResponse(
           ChangeSubscriberVO changeID_STBDiscountResult) {
           this.changeID_STBDiscountResult = changeID_STBDiscountResult;
    }


    /**
     * Gets the changeID_STBDiscountResult value for this ChangeID_STBDiscountResponse.
     * 
     * @return changeID_STBDiscountResult
     */
    public ChangeSubscriberVO getChangeID_STBDiscountResult() {
        return changeID_STBDiscountResult;
    }


    /**
     * Sets the changeID_STBDiscountResult value for this ChangeID_STBDiscountResponse.
     * 
     * @param changeID_STBDiscountResult
     */
    public void setChangeID_STBDiscountResult(ChangeSubscriberVO changeID_STBDiscountResult) {
        this.changeID_STBDiscountResult = changeID_STBDiscountResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangeID_STBDiscountResponse)) return false;
        ChangeID_STBDiscountResponse other = (ChangeID_STBDiscountResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.changeID_STBDiscountResult==null && other.getChangeID_STBDiscountResult()==null) || 
             (this.changeID_STBDiscountResult!=null &&
              this.changeID_STBDiscountResult.equals(other.getChangeID_STBDiscountResult())));
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
        if (getChangeID_STBDiscountResult() != null) {
            _hashCode += getChangeID_STBDiscountResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeID_STBDiscountResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ChangeID_STBDiscountResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changeID_STBDiscountResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeID_STBDiscountResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeSubscriberVO"));
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
