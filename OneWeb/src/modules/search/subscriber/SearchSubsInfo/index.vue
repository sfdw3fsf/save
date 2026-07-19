<template>
    <div class="main-wraper">
        <div class="breadcrumb-top">
            <div class="main-title">tra thông tin tài khoản adsl</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
                <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
                <li class="breadcrumb-item active">Lắp đặt mới</li>
            </ul>
        </div>
        <SearchSubsInfo />
    </div>
</template>
<script>
import SearchSubsInfo from "./SearchSubsInfo.vue"
export default {
    components: {
        SearchSubsInfo
    }
}
</script>
