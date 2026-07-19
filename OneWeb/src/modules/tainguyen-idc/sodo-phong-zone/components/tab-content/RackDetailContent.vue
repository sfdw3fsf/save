<template>
  <div class="rack-detail-content">
    <div class="nav tab-over tabs">
      <a
        href="#tabThongTinChung"
        v-on:click="onChangeTab(1)"
        data-toggle="tab"
        class="tab"
        :class="{ active: tab_index == 1 }"
      >
        Thông tin chung
      </a>
      <a
        href="#tabMatTruocSau"
        v-on:click="onChangeTab(2)"
        data-toggle="tab"
        class="tab"
        :class="{ active: tab_index == 2 }"
      >
        Mặt trước & Mặt sau
      </a>
      <a
        href="#tabDanhSachU"
        v-on:click="onChangeTab(3)"
        data-toggle="tab"
        class="tab"
        :class="{ active: tab_index == 3 }"
      >
        Danh sách U
      </a>
    </div>
    <div class="tab-content">
      <div class="tab-pane" :class="{ active: tab_index == 1 }" id="tabThongTinChung">
        <RackGeneralInfoTab :rack-id="modalItemID" />
      </div>
      <div class="tab-pane" :class="{ active: tab_index == 2 }" id="tabMatTruocSau">
        <RackBothViewsTab :rack-id="modalItemID" />
      </div>
      <div class="tab-pane" :class="{ active: tab_index == 3 }" id="tabDanhSachU">
        <RackUListTab :rack-id="modalItemID" />
      </div>
    </div>
  </div>
</template>

<script>
import RackGeneralInfoTab from './RackGeneralInfoTab.vue'
import RackBothViewsTab from './RackBothViewsTab.vue'
import RackUListTab from './RackUListTab.vue'

export default {
  name: 'RackDetailContent',
  components: {
    RackGeneralInfoTab,
    RackBothViewsTab,
    RackUListTab
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
.rack-detail-content {
  
}
.tab-content {
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