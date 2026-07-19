<template>
       <!-- <div class="main-wrapper">
        <div class="breadcrumb-top">
            <div class="main-title">Nhập thông tin xác nhận quyền</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
                <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
                <li class="breadcrumb-item active">Lắp đặt mới</li>
            </ul>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="#">
                        <span class="icon design_bullet-list-67 nc-icon-glyph"></span> Danh sách
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon one-excel"></span> Xuất Excel
                    </a>
                </li>
            </ul>
            <div class="more">
                <a href="#">
                    <span class="icon -ap  icon-dots-three-vertical"></span>
                </a>
            </div>
        </div>
        <div class="page-content">
            <a @click="handleShowModal" data-toggle="modal" class="btn btn-action">Show modal</a>
        </div> -->
        <ChuDeKhuyenMai ref="chuDeKhuyenMai"/>
    <!-- </div> -->
</template>
<script>
import ChuDeKhuyenMai from './ChuDeKhuyenMai'
export default {
  components: {
    ChuDeKhuyenMai
  },
  data () {
    return {

    }
  },
  mounted() {
    this.handleShowModal()
  },
  methods: {
    handleShowModal () {
      this.$refs.chuDeKhuyenMai.$refs.popupTCTTTB.show()
    }
  }
}
</script>
