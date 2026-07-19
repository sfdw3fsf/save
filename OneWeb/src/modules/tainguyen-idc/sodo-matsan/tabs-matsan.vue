<template>
  <div class="tabs-matsan-container">
    <!-- TAB NAVIGATION -->
    <div class="modal-content">
      <div class="">
        <div class="nav tab-over tabs">
          <a
            href="#tabChiTiet"
            v-on:click="onChangeTab(1)"
            data-toggle="tab"
            class="tab"
            :class="{ active: tab_index == 1 }"
          >
            Thông tin mặt sàn
          </a>
          <a
            href="#tabSoDo"
            v-on:click="onChangeTab(2)"
            data-toggle="tab"
            class="tab"
            :class="{ active: tab_index == 2 }"
          >
            Sơ đồ mặt sàn
          </a>
        </div>
      </div>
    </div>

    <!-- TAB CONTENT -->
    <div class="tab-content" style="margin-bottom: 20px">
      <!-- TAB CHI TIẾT -->
      <div class="tab-pane" id="tabChiTiet" :class="{ active: tab_index == 1 }">
        <ChiTietMatSan :matSanId="modalItemID" @selectObjectTaiNguyen="$emit('selectObjectTaiNguyen', $event)"/>
      </div>
      <!-- TAB SƠ ĐỒ MẶT SÀN -->
      <div class="tab-pane" id="tabSoDo" :class="{ active: tab_index == 2 }">
        <SoDoMatSan :modalItemID="modalItemID" />
      </div>
    </div>
  </div>
</template>

<script>
import SoDoMatSan from './index.vue'
import ChiTietMatSan from '../SoDoMatSanDetail/index.vue'

export default {
  name: 'TabsMatSan',

  components: {
    SoDoMatSan,
    ChiTietMatSan
  },

  props: {
    modalItemID: {
      type: [String, Number],
      required: true
    }
  },

  data () {
    return {
      tab_index: 1
    }
  },

  methods: {
    onChangeTab (index) {
      this.tab_index = index
    }
  }
}
</script>

<style scoped>
.tabs-matsan-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  position: relative;
}

.modal-content {
  background: #fff;
  border: none;
  box-shadow: none;
  z-index: 100;
  flex-shrink: 0;
}

.tab-content {
  padding: 10px 0;
  flex: 1;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.tab-pane {
  display: none;
  height: 100%;
}

.tab-pane.active {
  display: flex;
  flex-direction: column;
}
</style>
