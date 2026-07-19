/**
 * StbInfoVO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class StbInfoVO  implements java.io.Serializable {
    private String nhaCungCap;

    private String ngaySX;

    private String ngayHetHanBH;

    private String MAC;

    private String loai;

    private String chungLoai;

    private String serial;

    private java.util.Calendar responseTime;

    private int result;

    private String errorDesc;

    public StbInfoVO() {
    }

    public StbInfoVO(
           String nhaCungCap,
           String ngaySX,
           String ngayHetHanBH,
           String MAC,
           String loai,
           String chungLoai,
           String serial,
           java.util.Calendar responseTime,
           int result,
           String errorDesc) {
           this.nhaCungCap = nhaCungCap;
           this.ngaySX = ngaySX;
           this.ngayHetHanBH = ngayHetHanBH;
           this.MAC = MAC;
           this.loai = loai;
           this.chungLoai = chungLoai;
           this.serial = serial;
           this.responseTime = responseTime;
           this.result = result;
           this.errorDesc = errorDesc;
    }


    /**
     * Gets the nhaCungCap value for this StbInfoVO.
     * 
     * @return nhaCungCap
     */
    public String getNhaCungCap() {
        return nhaCungCap;
    }


    /**
     * Sets the nhaCungCap value for this StbInfoVO.
     * 
     * @param nhaCungCap
     */
    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }


    /**
     * Gets the ngaySX value for this StbInfoVO.
     * 
     * @return ngaySX
     */
    public String getNgaySX() {
        return ngaySX;
    }


    /**
     * Sets the ngaySX value for this StbInfoVO.
     * 
     * @param ngaySX
     */
    public void setNgaySX(String ngaySX) {
        this.ngaySX = ngaySX;
    }


    /**
     * Gets the ngayHetHanBH value for this StbInfoVO.
     * 
     * @return ngayHetHanBH
     */
    public String getNgayHetHanBH() {
        return ngayHetHanBH;
    }


    /**
     * Sets the ngayHetHanBH value for this StbInfoVO.
     * 
     * @param ngayHetHanBH
     */
    public void setNgayHetHanBH(String ngayHetHanBH) {
        this.ngayHetHanBH = ngayHetHanBH;
    }


    /**
     * Gets the MAC value for this StbInfoVO.
     * 
     * @return MAC
     */
    public String getMAC() {
        return MAC;
    }


    /**
     * Sets the MAC value for this StbInfoVO.
     * 
     * @param MAC
     */
    public void setMAC(String MAC) {
        this.MAC = MAC;
    }


    /**
     * Gets the loai value for this StbInfoVO.
     * 
     * @return loai
     */
    public String getLoai() {
        return loai;
    }


    /**
     * Sets the loai value for this StbInfoVO.
     * 
     * @param loai
     */
    public void setLoai(String loai) {
        this.loai = loai;
    }


    /**
     * Gets the chungLoai value for this StbInfoVO.
     * 
     * @return chungLoai
     */
    public String getChungLoai() {
        return chungLoai;
    }


    /**
     * Sets the chungLoai value for this StbInfoVO.
     * 
     * @param chungLoai
     */
    public void setChungLoai(String chungLoai) {
        this.chungLoai = chungLoai;
    }


    /**
     * Gets the serial value for this StbInfoVO.
     * 
     * @return serial
     */
    public String getSerial() {
        return serial;
    }


    /**
     * Sets the serial value for this StbInfoVO.
     * 
     * @param serial
     */
    public void setSerial(String serial) {
        this.serial = serial;
    }


    /**
     * Gets the responseTime value for this StbInfoVO.
     * 
     * @return responseTime
     */
    public java.util.Calendar getResponseTime() {
        return responseTime;
    }


    /**
     * Sets the responseTime value for this StbInfoVO.
     * 
     * @param responseTime
     */
    public void setResponseTime(java.util.Calendar responseTime) {
        this.responseTime = responseTime;
    }


    /**
     * Gets the result value for this StbInfoVO.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this StbInfoVO.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the errorDesc value for this StbInfoVO.
     * 
     * @return errorDesc
     */
    public String getErrorDesc() {
        return errorDesc;
    }


    /**
     * Sets the errorDesc value for this StbInfoVO.
     * 
     * @param errorDesc
     */
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof StbInfoVO)) return false;
        StbInfoVO other = (StbInfoVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nhaCungCap==null && other.getNhaCungCap()==null) || 
             (this.nhaCungCap!=null &&
              this.nhaCungCap.equals(other.getNhaCungCap()))) &&
            ((this.ngaySX==null && other.getNgaySX()==null) || 
             (this.ngaySX!=null &&
              this.ngaySX.equals(other.getNgaySX()))) &&
            ((this.ngayHetHanBH==null && other.getNgayHetHanBH()==null) || 
             (this.ngayHetHanBH!=null &&
              this.ngayHetHanBH.equals(other.getNgayHetHanBH()))) &&
            ((this.MAC==null && other.getMAC()==null) || 
             (this.MAC!=null &&
              this.MAC.equals(other.getMAC()))) &&
            ((this.loai==null && other.getLoai()==null) || 
             (this.loai!=null &&
              this.loai.equals(other.getLoai()))) &&
            ((this.chungLoai==null && other.getChungLoai()==null) || 
             (this.chungLoai!=null &&
              this.chungLoai.equals(other.getChungLoai()))) &&
            ((this.serial==null && other.getSerial()==null) || 
             (this.serial!=null &&
              this.serial.equals(other.getSerial()))) &&
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
        if (getNhaCungCap() != null) {
            _hashCode += getNhaCungCap().hashCode();
        }
        if (getNgaySX() != null) {
            _hashCode += getNgaySX().hashCode();
        }
        if (getNgayHetHanBH() != null) {
            _hashCode += getNgayHetHanBH().hashCode();
        }
        if (getMAC() != null) {
            _hashCode += getMAC().hashCode();
        }
        if (getLoai() != null) {
            _hashCode += getLoai().hashCode();
        }
        if (getChungLoai() != null) {
            _hashCode += getChungLoai().hashCode();
        }
        if (getSerial() != null) {
            _hashCode += getSerial().hashCode();
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
        new org.apache.axis.description.TypeDesc(StbInfoVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "StbInfoVO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nhaCungCap");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "NhaCungCap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ngaySX");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "NgaySX"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ngayHetHanBH");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "NgayHetHanBH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MAC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "MAC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loai");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Loai"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chungLoai");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ChungLoai"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serial");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Serial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
