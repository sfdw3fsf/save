/**
 * ChangeSubscriberV3Response.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangeSubscriberV3Response  implements java.io.Serializable {
    private ChangeSubscriberVO changeSubscriberV3Result;

    public ChangeSubscriberV3Response() {
    }

    public ChangeSubscriberV3Response(
           ChangeSubscriberVO changeSubscriberV3Result) {
           this.changeSubscriberV3Result = changeSubscriberV3Result;
    }


    /**
     * Gets the changeSubscriberV3Result value for this ChangeSubscriberV3Response.
     * 
     * @return changeSubscriberV3Result
     */
    public ChangeSubscriberVO getChangeSubscriberV3Result() {
        return changeSubscriberV3Result;
    }


    /**
     * Sets the changeSubscriberV3Result value for this ChangeSubscriberV3Response.
     * 
     * @param changeSubscriberV3Result
     */
    public void setChangeSubscriberV3Result(ChangeSubscriberVO changeSubscriberV3Result) {
        this.changeSubscriberV3Result = changeSubscriberV3Result;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangeSubscriberV3Response)) return false;
        ChangeSubscriberV3Response other = (ChangeSubscriberV3Response) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.changeSubscriberV3Result==null && other.getChangeSubscriberV3Result()==null) || 
             (this.changeSubscriberV3Result!=null &&
              this.changeSubscriberV3Result.equals(other.getChangeSubscriberV3Result())));
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
        if (getChangeSubscriberV3Result() != null) {
            _hashCode += getChangeSubscriberV3Result().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeSubscriberV3Response.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ChangeSubscriberV3Response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changeSubscriberV3Result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeSubscriberV3Result"));
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
