<template>
  <div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:;" class="pointer" @click="onClickSave" :class="{ disabled: mode === 'view' }">
              <span class="icon one-save"></span>Ghi lại
            </a>
          </li>
          <li>
            <a href="javascript:;" class="pointer" @click="setMode('view')" :class="{ disabled: mode === 'view' }">
              <span class="icon ui-1_circle-remove nc-icon-glyph"></span>Hủy bỏ
            </a>
          </li>
        </ul>
      </div>
      <!-- TABS -->
       <div class="modal-content p-2">
         <div class="nav tab-over tabs">
           <a href="#thongTinChung" v-on:click="onChangeTab(1)" data-toggle="tab" class="tab" :class="{active: tab_index == 1}">
             Thông tin chung</a>
           <!-- <a href="#haTangMang" v-on:click="onChangeTab(2)" data-toggle="tab" class="tab" :class="{active: tab_index == 2}">
             Hạ tầng mạng</a> -->
           <a href="#haTangAoHoa" v-on:click="onChangeTab(3)" data-toggle="tab" class="tab" :class="{active: tab_index == 3}">
             Hạ tầng ảo hóa/ Smart cloud/ IDG/ Backup</a>
           <a href="#subnet" v-on:click="onChangeTab(4)" data-toggle="tab" class="tab" :class="{active: tab_index == 4}">
             Subnet</a>
           <a href="#lichSuThayDoi" v-on:click="onChangeTab(5)" data-toggle="tab" class="tab" :class="{active: tab_index == 5}">
             Lịch sử thay đổi</a>
         </div>
         <div class="tab-content">
           <div class="tab-pane" id="thongTinChung" :class="{active: tab_index == 1}">
             <ThongTinChung :currentItem="currentItem" :mode="mode"></ThongTinChung>
           </div>
           <!-- <div class="tab-pane" id="haTangMang" :class="{active: tab_index == 2}">
             <HaTangMang></HaTangMang>
           </div> -->
           <div class="tab-pane" id="haTangAoHoa" :class="{active: tab_index == 3}">
             <HaTangAoHoa></HaTangAoHoa>
           </div>
           <div class="tab-pane" id="subnet" :class="{active: tab_index == 4}">
             <Subnet :vlanId="currentItem.id"></Subnet>
           </div>
         </div>
       </div>
  </div>
</template>
<script>
import ThongTinChung from './ThongTinChung/index.vue'
import HaTangMang from './HaTangMang/index.vue'
import HaTangAoHoa from './HaTangAoHoa/index.vue'
import Subnet from './Subnet/index.vue'
export default {
    name: 'PopupChiTiet',
    components: {
      ThongTinChung,
      HaTangMang,
      HaTangAoHoa,
      Subnet
  },
  props: {
    currentItem: {
        type: Object,
        default: {}
    },
    mode: {
      type: String,
      default: 'view'
    }
  },
  data() {
    return {
      tab_index: 1
    }
  },
  methods: {
    setMode(newMode) {
      this.mode = newMode
    },
    onChangeTab(index) {
      this.tab_index = index
    },
    onClickSave() {
      // Implement save logic here
      console.log('Save clicked')
    }
  }
}
</script>
