<template src="./index.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import ChonIP from "./Modal";
import DateTimeLib from "@/utils/DateTimeLib";
import Enum from "./enum";
import vdc from "./service/vdcService";
import vasc from "./service/vascService";
import vcc from "./service/vccService"
import HandoverTicketContent from '@/modules/contract/setup/HandoverTicket/HandoverTicket_content.vue'
import moment from "moment"
export default {
  components: { breadcrumb, ChonIP, HandoverTicketContent },
  name: "SyncDataDVGT", // Mockup EXTS/HOPDONG/DongBoVisaVascUpdate
  computed: {
    EnabledongBo: function () {
      let f = this.dsThueBao != null && this.dsThueBao.length > 0 && this.rad_kichhoat == 0 && !this.disableButton.dongBo
      console.log('EnabledongBo', f)
      return f;
    },
    EnablehoanCong: function () {
      let f = this.dsThueBao != null && this.dsThueBao.length > 0 && this.rad_kichhoat == 1 && !this.disableButton.hoanCong
      console.log('EnablehoanCong', f)
      return f
    },
    EnableHuyKichHoat: function () {
      let f = this.dsThueBao != null && this.dsThueBao.length > 0 && !this.disableButton.huyKichHoat
      return f
    },
    EnableGiaoPhieu: function () {
      let f = this.dsThueBao != null && this.dsThueBao.length > 0 && !this.disableButton.giaoPhieu
      return f
    }
  },
  watch: {
    giu_ip: function (val) {
      if (val) {
        this.isDisableTuDong = true;
        this.tudong = false;
        this.isDisableChonIp = true;
        this.diachi_ip = "";
      } else {
        this.isDisableTuDong = false;
        this.isDisableChonIp = false;
      }
    },
    tudong: function (val) {
      if (val) {
        this.diachi_ip = "";
        this.isDisableChonIp = true;
      } else {
        this.isDisableChonIp = false;
      }
    },
    rad_kichhoat: async function (val) {
      this.$root.showLoading(true);
      this.Lay_TrangThai_HD();
      await this.HienThiDanhSachTB();
      this.disableButton.hoanCong = val == 0 ? false : true;
      this.disableButton.dongBo = !this.disableButton.hoanCong;
      this.$root.showLoading(false);
    },
    diachi_ip: function (val) {
      if (val != "")
        this.isErrorIp = false;
      else
        this.isErrorIp = true;
    },
  },
  destroyed() { },
  methods: {
    //#region Hàm con
    HienThiDanhSachTB: async function () {
      this.lblTrangThaiMyTV = ''
      this.dsLyDoHuy = []
      this.lvwDVGT = []
      await this.Lay_ds_hdtb_gd_kichhoat();
      if (this.dsThueBao && this.dsThueBao.length > 0) {
        this.disableButton.dongBo = !(this.rad_kichhoat == 0);
        console.log('dongBo', this.disableButton.dongBo)
        this.disableButton.hoanCong = !(this.rad_kichhoat == 1);
      } else {
        this.thongTinKH.ma_gd = '';
        this.thongTinKH.ngay_yc = '';
        this.thongTinKH.ma_kh = '';
        this.thongTinKH.ten_kh = '';
        this.thongTinKH.diachi_kh = '';
        this.thongTinKH.ghichu = '';
        this.txtAccountCu = '';
        this.disableButton.dongBo = true;
        this.disableButton.hoanCong = true;
        this.disableButton.huyKichHoat = true;
      }
    },
    HienThiGiaoDien: async function (quytrinh_id) {
      try {
        console.log('HienThiGiaoDien')
        this.showGiaoPhieu = false;
        this.showHoanCong = false;
        let rs = await this.$root.context.get(	
          "/web-hopdong/dongbo-visa-vasc/lay_danhsach_thaotac/" + quytrinh_id	
        );
        console.log('dsThaoTac', rs.data)
        this.dsThaoTac = rs.data
        if (this.dsThaoTac != null && this.dsThaoTac.length > 0) {
          for (let i = 0; i < this.dsThaoTac.length; i++) {
            let temp = await this.lay_thaotac_control(this.dsThaoTac[i].thaotac_id)
            if (temp != null && temp.length > 0) {
              if (temp.filter(x => x.control_name.toLowerCase().includes('giaophieu'))) {
                if (this.dsThaoTac[i].enable == 1) {
                  this.disableButton.giaoPhieu = false;
                  this.showGiaoPhieu = true;
                } else {
                  this.disableButton.giaoPhieu = true;
                }
              }
              if (temp.filter(x => x.control_name.toLowerCase().includes('kichhoat'))) {
                if (this.dsThaoTac[i].enable == 1)
                  this.disableButton.dongBo = false;
              } else {
                this.disableButton.dongBo = true;
              }
              if (temp.filter(x => x.control_name.toLowerCase().includes('hoancong'))) {
                if (this.dsThaoTac[i].enable == 1)
                  this.disableButton.hoanCong = false;
                this.showHoanCong = true;
              } else {
                this.disableButton.hoanCong = true;
              }
              if (temp.filter(x => x.control_name.toLowerCase().includes('huykh'))) {
                if (this.dsThaoTac[i].enable == 1)
                  this.disableButton.huyKichHoat = false;
                // this.showGiaoPhieu = true;
              } else {
                this.disableButton.huyKichHoat = true;
              }
            }
          }

        }

      } catch (error) {
        return false;
      } finally {
      }
    },
    lay_thaotac_control: async function (thaotac_id) {
      let rs = await this.$root.context.get(	
        "/web-hopdong/dongbo-visa-vasc/lay_thactac_control/" + thaotac_id	
      );
      return rs.data;
    },    
    clearForm: function () {
      this.countRow = 0
      this.thongTinKH = {
        ma_gd: "",
        ma_kh: "",
        ten_kh: "",
        diachi_kh: "",
        ghichu: "",
        ngay_yc: DateTimeLib.toDateDisplay(new Date())
      };
      this.account = ""
      this.txtAccountCu = ""
      this.password = ""
      this.diachi_ip = ""
      this.txtSLIP = ""
    },
    ChuanHoa_XML: function (str) {
      if (str == null || str == undefined || str.toString().trim() == "")
        return ""
      let result = "";
      result = str.toString().replace("&", "&amp");
      result = result.replace("<", "&lt");
      result = result.replace(">", "&gt");
      result = result.replace("'", "&apos");
      result = result.replace(String.fromCharCode(34), "&quot");
      return result;
    },
    //#endregion
    lay_thongtin_nguoidung: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-hopdong/hoanthien-hs-tachnhap-tbao/thongtin_nguoidung"
        );
        return rs.data[0]
      } catch (error) {
        return null;
      }
    },
    LayHuongGiao_TiepNhan: async function (loaihd_id, kieuld_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-hopdong/dongbo-visa-vasc/layhuonggiao_tiepnhan",
          {
            loaihd_id: loaihd_id,
            kieuld_id: kieuld_id
          }
        );
        return rs.data
      } catch (e) {
        return null
      }
    },
    LayMucCuocTB: async function (tmp_thuebao_id, kieu) {
      let ds = await this.LAY_THONGTIN_DBTHUEBAO(tmp_thuebao_id)
      if (kieu == 1) {
        return ds.mucuoctb_id;
      }
      else {
        return ds.mucuoc_tb;
      }
    },
    LAY_DS_DBKH_THEO_KHID: async function (khachhang_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-hopdong/dongbo-visa-vasc/lay_ds_dbkh_theo_khid/" + khachhang_id,
        );
        if (rs.data != null && rs.data.length > 0)
          return rs.data[0]
        return null
      } catch (e) {
        return null
      }
    },
    LAY_THONGTIN_DBTHUEBAO: async function (thuebao_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-hopdong/dongbo-visa-vasc/lay_thongtin_dbthuebao/" + thuebao_id,
        );
        if (rs.data != null && rs.data.length > 0)
          return rs.data[0]
        return null
      } catch (error) {
        return null
      }
    },
    ///Hàm Kích hoạt Đồng Bộ thuê bao
    DongBo: async function () {
      if (this.dsThueBao.length == 0 || this.currentItem == undefined || this.currentItem == null) {
        this.$root.toastError("Không có thuê bao để thực hiện kích hoạt!");
        return;
      }
      console.log("Item", this.currentItem);
      let loaitb_id = Number(this.currentItem.loaitb_id);
      let kieuld_id = this.currentItem.kieuld_id;
      let hdtb_id = this.currentItem.hdtb_id;
      let thuebao_id = this.currentItem.thuebao_id

      let lst_chon = this.hdtb_ids;
      if (lst_chon.length == 0) {
        this.$root.toastError("Hãy chọn phiếu để kích hoạt thuê bao");
        return;
      }
      if (!this.dongbo_test) {   
        //#region Thay đổi dịch vụ - 7
        if (this.loaihd_id == Enum.LoaiHopDong.THAY_DOI_DV) {
          if (kieuld_id == Enum.KieuLapDat.TD_CNTT) {
            this.$confirm(
              `Bạn có chắc chắn kích hoạt tạm dừng các Account này lên Visa/Vasc hay không`,
              "Tạm dừng dịch vụ",
              {
                confirmButtonText: "Có",
                cancelButtonText: "Không",
              }
            ).then(async () => {
              try {
                this.$root.showLoading(true);
                let kiemtra = true;
                let ds = this.$refs['grid-thuebao'].$refs.grid.getSelectedRecords()
                for (let i = 0; i < ds.length; i++) {
                  let account = ds[i].ma_tb;
                  let in_loaitb_id = parseInt(ds[i].loaitb_id);
                  let in_hdtb_id = parseInt(ds[i].hdtb_id);
                  let kt = await this.KichHoat_TamDung(in_loaitb_id, in_hdtb_id, thuebao_id)
                  if (!kt) {
                    kiemtra = false;
                    this.$root.toastError(
                      "Có lỗi kích hoạt thuê bao " + account
                    );
                    //break;
                    continue;
                  }
                }
                if (kiemtra) this.$root.toastSuccess("Kích hoạt tạm dừng thành công!");
                await this.HienThiDanhSachTB();
                this.$root.showLoading(false);
              }
              catch (e) {
                console.log(e);
                this.$root.toastError("Có lỗi xảy ra trong quá trình đồng bộ!");
              } finally {
                this.$root.showLoading(false);
              }
            });    
          }
          if (kieuld_id == Enum.KieuLapDat.MTD_CNTT) {
            this.$confirm(
              `Bạn có chắc chắn kích hoạt mở tạm dừng các Account này lên Visa/Vasc hay không`,
              "Mở tạm dừng dịch vụ",
              {
                confirmButtonText: "Có",
                cancelButtonText: "Không",
              }
            ).then(async () => {
              try {
                this.$root.showLoading(true);
                let kiemtra = true;
                let ds = this.$refs['grid-thuebao'].$refs.grid.getSelectedRecords()
                for (let i = 0; i < ds.length; i++) {
                  let account = ds[i].ma_tb;
                  let in_loaitb_id = parseInt(ds[i].loaitb_id);
                  let in_hdtb_id = parseInt(ds[i].hdtb_id);
                  let kt = await this.KichHoat_MoTamDung(in_loaitb_id, in_hdtb_id, thuebao_id)
                  if (!kt) {
                    kiemtra = false;
                    this.$root.toastError(
                      "Có lỗi kích hoạt thuê bao " + account
                    );
                    //break;
                    continue;
                  }
                }
                if (kiemtra) this.$root.toastSuccess("Kích hoạt mở tạm dừng thành công!");
                await this.HienThiDanhSachTB();
                this.$root.showLoading(false);
              }
              catch (e) {
                console.log(e);
                this.$root.toastError("Có lỗi xảy ra trong quá trình đồng bộ!");
              } finally {
                this.$root.showLoading(false);
              }
            });    
          } 
          if (kieuld_id == Enum.KieuLapDat.TD_DVGT_CNTT) {
            this.$confirm(
              `Bạn có chắc chắn kích hoạt thay đổi DVGT các Account này lên Visa/Vasc hay không`,
              "Thay đổi DVGT",
              {
                confirmButtonText: "Có",
                cancelButtonText: "Không",
              }
            ).then(async () => {
              try {
                this.$root.showLoading(true);
                let kiemtra = true;
                let ds = this.$refs['grid-thuebao'].$refs.grid.getSelectedRecords()
                for (let i = 0; i < ds.length; i++) {
                  let account = ds[i].ma_tb;
                  let in_loaitb_id = parseInt(ds[i].loaitb_id);
                  let in_hdtb_id = parseInt(ds[i].hdtb_id);
                  for (var tt_gvgt of this.lvwDVGT) {
                    let kt = false
                    if (tt_gvgt.kieu_yc == 1) {
                      kt = await this.KichHoat_DangKy_DVGT(in_loaitb_id, in_hdtb_id, thuebao_id, tt_gvgt)
                    } else {
                      kt = await this.KichHoat_HuyDangKy_DVGT(in_loaitb_id, in_hdtb_id, thuebao_id, tt_gvgt)
                    }                   
                    
                    if (!kt) {
                      kiemtra = false;
                      this.$root.toastError(
                        "Có lỗi kích hoạt thuê bao " + account
                      );
                      //break;
                      continue;
                    }
                  }                  
                }
                if (kiemtra) this.$root.toastSuccess("Kích hoạt thay đổi DVGT thành công!");
                await this.HienThiDanhSachTB();
                this.$root.showLoading(false);
              }
              catch (e) {
                console.log(e);
                this.$root.toastError("Có lỗi xảy ra trong quá trình đồng bộ!");
              } finally {
                this.$root.showLoading(false);
              }
            });    
          } 
        }
        //#endregion

        //#region Thanh lý - 4
        else if (this.loaihd_id == Enum.LoaiHopDong.CHAMDUT_SD) {
          this.$confirm(
            `Bạn có chắc chắn kích hoạt hủy các Account này lên Visa/Vasc hay không`,
            "Hủy dịch vụ",
            {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
            }
          ).then(async () => {
            try {
              this.$root.showLoading(true);
              let kiemtra = true;
              let ds = this.$refs['grid-thuebao'].$refs.grid.getSelectedRecords()
              for (let i = 0; i < ds.length; i++) {
                let account = ds[i].ma_tb;
                let in_loaitb_id = parseInt(ds[i].loaitb_id);
                let in_hdtb_id = parseInt(ds[i].hdtb_id);
                let kt = await this.KichHoat_ThanhLy(in_loaitb_id, in_hdtb_id, thuebao_id)
                if (!kt) {
                  kiemtra = false;
                  this.$root.toastError(
                    "Có lỗi kích hoạt thuê bao " + account
                  );
                  //break;
                  continue;
                }
              }
              if (kiemtra) this.$root.toastSuccess("Kích hoạt hủy dịch vụ thành công!");
              await this.HienThiDanhSachTB();
              this.$root.showLoading(false);
            }
            catch (e) {
              console.log(e);
              this.$root.toastError("Có lỗi xảy ra trong quá trình đồng bộ!");
            } finally {
              this.$root.showLoading(false);
            }
          });
        }
        //#endregion
      } else {
        try {
          this.$root.showLoading(true);
          await this.CAPNHAT_NGAYKH_NGAY_HT_V2(hdtb_id);
          await this.HienThiDanhSachTB();
          this.$root.toastSuccess(
            "Đã cập nhật dữ liệu thành công!"
          );
        }
        catch (e) {
          console.log(e);
          this.$root.toastError("Có lỗi xảy ra trong quá trình đồng bộ!");
        } finally {
          this.$root.showLoading(false);
        }
      }   
    },    
    KichHoat_ThanhLy: async function (in_loaitb_id, in_hdtb_id, vthuebao_id) {
      let status = false;
      try {
        //#region Hủy VNPT VCC
        if (in_loaitb_id == Enum.LoaiHinhTB.VNPT_Cloud_Contact_Center) {
          let db_khachhang = await this.lay_thongtin_dbkhachhang(in_hdtb_id);          
          let custid = ''
          let custtelnum = ''
          let feenumber = ''
          if (db_khachhang != null) {
            custid = db_khachhang[0]['custid']
            custtelnum = db_khachhang[0]['custtelnum'] ? db_khachhang[0]['custtelnum'] : db_khachhang[0]['feenumber']
            feenumber = db_khachhang[0]['feenumber']
          } else {
            this.$toast.error('Không lấy được thông tin khách hàng!')
            return false
          }
          var input = {
            TimeStamp: moment(new Date()).format('YYYY:MM:DD HH:mm:ss'), //* Thời gian truyền bản tin - Yyyy: mm: dd hh24: mi: ss - 2022:05:04 04:31:02,
            ActionType: 2, //* Value: 2
            FeeNumber: feenumber, //* Số đại diện trừ tiền (Số Hotline)
            CustID: custid, //* ID của khách hàng
            CustTelNum: custtelnum, //* Số Hotline tổng đài của khách hàng
          }
          var result = await vcc.HuyDichVu(input)
          if (result == 0) status = true
          else status = false
        }
        //#endregion
        if (status) {
          await this.CAPNHAT_NGAYKH_NGAY_HT_V2(in_hdtb_id)
        } else {
          return false;
        }
        return true;
      } catch (e) {
        console.log(e)
        this.$root.toastError("Đồng bộ thuê bao thất bại!");
        return false;
      }
    },
    KichHoat_TamDung: async function (in_loaitb_id, in_hdtb_id, vthuebao_id) {
      let status = false;
      try {
        //#region Tạm ngưng VNPT VCC
        if (in_loaitb_id == Enum.LoaiHinhTB.VNPT_Cloud_Contact_Center) {
          let db_khachhang = await this.lay_thongtin_dbkhachhang(in_hdtb_id);
          let custid = ''
          let custtelnum = ''
          let feenumber = ''
          if (db_khachhang != null) {
            custid = db_khachhang[0]['custid']
            custtelnum = db_khachhang[0]['custtelnum'] ? db_khachhang[0]['custtelnum'] : db_khachhang[0]['feenumber']
            feenumber = db_khachhang[0]['feenumber']
          } else {
            this.$toast.error('Không lấy được thông tin khách hàng!')
            return false
          }
          var input = {
            TimeStamp: moment(new Date()).format('YYYY:MM:DD HH:mm:ss'), //* Thời gian truyền bản tin - Yyyy: mm: dd hh24: mi: ss - 2022:05:04 04:31:02,
            ActionType: 4, //* Value: 4
            FeeNumber: feenumber, //* Số đại diện trừ tiền (Số Hotline)
            CustID: custid, //* ID của khách hàng
            CustTelNum: custtelnum, //* Số Hotline tổng đài của khách hàng
          }
          var result = await vcc.TamNgungDichVu(input)
          if (result == 0) status = true
          else status = false
        }
        //#endregion
        if (status) {
          await this.CAPNHAT_NGAYKH_NGAY_HT_V2(in_hdtb_id)
        } else {
          return false;
        }
        return true;
      } catch (e) {
        console.log(e)
        this.$root.toastError("Đồng bộ thuê bao thất bại!");
        return false;
      }
    },
    KichHoat_MoTamDung: async function (in_loaitb_id, in_hdtb_id, vthuebao_id) {
      let status = false;
      try {
        //#region Tạm ngưng VNPT VCC
        if (in_loaitb_id == Enum.LoaiHinhTB.VNPT_Cloud_Contact_Center) {
          let db_khachhang = await this.lay_thongtin_dbkhachhang(in_hdtb_id);
          let custid = ''
          let custtelnum = ''
          let feenumber = ''
          if (db_khachhang != null) {
            custid = db_khachhang[0]['custid']
            custtelnum = db_khachhang[0]['custtelnum'] ? db_khachhang[0]['custtelnum'] : db_khachhang[0]['feenumber']
            feenumber = db_khachhang[0]['feenumber']
          } else {
            this.$toast.error('Không lấy được thông tin khách hàng!')
            return false
          }
          var input = {
            TimeStamp: moment(new Date()).format('YYYY:MM:DD HH:mm:ss'), //* Thời gian truyền bản tin - Yyyy: mm: dd hh24: mi: ss - 2022:05:04 04:31:02,
            ActionType: 5, //* Value: 5
            FeeNumber: feenumber, //* Số đại diện trừ tiền (Số Hotline)
            CustID: custid, //* ID của khách hàng
            CustTelNum: custtelnum, //* Số Hotline tổng đài của khách hàng
          }
          var result = await vcc.HuyTamNgungDichVu(input)
          if (result == 0) status = true
          else status = false
        }
        //#endregion
        if (status) {
          await this.CAPNHAT_NGAYKH_NGAY_HT_V2(in_hdtb_id)
        } else {
          return false;
        }
        return true;
      } catch (e) {
        console.log(e)
        this.$root.toastError("Đồng bộ thuê bao thất bại!");
        return false;
      }
    },
    KichHoat_DangKy_DVGT: async function (in_loaitb_id, in_hdtb_id, vthuebao_id, tt_gvgt) {
      let status = false;
      try {
        //#region Tạm ngưng VNPT VCC
        if (in_loaitb_id == Enum.LoaiHinhTB.VNPT_Cloud_Contact_Center) {
          let db_khachhang = await this.lay_thongtin_dbkhachhang(in_hdtb_id);
          let custid = ''
          let custtelnum = ''
          let feenumber = ''
          if (db_khachhang != null) {
            custid = db_khachhang[0]['custid']
            custtelnum = db_khachhang[0]['custtelnum'] ? db_khachhang[0]['custtelnum'] : db_khachhang[0]['feenumber']
            feenumber = db_khachhang[0]['feenumber']
          } else {
            this.$toast.error('Không lấy được thông tin khách hàng!')
            return false
          }
          var input = {
            TimeStamp: moment(new Date()).format('YYYY:MM:DD HH:mm:ss'), //* Thời gian truyền bản tin - Yyyy:mm:dd hh24:mi:ss - 2022:05:04 04:31:02,
            ActionType: 10, //* Value: 10
            FeeNumber: feenumber, //* Số đại diện trừ tiền (Số Hotline)
            CustID: custid, //* ID của khách hàng - MA_KH
            CustTelNum: custtelnum, //* Số Hotline tổng đài của khách hàng
            AddonID: tt_gvgt.ma_dvgt, //* Loại phí dịch vụ bổ sung: 1: Phí tích hợp dịch vụ VCC, 2: Phí lưu trữ dữ liệu, 3: Phí xây dựng kịch bản AI
            AddonPrice: tt_gvgt.tongtien, //* Giá tiền dịch vụ bổ sung(do TTKD nhập theo giá thực tế)
            AddonCount: tt_gvgt.noidung ? tt_gvgt.noidung : 0, //* Số lượng dịch vụ bổ sung
            AddonInt: 2, //* Kỳ cước thu phí: 1: Một lần, 2: Hàng tháng
          }
          var result = await vcc.DangKyCapNhatDichVuBoSung(input)
          if (result == 0) status = true
          else status = false
        }
        //#endregion
        if (status) {
          await this.CAPNHAT_NGAYKH_NGAY_HT_V2(in_hdtb_id)
        } else {
          return false;
        }
        return true;
      } catch (e) {
        console.log(e)
        this.$root.toastError("Đồng bộ thuê bao thất bại!");
        return false;
      }
    },
    KichHoat_HuyDangKy_DVGT: async function (in_loaitb_id, in_hdtb_id, vthuebao_id, tt_gvgt) {
      let status = false;
      try {
        //#region Tạm ngưng VNPT VCC
        if (in_loaitb_id == Enum.LoaiHinhTB.VNPT_Cloud_Contact_Center) {
          let db_khachhang = await this.lay_thongtin_dbkhachhang(in_hdtb_id);
          let custid = ''
          let custtelnum = ''
          let feenumber = ''
          if (db_khachhang != null) {
            custid = db_khachhang[0]['custid']
            custtelnum = db_khachhang[0]['custtelnum'] ? db_khachhang[0]['custtelnum'] : db_khachhang[0]['feenumber']
            feenumber = db_khachhang[0]['feenumber']
          } else {
            this.$toast.error('Không lấy được thông tin khách hàng!')
            return false
          }
          var input = {
            TimeStamp: moment(new Date()).format('YYYY:MM:DD HH:mm:ss'), //* Thời gian truyền bản tin - Yyyy:mm:dd hh24:mi:ss - 2022:05:04 04:31:02,
            ActionType: 10, //* Value: 10
            FeeNumber: feenumber, //* Số đại diện trừ tiền (Số Hotline)
            CustID: custid, //* ID của khách hàng - MA_KH
            CustTelNum: custtelnum, //* Số Hotline tổng đài của khách hàng
            AddonID: tt_gvgt.ma_dvgt, //* Loại phí dịch vụ bổ sung: 1: Phí tích hợp dịch vụ VCC, 2: Phí lưu trữ dữ liệu, 3: Phí xây dựng kịch bản AI
          }
          var result = await vcc.HuyDangKyDichVuBoSung(input)
          if (result == 0) status = true
          else status = false
        }
        //#endregion
        if (status) {
          await this.CAPNHAT_NGAYKH_NGAY_HT_V2(in_hdtb_id)
        } else {
          return false;
        }
        return true;
      } catch (e) {
        console.log(e)
        this.$root.toastError("Đồng bộ thuê bao thất bại!");
        return false;
      }
    },
    lay_thongtin_dbkhachhang: async function (hdtb_id) {
      try {
        let rs = await vcc.lay_dulieu_huy_vcc(hdtb_id)
        return rs
      } catch (error) {
        return null
      }
    },
    CAPNHAT_NGAYKH_NGAY_HT_V2: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.post(
          "/web-hopdong/dongbo-visa-vasc/capnhat_ngaykh_ngay_ht_v2",
          {
            hdtb_id: hdtb_id,
          }
        );
        if (rs.error_code == "BSS-00000000")
          return true
        else {
          this.$root.toastError(rs.message);
          return false;
        }

      } catch (error) {
        return false;
      }
    },
    //#region Hàm lấy dữ liệu
    getDsHopDongPL: async function () {
      try {
        let rs = await this.$root.context.get(	
          "/web-hopdong/dongbo-visa-vasc/lay_ds_loai_hopdong"	
        );
        this.dsHDPL = rs.data.filter(e => e['loaihd_id'] == 4 || e['loaihd_id'] == 7);
        if (rs.data != null && rs.data.length > 0) {
          this.loaihd_id = Number(this.dsHDPL[0].loaihd_id);
          this.Lay_TrangThai_HD();
          await this.onChangeLoaiHD();
        }
      } catch (error) {
      }
    },
    getDsQuyTrinh: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-hopdong/dongbo-visa-vasc/lay_ds_quytrinh",
          {
            loai_hd_id: this.loaihd_id,
            dichvu_vt_id: this.dichvuvt_id,
          }
        );
        this.dsQuyTrinh = rs.data;
        if (rs.data != null && rs.data.length > 0) {
          this.quytrinh_id = this.dsQuyTrinh[0].quytrinh_id;
          await this.doChangeQuyTrinh();
        } else {
          this.$toast.error('Quy trình chưa được thiết lập, hãy liên hệ admin để xử lý!')
        }          
      } catch (error) {
        console.log(error)
      }
    },

    maGD_keyPress: async function (event) {
      let madg = this.thongTinKH.ma_gd == "" ? "0" : this.thongTinKH.ma_gd;
      try {
        this.$root.showLoading(true);
        await this.Lay_ds_hdtb_gd_kichhoat_magd(madg);
        await this.getHienThiNutBam();
      } catch (error) {
        console.log(error)
        this.$root.toastError("Không tìm thấy phiếu phù hợp với mã giao dịch");
      } finally {
        this.$root.showLoading(false);
      }
    },
    Lay_ds_hdtb_gd_kichhoat_magd: async function (ma_gd) {
      this.clearForm();
      let t_loaitb_id = 0;
      if (this.chk_loaihinh) {
        t_loaitb_id = this.loaitb_id;
      }

      let input = { 
        vloaihd_id: this.loaihd_id, 
        vtthd_id: this.tthd_id, //1 để có data view 
        vstatus: this.status, 
        vdichvuvt_id: this.dichvuvt_id, 
        vloaitb_id: t_loaitb_id, 
        vquytrinh_id: this.quytrinh_id, 
        vma_gd: ma_gd 
      }
      let rs = await this.$root.context.post("/web-thicong/dongbo_dichvu_vcc/lay_ds_hdtb_gd_kichhoat_ma_gd", input);
      console.log('Lay_ds_hdtb_gd_kichhoat_magd', input, rs)
      if (rs && (rs.error_code != "BSS-00000000" && rs.error_code != "BSS-00000204")) {
        this.$toast.error('Lỗi ' + rs.message_detail)
        return
      }
      this.dsThueBao = rs.data;
      if (rs.data != null && rs.data.length > 0)
        this.tongphieu = rs.data.length;
      else {
        this.tongphieu = 0
        this.$root.toastError("Không tìm thấy phiếu phù hợp với mã giao dịch");
      }
    },
    Lay_ds_hdtb_gd_kichhoat: async function () {
      try {
        this.clearForm();
        let t_loaitb_id = 0;
        if (this.chk_loaihinh) {
          t_loaitb_id = this.loaitb_id;
        }
        let input = {	
          loai_hd_id: this.loaihd_id,	
          dichvu_vt_id: this.dichvuvt_id,	
          tthd_id: this.tthd_id,	
          status: this.status,	
          loaitbi_id: t_loaitb_id,	
          quytrinh_id: this.quytrinh_id,	
        };	
        let rs = await this.$root.context.post(	
          "/web-hopdong/dongbo-visa-vasc/lay_ds_hdtb_gd_kichhoat",	
          input	
        );
        console.log('Lay_ds_hdtb_gd_kichhoat', input, rs)
        if (rs && (rs.error_code != "BSS-00000000" && rs.error_code != "BSS-00000204" && rs.error_code != "0")) {
          this.$toast.error('Lỗi ' + rs.message_detail)
          return
        }
        this.dsThueBao = rs.data;
        if (rs.data && rs.data.length > 0)
          this.tongphieu = rs.data.length;
        else {
          this.tongphieu = 0
        }
        // this.dsThueBao = rs.data;
        //  this.tongphieu = rs.data.length;

      } catch (error) {
        this.$root.toastError("Không tìm thấy phiếu phù hợp");
      }
    },
    Lay_TrangThai_HD: function () {
      if (this.rad_kichhoat == "0") {
        if (this.loaihd_id == Enum.LoaiHopDong.CHAMDUT_SD)
          this.tthd_id = Enum.TrangThaiHD.MOI;
        else this.tthd_id = Enum.TrangThaiHD.THANH_TOAN;

        if (this.loaihd_id == Enum.LoaiHopDong.DOISO_TB ||
          this.loaihd_id == Enum.LoaiHopDong.DAT_MOI ||
          this.loaihd_id == Enum.LoaiHopDong.KHOIPHUC_SD)
          this.status = Enum.TRANGTHAI_DONGBO.DONGBO_LHD;
        else
          this.status = Enum.TRANGTHAI_DONGBO.CHUADONGBO;
      }
      else {
        this.status = Enum.TRANGTHAI_DONGBO.DONGBO_CM;
        if (this.loaihd_id == Enum.LoaiHopDong.CHAMDUT_SD)
          this.tthd_id = Enum.TrangThaiHD.MOI;
        else this.tthd_id = Enum.TrangThaiHD.THANH_TOAN;
      }
    },
    Lay_dv_bang_HDTB: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-hopdong/dongbo-visa-vasc/laydonvi_bang_hdtbid/" + hdtb_id
        );
        if (rs.data != null) return rs.data[0];
        else return null;
      } catch (error) {
        return null;
      }
    },
    Lay_ThamSo_MD: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-hopdong/dongbo-visa-vasc/lay_ds_thamso_md"
        );
        if (rs.data != null) {
          this.dsThamSo = rs.data;
          if (this.dsThamSo.length > 0) {
            let str_vtemp = "HUYGOI_KHI_HOANCONG_TL";
            let det = this.dsThamSo.filter(x => x.ma_ts == str_vtemp)

            if (det.length > 0) {
              let row = det[0];
              if (row.ten_ts.trim() == "1")
                this.huygoi_hoancong_tl = 1;
            }

            str_vtemp = "LHD_NHANTIN_DIABAN";
            det = this.dsThamSo.filter(x => x.ma_ts == str_vtemp)
            if (det.length > 0) {
              let row = det[0];
              if (row.ten_ts.toString().trim() == "1")
                this.nhantin_nhanvien_diaban = 1;
            }
          }
        }
      } catch (error) {
      }
    },
    Lay_DS_LapDat: async function () {
      try {
        let rs = await this.$root.context.get(	
          "/web-hopdong/dongbo-visa-vasc/lay_ds_kieu_ld",	
          {	
            loai_hd_id: this.loaihd_id,	
            loai_tbi_id: this.loaitb_id	
          }	
        );
        
        this.dsKieuLD = rs.data;
        if (rs.data && rs.data.length > 0) {
          this.kieuld_id = rs.data[0].kieuld_id;
        } 
      } catch (error) {
      }
    },
    LayDsDichVu_Vt: async function () {
      try {

        if (this.dsdichvuvt_id == null || this.dsdichvuvt_id == '') this.dsdichvuvt_id = '15';
        let rs = await this.$root.context.get(
          "/web-hopdong/dongbo-visa-vasc/lay_ds_dichvu_vt", {
          dichvu_vt_id: this.dsdichvuvt_id
        }
        );
        this.dsDichVu = rs.data;
        if (rs.data != null && rs.data.length > 0)
          this.dichvuvt_id = this.dsDichVu[0].dichvuvt_id;
      } catch (error) {
      }
    },
    xuLyTag: async function (tag) {
      try {
        this.tag = tag;
        console.log("this.tag:",this.tag)
        if (this.tag != null && this.tag != 'null') {
          if (this.tag.toString() != "") {
            let str = this.tag.toString();
            let words = str.split('+');
            if (words.length >= 1)
              this.tthd_id = parseInt(words[0]);
            if (words.length >= 2) {
              this.dsloaihd_id = words[1];
              this.dsloaihd_id = dsloaihd_id.replace(';', ',');
            }
            if (words.length >= 3) {
              this.dsdichvuvt_id = words[2];
              this.dsdichvuvt_id = dsdichvuvt_id.replace(';', ',');
            }
          }
          else {
            this.tthd_id = 2;
            this.dsloaihd_id = "1";
            this.dsdichvuvt_id = "1";
          }
        } else {
          this.tthd_id = 2;
          this.dsloaihd_id = "4,7,8,14";
          this.dsdichvuvt_id = "4";
        }
        console.log("this.dsloaihd_id:",this.dsloaihd_id)
      }
      catch (ex) {
        this.tthd_id = 2;
        this.dsloaihd_id = "4,7,8,14";
        this.dsdichvuvt_id = "4";
        console.log(ex)
      }
    },
    initForm: async function () {
      // await this.getTagForm()
      await this.Lay_ThamSo_MD()
      this.ngay_th = DateTimeLib.toDateDisplay(this.ngay_th);
      await this.LayDsDichVu_Vt(); // DV CNTT

      await this.getDsHopDongPL();
      // await this.getDsQuyTrinh();
      await this.Lay_DS_LoaiHinh(this.dichvuvt_id); // VNPT VCC
      await this.Lay_DS_LapDat();
      // await this.HienThiDanhSachTB();
      this.kt_load = true;
    },
    layDs: async function () {
      this.Lay_TrangThai_HD();
      await this.HienThiDanhSachTB();
    },
    //#endregion
    //#region Hàm chức năng
    PHAILAM: function (code) {
      if (this.dsThaoTac != null && this.dsThaoTac.length > 0) 
      {
        let dr = this.dsThaoTac.filter((x) => x.enable == 1 && x.code == code);
        if (dr.length > 0){
          return true;
        } 
        return false;
      } 
      else{
        return false;
      }
    },
    checkMapId_new: async function (map_id, no) {
      try {
        let rs = await this.$root.context.get(
          "/web-hopdong/hoanthien-hs-tachnhap-tbao/check_map_id", {
          map_id: map_id,
          no: no
        }
        );
        if (rs.data != null) {
          return rs.data[0].id
        }
      } catch (error) {
        return 0
      }
    },
    HienThiDVGT: function () {
      this.lvwDVGT = [];
      if (this.dsDichVuDK.length > 0) {
        this.lvwDVGT = this.dsDichVuDK.map(e => ({
          ...e,
          id: e.dichvugt_id,
          kieu_yc1: e.kieu_yc == 1 ? "Đăng ký" : "Hủy",
          tongtien: Number(e.cuoc_sd) + Number(e.vat_sd)
        }))
      }
      console.log('HienThiDVGT', this.lvwDVGT)
    },
    LAY_DS_DANGKY_DVGT: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-hopdong/dongbo-visa-vasc/lay_ds_dangky_dvgt/" + hdtb_id
        );
        console.log('LAY_DS_DANGKY_DVGT', rs)
        this.dsDichVuDK = rs.data;
      } catch (error) {
      }
    },
    Lay_DS_LyDoHuy_HDTB: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-hopdong/dongbo-visa-vasc/lay_ds_lydohuy_hdtb/" + hdtb_id
        );
        return rs.data;
      } catch (error) {
      }
    },
    Lay_DS_LoaiHinh: async function (dvvt_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-hopdong/dongbo-visa-vasc/lay_ds_loaihinh/" + dvvt_id
        );
        this.dsLoaiHinh = rs.data.filter(e => e['loaitb_id'] == Enum.LoaiHinhTB.VNPT_Cloud_Contact_Center);
        if (rs.data) {
          this.loaitb_id = Enum.LoaiHinhTB.VNPT_Cloud_Contact_Center// rs.data[0].loaitb_id;
        }
      } catch (error) {
      } finally {
      }
    },
    chonIP: function (val) {

      if (val != null) this.diachi_ip = val;
    },
    // chuanHoaXML: function (str) {
    //   let result = "";
    //   result = str.replace("&", "&amp");
    //   result = result.replace("<", "&lt");
    //   result = result.replace(">", "&gt");
    //   result = result.replace("'", "&apos");
    //   result = result.replace(String.fromCharCode(34), "&quot");
    //   return result;
    // },
    //#endregion
    //#region "On Change"
    onChangeLoaiHD: async function (e) {
      this.$root.showLoading(true);
      this.loaihd_id = Number(this.loaihd_id)
      if (this.loaihd_id == Enum.LoaiHopDong.DOISO_TB)
        this.lblAccount = "Account mới";
      else this.lblAccount = "Account";
      await this.getDsQuyTrinh();
      await this.Lay_DS_LapDat();
      this.Lay_TrangThai_HD()
      //this.loaitb_id = 0;
      await this.HienThiDanhSachTB();
      this.$root.showLoading(false);
    },
    gridthuebao_selectedCheckbox: function (data) {
      if (data != null) this.hdtb_ids = data;
    },
    gridthuebao_PageChanged: function (data) { },
    onChangeQuyTrinh: async function () {
      try {
        this.$root.showLoading(true);
        await this.doChangeQuyTrinh();
      } catch (e) {

      } finally {
        this.$root.showLoading(false);
      }
    },
    doChangeQuyTrinh: async function () {
      try {
        if (this.dsQuyTrinh.length > 0) {
          this.huonggiao_id = await this.Laythongtin_table(this.quytrinh_id);
          console.log('huonggiao_id', this.huonggiao_id)
          if (!this.huonggiao_id || this.huonggiao_id == -1) {
            this.$root.toastError('Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại');
            await this.HienThiDanhSachTB();
            return;
          }
          await this.HienThiGiaoDien(this.quytrinh_id)
          this.Lay_TrangThai_HD()
          await this.HienThiDanhSachTB();
        }
      } catch (e) {
      }
    },
    gridldhuy_selectedRowChanged: function (item) {
      if (item != null) this.currentLydoHuy = item;
    },
    gridthuebao_selectedRowChanged: async function (item) {
      console.log('gridthuebao_selectedRowChanged', item)
      if (item) {
        try {
          this.$root.showLoading(true);
          Object.assign(this.currentItem, item);
          this.thongTinKH = {
            ma_gd: item.ma_gd,
            ma_kh: item.ma_kh,
            ten_kh: item.ten_kh,
            diachi_kh: item.diachi_kh,
            ghichu: item.ghichu,
            ngay_yc: DateTimeLib.toDateDisplay(item.ngay_yc),
          };
          this.account = this.ChuanHoa_XML(item.ma_tb);
          this.hdtb_id = item.hdtb_id;
          this.status = item.status;
          try {
            this.thuebao_id = item.thuebao_id;
          } catch (e) {
            this.thuebao_id = 0;
          }
          let dtTem = await this.Lay_dv_bang_HDTB(item.hdtb_id);
          if (dtTem != null) this.donvi_id = dtTem.donvi_id;
          this.loaitb_id = Number(item.loaitb_id);
          this.bras_id = item.bras_id;
          this.tocdo_cu_id = item.tdcu_id;
          //Loại DAT_MOI và KHOIPHUC_SD
          ////
          if (item.status == Enum.TRANGTHAI_DONGBO.DONGBO_CM) {            
            if (this.PHAILAM("GIAOPHIEU")) this.disableButton.giaoPhieu = false;
            else this.disableButton.giaoPhieu = true;
          } else {
            this.disableButton.giaoPhieu = true;
          }
          await this.Lay_DS_LapDat();
          this.kieuld_id = item.kieuld_id
          if (this.loaihd_id == Enum.LoaiHopDong.THAY_DOI_DV) {
            await this.LAY_DS_DANGKY_DVGT(this.hdtb_id);
            // Hiển thị ra gridView
            this.HienThiDVGT();
          } else if (this.loaihd_id == Enum.LoaiHopDong.CHAMDUT_SD) {
            let dtlydohuy = await this.Lay_DS_LyDoHuy_HDTB(this.hdtb_id);
            if (dtlydohuy != null && dtlydohuy.length > 0)
              this.dsLyDoHuy = [...dtlydohuy];
            this.phieu_id = item.phieu_id;
          }
          if (this.tthd_id == Enum.TrangThaiHD.THANH_TOAN) {
            this.phieu_id = item.phieu_id;
          }
        }
        catch (e) {

        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    showChonIp: function () {
      if (this.dsThueBao.length == 0) {
        this.$root.toastError('Danh sách phiểu đang trống')
        return;
      }
      if (!this.dongbo_test) {
        let bras_id = 0;
        bras_id = this.bras_id;
        let in_tocdo_id = this.tocdo_adsl_id;
        let data = {
          bras_id: bras_id,
          in_tocdo_id: in_tocdo_id,
        };
        this.$refs.chon_ip.setData(data);
        this.$root.$bvModal.show("popupTCTTTB");
      }
    },
    gridtbao_dataGridCellCreated(e) {
      let item = e.cellDataItem;
      e.cellAllowHtml = true;
      if (item.status == 1) {
        $(e.cellElement).prepend('<span class="icon one-zoom-default"></span>');
      } else if (item.status == 5) {
        $(e.cellElement).prepend(
          '<span class="ui-1_zoom-out nc-icon-outline"></span>'
        );
      } else if (item.status == 0) {
        $(e.cellElement).prepend('<span class="icon one-zoom-default"></span>');
      } else {
        $(e.cellElement).prepend('<span class="icon one-zoom-default"></span>');
      }
    },

    //#endregion
    //#region  Hàm tiện ích

    getValueFromIndex: function (obj, index) {
      try {
        return obj[Object.keys(obj)[index]];
      } catch (e) {
        return null;
      }
    },

    getTagForm: async function () {
      try {
        let tag = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/getTagValue', { id: 1235 })
        await this.xuLyTag(tag.data.TAGVALUE)
      } catch (error) {
      } finally {
      }
    },
    GiaoPhieu: async function () {
      try {
        if (this.thongTinKH.ma_gd != "") {
          let dtTemp = await this.checkMapId_new(this.quytrinh_id, 26);
          this.huonggiao_gp_id = dtTemp;
          if (dtTemp != 0) {
            this.$refs['formGiaophieu'].show();
          }
          else
            this.$root.toastError("Chưa có hướng giao được gán !");
        }
      }
      catch (ex) {
        this.$root.toastError("Có lỗi xảy ra trong quá trình giao phiếu");
      }
    },
    //#endregion
    onQueryCellInfoThueBao: async function (args) {
    },
    modalGiaoPhieu_Ok: async function () {
      await this.HienThiDanhSachTB();
    },
    modalGiaoPhieu_Show: function () {
      this.$refs['formgiaophieu'].frmGiaoPhieu('', 1, this.huonggiao_gp_id, 0, 0, '0');
    },
    okGiaoPhieu: function () {

    },
    Laythongtin_table: async function (quytrinh_id) {
      try {
        let res = await this.$root.context.get(
          "/web-hopdong/dongbo-visa-vasc/Laythongtin_table/" + quytrinh_id

        );
        return res.data;
      } catch (e) {
        return -1;
      }
    },
    addMonths: function (so) {
      var myDate = new Date();
      myDate.setMonth(myDate.getMonth() + so);
      return DateTimeLib.toDateDisplayWithFormat(myDate, "yyyyMM");
    },
    async KIEMTRA_THAM_SO_DONGBO(keyname) {
      try {
        let kq = await this.$root.context.get("/web-ccdv/hoanthienhschuyenquyen/kiemtra_open_sync_vina", {
          keyname: keyname
        })
        if (kq.data && kq.data.length > 0) {
          return kq.data[0].KEY_VALUE
        } else {
          return false
        }
      } catch (e) {
        return false;
      }
    },
    async getHienThiNutBam() {
      if (this.dsThaoTac != null && this.dsThaoTac.length > 0) {
        for (let i = 0; i < this.dsThaoTac.length; i++) {
          let temp = await this.lay_thaotac_control(this.dsThaoTac[i].thaotac_id)
          if (temp != null && temp.length > 0) {
            if (temp.filter(x => x.control_name.toLowerCase().includes('giaophieu'))) {
              if (this.dsThaoTac[i].enable == 1) {
                this.disableButton.giaoPhieu = false;
                this.showGiaoPhieu = true;
              } else {
                this.disableButton.giaoPhieu = true;
              }
            }
            if (temp.filter(x => x.control_name.toLowerCase().includes('kichhoat'))) {
              if (this.dsThaoTac[i].enable == 1)
                this.disableButton.dongBo = false;
            } else {
              this.disableButton.dongBo = true;
            }            
          }
        }

      }
    },
    cbo_loaihinh_changed: async function (args) {
      try {
        console.log('cbo_loaihinh_changed',args)
        this.$root.showLoading(true);
        if (!(this.loaitb_id == null || this.loaitb_id == undefined)) {
          if (this.chk_loaihinh) {
            await this.HienThiDanhSachTB();
          }
        }
      } catch (e) {
        console.log(e);
      } finally {
        this.$root.showLoading(false);
      }
    },    
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      await this.initForm();
      vasc.setupRoot(this.$root)
      vdc.setupRoot(this.$root)
      vcc.setupRoot(this.$root)
      // console.log('getThongTinNguoiDung',await this.$root.token.getThongTinNguoiDung())
      this.ttnd = await this.lay_thongtin_nguoidung()
      let login = {
        userId: this.ttnd.user_visa,
        password: this.ttnd.pass_visa
      }
      vdc.setLogin(login)
      this.ten_tat_ccbs = this.$root.context.user.getProperty("tentat_ccbs")
      this.dongbo_test = await this.KIEMTRA_THAM_SO_DONGBO('DONGBO_TEST');
      console.log('dongbo_test', this.dongbo_test)
    } catch (e) {
      console.log(e);
    } finally {
      this.$root.showLoading(false);
    }
  },
  data() {
    return {
      header: {
        title: 'Đồng bộ dịch vụ GTGT',
        list: [
          {
            name: 'Đồng bộ dịch vụ GTGT',
            link: { name: 'Ui.cards' },
            active: true
          }
        ]
      },
      selectionSettings: { type: 'Single'},
      currentItem: {},
      currentLydoHuy: {},
      dsThueBao: [],
      dsHDPL: [],
      dsMucCuoc: [],
      dsQuyTrinh: [],
      dsDichVu: [],
      dsLyDoHuy: [],
      dsLoaiHinh: [],
      dsThaoTac: [],
      dsTocDoADSL: [],
      dsThamSo: [],
      dsTocDoThucMoi: [],
      dsDichVuDK: [],
      lvwDVGT: [],
      dsHDKH: [],
      dsHDTB: [],
      ngay_th: new Date(),
      rad_kichhoat: 0,
      thuebao_id: -1,
      tongphieu: 0,
      loaitb_id: 0,
      hdtb_ids: [],
      hdtb_id: -1,
      donvi_id: -1,
      muccuoc_id: -1,
      tocdo_adsl_id: -1,
      tocdo_thucmoi_id: -1,
      tocdo_cu_id: -1,
      //loaihinh_id: -1,
      txtAccountCu: "",
      lblAccount: "Account",
      chkTucThi: false,
      txtSLIP: 0,
      diachi_ip: "",
      giu_ip: false,
      tudong: false,
      async_status: false,
      phieu_id: -1,
      ishowMyTV: false,
      hoancong_ngay: false,
      account: "",
      lblTrangThaiMyTV: "",
      huygoi_hoancong_tl: -1,
      nhantin_nhanvien_diaban: -1,
      dsKieuLD: [],
      kt_load: false,
      bras_id: -1,
      dichvuvt_id: 4,
      quytrinh_id: -1,
      loaihd_id: -1,
      tthd_id: 0,
      status: 0,
      date: {
        dateVal: Date,
        dateFormat: "dd/MM/yyyy",
      },
      thongTinKH: {
        ma_gd: "D1C/TL/221010/00078",
        ma_kh: "",
        ten_kh: "",
        diachi_kh: "",
        ghichu: "",
        ngay_yc: new Date(),
      },
      password: "",
      kieuld_id: -1,
      chk_loaihinh: true,
      isDisable: true,
      isDisableThayDoiTD: false,
      isDisableGiuIp: false,
      isDisableTuDong: false,
      isDisableChonIp: false,
      showHuyKH: true,
      showDongBo: true,
      showGiaoPhieu: false,
      showHoanCong: true,
      dongbo_test: false,
      isErrorIp: false,
      disableButton: {
        dongBo: false,
        hoanCong: true,
        giaoPhieu: true,
        huyKichHoat: true,
      },
      billingInfo: "",
      txtcuoctb: "",
      txtcuockh: "",
      txtcuoctg: "",
      txttlgiam: "",
      dtpngiam: "",
      txtthgiam: "",
      txtmienip: "",
      txtslemail: "",
      txtdgemail: "",
      txttlgiamemail: "",
      dtpngiamemail: "",
      txtthgiamemail: "",
      txttlgiamip: "",
      dtpngiamip: "",
      txtthgiamip: "",
      txtcuocmb: "",
      txtmbmphi: "",
      txtcuocmphi: "",
      txttiengiam: "",
      tag: "",
      dsloaihd_id: "",
      dsdichvuvt_id: "",
      countRow: 0,
      index_loaitb_id: 0,
      index_hdtb_id: 0,
      index_account: 0,
      huonggiaotn_id: 0,
      quytrinh_id_2: 0,
      hdkh_id_huygoi_mytv: 0,
      hdtb_id_huygoi_mytv: 0,
      ttnd: {},
      ten_tat_ccbs: '',
      huonggiao_gp_id: -1,
      huonggiao_id: 0,
      p_kieu: '0',
      p_magd: "",
      p_huonggiaoId: '-1',
      isPopup: true
    }
  },
};
</script>
<style>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>