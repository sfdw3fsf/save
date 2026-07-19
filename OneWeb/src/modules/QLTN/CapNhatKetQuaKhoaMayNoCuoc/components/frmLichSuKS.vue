<template>
  <b-modal
    id="modal-lichsu-ks"
    size="lg"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    no-close-on-backdrop
    @hide="resetData"
    @show="openForm"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Lịch sử khảo sát không lưu lượng
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="close()"
        ></div>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Lịch sử khảo sát</div>
          <DataGrid
            :columns="[
              {
                fieldName: 'NGAY_HOI',
                headerText: 'Ngày KS',
                allowFiltering: true,
              },
              {
                fieldName: 'GHICHU',
                headerText: 'Ghi chú',
                allowFiltering: true,
              },
              {
                fieldName: 'NOIDUNG',
                headerText: 'Nội dung',
                allowFiltering: true,
              },
            ]"
            :dataSource="sources"
            :showFilter="true"
            ref="gridLSKS"
            :allowPaging="true"
            :enablePagingServer="false"
          />
        </div>
      </div>
    </div>
  </b-modal>
</template>
<style></style>

<script>
import API from "../../api/CapNhatKQKhoaMayNoCuoc";
export default {
  props: {
    thueBaoId: Number,
  },
  data() {
    return {
      sources: [],
    };
  },
  methods: {
    close() {
      this.$bvModal.hide("modal-lichsu-ks");
    },
    async openForm() {
      this.loading(true);
      try {
        const response = await API.getLichSuKS(this.axios, this.thueBaoId);
        if (response.data.error_code === "BSS-00000000") {
          this.sources = response.data.data;
        }
      } catch (error) {
        this.sources = [];
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    resetData() {
      this.sources = [];
    },
  },
};
</script>
