/**
 * Paginated.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.application;

public class Paginated  implements java.io.Serializable {
    private int PAGE_SIZE;

    private Object comparator;

    private int currentPage;

    private Object[] data;

    private int endRow;

    private int index;

    private int maxPage;

    private int maxRow;

    private int startRow;

    public Paginated() {
    }

    public Paginated(
           int PAGE_SIZE,
           Object comparator,
           int currentPage,
           Object[] data,
           int endRow,
           int index,
           int maxPage,
           int maxRow,
           int startRow) {
           this.PAGE_SIZE = PAGE_SIZE;
           this.comparator = comparator;
           this.currentPage = currentPage;
           this.data = data;
           this.endRow = endRow;
           this.index = index;
           this.maxPage = maxPage;
           this.maxRow = maxRow;
           this.startRow = startRow;
    }


    /**
     * Gets the PAGE_SIZE value for this Paginated.
     * 
     * @return PAGE_SIZE
     */
    public int getPAGE_SIZE() {
        return PAGE_SIZE;
    }


    /**
     * Sets the PAGE_SIZE value for this Paginated.
     * 
     * @param PAGE_SIZE
     */
    public void setPAGE_SIZE(int PAGE_SIZE) {
        this.PAGE_SIZE = PAGE_SIZE;
    }


    /**
     * Gets the comparator value for this Paginated.
     * 
     * @return comparator
     */
    public Object getComparator() {
        return comparator;
    }


    /**
     * Sets the comparator value for this Paginated.
     * 
     * @param comparator
     */
    public void setComparator(Object comparator) {
        this.comparator = comparator;
    }


    /**
     * Gets the currentPage value for this Paginated.
     * 
     * @return currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }


    /**
     * Sets the currentPage value for this Paginated.
     * 
     * @param currentPage
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }


    /**
     * Gets the data value for this Paginated.
     * 
     * @return data
     */
    public Object[] getData() {
        return data;
    }


    /**
     * Sets the data value for this Paginated.
     * 
     * @param data
     */
    public void setData(Object[] data) {
        this.data = data;
    }


    /**
     * Gets the endRow value for this Paginated.
     * 
     * @return endRow
     */
    public int getEndRow() {
        return endRow;
    }


    /**
     * Sets the endRow value for this Paginated.
     * 
     * @param endRow
     */
    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }


    /**
     * Gets the index value for this Paginated.
     * 
     * @return index
     */
    public int getIndex() {
        return index;
    }


    /**
     * Sets the index value for this Paginated.
     * 
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
    }


    /**
     * Gets the maxPage value for this Paginated.
     * 
     * @return maxPage
     */
    public int getMaxPage() {
        return maxPage;
    }


    /**
     * Sets the maxPage value for this Paginated.
     * 
     * @param maxPage
     */
    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }


    /**
     * Gets the maxRow value for this Paginated.
     * 
     * @return maxRow
     */
    public int getMaxRow() {
        return maxRow;
    }


    /**
     * Sets the maxRow value for this Paginated.
     * 
     * @param maxRow
     */
    public void setMaxRow(int maxRow) {
        this.maxRow = maxRow;
    }


    /**
     * Gets the startRow value for this Paginated.
     * 
     * @return startRow
     */
    public int getStartRow() {
        return startRow;
    }


    /**
     * Sets the startRow value for this Paginated.
     * 
     * @param startRow
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Paginated)) return false;
        Paginated other = (Paginated) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.PAGE_SIZE == other.getPAGE_SIZE() &&
            ((this.comparator==null && other.getComparator()==null) || 
             (this.comparator!=null &&
              this.comparator.equals(other.getComparator()))) &&
            this.currentPage == other.getCurrentPage() &&
            ((this.data==null && other.getData()==null) || 
             (this.data!=null &&
              java.util.Arrays.equals(this.data, other.getData()))) &&
            this.endRow == other.getEndRow() &&
            this.index == other.getIndex() &&
            this.maxPage == other.getMaxPage() &&
            this.maxRow == other.getMaxRow() &&
            this.startRow == other.getStartRow();
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
        _hashCode += getPAGE_SIZE();
        if (getComparator() != null) {
            _hashCode += getComparator().hashCode();
        }
        _hashCode += getCurrentPage();
        if (getData() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getData());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getData(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getEndRow();
        _hashCode += getIndex();
        _hashCode += getMaxPage();
        _hashCode += getMaxRow();
        _hashCode += getStartRow();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Paginated.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:Paginated.utils.hyframe.hyperlogy", "Paginated"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PAGE_SIZE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PAGE_SIZE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comparator");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comparator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentPage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "currentPage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("data");
        elemField.setXmlName(new javax.xml.namespace.QName("", "data"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endRow");
        elemField.setXmlName(new javax.xml.namespace.QName("", "endRow"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("index");
        elemField.setXmlName(new javax.xml.namespace.QName("", "index"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxPage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maxPage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxRow");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maxRow"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startRow");
        elemField.setXmlName(new javax.xml.namespace.QName("", "startRow"));
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
