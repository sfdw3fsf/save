<template>
  <div class="box-form box-col marb0" id="boxTop">
    <div class="legend-title">Danh sách User</div>
    <div class="table-content" style="height: calc(100% - 80px);">
      <table class="table-result tree-grid-2 table-gachle">
        <thead class="center">
          <tr>
              <th class="w20"></th>
              <th>STT</th>
              <th>Thu ngân</th>
              <th>Tiền thu</th>
              <th>Tổng phiếu</th>
          </tr>
          <tr>
            <th class="w20"></th>
            <th></th>
            <th>
              <div class="input-icon-right">
                <input type="text" class="form-control" @input="onChangeText($event.target.value, 'THUNGAN')">
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
            <th>
              <div class="input-icon-right">
                <input type="text" class="form-control" @input="onChangeText($event.target.value, 'TONGTIEN')">
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
            <th>
              <div class="input-icon-right">
                <input type="text" class="form-control" @input="onChangeText($event.target.value, 'TONGPHIEU')">
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="$data.searchResults == null || $data.searchResults.length == 0">
            <td :colspan="5" style="text-align: center">
              Không có bản ghi nào để hiển thị
            </td>
          </tr>
          <tr v-for="(item, idx) in $data.searchResults" :key="idx" :style="{background: index == idx ? '#FFF0C8' : null }" @click="selectItem(item, idx)">
            <td>
              <span class="fa fa-play text-main" v-if="index == idx"></span>
            </td>
            <td>{{item.STT}}</td>
            <td>{{item.THUNGAN}}</td>
            <td class="tright">{{item.TONGTIEN | numeral}}</td>
            <td class="tright">{{item.TONGPHIEU | numeral}}</td>
          </tr>
        </tbody>
      </table>
    </div>

</div>
</template>

<script>
import { searchArray } from '@/utils/util'
import RemotePagination from '@/components/RemotePagination'
import EventBus from '@/utils/eventBus'

export default {
  components: { AppRemotePagination : RemotePagination},
  props: {
    items: {
      type: Array
    },
    page: {
      type: Object
    }
  },
  data: () => {
    return {
      index: null,
      searchArray: [
        { key: 'THUNGAN', value: '', type: 'string'},
        { key: 'TONGTIEN', value: '', type: 'numeric'},
        { key: 'TONGPHIEU', value: '', type: 'numeric'}
      ],
      searchResults : []
    }
  },
  mounted () {
  },
  computed: {
  },
  methods: {
    onChangeText: function (text, key) {
      let searchData = {
        key: key || '',
        value: text || ''
      };

      let r = []
      let data = searchArray(this.items, searchData)
      this.searchResults = r
      if(this.searchResults.length > 0)
        this.index = 0
      else
        this.index = null
    },
    selectItem (item, index) {
      console.log('selectItem')
      this.index = index
    },
    reloadData (newVal) {
      this.searchResults = newVal.slice(0)
    }
  },
  watch: {
    items: {
      deep: true,
      handler (newVal) {
        let $this = this
        this.reloadData(newVal)
        if($this.searchResults.length > 0)
        {
          $this.index = 0
        }else{
          $this.index = null
        }
        console.log('watch Item')
      }
    },
    index : {
      handler: function(newVal, oldVal) {
        console.log(newVal, oldVal)
        if(newVal !== oldVal)
        {
          let nguoicapnhat_id = null
          if(newVal !== null){
            nguoicapnhat_id = this.searchResults[newVal].NGUOIDUNG_ID
          }
          EventBus.$emit('nguoiCapNhatChange', { nguoicapnhat_id : nguoicapnhat_id})
        }
      }
    }
  }
}
</script>
