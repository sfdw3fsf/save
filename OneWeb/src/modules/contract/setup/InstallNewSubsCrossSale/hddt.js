import API from './InstallNewSubsCrossSaleAPI'
export default{
  dongbo_hddt_tudong: async function(vthaotac, vkieu, vifkey, vphieutt_id, vhdkh_id, vma_gd, vten_kh, vdiachi_kh, vmst, vten_nh, vstk, vemail, vfax, vsodt, vndd, vloai_kh, vhttt, product) {
    if (vthaotac == 2) // Điều chỉnh hóa đơn
    {
        if (vkieu == 1 || vkieu == 2 || vkieu == 3)
        {
            //#region Điều chỉnh hóa đơn
            if (vifkey == "")
            {
                return "ERR: Không có thông tin Fkey cần điều chỉnh";
            }
            try
            {
                var str_token = "";
                str_token += " Select * ";
                str_token += " From " + DatabaseConstants.DB2 + ".phieutt_hd a, ";
                str_token += DatabaseConstants.DB2 + ".loai_hoadon b";
                str_token += " Where phieutt_id = " + vphieutt_id;
                str_token += "  and a.loaihoadon_id = b.loaihoadon_id ";
                var ds_token = await API.post_sp_huonggiao_runsql(this.axios, { "command": str_token }).then((response) => {
                  if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    return response.data.data.data_json;
                  }
                  else return [];
                });//ds_token = bangts.getDataFromSQL(str_token, "token");
                if (ds_token.length > 0)
                {
                    invToken = ds_token[0]["ten_loaihd"] + ";" + ds_token[0]["seri"] + ";" + ds_token[0]["soseri"];
                }
                else
                {
                    return "ERR: Không tìm thấy hóa đơn cần điều chỉnh !";
                }

                var bdhddt_cha_id = 0;
                var bd_cha = "";
                bd_cha += " Select max(nvl(bdhddt_id,0)) kq ";
                bd_cha += " From " + DatabaseConstants.DB6 + ".bd_hddt ";
                bd_cha += " Where hdkh_id = " + vhdkh_id;
                var ds_bd_cha = await API.post_sp_huonggiao_runsql(this.axios, { "command": bd_cha }).then((response) => {
                  if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    return response.data.data.data_json;
                  }
                  else return [];
                });//bangts.getDataFromSQL(bd_cha, "bd");
                if (ds_bd_cha.length > 0)
                    bdhddt_cha_id = ds_bd_cha[0].KQ;

                var xml = "";
                var tt78 = false;
                let kq = await API.post_fn_tt_thamso_md(this.axios, {
                    "type": 1,
                    "param": 'HDDT_THONGTU_78'
                }).then((response) => {
                    if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        return response.data.data;
                    }
                    else return 0;
                });
                if (kq == "1")
                    tt78 = true;
                let rs = await this.$root.context.post("/tichhop/vnptinvoice/downloadInvNoPay",{
                    "invToken":invToken,
                });
                xml = rs.data;
                // SAMPLE DATA:
                  //"data": "<?xml-stylesheet type='text/xsl' href='VNP1THUE' ?><HDon><DLHDon Id=\"DuLieuKy\"><TTChung><PBan>2.0.0</PBan><THDon>Hóa đơn GTGT</THDon><KHMSHDon>1</KHMSHDon><KHHDon>K22TBA</KHHDon><SHDon>16</SHDon><NLap>2022-07-21</NLap><DVTTe>VND</DVTTe><TGia>1</TGia><HTTToan>COD</HTTToan></TTChung><NDHDon><NBan><Ten>Công ty test nội bộ</Ten><MST>0100692594-996</MST><DChi>Số 2 Lê Hồng Phong, phường Bình Hưng, TP Phan Thiết, tỉnh Bình Thuận</DChi><SDThoai>0945601706</SDThoai><TNHang>Công ty công nghệ thông tin vnpt -it</TNHang></NBan><NMua><Ten>Test1111</Ten><MST>1111111111</MST><DChi>Xóm Hàng</DChi><MKHang>HPG-PS/000002238</MKHang></NMua><DSHHDVu><HHDVu><TChat>1</TChat><STT>1</STT><THHDVu>SP1</THHDVu><DVTinh>Cái</DVTinh><SLuong>5</SLuong><DGia>1000000</DGia><TLCKhau>0</TLCKhau><STCKhau>0</STCKhau><TSuat>10%</TSuat><ThTien>5000000</ThTien><TTKhac><TTin><TTruong>Amount</TTruong><KDLieu>numeric</KDLieu><DLieu>5500000</DLieu></TTin><TTin><TTruong>VATAmount</TTruong><KDLieu>numeric</KDLieu><DLieu>500000</DLieu></TTin></TTKhac></HHDVu></DSHHDVu><TToan><THTTLTSuat><LTSuat><TSuat>10%</TSuat><TThue>500000</TThue><ThTien>5000000</ThTien></LTSuat></THTTLTSuat><TgTCThue>5000000</TgTCThue><TgTThue>500000</TgTThue><TgTTTBSo>5500000</TgTTTBSo><TgTTTBChu>Năm triệu năm trăm ngàn không trăm đồng</TgTTTBChu></TToan></NDHDon></DLHDon><DSCKS><NBan><Signature Id=\"Signature-93622a3d472b4efab413a07e90de6e05\" xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" /><SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\" /><Reference URI=\"#DuLieuKy\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\" /></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /><DigestValue>2HjYA0KCmrJNvmqrHUsOWfXvw6o=</DigestValue></Reference><Reference URI=\"#SigningTime-00d1a5600c534b9ab0a8dad4b57526c8\"><DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /><DigestValue>Z6j9mFod2HawMpzdpy9tAUSCQUA=</DigestValue></Reference></SignedInfo><SignatureValue>CW8ui2V/75VomoXRi8+RlzhOa5f9ObMDRXYSRFJO75Kz3S0t8AQnTsYY8zSyuoW7k+CSmS7FGiYbYf3vN6DLnaALPMv9CsvIldVSJ2PxJ6hCRmJAFgvAX/Vz6G+KfYeTmkDvKv4kiqi2stHT31McpDrHAYrDDrBK7pEdQDw6BAavEBxBVcsZAzOkiFD11YsAAEuBDO+Q1Bl8RJXJmXWfN40slu18v7pJb/ohJvy9acoF6ej+5gqQ6HV7XZllv72JogVxbZ1GvZqhWfy12llauYzBpDY4y0gAn2U8yLgPOs9QOtvx8aaW8XnFWMGLnmelAwR7srXavGHc/8GnCAKtVw==</SignatureValue><KeyInfo><X509Data><X509SubjectName>OID.0.9.2342.19200300.100.1.1=MST:0106869738-012, CN=VNPT_TEST, L=Cầu Giấy, S=HẢI PHÒNG, C=VN</X509SubjectName><X509Certificate>MIIFJTCCBA2gAwIBAgIQVAEBAU2KFQWsnH3BMqmEVTANBgkqhkiG9w0BAQsFADBcMQswCQYDVQQGEwJWTjEzMDEGA1UECgwqVklFVE5BTSBQT1NUUyBBTkQgVEVMRUNPTU1VTklDQVRJT05TIEdST1VQMRgwFgYDVQQDDA9WTlBULUNBIFNIQS0yNTYwHhcNMjIwNDI1MDg1NjAwWhcNMjUwNzI4MTEwOTQ3WjBzMQswCQYDVQQGEwJWTjEVMBMGA1UECAwMSOG6okkgUEjDkk5HMRUwEwYDVQQHDAxD4bqndSBHaeG6pXkxEjAQBgNVBAMMCVZOUFRfVEVTVDEiMCAGCgmSJomT8ixkAQEMEk1TVDowMTA2ODY5NzM4LTAxMjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKVDB2wqFbrcw2qI4HsqJ7wVYPyhsXDrCBs0rNdPEHl/PJ09Oz3PdKlJPynWoPLNIyGzFR3HWIQWtHCeiGsIVkWsHmRoQbAETP9ymQVcRnvw/lqSWkSu/6XsuAruyeI8lwRDhRbKoRGw7aVa2Kr23BcxF7QXBSJovRRrQvoaEZPkeXheMP0lOdnkTq6GueF7zI8zWZDImJ17a/0RJ9aClSC/dW4NmDOTJ8K3fUXCM+MPQynZL25iM71sDgOHy/ueyI4GvwdsfM89+ThUHhcfOWTWoL2FEFuN7LaNPOW+ai3sAz7X+vVWmNAymzXFYQ/gnI004lVnTGMk+sYqGSAxX60CAwEAAaOCAcowggHGMH4GCCsGAQUFBwEBBHIwcDA5BggrBgEFBQcwAoYtaHR0cDovL3B1Yi52bnB0LWNhLnZuL2NlcnRzL3ZucHRjYS1zaGEyNTYuY2VyMDMGCCsGAQUFBzABhidodHRwOi8vb2NzcC1zaGEyNTYudm5wdC1jYS52bi9yZXNwb25kZXIwHQYDVR0OBBYEFLTDQdmccGEOPtXga6XslKg2FQU4MAwGA1UdEwEB/wQCMAAwHwYDVR0jBBgwFoAUtk1ra9amnTTtMjnsQlSsvjJj2HEwaAYDVR0gBGEwXzBdBg4rBgEEAYHtAwEBAwEBATBLMCIGCCsGAQUFBwICMBYeFABPAEkARAAtAFMAVAAtADIALgAwMCUGCCsGAQUFBwIBFhlodHRwOi8vcHViLnZucHQtY2Eudm4vcnBhMD8GA1UdHwQ4MDYwNKAyoDCGLmh0dHA6Ly9jcmwtc2hhMjU2LnZucHQtY2Eudm4vdm5wdGNhLXNoYTI1Ni5jcmwwDgYDVR0PAQH/BAQDAgTwMCAGA1UdJQQZMBcGCisGAQQBgjcKAwwGCSqGSIb3LwEBBTAZBgNVHREEEjAQgQ50ZXN0QGdtYWlsLmNvbTANBgkqhkiG9w0BAQsFAAOCAQEAW6THvtROHRK2OiYPK3waBmDPK8107dveXNh5auYdOHoSLQ89PH93jPwOwZhqcNquL6QcTlEWU6ATswDjojBcvlvWAOdW5cnaTlWE5jCOswBo/EW5ffRCAri+W/pBrBHIaKBh4AVnoVRfx3gmohTtunWt6BuIMjf0v+xPaQNjVatoDciwdcYzz27KeBADvobevcPZq5h3jdDuw2LlMSYf40pgMyPRP5x01uVatjVsv9ceyhEpWcOhV4KRppWw8z5V0MlY/GEdAzA2LonzPxNyj7xjk+q/mb433/i1QwELrRB7gtmgfUku7l48ELrYyJVEg5H2zL65iR++s5DTShXA8g==</X509Certificate></X509Data></KeyInfo><Object Id=\"SigningTime-00d1a5600c534b9ab0a8dad4b57526c8\"><SignatureProperties xmlns=\"\"><SignatureProperty Target=\"#Signature-93622a3d472b4efab413a07e90de6e05\"><SigningTime>2022-07-21T15:26:46</SigningTime></SignatureProperty></SignatureProperties></Object></Signature></NBan></DSCKS><Fkey>VNPTHPG2022000001228</Fkey></HDon>"}
                // END SAMPLE DATA
                // if (tt78)
                //     xml = invoice.downloadInvNoPay_V2(invToken, tt_nd.user_hddt, tt_nd.pass_hddt);
                // else
                //     xml = invoice.downloadInvNoPay_V2(invToken, tt_nd.user_hddt, tt_nd.pass_hddt);
                if (xml.trim().indexOf("ERR:")==0)
                {
                    return "ERR: downloadInvNoPay - " + xml;
                }
                else
                {
                    if (tt78)
                    {
                        var parseString = require('xml2js').parseString;
                        var hdon = parseString(xml, function (err, result) {
                            return result;
                        });
                        console.log(hdon); // test lai den day!
                        XmlDocument doc = new XmlDocument();
                        doc.LoadXml(xml);

                        HDon hdon = (HDon)ObjectToXML(doc.InnerXml, typeof(HDon));
                        HDon2 hdon2 = (HDon2)ObjectToXML(doc.InnerXml, typeof(HDon2));
                        if (hdon != null || hdon2 != null)
                        {
                            Product[] products = new Product[100];
                            if (hdon != null)
                            {
                                objHoaDonDienTu.Amount = hdon.DLHDon.NDHDon.TToan.TgTTTBSo;
                                objHoaDonDienTu.Amount_words = hdon.DLHDon.NDHDon.TToan.TgTTTBChu;
                                objHoaDonDienTu.ArisingDate = hdon.DLHDon.TTChung.NLap;
                                objHoaDonDienTu.Buyer = hdon.DLHDon.NDHDon.NMua.HVTNMHang;
                                objHoaDonDienTu.ComAddress = hdon.DLHDon.NDHDon.NBan.DChi;
                                //objHoaDonDienTu.ComBankNo = hdon.DLHDon.NDHDon.NMua.STKNHang;
                                objHoaDonDienTu.ComName = hdon.DLHDon.NDHDon.NBan.Ten;
                                objHoaDonDienTu.ComPhone = hdon.DLHDon.NDHDon.NBan.SDThoai;
                                objHoaDonDienTu.ComTaxCode = hdon.DLHDon.NDHDon.NBan.MST;
                                objHoaDonDienTu.CusAddress = hdon.DLHDon.NDHDon.NMua.DChi;
                                objHoaDonDienTu.CusBankName = hdon.DLHDon.NDHDon.NMua.TNHang;
                                objHoaDonDienTu.CusCode = hdon.DLHDon.NDHDon.NMua.MKHang;
                                objHoaDonDienTu.CusName = hdon.DLHDon.NDHDon.NMua.Ten;
                                objHoaDonDienTu.CusPhone = hdon.DLHDon.NDHDon.NMua.SDThoai;
                                objHoaDonDienTu.CusTaxCode = hdon.DLHDon.NDHDon.NMua.MST;
                                //objHoaDonDienTu.Discount_Amount =
                                //objHoaDonDienTu.Discount_Rate =
                                //objHoaDonDienTu.EmailDeliver =
                                if (hdon.DLHDon.TTChung.TTKhac != null)
                                    objHoaDonDienTu.Extra = hdon.DLHDon.TTChung.TTKhac[0].TTruong;
                                objHoaDonDienTu.InvoiceName = hdon.DLHDon.TTChung.THDon;
                                objHoaDonDienTu.InvoiceNo = hdon.DLHDon.TTChung.SHDon;
                                objHoaDonDienTu.InvoicePattern = hdon.DLHDon.TTChung.KHMSHDon;
                                objHoaDonDienTu.Kind_of_Payment = hdon.DLHDon.TTChung.HTTToan;
                                //objHoaDonDienTu.KindOfService =
                                //objHoaDonDienTu.MaKhachHang =
                                //objHoaDonDienTu.MaThanhToan =
                                objHoaDonDienTu.SerialNo = hdon.DLHDon.TTChung.KHHDon;
                                //objHoaDonDienTu.SignDate =
                                objHoaDonDienTu.Total = hdon.DLHDon.NDHDon.TToan.TgTCThue;
                                objHoaDonDienTu.VAT_Amount = hdon.DLHDon.NDHDon.TToan.TgTThue;
                                objHoaDonDienTu.VAT_Rate = "0";
                                products = new Product[hdon.DLHDon.NDHDon.DSHHDVu.Length];
                                for (int i = 0; i < products.Length; i++)
                                {
                                    products[i] = new Product();
                                    products[i].Amount = hdon.DLHDon.NDHDon.DSHHDVu[i].TTKhac[0].DLieu;
                                    if (hdon.DLHDon.NDHDon.DSHHDVu[i].MHHDVu != null)
                                        products[i].Code = hdon.DLHDon.NDHDon.DSHHDVu[i].MHHDVu;
                                    products[i].Discount = 0;
                                    products[i].DiscountAmount = 0;
                                    products[i].ProdName = hdon.DLHDon.NDHDon.DSHHDVu[i].THHDVu;
                                    products[i].ProdPrice = hdon.DLHDon.NDHDon.DSHHDVu[i].DGia;
                                    products[i].ProdQuantity = hdon.DLHDon.NDHDon.DSHHDVu[i].SLuong;
                                    products[i].ProdUnit = hdon.DLHDon.NDHDon.DSHHDVu[i].DVTinh;
                                    //products[i].Remark =
                                    products[i].Total = hdon.DLHDon.NDHDon.DSHHDVu[i].ThTien;
                                    if (hdon.DLHDon.NDHDon.DSHHDVu[i].TTKhac.Length > 1)
                                        products[i].VATAmount = hdon.DLHDon.NDHDon.DSHHDVu[i].TTKhac[1].DLieu;
                                    else
                                        products[i].VATAmount = 0;
                                    if (hdon.DLHDon.NDHDon.DSHHDVu[i].TSuat == "KKKNT")
                                        products[i].VATRate = -2;
                                    else
                                        if (hdon.DLHDon.NDHDon.DSHHDVu[i].TSuat == "KCT")
                                            products[i].VATRate = -1;
                                        else
                                            if (hdon.DLHDon.NDHDon.TToan.THTTLTSuat[0].TSuat.Contains("KHAC:"))
                                                products[i].VATRate = 10;
                                            else
                                                products[i].VATRate = decimal.Parse(hdon.DLHDon.NDHDon.DSHHDVu[i].TSuat.Substring(0, hdon.DLHDon.NDHDon.DSHHDVu[i].TSuat.Length - 1));
                                }
                                objHoaDonDienTu.Products = products;
                            }
                            else
                            {
                                objHoaDonDienTu.Amount = (decimal)hdon2.DLHDon.NDHDon.TToan.TgTTTBSo;
                                objHoaDonDienTu.Amount_words = hdon2.DLHDon.NDHDon.TToan.TgTTTBChu;
                                objHoaDonDienTu.ArisingDate = hdon2.DLHDon.TTChung.NLap;
                                //objHoaDonDienTu.Buyer = hdon2.DLHDon.NDHDon.NMua.HVTNMHang;
                                objHoaDonDienTu.ComAddress = hdon2.DLHDon.NDHDon.NBan.DChi;
                                //objHoaDonDienTu.ComBankNo = hdon.DLHDon.NDHDon.NMua.STKNHang;
                                objHoaDonDienTu.ComName = hdon2.DLHDon.NDHDon.NBan.Ten;
                                objHoaDonDienTu.ComPhone = hdon2.DLHDon.NDHDon.NBan.SDThoai;
                                objHoaDonDienTu.ComTaxCode = hdon2.DLHDon.NDHDon.NBan.MST;
                                objHoaDonDienTu.CusAddress = hdon2.DLHDon.NDHDon.NMua.DChi;
                                //objHoaDonDienTu.CusBankName = hdon2.DLHDon.NDHDon.NMua.TNHang;
                                objHoaDonDienTu.CusCode = hdon2.DLHDon.NDHDon.NMua.MKHang;
                                objHoaDonDienTu.CusName = hdon2.DLHDon.NDHDon.NMua.Ten;
                                //objHoaDonDienTu.CusPhone = hdon2.DLHDon.NDHDon.NMua.SDThoai;
                                //objHoaDonDienTu.CusTaxCode = hdon2.DLHDon.NDHDon.NMua.MST;
                                //objHoaDonDienTu.Discount_Amount =
                                //objHoaDonDienTu.Discount_Rate =
                                //objHoaDonDienTu.EmailDeliver =
                                //if (hdon2.DLHDon.TTChung.TTKhac != null)
                                //    objHoaDonDienTu.Extra = hdon2.DLHDon.TTChung.TTKhac[0].TTruong;
                                objHoaDonDienTu.InvoiceName = hdon2.DLHDon.TTChung.THDon;
                                objHoaDonDienTu.InvoiceNo = hdon2.DLHDon.TTChung.SHDon;
                                objHoaDonDienTu.InvoicePattern = hdon2.DLHDon.TTChung.KHMSHDon;
                                objHoaDonDienTu.Kind_of_Payment = hdon2.DLHDon.TTChung.HTTToan;
                                //objHoaDonDienTu.KindOfService =
                                //objHoaDonDienTu.MaKhachHang =
                                //objHoaDonDienTu.MaThanhToan =
                                objHoaDonDienTu.SerialNo = hdon2.DLHDon.TTChung.KHHDon;
                                //objHoaDonDienTu.SignDate =
                                objHoaDonDienTu.Total = (decimal)hdon2.DLHDon.NDHDon.TToan.TgTCThue;
                                objHoaDonDienTu.VAT_Amount = (decimal)hdon2.DLHDon.NDHDon.TToan.TgTThue;
                                try
                                {
                                    if (hdon2.DLHDon.NDHDon.TToan.THTTLTSuat.LTSuat.TSuat == "KKKNT")
                                        objHoaDonDienTu.VAT_Rate = "-2";
                                    else
                                        if (hdon2.DLHDon.NDHDon.TToan.THTTLTSuat.LTSuat.TSuat == "KCT")
                                            objHoaDonDienTu.VAT_Rate = "-1";
                                        else
                                            if (hdon2.DLHDon.NDHDon.TToan.THTTLTSuat.LTSuat.TSuat.Contains("KHAC:"))
                                                objHoaDonDienTu.VAT_Rate = "10";
                                            else
                                                objHoaDonDienTu.VAT_Rate = hdon2.DLHDon.NDHDon.TToan.THTTLTSuat.LTSuat.TSuat.Substring(0, hdon2.DLHDon.NDHDon.TToan.THTTLTSuat.LTSuat.TSuat.Length - 1);
                                }
                                catch { objHoaDonDienTu.VAT_Rate = "0"; }

                                products = new Product[1];
                                for (int i = 0; i < products.Length; i++)
                                {
                                    products[i] = new Product();
                                    products[i].Amount = decimal.Parse(hdon2.DLHDon.NDHDon.DSHHDVu.HHDVu[i].TTKhac.TTin[0].DLieu);
                                    //if (hdon2.DLHDon.NDHDon.DSHHDVu[i].MHHDVu != null)
                                    //    products[i].Code = hdon2.DLHDon.NDHDon.DSHHDVu.HHDVu[i].TTKhac[i].MHHDVu;
                                    products[i].Discount = 0;
                                    products[i].DiscountAmount = 0;
                                    products[i].ProdName = hdon2.DLHDon.NDHDon.DSHHDVu.HHDVu[i].THHDVu;
                                    products[i].ProdPrice = hdon2.DLHDon.NDHDon.DSHHDVu.HHDVu[i].DGia;
                                    products[i].ProdQuantity = hdon2.DLHDon.NDHDon.DSHHDVu.HHDVu[i].SLuong;
                                    //products[i].ProdUnit = hdon2.DLHDon.NDHDon.DSHHDVu.HHDVu[i].DVTinh;
                                    //products[i].Remark =
                                    products[i].Total = hdon2.DLHDon.NDHDon.DSHHDVu.HHDVu[i].ThTien;
                                    if (hdon2.DLHDon.NDHDon.DSHHDVu.HHDVu[i].TTKhac.TTin.Length > 1)
                                        products[i].VATAmount = decimal.Parse(hdon2.DLHDon.NDHDon.DSHHDVu.HHDVu[i].TTKhac.TTin[1].DLieu);
                                    else
                                        products[i].VATAmount = 0;
                                    if (hdon2.DLHDon.NDHDon.DSHHDVu.HHDVu[i].TSuat == "KKKNT")
                                        products[i].VATRate = -2;
                                    else
                                        if (hdon2.DLHDon.NDHDon.DSHHDVu.HHDVu[i].TSuat == "KCT")
                                            products[i].VATRate = -1;
                                        else
                                            if (hdon2.DLHDon.NDHDon.DSHHDVu.HHDVu[i].TSuat.Contains("KHAC:"))
                                                products[i].VATRate = 10;
                                            else
                                                products[i].VATRate = decimal.Parse(hdon2.DLHDon.NDHDon.DSHHDVu.HHDVu[i].TSuat.Substring(0, hdon2.DLHDon.NDHDon.DSHHDVu.HHDVu[i].TSuat.Length - 1));
                                }
                                objHoaDonDienTu.Products = products;
                            }
                            objHoaDonDienTuEdit = objHoaDonDienTu;
                        }
                        else
                        {
                            XmlNode xnList = doc.SelectNodes("/Invoice/Content").Item(0);
                            XmlDocument doc1 = new XmlDocument();
                            doc1.LoadXml("<Content>" + xnList.InnerXml + "</Content>");
                            objHoaDonDienTu = (Content)ObjectToXML(doc1.InnerXml, typeof(Content));
                        }
                    }
                    else
                    {
                        XmlDocument doc = new XmlDocument();
                        doc.LoadXml(xml);
                        XmlNode xnList = doc.SelectNodes("/Invoice/Content").Item(0);
                        XmlDocument doc1 = new XmlDocument();
                        doc1.LoadXml("<Content>" + xnList.InnerXml + "</Content>");
                        objHoaDonDienTu = (Content)ObjectToXML(doc1.InnerXml, typeof(Content));
                    }
                }
                loi_het_seri = false;
                tongtien_hddt = 0;
                tongvat_hddt = 0;
                tong_hddt = 0;

                string sohoadon = "";
                string pattern = "";
                string seri = "";
                int loaihoadon_id = 0;
                int seri_id = 0;

                string str_pattern = "";
                str_pattern += " select * ";
                str_pattern += " from ( ";
                str_pattern += " select a.loaihoadon_id, a.ten_loaihd, b.seri_id, b.seri ";
                str_pattern += " from " + DatabaseConstants.DB2 + ".loai_hoadon a, " + DatabaseConstants.DB2 + ".seri_hd b, " + DatabaseConstants.DB2 + ".loaihoadon_seri c ";
                str_pattern += " where a.loaihoadon_id = c.loaihoadon_id ";
                str_pattern += "     and b.seri_id = c.seri_id ";
                if (tt78)
                {
                    str_pattern += "     and (a.kieu = 2 or a.kieu = 4) ";
                    str_pattern += "     and (b.kieu = 2 or b.kieu = 4) ";
                    str_pattern += "     and a.ten_loaihd = '" + ds_token[0]["ten_loaihd"] + "'";
                    str_pattern += "     and b.seri = '" + ds_token[0]["seri"] + "'";

                }
                else
                {
                    str_pattern += "     and a.kieu = 2 ";
                    str_pattern += "     and b.kieu = 2 ";
                    str_pattern += "     and b.sudung = 1 ";
                    str_pattern += "     and b.trangthai in (1,2) ";
                }
                str_pattern += " order by b.thutu) ";
                str_pattern += " where rownum = 1 ";

                DataSet ds_hd_seri = new DataSet();
                ds_hd_seri = new BangThamSo().getDataFromSQL(str_pattern, "lhd_sr");
                if (ds_hd_seri.Tables[0].Rows.Count > 0)
                {
                    loaihoadon_id = Convert.ToInt32(ds_hd_seri.Tables[0].Rows[0]["loaihoadon_id"]);
                    pattern = ds_hd_seri.Tables[0].Rows[0]["ten_loaihd"];
                    seri_id = Convert.ToInt32(ds_hd_seri.Tables[0].Rows[0]["seri_id"]);
                    seri = ds_hd_seri.Tables[0].Rows[0]["seri"];
                }
                else
                {
                    return "ERR: Không tìm thấy loại hóa đơn, seri !";
                }

                // Kiểm tra hóa đơn hiện tại có đang ở trạng thái Đang sử dụng không ?
                string kq_kt = "";
                if (tt78)
                    kq_kt = invoice.listInvByCus_TT78(vma_gd, "", "", tt_nd.user_hddt, tt_nd.pass_hddt);
                else
                    kq_kt = invoice.listInvByCus_V2(vma_gd, "", "", tt_nd.user_hddt, tt_nd.pass_hddt, pattern);
                if (kq_kt.Substring(0, 3) == "ERR")
                {
                    return "ERR: listInvByCus - " + kq_kt;
                }
                else
                {
                    if (!tt78)
                    {
                        Data objKetQua_kt = (Data)ObjectToXML(kq_kt, typeof(Data));
                        EInvoice.Item item_kt = objKetQua_kt.Items.Where(x => x.invToken == invToken).Single();
                        if (item_kt.status != 1)
                        {
                            return "Hóa đơn đã bị thay thế/sửa đổi/hủy. Không thể điều chỉnh !";
                        }
                    }
                }

                //kichhoat:

                string kq = "";
                if (tt78)
                    kq = invoice.AdjustInvoiceAction_TT78(tt_nd.user_hddt_td, tt_nd.pass_hddt_td, GetInvoiceXml_DieuChinh(vkieu, vten_kh, vma_gd, vmst, vdiachi_kh,
                      vstk, vsodt, vhttt, product, objHoaDonDienTu), tt_nd.user_hddt, tt_nd.pass_hddt, vifkey, 0, "11", pattern, seri);
                else
                    kq = invoice.adjustInv_V2(tt_nd.user_hddt_td, tt_nd.pass_hddt_td, GetInvoiceXml_DieuChinh(vkieu, vten_kh, vma_gd, vmst, vdiachi_kh,
                      vstk, vsodt, vhttt, product, objHoaDonDienTu), tt_nd.user_hddt, tt_nd.pass_hddt, pattern, seri, vifkey, 0);
                string[] result = kq.Split(':');
                if (result[0] == "OK")
                {
                    try
                    {
                        string[] result1 = result.ToArray()[1].Split(';');
                        pattern = result1.ToArray()[0];
                        seri = result1.ToArray()[1];
                        sohoadon = result1.ToArray()[2].Split('_')[1];
                        fkey_new = result1.ToArray()[2].Split('_')[0];
                        invToken = pattern + ";" + seri + ";" + sohoadon;
                        //string xml1 = invoice.downloadInvNoPay_V2(invToken, tt_nd.user_hddt, tt_nd.pass_hddt);
                        //if (xml1.StartsWith("ERR:"))
                        //{
                        //return "ERR: Không có thông tin hóa đơn đã điều chỉnh !";
                        dsBDHDDT = new BD_HDDT_DATA();
                        BD_HDDT_DATA.BD_HDDTRow row = dsBDHDDT.BD_HDDT.NewBD_HDDTRow();
                        BDHDDT_ID = getkey.GetKey("BDHDDT_ID");
                        row.BDHDDT_ID = BDHDDT_ID;
                        row.MA_TT = vma_gd;
                        //row.THONGTINHD_CU = GetXMLFromObject(objHoaDonDienTu);
                        //row.THONGTINHD_MOI = GetXMLFromObject(objHoaDonDienTuEdit);
                        if (vkieu == 1) // Điều chỉnh tăng
                            row.KIEU_BD = 8;
                        else if (vkieu == 2) // Điều chỉnh giảm
                            row.KIEU_BD = 9;
                        else if (vkieu == 3) // Điều chỉnh thông tin
                            row.KIEU_BD = 10;
                        else
                            row.KIEU_BD = 1;

                        row.TONGTIEN = tong_hddt;
                        row.NGUOI_CN = tt_nd.ma_nd;
                        row.MAY_CN = tt_nd.may_cn;
                        row.NGAY_CN = tt_nd.ngay_cn;
                        row.NHANVIEN_ID = tt_nd.nhanvien_id;
                        row.DONVI_ID = tt_nd.donvi_id;
                        row.GHICHU = "Tạo hóa đơn điện tử tự động PTTB";
                        row.HDKH_ID = vhdkh_id;
                        row.PHIEUTT_ID = vphieutt_id;
                        row.LOAIHOADON_CU = objHoaDonDienTu.InvoicePattern;
                        row.LOAIHOADON_MOI = pattern;
                        row.SERI_CU = objHoaDonDienTu.SerialNo;
                        row.SERI_MOI = seri;
                        row.SO_HD_CU = objHoaDonDienTu.InvoiceNo;
                        row.SO_HD_MOI = sohoadon;
                        row.SERI_MOI_ID = seri_id;
                        row.TRANGTHAI = 1;
                        row.BDHDDT_CHA_ID = bdhddt_cha_id;
                        row.TEN_TT = objHoaDonDienTu.CusName;
                        row.DIACHI_TT = objHoaDonDienTu.CusAddress;
                        row.SDT = objHoaDonDienTu.CusPhone;
                        row.MST = objHoaDonDienTu.CusTaxCode;

                        row.HINHTHUC_TT = "Tiền mặt/Chuyển khoản";
                        row.TIEN = tongtien_hddt;
                        row.TIENBANGCHU = tbc;
                        row.TYLE_VAT = 10;
                        row.VAT = tongvat_hddt;

                        dsBDHDDT.BD_HDDT.AddBD_HDDTRow(row);
                        ecms.Insert(dsBDHDDT, DatabaseConstants.DB6);
                        dsBDHDDTCT = this.TaoDuLieuBDCT(product);
                        ecms.Insert(dsBDHDDTCT, DatabaseConstants.DB6);

                        string str_update = "";
                        if ((new BusinessFacade.CheckDataFacade().MAP_ID("ten_ts", DatabaseConstants.DB1 + ".thamso_md", "where ma_ts = 'HDDT_THONGTU_78' ")) != "1")
                        {
                            str_update = " Update " + DatabaseConstants.DB2 + ".phieutt_hd ";
                            str_update += " set hddt_fkey = '" + fkey_new + "', ";
                            str_update += " loaihoadon_id = '" + loaihoadon_id + "',";
                            str_update += " seri_id = '" + seri_id + "',";
                            str_update += " seri = '" + seri + "',";
                            str_update += " soseri = '" + sohoadon + "'";
                            str_update += " Where phieutt_id  = " + vphieutt_id;
                            new BangThamSo().ExeSQL(str_update);
                        }

                        str_update = "";
                        str_update += " Update " + DatabaseConstants.DB6 + ".bd_hddt ";
                        str_update += " Set trangthai = 3, ";
                        str_update += "     ngay_td = sysdate, ";
                        str_update += "     fkey = '" + fkey_new + "', ";
                        str_update += "     nguoi_td = '" + tt_nd.ma_nd + "'";
                        str_update += " Where bdhddt_id = " + bdhddt_cha_id;
                        bangts.ExeSQL(str_update);

                        Fkey = "";
                        fkey_new = "";
                        return "OK";
                    }
                    catch (Exception ex)
                    {
                        TruyenDanFacade tdan = new TruyenDanFacade();
                        tdan.EXECUTE_PROC("{?DB1}.QUANTRI_HETHONG.Log_Action",
                            "vuser_name", tt_nd.ma_nd,
                            "vcomputer_name", tt_nd.may_cn,
                            "vaction_name", "chucnang_dieuchinh",
                            "vkey_parameter", "ma_gd = " + vma_gd + ", ngay_cn = " + tt_nd.ngay_cn + ", Err : " + ex,
                            "vserver_name", tt_nd.ip,
                            "vnote", "chucnang_dieuchinh");
                        return "OK";
                    }
                }
                else
                {
                    return "ERR: Có lỗi trong quá trình xuất hóa đơn điện tử: " + kq;
                }
            }
            catch (Exception ex)
            {
                return "ERR: " + ex;
            }
            #endregion
        }
        else
        {
            return "ERR: Kiểm tra lại thông tin kiểu điều chỉnh !";
        }
    }
    else if (vthaotac == 3) // Thay thế hóa đơn
    {
        #region Thay thế hóa đơn
        if (vifkey == "")
        {
            return "ERR: Không có thông tin Fkey cần thay thế";
        }
        try
        {
            string str_token = "";
            str_token += " Select * ";
            str_token += " From " + DatabaseConstants.DB2 + ".phieutt_hd a, ";
            str_token += DatabaseConstants.DB2 + ".loai_hoadon b";
            str_token += " Where phieutt_id = " + vphieutt_id;
            str_token += "  and a.loaihoadon_id = b.loaihoadon_id ";
            DataSet ds_token = new DataSet();
            ds_token = bangts.getDataFromSQL(str_token, "token");
            if (ds_token.length > 0)
            {
                invToken = ds_token[0]["ten_loaihd"] + ";" + ds_token[0]["seri"] + ";" + ds_token[0]["soseri"];
            }
            else
            {
                return "ERR: Không tìm thấy hóa đơn cần thay thế !";
            }

            string sohoadon = "";
            string pattern = "";
            string seri = "";
            int loaihoadon_id = 0;
            int seri_id = 0;

            string str_pattern = "";
            str_pattern += " select * ";
            str_pattern += " from ( ";
            str_pattern += " select a.loaihoadon_id, a.ten_loaihd, b.seri_id, b.seri ";
            str_pattern += " from " + DatabaseConstants.DB2 + ".loai_hoadon a, " + DatabaseConstants.DB2 + ".seri_hd b, " + DatabaseConstants.DB2 + ".loaihoadon_seri c ";
            str_pattern += " where a.loaihoadon_id = c.loaihoadon_id ";
            str_pattern += "     and b.seri_id = c.seri_id ";
            str_pattern += "     and a.kieu = 2 ";
            str_pattern += "     and b.kieu = 2 ";
            str_pattern += "     and b.sudung = 1 ";
            str_pattern += "     and b.trangthai in (1,2) ";
            str_pattern += " order by b.thutu) ";
            str_pattern += " where rownum = 1 ";

            DataSet ds_hd_seri = new DataSet();
            ds_hd_seri = new BangThamSo().getDataFromSQL(str_pattern, "lhd_sr");
            if (ds_hd_seri.Tables[0].Rows.Count > 0)
            {
                loaihoadon_id = Convert.ToInt32(ds_hd_seri.Tables[0].Rows[0]["loaihoadon_id"]);
                pattern = ds_hd_seri.Tables[0].Rows[0]["ten_loaihd"];
                seri_id = Convert.ToInt32(ds_hd_seri.Tables[0].Rows[0]["seri_id"]);
                seri = ds_hd_seri.Tables[0].Rows[0]["seri"];
            }
            else
            {
                return "ERR: Không tìm thấy loại hóa đơn, seri !";
            }

            // Kiểm tra hóa đơn hiện tại có đang ở trạng thái Đang sử dụng không ?
            string kq_kt = invoice.listInvByCus_V2(vma_gd, "", "", tt_nd.user_hddt, tt_nd.pass_hddt, pattern);
            if (kq_kt.Substring(0, 3) == "ERR")
            {
                return "ERR: listInvByCus - " + kq_kt;
            }
            else
            {
                Data objKetQua_kt = (Data)ObjectToXML(kq_kt, typeof(Data));
                EInvoice.Item item_kt = objKetQua_kt.Items.Where(x => x.invToken == invToken).Single();
                if (item_kt.status != 1)
                {
                    return "ERR: Hóa đơn đã bị thay thế/sửa đổi/hủy. Không thể thay thế !";
                }
            }

            // Kiểm tra xem có tồn tại hóa đơn đã điều chỉnh không ?
            string bd_tt = "";
            bd_tt += " Select count(*) ";
            bd_tt += " From " + DatabaseConstants.DB6 + ".bd_hddt ";
            bd_tt += " Where hdkh_id = " + vhdkh_id;
            bd_tt += "      and trangthai = 3 ";
            DataSet ds_tt = bangts.getDataFromSQL(bd_tt, "bdtt");
            if (ds_tt.Tables[0].Rows.Count > 0)
            {
                if (Convert.ToInt32(ds_tt.Tables[0].Rows[0][0]) > 0)
                    return "ERR: Tồn tại hóa đơn đã bị điều chỉnh. Bạn không thể thay thé !";
            }
            // end kiểm tra


            long bdhddt_cha_id = 0;
            string bd_cha = "";
            bd_cha += " Select max(nvl(bdhddt_id,0)) ";
            bd_cha += " From " + DatabaseConstants.DB6 + ".bd_hddt ";
            bd_cha += " Where hdkh_id = " + vhdkh_id;
            DataSet ds_bd_cha = bangts.getDataFromSQL(bd_cha, "bd");
            if (ds_bd_cha.Tables[0].Rows.Count > 0)
                bdhddt_cha_id = Convert.ToInt64(ds_bd_cha.Tables[0].Rows[0][0]);

            string xml = invoice.downloadInvNoPay_V2(invToken, tt_nd.user_hddt, tt_nd.pass_hddt);
            if (xml.StartsWith("ERR:"))
            {
                return "ERR: Không có thông tin hóa đơn cần thay thế !";
            }
            else
            {
                XmlDocument doc = new XmlDocument();
                doc.LoadXml(xml);
                XmlNode xnList = doc.SelectNodes("/Invoice/Content").Item(0);
                XmlDocument doc1 = new XmlDocument();
                doc1.LoadXml("<Content>" + xnList.InnerXml + "</Content>");
                objHoaDonDienTu = (Content)ObjectToXML(doc1.InnerXml, typeof(Content));
            }
            loi_het_seri = false;
            tongtien_hddt = 0;
            tongvat_hddt = 0;
            tong_hddt = 0;

            //kichhoat:

            string kq = invoice.replaceInv_V2(tt_nd.user_hddt_td, tt_nd.pass_hddt_td, GetInvoiceXml_ThayThe(vma_gd, vhttt, product, objHoaDonDienTu), tt_nd.user_hddt, tt_nd.pass_hddt, pattern, seri, vifkey, 0);

            string[] result = kq.Split(':');
            if (result[0] == "OK")
            {
                string[] result1 = result.ToArray()[1].Split(';');
                pattern = result1.ToArray()[0];
                seri = result1.ToArray()[1];
                sohoadon = result1.ToArray()[2].Split('_')[1];
                fkey_new = result1.ToArray()[2].Split('_')[0];
                invToken = pattern + ";" + seri + ";" + sohoadon;
                string xml1 = invoice.downloadInvNoPay_V2(invToken, tt_nd.user_hddt, tt_nd.pass_hddt);
                if (xml1.StartsWith("ERR:"))
                {
                    return "ERR: Không có thông tin hóa đơn đã thay thế !";
                }
                else
                {
                    XmlDocument doc2 = new XmlDocument();
                    doc2.LoadXml(xml1);
                    XmlNode xnList = doc2.SelectNodes("/Invoice/Content").Item(0);
                    XmlDocument doc3 = new XmlDocument();
                    doc3.LoadXml("<Content>" + xnList.InnerXml + "</Content>");
                    objHoaDonDienTuEdit = (Content)ObjectToXML(doc3.InnerXml, typeof(Content));
                }


                dsBDHDDT = new BD_HDDT_DATA();
                BD_HDDT_DATA.BD_HDDTRow row = dsBDHDDT.BD_HDDT.NewBD_HDDTRow();
                BDHDDT_ID = getkey.GetKey("BDHDDT_ID");
                row.BDHDDT_ID = BDHDDT_ID;
                row.MA_TT = vma_gd;
                row.THONGTINHD_CU = GetXMLFromObject(objHoaDonDienTu);
                row.THONGTINHD_MOI = GetXMLFromObject(objHoaDonDienTuEdit);
                row.KIEU_BD = 2;

                row.TONGTIEN = tong_hddt;
                row.NGUOI_CN = tt_nd.ma_nd;
                row.MAY_CN = tt_nd.may_cn;
                row.NHANVIEN_ID = tt_nd.nhanvien_id;
                row.DONVI_ID = tt_nd.donvi_id;
                row.NGAY_CN = tt_nd.ngay_cn;
                row.GHICHU = "Thay thế hóa đơn điện tử tự động PTTB";
                row.HDKH_ID = vhdkh_id;
                row.PHIEUTT_ID = vphieutt_id;
                row.LOAIHOADON_CU = objHoaDonDienTu.InvoicePattern;
                row.LOAIHOADON_MOI = objHoaDonDienTuEdit.InvoicePattern;
                row.SERI_CU = objHoaDonDienTu.SerialNo;
                row.SERI_MOI = objHoaDonDienTuEdit.SerialNo;
                row.SO_HD_CU = objHoaDonDienTu.InvoiceNo;
                row.SO_HD_MOI = objHoaDonDienTuEdit.InvoiceNo;
                row.SERI_MOI_ID = seri_id;
                row.TRANGTHAI = 1;
                row.BDHDDT_CHA_ID = bdhddt_cha_id;

                row.HINHTHUC_TT = "Tiền mặt/Chuyển khoản";
                row.TIEN = tongtien_hddt;
                row.TIENBANGCHU = tbc;
                row.TYLE_VAT = 10;
                row.VAT = tongvat_hddt;

                dsBDHDDT.BD_HDDT.AddBD_HDDTRow(row);
                ecms.Insert(dsBDHDDT, DatabaseConstants.DB6);
                dsBDHDDTCT = this.TaoDuLieuBDCT(product);
                ecms.Insert(dsBDHDDTCT, DatabaseConstants.DB6);

                string str_update = " Update " + DatabaseConstants.DB2 + ".phieutt_hd ";
                str_update += " set hddt_fkey = '" + fkey_new + "', ";
                str_update += " loaihoadon_id = '" + loaihoadon_id + "',";
                str_update += " seri_id = '" + seri_id + "',";
                str_update += " seri = '" + seri + "',";
                str_update += " soseri = '" + sohoadon + "'";
                str_update += " Where phieutt_id  = " + vphieutt_id;
                new BangThamSo().ExeSQL(str_update);


                str_update = "";
                str_update += " Update " + DatabaseConstants.DB6 + ".bd_hddt ";
                str_update += " Set trangthai = 2, ";
                str_update += "     ngay_td = sysdate, ";
                str_update += "     fkey = '" + fkey_new + "', ";
                str_update += "     nguoi_td = '" + tt_nd.ma_nd + "'";
                str_update += " Where bdhddt_id = " + bdhddt_cha_id;
                bangts.ExeSQL(str_update);

                Fkey = "";
                fkey_new = "";
                return "OK";
            }
            else
            {
                return "ERR: Có lỗi trong quá trình xuất hóa đơn điện tử: " + kq;
            }
        }
        catch (ex)
        {
            return "ERR: " + ex;
        }
        #endregion
    }
    else if (vthaotac == 5) // Hủy hóa đơn
    {
        return "ERR";
    }
    else if (vthaotac == 6) // Xuất hóa đơn
    {
        //#region Xuất hóa đơn
        try
        {
            loi_het_seri = false;
            tongtien_hddt = 0;
            tongvat_hddt = 0;
            tong_hddt = 0;

            string sohoadon = "";
            string pattern = "";
            string seri = "";
            int loaihoadon_id = 0;
            int seri_id = 0;

            string str_pattern = "";
            str_pattern += " select * ";
            str_pattern += " from ( ";
            str_pattern += " select a.loaihoadon_id, a.ten_loaihd, b.seri_id, b.seri ";
            str_pattern += " from " + DatabaseConstants.DB2 + ".loai_hoadon a, " + DatabaseConstants.DB2 + ".seri_hd b, " + DatabaseConstants.DB2 + ".loaihoadon_seri c ";
            str_pattern += " where a.loaihoadon_id = c.loaihoadon_id ";
            str_pattern += "     and b.seri_id = c.seri_id ";
            str_pattern += "     and a.kieu = 2 ";
            str_pattern += "     and b.kieu = 2 ";
            str_pattern += " and b.sudung = 1 ";
            str_pattern += "     and b.trangthai = 1 ";
            str_pattern += " order by b.thutu) ";
            str_pattern += " where rownum = 1 ";

            DataSet ds_hd_seri = new DataSet();
            ds_hd_seri = new BangThamSo().getDataFromSQL(str_pattern, "lhd_sr");
            if (ds_hd_seri.Tables[0].Rows.Count > 0)
            {
                loaihoadon_id = Convert.ToInt32(ds_hd_seri.Tables[0].Rows[0]["loaihoadon_id"]);
                pattern = ds_hd_seri.Tables[0].Rows[0]["ten_loaihd"];
                seri_id = Convert.ToInt32(ds_hd_seri.Tables[0].Rows[0]["seri_id"]);
                seri = ds_hd_seri.Tables[0].Rows[0]["seri"];
            }
            else
            {
                return "ERR: Không tìm thấy loại hóa đơn, seri !";
            }
            string kq1 = invoice.UpdateCus_V2(GetInvoiceXml_Cus(vten_kh, vma_gd, vmst, vdiachi_kh, vten_nh, vstk, vemail, vsodt, vloai_kh, vfax), tt_nd.user_hddt, tt_nd.pass_hddt, 0);
            if (kq1 != "ok")
            {
                return "ERR: Có lỗi trong quá trình cập nhật thông tin khách hàng : " + kq1;
            }

            string kq = invoice.ImportAndPublishInv_V2(tt_nd.user_hddt_td, tt_nd.pass_hddt_td,
                GetInvoiceXml(vten_kh, vma_gd, vmst, vdiachi_kh, vstk, vsodt, vhttt, product),
                tt_nd.user_hddt, tt_nd.pass_hddt, pattern, seri, 0);
            string[] result = kq.Split(':');
            if (result[0] == "OK")
            {
                string[] result1 = result.ToArray()[1].Split(';');
                string InvoicePattern1 = result1.ToArray()[0];
                string[] result2 = result1.ToArray()[1].Split('-');
                string SerialNo1 = result2.ToArray()[0];
                string[] result3 = result2.ToArray()[1].Split('_');
                string InvoiceNo1 = result3.ToArray()[1];

                string kq2 = "";
                kq2 = invoice.confirmPaymentFkey_V2(Fkey, tt_nd.user_hddt, tt_nd.pass_hddt, InvoicePattern1, SerialNo1);
                if (kq2 != "OK:")
                {
                    return "ERR: Lỗi thanh toán HĐĐT : " + kq2;
                }

                if (loi_het_seri)
                {
                    // code insert vào 2 bảng seri_hd và loaihoadon_seri
                    // update thứ tự, trạng thái vào bảng seri_hd
                }

                string str_update = " Update " + DatabaseConstants.DB2 + ".phieutt_hd ";
                str_update += " set hddt_fkey = '" + Fkey + "', ";
                str_update += " loaihoadon_id = '" + loaihoadon_id + "',";
                str_update += " seri_id = '" + seri_id + "',";
                str_update += " seri = '" + SerialNo1 + "',";
                str_update += " soseri = '" + InvoiceNo1 + "',";
                str_update += " hddt_status = '" + kq2 + "'";
                str_update += " Where phieutt_id  = " + vphieutt_id;
                new BangThamSo().ExeSQL(str_update);

                string invToken = InvoicePattern1 + ";" + SerialNo1 + ";" + InvoiceNo1;
                string xml = invoice.downloadInvNoPay_V2(invToken, tt_nd.user_hddt, tt_nd.pass_hddt);
                if (xml.StartsWith("ERR:"))
                    return "ERR: Có lỗi cập nhật biến động hóa đơn điện tử: " + xml;
                else
                {
                    XmlDocument doc = new XmlDocument();
                    doc.LoadXml(xml);
                    XmlNode xnList = doc.SelectNodes("/Invoice/Content").Item(0);
                    XmlDocument doc1 = new XmlDocument();
                    doc1.LoadXml("<Content>" + xnList.InnerXml + "</Content>");
                    objHoaDonDienTu = (Content)ObjectToXML(doc1.InnerXml, typeof(Content));
                    objHoaDonDienTuEdit = (Content)ObjectToXML(doc1.InnerXml, typeof(Content));
                }

                dsBDHDDT = new BD_HDDT_DATA();
                BD_HDDT_DATA.BD_HDDTRow row = dsBDHDDT.BD_HDDT.NewBD_HDDTRow();
                BDHDDT_ID = new GetKeyFacade().GetKey("BDHDDT_ID");
                row.BDHDDT_ID = BDHDDT_ID;
                row.MA_TT = objHoaDonDienTuEdit.CusCode;
                row.KIEU_BD = 5;
                row.THONGTINHD_CU = GetXMLFromObject(objHoaDonDienTu);
                row.THONGTINHD_MOI = GetXMLFromObject(objHoaDonDienTuEdit);
                row.TONGTIEN = tong_hddt;
                row.NGUOI_CN = tt_nd.ma_nd;
                row.MAY_CN = tt_nd.may_cn;
                row.NGAY_CN = tt_nd.ngay_cn;
                row.NHANVIEN_ID = tt_nd.nhanvien_id;
                row.DONVI_ID = tt_nd.donvi_id;
                row.GHICHU = "Tạo hóa đơn điện tử tự động PTTB";
                row.HDKH_ID = vhdkh_id;
                row.PHIEUTT_ID = vphieutt_id;
                row.LOAIHOADON_CU = objHoaDonDienTu.InvoicePattern;
                row.LOAIHOADON_MOI = objHoaDonDienTuEdit.InvoicePattern;
                row.SERI_CU = objHoaDonDienTu.SerialNo;
                row.SERI_MOI = objHoaDonDienTuEdit.SerialNo;
                row.SO_HD_CU = objHoaDonDienTu.InvoiceNo;
                row.SO_HD_MOI = objHoaDonDienTuEdit.InvoiceNo;
                row.SERI_MOI_ID = seri_id;
                row.TRANGTHAI = 1;

                row.HINHTHUC_TT = "Tiền mặt/Chuyển khoản";
                row.TIEN = tongtien_hddt;
                row.TIENBANGCHU = tbc;
                row.TYLE_VAT = 10;
                row.VAT = tongvat_hddt;

                dsBDHDDT.BD_HDDT.AddBD_HDDTRow(row);
                ecms.Insert(dsBDHDDT, DatabaseConstants.DB6);
                dsBDHDDTCT = this.TaoDuLieuBDCT(product);
                ecms.Insert(dsBDHDDTCT, DatabaseConstants.DB6);

                Fkey = "";
                return "OK";
            }
            else
            {
                //if (kq.StartsWith("ERR:20"))
                //{
                //    string lay_seri = invoice.getSerialByPattern_V2(tt_nd.user_hddt_td, tt_nd.pass_hddt_td, pattern);
                //    if (lay_seri.StartsWith("ERR"))
                //    {
                //        return "ERR: Lỗi lấy seri điện tử ";
                //    }
                //    else
                //    {
                //        string[] result_s = lay_seri.Split(';');
                //        seri = result_s[0];
                //        loi_het_seri = true;
                //        //goto kichhoat;
                //    }
                //}
                //else
                return "ERR: Có lỗi trong quá trình xuất hóa đơn điện tử: " + kq;
            }
        }
        catch (Exception ex)
        {
            return "ERR: " + ex;
        }
        #endregion
    }
    else
    {
        return "Sai thao tác !";
    }
  },
  TaoDuLieuBDCT: function(tb_hddt)
  {
      var dsBDHDDTCT = [];
      for (var i = 0; i < tb_hddt.length; i++)
      {
          if (tb_hddt[i]["PRODNAME"])
          {
              var row = {};
              row.BDHDDT_ID = BDHDDT_ID;
              row.DONGIA = Math.abs(tb_hddt[i]["PRODPRICE"]);
              row.DONVITINH = tb_hddt[i]["PRODUNIT"];
              row.NOIDUNG = tb_hddt[i]["PRODNAME"];
              row.SOLUONG = tb_hddt[i]["PRODQUANTITY"]*1;
              row.STT = i + 1;
              row.THANHTIEN = Math.abs(tb_hddt[i]["AMOUNT"]*1);
              dsBDHDDTCT.push(row);
          }
      }
      return dsBDHDDTCT;
  }
}
