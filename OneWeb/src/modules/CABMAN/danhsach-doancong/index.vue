<template src="./index.template.html"></template>
<script>
import ModalQLDoanCong from "../BanDoTuyenCong/QLDoanCongVaCacOngCongNamTrongDoanCong/Modal";
export default {
  components: {
    ModalQLDoanCong,
  },
  data: function () {
    return {
      dsTTVT: [],
      selectedTTVT: 0,
      dsToKT: [],
      selectedToKT: 0,
      dsTuyenCong: [],
      selectedTuyenCong: 0,
      inputTuyenCongChecked: true,
      kqTraCuu: [],
      doanCongItem: {},
      noiDungTimKiem: ""
    };
  },
  created: async function () {
    this.$root.showLoading(true);
    try {
      this.dsTTVT = await this.getDsTTVT();
      if (!(this.dsTTVT == null || this.dsTTVT.length == 0)) {
        this.selectedTTVT = this.dsTTVT[0].DONVI_ID;
        this.dsToKT = await this.getDsToKT();
      }
    } catch (error) {
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    getDsTTVT: async function () {
      let rs = await this.$root.context.post(
        "/web-ecms/danhmuc/layDsTTVTTrongToken"
      );
      return rs.data;
    },
    getDsToKT: async function () {
      let rs = await this.$root.context.post("/web-ecms/danhmuc/layDsToKT", {
        id: this.selectedTTVT,
      });
      return rs.data;
    },
    getDsTuyenCong: async function () {
      let rs = await this.$root.context.post(
        "/web-cabman/danhmuc/layDsTuyenCongTheoToQL",
        {
          toQLID: this.selectedToKT,
          loaiTuyen: 1,
        }
      );
      return rs.data;
    },
    getDsDoanCong: async function () {
      let tuyenCot = null;
      if (this.inputTuyenCongChecked) {
        if (this.selectedTuyenCong > 0) tuyenCot = this.selectedTuyenCong;
      }
      let q = $("#inputTimKiem").val().toString().trim();
      this.noiDungTimKiem = "";
      if(q != ""){
        this.noiDungTimKiem = "thỏa mãn điều kiện tìm kiếm";
      }
      else {
        if(tuyenCot == null){
          this.noiDungTimKiem = "chưa xác định tuyến";
        }
        else {
          let t = this.dsTuyenCong.find(x => x.TUYENCB_ID == tuyenCot);
          if(t != null){
            this.noiDungTimKiem = "thuộc tuyến: " + t.TUYEN_CB;
          }
        }
      }
      let rs = await this.$root.context.post(
        "/web-cabman/danhsachdoancong/lietKeDanhSachDoanCong",
        {
          tuyencot_id: tuyenCot,
          toql_id: this.selectedToKT > 0 ? this.selectedToKT : null,
          query: q,
        }
      );
      return rs.data;
    },
    selectTTVT_OnChange: async function () {
      this.$root.showLoading(true);
      try {
        this.selectedToKT = 0;
        this.dsToKT = await this.getDsToKT();
        this.selectedTuyenCong = 0;
        this.dsTuyenCong = [];
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    selectToKT_OnChange: async function () {
      this.$root.showLoading(true);
      try {
        this.selectedTuyenCong = 0;
        this.dsTuyenCong = await this.getDsTuyenCong();
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    btnTraCuu_onClick: async function () {
      this.$root.showLoading(true);
      try {
        if(this.selectedToKT > 0){
          this.kqTraCuu = await this.getDsDoanCong();
        }
        else {
          this.$root.toastError("Vui lòng nhập thông tin tra cứu");
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    grid_onCommandClicked: function (name, dataItem) {
      this.doanCongItem = {
        doanCongId: dataItem.DOANCONG_ID,
        tuyenCongId: dataItem.TUYENCB_ID,
        dvql: {
          id: dataItem.DONVI_ID,
          name: dataItem.TEN_DV
        },
      };
      this.$bvModal.show("popupQLDoanCong");
    },
  },
};
</script>
