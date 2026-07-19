<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title">
        <span class="icon one-notepad"></span> Danh sách các biến động
      </div>
      <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('popupDanhSachBienDongTB')">
      </div>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="onClickChapNhan">
            <span class="icon one-notepad" ></span> Chấp nhận
          </a>

        </li>
      </ul>

    </div>
    <div class="popup-body">
      <div class="box-form">
        <DataGrid
          :columns="gridHeaders"
          :dataSource="dsBienDongFormatted"
          :enable-paging-server="false"
          :allowPaging="true"
          :showFilter="true"
          @selectedRowChanged="gridSelectedRowChanged"
          @recordDoubleClick="gridDoubleClick"
        >
        </DataGrid>
      </div>
    </div>
  </div>
</template>

<style>
#popupDanhSachBienDongTB .modal-dialog {
  max-width: 100%;
}
</style>

<script>

export default {
  name: "DanhSachBienDongTB",
  components: {},

  props: {
    ds_biendong: { type: Array, default: [] },
  },

  mounted() {
    this.dsBienDongFormatted = JSON.parse(JSON.stringify(this.ds_biendong));
  },

  data() {
    return {
      dsBienDongFormatted: [],
      gridHeaders: [
        { fieldName: 'ma_gd', headerText: 'Mã giao dịch' },
        { fieldName: 'ngay_yc', headerText: 'Ngày yêu cầu' },
        { fieldName: 'trangthai_hd', headerText: 'Trạng thái' },
        { fieldName: 'ten_loaihd', headerText: 'Loại hợp đồng' },
        { fieldName: 'ten_kieuld', headerText: 'Kiểu lắp đặt' },
        { fieldName: 'ten_dv', headerText: 'Đơn vị lập HĐ/PL' },
      ],
      selectedItem: null,
    };
  },

  methods: {
    gridSelectedRowChanged(item) {
      this.selectedItem = item;
    },
    gridDoubleClick(item) {
      this.$emit("accept", this.selectedItem);
    },
    onClickChapNhan() {
      this.$emit("accept", this.selectedItem);
    }
  },

}
</script>
