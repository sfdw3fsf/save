/**
 * SubInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.subman;

public class SubInfo  implements java.io.Serializable {
    private String soMay;

    private int tongDai;

    private String tenTongDai;

    private String viTri;

    private String thongBao;

    private SubStatus trangThai;

    private int[] DSDichVu;

    private String[] tenDichVu;

    public SubInfo() {
    }

    public SubInfo(
           String soMay,
           int tongDai,
           String tenTongDai,
           String viTri,
           String thongBao,
           SubStatus trangThai,
           int[] DSDichVu,
           String[] tenDichVu) {
           this.soMay = soMay;
           this.tongDai = tongDai;
           this.tenTongDai = tenTongDai;
           this.viTri = viTri;
           this.thongBao = thongBao;
           this.trangThai = trangThai;
           this.DSDichVu = DSDichVu;
           this.tenDichVu = tenDichVu;
    }


    /**
     * Gets the soMay value for this SubInfo.
     * 
     * @return soMay
     */
    public String getSoMay() {
        return soMay;
    }


    /**
     * Sets the soMay value for this SubInfo.
     * 
     * @param soMay
     */
    public void setSoMay(String soMay) {
        this.soMay = soMay;
    }


    /**
     * Gets the tongDai value for this SubInfo.
     * 
     * @return tongDai
     */
    public int getTongDai() {
        return tongDai;
    }


    /**
     * Sets the tongDai value for this SubInfo.
     * 
     * @param tongDai
     */
    public void setTongDai(int tongDai) {
        this.tongDai = tongDai;
    }


    /**
     * Gets the tenTongDai value for this SubInfo.
     * 
     * @return tenTongDai
     */
    public String getTenTongDai() {
        return tenTongDai;
    }


    /**
     * Sets the tenTongDai value for this SubInfo.
     * 
     * @param tenTongDai
     */
    public void setTenTongDai(String tenTongDai) {
        this.tenTongDai = tenTongDai;
    }


    /**
     * Gets the viTri value for this SubInfo.
     * 
     * @return viTri
     */
    public String getViTri() {
        return viTri;
    }


    /**
     * Sets the viTri value for this SubInfo.
     * 
     * @param viTri
     */
    public void setViTri(String viTri) {
        this.viTri = viTri;
    }


    /**
     * Gets the thongBao value for this SubInfo.
     * 
     * @return thongBao
     */
    public String getThongBao() {
        return thongBao;
    }


    /**
     * Sets the thongBao value for this SubInfo.
     * 
     * @param thongBao
     */
    public void setThongBao(String thongBao) {
        this.thongBao = thongBao;
    }


    /**
     * Gets the trangThai value for this SubInfo.
     * 
     * @return trangThai
     */
    public SubStatus getTrangThai() {
        return trangThai;
    }


    /**
     * Sets the trangThai value for this SubInfo.
     * 
     * @param trangThai
     */
    public void setTrangThai(SubStatus trangThai) {
        this.trangThai = trangThai;
    }


    /**
     * Gets the DSDichVu value for this SubInfo.
     * 
     * @return DSDichVu
     */
    public int[] getDSDichVu() {
        return DSDichVu;
    }


    /**
     * Sets the DSDichVu value for this SubInfo.
     * 
     * @param DSDichVu
     */
    public void setDSDichVu(int[] DSDichVu) {
        this.DSDichVu = DSDichVu;
    }


    /**
     * Gets the tenDichVu value for this SubInfo.
     * 
     * @return tenDichVu
     */
    public String[] getTenDichVu() {
        return tenDichVu;
    }


    /**
     * Sets the tenDichVu value for this SubInfo.
     * 
     * @param tenDichVu
     */
    public void setTenDichVu(String[] tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof SubInfo)) return false;
        SubInfo other = (SubInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.soMay==null && other.getSoMay()==null) || 
             (this.soMay!=null &&
              this.soMay.equals(other.getSoMay()))) &&
            this.tongDai == other.getTongDai() &&
            ((this.tenTongDai==null && other.getTenTongDai()==null) || 
             (this.tenTongDai!=null &&
              this.tenTongDai.equals(other.getTenTongDai()))) &&
            ((this.viTri==null && other.getViTri()==null) || 
             (this.viTri!=null &&
              this.viTri.equals(other.getViTri()))) &&
            ((this.thongBao==null && other.getThongBao()==null) || 
             (this.thongBao!=null &&
              this.thongBao.equals(other.getThongBao()))) &&
            ((this.trangThai==null && other.getTrangThai()==null) || 
             (this.trangThai!=null &&
              this.trangThai.equals(other.getTrangThai()))) &&
            ((this.DSDichVu==null && other.getDSDichVu()==null) || 
             (this.DSDichVu!=null &&
              java.util.Arrays.equals(this.DSDichVu, other.getDSDichVu()))) &&
            ((this.tenDichVu==null && other.getTenDichVu()==null) || 
             (this.tenDichVu!=null &&
              java.util.Arrays.equals(this.tenDichVu, other.getTenDichVu())));
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
        if (getSoMay() != null) {
            _hashCode += getSoMay().hashCode();
        }
        _hashCode += getTongDai();
        if (getTenTongDai() != null) {
            _hashCode += getTenTongDai().hashCode();
        }
        if (getViTri() != null) {
            _hashCode += getViTri().hashCode();
        }
        if (getThongBao() != null) {
            _hashCode += getThongBao().hashCode();
        }
        if (getTrangThai() != null) {
            _hashCode += getTrangThai().hashCode();
        }
        if (getDSDichVu() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDSDichVu());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getDSDichVu(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTenDichVu() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTenDichVu());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getTenDichVu(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SubInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "SubInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soMay");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SoMay"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tongDai");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "TongDai"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tenTongDai");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "TenTongDai"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("viTri");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ViTri"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("thongBao");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ThongBao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trangThai");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "TrangThai"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "SubStatus"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DSDichVu");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DSDichVu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://tempuri.org/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tenDichVu");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "TenDichVu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://tempuri.org/", "string"));
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
