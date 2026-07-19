<template src="./template.html"></template>
<script>
import breadcrumb from "@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue";
import DataGridCustom from "@/modules/contract/setup/DeclareLandline/components/DataGridCustom";
import Select2Ext from "@/modules/contract/setup/DeclareLandline/components/Select2Ext";
export default {
  components: {
    breadcrumb,
    DataGridCustom,
    Select2Ext,
  },
  data() {
    return {
      columns: [
        {
          fieldName: "ma_nhom_quyen",
          headerText: "Mã nhóm quyền",
          allowFiltering: true,
          allowSorting: true,
          width: "10%",
        },
        {
          fieldName: "ten_nhom_quyen",
          headerText: "Tên nhóm quyền",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "mo_ta",
          headerText: "Mô tả nhóm quyền",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "trang_thai",
          headerText: "Trạng thái",
          allowFiltering: true,
          allowSorting: true,
          template: this.getTrangThaiTemplate(this),
          textAlign: "left",
          width: "auto",
        },
        {
          fieldName: "ma_nguoi_tao",
          headerText: "Người tạo",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "ngay_tao",
          headerText: "Ngày tạo",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "ma_nguoi_cn",
          headerText: "Người cập nhật",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "ngay_cn",
          headerText: "Ngày cập nhật",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
      ],
      is_insert: 0,
      current_index: 0,
      trang_thai: 0,
      old_index: 0,
    };
  },
  mounted() {
    this.$refs.cboTrangThai.dataSource = [
      {
        text: "Active",
        value: 1,
      },
      {
        text: "Deactive",
        value: 0,
      },
    ];
    this.$refs.cboTrangThai.dataTextField = "text";
    this.$refs.cboTrangThai.dataValueField = "value";
    this.$refs.cboTrangThai.value = 1;
    this.DirectoryPermissionGroups_Load();
  },
  methods: {
    clearValidate() {
      for (let key in this.$refs) {
        if (this.$refs[key] && this.$refs[key][0])
          this.$refs[key][0].classList.remove("invalid_in");
      }
    },
    validate() {
      this.clearValidate();
      if (!this.$refs.txtMaNhomQuyen.value) {
        this.$toast.error("Mã nhóm quyền không được để trống!");
        this.$refs.txtMaNhomQuyen.focus();
        this.$refs.txtMaNhomQuyen.classList.add("invalid_in");
        return;
      }

      if (this.$refs.txtMaNhomQuyen.value.length > 255) {
        this.$toast.error("Mã nhóm quyền không được lớn hơn 255 ký tự!");
        this.$refs.txtMaNhomQuyen.focus();
        this.$refs.txtMaNhomQuyen.classList.add("invalid_in");
        return;
      }

      if (!this.$refs.txtTenNhomQuyen.value) {
        this.$toast.error("Tên nhóm quyền không được để trống!");
        this.$refs.txtTenNhomQuyen.focus();
        this.$refs.txtTenNhomQuyen.classList.add("invalid_in");
        return;
      }

      if (this.$refs.txtMoTaNhomQuyen.value.length > 255) {
        this.$toast.error("Mô tả nhóm quyền không được lớn hơn 255 ký tự!");
        this.$refs.txtMoTaNhomQuyen.focus();
        this.$refs.txtMoTaNhomQuyen.classList.add("invalid_in");
        return;
      }
    },
    dtgDanhsach_focus(data) {
      let selected = this.$refs.dtgDanhsach.getSelectedRecords();
      if (selected.length > 0) {
        this.is_insert = 0;
        this.current_index = data.rowIndex;
        this.$refs.txtMaNhomQuyen.value = selected[0].ma_nhom_quyen;
        this.$refs.txtTenNhomQuyen.value = selected[0].ten_nhom_quyen;
        this.$refs.txtMoTaNhomQuyen.value = selected[0].mo_ta;
        this.$refs.cboTrangThai.value = selected[0].trang_thai;
        this.setDisable("btnNhapMoi", false);
        if (selected[0].trang_thai == 1) {
          this.$refs.btnXoa.style.display = null;
        } else {
          this.$refs.btnXoa.style.display = "none";
        }
      }
    },
    getTrangThaiTemplate(parent) {
      return function () {
        return {
          template: {
            template: `<span :class="data.trang_thai == 1 ? 'text-success' : 'text-danger'">{{data.trang_thai == 1 ? 'Available' : 'Not Available'}}</span>`,
            data() {
              return {};
            },
          },
        };
      };
    },
    async DirectoryPermissionGroups_Load() {
      let list = await this.getData(
        "web-quantri/vnp08/dm_nhom_quyen/sp_lay_ds_nhom_quyen",
        "get"
      );
      this.$refs.dtgDanhsach.dataSource = list.data;
      this.setIndex();
    },
    async btnXoa_Click() {
      try {
        let selected = this.$refs.dtgDanhsach.getSelectedRecords();
        if (selected.length > 0) {
          this.old_index = this.current_index;
          let data_manq = await this.getData(
            "web-quantri/vnp08/dm_nhom_quyen/sp_deactive_nhom_quyen",
            "post",
            {
              ma_nhom_quyen: selected[0].ma_nhom_quyen,
            }
          );
          if (data_manq.data.error_code == "BSS-00000000") {
            this.$toast.success("Điều chỉnh thành công!");
          } else {
            this.$toast.error(insert.data.message);
          }
          this.DirectoryPermissionGroups_Load();
        }
      } catch (err) {
        console.log(err);
      }
    },
    async btnNhapMoi_Click() {
      try {
        let data_manq = await this.getData(
          "web-quantri/vnp08/dm_nhom_quyen/sinh_ma_nhom_quyen",
          "get"
        );
        this.$refs.txtMaNhomQuyen.value = data_manq.data;
        this.$refs.txtTenNhomQuyen.value = "";
        this.$refs.txtMoTaNhomQuyen.value = "";
        this.$refs.cboTrangThai.value = 1;
        this.is_insert = 1;
        this.setDisable("btnNhapMoi", true);
      } catch (err) {
        console.log(err);
      }
    },
    async btnLuu_Click() {
      try {
        this.old_index = this.current_index;
        let is_insert = this.is_insert;
        let trang_thai = this.$refs.cboTrangThai.value;
        let ma_nhom_quyen = this.$refs.txtMaNhomQuyen.value;
        let ten_nhom_quyen = this.$refs.txtTenNhomQuyen.value;
        let mo_ta = this.$refs.txtMoTaNhomQuyen.value;

        let insert = await this.axios.post(
          "web-quantri/vnp08/dm_nhom_quyen/sp_insert_update_nhom_quyen",
          { is_insert, ma_nhom_quyen, ten_nhom_quyen, mo_ta, trang_thai }
        );
        if (insert.data.error_code == "BSS-00000000") {
          if (this.is_insert == 1) this.$toast.success("Tạo mới thành công!");
          else this.$toast.success("Điều chỉnh thành công!");
        } else {
          this.$toast.error(insert.data.message);
        }
        this.DirectoryPermissionGroups_Load();
      } catch (err) {
        console.log(err);
      }
    },
    setIndex() {
      this.$refs.dtgDanhsach.$refs.DataGridCustom.setCurrentSelectedRow(
        this.old_index
      );
      this.$refs.dtgDanhsach.$refs.DataGridCustom.flagSelectedRowIndexes = [];
      this.$refs.dtgDanhsach.$refs.DataGridCustom.flagSelectedRowIndexes.push(
        this.old_index
      );
    },
    async getData(url, method, param = {}) {
      return new Promise(async (resolve, reject) => {
        let data;
        if (method == "post") {
          data = await this.axios.post(url, param);
        } else {
          data = await this.axios.get(url, { params: param });
        }

        if (data && data.data && data.data.error_code == "BSS-00000000") {
          resolve(data.data);
        } else {
          reject(data.data);
        }
      });
    },
    setDisable(ref, value) {
      if (this.$refs[ref].tagName == "A") {
        if (value == true) this.$refs[ref].classList.add("disabled");
        else this.$refs[ref].classList.remove("disabled");
      } else {
        this.$refs[ref].disabled = value;
      }
    },
  },
};
</script>
