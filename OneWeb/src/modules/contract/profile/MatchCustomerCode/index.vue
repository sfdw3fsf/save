<template src="./template.html"></template>
<style src="./style.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import api from "./api";
import enums from "./enums";
import dummy from "./dummy";
// import HandoverTicket_KSTHAPI from './HandoverTicket_KSTHAPI'
import SearchAccount from "@/modules/search/subscriber/SearchAccount/SearchAccount.vue";
import LichSuGhepModal from "./LichSuGhepModal";
const defaultKH = {
  maKH: "",
  ngayLHD: null,
  tenKH: null,
  diaChiKH: null,
  idKH: null,
  gridDSTB: {
    list: [],
    selected: [],
  },
  gridDSHD: {
    list: [],
    selected: [],
  },
};

export default {
  components: { breadcrumb, SearchAccount, LichSuGhepModal },
  name: "MatchCustomerCode",
  props: {
    isPopup: {
      type: Boolean,
    },
  },
  mounted() {
    this.clearData();
    this.$refs.maKHGocInput.focus();
    window.addEventListener("keydown", this.onF5KeyDown);
  },
  destroyed() {},
  computed: {
    enableSave() {
      return (
        this.KHGoc.idKH !== null &&
        this.KHGhep.gridDSHD.list.length > 0 &&
        this.KHGhep.gridDSHD.selected.length > 0
      );
    },
  },
  watch: {
    loading: function (val, oldval) {
      this.$root.showLoading(val);
    },
    lichSuGhepModalVisible: function (val, oldval) {
      if (val) {
        window.removeEventListener("keydown", this.onF5KeyDown);
      } else {
        window.addEventListener("keydown", this.onF5KeyDown);
      }
    },
  },
  data() {
    return {
      loading: false,
      header: {
        title: "GHÉP MÃ KHÁCH HÀNG",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          { name: "Lắp đặt mới", link: { name: "Ui.cards" } },
        ],
      },
      KHGoc: Object.assign({}, defaultKH),
      KHGhep: Object.assign({}, defaultKH),
      headerGridboxDSTBKHGoc: enums.headerGridboxDSTBKHGoc,
      headerGridboxDSTBKHGhep: enums.headerGridboxDSTBKHGhep,
      headerGridboxDSHDKHGhep: enums.headerGridboxDSHDKHGhep,
      lichSuGhepModalVisible: false,
    };
  },
  methods: {
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
    showLichSuGhepModal() {
      this.lichSuGhepModalVisible = true;
    },
    searchContractKHGoc() {
      // open modal search contract UR2.8.002
      this.$refs.searchAccountModalKHGoc.show();
    },
    async searchContractDetailsKHGoc() {
      if (this.KHGoc.maKH !== "") {
        this.loading = true;
        try {
          this.KHGoc.ngayLHD = null;
          this.KHGoc.tenKH = null;
          this.KHGoc.diaChiKH = null;
          this.KHGoc.gridDSTB = { list: [], value: {} };
          this.KHGoc.idKH = null;
          let data = this.GetData(
            await api.getTTKhachHang(this.axios, { ma_kh: this.KHGoc.maKH, type: 0 })
          );
          if (data && data.out_khachhang_id) {
            this.KHGoc.ngayLHD = moment(
              data.out_ngay_lap_hd,
              "DD/MM/YYYY HH:mm:ss"
            ).format("DD/MM/YYYY");
            this.KHGoc.tenKH = data.out_ten_kh;
            this.KHGoc.diaChiKH = data.out_dchi_kh;
            this.KHGoc.idKH = data.out_khachhang_id;
            // this.KHGoc.gridDSTB.list = dummy.gridboxDSTBKHGoc
            if (data.data) this.KHGoc.gridDSTB.list = data.data;
          } else {
            this.$toast.error(`Không tìm thấy khách hàng có mã ${this.KHGoc.maKH}`);
          }
        } catch (err) {
          console.error(err);
        } finally {
          this.loading = false;
        }
      } else {
        this.$toast.error("Bạn chưa nhập mã khách hàng !");
      }
    },
    searchAccountCloseKHGoc(value) {
      if (value && value.ma_kh && value.ma_kh.toString().trim() !== "") {
        this.KHGoc.maKH = value.ma_kh.toString().trim();
        this.searchContractDetailsKHGoc();
      }
      // else {
      //   this.$toast.warning('Đối tượng chọn không chứa thông tin Mã KH')
      // }
      this.$refs.searchAccountModalKHGoc.hide();
    },
    grdChangedDSTBKHGoc(obj) {
      this.KHGoc.gridDSTB.selected = obj;
    },
    searchContractKHGhep: function () {
      // open modal search contract UR2.8.002
      this.$refs.searchAccountModalKHGhep.show();
    },
    async searchContractDetailsKHGhep() {
      if (this.KHGhep.maKH !== "") {
        this.loading = true;
        try {
          this.KHGhep.ngayLHD = null;
          this.KHGhep.tenKH = null;
          this.KHGhep.diaChiKH = null;
          this.KHGhep.gridDSHD = { list: [], selected: [] };
          let data = this.GetData(
            await api.getTTKhachHang(this.axios, { ma_kh: this.KHGhep.maKH, type: 1 })
          );
          if (data && data.out_khachhang_id) {
            this.KHGhep.ngayLHD = moment(
              data.out_ngay_lap_hd,
              "DD/MM/YYYY HH:mm:ss"
            ).format("DD/MM/YYYY");
            this.KHGhep.tenKH = data.out_ten_kh;
            this.KHGhep.diaChiKH = data.out_dchi_kh;
            const gridDSHDData = data.data;
            // const gridDSHDData = dummy.gridboxDSHDKHGhep
            if (data.data) this.KHGhep.gridDSHD.list = data.data;
            // this.getDSTBKHGhep()
          } else {
            this.$toast.error(`Không tìm thấy khách hàng có mã ${this.KHGhep.maKH}`);
          }
        } catch (err) {
          console.error(err);
        } finally {
          this.loading = false;
        }
      } else {
        this.$toast.error("Bạn chưa nhập mã khách hàng !");
      }
    },
    searchAccountCloseKHGhep(value) {
      if (value && value.ma_kh && value.ma_kh.toString().trim() !== "") {
        this.KHGhep.maKH = value.ma_kh.toString().trim();
        this.searchContractDetailsKHGhep();
      }
      // else {
      //   this.$toast.warning("Đối tượng chọn không chứa thông tin Mã KH");
      // }
      this.$refs.searchAccountModalKHGhep.hide();
    },
    async getDSTBKHGhep() {
      this.KHGhep.gridDSTB = { list: [], selected: [] };
      if (
        this.KHGhep.gridDSHD.list.length > 0 &&
        this.KHGhep.gridDSHD.selected.length > 0
      ) {
        this.loading = true;
        try {
          for (const item of this.KHGhep.gridDSHD.selected) {
            let data = this.GetData(
              await api.getDSThueBao(this.axios, { thanhtoan_id: item.thanhtoan_id })
            );
            this.KHGhep.gridDSTB.list = this.KHGhep.gridDSTB.list.concat(data);
          }
        } catch (err) {
          console.error(err);
        } finally {
          this.loading = false;
        }
      }
    },
    grdChangedDSTBKHGhep(obj) {
      this.KHGhep.gridDSTB.selected = obj;
    },

    grdChangedDSHDKHGhep(selectedKey) {
      this.KHGhep.gridDSHD.selected = selectedKey;
      this.getDSTBKHGhep();
    },
    clearData() {
      this.KHGoc = { ...defaultKH, maKH: this.KHGoc.maKH };
      this.KHGhep = { ...defaultKH };

      this.KHGoc.ngayLHD = moment(new Date()).format("DD/MM/YYYY");
      this.KHGhep.ngayLHD = moment(new Date()).format("DD/MM/YYYY");
    },
    async okGhepMa() {
      const thanhtoanIdArr = this.KHGhep.gridDSHD.selected.map(
        (item) => item.thanhtoan_id
      );
      const params = {
        khachhang_goc_id: this.KHGoc.idKH,
        thanhtoan_id_arr: thanhtoanIdArr,
        nguoi_cn: this.$root.token.getUserName(),
        may_cn: await this.$root.token.getMachine(),
        ip_cn: await this.$root.token.getIP(),
      };
      this.loading = true;
      try {
        let response = await api.submitGMKH(this.axios, params);
        // console.log(response)
        if (
          response.data.error === 200 ||
          (response.data.error === "200" && response.data.error_code === "BSS-00000000")
        ) {
          this.$toast.info("Ghép mã khách hàng thành công!");
        } else {
          console.log(response.data.error_code);
          this.$toast.error("Ghép mã khách hàng lỗi!");
        }
      } catch (err) {
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    showGhepMaModal() {
      try {
        // const $this = this
        this.$bvModal
          .msgBoxConfirm("Bạn thực sự muốn ghép mã khách hàng không?", {
            title: "Thông báo",
            size: "sm",
            okTitle: "Yes",
            cancelTitle: "No",
          })
          .then(async (yes) => {
            if (yes) {
              this.okGhepMa();
            }
          });
      } catch (ex) {
        this.loading = false;
        this.$toast.warning("Có lỗi xảy ra trong quá trình ghép mã");
      }
    },
  },
};
</script>
