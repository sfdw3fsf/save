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
      lstTrangthai: [
        {
          text: "Active",
          value: 1,
        },
        {
          text: "Deactive",
          value: 0,
        }
      ],
      columns: [
        {
          fieldName: "ma_ch",
          headerText: "Mã câu",
          allowFiltering: true,
          allowSorting: true,
          width: "10%",
        },
        {
          fieldName: "noidung",
          headerText: "Nội dung câu hỏi",
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
      lstCauTL: []
    };
  },
  async mounted() {

    this.DirectoryPermissionGroups_Load();
  },
  methods: {
    btnAddCauTL_Click() {
      this.lstCauTL.push(1)
    },
    clearValidate() {
      this.$refs.txtNoiDungCauHoi.classList.remove("invalid_in");
      this.$refs.txtMaCauHoi.classList.remove("invalid_in");
    },
    validate() {
      this.clearValidate();
      if (!this.$refs.txtMaCauHoi.value) {
        this.$toast.error("Mã câu hỏi không được để trống!");
        this.$refs.txtMaCauHoi.focus();
        this.$refs.txtMaCauHoi.classList.add("invalid_in");
        return;
      }

      if (this.$refs.txtMaCauHoi.value.length > 255) {
        this.$toast.error("Mã câu hỏi không được lớn hơn 255 ký tự!");
        this.$refs.txtMaCauHoi.focus();
        this.$refs.txtMaCauHoi.classList.add("invalid_in");
        return;
      }

      if (this.$refs.txtNoiDungCauHoi.value.length > 255) {
        this.$toast.error("Nội dung câu hỏi không được lớn hơn 255 ký tự!");
        this.$refs.txtNoiDungCauHoi.focus();
        this.$refs.txtNoiDungCauHoi.classList.add("invalid_in");
        return;
      }
    },
    dtgDanhsach_focus(data) {
      let selected = this.$refs.dtgDanhsach.getSelectedRecords();
      if (selected.length > 0) {
        this.is_insert = 0;
        this.current_index = data.rowIndex;
        this.$refs.txtMaCauHoi.value = selected[0].ma_ch;
        this.$refs.txtNoiDungCauHoi.value = selected[0].noidung;
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
        "web-quantri/vnp08/dm_cau_hoi/sp_lay_ds_cau_hoi",
        "get"
      );
      this.$refs.dtgDanhsach.dataSource = list.data;
      this.setIndex();
      this.$refs.cboCauHoi.HT_COMBOBOX(this.lstTrangthai, "text", "value")
    },
    async btnXoa_Click() {
      try {
        let selected = this.$refs.dtgDanhsach.getSelectedRecords();
        if (selected.length > 0) {
          this.old_index = this.current_index;
          let data_manq = await this.getData(
            "web-quantri/vnp08/dm_quyen/sp_deactive_quyen",
            "post",
            {
              ma_quyen: selected[0].ma_quyen,
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
          "web-quantri/vnp08/dm_cau_hoi/sinh_ma_cau_hoi",
          "get"
        );
        this.$refs.txtMaCauHoi.value = data_manq.data;
        this.$refs.txtNoiDungCauHoi.value = "";
        this.lstCauTL = [1]
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
        let cauhoi_id = this.$refs.txtMaCauHoi.value;
        let noidung = this.$refs.txtNoiDungCauHoi.value;
        let lstCauTL = []
        this.$refs.txtTraLoi.forEach((item, key) => {
            console.log($(item).find("input"))
        });
        return
        let insert = await this.axios.post(
          "web-quantri/vnp08/dm_cau_hoi/sp_insert_update_cau_hoi_tl",
          { is_insert, cauhoi_id , noidung , lstCauTL }
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
        
        resolve(data.data);
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
<style scoped>
  .box {
    background: #FFFFFF;
    padding: 16px 12px;
    border: 1px solid #E0E0E0;
    border-radius: 4px;
    margin-bottom: 8px;
    box-shadow: 0px 4px 4px rgb(177 177 177 / 15%);
  }
</style>
