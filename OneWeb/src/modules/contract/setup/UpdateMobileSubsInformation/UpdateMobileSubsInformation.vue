<template src="./UpdateMobileSubsInformation.html"></template>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
import "@/assets/vendor/jquery/split";
import moment from "moment";
import ModalChonDiaChi from "@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi";
import ChonCongTacVienModal from "@/modules/contract/setup/SurveyRequest/popups/ChonCongTacVien/ChonCongTacVienModal.vue";
import ChonNguoiGTModal from "@/modules/contract/setup/SurveyRequest/popups/ChonNguoiGT/ChonNguoiGTModal.vue";
export default {
  components: {
    breadcrumb,
    ModalChonDiaChi,
    ChonCongTacVienModal,
    ChonNguoiGTModal,
  },
  data: function () {
    return {
      header: {
        title: "Cập nhật thông tin thuê bao di động chuyển từ mạng khác",
      },
      list: [
        { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
        {
          name: "Lắp đặt mới",
          link: { name: "Ui.buttons" },
        },
      ],
      thuebao_id: 0,
      khachhang_id: 0,
      vhdkh_id: 0,
      vphieu_tv_id: 0,
      filterbox: {
        ctv: {
          value: "",
          ischecked: false,
          ma_ctv: "",
          ctv_id: 0,
        },
        nguoigt: {
          value: "",
          ischecked: false,
          nguoigt_id: "",
        },
      },
      cboGiayToKH: {
        data: [],
        value: 0,
      },
      cboNhomNN: {
        data: [],
        value: 0,
      },
      cboChuyenMang: {
        data: [],
        value: 0,
      },
      cboNguyenNhan: {
        data: [],
        value: 0,
      },
      dtDs: [],
      tsbtnNhapMoi: {
        enabled: true,
      },
      tsbtnGhiLai: {
        enabled: false,
      },
      tsbtnXoa: {
        enabled: false,
      },
      tsbtnHuyBo: {
        enabled: false,
      },
      txtMaTB: "",
      txtTenTB: "",
      txtDiaChiTB: "",
      thuebao_id: 0,
      txtGhiChu: "",
      khachhang_id: 0,
      txtMaKH: "",
      txtTenKH: "",
      txtSoGT: "",
      txtDiaChiKH: "",
      txtNoiCap: "",
      diachiTB: {
        DIACHI_ID: 0,
        DIACHI: null,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: null,
        KINHDO:"0.0",
        VIDO:"0.0",
        BLOCK:null,
        TANG:null,
        PHONG:null,
        MOTA:null,
      },
      dtpNgayCap: new Date(),
      today: new Date(),
      dtpNgaySinh: new Date(),
      DB_DDTT: [],
      dsDiaChi: [],
      
    };
  },
  validations: {},
  created() {
    this.HT_LoaiGT_Combobox();
    this.HT_DS_GRIDLOOKUP();
    this.LoadDs();
  },
  methods: {
    HT_LoaiGT_Combobox() {
      API.sp_chuyenmang_pi_danhmuc(this.axios, {
        p_nghiepvu: "chuyenmang_pi_loai_gt",
        p_ds_para: "",
      })
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            this.cboGiayToKH.data = response.data.data.map((x) => ({
              id: x.loaigt_id,
              text: x.loai_gt,
            }));
            this.cboGiayToKH.value = response.data.data[0].loaigt_id;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    HT_DS_GRIDLOOKUP() {
      API.lay_ds_nhom_nn_pi(this.axios)
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            this.cboNhomNN.data = response.data.data.map((x) => ({
              id: x.nhomnn_id,
              text: x.nhom_nn,
            }));
            this.cboNhomNN.value = response.data.data[0].nhomnn_id;
            this.HT_DS_NGUYENNHAN(this.cboNhomNN.value, 0);
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
      API.lay_ds_trangthai_pi(this.axios)
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            this.cboChuyenMang.data = response.data.data.map((x) => ({
              id: x.trangthai_id,
              text: x.trangthai,
            }));
            this.cboChuyenMang.value = response.data.data[0].trangthai_id;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    HT_DS_NGUYENNHAN(data, nguyennhanid) {
      API.lay_ds_nguyennhan_pi(this.axios, data)
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            this.cboNguyenNhan.data = response.data.data.map((x) => ({
              id: x.nguyennhan_id,
              text: x.nguyennhan,
            }));
            if (nguyennhanid != 0) {
              this.cboNguyenNhan.value = nguyennhanid;
            }
            this.cboNguyenNhan.value = response.data.data[0].nguyennhan_id;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    cboNhomNN_SelectedValueChanged(agrs) {
      this.HT_DS_NGUYENNHAN(agrs.id, 0);
    },
    LoadDs() {
      API.lay_ds_thuebao_pi(this.axios, this.$root.token.getNhanVienID())
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            this.dtDs = response.data.data;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },

    SetButton(kieu) {
      this.tsbtnNhapMoi.enabled = false;
      this.tsbtnGhiLai.enabled = false;
      this.tsbtnXoa.enabled = false;
      this.tsbtnHuyBo.enabled = false;

      if (kieu == -1) {
        //Bat dau
        this.$refs.inputMaTB.focus();
        this.tsbtnGhiLai.enabled = true;
        this.tsbtnHuyBo.enabled = true;
      }

      if (kieu == 0) {
        //Bat dau
        this.$refs.inputMaTB.focus();
        this.tsbtnNhapMoi.enabled = true;
        this.Clear();
      }

      if (kieu == 1) {
        //Them moi
        this.$refs.inputMaTB.focus();
        this.tsbtnGhiLai.enabled = true;
        this.tsbtnHuyBo.enabled = true;
        this.Clear();
      }

      if (kieu == 2) {
        //Huy
        this.$refs.inputMaTB.focus();
        this.tsbtnNhapMoi.enabled = true;
        this.tsbtnXoa.enabled = true;
        this.Clear();
      }

      if (kieu == 3) {
        //Edit
        this.tsbtnNhapMoi.enabled = true;
        this.tsbtnXoa.enabled = true;
        this.tsbtnGhiLai.enabled = true;
        this.tsbtnHuyBo.enabled = true;
      }
    },
    Clear() {
      this.txtMaTB = "";
      this.txtTenTB = "";
      this.txtDiaChiTB = "";
      this.thuebao_id = this.diachiTB.DIACHI_ID = this.khachhang_id = 0;
      this.txtGhiChu = "";

      this.txtMaKH = "";
      this.txtTenKH = "";
      this.txtSoGT = "";
      this.txtDiaChiKH = "";

      this.diachiTB.DIACHI = "";
      this.diachiTB.TINH_ID = 0;
      this.diachiTB.QUAN_ID = 0;
      this.diachiTB.PHUONG_ID = 0;
      this.diachiTB.PHO_ID = 0;
      this.diachiTB.AP_ID = 0;

      this.filterbox.ctv.value = null;
      this.filterbox.ctv.ma_ctv = null;
      this.filterbox.ctv.ctv_id = null;
      this.filterbox.nguoigt.value = null;
      this.filterbox.nguoigt.nguoigt_id = null;
      this.filterbox.ctv.ischecked=false;
      this.filterbox.nguoigt.ischecked=false;
      this.HT_LoaiGT_Combobox();
      this.HT_DS_GRIDLOOKUP();
    },
    tsbtnNhapMoi_Click() {
      this.SetButton(1);
    },
    async tsbtnGhiLai_Click() {
      if (!this.KiemTraDuLieuNhap()) return;
     // if(!this.KiemTraSoGT()) return;
      if (this.tsbtnNhapMoi.enabled == false) {
        await this.TaoDuLieu(true);
        this.loading(true);
        API.sp_capnhat_hd_chuyenmang_pi(this.axios, {
          p_kieu: 1, //them moi
          p_ds_para: "{ \"MA_TB\":\""+this.txtMaTB+"\", \"PHIEU_TV_ID\": 1212, \"NHANVIEN_ID\":\""+this.$root.token.getNhanVienID()+"\", \"DONVI_ID\":\""+this.$root.token.getDonViID()+"\", \"NGUOI_CN\":\""+this.$root.token.getUserName()+"\", \"MAY_CN\":\""+this.$root.token.getUserName()+"\", \"IP_CN\":\"admin\" }", 
          p_db_ddtt_data: JSON.stringify(this.DB_DDTT),
          p_diachi_data: JSON.stringify(this.dsDiaChi),
        })
          .then((response) => {
            if (response.data.error_code === "BSS-00000000"
            ) {
              this.LoadDs();
              this.$toast.success("Cập nhật công văn thành công !"); // chổ này sẽ bị bug do lỗi nhưng API trả ra susscess
              this.SetButton(3);
            }else{
                this.$toast.error(response.data.message)
            }
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {
            this.loading(false);
          });
      } else {
        await this.TaoDuLieu(false);
        this.loading(true);
        API.sp_capnhat_hd_chuyenmang_pi(this.axios, {
          p_kieu: 2, //cap nhat
        p_ds_para: "{ \"MA_TB\":\""+this.txtMaTB+"\", \"PHIEU_TV_ID\": 1212, \"NHANVIEN_ID\":\""+this.$root.token.getNhanVienID()+"\", \"DONVI_ID\":\""+this.$root.token.getDonViID()+"\", \"NGUOI_CN\":\""+this.$root.token.getUserName()+"\", \"MAY_CN\":\""+this.$root.token.getUserName()+"\", \"IP_CN\":\"admin\" }", 
          p_db_ddtt_data: JSON.stringify(this.DB_DDTT),
          p_diachi_data: JSON.stringify(this.dsDiaChi),
        })
          .then((response) => {
            if (response.data.error_code === "BSS-00000000"
            ) {
              this.LoadDs();
              this.$toast.success("Cập nhật công văn thành công !");
              this.SetButton(3);
            }else{
                this.$toast.error(response.data.messag)
            }
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {
            this.loading(false);
          });
      }
    },
    async TaoDuLieu(themmoi) {
      this.DB_DDTT = [];
      var rowdata = {};
      await this.TaoDuLieu_DiaChi(themmoi);
      if (themmoi) {
        await API.post_get_keys(this.axios, { keyname: "DB_THUEBAO" }).then(
          (response) => {
            if ( response.data.error_code === "BSS-00000000"
            ) {
              this.thuebao_id = parseInt(response.data.data);
            }
          }
        );
      }
      rowdata.PHANVUNG_ID = this.$root.token.getPhanVungID();
      rowdata.THUEBAO_ID = this.thuebao_id;
      if (this.khachhang_id > 0) {rowdata.KHACHHANG_ID = this.khachhang_id;}else{rowdata.KHACHHANG_ID=null}
      rowdata.MA_TB = this.txtMaTB;
      rowdata.TEN_TB = this.txtTenTB;
      rowdata.DIACHI_TB = this.diachiTB.DIACHI;
      rowdata.DIACHI_ID = this.diachiTB.DIACHI_ID;
      rowdata.LOAIGT_ID = this.cboGiayToKH.value;
      rowdata.SO_GT = this.txtSoGT;
      rowdata.GHICHU = this.txtGhiChu;
      rowdata.NGUOI_CN = this.$root.token.getUserName();
      rowdata.NGAY_CN = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
      rowdata.MAY_CN = this.$root.token.getUserName();
      rowdata.IP_CN = "admin";
      rowdata.NGAYSINH = moment(this.dtpNgaySinh).format("DD/MM/YYYY HH:mm:ss");
      rowdata.NGAYCAP = moment(this.dtpNgayCap).format("DD/MM/YYYY HH:mm:ss");
      rowdata.NOICAP = this.txtNoiCap;
      rowdata.CHUYENMANG = this.cboChuyenMang.value;
      rowdata.NGUYENNHAN_ID = this.cboNguyenNhan.value;
      if (this.filterbox.ctv.ctv_id > 0 && this.filterbox.ctv.ischecked){
             rowdata.CTV_ID = this.filterbox.ctv.ctv_id;
      }else
      {
         rowdata.CTV_ID=0; 
      }
      rowdata.NHANVIEN_ID = this.$root.token.getNhanVienID();
      rowdata.DONVI_ID = this.$root.token.getDonViID();
      
      if (this.filterbox.nguoigt.nguoigt_id > 0 &&
        this.filterbox.nguoigt.ischecked ){
            rowdata.NHANVIENGT_ID = this.filterbox.nguoigt.nguoigt_id;
        }else
        {
          rowdata.NHANVIENGT_ID=null;
        }
        
      if (this.vhdkh_id > 0) {
        rowdata.HDKH_ID = this.vhdkh_id;
      }else
      {
          rowdata.HDKH_ID = null;
      }
      this.DB_DDTT.push(rowdata);
    },
    async TaoDuLieu_DiaChi(themmoi) {
      this.dsDiaChi = [];
      var rowDiaChiTB = {};
     if (themmoi) {
        await API.post_get_keys(this.axios, { keyname: "DIACHI" }).then(
          (response) => {
            if (
              response.data.error_code === "BSS-00000000"
            ) {
              this.diachiTB.DIACHI_ID = parseInt(response.data.data);
            }
          }
        );
      }
      rowDiaChiTB.PHANVUNG_ID = this.$root.token.getPhanVungID();
      rowDiaChiTB.DIACHI_ID = this.diachiTB.DIACHI_ID;
      rowDiaChiTB.DIACHI = this.diachiTB.DIACHI;
      rowDiaChiTB.TINH_ID = this.diachiTB.TINH_ID;
      rowDiaChiTB.QUAN_ID = this.diachiTB.QUAN_ID;
      rowDiaChiTB.PHUONG_ID = this.diachiTB.PHUONG_ID;
      if (this.diachiTB.PHO_ID != 0) rowDiaChiTB.PHO_ID = this.diachiTB.PHO_ID;
      if (this.diachiTB.AP_ID != 0) rowDiaChiTB.AP_ID = this.diachiTB.AP_ID;
      if (this.diachiTB.KHU_ID != 0) rowDiaChiTB.KHU_ID = this.diachiTB.KHU_ID;
      rowDiaChiTB.SONHA = this.diachiTB.SO_NHA;
      if (this.diachiTB.DACDIEM_ID != 0)
        rowDiaChiTB.DACDIEM_ID = this.diachiTB.DACDIEM_ID;
      rowDiaChiTB.KINHDO = "0.0";
      rowDiaChiTB.VIDO = "0.0";
      rowDiaChiTB.BLOCK = null;
      rowDiaChiTB.TANG = null;
      rowDiaChiTB.PHONG = null;
      rowDiaChiTB.MOTA = null;

      this.dsDiaChi.push(rowDiaChiTB);
      //console.log("dsDiaChi: " + JSON.stringify(this.dsDiaChi));
    },
    tsbtnXoa_Click() {
      this.loading(true);
      API.sp_capnhat_hd_chuyenmang_pi(this.axios, {
        p_kieu: 3,
        p_ds_para: '{ "THUEBAO_ID":'+this.thuebao_id+', "DIACHI_ID":'+this.diachiTB.DIACHI_ID+' }',
        p_db_ddtt_data: "",
        p_diachi_data: "",
      })
        .then((response) => {
          if (response.data.error_code === "BSS-00000000"
          ) {
            this.LoadDs();
            this.$toast.success("Cập nhật xóa thành công !");
            this.SetButton(3);
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    tsbtnHuyBo_Click() {
      this.SetButton(0);
    },
    showpopupDiaChiTB() {
      this.$bvModal.show("popupDiaChiTB");
    },
    getDiaChi: function (data, datatype) {
      this[datatype].DIACHI = data.diachimoi;
      this[datatype].TINH_ID = data.tinh_id;
      this[datatype].QUAN_ID = data.quan_id;
      this[datatype].PHUONG_ID = data.phuong_id;
      this[datatype].PHO_ID = data.pho_id;
      this[datatype].AP_ID = data.ap_id;
      this[datatype].KHU_ID = data.khu_id;
      this[datatype].DACDIEM_ID = data.dacdiem_id;
      this[datatype].SO_NHA = data.so_nha;
    },
    getDiaChiTB: function (data) {
      this.getDiaChi(data, "diachiTB");
    },
    KiemTraDuLieuNhap() {
      if (this.txtMaTB == "") {
        this.$toast.error("Hãy nhập mã thuê bao !");
        this.$refs.inputMaTB.focus();
        return false;
      }

      if (this.txtMaTB.length != 9) {
        this.$toast.error(
          "Số thuê bao không đúng chuẩn, bạn hãy nhập lại (thuê bao phải nhập 9 số, không có đầu 0 hay đầu 84 ở đầu)!"
        );
        this.$refs.inputMaTB.focus();
        return false;
      }
      //Kiểm tra mã thuê bao
      if (this.txtTenTB == "") {
        this.$toast.error("Hãy nhập tên thuê bao !");
        this.$refs.inputTenTB.focus();
        return false;
      }
      if (
        this.diachiTB.DIACHI == "" ||
        this.diachiTB.QUAN_ID == 0 ||
        this.diachiTB.PHUONG_ID == 0
      ) {
        this.$toast.error("Hãy nhập địa chỉ thuê bao!");
        this.$refs.inputDiaChiTB.focus();
        return false;
      }
      if(this.txtSoGT!=""|| this.txtSoGT!=null){
          let kt = /^[a-zA-Z0-9]*$/.test(this.txtSoGT);
          if (!kt) {
            this.$toast.error(
              "Số giấy tờ không hợp lệ.Số giấy tờ gồm các ký tự từ 0->9a->z. Hãy kiểm tra lại"
            );
            this.$refs.inputSoGT.focus();
            return false;
          }
          this.KiemTraSoGT();
      
      }
      return true;
    },
     changeSoGiayTo(){
        if(this.txtSoGT!=null || this.txtSoGT!="")
        {
           this.KiemTraSoGT();
        }
      },
    async KiemTraSoGT() {
      let res = await this.axios.post("web-hopdong/capnhat-ttdd-chuyenmang-den-pi/sp_chuyenmang_pi_danhmuc", {
                          "p_nghiepvu": "CHUYENMANG_PI_KHACHHANG",
                          "p_ds_para": "{ \"SO_GT\":\""+this.txtSoGT+"\"}"
                          //031461412
             })
          if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
               
                   if (res.data.data !== undefined) {
                        this.txtMaKH = res.data.data[0]["ma_kh"];
                        this.txtTenKH = res.data.data[0]["ten_kh"];
                        this.txtDiaChiKH = res.data.data[0]["diachi_kh"];
                        this.khachhang_id = res.data.data[0]["khachhang_id"];
                        if (res.data.data[0]["loaigt_id"] != null)
                          this.cboGiayToKH.value = res.data.data[0]["loaigt_id"];
                      } else {
                        this.txtMaKH = "";
                        this.txtTenKH = "";
                        this.txtDiaChiKH = "";
                        this.khachhang_id = 0;
                      }
          }
          // else
          // {
          //    this.$toast.error("Số giấy tờ chưa có trong hệ thống. Vui lòng kiểm tra lại");
          // }
       return true;
      
    },
    ShowPopupCTV() {
      if (this.filterbox.ctv.ischecked) {
        this.$refs.chonCongTacVienModal.showModal();
      }
    },
    ShowPopupNguoiGT() {
      if (this.filterbox.nguoigt.ischecked) {
        this.$refs.chonNguoiGTModal.showModal();
      }
    },
    acceptCTV(data) {
      Object.assign(this.filterbox.ctv, data);
      this.filterbox.ctv.value =
        this.filterbox.ctv.ma_ctv + " - " + this.filterbox.ctv.ten_ctv;
      this.filterbox.ctv.ctv_id = this.filterbox.ctv.ctv_id;
    },
    acceptNguoiGT(data) {
      Object.assign(this.filterbox.nguoigt, data);
      this.filterbox.nguoigt.value =
        this.filterbox.nguoigt.ma_nguoi_gt +
        " - " +
        this.filterbox.nguoigt.ten_nguoi_gt;
      this.filterbox.nguoigt.nguoigt_id = this.filterbox.nguoigt.nguoi_gt_id;
    },
    async gridviewDanhSach_FocusedRowChanged(data) {
      //console.log(JSON.stringify(data));
      this.thuebao_id = data.thuebao_id;
      this.diachiTB.DIACHI_ID = data.diachi_id;
      if (data.khachhang_id != null) this.khachhang_id = data.khachhang_id;
      this.cboGiayToKH.value = data.loaigt_id;
      this.txtMaTB = data.ma_tb;
      this.txtTenTB = data.ten_tb;
      this.diachiTB.DIACHI = data.diachi_tb;
      this.txtTenKH = data.ten_kh;
      this.txtMaKH = data.ma_kh;
      this.txtSoGT = data.so_gt;
      this.txtGhiChu = data.ghichu;
      this.txtDiaChiKH = data.diachi_kh;
      this.txtNoiCap = data.noicap;
      if (data.chuyenmang != null) this.cboChuyenMang.value = data.chuyenmang;
      if (data.nhomnn_id != null) this.cboNhomNN.value = data.nhomnn_id;
      //if (data.nguyennhan_id != null) this.cboNguyenNhan = data.nguyennhan_id;
      await this.HT_DS_NGUYENNHAN(this.cboNhomNN.value, data.nguyennhan_id);
      await API.view_diachi(this.axios, data.diachi_id)
        .then((response) => {
          if ( response.data.error_code === "BSS-00000000"
          ) {
            this.diachiTB.TINH_ID = response.data.data[0].tinh_id;
            this.diachiTB.QUAN_ID = response.data.data[0].quan_id;
            this.diachiTB.PHUONG_ID = response.data.data[0].phuong_id;
            this.diachiTB.PHO_ID = response.data.data[0].pho_id;
            this.diachiTB.AP_ID = response.data.data[0].ap_id;
            this.diachiTB.KHU_ID = response.data.data[0].khu_id;
            this.diachiTB.DACDIEM_ID = response.data.data[0].dacdiem_id;
            this.diachiTB.SO_NHA = response.data.data[0].sonha;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
      if (data.ngaycap != null)
        this.dtpNgayCap = data.ngaycap;
      if (data.ngaysinh != null)
        this.dtpNgaySinh = data.ngaysinh;
      if (data.ctv_id != null) {
        this.filterbox.ctv.ischecked = true;
        this.filterbox.ctv.ctv_id = data.ctv_id;
        await API.fn_lay_tennv_theo_nhanvien_id(this.axios, data.ctv_id)
          .then((response) => {
            if (response.data.error_code === "BSS-00000000"
            ) {
              this.filterbox.ctv.value = response.data.data;
            }
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {});
      } else {
        this.filterbox.ctv.value = "";
        this.filterbox.ctv.ctv_id = 0;
        this.filterbox.ctv.ischecked = false;
      }
      if (data.nhanviengt_id != null) {
        this.filterbox.nguoigt.ischecked = true;
        this.filterbox.nguoigt.nguoigt_id = data.nhanviengt_id;
        await API.fn_lay_tennv_theo_nhanvien_id(this.axios, data.nhanviengt_id)
          .then((response) => {
            if ( response.data.error_code === "BSS-00000000"
            ) {
              this.filterbox.nguoigt.value = response.data.data;
            }
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {});
      } else {
        this.filterbox.nguoigt.value = "";
        this.filterbox.nguoigt.ischecked = false;
        this.filterbox.nguoigt.nguoigt_id = 0;
      }
      this.SetButton(3);
    },
  },
};
</script>
