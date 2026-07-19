<template>
    <div class="main-wraper">
        <div class="breadcrumb-top">
            <div class="main-title">Tra cứu dữ liệu upload cước cao</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
                <li class="breadcrumb-item"><a href="#">Tra cứu</a></li>
                <li class="breadcrumb-item active">Dữ liệu upload cước cao</li>
            </ul>
        </div>
        <SearchHighcostUploadData />
    </div>
</template>
<script>
import SearchHighcostUploadData from "./SearchHighcostUploadData.vue"
export default {
  components: {
    SearchHighcostUploadData
  }
}
</script>
