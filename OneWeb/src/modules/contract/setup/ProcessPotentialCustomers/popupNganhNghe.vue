<template>
  <b-modal
    ref="popupnganhnghe"
    size="lg"
    hide-footer
    hide-header
    no-close-on-backdrop
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Chi tiết ngành nghề
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
         @click="hideModal"
        ></div>
      </div>

      <div class="popup-body">
        <div class="box-form" style="overflow: hidden">
          <div class="info-row">
            <div class="key nowrap">
              Tìm kiếm theo mã tiểu ngành (rê chuột vào ô tìm kiếm để xem HD
              =&gt; Enter để tìm kiếm)
            </div>
            <div class="value">
              <input
                type="text"
                class="form-control"
                v-model="tieunganh.search"
              />
            </div>
          </div>
          <div class="info-row nowrap">
            <div class="key">Tiểu ngành cấp 1</div>
            <div class="value">
              <select2
                v-model="tieunganh.TNC1_ID"
                class="select-custom"
                :options="
                  tieunganh.dsTNC1.map((nn) => ({
                    id: nn.tnc1_id,
                    text: nn.ten_tnc1,
                  }))
                "
              ></select2>
            </div>
          </div>
          <div class="info-row nowrap">
            <div class="key">Tiểu ngành cấp 2</div>
            <div class="value">
              <select2
                v-model="tieunganh.TNC2_ID"
                class="select-custom"
                :options="
                  tieunganh.dsTNC2.map((nn) => ({
                    id: nn.tnc2_id,
                    text: nn.ten_tnc2,
                  }))
                "
              ></select2>
            </div>
          </div>
          <div class="info-row nowrap">
            <div class="key">Tiểu ngành cấp 3</div>
            <div class="value">
              <select2
                v-model="tieunganh.TNC3_ID"
                class="select-custom"
                :options="
                  tieunganh.dsTNC3.map((nn) => ({
                    id: nn.tnc3_id,
                    text: nn.ten_tnc3,
                  }))
                "
              ></select2>
            </div>
          </div>
          <div class="info-row nowrap">
            <div class="key">Tiểu ngành cấp 4</div>
            <div class="value">
              <select2
                v-model="tieunganh.TNC4_ID"
                class="select-custom"
                :options="
                  tieunganh.dsTNC4.map((nn) => ({
                    id: nn.tnc4_id,
                    text: nn.ten_tnc4,
                  }))
                "
              ></select2>
            </div>
          </div>
          <div class="info-row nowrap">
            <div class="key">Mô tả chi tiết</div>
            <div class="value">
              <textarea
                v-model="tieunganh.TNCMOTA"
                class="form-control"
                style="height: 100px; resize: none"
              ></textarea>
            </div>
          </div>
          <div class="group-buttons -bottom center">
            <button
              class="btn btn-second nocorner"
              style="width: 120px"
                @click="ghiLai"
            >
              <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp
              nhận
            </button>
            <button
              class="btn btn-second nocorner"
              style="width: 120px"
               @click="hideModal"
            >
              <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
            </button>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from "@/components/ActionTop.vue";
import API from "./API";
export default {
  name: "popupnganhnghe",
  components: {
    ActionTop,
  },
    props: ['vnganhnghe'],
  data() {
    return {
     tieunganh: {
        dsTNC1: [],
        dsTNC2: [],
        dsTNC3: [],
        dsTNC4: [],
        TNC1_ID: 0,
        TNC2_ID: 0,
        TNC3_ID: 0,
        TNC4_ID: 0,
        TNCMOTA: '',
      },
      nganh: null,
    };
  },
  mounted() {
      this.loadTieuNganh();
  },
  methods: {
    showModal() {
      this.nganh = this.vnganhnghe;
      this.loadTieuNganh();
      this.$refs["popupnganhnghe"].show();
    },
    hideModal() {
      this.$refs["popupnganhnghe"].hide();
    },
    ghiLai() {
      this.$emit("acceptChange", this.list);
      this.hideModal();
    },
    loadTieuNganh(){
      this.$toast.error("modal: "+this.nganh)
        API.post_lay_ds_tieunganh(this.axios, { "vkieu":1, "vnganhnghe_id":this.nganh, "vtc_id": 0 }).then((response) => {
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.tieunganh.dsTNC1 = response.data.data;
          if(this.tieunganh.dsTNC1 && this.tieunganh.dsTNC1.length>0) this.tieunganh.TNC1_ID = this.tieunganh.dsTNC1[0].tnc1_id;
        }
      });
    }
  },
};
</script>