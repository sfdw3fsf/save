/**
 * GetHisPayTVResp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetHisPayTVResp  implements java.io.Serializable {
    private GetHisPayTVRespDataKplus dataKplus;

    private GetHisPayTVRespDataVTVcab dataVTVcab;

    private GetHisPayTVRespDataHBO dataHBO;

    private GetHisPayTVRespDataFafilm dataFafilm;

    private java.util.Calendar responseTime;

    private int result;

    private String errorDesc;

    public GetHisPayTVResp() {
    }

    public GetHisPayTVResp(
           GetHisPayTVRespDataKplus dataKplus,
           GetHisPayTVRespDataVTVcab dataVTVcab,
           GetHisPayTVRespDataHBO dataHBO,
           GetHisPayTVRespDataFafilm dataFafilm,
           java.util.Calendar responseTime,
           int result,
           String errorDesc) {
           this.dataKplus = dataKplus;
           this.dataVTVcab = dataVTVcab;
           this.dataHBO = dataHBO;
           this.dataFafilm = dataFafilm;
           this.responseTime = responseTime;
           this.result = result;
           this.errorDesc = errorDesc;
    }


    /**
     * Gets the dataKplus value for this GetHisPayTVResp.
     * 
     * @return dataKplus
     */
    public GetHisPayTVRespDataKplus getDataKplus() {
        return dataKplus;
    }


    /**
     * Sets the dataKplus value for this GetHisPayTVResp.
     * 
     * @param dataKplus
     */
    public void setDataKplus(GetHisPayTVRespDataKplus dataKplus) {
        this.dataKplus = dataKplus;
    }


    /**
     * Gets the dataVTVcab value for this GetHisPayTVResp.
     * 
     * @return dataVTVcab
     */
    public GetHisPayTVRespDataVTVcab getDataVTVcab() {
        return dataVTVcab;
    }


    /**
     * Sets the dataVTVcab value for this GetHisPayTVResp.
     * 
     * @param dataVTVcab
     */
    public void setDataVTVcab(GetHisPayTVRespDataVTVcab dataVTVcab) {
        this.dataVTVcab = dataVTVcab;
    }


    /**
     * Gets the dataHBO value for this GetHisPayTVResp.
     * 
     * @return dataHBO
     */
    public GetHisPayTVRespDataHBO getDataHBO() {
        return dataHBO;
    }


    /**
     * Sets the dataHBO value for this GetHisPayTVResp.
     * 
     * @param dataHBO
     */
    public void setDataHBO(GetHisPayTVRespDataHBO dataHBO) {
        this.dataHBO = dataHBO;
    }


    /**
     * Gets the dataFafilm value for this GetHisPayTVResp.
     * 
     * @return dataFafilm
     */
    public GetHisPayTVRespDataFafilm getDataFafilm() {
        return dataFafilm;
    }


    /**
     * Sets the dataFafilm value for this GetHisPayTVResp.
     * 
     * @param dataFafilm
     */
    public void setDataFafilm(GetHisPayTVRespDataFafilm dataFafilm) {
        this.dataFafilm = dataFafilm;
    }


    /**
     * Gets the responseTime value for this GetHisPayTVResp.
     * 
     * @return responseTime
     */
    public java.util.Calendar getResponseTime() {
        return responseTime;
    }


    /**
     * Sets the responseTime value for this GetHisPayTVResp.
     * 
     * @param responseTime
     */
    public void setResponseTime(java.util.Calendar responseTime) {
        this.responseTime = responseTime;
    }


    /**
     * Gets the result value for this GetHisPayTVResp.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this GetHisPayTVResp.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the errorDesc value for this GetHisPayTVResp.
     * 
     * @return errorDesc
     */
    public String getErrorDesc() {
        return errorDesc;
    }


    /**
     * Sets the errorDesc value for this GetHisPayTVResp.
     * 
     * @param errorDesc
     */
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetHisPayTVResp)) return false;
        GetHisPayTVResp other = (GetHisPayTVResp) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dataKplus==null && other.getDataKplus()==null) || 
             (this.dataKplus!=null &&
              this.dataKplus.equals(other.getDataKplus()))) &&
            ((this.dataVTVcab==null && other.getDataVTVcab()==null) || 
             (this.dataVTVcab!=null &&
              this.dataVTVcab.equals(other.getDataVTVcab()))) &&
            ((this.dataHBO==null && other.getDataHBO()==null) || 
             (this.dataHBO!=null &&
              this.dataHBO.equals(other.getDataHBO()))) &&
            ((this.dataFafilm==null && other.getDataFafilm()==null) || 
             (this.dataFafilm!=null &&
              this.dataFafilm.equals(other.getDataFafilm()))) &&
            ((this.responseTime==null && other.getResponseTime()==null) || 
             (this.responseTime!=null &&
              this.responseTime.equals(other.getResponseTime()))) &&
            this.result == other.getResult() &&
            ((this.errorDesc==null && other.getErrorDesc()==null) || 
             (this.errorDesc!=null &&
              this.errorDesc.equals(other.getErrorDesc())));
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
        if (getDataKplus() != null) {
            _hashCode += getDataKplus().hashCode();
        }
        if (getDataVTVcab() != null) {
            _hashCode += getDataVTVcab().hashCode();
        }
        if (getDataHBO() != null) {
            _hashCode += getDataHBO().hashCode();
        }
        if (getDataFafilm() != null) {
            _hashCode += getDataFafilm().hashCode();
        }
        if (getResponseTime() != null) {
            _hashCode += getResponseTime().hashCode();
        }
        _hashCode += getResult();
        if (getErrorDesc() != null) {
            _hashCode += getErrorDesc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetHisPayTVResp.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "GetHisPayTVResp"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataKplus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DataKplus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetHisPayTVResp>DataKplus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataVTVcab");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DataVTVcab"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetHisPayTVResp>DataVTVcab"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataHBO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DataHBO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetHisPayTVResp>DataHBO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFafilm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DataFafilm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetHisPayTVResp>DataFafilm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ResponseTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ErrorDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
