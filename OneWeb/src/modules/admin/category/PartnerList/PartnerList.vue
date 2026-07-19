<template src="./PartnerList.html"></template>
<style src="./PartnerList.scss" scoped></style>
<script>
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import KBreadCrumb from '@/components/kylq_components/mirana/KBreadCrumb.vue'
import DataGrid from "@/components/Controls/DataGrid";
import SelectExt from "@/components/Controls/SelectExt";
import api from "./API.js";

export default {
  components: {
    breadcrumb,
    KBreadCrumb,
    DataGrid,
    SelectExt,
  },
  name: "PartnerList",
  data() {
    return {
      PL_key: 0,
      button: {
        nhapmoi: true,
        ghilai: true,
        xoa: true,
        huybo: true,
      },
      header: {
        title: "Danh mục đối tác",
        list: [
          //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
          // { name: "Quản lý nhân viên", link: { name: "Ui.cards" } },
          {
            name: "Danh mục đối tác",
            link: { name: "Ui.cards" },
            active: true,
          },
        ],
      },
      page_num: 0,
      page_sz: 10,
      rowIndex: 0,
      totalRecords: 0,
      // nganhang_id: 0,
      ds_nganhang: [],
      check_new: 0,
      params: {
        doitac_id: "",
        ten_dt: "",
        diachi: "",
        ma_dt: "",
        nguoi_dd: "",
        so_dt: "",
        taikhoan: "",
        ngay_cn: moment(new Date()).format("DD/MM/y"),
        may_cn: this.$root.token.getMachine(),
        nguoi_cn: this.$root.token.getUserName(),
        ip_cn: this.$root.token.getIP(),
        nguoidung_id: "",
        nganhang_id: -1,
        ma_dt_visa: "",
        username_sub: "",
        ghichu: "",
      },
      ds_doitac: {
        list: [],
        header: [
          {
            fieldName: "ma_dt",
            headerText: "Mã đối tác",
            allowFiltering: true,
            customAttributes: { class: "col-blue" },
          },
          {
            fieldName: "ten_dt",
            headerText: "Tên đối tác",
            allowFiltering: true,
            customAttributes: { class: "bold" },
          },
          {
            fieldName: "so_dt",
            headerText: "Số điện thoại",
            allowFiltering: true,
            customAttributes: { class: "red" },
          },
          {
            fieldName: "nguoi_dd",
            headerText: "Người đại diện",
            allowFiltering: true,
          },
          {
            fieldName: "taikhoan",
            headerText: "Số tài khoản",
            allowFiltering: true,
            customAttributes: { class: "col-blue" },
          },
          {
            fieldName: "ten_nh",
            headerText: "Ngân hàng",
            allowFiltering: true,
          },
          {
            fieldName: "diachi",
            headerText: "Địa chỉ",
            allowFiltering: true,
          },
          {
            fieldName: "ma_dt_visa",
            headerText: "Mã đối tác Visa",
            allowFiltering: true,
          },
          {
            fieldName: "username_sub",
            headerText: "Username Sub",
            allowFiltering: true,
          },
          {
            fieldName: "ghichu",
            headerText: "Ghi chú",
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
      await Promise.all([
        this.getDsNganHang(),
        this.getDSDoitac(1, this.page_sz, 1),
      ]);
      this.loading(false);
    } catch (ex) {
      this.loading(false);
    }
  },
  computed: {
    cp_params: {
      get() {
        return this.params;
      },
      set(value) {
        return (this.params = value);
      },
    },
    cp_doitac: {
      get() {
        return this.ds_doitac;
      },
      set(value) {
        return (this.ds_doitac = value);
      },
    },
  },
  methods: {
    getDsNganHang: async function () {
      var ds_nganhang = [];
      let data = this.GetData(await api.ds_nganhang(this.axios));
      data.forEach((e) => {
        var temp = {
          nganhang_id: e.NGANHANG_ID,
          ten_nh: e.TEN_NH,
          ma_nh: e.MA_NH,
        };
        ds_nganhang.push(temp);
      });
      this.ds_nganhang = ds_nganhang;
    },
    getDSDoitac: async function (page_num, page_sz, total_row) {
      var input = {
        page_num: page_num,
        page_sz: page_sz,
        total_row: total_row,
      };
      let data = this.GetData(await api.ds_doitac(this.axios, input));
      this.ds_doitac.list = data.out;
      if (total_row == 1) {
        this.totalRecords =
          data.paginfo[0].record_count > 0 ? data.paginfo[0].record_count : 0;
      }
      this.page_num = data.paginfo[0].page_num;
      this.page_sz = data.paginfo[0].page_size;

      if (this.totalRecords <= 0) {
        this.SetButton(0);
      }
    },
    update_doitac: async function (input) {
      let response = await api.update_doitac(this.axios, input);
      if (response.data.error === 200 || response.data.error === "200") {
        this.ShowSuccess("Thành công!");
      } else {
        this.ShowError(response.data.message);
      }
    },
    delete_doitac: async function (input) {
      try {
        let response = await api.delete_doitac(this.axios, input);
        if (
          response.data.error_code === "BSS-00000000" &&
          (response.data.error === 200 || response.data.error === "200")
        ) {
          this.ShowSuccess("Thành công!");
        } else {
          response.data.message
            ? this.ShowError(response.data.message)
            : this.ShowError("Có lỗi trong quá trình xóa dữ liệu!");
        }
      } catch (e) {
        this.ShowError("Có lỗi trong quá trình xóa dữ liệu!");
      }
    },
    onChangeNganHang(item) {
      this.cp_params.nganhang_id = item.nganhang_id;
    },
    handleButtonSearch: function () {
      this.loading(true);
      this.rowIndex = 0;
      this.getDSDoitac(1, this.page_sz, 1);
      this.$refs.dsdoitac.$refs.pagination.gotoPage(0);
      this.loading(false);
    },
    handleButtonCreate: function () {
      this.SetButton(1);
      this.check_new = 1;
      this.cp_params = {
        doitac_id: "",
        ten_dt: "",
        diachi: "",
        ma_dt: "",
        nguoi_dd: "",
        so_dt: "",
        taikhoan: "",
        ngay_cn: moment(new Date()).format("DD/MM/y"),
        may_cn: this.$root.token.getMachine(),
        nguoi_cn: this.$root.token.getUserName(),
        ip_cn: this.$root.token.getIP(),
        nguoidung_id: "",
        nganhang_id:
          this.ds_nganhang.length > 0 ? this.ds_nganhang[0].nganhang_id : -1,
        ma_dt_visa: "",
        username_sub: "",
        ghichu: "",
      };
    },
    handleButtonSave: async function () {
      this.loading(true);
      var checked = this.validated();
      if (checked) {
        var params = [
          {
            DOITAC_ID: this.cp_params.doitac_id,
            TEN_DT: this.cp_params.ten_dt
              ? this.cp_params.ten_dt.toString().trim()
              : "",
            DIACHI: this.cp_params.diachi
              ? this.cp_params.diachi.toString().trim()
              : "",
            MA_DT: this.cp_params.ma_dt
              ? this.cp_params.ma_dt.toString().trim()
              : "",
            NGUOI_DD: this.cp_params.nguoi_dd
              ? this.cp_params.nguoi_dd.toString().trim()
              : "",
            SO_DT: this.cp_params.so_dt
              ? this.cp_params.so_dt.toString().trim()
              : "",
            TAIKHOAN: this.cp_params.taikhoan
              ? this.cp_params.taikhoan.toString().trim()
              : "",
            NGAY_CN: moment(new Date()).format("DD/MM/y"),
            MAY_CN: await this.$root.token.getMachine(),
            NGUOI_CN: await this.$root.token.getUserName(),
            IP_CN: await this.$root.token.getIP(),
            NGUOIDUNG_ID: this.cp_params.nguoidung_id,
            NGANHANG_ID: this.cp_params.nganhang_id,
            MA_DT_VISA: this.cp_params.ma_dt_visa
              ? this.cp_params.ma_dt_visa.toString().trim()
              : "",
            USERNAME_SUB: this.cp_params.username_sub
              ? this.cp_params.username_sub.toString().trim()
              : "",
            GHICHU: this.cp_params.ghichu
              ? this.cp_params.ghichu.toString().trim()
              : "",
          },
        ];
        var input = {
          check_new: this.check_new,
          data: JSON.stringify(params),
        };
        await this.update_doitac(input);

        if (this.check_new) {
          await this.getDSDoitac(this.page_num + 1, this.page_sz, 1);
          var page_num =
            (this.totalRecords - (this.totalRecords % this.page_sz)) /
            this.page_sz;
          this.$refs.dsdoitac.$refs.pagination.gotoPage(page_num);
          this.rowIndex =
            this.totalRecords % this.page_sz == 0
              ? this.page_sz - 1
              : (this.totalRecords % this.page_sz) - 1;
        } else {
          await this.getDSDoitac(this.page_num, this.page_sz, 1);
        }
      }
      this.loading(false);
    },
    handleButtonHuy: async function () {
      this.check_new = 0;
      await this.getDSDoitac(this.page_num, this.page_sz, 1);
      var temp = this.ds_doitac.list;
      temp && temp > 0 ? this.SetButton(2) : this.SetButton(0);
    },
    handleButtonDelete: async function () {
      var input = {
        id: this.cp_params.doitac_id,
      };
      this.loading(true);
      this.$bvModal
        .msgBoxConfirm("Bạn thật sự muốn xóa dữ liệu không ?", {
          title: "Xác nhận hành động",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            await this.delete_doitac(input);
            await this.getDSDoitac(this.page_num, this.page_sz, 1);
          }
        })
        .catch((e) => {
          this.ShowError(e);
          this.loading(false);
        });
      this.loading(false);
    },
    validated: function () {
      if (this.cp_params.nganhang_id == -1) {
        this.$bvModal.msgBoxConfirm("Bạn chưa chọn ngân hàng!", {
          title: "Thông báo",
          size: "m",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        });
        return false;
      }

      if (!this.cp_params.ten_dt || this.cp_params.ten_dt.length <= 0) {
        this.$bvModal.msgBoxConfirm("Bạn chưa nhập Tên đối tác!", {
          title: "Thông báo",
          size: "m",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        });
        return false;
      }
      if (this.cp_params.ten_dt && this.cp_params.ten_dt.length > 300) {
        this.$bvModal.msgBoxConfirm(
          "Tên đối tác không được lớn hơn 300 ký tự!",
          {
            title: "Thông báo",
            size: "m",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          }
        );
        return false;
      }
      if (this.cp_params.nguoi_dd && this.cp_params.nguoi_dd.length > 500) {
        this.$bvModal.msgBoxConfirm(
          "Tên người đại diện không được lớn hơn 500 ký tự!",
          {
            title: "Thông báo",
            size: "m",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          }
        );
        return false;
      }
      if (this.cp_params.diachi && this.cp_params.diachi.length > 300) {
        this.$bvModal.msgBoxConfirm("Địa chỉ không được lớn hơn 300 ký tự!", {
          title: "Thông báo",
          size: "m",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        });
        return false;
      }
      if (this.cp_params.ghichu && this.cp_params.ghichu.length > 500) {
        this.$bvModal.msgBoxConfirm("Ghi chú không được lớn hơn 500 ký tự!", {
          title: "Thông báo",
          size: "m",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        });
        return false;
      }
      if (
        this.cp_params.username_sub &&
        this.cp_params.username_sub.length > 50
      ) {
        this.$bvModal.msgBoxConfirm(
          "Username sub không được lớn hơn 50 ký tự!",
          {
            title: "Thông báo",
            size: "m",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          }
        );
        return false;
      }
      if (this.cp_params.ma_dt_visa && this.cp_params.ma_dt_visa.length > 50) {
        this.$bvModal.msgBoxConfirm("Mã ĐT Visa không được lớn hơn 50 ký tự!", {
          title: "Thông báo",
          size: "m",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        });
        return false;
      }
      if (this.cp_params.so_dt && this.cp_params.so_dt.length > 11) {
        this.$bvModal.msgBoxConfirm(
          "Số điện thoại không được lớn hơn 11 ký tự!",
          {
            title: "Thông báo",
            size: "m",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          }
        );
        return false;
      }
      if (this.cp_params.so_dt && this.cp_params.so_dt.length < 7) {
        this.$bvModal.msgBoxConfirm(
          "Số điện thoại không được nhỏ hơn 7 ký tự!",
          {
            title: "Thông báo",
            size: "m",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          }
        );
        return false;
      }
      if (this.cp_params.so_dt && !Number(this.cp_params.so_dt)) {
        this.$bvModal.msgBoxConfirm("Số điện thoại phải là dạng số!", {
          title: "Thông báo",
          size: "m",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        });
        return false;
      }
      if (this.cp_params.taikhoan && this.cp_params.taikhoan.length > 20) {
        this.$bvModal.msgBoxConfirm(
          "Số tài khoản không được lớn hơn 20 ký tự!",
          {
            title: "Thông báo",
            size: "m",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          }
        );
        return false;
      }
      return true;
    },
    selectedDoitac_click: async function (value) {
      this.check_new = 0;
      value && value.length > 0 ? this.SetButton(3) : this.SetButton(0);
      this.cp_params =
        value && value.length > 0
          ? value[0]
          : {
              doitac_id: "",
              ten_dt: "",
              diachi: "",
              ma_dt: "",
              nguoi_dd: "",
              so_dt: "",
              ngay_cn: moment(new Date()).format("DD/MM/y"),
              may_cn: await this.$root.token.getMachine(),
              nguoi_cn: await this.$root.token.getUserName(),
              ip_cn: await this.$root.token.getIP(),
              nguoidung_id: "",
              nganhang_id: -1,
              ma_dt_visa: "",
              username_sub: "",
              ghichu: "",
            };
      var index = this.ds_nganhang.findIndex(
        (x) => x.nganhang_id == this.cp_params.nganhang_id
      );
      if (index > -1) {
        this.$refs.cboNganHang.$refs.grid.setCurrentSelectedRow(index);
        this.$refs.cboNganHang.$refs.grid.flagSelectedRowIndexes = [];
        this.$refs.cboNganHang.$refs.grid.flagSelectedRowIndexes.push(index);
      }
    },
    rowSelected: function (value) {
      this.rowIndex = value.rowIndex;
    },
    pageChanged: function (value) {
      this.loading(true);
      this.getDSDoitac(value.pageIndex + 1, value.pageSize, 0);
      this.loading(false);
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else {
        // console.log(response.data.message);
        this.ShowError(response.data.message);
      }
      return [];
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
    SetButton(kieu) {
      this.button.nhapmoi = false;
      this.button.xoa = false;
      this.button.ghilai = false;
      this.button.huybo = false;
      switch (kieu) {
        case -1: //batdau
          this.button.nhapmoi = true;
          this.button.xoa = true;
          break;
        case 0: //Bat dau
          this.button.nhapmoi = true;
          break;
        case 1: //Them moi
          this.button.ghilai = true;
          this.button.huybo = true;
          break;
        case 2: //huy
          this.button.nhapmoi = true;
          this.button.xoa = true;
          break;
        case 3: //edit
          this.button.nhapmoi = true;
          this.button.xoa = true;
          this.button.ghilai = true;
          this.button.huybo = true;
          break;
      }
    },
  },
};
</script>
<style>
.col-blue {
  color: blue !important;
}
</style>