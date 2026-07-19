<template>
    <div class="main-wraper">
        <div class="breadcrumb-top">
            <div class="main-title">Tra cứu thông tin gói cước/khuyến mại</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
                <li class="breadcrumb-item"><a href="#">Tra cứu</a></li>
                <li class="breadcrumb-item active">Gói cước - khuyến mại</li>
            </ul>
        </div>
        <SearchPackagesPromotions />
    </div>
</template>
<script>
import SearchPackagesPromotions from "./SearchPackagesPromotions.vue"
export default {
  components: {
    SearchPackagesPromotions
  }
}
</script>
 