/**
 * SuspendSubscriberV2Response.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class SuspendSubscriberV2Response  implements java.io.Serializable {
    private SuspendSubscriberVO suspendSubscriberV2Result;

    public SuspendSubscriberV2Response() {
    }

    public SuspendSubscriberV2Response(
           SuspendSubscriberVO suspendSubscriberV2Result) {
           this.suspendSubscriberV2Result = suspendSubscriberV2Result;
    }


    /**
     * Gets the suspendSubscriberV2Result value for this SuspendSubscriberV2Response.
     * 
     * @return suspendSubscriberV2Result
     */
    public SuspendSubscriberVO getSuspendSubscriberV2Result() {
        return suspendSubscriberV2Result;
    }


    /**
     * Sets the suspendSubscriberV2Result value for this SuspendSubscriberV2Response.
     * 
     * @param suspendSubscriberV2Result
     */
    public void setSuspendSubscriberV2Result(SuspendSubscriberVO suspendSubscriberV2Result) {
        this.suspendSubscriberV2Result = suspendSubscriberV2Result;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof SuspendSubscriberV2Response)) return false;
        SuspendSubscriberV2Response other = (SuspendSubscriberV2Response) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.suspendSubscriberV2Result==null && other.getSuspendSubscriberV2Result()==null) || 
             (this.suspendSubscriberV2Result!=null &&
              this.suspendSubscriberV2Result.equals(other.getSuspendSubscriberV2Result())));
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
        if (getSuspendSubscriberV2Result() != null) {
            _hashCode += getSuspendSubscriberV2Result().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SuspendSubscriberV2Response.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">SuspendSubscriberV2Response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suspendSubscriberV2Result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SuspendSubscriberV2Result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "SuspendSubscriberVO"));
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
