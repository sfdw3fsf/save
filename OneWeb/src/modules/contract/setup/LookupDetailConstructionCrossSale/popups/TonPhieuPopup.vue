<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span>Danh sách lịch sử các lần tồn phiếu</div>
      <div
        class="close -ap icon-close"
        data-dismiss="modal"
        @click="$bvModal.hide('popup-tonphieu')"
      ></div>
    </div>
    <div class="popup-body">
      <DataGrid v-bind:columns="gridHeaders" :show-filter="false"
                v-bind:dataSource="gridData" :showColumnCheckbox="false"></DataGrid>
    </div>
  </div>
</template>

<script>
import api from "../api";

// Tham chiếu UR2.8.004 mục Tồn phiếu
export default {
  name: "TonPhieuPopup",
  components: {

  },

  props: {
    hdtb_id: { type: Number }
  },

  mounted () {
    this.loadDsPhieuTon();
  },

  data() {
    return {
      gridData: [],
      gridHeaders: [
        {fieldName: 'ngay_bt', headerText: 'Ngày báo tồn', allowFiltering: true},
        {fieldName: 'nguoi_bt', headerText: 'Người báo tồn', allowFiltering: true},
        {fieldName: 'donvi_bt', headerText: 'Đơn vị báo tồn', allowFiltering: true},
        {fieldName: 'nhom_ton', headerText: 'Nhóm tồn', allowFiltering: true},
        {fieldName: 'lydoton_tc', headerText: 'Lý do tồn', allowFiltering: true},
        {fieldName: 'nguoi_cn', headerText: 'Người cập nhật', allowFiltering: true},
        {fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true}
      ],
    };
  },

  methods: {
    async loadDsPhieuTon() {
      try {
        this.loading(true);
        this.gridData = (await api.sp_lay_ds_ton_phieu_hist(this.axios, {
          hdtb_id: this.hdtb_id,
          phieu_id: 0
        }));

      } catch (e) {
        this.$toast("Có lỗi khi lấy danh sách phiếu tồn");

      } finally {
        this.loading(false);
      }
    }
  },
}
</script>
