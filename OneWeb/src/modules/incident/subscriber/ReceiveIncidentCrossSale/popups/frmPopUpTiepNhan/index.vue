<template src="./index.html">

</template>

<script>

import api from "./api";
import config from "./config";
import moment from "moment";
import {DichVuVienThong, LoaiHinhTB} from "../../../../../../const/enums";
import ExportDataModal from "@/modules/contract/profile/ExportData/ExportDataModal";
import NoiDungTT from "../../../UpdateProcessIncident/popups/NoiDungTT";
import NoiDung from "../../../UpdateProcessIncident/popups/NoiDung";
import PopupGuiSms from '@/modules/install/UpdateInstallProgress/popups/PopupGuiSms.vue'
import frmTraCuuMDF from '../../../HandleAcceptanceIncidentReports/Popups/TraCuuMDF/TraCuuMDF.vue';
import frmPhanAnhBH from "../../../SearchIncidentUpdateAppointment/components/frmPhanAnhBH";
import frmTraCuuPhieuBH from "../../../SearchIncidentUpdateAppointment"
import CheckPort from '@/modules/search/subscriber/CheckPort';

export default {
  name: "frmPopUpTiepNhan",
  components: {
    ExportDataModal,
    NoiDungTT,
    NoiDung,
    PopupGuiSms,
    frmTraCuuMDF,
    frmPhanAnhBH,
    CheckPort,
    frmTraCuuPhieuBH
  },
  data() {
    return {
      headerTitle: 'Cập nhật tiến trình báo hỏng',
      resolvePromise: null,
      rejectPromise: null,
      fparent: false,
      thuebao_id: -1,
      fields: '',
      ds_donvi: [],
      cboLoaiNhom: {
        value: 0,
        datasource: []
      },
      cboLoaiHDPL: {
        value: 0,
        datasource: []
      },
      cboDichVuVT: {
        value: 0,
        datasource: []
      },
      cboTrangThaiBH: {
        value: 0,
        datasource: []
      },
      gridDanhSach: [],
      vdonvi_id: 0,
      vngaybh_tu: moment().format('DD/MM/YYYY'),
      vngaybh_den: moment().format('DD/MM/YYYY'),
      vngaynt_tu: moment().format('DD/MM/YYYY'),
      vngaynt_den: moment().format('DD/MM/YYYY'),
      chkNgayBH: false,
      chkNgayNT: false,
      tttb: {
        ma_tb: '',
        ten_tb: '',
        kieu_ld: '',
        ma_lt: '',
        ma_gd: '',
        diachi_ld: '',
        ghichu: '',
      },
      gridTienTrinh: [],
      gridTienTrinhCols: config.gridTienTrinhCols,
      gridDanhSachCols: config.gridDanhSachCols,
      tsmMoTamNgung: {
        Visible: true
      },
      tsbtnCapNhatMucDo: {
        Enabled: true,
        Visible: true,
      },
      btnNhapMoi: {
        Enabled: false
      },
      itemFocused: null,
      itemTTFocused: null,
      HPG_ID: 26,
      vuser_alt: '',
      vpass_alt: '',
      ndtt: {
        ndtt_vtinh: null,
        ndtt_tag: "",
        ndtt_vtientrinhbh_id: null,
        ndtt_vnhom_id: null,
        ndtt_vbaohong_id: null,
        ndtt_vnoidung_goc: "",
        ndtt_vnd_baohong: "",
      },
      smsProp: {
        nhomId: null,
        baoHongId: 0,
        maTB: '',
        noiDung: '',
        tienTrinhBHId: null,
      },
      nd_tientrinhbh_id: null,
      position: {X: 'center', Y: 'top'},
      smsSentList: [],
      frmPhanAnhBHInput: {},
      checkPortDialog: {
        isVisiable: false,
        input: {
          vthuebao_id: 0,
          vma_tb: 0
        }
      },
      animationSettings: { effect: 'None' },
    }
  },
  watch: {
    'cboLoaiNhom.value': function (newVal, oldVal) {
      this.cboLoaiNhom_SelectedIndexChanged();
    }
  },
  computed: {
    gridDanhSachLength() {
      return this.gridDanhSach == null ? 0 : this.gridDanhSach.length;
    },
    phanVungID() {
      return parseInt(this.$root.token.getPhanVungID());
    },
    nhanVienID() {
      return parseInt(this.$root.token.getNhanVienID());
    },
    vnhom_id() {
      return parseInt(this.cboLoaiNhom.value);
    },
    vdichvuvt_id() {
      return parseInt(this.cboDichVuVT.value)
    },
    vtrangthaibh_id() {
      return parseInt(this.cboTrangThaiBH.value);
    },
    vngaybh_1: {
      get() {
        if (this.chkNgayBH)
          return this.vngaybh_tu;
        return "";
      }
    },
    vngaybh_2:
      {
        get() {
          if (this.chkNgayBH)
            return this.vngaybh_den;
          return "";
        }
      },
    vngaynt_1:
      {
        get() {
          if (this.chkNgayNT)
            return this.vngaynt_tu
          return "";
        }
      },
    vngaynt_2:
      {
        get() {
          if (this.chkNgayNT)
            return this.vngaynt_den;
          return "";
        }
      }
  },
  methods: {
    handleShowModal() {
      this.resolvePromise = null;
    },

    hideModal() {
      this.$refs.frmPopUpTiepNhan.hide();
      this.resolvePromise(true);
    },

    initData(fparent, thuebao_id, dichvu_id) {
      try {
        this.fparent = fparent;
        this.thuebao_id = thuebao_id;
        this.cboDichVuVT.value = dichvu_id;
      } catch (error) {
        this.$root.$toast.error('Có lỗi: ' + error)
      }
    },

    showModal() {
      return new Promise(async (resolve, reject) => {
        this.$refs.frmPopUpTiepNhan.show();
        await this.frmTienTrinhBH_v2_Load()

        this.resolvePromise = resolve;
        this.rejectPromise = reject;
      });
    },

    async frmTienTrinhBH_v2_Load() {
      this.loading(true);
      this.tsbtnCapNhatMucDo.Visible = this.phanVungID === this.HPG_ID;
      await this.NAP_TVW_DONVI();
      await this.NAP_CBO_LOAI_NHOM();
      await this.NAP_CBO_DICHVU_VT();
      await this.NAP_CBO_TRANGTHAI();
      if (this.fparent) {
        await this.NAP_GRD_DANHSACH();
      }

      let vuser_alt = await this.newMapData(
        '/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tinh',
        {param: this.$root.token.getPhanVungID(), type: 2});
      if (vuser_alt == "" || vuser_alt == "-1") {
        this.vuser_alt = "";
      } else {
        this.vuser_alt = vuser_alt;
      }

      let vpass_alt = await this.newMapData(
        '/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tinh',
        {param: this.$root.token.getPhanVungID(), type: 3});
      if (vpass_alt == "" || vpass_alt == "-1") {
        this.vpass_alt = "";
      } else {
        this.vpass_alt = vpass_alt;
      }
      this.loading(false);
    },

    async NAP_TVW_DONVI() {
      let response = await api.NAP_TVW_DONVI(this.axios, {
        vnhanvien_id: this.nhanVienID
      });
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.ds_donvi = response.data.data
      }

      let lvtDonVi = this.createTreeDonVi(this.ds_donvi);
      lvtDonVi[0].expanded = true
      this.fields = {
        dataSource: lvtDonVi,
        id: 'donvi_id',
        text: 'ten_dv',
        parentID: 'donvi_cha_id',
        hasChildren: 'hasChild'
      }
    },

    createTreeDonVi(lvtDonVi) {
      let tree = []
      let lvtChaArray = lvtDonVi.map(item => (item['donvi_cha_id']))
      lvtDonVi.forEach(item => {
        if (lvtChaArray.includes(item['donvi_id'])) {
          tree.push({...item, hasChild: true, expanded: false})
        } else {
          tree.push(item)
        }
      })
      return tree;
    },

    selectDonViFocusedNodeChange(node) {
      this.vdonvi_id = Number(node.nodeData.id)
    },

    async NAP_CBO_LOAI_NHOM() {
      try {
        let rs = await api.NAP_CBO_LOAI_NHOM(this.axios, {});
        if (rs.data.data != null) {
          this.cboLoaiNhom = {
            value: rs.data.data[0].nhom_id,//ten_nhom
            datasource: rs.data.data
          }
        }
      } catch (e) {
        console.log(e);
      }
    },

    async NAP_CBO_DICHVU_VT() {
      try {
        let rs = await api.NAP_CBO_DICHVU_VT(this.axios, {});
        if (rs.data.data != null) {
          this.cboDichVuVT = {
            value: rs.data.data[0].dichvuvt_id,//ten_dvvt
            datasource: rs.data.data
          }
        }
      } catch (e) {
        console.log(e);
      }
    },

    async NAP_CBO_TRANGTHAI() {
      try {
        let rs = await api.NAP_CBO_TRANGTHAI(this.axios, {});
        if (rs.data.data != null) {
          this.cboTrangThaiBH = {
            value: rs.data.data[0].ttbh_id,//trangthai_bh
            datasource: rs.data.data
          }
        }
      } catch (e) {
        console.log(e);
      }
    },

    async NAP_GRD_DANHSACH() {
      try {
        let vdichvuvt_id = this.cboDichVuVT.value;
        let vtrangthaibh_id = this.cboTrangThaiBH.value;
        let {vdonvi_id} = this;
        if (this.fparent) {
          this.gridDanhSach = await this.Lay_ds_popup_tientrinh_bh(vdichvuvt_id, 1, this.thuebao_id);
        } else {
          if (this.cboLoaiNhom.value == 2) //Báo hỏng
            this.gridDanhSach = await this.Lay_ds_phieu_cn_tientrinh_bh(vdichvuvt_id, vdonvi_id, vtrangthaibh_id, this.vngaybh_1, this.vngaybh_2, this.vngaynt_1, this.vngaynt_2);
          else // bảo dưỡng
            this.gridDanhSach = await this.Lay_ds_phieu_cn_tientrinh_bd(vdichvuvt_id, vdonvi_id, vtrangthaibh_id, this.vngaybh_1, this.vngaybh_2, this.vngaynt_1, this.vngaynt_2);
        }
      } catch (ex) {
        this.$toast.error(ex);
      }
    },

    async Lay_ds_popup_tientrinh_bh(vdichvuvt_id, vtrangthaibh_id, vthuebao_id) {
      try {
        let rs = await api.lay_ds_popup_baohong(this.axios, {
          vdichvuvt_id,
          vtrangthaibh_id,
          vthuebao_id
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }
      return [];
    },
    async Lay_ds_phieu_cn_tientrinh_bh(vdichvuvt_id, vdonvi_id, vtrangthaibh_id, vngaybh_tu, vngaybh_den, vngaynt_tu, vngaynt_den) {
      try {
        let rs = await api.lay_ds_phieu_cn_tientrinh_bh(this.axios, {
          vdichvuvt_id,
          vdonvi_id,
          vtrangthaibh_id,
          vngaybh_tu,
          vngaybh_den,
          vngaynt_tu,
          vngaynt_den
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }
      return [];
    },
    async Lay_ds_phieu_cn_tientrinh_bd(vdichvuvt_id, vdonvi_id, vtrangthaibh_id, vngaybh_tu, vngaybh_den, vngaynt_tu, vngaynt_den) {
      try {
        let rs = await api.lay_ds_phieu_cn_tientrinh_bd(this.axios, {
          vdichvuvt_id,
          vdonvi_id,
          vtrangthaibh_id,
          vngaybh_tu,
          vngaybh_den,
          vngaynt_tu,
          vngaynt_den
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }
      return [];
    },

    cboLoaiNhom_SelectedIndexChanged() {
      this.NAP_CBO_LOAI_HD_PL();
    },

    async NAP_CBO_LOAI_HD_PL() {
      try {
        let rs = await api.NAP_CBO_LOAI_HD_PL(this.axios, this.vnhom_id);
        if (rs.data.data != null) {
          this.cboLoaiHDPL = {
            value: rs.data.data[0].loaihd_id,//ten_loaihd
            datasource: rs.data.data
          }
        }
      } catch (e) {
        console.log(e);
      }
    },

    gridDanhSach_selectedRowChanged(args) {
      let itemFocused = args.data;
      this.itemFocused = itemFocused;
      if (this.gridDanhSachLength <= 0) return;
      this.NAP_GRD_TIENTRINH();
      if (this.phanVungID === this.HPG_ID) {
        let vloaitb_id = parseInt(itemFocused.loaitb_id);
        this.tsmMoTamNgung.Visible = vloaitb_id === LoaiHinhTB.INTERNET_MYTV;
      } else {
        this.tsmMoTamNgung.Visible = false;
      }
    },

    gridTienTrinh_selectedRowChanged(args) {
      this.itemTTFocused = args.data;
    },

    async NAP_GRD_TIENTRINH() {
      if (this.itemFocused) {
        let itemFocused = this.itemFocused;
        let vbaohong_id = itemFocused.baohong_id;
        let vthuebao_id = itemFocused.thuebao_id;
        let vdichvuvt_id = itemFocused.dichvuvt_id;
        let vtrangthai_id = await this.newMapData(
          "/web-tratruoc/thongtin_tratruoc_dn/fn_tt_baohong",
          {param: vbaohong_id, type: 4});

        this.tttb = {
          ...this.tttb,
          ma_tb: itemFocused.ma_tb,
          ten_tb: itemFocused.ten_tb,
          kieu_ld: itemFocused.ten_kieuld,
          ma_gd: itemFocused.ma_gd,
          diachi_ld: itemFocused.diachi_ld,
          ghichu: itemFocused.ghichu_tb,
        };

        let vdanhba_con = "";
        switch (vdichvuvt_id) {
          case DichVuVienThong.CO_DINH:
            vdanhba_con = "db_cd";
            break;
          case DichVuVienThong.ADSL:
          case DichVuVienThong.MEGA_EYES:
            vdanhba_con = "db_adsl";
            break;
          case DichVuVienThong.TSL:
            vdanhba_con = "db_tsl";
            break;
        }

        if (vdanhba_con !== "")
          this.tttb.ma_lt = await this.newMapData(
            "/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_con",
            {param: vthuebao_id, type: 1}
          );

        await this.getDSTienTrinh(this.vnhom_id, vbaohong_id);
        for (let i = 0; i < this.gridTienTrinh.length; i++) {
          let id = "onSMS_" + this.gridTienTrinh[i].tientrinhbh_id + "_" + this.gridTienTrinh[i].noidung
          this.gridTienTrinh[i].sms_command = "<button class='btn btn-sm btn-primary' id='" + id + "'><i class='fa fa-envelope'></button>"
          id = "onSua_" + this.gridTienTrinh[i].tientrinhbh_id
          this.gridTienTrinh[i].sua_command = "<button class='btn btn-sm btn-warning' id='" + id + "'><i class='fa fa-pencil'></i></button>"
          id = "onXoa_" + this.gridTienTrinh[i].tientrinhbh_id
          this.gridTienTrinh[i].xoa_command = "<button class='btn btn-sm btn-danger' id='" + id + "'><i class='fa fa-times'></button>"
        }

        if (this.vnhom_id === 2 || this.vnhom_id === 3) {
          if (this.phanVungID === this.HPG_ID) {
            if (itemFocused.ngaygiao_td) {
              this.tsbtnCapNhatMucDo.Enabled = (new Date() < this.strDDMMYYYY_toDate(String(itemFocused.ngaygiao_td)));
            }
          }
        }

        this.btnNhapMoi.Enabled = vtrangthai_id != "6" && vtrangthai_id != "7";
      } else {
        this.gridTienTrinh = [];
        this.tttb = {
          ma_gd: '',
          ma_tb: '',
          kieu_ld: '',
          ten_tb: '',
          diachi_ld: '',
          ma_lt: '',
          ghichu: ''
        };

        this.btnNhapMoi.Enabled = false;
      }
    },

    strDDMMYYYY_toDate(dtStr) {
      let parts_of_date = dtStr.split("/");
      return new Date(+parts_of_date[2], parts_of_date[1] - 1, +parts_of_date[0]);
    },

    async mapData(id_neo, table, dk) {
      try {
        let rs = await api.fnMapId(this.axios, {id_neo, table, dk});
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Khong load duoc ky hoa don');
      }
    },

    getDataFromAPI(rs) {
      if (rs.data != null && rs.data.data != null && (typeof rs.data.data == 'number' || rs.data.data.length > 0)) {
        return rs.data.data;
      }

      return null;
    },

    async getDSTienTrinh(vnhom_id, vbaohong_id) {
      try {
        let rs = await api.sp_ds_tientrinh_popuptiepnhan(this.axios, {
          vnhom_id,
          vbaohong_id
        });

        this.gridTienTrinh = rs.data.data;
      } catch (e) {
        console.log(e);
        this.gridTienTrinh = [];
      }
    },

    async txtMaTB_KeyPress() {
      if (this.tttb.ma_tb) {
        this.loading(true);
        try {
          if (this.vnhom_id === 2) //Báo hỏng
            this.gridDanhSach = await this.lay_ds_tientrinh_bh_theo_matb(
              this.vdichvuvt_id,
              this.vdonvi_id,
              this.vtrangthaibh_id,
              this.vngaybh_1,
              this.vngaybh_2,
              this.vngaynt_1,
              this.vngaynt_2,
              this.tttb.ma_tb
            );

          if (this.gridDanhSachLength === 0) {
            this.itemFocused = null;
            await this.NAP_GRD_TIENTRINH()
          }
        } catch (ex) {
          this.$toast.error(ex);
        } finally {
          this.loading(false);
        }
      }
    },

    async lay_ds_tientrinh_bh_theo_matb(vdichvuvt_id, vdonvi_id, vtrangthaibh_id, vngaybh_tu, vngaybh_den, vngaynt_tu, vngaynt_den, vma_tb) {
      try {
        let rs = await api.lay_ds_tientrinh_bh_theo_matb(this.axios, {
          vdichvuvt_id, vdonvi_id, vtrangthaibh_id, vngaybh_tu, vngaybh_den, vngaynt_tu, vngaynt_den, vma_tb
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }
      return [];
    },

    btnTimKiem_Click() {
      this.fparent = false;
      this.NAP_GRD_DANHSACH();
    },

    async tsbtnCapNhatMucDo_Click() {
      try {
        let vbaohong_id = this.itemFocused.baohong_id;
        await this.capnhat_ngaygiao_td(vbaohong_id, moment().format('DD/MM/YYYY HH:mm:ss'), 2);
      } catch (ex) {
        this.$toast.error(ex);
      }
    },

    async capnhat_ngaygiao_td(vbaohong_id, vngaydieu, vmucdo_id) {
      try {
        let params = {
          "vbaohong_id": vbaohong_id,
          "vmucdo_id": vmucdo_id,
          "vngaydieu": vngaydieu
        }
        let response = await api.capnhat_ngaygiao_td(this.axios, params);
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.$alert("Cập nhật thành công");
        } else {
          this.$toast.error(response.data.message_detail);
        }
      } catch (e) {
        this.$toast.error(e.data.message_detail);
      }
    },

    async tsbtnChiTiet_Click() {
      if (this.gridDanhSachLength === 0) return;
      if (this.itemFocused == null) return;
      let vma_tb = this.itemFocused.ma_tb;
      this.$refs.dlg_tracuuphieubh.show()
      await this.$refs.frmTraCuuPhieuBH.HienThi_LichSu_BaoHong(vma_tb, this.thuebao_id, "", 0)
    },

    tsbtnDoKiem_Click() {
      this.Showport();
    },

    Showport() {
      try {
        if (this.gridDanhSachLength <= 0) {
          this.$toast.error("Bạn hãy nhập mã thuê bao !");
          return;
        }

        let {thuebao_id, vma_tb} = this.itemFocused;
        this.checkPortDialog.input.vma_tb = vma_tb
        this.checkPortDialog.input.vthuebao_id = this.thuebao_id
        this.checkPortDialog.input.vkieu = 0
        this.checkPortOnClick()
      } catch (ex) {
        this.$toast.error(ex);
      }
    },

    async tsbtnTestAllPort_Click() {
      this.loading(true);
      if (this.vuser_alt === "" || this.vpass_alt === "") {
        this.$toast.error("User hoặc mật khẩu alinetest chưa được thiết lập!");
        return;
      }

      let lst_chon = this.$refs.gridDanhSach.getSelectedRecords();
      if (lst_chon.length === 0) {
        this.$root.$toast.error("Bạn chưa chọn thuê bao.");
        return;
      }

      await this.GetPortStatus_Unsync();

      this.loading(false);
    },

    async GetPortStatus_Unsync() {
      let lst_chon = this.$refs.gridDanhSach.getSelectedRowIndexes();
      let gridDanhSach = [...this.gridDanhSach];
      for (let i = 0; i < gridDanhSach.length; i++) {
        for (let lst of lst_chon) {
          if (i === lst) {
            let vInput = gridDanhSach[i].ma_tb;
            let vdichvuvtID = gridDanhSach[i].dichvuvt_id;

            gridDanhSach[i].tt_port = await this.getPortStatus(vInput, vdichvuvtID);
          }
        }
      }

      this.gridDanhSach = gridDanhSach;
      this.itemFocused = null;
    },

    async getPortStatus(vInput, vdichvuvt_id) {
      try {
        if (this.vuser_alt === "" || this.vpass_alt === "") return "";
        //Đo kiểm trước khi nghiệm thu
        if (vdichvuvt_id !== DichVuVienThong.ADSL)
          return "";

        let rs = await api.bnm_GetPortStatus(this.axios, {
          "input": vInput,
          "linetestPassword": this.vpass_alt,
          "linetestUser": this.vuser_alt,
          "mac": "0"
        });

        return this.getDataFromAPI(rs);
      } catch (ex) {
        return ex;
      }
    },

    tsbtnXuatFile_Click() {
      try {
        if (this.gridDanhSachLength > 0) {
          this.$refs.frmLayDuLieuBC.showModal();
        } else {
          this.$root.$toast.error("Không có dữ liệu. Bạn hãy kiểm tra lại điều kiện lấy báo cáo !");
        }
      } catch (ex) {
        console.log(ex);
      }
    },

    tsbtnXuatExcel_Click() {
      if (this.gridDanhSachLength === 0)
        this.$root.$toast.error("Không có dữ liệu để xuất Excel!");
      else
        this.HT_DLG_XUAT_EXCEL();
    },

    HT_DLG_XUAT_EXCEL() {
      if (this.gridDanhSachLength <= 0) {
        this.$root.$toast.error('Chưa có dữ liệu trên lưới để xuất excel!');
        return;
      }

      //ẩn cột checkbox nếu allowCheckBox
      let cols = this.$refs.gridDanhSach.grid.getColumns();
      cols[0].visible = false;
      let excelExportProperties = {
        dataSource: this.gridDanhSach,
        enableFilter: true,
        fileName: `export_${moment().format('YYYYMMDD')}.xlsx`
      };
      this.$refs.gridDanhSach.excelExport(excelExportProperties)
        .then((e) => cols[0].visible = true);//hiện lại khi export xong
    },

    tsbtnGianDo_Click() {
      this.$refs.frmTraCuuMDF.frmTraCuuMDF_Load(this.tttb.ma_tb);
      this.$refs.dlg_mdf.show()
    },

    tsbKHGoi_Click() {
      if (this.gridDanhSachLength <= 0) return;
      let vbaohong_id = this.itemFocused.baohong_id;
      if (vbaohong_id) {
        this.frmPhanAnhBHInput.vbaohong_id_t = vbaohong_id;
        this.$refs.dialogfrmPhanAnhBH.openDialog();
      } else {
        this.$root.$toast.error("Chưa có thông tin báo hỏng!");
      }
    },

    btnNhapMoi_Click() {
      this.HT_SMS_TEMPLATE(null);
    },

    HT_SMS_TEMPLATE(tientrinhbh_id) {
      this.loading(true);
      try {
        if (this.gridDanhSachLength <= 0) {
          this.$toast.error("Không có dữ liệu trên lưới để nhập tiến trình!");
          return;
        }

        let vnoidung_goc = '';
        let vnd_baohong = '';
        if (tientrinhbh_id != null) {
          vnoidung_goc = this.itemFocused.noidung;
        } else {
          if (this.gridDanhSachLength > 0) {
            vnoidung_goc = this.itemFocused.ma_tb + " :   " + this.itemFocused.nhanvien_tc;
            try {
              vnd_baohong = "VNPT " + this.$root.token.getMaTinh() + ":[" +
                this.checkUndefined(this.itemFocused.ma_tb) + ";;" +
                this.checkUndefined(this.itemFocused.ltb) + "]:" +
                this.checkUndefined(this.itemFocused.ma_loaikh) + "," +
                this.checkUndefined(this.itemFocused.ngay_yc) + "," +
                this.checkUndefined(this.itemFocused.ten_kh) + ",LH:" +
                this.checkUndefined(this.itemFocused.so_dt) + "," +
                this.checkUndefined(this.itemFocused.diachi_ld) + "";
            } catch (e) {
              vnd_baohong = this.itemFocused.ma_tb + " :   " + this.itemFocused.nhanvien_tc;
            }
          } else {
            vnd_baohong = "";
            vnoidung_goc = "";
          }
        }

        this.ndtt = {
          ...this.ndtt,
          ndtt_tag: '1',
          ndtt_vtinh: {tinhthicong_id: 0},
          ndtt_vtientrinhbh_id: tientrinhbh_id,
          ndtt_vnhom_id: this.vnhom_id,
          ndtt_vbaohong_id: this.itemFocused.baohong_id,
          ndtt_vnoidung_goc: vnoidung_goc,
          ndtt_vnd_baohong: vnd_baohong
        }

        this.$refs.dlg_noidungtt.show()
        this.$refs.ppNoiDungTT.frmNoiDungTT_Load()
      } catch (ex) {
        console.log(ex);
      } finally {
        this.loading(false);
      }
    },

    checkUndefined(s) {
      if (s == undefined || s == null) return ""
      return s.trim()
    },

    async ndtt_btnGhiLai_Click() {
      this.ndtt = {
        ...this.ndtt,
        ndtt_vtinh: null,
        ndtt_tag: "",
        ndtt_vtientrinhbh_id: null,
        ndtt_vnhom_id: null,
        ndtt_vbaohong_id: null,
        ndtt_vnoidung_goc: "",
        ndtt_vnd_baohong: "",
      };
      this.$refs.dlg_noidungtt.hide();
      await this.NAP_GRD_TIENTRINH();
    },

    async nd_btnGhiLai_Click(val) {
      this.$refs.dlg_noidung.hide()
      try {
        this.loading(true)
        if (await this.capnhat_tientrinh_bh(val.tientrinhbh_id, val.noidung)) {
          await this.NAP_GRD_TIENTRINH();
        }
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },

    async capnhat_tientrinh_bh(vtientrinhbh_id, vnoidung) {
      try {
        let params = {
          "vbaohong_id": this.itemFocused.baohong_id,
          "vdonvi_id": this.$root.token.getDonViID(),
          "vip_cn": await this.$root.token.getIP(),
          "vloai_id": vtientrinhbh_id == null ? "1" : "2",
          "vnguoi_cn": this.$root.token.getUserName(),
          "vnguoidung_id": this.$root.token.getNguoiDungID(),
          "vnhanvien_id": this.nhanVienID,
          "vnoidung": vnoidung,
          "vtientrinhbh_id": vtientrinhbh_id
        }
        let response = await api.capnhat_tientrinh_bh(this.axios, params);
        // console.log(response)
        if (response.data.data !== '1') {
          this.$toast.error(response.data.message_detail);
          return false;
        }

        return true;
      } catch (e) {
        console.log(e);
      }

      return false;
    },

    onClosePopupGuiSMS() {
      this.$refs.popupGuiSmsRef.hide()
    },

    //Xu ly grid tien trinh
    gridTTXL_actionComplete() {
      if (this.gridTienTrinh.length > 0) {
        for (let i = 0; i < this.gridTienTrinh.length; i++) {
          let id = "onSMS_" + this.gridTienTrinh[i].tientrinhbh_id + "_" + this.gridTienTrinh[i].noidung
          var button = document.getElementById(id);
          if (button != null && button != undefined) {
            button.addEventListener("click", onSMS)
          }

          id = "onSua_" + this.gridTienTrinh[i].tientrinhbh_id
          button = document.getElementById(id);
          if (button != null && button != undefined) {
            button.addEventListener("click", onSua)
          }

          id = "onXoa_" + this.gridTienTrinh[i].tientrinhbh_id
          button = document.getElementById(id);
          if (button != null && button != undefined) {
            button.addEventListener("click", onXoa)
          }
        }
      }
    },
    async gridTTXL_onSMS() {
      try {
        var bhi = this.itemFocused.baohong_id
        var tti = $("#tientrinhbh_id").val();

        if (bhi != null && tti != null) {
          await this.initSMSPopup(bhi, tti);
          this.$refs.popupGuiSmsRef.show()
        }
      } catch (e) {
        console.log(e);
      }
    },

    async initSMSPopup(bhi, tti) {
      let f = this.$refs.guiSmsRef;
      f.filterForm.baoHongId = Number(bhi);
      f.filterForm.nhomId = Number(this.vnhom_id);
      f.filterForm.tienTrinhBHId = Number(tti);
      f.filterForm.tienTrinhTCId = 0;
      f.filterForm.maTB = this.tttb.ma_tb;
      f.filterForm.noiDung = $('#noidung').val();
      f.filterForm.hdtbId = 0;

      if (f.filterForm.hdtbId === 0 && f.filterForm.nhomId === 0) f.vNhomId = 2
      else if (f.filterForm.hdtbId === 0 && f.filterForm.nhomId === 2) f.vNhomId = 2
      else f.vNhomId = 1

      await f.getSoDienThoaiGuiSMS()
      await f.onGetDanhBa()
      await f.getDanhSachTinNhanDaGui()
    },

    gridTTXL_onSua() {
      this.nd_tientrinhbh_id = $("#tientrinhbh_id").val()
      let nd_txtNoiDung = ""
      if (this.nd_tientrinhbh_id != null && this.nd_tientrinhbh_id != "") {
        nd_txtNoiDung = this.gridTienTrinh.find(x => x.tientrinhbh_id == this.nd_tientrinhbh_id).noidung
      } else {
        if (this.gridTienTrinh.length > 0) {
          nd_txtNoiDung = this.itemFocused.ma_tb + " :   " + this.itemFocused.nhanvien_tc
        } else {
          nd_txtNoiDung = ""
        }
      }
      this.$refs.dlg_noidung.show()
      this.$refs.ppNoiDung.txtNoiDung = nd_txtNoiDung
    },

    async gridTTXL_onXoa() {
      let vtientrinhbh_id = $("#tientrinhbh_id").val()
      this.$bvModal.msgBoxConfirm('Bạn có thực sự muốn xóa tiến trình được chọn không ?', {
        title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
      }).then(async v => {
        if (v) {
          try {
            this.loading(true)
            let sdt = await this.newMapData("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tientrinhsms", {
              param: vtientrinhbh_id,
              type: 1
            });
            if (sdt != undefined && sdt != null && sdt != "-1") {
              this.$toast.error("Bạn không thể xóa tiến trình này\r\nTiến trình đã được gửi tin nhắn đến " + sdt);
            } else {
              let vbaohong_id = await this.newMapData("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tientrinh_bh", {
                param: vtientrinhbh_id,
                type: 1
              });
              let kt = await this.fn_xoa_tientrinh_bh(vbaohong_id, vtientrinhbh_id);
              if (kt !== '1') {
                this.$toast.error(kt);
                return;
              }

              await this.NAP_GRD_TIENTRINH();
            }
          } catch (error) {
            this.$toast.error(error.message ? error.message : 'Đã có lỗi xảy ra')
          } finally {
            this.loading(false)
          }
        }
      })
    },

    async fn_xoa_tientrinh_bh(vbaohong_id, vtientrinhbh_id) {
      try {
        let params = {
          "vbaohong_id": Number(vbaohong_id),
          "vtientrinhbh_id": Number(vtientrinhbh_id)
        }
        let response = await api.fn_xoa_tientrinh_bh(this.axios, params);
        return response.data.data === '1' ? response.data.data : response.data.message_detail;
      } catch (e) {
        console.log(e);
      }

      return '-1';
    },

    async gridTTXL_rowDoubleClicked(rowIndex, rowData) {
      this.loading(true)
      try {
        const response = await api.lay_ds_tientrinh_sms_theo_tientrinh_id(
          this.axios,
          {
            nhom_id: 2,
            tientrinhbh_id: rowData.tientrinhbh_id,
            tientrinhtc_id: 0,
            tinhthicong_id: 0
          }
        )

        this.smsSentList = response.data.data;
        this.$refs.dlg_sms_sent.show();
      } catch (e) {
        this.smsSentList = [];
      } finally {
        this.loading(false)
      }
    },

    async newMapData(url, params) {
      try {
        let rs = await api.newMapId(this.axios, url, params);
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('newMapData');
      }
    },
    async checkPortOnClick() {
      this.checkPortDialog.isVisiable = true
      this.$refs.checkPort.show()
    },
    async checkPortOnClose() {
      this.checkPortDialog.isVisiable = false
    },
  }
}

function setTienTrinhBHID(e) {
  var tientrinhbh_id = ""
  let noidung = '';
  for (let i = 0; i < e.path.length; i++) {
    if (e.path[i].id != undefined && e.path[i].id != null && e.path[i].id != "") {
      let tmp = e.path[i].id.split("_");
      tientrinhbh_id = tmp[1]
      noidung = tmp[2] || '';
      break;
    }
  }
  $("#tientrinhbh_id").val(tientrinhbh_id)
  $("#noidung").val(noidung)
}

function onSMS(e) {
  setTienTrinhBHID(e)
  document.getElementById("sms").click()
}

function onSua(e) {
  setTienTrinhBHID(e)
  document.getElementById("sua").click()
}

function onXoa(e) {
  setTienTrinhBHID(e)
  document.getElementById("xoa").click()
}
</script>

<style>
.modal-xxl {
  min-width: 90% !important;
}
</style>
