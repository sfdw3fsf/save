/**
 * ChangeSubscriberV2Response.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangeSubscriberV2Response  implements java.io.Serializable {
    private ChangeSubscriberVO changeSubscriberV2Result;

    public ChangeSubscriberV2Response() {
    }

    public ChangeSubscriberV2Response(
           ChangeSubscriberVO changeSubscriberV2Result) {
           this.changeSubscriberV2Result = changeSubscriberV2Result;
    }


    /**
     * Gets the changeSubscriberV2Result value for this ChangeSubscriberV2Response.
     * 
     * @return changeSubscriberV2Result
     */
    public ChangeSubscriberVO getChangeSubscriberV2Result() {
        return changeSubscriberV2Result;
    }


    /**
     * Sets the changeSubscriberV2Result value for this ChangeSubscriberV2Response.
     * 
     * @param changeSubscriberV2Result
     */
    public void setChangeSubscriberV2Result(ChangeSubscriberVO changeSubscriberV2Result) {
        this.changeSubscriberV2Result = changeSubscriberV2Result;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangeSubscriberV2Response)) return false;
        ChangeSubscriberV2Response other = (ChangeSubscriberV2Response) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.changeSubscriberV2Result==null && other.getChangeSubscriberV2Result()==null) || 
             (this.changeSubscriberV2Result!=null &&
              this.changeSubscriberV2Result.equals(other.getChangeSubscriberV2Result())));
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
        if (getChangeSubscriberV2Result() != null) {
            _hashCode += getChangeSubscriberV2Result().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeSubscriberV2Response.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ChangeSubscriberV2Response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changeSubscriberV2Result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeSubscriberV2Result"));
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
