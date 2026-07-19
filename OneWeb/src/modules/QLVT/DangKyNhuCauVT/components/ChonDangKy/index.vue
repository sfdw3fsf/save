<template src='./index.html'></template>
<style src="./index.scss"></style>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import moment from "moment";

export default {
  components: {},
  name: "ChonDangKy",
  created: function () {},
  props: ["tag", "vdangky_id"],
  mounted() {},
  computed: {},
  watch: {
    checkListDSDK: async function (data) {
      if (data) {
        this.show_XacNhan = true;
      } else {
        this.show_XacNhan = false;
      }
    },
  },
  data() {
    return {
      // tag: 99,
      xacnhan: false,
      // dk_id: 0,
      kieu: 0,
      listDK: [],
      listChiTietVatTu: [],
      listChiTietVatTu_temp: [],
      animationSettings: { effect: "Zoom" },
      target: ".main-wrapper",
      editSettings: { allowEditing: true, mode: "Batch" },
      ok: [],
      isTab: false,
      tuNgay: new Date(new Date().getTime() - 15 * 24 * 60 * 60 * 1000), //trừ 15 ngày
      denNgay: new Date(),
      checkListDSDK: [],
      show_XacNhan: false,
      show_HuyBo: false,
      show_ChapNhan: false,
      status_ChapNhan: false,
      status_TABDSDK: false,
      checkListDSChiTietVT: [],
      bodyDSChiTietVT: [],
    };
  },
  methods: {
    ...mapActions("dangkynhucauVT", [
      "getDSDangKY_CDK",
      "getDSChiTietDangKY_CDK",
      "chapNhanDangKy",
    ]),
    showPopupNhapNgayDuyet() {
      this.$bvModal.show("popupChonDangKy");
    },
    f_StringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },

    datetoString(value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },

    async fetchDSDangKy_frmCopy() {
      this.loading(true);
      try {
        this.listChiTietVatTu = [];
        let data = {
          vkieu_dk: this.tag,
          vngay_dk_den: moment(this.denNgay).format("DD/MM/YYYY"),
          vngay_dk_tu: moment(this.tuNgay).format("DD/MM/YYYY"),
          vnhanvien_id: this.$root.token.getNhanVienID(),
        };
        const response = await this.getDSDangKY_CDK(data);
        this.listDK = response;
      } catch (ex) {
        this.$toast.error(ex.data.message);
      } finally {
        this.loading(false);
      }
    },

    async fetchDSChiTietDangKy(_vdangky_ms_id) {
      this.loading(true);
      try {
        let myArray = _vdangky_ms_id.map((str) => ({ DANGKY_ID: str }));
        console.log("myArray == ", myArray);
        let data = {
          vds_dk_id: myArray,
          vkieu_dk: this.tag,
        };
        console.log("fetchDSChiTietDangKy data == ", data);
        const response = await this.getDSChiTietDangKY_CDK(data);
        console.log("DS Đăng Ký ", response);
        this.listChiTietVatTu = response;
      } catch (ex) {
        this.$toast.error(ex.data.message);
      } finally {
        this.loading(false);
      }
    },
    async fetchChapNhanDangKy(_body, _vkieu) {
      this.loading(true);
      try {
        let data = {
          vdangky_id: this.vdangky_id,
          vjson: _body,
          vkieu: _vkieu,
        };
        console.log("chapNhanDangKy data == ", data);
        const response = await this.chapNhanDangKy(data);
        if (response == "1") {
          this.$toast.success("Chọn đăng ký thành công!");
        }
      } catch (ex) {
        this.$toast.error(ex.data.message);
      } finally {
        this.loading(false);
      }
    },

    async btnChapNhan() {
      this.loading(true);
      let result = [];
      this.bodyDSChiTietVT = [];
      if (this.checkListDSChiTietVT) {
        for (const i of this.checkListDSChiTietVT) {
          console.log("i = ", i);
          result = this.listChiTietVatTu.find((str) => i == str.DANGKY_ID);
          console.log("result = ", result);

          this.bodyDSChiTietVT.push({
            VTDK_ID: result.VTDK_ID,
            MAPHIEU_DK: result.MAPHIEU_DK,
            DANGKY_ID: result.DANGKY_ID,
            SL_CL: result.SL_CL,
            VATTU_ID: result.VATTU_ID,
            TEN_VT: result.TEN_VT,
            DVI_TINH: result.DVI_TINH,
            SL_DANGKY: result.SL_CL,
          });
        }
      }
      await this.fetchChapNhanDangKy(this.bodyDSChiTietVT, 1);
      this.$refs.popupChonDangKy.hide();
      this.loading(false);
    },

    async listVat_selectedRowChanged(data) {},

    async listDK_selectedItemChanged(data) {
      this.checkListDSDK = data;
    },

    listChiTietVatTu_selectedRowChanged(data) {
      console.log("this.listChiTietVatTu_selectedRowChanged == ", data);
    },

    listChiTietVatTu_selectedItemChanged(data) {
      this.checkListDSChiTietVT = data;
      console.log("this.checkListDSChiTietVT == ", this.checkListDSChiTietVT);
      if (data) {
        this.show_ChapNhan = true;
      } else {
        this.show_ChapNhan = false;
      }
    },
    async btnXacNhan() {
      await this.fetchDSChiTietDangKy(this.checkListDSDK);
      this.show_HuyBo = true;
      this.isTab = true;
      this.status_TABDSDK = true;
      this.listDK = [];
    },
    async btnHuyBo() {
      this.isTab = false;
      this.show_XacNhan = false;
      this.show_HuyBo = false;
      this.status_TABDSDK = false;
      this.listChiTietVatTu = [];
      await this.fetchDSDangKy_frmCopy();
    },
    async dialogOpen() {
      await this.fetchDSDangKy_frmCopy();
    },
    onClickThoat() {
      this.Close();
    },
    Open() {
      this.$refs.popupChonDangKy.show();
    },

    Close() {
      this.$refs.popupChonDangKy.hide();
      this.$parent.chonDK_visible = false;
      this.listDK = [];
      this.listChiTietVatTu = [];
    },

    changeTab(data) {
      if (data == 1) {
        this.isTab = true;
        this.listChiTietVatTu = [...this.listChiTietVatTu];
      } else {
        this.isTab = false;
        this.listDK = [...this.listDK];
      }
    },
    async editGridSLDK(arg) {
      try {
        console.log("editGridSLDK = ", arg);
        let f = this.$refs.listChiTietVatTu_Ref;
        let changes = f.getBatchChanges();
        if (changes.changedRecords.length == 0) {
          console.log("changedRecords = ", changes);
          return;
        }
        if (arg.columnName == "SL_DK") {
          if (
            parseFloat(changes.changedRecords[0].SL_DK) >=
            parseFloat(changes.changedRecords[0].SL_CL)
          ) {
            this.status_ChapNhan = false;
            this.$root.toastError("Số lượng đăng ký vượt quá số lượng sẵn có!");
          } else if (parseFloat(changes.changedRecords[0].SL_DK) < 0) {
            this.status_ChapNhan = false;
            this.$root.toastError("Số lượng đăng ký phải lớn hơn 0!");
          } else {
            this.status_ChapNhan = true;

            //check lại để gán theo cột SL Đăng Ký
            for (var e of this.listChiTietVatTu) {
              if (e.DANGKY_ID == changes.changedRecords[0].DANGKY_ID) {
                e.SL_DK = changes.changedRecords[0].SL_DK;
              }
            }
            if (
              !this.checkListDSChiTietVT.includes(
                changes.changedRecords[0].DANGKY_ID
              )
            ) {
              this.checkListDSChiTietVT.push(
                changes.changedRecords[0].DANGKY_ID
              );
            }
          }
        }
      } catch (e) {
        console.log("editGridSLDK error = ", e);
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
  },
};
</script>
