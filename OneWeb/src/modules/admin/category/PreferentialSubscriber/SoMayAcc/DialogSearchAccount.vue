<template>
  <b-modal
    size="xl"
    dialog-class="dialog-content"
    body-class="body-content"
    header-class="popup-header"
    content-class="popup-box"
    v-model="isShowModal"
    hide-footer
    hide-header
  >
    <!-- <template #modal-header>
      <div class="title" >
        <span class="icon one-notepad"></span> Tra cứu danh dạ
      </div>
      <div class="close -ap icon-close" @click.prevent="show(false)" v-if="false"></div>
    </template> -->
    <div style="height:900px">
      <SearchAccount
        :isPopup="isDialog"
        @form-close="onSelectCustomer"
        :vma_tb="vma_tb"
      />
    </div>
  </b-modal>
</template>
<script>
// import SearchAccount from "../../../../search/subscriber/SearchAccount";
import SearchAccount from "../SearchAccount/SearchAccount.vue";
export default {
  props: ["isDialog", "vma_tb"],
  components: { SearchAccount },
  data: () => ({
    isShowModal: false
  }),
  computed: {},
  methods: {
    show(visible) {
      this.isShowModal = visible;
    },
    onSelectCustomer(item) {
      console.log("onSelectCustomer:", item);
      this.$emit("on-select-customer", item);
      this.show(false);
    }
  }
};
</script>
