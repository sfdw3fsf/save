<template src="./index.template.html"></template>

<script>
export default {
  data: function () {
    return {
      dataItems: [],
      dsLoaiToiUu: [],
      dsTrangThaiToiUu: [],
      dsThangNamToiUu: [],
      dsPhanLoaiCong: [],
      dsNhomCong: [],
      currentItem: {
        loaiToiUuId: 2,
        loaiToiUuName: "Giảm",
        trangThaiToiUuId: 0,
        thangNamToiUu:'',
        id: 0,
        ten: "",
        phanLoai: 1,
        thuocNhom: 1,
        camModule: 0,
        tocDoPort: "",
        ghiChu: null,
        hieuLuc: 1,
        trangthai: 0,
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
        btnTimKiem: {
          enabled: true,
        },
        btnXoa: {
          enabled: false,
        },
      },
      sizingHienTaiColumns: [
      {
          field: 'SIZING_CPU_HT',
          headerText: 'CPU',
          headerAlign:'Center',
          cssClass: "text-center",
          textAlign: 'Center',
          width: 130,
          allowFiltering: true
        },
        {
          field: 'SIZING_RAM_HT',
          width: 130,
          headerText: 'RAM',
          headerAlign:'Center',
          textAlign: 'Center',
          cssClass: "text-center",
          allowFiltering: true
        },
      ],
      mucDoSDCaoNhatColumns: [
      {
          field: 'MAX_CPU',
          headerText: 'CPU',
          headerAlign:'Center',
          textAlign: 'Center',
          width: 130,
          cssClass: "text-center",
          allowFiltering: true
        },
        {
          field: 'MAX_RAM',
          headerText: 'RAM',
          headerAlign:'Center',
          textAlign: 'Center',
          width: 130,
          cssClass: "text-center",
          allowFiltering: true
        },
      ],
      soLanVuotNguongColumns: [
      {
          field: 'SL_CPU',
          headerText: 'CPU',
          textAlign: 'Center',
          headerAlign:'Center',
          width: 130,
          cssClass: "text-center",
          allowFiltering: true
        },
        {
          field: 'SL_RAM',
          headerText: 'RAM',
          headerAlign:'Center',
          textAlign: 'Center',
          width: 130,
          cssClass: "text-center",
          allowFiltering: true
        },
      ],
      sizingCanTangColumns: [
      {
          field: 'SIZING_CPU_TANG',
          headerText: 'CPU',
          headerAlign:'Center',
          cssClass: "text-center",
          textAlign: 'Center',
          width: 130,
          allowFiltering: true,
          allowEditing: true, // Cho phép chỉnh sửa
          editType: 'numericedit', // Cho phép chỉnh sửa số
          edit: {
            params: {
              decimals: 0, // Số chữ số thập phân
              min: 0,      // Giá trị tối thiểu
              max: 100   // Giá trị tối đa
            }
      }
        },
        {
      field: 'SIZING_RAM_TANG',
      headerText: 'RAM',
      headerAlign: 'Center',
      cssClass: "text-center",
      textAlign: 'Center',
      width: 130,
      allowFiltering: true,
      allowEditing: true, // Cho phép chỉnh sửa
      editType: 'numericedit', // Cho phép chỉnh sửa số
      edit: {
        params: {
          decimals: 0, // Số chữ số thập phân
          min: 0,      // Giá trị tối thiểu
          max: 10000   // Giá trị tối đa
        }
      }
  },
      ],
      editSettings:{ allowEditing: true, mode:'Batch'},
      sizingSauKhiTangColumns: [
      {
          field: 'SIZING_CPU_SAUKHI',
          headerText: 'CPU',
          headerAlign:'Center',
          cssClass: "text-center",
          textAlign: 'Center',
          width: 130,
          allowFiltering: true
        },
        {
          field: 'SIZING_RAM_SAUKHI',
          headerText: 'RAM',
          headerAlign:'Center',
          cssClass: "text-center",
          textAlign: 'Center',
          width: 130,
          allowFiltering: true
        },
      ],
      dsYeuCauToiUu: []
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
      this.buttons.btnGhiLai.enabled = value > 0;

    },
    "currentItem.ten": function (value) {
      this.controls.txtName.invalid = value == "";
    },
    "currentItem.tocDoPort": function (value) {
      this.controls.txtTocDoPort.invalid = (value == "") || (value == null);
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
    actionComplete: function (args) {
    if (args.requestType === 'save') {
      console.log('Dữ liệu đã chỉnh sửa:', args.data);
      // Thực hiện cập nhật dữ liệu vào server hoặc local state
    }
  },
    getHieuLucText: function (value) {
      return value == 1 ? "Hiệu lực" : "Không hiệu lực";
    },
  cellSaved(args){
    console.log('cellSaved', args)

      const index=this.dataItems.findIndex(x=>x.ID==args.rowData.ID)
      if(index>-1){
        if(args.columnName=='SIZING_CPU_TANG'){
        this.dataItems[index].SIZING_CPU_TANG=args.value
        }else if(args.columnName=='SIZING_RAM_TANG'){
          this.dataItems[index].SIZING_RAM_TANG=args.value
        }
      }
      console.log('data', this.dataItems[index])
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
    onExport: async function (args) {
          const exportProperties = {
            fileName: 'DanhSachToiUuTaiNguyenTang.xlsx',
          };
          this.gridItems.excelExport(exportProperties);
        },
    load: async function () {
      this.dsThangNamToiUu = await this.getThangNamToiUu();
      if (this.dsThangNamToiUu.length > 0) {
      this.currentItem.thangNamToiUu = this.dsThangNamToiUu[0].ID; // Gán giá trị đầu tiên
      }
      this.dsLoaiToiUu = await this.getLoaiToiUu();
      this.dsTrangThaiToiUu = await this.getTrangThaiToiUu();
      this.dsYeuCauToiUu = await this.apiListDanhSach();
    },
    getThangNamToiUu: async function () {
          let result = [];
          try {
            let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
              loaiDanhMuc: "HATANG_THANGNAM_TOIUU",
              thamSo1: null,
              thamSo2: null
            });
            result = rs.data;

          } catch (e) {
            this.handleApiError(e);
          }
          return result;
        },
    getLoaiToiUu: async function () {
          let result = [];
          try {
            let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
              loaiDanhMuc: "HATANG_LOAI_TOIUU",
              thamSo1: `2`,
              thamSo2: null
            });
            result = rs.data;

          } catch (e) {
            this.handleApiError(e);
          }
          return result;
        },
        getTrangThaiToiUu: async function () {
          let result = [];
          try {
            let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
              loaiDanhMuc: "HATANG_TRANGTHAI_TOIUU",
              thamSo1: null,
              thamSo2: null
            });
            result = rs.data;

          } catch (e) {
            this.handleApiError(e);
          }
          return result;
        },
        apiListDanhSach: async function () {
          let result = [];
          let postData ={
            thang_nam: this.currentItem.thangNamToiUu,
            loai_toiuu: this.currentItem.loaiToiUuId,
            trangthai_xacnhan: this.currentItem.trangThaiToiUuId
                  };
          try {
            let rs = await this.$root.context.post("/web-ecms/thuchien_toiuu-tainguyen/lay_ds_yeucau_toiuu",postData);
            result = rs.data;
          } catch(e) {
            this.$toast.error(e.response.data.message_detail)
          }
          return result;
        },
      apiDanhSachMayAo: async function () {
          let result = [];
          let postData ={
            phieu_toiuu: this.currentItem.id
                  };
          try {
            let rs = await this.$root.context.post("/web-ecms/thuchien_toiuu-tainguyen/danhsach_mayao",postData);
            result = rs.data;
          } catch(e) {
            this.$toast.error(e.response.data.message_detail)
          }
          return result;
        },
    apiXacNhanToiUu: async function () {
      let result = false;
      let postData ={
            phieu_toiuu: this.currentItem.id
                  };
      try {
        let rs = await this.$root.context.post("/web-ecms/thuchien_toiuu-tainguyen/xacnhan_toiuu_tainguyen",postData);
        if (rs.error_code == 'BSS-00000000') {
            result = rs.data;
          } else {
            this.$root.toastError(rs.error_message);
            result = false;
          }

      } catch { }
      return result;
    },
    getItems: async function () {
      return this.dataItems;
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
      } catch { }
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
      } catch { }
      return result;
    },
    buildDanhSachMayAo() {
  const danhsach_mayao = this.dataItems.map(item => ({
    mayAoId: item.MAYAOID,
    sizingCpuTang: item.SIZING_CPU_TANG,
    sizingRamTang: item.SIZING_RAM_TANG
  }));
  return danhsach_mayao;
},
    updateDataItem: async function () {
      let result = false;
     
      try {
        let rs = await this.$root.context.post("/web-ecms/thuchien_toiuu-tainguyen/update_phieu_toiuu",{
            phieu_toiuu: this.currentItem.id,
            danhsach_mayao: this.buildDanhSachMayAo()
                            });
        if (rs.error_code == 'BSS-00000000') {
          result =true;
        } else {
          this.$root.toastError(rs.error_message);
          result = false;
        }
        
      } catch { }
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
      this.currentItem.trangthai = item.TRANGTHAI;
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
    gridDS_RowSelected: async function (args) {
      this.setCurrentItem(args.data);
      this.dataItems = await this.apiDanhSachMayAo();

    },
    gridDS_RowDeselected: async function (args) {
      this.setCurrentItem(args.data);
      this.dataItems = await this.apiDanhSachMayAo();
    },
    gridItems_RowSelected: async function (args) {
      this.setCurrentItem(args.data);
    },
    gridItems_RowDeselected: async function (args) {
      let items = this.gridItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        this.setCurrentItem(items[0]);
      } else {
        this.setCurrentItemDefault();
      }
    },
    validateSearchControls: function () {
      let msg = "";
      if (this.currentItem.thangNamToiUu == null || this.currentItem.thangNamToiUu == "") {
        msg = "Tháng năm tối ưu không được để trống.";
      }
      if (msg != "") this.$root.toastError(msg);
      return msg == "";
    },
    validateControls: function () {
      let msg = "";
      if (this.currentItem.id == 0) {
        msg = "Vui lòng chọn yêu cầu tối ưu trước khi thực hiện thao tác này.";

      }
      if (this.currentItem.trangthai == 1) {
        msg = "Yêu cầu tối ưu đã được xác nhận, không thể thực hiện thao tác này.";
      }

      if (this.currentItem.loaiToiUuId == null || this.currentItem.loaiToiUuId == "") {
        if (msg != "") msg += "\n\r";
        msg += "Loại tối ưu không được để trống.";
      }
      if (msg != "") this.$root.toastError(msg);
      return msg == "";
    },
    btnHoanThanh_Click: async function (args) {
      if (this.validateControls()) {
        let flag = await this.$confirm(
          "Có chắc bạn muốn xác nhận tăng tài nguyên cho máy ảo?",
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

              let rs = await this.apiXacNhanToiUu();
              //let rs = await this.addItem(this.currentItem);
              if (rs) {
                // this.dataItems = await this.getItems();
                this.dataItems = [];
                this.$root.toastSuccess("Xác nhận tối ưu thành công");
              } else {
                this.$root.toastError("Xác nhận tối ưu thất bại");
              }
              this.enableControls(!rs);

          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    btnSua_Click: async function (args) {
      this.enableControls(true);
    },
    btnGhiLai_Click: async function (args) {
      if (this.validateControls()) {
        let flag = await this.$confirm(
          "Có chắc bạn muốn ghi lại thông tin yêu cầu tối ưu này?",
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
            let rs = await this.updateDataItem();
            if (rs) {
              this.$root.toastSuccess("Cập nhật thông tin yêu cầu tối ưu thành công");
              //this.dataItems = await this.getItems();
              this.dsYeuCauToiUu = await this.apiListDanhSach();
              this.enableControls(false);
            } else {
              this.$root.toastError("Cập nhật thông tin yêu cầu tối ưu thất bại");
            }
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    btnTimKiem_Click: async function (args) {
      if (this.validateSearchControls()) {
      this.dataItems = [];
      
      this.setCurrentItemDefault();
      this.dsYeuCauToiUu = await this.apiListDanhSach();
    }
    },

  },
};
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type='checkbox']:checked:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type='checkbox']:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
}
</style>
