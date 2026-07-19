<template src="./LoaiThietBi.html"></template>
<style scoped src="./LoaiThietBi.scss"></style>
<script>
import Vue from "vue";
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
Vue.use(DialogPlugin);
export default {
  name: "LoaiThietBi",
  data() {
    return {
      dialog: {
        target: ".main-wrapper",
        width: "80%",
        animationSettings: { effect: "Zoom" },
      },
      status_tsbtnNhapMoi: false,
      status_tsbtnGhiLai: false,
      status_tsbtnXoa: false,
      status_tsbtnHuyBo: false,
      list_NhomTB: [],
      nhomThietBiSelected: "",
      ghiChuSelected: "",
      checkNhomTB: false,
      nhom_tbi_id: 0,
      rowSelected: null,
      nhomTbi_temp: "",
      indexDx: "",
    };
  },
  computed: {},
  created() {},
  methods: {
    ...mapActions("loaithietbi", [
      "getDSNhomTB",
      "addDuLieuDSTB",
      "getKeyNhomThietBi",
      "updateDuLieuDSTB",
      "deleteDLNTB",
    ]),
    openDialog() {
      this.$refs.popupLoaiThietBi.show();
    },
    dialogOpen() {
      this.SetButton(-1);
      this.fetchDSNhomTB();
      this.fetchGetKey();
    },
    closePopup() {
      this.list_NhomTB = [];
      this.nhomThietBiSelected = "";
      this.ghiChuSelected = "";
    },
    Clean() {
      this.nhomThietBiSelected = "";
      this.ghiChuSelected = "";
    },
    async fetchDeleteDLNTB() {
      if (!this.rowSelected) {
        this.$toast.error("Chưa chọn loại thiết bị ");
        return;
      }

      this.$bvModal
        .msgBoxConfirm(`Bạn có muốn Xóa thiết bị hay không?`, {
          title: "Thông báo",
          size: "sm",
          id: "modal-notice",
          buttonSize: "md",
          okVariant: "primary",
          headerClass: "p-2 pb-0 border-bottom-0",
          footerClass: "p-2 pt-0 border-top-0 justify-content-center",
          centered: true,
          noCloseOnBackdrop: true,
          okTitle: "Đồng ý",
          cancelTitle: "Không đồng ý",
        })
        .then((value) => {
          value && this.excuteDelete();
        });
    },
    async excuteDelete() {
      try {
        this.loading(true);
        let data = {
          nhomTbiId: this.nhom_tbi_id,
        };
        const response = await this.deleteDLNTB(data);
        if (response.data.error_code === "BSS-00000000") {
          this.$toast.success(
            "Xóa nhóm thiết bị trên dữ liệu viễn thông tỉnh thành công!"
          );
        }
      } catch (e) {
        this.$toast.error("Không thể xóa bản ghi này, ", e.data.message_detail);
      } finally {
        this.fetchDSNhomTB();
        this.loading(false);
      }
    },
    async fetchDSNhomTB() {
      try {
        this.loading(true);
        this.list_NhomTB = [];
        const response = await this.getDSNhomTB();
        console.log("Danh sách nhóm thiết bị =", response);
        if (response.data.error_code === "BSS-00000000") {
          this.list_NhomTB = response.data.data;
          // this.$refs.tableNTB.rowClick(0, null);
        }
      } catch (e) {
        console.log("e = ", e);
        this.$toast.error(e.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchGetKey() {
      try {
        this.loading(true);
        let data = {
          phanVungId: this.$root.token.getPhanVungID(),
          keyName: "NHOM_TBI_ID",
          numBlockSize: 0,
          numRetry: 0,
        };
        const response = await this.getKeyNhomThietBi(data);
        // console.log("GetKey", response);
        if (response.data.error_code === "BSS-00000000") {
          this.nhom_tbi_id = response.data.data;
        }
      } catch (e) {
        this.$toast.error(e.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchAddDuLieuDSTB() {
      // try {
      this.loading(true);
      this.nhomTbi_temp = [];
      let data = {
        // nhomTbiId: //this.nhom_tbi_id,
        nhomTbi: this.nhomThietBiSelected,
        ghiChu: this.ghiChuSelected,
      };
      this.nhomTbi_temp = this.nhomThietBiSelected;
      const response = await this.addDuLieuDSTB(data);
      console.log("fetchAddDuLieuDSTB = ", response);
      if (response.data.error_code == "BSS-00000000") {
        this.$toast.success(
          "Thêm mới nhóm thiết bị vào cơ sở dữ liệu tỉnh thành công!"
        );
      }
      // } catch (e) {
      // this.$toast.error(e.data.message);
      // } finally {
      this.loading(false);
      // }
    },
    async fetchUpdateDuLieuDSTB() {
      // try {
      this.loading(true);
      this.nhomTbi_temp = [];
      let data = {
        nhomTbiId: this.nhom_tbi_id,
        nhomTbi: this.nhomThietBiSelected,
        ghiChu: this.ghiChuSelected,
      };
      this.nhomTbi_temp = this.nhomThietBiSelected;
      const response = await this.updateDuLieuDSTB(data);
      console.log("fetchUpdateDuLieuDSTB = ", response);
      if (response.data.error_code == "BSS-00000000") {
        this.$toast.success(
          "Cập nhật nhóm thiết bị vào cơ sở dữ liệu tỉnh thành công!"
        );
      }
      // } catch (e) {
      //   this.$toast.error(e.message);
      // } finally {
      this.loading(false);
      // }
    },
    gridNhomTB_selectedRowChanged(data) {
      if (data) {
        this.SetButton(3);
        this.rowSelected = data;
        this.nhom_tbi_id = data.nhomTbiId;
        this.nhomThietBiSelected = data.nhomTbi;
        this.ghiChuSelected = data.ghiChu;
      }
    },
    SetButton(kieu) {
      this.status_tsbtnNhapMoi = false;
      this.status_tsbtnGhiLai = false;
      this.status_tsbtnXoa = false;
      this.status_tsbtnHuyBo = false;
      if (kieu == -1) {
        //Bat dau

        this.status_tsbtnGhiLai = true;
        // this.status_tsbtnNhapMoi = true;
        // this.status_tsbtnXoa = true;
        this.status_tsbtnHuyBo = true;
      }
      if (kieu == 0) {
        //Bat dau
        this.$refs.txtLoaiThietBi.focus();
        this.status_tsbtnNhapMoi = true;
      }
      if (kieu == 1) {
        //Them moi
        this.$refs.txtLoaiThietBi.focus();
        this.status_tsbtnGhiLai = true;
        this.status_tsbtnHuyBo = true;
      }
      if (kieu == 2) {
        //Huy
        this.status_tsbtnNhapMoi = true;
        // this.status_tsbtnGhiLai = true;
        this.status_tsbtnXoa = true;
      }
      if (kieu == 3) {
        //Edit
        this.status_tsbtnNhapMoi = true;
        this.status_tsbtnXoa = true;
        this.status_tsbtnGhiLai = true;
        this.status_tsbtnHuyBo = true;
      }
    },
    onClickAddNew() {
      this.Clean();
      this.status_tsbtnGhiLai = true;
      this.status_tsbtnHuyBo = true;
      this.status_tsbtnXoa = false;
      this.status_tsbtnNhapMoi = false;
    },
    onChangeForm() {
      this.status_tsbtnHuyBo = true;
      this.status_tsbtnGhiLai = true;
    },
    tsbtnNhapMoi_Click() {
      this.SetButton(1);
      this.Clean();
    },
    tsbtnHuyBo_Click() {
      // this.SetButton(2);
      // this.Clean();
      // if (this.rowSelected) {
      //   this.nhomThietBiSelected = this.rowSelected.nhomTbi;
      //   this.ghiChuSelected = this.rowSelected.ghiChu;
      // }
      this.$refs.tableNTB.selectRow(3);
    },
    KiemTra_DuLieu() {
      if (!this.nhomThietBiSelected) {
        this.$toast.error("Nhóm thiết bị không được để trống !");
        return (this.checkNhomTB = false);
      }
      return (this.checkNhomTB = true);
    },
    async tsbtnGhiLai_Click() {
      this.KiemTra_DuLieu();
      if (this.checkNhomTB) {
        if (this.status_tsbtnNhapMoi === false) {
          console.log("Add");
          await this.fetchAddDuLieuDSTB();
        } else {
          console.log("Update");
          await this.fetchUpdateDuLieuDSTB();
        }
        await this.fetchDSNhomTB();
        if (this.list_NhomTB.length > 0) {
          for (let index = 0; index < this.list_NhomTB.length; index++) {
            const el = this.list_NhomTB[index];
            if (el.nhomTbi == this.nhomTbi_temp) {
              console.log("el.nhomTbi = ", el.nhomTbi);
              console.log("this.nhomTbi_temp = ", this.nhomTbi_temp);
              console.log("index = ", index);
              this.indexDx = index;
              // this.$refs.tableNTB.selectRow(index);
              break;
            }
          }
        }
        // this.$refs.tableNTB.selectRow(3);
        this.SetButton(3);
      }
    },
    gridNhomThietBiSelectRow() {
      this.$refs.tableNTB.selectRow(this.indexDx);
      this.indexDx = 0;
    },
  },
  mounted() {},
  destroyed() {},
  computed: {},
};
</script>
