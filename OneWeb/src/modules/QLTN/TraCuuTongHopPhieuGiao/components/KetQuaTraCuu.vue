<template>
  <div class="box-form">
    <div class="table-content">
      <table class="table-result tree-grid-2 table-gachle">
        <thead class="center">
            <tr>
                <th class="w20"></th>
                <th colspan="6">Tổng hợp giao nhận</th>
                <th colspan="4">Phiếu nhận</th>
                <th colspan="4">Phiếu thu</th>
            </tr>
            <tr>
                <th class="w20"></th>
                <th>Lượt giao</th>
                <th>Ngày giao</th>
                <th>Giấy báo giao</th>
                <th>Hóa đơn giao</th>
                <th>Tiền hóa đơn giao</th>
                <th>Hơn đơn tồn</th>
                <th>Hẹn trả</th>
                <th>Trả giấy báo</th>
                <th>Trả hóa đơn</th>
                <th>Trả trọn bộ</th>
                <th>Giấy báo phát được</th>
                <th>Tỷ lệ phát</th>
                <th>Hóa đơn thu</th>
                <th>Tỷ lệ thu</th>
            </tr>
            <tr>
                <th class="w20"></th>
                <th>
                    <div class="input-icon-right">
                        <input type="text" class="form-control" @input="onChangeText($event.target.value, 'TEN_NV')">
                        <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                </th>
                <th>
                    <div class="input-icon-right">
                        <input type="text" class="form-control" @input="onChangeText($event.target.value, 'NGAYGIAO')">
                        <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                </th>
                <th>
                    <div class="input-icon-right">
                        <input type="text" class="form-control" @input="onChangeText($event.target.value, 'GBC_PHAT')">
                        <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                </th>
                <th>
                    <div class="input-icon-right">
                        <input type="text" class="form-control" @input="onChangeText($event.target.value, 'SOLUONG_HD')">
                        <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                </th>
                <th>
                    <div class="input-icon-right">
                        <input type="text" class="form-control" @input="onChangeText($event.target.value, 'TONGTIEN_HD')">
                        <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                </th>
                <th>
                    <div class="input-icon-right">
                        <input type="text" class="form-control" @input="onChangeText($event.target.value, 'HOADON_TON')">
                        <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                </th>
                <th>
                    <div class="input-icon-right">
                        <input type="text" class="form-control" @input="onChangeText($event.target.value, 'HENTRA')">
                        <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                </th>
                <th>
                    <div class="input-icon-right">
                        <input type="text" class="form-control" @input="onChangeText($event.target.value, 'TRA_GB')">
                        <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                </th>
                <th>
                    <div class="input-icon-right">
                        <input type="text" class="form-control" @input="onChangeText($event.target.value, 'TRA_HD')">
                        <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                </th>
                <th>
                    <div class="input-icon-right">
                        <input type="text" class="form-control" @input="onChangeText($event.target.value, 'TRA_TB')">
                        <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                </th>
                <th>
                    <div class="input-icon-right">
                        <input type="text" class="form-control" @input="onChangeText($event.target.value, 'GBC_PHAT')">
                        <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                </th>
                <th>
                    <div class="input-icon-right">
                        <input type="text" class="form-control" @input="onChangeText($event.target.value, 'TILE_PHAT')">
                        <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                </th>
                <th>
                    <div class="input-icon-right">
                        <input type="text" class="form-control" @input="onChangeText($event.target.value, 'HD_THU')">
                        <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                </th>
                <th>
                    <div class="input-icon-right">
                        <input type="text" class="form-control" @input="onChangeText($event.target.value, 'TILE_THU')">
                        <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                </th>
            </tr>
        </thead>
        <tbody>
          <tr v-if="$data.searchResults == null || $data.searchResults.length == 0">
              <td :colspan="14" style="text-align:center">
                  Không có bản ghi nào để hiển thị
              </td>
          </tr>
          <tr v-for="(item, idx) in $data.searchResults" :key="idx" :style="{background: index == idx ? '#FFF0C8!important' : null }" @click="selectItem(idx)">
            <td>
              <span class="fa fa-play text-main" v-if="index == idx"></span>
            </td>
            <td>{{item.TEN_NV}}</td>
            <td class="tright">{{item.NGAYGIAO}}</td>
            <td class="tright">{{item.GBC_PHAT | numeral}}</td>
            <td class="tright">{{item.SOLUONG_HD | numeral}}</td>
            <td class="tright">{{item.TONGTIEN_HD | numeral}}</td>
            <td class="tright">{{item.HOADON_TON | numeral}}</td>
            <td class="tright">{{item.HENTRA | numeral}}</td>
            <td class="tright">{{item.TRA_GB | numeral}}</td>
            <td class="tright">{{item.TRA_HD | numeral}}</td>
            <td class="tright">{{item.TRA_TB | numeral}}</td>
            <td class="tright">{{item.GBC_PHAT | numeral}}</td>
            <td class="tright">{{item.TILE_PHAT | numeral}}</td>
            <td class="tright">{{item.HD_THU | numeral}}</td>
            <td class="tright">{{item.TILE_THU | numeral}}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <app-remote-pagination
      :pageinfo="$data.searchPageInfo"
      @change="onChangePage"
      @goPrev="onGoPrevItem"
      @goNext="onGoNextItem">
    </app-remote-pagination>
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
    created (){
      this.searchPageInfo = {...this.page}
    },
    data: () => {
       return {
        searchArray: [
          { key: 'TEN_NV', value: '', type: 'string'},
          { key: 'NGAYGIAO', value: '', type: 'string'},
          { key: 'GBC_PHAT', value: '', type: 'numeric'},
          { key: 'SOLUONG_HD', value: '', type: 'numeric'},
          { key: 'TONGTIEN_HD', value: '', type: 'numeric'},
          { key: 'HOADON_TON', value: '', type: 'numeric'},
          { key: 'HENTRA', value: '', type: 'numeric'},
          { key: 'TRA_GB', value: '', type: 'numeric'},
          { key: 'TRA_HD', value: '', type: 'numeric'},
          { key: 'TRA_TB', value: '', type: 'numeric'},
          { key: 'GBC_PHAT', value: '', type: 'numeric'},
          { key: 'TILE_PHAT', value: '', type: 'numeric'},
          { key: 'HENTRA', value: '', type: 'numeric'},
          { key: 'HD_THU', value: '', type: 'numeric'},
          { key: 'TILE_THU', value: '', type: 'numeric'},
        ],
        searchResults : [],
        selectedResults : [],
        searchPageInfo: {},
        index: null,
      }
    },
    provide: {
    },
    computed: {
    },
    methods: {
      onChangeText: function (text, key) {

        let searchData = {
          key: key || '',
          value: text || ''
        };
        let i = 0

        for(i = 0; i < this.searchArray.length; i++)
        {
          if(this.searchArray[i].key === searchData.key)
          {
            this.searchArray[i].value = searchData.value

          }

        }

        // console.log(this.searchArray)
        let fSearch = false

        let data = this.items

        for(i = 0; i < this.searchArray.length; i++)
        {
          if(this.searchArray[i].value !== '')
          {
            fSearch = true
            data = searchArray(data, this.searchArray[i])
          }

        }

        this.searchResults = data
        if(fSearch)
        {

          this.searchPageInfo.totalElement = this.searchResults.length
          if(this.searchResults.length > 0){
            this.searchPageInfo.totalPages = 1
            this.searchPageInfo.page = 1
          } else {
            this.searchPageInfo.totalPages = 0
            this.searchPageInfo.page = 0
            this.searchPageInfo.currentRow = 0
          }


        } else
        {
          this.searchPageInfo = {...this.page}
        }


        if(this.searchResults.length > 0)
          this.selectItem(0)
        else
          this.selectItem(-1)
      },
      selectItem (index) {
        this.index = index
        this.searchPageInfo.currentRow = index + 1
      },
      reloadData (newVal) {
        this.searchResults = newVal.slice(0)
        this.searchPageInfo = {...this.page}
      },
      onChangePage (args) {
        EventBus.$emit('pageChange', args)
      },
      onGoNextItem (args) {
        this.selectItem(args - 1)
      },
      onGoPrevItem (args) {
        this.selectItem(args - 1)
      },
    },
    watch: {
      items: {
        deep: true,
        handler (newVal) {
          let $this = this
          this.reloadData(newVal)
          if($this.searchResults.length > 0)
          {
            this.selectItem(0)
          }else{
            this.selectItem(-1)
          }

        }
      },

    }
  }
  </script>
