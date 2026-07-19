<template>
    <div class="main-wraper">
        <div class="breadcrumb-top">
            <div class="main-title">Tra cứu SMS</div>
            <span class="text-danger">(Số liệu chỉ tra cứu trong vòng 35 ngày)</span>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
                <li class="breadcrumb-item"><a href="#">Tra cứu</a></li>
                <li class="breadcrumb-item active">SMS</li>
            </ul>
        </div>
        <SearchSMS />
    </div>
</template>
<script>
import SearchSMS from "./SearchSMS.vue"
export default {
  components: {
    SearchSMS
  }
}
</script>
