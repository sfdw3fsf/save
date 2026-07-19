<template src="./DeleteUnpaidContract.html"></template>
<style src="./DeleteUnpaidContract.scss" scoped></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import SelectExt from "@/components/Controls/SelectExt";
import DataGrid from "@/components/Controls/DataGrid";
import api from "./API.js";

export default {
  components: {
    breadcrumb,
    SelectExt,
    DataGrid,
  },
  name: "DeleteUnpaidContract",
  props: {
    vloaihd_id: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      button: {
        xoa: true,
      },
      header: {
        title: "Xóa hợp đồng chưa thanh toán",
        list: [
          //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Xóa hợp đồng chưa thanh toán",
            link: { name: "Ui.cards" },
          },
        ],
      },
      loaihd_id: this.vloaihd_id,
      ds_loaihd: [],
      ds_selected: [],
      ds_HDCTTT: {
        list: [],
        header: [
          {
            fieldName: "ma_gd",
            headerText: "Mã GD",
            allowFiltering: true,
          },
          {
            fieldName: "ten_kh",
            headerText: "Tên KH",
            allowFiltering: true,
          },
          {
            fieldName: "diachi_kh",
            headerText: "Địa chỉ KH",
            allowFiltering: true,
          },
          {
            fieldName: "ngay_yc",
            headerText: "Ngày yêu cầu",
            textAlign: "right",
            allowFiltering: true,
          },
          {
            fieldName: "nguoi_cn",
            headerText: "Người CN",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
    };
  },
  async created() {
    this.loading(true);
    try {
      Promise.all([this.getDsLoaiHD()]);
      this.loading(false);
    } catch (ex) {
      this.loading(false);
    }
    this.loading(false);
  },
  computed: {
    cp_loaihd_id: {
      get() {
        return this.loaihd_id;
      },
      set(value) {
        return (this.loaihd_id = value);
      },
    },
    cp_loaihd: {
      get() {
        return this.ds_loaihd;
      },
      set(value) {
        return (this.ds_loaihd = value);
      },
    },
    cp_HDCTTT: {
      get() {
        return this.ds_HDCTTT;
      },
      set(value) {
        return (this.ds_HDCTTT = value);
      },
    },
    cp_selected: {
      get() {
        return this.ds_selected;
      },
      set(value) {
        value.length > 0 ? this.SetButton(1) : this.SetButton(0);
        return (this.ds_selected = value);
      },
    },
  },
  watch: {
    vloaihd_id(val) {
      this.loaihd_id = val;
    },
  },
  methods: {
    getDsLoaiHD: async function () {
      this.loading(true);
      try {
        var ds_loaihd = [];
        let data = this.GetData(await api.ds_loaihd(this.axios));
        data.forEach((e) => {
          var temp = {
            id: e.LOAIHD_ID,
            text: e.TEN_LOAIHD,
          };
          ds_loaihd.push(temp);
        });
        this.ds_loaihd = ds_loaihd;
        this.cp_loaihd_id = ds_loaihd[0].id;
      } catch (e) {}
      await this.getDsHDCTTT(this.cp_loaihd_id);
      this.loading(false);
    },
    getDsHDCTTT: async function (loaihd_id) {
      try {
        this.cp_HDCTTT.list = [];
        this.cp_selected = [];
        var input = {
          loaihd_id: loaihd_id,
        };
        let response = await api.ds_hdctt(this.axios, input);
        if (response.data.error === 200 || response.data.error === "200") {
          var data = response.data.data;
          this.cp_HDCTTT.list = data;
        } else if (
          response.data.error === 204 ||
          response.data.error === "204"
        ) {
          var ten_hd = this.ds_loaihd.filter((e) => e.id == loaihd_id)[0].text;
          this.ShowError(
            "Không có danh sách chưa thanh toán với loại HD/PL " + ten_hd
          );
        } else {
          // console.log(response.data.message);
          this.ShowError(
            "Có lỗi lấy danh sách chưa thanh toán " + response.data.message
          );
        }
      } catch (e) {
        this.ShowError(
          "Có lỗi lấy danh sách chưa thanh toán: [" +
            e.status +
            "] " +
            e.statusText
        );
      }
    },
    delete_HDCTT: async function (array) {
      var input = {
        hdkh_id_arr: array,
      };
      let response = await api.delete_hdctt(this.axios, input);
      if (response.data.error === 200 || response.data.error === "200") {
        this.ShowSuccess("Đã xóa hợp đồng thành công!");
      } else {
        this.ShowError("Xóa hợp đồng không thành công");
      }
    },
    selectLoaiHD: async function (value) {
      this.loading(true);
      this.SetButton(0);
      this.cp_loaihd_id = Number(value.id);
      await this.getDsHDCTTT(this.cp_loaihd_id);
      this.loading(false);
    },
    selectedItems_click: function (value) {
      this.loading(true);
      this.cp_selected = [];
      if (!value || value.length <= 0) {
        this.SetButton(0);
      } else {
        var ds_selected = [];
        value.forEach((e) => {
          ds_selected.push(Number(e.hdkh_id));
        });
        this.cp_selected = ds_selected;
      }
      this.loading(false);
    },
    handleRowClick: function (value) {
      this.loading(true);
      this.SetButton(1);
      this.loading(false);
    },
    handleButtonDelete: async function () {
      this.$bvModal
        .msgBoxConfirm("Bạn thật sự muốn xóa dữ liệu không ?", {
          title: "Xác nhận hành động",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            this.loading(true);
            await this.delete_HDCTT(this.cp_selected);
            await this.getDsHDCTTT(this.cp_loaihd_id);
            this.loading(false);
          }
        })
        .catch((e) => {
          this.ShowError(e);
          this.loading(false);
        });
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else if (response.data.error === 204 || response.data.error === "204") {
        // this.ShowError("Không có dữ liệu!");
        return [];
      } else {
        // console.log(response.data.message);
        this.ShowError(
          "Có lỗi trong quá trình lấy dữ liệu " + response.data.message
        );
        return [];
      }
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
    SetButton(kieu) {
      this.button.xoa = false;
      switch (kieu) {
        case 0: //batdau
          this.button.xoa = true;
          break;
        case 1: //Chon row
          this.button.xoa = false;
          break;
      }
    },
  },
};
</script>
