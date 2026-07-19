<template src="./template.html"></template>
<style src="./style.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import api from "./api";
import enums from "./enums";
import dummy from "./dummy";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
import PopupBuyDevices from "@/modules/contract/setup/InstallNewSubs/Popups/popupBuyDevices.vue";
import PopupTienKM from "@/modules/contract/setup/InstallNewSubs/Popups/popupTienKM.vue";
import ModalNormalizeCAinformation from "@/modules/contract/setup/NormalizeCAinformation/ModalNormalizeCAinformation.vue";
const toUpperKey = (arr) => {
  if (Array.isArray(arr)) {
    return arr.map((item) => toLowerKey(item));
  } else {
    const rt = {};
    for (const [key, value] of Object.entries(arr)) {
      rt[key.toUpperCase()] = value;
    }
    return rt;
  }
};
const toLowerKey = (arr) => {
  if (Array.isArray(arr)) {
    return arr.map((item) => toLowerKey(item));
  } else {
    const rt = {};
    for (const [key, value] of Object.entries(arr)) {
      rt[key.toLowerCase()] = value;
    }
    return rt;
  }
};
const TTKHClear = {
  maGD: "",
  maHD: "",
  maTB: "",
  ghiChu: "",
  diaChi: "",
  maKH: "",
  tenKH: "",
  tenTB: "",
  diaChiLD: "",
  trangthaiTB: "",
  ngayYC: null,
  diachiLDOBj: {},
  mucCuocId: 0,
  tocDoId: 0,
  thuebaoId: 0,
  hdtbId: 0,
  khachhangId: 0,
  hdkhId: 0,
};
export default {
  components: {
    breadcrumb,
    SearchContractModal,
    PopupBuyDevices,
    PopupTienKM,
    ModalNormalizeCAinformation,
  },
  name: "SellerEquipment",
  props: {
    isPopup: {
      type: Boolean,
    },
  },
  mounted() {
    // this.clearData()
    // this.$refs.maKHGocInput.focus()
    this.ngayCN = this.$auth.getNgayCapNhat();
    this.addKeyDownEvent();
    this.getComboboxData();
  },
  destroyed() {},
  computed: {
    enableNhapmoiBnt() {
      return !this.isNhapMoi;
    },
    enableGhiLaiBnt() {
      return (
        this.TTKH.maTB !== "" &&
        this.TTKH.loaihinh.selected &&
        this.TTKH.loaihinh.selected.loaitb_id
      );
    },
    buyDevicesData() {
      if (this.TTKH.loaihinh.selected) {
        return {
          _loaitb_id: this.TTKH.loaihinh.selected.loaitb_id,
          ds: this.dsThietBiMua,
        };
      } else {
        return { ds: this.dsThietBiMua };
      }
    },
  },
  watch: {
    loading: function (val, oldval) {
      this.$root.showLoading(val);
    },
    openedPopup: function (val, oldval) {
      if (val) {
        this.removeKeyDownEvent();
      } else {
        this.addKeyDownEvent();
      }
    },
  },
  data() {
    return {
      loading: false,
      header: {
        title: "PHIẾU YÊU CẦU BÁN THIẾT BỊ",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          { name: "Lắp đặt mới", link: { name: "Ui.cards" } },
        ],
      },
      filterbox: {
        ctv: {
          value: "",
          ischecked: false,
          ma_ctv: "",
        },
        nguoigt: {
          value: "",
          ischecked: false,
          nguoigt_id: "",
        },
      },
      maduan: "",
      ngayCN: moment(new Date()).format("DD/MM/YYYY"),
      searchDate: moment(new Date()).format("DD/MM/YYYY"),
      TTKH: {
        maGD: "",
        maHD: "",
        maTB: "",
        ghiChu: "",
        diaChi: "",
        maKH: "",
        tenKH: "",
        tenTB: "",
        diaChiLD: "",
        trangthaiTB: "",
        loaihinh: { data: [], selected: null },
        dichvu: { data: [], selected: null },
        ngayYC: null,
        diachiLDOBj: {},
        mucCuocId: 0,
        tocDoId: 0,
        thuebaoId: 0,
        hdtbId: 0,
        khachhangId: 0,
        hdkhId: 0,
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        "form-close": this.popupClosed,
        acceptChangeCTV: this.popupClosed,
        acceptChangeNGT: this.popupClosed,
        PopUpCongTacVien_Thoat: this.popupClosed,
      },
      popupComponentName: "",
      isNhapMoi: true,
      dsDvBanThietBi: [],
      dsThietBiMua: [],
      dsTienKMPopup: {
        ds: [],
        cols: [
          {
            fieldName: "tentat_kmtt",
            headerText: "Khoản mục",
            allowFiltering: false,
            allowSorting: false,
            isPrimaryKey: true,
          },
          {
            fieldName: "tongtien",
            textAlign: "right",
            allowEditing: true,
            editType: "numericedit",
            format: "N",
            customAttributes: { class: "tongtien" },
            headerText: "Tổng tiền",
            allowFiltering: false,
            allowSorting: false,
            edit: "tongtienParam",
          },
          {
            fieldName: "tien",
            textAlign: "right",
            allowEditing: true,
            editType: "numericedit",
            format: "N",
            customAttributes: { class: "cuoclapdat" },
            headerText: "Tiền",
            allowFiltering: false,
            allowSorting: false,
            edit: "tienParam",
          },
          {
            fieldName: "vat",
            textAlign: "right",
            format: "N",
            customAttributes: { class: "vatlapdat" },
            headerText: "Vat",
            allowFiltering: false,
            allowSorting: false,
          },
        ],
        keycol: "khoanmuctt_id",
        allowExit: false,
        tieude: "Chi tiết tiền khoản mục",
        tieudebang: "Chi tiết khoản mục thanh toán",
      },
      tienKM: {},
      headerGridboxDSHDTB: enums.headerGridboxDSHDTB,
      buyDevicesForm: { tien_tbi: 0, vat_tbi: 0, dsThietBi: [] },
      gridboxDSHDTB: {
        data: [],
        selected: [],
      },
      openedPopup: false,
      xacNhanGhiLaiVisible: false,
      dateconfig: {
        altFormat: "d/m/Y",
        altInput: true,
        dateFormat: "d/m/Y",
        allowInput: true,
      },
      LoaiHopDong: enums.LoaiHopDong,
    };
  },
  methods: {
    addKeyDownEvent() {
      window.addEventListener("keydown", this.onF5KeyDown);
    },
    removeKeyDownEvent() {
      window.removeEventListener("keydown", this.onF5KeyDown);
    },
    onF5KeyDown(event) {
      // if (event.keyCode === 116) {
      //   event.preventDefault()
      //   this.clearData()
      // }
    },
    GetData(response) {
      // console.log(response)
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else {
        console.log(response.data.error_code);
      }
      return [];
    },
    GetDataObj(response) {
      // console.log(response)
      if (
        (response.data.error === 200 || response.data.error === "200") &&
        response.data.error_code === "BSS-00000000"
      ) {
        return response.data.data;
      } else {
        throw new Error(response.data.message);
      }
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
    async popupClosed(val) {
      if (!val) {
        this.popupRetVal = {};
        this.ClosePopup("popupComponents");
        return;
      }
      this.loading = true;
      try {
        switch (this.popupComponentName) {
          case "popupCTV":
            this.filterbox.ctv.value = val.ten_ht;
            this.filterbox.ctv.ma_ctv = val.nhanvien_id;
            this.ctv_id = val.nhanvien_id;
            break;
          case "popupNGT":
            this.filterbox.nguoigt.value = val.ten_ht;
            this.filterbox.nguoigt.nguoigt_id = val.nhanvien_id;
            break;
        }
      } catch (ex) {
        this.$toast.error("" + ex);
        if (this.debug) console.error(ex);
      } finally {
        this.ClosePopup("popupComponents");
        this.loading = false;
      }
    },
    Popup(modalId) {
      console.log(this.$refs[modalId]);
      this.loading = false;
      if (this.$refs[modalId]) this.$refs[modalId].show();
      else this.$bvModal.show(modalId);
    },
    ShowPopupCTV() {
      if (this.filterbox.ctv.ischecked) {
        this.popupComponent = () =>
          import("@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien");
        this.popupComponentName = "popupCTV";
        // SearchAccount.data.isPopup = true;
        this.Popup("popupComponents");
      }
    },
    ShowPopupNguoiGT() {
      if (this.filterbox.nguoigt.ischecked) {
        this.popupComponent = () =>
          import("@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu");
        this.popupComponentName = "popupNGT";
        // SearchAccount.data.isPopup = true;
        this.Popup("popupComponents");
      }
    },
    showPopupSearchContract() {
      this.$refs.popupSearchContract.showModal();
    },
    acceptSearchContract(item) {
      console.log(item);
      this.TTKH.maGD = item.ma_gd;
      this.getDSHD();
    },
    timKiemHDCNTTShow() {
      this.$refs.timKiemHDCNTTModal.showModal();
    },
    timKiemHDCNTTClose(item) {
      // item = { ma_tb: 'takahata' }
      if (item && item.ma_tb) {
        this.TTKH.maTB = item.ma_tb;
        this.getDetailsMaTB();
      } else {
        this.$toast.error("Không tìm được thông tin thuê bao");
      }
    },
    buyDevicesShow() {
      this.$refs.BuyDevicesModal.show();
    },
    buyDevicesClose(item) {
      this.dsThietBiMua = item;
      this.$refs.BuyDevicesModal.hide();
      this.hienThiTB();
    },
    async tienKMShow() {
      this.loading = true;

      try {
        const ds = this.GetData(
          await api.lay_ds_tien_kmtt(this.axios, {
            vkieuld_id: 49,
            vloaihd_id: enums.LoaiHopDong.DAT_MOI,
            vloaitb_id: this.TTKH.loaihinh.selected.loaitb_id,
          })
        );
        ds.forEach((item) => {
          const x = this.dsTienKMPopup.ds.find(
            (x) => x.khoanmuctt_id == item.khoanmuctt_id
          );
          if (x) {
            item.tien = x.tien;
            item.vat = x.vat;
            item.tentat_kmtt = x.tentat_kmtt;
            item.tongtien = item.tien + item.vat;
          }
        });
        this.dsTienKMPopup.ds = ds;
        this.$refs.TienKMModal.show();
      } catch (err) {
        console.error(err);
        // setTimeout(() => {
        //   this.getComboboxData()
        // }, 3000)
      } finally {
        this.loading = false;
      }
    },
    tienKMClose(item) {
      console.log(item);
      // this.dsThietBiMua = item
      this.$refs.TienKMModal.hide();
    },
    async getComboboxData() {
      this.loading = true;
      try {
        this.TTKH.loaihinh.selected = null;
        this.TTKH.loaihinh.data = this.GetData(await api.getComboxDataLHTB(this.axios));
        // eslint-disable-next-line eqeqeq
        this.TTKH.dichvu.selected = null;
        this.TTKH.dichvu.data = this.GetData(await api.getComboxDataLHDV(this.axios));
      } catch (err) {
        console.error(err);
        // setTimeout(() => {
        //   this.getComboboxData()
        // }, 3000)
      } finally {
        this.loading = false;
      }
    },
    async hienThiTB() {
      this.loading = true;

      try {
        let tien_tbi = 0;
        let vat_tbi = 0;
        let vsl_cha = -1;
        let dtien_tbi_tmp = 0;
        let dvat_tbi_tmp = 0;
        let dsThietBi = [];
        const loaitb_id = this.TTKH.loaihinh.selected
          ? this.TTKH.loaihinh.selected.loaitb_id
          : 0;
        for (const item of this.dsThietBiMua) {
          const soluong = parseInt(item.soluong);
          if (item.tien.toString() !== "") {
            dtien_tbi_tmp = parseFloat(item.tien);
            tien_tbi += dtien_tbi_tmp * soluong;
          }
          if (item.vat.toString() !== "") {
            dvat_tbi_tmp = parseFloat(item.vat);
            vat_tbi += dvat_tbi_tmp * soluong;
          }

          dsThietBi.push(`${item.loai_tbi} - Số lượng: ${item.soluong}`);
          if (vsl_cha !== -1) {
            if (
              item.block_tiep.toString() !== "" &&
              item.gia_block_tiep.toString() !== "" &&
              item.gia_block_tiep.toString() !== "0"
            ) {
              if (
                loaitb_id === enums.LoaiHinhTB.IOFFICE ||
                loaitb_id === enums.LoaiHinhTB.HNTH_NGN ||
                loaitb_id === enums.LoaiHinhTB.VNPT_MEETING
              ) {
                if (vsl_cha > parseInt(item.block_dau)) {
                  let vsl_conlai = vsl_cha - parseInt(item.block_dau);

                  let t2 = vsl_conlai % parseInt(item.block_tiep);
                  let vsl_bl = 0;
                  if (t2 > 0) {
                    vsl_bl = vsl_conlai / parseInt(item.block_tiep) + 1;
                  } else {
                    vsl_bl = vsl_conlai / parseInt(item.block_tiep);
                  }

                  let tien_block = 0;
                  tien_block = vsl_bl * parseFloat(item.gia_block_tiep);
                  dtien_tbi_tmp += tien_block;
                  tien_tbi = tien_tbi + tien_block * soluong;
                }
              }
            }
            if (
              item.block_tiep.toString() !== "" &&
              item.vat_block_tiep.toString() !== "" &&
              item.vat_block_tiep.toString() !== "0"
            ) {
              if (
                loaitb_id === enums.LoaiHinhTB.IOFFICE ||
                loaitb_id === enums.LoaiHinhTB.HNTH_NGN ||
                loaitb_id === enums.LoaiHinhTB.VNPT_MEETING
              ) {
                if (vsl_cha > parseInt(item.block_dau)) {
                  let vsl_conlai = vsl_cha - parseInt(item.block_dau);

                  let t2 = vsl_conlai % parseInt(item.block_tiep);
                  let vsl_bl = 0;
                  if (t2 > 0) {
                    vsl_bl = vsl_conlai / parseInt(item.block_tiep) + 1;
                  } else {
                    vsl_bl = vsl_conlai / parseInt(item.block_tiep);
                  }

                  let vat_block = 0;
                  vat_block = vsl_bl * parseFloat(item.vat_block_tiep);
                  dvat_tbi_tmp += vat_block;
                  vat_tbi = vat_tbi + vat_block * soluong;
                }
              }
            }
          }

          // this.GetData(
          //   await api.sp_ht_ttct_thietbi_loaitbi(this.axios, {
          //     loaitbi_id: item.loaitbi_id
          //   })
          // )
          // const dsKM = this.GetData(
          //   await api.sp_lay_ctkm_loai_tbi(this.axios, {
          //     ds_ctkm_id: '-1',
          //     loaitbi_id: item.loaitbi_id
          //   })
          // )
        }
        if (this.dsTienKMPopup.ds && this.dsTienKMPopup.ds.length > 0) {
          const idx = this.dsTienKMPopup.ds.findIndex((item) => item.khoanmuctt_id == 52);
          if (idx !== -1) {
            this.dsTienKMPopup.ds[idx].tien = tien_tbi;
            this.dsTienKMPopup.ds[idx].vat = vat_tbi;
          }
        }
        tien_tbi = tien_tbi.toLocaleString("vi-VN", {
          style: "currency",
          currency: "VND",
        });
        vat_tbi = vat_tbi.toLocaleString("vi-VN", { style: "currency", currency: "VND" });
        this.buyDevicesForm = { tien_tbi, vat_tbi, dsThietBi };
      } catch (err) {
        console.error(err);
        // setTimeout(() => {
        //   this.getComboboxData()
        // }, 3000)
      } finally {
        this.loading = false;
      }
    },
    async getDSHD() {
      if (this.TTKH.maGD !== "") {
        this.loading = true;

        try {
          this.TTKH = { ...this.TTKH, ...TTKHClear, maGD: this.TTKH.maGD };
          this.TTKH.loaihinh.selected = null;
          this.TTKH.dichvu.selected = null;
          this.dsTienKMPopup.ds = [];
          this.dsThietBiMua = [];
          this.buyDevicesForm = { tien_tbi: 0, vat_tbi: 0, dsThietBi: [] };
          this.dsDvBanThietBi = [];
          this.gridboxDSHDTB = {
            data: [],
            selected: [],
          };
          const dsHD = this.GetData(
            await api.lay_ds_hopdong_theo_ma_gd(this.axios, {
              in_ma_gd: this.TTKH.maGD,
              in_loaihd_id: this.LoaiHopDong.BAN_THIETBI,
              in_donvi_id: this.$root.token.getDonViID(),
              in_tthd_id: 1,
              in_nhanvien_id: this.$root.token.getNhanVienID(),
              in_donvi_dl_id: 0,
            })
          );
          if (dsHD.length > 0) {
            this.TTKH.hdkhId = dsHD[0].hdkh_id;
            this.TTKH.hdtbId = dsHD[0].hdtb_id;
            // a.Long zem 11052016
            // cboDichVuVT.SelectedValue = Convert.ToInt32(ds.Tables[0].Rows[0]["dichvuvt_id"]);
            this.TTKH.maGD = dsHD[0].ma_gd;
            this.TTKH.maHD = dsHD[0].ma_hd;
            this.TTKH.maKH = dsHD[0].ma_kh;
            this.TTKH.khachhangId = dsHD[0].khachhang_id;
            this.TTKH.tenKH = dsHD[0].ten_kh;
            this.TTKH.ngayYC = moment(new Date(dsHD[0].ngay_yc)).format("DD/MM/YYYY");
            this.ngayCN = moment(new Date(dsHD[0].ngaylap_hd)).format("DD/MM/YYYY");
            const doituong_id = dsHD[0].doituong_id;
            // const ds_hdtb = this.GetData(await api.ds_hopdong_tb_theo_hdkh_id(this.axios, { hdkhId: 4922568  }))
            const ds_hdtb = this.GetData(
              await api.ds_hopdong_tb_theo_hdkh_id(this.axios, {
                hdkhId: this.TTKH.hdkhId,
              })
            );
            if (ds_hdtb.length > 0) {
              const tchd = ds_hdtb[0];
              this.TTKH.thueBaoId = tchd.thuebao_id;
              this.TTKH.hdtbId = tchd.hdtb_id;
              this.TTKH.tenTB = tchd.ten_tb;
              this.TTKH.maTB = tchd.ma_tb;
              this.TTKH.maGD = tchd.ma_gd;
              this.TTKH.diaChiLD = tchd.diachi_ld;
              this.TTKH.diaChi = tchd.diachi_tb;
              this.TTKH.ghiChu = tchd.ghichu;
              this.TTKH.loaihinh.selected = this.TTKH.loaihinh.data.find(
                (item) => item.loaitb_id === tchd.loaitb_id
              );
              this.TTKH.dichvu.selected = this.TTKH.dichvu.data.find(
                (item) => item.DICHVUVT_ID === tchd.dichvuvt_id
              );
              // HT_ThietBi()
              this.gridboxDSHDTB.data = ds_hdtb;
              this.isNhapMoi = false;
            }
            this.dsThietBiMua = this.GetData(
              await api.lay_ds_mua_tbi(this.axios, { hdtb_id: this.TTKH.hdtbId })
            );
            this.dsTienKMPopup.ds = this.GetData(
              await api.sp_lay_tienkhoanmuc_loaihd_banthietbi(this.axios)
            );
            await this.hienThiTB();
            // HienThiTTHopDongTB(ds_hdtb);
          } else {
            this.$toast.error("Không tìm được hợp đồng");
          }
        } catch (err) {
          console.error(err);
          // setTimeout(() => {
          //   this.getComboboxData()
          // }, 3000)
        } finally {
          this.loading = false;
        }
      }
    },
    async getDetailsMaTB() {
      if (this.TTKH.maTB !== "") {
        this.loading = true;
        this.isNhapMoi = true;
        try {
          this.TTKH = { ...this.TTKH, ...TTKHClear, maTB: this.TTKH.maTB };
          this.TTKH.loaihinh.selected = null;
          this.TTKH.dichvu.selected = null;
          this.dsTienKMPopup.ds = [];
          this.dsThietBiMua = [];
          this.buyDevicesForm = { tien_tbi: 0, vat_tbi: 0, dsThietBi: [] };
          this.dsDvBanThietBi = [];
          this.gridboxDSHDTB = {
            data: [],
            selected: [],
          };
          const dsDbTB = this.GetData(
            await api.sp_lay_ds_db_thuebao_theo_ma_tb(this.axios, {
              ma_tb: this.TTKH.maTB,
            })
          );
          if (dsDbTB.length > 0) {
            const dichvuvt_id = dsDbTB[0].dichvuvt_id;
            this.TTKH.dichvu.selected = this.TTKH.dichvu.data.find(
              (item) => item.DICHVUVT_ID === dichvuvt_id
            );
            const ds_dbkh = this.GetData(
              await api.lay_danhba_theo_matb(this.axios, {
                in_ma_tb: this.TTKH.maTB,
                in_dichvuvt_id: dichvuvt_id,
                in_donvi_dl_id: 0,
              })
            );
            if (ds_dbkh.length > 0) {
              const trangthaiTbId = ds_dbkh[0].trangthaitb_id;
              if (trangthaiTbId === 7 || trangthaiTbId === 8 || trangthaiTbId === 9) {
                this.$toast.warning(
                  "Không thể mua thêm thiết bị cho thuê bao đang dừng hoạt động!"
                );
                return;
              }
              this.TTKH.khachhangId = ds_dbkh[0].khachhang_id;
              this.TTKH.maHD = ds_dbkh[0].ma_hd;
              this.TTKH.maKH = ds_dbkh[0].ma_kh;
              this.TTKH.tenKH = ds_dbkh[0].ten_kh;

              this.TTKH.thuebaoId = ds_dbkh[0].thuebao_id;
              this.TTKH.tenTB = ds_dbkh[0].ten_tb;
              this.TTKH.diaChi = ds_dbkh[0].diachi_tb;
              this.TTKH.diaChiLD = ds_dbkh[0].diachi_ld;
              this.TTKH.trangthaiTB = ds_dbkh[0].trangthai_tb;
              const doituong_id = ds_dbkh[0].DOITUONG_ID;
              const loaitb_id = ds_dbkh[0].loaitb_id;
              const donvi_id = ds_dbkh[0].donvi_id;
              this.TTKH.loaihinh.selected = this.TTKH.loaihinh.data.find(
                (item) => item.loaitb_id === loaitb_id
              );

              this.diachiLDOBj = null;
              const dcldData = this.GetData(
                await api.sp_lay_diachi_theo_thuebao_id(this.axios, {
                  thuebao_id: ds_dbkh[0].thuebao_id,
                })
              );
              if (dcldData.length > 0) {
                this.diachiLDOBj = dcldData[0];
              }

              // eslint-disable-next-line eqeqeq
              if (
                dichvuvt_id == 13 ||
                dichvuvt_id == 14 ||
                dichvuvt_id == 15 ||
                dichvuvt_id == 16
              ) {
                const dbcntt = this.GetData(
                  await api.lay_ds_danhba_cntt(this.axios, {
                    thueBaoId: ds_dbkh[0].thuebao_id,
                  })
                );
                if (dbcntt.length > 0) {
                  this.TTKH.mucCuocId = dbcntt[0].muccuoc_id;
                  this.TTKH.tocDoId = dbcntt[0].tocdo_id;
                }
              }
              // this.gridboxDSHDTB.data = ds_dbkh
              const dsDvBanThietBi = this.GetData(
                await api.sp_lay_ds_quytrinh_ban_thietbi(this.axios, {
                  dichvuvt_id: dichvuvt_id,
                  tocdo: this.TTKH.tocDoId,
                  muccuoc: this.TTKH.mucCuocId,
                  loaihinh: loaitb_id,
                })
              );
              if (dsDvBanThietBi) {
                for (let i = 0; i < dsDvBanThietBi.length; i++) {
                  const data = this.GetData(
                    await api.sp_lay_ds_donvi_tramtc_banthietbi(this.axios, {
                      p_donviql_id: donvi_id,
                      p_loaidv_id: dsDvBanThietBi[i].loaidv_dich_id,
                    })
                  ).map((item) => {
                    return {
                      ten_dv: item.ten_dv || "",
                      donvi_id: item.donvi_id,
                    };
                  });
                  dsDvBanThietBi[i].comboBox = {
                    data: data,
                    selected: data.length > 0 ? data[0] : null,
                  };
                }
                this.dsDvBanThietBi = dsDvBanThietBi;
              }
              this.dsTienKMPopup.ds = this.GetData(
                await api.sp_lay_tienkhoanmuc_loaihd_banthietbi(this.axios)
              );

              // this.GetData(
              //   await api.lay_danhba_theo_matb(this.axios, {
              //     in_ma_tb: this.TTKH.maTB,
              //     in_dichvuvt_id: dvId,
              //     in_donvi_dl_id: 0
              //   })
              // )

              // this.GetData(await api.sp_lay_ds_dbtb_dv(this.axios, { p_thuebao_id: thuebaoId }))
              // this.GetData(await api.lay_ds_db_dd_theo_tbid(this.axios, { in_thuebao_id: thuebaoId }))
            }
          } else {
            this.$toast.warning("Nhập lại mã thuê bao tìm kiếm !");
          }
        } catch (err) {
          console.error(err);
          // setTimeout(() => {
          //   this.getComboboxData()
          // }, 3000)
        } finally {
          this.loading = false;
        }
      }
    },
    async getTTTB() {
      if (this.TTKH.maTB !== "") {
        this.loading = true;
        try {
          const ds_dbkh = this.GetData(
            await api.lay_danhba_theo_matb(this.axios, {
              in_ma_tb: this.TTKH.maTB,
              in_dichvuvt_id: this.TTKH.dichvu.selected.DICHVUVT_ID,
              in_donvi_dl_id: 0,
            })
          );
          if (ds_dbkh.length > 0) {
            this.TTKH.trangthaiTB = ds_dbkh[0].trangthai_tb;
          }
        } catch (err) {
          console.error(err);
        } finally {
          this.loading = false;
        }
      }
    },
    selectedICGridboxDSHDTB(selectedIndexed) {
      this.gridboxDSHDTB.selected = selectedIndexed;
    },
    clearData() {
      this.gridboxDSHDTB = { data: [], selected: [] };
      this.TTKH = { ...this.TTKH, ...TTKHClear };
      this.TTKH.loaihinh.selected = null;
      this.TTKH.dichvu.selected = null;
      this.dsTienKMPopup.ds = [];
      this.dsThietBiMua = [];
      this.buyDevicesForm = { tien_tbi: 0, vat_tbi: 0, dsThietBi: [] };
      this.gridboxDSHDTB = {
        data: [],
        selected: [],
      };
    },
    nhapMoiOk() {
      this.clearData();
      this.isNhapMoi = true;
    },
    HuyBnt() {
      if (this.isNhapMoi) {
        this.clearData();
      } else {
        this.getDSHD();
      }
    },
    xoaHDBnt() {
      this.$bvModal
        .msgBoxConfirm("Bạn thật sự muốn xóa HĐ ?", {
          title: "Thông báo",
          size: "sm",
          centered: true,
          okTitle: "Ok",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (!v) {
            $this.$toast.warning("Bạn đã hủy xóa HĐ ");
          } else {
            this.xoaHDOk();
          }
        });
    },
    async xoaHDOk() {
      if (this.isNhapMoi) {
        this.$toast.warning("Không đúng trạng thái không thể xoá");
      } else if (this.TTKH.hdtbId) {
        this.loading = true;
        try {
          const response = await api.xoa_hdkh_v2(this.axios, {
            hdkh_id: this.TTKH.hdkhId,
            loaihd_id: this.LoaiHopDong.BAN_THIETBI,
          });

          if (
            (response.data.error === 200 || response.data.error === "200") &&
            response.data.error_code === "BSS-00000000"
          ) {
            this.$toast.success("Xóa HD thành công");
            this.isNhapMoi = true;
            this.clearData();
          } else {
            this.$toast.error(response.data.message);
          }
        } catch (err) {
          console.error(err);
          // setTimeout(() => {
          //   this.getComboboxData()
          // }, 3000)
        } finally {
          this.loading = false;
        }
      }
    },
    async ghiLaiOk() {
      if (
        this.TTKH.maTB !== "" &&
        this.TTKH.loaihinh.selected &&
        this.TTKH.loaihinh.selected.loaitb_id
      ) {
        this.loading = true;
        try {
          const huongGiaoTnData = this.GetDataObj(
            await api.lay_huonggiao_tiepnhan(this.axios, {
              kieuLdId: 49,
              loaiHdId: this.LoaiHopDong.BAN_THIETBI,
              loaiTbId: this.TTKH.loaihinh.selected.loaitb_id,
              mucCuocId: this.TTKH.mucCuocId,
              tocDoId: this.TTKH.tocDoId,
            })
          );
          if (huongGiaoTnData) {
            const quytrinhId = huongGiaoTnData.quytrinh_id;
            const huonggiao_id = huongGiaoTnData.huonggiao_id;
            // TaoDuLieuHDKhachHang

            const vds_hdkh = {
              HDKH_ID: this.TTKH.hdkhId,
              MA_GD: this.TTKH.maGD,
              KHACHHANG_ID: this.TTKH.khachhangId,
              LOAIHD_ID: this.LoaiHopDong.BAN_THIETBI,
              NGAY_YC: this.$auth.getNgayCapNhat(),
              NGAYLAP_HD: this.$auth.getNgayCapNhat(),
              NHANVIEN_ID: this.$root.token.getNhanVienID(),
              DONVI_ID: this.$root.token.getDonViID(),
              MA_DUAN: this.maduan.trim(),
            };
            if (this.isNhapMoi) {
              this.TTKH.hdkhId = this.GetDataObj(
                await api.get_keys(this.axios, {
                  keyname: "HD_KHACHHANG",
                  numblocksize: 1,
                  numretry: 10,
                })
              );
              vds_hdkh.HDKH_ID = this.TTKH.hdkhId;
              vds_hdkh.MA_GD = this.GetDataObj(
                await api.sinh_magd(this.axios, {
                  loaihd_id: this.LoaiHopDong.BAN_THIETBI,
                })
              );
            }
            if (this.filterbox.ctv.ischecked && this.filterbox.ctv.ma_ctv)
              vds_hdkh.CTV_ID = this.filterbox.ctv.ma_ctv;
            if (this.filterbox.nguoigt.ischecked && this.filterbox.nguoigt.nguoigt_id)
              vds_hdkh.NHANVIENGT_ID = this.filterbox.nguoigt.nguoigt_id;
            // console.log(vds_hdkh)
            // TaoDuLieuHDThueBao
            if (this.isNhapMoi) {
              this.TTKH.hdtbId = this.GetDataObj(
                await api.get_keys(this.axios, {
                  keyname: "HD_THUEBAO",
                  numblocksize: 1,
                  numretry: 10,
                })
              );
            }
            const vds_hdtb = {
              HDTB_ID: this.TTKH.hdtbId,
              HDKH_ID: this.TTKH.hdkhId,
              KIEULD_ID: 49,
              QUYTRINH_ID: quytrinhId,
              TEN_TB: this.TTKH.tenTB,
              THUEBAO_ID: this.TTKH.thuebaoId,
              TTHD_ID: 1,
              GHICHU: this.TTKH.ghiChu,
            };

            // TaoDuLieu_CT_TIENHD
            if (this.dsTienKMPopup.ds.length > 0) {
              let vds_cttt = [];
              for (const tienKhoanmuc of this.dsTienKMPopup.ds) {
                const cttien = {
                  HDTB_ID: this.TTKH.hdtbId,
                  CT_TIENHD_ID: this.GetDataObj(
                    await api.get_keys(this.axios, {
                      keyname: "CT_TIENHD",
                      numblocksize: 1,
                      numretry: 10,
                    })
                  ),
                  ID: tienKhoanmuc.khoanmuctt_id,
                  KHOANMUCTT_ID: tienKhoanmuc.khoanmuctt_id,
                  TIEN: parseFloat(tienKhoanmuc.tien),
                  VAT: parseFloat(tienKhoanmuc.vat),
                  LOAI_ID: 1,
                };
                vds_cttt.push(cttien);
              }
              // TaoDuLieuCTThietBi
              const vds_ctmua_tbi = this.dsThietBiMua.map((item) => {
                const tbi = { ...item, HDTB_ID: this.TTKH.hdtbId };
                return toUpperKey(tbi);
              });
              // TaoDuLieu_HDTB_DV
              const vds_hdtb_dv = this.dsDvBanThietBi.map((item) => {
                return {
                  DONVI_ID: item.comboBox.selected.donvi_id,
                  HDTB_ID: this.TTKH.hdtbId,
                  LOAIDV_ID: item.loaidv_dich_id,
                  KIEUDV_ID: 2,
                };
              });
              const params = {
                vthuebao_id: this.TTKH.thuebaoId,
                vkhachhang_id: this.TTKH.khachhangId,
                vhdtb_id: this.TTKH.hdtbId,
                vhuonggiao_id: huonggiao_id,
                vds_hdkh: JSON.stringify(vds_hdkh),
                vds_hdtb: JSON.stringify(vds_hdtb),
                vds_cttt: JSON.stringify(vds_cttt),
                vds_ctmua_tbi: JSON.stringify(vds_ctmua_tbi),
                vds_hdtb_dv: JSON.stringify(vds_hdtb_dv),
                vnguoi_cn: this.$root.token.getNhanVienID(),
                vmay_cn: await this.$root.token.getMachine(),
                vip_cn: await this.$root.token.getIP(),
                vkieu: this.isNhapMoi ? 0 : 1,
              };
              // console.log(params);
              const thucthi = this.GetDataObj(
                await api.capnhat_hd_mua_thietbi(this.axios, params)
              );
              if (thucthi.toString() === "ok") {
                this.TTKH.maGD = vds_hdkh.MA_GD;
                await this.getDSHD();
                await this.getTTTB();
                this.$toast.success("Thêm thành công");
              } else {
                this.$toast.error(thucthi.toString());
              }
            } else {
              this.$toast.error("Lỗi thanh toán: Chưa có khoản mục thanh toán");
              return;
            }
          } else {
            this.$toast.error(
              "Quy trình chưa được thiết lập, bạn vui lòng liên hệ Admin để được hỗ trợ!"
            );
          }
        } catch (err) {
          console.error(err);
          // setTimeout(() => {
          //   this.getComboboxData()
          // }, 3000)
        } finally {
          this.loading = false;
        }
      }
    },
  },
};
</script>
