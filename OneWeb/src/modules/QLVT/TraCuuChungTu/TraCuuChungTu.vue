<template src='./TraCuuChungTu.html'></template>
<style src='./TraCuuChungTu.scss'></style>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import xlsx from "xlsx";
import ClickOutside from "vue-click-outside";
import Vue from "vue";
import NhapXuatVT from "../NhapXuatVT/NhapXuatVT.vue";

export default {
  components: { breadcrumb, NhapXuatVT },
  name: "TraCuuChungTu",
  mounted() {},
  watch: {
    chk_LSXCT: async function (data) {
      console.log("LSXCT = ", data);

      await this.lichsu_xoact(data);
      if (data == false) {
        await this.fetchTraCuuChungTu();
      }
    },
    showlistVatTuDK: function (data) {
      this.vatTuDKSelected = "";
      data.forEach((element) => {
        this.vatTuDKSelected += `, ${element.text}`;
      });
      this.vatTuDKSelected = this.vatTuDKSelected.substring(2);
    },
    chk_VatTuDK: function (bool) {
      if (bool === false) {
        this.showVatTuDK = false;
        this.vatTuDKSelected = "";
      }
    },
    showlistTuKho: function (data) {
      this.khoDonViSelected = "";
      data.forEach((element) => {
        this.khoDonViSelected += `, ${element.tenKho}`;
      });
      this.khoDonViSelected = this.khoDonViSelected.substring(2);
    },
    chk_TuKho: function (bool) {
      this.showTuKho = bool;
      // this.khoDonViSelected = "";
    },
    chk_DenDonVi: function (bool) {
      if (bool === false) {
        this.showDenDonVi = false;
        this.denDonViSelected = "";
      }
    },
    chk_DenKho: function (bool) {
      if (bool === false) {
        this.showDenKho = false;
        this.denKhoSelected = "";
      }
    },
    tuDonViSelected: function (data) {
      console.log("từ đươn vị");
    },
    chk_TuDonVi: function (bool) {
      if (bool === false) {
        this.showTuDonVi = false;
        this.tuDonViSelected = "";
      }
    },
    tuDonViId: function (data) {
      this.fetchListKhoDonVi(data);
    },
    denDonViId: function (data) {
      this.fetchListTuKho(data);
    },
    chk_CuonCap: function (data) {
      if (data === false) {
        this.cuonCapText = "";
      }
    },
    chk_LoHang: function (data) {
      if (data === false) {
        this.loHangText = "";
      }
    },
    chk_lenhVB: function (data) {
      if (data === false) {
        this.lenhVBText = "";
      }
    },
    chk_idg: function (data) {
      if (data === false) {
        this.idgText = "";
      }
    },
    chk_soPhieu: function (data) {
      if (data === false) {
        this.soPhieuText = "";
      }
    },
    chk_soCV: function (data) {
      if (data === false) {
        this.soCVText = "";
      }
    },
    chk_soHD: function (data) {
      if (data === false) {
        this.soHDText = "";
      }
    },
    chk_SPXK: function (data) {
      if (data === false) {
        this.SPXKText = "";
      }
    },
    chk_MaDA: function (data) {
      if (data === false) {
        this.maDAText = "";
      }
    },
    chk_DonviDK: function (bool) {
      if (bool === false) {
        this.donViDKselected = "";
      }
    },
    loaiCTSelected: {
      handler: async function (data) {
        if (data) {
          if (this.chk_LoaiCT) {
            await this.fetchListKieuPhieu(data);
          }
        }
      },
    },
    chk_KieuPhieu: function (bool) {
      if (bool === false) {
        this.kieuPhieuSelected = "";
      }
    },
    chk_MucDich: {
      handler: function (bool) {
        if (bool === false) {
          this.mucDichSelected = "";
        }
      },
    },
    chk_TrangThaiCT: function (bool) {
      if (bool === false) {
        this.trangThaiCTSelected = "";
      }
    },
    status_btnLamMoi: {
      handler: function (val) {
        console.log(val);
        // if (this.chk_LoaiCT === true) {
        //   this.checkboxLoaiCT();
        // }
        this.chk_LoaiCT = false;
        this.chk_MucDich = false;
        this.chk_TrangThaiCT = false;
        this.chk_DonviDK = false;
        this.chk_VatTuDK = false;
        this.chk_TuDonVi = false;
        this.chk_TuKho = false;
        this.chk_DenDonVi = false;
        this.chk_DenKho = false;
        this.chk_denNgay = false;
        this.chk_CuonCap = false;
        this.chk_LoHang = false;
        this.chk_lenhVB = false;
        this.chk_idg = false;
        this.chk_soPhieu = false;
        this.chk_ngayYC = false;
        this.chk_soCV = false;
        this.chk_soHD = false;
        this.chk_MaDA = false;
        this.chk_SPXK = false;
        this.chk_KieuPhieu = false;
      },
    },
    chk_denNgay: function (val) {
      console.log("chk_denNgay", val);
      if (val === false) {
        this.enabled_DenNgay = false;
      } else {
        this.enabled_DenNgay = true;
      }
    },
  },
  computed: {},
  created() {
    this.firstRun();
    this.initPullDown("#nvtc-pd", "root");
    this.initPullDown("#dvdk-pd", "root-2");
  },
  data() {
    return {
      header: {
        title: "Tra cứu chứng từ",
        list: [
          {
            name: "Quản lí vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Tra cứu vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Tra cứu chứng từ",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      //Loại chứng từ
      listLoaiCT: [],
      loaiCTSelected: 1,
      chk_LoaiCT: false,
      //Mục đích
      listMucDich: [],
      mucDichSelected: 10011,
      chk_MucDich: false,
      mucDichText: "",
      //Trạng thái CT
      listTrangThaiCT: [],
      trangThaiCTSelected: 12,
      chk_TrangThaiCT: false,
      //Đơn vị đăng ký
      listDonViDK: [],
      donViDKselected: -1,
      chk_DonviDK: false,
      dvdkText: "",
      //Vật tư đăng ký
      listVatTuDK: [],
      vatTuDKSelected: "",
      showVatTuDK: false,
      showlistVatTuDK: [],
      chk_VatTuDK: false,
      listVatTuDKID: [],
      sendListVatTuDKID: [],
      // Từ Đơn vị
      listDonVi: [],
      tuDonViSelected: "",
      showTuDonVi: false,
      chk_TuDonVi: false,
      showlistTuDonVi: [],
      tuDonViId: "",
      //Từ Kho
      listKhoDonVi: [],
      khoDonViSelected: "",
      showTuKho: false,
      showlistTuKho: [],
      chk_TuKho: false,
      listKhoDonViID: [],
      sendListKhoDonViID: [],
      //Đến đơn vị
      denDonViSelected: "",
      showDenDonVi: false,
      chk_DenDonVi: false,
      denDonViId: "",
      //Đến Kho
      listDenKho: [],
      denKhoSelected: "",
      showDenKho: false,
      showlistDenKho: [],
      chk_DenKho: false,
      denKhoId: "",
      //TextInput
      chk_denNgay: false,
      denNgay: new Date(),
      chk_CuonCap: false,
      cuonCapText: "",
      chk_LoHang: false,
      loHangText: "",
      chk_lenhVB: false,
      lenhVBText: "",
      chk_idg: false,
      idgText: "",
      chk_soPhieu: false,
      soPhieuText: "",
      chk_ngayYC: false,
      ngayYCtu: new Date(),
      chk_soCV: false,
      soCVText: "",
      chk_soHD: false,
      soHDText: "",
      chk_MaDA: false,
      maDAText: "",
      chk_SPXK: false,
      SPXKText: "",
      //Kiểu phiếu
      listKieuPhieu: [],
      kieuPhieuSelected: "",
      chk_KieuPhieu: false,

      //Đức bổ sung button Chấp nhận
      chungtu_id: 0,
      chungTuRowSelected: {},
      sophieu: "",
      xacnhan: false,
      parentForm: "",
      isShowMD: false,
      isShowDonViDK: false,
      //Danh sách Tra cứu chứng từ
      listTraCuuChungTu: [],
      chungTuIDSelected: "",
      listChungTuVatTu: [],
      status_TTTCCT: true,
      listThietBi: [],
      status_btnLamMoi: false,
      enabled_DenNgay: false,
      mucDichLst: [],
      dvDkLst: [],
      pageInfo: {
        pageNo: 1,
        pageSize: 10,
        totalElement: 0,
      },
      chk_ChuaCoDauVao: false,
      chk_LSXCT: false,

      enabledHoanThanh: false,
      isTab: false,
      statusTemplate() {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div>
                        <span v-if="data.TTPH_ID == 4" class="one-check f20 text-success"></span>
                      </div>`,
            data() {
              return { data: {} };
            },
          }),
        };
      },
    };
  },
  directives: {
    ClickOutside,
  },
  methods: {
    ...mapActions("tracuuchungtu", [
      "getListLoaiCT",
      "getListMucDich",
      "getListTrangThaiCT",
      "getListDonViDK",
      "getListVatTuDK",
      "getListDonVi",
      "getListKhoDonVi",
      "getListTuKho",
      "getKieuPhieu",
      "getTraCuuChungTu",
      "getChungTuVatTu",
      "getChungTuThietBi",
      "tra_cuu_chung_tu_da_xoa",
    ]),

    async fetch_tra_cuu_chung_tu_da_xoa() {
      try {
        this.loading(true);
        let data = {
          soPhieu: this.chk_soPhieu ? this.soPhieuText : "0",
          kieu: 0,
        };
        const response = await this.tra_cuu_chung_tu_da_xoa(data);
        console.log("response tra_cuu_chung_tu_da_xoa = ", response);
        if (response.error_code === "BSS-00000000") {
          this.listTraCuuChungTu = response.data;
        }
      } catch (ex) {
        this.$root.toastError(ex.data.message);
      } finally {
        this.loading(false);
      }
    },

    lichsu_xoact(state) {
      if (state == true) {
        this.enabledHoanThanh = !state;
        this.chk_soPhieu = state;
        this.chk_LoaiCT = !state;
        this.chk_MucDich = !state;
        this.chk_TrangThaiCT = !state;
        this.chk_ngayYC = !state;
        this.chk_ChuaCoDauVao = !state;
        this.chk_soCV = !state;
        this.chk_soHD = !state;
        this.chk_MaDA = !state;
        this.chk_DonviDK = !state;
        this.chk_VatTuDK = !state;
        this.chk_SPXK = !state;
        this.chk_TuDonVi = !state;
        this.chk_TuKho = !state;
        this.chk_DenDonVi = !state;
        this.chk_DenKho = !state;
        this.chk_denNga = !state;
        this.chk_LoHang = !state;
        this.chk_CuonCap = !state;
      } else {
        this.chk_soPhieu = state;
        this.chk_LoaiCT = state;
        this.chk_MucDich = state;
        this.chk_TrangThaiCT = state;
        this.chk_ngayYC = state;
        this.chk_ChuaCoDauVao = state;
        this.chk_soCV = state;
        this.chk_soHD = state;
        this.chk_MaDA = state;
        this.chk_DonviDK = state;
        this.chk_VatTuDK = state;
        this.chk_SPXK = state;
        this.chk_TuDonVi = state;
        this.chk_TuKhoy = state;
        this.chk_DenDonVi = state;
        this.chk_DenKho = state;
        this.chk_denNgay = state;
        this.chk_LoHang = state;
        this.chk_CuonCap = state;
        this.enabledHoanThanh = !state;
      }
    },

    async btnHoanThanh_Click() {
      let f = this.$refs.frmNhapXuatVT;
      await f.frmNhapXuatVT_Load();
      this.$refs.popupNhapXuatVT.show();
    },
    tsbtnDongY_Click() {
      if (this.chungtu_id == 0) {
        this.$toast.error(
          "Bạn chưa chọn dòng chứng từ nào ! Click vào một dòng trong lưới danh sách chứng từ !"
        );
        return;
      }
      if (
        this.chungTuRowSelected == {} ||
        this.chungTuRowSelected.SOPHIEU == null ||
        this.chungTuRowSelected.SOPHIEU == ""
      ) {
        this.$toast.error(
          "Bạn chưa chọn dòng chứng từ nào ! Click vào một dòng trong lưới danh sách chứng từ !"
        );
        return;
      }
      if (this.chungTuRowSelected.TTPH_ID != "4") {
        this.$toast.error("Bạn phải chọn chứng từ đã hoàn thành!");
        return;
      }
      this.sophieu = this.chungTuRowSelected.SOPHIEU;
      this.xacnhan = true;
      if (this.parentForm == "SuaSerialGP") {
        this.parentForm = "";
        this.$parent.$parent.XacNhan_TraCuuChungTu();
      }
    },
    changeLoaiCT(value) {
      if (value) {
        this.fetchListKieuPhieu(value);
      }
    },
    hideTuDonVi() {
      this.showTuDonVi = false;
    },
    hideTuKho() {
      this.showTuKho = false;
    },
    hideDenDonVi() {
      this.showDenDonVi = false;
    },
    hideDenKho() {
      this.showDenKho = false;
    },
    hideVatTuDK() {
      this.showVatTuDK = false;
    },
    async firstRun() {
      await this.fetchListLoaiCT();
      await this.fetchListMucDich();
      await this.fetchListTrangThaiCT();
      await this.fetchListDonViDK();
      await this.fetchListVatTuDK();
      this.fetchListDonVi();
      await this.fetchListTuKho();
      this.fetchListKhoDonVi();
    },
    async fetchListLoaiCT() {
      try {
        this.loading(true);
        const response = await this.getListLoaiCT();
        if (response.error_code === "BSS-00000000") {
          response.data.forEach((element) =>
            this.listLoaiCT.push({
              text: element.loaiCt,
              value: element.loaiCtId,
            })
          );
          this.loaiCTSelected = response.data[0].loaiCtId;
        }
      } catch (ex) {
        this.$root.toastError(ex.data.message);
      } finally {
        this.loading(false);
      }
    },
    async fetchListMucDich() {
      this.loading(true);
      const response = await this.getListMucDich();
      if (response.error_code === "BSS-00000000") {
        // response.data.forEach((element) =>
        //   this.listMucDich.push({
        //     text: element.mucDich,
        //     value: element.mucDichId,
        //   })
        // );
        this.mucDichLst = response.data;
      }
      this.loading(false);
    },
    async fetchListTrangThaiCT() {
      this.loading(true);
      const response = await this.getListTrangThaiCT();
      if (response.error_code === "BSS-00000000") {
        response.data.forEach((element) =>
          this.listTrangThaiCT.push({
            text: element.trangThaiCt,
            value: element.trangThaiCtId,
          })
        );
      }
      console.log("this.listTrangThaiCT", this.listTrangThaiCT);
      this.loading(false);
    },
    async fetchListDonViDK() {
      this.loading(true);
      const response = await this.getListDonViDK();
      if (response.error_code === "BSS-00000000") {
        this.dvDkLst = response.data;
        console.log(this.dvDkLst);
        // response.data.forEach((element) =>
        //   this.listDonViDK.push({
        //     text: element.tenDonVi,
        //     value: element.donViId,
        //   })
        // );
      }
      // console.log("this.listDonViDK", this.listDonViDK);
      this.loading(false);
    },
    async fetchListVatTuDK() {
      this.loading(true);
      const response = await this.getListVatTuDK();
      if (response.error_code === "BSS-00000000") {
        response.data.forEach((element) =>
          this.listVatTuDK.push({
            text: element.tenVatTu,
            value: element.vatTuID,
          })
        );
      }
      console.log("this.listVatTuDK", this.listVatTuDK);
      this.loading(false);
    },
    async fetchListDonVi() {
      this.loading(true);
      const response = await this.getListDonVi();
      if (response.error_code === "BSS-00000000") {
        this.listDonVi = response.data;
        //set giá trị đầu vào Từ đơn vị và Đến đơn vị
        this.tuDonViId = response.data[0].donViId;
        this.tuDonViSelected = response.data[0].tenDonVi;
        this.denDonViId = response.data[0].donViId;
        this.denDonViSelected = response.data[0].tenDonVi;
      }
      //this.hideDenDonVi();
      console.log("this.listDonVi", this.listDonVi);
      this.loading(false);
    },
    async fetchListKhoDonVi() {
      this.loading(true);
      this.sendListKhoDonViID = [];
      let data = {
        nhanVienId: this.$root.token.getNhanVienID(),
        donViId:
          this.denDonViId === "" || this.denDonViId === null
            ? this.$root.token.getDonViID()
            : this.denDonViId,
      };
      const response = await this.getListKhoDonVi(data);
      if (response.error_code === "BSS-00000000") {
        this.listDenKho = response.data;
        // this.khoDonViSelected = response.data[0].tenKho;
        // this.sendListKhoDonViID.push({ KHO_ID: response.data[0].khoID });
      }
      console.log("this.listDenKho", this.listDenKho);
      this.loading(false);
    },
    async fetchListTuKho() {
      this.loading(true);
      const response = await this.getListTuKho();
      if (response.error_code === "BSS-00000000") {
        this.listKhoDonVi = response.data;
        // this.denKhoId = response.data[0].khoID;
        // this.denKhoSelected = response.data[0].tenKho;
      }
      console.log("this.listKhoDonVi", this.listKhoDonVi);
      this.loading(false);
    },
    async fetchListKieuPhieu(_loaiCtId) {
      this.listKieuPhieu = [];
      let data = {
        loaiCtId: _loaiCtId,
      };
      const response = await this.getKieuPhieu(data);
      console.log("response Kiểu Phiếu = ", response);
      if (response.error_code === "BSS-00000000") {
        response.data.forEach((element) => {
          this.listKieuPhieu.push({
            text: element.mucDich,
            value: element.mucDichId,
          });
        });
      }
      // console.log("this.listKieuPhieu", this.listKieuPhieu);
    },
    async fetchTraCuuChungTu() {
      this.loading(true);
      let data = {
        dataRequest: {
          phanVungId: this.$root.token.getPhanVungID(),
          sophieu: this.chk_soPhieu ? this.soPhieuText : "",
          loaiCt: this.chk_LoaiCT ? this.loaiCTSelected : null,
          mucDich: this.chk_MucDich ? this.mucDichSelected : null,
          ttct: this.chk_TrangThaiCT ? this.trangThaiCTSelected : null,
          tuNgayYc: this.chk_ngayYC
            ? moment(this.ngayYCtu).format("DD/MM/yyyy")
            : "",
          denNgayYc: this.chk_denNgay
            ? moment(this.denNgay).format("DD/MM/yyyy")
            : "",
          tuDvi: this.chk_TuDonVi ? this.tuDonViId : null,
          denDvi: this.chk_DenDonVi ? this.denDonViId : null,
          denKho: this.chk_DenKho ? this.denKhoId : null,
          soCv: this.chk_soCV ? this.soCVText : "",
          soHd: this.chk_soHD ? this.soHDText : "",
          maDa: this.chk_MaDA ? this.maDa : "",
          dViDKid: this.chk_DonviDK ? this.donViDKselected : null,
          dsVatTu:
            this.sendListVatTuDKID.length === 0 ? [{}] : this.sendListVatTuDKID,
          dsKho: this.chk_TuKho
            ? this.sendListKhoDonViID.length === 0
              ? [{}]
              : this.sendListKhoDonViID
            : [{}],
          loHang: this.chk_LoHang ? this.loHangText : "",
          cuonCap: this.chk_CuonCap ? this.cuonCapText : "",
          lenhVb: this.chk_lenhVB ? this.lenhVBText : "",
          spxk: this.chk_SPXK ? this.SPXKText : "",
          ctChuaCoDauVao: this.chk_ChuaCoDauVao == true ? 1 : 0,
          idGoc: this.chk_idg ? this.idgText : null,
          dsBoSung: [{}],
        },
        pageNo: this.pageInfo.pageNo,
        pageSize: this.pageInfo.pageSize,
        totalRow: 1,
      };
      try {
        const response = await this.getTraCuuChungTu(data);
        if (response.error_code === "BSS-00000000") {
          this.listTraCuuChungTu = response.data.data;
          this.pageInfo.totalElement = response.data.totalElement;
        }
        console.log("this.listTraCuuChungTu", this.listTraCuuChungTu);
      } catch (Ex) {
        this.$toast.error(Ex.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchChungTuVatTu(_chungTuId) {
      this.loading(true);
      let data = {
        chungTuId: _chungTuId,
      };
      let response = await this.getChungTuVatTu(data);
      if (response.error_code === "BSS-00000000") {
        this.listChungTuVatTu = response.data;
      }
      console.log("this.listChungTuVatTu", this.listChungTuVatTu);
      this.loading(false);
    },
    gridVatTuDK_selectedRowChanged() {},
    gridVatTuDK_selectedData_checkbox(dataItem) {
      this.listVatTuDKID = [];
      this.listVatTuDKID = dataItem;
      console.log("this.listVatTuDKID", this.listVatTuDKID);
      this.showlistVatTuDK = [];
      this.sendListVatTuDKID = [];
      this.listVatTuDKID.forEach((el) => {
        this.listVatTuDK.forEach((data) => {
          if (el === data.value) {
            this.showlistVatTuDK.push(data);
            this.sendListVatTuDKID.push({ VATTU_ID: data.value });
          }
        });
      });
      //this.sendListVatTuDKID = JSON.stringify(this.sendListVatTuDKID);
      console.log("this.sendListVatTuDKID", this.sendListVatTuDKID);
      console.log("this.showlistVatTuDK", this.showlistVatTuDK);
    },
    async TimKiem() {
      // await this.lichsu_xoact(this.chk_LSXCT);
      if (this.chk_LSXCT) {
        await this.fetch_tra_cuu_chung_tu_da_xoa();
      } else {
        await this.fetchTraCuuChungTu();
      }
    },
    async pageChanged_DSTraCuuChungTu(e) {
      console.log("Paging ", e);
      if (!this.chk_LSXCT) {
        this.pageInfo.pageNo = e.pageIndex + 1;
        this.pageInfo.pageSize = e.pageSize;
      }
      // this.pageInfo.totalElement = e.totalElement;
      await this.TimKiem();
    },
    gridTuDonVi_selectedRowChanged(data) {
      console.log(data);
      this.tuDonViSelected = data.tenDonVi;
      this.tuDonViId = data.donViId;
      this.hideTuDonVi();
      console.log("Từ đơn vị", this.tuDonViSelected, this.tuDonViId);
    },
    gridTuDonVi_selectedData_checkbox() {},
    gridDenDonVi_selectedRowChanged(data) {
      this.denDonViId = data.donViId;
      this.denDonViSelected = data.tenDonVi;
      console.log("Đến đơn vị", this.denDonViSelected, this.denDonViId);
      this.hideDenDonVi();
    },
    gridDenDonVi_selectedData_checkbox() {},
    gridTuKho_selectedRowChanged(dataItem) {
      console.log(dataItem);
    },
    gridTuKho_selectedData_checkbox(dataItem) {
      console.log(dataItem);
      this.listKhoDonViID = [];
      this.listKhoDonViID = dataItem;
      this.showlistTuKho = [];
      this.sendListKhoDonViID = [];
      this.listKhoDonViID.forEach((el) => {
        this.listKhoDonVi.forEach((data) => {
          if (el === data.khoID) {
            this.showlistTuKho.push(data);
            this.sendListKhoDonViID.push({ KHO_ID: data.khoID });
          }
        });
      });
      console.log("this.showlistTuKho", this.showlistTuKho);
    },
    gridDenKho_selectedRowChanged(data) {
      this.denKhoId = data.khoID;
      this.denKhoSelected = data.tenKho;
      console.log("Đến kho", this.denKhoSelected, this.denKhoId);
    },
    checkBlackedOutVatTuDKId() {
      this.$refs.vatTuDKId.select();
    },
    checkBlackedOutTuKhoId() {
      this.$refs.tuKho.select();
    },
    gridTraCuuChungTu_selectedRowChanged(data) {
      console.log("go herreeeeee", data);
      if (data) {
        this.chungTuRowSelected = data;
        this.enabledHoanThanh = data.TTPH_ID != 4;

        this.chungtu_id = data.CHUNGTU_ID;
        this.fetchChungTuVatTu(data.CHUNGTU_ID);
        this.fetchChungTuThietBi(data.CHUNGTU_ID); //159948
        console.log(data.CHUNGTU_ID);
      } else {
        this.enabledHoanThanh = false;
      }
    },
    gridChungTuVatTu_selectedRowChanged() {},
    async fetchChungTuThietBi(_chungTuId) {
      this.loading(true);
      let data = {
        chungTuId: _chungTuId,
      };
      const response = await this.getChungTuThietBi(data);
      console.log("Danh sách thiết bị = ", response);
      if (response.error_code === "BSS-00000000") {
        this.listThietBi = response.data.data;
      }
      console.log("this.listThietBi", this.listThietBi);
      this.loading(false);
    },
    // gridThietBi_selectedRowChanged() {},
    exportExcel() {
      console.log("trung");
      this.$bvModal
        .msgBoxConfirm("Bạn có muốn xuất dữ liệu vật tư không?", {
          size: "sm",
          okTitle: "Có",
          cancelTitle: "Không",
          centered: true,
        })
        .then(async (v) => {
          if (v) {
            if (this.listChungTuVatTu.length <= 0) {
              this.$root.toastError(
                "Danh sách vật tư không có dữ liệu để xuất Excel!"
              );
            } else if (this.listThietBi.length <= 0) {
              this.$root.toastError(
                "Danh sách thiết bị không có dữ liệu để xuất Excel!"
              );
            } else {
              this.loading(true);
              try {
                var dsach = xlsx.utils.json_to_sheet(this.listChungTuVatTu);
                var dsach2 = xlsx.utils.json_to_sheet(this.listThietBi);
                var wb = xlsx.utils.book_new();
                var wb2 = xlsx.utils.book_new();
                xlsx.utils.book_append_sheet(wb, dsach, "DS");
                xlsx.utils.book_append_sheet(wb2, dsach2, "DS");
                xlsx.writeFile(wb, "Danh sách vật tư trong chứng từ.xlsx");
                xlsx.writeFile(wb2, "Danh sách thiết bị.xlsx");
              } catch (error) {
              } finally {
                this.$root.toastSuccess(
                  "Xuất dữ liệu Danh sách thiết bị thành công!"
                );
                this.loading(false);
              }
            }
          }
        });
    },
    lamMoiButton() {
      this.status_btnLamMoi = !this.status_btnLamMoi;
      this.listTraCuuChungTu = [];
      this.listChungTuVatTu = [];
      this.listThietBi = [];
    },
    exportExcelDSCT() {
      this.$bvModal
        .msgBoxConfirm(
          "Bạn có muốn xuất tất cả chứng từ và vật tư chứng từ tra cứu được không?",
          {
            size: "sm",
            okTitle: "Có",
            cancelTitle: "Không",
            centered: true,
          }
        )
        .then(async (v) => {
          if (v) {
            if (this.listTraCuuChungTu.length == 0) {
              this.$root.toastError(
                "Danh sách chứng từ không có dữ liệu để xuất Excel!"
              );
            } else {
              this.loading(true);
              try {
                var dsach = xlsx.utils.json_to_sheet(this.listTraCuuChungTu);
                var wb = xlsx.utils.book_new();
                xlsx.utils.book_append_sheet(wb, dsach, "DS");
                xlsx.writeFile(wb, "Danh sách chứng từ.xlsx");

                this.$root.toastSuccess(
                  "Xuất dữ liệu Danh sách chứng từ thành công!"
                );
              } catch (error) {
                this.$root.toastError("Xuất dữ liệu không thành công!");
              } finally {
                this.loading(false);
              }
            }
          }
        })
        .catch((err) => {
          this.$toast.error("Hủy thất bại");
        });
    },
    onChangeCustomDropDown(id) {
      var isHidden = true;
      if (id == "#nvtc-pd") {
        isHidden = this.isShowMD;
        this.isShowMD = !this.isShowMD;
      }

      if (id == "#dvdk-pd") {
        isHidden = this.isShowDonViDK;
        this.isShowDonViDK = !this.isShowDonViDK;
      }

      if (isHidden) {
        $(id).show();
      } else {
        $(id).hide();
      }
    },
    onSelectMucDich(value) {
      if (value) {
        this.mucDichSelected = value.mucDichId;
        this.mucDichText = value.mucDich;
        // $("#nvtc-pd").hide();
        this.onChangeCustomDropDown("#nvtc-pd");
      }
    },
    initPullDown(selector, ignore) {
      $(document).on("mouseup", (e) => {
        var container = $(selector);
        const id = $(e.target).attr("id");
        if (
          !container.is(e.target) &&
          container.has(e.target).length === 0 &&
          id != ignore
        ) {
          container.hide();
        }
      });
      $(document).keyup(function (e) {
        if (e.key === "Escape") {
          // escape key maps to keycode `27`
          const isHidden = $("#nvtc-pd").is(":hidden");
          if (!isHidden) {
            $("#nvtc-pd").hide();
          }
        }
      });
    },
    onSelectDVDK(value) {
      if (value) {
        this.donViDKselected = value.donViId;
        this.dvdkText = value.tenDonVi;
        // $("#nvtc-pd").hide();
      }
      this.onChangeCustomDropDown("#dvdk-pd");
    },

    changeTab(data) {
      if (data == 1) {
        this.isTab = true;
        this.listThietBi = [...this.listThietBi];
      } else {
        this.isTab = false;
        this.listChungTuVatTu = [...this.listChungTuVatTu];
      }
    },
  },
};
</script>
