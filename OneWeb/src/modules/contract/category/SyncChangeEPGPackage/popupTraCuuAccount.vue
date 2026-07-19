<template>
  <b-modal
    ref="popupTraCuuAccount"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">F3 - Tìm kiếm Account MyTV</div>
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
                 autofocus ref="inputAccount"
              />
            </div>
          </div>
          <div class="group-buttons -bottom center">
            <button class="btn btn-second nocorner" @click="btnThayDoi">
              <span class="inline vcenter f20 one-sync"></span> OK
            </button>
            <button
              class="btn btn-outline-secondary nocorner"
              @click="btnThoat"
            >
              <span class="inline vcenter f20 one-export"></span> Cancel
            </button>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import ActionTop from "@/components/ActionTop.vue";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
export default {
  name: "popupTraCuuAccount",
  components: { breadcrumb, ActionTop },
  data() {
    return {
      txtAcount: "",
    };
  },
  mounted() {
   
  },
  methods: {
    showModal() {
      this.$refs["popupTraCuuAccount"].show();
    },
    hideModal() {
      this.$refs["popupTraCuuAccount"].hide();
    },
    async btnThoat() {
      this.hideModal();
    },
    btnThayDoi(){
      if(this.txtAcount.trim() ==""){
        this.$toast.error("Vui lòng nhập Account");
        this.$refs.inputAccount.focus();
        return;
      }
      this.$emit("acceptChange", this.txtAcount.trim());
    }
    
  },
};
</script>
