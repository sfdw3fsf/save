/**
 * BlockPayTVResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class BlockPayTVResponse  implements java.io.Serializable {
    private BlockPayTVResp blockPayTVResult;

    public BlockPayTVResponse() {
    }

    public BlockPayTVResponse(
           BlockPayTVResp blockPayTVResult) {
           this.blockPayTVResult = blockPayTVResult;
    }


    /**
     * Gets the blockPayTVResult value for this BlockPayTVResponse.
     * 
     * @return blockPayTVResult
     */
    public BlockPayTVResp getBlockPayTVResult() {
        return blockPayTVResult;
    }


    /**
     * Sets the blockPayTVResult value for this BlockPayTVResponse.
     * 
     * @param blockPayTVResult
     */
    public void setBlockPayTVResult(BlockPayTVResp blockPayTVResult) {
        this.blockPayTVResult = blockPayTVResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof BlockPayTVResponse)) return false;
        BlockPayTVResponse other = (BlockPayTVResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.blockPayTVResult==null && other.getBlockPayTVResult()==null) || 
             (this.blockPayTVResult!=null &&
              this.blockPayTVResult.equals(other.getBlockPayTVResult())));
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
        if (getBlockPayTVResult() != null) {
            _hashCode += getBlockPayTVResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BlockPayTVResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">BlockPayTVResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blockPayTVResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "BlockPayTVResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "BlockPayTVResp"));
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
