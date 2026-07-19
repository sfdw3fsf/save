<template>
  <b-modal
    id="popup_KetQuaDoKiem"
    title="Kết quả đo kiểm"
    modal-class="suakenhthu"
    header-class="popup-header"
    content-class="tracuu-content"
    body-class="none-padding tracuu-body"
    size="xl"
    hide-footer
  >
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
          <li @click="tsbtnCapNhat_Click">
            <a> <span class="icon one-save"></span>Cập nhật </a>
          </li>
          <li @click="tsbtnXoa_Click" :class="tsbtnXoa.Enabled ? 'non-active' : ''">
            <a> <span class="icon one-trash"></span>Xoá </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Kêt quả đo chất lượng</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">Speed Up</div>
                <div class="value">
                  <input
                    v-model="nud_speed_up"
                    type="number"
                    class="form-control fw6"
                  />
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">Attenuation Up</div>
                <div class="value">
                  <input
                    v-model="nud_atten_up"
                    type="number"
                    class="form-control"
                  />
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">SNR Up</div>
                <div class="value">
                  <input
                    v-model="nud_snr_up"
                    type="number"
                    class="form-control"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">Speed Down</div>
                <div class="value">
                  <input
                    v-model="nud_speed_down"
                    type="number"
                    class="form-control fw6"
                  />
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">Attenuation Down</div>
                <div class="value">
                  <input
                    v-model="nud_atten_down"
                    type="number"
                    class="form-control"
                  />
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">SNR Down</div>
                <div class="value">
                  <input
                    v-model="nud_snr_down"
                    type="number"
                    class="form-control"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="info-row">
              <div class="key w130">Ghi chú</div>
              <div class="value">
                <input
                  v-model="txtGhiChu"
                  type="text"
                  class="form-control fw6"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
export default {
  mounted() {},
  data() {
    return {
      baohong_id: 0,
      hdtb_id: 0,
      nud_speed_up: "",
      nud_speed_down: "",
      nud_atten_up: "",
      nud_atten_down: "",
      nud_snr_up: "",
      nud_snr_down: "",
      txtGhiChu: "",
      kieu: 0,
      tsbtnXoa: {
        Enabled: false,
      },
    };
  },
  methods: {
    show(hdtb_id, baohong_id) {
      this.$bvModal.show("popup_KetQuaDoKiem");
      this.baohong_id = baohong_id;
      this.hdtb_id = hdtb_id;
      this.Lay_DS_CHATLUONG(hdtb_id, baohong_id);
    },
    async Lay_DS_CHATLUONG(hdtb_id, baohong_id) {
      try {
        let data = await this.axios.post(
          "web-ccdv/khaibaotsl/sp_ds_chatluong",
          {
            vhdtb_id: hdtb_id,
            vbaohong_id: baohong_id,
          }
        );

        let rs = data.data ? data.data.data : [];
        if (rs.length > 0) {
          this.nud_speed_up = rs[0]["speed_up"];
          this.nud_speed_down = rs[0]["speed_down"];
          this.nud_atten_up = rs[0]["attenuation_up"];
          this.nud_atten_down = rs[0]["attenuation_down"];
          this.nud_snr_up = rs[0]["snr_up"];
          this.nud_snr_down = rs[0]["snr_down"];
          this.txtGhiChu = rs[0]["ghichu"];
          this.nhapmoi = false;
          this.tsbtnXoa.Enabled = true;
          this.kieu = 1
        } else {
          this.nud_speed_up = "";
          this.nud_speed_down = "";
          this.nud_atten_up = "";
          this.nud_atten_down = "";
          this.nud_snr_up = "";
          this.nud_snr_down = "";
          this.txtGhiChu = "";
          this.tsbtnXoa.Enabled = false;
          this.kieu = 0
        }
      } catch (err) {
        console.log(err);
      }
    },
    async tsbtnCapNhat_Click() {
      let rs = null;
      try {
        this.loading(true)
        if(this.hdtb_id) {
          let ds_chatluong = [
            {
              HDTB_ID: this.hdtb_id,
              SPEED_UP: this.nud_speed_up,
              SPEED_DOWN: this.nud_speed_down,
              ATTENUATION_UP: this.nud_atten_up,
              ATTENUATION_DOWN: this.nud_atten_down,
              SNR_UP: this.nud_snr_up,
              SNR_DOWN: this.nud_snr_down,
              GHICHU: this.txtGhiChu,
              NGUOI_CN: this.$root.token.getUserName(),
              MAY_CN: this.$root.token.getMachine(),
              IP_CN: this.$root.token.getIP(),
              ATT_MAX: 30.0,
              SNR_MIN: 0.0,
              ATT_MIN: 0.0,
              TRANGTHAI: "UP",
            },
          ];
          rs = await this.axios.post(
            "web-ccdv/khaibaotsl/capnhat_chatluong_hdtb",
            {
              "vkieu": this.kieu,
              "ds_chatluong": JSON.stringify(ds_chatluong)
            }
          );
        }
        if(this.baohong_id) {
          let ds_chatluong = [
            {
              BH_ID: this.baohong_id,
              SPEED_UP: this.nud_speed_up,
              SPEED_DOWN: this.nud_speed_down,
              ATTENUATION_UP: this.nud_atten_up,
              ATTENUATION_DOWN: this.nud_atten_down,
              SNR_UP: this.nud_snr_up,
              SNR_DOWN: this.nud_snr_down,
              GHICHU: this.txtGhiChu,
              NGUOI_CN: this.$root.token.getUserName(),
              MAY_CN: this.$root.token.getMachine(),
              IP_CN: this.$root.token.getIP(),
              ATT_MAX: 30.0,
              SNR_MIN: 0.0,
              ATT_MIN: 0.0,
              TRANGTHAI: "UP",
            },
          ];
          rs = await this.axios.post(
            "web-ccdv/khaibaotsl/capnhat_chatluong_bh",
            {
              "vkieu": this.kieu,
              "ds_chatluong": JSON.stringify(ds_chatluong)
            }
          );
        }
        this.$toast.success("Thành công!")
        this.Lay_DS_CHATLUONG(this.hdtb_id, this.baohong_id)
        this.loading(false)
      } catch(err) {
        this.loading(false)
        this.$toast.error("Thất bại!")
      }
    },
    async tsbtnXoa_Click() {
      try {
        this.loading(true)
        if(this.hdtb_id) {
          this.axios.post("web-ccdv/khaibaotsl/capnhat_chatluong_hdtb", {
            "vkieu": 2,
            "ds_chatluong":"[{\"HDTB_ID\":" + this.hdtb_id +  "}]"
          })
        }
        if(this.baohong_id) {
          this.axios.post("web-ccdv/khaibaotsl/capnhat_chatluong_bh", {
            "vkieu": 2,
            "ds_chatluong":"[{\"BH_ID\":" + this.baohong_id +  "}]"
          })
        }
        this.$toast.success("Xóa thành công!")
        this.Lay_DS_CHATLUONG(this.hdtb_id, this.baohong_id)
        this.loading(false)
      } catch (e) {
        this.loading(false)
        this.$toast.error("Thất bại!")
      }
    }
  },
};
</script>
<style>
.non-ative a {
  color: #d3d3d3 !important;
}
</style>
