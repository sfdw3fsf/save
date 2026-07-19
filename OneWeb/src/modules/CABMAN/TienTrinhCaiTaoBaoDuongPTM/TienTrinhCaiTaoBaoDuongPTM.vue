<template src="./TienTrinhCaiTaoBaoDuongPTM.html">
</template>

<script>
import DateTimeLib from "../../../utils/DateTimeLib";
import NoiDungTTCC from "../caitao-baoduong/modals/NoiDungTTCC.vue";

export default {
  name: "TienTrinhCaiTaoBaoDuongPTM",
  components: {NoiDungTTCC},
  data: function () {
    return {
      fields: [],
      chkNgayGiao: false,
      dtpTuNgay: new Date,
      dtpDenNgay: new Date,
      dateFormat: 'dd/MM/yyyy',
      dateVal: new Date,
      waterMark: 'Chọn ngày',

      dsTrangThaiCC: [],
      cboTrangThai_CC: null,
      grcDSPT: [],
      gridTienTrinh: [],
      grcDSHSCC: [],
      dsDonVi: [],

      kieugoi_id: 0,
      Tag: null,
      donviIDCHA: -1,
      donviID: 0,
      vdonvi_id: -1,
      donviBC: 0,
      muc_id_cha: 0,
      kt_load: true,

      txtMa_HSCC: null,
      hscc_id: 0,
      NoiDungTTCCData: null,
      currentItem: {
        chiTietCCID: 0,
      },
    }
  },
  async created() {
    await this.frmTienTrinhTC_Load()
  },
  methods: {
    btnTimKiem_Click() {
      this.gridTienTrinh = []
      this.grcDSPT = []
      this.HienThi_DS_CaiTao_BaoDuong()
    },
    tsbtnXuatExcel_Click() {
      if (!this.grcDSHSCC.length) {
        this.$root.toastError("Không có dữ liệu để xuất Excel!")
        return
      }
      let exportProperties = {
        dataSource: this.grcDSHSCC
      }
      this.$refs.grcDSHSCC.excelExport(exportProperties)
    },
    async HienThi_DS_CaiTao_BaoDuong() {
      try {
        if (this.vdonvi_id == -1) {
          this.$root.toastError("Bạn chưa chọn đơn vị!")
          return
        }
        let tungay = "0", denngay = "0"
        if (this.chkNgayGiao) {
          if (this.isNullOrEmpty(this.dtpTuNgay) || this.isNullOrEmpty(this.dtpDenNgay)) {
            this.$root.toastError("Bạn hãy chọn lại từ ngày, đến ngày !")
            return
          }
          tungay = DateTimeLib.toDateDisplay(this.dtpTuNgay)
          denngay = DateTimeLib.toDateDisplay(this.dtpDenNgay)
        }
        this.loading(true)
        this.grcDSHSCC = await this.layDsHoSoCTBDTT(this.vdonvi_id, tungay, denngay, this.txtMa_HSCC ? this.txtMa_HSCC : '0', parseInt(this.cboTrangThai_CC + ''))
        if (!this.grcDSHSCC.length) {
          this.txtMa_HSCC = ""
          this.hscc_id = 0
        }
      } catch (e) {

      } finally {
        this.loading(false)
      }
    },
    async grvDSHSCC_SelectedIndexChanged(item) {
      this.txtMa_HSCC = item.MA_HS_CC
      this.hscc_id = item.HSCC_ID
      try {
        this.loading(true)
        this.grcDSPT = await this.layDsPtmCTBD(this.hscc_id, item.PHIEUCC_ID)
      } catch (e) {
        this.$root.toastError("Có lỗi khi focus row: " + e)
      } finally {
        this.loading(false)
      }
    },
    grvDSPT_CommandClicked: async function (name, args) {
      if (name.toUpperCase() == "THEM_TIENTRINH") {
        this.createNoiDungTTCCData(args.HSCC_ID, args.CHITIETCC_ID, 0, "");
        this.$refs.modalNoiDung.show();
      }
    },
    grvDSPT_FocusedRowChanged: async function (args) {
      try {
        console.log("grvDSPT_FocusedRowChanged:",args)
        this.loading(true)
        let itemData = args.data;
        if (args.isHeaderCheckboxClicked) itemData = args.data[0];
        this.currentItem.chiTietCCID = itemData.CHITIETCC_ID;
        this.gridTienTrinh = await this.layDsTienTrinh(itemData.CHITIETCC_ID)
      } catch (e) {

      } finally {
        this.loading(false)
      }
    },
    gridTienTrinh_CommandClicked: async function (name, args) {
      this.$root.showLoading(true);
      try {
        if (name.toUpperCase() == "SUA") {
          this.createNoiDungTTCCData(0, 0, args.TIENTRINHCC_ID, args.NOIDUNG);
          this.$refs.modalNoiDung.show();
        } else if (name.toUpperCase() == "XOA") {
          if (args.KIEU == 2) {
            this.$root.toastError(
              "Bạn không thể xóa tiến trình lưu biến động cập nhật ngày bắt đầu, ngày hoàn thành!"
            );
          } else {
            this.$bvModal
              .msgBoxConfirm("Bạn có đồng ý xóa tiến trình không ?", {
                title: "Thông báo",
                size: "sm",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
                centered: true,
              })
              .then(async (v) => {
                if (v) {
                  let rs = await this.xoaTienTrinhCatChuyen(args.TIENTRINHCC_ID, args.CHITIETCC_ID, args.NHANVIEN_ID);
                  if (rs) {
                    this.$root.toastSuccess("Xóa tiến trình thành công!");
                    this.gridTienTrinh = await this.layDsTienTrinh(this.currentItem.chiTietCCID);
                  } else {
                    this.$root.toastError("Xóa tiến trình thất bại");
                  }
                }
              });
          }
        }
      } catch (e) {

      } finally {
        this.$root.showLoading(false);
      }
    },
    createNoiDungTTCCData: function (hsccID, chiTietCCID, tienTrinhCCID, noiDung) {
      this.NoiDungTTCCData = {
        hsccID: hsccID,
        chiTietCCID: chiTietCCID,
        tienTrinhCCID: tienTrinhCCID,
        noiDung: noiDung,
      };
    },
    async frmTienTrinhTC_Load() {
      try {
        this.loading(true)
        if (this.kieugoi_id == 0)
          if (this.Tag)
            this.kieugoi_id = parseInt(this.Tag + '')
          else this.kieugoi_id = 1
        let user_map = await this.getUsermap()
        if (user_map == "-1")
          user_map = ""
        if (!this.isNullOrEmpty(user_map)) {
          this.donviIDCHA = await this.getDonviIdByUsermap(user_map)
          this.donviBC = this.donviIDCHA
          this.muc_id_cha = await this.getMucIdByDonviId(this.donviIDCHA)
        } else {
          this.donviIDCHA = await this.getDonViChaId(this.$auth.getDonViID())
          if (this.donviIDCHA == "-1")
            this.donviIDCHA = this.$auth.getDonViID()
          this.donviBC = this.donviIDCHA
          this.muc_id_cha = await this.getMucIdByDonviId(this.donviIDCHA)
        }
        this.dsDonVi = await this.layDsDonVi(this.donviIDCHA)
        let donViTree = this.createTreeDonVi(this.dsDonVi)
        this.fields = {
          dataSource: donViTree,
          id: 'DONVI_ID',
          text: 'TEN_DV',
          parentID: 'DONVI_CHA_ID',
          hasChildren: 'hasChild'
        }

        this.dsTrangThaiCC = await this.layDsTrangThai()
        this.cboTrangThai_CC = this.dsTrangThaiCC.length ? this.cboTrangThai_CC = this.dsTrangThaiCC[0].TTCC_ID : null

        this.kt_load = false
      } catch (e) {

      } finally {
        this.loading(false)
      }
    },
    donviTreeViewSelected(args) {
      console.log(args)
      this.vdonvi_id = parseInt(args.nodeData.id + '')
    },
    createTreeDonVi(dsDonVi) {
      let tree = []
      let donviChaArray = dsDonVi.map(item => (item['DONVI_CHA_ID']))
      let donviArray = dsDonVi.map(item => (item['DONVI_ID']))
      dsDonVi.forEach(item => {
        //case item la parent node
        if (donviChaArray.includes(item['DONVI_ID'])) {
          //check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id (case both child, parent)
          if (!donviArray.includes(item['DONVI_CHA_ID'])) {
            delete item.DONVI_CHA_ID
          }
          tree.push({...item, hasChild: true, expanded: true})
        } else {
          // node is only child
          if (item['DONVI_CHA_ID'] != null && donviArray.includes(item['DONVI_CHA_ID'])) tree.push(item)
          else {
            //case both child, parent
            if (!donviArray.includes(item['DONVI_CHA_ID'])) {
              delete item.DONVI_CHA_ID
            }
            tree.push({...item, hasChild: true, expanded: true}) // DONVI_CHA_ID = null, -> item is parent node
          }
        }
      })
      return tree
    },
    isNullOrEmpty: function (value) {
      return (
        value == undefined || value == null || value.toString().trim() == ""
      );
    },
    async layDsDonVi(donvicha_id) {
      let rs = await this.$root.context.get("web-cabman/tientrinh-caitao-baoduong-ptm/lay-ds-donvi", {donvicha_id: donvicha_id});
      return rs.data
    },
    async getUsermap() {
      let rs = await this.$root.context.get("web-cabman/tientrinh-caitao-baoduong-ptm/get-usermap");
      if (rs.data)
        return rs.data.USER_MAP
      else rs.data
    },
    async getDonviIdByUsermap(usermap) {
      let rs = await this.$root.context.get("web-cabman/tientrinh-caitao-baoduong-ptm/get-donvi-usermap", {usermap: usermap});
      if (rs.data)
        return rs.data.DONVI_ID
      else rs.data
    },
    async getMucIdByDonviId(donviId) {
      let rs = await this.$root.context.get("web-cabman/tientrinh-caitao-baoduong-ptm/get-mucid-donviid", {donviId: donviId});
      if (rs.data)
        return rs.data.MUC_ID
      else rs.data
    },
    async getDonViChaId(donviId) {
      let rs = await this.$root.context.get("web-cabman/tientrinh-caitao-baoduong-ptm/get-donvicha-id", {donviId: donviId});
      if (rs.data)
        return rs.data.DONVI_CHA_ID
      else rs.data
    },
    async layDsTrangThai() {
      let rs = await this.$root.context.get("web-cabman/tientrinh-caitao-baoduong-ptm/lay-ds-trangthai");
      return rs.data
    },
    async layDsPtmCTBD(hscc_id, phieucc_id) {
      let rs = await this.$root.context.get("web-cabman/tientrinh-caitao-baoduong-ptm/lay-ds-ptm-ctbd", {
        hscc_id: hscc_id,
        phieucc_id: phieucc_id
      });
      return rs.data
    },
    async layDsHoSoCTBDTT(donvi_id, tu_ngay, den_ngay, ma_hs_cc, ttcc_id) {
      let rs = await this.$root.context.post("web-cabman/tientrinh-caitao-baoduong-ptm/lay-ds-hoso-ctbd-tt", {
        donvi_id: donvi_id,
        tu_ngay: tu_ngay,
        den_ngay: den_ngay,
        ma_hs_cc: ma_hs_cc,
        ttcc_id: ttcc_id,
      });
      return rs.data
    },
    async layDsTienTrinh(chitietcc_id) {
      let rs = await this.$root.context.get("web-cabman/tientrinh-caitao-baoduong-ptm/lay-ds-tientrinh", {chitietcc_id: chitietcc_id})
      return rs.data
    },
    xoaTienTrinhCatChuyen: async function (tienTrinhCCID, chiTietCCID, nhanVienID) {
      let rs = await this.$root.context.post(
        "/web-cabman/CaiTaoBaoDuong/xoaTienTrinhCatChuyen",
        {
          tienTrinhCCID: tienTrinhCCID,
          chiTietCCID: chiTietCCID,
          nhanVienID: nhanVienID,
        }
      );
      return rs.data.result;
    },
    modalNoiDung_Show: function (args) {
      this.$refs.NoiDungTTCC.fillData(
        this.NoiDungTTCCData.hsccID,
        this.NoiDungTTCCData.chiTietCCID,
        this.NoiDungTTCCData.tienTrinhCCID,
        this.NoiDungTTCCData.noiDung
      );
    },
    modalNoiDung_refresh: function (args) {
      this.modalNoiDung_Show(args);
    },
    modalNoiDung_Save: async function (args) {
      if (this.$refs.NoiDungTTCC.validateForm()) {
        this.$root.showLoading(true);
        try {
          await this.$refs.NoiDungTTCC.saveData();
          if (this.$refs.NoiDungTTCC.tienTrinhCCID == 0) {
            this.$root.toastSuccess("Thêm tiến trình thành công");
          } else {
            this.$root.toastSuccess("Cập nhật tiến trình thành công");
          }
          this.gridTienTrinh = await this.layDsTienTrinh(this.currentItem.chiTietCCID);
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
        this.$refs.modalNoiDung.hide();
      }
    },
  }
}
</script>

<style>
.disable-btn:hover {
  background-color: white !important;
}

.disable-btn a {
  color: #6c757d !important;
}

.disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}
</style>
