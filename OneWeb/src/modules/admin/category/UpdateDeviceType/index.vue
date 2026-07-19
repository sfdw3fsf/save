<template src="./template.html"></template>
<style src="./style.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import api from "./api";
import dummy from "./dummy";
import EditGridCell from "./components/EditGridCell";
import PopupImageUpload from "./components/PopupImageUpload";
export default {
  components: { breadcrumb, PopupImageUpload },
  name: "UpdateDeviceType",
  props: {
    isPopup: {
      type: Boolean,
    },
  },
  mounted() {
    this.SetButton(0);
    this.loadData();
    this.$refs.tableThongTinThuocTinh.grid.ej2Instances.element.addEventListener(
      "mouseup",
      function (e) {
        var instance = this.ej2_instances[0];
        if (e.target.classList.contains("e-rowcell")) {
          if (instance.isEdit) instance.endEdit();
          let index = parseInt(e.target.getAttribute("Index"));
          instance.selectRow(index);
          instance.startEdit();
        }
      }
    );

    // this.clearData()
    // this.$refs.maKHGocInput.focus()
    // this.addKeyDownEvent()
  },
  destroyed() {},
  computed: {
    // enableDeleteBnt() {
    //   return this.gridboxDSPTT.selected !== null
    // }
    tableThongTinThuocTinhData() {
      return this.$store.state.updateDeviceType.dsThuocTinh;
    },
  },
  watch: {
    loading: function (val, oldval) {
      this.$root.showLoading(val);
    },
    openedPopup: function (val, oldval) {
      if (val) {
        this.removeKeyDownEvent();
      } else {
        this.addKeyDownEvent();
      }
    },
    "comboBoxDoiTuong.selected": {
      handler: function (val, oldval) {
        if (val) {
          this.getDataTableDanhSach();
        }
      },
      deep: true,
    },
    "tableDanhSach.selected": {
      handler: function (val, oldval) {
        if (val) {
          this.nhomtbi_id = val.nhomtbi_id;
          this.tenLoai = val.nhom_tbi;
          this.getDataTableThongTinThuocTinh();
          this.SetButton(3);
        }
      },
      deep: true,
    },
    bntMode: function (val, oldVal) {
      this.enableNhapMoiBnt = false;
      this.enableGhiLaiBnt = false;
      this.enableXoaBnt = false;
      this.enableHuyBnt = false;
      if (val === -1) {
        //Bat dau
        this.$refs.textBoxTenLoai.focus();
        this.enableGhiLaiBnt = true;
        this.enableHuyBnt = true;
      }

      if (val === 0) {
        //Bat dau
        this.$refs.textBoxTenLoai.focus();
        this.enableNhapMoiBnt = true;
        this.clearData();
      }

      if (val === 1) {
        //Them moi
        this.enableGhiLaiBnt = true;
        this.enableHuyBnt = true;
        this.$refs.textBoxTenLoai.focus();
        this.clearData();
      }

      if (val === 2) {
        //Huy
        if (oldVal === 3) {
          this.nhomtbi_id = this.tableDanhSach.selected.nhomtbi_id;
          this.tenLoai = this.tableDanhSach.selected.nhom_tbi;
          this.getDataTableThongTinThuocTinh();
          this.SetButton(3);
        } else {
          this.SetButton(1);
        }
      }

      if (val === 3) {
        //Edit
        this.enableNhapMoiBnt = true;
        this.enableXoaBnt = true;
        this.enableGhiLaiBnt = true;
        this.enableHuyBnt = true;
      }
    },
  },
  data() {
    return {
      loading: false,
      header: {
        title: "LOẠI THIẾT BỊ",
        list: [
          {
            name: "Lập hợp đồng",
            link: { name: "Ui.cards" },
            active: false,
          },
          {
            name: "Lắp đặt mới",
            link: { name: "Ui.buttons" },
            active: true,
          },
        ],
      },
      enableNhapMoiBnt: false,
      enableGhiLaiBnt: false,
      enableHuyBnt: false,
      enableXoaBnt: false,
      bntMode: null,
      nhomtbi_id: 0,

      tableThongTinThuocTinh: {
        data: [],
        selected: null,
        groupSettings: { showDropArea: false, columns: ["nhom_tt"] },
        columns: [
          { fieldName: "nhom_tt", headerText: "Nhóm", isGroupedColumn: true },
          {
            fieldName: "thuoctinh_id",
            headerText: "Thuộc tính ID",
            isPrimaryKey: true,
            visible: false,
          },
          {
            fieldName: "thuoctinh",
            headerText: "Thuộc tính",
            clipMode: "",
            allowFiltering: true,
            allowSorting: true,
            width: "300",
            allowEditing: false,
            textAlign: "Left",
          },
          {
            fieldName: "giatri",
            headerText: "Giá trị",
            allowFiltering: true,
            allowSorting: true,
            width: "auto",
            allowEditing: false,
            textAlign: "Left",
            template: () => {
              return {
                template: EditGridCell,
              };
            },
          },
        ],
        editSettings: { allowEditing: false, mode: "Normal" },
      },
      tableDanhSach: {
        data: [],
        selected: null,
        columns: [
          {
            fieldName: "ten_vt",
            headerText: "Đối tượng",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "nhom_tbi",
            headerText: "Thể loại",
            allowFiltering: true,
            allowSorting: true,
          },
        ],
      },
      comboBoxDoiTuong: { data: [], selected: null },
      openedPopup: false,
      tenLoai: "",
    };
  },
  methods: {
    addKeyDownEvent() {
      window.addEventListener("keydown", this.onF5KeyDown);
    },
    removeKeyDownEvent() {
      window.removeEventListener("keydown", this.onF5KeyDown);
    },
    onF5KeyDown(event) {
      if (event.keyCode === 116) {
        // event.preventDefault()
        // this.clearData()
      }
    },
    GetData(response) {
      // console.log(response);
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else {
        console.log(response.data.error_code);
      }
      return [];
    },
    GetDataObj(response) {
      // console.log(response);
      if (
        (response.data.error === 200 || response.data.error === "200") &&
        response.data.error_code === "BSS-00000000"
      ) {
        return response.data.data;
      } else {
        throw new Error(response.data.message_detail);
      }
    },
    showPopupImageUpload(data) {
      this.$refs.popupImageUpload.showModal(data);
    },
    actionBegin(args) {
      if (args.requestType === "save") {
        console.log(args.data);
      }
    },
    async getComboBoxDoiTuong() {
      this.comboBoxDoiTuong.selected = null;
      try {
        this.loading = true;
        this.comboBoxDoiTuong.data = this.GetData(
          await api.sp_lay_ds_thietbi_loaitbi(this.axios, {
            p_mavt: 70,
          })
        );
        if (this.comboBoxDoiTuong.data.length > 0) {
          this.comboBoxDoiTuong.selected = this.comboBoxDoiTuong.data[0];
        }
      } catch (err) {
        this.comboBoxDoiTuong.data = [];
        console.error(err);
        // setTimeout(() => {
        //   this.getComboboxData()
        // }, 3000)
      } finally {
        this.loading = false;
      }
    },
    async getDataTableThongTinThuocTinh() {
      if (this.comboBoxDoiTuong.selected) {
        this.loading = true;
        try {
          let data = this.GetData(
            await api.sp_lay_tt_thuoctinh_theo_nhomtbi(this.axios, {
              p_nhomtbi_id: this.nhomtbi_id,
              p_vattu_id: this.comboBoxDoiTuong.selected.vattu_id,
            })
          );
          if (data.length > 0) {
            data = await this.rowDataPreProcessed(data);
          }
          this.$store.dispatch("updateDeviceType/setDsThuocTinh", { dsThuocTinh: data });
        } catch (err) {
          this.$store.dispatch("updateDeviceType/setDsThuocTinh", { dsThuocTinh: [] });
          console.error(err);
        } finally {
          this.loading = false;
        }
      }
    },
    async rowDataPreProcessed(data) {
      for (const idx in data) {
        const row = data[idx];
        row.error = null;
        // console.error(row.ten_loai)
        if (row.ten_loai == "Combobox") {
          try {
            const command = `${row.id}`;
            const sql = this.GetDataObj(
              await api.sp_lay_dl_thuoctinh_theo_rowid(this.axios, {
                p_r_id: command,
              })
            );
            row.comboBoxData = sql.data_json;
            row.comboBoxColumns = sql.columns.map((item) => {
              return {
                fieldName: item.column_name,
                headerText: item.column_label,
                allowFiltering: true,
                allowSorting: true,
                width: item.column_size,
              };
            });
          } catch (err) {
            this.$toast.error(
              `${row.thuoctinh} - Lấy dữ liệu thuộc tính không thành công`
            );
            console.error(err);
          }
        }
      }
      return data;
    },
    // async rowDataPreProcessed_sync(data) {
    //   for (const idx in data) {
    //     const row = data[idx];
    //     row.error = null;
    //     if (row.ten_loai == "Combobox") {
    //       try {
    //         const command = `${row.id}`;
    //         const sql = this.GetData(
    //           await api.sp_lay_dl_thuoctinh_theo_rowid(this.axios, {
    //             p_r_id: command,
    //           })
    //         );
    //         row.comboBoxData = sql.data_json;
    //         row.comboBoxColumns = sql.columns.map((item) => {
    //           return {
    //             fieldName: item.column_name,
    //             headerText: item.column_label,
    //             allowFiltering: true,
    //             allowSorting: true,
    //             width: item.column_size,
    //           };
    //         });
    //       } catch (err) {
    //         this.$toast.error(`${row.thuoctinh} - Lấy dữ liệu không thành công`);
    //         console.error(err);
    //       }
    //     }
    //   }
    //   return data;
    // },
    async getDataTableDanhSach() {
      // this.tableDanhSach.selected = null
      if (this.comboBoxDoiTuong.selected) {
        this.loading = true;
        try {
          this.tableDanhSach.data = this.GetData(
            await api.sp_lay_ds_nhom_tbi(this.axios, {
              p_mavt: 70,
              p_vattu_id: this.comboBoxDoiTuong.selected.vattu_id,
            })
          );
        } catch (err) {
          this.tableDanhSach.data = [];
          console.error(err);
        } finally {
          this.loading = false;
        }
      }
      if (this.tableDanhSach.data.length === 0) {
        this.NhapMoiBnt();
      }
    },
    async ghiLaiTB() {
      if (this.tenLoai != "") {
        this.loading = true;
        try {
          const data = this.GetDataObj(
            await api.fn_frmnhom_tbi_capnhat(this.axios, {
              p_nhomtbi_id: this.nhomtbi_id,
              p_tennhom_tbi: this.tenLoai,
              p_vattu_id: this.comboBoxDoiTuong.selected.vattu_id,
              p_may_cn: await this.$root.token.getMachine(),
              p_nguoi_cn: this.$root.token.getUserName(),
              p_ip_cn: await this.$root.token.getIP(),
              p_themmoi: this.nhomtbi_id === 0 ? "1" : "0",
            })
          );
          if (data && data.toString().startsWith("OK")) {
            const newNhomtbi_id = data.split(";")[1];
            await this.ghiLaiTTThuocTinh(newNhomtbi_id);
            await this.getDataTableDanhSach();
          } else {
            this.$toast.error("Có lỗi trong quá trình lưu thông tin của loại thiết bị");
          }
        } catch (err) {
          console.error(err);
        } finally {
          this.loading = false;
        }
      } else {
        this.$toast.info("Bạn chưa nhập tên của loại thiết bị");
        this.$refs.textBoxTenLoai.focus();
      }
    },
    async xoaThietBi() {
      this.loading = true;
      try {
        const data = this.GetDataObj(
          await api.fn_frmnhom_tbi_delete(this.axios, {
            p_nhomtbi_id: this.nhomtbi_id,
          })
        );
        if (data && data.toString().startsWith("OK")) {
          this.$toast.success("Xóa thành công");
          await this.getDataTableDanhSach();
        } else {
          this.$toast.error("Có lỗi trong quá trình xóa loại thiết bị");
        }
      } catch (err) {
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    async ghiLaiTTThuocTinh(nhomtbi_id) {
      let sucessCount = 0;
      for (const row of this.tableThongTinThuocTinhData) {
        try {
          const data = this.GetDataObj(
            await api.update_nhom_tbi_thuoctinh(this.axios, {
              p_nhomtbi_id: nhomtbi_id,
              p_ttvt_id: row.ttvt_id,
              p_gttt_id: row.gttt_id,
              p_kieutt_id: row.kieutt_id,
              p_thuoctinh_id: row.thuoctinh_id,
              p_giatri: row.giatri,
              p_nhom_tbi: this.tenLoai,
            })
          );
          // this.$store.dispatch('updateDeviceType/setErrorThuocTinh', { id: row.thuoctinh_id, error: null })
          sucessCount = sucessCount + 1;
        } catch (err) {
          console.error(err);
          // this.$store.dispatch('updateDeviceType/setErrorThuocTinh', { id: row.thuoctinh_id, error: err })
          this.$root.$toast.error(`${row.thuoctinh} - Cập nhật không thành công`);
        }
      }
      if (sucessCount > 0)
        this.$toast.info(
          `${sucessCount}/${this.tableThongTinThuocTinhData.length} thuộc tính đã được cập nhật`
        );
    },
    selectedItemsChangedTableDanhSach(item) {
      if (item && item.length > 0) this.tableDanhSach.selected = item[0];
      else this.tableDanhSach.selected = null;
    },
    clearData() {
      this.tenLoai = "";
      this.tableDanhSach.selected = null;
      this.nhomtbi_id = 0;
      this.$refs.tableDanhSach.grid.ej2Instances.selectionModule.clearSelection();
      this.getDataTableThongTinThuocTinh();
    },
    async loadData() {
      await this.getComboBoxDoiTuong();
      await this.getDataTableDanhSach();
    },
    SetButton(mode) {
      this.bntMode = mode;
    },
    NhapMoiBnt() {
      this.SetButton(1);
    },
    GhiLaiBnt() {
      this.ghiLaiTB();
    },
    HuyBnt() {
      this.SetButton(2);
    },
    XoaBnt() {
      if (this.nhomtbi_id) {
        this.$bvModal
          .msgBoxConfirm(`Bạn chắc chắn muốn xóa loại thiết bị này?`, {
            title: "Thông báo",
            size: "sm",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then(async (yes) => {
            if (yes) {
              this.xoaThietBi();
            }
          });
      } else {
        this.$root.$toast.error("Bạn chưa chọn dữ liệu để xóa");
      }
    },
  },
};
</script>
