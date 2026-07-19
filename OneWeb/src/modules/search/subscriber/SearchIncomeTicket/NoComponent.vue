<template>
  <div class="main-wrapper">
    <div class="breadcrumb-top">
      <div class="main-title">Tiếp nhận yêu cầu khoá máy cố định</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a>
        </li>
        <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
        <li class="breadcrumb-item active">Lắp đặt mới</li>
      </ul>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#"> <span class="icon ui-1_circle-add nc-icon-glyph"></span> Nhập mới(F5) </a>
        </li>
        <li>
          <a href="#"> <span class="icon media-1_sd nc-icon-glyph"></span> Ghi lại(F9) </a>
        </li>
        <li>
          <a href="#"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Hủy </a>
        </li>
        <li>
          <a href="#"> <span class="icon ui-1_trash nc-icon-glyph"></span> Xóa HĐ (F8) </a>
        </li>
        <li>
          <a href="#"> <span class="icon files_single-content-02 nc-icon-glyph"></span> Hồ sơ </a>
        </li>
        <li>
          <a href="#"> <span class="icon shopping_credit-card nc-icon-glyph"></span> Thanh toán </a>
        </li>
        <li>
          <a href="#"> <span class="icon ui-1_circle-add nc-icon-glyph"></span> Thêm TT </a>
        </li>
        <li>
          <a href="#"> <span class="icon ui-1_trash nc-icon-glyph"></span> Xóa TT </a>
        </li>
        <li>
          <a href="#"> <span class="icon ui-1_circle-add nc-icon-glyph"></span>Thêm TB </a>
        </li>
        <li>
          <a href="#"> <span class="icon ui-1_trash nc-icon-glyph"></span>Xóa TB </a>
        </li>
        <li>
          <a href="#"> <span class="icon objects_support-17 nc-icon-glyph"></span>Chăm sóc KH </a>
        </li>
      </ul>
      <div class="more">
        <a href="#">
          <span class="icon -ap icon-dots-three-vertical"></span>
        </a>
      </div>
    </div>
    <p>UR tham chiếu chưa có</p>
  </div>
</template>
<script>
export default {
  components: {},
  name: 'NoComponent',
  created() {},
  data() {
    return {
      header: {
        title: 'Tra cứu',
        list: [
          { name: ' Thay đổi địa chỉ MAC FIBER', link: { name: 'Ui.cards' } },
          {
            name: 'Thay đổi địa chỉ MAC FIBER',
            link: { name: 'Ui.buttons' }
          }
        ]
      }
    }
  }
}
