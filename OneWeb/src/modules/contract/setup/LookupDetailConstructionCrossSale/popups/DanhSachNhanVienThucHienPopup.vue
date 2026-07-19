<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span>Danh sách nhân viên thực hiện</div>
      <div
        class="close -ap icon-close"
        data-dismiss="modal"
        @click="$bvModal.hide('popup-DanhSachNhanVienThucHien')"
      ></div>
    </div>
    <div class="popup-body">
      <DataGrid ref="gridDsNhanVienThucHien" v-bind:columns="gridHeaders"
                v-bind:dataSource="dsNhanVienThucHien" :showColumnCheckbox="false"
                :enable-paging-server="false" :allowPaging="false" :showFilter="true"></DataGrid>
    </div>
  </div>
</template>

<style>
#popup-DanhSachNhanVienThucHien .modal-dialog {
  max-width: 100%;
}
</style>

<script>
import api from "../api";

export default {
  name: "DanhSachNhanVienThucHienPopup",
  props: {
    tinh_id: {  },
    phieu_id: { required: true },
    phieu_bh_id: {  },
    phieu_kn_id: {  },
    huong_giao_id: {  },
    nhanvien_id: { }
  },
  components: {

  },

  mounted () {
    this.loadDsNhanVienThucHien();
  },

  data() {
    return {
      dsNhanVienThucHien: [],
      ts_tc_bancheo: 0,
      gridHeaders: [
        { fieldName: 'gr', headerText: ' ', isGroupedColumn: true, captionTemplate: "${key}" },
        { fieldName: 'ngaygiao', headerText: 'Ngày giao' },
        { fieldName: 'nv_giao', headerText: 'Người giao' },
        // { fieldName: 'ma_nd_th', headerText: 'User người TH' },
        { fieldName: 'ma_nv', headerText: 'Mã NV TH' },
        { fieldName: 'ten_nv', headerText: 'Người thực hiện' },
        { fieldName: 'so_dt', headerText: 'Điện thoại' },
        { fieldName: 'nhiemvu', headerText: 'Nhiệm vụ' },
        { fieldName: 'kqxl_id', headerText: 'Kết quả XL' },
        { fieldName: 'trangthai', headerText: 'Trạng thái' },
        { fieldName: 'ghichu', headerText: 'Ghi chú' }
      ],
    };
  },

  methods: {
    async loadDsNhanVienThucHien() {
      try {
        this.loading(true);
        if(this.huong_giao_id != 7777 && this.huong_giao_id != 9999) {
            this.ts_tc_bancheo = 0
        }else{
            this.ts_tc_bancheo = 1
        }
        this.dsNhanVienThucHien = (await api.sp_frmnhanvienthbc_load(this.axios, {
            "phieu_id": this.phieu_id,
            "phieu_bh_id": 0,
            "phieu_kn_id": 0,
            "huonggiao": this.huong_giao_id || 0,
            "nhanvien_id": this.nhanvien_id || 0,
            "tt_nd_tinh_id": this.tinh_id || 0,
            "ts_tc_bancheo": this.ts_tc_bancheo
          })).data.data;

      } catch (e) {

      } finally {
        this.loading(false);
      }
    }
  },
}
</script>
