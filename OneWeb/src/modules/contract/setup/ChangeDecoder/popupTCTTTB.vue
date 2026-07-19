<template>
  <b-modal
  id="popupTCTTTB"
    ref="popupTCTTTB"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">Thay đổi bộ giải mã Account MyTV</div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="info-row">
            <div class="key w120">Account (*)</div>
            <div class="value">
              <input
                type="text"
                class="form-control"
                v-model="txtAcount"
                ref="inputAccount"
              />
            </div>
          </div>
          <div class="info-row">
            <div class="key w120">Ngày thay đổi</div>
            <div class="value">
              <div class="input-icon-right">
                <ejs-datepicker
                  v-model="ngaythaydoi"
                  :value="today"
                  :format="'dd/MM/y HH:mm:ss'"
                  :showClearButton="false"
                  :placeholder="'Ngày thay đổi'"
                  :readonly="true"
                >
                </ejs-datepicker>
                <span class="icon one-calendar"></span>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w120">Lý do thay đổi (*)</div>
            <div class="value">
              <textarea
                class="form-control"
                style="height: 100px; resize: none"
                v-model="txtLyDo"
                ref="inputLyDo"
              ></textarea>
            </div>
          </div>
          <div class="group-buttons -bottom center">
            <button class="btn btn-second nocorner" @click="btnGhiLai">
              <span class="inline vcenter f20 one-sync"></span> Thay đổi
            </button>
            <button
              class="btn btn-outline-secondary nocorner"
              @click="btnThoat"
            >
              <span class="inline vcenter f20 one-export"></span> Thoát
            </button>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from "@/components/ActionTop.vue";
import breadcrumb from "@/components/breadcrumb";
export default {
  name: "popupTCTTTB",
  components: { breadcrumb, ActionTop },
  data() {
    return {
      txtAcount: "",
      ngaythaydoi: new Date(),
      txtLyDo: "",
      today: new Date(),
    };
  },
  props: {
    open: Boolean,
  },
  methods: {
    showModal() {
      this.$refs["popupTCTTTB"].show();
    },
    hideModal() {
      this.$refs["popupTCTTTB"].hide();
    },
    async btnThoat() {
      this.hideModal();
    },
    handleShowModal(){},
    async btnGhiLai() {
      if(this.txtAcount.trim() == ""){
        this.$toast.error("Vui lòng nhập thông tin account!");
        this.$refs.inputAccount.focus();
        return false;
      }
      if(this.txtLyDo.trim() == ""){
        this.$toast.error("Vui lòng nhập lý do thay đổi");
        this.$refs.inputLyDo.focus();
        return false;
      }
      if(this.txtLyDo.length >1000){
        this.$toast.error("Trường Lý do thay đổi nhập dữ liệu vượt quá độ dài cho phép là 1000 ký tự!");
        return false;
      }
      this.loading(true);
      let changestb = await this.$root.context.post("/tichhop/vasc/changeSTB",
      {
        iptvaccount: this.txtAcount.trim(),
        reason: this.txtLyDo.trim(),
      });
      if (changestb.errorCode === 0) {//==0
        let strGiaTri =
          this.txtAcount.trim() + ";THAYDOI_STB,LYDO:" + this.txtLyDo.trim();
        let mats = await this.$root.context.post("/web-ccdv/ds_yeucau_khaosat/fn_lay_ds_thamso_md_mats",
          {
            ma_ts: "TAOHD_THAYDOI_BOGIAIMA",
          }
        );
        if (mats.error_code === "BSS-00000000" && mats.data === 1) {
          let thaydoi_bgm = await this.$root.context.post("/web-ccdv/bogiaima_account_mytv/capnhat_thaydoi_bgm",
            {
              vghichu: this.txtLyDo.trim(),
              vma_tb: this.txtAcount.trim(),
            }
          );
          if (
            thaydoi_bgm.error_code === "BSS-00000000" &&
            thaydoi_bgm.data !== undefined
          ) {
          }
        }
        let rslog = await this.$root.context.post("/web-ccdv/bogiaima_account_mytv/capnhat_log_td_mytv",
          {
            vgiatri: strGiaTri,
            vkieu: 0,
          }
        );
        if (rslog.error_code === "BSS-00000000" && rslog.data !== undefined) {
          this.$toast.success("Thay đổi bộ giải mã cho thuê bao " +this.txtAcount.trim() +" thành công !");
          this.txtAcount = "";
          this.txtLyDo = "";
          this.$refs.inputAccount.focus();
        } else {
          this.$toast.error(rslog.message_detail);
        }
      }
      if (changestb.errorCode === 1) {
        this.$toast.error("Lỗi do khách hàng không tồn tại trên hệ thống !");
        this.$refs.inputAccount.focus();
      }
      if (changestb.errorCode === 2) {
        this.$toast.error("Lỗi do Set-Top-Box không tại trên hệ thống !");
        this.$refs.inputAccount.focus();
      }

      this.loading(false);
    },
  },
};
</script>
