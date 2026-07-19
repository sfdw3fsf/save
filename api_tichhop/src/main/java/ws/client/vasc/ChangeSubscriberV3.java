/**
 * ChangeSubscriberV3.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangeSubscriberV3  implements java.io.Serializable {
    private ChangeSubscriberRequest objRequset;

    public ChangeSubscriberV3() {
    }

    public ChangeSubscriberV3(
           ChangeSubscriberRequest objRequset) {
           this.objRequset = objRequset;
    }


    /**
     * Gets the objRequset value for this ChangeSubscriberV3.
     * 
     * @return objRequset
     */
    public ChangeSubscriberRequest getObjRequset() {
        return objRequset;
    }


    /**
     * Sets the objRequset value for this ChangeSubscriberV3.
     * 
     * @param objRequset
     */
    public void setObjRequset(ChangeSubscriberRequest objRequset) {
        this.objRequset = objRequset;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangeSubscriberV3)) return false;
        ChangeSubscriberV3 other = (ChangeSubscriberV3) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.objRequset==null && other.getObjRequset()==null) || 
             (this.objRequset!=null &&
              this.objRequset.equals(other.getObjRequset())));
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
        if (getObjRequset() != null) {
            _hashCode += getObjRequset().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeSubscriberV3.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ChangeSubscriberV3"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("objRequset");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "objRequset"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeSubscriberRequest"));
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
