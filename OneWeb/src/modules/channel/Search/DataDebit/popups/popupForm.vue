<template src="./popupForm.html"></template>
<script>
import VueDateExt from "@/modules/contract/setup/DeclareLandline/components/VueDateExt";
import Select2Ext from "@/modules/contract/setup/DeclareLandline/components/Select2Ext";
import moment from "moment";
export default {
  data() {
    return {
      resizeHandles: ["All"],
      draggable: true,
      sgt: "",
      enterpriseName: "",
    };
  },
  components: {
    VueDateExt,
    Select2Ext,
  },
  props: ["selected"],
  methods: {
    async showDialog() {
      await this.getDanhmuc();
      
      this.$refs.ngaytt.value = moment().format("DD/MM/YYYY");
      this.$refs.ngaycn.value = moment().format("DD/MM/YYYY");
      this.$refs.comboNhamang.value = this.$refs.comboNhamang.dataSource.filter(
        (x) => x.tendaydu == this.enterpriseName
      )[0].chuquan_id;
      this.$refs.popupForm.show();
    },
    closePopup() {
      this.$refs.popupForm.hide();
    },
    async submitForm() {
      try {
        let files = this.$refs.file_upload.files;
        if (!this.$refs.ngaytt.value) {
          this.$toast.error("Ngày thanh toán không được để trống!");
          return;
        }
        var url = "";
        if (files.length > 0) {
          let formData = new FormData();
          formData.append("files", files[0]);
          let rs = await this.axios.post("/web-quantri/upload", formData, {
            headers: { "Content-Type": "multipart/form-data" },
          });
          url = rs.data.data[0];
        }

        if (!url) {
          this.$toast.error("File không được để trống!");
          return;
        }

        let chuquan_id = this.$refs.comboNhamang.value;
        let ngaycn = this.$refs.ngaycn.value;
        let ngaytt = this.$refs.ngaytt.value;
        let sgt = this.sgt;
        let hscvt_id;
        let data = await this.axios.post("web-tracuu/tracuunocuoc/get_keys_v4", {
          keyname: "HSCVT_ID",
        });
        hscvt_id = data.data.data;

        this.axios
          .post("web-tracuu/tracuunocuoc/fn_ins_update_hs_cucvt", {
            p_ds_para: JSON.stringify({
              HS_CUCVT: [
                {
                  SO_GT: sgt,
                  CHUQUAN_ID: chuquan_id,
                  URL: [{link : url}],
                  NGAY_TT: ngaytt,
                  HSCVT_ID: hscvt_id,
                },
              ],
              KIEU_TH: 1,
            }),
            p_tt_nd: JSON.stringify({
              NGUOI_CN: this.$root.token.getUserName(),
              NGUOIDUNG_ID: this.$root.token.getNguoiDungID(),
              NHANVIEN_ID: this.$root.token.getNhanVienID(),
              MAY_CN: await this.$root.token.getMachine(),
              IP_CN: await this.$root.token.getIP(),
            }),
          })
          .then((rs) => {
            this.$refs.file_upload.value = null;
            this.$refs.popupForm.hide();
            this.$toast.success("Đã cập nhật thành công");
            this.$emit("submit");
          });
      } catch (error) {
        this.$toast.error(error.message);
      }
    },
    async getDanhmuc() {
      return new Promise(async (rs, rj) => {
        let data = await this.axios.post(
          "web-tracuu/tracuunocuoc/sp_lay_danhmuc_hs_cucvt",
          {
            p_tt_bien: JSON.stringify({
              DANHMUC: "NHACC_CU",
            }),
          }
        );
        this.$refs.comboNhamang.HT_COMBOBOX(data.data.data, "tenchuquan", "chuquan_id");

        rs();
      });
    },
  },
};
</script>
