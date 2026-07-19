<template>
  <b-modal
    id="modal-nganhangnn"
    modal-class="modal-nganhangnn"
    hide-footer
    hide-header
    no-close-on-backdrop
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span> Chi tiết MLNS</div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="hideDialog()"
        >
        </div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a
              href="#"
              @click.prevent="hideDialog()"
            >
              <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="info-row">
            <div class="key">Đơn vị QHNS</div>
            <div class="value">
              <input
                type="text"
                :value="nganhang.DV_QHNS"
                @change="v=>nganhang.DV_QHNS=v.target.value"
                class="form-control"
              />
            </div>
          </div>
          <div class="info-row">
            <div class="key">Mã chương</div>
            <div class="value">
              <input
                type="text"
                :value="nganhang.MACHUONG"
                @change="v=>nganhang.MACHUONG=v.target.value"
                class="form-control"
              />
            </div>
          </div>
          <div class="info-row">
            <div class="key">Mã ngành KT</div>
            <div class="value">
              <input
                type="text"
                :value="nganhang.MN_KT"
                @change="v=>nganhang.MN_KT=v.target.value"
                class="form-control"
              />
            </div>
          </div>
          <div class="info-row">
            <div class="key">Mã nội dung KT</div>
            <div class="value">
              <input
                type="text"
                :value="nganhang.MA_NDKT"
                @change="v=>nganhang.MA_NDKT=v.target.value"
                class="form-control"
              />
            </div>
          </div>
          <div class="info-row">
            <div class="key">Mã nguồn NSNN</div>
            <div class="value">
              <input
                type="text"
                :value="nganhang.MN_NSNN"
                @change="v=>nganhang.MN_NSNN=v.target.value"
                class="form-control"
              />
            </div>
          </div>
          <div class="info-row">
            <div class="key">Tiền định mức</div>
            <div class="value">
              <vue-numeric
                class="form-control tright"
                separator=","
                v-model="nganhang.TIEN_DM"
                placeholder="0"
                output-type="Number"
              ></vue-numeric>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
export default {
    props: ['nganhang_input'],
    mounted() {
        this.nganhang = this.nganhang_input
    },
    data() {
        return {
            nganhang: {
                DV_QHNS : "",
                MACHUONG: "",
                MN_KT : "",
                MA_NDKT : "",
                MN_NSNN : "",
                TIEN_DM : "",
            }
        }
    },
    methods: {
        showDialog() {
            this.$bvModal.show('modal-nganhangnn')
        },
        hideDialog() {
            this.$emit('hideDialog', this.nganhang)
            this.$bvModal.hide('modal-nganhangnn')
        }
    }
}
</script>