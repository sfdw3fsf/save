<template src="./index.template.html"></template>

<script>
export default {
  data: function () {
    return {
      dataItems: [],
      dataDelete: [],
      currentItem: {
        id: 0,
        ten: "",
        bangthong: null,
        ghiChu: null,
        hieuLuc: 1,
      },
      currentIdUpdate: 0,
      lengthGrid: 0,
      controls: {
        txtName: {
          enabled: false,
          invalid: false,
        },
        checBoxHieuLuc: {
          enabled: false,
        },
        txtBangThong: {
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
      if (this.buttons.btnGhiLai.enabled) {
        this.controls.txtName.invalid = value == "";
      } else {
        this.controls.txtName.invalid = false;
      }
    },
    "currentItem.bangThong": function (value) {
      if (this.buttons.btnGhiLai.enabled) {
        this.controls.txtBangThong.invalid = value == "";
      } else {
        this.controls.txtBangThong.invalid = false;
      }
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
        let rs = await this.$root.context.post("/web-ecms/hatang/bangthong-cong/get-items");
        result = rs.data;
        this.lengthGrid = result.length;

        setTimeout(() => {
          if (this.currentIdUpdate > 0) {
            const pageSize = this.$refs.gridItems.pageSize
            this.$refs.gridItems.selectRow(this.dataItems.findIndex(x => x.ID == this.currentIdUpdate) % pageSize)
            this.currentIdUpdate = 0
          }
        }, 100)
      } catch { }
      return result;
    },

    addItem: async function (args) {
      let result = 0;
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/bangthong-cong/add", {
          ten: args.ten?.trim(),
          bangThong: args.bangThong,
          ghiChu: args.ghiChu?.trim(),
          hieuLuc: args.hieuLuc,
        });
        result = rs.data.result;
      } catch { }
      return result;
    },

    updateItem: async function (args) {
      let result = 0;
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/bangthong-cong/update", {
          id: args.id,
          ten: args.ten?.trim(),
          bangThong: args.bangThong,
          ghiChu: args.ghiChu?.trim(),
          hieuLuc: args.hieuLuc,
        });
        result = rs.data.result;
      } catch { }
      return result;
    },

    deleteItem: async function (ids) {
      let result = 0;
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/bangthong-cong/delete", {
          ids: ids,
        });
        result = rs.data.result;
      } catch { }
      return result;
    },

    enableControls: function (args) {
      this.controls.txtName.enabled = args;
      this.controls.checBoxHieuLuc.enabled = args;
      this.controls.txtBangThong.enabled = args;
      this.controls.txtGhiChu.enabled = args;

      this.buttons.btnGhiLai.enabled = args;
      this.buttons.btnNhapMoi.enabled = !args;
      this.buttons.btnHuyBo.enabled = this.buttons.btnGhiLai.enabled;
      if (this.currentItem.id > 0)
        this.buttons.btnSua.enabled = !this.buttons.btnGhiLai.enabled;
    },

    validateControl: function () {
      let msg = "";
      if (this.controls.txtName.invalid) {
        msg = "Tên danh mục băng thông cổng không được để trống.";
      }
      if (this.controls.txtBangThong.invalid) {
        if (msg != "")
          msg += "\n\r";
        msg += "Băng thông không được để trống.";
      }
      if (msg != "")
        this.$root.toastError(msg);
      return msg == "";
    },

    setCurrentItem: function (item) {
      this.currentItem.id = item.ID;
      this.currentItem.ten = item.TEN;
      this.currentItem.bangThong = item.BANGTHONG;
      this.currentItem.ghiChu = item.GHI_CHU;
      this.currentItem.hieuLuc = item.HIEU_LUC;
    },

    setCurrentItemDefault: function () {
      this.setCurrentItem({
        ID: 0,
        TEN: "",
        BANGTHONG: 0,
        GHI_CHU: null,
        HIEU_LUC: 1,
      });
    },

    gridItems_RowSelected: async function (args) {
      let ds = this.$refs.gridItems.getSelectedRecords();
      this.dataDelete = ds.map(item => item.ID);

      if (args.data.length = 1) {
        this.setCurrentItem(args.data);
      }
      if (Number(args.row?.length || 0) == Number(this.lengthGrid)) {
        this.setCurrentItem(ds[0]);
      }
    },

    gridItems_RowDeselected: async function (args) {
      let items = this.gridItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        this.setCurrentItem(items[0]);
        this.dataDelete = items.map(item => item.ID);
      } else {
        this.setCurrentItemDefault();
        this.dataDelete = [];
      }
    },

    btnGhiLai_Click: async function (args) {
      if (!this.validateControl()) {
        return;
      }
      let flag = await this.$confirm(
        "Có chắc bạn muốn lưu lại danh mục băng thông cổng đang chọn?",
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
            if (rs === -1) {
              this.$root.toastError("Tên danh mục băng thông cổng không được trùng");
            } else if (rs === 0) {
              this.$root.toastError("Thêm danh mục băng thông cổng thất bại");
            }
            else {
              this.dataItems = await this.getItems();
              this.$root.toastSuccess("Thêm danh mục băng thông cổng thành công");
            }
            this.enableControls(!(rs > 0));
          } else {
            let rs = await this.updateItem(this.currentItem);
            if (rs === -1) {
              this.$root.toastError("Tên danh mục băng thông cổng không được trùng");
            } else if (rs == 0) {
              this.$root.toastError("Cập nhật danh mục băng thông cổng thất bại");
            } else {
              this.dataItems = await this.getItems();
              this.$root.toastSuccess("Cập nhật danh mục băng thông cổng thành công");
            }
            this.enableControls(!(rs === 1));
          }
        } finally {
          this.$root.showLoading(false);
        }
      }
    },

    btnNhapMoi_Click: async function (args) {
      this.enableControls(true);
      this.setCurrentItemDefault();
    },

    btnHuyBo_Click: async function (args) {
      if (this.$refs.gridItems) {
        // Bật lại các sự kiện click khi hủy bỏ
        const rows = this.$refs.gridItems.$el.querySelectorAll('tr');
        rows.forEach(row => {
          row.style.pointerEvents = 'auto';
        });
      }
      let items = this.gridItems.getSelectedRecords();
      let item = null;
      if (!(items == null || items.length == 0)) {
        if (this.currentItem.id == 0) {
          item = items[0];
        }
        else {
          item = items.find(x => x.ID == this.currentItem.id);
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
      if (this.$refs.gridItems) {

        // Vô hiệu hóa tất cả checkbox
        const checkboxes = this.$refs.gridItems.$el.querySelectorAll('input[type="checkbox"]');
        checkboxes.forEach(checkbox => {
          checkbox.disabled = true;
        });

        // Vô hiệu hóa tất cả sự kiện click trên các dòng
        const rows = this.$refs.gridItems.$el.querySelectorAll('tr');
        rows.forEach(row => {
          row.style.pointerEvents = 'none';
        });
      }

      const pageSize = this.$refs.gridItems.pageSize
      this.currentIdUpdate = this.currentItem.id

      // Chọn dòng hiện tại
      this.$refs.gridItems.selectRow(this.dataItems.findIndex(x => x.ID == this.currentItem.id) % pageSize);
      this.dataDelete = [];
      this.dataDelete.push(this.currentItem.id);

      this.enableControls(true);
    },

    btnXoa_Click: async function (args) {
      let flag = await this.$confirm(
        "Có chắc bạn muốn xóa danh mục băng thông cổng đang chọn?",
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
          let rs = await this.deleteItem(this.dataDelete.join(','));
          if (rs === 1) {
            this.setCurrentItemDefault();
            this.dataItems = await this.getItems();
            this.enableControls(false);
            this.$root.toastSuccess("Xóa băng thông cổng thành công");
          } else if (rs === -2) {
            this.$root.toastError("Danh mục băng thông cổng không tồn tại");
          } else {
            this.$root.toastError("Xóa băng thông cổng thất bại");
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

.e-rowcell .check-action input[type="checkbox"]:checked:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type="checkbox"]:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
}

.bangthong-gb {
  padding-left: 10px;
}
</style>
