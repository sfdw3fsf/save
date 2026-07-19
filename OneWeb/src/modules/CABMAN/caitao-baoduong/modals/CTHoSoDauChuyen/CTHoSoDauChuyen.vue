<template src="./CTHoSoDauChuyen.html">

</template>

<script>
import Tab from './tab/index.vue'
import TabHSPTM from './TabHSPTM'
import moment from 'moment'
import DateTimeLib from "../../../../../utils/DateTimeLib";

export default {
  props: {
    id: String,
  },
  name: "CTHoSoDauChuyen",
  components: {
    Tab,
  },
  data: function () {
    return {
      // resolvePromise: null,
      // rejectPromise: null,
      Tag: null,
      label29: "LẬP HỒ SƠ ĐẤU CHUYỂN",
      Text: "LẬP HỒ SƠ ĐẤU CHUYỂN",
      hscc_id: null,

      dateFormat: 'dd/MM/yyyy HH:mm:ss',
      dateVal: new Date,
      waterMark: 'Chọn ngày',

      txtTenND: null,
      dieukien_hs: false,
      GiaoPhieuTuDong: false,
      huonggiao_id: 0,
      isLoaded: false,
      isReadonly: false,
      new: true,
      layoutControl1: [], // first layout of ptm tab
      isShowChuyen119tooltip: true,
      dataUC_PTM_V3: null,

      hs_ptm: [],
      ds_ptm: [],
      hscc_dv: [],

      tabPageTB: {
        PageVisible: true,
      },
      tabPagePTM: {
        PageVisible: true,
      },

      tsbtnNapLai: {
        Visible: true
      },
      btnDongBo: {
        Visible: true
      },
      btnHoanThanh: {
        Visible: true
      },

      groupChonTB: {
        Enabled: false,
        Visible: false,
      },
      groupDsTB: {
        Enabled: false,
        Visible: false,
      },
      cboTTVT: {
        Enabled: true,
        Value: null,
        DataSource: []
      },
      cboVeTinh: {
        Enabled: true,
        Value: null,
        DataSource: []
      },
      cboLoaiHS: {
        Enabled: true,
        Value: null,
        DataSource: []
      },
      cboKieuCC: {
        Enabled: true,
        Value: null,
        DataSource: []
      },
      cboTrangThaiCC: {
        Enabled: true,
        Value: null,
        DataSource: []
      },
      cboNhomTbi: {
        Enabled: true,
        Value: null,
        DataSource: []
      },
      cboDichVu: {
        Enabled: true,
        Value: null,
        DataSource: []
      },
      chkChuyen119: {
        Enabled: true,
        Value: false,
      },
      cboMucDo: {
        Enabled: true,
        Value: false,
        DataSource: []
      },
      daeNgayBD: null,
      daeNgayKT: null,

      txtTenHS: null,
      txtMaHS: null,
      componentTabHSPTM: null,

    }
  },
  computed: {
    loai_hs_id() {
      return this.cboLoaiHS.Value ? this.cboLoaiHS.Value : -1
    },
    kieu_cc_id() {
      return this.cboKieuCC.Value ? this.cboKieuCC.Value : -1
    },
    ttcc_id() {
      return this.cboTrangThaiCC.Value ? this.cboTrangThaiCC.Value : -1
    },
    ttvt_id() {
      return this.cboTTVT.Value ? this.cboTTVT.Value : -1
    },
    tramtbi_id() {
      return this.cboVeTinh.Value ? this.cboVeTinh.Value.split(":")[0] : -1
    }
  },
  methods: {
    show() {
      // return new Promise((resolve, reject) => {
        this.$bvModal.show(this.id)
      //   this.resolvePromise = resolve
      //   this.rejectPromise = reject
      // })
    },
    async focusElement() {
      this.componentTabHSPTM = this.$refs['tab-hoso-ptm']
      try {
        this.loading(true)
        this.componentTabHSPTM.setTabContentConstructor(TabHSPTM)
        await this.frmCTHoSoDauChuyen_Load()
        await this.NAP_CBO_LOAI_HS_CC()
        await this.NAP_CBO_KIEU_CC()
        await this.NAP_CBO_TRANGTHAI_CC()
        await this.NAP_CBO_DICHVU()
        await this.NAP_CBO_TTVT()
        await this.NAP_CBO_MUCDO()
        if (!this.new && this.hscc_id != -1) {
          // this case for tab TB
          await this.NAP_THONGTIN_HOSO();
          await this.NAP_DS_THUEBAO_DANG_CATCHUYEN();
        }
        this.isLoaded = true;
      } catch (e) {

      } finally {
        this.loading(false)
      }
    },
    hiddenPopup() {
      this.componentTabHSPTM.clear()
      this.clear()
    },
    /**
     * Constructor call by form another, edit
     * @param hscc_id
     */
    frmCTHoSoDauChuyen(hscc_id) {
      this.hscc_id = hscc_id
      this.isNew(false)
    },
    async frmCTHoSoDauChuyen_Load() {
      await this.NAP_DS_NHOM_TBI()
      this.dieukien_hs = await this.KIEMTRA_DK_HOSO_CATCHUYEN()
      if (this.Tag) {
        this.tsbtnNapLai.Visible = false
        if (this.Tag == 2) {
          // this case not THEM HS
          if (this.new) {
            this.label29 = 'THÊM MỚI DANH SÁCH CẢI TẠO, BẢO DƯỠNG'
            this.Text = 'Thêm mới danh sách cải tạo, bảo dưỡng'
          } else {
            this.label29 = 'SỬA DANH SÁCH CẢI TẠO, BẢO DƯỠNG'
            this.Text = 'Sửa danh sách cải tạo, bảo dưỡng'
          }
          this.cboVeTinh.Enabled = false
          // xtcTTTT.ShowTabHeader = DevExpress.Utils.DefaultBoolean.False;
          // if(this.isNew==true)
          //   TAO_TAB_HS();
        } else {
          if (this.new) {
            this.label29 = 'THÊM MỚI DANH SÁCH CẢI TẠO, BẢO DƯỠNG'
            this.Text = 'Thêm mới danh sách cải tạo, bảo dưỡng'
          } else {
            this.label29 = 'SỬA DANH SÁCH CẢI TẠO, BẢO DƯỠNG'
            this.Text = 'Sửa danh sách cải tạo, bảo dưỡng'
          }
          this.cboVeTinh.Enabled = false
        }
      }
      try {
        this.txtTenND = await this.lay_ten_nd()
      }catch (e) {
        if (e.response && e.response.data.message_detail)
          this.$root.toastError(e.response.data.message_detail)
      }
    },
    async KIEMTRA_DK_HOSO_CATCHUYEN() {
      let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/kiemtra-dk-hoso-catchuyen")
      return !!(rs && rs.data && rs.data.length > 0);
    },
    async NAP_DS_NHOM_TBI() {
      try {
        let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/lay-ds-nhom-tbi")
        this.cboNhomTbi.DataSource = rs.data
        if (rs.data.length) this.cboNhomTbi.Value = rs.data[0].NHOMTBI_ID
      } catch (e) {
        this.$root.toastError(e.message)
      }
    },
    async TAO_TAB_TTSC_EDIT(vhsptm_id, vloaitb_id) {
      this.componentTabHSPTM.setTabContentConstructor(TabHSPTM)
      let items = this.componentTabHSPTM.getDsItem()
      let dt = await this.LAY_DS_PTM(vhsptm_id),
        vnhomtb = dt[0].NHOMTBI_ID
      let data = {
        dt: dt,
        vloaitb_id: vloaitb_id,
        vnhomtb: vnhomtb,
        vhsptm_id: vhsptm_id
      }
      this.componentTabHSPTM.addTab(data, 'Hồ sơ PTM ' + (parseInt(items.length + '') + 1), 'UC_PTM_V3')
    },
    TAO_TAB_TTSC() {
      try {
        this.componentTabHSPTM.setTabContentConstructor(TabHSPTM)
        let items = this.componentTabHSPTM.getDsItem()
        let sl_hs_ptm = items.filter(item => item.type == 'UC_PTM_V3').length
        if (sl_hs_ptm >= 1 && this.dieukien_hs) {
          this.$root.toastError("Bạn chỉ có thể thêm 1 hồ sơ phần tử mạng vào 1 hồ sơ cải tạo, bảo dưỡng!")
          return
        }
        this.componentTabHSPTM.addTab(null, 'Hồ sơ PTM ' + (parseInt(items.length + '') + 1), 'UC_PTM_V3')
      }catch (e) {
        console.log(e)
      }
    },
    async TAO_TAB_HOSO_PTM(ds_hsptm) {
      // var ptm = new UC_DS_PTM() { Dock = DockStyle.Fill };
      // xtpNewTab.Controls.Add(ptm);
    },
    async NAP_CBO_LOAI_HS_CC() {
      if (this.Tag) {
        try {
          let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/lay-ds-loai-hoso-catchuyen")
          this.cboLoaiHS.DataSource = rs.data
          this.cboLoaiHS.Value = 5
          this.cboLoaiHS.Enabled = false
        } catch (e) {
          console.log(e)
          this.cboLoaiHS.DataSource = []
          this.$root.toastError(e.message)
        }

      } else {
        //this case not 'THEM DS YEU CAU CAI TAO BAO DUONG'
        // call api where loaihscc_id<>5
      }
    },
    async NAP_CBO_KIEU_CC() {
      try {
        let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/lay-ds-kieu-catchuyen")
        this.cboKieuCC.DataSource = rs.data
        if (rs.data.length) this.cboKieuCC.Value = rs.data[0].KIEUCC_ID
      } catch (e) {
        this.cboKieuCC.DataSource = []
        this.$root.toastError(e.message)
      }
    },
    async NAP_CBO_TRANGTHAI_CC() {
      try {
        let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/lay-ds-trangthai-catchuyen")
        this.cboTrangThaiCC.DataSource = rs.data
        if (rs.data.length) this.cboTrangThaiCC.Value = rs.data[0].TTCC_ID
      } catch (e) {
        this.cboTrangThaiCC.DataSource = []
        this.$root.toastError(e.message)
      }
    },
    async NAP_CBO_DICHVU() {
      try {
        let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/lay-ds-dichvu")
        this.cboDichVu.DataSource = rs.data
        if (rs.data.length) this.cboDichVu.Value = rs.data[0].DICHVUVT_ID
      } catch (e) {
        this.cboDichVu.DataSource = []
        this.$root.toastError(e.message)
      }
    },
    async LAY_DS_DICHVU_PTM_COMPONENT() {
      let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/lay-ds-dichvu-ptm")
      return rs.data
    },
    async NAP_CBO_TTVT() {
      this.cboVeTinh.Value = null
      try {
        let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/lay-ds-ttvt")
        this.cboTTVT.DataSource = rs.data
        if (rs.data.length) this.cboTTVT.Value = rs.data[0].DONVI_ID
      } catch (e) {
        this.cboTTVT.DataSource = []
        this.$root.toastError(e.message)
      }
    },
    async NAP_CBO_MUCDO() {
      try {
        let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/lay-ds-mucdo")
        this.cboMucDo.DataSource = rs.data
        if (rs.data.length) this.cboMucDo.Value = rs.data[0].MUCDO_ID
      } catch (e) {
        this.cboMucDo.DataSource = []
        this.$root.toastError(e.message)
      }
    },
    async NAP_THONGTIN_HOSO() {
      try {
        let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/lay-thongtin-hoso", {hscc_id: this.hscc_id})
        if (rs.data.length) {
          let dataRow = rs.data[0]
          this.txtTenHS = dataRow.TEN_HS
          this.txtMaHS = dataRow.MA_HS_CC
          this.cboLoaiHS.Value = dataRow.LOAIHSCC_ID
          this.cboKieuCC.Value = dataRow.KIEUCC_ID
          this.txtTenND = dataRow.NGUOI_DK
          this.daeNgayBD = moment(dataRow.NGAYBD_DK, 'DD/MM/YYYY HH:mm:ss').toDate()
          this.daeNgayKT = moment(dataRow.NGAYKT_DK, 'DD/MM/YYYY HH:mm:ss').toDate()
          this.cboTTVT.Value = dataRow.TTVT_ID
          this.cboMucDo.Value = dataRow.MUCDO_ID
          this.chkChuyen119 = dataRow.CHAN_BAOHONG == '1'
          if (this.cboVeTinh.DataSource)
            for (let item of this.cboVeTinh.DataSource)
              if (item.DONVI_ID.includes(":"))
                if (item.DONVI_ID.split(":")[0] == dataRow.TRAMTBI_ID) {
                  this.cboVeTinh.Value = item.DONVI_ID
                  break
                }
          this.cboTrangThaiCC.Value = dataRow.TTCC_ID
          if (dataRow.TTCC_ID == 6)
            this.isReadonly = true
          if (dataRow.KIEUCC_ID == 5) {
            // this code tab TB
            // repositoryItemButtonEdit1.Buttons[0].Caption = "Thay dây";
            // btnChuyenCapAll.Text = "Thay dây";
            // btnNhapCapHangLoat.Visible = false;
          } else {
            // this code tab TB
            // repositoryItemButtonEdit1.Buttons[0].Caption = "Đổi thông tin cáp";
            // btnChuyenCapAll.Text = "Chuyển khác vệ tinh";
            // btnNhapCapHangLoat.Visible = true;
          }
          // repositoryItemButtonEdit1.Buttons[0].Tag = dataRow["KIEUCC_ID"].ToString();
          // btnChuyenCapAll.Tag = dataRow["KIEUCC_ID"].ToString();
        }
      } catch (e) {
        this.$root.toastError(e.message)
      }
    },
    async LAY_DS_HOSO_PTM(hscc_id) {
      let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/lay-ds-hoso-ptm", {hscc_id: hscc_id})
      return rs.data
    },
    async LAY_DS_PTM(hsptm_id) {
      let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/lay-ds-ptm", {hsptm_id: hsptm_id})
      return rs.data
    },
    async LAY_DS_DONVI_THEO_LOAIDV(loaidv_id) {
      let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/lay-ds-donvi-theo-loaidv-id", {loaidv_id: loaidv_id})
      return rs.data
    },
    async LAY_DONVI_ID_THEO_HSCC_ID_LOAIDV_ID(hscc_id, loaidv_id) {
      let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/lay-donvi-id-theo-hscc-id-loaidv_id", {
        hscc_id: hscc_id,
        loaidv_id: loaidv_id
      })
      return rs.data
    },
    async NAP_DS_THUEBAO_DANG_CATCHUYEN() {
      // this code tab TB
    },
    async NAP_CBO_VETINH(ttvt_id) {
      try {
        let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/lay-ds-vetinh", {ttvt_id: ttvt_id})
        this.cboVeTinh.DataSource = rs.data
        this.cboVeTinh.Value = null
      } catch (e) {
        this.cboVeTinh.DataSource = []
        this.$root.toastError(e.message)
      }
    },
    async TAO_HOSO_CATCHUYEN_PTM(vhscc_id, vloai, vttvt_id, vnguoi_cn, vmay_cn, vip_cn, vnhanvien_id, vdonvi_nhan_id, vds_hs, vs_ct, vhscc_dv) {
      let rs = await this.$root.context.post("web-cabman/them-hoso-ptm/tao-hoso-catchuyen-ptm", {
        HSCC_ID: vhscc_id,
        LOAI: vloai,
        TTVT_ID: vttvt_id,
        NGUOI_CN: vnguoi_cn,
        MAY_CN: vmay_cn,
        IP_CN: vip_cn,
        NHANVIEN_ID: vnhanvien_id,
        DONVI_NHAN_ID: vdonvi_nhan_id,
        DS_HS: vds_hs,
        S_CT: vs_ct,
        HSCC_DV: vhscc_dv
      })
      return rs.data
    },
    async SUA_HOSO_CATCHUYEN_PTM(vhscc_id, vloai, vttvt_id, vnguoi_cn, vmay_cn, vip_cn, vnhanvien_id, vdonvi_nhan_id, vds_hs, vs_ct, vhscc_dv) {
      let rs = await this.$root.context.post("web-cabman/them-hoso-ptm/sua-hoso-catchuyen-ptm", {
        HSCC_ID: vhscc_id,
        LOAI: vloai,
        TTVT_ID: vttvt_id,
        NGUOI_CN: vnguoi_cn,
        MAY_CN: vmay_cn,
        IP_CN: vip_cn,
        NHANVIEN_ID: vnhanvien_id,
        DONVI_NHAN_ID: vdonvi_nhan_id,
        DS_HS: vds_hs,
        S_CT: vs_ct,
        HSCC_DV: vhscc_dv
      })
      return rs.data
    },
    async KIEMTRA_PHIEUCC_ID(hscc_id) {
      let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/kiemtra-phieucc-id", {hscc_id: hscc_id})
      return rs.data
    },
    async XOA_HOSO_CC(hscc_id) {
      await this.$root.context.post(`web-cabman/them-hoso-ptm/xoa-hoso-cc?hscc_id=${hscc_id}`).then(res =>{
      }).catch(e =>{
        if (e.response.data && e.response.data.message_detail) this.$root.toastError(e.response.data.message_detail)
      })
    },
    async GIAOPHIEU_CAITAO_BAODUONG(phieu_id, hscc_id, donvi_id, huonggiao_id, nhanvien_id, nguoidung_id, noidung, may_cn, nguoi_cn, ip_cn) {
      let rs = await this.$root.context.post("web-cabman/them-hoso-ptm/giaophieu-caitao-baoduong", {
        PHIEU_ID: phieu_id,
        HSCC_ID: hscc_id,
        DONVI_ID: donvi_id,
        HUONGGIAO_ID: huonggiao_id,
        NHANVIEN_ID: nhanvien_id,
        NGUOIDUNG_ID: nguoidung_id,
        NOIDUNG: noidung,
        MAY_CN: may_cn,
        NGUOI_CN: nguoi_cn,
        IP_CN: ip_cn,
      })
      return rs.data
    },
    async tao_ma_hscc() {
      let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/sinh-ma-hscc")
      return rs.data
    },
    async getKey(key) {
      let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/get-key", {key: key})
      return rs.data
    },
    async lay_ten_nd() {
      let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/lay-ten-nd", {nguoidung_id: this.$auth.getNguoiDungID()})
      return rs.data
    },
    kiemtra_hs() {
      let sl = 0
      let items = this.componentTabHSPTM.getDsItem()
      for (let item of items) {
        if (item.type == 'UC_PTM_V3') {
          sl++
          let dl = this.componentTabHSPTM.$refs[item.id][0].lay_ds(),
            valid = this.componentTabHSPTM.$refs[item.id][0].get_valid()
          if (valid == false)
            return 2
          if (dl == null || dl.length <= 0)
            return 1
        }
      }
      if (sl == 0) {
        return 1
      }
      return 0
    },
    kiemtra_hs_ptm() {
      // not tag = 1
      return false
    },
    KIEMTRA(isInsert) {
      if (!this.txtTenHS || (this.txtTenHS + '').trim() == '') {
        this.$refs.txtTenHS.focus()
        this.$root.toastError("Chưa nhập tên hồ sơ")
        return false
      }
      if (this.txtTenHS && this.txtTenHS.length > 200) {
        this.$refs.txtTenHS.focus()
        this.$root.toastError("Tên hồ sơ không đươc vượt quá 200 ký tự")
        return false
      }
      if (this.loai_hs_id == -1) {
        this.$root.toastError("Chưa chọn loại hồ sơ")
        return false
      }
      if (this.kieu_cc_id == -1) {
        this.$root.toastError("Chưa chọn hình thức đấu chuyển")
        return false
      }
      if (this.daeNgayBD == null) {
        this.$refs.daeNgayBD.focusIn()
        this.$root.toastError("Chưa chọn ngày bắt đầu");
        return false;
      }
      if (this.daeNgayKT == null) {
        this.$refs.daeNgayKT.focusIn()
        this.$root.toastError("Chưa chọn ngày kết thúc");
        return false;
      }
      if (this.daeNgayBD > this.daeNgayKT) {
        this.$refs.daeNgayKT.focusIn();
        this.$root.toastError("Ngày bắt đầu không được sau ngày kết thúc");
        return false;
      }
      if (this.loai_hs_id != 5) {
        if (this.tramtbi_id == -1) {
          this.$refs.cboVeTinh.focus();
          this.$root.toastError("Chưa chọn vệ tinh");
          return false;
        }
      }
      return true;
    },
    async TAO_DULIEU_UC_PTM_V3(isNew) {
      let items = this.componentTabHSPTM.getDsItem()
      for (let item of items) {
        if (item.type == 'UC_PTM_V3') {
          let hs_ptm_id
          if (!isNew) {
            hs_ptm_id = this.componentTabHSPTM.$refs[item.id][0].componentUC_PTM_V3.tag
            if (hs_ptm_id == null || hs_ptm_id == 0) hs_ptm_id = await this.getKey('HSPTM_ID')
          } else {
            hs_ptm_id = await this.getKey('HSPTM_ID')
          }
          let dl = this.componentTabHSPTM.$refs[item.id][0].lay_ds()
          if (dl.length > 0) {
            let dr_ds = {}
            dr_ds.HSPTM_ID = hs_ptm_id
            dr_ds.HSCC_ID = this.hscc_id
            dr_ds.LOAITBI_ID = dl[0].LOAITBI_ID
            dr_ds.SOLUONG = dl.length
            this.hs_ptm.push(dr_ds)
            for (let i of dl) {
              let dr_ptm = {}
              dr_ptm.HSCC_ID = this.hscc_id
              dr_ptm.HSPTM_ID = hs_ptm_id
              dr_ptm.LOAITBI_ID = i.LOAITBI_ID
              dr_ptm.PTM_ID = i.THIETBI_ID
              dr_ptm.TINHTRANG_ID = i.TINHTRANG_ID
              dr_ptm.GHICHU = i.GHICHU
              dr_ptm.NGAY_BD = this.ddmmyyyyhhmiss(i.NGAY_BD)
              dr_ptm.NGAY_KT = this.ddmmyyyyhhmiss(i.NGAY_KT)
              this.ds_ptm.push(dr_ptm)
            }
          }
        }
      }
      for (let item of this.layoutControl1) {
        let dr = {}
        dr.HSCC_ID = this.hscc_id
        dr.LOAIDV_ID = item.data().tag
        dr.DONVI_ID = item.data().value
        this.hscc_dv.push(dr)
      }
    },
    async tsbtnGhiLai_Click() {
      try {
        if (this.kiemtra_hs() == 1 && this.loai_hs_id == 5 && this.Tag == "1") {
          this.$root.toastError("Chưa có hồ sơ ptm hoặc hồ sơ ptm chưa có ptm!");
          return;
        }
        if (this.kiemtra_hs() == 2 && this.loai_hs_id == 5 && this.Tag == "1") {
          this.$root.toastError("Dữ liệu ngày bắt đầu và kết thúc chưa chính xác!");
          return;
        }
        if (!this.KIEMTRA(this.new)) return
        if (!this.kiemtra_hs_ptm() && this.loai_hs_id == 5 && this.Tag == "2") {
          this.$root.toastError("Chưa có hồ sơ ptm!");
          return;
        }
        this.loading(true)
        if (this.new) {
          let ma_hscc = await this.tao_ma_hscc()
          if (this.loai_hs_id == 5) {
            this.hs_ptm = []
            this.ds_ptm = []
            this.hscc_dv = []
            let hs_catchuyen = {
              TEN_HS: this.txtTenHS,
              LOAIHSCC_ID: this.loai_hs_id,
              KIEUCC_ID: this.kieu_cc_id,
              TTCC_ID: this.ttcc_id,
              NGAYBD_DK: this.ddmmyyyyhhmiss(this.daeNgayBD),
              NGAYKT_DK: this.ddmmyyyyhhmiss(this.daeNgayKT),
              NGUOI_DK_ID: this.$auth.getNguoiDungID(),
              TTVT_ID: this.ttvt_id,
              CHAN_BAOHONG: this.chkChuyen119 ? 1 : 0,
              NHANVIEN_ID: this.$auth.getNhanVienID(),
              DONVI_ID: this.$auth.getDonViID(),
              NGUOIDUNG_ID: this.$auth.getNguoiDungID(),
              NGUOI_TAO: this.$auth.getUserName(),
              NGUOI_CN: this.$auth.getUserName(),
              MAY_CN: 'Unknown',
              IP_CN: 'Unknown',
              MA_HS_CC: ma_hscc,
              MUCDO_ID: this.cboMucDo.Value,
            }
            await this.$root.context.post("web-cabman/them-hoso-ptm/themmoi-hs-catchuyen", hs_catchuyen).then(res => {
              if (res.error_code == "BSS-00000000" && res.data && res.data.length) {
                this.hscc_id = res.data[0].HSCC_ID
              }
            }).catch(e => {
              if (e.response && e.response.data.message_detail)
                this.$root.toastError(e.response.data.message_detail)

            })
          } else {
            // this case not loai_hs_id = 5 // not use for loai_hs_id = 5, tag = 1
          }
          if (this.loai_hs_id == 5 && this.Tag == 1) {
            await this.TAO_DULIEU_UC_PTM_V3()
            let kq =
              await this.TAO_HOSO_CATCHUYEN_PTM(
                this.hscc_id, parseInt(this.Tag + '', 10), this.ttvt_id, this.$auth.getUserName(),
                'Unknown', 'Unknown', this.$auth.getNhanVienID(), 0,
                this.hs_ptm, this.ds_ptm, this.hscc_dv)
            if (kq == 'OK') {
              if (this.GiaoPhieuTuDong) {
                let donvi_id = this.$auth.getDonViID()
                let rs = await this.KIEMTRA_PHIEUCC_ID(this.hscc_id)
                if (rs.length == 0) {
                  await this.XOA_HOSO_CC()
                  this.$root.toastError("Có lỗi khi cập nhật hồ sơ!")
                  return
                } else {
                  let phieu_id = rs[0].PHIEUCC_ID, noidung_giao = ""
                  let result =
                    await this.GIAOPHIEU_CAITAO_BAODUONG(phieu_id, this.hscc_id, donvi_id, this.huonggiao_id,
                      this.$auth.getNhanVienID(), this.$auth.getNguoiDungID(), noidung_giao, 'Unknown',
                      this.$auth.getUserName(), 'Unknown')
                  if (result != 'OK') {
                    this.$root.toastError("Có lỗi khi cập nhật hồ sơ!")
                    await this.XOA_HOSO_CC(this.hscc_id);
                    return
                  }
                }
              }
              this.$root.toastSuccess("Cập nhật hồ sơ thành công!")
            } else {
              this.$root.toastError("Có lỗi! " + kq)
            }
          } else if (this.loai_hs_id == 5 && this.Tag == 2) {
            // this form using for loai_hs_id == 5, tag == 1
          }
          this.isNew(false)
        } else {
          // edit
          if (this.loai_hs_id == 5) {
            let hs_catchuyen = {
              HSCC_ID: this.hscc_id,
              TEN_HS: this.txtTenHS,
              NGAYBD_DK: this.ddmmyyyyhhmiss(this.daeNgayBD),
              NGAYKT_DK: this.ddmmyyyyhhmiss(this.daeNgayKT),
              NGUOI_CN: this.$auth.getUserName(),
              MAY_CN: 'Unknown',
              IP_CN: 'Unknown',
              MUCDO_ID: this.cboMucDo.Value,
            }
            try {
              let res = await this.$root.context.post("web-cabman/them-hoso-ptm/capnhat-hs-catchuyen", hs_catchuyen)
              if (res.data && res.data.length && res.data[0].KQ == 0) {
                this.$root.toastError("Cập nhật hồ sơ cắt chuyển thất bại!")
                return
              }
            } catch (e) {
              if (e.response && e.response.data.message_detail)
                this.$root.toastError(e.response.data.message_detail)
              return
            }
            this.hs_ptm = []
            this.ds_ptm = []
            this.hscc_dv = []
            if (this.Tag == 1) {
              await this.TAO_DULIEU_UC_PTM_V3()
              let kq =
                await this.SUA_HOSO_CATCHUYEN_PTM(this.hscc_id, parseInt(this.Tag + '', 10), this.ttvt_id,
                  this.$auth.getUserName(), 'Unknown', 'Unknown', this.$auth.getNhanVienID(),
                  0, this.hs_ptm, this.ds_ptm, this.hscc_dv)
              if (kq == 'OK') {
                this.$root.toastSuccess("Cập nhật hồ sơ thành công!")
              } else {
                this.$root.toastError("Có lỗi! " + kq)
              }
            } else if (this.Tag == 2) {
              // this form using for loai_hs_id == 5, tag == 1

            }
          } else {
            //case : loai_hs_id != 5
            // not handle
          }
        }
        // IsModified = true; for tab TB
        this.$emit("NAP_DS_HOSO_CATCHUYEN")
        await this.NAP_THONGTIN_HOSO()
        await this.NAP_DS_THUEBAO_DANG_CATCHUYEN()
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    isNew(value) {
      this.new = value
      this.groupChonTB.Enabled = !value;
      this.groupDsTB.Enabled = !value;
      this.cboTTVT.Enabled = value;
      this.cboVeTinh.Enabled = value;
      this.cboLoaiHS.Enabled = value;
      this.cboKieuCC.Enabled = value;
      this.chkChuyen119.Enabled = value;
    },
    clear() {
      this.txtMaHS = null
      this.txtTenHS = null
      this.txtTenND = null
      this.daeNgayBD = null
      this.daeNgayKT = null
      this.isNew(true)
      this.cboTTVT.DataSource = []
      this.cboTTVT.Value = null
      this.cboVeTinh.DataSource = []
      this.cboVeTinh.Value = null
      this.cboLoaiHS.DataSource = []
      this.cboLoaiHS.Value = null
      this.cboKieuCC.DataSource = []
      this.cboKieuCC.Value = null
      this.cboTrangThaiCC.DataSource = []
      this.cboTrangThaiCC.Value = null
      this.cboNhomTbi.DataSource = []
      this.cboNhomTbi.Value = null
      this.cboDichVu.DataSource = []
      this.cboDichVu.Value = null
      this.cboMucDo.DataSource = []
      this.cboMucDo.Value = null
    },
    ddmmyyyy(date) {

      if (!date) {
        return ''
      }

      date = this.getDateElements(date)
      return `${date.d}/${date.M}/${date.y}`
    },
    ddmmyyyyhhmiss(date) {
      if (!date) return ''
      let data = this.getDateElements(date)
      return `${data.d}/${data.M}/${data.y} ${data.h}:${data.m}:${data.s}`
    },
    getDateElements(date) {

      date = new Date(date)

      let day = date.getDate()
      day = day > 9 ? day : '0' + day

      let month = date.getMonth() + 1
      month = month > 9 ? month : '0' + month

      let hour = date.getHours()
      hour = hour > 9 ? hour : '0' + hour

      let min = date.getMinutes()
      min = min > 9 ? min : '0' + min

      let second = date.getSeconds()
      second = second > 9 ? second : '0' + second

      return {
        y: date.getFullYear(),
        M: month,
        d: day,
        h: hour,
        m: min,
        s: second
      }
    },
  },
  watch: {
    'cboTTVT.Value': async function (val) {
      this.NAP_CBO_VETINH(val)
    },
    daeNgayBD: function (val) {
      if (val > this.daeNgayKT)
        this.daeNgayKT = val
    },
    daeNgayKT: function (val) {
      if (this.daeNgayBD > val) {
        this.daeNgayBD = val
      }
    },
    "chkChuyen119.Value": function (val) {
      if (val) {
        if (!this.isShowChuyen119tooltip) return
        this.isShowChuyen119tooltip = false
        this.$root.toastInfo("*Lưu ý: Khi tick chọn Chuyển 119, nếu thuê bao trong dánh sách đấu chuyển chưa được hoàn thiện mà khách hàng báo hỏng thì sẽ không được phép nhập phiếu báo hỏng.")
      }
    },
    "cboLoaiHS.Value": async function (val) {
      try {
        if (val == 5) {
          this.layoutControl1 = []
          this.tabPageTB.PageVisible = false
          this.tabPagePTM.PageVisible = true
          this.btnDongBo.Visible = false
          this.btnHoanThanh.Visible = false
          let dtControl = await this.LAY_DS_DICHVU_PTM_COMPONENT()
          if (!this.new) { // SỬA DS CẢI TẠO, BẢO DƯỠNG
            if (this.Tag == 1) {
              // this case for edit PTM
              let ds_hs_ptm = await this.LAY_DS_HOSO_PTM(this.hscc_id)
              for (let item of ds_hs_ptm) {
                let hs_id = item.HSPTM_ID,
                  vloaitbi_id = item.LOAITBI_ID
                await this.TAO_TAB_TTSC_EDIT(hs_id, vloaitbi_id)
              }
            } else {
              // this case not tag 1. Not use
              let ds_hs_ptm = await this.LAY_DS_HOSO_PTM(this.hscc_id)
              await this.TAO_TAB_HOSO_PTM(ds_hs_ptm)
            }
          }
          for (let i = 0; i < dtControl.length; i++) {
            let lk = {}
            lk.Tag = dtControl[i].LOAIDV_DICH_ID
            let dt = await this.LAY_DS_DONVI_THEO_LOAIDV(dtControl[i].LOAIDV_DICH_ID)
            lk.DataSource = dt
            // this.layoutControl1_DataSource = new Array(dtControl.length)
            // this.layoutControl1_DataSource[i] = dt
            if (dt.length > 0) {
              if (!this.new) { // edit ptm
                let donvi_id = await this.LAY_DONVI_ID_THEO_HSCC_ID_LOAIDV_ID(this.hscc_id, dtControl[i].LOAIDV_DICH_ID)
                donvi_id = parseInt(donvi_id, 10)
                if (isNaN(donvi_id)) lk.Value = dt[0].DONVI_ID
                else lk.Value = donvi_id
              } else lk.Value = dt[0].DONVI_ID
            } else lk.Value = null
            // 4 combo per a row
            let dynamic_component = {
              template: `
                <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key">${dtControl[i].TEN_LOAIDV}</div>
                  <div class="value">
                    <SelectExt 
                      @change="handleChange"
                      v-model.trim="value"
                      :dataSource="dataSource"
                      dataTextField="TEN_DV"
                      dataValueField="DONVI_ID"/>
                  </div>
                </div>
                </div>
              `,
              data: function () {
                return {
                  tag: lk.Tag,
                  dataSource: lk.DataSource,
                  value: lk.Value
                }
              },
              mounted() {
                this.dataSource = this.dataSource.map(item => {
                  return {
                    DONVI_ID: item.DONVI_ID,
                    TEN_DV: item.TEN_DV
                  }
                })
              },
              methods: {
                  handleChange(value) {
                    lk.Value=value;
                    console.log('Selected:', value);
                  },
              }
            }
            this.layoutControl1.push(dynamic_component)
          }

        } else {
          // this case for tab TB
          this.tabPageTB.PageVisible = true;
          this.tabPagePTM.PageVisible = false;
          this.btnDongBo.Visible = true;
          this.btnHoanThanh.Visible = true;
        }
      } catch (e) {
        console.log(e)
      }
    },
  },
}
</script>

<style>
@media (min-width: 1200px) {
  .modal-xl {
    max-width: 95% !important;
  }
}
.ct-hoso-dauchuyen .select2-container .select2-selection--single .select2-selection__rendered {
  white-space: initial!important;
  height: 28px;
}

.ct-hoso-dauchuyen .col-tab {
  flex: 0 0 8.333333%;
  max-width: 4.333333%;
}
.ct-hoso-dauchuyen .col-tab-content {
  flex: 0 0 91.666667%;
  max-width: 95.666667%;
}

.ct-hoso-dauchuyen .tabs a {
  min-width: 50px !important;
}
</style>
