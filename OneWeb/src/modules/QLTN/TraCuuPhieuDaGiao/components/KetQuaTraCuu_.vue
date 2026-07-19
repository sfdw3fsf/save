<template>
  <div class="box-form">
        <div class="legend-title">
            <div class="pull-left">Danh sách khách hàng xử lý nợ</div>
            <div class="pull-right">
                <div class="-color normal">
                  <b-form-checkbox
                    v-model="p_chk_group"
                    class="check"
                    value="1"
                    unchecked-value="0"
                  >
                  <span class="name">Nhóm theo nhân viên</span>
                  </b-form-checkbox>
                </div>
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="table-content">
          <table class="table-result tree-grid-2 table-gachle">
            <thead class="center">
              <tr>
                  <th class="w20"></th>
                  <th class="w20"></th>
                  <th>Mã khách hàng</th>
                  <th>Mã đại diện</th>
                  <th>Tên khách hàng</th>
                  <th>Địa chỉ khách hàng</th>
                  <th>Nợ hiện tại</th>
                  <th>Số công văn</th>
                  <th>Lần XLN</th>
              </tr>
              <tr>
                <th class="w20"></th>
                <th class="w20">
                  <div class="check-action -color">
                      <input type="checkbox" @click="selectAll" v-model="allSelected" class="check">
                      <span class="name"></span>
                  </div>
                </th>
                <th>
                  <div class="input-icon-right">
                    <input type="text" class="form-control" @input="onChangeText($event.target.value, 'MA_KHACHHANG')">
                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                  </div>
                </th>
                <th>
                  <div class="input-icon-right">
                    <input type="text" class="form-control" @input="onChangeText($event.target.value, 'MATB_DD')">
                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                  </div>
                </th>
                <th>
                  <div class="input-icon-right">
                    <input type="text" class="form-control" @input="onChangeText($event.target.value, 'TEN_KH')">
                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                  </div>
                </th>
                <th>
                    <div class="input-icon-right">
                        <input type="text" class="form-control" @input="onChangeText($event.target.value, 'DIACHI_KH')">
                        <span class="icon nc-icon-outline ui-1_zoom"></span>
                    </div>
                </th>
                <th>
                  <div class="input-icon-right">
                    <input type="text" class="form-control" @input="onChangeText($event.target.value, 'TONGNO')">
                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                  </div>
                </th>
                <th>
                  <div class="input-icon-right">
                    <input type="text" class="form-control" @input="onChangeText($event.target.value, 'SO_CV')">
                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                  </div>
                </th>
                <th>
                  <div class="input-icon-right">
                    <input type="text" class="form-control" @input="onChangeText($event.target.value, 'LAN_XLN')">
                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                  </div>
                </th>
            </tr>
            </thead>
            <tbody>
              <tr v-if="$data.searchResults == null || $data.searchResults.length == 0">
                  <td :colspan="9">
                      Không có bản ghi nào để hiển thị
                  </td>
              </tr>
              <template v-for="(nvItem, nvIndex) in $data.searchResults">
                <tr v-show="!nvItem.isHide" :class="{ 'tree-open' : nvItem.isOpened, 'highlight' : nvItem.isSelected}" @click="doOpenTree(nvIndex, nvItem.key)">
                  <td>
                      <span class="fa text-main" :class="{ 'fa-play' : nvItem.isSelected}"></span>
                  </td>
                  <td colspan="8" class="tree-name nowrap" :class="{ 'selected' : nvItem.isSelected}">
                      <div class="tree-arrow"></div> Nhân viên: {{nvItem.key}}: {{nvItem.items.length}}
                  </td>
                </tr>
                <tr v-show="nvItem.isOpened" v-for="(item, index) in nvItem.items" :key="`${nvIndex}-${index}`" :style="{background: isSelected == nvIndex +'-' + index ? '#FFF0C8' : null }" @click="selectKhachHang(item, nvIndex, index)">
                  <td>
                    <span class="fa fa-play text-main" v-if="isSelected == nvIndex + '-' + index"></span>
                  </td>
                  <td class="tree-first" style="background: #fff;">
                      <div class="tree-line"></div>
                      <div class="tree-indent"></div>
                  </td>
                  <td>
                    <div class="check-action">
                      <input type="checkbox" class="check">
                      <span class="name"> {{item.MA_KHACHHANG}}</span>
                    </div>
                  </td>
                  <td>{{item.MATB_DD}}</td>
                  <td>{{item.TEN_KH}}</td>
                  <td>{{item.DIACHI_KH}}</td>
                  <td class="tright">{{item.TONGNO | numeral}}</td>
                  <td class="tright">{{item.SO_CV}}</td>
                  <td class="tright">{{item.LAN_XLN}}</td>
                </tr>
              </template>
            </tbody>
          </table>
        </div>
        <app-remote-pagination :totalRows="$props.page.totalElement"
          @change="onChangePage"
          @goPrev="onGoPrevItem"
          @goNext="onGoNextItem"></app-remote-pagination>
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
        type: Object
      },
      page: {
        type: Object
      }
    },
    data: () => {
       return {
        idxNV : null,
        idxPhieu: null,
        currentPage: 1,
        p_chk_group: '1',
        khachhang: {
          ma_khachhang: null
        },
        gridData : [],
        searchArray: [
          { key: 'MA_KHACHHANG', value: '', type: 'string'},
          { key: 'MATB_DD', value: '', type: 'string'},
          { key: 'TEN_KH', value: '', type: 'string'},
          { key: 'DIACHI_KH', value: '', type: 'string'},
          { key: 'TONGNO', value: '', type: 'numeric'},
          { key: 'SO_CV', value: '', type: 'numeric'},
          { key: 'LAN_XLN', value: '', type: 'numeric'}
        ],
        searchResults : [],
        selectedResults : []
      }
    },
    created () {

    },
    provide: {
    },
    computed: {
      isSelected () {
        if(this.idxNV !== null && this.idxPhieu !== null)
          return this.idxNV + '-' + this.idxPhieu
        else
          return null
      }
    },
    methods: {
      onChangeText: function (text, key) {
        let searchData = {
          key: key || '',
          value: text || ''
        };

        let r = []
        for(let i = 0; i < this.gridData.length; i++)
        {
          let data = searchArray(this.gridData[i].items, searchData)
          r.push({
              key : this.gridData[i].key,
              isSelected: this.gridData[i].isSelected,
              isOpened: this.gridData[i].isOpened,
              isHide: this.gridData[i].isHide,
              items: data
          })
        }
        this.searchResults = r
      },
      selectKhachHang (item, nvIndex, index) {
        this.idxNV = nvIndex
        this.idxPhieu = index
        this.khachhang = {
          ...this.khachhang,
          ma_khachhang: item.MA_KHACHHANG
        }
        // console.log('khachhang', this.khachhang)
      },
      doOpenTree (index, key) {
        let found = this.searchResults.findIndex((obj => obj.isSelected === true))
        if(found >= 0)
          this.searchResults[found].isSelected = false
        found = this.searchResults.findIndex((obj => obj.key === key))
        this.searchResults[found].isOpened = !this.searchResults[found].isOpened
        this.searchResults[found].isSelected = true
        this.idxNV = index
      },
      onChangePage (args) {
        EventBus.$emit('pageChange', args)
        console.log(args)
      },
      onGoNextItem () {
        if(this.idxNV !== null){
          let maxNo = this.searchResults[this.idxNV].items.length
          if(this.idxPhieu < maxNo  - 1)
            this.idxPhieu++
        }
      },
      onGoPrevItem () {
        if(this.idxNV !== null){
          if(this.idxPhieu > 0)
            this.idxPhieu--
        }
      },
      reloadData (newVal) {
        let $this = this
        $this.gridData = []
        $this.searchResults = []
        $.each(newVal, function (key, data) {
          $this.gridData.push( { key : key, items : data, isSelected: false, isOpened: false, isHide: false} )
          $this.searchResults.push( { key : key, items : data, isSelected: false, isOpened: false, isHide: false} )
        })
      },
      allSelected: function() {
        this.selectedResults = []
        if (!this.allSelected) {
          for (item in this.searchResults) {
            // this.selectedResults.push(this.searchResults[item]);
            this.selectedResults.push(this.searchResults[item].data)
          }
        }
      }
    },
    watch: {
      currentPage : {
        handler: function(value) {
          // page based on 1
          EventBus.$emit('pageChange', value - 1);
        }
      },
      p_chk_group : {
        handler (newVal) {
          if(newVal === '1') {  // grouped
            for(let i = 0; i < this.searchResults.length; i++){
              this.searchResults[i].isOpened = false
              this.searchResults[i].isHide = false
            }
            if(this.searchResults.length > 0){
              this.searchResults[0].isOpened = true
              this.searchResults[0].isSelected = true
              this.idxNV = 0
              this.idxPhieu = 0
            }

          } else {
            for(let i = 0; i < this.searchResults.length; i++){
              this.searchResults[i].isHide = true
              this.searchResults[i].isOpened = true
            }
            if(this.searchResults.length > 0){
              this.idxNV = 0
              this.idxPhieu = 0
            }
          }
        }
      },
      items: {
        deep: true,
        handler (newVal) {
          let $this = this
          this.reloadData(newVal)
          if($this.searchResults.length > 0)
          {
            $this.doOpenTree(null, $this.searchResults[0].key)
            $this.idxNV = 0
            $this.idxPhieu = 0
          }else{
            $this.idxNV = null
            $this.idxPhieu = null
          }

        }
      }

    }
  }
  </script>
