<template>
  <b-modal
    id="popup-cauHinhSMTP"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    @show="onShow"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon -ap icon-apps text-main"></span>Cấu hình SMTP
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="$bvModal.hide('popup-cauHinhSMTP')"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript: void(0)" @click="onCapNhapClicked">
              <span class="icon one-reload1"></span> Cập nhật
            </a>
          </li>
          <li>
            <a href="javascript: void(0)" @click="onKiemTraKetNoiClicked">
              <span class="icon one-connect1"></span> Kiểm tra kết nối
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin SMTP</div>
          <div class="info-row">
            <div class="key w70">Server</div>
            <div class="value">
              <input
                type="text"
                class="form-control"
                ref="txtServer"
                v-model="pTxtServer"
              />
            </div>
          </div>
          <div class="info-row">
            <div class="key w70">Username</div>
            <div class="value">
              <input
                type="text"
                class="form-control"
                ref="txtUsername"
                v-model="pTxtUsername"
              />
            </div>
          </div>
          <div class="row">
            <div class="col-sm-8 col-12">
              <div class="info-row">
                <div class="key w70">Password</div>
                <div class="value">
                  <input
                    type="password"
                    class="form-control"
                    ref="txtPassword"
                    v-model="pTxtPassword"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w40">Port</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    ref="txtPort"
                    v-model="pTxtPort"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w70">Alias</div>
            <div class="value">
              <input
                type="text"
                class="form-control"
                ref="txtAlias"
                v-model="pTxtAlias"
              />
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Thông tin liên hệ</div>
          <div class="info-row">
            <div class="key w70">Địa chỉ</div>
            <div class="value">
              <input
                type="text"
                class="form-control"
                ref="txtDiaChiLienHe"
                v-model="pTxtDiaChiLienHe"
              />
            </div>
          </div>
          <div class="info-row">
            <div class="key w70">Email</div>
            <div class="value">
              <input
                type="text"
                class="form-control"
                ref="txtMailLienHe"
                v-model="pTxtMailLienHe"
              />
            </div>
            <div class="key w80 padl10">Điện thoại</div>
            <div class="value">
              <input
                type="text"
                class="form-control"
                ref="txtSDTLienHe"
                v-model="pTxtSDTLienHe"
              />
            </div>
          </div>

          <!-- <div class="info-row">
            <div class="key w70">Điện thoại</div>
            <div class="value">
              <input
                type="text"
                class="form-control"
                ref="txtSDTLienHe"
                v-model="pTxtSDTLienHe"
              />
            </div>
          </div> -->

          <div class="info-row">
            <div class="key w70">Thông tin</div>
            <div class="value">
              <input
                type="text"
                class="form-control"
                ref="txtSDTLienHe"
                v-model="pTxtThongTin"
              />
            </div>
          </div>
          <div class="info-row">
            <div class="key w70">TT chuyển khoản</div>
            <div class="value">
              <textarea v-model="pTxtChuyenKhoan" class="form-control"
                style="height: 80px;resize: vertical;"></textarea>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import { mapActions, mapState } from "vuex";
import api from "../../api/TestEmailApi";
export default {
  name: "CauHinhSMTP",
  props: ['idNhanVien'],
  components: {},
  data() {
    return {
      pCauHinhSMTP: null,

      pTxtServer: "",
      pTxtUsername: "",
      pTxtPassword: "",
      pTxtPort: "",
      pTxtAlias: "",
      pTxtDiaChiLienHe: "",
      pTxtMailLienHe: "",
      pTxtSDTLienHe: "",
      pTxtThongTin: "",
      pTxtChuyenKhoan:"",

      API_SUCCESS: "BSS-00000000",
    };
  },
  created() {},
  computed: {
    ...mapState("reg", [
      "SMTP_CSKH_SERVER",
      "SMTP_CSKH_USERNAME",
      "SMTP_CSKH_PASSWORD",
      "SMTP_CSKH_PORT",
      "SMTP_ALIAS",
      "SMTP_CSKH_DCLH",
      "SMTP_CSKH_EMAIL",
      "SMTP_CSKH_SDT",
    ]),
  },
  mounted() {},
  methods: {
    ...mapActions("reg", ["setSMTPInfor"]),
    async onShow() {
      try {
        this.$root.showLoading(true);

        await this.getCauHinhSMTP();

        let strServer = "",
          strUsername = "",
          strPassword = "",
          strPort = "",
          sAlias = "",
          strEmail_LD = "",
          strSDT_LD = "",
          strDiaChi_LD = "",
          strThongTin = "",
          strChuyenKhoan = "";
        if (this.pCauHinhSMTP && this.pCauHinhSMTP.length > 0) {
          strServer = this.pCauHinhSMTP[0].SERVER;
          strUsername = this.pCauHinhSMTP[0].USERNAME;
          strPassword = this.pCauHinhSMTP[0].PASSWORD;
          strPort = this.pCauHinhSMTP[0].PORT;
          sAlias = this.pCauHinhSMTP[0].ALIAS;
          strEmail_LD = this.pCauHinhSMTP[0].EMAIL;
          strSDT_LD = this.pCauHinhSMTP[0].SO_DT;
          strDiaChi_LD = this.pCauHinhSMTP[0].DIACHI_LH;
          strThongTin = this.pCauHinhSMTP[0].MOTA;
          strChuyenKhoan = this.pCauHinhSMTP[0].CHUYEN_KHOAN;
        }

        const objSMTP = localStorage.getItem('KEY_SMTP')
        const valueSMTP = JSON.parse(objSMTP)

        this.pTxtServer = (valueSMTP && valueSMTP.SMTP_CSKH_SERVER) ? valueSMTP.SMTP_CSKH_SERVER : this.SMTP_CSKH_SERVER
          ? this.SMTP_CSKH_SERVER
          : strServer;
        this.pTxtUsername = (valueSMTP && valueSMTP.SMTP_CSKH_USERNAME) ? valueSMTP.SMTP_CSKH_USERNAME : this.SMTP_CSKH_USERNAME
          ? this.SMTP_CSKH_USERNAME
          : strUsername;
        this.pTxtPassword = (valueSMTP && valueSMTP.SMTP_CSKH_PASSWORD) ? valueSMTP.SMTP_CSKH_PASSWORD : this.SMTP_CSKH_PASSWORD
          ? this.SMTP_CSKH_PASSWORD
          : strPassword;
        this.pTxtPort = (valueSMTP && valueSMTP.SMTP_CSKH_PORT) ? valueSMTP.SMTP_CSKH_PORT : this.SMTP_CSKH_PORT ? this.SMTP_CSKH_PORT : strPort;
        this.pTxtAlias = (valueSMTP && valueSMTP.SMTP_ALIAS) ? valueSMTP.SMTP_ALIAS : this.SMTP_ALIAS ? this.SMTP_ALIAS : sAlias;
        this.pTxtDiaChiLienHe = (valueSMTP && valueSMTP.SMTP_CSKH_DCLH) ? valueSMTP.SMTP_CSKH_DCLH : this.SMTP_CSKH_DCLH || strDiaChi_LD;
        this.pTxtMailLienHe = (valueSMTP && valueSMTP.SMTP_CSKH_EMAIL) ? valueSMTP.SMTP_CSKH_EMAIL : this.SMTP_CSKH_EMAIL
          ? this.SMTP_CSKH_EMAIL
          : strEmail_LD;
        this.pTxtSDTLienHe = (valueSMTP && valueSMTP.SMTP_CSKH_SDT) ? valueSMTP.SMTP_CSKH_SDT : this.SMTP_CSKH_SDT
          ? this.SMTP_CSKH_SDT
          : strSDT_LD;
        this.pTxtThongTin = (valueSMTP && valueSMTP.SMTP_CSKH_THONGTIN) ? valueSMTP.SMTP_CSKH_THONGTIN : strThongTin
        this.pTxtChuyenKhoan = (valueSMTP && valueSMTP.SMTP_CSKH_CHUYENKHOAN) ? valueSMTP.SMTP_CSKH_CHUYENKHOAN : strChuyenKhoan
        this.$refs.txtServer.focus();
      } catch (e) {
        console.log(e);
      } finally {
        this.$root.showLoading(false);
        this.$refs.txtServer.focus();
      }
    },
    async getCauHinhSMTP() {
      try {
        let rs = await this.$root.context.get(
          "/web-cskh/api/in-tong-hop-khl/cau-hinh-smtp",
          {
            nhanVienId: this.idNhanVien ? this.idNhanVien : this.$root.token.getNhanVienID(),
          }
        );
        if (rs.error_code === this.API_SUCCESS) {
          this.pCauHinhSMTP = rs.data.data;
        }
      } catch (e) {
        console.log(e);
      }
    },
    async onCapNhapClicked() {
      try {
        this.$root.showLoading(true);
        let data = {
          SMTP_CSKH_SERVER: this.pTxtServer,
          SMTP_CSKH_USERNAME: this.pTxtUsername,
          SMTP_CSKH_PASSWORD: this.pTxtPassword,
          SMTP_CSKH_PORT: this.pTxtPort,
          SMTP_ALIAS: this.pTxtAlias,

          SMTP_CSKH_DCLH: this.pTxtDiaChiLienHe,
          SMTP_CSKH_EMAIL: this.pTxtMailLienHe,
          SMTP_CSKH_SDT: this.pTxtSDTLienHe,
          SMTP_CSKH_THONGTIN: this.pTxtThongTin,
          SMTP_CSKH_CHUYENKHOAN: this.pTxtChuyenKhoan
        };
        this.setSMTPInfor(data);
        localStorage.setItem('KEY_SMTP',JSON.stringify(data))
        const res = await this.$root.context.post(
          "/web-cskh/api/in-tong-hop-khl/thiet-lap-in-tong-hop",
          {
            diaChiLH: this.pTxtDiaChiLienHe,
            moTa: this.pTxtThongTin,
            chuyenKhoan: this.pTxtChuyenKhoan
          })
        if (res.error_code == "BSS-00000000") {
        //   this.$toast.success(message);
          this.$root.toastSuccess("Cập nhật thành công!");
        } else {
          this.$toast.error(res.message_detail);
        }
      } catch (e) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    async onKiemTraKetNoiClicked() {
      let username = this.pTxtUsername
      if((!/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z0-9]+\.)+[a-zA-Z]{2,}))$/.test(this.pTxtUsername)) && this.pTxtUsername.length > 0) {
        username = username + '@' + this.pTxtServer
      }
      this.$root.showLoading(true);
      let toMail = [];
      toMail.push(this.pTxtAlias);

      let body = {
        to: toMail,
        cc: [],
        subject: "Kiểm tra cấu hình mail server",
        body: "Cấu hình thành công!",
        host: this.pTxtServer,
        port: this.pTxtPort,
        username: username,
        password: this.pTxtPassword,
      };
      console.log(body)

      try {
        var res = await api.kiemTraKetNoi(this.axios, body);
        if (res.data.error == 200) {
          this.$toast.success(message);
        } else {
          this.$toast.error(res.data.data);
        }
        this.$root.showLoading(false);
      } catch (error) {
        this.$toast.error(error.data.message_detail);
        this.$root.showLoading(false);
      }
    },
  },
};
</script>
