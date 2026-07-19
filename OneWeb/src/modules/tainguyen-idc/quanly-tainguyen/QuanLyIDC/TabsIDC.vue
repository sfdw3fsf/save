<template>
  <div class="tabs-idc-container">
    <!-- TAB NAVIGATION -->
    <div class="modal-content">
      <div class="">
        <div class="nav tab-over tabs">
          <a
            href="#tabChiTiet"
            @click="onChangeTab(1)"
            data-toggle="tab"
            class="tab"
            :class="{ active: tab_index == 1 }"
          >
            Thông tin IDC
          </a>
<!--          <a-->
<!--            href="#tabKetNoiDien"-->
<!--            @click="onChangeTab(2)"-->
<!--            data-toggle="tab"-->
<!--            class="tab"-->
<!--            :class="{ active: tab_index == 2 }"-->
<!--          >-->
<!--            Sơ đồ kết nối điện-->
<!--          </a>-->
<!--          <a-->
<!--            href="#tabKetNoiMang"-->
<!--            @click="onChangeTab(3)"-->
<!--            data-toggle="tab"-->
<!--            class="tab"-->
<!--            :class="{ active: tab_index == 3 }"-->
<!--          >-->
<!--            Sơ đồ kết nối mạng-->
<!--          </a>-->
        </div>
      </div>
    </div>

    <!-- TAB CONTENT -->
    <div class="tab-content" style="margin-bottom: 20px">
      <!-- TAB CHI TIẾT IDC -->
      <div class="tab-pane" id="tabChiTiet" :class="{ active: tab_index == 1 }">
        <ChiTietIDC
          :modalItemID="modalItemID"
          @selectObjectTaiNguyen="$emit('selectObjectTaiNguyen', $event)"
          @view-toanha-detail="$emit('view-toanha-detail', $event)"
          @view-matsan-detail="$emit('view-matsan-detail', $event)"
          @view-rack-detail="$emit('view-rack-detail', $event)"
          @view-customer-detail="$emit('view-customer-detail', $event)"
          @view-contract-detail="$emit('view-contract-detail', $event)"
        />
      </div>
      <!-- TAB SƠ ĐỒ KẾT NỐI ĐIỆN -->
      <div class="tab-pane" id="tabKetNoiDien" :class="{ active: tab_index == 2 }">
        <SoDoKetNoiDien :idcId="modalItemID" />
      </div>
        <!-- TAB SƠ ĐỒ KẾT NỐI MẠNG -->
      <div class="tab-pane" id="tabKetNoiMang" :class="{ active: tab_index == 3 }">
        <SoDoKetNoiMang :idcId="modalItemID" />
      </div>
    </div>
  </div>
</template>

<script>
import ChiTietIDC from './ChiTietIDC/index.vue'
import SoDoKetNoiDien from '../../sodo-daunoi-dien/index.vue'
import SoDoKetNoiMang from '../../sodo-daunoi-mang/index.vue'

export default {
  name: 'TabsIDC',

  components: {
    ChiTietIDC,
    SoDoKetNoiDien,
    SoDoKetNoiMang
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
.tabs-idc-container {
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
