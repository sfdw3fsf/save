<template>
    <div class="main-wraper">
        <div class="breadcrumb-top">
            <div class="main-title">Tra cứu khách hàng trùng giấy tờ</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
                <li class="breadcrumb-item"><a href="#">Tra cứu</a></li>
                <li class="breadcrumb-item active">khách hàng trùng giấy tờ</li>
            </ul>
        </div>
        <SearchCustomersSameNumber />
    </div>
</template>
<script>
import SearchCustomersSameNumber from "./SearchCustomersSameNumber.vue"
export default {
  components: {
    SearchCustomersSameNumber
  }
}
</script>
