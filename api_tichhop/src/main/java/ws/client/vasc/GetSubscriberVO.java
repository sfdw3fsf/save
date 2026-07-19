/**
 * GetSubscriberVO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetSubscriberVO  implements java.io.Serializable {
    private int result;

    private String errordesc;

    private GetSubscriberVOData data;

    public GetSubscriberVO() {
    }

    public GetSubscriberVO(
           int result,
           String errordesc,
           GetSubscriberVOData data) {
           this.result = result;
           this.errordesc = errordesc;
           this.data = data;
    }


    /**
     * Gets the result value for this GetSubscriberVO.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this GetSubscriberVO.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the errordesc value for this GetSubscriberVO.
     * 
     * @return errordesc
     */
    public String getErrordesc() {
        return errordesc;
    }


    /**
     * Sets the errordesc value for this GetSubscriberVO.
     * 
     * @param errordesc
     */
    public void setErrordesc(String errordesc) {
        this.errordesc = errordesc;
    }


    /**
     * Gets the data value for this GetSubscriberVO.
     * 
     * @return data
     */
    public GetSubscriberVOData getData() {
        return data;
    }


    /**
     * Sets the data value for this GetSubscriberVO.
     * 
     * @param data
     */
    public void setData(GetSubscriberVOData data) {
        this.data = data;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetSubscriberVO)) return false;
        GetSubscriberVO other = (GetSubscriberVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.result == other.getResult() &&
            ((this.errordesc==null && other.getErrordesc()==null) || 
             (this.errordesc!=null &&
              this.errordesc.equals(other.getErrordesc()))) &&
            ((this.data==null && other.getData()==null) || 
             (this.data!=null &&
              this.data.equals(other.getData())));
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
        _hashCode += getResult();
        if (getErrordesc() != null) {
            _hashCode += getErrordesc().hashCode();
        }
        if (getData() != null) {
            _hashCode += getData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetSubscriberVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "GetSubscriberVO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errordesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Errordesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("data");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Data"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetSubscriberVO>Data"));
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
