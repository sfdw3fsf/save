<template>
  <b-modal
    id="modal-thongtinTT"
    size="xl"
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
          <span class="icon one-notepad"></span>Thông tin thanh toán
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="close()"
        ></div>
      </div>
      <div class="popup-body">
        <div class="legend-title mart10">Thông tin thanh toán</div>
        <DataGrid
          :columns="[
            {
              fieldName: 'MA_TT',
              headerText: 'Mã thanh toán',
              allowFiltering: true,
              width: 100,
            },
            {
              fieldName: 'MATB_DD',
              headerText: 'Mã đại diện',
              allowFiltering: true,
              width: 100,
            },
            {
              fieldName: 'TEN_TT',
              headerText: 'Tên thanh toán',
              allowFiltering: true,
            },
            {
              fieldName: 'DIACHI_TT',
              headerText: 'Địa chỉ TT',
              allowFiltering: true,
            },
          ]"
          :dataSource="dsThanhToan"
          :showFilter="true"
          ref="gridThongtin"
          :allowPaging="true"
          :enablePagingServer="false"
        />
      </div>
    </div>
  </b-modal>
</template>
<script>
import API from "../../api/GachNoDiDongTienMat";
export default {
  name: "FormDSTraCuuTTTT",
  props: {
    ma_tb: String,
    kyCuoc: String,
  },
  computed: {},
  mounted() {},
  data() {
    return {
      dsThanhToan: [],
    };
  },
  methods: {
    openForm() {
      this.fetchData();
    },
    close() {
      this.$bvModal.hide("modal-thongtinTT");
    },
    resetData() {
      this.dsThanhToan = [];
    },
    async fetchData() {
      try {
        this.loading(true);
        const params = {
          kyCuoc: this.kyCuoc,
          maTB: this.ma_tb,
        };
        const response = await API.getDsThongTinTT(this.axios, params);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsThanhToan = response.data.data;
        } else {
          this.dsThanhToan = [];
        }
      } catch (error) {
        this.dsThanhToan = [];
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
  },
};
</script>
