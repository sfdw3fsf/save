<template src="./index.template.html"></template>

<script>
export default {
  data: function () {
    return {
      dsHT: [],
      thongTinHaTangIDG: {
        ID: 0,
        TEN: "",
        LOAIHATANG: "",
        MAHATANG_INFRA: "",
        HATANG_CONGNGHE: "",
      },
    };
  },
  computed: {},
  created: function () {},
  methods: {
    loadData: async function (id) {
      let item = await this.getThongTinHaTangIDG(id);
      this.thongTinHaTangIDG = {
        ID: 0,
        TEN: "",
        LOAIHATANG: "",
        MAHATANG_INFRA: "",
        HATANG_CONGNGHE: "",
      };
      if (item.result) {
        this.thongTinHaTangIDG = item.data;
      } else {
        this.$root.toastError(item.msg);
      }
      await this.loadDsHT();
    },
    loadDsHT: async function () {
      this.dsHT = [];
      let item = await this.getDsHaTangDaDN();
      if (item.result) {
        this.dsHT = item.data;
      } else {
        this.$root.toastError(item.msg);
      }
    },
    validateForm: function () {
      let result = {
        result: false,
        msg: "",
      };
      let selectedItems = this.$refs.gridItems.getSelectedRecords();
      if (selectedItems.length == 0) {
        result.msg = "Chưa chọn hạ tầng cần gỡ đối nối";
      } else {
        result.result = true;
      }
      return result;
    },
    getThongTinHaTangIDG: async function (haTangIDGID) {
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      try {
        let rs = await this.$root.context.post(
          "/web-ecms/hatang/sodo-logic/get-thongtin-htidg",
          {
            id: haTangIDGID,
          }
        );
        result.data = rs.data;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi lấy thông tin hạ tầng";
      }
      return result;
    },
    getDsHaTangDaDN: async function () {
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      try {
        let rs = await this.$root.context.post(
          "/web-ecms/hatang/sodo-logic/get-ds-hatang-dadn-idg",
          {
            id: this.thongTinHaTangIDG.ID,
          }
        );
        result.data = rs.data;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi lấy thông tin danh sách hạ tầng đã đấu nối";
      }
      return result;
    },
    goDauNoi: async function (items) {
      let result = {
        result: false,
        data: null,
        msg: "",
      };

      let selectedItems = this.$refs.gridItems.getSelectedRecords();
      let dataItem = [];
      if (selectedItems.length == 0) {
        result.msg = "Chưa chọn hạ tầng cần gỡ đối nối";
      } else {
        selectedItems.forEach((item) => {
          dataItem.push({ id: item.HATANG_ID, phanLoai: item.PHANLOAI });
        });
        result = await this.goDauNoiDsHTvsIDG(dataItem);
      }
      return result;
    },
    goDauNoiDsHTvsIDG: async function (items) {
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      try {
        let rs = await this.$root.context.post(
          "/web-ecms/hatang/sodo-logic/go-daunoi-htidg-htah-htcloud",
          {
            haTangIDGID: this.thongTinHaTangIDG.ID,
            dsHT: items,
          }
        );
        result.data = rs.data.result;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi gỡ đối nối hạ tầng IDG";
      }
      return result;
    },
  },
};
</script>
<style scoped>
.go-daunoi .info-row div.key label {
  padding-top: 3px;
}
.go-daunoi .info-row span.form-control {
  line-height: 28px;
  color: #000;
}
</style>
