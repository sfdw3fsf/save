<template>
  <b-modal
    size="xl"
    dialog-class="dialog-content"
    body-class="body-content"
    header-class="popup-header"
    content-class="popup-box"
    v-model="isShowModal"
    hide-footer
  >
    <!-- <template #modal-header>
      <div class="title">
        <span class="icon one-notepad"></span> Tra cứu danh dạ
      </div>
      <div class="close -ap icon-close" @click.prevent="show(false)"></div>
    </template> -->
    <div style="height:900px">
      <SearchAccount :isPopup="isDialog" @form-close="onSelectCustomer" />
    </div>
  </b-modal>
</template>
<script>
import SearchAccount from "../../../../search/subscriber/SearchAccount"
export default {
  props: ["isDialog"],
  components: { SearchAccount },
  data: () => ({
    isShowModal: false
  }),
  computed: {},
  methods: {
    show (visible) {
      this.isShowModal = visible
    },
    onSelectCustomer (e) {
      this.$emit("onSelectCustomer", e)
      this.show(false)
    }
  }
}
</script>
