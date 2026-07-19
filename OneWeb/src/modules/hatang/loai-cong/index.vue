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
        phanLoai: 1,
        thuocNhom: 1,
        camModule: 0,
        tocDoPort: "",
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
        checBoxCamModule: {
          enabled: false,
        },
        comboboxPhanLoai: {
          enabled: false,
        },
        comboboxNhom: {
          enabled: false,
        },
        txtTocDoPort: {
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
    "currentItem.tocDoPort": function (value) {
      this.controls.txtTocDoPort.invalid = value == "" || value == null;
    },
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      await this.load();
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
    getColumnCamModuleTemplate: function () {
      return function () {
        return {
          template: {
            template: `<div class="check-action"><input type="checkbox" class="check" disabled :checked="data.CAM_MODULE == 1" /><span class="name"></span></div>`,
          },
        };
      };
    },
    load: async function () {
      this.dsPhanLoaiCong = await this.getDsPhanLoaiCong();
      this.dsNhomCong = await this.getDsNhomCong();
      this.dataItems = await this.getItems();
    },
    getDsPhanLoaiCong: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post(
          "/web-ecms/hatang/common/get-ds-phanloai-cong"
        );
        result = rs.data;
      } catch {}
      return result;
    },
    getDsNhomCong: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/common/get-ds-nhomcong");
        result = rs.data;
      } catch {}
      return result;
    },
    getItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/loai-cong/get-items");
        result = rs.data;
      } catch {}
      if (result.length > 0) {
        for (let i = 0; i < result.length; i++) {
          if (result[i].THUOCNHOM_NAME == undefined) {
            if (this.dsNhomCong.length > 0) {
              let nhom = this.dsNhomCong.find((item) => item.ID == result[i].THUOCNHOM);
              if (nhom != null) {
                result[i].THUOCNHOM_NAME = nhom.Name;
              }
            }
          }
          if (result[i].PHANLOAI_NAME == undefined) {
            if (this.dsPhanLoaiCong.length > 0) {
              let phanLoai = this.dsPhanLoaiCong.find(
                (item) => item.ID == result[i].PHANLOAI
              );
              if (phanLoai != null) {
                result[i].PHANLOAI_NAME = phanLoai.Name;
              }
            }
          }
        }
      }
      return result;
    },
    addItem: async function (args) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/loai-cong/add", {
          ten: args.ten,
          phanLoai: args.phanLoai,
          thuocNhom: args.thuocNhom,
          camModule: args.camModule,
          tocDoPort: args.tocDoPort,
          ghiChu: args.ghiChu,
          hieuLuc: args.hieuLuc,
        });
        result = rs.data.result;
      } catch (error) {
        let msg = "";
        if (!(error.response == null || error.response == undefined)) {
          let response = error.response;
          if (response.data.data == null || response.data.data == undefined) {
            msg = response.data.message_detail;
          } else {
            let keys = Object.keys(response.data.data);
            for (let i = 0; i < keys.length; i++) {
              const key = keys[i];
              if (msg != "") msg += "\n";
              msg += response.data.data[key];
            }
          }
        }
        if (msg == "") msg = "Có lỗi xảy ra khi thêm loại cổng.";
        this.$root.toastError(msg);
      }
      return result;
    },
    updateItem: async function (args) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/loai-cong/update", {
          id: args.id,
          ten: args.ten,
          phanLoai: args.phanLoai,
          thuocNhom: args.thuocNhom,
          camModule: args.camModule,
          tocDoPort: args.tocDoPort,
          ghiChu: args.ghiChu,
          hieuLuc: args.hieuLuc,
        });
        result = rs.data.result;
      } catch (error) {
        let msg = "";
        if (!(error.response == null || error.response == undefined)) {
          let response = error.response;
          if (response.data.data == null || response.data.data == undefined) {
            msg = response.data.message_detail;
          } else {
            let keys = Object.keys(response.data.data);
            for (let i = 0; i < keys.length; i++) {
              const key = keys[i];
              if (msg != "") msg += "\n";
              msg += response.data.data[key];
            }
          }
        }
        if (msg == "") msg = "Có lỗi xảy ra khi cập nhật loại cổng.";
        this.$root.toastError(msg);
      }
      return result;
    },
    deleteItem: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/loai-cong/delete", {
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
          "/web-ecms/hatang/loai-cong/delete-items",
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
      this.controls.checBoxCamModule.enabled = args;
      this.controls.comboboxPhanLoai.enabled = args;
      this.controls.comboboxNhom.enabled = args;
      this.controls.txtTocDoPort.enabled = args;
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
      this.currentItem.phanLoai = item.PHANLOAI;
      this.currentItem.thuocNhom = item.THUOCNHOM;
      this.currentItem.camModule = item.CAM_MODULE;
      this.currentItem.tocDoPort = item.TOCDO_PORT;
      this.currentItem.ghiChu = item.GHI_CHU;
      this.currentItem.hieuLuc = item.HIEU_LUC;
    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        ID: 0,
        TEN: "",
        PHANLOAI: 1,
        THUOCNHOM: 1,
        CAM_MODULE: 0,
        TOCDO_PORT: null,
        GHI_CHU: null,
        HIEU_LUC: 1,
      });
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
    validateControls: function () {
      let msg = "";
      if (this.controls.txtName.invalid) {
        msg = "Tên loại cổng không được để trống.";
      }
      if (this.controls.txtTocDoPort.invalid) {
        if (msg != "") msg += "\n\r";
        msg += "Tốc độ PORT không được để trống.";
      }
      if (msg != "") this.$root.toastError(msg);
      return msg == "";
    },
    btnGhiLai_Click: async function (args) {
      if (this.validateControls()) {
        let flag = await this.$confirm(
          "Có chắc bạn muốn lưu lại loại cổng đang chọn?",
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
                this.$root.toastSuccess("Thêm loại cổng thành công");
              }
              // else {
              //   this.$root.toastError("Thêm loại cổng thất bại");
              // }
              this.enableControls(!rs);
            } else {
              let rs = await this.updateItem(this.currentItem);
              if (rs) {
                this.dataItems = await this.getItems();
                this.$root.toastSuccess("Cập nhật loại cổng thành công");
              }
              // else {
              //   this.$root.toastError("Cập nhật loại cổng thất bại");
              // }
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
        "Có chắc bạn muốn xóa loại cổng đang chọn?",
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
            this.$root.toastSuccess("Xóa loại cổng thành công");
          } else {
            this.$root.toastError("Xóa loại cổng thất bại");
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
