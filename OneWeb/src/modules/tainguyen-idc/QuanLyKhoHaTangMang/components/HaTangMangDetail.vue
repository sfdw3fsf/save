<template>
  <div class="ha-tang-mang-detail">
    <!-- Action buttons -->
    <div
      class="list-actions-top"
      style="position: sticky; top: 0; background: white; z-index: 10; margin-bottom: 15px;"
      v-if="mode !== 'VIEW'"
    >
      <ul class="list">
        <li>
          <a href="#" @click.prevent="handleSave"> <span class="icon one-save"></span> Ghi lại </a>
        </li>
        <li>
          <a href="#" @click.prevent="$emit('cancelSave')">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy
          </a>
        </li>
      </ul>
    </div>
    <!-- TAB NAVIGATION -->
    <div class="">
      <div class="nav tab-over tabs" style="margin-top: 20px;">
        <a
          href="#tabThongTinChung"
          v-on:click="onChangeTab(1)"
          data-toggle="tab"
          class="tab"
          :class="{ active: tab_index == 1 }"
          >Thông tin chung</a
        >
        <a
          v-if="mode != 'ADD'"
          href="#tabHaTangAoHoa"
          v-on:click="onChangeTab(2)"
          data-toggle="tab"
          class="tab"
          :class="{ active: tab_index == 2 }"
          >Hạ tầng ảo hóa/ Smart Cloud / iIDG / Backup</a
        >
        <a
          v-if="mode != 'ADD'"
          href="#tabSubnet"
          v-on:click="onChangeTab(3)"
          data-toggle="tab"
          class="tab"
          :class="{ active: tab_index == 3 }"
          >Subnet</a
        >
        <a
          v-if="mode != 'ADD'"
          href="#tabIPAddress"
          v-on:click="onChangeTab(4)"
          data-toggle="tab"
          class="tab"
          :class="{ active: tab_index == 4 }"
          >IP Address</a
        >
        <a
          v-if="mode != 'ADD'"
          href="#tabThietBiVatLy"
          v-on:click="onChangeTab(5)"
          data-toggle="tab"
          class="tab"
          :class="{ active: tab_index == 5 }"
          >Thiết bị vật lý</a
        >
        <a
          v-if="mode != 'ADD'"
          href="#tabLichSuThayDoi"
          v-on:click="onChangeTab(6)"
          data-toggle="tab"
          class="tab"
          :class="{ active: tab_index == 6 }"
          >Lịch sử thay đổi</a
        >
      </div>
    </div>
    <div class="tab-content" style="margin-bottom: 20px;">
      <div class="tab-pane" id="tabThongTinChung" :class="{ active: tab_index == 1 }">
        <ThongTinChung ref="thongTinChung" :itemId="itemId" :mode="mode" />
      </div>
      <div v-if="mode != 'ADD'" class="tab-pane" id="tabHaTangAoHoa" :class="{ active: tab_index == 2 }">
        <HaTangAoHoa ref="haTangAoHoa" :itemId="itemId" :mode="mode" />
      </div>
      <div v-if="mode != 'ADD'" class="tab-pane" id="tabSubnet" :class="{ active: tab_index == 3 }">
        <Subnet ref="subnet" :itemId="itemId" :mode="mode" />
      </div>
      <div v-if="mode != 'ADD'" class="tab-pane" id="tabIPAddress" :class="{ active: tab_index == 4 }">
        <IPAddress ref="ipAddress" :itemId="itemId" :mode="mode" />
      </div>
      <div v-if="mode != 'ADD'" class="tab-pane" id="tabThietBiVatLy" :class="{ active: tab_index == 5 }">
        <ThietBiVatLy ref="thietBiVatLy" :itemId="itemId" :mode="mode" />
      </div>
      <div v-if="mode != 'ADD'" class="tab-pane" id="tabLichSuThayDoi" :class="{ active: tab_index == 6 }">
        <LichSuThayDoi
          v-if="itemId > 0"
          ref="lichSuThayDoi"
          :tenBang="'HaTang_Mang'"
          :tenCot="''"
          :khoaDuLieu="Number(itemId)"
        />
      </div>
    </div>
  </div>
</template>
<script>
import ThongTinChung from './ThongTinChungHTM.vue'
import HaTangAoHoa from './HaTangAoHoa.vue'
import Subnet from './Subnet.vue'
import IPAddress from './IPAddress.vue'
import ThietBiVatLy from './ThietBiVatLy.vue'
import LichSuThayDoi from '../../components/Tabs/TabHistory/index.vue'

export default {
  name: 'HaTangMangDetail',
  components: {
    ThongTinChung,
    HaTangAoHoa,
    Subnet,
    IPAddress,
    ThietBiVatLy,
    LichSuThayDoi
  },
  props: {
    itemId: {
      type: Number,
      default: 0
    },
    mode: {
      type: String,
      default: 'VIEW', // 'ADD', 'EDIT', 'VIEW'
      validator: (value) => ['ADD', 'EDIT', 'VIEW'].includes(value)
    }
  },
  data() {
    return {
      tab_index: 1
    }
  },
  watch: {
    itemId: {
      immediate: false,
      handler(newVal, oldVal) {
        if (newVal !== oldVal) {
          this.tab_index = 1 // Reset to first tab when itemId changes
        }
      }
    }
  },
  methods: {
    onChangeTab(index) {
      this.tab_index = index
      this.$nextTick(() => {
        switch (index) {
          case 1:
            if (this.$refs.thongTinChung && this.$refs.thongTinChung.loadData) {
              this.$refs.thongTinChung.loadData()
            }
            break

          case 2:
            if (this.$refs.haTangAoHoa && this.$refs.haTangAoHoa.loadData) {
              this.$refs.haTangAoHoa.loadData()
            }
            break

          case 3:
            if (this.$refs.subnet && this.$refs.subnet.loadData) {
              this.$refs.subnet.loadData()
            }
            break

          case 4:
            if (this.$refs.ipAddress && this.$refs.ipAddress.loadData) {
              this.$refs.ipAddress.loadData()
            }
            break

          case 5:
            if (this.$refs.thietBiVatLy && this.$refs.thietBiVatLy.loadData) {
              this.$refs.thietBiVatLy.loadData()
            }
            break

          case 6:
            if (this.$refs.lichSuThayDoi && this.$refs.lichSuThayDoi.refreshData) {
              this.$refs.lichSuThayDoi.refreshData()
            }
            break
        }
      })
    },
    async handleSave() {
      if (this.$refs.thongTinChung && this.$refs.thongTinChung.handleSave) {
        const result = await this.$refs.thongTinChung.handleSave(this.itemId)
        if (result) {
          this.$emit('saveSuccessed', true)
        }
      }
    }
  }
}
</script>
