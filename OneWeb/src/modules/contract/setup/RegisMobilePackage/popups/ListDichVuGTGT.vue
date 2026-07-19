<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span>Danh sách các dịch vụ GTGT đang dùng</div>
      <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('popup-ListDichVuGTGT')"></div>
    </div>
    <div class="popup-body">
      <DataGrid :columns="gridFields" :dataSource="listDichVuGTGT"></DataGrid>
    </div>
  </div>
</template>

<script>
import api from "../api";

export default {
  name: "ListDichVuGTGT",
  components: {},

  props: {
    modalId: { type: String },
    so_tb: { type: String }
  },

  mounted() {
    this.loadListDichVu();
  },

  data() {
    return {
      gridFields: [
        {fieldName: 'ngay_thang', headerText: 'Tên nhóm DV'},
        {fieldName: 'ten_dv', headerText: 'Dịch vụ'},
        {fieldName: 'ma_dv', headerText: 'ID gói'},
        {fieldName: 'ten_dv', headerText: 'Tên gói'},
        {fieldName: '', headerText: 'Thao tác'},
      ],
      listDichVuGTGT: []
    };
  },

  methods: {
    async loadListDichVu() {
      try {
        this.loading(true);
        this.listDichVuGTGT = (await api.app_tracuu_sudung_dv(this.axios, { so_tb: this.so_tb })).data.data;

      } catch (e) {
        this.$toast("Có lỗi khi lấy danh sách dịch vụ");

      } finally {
        this.loading(false);
      }
    },
  },
};
</script>
