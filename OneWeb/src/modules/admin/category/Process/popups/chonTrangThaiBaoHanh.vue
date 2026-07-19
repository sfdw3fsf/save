<template>
   <b-modal
    id="chonTrangThaiBaoHanh"
    :title="titleModal"
    modal-class="chon-trang-thai-bao-hanh-modal"
    header-class="popup-header"
    content-class="chon-trang-thai-bao-hanh-content"
    body-class="chon-trang-thai-bao-hanh-body"
    size="xl"
    hide-footer
  >
    <div class="popup-body">
        <div class="box-form">
            <div class="info-row">
                <div class="key w30 nowrap">Trạng thái</div>
                <div class="value">
                    <div class="select-custom">
                        <select name="" id="" class="form-control">
                            <option value=""></option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    </div>
  </b-modal>
</template>
<script>
import moment from "moment";
import Vue from 'vue'
import API from '../API'
import { ContextMenuComponent } from "@syncfusion/ej2-vue-navigations";
import { ContextMenuPlugin } from "@syncfusion/ej2-vue-navigations";
import { enableRipple } from '@syncfusion/ej2-base';
Vue.use(ContextMenuPlugin);
enableRipple(true);
export default {
  name: 'chonTrangThaiBaoHanh',
  components: {
    API
  },
  props: [],
  mounted() {
  },
  data() {
    return {
    };
  },
  watch: {
  },
  methods : {
    show() {
      this.$bvModal.show("chonTrangThaiBaoHanh");
    },
  }
}
</script>
<style scoped>
</style>
