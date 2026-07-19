<template src="./template.html"></template>
<style src="./style.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import api from "./api";
import enums from "./enums";
import { LoaiHopDong, TrangThaiHD } from "@/utils/Enum";
import dummy from "./dummy";
// import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
import InPhieuChi from "@/modules/print/InPhieuChi/InPhieuChi.vue";
export default {
  components: { breadcrumb, SearchContractModal, InPhieuChi },
  name: "CancellationContract",
  props: {
    isPopup: {
      type: Boolean,
    },
  },
  mounted() {
    // console.log(this.$route.query.tag?true:false);
    this.getComboboxData();
    this.getTSMD();
  },
  destroyed() {},
  computed: {
    enableThoaiTraBnt() {
      if (
        this.gridboxDSPTT.selected &&
        this.gridboxDSTB.selected &&
        this.gridboxDSKM.data.length > 0
      ) {
        // eslint-disable-next-line eqeqeq
        if (
          this.gridboxDSTB.selected.tthd_id == this.TrangThaiHD.THANH_TOAN ||
          (this.gridboxDSTB.selected.tthd_id == this.TrangThaiHD.MOI &&
            this.gridboxDSPTT.selected.loaihd_id == this.LoaiHopDong.CHAMDUT_SD)
        ) {
          if (
            this.gridboxDSTB.selected.tien == 0 ||
            !(
              this.gridboxDSPTT.selected.hddt_fkey &&
              this.gridboxDSPTT.selected.hddt_fkey.toString().length > 0
            )
          ) {
            return { thoaiTraTam: false, thoaiTra: true };
          } else {
            return { thoaiTraTam: true, thoaiTra: false };
          }
          // eslint-disable-next-line eqeqeq
        } else if (this.gridboxDSTB.selected.tthd_id == this.TrangThaiHD.THOAITRA_TMP) {
          return { thoaiTraTam: false, thoaiTra: false };
        } else {
          // eslint-disable-next-line eqeqeq
          if (
            this.gridboxDSTB.selected.tthd_id == this.TrangThaiHD.DA_THI_CONG_XONG ||
            this.gridboxDSTB.selected.tthd_id == this.TrangThaiHD.HTHS_BANCHEO ||
            this.gridboxDSTB.selected.tthd_id == this.TrangThaiHD.DOISOAT_HOSO
          ) {
            if (this.checkedDKThoaiTra !== "2") {
              return { thoaiTraTam: false, thoaiTra: false };
            } else {
              if (
                this.gridboxDSTB.selected.tien == 0 ||
                !(
                  this.gridboxDSPTT.selected.hddt_fkey &&
                  this.gridboxDSPTT.selected.hddt_fkey.toString().length > 0
                )
              ) {
                return { thoaiTraTam: false, thoaiTra: true };
              } else {
                return { thoaiTraTam: true, thoaiTra: false };
              }
            }
          } else {
            return { thoaiTraTam: false, thoaiTra: false };
          }
        }
      } else {
        return { thoaiTraTam: false, thoaiTra: true };
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
    "gridboxDSPTT.selected": {
      handler: function (val, oldval) {
        this.khachHangTiemNang = val && val.loaihd_id === this.LoaiHopDong.DAT_MOI;
        this.getDataGridboxDSTB();
      },
      deep: true,
    },
    "gridboxDSTB.selected": {
      handler: function (val, oldval) {
        this.getDataGridboxDSKM();
      },
      deep: true,
    },
  },
  data() {
    return {
      loading: false,
      TrangThaiHD: TrangThaiHD,
      LoaiHopDong: LoaiHopDong,
      khachHangTiemNang: false,
      header: {
        title: "THOÁI TRẢ HỢP ĐỒNG",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          { name: "Lắp đặt mới", link: { name: "Ui.cards" } },
        ],
      },
      isHNI: false,
      checkedDKThoaiTra: null,
      headerGridboxDSPTT: enums.headerGridboxDSPTT,
      gridboxDSPTT: {
        data: [],
        selected: null,
      },
      headerGridboxDSTB: enums.headerGridboxDSTB,
      gridboxDSTB: {
        data: [],
        selected: null,
      },
      headerGridboxDSKM: enums.headerGridboxDSKM,
      gridboxDSKM: {
        data: [],
        selected: [],
      },
      openedPopup: false,
      xacNhanGhiLaiVisible: false,
      maGD: "",
      comboBox: {
        nvThuNgan: { data: [], selected: null },
        lyDo: { data: [], selected: null },
      },
      searchDate: {
        isEnabledFromDate: true,
        isEnabledToDate: false,
        fromDate: moment(new Date()).format("DD/MM/YYYY"),
        toDate: moment(new Date()).format("DD/MM/YYYY"),
        dateconfig: {
          altFormat: "d/m/Y",
          altInput: true,
          dateFormat: "d/m/Y",
          allowInput: true,
        },
      },
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
      if (event.keyCode === 116) {
        event.preventDefault();
        this.clearData();
      }
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
        throw new Error(response.data.message_detail);
      }
    },
    async getTSMD() {
      this.loading = true;
      try {
        const data = this.GetData(await api.lay_thamso_macdinh(this.axios));
        if (data === null || data.length === 0) {
          this.isHNI = false;
        } else {
          this.isHNI = true;
        }
      } catch (err) {
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    async getComboboxData() {
      this.loading = true;
      try {
        this.comboBox.nvThuNgan.data = this.GetData(
          await api.sp_lay_ds_thungan(this.axios, {
            loainv_id: 17,
            nhanvien_id: this.$root.token.getNhanVienID(),
          })
        );
        // eslint-disable-next-line eqeqeq
        this.comboBox.nvThuNgan.selected = this.comboBox.nvThuNgan.data.find(
          (item) => item.nhanvien_id == this.$root.token.getNhanVienID()
        );
        this.comboBox.lyDo.data = this.GetData(
          await api.sp_lay_ds_lydo_tt(this.axios, {})
        );
      } catch (err) {
        // this.getComboboxData()
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    async search() {
      if (this.maGD === "" && !this.searchDate.isEnabledFromDate) {
        this.$toast.error("Hãy nhập thông tin tìm kiếm");
        this.$refs.textBoxMaGd.focus();
      } else {
        let nhanvienId = this.$root.token.getNhanVienID();
        this.gridboxDSPTT = { data: [], selected: null };
        this.gridboxDSKM = { data: [], selected: [] };
        this.gridboxDSTB = { data: [], selected: null };
        this.loading = true;
        try {
          // const params = { nhanvien_id: 3923, ngay_bg: '01/05/2021', ngay_kt: '10/05/2021', ma_gd: 'HPG-LD/00741477' }
          const params = {
            // nhanvien_id: nhanvienId,
            ngay_bg: this.searchDate.isEnabledFromDate ? this.searchDate.fromDate : "0",
            ngay_kt: this.searchDate.isEnabledFromDate ? this.searchDate.toDate : "0",
            ma_gd: this.maGD,
          };
          const tag = this.$route.query.tag;
          if (tag == 0) {
            params.nhanvien_id = 0;
            if (!params.ma_gd.trim())
              throw new Error("Yêu cầu nhập mã giao dịch! Vui lòng kiểm tra lại!");
          } else params.nhanvien_id = nhanvienId;
          let data = this.GetData(await api.lay_ds_phieutt_thoaitra(this.axios, params));
          if (data && data.length > 0) {
            this.gridboxDSPTT.data = data.map((item) => {
              return {
                ...item,
                // ngay_yc: item.ngay_yc ? moment(item.ngay_yc).format('DD/MM/YYYY') : '',
                tongtien: Number(item.tongtien).toLocaleString("vi-VN", {
                  style: "currency",
                  currency: "VND",
                }),
              };
            });
            // this.gridboxDSPTT.selected = data[0]
          }
          // else {
          //   this.$toast.error('Không tìm thấy kết quả theo yêu cầu!')
          // }
        } catch (err) {
          console.error(err);
          this.$toast.error(err.message);
        } finally {
          this.loading = false;
        }
      }
    },
    async getDataGridboxDSTB() {
      if (this.gridboxDSPTT.selected && this.gridboxDSPTT.selected.phieutt_id) {
        this.loading = true;
        this.gridboxDSKM = { data: [], selected: [] };
        this.gridboxDSTB = { data: [], selected: null };
        try {
          let data = this.GetData(
            await api.lay_ds_hdtb_theo_phieutt(this.axios, {
              phieutt_id: this.gridboxDSPTT.selected.phieutt_id,
            })
          );
          if (data && data.length > 0) {
            this.gridboxDSTB.data = data.map((item) => {
              const ztien = Number(item.tien).toLocaleString("vi-VN", {
                style: "currency",
                currency: "VND",
              });
              return {
                ...item,
                ztien: ztien,
              };
            });
            // this.gridboxDSTB.selected = data[0]
          }
        } catch (err) {
          console.error(err);
        } finally {
          this.loading = false;
        }
      }
    },
    async getDataGridboxDSKM() {
      if (
        this.gridboxDSTB.selected &&
        this.gridboxDSTB.selected.phieutt_id &&
        this.gridboxDSTB.selected.hdtb_id
      ) {
        this.loading = true;
        this.gridboxDSKM = { data: [], selected: [] };
        try {
          let data = this.GetData(
            await api.lay_ds_khoanmuc_theo_hdtb(this.axios, {
              hdtb_id: this.gridboxDSTB.selected.hdtb_id,
              phieutt_id: this.gridboxDSTB.selected.phieutt_id,
            })
          );
          if (data && data.length > 0) {
            this.gridboxDSKM.data = data.map((item) => {
              return {
                ...item,
                tien_thu: Number(item.tien_thu).toLocaleString("vi-VN", {
                  style: "currency",
                  currency: "VND",
                }),
              };
            });
            // this.gridboxDSKM.selected = data[0]
          }
        } catch (err) {
          console.error(err);
        } finally {
          this.loading = false;
        }
      }
    },
    rowSelectedGridboxDSPTT(items) {
      if (items.length > 0) {
        this.gridboxDSPTT.selected = items[0];
      } else {
        this.gridboxDSPTT.selected = null;
      }
    },
    async rowSelectedGridboxDSTB(items) {
      if (items.length > 0) {
        this.gridboxDSTB.selected = items[0];
        this.checkedDKThoaiTra = null;
        this.loading = true;
        try {
          let checkedData = this.GetDataObj(
            await api.fn_thoaitra_kt_luong_qt(this.axios, {
              hdtb_id: this.gridboxDSTB.selected.hdtb_id,
            })
          );
          if (checkedData && checkedData.length > 0) {
            this.checkedDKThoaiTra = checkedData;
          }
        } catch (err) {
          console.error(err);
        } finally {
          this.loading = false;
        }
      }
    },
    rowSelectedGridboxDSKM(items) {
      this.gridboxDSKM.selected = items;
    },
    async thoaiTraHopDong(thoaiTraTam = 0) {
      if (
        !(
          this.gridboxDSPTT.selected &&
          this.gridboxDSTB.selected &&
          this.gridboxDSKM.data.length > 0
        )
      ) {
        this.$toast.error("Không tìm thấy hợp đồng để thoái trả!");
      }
      if (this.comboBox.lyDo.selected === null) {
        this.$toast.error("Bạn chưa nhập nguyên nhân thoái trả!");
        this.$refs.comboBoxlyDo.$el.focus();
      } else if (this.comboBox.nvThuNgan.selected === null && this.isHNI) {
        this.$toast.error("Bạn chưa chọn nhân viên thu ngân");
        this.$refs.comboBoxnvThuNgan.$el.focus();
      } else {
        this.loading = true;
        try {
          const params = {
            thoaitra_tam: thoaiTraTam,
            hdtb_id: this.gridboxDSTB.selected.hdtb_id,
            phieutt_id: this.gridboxDSTB.selected.phieutt_id,
            ct_tienhd_id: this.gridboxDSKM.data.map((item) => item.ct_tienhd_id),
            lydotontc_id: this.comboBox.lyDo.selected.lydotontc_id,
            tt_dongbo: this.gridboxDSTB.selected.status,
            dichvuvt_id: this.gridboxDSTB.selected.dichvuvt_id,
            loaihd_id: this.gridboxDSPTT.selected.loaihd_id,
            chk_kh_tiemnang: this.khachHangTiemNang ? 1 : 0,
          };
          if (this.comboBox.nvThuNgan.selected) {
            params.nv_thungan_id = this.comboBox.nvThuNgan.selected.nhanvien_id;
          }
          const response = await api.thoaitra_hopdong(this.axios, params);
          if (response.data.error === 200 || response.data.error === "200") {
            if (response.data.error_code === "BSS-00000000") {
              this.$toast.success(response.data.message);
              this.search();
            } else {
              this.$toast.error(response.data.message);
            }
          } else {
            this.$toast.error(response.data.message);
            console.log(response.data.error_code);
          }
        } catch (err) {
          console.error(err);
        } finally {
          this.loading = false;
        }
      }
    },
    ThoaiTra() {
      this.thoaiTraHopDong(0);
    },
    ThoaiTraTam() {
      this.thoaiTraHopDong(1);
    },
    PhieuChi() {
      this.$refs.popupInPhieuChi.showModal();
    },
    showPopupSearchContract() {
      this.$refs.popupSearchContract.showModal();
    },
    acceptSearchContract(item) {
      this.maGD = item.ma_gd;
      this.search();
    },
    clearData() {
      this.searchDate.fromDate = moment(new Date()).format("DD/MM/YYYY");
      this.searchDate.toDate = moment(new Date()).format("DD/MM/YYYY");
      this.gridboxDSPTT = { data: [], selected: null };
      this.gridboxDSTB = { data: [], selected: null };
      this.gridboxDSKM = { data: [], selected: [] };
      this.maGD = "";
      this.comboBox.nvThuNgan.selected = null;
      this.comboBox.lyDo.selected = null;
    },
  },
};
</script>
