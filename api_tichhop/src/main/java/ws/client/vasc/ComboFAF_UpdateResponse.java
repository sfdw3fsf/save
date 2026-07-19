/**
 * ComboFAF_UpdateResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ComboFAF_UpdateResponse  implements java.io.Serializable {
    private ChangeSubscriberVO comboFAF_UpdateResult;

    public ComboFAF_UpdateResponse() {
    }

    public ComboFAF_UpdateResponse(
           ChangeSubscriberVO comboFAF_UpdateResult) {
           this.comboFAF_UpdateResult = comboFAF_UpdateResult;
    }


    /**
     * Gets the comboFAF_UpdateResult value for this ComboFAF_UpdateResponse.
     * 
     * @return comboFAF_UpdateResult
     */
    public ChangeSubscriberVO getComboFAF_UpdateResult() {
        return comboFAF_UpdateResult;
    }


    /**
     * Sets the comboFAF_UpdateResult value for this ComboFAF_UpdateResponse.
     * 
     * @param comboFAF_UpdateResult
     */
    public void setComboFAF_UpdateResult(ChangeSubscriberVO comboFAF_UpdateResult) {
        this.comboFAF_UpdateResult = comboFAF_UpdateResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ComboFAF_UpdateResponse)) return false;
        ComboFAF_UpdateResponse other = (ComboFAF_UpdateResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.comboFAF_UpdateResult==null && other.getComboFAF_UpdateResult()==null) || 
             (this.comboFAF_UpdateResult!=null &&
              this.comboFAF_UpdateResult.equals(other.getComboFAF_UpdateResult())));
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
        if (getComboFAF_UpdateResult() != null) {
            _hashCode += getComboFAF_UpdateResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ComboFAF_UpdateResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ComboFAF_UpdateResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comboFAF_UpdateResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ComboFAF_UpdateResult"));
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
