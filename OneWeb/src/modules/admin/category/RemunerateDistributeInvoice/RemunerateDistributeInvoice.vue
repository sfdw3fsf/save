<template src="./RemunerateDistributeInvoice.html"></template>
<style src="./RemunerateDistributeInvoice.scss" scoped></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import api from "./API.js";
import DataGrid from "@/components/Controls/DataGrid";
import KyHoaDon from "./KyHoaDon";
import LoaiNhanVien from "./LoaiNhanVien";
import KhuVuc from "./KhuVuc";

export default {
  components: {
    breadcrumb,
    DataGrid,
    KyHoaDon,
    LoaiNhanVien,
    KhuVuc,
  },
  name: "RemunerateDistributeInvoice",
  data() {
    return {
      header: {
        title: "Thù lao phát hóa đơn chuyển khoản",
        list: [
          //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: "Quản lý thu nợ", link: { name: "Ui.cards" } },
          { name: "Tham số tính lương đại lý", link: { name: "Ui.cards" } },
          {
            name: "Thù lao phát hóa đơn chuyển khoản",
            link: { name: "Ui.cards" },
            active: true,
          },
        ],
      },
      button: {
        nhapmoi: true,
        ghilai: true,
        xoa: true,
        huybo: true,
      },
      pagenumber: 0,
      pagesize: 10,
      totalRecords: 0,
      thulao_ck_id: 0,
      thulaock: {
        THULAO_CK: "",
        DONGIA: 0,
      },
      thulaock_khd: [],
      thulaock_lnv: [],
      thulaock_kv: [],
      ds_thulao_phd: {
        list: [],
        header: [
          {
            fieldName: "stt",
            headerText: "STT",
            allowFiltering: true,
            width: 80,
          },
          {
            fieldName: "thulao_ck",
            headerText: "Nội dung",
            allowFiltering: true,
          },
          {
            fieldName: "dongia",
            headerText: "Đơn giá",
            allowFiltering: true,
            type: "Number",
            format: "N0",
            width: 100,
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
      Promise.all([this.get_ds_thulao_phathd(1, this.pagesize, 1)]);
      this.loading(false);
    } catch (ex) {
      this.loading(false);
    }
    this.loading(false);
  },
  computed: {
    cp_thulao_phd: {
      get() {
        return this.ds_thulao_phd;
      },
      set(value) {
        return (this.ds_thulao_phd = value);
      },
    },
    cp_thulaock_id: {
      get() {
        return this.thulao_ck_id;
      },
      set(value) {
        return (this.thulao_ck_id = value);
      },
    },
    cp_thulaock: {
      get() {
        return this.thulaock;
      },
      set(value) {
        return (this.thulaock = value);
      },
    },
  },
  methods: {
    get_ds_thulao_phathd: async function (pagenumber, pagesize, total_row) {
      var input = {
        page_num: pagenumber,
        page_sz: pagesize,
        total_row: total_row,
      };
      let data = this.GetData(await api.ds_thulao_phathd(this.axios, input));
      this.ds_thulao_phd.list = data.out;
      if (total_row == 1) {
        this.totalRecords =
          data.pageInfo[0].record_count > 0 ? data.pageInfo[0].record_count : 0;
      }
      if (this.totalRecords <= 0) {
        this.SetButton(0);
      }
    },
    update_thulao_phd: async function (input) {
      let response = await api.thulao_phathd_update(this.axios, input);
      if (response.data.error === 200 || response.data.error === "200") {
        this.ShowSuccess("Thành công!");
      } else {
        this.ShowError(response.data.message);
      }
    },
    delete_thulao_phd: async function () {
      var input = {
        thulao_ck_id: this.thulao_ck_id,
      };
      let response = await api.thulao_phathd_delete(this.axios, input);
      if (response.data.error === 200 || response.data.error === "200") {
        this.ShowSuccess("Thành công!");
      } else {
        this.ShowError(response.data.message);
      }
    },
    handleButtonCreate: function () {
      this.SetButton(1);
      this.thulao_ck_id = 0;
      this.thulaock_khd = [];
      this.thulaock_lnv = [];
      this.thulaock_kv = [];
      var param = {
        THULAO_CK: "",
        DONGIA: 0,
      };
      this.thulaock = param;
    },
    handleButtonHuy: async function () {
      this.loading(true);
      var thulaock = {
        THULAO_CK: "",
        DONGIA: 0,
      };
      var params = this.ds_thulao_phd.list;
      this.ds_thulao_phd.list = await [];
      this.ds_thulao_phd.list = await params;
      this.thulao_ck_id =
        params && params.length > 0 ? params[0].thulao_ck_id : 0;
      thulaock.THULAO_CK =
        params && params.length > 0 ? params[0].thulao_ck : "";
      thulaock.DONGIA = params && params.length > 0 ? params[0].dongia : 0;
      this.thulaock = thulaock;
      params && params.length > 0 ? this.SetButton(2) : this.SetButton(0);
      this.loading(false);
    },
    handleButtonSave: async function () {
      this.loading(true);
      try {
        this.thulaock.DONGIA = Number(this.thulaock.DONGIA);
        var checked = this.validated();
        if (checked) {
          var input = {
            thulao_ck_id: this.thulao_ck_id,
            json_thulaock: [this.thulaock],
            json_thulaock_kv: this.thulaock_kv,
            json_thulaock_lnv: this.thulaock_lnv,
            json_thulaock_khd: this.thulaock_khd,
          };
          await this.update_thulao_phd(input);
          await this.get_ds_thulao_phathd(1, this.pagesize, 1);
        }
        this.loading(false);
      } catch (e) {
        this.loading(false);
        this.ShowError("Có lỗi: " + e.data.message_detail);
      }
    },
    handleButtonDelete: async function () {
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
            await this.delete_thulao_phd();
            await this.get_ds_thulao_phathd(1, this.pagesize, 1);
          }
        })
        .catch((e) => {
          this.ShowError("Có lỗi: " + e.data.message_detail);
          this.loading(false);
        });
      this.loading(false);
    },
    validated: function () {
      if (!this.thulaock.THULAO_CK || this.thulaock.THULAO_CK == "") {
        this.$bvModal
          .msgBoxConfirm(
            'Bạn chưa nhập "Nội dung thù lao phát hóa đơn chuyển khoản"!',
            {
              title: "Thông báo",
              size: "m",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then(async (v) => {
            if (v) {
              this.$refs.noidung.focus();
            }
          })
          .catch((e) => {
            this.$refs.noidung.focus();
          });
        return false;
      }
      if (
        this.thulaock.THULAO_CK &&
        this.thulaock.THULAO_CK.trim().length > 400
      ) {
        this.$bvModal
          .msgBoxConfirm(
            "Nội dung thù lao phát hóa đơn chuyển khoản không dược quá 400 ký tự!",
            {
              title: "Thông báo",
              size: "m",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then(async (v) => {
            if (v) {
              this.$refs.noidung.focus();
            }
          })
          .catch((e) => {
            this.$refs.noidung.focus();
          });
        return false;
      }

      if (!this.thulaock.DONGIA || this.thulaock.DONGIA <= 0) {
        this.$bvModal
          .msgBoxConfirm('"Đơn giá" phải lớn hơn 0!', {
            title: "Thông báo",
            size: "sm",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then(async (v) => {
            if (v) {
              this.$refs.dongia.focus();
            }
          })
          .catch((e) => {
            this.$refs.dongia.focus();
          });

        return false;
      }
      if (this.thulaock.DONGIA && this.thulaock.DONGIA > 999999999999) {
        this.$bvModal
          .msgBoxConfirm('"Đơn giá" không được lớn hơn 999,999,999,999!', {
            title: "Thông báo",
            size: "sm",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then(async (v) => {
            if (v) {
              this.$refs.dongia.focus();
            }
          })
          .catch((e) => {
            this.$refs.dongia.focus();
          });

        return false;
      }

      if (!this.thulaock_khd || this.thulaock_khd.length <= 0) {
        this.$bvModal.msgBoxConfirm("Bạn chưa gán kỳ hóa đơn!", {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        });
        return false;
      }
      if (!this.thulaock_lnv || this.thulaock_lnv.length <= 0) {
        this.$bvModal.msgBoxConfirm("Bạn chưa gán loại nhân viên!", {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        });
        return false;
      }
      if (!this.thulaock_kv || this.thulaock_kv.length <= 0) {
        this.$bvModal.msgBoxConfirm("Bạn chưa gán khu vực!", {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        });
        return false;
      }

      return true;
    },
    pageChanged: function (value) {
      this.get_ds_thulao_phathd(value.pageIndex + 1, value.pageSize, 0);
    },
    selectedHoaDon_click: function (value) {
      value && value.length > 0 ? this.SetButton(3) : this.SetButton(0);
      this.thulao_ck_id = value && value.length > 0 ? value[0].thulao_ck_id : 0;
      var param = {
        THULAO_CK: "",
        DONGIA: 0,
      };
      param.THULAO_CK = value && value.length > 0 ? value[0].thulao_ck : "";
      param.DONGIA = value && value.length > 0 ? value[0].dongia : 0;
      this.thulaock = param;
    },
    getKyHoaDon: function (value) {
      this.thulaock_khd = value;
    },
    getLoaiNhanVien: function (value) {
      this.thulaock_lnv = value;
    },
    getKhuVuc: function (value) {
      this.thulaock_kv = value;
    },
    GetData: function (response) {
      if (
        response.data.error === 200 ||
        response.data.error === "200" ||
        response.data.error === 204 ||
        response.data.error === "204"
      ) {
        return response.data.data;
      } else {
        // console.log(response.data.error_code);
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
