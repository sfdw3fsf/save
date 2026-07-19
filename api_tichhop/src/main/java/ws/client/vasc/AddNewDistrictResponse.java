/**
 * AddNewDistrictResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class AddNewDistrictResponse  implements java.io.Serializable {
    private DistrictVO addNewDistrictResult;

    public AddNewDistrictResponse() {
    }

    public AddNewDistrictResponse(
           DistrictVO addNewDistrictResult) {
           this.addNewDistrictResult = addNewDistrictResult;
    }


    /**
     * Gets the addNewDistrictResult value for this AddNewDistrictResponse.
     * 
     * @return addNewDistrictResult
     */
    public DistrictVO getAddNewDistrictResult() {
        return addNewDistrictResult;
    }


    /**
     * Sets the addNewDistrictResult value for this AddNewDistrictResponse.
     * 
     * @param addNewDistrictResult
     */
    public void setAddNewDistrictResult(DistrictVO addNewDistrictResult) {
        this.addNewDistrictResult = addNewDistrictResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof AddNewDistrictResponse)) return false;
        AddNewDistrictResponse other = (AddNewDistrictResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.addNewDistrictResult==null && other.getAddNewDistrictResult()==null) || 
             (this.addNewDistrictResult!=null &&
              this.addNewDistrictResult.equals(other.getAddNewDistrictResult())));
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
        if (getAddNewDistrictResult() != null) {
            _hashCode += getAddNewDistrictResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AddNewDistrictResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">AddNewDistrictResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addNewDistrictResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "AddNewDistrictResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "DistrictVO"));
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
