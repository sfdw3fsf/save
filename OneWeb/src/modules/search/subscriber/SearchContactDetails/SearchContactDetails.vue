<template src="./SearchContactDetails.html"></template>
<style src="./style.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import VueFlatPickr from "vue-flatpickr-component";
import API from "./api";
import gridview from "@/components/Shared/gridview";
//CSS
import "@/assets/vendor/bootstrap-table/bootstrap-table.min.css";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css";
//JS
import "@/assets/vendor/jquery/split";
import "@/assets/vendor/bootstrap-table/bootstrap-table.min";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min";
import moment from "moment";
//
import state from "./define.js";
import ThongTinKhachHang from "./part/ThongTinKhachHang.vue";
import ThongTinThanhToan from "./part/ThongTinThanhToan.vue";
import ThongTinThueBao from "./part/ThongTinThueBao.vue";
import BangRongCoDinh from "./part/BangRongCoDinh.vue";
import MacDinh from "./part/MacDinh.vue";
import popupThietbiIDC from './popup/popupThietbiIDC.vue'
import ThongTinLienHe from "@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue"
import ThongTinEmail from "@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue"
import TraCuuVatTuThueBao from '@/modules/QLVT/components/TraCuuVatTuThueBao/TraCuuVatTuThueBao.vue'
import pop_toanha from '@/modules/search/subscriber/SearchInstallDetail/popup/ThongTinToaNha.vue'
import ThueBao_ToaNha from '@/modules/search/subscriber/SearchGeneral/popup/ThueBao_ToaNha.vue'
import KDatePicker from "@/components/kylq_components/KDatePicker.vue";
import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import frmDanhSachSoThanhVien from "@/modules/contract/setup/VoiceBrandname/part/DanhSachThanhVien.vue";
import frmNhapDSThanhVienNgoaiMang from '@/modules/contract/setup/ChangeContractOwner/popup/frmNhapDSThanhVienNgoaiMang.vue';
import frmHoSoEKYCSMCA from './popup/XemHSEkyc/PopupXemHoSoEkycDB.vue'

export default {
  components: {
    breadcrumb,
    frmHoSoEKYCSMCA,
    VueFlatPickr,
    gridview,
    API,
    ThongTinKhachHang,
    ThongTinThanhToan,
    ThongTinThueBao,
    MacDinh,
    BangRongCoDinh,
    ThongTinLienHe,
    ThongTinEmail,
    TraCuuVatTuThueBao,
    pop_toanha,
    KDatePicker,
    DataGridCustom,
    ThueBao_ToaNha,
    popupThietbiIDC,
    frmDanhSachSoThanhVien,
    frmNhapDSThanhVienNgoaiMang
  },
  name: "SearchContactDetails",
  created() {
    if (
      this.$route.params.tag != null &&
      this.$route.params.tag != "" &&
      this.$route.params.tag.length > 0
    ) {
      this.Tag = this.$route.params.tag;
      if (kieugoi_id == 0) {
        if (Tag.ToString() != "") this.kieugoi_id = parseInt(this.Tag);
        else this.kieugoi_id = 0;
      }
    }
  },
  mounted() {
    this.vform = this.DichVuVienThong.TATCA;
    this.init();
    // kiểm tra tag để load giao diện button
  },
  data() {
    return {
      showFrmNhapDSSoThanhvien: false,
      header: {
        title: "TRA CỨU CHI TIẾT THÔNG TIN DANH BẠ",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Lắp đặt mới",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      ...state,
      vform: 0,
      watcherActive: false,
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: "",
      popupComponentEvents: {
        "form-close": "popupClosed",
        acceptChangeCTV: "popupClosed",
        acceptChangeNGT: "popupClosed",
      },
    };
  },
  computed: {
    HdKhIdInt: function () {
      if (this.hdkh_id != null && this.hdkh_id != '') {
        return parseInt(this.hdkh_id)
      } else {
        return 0
      }
    },
    KhachHangIdInt: function () {
      if (this.khachhang_id == null || this.khachhang_id == '') {
        return 0
      }
      return parseInt(this.khachhang_id)
    },
  },
  methods: {
    async init() {
      //load cbx
      this.loading(true);
      this.watcherActive = false;
      var api1 = API.post_lay_danhmuc_lapmoi_tt_chung_v8(this.axios, {
        vchuoi:
          "MUCCUOC|DONVI_QL|LOAI_HD|LOAI_GT|LOAI_KH|TRANG_BI|LOAI_TB|CHU_QUAN|TIEN_KM|DOITUONG|DICHVU_VT|LOAIHINH_TB|LOAI_KHL|LOAI_GT|PHANLOAI_KH|LOAI_KH|NGANHANG|HINHTHUC_TT|HINHTHUC_TC|DANTOC|NGANHNGHE|QUOCTICH|HINHTHUC_TTHI|KIEU_HD|GOI_KT|DONVI_QL|KIEU_LD|NHACC_CU|CONGNGHE|HINHTHUC_CM|THOIHAN|LOAI_CSHT|DOITAC|LOAI_IP|PHANLOAI_DV",
      }).then((response) => {
        if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
          this.options.cbx_quoctich = response.data.data["quoctich"];
          this.options.cbx_nganhnghe = response.data.data["nganhnghe"];
          this.options.cbx_dantoc = response.data.data["dantoc"];
          this.options.cbx_loaikh = response.data.data["loai_kh"];
          this.options.cbx_nganhang = response.data.data["nganhang"];
          this.options.cbx_nganhangtt = response.data.data["nganhang"];
          this.options.cbx_dichvuvt = response.data.data["dichvu_vt"];
          //this.options.cbx_muccuoctb = response.data.data["muccuoc_tb"];
          this.options.cbx_doituong = response.data.data["doituong"];
          this.options.cbx_trangbi = response.data.data["trangbi"];
          this.options.cbx_thoihan = response.data.data["thoihan"];
          this.options.cbx_doitac = response.data.data["doitac"];
          this.options.cbx_chuquan = response.data.data["chu_quan"];
          this.options.data_phanloaikh = response.data.data["phanloai_kh"];

          this.form_TTTB.dichvuvt_id =  response.data.data["dichvu_vt"][0].dichvuvt_id
          this.changeDVVT(response.data.data["dichvu_vt"][0].dichvuvt_id)
        }
      });
      var api0 = API.MUCCUOC_TB(this.axios).then((response) => {
        if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {
          //this.options.tmp_cbx_muccuoctb = response.data.data;
          this.options.tmp_cbx_muccuoctb = response.data.data.map(i => {
                                            i.MUCUOCTB_ID = i.MUCUOCTB_ID//`${i.MA_NV} - ${i.TEN_NV}`
                                            i.MUCCUOC = `${i.MUCCUOC} - ${i.CUOC_TG}đ`
                                            return i
                                          })
        }
      });

      var api2 = API.CSS_LOAI_GT(this.axios).then((response) => {
        if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {
          this.options.cbx_dsloaigt = response.data.data;
        }
      });

      var api3 = API.lay_ds_cacloai_danhmuc(this.axios).then((response) => {
        if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data) {
          this.options.cbx_httt = response.data.data.dsHinhThucTT;
        }
      });

      var api4 = API.CSS_LOAI_HD(this.axios).then((response) => {
        if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {
          this.options.cbx_loaihd = response.data.data;
        }
      });

      var api5 = API.get_donvi_ql_tt(this.axios, {}).then((response) => {
        if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {
          this.options.cbx_donvitt = response.data.data;
        }
      });
      var api6 = API.lay_ds_thamso_md(this.axios, 0).then((response) => {
        if (
          response &&
          response.data &&
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          let str_vtemp = "DONVIQL_HTTT";
          let index = response.data.data.findIndex((x) => x.ma_ts == str_vtemp);
          if (index >= 0 && response.data.data[index].ten_ts == "1")
            this.DONVIQL_HTTT = 1;

          str_vtemp = "TUYENTHU_HTTT";
          index = response.data.data.findIndex((x) => x.ma_ts == str_vtemp);
          if (index >= 0 && response.data.data[index].ten_ts == "1")
            this.TUYENTHU_HTTT = 1;

          str_vtemp = "PHANTAP_KH";
          index = response.data.data.findIndex((x) => x.ma_ts == str_vtemp);
          if (index >= 0 && response.data.data[index].ten_ts == "1")
            this.phantap_kh = true;

          str_vtemp = "DN_KHONG_THU_TAI_DIACHILD";
          index = response.data.data.findIndex((x) => x.ma_ts == str_vtemp);
          if (index >= 0)
            this.DN_KHONG_THU_TAI_DIACHILD = response.data.data[index].ten_ts;

          str_vtemp = "THONGBAO_TRACUU";
          index = response.data.data.findIndex((x) => x.ma_ts == str_vtemp);
          if (index >= 0 && response.data.data[index].ten_ts == "1")
            this.THONGBAO_TRACUU = 1;

          str_vtemp = "HT_TOANHA";
          index = response.data.data.findIndex((x) => x.ma_ts == str_vtemp);
          if (index >= 0 && response.data.data[index].ten_ts == "1")
            visibles.tsbtnToaNha = true
        }
      });

      var api7 = API.getKyCuocHienHanh(this.axios).then((response) => {
        if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {
          this.kycuoc = response.data.data[0].KYCUOC
        }
      });
      var api8 = API.getDanhSachDonVi2(this.axios,
        {
          loaiDVId: 3,
          donViId: null
        }).then(response => {
        if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {
          this.options.cbx_donvi = response.data.data
        }

        });
      var api9 = API.CSS_MUCCUOC(this.axios).then((response) => {
        if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {
          this.options.cbx_muccuoc = response.data.data
        }
      });
      var api9 = API.THIETBI_IMS(this.axios).then((response) => {
        if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {
          this.options.cbx_thietbi_ims = response.data.data
        }
      });
      var api11 = API.getBras(this.axios,{}).then((response) => {
        if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {
          this.options.cbx_bras = response.data.data
        }
      });
      var api12 = API.CABMAN_LOAI_CAP(this.axios).then((response) => {
        if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {
          this.options.cbx_loaicap = response.data.data
        }
      });
      var api13 = API.CSS_LOAI_KENH(this.axios).then((response) => {
        if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {
          this.options.cbx_loaikenh = response.data.data
        }
      });
      var api14 = API.CSS_LOAITBI_TSL(this.axios).then((response) => {
        if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {
          this.options.cbx_loaitb = response.data.data
        }
      });

      var api17 = API.lay_ds_donvi_by_loaidv(this.axios, {donviId:0,loaidvId:5}).then((response) => {
        if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {
          this.options.cbx_donvi_dau = response.data.data
          this.options.cbx_donvi_cuoi = response.data.data
        }
      });
      var api18 = API.CSS_TOCDO_KENH(this.axios, {donviId:0,loaidvId:5}).then((response) => {
        if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {
          let temp =[]
          $.each(response.data.data, function (key, item) {
              temp.push({TOCDO_ID:item.TOCDO_ID,TT_TOCDO:item.TOCDO+' ' + item.DONVI})
            });
            this.options.cbx_tocdo_kenh = temp
        }
      });
      var api19 = API.CSS_LOAI_MODEM(this.axios).then((response) => {
        if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {
          this.options.cbx_loai_modem = response.data.data
        }
      });

      //gán loại node
      this.options.cbx_loainode = this.LOAI_NODE;

      Promise.all([api0,api1, api2, api3, api4, api5, api6, api7,api8,api9,api11,api12,api13,api14,api17,api18,api19])
        .then(() => {

        })
        .catch(() => {})
        .then(() => {

          this.HienThiGiaoDien(this.kieugoi_id)

          //this.getChuKyNoTheoKyCuoc(this.kycuoc.substring(0,4))
        })
        .finally(() => {
          this.loading(false);
          this.watcherActive = true;
        });
    },
    btnDSSoThanhVien_Click() {
      this.showFrmNhapDSSoThanhvien = true;
      this.$refs.frmDanhSachSoThanhVien.showModal();
    },

    btnDSSoThanhVienNgoaiMang_Click() {
      this.$refs.frmNhapDSThanhVienNgoaiMang.hdtb_id = 0;
      console.log("thuebao_id", this.form_TTTB.thuebao_id)
      this.$refs.frmNhapDSThanhVienNgoaiMang.thuebao_id = this.form_TTTB.thuebao_id;
      this.$refs.frmNhapDSThanhVienNgoaiMang.view_only = true;
      this.$refs.frmNhapDSThanhVienNgoaiMang.ShowDialog();
    },

    timkiem() {
      if (this.form_TTKH.ma_kh == null || this.form_TTKH.ma_kh.trim() == "") {
        return;
      }

      //get thông tin khách hàng by mã khách hàng
      this.lay_ds_dbkh_theo_makh(this.form_TTKH.ma_kh);
    },
    // tìm kiếm thông tin theo mã khách hàng/mã thuê bao
    lay_ds_dbkh_theo_makh(ma_kh) {
      this.searchTerm = ma_kh.trim()
      this.isClick = true
      this.clearFormKH()
      this.clearFormTT()
      this.clearFormTB()
      try {
        API.lay_ds_dbkh_theo_makh(this.axios, {
          maKh: this.searchTerm,
        }).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&&response.data.data.length>0) {
            this.khachhang_id = response.data.data[0].khachhang_id;
            let formData = response.data.data[0];
            for (let key in formData) {
              this.$set(this.form_TTKH, key, formData[key]);
            }
            this.form_TTKH.ten_kh = this.ChuanHoaTen(formData.ten_kh)
            this.form_TTKH.diachi_kh = this.ChuanHoaTen(formData.diachi_kh)
            if (formData.nguoi_dd && formData.nguoi_dd!= ""){
              this.form_TTKH.nguoi_dd = this.ChuanHoaTen(formData.nguoi_dd)
            }
            else this.form_TTKH.nguoi_dd = this.form_TTKH.ten_kh
            if (response.data.data[0].ngaylap_hd) {
              this.form_TTKH.ngaylap_hd = this.formatDate(
                new Date(response.data.data[0].ngaylap_hd)
              );
            }
            if (response.data.data[0].ngay_sn) {
              this.form_TTKH.ngay_sn = this.formatDate(
                new Date(response.data.data[0].ngay_sn)
              );
            }
            if (response.data.data[0].ngaycap) {
              this.form_TTKH.ngaycap = this.formatDate(
                new Date(response.data.data[0].ngaycap)
              );
            }
            if (response.data.data[0].ngayhethan_gt) {
              this.form_TTKH.ngayhethan_gt = this.formatDate(
                new Date(response.data.data[0].ngayhethan_gt)
              );
            }
            if (response.data.data[0].ngay_hk) {
              this.form_TTKH.ngay_hk = this.formatDate(
                new Date(response.data.data[0].ngay_hk)
              );
            }
            this.form_TTKH.d_tieunganh.TNC1_ID = response.data.data[0].tnc1_id
            this.form_TTKH.d_tieunganh.TNC2_ID = response.data.data[0].tnc2_id
            this.form_TTKH.d_tieunganh.TNC3_ID = response.data.data[0].tnc3_id
            this.form_TTKH.d_tieunganh.TNC4_ID = response.data.data[0].tnc4_id


            if (response.data.data[0].ma_kh.trim() == "1") this.kt_kylai = true;
            else this.kt_kylai = false;

            if (response.data.data[0].loaikh_id != "") {
              this.loaikh_id = response.data.data[0].loaikh_id;
            } else this.loaikh_id = 0;


            if (response.data.data[0].gioitinh!=0 && response.data.data[0].gioitinh!=1){
              this.form_TTKH.gioitinh=2
            }
            if (
              response.data.data[0].quoctich_id != null &&
              response.data.data[0].quoctich_id != ""
            ) {
              this.options.chkquoctich = true;
              this.options.enables.cbx_quoctich = true;
            } else {
              this.options.chkquoctich = false;
              this.options.enables.cbx_quoctich = false;
            }

            if (
              response.data.data[0].dantoc_id != null &&
              response.data.data[0].dantoc_id != ""
            ) {
              this.options.chkdantoc = true;
              this.options.enables.cbx_dantoc = true;
            } else {
              this.options.chkdantoc = false;
              this.options.enables.cbx_dantoc = false;
            }

            if (
              response.data.data[0].nganhang_id != null &&
              response.data.data[0].nganhang_id != ""
            ) {
              this.options.chknganhang = true;
            }

            if (
              response.data.data[0].phanloaikh_id != null &&
              response.data.data[0].phanloaikh_id != ""
            ) {
              let item_phanloaikh = this.options.data_phanloaikh.find(
                (item) =>
                  item.phanloaikh_id == response.data.data[0].phanloaikh_id
              );
              if (item_phanloaikh)
                this.form_TTKH.phanloai_kh = item_phanloaikh.ma_plkh;
            }
            //lấy thông tin thêm khách hàng
            this.lay_ds_hienthi_thongtin_dbkh(response.data.data[0].khachhang_id,response.data.data[0].phanloaikh_id,this.kycuoc)
            //lấy thông tin danh sách thanh toán
            this.dbtt_theo_khid(response.data.data[0].khachhang_id);

            //lấy thông tin danh sách thuê bao
            this.lay_ds_dbtb_theo_khid(response.data.data[0].khachhang_id);
          } else if (response.data.error_code == "BSS-00000204") {
            this.$toast.error(
              "Không tìm thấy thông tin! Bạn hãy nhập lại thông tin tìm kiếm"
            );
          } else {
            this.$toast.error(
              "Có lỗi xảy ra, vui lòng lin hệ Quản trị để được hỗ trợ!"
            );
          }
          this.loading(false)
        });
      } catch (error) {
        console.log(error)
      }
    },
    //lấy danh sách thanh toán theo khachhang_id
    dbtt_theo_khid(kh_id) {
      this.gvDSTT.list = [];
      try {
        API.dbtt_theo_khid(this.axios, {
          khachHangId: kh_id,
        }).then((response) => {

          if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {

            this.gvDSTT.list = response.data.data;
            for(var i in this.gvDSTT.list) this.gvDSTT.list[i].so_tt=(parseInt(i)+1).toString();
          } else if (response &&response.data &&response.data.error_code == "BSS-00000204") {
            //this.$toast.error('Không tìm thấy thông tin thanh toán của khách hàng.')
          } else {
            this.$toast.error(
              "Có lỗi xảy ra, vui lòng lin hệ Quản trị để được hỗ trợ!"
            );
          }
          this.loading(false);
        });
      } catch (error) {
        console.log(error)
      }
    },
    //lấy danh sách thuê bao theo khách hàng id
    lay_ds_dbtb_theo_khid(kh_id) {
      try {
        let pIndex = 1
        let pSize = this.$refs.gvDSTB.$refs.pagination.pageSizeData
        API.lay_ds_dbtb_theo_khid_page(this.axios, {
          //todo
          "khachhang_id": kh_id,
          "pageNum": pIndex,
          "pageRec": pSize,
          "totalRow": 1,
          "ma_tb": this.searchTerm,

        }).then((response) => {
          if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {
            this.gvDSTB.list = response.data.data
            this.totalRecords = response.data.page_info.record_count
            // let temp=response.data.data
            // let index = response.data.data.findIndex(
            //   (item) => item.ma_tb.trim() == this.searchTerm
            // );
            // if (index>-1){
            //   let row = temp[index]
            //   temp.splice(index, 1)
            //   temp.unshift(row)
            // }
            // this.gvDSTB.list = temp
          } else if (response &&response.data &&response.data.error_code == "BSS-00000204") {
              this.$toast.error("Không tìm thấy danh sách thuê bao theo khách hàng!");
          } else {
            this.$toast.error(
              "Có lỗi xảy ra, vui lòng lin hệ Quản trị để được hỗ trợ!"
            );
          }
          this.loading(false);
        });
      } catch (error) {
        console.log(error)
      }
    },
    //lấy thông tin đơn vị theo thuê bao id
    async lay_tt_dbtb_by_id(tb_id) {
      try {
        await API.lay_tt_dbtb_by_id(this.axios, {
          thuebaoId: tb_id,
        }).then((response) => {
          if (response &&response.data &&response.data.error_code === "BSS-00000000") {
            //hiển thị thông tin thuê bao tsl
            this.form_TTTB.db_con.donvi_id_dau = response.data.data.donvi_dau_id1
            this.form_TTTB.db_con.donvi_id_cuoi = response.data.data.donvi_cuoi_id1
            this.form_TTTB.db_con.donvi_id = response.data.data.donvi_id

          } else if (response &&response.data &&response.data.error_code == "BSS-00000204") {
          } else {
            this.$toast.error(
              "Có lỗi xảy ra, vui lòng lin hệ Quản trị để được hỗ trợ!"
            );
          }
        });
      } catch (error) {
        console.log(error)
      }
    },
    // hiển thị thông tin khách hàng
    async lay_ds_hienthi_thongtin_dbkh(khachhang_id,phanloaikh_id,kycuoc_chuky) {
      try {
        await API.lay_ds_hienthi_thongtin_dbkh(this.axios,
        {
        "khachhang_id": khachhang_id,
        "phanloaikh_id": phanloaikh_id,
        "kycuoc_chuky": kycuoc_chuky
        }).then(response => {
          if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.ds_diachi.length>0) {
            let formData = response.data.data.ds_diachi[0]
            for (let key in formData) {
              this.$set(this.form_TTKH.d_diachi_kh, key, formData[key])

              //load thông tin chi tiết địa chỉ chứng từ theo địa chỉ của khách hàng
              this.$set(this.form_TTTT.d_diachi_ct, key, formData[key])
            }
            this.form_TTKH.d_diachi_kh.so_nha = formData.sonha
            this.form_TTTT.d_diachi_ct.so_nha = formData.sonha

            //Lấy địa chỉ người đại diện theo địa chỉ khách hàng
            for (let key in formData) {
              this.$set(this.form_TTKH.d_diachi_ndd, key, formData[key])
            }
            if (this.form_TTKH.diachi_ndd==null || this.form_TTKH.diachi_ndd==''){
              this.form_TTKH.diachi_ndd = this.form_TTKH.diachi_kh
            }
            //this.form_TTKH.diachi_ndd.so_nha = formData.sonha

            if (response.data.data.diemtn && response.data.data.diemtn.length>0){
              console.log('dtnhiem '+response.data.data.diemtn[0])
              this.form_TTKH.diem_tn = response.data.data.diemtn[0]["diemtinnhiem"]
            }

            //data dropdown thanh toán
            this.options.cbx_thanhtoan = response.data.data.ds_thanhtoan

            //dữ liệu phân tập khách hàng
            if (this.phantap_kh && this.khachhang_id > 0 && response.data.data.p_out) {
            let ds_dt_dtn = response.data.data.p_out
            if (ds_dt_dtn) {
              if (ds_dt_dtn.doanhthu != "")
                this.pt_doanhthu = ds_dt_dtn.doanhthu;
              else this.pt_doanhthu = 0;

              if (ds_dt_dtn.dtn != "") this.pt_diem_tn = ds_dt_dtn.dtn;
              else this.pt_diem_tn = 0;
            } else {
              this.pt_doanhthu = 0;
              this.pt_diem_tn = 0;
            }
          }
        }

        });
      } catch (error) {
        console.log(error)
      }
    },
    //hiển thị thông tin thuê bao
    async thongtin_dbtb(thuebao_id, kieuld_id, dichvuvt_id, loaitb_id,thuebao_cha_id) {
      try {
        this.loading(true);        
        await API.thongtin_dbtb(this.axios, {
          thuebao_id: thuebao_id,
          kieuld_id: kieuld_id,
          dichvuvt_id: dichvuvt_id,
          loaitb_id: loaitb_id,
          mucuoctb_id: null,
          thuebao_cha_id: thuebao_cha_id,
          goikt_id: null,
        }).then((response) => {
          if (response &&response.data &&response.data.error_code === "BSS-00000000") {
            this.form_TTTB.khuvuc = response.data.data.khuvuc;
            this.form_TTTB.khuvuc_id_cuoi = response.data.data.khuvuc_id_cuoi;
            this.form_TTTB.huongketnoi_megawan = response.data.data.huongketnoi_megawan;
            this.form_TTTB.vrf = response.data.data.vrf;
            // this.form_TTTB.sothang_ck = response.data.data.sothang_ck;
            // this.form_TTTB.ngay_ktck = response.data.data.ngay_ktck;
            this.form_TTTB.goi_kt = response.data.data.goi_kt;
            this.form_TTTB.loaihd_id = response.data.data.loaihd_id;
            this.form_TTTB.capngoai_vnpt = response.data.data.capngoai_vnpt;            
            //this.form_TTTB.dv_congthem = response.data.data.ds_sudung_gt;
            if (response.data && response.data.data && response.data.data.ds_sudung_gt && response.data.data.ds_sudung_gt.length>0){
              let temp =''
              $.each(response.data.data.ds_sudung_gt, function (key, item) {
                temp = temp + item.ma_dvgt + '-' + item.ten_dvgt +'\n'
              });
              this.form_TTTB.dv_congthem = temp
            }

            if (response.data && response.data.data && response.data.data.ds_sudung_k && response.data.data.ds_sudung_k.length>0){
              let temp1 =''
              $.each(response.data.data.ds_sudung_k, function (key, item) {
                temp1 = temp1 + item.ma_dvkhac + '-' + item.ten_dvkhac +'\n'
              });
              this.form_TTTB.dv_khac = temp1
            }
            //this.form_TTTB.dv_khac = response.data.data.ds_sudung_k;            
            //load thông tin địa chỉ
            //địac chỉthuê bao
            if (response.data && response.data.data && response.data.data.ds_dc && response.data.data.ds_dc.length>0){
              let formData = response.data.data.ds_dc[0]
              for (let key in formData) {
                this.$set(this.form_TTTB.d_diachi_tb, key, formData[key])
              }
              this.form_TTTB.d_diachi_tb.so_nha = formData.sonha
              this.form_TTTB.d_diachi_tb.pho_id = (formData.pho_id && formData.pho_id != -1)? formData.pho_id : null
              this.form_TTTB.d_diachi_tb.ap_id = (formData.ap_id && formData.ap_id != -1)? formData.ap_id : null
              this.form_TTTB.d_diachi_tb.khu_id = (formData.khu_id && formData.khu_id != -1)? formData.khu_id : null
              this.form_TTTB.d_diachi_tb.dacdiem_id = (formData.dacdiem_id && formData.dacdiem_id != -1)? formData.dacdiem_id : null
              //địa chỉ lắp đặt
              this.form_TTTB.d_diachi_ld.tinh_id = formData.tinhld_id
              this.form_TTTB.d_diachi_ld.quan_id = formData.quanld_id
              this.form_TTTB.d_diachi_ld.phuong_id = formData.phuongld_id
              this.form_TTTB.d_diachi_ld.pho_id = (formData.phold_id && formData.phold_id != -1)? formData.phold_id : null
              this.form_TTTB.d_diachi_ld.ap_id = (formData.apld_id && formData.apld_id != -1)? formData.apld_id : null
              this.form_TTTB.d_diachi_ld.khu_id = (formData.khuld_id && formData.khuld_id != -1)? formData.khuld_id : null
              this.form_TTTB.d_diachi_ld.dacdiem_id = (formData.dacdiemld_id && formData.dacdiemld_id != -1)? formData.dacdiemld_id : null
              this.form_TTTB.d_diachi_ld.sonha = formData.sonha_ld
              this.form_TTTB.d_diachi_ld.kinhdo = formData.kinhdo_ld
              this.form_TTTB.d_diachi_ld.vido = formData.vido_ld
              this.form_TTTB.d_diachi_ld.so_nha = formData.sonha_ld
            }            
            //danh sách kiểu lắp đặt
            this.lay_ds_tt_theo_loaitb(loaitb_id, response.data.data.loaihd_id);


            //thông tin số chính + chi tiết một số dịch vụ
            this.form_TTTB.db_con.sochinh = response.data.data.ma_tb

            //thông tin megawan
            this.form_TTTB.db_con.huongketnoi_megawan = response.data.data.huongketnoi_megawan
            this.form_TTTB.db_con.vrf = response.data.data.vrf
            this.form_TTTB.db_con.capngoai_vnpt = response.data.data.capngoai_vnpt
            this.form_TTTB.db_con.diachi_pop = response.data.data.diachi_pop
            this.form_TTTB.db_con.quocgia_pop = response.data.data.quocgia_pop
            this.form_TTTB.db_con.huongut = response.data.data.huongut
            this.loading(false);
          }
        });
      } catch (error) {
        this.loading(false);
        console.log(error)

      }
      this.loading(false);
    },
    //lấy list kiểu lắp đặt
    async lay_ds_tt_theo_loaitb(loaitb_id, loaihd_id) {
      try {
        await API.lay_ds_tt_theo_loaitb(this.axios, {
          loaitbId: loaitb_id,
          loaihdId: loaihd_id,
        }).then((response) => {
          if (response &&response.data &&response.data.error_code === "BSS-00000000") {
            this.options.cbx_kieuld = response.data.data.dsKieuLd;
          }
        });
      } catch (error) {
        console.log(error)
      }
    },
    //hiển thị thông tin thanh toán
    async lay_ds_hienthi_dbtt(thanhtoan_id, ma_tt) {
      try {
        await API.lay_ds_hienthi_dbtt(this.axios, {
          thanhtoanId: thanhtoan_id,
          maTb: ma_tt
        }).then((response) => {
          if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data) {
            console.log(response)
            this.options.cbx_thuebao_dd = response.data.data.ds_madd
            this.form_TTTT.matt_ccbs = response.data.data.matt_ccbs

            if (response.data.data.ds_diachi.length>0){
              let formData = response.data.data.ds_diachi[0]
              for (let key in formData) {
                this.$set(this.form_TTTT.d_diachi_tt, key, formData[key])
              }
              this.form_TTTT.d_diachi_tt.so_nha = formData.sonha

              this.form_TTTT.d_diachi_bc.tinh_id = formData.tinhbc_id
              this.form_TTTT.d_diachi_bc.quan_id = formData.quanbc_id
              this.form_TTTT.d_diachi_bc.phuong_id = formData.phuongbc_id
              this.form_TTTT.d_diachi_bc.pho_id = formData.phobc_id
              this.form_TTTT.d_diachi_bc.ap_id = formData.apbc_id
              this.form_TTTT.d_diachi_bc.khu_id = formData.khubc_id
              this.form_TTTT.d_diachi_bc.dacdiem_id = formData.dacdiembc_id
              this.form_TTTT.d_diachi_bc.sonha = formData.sonha_bc
              this.form_TTTT.d_diachi_bc.so_nha = formData.sonha_bc
              this.form_TTTT.d_diachi_bc.kinhdo = formData.kinhdo_bc
              this.form_TTTT.d_diachi_bc.vido = formData.vido_bc
            }
            //dv_khac
            // let list = [];
            if (response.data.data.ds_sudung.length>0){
              let temp =''
              $.each(response.data.data.ds_sudung, function (key, item) {
                temp = temp + item.ma_dvkhac + '-' + item.ten_dvkhac +'\n'
              });
              this.form_TTTT.dv_khac = temp
            }
          }
        });
        await API.lay_tt_nhanvien_quanly(this.axios,
          {
            kieuId:1,
            id:thanhtoan_id,
          }).then(response => {
            if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.ten_nv){
              this.form_TTTT.ma_nvql = response.data.data.ten_nv
            }
        })

      } catch (error) {
        console.log(error)
      }
    },
    //lấy list mức cước thuê bao
    async lay_ds_muccuoc_thuebao(loaitb_id,tocdo_id) {
      try {
        await API.lay_ds_muccuoc_thuebao(this.axios, {
          khuld_id: 0,
          apld_id: 0,
          phold_id: 0,
          phuongld_id: 0,
          loaitb_id: loaitb_id,
          doituong_id: 1,
          tocdo_id: tocdo_id,
          muccuoc_id: 0,
          ngay: moment(new Date()).format('DD-MM-YYYY')//"21/10/2021",
        }).then((response) => {
          if (response &&response.data &&response.data.error_code === "BSS-00000000") {
            this.options.cbx_muccuoctb = response.data.data;
          }
        });
      } catch (error) {
        console.log(error)
      }
    },
    //lấy danh sách nhân viên địa bàn theo thuê bao id
    async lay_ds_nhanvien_dbtb_by_id(thuebao_id) {
      try {
        await API.lay_ds_nhanvien_dbtb_by_id(this.axios,
          {
          "thuebaoId": thuebao_id,
          "pageNum": 1,
          "pageRec": 10,
          "totalRow": 1
          }).then(response => {
            if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.length>0){
              this.form_TTTB.dataPopupNVDB = response.data.data
            }
          })
        } catch (error) {
        console.log(error)
      }
    },
    async getChuKyNoTheoKyCuoc(kycuoc) {
      try {
        await API.getChuKyNoTheoKyCuoc(this.axios,  kycuoc ).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
            this.chuky = response.data.data[0].CHUKY;
          }
        });
      } catch (error) {
        console.log(error)
      }
    },
    //thông tin thuê bao mở rộng theo từng dịch vụ viễn thông
    async lay_tt_morong_thuebao(thuebao_id,dichvuvt_id) {
      //TODO: CLEAN ALL FORM DATA BEFORE SEARCH
      let param = {
        thuebaoId: thuebao_id,
        dichvuvtId: dichvuvt_id
      }
      try {
        this.infoDBTB = ""
        switch (dichvuvt_id*1) {
          case this.DichVuVienThong.ADSL*1:
            await API.lay_ds_db_adsl_by_tbid(this.axios,  param ).then((response) => {
                if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&&response.data.data.length>0) {
                  let formData = response.data.data[0]
                  for (let key in formData) {
                    this.$set(this.form_TTTB.db_con, key, formData[key])
                  }
                  if (formData.vmp=='1')
                    this.form_TTTB.db_con.vmp_checked=true

                  if (formData.selfid01 && formData.selfid01!=''){
                      this.form_TTTB.db_con.nasportid = formData.system + "/" +
                                                        formData.selfid04 + "/" +
                                                        formData.selfid03 + "/" +
                                                        formData.selfid02 + "/" +
                                                        formData.selfid01;
                  }
                  else
                      this.form_TTTB.db_con.nasportid = formData.system;

                  this.lay_tt_dichvu_adsl(thuebao_id,formData.congnghe_id)
                  this.getTocDo_ADSL(this.form_TTTB.loaitb_id)
                  this.getDslam(formData.bras_id,2)
                  this.READ_TEXT()
                }
              })
            break;
          case this.DichVuVienThong.MEGA_EYES*1: //megaeyes giống adsl trên winform và designs
            await API.lay_ds_db_adsl_by_tbid(this.axios,  param ).then((response) => {
                if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&&response.data.data.length>0) {
                  let formData = response.data.data[0]
                  for (let key in formData) {
                    this.$set(this.form_TTTB.db_con, key, formData[key])
                  }
                  if (formData.vmp=='1')
                    this.form_TTTB.db_con.vmp_checked=true
                  this.lay_tt_dichvu_adsl(thuebao_id,formData.congnghe_id)
                  this.getTocDo_ADSL(this.form_TTTB.loaitb_id)
                  this.getDslam(formData.bras_id,2)
                }
              })
            break;
          case this.DichVuVienThong.CO_DINH*1:
            await API.lay_ds_db_cd_by_tbid(this.axios,  param ).then((response) => {
                if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&&response.data.data.length>0) {
                  let formData = response.data.data[0]
                  for (let key in formData) {
                    this.$set(this.form_TTTB.db_con, key, formData[key])
                  }
                  this.lay_tt_dichvu_codinh(thuebao_id,formData.ne_id)
                }
              })
            break;
          case this.DichVuVienThong.DI_DONG*1:
            await API.lay_ds_db_dd_by_tbid(this.axios,  param ).then((response) => {
                if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&&response.data.data.length>0) {
                  let formData = response.data.data[0]
                  for (let key in formData) {
                    this.$set(this.form_TTTB.db_con, key, formData[key])
                  }
                }
              })
            break;
          case this.DichVuVienThong.GPHONE*1:
            await API.lay_ds_db_gp_by_tbid(this.axios,  param ).then((response) => {
                if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&&response.data.data.length>0) {
                  let formData = response.data.data[0]
                  for (let key in formData) {
                    this.$set(this.form_TTTB.db_con, key, formData[key])
                  }
                  this.lay_tt_donvi_theo_thuebao(thuebao_id)
                }
              })
            break;
          case this.DichVuVienThong.IMS*1:
            await API.lay_ds_danhba_theo_dichvu_va_thuebao(this.axios,  param ).then((response) => {
                if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&&response.data.data.length>0) {
                  let formData = response.data.data[0]
                  for (let key in formData) {
                    this.$set(this.form_TTTB.db_con, key, formData[key])
                  }
                  this.lay_tt_donvi_theo_thuebao(thuebao_id)
                  this.getDslam(formData.bras_id,2)
                }
              })
            break;
          case this.DichVuVienThong.MEGAWAN*1:
          case this.DichVuVienThong.METRONET*1:
          case 26:
            param = {
              thueBaoId: thuebao_id
            }
            await API.lay_ds_danhba_megawan(this.axios,  param ).then((response) => {
                if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&&response.data.data.length>0) {
                  let formData = response.data.data[0]
                  for (let key in formData) {
                    this.$set(this.form_TTTB.db_con, key, formData[key])
                  }
                  this.lay_tt_donvi_theo_thuebao(thuebao_id)
                  //this.getDslam(formData.bras_id,2)
                }
              })
            break;
          case this.DichVuVienThong.TSL*1:
          case 25:
            await API.lay_ds_db_tsl_by_tbid(this.axios,  param ).then((response) => {
                if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&&response.data.data.length>0) {
                  let formData1 = response.data.data.filter((item) => item.daucuoi_id === 1); //điểm đầu tls
                  let formData2 = response.data.data.filter((item) => item.daucuoi_id === 2); //điểm cuối tls

                  if (formData1 && formData1.length>0){
                    for (let key in formData1[0]) {
                      this.$set(this.form_TTTB.db_con, key, formData1[0][key])
                    }
                    this.form_TTTB.db_con.diachi_dau = formData1[0].diachi
                    this.form_TTTB.db_con.site_id_dau = formData1[0].site_id
                  }
                  if (formData1 && formData1.length>0&& formData2 && formData2.length>0){
                    this.form_TTTB.db_con.diachi_cuoi = formData2[0].diachi
                    this.form_TTTB.db_con.site_id_cuoi = formData2[0].site_id
                    this.lay_tt_hienthi_diachi_tsl(thuebao_id,formData1[0].diachi_id,formData2[0].diachi_id)
                  }

                  this.lay_tt_dbtb_by_id(thuebao_id)

                }
              })
            break;

            case this.DichVuVienThong.DICHVU_CNTT*1:
            case this.DichVuVienThong.TRUNGTAM_DULIEU*1:
            case this.DichVuVienThong.ANTOAN_BAOMAT_TT*1:
            case this.DichVuVienThong.HOINGHI_TRUYENHINH*1:
              this.getDsThuocTinhHDTB_CNTT(this.form_TTTB.loaitb_id,thuebao_id)
            break;
          default:
            this.title.title_sl = "Số cuộc gọi đồng thời";
            break;
        }
        await this.lay_ds_donvi_theo_loaidv(this.form_TTTB.donvi_id,5)
      } catch (error) {
        console.log(error)
      }
    },
    //lấy thông tin thêm dịch vụ brcd
    async lay_tt_dichvu_adsl(thuebao_id,congnghe_id) {
      let data = {
        thuebaoId:thuebao_id,
        congngheId:congnghe_id?congnghe_id:-1
      }
      try {
        await API.lay_tt_dichvu_adsl(this.axios,  data ).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
            this.form_TTTB.db_con.congnghe=response.data.data.congnghe
            this.form_TTTB.db_con.donvi_id=response.data.data.donvi_id
          }
        });
      } catch (error) {
        console.log(error)
      }
    },
    //lấy thông tin thêm dịch vụ cố định
    async lay_tt_dichvu_codinh(thuebao_id,ne_id) {
      let data = {
        thuebaoId:thuebao_id,
        neId:ne_id
      }
      try {
        await API.lay_tt_dichvu_codinh(this.axios,  data ).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
            // this.form_TTTB.db_con.congnghe=response.data.data.congnghe
            this.form_TTTB.db_con.tram_ne=response.data.data.ten_dv
            this.form_TTTB.db_con.tram_id=response.data.data.donvi_id
          }
        });
      } catch (error) {
        console.log(error)
      }
    },
    //lấy thông tin mở rộng dịch vụ CNTT
    getDsThuocTinhHDTB_CNTT: async function (loaithuebao, thuebao_id) {
      try {
        const response = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/danhsach-thuoctinh-capnhat-danhba?ploaitbID=${loaithuebao}&pThueBaoID=${thuebao_id}&pTenBang=HDTB_CNTT&pOptList=00000`
        );
        if (response &&response.data &&response.error_code &&response.error_code === "BSS-00000000") {
          this.form_TTTB.dsThuocTinhHDTB_CNTT = response.data;
        }

      } catch (error) {
        console.log(error)
      }
    },
    //lấy list tốc độ internet theo loại thuê bao
    async getTocDo_ADSL(loaitb_id) {
      let data = {
        loaitb_id:loaitb_id,
      }
      try {
        await API.getTocDo_ADSL(this.axios,  data ).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
              this.options.cbx_tocdo=response.data.data
              this.options.cbx_tocdothuc=response.data.data
          }
        });
      } catch (error) {
        console.log(error)
      }
    },
    //lấy thông tin dslam theo bras
    async getDslam(bras_id, kieu) {
      let data = {
        kieu:kieu,
        brasId: bras_id
      }
      try {
        await API.getDslam(this.axios,  data ).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
              this.options.cbx_dslam=response.data.data
          }
        });
      } catch (error) {
        console.log(error)
      }
    },
    //lấy địa chỉ truyền số liệu
    async lay_tt_hienthi_diachi_tsl(thuebao_id,diachidau_id,diachicuoi_id) {
       try {
        await API.lay_tt_hienthi_diachi_tsl(this.axios, {
            "thuebao_id": thuebao_id,
            "diachidau_id": diachidau_id,
            "diachicuoi_id": diachicuoi_id
        }).then((response) => {
            if (response &&response.data &&response.data.data &&response.data.data.ds_khuvuc && response.data.data.ds_khuvuc.length>0) {
                let formData1 = response.data.data.ds_khuvuc.filter((item) => item.kieukv_id === 1); //khu vực đầu tls
                let formData2 = response.data.data.ds_khuvuc.filter((item) => item.kieukv_id === 2); //khu vực cuối tls

                if (formData1 && formData1.length>0)  this.form_TTTB.db_con.khuvuc_dau= formData1[0].ten_kv
                if (formData2 && formData2.length>0)  this.form_TTTB.db_con.khuvuc_cuoi= formData2[0].ten_kv
            }
        });
      } catch (error) {
        console.log(error)
      }
    },
    //lấy list tổ thi công theo đơn vị, loại đơn vị
    async lay_ds_donvi_theo_loaidv(donvi_id, loaidv_id) {
      let params = {
        donviId:donvi_id,
        loaidvId: loaidv_id
      }
      try {
        await API.lay_ds_donvi_theo_loaidv(this.axios,  params ).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
              this.options.cbx_tothicong=response.data.data
              this.options.cbx_donvi_dau = response.data.data
              this.options.cbx_donvi_cuoi = response.data.data
          }
        });
      } catch (error) {
        console.log(error)
      }
    },
    //lấy đơn vị id theo thuê bao id
    async lay_tt_donvi_theo_thuebao(thuebao_id) {
      let params = {
        thuebaoId:thuebao_id,
      }
      try {
        await API.lay_tt_donvi_theo_thuebao(this.axios,  params ).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
              this.form_TTTB.db_con.donvi_id=response.data.data
          }
        });
      } catch (error) {
        console.log(error)
      }
    },
    // thông tin csr CA - CNTT
    async lay_tt_ca_csr_by_tbid(thuebao_id) {
      let params = {
        thuebaoId:thuebao_id,
      }
      try {
        await API.lay_tt_ca_csr_by_tbid(this.axios,  params ).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
              return response
          }
        });
      } catch (error) {
        console.log(error)
      }
    },

    gvDSTB_DataBound: function (args) {
      try {
        //return;
        console.log(this.gvDSTB.list)
        // if (this.gvDSTB.list.length>0){
        //   let index = this.gvDSTB.list.findIndex(
        //     (x) => x.ma_tb.trim() == this.searchTerm
        //   );
        //   if (index >= 0) {
        //     this.$refs.gvDSTB.selectRow(index);
        //   } else {
        //     this.$refs.gvDSTB.selectRow(0);
        //   }
        // }
      } catch (error) {
        console.log(error)
      }
    },
    async gvDSTB_rowSelected(data) {
      try {
        if (!data.data) return;
        console.log(data.data)
        //clean form
        this.clearFormTB()
        this.clearFormTT()

        //load thông tin thuê bao
        //hiển thị chi tiết theo data select
        let formData = data.data;
        for (let key in formData) {
          this.$set(this.form_TTTB, key, formData[key]);
        }
        this.form_TTTB.ten_tb = this.ChuanHoaTen(formData.ten_tb)
        this.form_TTTB.diachi_tb = this.ChuanHoaTen(formData.diachi_tb)
        this.form_TTTB.diachi_ld = this.ChuanHoaTen(formData.diachi_ld)

        this.form_TTTB.ngay_sd = this.formatDate(new Date(data.data.ngay_sd));
        this.form_TTTB.chuyenmang = data.data.chuyenmang

        await this.thongtin_dbtb(data.data.thuebao_id,data.data.kieuld_id,data.data.dichvuvt_id,data.data.loaitb_id,data.data.thuebao_cha_id);

        let res = await API.hienthi_ds_thietbi_idc(this.axios,{thuebao_id:data.data.thuebao_id});
        if (res && res.data && res.data.error_code === 'BSS-00000000' && res.data.data) {
            this.dataPopupThietbiIDC = res.data.data
        }

        //this.lay_ds_tt_theo_loaitb(data.data.loaitb_id,this.form_TTTB.loaihd_id)
        //await this.lay_ds_muccuoc_thuebao(data.data.loaitb_id);
        this.options.cbx_muccuoctb = this.options.tmp_cbx_muccuoctb.filter(
          (item) => item.DICHVUVT_ID === data.data.dichvuvt_id
        );

        this.changeDVVT(data.data.dichvuvt_id);
        this.form_TTTB.loaitb_id = data.data.loaitb_id;
        switch (data.data.dichvuvt_id) {
          case this.DichVuVienThong.ADSL:
            if (
              this.form_TTTB.loaitb_id == this.LoaiHinhTB.HTVC_FIBERVNN_ANALOG ||
              this.form_TTTB.loaitb_id == this.LoaiHinhTB.HTVC_FIBERVNN_DIGITAL
            )
              this.title.title_sl = "Số lượng ti vi";
            else this.title.title_sl = "";
            break;
          case this.DichVuVienThong.IMS:
            this.title.title_sl = "Số cuộc gọi đồng thời";
            break;
          default:
            this.title.title_sl = "Số cuộc gọi đồng thời";
        }

        //thông tin cam kết
        if (data.data.ngay_ktck) {
          //this.form_TTTB.ngay_ktck = this.formatDate(new Date(data.data.ngay_ktck));
          this.form_TTTB.ngay_ktck = moment(new Date(data.data.ngay_ktck)).format('DD/MM/yyyy')
        }
        //thông tin ngày sinh
        if (data.data.ngay_sn) {
          this.form_TTTB.ngay_sn = moment(new Date(data.data.ngay_sn)).format('DD/MM/yyyy')
        }
        this.form_TTTB.sothang_ck = data.data.sothang_ck
        //load thông tin nhân viên địa bàn
        await this.lay_ds_nhanvien_dbtb_by_id(data.data.thuebao_id)

        //load thêm thông tin thuê bao bổ sung theo loại dịch vụ
        await this.lay_tt_morong_thuebao(data.data.thuebao_id,data.data.dichvuvt_id)
        //await this.lay_ds_muccuoc_thuebao(data.data.loaitb_id, this.form_TTTB.db_con.tocdo_id);
        //set focus cho grid view thanh toán

        let index = this.gvDSTT.list.findIndex(
          (x) => x.thanhtoan_id == data.data.thanhtoan_id
        );


        let temp=this.gvDSTT.list
        if (index >= 0) {
          if (index>=10){
              let row = temp[index]
              temp.splice(index, 1)
              temp.unshift(row)
              this.gvDSTT.list = temp
              for(var i in this.gvDSTT.list) this.gvDSTT.list[i].so_tt=(parseInt(i)+1).toString();
              this.$refs.gvDSTT.selectRow(0);
          } else {
            this.$refs.gvDSTT.selectRow(index);
          }
        }

        await API.lay_ds_diachi_theo_dbtbid(this.axios, data.data.thuebao_id).then((response) => {
          if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data && response.data.data.length > 0) {
            this.dataDiachi = response.data.data[0]
          }
        })
      } catch (error) {
        console.log(error)
      }
    },

    gvDSTT_rowSelected(data) {
      try {
        if (!data.data) return;

        this.clearFormTT()
        let formData = data.data;
        for (let key in formData) {
          this.$set(this.form_TTTT, key, formData[key]);
        }
        this.lay_ds_hienthi_dbtt(data.data.thanhtoan_id,data.data.ma_tt)
      } catch (error) {
        console.log(error)
      }
    },
    closePopupThietbiIDC(){},
    gvDSTT_DataBound: function (args) {},
    gvDSTB_rowClicked(i, item) {},
    HienThiGiaoDien(kieugoi_id) {
      this.visibles.tsbtnGhiLai = false;
      this.visibles.tsbtnHuyBo = false;
      this.visibles.tsbtnNhapMoi = false;

      this.header.title = "TRA CỨU CHI TIẾT THÔNG TIN DANH BẠ";

      if (this.kieugoi_id == 1 || this.kieugoi_id == 2) {
        this.visibles.tsbtnGhiLai = true;
        this.visibles.tsbtnHuyBo = true;
        this.visibles.tsbtnNhapMoi = true;
        this.header.title = "SỬA ĐỔI THÔNG TIN DANH BẠ";
      }
    },
    changeDVVT(dvvt_id) {
      try {
        if (dvvt_id == this.DichVuVienThong.TSL*1){
          this.visibles.btnKhuvuc = false
        }
        else {
          this.visibles.btnKhuvuc = true
        }
        //load loaihinhtb
        API.getLoaiHinh(this.axios, { dvvt_id }).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
            this.options.cbx_loaihinh = response.data.data;
            //TODO: ẩn hiện controls
          }
        });
        if (this.khachhang_id != 0)
          this.form_TTTB.loaitb_id=0
        else
        {
            switch (dvvt_id)
            {
                case this.DichVuVienThong.CO_DINH*1:
                    this.form_TTTB.loaitb_id = this.LoaiHinhTB.DIENTHOAI_CD;
                    this.options.enables.cbx_trangbi=false
                    break;
                case this.DichVuVienThong.DI_DONG*1:
                    this.form_TTTB.loaitb_id = this.LoaiHinhTB.DIDONGTRASAU;
                    this.options.enables.cbx_trangbi= true;
                    break;
                case this.DichVuVienThong.IMS*1:
                    this.form_TTTB.loaitb_id = this.LoaiHinhTB.IMS_POTS;
                    this.options.enables.cbx_trangbi= false;
                    break;
                case this.DichVuVienThong.ADSL*1:
                    this.form_TTTB.loaitb_id = this.LoaiHinhTB.MEGA;
                    this.options.enables.cbx_trangbi= true;
                    break;
                case this.DichVuVienThong.TSL*1:
                case 25:
                    this.form_TTTB.loaitb_id = this.LoaiHinhTB.LEASEDLINE;
                    this.options.enables.cbx_trangbi= false;
                    break;
                case this.DichVuVienThong.MEGAWAN*1:
                case 26:
                    this.options.enables.cbx_trangbi= false;
                    break;
                case this.DichVuVienThong.GPHONE*1:
                    this.options.enables.cbx_trangbi= true;
                    break;
                default:
                    this.options.enables.cbx_trangbi= false;
                    break;
            }
        }
      } catch (error) {
        console.log(error)
      }
    },
    async ThongTinLienHe_OnClick(){
      if (this.khachhang_id != 0) {
        this.$bvModal.show("ThongTinLienHe");
      }
      // try {
      //   if (this.khachhang_id > 0) {
      //     await this.$refs.thongTinLienHePopUp.getThongTinLienHe()
      //   }
      // } catch (err) {
      //   console.log('lienHeOpen-err', err)
      // } finally {
      //   this.$refs.thongtinlienhe.show()
      // }

    },
    async ThongTinEmail_OnClick(){
      if (this.khachhang_id != 0) {
        this.$bvModal.show("ThongTinEmail");
      }
      // try {
      //   await this.$refs.thongTinEmailPopUp.getDsThongTinEmail()
      //   } catch (err) {
      //     console.log('thongTinEmailOpen-err', err)
      //   } finally {
      //     this.$refs.thongtinemail.show()
      //   }
    },
    btnToaNha() {
      if (this.thuebao_id == '' || this.thuebao_id == 0) {
        this.ShowAlert('Chưa có thuê bao được chọn!', 2)
        return
      }
      //Tham chiếu UR2.8.004 (chức năng của Tân)
      //this.$bvModal.show('pop_toanha')
      this.$refs.formDiachi.initData()
      this.$refs.pop_toanha.show()
    },
    showFormVT () {
      if (this.form_TTTB.thuebao_id=="" || this.form_TTTB.thuebao_id==0) {
        this.$toast.error("Chưa có thuê bao được chọn!");
        return
      }
      //Tham chiếu UR13.1.037
      this.$refs.formVattu.p_so_may=this.form_TTTB.ma_tb
      this.$refs.formVattu.frmTraCuuVatTuTB(this.form_TTTB.thuebao_id)
      //this.$bvModal.show('popup-tracuuvattuthuebao')
      this.$refs.formVattu.dialogOpen()
    },
    async onDownloadCSR() {
      /*try {
        if (this.form_TTTB.dichvuvt_id!=this.DichVuVienThong.ANTOAN_BAOMAT_TT*1) return;
        let params = {
          thuebaoId:this.form_TTTB.thuebao_id,      //2976143//
        }
        await API.lay_tt_ca_csr_by_tbid(this.axios,  params ).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
              let content = response.data.data
              var FileSaver = require("file-saver");
              var file = new File([content], this.form_TTTB.ma_tb, {
                  type: "application/pkix-cert"
              });
              FileSaver.saveAs(file);
          }
        });
      } catch (error) {
        console.log(error)
      } */

    },
    async onXemHSEkyc()
    {
      try {
        let params = {
          thuebao_id:this.form_TTTB.thuebao_id
        }
        await this.$refs.ref_frmHoSoEKYCSMCA.openDialog(params)
      }
      catch (e)
      {
        console.log(e);
      }
    },
    async onDownloadCTS() {
      try {
        if (this.form_TTTB.dichvuvt_id!=this.DichVuVienThong.ANTOAN_BAOMAT_TT*1) return;
        let params = {
          thuebaoId:this.form_TTTB.thuebao_id,      //2976143//
        }
        await API.lay_tt_ca_token_by_tbid(this.axios,  params ).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
              let content = response.data.data
              var FileSaver = require("file-saver");
              var file = new File([content], this.form_TTTB.ma_tb, {
                  type: "application/x-pkcs7-certificates"
              });
              FileSaver.saveAs(file);
          }
        });
      } catch (error) {
        console.log(error)
      }
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    popupClosed(){
      this.$bvModal.hide('popupComponent')
    },
    updateFormPropsKH(newValue) {
      this.form_TTKH.ma_kh = newValue.ma_kh;
    },
    updateFormPropsTT(newValue) {
      //this.form_TTKH.ma_kh = newValue.ma_kh
    },
    formatDate(date) {
      return moment(date).format("DD/MM/YYYY HH:mm:ss");
    },
    formatDate1(date) {
      return moment(date).format("DD/MM/YYYY");
    },
    async gridDanhSachTB_pageChanged() {
      if (this.isClick) {
        this.isClick = false
        return
      }
      this.loading(true);
      this.isClick = false
      this.gvDSTB.list = []
      try {
          let pIndex = this.$refs.gvDSTB.$refs.pagination.pageIndexData
          let pSize = this.$refs.gvDSTB.$refs.pagination.pageSizeData
          await API.lay_ds_dbtb_theo_khid_page(this.axios, {
            //todo
            "khachhang_id": this.khachhang_id,
            "pageNum": pIndex+1,
            "pageRec": pSize,
            "totalRow": 0,
            "ma_tb": '',

          }).then((response) => {
            if (response &&response.data &&response.data.error_code === "BSS-00000000" &&response.data.data.length > 0) {
              this.gvDSTB.list = response.data.data

            } else if (response &&response.data &&response.data.error_code == "BSS-00000204") {
                this.$toast.error("Không tìm thấy danh sách thuê bao theo khách hàng!");
            } else {
              this.$toast.error(
                "Có lỗi xảy ra, vui lòng lin hệ Quản trị để được hỗ trợ!"
              );
            }
            this.loading(false);
          });


      } catch (exp) {
          this.$toast.error(exp)
          this.loading(false);
      }
    },
    READ_TEXT(){
      this.form_TTTB.db_con.infoDBTB=''
      try
        {
            this.form_TTTB.db_con.infoDBTB += "Account: " + this.form_TTTB.ma_tb;
            this.form_TTTB.db_con.infoDBTB += "\r\nPassword: " + this.form_TTTB.db_con.password;
            let item_tocdo= this.options.cbx_tocdo.find((item) =>item.tocdo_id == this.form_TTTB.db_con.tocdo_id)
            if (item_tocdo)
              this.form_TTTB.db_con.infoDBTB += "\r\nTốc độ: " + item_tocdo.thuonghieu;

            let item_bras= this.options.cbx_bras.find((item) =>item.bras_id == this.form_TTTB.db_con.bras_id)
            if (item_bras)
              this.form_TTTB.db_con.infoDBTB += "\r\nBras: " + item_bras.tenbras;
            this.form_TTTB.db_con.infoDBTB += "\r\nSystem: " + this.form_TTTB.db_con.system + " (" + this.form_TTTB.db_con.ip_dslam + ")";
            this.form_TTTB.db_con.infoDBTB += "\r\nPort: " + this.form_TTTB.db_con.slot + "-" + this.form_TTTB.db_con.port + "-" + this.form_TTTB.db_con.vpi;
            this.form_TTTB.db_con.infoDBTB += "\r\nPort Mdf:" + this.form_TTTB.db_con.port_mdf;
            this.form_TTTB.db_con.infoDBTB += "\r\nVPI/SVLAN: " + this.form_TTTB.db_con.vpi;
            this.form_TTTB.db_con.infoDBTB += "\r\nVCI/CVLAN: " + this.form_TTTB.db_con.vci;
            this.form_TTTB.db_con.infoDBTB += "\r\nOnu Pass: " + this.form_TTTB.db_con.onu_password;
            this.form_TTTB.db_con.infoDBTB += "\r\nTrạng thái HĐ: " + this.form_TTTB.trangthai_tb;
        }
        catch(ex)
        { }

    },
    ChuanHoaTen(ten) {
      if (!ten) return ''
      let tenNew = ''
      let strCon = ten.trim()
      let strXl = ''
      let k = 0
      let found = true
      if (strCon.length < 1) return
      strCon = strCon.replaceAll(/\s+/g, ' ')
      try {
        while (found) {
          k = strCon.indexOf(' ')
          if (k > 0) {
            strXl = strCon.substring(0, k)
            strCon = strCon.substring(k + 1)
            strXl = this.ChuyenChuHoa(strXl)
            if (tenNew !== '') {
              tenNew += ' ' + strXl
            } else tenNew = strXl
            found = true
          } else found = false
        }
        if (strCon !== '') {
          strXl = strCon
          strXl = this.ChuyenChuHoa(strXl)
          if (tenNew !== '') {
            tenNew = tenNew + ' ' + strXl
          } else {
            tenNew = strXl
          }
        }
        return tenNew
      } catch (e) {
        return ten
      }
    },
    clearFormKH(){
      this.loaikh_id=0
      this.hdkh_id=0
      this.cbx_thanhtoan=[]
      this.cbx_thuebao_dd=[]
      this.gvDSTT.list=[]
      this.gvDSTB.list=[]
      //clear thông tin khách hàng khi tìm kiếm mới
      this.khachhang_id=0
      this.form_TTKH={
        ma_hd:null,
        ngaylap_hd:null,
        ma_kh:null,
        ten_kh:null,
        diem_tn:null,
        diachi_kh:null,
        loaigt_id:0,
        so_gt:null,
        ngaycap:null,
        noicap:null,
        ngayhethan_gt:null,
        ngay_sn:null,
        gioitinh:null,
        quoctich_id:0,
        nganhnghe_id:0,
        dantoc_id:0,
        loaikh_id:0,
        phanloai_kh:null,
        nguoi_dd:null,
        chucdanh:null,
        diachi_ndd:null,
        so_dt:null,
        so_fax:null,
        hokhau:null,
        noicap_hk:null,
        ngay_hk:null,
        nganhang_id:0,
        mst:null,
        stk:null,
        dactrung:null,
        ghichu:null,
        d_tieunganh:{
          TNC1_ID:0,
          TNC2_ID:0,
          TNC3_ID:0,
          TNC4_ID:0,
        },
        d_diachi_kh:{
          tinh_id : 0,
          quan_id : 0,
          phuong_id : 0,
          pho_id : 0,
          ap_id : 0,
          khu_id : 0,
          dacdiem_id : 0,
          sonha : null,
          so_nha : null,
          kinhdo:null,
          vido:null,
          block:null,
          tang:null,
          phong:null,
          mota:null,
        },
        d_diachi_ndd:{
          tinh_id : 0,
          quan_id : 0,
          phuong_id : 0,
          pho_id : 0,
          ap_id : 0,
          khu_id : 0,
          dacdiem_id : 0,
          sonha : null,
          so_nha : null,
          kinhdo:null,
          vido:null,
          block:null,
          tang:null,
          phong:null,
          mota:null,
        }
      }
      //clear thông tin thuê bao, thông tin thanh toán khi tìm kiếm mới, chọn thuê bao khác
    },
    clearFormTT(){
      this.form_TTTT= {
        ma_tt:null,
        matb_dd:null, //load danh sách cho vào combobox
        matt_ccbs:null,
        ten_tt:null,
        diachi_tt:null,
        diachi_ct:null,
        diachi_bc:null,
        main_ghep:null,
        httt_id:0,
        nganhang_id:0,
        chu_tk:null,
        ghichu:null,
        email:null,
        donvi_id:0,
        manv_tc:null,
        ma_tuyen:null,
        stk:null,
        mst:null,
        ma_nvql:null,
        so_dt:null,
        dv_khac:null,
        d_diachi_tt:{
          tinh_id : 0,
          quan_id : 0,
          phuong_id : 0,
          pho_id : 0,
          ap_id : 0,
          khu_id : 0,
          dacdiem_id : 0,
          sonha : null,
          kinhdo:null,
          vido:null,
          block:null,
          tang:null,
          phong:null,
          mota:null,
        },
        d_diachi_ct:{
          tinh_id : 0,
          quan_id : 0,
          phuong_id : 0,
          pho_id : 0,
          ap_id : 0,
          khu_id : 0,
          dacdiem_id : 0,
          sonha : null,
          so_nha : null,
          kinhdo:null,
          vido:null,
          block:null,
          tang:null,
          phong:null,
          mota:null,
        },
        d_diachi_bc:{
          tinh_id : 0,
          quan_id : 0,
          phuong_id : 0,
          pho_id : 0,
          ap_id : 0,
          khu_id : 0,
          dacdiem_id : 0,
          sonha : null,
          so_nha : null,
          kinhdo:null,
          vido:null,
          block:null,
          tang:null,
          phong:null,
          mota:null,
        }
      }
    },
    clearFormTB(){
      this.dataDiachi = { }
      this.form_TTTB= {
        phanvung_id:0,
        loaihd_id:0, //TODO load theo kiểu LĐ
        dichvuvt_id:0,
        kieuld_id:0,
        mucuoctb_id:0,
        ma_tb:null,
        thanhtoan_id:0,
        ten_tb:null,
        trangthai_tb:null,
        loaitb_id:0,
        khuvuc:null,
        khuvuc_id:0,
        doituong_id:0,
        ma_lt:null,
        ngay_sn:null,
        diachi_tb:null,
        diachi_ld:null,
        donvi_id:0,
        ma_dhvip:null,
        ghichu:null,
        sothang_ck:null,
        ngay_ktck:null,
        dv_khac:null, //load riêng
        dv_congthem:null, //load riêng
        ngay_sd:null,
        cuoc_tb:null,
        trangbi_id:0,
        goi_kt:null,  //load tùy theo dịch vụ
        cuoc_dv:null,
        soluong:null, //load tùy theo dịch vụ
        khuvuc_id_cuoi:0,
        dataPopupKhuVuc:{
          quan_id:0,
          phuong_id:0,
          pho_id:0,
          ap_id:0,
          khu_id:0,
          dacdiem_id:0,
          khuvuc_id:0,
          donviql_id:0,
          hdtb_id:0
        },
        dataPopupNVDB:[],
        d_diachi_tb:{
          tinh_id : 0,
          quan_id : 0,
          phuong_id : 0,
          pho_id : 0,
          ap_id : 0,
          khu_id : 0,
          dacdiem_id : 0,
          sonha : null,
          so_nha : null,
          kinhdo:null,
          vido:null,
          block:null,
          tang:null,
          phong:null,
          mota:null,
        },
        d_diachi_ld:{
          tinh_id : 0,
          quan_id : 0,
          phuong_id : 0,
          pho_id : 0,
          ap_id : 0,
          khu_id : 0,
          dacdiem_id : 0,
          sonha : null,
          so_nha : null,
          kinhdo:null,
          vido:null,
          block:null,
          tang:null,
          phong:null,
          mota:null,
        },
        db_con: {
          email:"",
          donvi_id: 0,
          bras_id: 0,
          dslam_id: 0,
          tocdo_id: 0,
          tocdothuc_id: 0,
          muccuoc_id: 0,
          chuquan_id: 0,
          loainode_id: 1,
          doitac_id: 0,
          tocdo_pir_id: 0,
          tocdocir_id: 0,
          loaikenh_id: 0,
          loaimd_id: 0,
          thoihan_id: 0,
          quoctich_id: 0,
          tocdokenh_id: 0,
          thietbid_id: 0,
          trangbi_id: 0,
          cuoc_dv: 0,
          username: "",
          password: "",
          slot: "",
          port: "",
          rack: "",
          shelf: "",
          vpi: "",
          vci: "",
          loaicap_id: 0,
          tucap_goc: "",
          doicap_goc: "",
          cap_goc: "",
          hopcap_p: "",
          tucap_p: "",
          doicap_p: "",
          cap_ngon: "",
          cap_p:"",
          tendslam: "",
          tuyencap: "",
          lientu: "",
          culy: "",
          thietbi_id: 0,
          system: "",
          email: "",
          ip_dslam: "",
          nasportid: "",
          port_mdf: "",
          adsl_port: "",
          onu_password: "",
          ip: "",
          matb_tn: "",
          congnghe: "",
          mac_addr: "",
          diachi_pop: "",
          soluong: 0,
          cuoc_tk: 0,
          cuoc_tc: 0,
          cuoc_ht: 0,
          cuoc_tbi: 0,
          cuoc_tkdt: 0,
          cuoc_tcdt: 0,
          cuoc_isp: 0,
          cuoc_nix: 0,
          cuoc_ip: 0,
          mahd_dt: "",
          diachi_dau: "",
          diachi_cuoi: "",
          donvi_id_dau: 0,
          donvi_id_cuoi: 0,
          site_id_dau: 0,
          site_id_cuoi: 0,
          khuvuc_id_dau: 0,
          khuvuc_id_cuoi: 0,
          khuvuc_dau: "",
          khuvuc_cuoi: "",
          huong_kn: "",
          huong_ut: "",
          vmp: 0,
          vmp_checked: false,
          capngoai: 0,
          luonggiua: 0,
          mahd_dt: "",
          ne: "",
          sochinh: "",
          simcard: "",
          sodu_tk: 0,
          goicuoc_id: 0,
          madoicap: "",
          mc_tbgp_id: "",
          toado: "",
          cell1: "",
          tramtb_id:0,
          tram_ne:"",
          tengoicuoc:"",
          thietbidc_id:0, //tsl
          huongketnoi_megawan:"",
          vrf:"",
          capngoai_vnpt:"",
          site_id:0,
          giu_kenh:false,
          quocgia_pop:0,
          infoDBTB:""
        },
        dsThuocTinhHDTB_CNTT: [],
      }
    }
  },
};
</script>
