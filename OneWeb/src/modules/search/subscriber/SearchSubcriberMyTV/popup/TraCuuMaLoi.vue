<template>
  <b-modal hide-footer title="Tra cứu mã lỗi MyTV" size="xl" ref="dialogErrorCodeMyTV" class="modal-content popup-box">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click="tsbtnTraCuu_Click" style="cursor: pointer">
            <span class="icon one-reload1"></span>Tra Cứu
          </a>
        </li>
        <li>
          <a @click.prevent="tsbtnThoat_Click" style="cursor: pointer">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="title fw6 upper">Tra cứu mã lỗi MyTV</div>
      </div>
      <div class="box-form">
        <div class="legend-title">Thông tin tra cứu</div>

        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">Mã lỗi</div>
              <div class="value">
                <div class="input-more-button -right">
                  <button class="btn" @click="tsbtnTraCuu_Click">
                    <span class="one-search f20"></span>
                  </button>
                  <input v-model="txtErrorCode" type="text" class="form-control highlight"
                         v-on:keyup.enter="txtErrorCode_KeyPress"
                         autofocus>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">Từ khóa</div>
              <div class="value">
                <input v-model="txtKeyWord" type="text" class="form-control">
              </div>
            </div>
          </div>
        </div>

        <div class="legend-title">Chi tiết mã lỗi</div>
        <div class="info-row">
          <div class="key w90">Phân loại</div>
          <div class="value">
            <input v-model="txtPhanLoai" type="text" class="form-control" readonly>
          </div>
        </div>
        <div class="info-row">
          <div class="key w90">Hệ thống</div>
          <div class="value">
            <input v-model="txtHeThong" type="text" class="form-control" readonly>
          </div>
        </div>
        <div class="info-row">
          <div class="key w90">Thiết bị</div>
          <div class="value">
            <input v-model="txtThietBi" type="text" class="form-control" readonly>
          </div>
        </div>

        <div class="info-row">
          <div class="key w90">Mô tả lỗi</div>
          <div class="value">
            <textarea v-model="txtMoTaLoi" class="form-control" rows="3" readonly></textarea>
          </div>
        </div>

        <div class="info-row">
          <div class="key w90">Cách khắc phục</div>
          <div class="value">
            <textarea v-model="txtCachKhacPhuc" class="form-control" rows="5" readonly></textarea>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import api from "../api";

export default {
  name: "TraCuuMaLoi",
  methods: {
    async showModal() {
      this.$refs['dialogErrorCodeMyTV'].show()
    },
    async tsbtnThoat_Click() {
      this.$refs['dialogErrorCodeMyTV'].hide();
    },
    async tsbtnTraCuu_Click() {
      await this.TRACUU_MALOI();
    },
    async TRACUU_MALOI() {
      try {
        let maLoi = this.txtErrorCode.toString().trim();
        let tuKhoa = this.txtKeyWord.toString().trim();

        if (maLoi === "") {
          this.$toast.error("Hãy nhập mã lỗi.")
          return;
        }

        this.loading(true)
        const response = await api.errorSolution(this.axios, {
          "error_code": maLoi, "error_keyword": tuKhoa
        });
        const data = await response.data
        if (data.errorCode === 0) {
          if (data.data.length <= 0) {
            this.$root.$toast.error("Không tồn tại mã lỗi trên hệ thống");
            this.loading(false);
            return;
          }
          //   {
          //
          //   }
          //   ErrorDetail detail = error.data[0];
          let detail = data.data[0];
          this.txtPhanLoai = detail.type;
          this.txtHeThong = detail.system;
          this.txtThietBi = detail.device;
          this.txtMoTaLoi = detail.error_desc;
          this.txtCachKhacPhuc = detail.solution_vtt;
        } else {
          this.$root.$toast.error(data.faultString);
        }
      } catch (ex) {
        this.$toast.error(ex.toString())
      }
      this.loading(false)
    },
    txtErrorCode_KeyPress: async function (e) {
      if (e.keyCode === 13) {
        await this.tsbtnTraCuu_Click();
      } else {

      }
    },
  },
  data() {
    return {
      txtErrorCode: '',
      txtKeyWord: '',
      txtPhanLoai: '',
      txtHeThong: '',
      txtThietBi: '',
      txtMoTaLoi: '',
      txtCachKhacPhuc: ''
    }
  }
}
</script>

<style scoped>
input[readonly] {
  background-color: white !important;
}

textarea[readonly] {
  background-color: white !important;
}

/*.modal-content {*/
/*  width: 800px !important;*/
/*}*/
</style>
