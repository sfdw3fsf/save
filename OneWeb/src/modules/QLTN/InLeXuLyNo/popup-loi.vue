<template>
  <b-modal
    id="popup-loixln"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    no-close-on-backdrop
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Danh sách lỗi
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="$bvModal.hide('popup-loixln')"
        ></div>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Danh sách lỗi</div>
          <DataGrid
            :columns="fields"
            :dataSource="sources"
            :showFilter="true"
            ref="dsLoi"
            :allowPaging="true" :enablePagingServer="false"
          ></DataGrid>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<style></style>
<script>
export default {
  name: "PopupDocLoiXLN",
  props: {
    sources: Array,
  },
  data() {
    return {
      fields: [
        {
          fieldName: "MA_XLN",
          headerText: "Mã XLN",
          allowFiltering: true,
        },
        {
          fieldName: "TRANGTHAI",
          headerText: "Lỗi",
          allowFiltering: true,
        },
      ],
    };
  },
  methods: {
    close() {
      this.$bvModal.hide("popup-loixln");
    },
  },
};
</script>
