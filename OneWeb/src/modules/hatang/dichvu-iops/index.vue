<template src="./index.template.html"></template>

<script>
export default {
  data: function () {
    return {
      dataItems: [],
      dsPhanLoaiCong: [],
      dsNhomCong: [],
      currentItem: {
        id: 0,
        ten: "",
        giaTri: 0,
        ghiChu: null,
        hieuLuc: 1,
      },
      controls: {
        txtName: {
          enabled: false,
          invalid: false,
        },
        checBoxHieuLuc: {
          enabled: false,
        },
        txtGiaTri: {
          enabled: false,
          invalid: false,
        },
        txtGhiChu: {
          enabled: false,
        },
      },
      buttons: {
        btnNhapMoi: {
          enabled: true,
        },
        btnGhiLai: {
          enabled: false,
        },
        btnHuyBo: {
          enabled: false,
        },
        btnSua: {
          enabled: false,
        },
        btnXoa: {
          enabled: false,
        },
      },
    };
  },
  computed: {
    gridItems: function () {
      return this.$refs.gridItems;
    },
  },
  watch: {
    "currentItem.id": function (value) {
      this.buttons.btnSua.enabled = value > 0;
      this.buttons.btnXoa.enabled = value > 0;
    },
    "currentItem.ten": function (value) {
      this.controls.txtName.invalid = value == "";
    },
    "currentItem.giaTri": function (value) {
      this.controls.txtGiaTri.invalid = value == "" || value == null;
    },
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.dataItems = await this.getItems();
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    getHieuLucText: function (value) {
      return value == 1 ? "Hiệu lực" : "Không hiệu lực";
    },
    getColumnHieuLucTemplate: function () {
      return function () {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function () {
                return this.$parent.$parent.$parent.getHieuLucText(this.data.HIEU_LUC);
              },
            },
          },
        };
      };
    },
    getItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/dichvu-iops/get-items");
        result = rs.data;
      } catch {}
      return result;
    },
    addItem: async function (args) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/dichvu-iops/add", {
          ten: args.ten,
          giaTri: args.giaTri,
          ghiChu: args.ghiChu,
          hieuLuc: args.hieuLuc,
        });
        result = rs.data.result;
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    updateItem: async function (args) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/dichvu-iops/update", {
          id: args.id,
          ten: args.ten,
          giaTri: args.giaTri,
          ghiChu: args.ghiChu,
          hieuLuc: args.hieuLuc,
        });
        result = rs.data.result;
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    deleteItem: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/dichvu-iops/delete", {
          id: id,
        });
        result = rs.data.result;
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    deleteItems: async function (ids) {
      let result = false;
      try {
        let rs = await this.$root.context.post(
          "/web-ecms/hatang/dichvu-iops/delete-items",
          {
            id: ids,
          }
        );
        result = rs.data.result;
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    enableControls: function (args) {
      this.controls.txtName.enabled = args;
      this.controls.checBoxHieuLuc.enabled = args;
      this.controls.txtGiaTri.enabled = args;
      this.controls.txtGhiChu.enabled = args;

      this.buttons.btnGhiLai.enabled = args;
      this.buttons.btnNhapMoi.enabled = !args;
      this.buttons.btnHuyBo.enabled = this.buttons.btnGhiLai.enabled;
      if (this.currentItem.id > 0)
        this.buttons.btnSua.enabled = !this.buttons.btnGhiLai.enabled;
    },
    setCurrentItem: function (item) {
      this.currentItem.id = item.ID;
      this.currentItem.ten = item.TEN;
      this.currentItem.giaTri = item.GIATRI;
      this.currentItem.ghiChu = item.GHI_CHU;
      this.currentItem.hieuLuc = item.HIEU_LUC;
    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        ID: 0,
        TEN: "",
        GIATRI: null,
        GHI_CHU: null,
        HIEU_LUC: 1,
      });
    },
    validateControls: function () {
      let msg = "";
      if (this.controls.txtName.invalid) {
        msg = "Tên dịch vụ IPOS không được để trống.";
      }
      if (this.controls.txtGiaTri.invalid) {
        if (msg != "") msg += "\n\r";
        msg += "Giá trị không được để trống.";
      }
      if (msg != "") this.$root.toastError(msg);
      return msg == "";
    },
    gridItems_RowSelected: async function (args) {
      this.setCurrentItem(args);
    },
    gridItems_RowDeselected: async function (args) {
      let items = this.gridItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        this.setCurrentItem(items[0]);
      } else {
        this.setCurrentItemDefault();
      }
    },
    btnGhiLai_Click: async function (args) {
      if (this.validateControls()) {
        let flag = await this.$confirm(
          "Có chắc bạn muốn lưu lại dịch vụ IPOS đang chọn?",
          "Xác nhận thực hiện",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "info",
            dangerouslyUseHTMLString: true,
          }
        )
          .then((s) => true)
          .catch((e) => {
            return false;
          });
        if (flag) {
          try {
            this.$root.showLoading(true);
            if (this.currentItem.id == 0) {
              let rs = await this.addItem(this.currentItem);
              if (rs) {
                this.dataItems = await this.getItems();
                this.$root.toastSuccess("Thêm dịch vụ IPOS thành công");
              } else {
                this.$root.toastError("Thêm dịch vụ IPOS thất bại");
              }
              this.enableControls(!rs);
            } else {
              let rs = await this.updateItem(this.currentItem);
              if (rs) {
                this.dataItems = await this.getItems();
                this.$root.toastSuccess("Cập nhật dịch vụ IPOS thành công");
              } else {
                this.$root.toastError("Cập nhật dịch vụ IPOS thất bại");
              }
              this.enableControls(!rs);
            }
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    btnNhapMoi_Click: async function (args) {
      this.enableControls(true);
      this.setCurrentItemDefault();
    },
    btnHuyBo_Click: async function (args) {
      let items = this.gridItems.getSelectedRecords();
      let item = null;
      if (!(items == null || items.length == 0)) {
        if (this.currentItem.id == 0) {
          item = items[0];
        } else {
          item = items.find((x) => x.ID == this.currentItem.id);
        }
      }
      if (item != null) {
        this.setCurrentItem(item);
      } else {
        this.setCurrentItemDefault();
      }
      this.enableControls(false);
    },
    btnSua_Click: async function (args) {
      this.enableControls(true);
    },
    btnXoa_Click: async function (args) {
      let flag = await this.$confirm(
        "Có chắc bạn muốn xóa dịch vụ IPOS đang chọn?",
        "Xác nhận thực hiện",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
          dangerouslyUseHTMLString: true,
        }
      )
        .then((s) => true)
        .catch((e) => {
          return false;
        });
      if (flag) {
        try {
          this.$root.showLoading(true);
          let rs = false;
          let items = this.gridItems.getSelectedRecords();
          if (!(items == null || items == undefined)) {
            let ids = [];
            items.forEach((item) => {
              ids.push(item.ID);
            });
            rs = await this.deleteItems(ids);
          }
          // else {
          //   let id = this.currentItem.id;
          //   if (items != null) {
          //     if (items.length == 0)
          //       id = items[0].ID;
          //   }
          //   rs = await this.deleteItem(id);
          // }
          if (rs) {
            this.setCurrentItemDefault();
            this.dataItems = await this.getItems();
            this.enableControls(false);
            this.$root.toastSuccess("Xóa dịch vụ IPOS thành công");
          } else {
            this.$root.toastError("Xóa dịch vụ IPOS thất bại");
          }
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
  },
};
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type="checkbox"]:checked:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type="checkbox"]:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
}
</style>
