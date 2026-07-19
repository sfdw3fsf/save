<template src="./XacNhanNguyenNhanNoCuoc2.html"></template>
<style src="./XacNhanNguyenNhanNoCuoc2.scss" scoped></style>

<script>
import Vue from 'vue'
import moment from 'moment'
import TuyenThuAPI from '../api/TuyenThuAPI'
import NNNC2API from '../api/XacNhanNguyenNhanNoCuoc2'
import CommonsAPI from '../api/Commons'

import { MultiSelectPlugin, CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"
import { CheckBoxPlugin } from "@syncfusion/ej2-vue-buttons"
import { Query } from '@syncfusion/ej2-data'

import { DialogUtility } from '@syncfusion/ej2-popups'

Vue.use(DialogUtility)
let DialogObj = undefined


Vue.use(MultiSelectPlugin)
Vue.use(CheckBoxPlugin)


export default Vue.extend({
  created () {
    this.loadNVThuCuoc()
    this.loadDSLoaiPhieuTC()
    this.loadDSKQXacMinh()

    this.tabChange(1)
  },
  computed : {
    f_tao_ycchem_enabled () {
      return this.params.p_chk_tao_yc_khoamay === '1' && this.configs.p_chk_taoyc_enable
    },
  },
  data () {
    return {
      hideTable: true,
      configs: {
        selectionSettings: {checkboxOnly: true, type: 'Multiple'},
        f_giao_nvkt_visible: false,
        f_giao_nvkd_visible: false,
        f_giao_nvdb_visible: false,
        f_thoaitra_visible: false,
        f_ketquaXM_visible: false,
        f_hoanthien_visible: false,
        f_nguoinhan_visible: false,
        p_chk_taoyc_enable: true,
        f_capnhattaophieu_visible: false,
        nvtc: {
          fields : { text: 'TEN_NV', value: 'NHANVIEN_ID' },
          mode: 'CheckBox',
          showSelectAll: true,
          showDropDownIcon: true,
          filterBarPlaceholder: 'Tìm kiếm nhanh'
        },
        fieldsNguoiNhan: { text: 'TEN_NV', value: 'NHANVIEN_ID' },
        fieldsLoaiPhieu: { text: 'LOAIPHIEU', value: 'LOAIPHIEU_ID' },
        fieldsKQXL: { text: 'NOIDUNG', value: 'KQXL_ID' },
      },
      params: {
        ds_tbchem : null,
        p_chk_tao_yc_khoamay: '0',
        p_nvtc_options: [],
        p_nvtc: [],
        p_loaiphieu_options: [],
        p_loaiphieu: null,
        p_ghichu: null,
        p_nguoinhan_options: [],
        p_nguoinhan: null,
        p_kqxl_all: [],
        p_kqxl_options: [],
        p_kqxl: null,
        p_LOAI_LYDO: 1,
        p_KHONG_XN_LYDO_NOCUOC_NVTC : 0,
        p_nhatky_tn_id: 0,
        p_r_nhanvien_tra_id: 0,
        p_r_thanhtoan_id : 0,
        p_loai_trangthai: 0,
        p_r_tthd_id: 0,
        p_ma_tt: null,
        p_ma_tb: null,
        p_loaitb_id: 0,
        p_kieu_khoa: 1,
        p_dichvuvt_id: 0,
        p_thuebao_id: 0

      },
      tabData : {
        1: [],
        2: [],
        3: [],
        4: [],
        5: [],
        6: [],
        7: [],
        8: []
      },
      tabPageInfo:{
        1: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
        2: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
        3: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
        4: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
        5: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
        6: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
        7: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
        8: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
      },
      tabKey: {
        1: 0,
        2: 0,
        3: 0,
        4: 0,
        5: 0,
        6: 0,
        7: 0,
        8: 0,
      },
      curren_tab: 1, //
      dsChiTiet: [],
      dsTBKhoaMay: []
    }
  },
  methods : {
    loadNVThuCuoc: function () {
      this.params.p_nvtc_options = []
      this.params.p_nvtc = []

      TuyenThuAPI.getDanhSachNVTC(this.axios, {pNguoiDungId: this.$auth.getNguoiDungID(), pageNo: 0, pageSize: 100})
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.data !== undefined
              && response.data.data.data.length > 0)
          {
            let items = []
            if (response.data.data !== undefined) {
              this.params.p_nvtc_options = response.data.data.data
            }
            // this.params.p_nhanvienthu_options = response.data.data
            // this.params.p_nhanvienthu = this.params.p_nhanvienthu_options[0]
          }
        }
      )
      .catch(error => {
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
      })
    },
    loadDSLoaiPhieuTC: function () {
      this.params.p_loaiphieu_options = []
      this.params.p_loaiphieu_options.push({
        LOAIPHIEU: 'Tất cả',
        LOAIPHIEU_ID: 0
      })
      this.params.p_loaiphieu = 0

      NNNC2API.getDanhMucDaiLy(this.axios, 'LOAIPHIEU')
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
          {
            this.params.p_loaiphieu_options = [...this.params.p_loaiphieu_options, ...response.data.data]
            //this.params.p_loaiphieu = response.data.data[0].LOAIPHIEU_ID
          }
        }
      )
      .catch(error => {
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
      })
    },
    loadDSKQXacMinh: function () {
      this.params.p_kqxl_all = []
      this.params.p_kqxl_options = []
      this.params.p_kqxl = null

      NNNC2API.getDanhMucDaiLy(this.axios, 'KETQUA_XMNC')
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
          {
            this.params.p_kqxl_all = response.data.data
            this.params.p_kqxl_options = [...this.params.p_kqxl_all]
            this.params.p_kqxl = response.data.data[0].KQXL_ID
          }
        }
      )
      .catch(error => {
        //console.log(error)
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
      })
    },

    onXemClick()
    {
      this.HienThiDanhSach();
    },

    HienThiDanhSach()
    {
      if (this.params.p_LOAI_LYDO  === -9)
      {
        this.getDSPhieuDangXacMinh(null)
        /*
          gridPhieuDangXuLy.DataSource = bangtd_fd.THUCTHI_PROCEDURE("lay_ds_phieu_xacminh_dang_xacminh", DatabaseConstants.DB3, new string[] { "ds" },
          "vnhanvien_id", tt_nd.nhanvien_id).Tables[0];
          return;
        */
      }
      else if (this.params.p_LOAI_LYDO !== -1)
      {
        if(this.params.p_nvtc.length <= 0)
        {
          return
        }
        if(this.params.p_loaiphieu === null)
        {
          return
        }
        //console.log(this.params.p_nvtc)

        let postData = {
          dsNhanVien: this.params.p_nvtc.join(','),
          loaiPhieuId: this.params.p_loaiphieu,
          loaiLyDo: this.params.p_LOAI_LYDO
        }

        this.getDSPhieuXacMinhNoCuoc(postData)
      }
      else
      {
        let postData = {
          tthdId: 16,
          nhanVienId : this.$auth.getNhanVienID()
        }
        this.getDSPhieuXacMinhChoXuLy(postData)
      }
    },

    getDSPhieuDangXacMinh(id)
    {
      this.loading(true)
      NNNC2API.getDSPhieuDangXacMinh(this.axios, this.$auth.getNhanVienID())
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
          {

          }
        }
      )
      .catch(error => {
        //console.log(error)
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
        this.loading(false)
      })
    },
    getDSPhieuXacMinhNoCuoc(postData)
    {
      this.loading(true)
      if(this.params.p_LOAI_LYDO === 1)
        this.tabData[1] = []
      else if(this.params.p_LOAI_LYDO === 2)
        this.tabData[2] = []
      else if(this.params.p_LOAI_LYDO === 3)
        this.tabData[3] = []
      else if(this.params.p_LOAI_LYDO === 10)
        this.tabData[5] = []

      NNNC2API.getDSPhieuXacMinhNoCuoc(this.axios, postData)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
          {
            if(this.params.p_LOAI_LYDO === 1)
              this.tabData[1] = response.data.data
            else if(this.params.p_LOAI_LYDO === 2)
              this.tabData[2] = response.data.data
            else if(this.params.p_LOAI_LYDO === 3)
              this.tabData[3] = response.data.data
            else if(this.params.p_LOAI_LYDO === 10)
              this.tabData[5] = response.data.data
          }
        }
      )
      .catch(error => {
        //console.log(error)
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
        this.loading(false)
      })
    },
    getDSPhieuXacMinhChoXuLy(postData)
    {
      this.loading(true)

      if(this.curren_tab === 4)
      {
        this.tabData[4] = []
        this.tabPageInfo[4].totalElement = 0
        this.tabPageInfo[4].page = 0
      }

      NNNC2API.getDSPhieuXacMinhChoXuLy(this.axios, postData)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
          {
            if(this.curren_tab === 4)
            {
              this.tabData[4] = response.data.data
              this.tabPageInfo[4].totalElement = response.data.data.length
            }
              
          }
        }
      )
      .catch(error => {
        //console.log(error)
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
        this.loading(false)
      })
    },

    tabChange(index)
    {
      this.curren_tab = index

      if(this.curren_tab === 1)  // lý do kỹ thuật
      {
        this.params.p_LOAI_LYDO = 1
        this.configs.f_giao_nvkt_visible = true
        this.configs.f_giao_nvkd_visible = false
        this.configs.f_thoaitra_visible = true

        this.configs.f_capnhattaophieu_visible = false
        this.configs.f_ketquaXM_visible = false
        this.configs.f_hoanthien_visible = false
        this.configs.p_chk_taoyc_enable = false
        //popupFilter.Enabled = false;
        this.configs.f_nguoinhan_visible = true
        //lookUpNguoiNhanPhieu.Visible = true;
      } else if(this.curren_tab === 2) //lý do kinh doanh
      {
        this.params.p_LOAI_LYDO = 2;
        this.configs.f_giao_nvkt_visible = false;
        this.configs.f_giao_nvkd_visible = true;
        this.configs.f_thoaitra_visible = true;
        this.configs.f_capnhattaophieu_visible = false;
        this.configs.f_ketquaXM_visible = false;
        this.configs.f_hoanthien_visible = false;
        this.configs.p_chk_taoyc_enable = false;
        //popupFilter.Enabled = false;
        this.configs.f_nguoinhan_visible = true
        //lookUpNguoiNhanPhieu.Visible = true;
      } else if(this.curren_tab === 3) // lý do khách hàng
      {
        this.params.p_LOAI_LYDO = 3;
        this.configs.f_giao_nvkt_visible = false;
        this.configs.f_giao_nvkd_visible = true;
        this.configs.f_thoaitra_visible = true;
        this.configs.f_capnhattaophieu_visible = false;
        this.configs.f_ketquaXM_visible = true;
        this.configs.f_hoanthien_visible = true;
        this.configs.p_chk_taoyc_enable = false;
        //popupFilter.Enabled = false;
        this.configs.f_nguoinhan_visible = true
        //lookUpNguoiNhanPhieu.Visible = true;
        let f = this.params.p_kqxl_all.filter(x => (x.LOAI_LYDO == this.params.p_LOAI_LYDO && (x.TTHD_ID_TIEPTHEO === 10 || x.TTHD_ID_TIEPTHEO === 16)))
        //var dtcbo = dtKQ_XacMinh.Select(string.Format("LOAI_LYDO = {0} AND TTHD_ID_TIEPTHEO IN (10, 16)", LOAI_LYDO));

        if (f.length > 0)
          this.params.p_kqxl_options = f
        else
          this.params.p_kqxl_options = []

      } else if(this.curren_tab === 4) // chờ xử lý
      {
        this.params.p_LOAI_LYDO = -1;
        this.configs.f_giao_nvkt_visible = false;
        this.configs.f_giao_nvkd_visible = false;
        this.configs.f_thoaitra_visible  = true;
        this.configs.f_capnhattaophieu_visible = true;
        this.configs.f_ketquaXM_visible = false;
        this.configs.f_hoanthien_visible = false;
        this.configs.p_chk_taoyc_enable = true;
        //popupFilter.Enabled = true;
        //gridTBKhoaMay.DataSource = null;
        this.configs.f_nguoinhan_visible = false
        //lookUpNguoiNhanPhieu.Visible = false;
      } else if(this.curren_tab === 5) // đã xử lý
      {
        this.params.p_LOAI_LYDO = 10;
        this.configs.f_giao_nvkt_visible = false;
        this.configs.f_giao_nvkd_visible = false;
        this.configs.f_thoaitra_visible = false;
        this.configs.f_capnhattaophieu_visible = false;
        this.configs.f_ketquaXM_visible = false;
        this.configs.f_hoanthien_visible = false;
        this.configs.p_chk_taoyc_enable = false;
        //popupFilter.Enabled = false;
        //gridTBKhoaMay.DataSource = null;
        this.configs.f_nguoinhan_visible = false
        //lookUpNguoiNhanPhieu.Visible = false;
      } else if(this.curren_tab === 6) // đang xác minh
      {
        this.params.p_LOAI_LYDO = -9;
        this.configs.f_giao_nvkt_visible = false;
        this.configs.f_giao_nvkd_visible = false;
        this.configs.f_thoaitra_visible = false;
        this.configs.f_capnhattaophieu_visible = false;
        this.configs.f_ketquaXM_visible = false;
        this.configs.f_hoanthien_visible = false;
        this.configs.p_chk_taoyc_enable = false;
        //popupFilter.Enabled = false;
        //gridTBKhoaMay.DataSource = null;
        this.configs.f_nguoinhan_visible = false
        //lookUpNguoiNhanPhieu.Visible = false;
      }

      this.onXemClick()
    },


    getDSNhanVienTheoThanhToan(thanhtoan_id)
    {
      this.params.p_nguoinhan_options = []
      this.params.p_nguoinhan = null

      this.loading(true)
      NNNC2API.getDSNhanVienTheoThanhToan(this.axios, thanhtoan_id)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
          {
            this.params.p_nguoinhan_options = response.data.data
            this.params.p_nguoinhan = response.data.data[0].NHANVIEN_ID
          }
        }
      )
      .catch(error => {
        //console.log(error)
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
        this.loading(false)
      })
    },

    onGiaoNVKT()
    {
      DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:10px'>Bạn muốn giao phiếu đang chọn cho Nhân viên kỹ thuật xác minh ?</div>",
          okButton: {  text: 'Đồng ý', click: this.confirmGiaoNVKT},
          cancelButton: {  text: 'Hủy'},
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
        });
    },

    confirmGiaoNVKT()
    {
      DialogObj.hide()

      if (this.params.p_nhatky_tn_id === 0)
      {
        this.$toast.error("Bạn chưa chọn phiếu cần giao")
        return
      }
      /*
      if (DatabaseConstants.DB2.Substring(4).ToUpper() == "HNI")
      {

          bangtd_fd.EXECUTE_PROC(DatabaseConstants.DB3 + ".GIAOPHIEU_XULY_NHATKY_TN_V2", new object[]
          {
              "vnhatky_tn_id", nhatky_tn_id,
          "vphieu_id", 0,
          "vkqxl_id", LOAI_LYDO,
              "vnoidung_giaophieu", txtGhiChu.Text,
              "vnhanvien_id", Convert.ToInt32(lookUpNguoiNhanPhieu.EditValue),
              "vnguoi_cn", tt_nd.ma_nd,
              "vmay_cn", tt_nd.may_cn,
              "vip", tt_nd.ip
          });
      }
      else
      */
      {
        let postData = {
          "nhatKyTNId": this.params.p_nhatky_tn_id,
          "phieuId": 0,
          "kqxlId": this.params.p_LOAI_LYDO,
          "noiDungGiaoPhieu": this.params.p_ghichu !== null ? this.params.p_ghichu.trim() : null,
          "nhanVienId": this.params.p_nguoinhan
        }

        this.loading(true)

        NNNC2API.giaoPhieuXLNhatKyThuNo(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined)
            {
              this.$toast.success(response.data.data)
              this.Clear();
              this.onXemClick()
            }
          }
        )
        .catch(error => {
          //console.log(error)
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
      }
    },
    onGiaoNVKD()
    {
      DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:10px'>Bạn muốn giao phiếu đang chọn cho Nhân viên địa bàn xác minh ?</div>",
          okButton: {  text: 'Đồng ý', click: this.confirmGiaoNVKD},
          cancelButton: {  text: 'Hủy'},
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
        });
    },

    confirmGiaoNVKD()
    {
      DialogObj.hide()

      if (this.params.p_nhatky_tn_id === 0)
      {
        this.$toast.error("Bạn chưa chọn phiếu cần giao")
        return
      }
      /*
      if (DatabaseConstants.DB2.Substring(4).ToUpper() == "HNI")
      {

          bangtd_fd.EXECUTE_PROC(DatabaseConstants.DB3 + ".GIAOPHIEU_XULY_NHATKY_TN_V2", new object[]
          {
              "vnhatky_tn_id", nhatky_tn_id,
          "vphieu_id", 0,
          "vkqxl_id", LOAI_LYDO,
              "vnoidung_giaophieu", txtGhiChu.Text,
              "vnhanvien_id", Convert.ToInt32(lookUpNguoiNhanPhieu.EditValue),
              "vnguoi_cn", tt_nd.ma_nd,
              "vmay_cn", tt_nd.may_cn,
              "vip", tt_nd.ip
          });
      }
      else
      */
      {
        let postData = {
          "nhatKyTNId": this.params.p_nhatky_tn_id,
          "phieuId": 0,
          "kqxlId": this.params.p_LOAI_LYDO,
          "noiDungGiaoPhieu": this.params.p_ghichu !== null ? this.params.p_ghichu.trim() : null,
          "nhanVienId": this.params.p_nguoinhan
        }

        this.loading(true)

        NNNC2API.giaoPhieuXLNhatKyThuNo(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined)
            {
              this.$toast.success(response.data.data)
              this.Clear();
              this.onXemClick()
            }
          }
        )
        .catch(error => {
          //console.log(error)
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
      }
    },

    onTaoPhieu()
    {
      DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:10px'>Bạn muốn hoàn thiện xác minh phiếu đang chọn ?</div>",
          okButton: {  text: 'Đồng ý', click: this.confirmTaoPhieu},
          cancelButton: {  text: 'Hủy'},
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
        });
    },

    confirmTaoPhieu()
    {
      DialogObj.hide()

      let vthuhoi = 0
      let mess = ""
      let ktra_ct_no = 1; // Kiểm tra xem đã thanh toán hết nợ hay chưa

      if((this.params.p_ghichu === null
        || ('' + this.params.p_ghichu).trim() === '')
        && this.params.p_chk_tao_yc_khoamay === '1'
      )
      {
        this.$toast.error("Chưa nhập lý do khóa máy")
        return
      }
      let postData = {
        "nhatKyTNId": this.params.p_nhatky_tn_id,
        "tthdId": 10,
        "noiDungGiaoPhieu": this.params.p_ghichu !== null ? this.params.p_ghichu.trim() : null
      }

      this.loading(true)

      NNNC2API.hoanthienXLNhatKyThuNo(this.axios, postData)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
          {
            if(this.params.p_chk_tao_yc_khoamay === '1')
            {

            }
          }
        }
      )
      .catch(error => {
        //console.log(error)
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
        this.loading(false)
      })

    },

    onThoaiTra()
    {
      DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:10px'>Bạn muốn thoái phiếu đang chọn ?</div>",
          okButton: {  text: 'Đồng ý', click: this.confirmThoaiTra},
          cancelButton: {  text: 'Hủy'},
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
        });

    },

    confirmThoaiTra()
    {
      DialogObj.hide()
      if (this.params.p_nhatky_tn_id === 0)
      {
        this.$toast.error('Bạn chưa chọn phiếu cần giao')
        return
      }

      let postData = {
        "nhatKyTNId": this.params.p_nhatky_tn_id,
        "phieuId": 0,
        "kqxlId": 7,
        "noiDungGiaoPhieu": this.params.p_ghichu !== null ? this.params.p_ghichu.trim() : null,
        "nhanVienId": this.$auth.getNhanVienID()
      }

      this.loading(true)

      NNNC2API.giaoPhieuXLNhatKyThuNo(this.axios, postData)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined)
          {
            this.$toast.success(response.data.data)
            this.Clear();
            this.onXemClick()
          }
        }
      )
      .catch(error => {
        //console.log(error)
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
        this.loading(false)
      })



    },

    getDSPhieuXacMinhDaXuLy(nhatky_id)
    {
      this.loading(true)

      this.dsChiTiet = []

      NNNC2API.getDSPhieuXacMinhDaXuLy(this.axios, nhatky_id)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
          {
            this.dsChiTiet = response.data.data
          }
        }
      )
      .catch(error => {
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
        this.loading(false)
      })
    },

    getDSThueBaoTheoMaTT(ma_tt)
    {
      this.loading(true)

      this.dsTBKhoaMay = []

      let postData = {
        maTT: ma_tt
      }

      CommonsAPI.getDanhBaTheoMaThanhToan(this.axios, postData)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
          {
            this.dsTBKhoaMay = response.data.data
          }
        }
      )
      .catch(error => {
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
        this.loading(false)
      })
    },

    onLyDoKTRecordClick(args)
    {
      if(args === null)
      {
        return
      }

      //console.log(args.rowData)


      this.params.p_nhatky_tn_id = args.rowData.NHATKYTN_ID
      this.params.p_r_nhanvien_tra_id = args.rowData.NHANVIEN_TRA_ID
      this.params.p_r_thanhtoan_id = args.rowData.THANHTOAN_ID

      if(this.params.p_r_thanhtoan_id !== 0)
      {
        this.getDSNhanVienTheoThanhToan(this.params.p_r_thanhtoan_id)
      }
    },

    onLyDoKDRecordClick(args)
    {
      if(args === null)
      {
        return
      }

      this.params.p_nhatky_tn_id = args.rowData.NHATKYTN_ID
      this.params.p_r_nhanvien_tra_id = args.rowData.NHANVIEN_TRA_ID
      this.params.p_r_thanhtoan_id = args.rowData.THANHTOAN_ID

      if(this.params.p_r_thanhtoan_id !== 0)
      {
        this.getDSNhanVienTheoThanhToan(this.params.p_r_thanhtoan_id)
      }
    },

    onLyDoKhachHangRecordClick(args)
    {
      if(args === null)
      {
        return
      }

      this.params.p_nhatky_tn_id = args.rowData.NHATKYTN_ID
      this.params.p_r_nhanvien_tra_id = args.rowData.NHANVIEN_TRA_ID
      this.params.p_r_thanhtoan_id = args.rowData.THANHTOAN_ID

      if(this.params.p_r_thanhtoan_id !== 0)
      {
        this.getDSNhanVienTheoThanhToan(this.params.p_r_thanhtoan_id)
      }
    },



    onPhieuDaXuLyRecordClick(args)
    {
      if(args === null)
      {
        return
      }
      this.params.p_nhatky_tn_id = args.rowData.NHATKYTN_ID
    },

    onPhieuChoXuLyRecordClick(args)
    {
      if(args === null)
      {
        this.dsChiTiet = []
        return
      }

      //console.log(args)


      this.params.p_r_tthd_id = args.rowData.TTHD_ID

      //loaitb_id = Convert.ToInt32(grvPhieuChoXuLy.GetRowCellValue(indx, "LOAITB_ID").ToString());
      this.params.p_nhatky_tn_id = args.rowData.NHATKYTN_ID
      this.params.p_ma_tt = args.rowData.MA_TT

      this.getDSPhieuXacMinhDaXuLy(this.params.p_nhatky_tn_id)
      this.getDSThueBaoTheoMaTT(this.params.p_ma_tt)

      if(this.params.p_r_tthd_id === 10 || this.params.p_r_tthd_id === 7)
      {
        this.params.p_chk_tao_yc_khoamay = '0'
        this.params.p_loai_trangthai = 0
      } else
      {
        this.params.p_chk_tao_yc_khoamay = '1'
        this.params.p_loai_trangthai = 1
      }


    },

    Clear()
    {
      this.params.p_ghichu = null
      this.dsChiTiet = []
    },

    onChangeCustomDropDown(id) {
      const isHIdden = $(id).is(":hidden");
      if (isHIdden) {
        $(id).show();
      } else {
        $(id).hide();
      }

      let selectedRows = this.$refs.gridDSTBChem.$refs.grid.getSelectedRecords()
      if(selectedRows.length > 0)
      {
        this.params.ds_tbchem = selectedRows.map(x => x.MA_TB).join(',')
      } else
      {
        this.params.ds_tbchem = null
      }
    },
  },
  provide: {
    multiselect: [CheckBoxSelection]
  }
})

</script>

