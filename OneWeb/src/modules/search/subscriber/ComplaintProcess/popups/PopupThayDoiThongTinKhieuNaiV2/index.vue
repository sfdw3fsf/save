<template src="./template.html"></template>
<style src="./style.scss"></style>
<script>
import api from "./api";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import ModalNhanVienLoi from "@/modules/EXTS/KHIEUNAI/TiepNhanKN/modal/KetQuaLoi/NhanVienLoi";
import ModalDonViLoi from "@/modules/EXTS/KHIEUNAI/TiepNhanKN/modal/KetQuaLoi/DonViLoi";
import Vue from "vue";
import { Query } from "@syncfusion/ej2-data";
import { NumericTextBox } from "@syncfusion/ej2-inputs";
import DataGridEdit from "./DataGridEdit/index.vue";
const DeleteBnt = Vue.component("DeleteBnt", {
  template:
    "<button class='btn' v-on:click='onClickBnt'><i class='fa fa-close'></i></button>",
  data() {
    return {
      data: {},
    };
  },
  methods: {
    async onClickBnt() {
      try {
        this.loading(true);
        // const response = await api.sp_delete_tientrinh_kn(this.axios, {
        //   vtientrinhkn_id: this.data.khieunai_id
        // })
        // if ((response.data.error === 200 || response.data.error === '200') && response.data.error_code === 'BSS-00000000') {
        //   this.$root.$toast.success('Xóa thành công')
        //   parentForm.get_ds_tientrinhxuly()
        // }
      } catch (err) {
        console.error(err);
      } finally {
        this.loading(false);
      }
    },
  },
});
const numericParams = {
  params: {
    validateDecimalOnType: true,
    decimals: 0,
    format: "N",
  },
};
let tienElem, tienObj, tienGcElem, tienGcObj, vatElem, vatObj, vatPtElem, vatPtObj;
export default {
  components: { breadcrumb, ModalNhanVienLoi, ModalDonViLoi, DataGridEdit },
  name: "PopupThayDoiThongTinKhieuNaiV2",
  props: {
    isPopup: {
      type: Boolean,
      default: false,
    },
    inputData: {
      type: Object,
      default: () => {
        return { khieunai_id: 8055 };
      },
    },
  },
  mounted() {
    this.loadData();
  },
  data() {
    return {
      header: {
        title: "THAY ĐỔI THÔNG TIN KHIẾU NẠI",
        list: [],
      },
      TTTTKN: {
        DienThoaiLH: null,
        NgayBaoNhan: new Date(),
        NoiDungKn: "",
        GhiChu: "",
        NguyenNhan: "",
        NoiDungTL: "",
      },
      KetQuaLoi: {
        data: [],
        selected: null,
        fields: { text: "noidung", value: "ketquakn_id" },
      },
      TableTienGC: {
        ThangGCData: [],
        KyGiamData: [],
        data: [],
        selected: null,
        editSettings: {
          allowEditing: true,
          allowAdding: true,
          allowDeleting: true,
          mode: "Normal",
          newRowPosition: "Bottom",
        },
        toolbar: [
          "Add",
          "Edit",
          "Cancel",
          "Delete",
          // { text: 'Thêm mới', tooltipText: 'Thêm mới', suffixIcon: 'plus-3', id: 'Add' },
          // { text: 'Sửa', tooltipText: 'Sửa', suffixIcon: 'edit', id: 'Edit' },
          // { text: 'Hủy', tooltipText: 'Hủy', suffixIcon: 'close', id: 'Cancel' },
          // { text: 'Xóa', tooltipText: 'Xóa', suffixIcon: 'circle-remove', id: 'Delete' }
        ],
      },
    };
  },
  watch: {},
  computed: {
    enableKetQuaLoiBnt() {
      return this.KetQuaLoi.selected === 7 || this.KetQuaLoi.selected === 9;
    },
    TableTienGCColumns() {
      return [
        {
          fieldName: "id",
          headerText: "Stt",
          allowFiltering: false,
          allowSorting: false,
          allowEditing: true,
          isPrimaryKey: true,
          visible: false,
        },
        {
          fieldName: "thang_gc",
          headerText: "Tháng GC",
          allowFiltering: true,
          allowSorting: false,
          allowEditing: true,
          validationRules: { required: true },
          editType: "dropdownedit",
          edit: {
            params: {
              allowFiltering: true,
              dataSource: this.TableTienGC.ThangGCData,
              fields: { text: "thang_gcht", value: "thang_gc" },
              query: new Query(),
              actionComplete: () => false,
            },
          },
        },
        {
          fieldName: "ky_gc",
          headerText: "Kỳ giảm",
          allowFiltering: true,
          allowSorting: false,
          allowEditing: true,
          validationRules: { required: true },
          editType: "dropdownedit",
          edit: {
            params: {
              allowFiltering: true,
              dataSource: this.TableTienGC.KyGiamData,
              fields: { text: "thang_apht", value: "ky_gc" },
              query: new Query(),
              actionComplete: () => false,
            },
          },
        },
        {
          fieldName: "tien",
          headerText: "Tiền GC",
          allowFiltering: true,
          allowSorting: false,
          allowEditing: true,
          editType: "numericedit",
          edit: {
            create: () => {
              tienElem = document.createElement("input");
              return tienElem;
            },
            read: () => {
              return tienObj.value;
            },
            destroy: () => {
              tienObj.destroy();
            },
            write: (args) => {
              tienObj = new NumericTextBox({
                value: args.rowData[args.column.field],
                change: function (args) {
                  // let formEle = document.getElementById("grid").querySelector("form")
                  //   .ej2_instances[0];
                  // var vat_pt_ele = formEle.getInputElement("vat_pt");
                  tienGcObj.value = Math.round(
                    tienObj.value / (1 + vatPtObj.value / 100)
                  );
                  vatObj.value = tienObj.value - tienGcObj.value;
                },
              });
              tienObj.appendTo(tienElem);
            },
            params: {
              validateDecimalOnType: true,
              decimals: 0,
              format: "N",
            },
          },
          valueAccessor: (field, data, column) => {
            return Number(data.tien).toLocaleString();
          },
          validationRules: { required: true },
        },
        {
          fieldName: "tien_gc",
          headerText: "Tiền",
          allowFiltering: true,
          allowSorting: false,
          allowEditing: true,
          editType: "numericedit",
          edit: {
            create: () => {
              tienGcElem = document.createElement("input");
              return tienGcElem;
            },
            read: () => {
              return tienGcObj.value;
            },
            destroy: () => {
              tienGcObj.destroy();
            },
            write: (args) => {
              tienGcObj = new NumericTextBox({
                value: args.rowData[args.column.field],
                change: function (args) {
                  // let formEle = document.getElementById("grid").querySelector("form")
                  //   .ej2_instances[0];
                  // var vat_pt_ele = formEle.getInputElement("vat_pt");
                  tienObj.value = Math.round(
                    tienGcObj.value * (1 + vatPtObj.value / 100)
                  );
                  vatObj.value = tienObj.value - tienGcObj.value;
                },
              });
              tienGcObj.appendTo(tienGcElem);
            },
            params: {
              validateDecimalOnType: true,
              decimals: 0,
              format: "N",
            },
          },
          valueAccessor: (field, data, column) => {
            return Number(data.tien_gc).toLocaleString();
          },
          validationRules: { required: true },
        },
        {
          fieldName: "vat_gc",
          headerText: "Thuế",
          allowFiltering: true,
          allowSorting: false,
          allowEditing: true,
          editType: "numericedit",
          edit: {
            create: () => {
              vatElem = document.createElement("input");
              return vatElem;
            },
            read: () => {
              return vatObj.value;
            },
            destroy: () => {
              vatObj.destroy();
            },
            write: (args) => {
              vatObj = new NumericTextBox({
                value: args.rowData[args.column.field],
                change: function (args) {
                  // let formEle = document.getElementById("grid").querySelector("form")
                  //   .ej2_instances[0];
                  // var vat_pt_ele = formEle.getInputElement("vat_pt");
                  // tienGcObj.value = Math.round(vatObj.value / (vat_pt_ele.value / 100));
                  // tienObj.value = vatObj.value + tienGcObj.value;
                },
              });
              vatObj.appendTo(vatElem);
            },
            params: {
              validateDecimalOnType: true,
              decimals: 0,
              format: "N",
            },
          },
          valueAccessor: (field, data, column) => {
            return Number(data.vat_gc).toLocaleString();
          },
          validationRules: { required: true },
        },
        {
          fieldName: "vat_pt",
          headerText: "VAT%",
          allowFiltering: true,
          allowSorting: false,
          allowEditing: true,
          defaultValue: 10,
          allowEditing: true,
          editType: "numericedit",
          edit: {
            create: () => {
              vatPtElem = document.createElement("input");
              return vatPtElem;
            },
            read: () => {
              return vatPtObj.value;
            },
            destroy: () => {
              vatPtObj.destroy();
            },
            write: (args) => {
              vatPtObj = new NumericTextBox({
                value: args.rowData[args.column.field],
                change: function (args) {
                  // let formEle = document.getElementById("grid").querySelector("form")
                  //   .ej2_instances[0];
                  // var vat_pt_ele = formEle.getInputElement("vat_pt");
                  // tienGcObj.value = Math.round(
                  //   tienObj.value / (1 + vat_pt_ele.value / 100)
                  // );
                  // vatObj.value = tienObj.value - tienGcObj.value;
                },
              });
              vatPtObj.appendTo(vatPtElem);
            },
            params: {
              validateDecimalOnType: true,
              decimals: 0,
              format: "N",
            },
          },
          validationRules: { required: true },
        },
        // {
        //   fieldName: 'DELETE',
        //   headerText: 'Xóa',
        //   allowFiltering: false,
        //   allowSorting: false,
        //   width: '70',
        //   template: () => {
        //     return {
        //       template: DeleteBnt
        //     }
        //   }
        // }
      ];
    },
  },
  methods: {
    GetData(response) {
      console.log(response);
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else {
        console.log(response.data.error_code);
      }
      return [];
    },
    async loadData() {
      await this.getKetQuaLoiCBData();
      await this.getDSKhieuNai();
    },
    async getKetQuaLoiCBData() {
      this.KetQuaLoi.data = [];
      this.KetQuaLoi.selected = null;
      try {
        this.loading(true);
        this.KetQuaLoi.data = this.GetData(await api.lay_ketqua_kn_theo_loai(this.axios));
        if (this.KetQuaLoi.data.length > 0) {
          this.KetQuaLoi.selected = this.KetQuaLoi.data[0].ketquakn_id;
        } else {
          this.$root.$toast.error(
            "Chưa có dữ liệu kết quả lỗi. Liên hệ admin để xử lý !"
          );
        }
      } catch (e) {
        console.log(e);
        this.$root.$toast.error("Lấy dữ liệu không thành công");
      } finally {
        this.loading(false);
      }
    },
    async loadDataCBThangGC(ma_tb, ma_tt) {
      try {
        this.loading(true);
        this.TableTienGC.ThangGCData = this.GetData(
          await api.sp_lay_thang_gc(this.axios, {
            ma_tb: ma_tb,
            ma_tt: ma_tt,
          })
        );
      } catch (e) {
        console.log(e);
        this.$root.$toast.error("Lấy dữ liệu không thành công");
      } finally {
        this.loading(false);
      }
    },
    async loadDataCBKyGiam() {
      try {
        this.loading(true);
        this.TableTienGC.KyGiamData = this.GetData(
          await api.sp_lay_kygiamcuoc(this.axios)
        );
      } catch (e) {
        console.log(e);
        this.$root.$toast.error("Lấy dữ liệu không thành công");
      } finally {
        this.loading(false);
      }
    },
    async getDataTableTienGC() {
      this.TableTienGC.data = [];
      this.TableTienGC.selected = null;
      try {
        this.loading(true);
        this.TableTienGC.data = this.GetData(
          await api.sp_lay_khieunai_gc_by_id(this.axios, {
            p_khieunai_id: this.inputData.khieunai_id,
          })
        );
      } catch (e) {
        console.log(e);
        this.$root.$toast.error("Lấy dữ liệu không thành công");
      } finally {
        this.loading(false);
      }
    },
    async getDSKhieuNai() {
      this.TTTTKN = {
        DienThoaiLH: null,
        NgayBaoNhan: new Date(),
        NoiDungKn: "",
        GhiChu: "",
        NguyenNhan: "",
        NoiDungTL: "",
      };
      this.CuocCP = { Giam: 0, Tien: 0, Vat: 0 };
      this.CuocDT = { Giam: 0, Tien: 0, Vat: 0 };
      try {
        this.loading(true);
        const data = this.GetData(
          await api.sp_lay_ds_khieunai_by_id(this.axios, {
            p_khieunai_id: this.inputData.khieunai_id,
          })
        );
        if (data && data.length > 0) {
          this.TTTTKN.DienThoaiLH = data[0].dienthoai_lh;
          this.TTTTKN.NoiDungKn = data[0].nd_khieunai;
          this.TTTTKN.GhiChu = data[0].nguyennhan_kn;
          this.TTTTKN.NguyenNhan = data[0].noidung_gq;
          this.TTTTKN.NoiDungTL = data[0].ghichu_kn;
          this.KetQuaLoi.selected = data[0].ketquakn_id;
          this.TTTTKN.NgayBaoNhan = new Date(data[0].ngay_bn);
          await this.loadDataCBThangGC(data[0].ma_tb, data[0].ma_tt);
          await this.loadDataCBKyGiam();
        }

        await this.getDataTableTienGC();
      } catch (e) {
        console.log(e);
        this.$root.$toast.error("Lấy dữ liệu không thành công");
      } finally {
        this.loading(false);
      }
    },
    selectedItemsChangedTableTienGC(items) {
      if (items.length > 0) {
        this.TableTienGC.selected = items[0];
      }
    },
    async KetQuaLoiBnt() {
      if (this.KetQuaLoi.selected === 7) {
        this.$refs.ModalNhanVienLoi.show(this.inputData.khieunai_id).then((result) => {
          console.log("ModalNhanVienLoi", result);
          if (result.ok) {
          } else {
          }
        });
      }
      if (this.KetQuaLoi.selected === 9) {
        this.$refs.ModalDonViLoi.show(this.inputData.khieunai_id).then((result) => {
          console.log("ModalDonViLoi", result);
          if (result.ok) {
          } else {
          }
        });
      }
    },
    async CapNhatBnt() {
      this.$refs.lblNDXuLy.focus();
      if (this.TTTTKN.DienThoaiLH === "") {
        this.$root.$toast.warning("Số điện thoại không được để trống");
        return;
      }
      if (this.TTTTKN.NoiDungKn === "") {
        this.$root.$toast.warning("Nội dung không được để trống");
        return;
      }
      if (this.KetQuaLoi.selected === null) {
        this.$root.$toast.warning("Hãy chọn kết quả lỗi!");
        this.$refs.KetQuaLoiCB.$el.focus();
        return;
      }

      try {
        this.loading(true);
        this.$refs.TableTienGC.grid.ej2Instances.endEdit();
        const tableData = this.$refs.TableTienGC.dataItems.map((item) => {
          const rtItem = {};
          for (const [key, value] of Object.entries(item)) {
            rtItem[key.toUpperCase()] = value;
          }
          return rtItem;
        });
        const vgiamcuoc_dt = this.$refs.TableTienGC.dataItems.reduce(function (acc, obj) {
          return acc + obj.tien_gc;
        }, 0);
        const vvat_dt = this.$refs.TableTienGC.dataItems.reduce(function (acc, obj) {
          return acc + obj.vat_gc;
        }, 0);
        const response = await api.thaydoi_ttkn_v5(this.axios, {
          vkhieunai_id: this.inputData.khieunai_id,
          vnhanvien_id: this.$root.token.getNhanVienID(),
          vdonvi_id: this.$root.token.getDonViID(),
          vketquakn_id: this.KetQuaLoi.selected,
          vghichu: this.TTTTKN.GhiChu,
          vdienthoai_lh: this.TTTTKN.DienThoaiLH,
          vnd_khieunai: this.TTTTKN.NoiDungKn,
          vnoidung_gq: this.TTTTKN.NoiDungTL,
          vnguyennhan_kn: this.TTTTKN.NguyenNhan,
          vgiamcuoc_dt: vgiamcuoc_dt,
          vvat_dt: vvat_dt,
          vgiamcuoc_cp: this.CuocCP.Tien,
          vvat_cp: this.CuocCP.Vat,
          vngay_cn: moment().format("DD/MM/YYYY hh:mm:ss"),
          vnguoi_cn: this.$root.token.getUserName(),
          vmay_cn: await this.$root.token.getMachine(),
          vip_cn: await this.$root.token.getIP(),
          vngay_bn: moment(this.TTTTKN.NgayBaoNhan).format("DD/MM/YYYY hh:mm:ss"),
          vjs_khieunai_gc: JSON.stringify(tableData),
        });
        console.log(response);
        if (
          (response.data.error === 200 || response.data.error === "200") &&
          response.data.error_code === "BSS-00000000"
        ) {
          this.$root.$toast.success("Cập nhật thành công");
          this.$emit("onSuccess");
          // this.getDSKhieuNai()
        } else {
          this.$root.$toast.error("Cập nhật không thành công");
        }
      } catch (e) {
        console.log(e);
        this.$root.$toast.error("Không thể cập nhật");
      } finally {
        this.loading(false);
      }
    },
  },
};
</script>
