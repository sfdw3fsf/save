<template>
    <div>
        <ChangeIPAccess ref="ChangeIPAccessModal" />
    </div>
</template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from "@/components/breadcrumb";
import Vue from "vue";
import ChangeIPAccess from "./ChangeIPAccess.vue";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
Vue.use(DialogPlugin);
export default {
    components: {
        breadcrumb,ChangeIPAccess
    },
    data: function () {
    return {}
    },
    mounted(){
        this.$refs.ChangeIPAccessModal.showModal();
    }
}
</script>