<template>
    <div class="main-wraper">
        <div class="breadcrumb-top">
            <div class="main-title">Tra cứu Esim</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
                <li class="breadcrumb-item"><a href="#">Tra cứu</a></li>
                <li class="breadcrumb-item active">Esim</li>
            </ul>
        </div>
        <SearchEsim />
    </div>
</template>
<script>
import SearchEsim from "./SearchEsim.vue"
export default {
  components: {
    SearchEsim
  }
}
</script>
