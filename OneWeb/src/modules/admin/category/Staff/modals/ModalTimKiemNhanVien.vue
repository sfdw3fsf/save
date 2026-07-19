<template>
  <ejs-dialog
    :enableResize='true'
    :visible="false"
    :isModal="true"
    ref="modalTimKiemNhanVien"
    :header='"Tìm kiếm nhân viên"'
    showCloseIcon=true
    :target="'.main-wrapper'"
    width='80%'
    height="650"
    @open="openDialog"
  >
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="searchStaff">
            <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp Nhận
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <table-danh-sach-nhan-vien
        ref="tableStaff"
        @selected-change="changeSelectedStaff"
        :danh-sach-nhan-vien="danhSachNhanVien" />
    </div>
  </ejs-dialog>
</template>

<script>
import { pagingAndFilter } from '../mixins/pagingAndFilter'
import TableDanhSachNhanVien from '../part/TableDanhSachNhanVien'

export default {
  mixins: [pagingAndFilter],
  components: {
    TableDanhSachNhanVien
  },
  data: () => ({
    selectedStaffCode: null
  }),
  props: {
    danhSachNhanVien: {
      type: Array,
      default: () => []
    }
  },
  watch: {},
  methods: {
    show () {
      this.$refs.modalTimKiemNhanVien.show()
    },
    searchStaff () {
      this.$emit('searchStaffByCode', this.selectedStaffCode)
      this.$refs.modalTimKiemNhanVien.hide()
    },
    changeSelectedStaff (staff) {
      this.selectedStaffCode = staff.ma_nv
    },
    openDialog () {
      this.$refs.tableStaff.selectIndex(0)
    }
  }
}
</script>
