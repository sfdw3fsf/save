<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span>Gói đa dịch vụ</div>
      <div
        class="close -ap icon-close"
        data-dismiss="modal"
        @click="$bvModal.hide('popup-goidadichvu')"
      ></div>
    </div>
    <div class="popup-body">
      <DataGrid v-bind:columns="gridHeaders"
                v-bind:dataSource="gridData" :showColumnCheckbox="false"
                :showFilter="true" @rowClicked=""></DataGrid>
    </div>
  </div>
</template>

<script>
import api from "../api";

// Tham chiếu UR2.8.004 mục Gói đa dịch vụ
export default {
  name: "GoiDaDichVuPopup",
  components: {

  },

  props: {
    hdtb_id: { type: Number },
    hdkh_id: { type: Number },
  },

  mounted () {
    this.loadDsGoiDaDichVu();
  },

  data() {
    return {
      gridData: [],
      gridHeaders: [
        {fieldName: 'ma_tb', headerText: 'Số máy/Account', allowFiltering: true, allowSorting: false},
        {fieldName: 'ten_goi', headerText: 'Gói đa dịch vụ', allowFiltering: true, allowSorting: false},
      ],
    };
  },

  methods: {
    async loadDsGoiDaDichVu() {
      try {
        this.loading(true);
        this.gridData = (await api.sp_lay_ds_tb_dk_goi(this.axios, {
          hdtb_id: this.hdtb_id,
          hdkh_id: this.hdkh_id
        })).data.data;

      } catch (e) {
        this.$toast("Có lỗi khi lấy danh sách gói đa dịch vụ");

      } finally {
        this.loading(false);
      }
    }
  },
}
</script>
