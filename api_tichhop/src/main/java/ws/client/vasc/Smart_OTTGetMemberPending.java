/**
 * Smart_OTTGetMemberPending.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class Smart_OTTGetMemberPending  implements java.io.Serializable {
    private Smart_OTTGetMemberPendingRequest req;

    public Smart_OTTGetMemberPending() {
    }

    public Smart_OTTGetMemberPending(
           Smart_OTTGetMemberPendingRequest req) {
           this.req = req;
    }


    /**
     * Gets the req value for this Smart_OTTGetMemberPending.
     * 
     * @return req
     */
    public Smart_OTTGetMemberPendingRequest getReq() {
        return req;
    }


    /**
     * Sets the req value for this Smart_OTTGetMemberPending.
     * 
     * @param req
     */
    public void setReq(Smart_OTTGetMemberPendingRequest req) {
        this.req = req;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Smart_OTTGetMemberPending)) return false;
        Smart_OTTGetMemberPending other = (Smart_OTTGetMemberPending) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.req==null && other.getReq()==null) || 
             (this.req!=null &&
              this.req.equals(other.getReq())));
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
        if (getReq() != null) {
            _hashCode += getReq().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Smart_OTTGetMemberPending.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Smart_OTTGetMemberPending"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("req");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "req"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "Smart_OTTGetMemberPendingRequest"));
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
