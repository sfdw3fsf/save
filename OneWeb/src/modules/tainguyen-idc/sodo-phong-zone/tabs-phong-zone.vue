<template>
  <div class="tabs-phong-zone-container">
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
            Thông tin phòng/zone
          </a>
          <a
            href="#tabSoDo"
            v-on:click="onChangeTab(2)"
            data-toggle="tab"
            class="tab"
            :class="{ active: tab_index == 2 }"
          >
            Sơ đồ phòng/zone
          </a>
        </div>
      </div>
    </div>

    <!-- TAB CONTENT -->
    <div class="tab-content" style="margin-bottom: 20px">
      <!-- TAB CHI TIẾT -->
      <div class="tab-pane" id="tabChiTiet" :class="{ active: tab_index == 1 }">
        <RoomDetailContent :modalItemID="modalItemID" ref="roomDetail" @selectObjectTaiNguyen="$emit('selectObjectTaiNguyen', $event)" />
      </div>
      <!-- TAB SƠ ĐỒ -->
      <div class="tab-pane" id="tabSoDo" :class="{ active: tab_index == 2 }">
        <SoDoPhongZone :modalItemID="modalItemID" @refresh="onRefresh" />
      </div>
    </div>
  </div>
</template>

<script>
import SoDoPhongZone from './index.vue'
import RoomDetailContent from '../sodo-matsan/components/RoomDetailContent.vue'

export default {
  name: 'TabsPhongZone',

  components: {
    SoDoPhongZone,
    RoomDetailContent
  },

  props: {
    modalItemID: {
      type: Number,
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
    },
    onRefresh() {
      this.$refs.roomDetail.onRefresh()
    }
  }
}
</script>

<style scoped>
.tabs-phong-zone-container {
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
