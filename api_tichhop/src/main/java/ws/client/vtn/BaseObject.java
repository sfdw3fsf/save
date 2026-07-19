/**
 * BaseObject.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vtn;

public class BaseObject  implements java.io.Serializable {
    private int OBJECT_NEW;

    private int OBJECT_CHANGE;

    private int OBJECT_DELETE;

    public BaseObject() {
    }

    public BaseObject(
           int OBJECT_NEW,
           int OBJECT_CHANGE,
           int OBJECT_DELETE) {
           this.OBJECT_NEW = OBJECT_NEW;
           this.OBJECT_CHANGE = OBJECT_CHANGE;
           this.OBJECT_DELETE = OBJECT_DELETE;
    }


    /**
     * Gets the OBJECT_NEW value for this BaseObject.
     * 
     * @return OBJECT_NEW
     */
    public int getOBJECT_NEW() {
        return OBJECT_NEW;
    }


    /**
     * Sets the OBJECT_NEW value for this BaseObject.
     * 
     * @param OBJECT_NEW
     */
    public void setOBJECT_NEW(int OBJECT_NEW) {
        this.OBJECT_NEW = OBJECT_NEW;
    }


    /**
     * Gets the OBJECT_CHANGE value for this BaseObject.
     * 
     * @return OBJECT_CHANGE
     */
    public int getOBJECT_CHANGE() {
        return OBJECT_CHANGE;
    }


    /**
     * Sets the OBJECT_CHANGE value for this BaseObject.
     * 
     * @param OBJECT_CHANGE
     */
    public void setOBJECT_CHANGE(int OBJECT_CHANGE) {
        this.OBJECT_CHANGE = OBJECT_CHANGE;
    }


    /**
     * Gets the OBJECT_DELETE value for this BaseObject.
     * 
     * @return OBJECT_DELETE
     */
    public int getOBJECT_DELETE() {
        return OBJECT_DELETE;
    }


    /**
     * Sets the OBJECT_DELETE value for this BaseObject.
     * 
     * @param OBJECT_DELETE
     */
    public void setOBJECT_DELETE(int OBJECT_DELETE) {
        this.OBJECT_DELETE = OBJECT_DELETE;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof BaseObject)) return false;
        BaseObject other = (BaseObject) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.OBJECT_NEW == other.getOBJECT_NEW() &&
            this.OBJECT_CHANGE == other.getOBJECT_CHANGE() &&
            this.OBJECT_DELETE == other.getOBJECT_DELETE();
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
        _hashCode += getOBJECT_NEW();
        _hashCode += getOBJECT_CHANGE();
        _hashCode += getOBJECT_DELETE();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BaseObject.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.crm.telsoft/", "baseObject"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OBJECT_NEW");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OBJECT_NEW"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OBJECT_CHANGE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OBJECT_CHANGE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OBJECT_DELETE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OBJECT_DELETE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
