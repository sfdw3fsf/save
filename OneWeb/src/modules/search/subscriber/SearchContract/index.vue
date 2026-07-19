<template>
<div>
        <div class="breadcrumb-top">
        <div class="main-title">Tra cứu hợp đồng, phụ lục</div>
        <ul class="breadcrumb">
            <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
            <li class="breadcrumb-item"><a href="#">Tra cứu</a></li>
            <li class="breadcrumb-item active">Phát triển thuê bao</li>
            <li class="breadcrumb-item active">Tra cứu hợp đồng</li>
        </ul>
    </div>
      <SearchContract ref="SearchContract" :isPopup="1"/>
</div>

</template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import Vue from "vue";
import SearchContract from "./SearchContract.vue"
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
Vue.use(DialogPlugin);
export default {
    components:{
        SearchContract,
    },
    data: function () {
        return {}
    },
    methods:{

    },
    mounted(){

    }
}
</script>
<style scope>
     .modal-body .page-content {
  position: inherit;
}
</style>
