<template>
  <b-modal
    ref="dsBDTBRef"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Danh sách các biến động
        </div>
        <div class="close -ap icon-close" @click="tsbtnThoat_Click"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" @click.prevent="tsbtnChapNhan_Click">
              <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
            </a>
          </li>
          <li>
            <a href="#" @click.prevent="tsbtnThoat_Click">
              <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Danh sách các biến động</div>
          <DataGrid
            :columns="headers"
            :dataSource="grcDSBienDong"
            :showColumnCheckbox="false"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            @selectedRowChanged="selectedRowChanged"
          />
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
export default {
  name: 'DanhSachBDTBPopup',
  props: {
    grcDSBienDong: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      headers: [
        { fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true },
        { fieldName: 'ngay_yc', headerText: 'Ngày yêu cầu', allowFiltering: true },
        { fieldName: 'trangthai_hd', headerText: 'Trạng thái', allowFiltering: true },
        { fieldName: 'ten_loaihd', headerText: 'Loại hợp đồng', allowFiltering: true },
        { fieldName: 'ten_kieuld', headerText: 'Kiếu lắp đặt', allowFiltering: true },
        { fieldName: 'ten_dv', headerText: 'Đơn vị lập HĐ/PL', allowFiltering: true }
      ],
      selectedItem: null
    }
  },
  methods: {
    showModal () {
      this.$refs.dsBDTBRef.show()
    },
    selectedRowChanged (item) {
      this.selectedItem = { ...item }
    },
    tsbtnChapNhan_Click () {
      console.log(this.selectedItem)
      this.$emit('onOk', { chapnhan: true, item: this.selectedItem })
    },
    tsbtnThoat_Click () {
      this.$emit('onOk', { chapnhan: false, item: null })
    },
    hideModal () {
      this.$refs.dsBDTBRef.hide()
    }
  }
}
</script>
