<script>
import PopupGuiSms from "@/modules/install/UpdateInstallProgress/popups/PopupGuiSms.vue";
export default {
  name: "PopupGuiSMSTienTrinhBaoHong",
  components: {
    PopupGuiSms
  },
  props: {
    show: {
      type: Boolean,
      default: false
    },
    selectedRow: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      showModal: false
    };
  },
  methods: {
    closeModal() {
      this.$emit("update:show", false);
      this.showModal = false;
    }
  },
  watch: {
    async show(newValue) {
      this.showModal = newValue;
      if (newValue) {
        document.querySelector(".dashboard-header").style.zIndex = -1;
        await this.$refs.popupGuiSMS.onInit();
      } else {
        document.querySelector(".dashboard-header").style.zIndex = "initial";
      }
    }
  }
};
</script>

<template>
  <b-modal
    static
    size="xl"
    v-model="showModal"
    hide-footer
    no-close-on-backdrop
    no-close-on-esc
  >
    <template #modal-header
      ><div class="title">
        <span class="icon one-notepad"></span> Gửi SMS tiến trình báo hỏng
      </div>
      <div
        class="close -ap icon-close"
        data-dismiss="modal"
        @click="closeModal"
      ></div
    ></template>
    <PopupGuiSms
      :baoHongId="selectedRow.baohong_id"
      :baoHongRow="selectedRow"
      ref="popupGuiSMS"
    />
  </b-modal>
</template>
