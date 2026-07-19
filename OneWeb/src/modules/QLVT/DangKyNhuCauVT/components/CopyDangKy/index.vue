<template src='./index.html'></template>
<style src='./index.scss' scoped></style>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import breadcrumb from "@/components/breadcrumb";
export default {
  components: { breadcrumb },
  name: "CopyDangKy",
  created: function () {},
  props: ["tag"],
  mounted() {},
  computed: {},
  watch: {
    // data: async function (data) {
    //   handler: {
    //     console.log("Props data", data);
    //     if (data.toString() != "0") {
    //       await this.fetchDSDangKy_frmCopy(this.tag);
    //     }
    //   }
    // },
  },
  data() {
    return {
      header: {
        title: "NGHIỆM THU ĐƠN HÀNG",

        list: [
          {
            name: "Quản lí vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Nghiệm thu đơn hàng",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      // tag: 99,
      xacnhan: false,
      // dk_id: 0,
      kieu: 0,
      listDK: [],
      listVatTuDaDK: [],
      listVatTuDaDK_temp: [],
      animationSettings: { effect: "Zoom" },
      target: ".main-wrapper",
      editSettings: { allowEditing: true, mode: "Batch" },
      ok: [],
    };
  },
  methods: {
    ...mapActions("dangkynhucauVT", ["getDSDangKy_frmCopy"]),
    showPopupNhapNgayDuyet() {
      this.$bvModal.show("popupCopyDangKy");
    },
    async fetchDSDangKy_frmCopy(_tag) {
      this.loading(true);
      this.listVatTuDaDK = [];
      let data = {
        tagForm: _tag.toString() == "1" || _tag.toString() == "2" ? _tag : 99,
        kieu: 0,
        donViId: this.$root.token.getDonViID(),
        dangKyId: 0,
      };
      console.log("fromCopy", data);
      const response = await this.getDSDangKy_frmCopy(data);
      console.log("DS DS Đăng Ký fromCopy", response);
      if (response.error_code === "BSS-00000000") {
        this.listDK = response.data;
      }
      this.loading(false);
    },
    async fetchDSVatTuDaDK_frmCopy(_dangKyId) {
      this.loading(true);
      let data = {
        tagForm: 1,
        kieu: 1,
        donViId: this.$root.token.getDonViID(),
        dangKyId: _dangKyId,
      };
      const response = await this.getDSDangKy_frmCopy(data);
      console.log("Danh sách vật tư đã đăng ký", response);
      if (response.error_code === "BSS-00000000") {
        this.listVatTuDaDK = response.data;
      }
      this.loading(false);
    },
    async listDK_selectedRowChanged(data) {
      console.log("listDK_selectedRowChanged", data);
      this.ok = [];
      if (data) {
        await this.fetchDSVatTuDaDK_frmCopy(data.DANGKY_ID);
        this.listDK.forEach((el) => {
          if (el.DANGKY_ID == data.DANGKY_ID) {
            this.ok.push(el);
          }
        });
        console.log("Son List ok", this.ok);
      }
    },
    listVatTuDaDK_selectedRowChanged(data) {
      console.log("listVatTuDaDK_selectedRowChanged", data);
    },
    Close() {
      this.$parent.hide();
    },
    ghilai() {
      if (this.listDK.length > 0) {
        this.xacnhan = true;
        this.Close();
      } else {
        this.$toast.error("Không có dữ liệu hiển thị !");
      }
    },
    async dialogOpen() {
      await this.fetchDSDangKy_frmCopy(this.tag);
    },
    onClickThoat() {
      this.Close();
    },
    Open() {
      this.$refs.popupCopyDangKy.show();
    },
    Close() {
      this.$refs.popupCopyDangKy.hide();
      this.$parent.tsbtnCopyDK_Click();
    },
    async editGridVatTuDaDK(arg) {
      // console.log("Cột thay đổi", arg);
      // console.log("Trường thay đổi", arg.value);
      if (arg.columnName == "SOLUONG") {
        if (arg.value) {
          this.listVatTuDaDK.forEach((el) => {
            if (el.VTDK_ID === arg.rowData.VTDK_ID) {
              this.listVatTuDaDK_temp.push(arg.rowData);
            } else {
              this.listVatTuDaDK_temp.push(arg.rowData);
            }
          });
        }
      }
      console.log("this.listVatTuDaDK_temp", this.listVatTuDaDK_temp);
    },
  },
};
</script>
