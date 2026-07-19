<style scoped>
.list-actions-top .list > li > div.button {
  display: block;
  padding: 2px 15px;
  color: #0176ff;
  border-left: 1px solid #bae7ff;
  text-decoration: none;
  font-weight: 600;
  cursor: pointer;
}
</style>
<template src="./frmBRNGuiMail.html"></template>
<script>
import api from "./PopUpApi";

export default {
  components: {
    api,
  },
  name: "frmBRNGuiMail",
  data() {
    return {
      Loading: false,
      target: "#app", //  .main-wrapper-payment
      targetTop: "#app", // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: "Zoom" },
      position: { X: "center", Y: "top" },
      showTimePanel: false,
      tabIndex: 0,

      tt_nd: {
        nhanvien_id: this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: this.$root.token.getMachine(),
        ip_cn: this.$root.token.getIP(),
        ten_dv: "",
        nguoi_cn: this.$root.token.getUserName(),
      },

      state: { hdtb_id: 0 },
      controls: {
        txtNguoiNhan: "",
        txtTieuDe: "",
        txtNoiDung: "",
        txtTrangThaiGui: "",
        cboFileGui: 0,
        txtUsername: "",
        txtPassword: "",
      },

      tsbtnGuiEmail: { Enabled: false },
      cboFileGuiData: [],

      smtpServer: "",
      smtpUser: "",
      smtpPass: "",
      smtpPort: "",
      smtpAlias: "",
      mailsend: "",
      sendmail: false,
      flag_mail: 0,

      agent_id: 0,
      adser_id: 0,
      contract_id: 0,
      agent_type_id: 0,
      contract_type_id: 0,
      label_id: 0,
    };
  },
  created: async function () {},
  mounted: function () {},
  watch: {},
  methods: {
    dialogClose() {},
    openDialog(data) {
      if (!data) {
        return;
      }

      this.state = {
        hdtb_id: data.hdtb_id,
      };
      console.log(this.state);
      this.$refs.dialogFrmBRNGuiMail.show();
      this.frm_BRN_GuiMail_Load();
    },

    hideDialog() {
      this.$refs.dialogFrmBRNGuiMail.hide();
    },

    async frm_BRN_GuiMail_Load() {
      try {
        this.loading(true);
        let { hdtb_id } = this.state;

        //load tiêu đề, người nhận, nội dung
        let res = await api.lay_thong_tin_gui_mail(this.axios, hdtb_id);
        let { data } = res.data;
        if (data) {
          this.controls = {
            ...this.controls,
            txtNguoiNhan: data.nguoi_nhan,
            txtTieuDe: data.tieu_de,
            txtNoiDung: data.noi_dung,
            cboFileGui: data.file_path,
          };

          this.cboFileGuiData = [
            {
              FILE_NAME: data.file_name,
              FILE_PATH: data.file_path,
            },
          ];
        }

        res = await api.sp_load_dulieu_form_tao_brn(this.axios, hdtb_id);
        data = res.data.data;
        if (data && data.length > 0) {
          this.agent_id = data[0].agent_id * 1;
          this.adser_id = data[0].adser_id * 1;
          this.contract_id = data[0].contract_id * 1;
          this.agent_type_id = data[0].agent_type_id * 1;
          this.contract_type_id = data[0].contract_type_id * 1;
          this.label_id = data[0].label_id * 1;
        }

        res = await api.sp_lay_thongtin_mail_server_smsbrn(this.axios);
        data = res.data.data;
        if (data && data.length > 0) {
          this.smtpServer = data[0].server;
          this.smtpUser = this.controls.txtUsername;
          this.smtpPass = this.controls.txtPassword;
          this.smtpPort = data[0].port;
          this.smtpAlias = this.controls.txtUsername;

          if (this.smtpServer != "" && this.smtpPort != "")
            this.tsbtnGuiEmail.Enabled = true;

          if (data[0].flag) this.flag_mail = data[0].flag * 1;
        }
      } catch (ex) {
        console.error(ex);
        this.showError(ex);
      } finally {
        this.loading(false);
      }
    },

    async tsbtnGuiEmail_Click(e) {
      try {
        this.loading(true);
        let confirm = await this.$confirm(
          `Bạn có chắc chắn muốn thực hiện?`,
          "Thông báo",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
          }
        )
          .then(() => true)
          .catch(() => false);
        if (!confirm) {
          return;
        }

        let flag = false;
        //gui mail
        try {
          /*
              str_id : mã đối tượng
              sTo: email
              str5: id email
              đường dẫn  ; mail + mail2 + khachhang_id
           */

          let num1 = 0;
          let num2 = 0;

          let sTo = this.controls.txtNguoiNhan.replaceAll(" ", "").replaceAll(";", ",");
          if (sTo == "") {
            this.ShowError("Địa chỉ email trống");
            flag = true;
            return;
          } else {
            let file = null;
            for (const tmp of this.cboFileGuiData) {
              if (tmp.FILE_PATH == this.controls.cboFileGui) {
                file = tmp;
                break;
              }
            }
            let rs = await api.gui_mail(this.axios, {
              mailFrom: this.controls.txtUsername,
              mailTo: sTo,
              mailServerHost: this.smtpServer,
              mailServerPort: this.smtpPort,
              username: this.controls.txtUsername,
              password: this.controls.txtPassword,
              subject: this.controls.txtTieuDe,
              message: this.controls.txtNoiDung,
              fileName: file ? file.FILE_NAME : "",
              filePath: this.controls.cboFileGui,
            });

            if (
              rs.data != null &&
              rs.data.error === "200" &&
              rs.data.error_code != "BSS-00000500"
            ) {
              this.hideDialog();
            } else {
              this.ShowError("Không gửi được(!))");
              flag = true;
              return;
            }
          }
        } catch (ex) {
          flag = true;
          console.log(ex);
        }

        if (flag) {
          this.ShowError("Có lỗi trong quá trình thực hiện");
        } else {
          this.ShowSuccess("Đã thực hiện xong");
        }
      } catch (ex) {
        console.error(ex);
        this.showError(ex);
      } finally {
        this.loading(false);
      }
    },

    ShowAlert: function (msg) {
      this.$toast.warning(msg);
    },

    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },

    ShowError: function (msg) {
      this.$toast.error(msg);
    },
  },
};
</script>

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>
