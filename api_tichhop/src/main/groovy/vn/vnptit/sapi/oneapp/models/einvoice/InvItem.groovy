package vn.vnptit.sapi.oneapp.models.einvoice

import vn.vnptit.sapi.oneapp.util.NumberToWord

import javax.validation.Valid

import com.fasterxml.jackson.annotation.JsonProperty

import io.swagger.annotations.ApiModelProperty

public class InvItem {

    @JsonProperty("fkey")
    private String fkey

    @JsonProperty("ma_gd")
    private String ma_gd

    @JsonProperty("mst")
    private String mst

    @JsonProperty("ma_nv")
    private String ma_nv

    @JsonProperty("vten_tt")
    private String vten_tt

    @JsonProperty("diachi_ct")
    private String diachi_ct

    @JsonProperty("vsodt")
    private String vsodt

    @JsonProperty("vMDVQHNSach")
    private String vMDVQHNSach

    @JsonProperty("vCCCDan")
    private String vCCCDan

    @JsonProperty("vHDCTTChinh")
    private String vHDCTTChinh

    @JsonProperty("products")
    @Valid
    private List<InvProduct> products = null

    /**
     * Get token
     *
     * @return token
     */
    @ApiModelProperty(value = "")
    public String getFkey() {
        return fkey
    }

    public void setFkey(String fkey) {
        this.fkey = fkey
    }

    /**
     * Get token
     *
     * @return token
     */
    @ApiModelProperty(value = "")
    public String getMa_nv() {
        return ma_nv
    }

    public void setMa_nv(String ma_nv) {
        this.ma_nv = ma_nv
    }

    /**
     * Get token
     *
     * @return token
     */
    @ApiModelProperty(value = "")
    public String getVten_tt() {
        return vten_tt
    }

    public void setVten_tt(String vten_tt) {
        this.vten_tt = vten_tt
    }

    /**
     * Get token
     *
     * @return token
     */
    @ApiModelProperty(value = "")
    public String getDiachi_ct() {
        return diachi_ct
    }

    public void setDiachi_ct(String diachi_ct) {
        this.diachi_ct = diachi_ct
    }

    /**
     * Get token
     *
     * @return token
     */
    @ApiModelProperty(value = "")
    public String getVsodt() {
        return vsodt
    }

    public void setVsodt(String vsodt) {
        this.vsodt = vsodt
    }

    /**
     * Get token
     *
     * @return token
     */
    @ApiModelProperty(value = "")
    public String getMa_gd() {
        return ma_gd
    }

    public void setMa_gd(String ma_gd) {
        this.ma_gd = ma_gd
    }

    /**
     * Get token
     *
     * @return token
     */
    @ApiModelProperty(value = "")
    public String getMst() {
        return mst
    }

    public void setMst(String mst) {
        this.mst = mst
    }

    public String getVMDVQHNSach() {
        return vMDVQHNSach
    }

    public void setVMDVQHNSach(String vMDVQHNSach) {
        this.vMDVQHNSach = vMDVQHNSach
    }

    public String getVCCCDan() {
        return vCCCDan
    }

    public void setVCCCDan(String vCCCDan) {
        this.vCCCDan = vCCCDan
    }

    public String getVHDCTTChinh() {
        return vHDCTTChinh
    }

    public void setVHDCTTChinh(String vHDCTTChinh) {
        this.vHDCTTChinh = vHDCTTChinh
    }

    /**
     * Get token
     *
     * @return token
     */
    @ApiModelProperty(value = "")
    public List<InvProduct> getProducts() {
        return products
    }

    public void setProducts(List<InvProduct> products) {
        this.products = products
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true
        }
        if (o == null || getClass() != o.getClass()) {
            return false
        }
        InvItem invItem = (InvItem) o
        return Objects.equals(this.fkey, invItem.fkey) &&
                Objects.equals(this.ma_gd, invItem.ma_gd) &&
                Objects.equals(this.mst, invItem.mst) &&
                Objects.equals(this.ma_nv, invItem.ma_nv) &&
                Objects.equals(this.vten_tt, invItem.vten_tt) &&
                Objects.equals(this.diachi_ct, invItem.diachi_ct) &&
                Objects.equals(this.vsodt, invItem.vsodt) &&
                Objects.equals(this.vMDVQHNSach, invItem.vMDVQHNSach) &&
                Objects.equals(this.vCCCDan, invItem.vCCCDan) &&
                Objects.equals(this.vHDCTTChinh, invItem.vHDCTTChinh)
    }

    @Override
    public int hashCode() {
        return Objects.hash(fkey, ma_gd, mst, ma_nv, vten_tt, diachi_ct, vsodt, vMDVQHNSach, vCCCDan, vHDCTTChinh)
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
        sb.append("class InvItem {\n")

        sb.append("    ma_gd: ").append(toIndentedString(ma_gd)).append("\n")
        sb.append("    mst: ").append(toIndentedString(mst)).append("\n")
        sb.append("    ma_nv: ").append(toIndentedString(ma_nv)).append("\n")
        sb.append("    vten_tt: ").append(toIndentedString(vten_tt)).append("\n")
        sb.append("    diachi_ct: ").append(toIndentedString(diachi_ct)).append("\n")
        sb.append("    vsodt: ").append(toIndentedString(vsodt)).append("\n")

        sb.append("}")
        return sb.toString()
    }

    public String toXml78() {
        StringBuilder str = new StringBuilder()
        str.append("<Invoices>")
        str.append("<Inv>")
        str.append("<key>$fkey</key>")
        str.append("<Invoice>")
        str.append("<CusCode>$ma_gd</CusCode>")
        str.append("<CusName><![CDATA[$vten_tt]]></CusName>")

        if (vMDVQHNSach) {
            str.append("<MDVQHNSach><![CDATA[$vMDVQHNSach]]></MDVQHNSach>")
        }
        if (vCCCDan) {
            str.append("<CCCDan><![CDATA[$vCCCDan]]></CCCDan>")
        }
        if (vHDCTTChinh) {
            str.append("<HDCTTChinh><![CDATA[$vHDCTTChinh]]></HDCTTChinh>")
        }

        str.append("<CusAddress><![CDATA[$diachi_ct]]></CusAddress>")
        str.append("<CusPhone>$vsodt</CusPhone>")
        str.append("<CusTaxCode><![CDATA[$mst]]></CusTaxCode>")
        str.append("<PaymentMethod>Tiền mặt/Chuyển khoản</PaymentMethod>")
        str.append("<KindOfService></KindOfService>")
        str.append("<Products>")

        Double tongtien_hddt = 0
        Double tongvat_hddt = 0
        Double tong_hddt = 0

        Double vat_amount = 0, vat_amount_5 = 0, vat_amount_10 = 0
        Double total = 0, total_0 = 0, total_novat_t = 0, total_novat_c = 0, total_5 = 0, total_10 = 0


        products.each { InvProduct it ->
            if ("" != it.prodName) {
                str.append(it.toXml78())
                tongtien_hddt += Double.parseDouble(it.tien)
                tongvat_hddt += Double.parseDouble(it.vat)
                tong_hddt += Double.parseDouble(it.tongTien)

                Double _VATRate = Math.abs(Double.parseDouble(it.tyLeVat))
                Double _vat_amount = Math.abs(Double.parseDouble(it.vat))

                if (_VATRate == -2) {
                    total_novat_t += tongtien_hddt
                } else if (_VATRate == -1) {
                    total_novat_c += tongtien_hddt
                } else if (_VATRate == 0) {
                    total_0 += tongtien_hddt
                } else if (_VATRate == 5) {
                    total_5 += tongtien_hddt
                    vat_amount_5 += _vat_amount
                } else if (_VATRate == 10) {
                    total_10 += tongtien_hddt
                    vat_amount_10 += _vat_amount
                }
                total += tongtien_hddt
                vat_amount += _vat_amount
            }
        }

        tong_hddt = Math.abs(tong_hddt)
        tongvat_hddt = Math.abs(tongvat_hddt)

        str.append("</Products>")
        str.append("<Total>$tongtien_hddt</Total>")
        str.append("<DiscountAmount></DiscountAmount>")
        str.append("<VATRate>10</VATRate>")
        str.append("<VATAmount>$tongvat_hddt</VATAmount>")
        str.append("<Amount>$tong_hddt</Amount>")

        String tienkt
        String tbc
        tienkt = String.valueOf(Math.floor(tong_hddt)).replace(".0", "")
        if (tong_hddt.toString().contains("-"))
            tienkt = tienkt.replace("-", "")

        tbc = NumberToWord.num2String(Long.parseLong(tienkt)).substring(0, 1).toUpperCase() + NumberToWord.num2String(Long.parseLong(tienkt)).substring(1) + " đồng."

        str.append("<AmountInWords>$tbc</AmountInWords>")
        str.append("<GrossValue_NonTax>" + total_novat_t + "</GrossValue_NonTax>")
        str.append("<GrossValue>" + total_novat_c + "</GrossValue>")
        str.append("<GrossValue0>" + total_0 + "</GrossValue0>")
        str.append("<VatAmount0>0</VatAmount0>")
        str.append("<GrossValue5>" + total_5 + "</GrossValue5>")
        str.append("<VatAmount5>" + vat_amount_5 + "</VatAmount5>")
        str.append("<GrossValue10>" + total_10 + "</GrossValue10>")
        str.append("<VatAmount10>" + vat_amount_10 + "</VatAmount10>")
        str.append("<Teller>$ma_nv</Teller>")
        str.append("</Invoice>")
        str.append("</Inv>")
        str.append("</Invoices>")

        String xml = str.toString().replaceAll("]]>", "]]]]><![CDATA[>")
        return "<![CDATA[$xml]]>".toString()
    }

    public String toXml32() {
        StringBuilder str = new StringBuilder()
        str.append("<Invoices>")
        str.append("<Inv>")
        str.append("<key>$fkey</key>")
        str.append("<Invoice>")
        str.append("<CusCode>$ma_gd</CusCode>")
        str.append("<CusName><![CDATA[$vten_tt]]></CusName>")

        str.append("<CusAddress><![CDATA[$diachi_ct]]></CusAddress>")
        str.append("<CusPhone>$vsodt</CusPhone>")
        str.append("<CusTaxCode><![CDATA[$mst]]></CusTaxCode>")
        str.append("<PaymentMethod>Tiền mặt/Chuyển khoản</PaymentMethod>")
        str.append("<KindOfService></KindOfService>")
        str.append("<Products>")

        Double tongtien_hddt = 0
        Double tongvat_hddt = 0
        Double tong_hddt = 0

        products.each { InvProduct it ->
            if ("" != it.prodName) {
                str.append(it.toXml32())
                tongtien_hddt += Double.parseDouble(it.tien)
                tongvat_hddt += Double.parseDouble(it.vat)
                tong_hddt += Double.parseDouble(it.tongTien)
            }
        }

        tong_hddt = Math.abs(tong_hddt)
        tongvat_hddt = Math.abs(tongvat_hddt)

        str.append("</Products>")
        str.append("<Total>$tongtien_hddt</Total>")
        str.append("<DiscountAmount></DiscountAmount>")
        str.append("<VATRate>10</VATRate>")
        str.append("<VATAmount>$tongvat_hddt</VATAmount>")
        str.append("<Amount>$tong_hddt</Amount>")

        String tienkt
        String tbc
        tienkt = String.valueOf(Math.floor(tong_hddt)).replace(".0", "")
        if (tong_hddt.toString().contains("-"))
            tienkt = tienkt.replace("-", "")

        tbc = NumberToWord.num2String(Long.parseLong(tienkt)).substring(0, 1).toUpperCase() + NumberToWord.num2String(Long.parseLong(tienkt)).substring(1) + " đồng."

        str.append("<AmountInWords>$tbc</AmountInWords>")
        str.append("<Teller>$ma_nv</Teller>")
        str.append("</Invoice>")
        str.append("</Inv>")
        str.append("</Invoices>")

        String xml = str.toString().replaceAll("]]>", "]]]]><![CDATA[>")
        return "<![CDATA[$xml]]>".toString()
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null"
        }
        return o.toString().replace("\n", "\n    ")
    }

    String _docSo(long x) {
        if (x > 999_999_999_999) {
            return ""
        } else {
            long ty = x / 1_000_000_000
            long trieu = (x - ty * 1_000_000_000) / 1_000_000
            long nghin = (x - ty * 1_000_000_000 - trieu * 1_000_000) / 1_000
            long donvi = (x - ty * 1_000_000_000 - trieu * 1_000_000 - nghin * 1_000)

            StringBuilder sb = new StringBuilder()

            if (ty > 0) {
                sb.append(_doc3so(ty)).append(" tỷ")
            }
            if (trieu > 0) {
                if (trieu < 100) sb.append(" không trăm")
                sb.append(_doc3so(trieu)).append(" triệu")
            }
            if (nghin > 0) {
                if (nghin < 100) sb.append(" không trăm")
                sb.append(_doc3so(nghin)).append(" nghìn")
            }
            if (donvi > 0) {
                if (donvi < 100) sb.append(" không trăm")
                sb.append(_doc3so(donvi))//.append(" ")
            }

            return sb.append(" đồng").toString().trim()
        }
    }

    String _doc3so(long x) {
        return _doc3so(x as int)
    }

    String _doc3so(int x) {

        def bt = [2: " hai",
                  3: " ba",
                  4: " bốn",
                  6: " sáu",
                  7: " bẩy",
                  8: " tám",
                  9: " chín"] // as Map<Integer, String>
        //println(bt.containsValue('2'))
        String so = String.format("%03d", x)
        int tram = so.charAt(0) - 48
        int chuc = so.charAt(1) - 48
        int donvi = so.charAt(2) - 48

        StringBuilder sb = new StringBuilder()
        if (bt.containsKey(tram)) {
            sb.append(bt[tram]).append(" trăm")
        } else if (tram == 1) {
            sb.append(" một trăm")
        } else if (tram == 5) {
            sb.append(" năm trăm")
        }

        if (bt.containsKey(chuc)) {
            sb.append(bt[chuc]).append(" mươi")
        } else if (tram > 0 && chuc == 0 && donvi > 0) {
            sb.append(" lẻ")
        } else if (chuc == 1) {
            sb.append(" mười")
        } else if (chuc == 5) {
            sb.append(" năm mươi")
        } else if (chuc == 0) {
            sb.append(" lẻ")
        }

        if (bt.containsKey(donvi)) {
            sb.append(bt[donvi])
        } else if (donvi == 1 && chuc < 2) {
            sb.append(" một")
        } else if (donvi == 1) {
            sb.append(" mốt")
        } else if (donvi == 5 && chuc > 0) {
            sb.append(" lăm")
        } else if (donvi == 5) {
            sb.append(" năm")
        }

        return sb.toString()
    }
}
