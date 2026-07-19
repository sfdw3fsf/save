<template src='./DanhMucThietBi.html'></template>
<style scoped src='./DanhMucThietBi.scss'></style>
<script>
import { mapActions, mapState } from "vuex";
import moment from "moment";
import Vue from "vue";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
Vue.use(DialogPlugin);

const isInt = (value) => {
  return (
    !isNaN(value) &&
    parseInt(Number(value)) == value &&
    !isNaN(parseInt(value, 10))
  );
};

const isANumber = (value) => {
  const str = value.toString().replaceAll(",", "");
  if (str.trim().length == 0) return false;
  if (!isNaN(str)) return true;
  if (typeof str != "string") return false;
  return !isNaN(str) && !isNaN(parseFloat(str));
};

export default {
  components: {},
  name: "DanhMucThietBi",
  mounted() {},
  watch: {
    tyLeVatSelected: function (data) {
      this.listTyLeVat.forEach((e) => {
        if (data === e.tyLeVatId) {
          this.text_tyLeVatSelected = e.tyleVat;
          // console.log("this.text_tyLeVatSelected", this.text_tyLeVatSelected);
        }
      });
    },
    dichVuVTSelected: async function (data) {
      if (data) {
        await this.fetchDSLoaiHinhTB(this.loai_tbi_id, 1);
        this.$refs.ref_listLoaiHinhTB_0.grid.refreshColumns();
      }
    },
  },
  computed: {},
  created() {},
  filters: {
    roundup: function (value) {
      // console.log(value)
      if (value.length > 0 && isANumber(value)) {
        const isDotEnd = value.substr(value.length - 1) == ".";
        if (!isDotEnd) return Math.round(parseFloat(value));
      }
      return value;
    },
  },
  data() {
    return {
      dialog: {
        target: ".main-wrapper",
        width: "85%",
        animationSettings: { effect: "Zoom" },
      },
      tsbtnNhapMoi: false,
      tsbtnGhiLai: false,
      tsbtnXoa: false,
      tsbtnHuyBo: false,
      listNhomTB: [],
      nhomTBSelected: "",
      listDTV: [],
      donViTinhSelected: "",
      listLoaiPhi: [],
      loaiPhiSelected: "",
      listTyLeVat: [],
      tyLeVatSelected: 3,
      text_tyLeVatSelected: "",
      listDichVuVT: [],
      dichVuVTSelected: "",
      listLoaiThietBi: [],
      listLoaiHinhTB_0: [],
      listLoaiHinhTB_0_checked: [],
      listLoaiHinhTB_1: [],
      listLoaiHinhTB_0_checked: [],
      loai_tbi_id: 0,
      txtMA_TBI_Selected: "",
      txtTEN_LOAITBI_Selected_Selected: "",
      txtTIEN_Selected: 0,
      txtTienKM_Selected: 0,
      txtVAT_Selected: 0,
      txtVatKm_Selected: 0,
      txtGhiChu_Selected: "",
      blockDau_Selected: "",
      blockTiep_Selected: "",
      giaBlockTiep_Selected: "",
      vatBlockTiep_Selected: "",
      chk_CaiDatMD: false,
      ghiChu: "",
      chkSeri_MD: false,
      khoanMucTtId_value: "",
      thietBiSelected: "",
      checkData: false,
      tongTien: "0",
      listKhoanMucTT:[]
    };
  },
  methods: {
    ...mapActions("danhmucthietbi", [
      "getDSNhomTB",
      "getDSDVT",
      "getDSLoaiPhi",
      "getDSTyLeVat",
      "getDSDichVuVT",
      "getDSDanhMucLoaiThietBi",
      "getDSLoaiHinhTB",
      "createDSLTB",
      "updateDSLTB",
      "createLTBLoaiHinhTB",
      "deleteLTBLoaiHinhTB",
      "deleteLTB",
    ]),
    dialogOpen() {
      this.fetchDSLoaiThietBi();
      this.fetchDSNhomTB();
      this.fetchDSDVT();
      this.fetchDSLoaiPhi();
      this.fetchDSTyLeVat();
      this.fetchDSDichVuVT();
      this.fetchDSKhoanMucTT();
      this.SetButton(-1);
    },
    VATChanged(){      
      if(this.txtTIEN_Selected && this.tyLeVatSelected){
        let Tyle = this.listTyLeVat.filter(e=> e.value == this.tyLeVatSelected)
        if(Tyle.length == 0){
          return
        }
        this.txtVAT_Selected = this.txtTIEN_Selected * Tyle[0].giatri/100
      }
    },
    openDialog() {
      this.$refs.popupdanhMucThietBi.show();
    },
    roundUp: function (value) {
      if (value.length > 0 && isANumber(value)) {
        const isDotEnd = value.substr(value.length - 1) == ".";
        if (!isDotEnd) return Math.round(parseFloat(value));
      }
      return value;
    },
    onClickDelete() {
      this.$bvModal
        .msgBoxConfirm(`Bạn thật sự muốn xóa dữ liệu không ?`, {
          title: "Thông báo",
          size: "sm",
          buttonSize: "md",
          okVariant: "primary",
          headerClass: "p-2 pb-0 border-bottom-0",
          footerClass: "p-2 pt-0 border-top-0 justify-content-center",
          centered: true,
          noCloseOnBackdrop: true,
          okTitle: "Đồng ý",
          cancelTitle: "Không đồng ý",
        })
        .then((value) => {
          value && this.deleteThietBi();
        });
    },
    async deleteThietBi() {
      try {
        const { data } = await this.axios.delete(
          `/web-qlvt/api/danh-muc-thiet-bi/xoa-loai-tbi/${this.loai_tbi_id}`
        ); //this.deleteLTB({loaiTbiId :this.loai_tbi_id});
        if (data.error_code === "BSS-00000000") {
          this.$toast.success("Xóa thiết bị thành công!");
          this.Clear();
          this.fetchDSLoaiThietBi();
        }
      } catch (e) {
        this.$toast.error(e.message_detail);
      }
    },
    SetButton(kieu) {
      this.tsbtnNhapMoi = false;
      this.tsbtnGhiLai = false;
      this.tsbtnXoa = false;
      this.tsbtnHuyBo = false;

      if (kieu == -1) {
        //Bat dau
        this.tsbtnGhiLai = true;
        this.tsbtnHuyBo = true;
        this.$refs.txtMA_TBI.focus();
      }

      if (kieu == 0) {
        //Bat dau
        this.tsbtnNhapMoi = true;
        this.$refs.txtMA_TBI.focus();
        this.Clear();
        // console.log(
        //   "kieu=0",
        //   this.tsbtnNhapMoi,
        //   this.tsbtnGhiLai,
        //   this.tsbtnHuyBo,
        //   this.tsbtnXoa
        // );
      }

      if (kieu == 1) {
        //Them moi
        this.tsbtnGhiLai = true;
        this.tsbtnHuyBo = true;
        this.$refs.txtMA_TBI.focus();
        this.Clear();
        this.loai_tbi_id = 0;
        this.fetchDSLoaiHinhTB(this.loai_tbi_id, 1);
        this.listLoaiHinhTB_1 = [];
      }

      if (kieu == 2) {
        //Huy
        this.tsbtnNhapMoi = true;
        this.tsbtnXoa = true;
        this.$refs.txtMA_TBI.focus();
        this.$refs.listLTB.setCurrentSelectedRow(0);
        this.Clear();
      }

      if (kieu == 3) {
        //Edit
        this.tsbtnNhapMoi = true;
        this.tsbtnXoa = true;
        this.tsbtnGhiLai = true;
        this.tsbtnHuyBo = true;
      }
    },
    async fetchDSNhomTB() {
      this.loading(false);
      try {
        const response = await this.getDSNhomTB();
        if (response.error_code === "BSS-00000000") {
          response.data.forEach((e) => {
            this.listNhomTB.push({ text: e.nhomTBi, value: e.nhomTBiId });
          });
        }
      } catch (e) {
        this.$toast.error(e.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchDSDVT() {
      this.loading(false);
      try {
        const response = await this.getDSDVT();
        if (response.error_code === "BSS-00000000") {
          response.data.forEach((e) => {
            this.listDTV.push({ text: e.donViTbi, value: e.donViTbiId });
          });
        }
      } catch (e) {
        this.$toast.error(e.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchDSLoaiPhi() {
      this.loading(false);
      try {
        const response = await this.getDSLoaiPhi();
        if (response.error_code === "BSS-00000000") {
          response.data.forEach((e) => {
            e.tenLoaiPhi &&
              this.listLoaiPhi.push({
                text: e.tenLoaiPhi || "",
                value: e.loaiPhiId,
              });
          });
        }
      } catch (e) {
        this.$toast.error(e.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchDSTyLeVat() {
      this.loading(false);
      try {
        const response = await this.getDSTyLeVat();
        if (response.error_code === "BSS-00000000") {
          response.data.forEach((e) => {
            this.listTyLeVat.push({ text: e.tyleVat, value: e.tyLeVatId, giatri: e.giatri });
          });
        }
        console.log(this.listTyLeVat , "  = listTyLeVat")
      } catch (e) {
        this.$toast.error(e.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchDSDichVuVT() {
      this.loading(false);
      let data = {
        nguoiDungId: this.$root.token.getNguoiDungID(),
      };
      try {
        const response = await this.getDSDichVuVT(data);
        if (response.error_code === "BSS-00000000") {
          response.data.forEach((e) => {
            this.listDichVuVT.push({ text: e.tenDvVt, value: e.dichVuVtId });
          });
          this.dichVuVTSelected = this.listDichVuVT[0].value;
        }
      } catch (e) {
        this.$toast.error(e.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async getKey() {
      let id = 0;
      const param = {
        keyName: "LOAI_TBI",
        numBlockSize: 0,
        numRetry: 0,
      };
      try {
        const rs = await this.$root.context.post(
          "/web-qlvt/api/cap-phat-ccdc/get-key",
          param
        );
        if (rs.error_code === "BSS-00000000") {
          id = rs.data;
        }
        return id;
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    async fetchDSKhoanMucTT(){
        try {
        const rs = await this.$root.context.post("/web-thicong/danhmuc-tbi/lay_ds_khoanmuc");
        if (rs.error_code === "BSS-00000000") {
          // this.listKhoanMucTT = rs.data;
          rs.data.forEach((e) => {
            this.listKhoanMucTT.push({ text: e.ten_kmtt, value: e.khoanmuctt_id });
          });
        } else {
          this.listKhoanMucTT = []
        }
      } catch (error) {
          this.listKhoanMucTT = []
      } finally {
        this.loading(false);
      }
    },
    async fetchDSLoaiThietBi() {
      this.loading(false);
      //LAY_DS_HOADON_GIAO_NVTC_NV
      const param = {
        "page-no": 0,
        "page-size": 10000,
      };
      try {
        const rs = await this.$root.context.get(
          "/web-qlvt/api/loai-thiet-bi/ds-loai-thiet-bi",
          param
        );
        if (rs.error_code === "BSS-00000000") {
          this.listLoaiThietBi = rs.data.data;
          this.loai_tbi_id = rs.data.data.loaiTbiId;
        }
      } catch (error) {
      } finally {
        this.loading(false);
      }
      // try {
      //   const response = await this.getDSDanhMucLoaiThietBi();
      //   if (response.error_code === "BSS-00000000") {
      //     this.listLoaiThietBi = response.data.data;
      //     this.loai_tbi_id = response.data.data.loaiTbiId;
      //   }
      //   // console.log("Danh sách loại thiết bị", this.listLoaiThietBi);
      // } catch (e) {
      //   this.$toast.error(e.message_detail);
      // } finally {
      //   this.loading(false);
      // }
    },
    gridDSThietBi_selectedRowChanged(data) {
      if (data) {
        const { tien, vat, tienKm, vatKm } = data;
        const tongTien = tien + vat - tienKm - vatKm;
        this.tongTien = Number(tongTien).toLocaleString("En-en");

        this.khoanMucTtId_value = data.khoanMucTtId;
        this.tsbtnNhapMoi = true;
        this.tsbtnXoa = true;
        this.nhomTBSelected = data.nhomTbiId;
        this.loai_tbi_id = data.loaiTbiId;
        this.thietBiSelected = data.loaiTbi;
        this.txtMA_TBI_Selected = data.maTbi;
        this.txtTIEN_Selected = data.tien;
        this.txtVAT_Selected = data.vat;
        this.txtTienKM_Selected = data.tienKm;
        this.txtVatKm_Selected = data.vatKm;
        this.donViTinhSelected = data.donViTbiId;
        this.chk_CaiDatMD = data.caiDatMd === 0 ? false : true;
        this.chkSeri_MD = data.seriMd === 0 ? false : true;
        this.ghiChu = data.ghiChu;
        this.loaiPhiSelected = data.loaiPhiId;
        this.tyLeVatSelected = data.tyLeVatId;
        this.blockDau_Selected = data.blockDau;
        this.blockTiep_Selected = data.blockTiep;
        this.giaBlockTiep_Selected = data.giaBlockTiep;
        this.vatBlockTiep_Selected = data.vatBlockTiep;
        this.fetchDSLoaiHinhTB(data.loaiTbiId, 0);
        this.fetchDSLoaiHinhTB(data.loaiTbiId, 1);
      }
    },
    gridlistLoaiHinhTB_0_selectedRowChanged() {},
    gridlistLoaiHinhTB_0_selectedData_checkbox(data) {
      this.listLoaiHinhTB_0_checked = data;
      // console.log(data);
      // console.log(this.listLoaiHinhTB_0_checked);
    },
    gridlistLoaiHinhTB_1_selectedRowChanged() {},
    gridlistLoaiHinhTB_1_selectedData_checkbox(data) {
      this.listLoaiHinhTB_1_checked = data;
    },
    async fetchDSLoaiHinhTB(_loaiTbiId, _chk) {
      let data = {
        dichVuVtId: this.dichVuVTSelected,
        loaiTbiId: _loaiTbiId,
        chk: _chk,
      };
      // console.log('clicnhapmoi', data)
      if (this.dichVuVTSelected) {
        this.loading(false);
        const response = await this.getDSLoaiHinhTB(data);
        if (response.error_code === "BSS-00000000") {
          if (_chk === 0) {
            // this.khoanMucTtId_value = ""
            this.listLoaiHinhTB_1 = response.data;
            if(this.listLoaiHinhTB_1.length > 0){
              if(this.listLoaiHinhTB_1[0].KHOANMUCTT_ID != ""){
                this.khoanMucTtId_value = this.listLoaiHinhTB_1[0].KHOANMUCTT_ID
              }
            }
          } else {
            this.listLoaiHinhTB_0 = response.data;
          }
        } else {
          this.$toast.error(response.message_detail);
        }
        this.loading(false);
      }
    },
    angleRight() {
      let arr = [];
      this.listLoaiHinhTB_1_checked.map((val) => {
        let idx = this.listLoaiHinhTB_1.findIndex((v) => v.LOAITB_ID == val);
        if (idx >= 0) {
          arr.push(this.listLoaiHinhTB_1[idx]);
          this.listLoaiHinhTB_1 = this.listLoaiHinhTB_1.filter(
            (v) => v.LOAITB_ID != val
          );
        }
      });
      this.listLoaiHinhTB_0 = this.listLoaiHinhTB_0.concat(arr);
      this.listLoaiHinhTB_0_checked = [];
    },
    angleDoubleRight() {
      this.listLoaiHinhTB_0 = this.listLoaiHinhTB_0.concat(
        this.listLoaiHinhTB_1
      );
      this.listLoaiHinhTB_1 = [];
      this.listLoaiHinhTB_0_checked = [];
    },
    angleLeft() {
      let arr = [];
      this.listLoaiHinhTB_0_checked.map((val) => {
        let idx = this.listLoaiHinhTB_0.findIndex((v) => v.LOAITB_ID == val);
        if (idx >= 0) {
          arr.push(this.listLoaiHinhTB_0[idx]);
          this.listLoaiHinhTB_0 = this.listLoaiHinhTB_0.filter(
            (v) => v.LOAITB_ID != val
          );
        }
      });
      this.listLoaiHinhTB_1 = this.listLoaiHinhTB_1.concat(arr);
      this.listLoaiHinhTB_1_checked = [];
    },
    angleDoubleLeft() {
      this.listLoaiHinhTB_1 = this.listLoaiHinhTB_1.concat(
        this.listLoaiHinhTB_0
      );
      this.listLoaiHinhTB_0 = [];
      this.listLoaiHinhTB_1_checked = [];
    },
    Clear() {
      this.txtMA_TBI_Selected = "";
      this.txtTEN_LOAITBI_Selected_Selected = "";
      this.txtTIEN_Selected = 0;
      this.txtTienKM_Selected = 0;
      this.txtVAT_Selected = 0;
      this.txtVatKm_Selected = 0;
      this.txtGhiChu_Selected = "";
      this.loai_tbi_id = 0;
      this.thietBiSelected = "";
      this.ghiChu = "";
      this.khoanMucTtId_value = ""
    },
    closePopup() {
      this.txtMA_TBI_Selected = "";
      this.txtTEN_LOAITBI_Selected_Selected = "";
      this.txtTIEN_Selected = 0;
      this.txtTienKM_Selected = 0;
      this.txtVAT_Selected = 0;
      this.txtVatKm_Selected = 0;
      this.txtGhiChu_Selected = "";
      this.loai_tbi_id = 0;
      this.listNhomTB = [];
      this.listDTV = [];
      this.listDichVuVT = [];
      this.listLoaiPhi = [];
      this.listTyLeVat = [];
      this.thietBiSelected = "";
      this.ghiChu = "";
      this.listLoaiHinhTB_1 = [];
      this.listLoaiHinhTB_0 = [];
    },
    async GhiLai() {
      if (this.KiemTraDuLieuNhap()) {
        this.loading(true);
        let data = {
          nhomTbiId: this.nhomTBSelected,
          loaiTbi: this.thietBiSelected,
          maTbi: this.txtMA_TBI_Selected,
          tien: this.txtTIEN_Selected,
          vat: this.txtVAT_Selected,
          tienKm: this.txtTienKM_Selected,
          vatKm: this.txtVatKm_Selected,
          donViTbiId: this.donViTinhSelected,
          caiDatMd: this.chk_CaiDatMD ? "1" : "0",
          ghiChu: this.ghiChu,
          seriMd: this.chkSeri_MD ? "1" : "0",
          loaiPhiId: this.loaiPhiSelected === null ? 0 : this.loaiPhiSelected,
          tyLeVat: this.text_tyLeVatSelected,
          tyLeVatId: this.tyLeVatSelected,
          khoanMucTtId: this.khoanMucTtId_value,
          blockDau: this.blockDau_Selected,
          blockTiep: this.blockTiep_Selected,
          giaBlockTiep: this.giaBlockTiep_Selected,
          vatBlockTiep: this.vatBlockTiep_Selected,
        };
        // console.log(data);
        try {
          if (this.loai_tbi_id == 0) {
            // data.loaiTbiId = await this.getKey();
            const response = await this.createDSLTB(data);
            if (response.error_code === "BSS-00000000") {
              const id = response.data;
              if (this.listLoaiHinhTB_1.length > 0 && id) {
                const body = this.listLoaiHinhTB_1.map((item) => ({
                  loaiTbId: item.LOAITB_ID,
                  loaiTbiId: id,
                }));
                await this.addLoaiThueBao(body);
              }
              this.fetchDSLoaiThietBi();
            }
          } else {
            this.$bvModal
              .msgBoxConfirm(`Bạn có muốn cập nhật thay đổi không?`, {
                title: "Thông báo",
                size: "sm",
                buttonSize: "md",
                okVariant: "primary",
                headerClass: "p-2 pb-0 border-bottom-0",
                footerClass: "p-2 pt-0 border-top-0 justify-content-center",
                centered: true,
                noCloseOnBackdrop: true,
                okTitle: "Đồng ý",
                cancelTitle: "Không đồng ý",
              })
              .then((value) => {
                value && this.excuteUpdate(data);
              });
          }
          this.SetButton(3);
        } catch (ex) {
          this.$toast.error("Cập nhật dữ liệu không thành công!");
        } finally {
          this.loading(false);
        }
      }
    },
    excuteUpdate: async function (data) {
      const response = await this.updateDSLTB({
        ...data,
        loaiTbiId: this.loai_tbi_id,
      });
      if (response.error_code === "BSS-00000000") {
        await this.deleteLoaiThueBao(this.loai_tbi_id);
      }
    },
    addLoaiThueBao: async function (body) {
      console.log("body", body);
      try {
        const rs = await this.$root.context.post(
          "/web-qlvt/api/danh-muc-thiet-bi/tao-ds-loai-tbi-lh-thue-bao",
          body
        );
        this.$toast.success("Cập nhật dữ liệu lên viễn thông tỉnh thành công!");
      } catch (error) {}
    },
    async deleteLoaiThueBao(loaiTbiId) {
      try {
        const { data } = await this.axios.delete(
          `/web-qlvt/api/danh-muc-thiet-bi/xoa-loai-tbi-loai-hinh-tbao/${loaiTbiId}`
        );
        if (data.error_code === "BSS-00000000") {
          if (this.listLoaiHinhTB_1.length > 0 && !!loaiTbiId) {
            const body = this.listLoaiHinhTB_1.map((item) => ({
              loaiTbId: item.LOAITB_ID,
              loaiTbiId: loaiTbiId,
            }));
            await this.addLoaiThueBao(body);
          }
          this.fetchDSLoaiThietBi();
        }
      } catch (e) {
        this.$toast.error(e.message_detail);
      }
    },
    KiemTraDuLieuNhap() {
      if (!this.thietBiSelected) {
        this.$toast.error("Chưa nhập thiết bị!");
        this.$refs.txtMA_TBI.focus();
        return false;
      }
      if (
        !isInt(this.txtTIEN_Selected) ||
        parseInt(this.txtTIEN_Selected) < 0
      ) {
        this.$toast.error("Nhập sai kiểu dữ liệu tiền!");
        this.$refs.tien.focus();
        return false;
      }
      if (!isInt(this.txtVAT_Selected) || parseInt(this.txtVAT_Selected) < 0) {
        // console.log(this.txtVAT_Selected);
        this.$toast.error("Nhập sai kiểu dữ liệu VAT!");
        this.$refs.vat.focus();
        return false;
      }

      if (!!this.ghiChu && this.ghiChu.length > 100) {
        this.$toast.error("Ghi chú quá dài!");
        this.$refs.ghiChuText.focus();
        return false;
      }
      if (!isInt(this.blockDau_Selected)) {
        this.$toast.error("Nhập sai kiểu dữ liệu Block đầu!");
        this.$refs.txtBlockDau.focus();
        return false;
      }
      if (!isInt(this.giaBlockTiep_Selected)) {
        this.$toast.error("Nhập sai kiểu dữ Block tiếp!");
        this.$refs.txtBlockTiep.focus();
        return false;
      }
      if (!isInt(this.blockTiep_Selected)) {
        this.$toast.error("Nhập sai kiểu dữ liệu Giá Block tiếp!");
        this.$refs.txtGiaBlockTiep.focus();
        return false;
      }
      if (!isInt(this.vatBlockTiep_Selected)) {
        this.$toast.error("Nhập sai kiểu dữ liệu Vat Block tiếp!");
        this.$refs.txtVatBlockTiep.focus();
        return false;
      }
      if (!this.khoanMucTtId_value) {
        this.$toast.error("Bạn chưa chọn khoản mục thanh toán!");
        return false;
      }
      return true;
    },
    async fetchCreateLTBLoaiHinhTB() {
      this.loading(false);
      try {
        const response = await this.createLTBLoaiHinhTB();
        if (response.error_code === "BSS-00000000") {
          response.data.forEach((e) => {
            this.listNhomTB.push({ text: e.nhomTBi, value: e.nhomTBiId });
          });
          this.nhomTBSelected = this.listNhomTB[0].value;
        }
      } catch (e) {
        this.$toast.error(e.message_detail);
      } finally {
        this.loading(false);
      }
    },
  },
};
</script>
