<template src="./index.template.html"></template>

<script>
export default {
  data: function () {
    return {
      dataItems: [],
      dataDelete: [],
      dsDoiTuong: [],
      dynamicPlaceholder: "PNG, GIF, JPEC",
      currentItem: {
        id: 0,
        ten: "",
        doiTuong: null,
        tenDoiTuong: "",
        anhDaiDien: null,
        ghiChu: "",
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
        comboboxDoiTuong: {
          enabled: false,
        },
        formFileAnhDaiDien: {
          enabled: false,
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
    }
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.dsDoiTuong = await this.getDsDoiTuong();
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

    getDsDoiTuong: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post(
          "/web-ecms/hatang/loai-vaitro/get-doituong"
        );
        result = rs.data;
      } catch { }
      return result;
    },

    getItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/loai-vaitro/get-items");
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

    getAnhDaiDien: async function (id) {
      let result = null;
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/loai-vaitro/get-anh-dai-dien", {
          id: id,
        });
        result = rs.data[0].ANH_DAI_DIEN;
      } catch { }
      return result;
    },

    addItem: async function (args) {
      let result = 0;

      try {
        const formData = new FormData();
        formData.append("p_file", (args.anhDaiDien == null) ? new Blob() : args.anhDaiDien); // Thêm file vào FormData
        formData.append("p_ten", args.ten?.trim());
        formData.append("p_doi_tuong", (!args.doiTuong) ? null : args.doiTuong);
        formData.append("p_ghi_chu", (!args.ghiChu || args.ghiChu?.trim() === "") ? "" : args.ghiChu?.trim());
        formData.append("p_hieu_luc", args.hieuLuc);

        let rs = await this.$root.context.post("/web-ecms/hatang/loai-vaitro/add", formData, {
          headers: {
            "Content-Type": "multipart/form-data", // Đặt Content-Type
          },
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
      let result = 0;

      try {
        const formData = new FormData();
        formData.append("p_file", (args.anhDaiDien == null) ? new Blob() : args.anhDaiDien); // Thêm file vào FormData
        formData.append("p_id", args.id);
        formData.append("p_ten", args.ten?.trim());
        formData.append("p_doi_tuong", (!args.doiTuong) ? null : args.doiTuong);
        formData.append("p_ghi_chu", (!args.ghiChu || args.ghiChu?.trim() === "") ? "" : args.ghiChu?.trim());
        formData.append("p_hieu_luc", args.hieuLuc);

        let rs = await this.$root.context.post("/web-ecms/hatang/loai-vaitro/update", formData, {
          headers: {
            "Content-Type": "multipart/form-data", // Đặt Content-Type
          },
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

    deleteItem: async function (ids) {
      let result = 0;
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/loai-vaitro/delete", {
          ids: ids,
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

    enableControls: function (args) {
      this.controls.txtName.enabled = args;
      this.controls.checBoxHieuLuc.enabled = args;
      this.controls.comboboxDoiTuong.enabled = args;
      this.controls.formFileAnhDaiDien.enabled = args;
      this.controls.txtGhiChu.enabled = args;

      this.buttons.btnGhiLai.enabled = args;
      this.buttons.btnNhapMoi.enabled = !args;
      this.buttons.btnHuyBo.enabled = this.buttons.btnGhiLai.enabled;
      if (this.currentItem.id > 0)
        this.buttons.btnSua.enabled = !this.buttons.btnGhiLai.enabled;
    },

    setCurrentItem: function (item, dataImage) {
      this.currentItem.id = item.ID;
      this.currentItem.ten = item.TEN;
      this.currentItem.doiTuong = item.DOI_TUONG;
      this.currentItem.tenDoiTuong = item.TEN_DOI_TUONG;
      this.currentItem.anhDaiDien = this.convertBase64ToBinary(dataImage);
      this.currentItem.ghiChu = item.GHI_CHU;
      this.currentItem.hieuLuc = item.HIEU_LUC;
      if (dataImage) {
        this.dynamicPlaceholder = item.TEN + ".png";
      }
      else {
        this.dynamicPlaceholder = "PNG, GIF, JPEG";
      }
    },

    setCurrentItemDefault: function () {
      this.setCurrentItem({
        ID: 0,
        TEN: "",
        DOI_TUONG: null,
        TEN_DOI_TUONG: "",
        ANH_DAI_DIEN: null,
        GHI_CHU: null,
        HIEU_LUC: 1,
      }, null);
    },

    convertBase64ToBinary(base64String) {
      if (base64String) {
        const binaryString = atob(base64String);
        const binaryArray = new Uint8Array(binaryString.length);
        for (let i = 0; i < binaryString.length; i++) {
          binaryArray[i] = binaryString.charCodeAt(i);
        }
        const blob = new Blob([binaryArray], { type: "application/octet-stream" });
        return blob;
      } else {
        return null;
      }
    },

    validateControls: function () {
      let msg = "";
      if (this.controls.txtName.invalid) {
        msg = "Tên loại vai trò không được để trống.";
      }
      if (msg != "") this.$root.toastError(msg);
      return msg == "";
    },

    gridItems_RowSelected: async function (args) {
      let ds = this.$refs.gridItems.getSelectedRecords();
      this.dataDelete = ds.map(item => item.ID);
      if (args.data.length = 1) {
        let dataImage = await this.getAnhDaiDien(args.data.ID);
        this.setCurrentItem(args.data, dataImage);
      }
      if (Number(args.row?.length || 0) == Number(this.lengthGrid)) {
        let dataImage = await this.getAnhDaiDien(ds[0].ID);
        this.setCurrentItem(ds[0], dataImage);
      }
      console.log('chon', this.dataDelete)

      // let dataImage = await this.getAnhDaiDien(args.data.ID);
      // this.setCurrentItem(args.data, dataImage);
      // this.dataDelete.push(args.data.ID);

      // if (this.checkAllGrid) {
      //   let ds = this.$refs.gridItems.getSelectedRecords();
      //   this.lengthGrid = ds.length;
      //   let dataImage = await this.getAnhDaiDien(ds[0].ID);
      //   this.setCurrentItem(ds[0], dataImage);
      //   this.dataDelete = ds.map(item => item.ID);
      // }
    },

    gridItems_RowDeselected: async function (args) {
      let items = this.gridItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        let dataImage = await this.getAnhDaiDien(items[0].ID);
        this.setCurrentItem(items[0], dataImage);
        this.dataDelete = items.map(item => item.ID);
      } else {
        this.setCurrentItemDefault();
        this.dataDelete = [];
      }
      console.log('ko chon delete', this.dataDelete)

      // this.dataDelete = this.dataDelete.filter(item => item !== args.data.ID);

      // if (this.dataDelete.length > 0) {
      //   let id = this.dataDelete[this.dataDelete.length - 1];
      //   let item = this.dataItems.filter(a => a.ID === id);
      //   let dataImage = await this.getAnhDaiDien(id);
      //   this.setCurrentItem(item[0], dataImage);
      // }
      // else {
      //   this.setCurrentItemDefault();
      // }
      // //nếu số lượng uncheck = số lượng tổng bản ghi thì là ko chọn tất cả
      // if (args.data.length == this.lengthGrid) {
      //   this.dataDelete = [];
      //   this.setCurrentItemDefault();
      // }
    },

    btnGhiLai_Click: async function (args) {
      if (this.validateControls()) {
        let flag = await this.$confirm(
          "Có chắc bạn muốn lưu lại loại vai trò đang chọn?",
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
                this.$root.toastError("Tên loại vai trò không được trùng");
              } else if (rs === 0) {
                this.$root.toastError("Thêm loại vai trò thất bại");
              }
              else {
                this.$root.toastSuccess("Thêm loại vai trò thành công");
                this.dataItems = await this.getItems();

              }
              this.enableControls(!(rs > 0));
            } else {
              let rs = await this.updateItem(this.currentItem);
              if (rs === -1) {
                this.$root.toastError("Tên loại vai trò không được trùng");
              } else if (rs === -2) {
                this.$root.toastError("Danh mục loại vai trò không tồn tại");
              } else if (rs === 0) {
                this.$root.toastError("Cập nhật loại vai trò thất bại");
              } else {
                this.dataItems = await this.getItems();
                this.$root.toastSuccess("Cập nhật loại vai trò thành công");
              }
              this.enableControls(!(rs === 1));
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
        "Có chắc bạn muốn xóa loại vai trò đang chọn?",
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
            this.$root.toastSuccess("Xóa loại vai trò thành công");
          } else if (rs === -2) {
            this.$root.toastError("Danh mục loại vai trò không tồn tại");
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
</style>
