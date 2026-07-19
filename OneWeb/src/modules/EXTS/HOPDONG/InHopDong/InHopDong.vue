<template>
    <SearchContract ref="SearchContract" :isPopup="1" :isShowTaoHDDT="true"/>
</template>

<script>
// import SearchContract from '../DichChuyenNew/popup/SearchContract/SearchContract.vue';
import SearchContract from '@/modules/search/subscriber/SearchContract/SearchContract.vue';
import Vue from "vue";
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

</style>
