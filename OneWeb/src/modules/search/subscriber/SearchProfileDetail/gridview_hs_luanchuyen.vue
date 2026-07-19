<template>
<div>
<div class="table-content"  v-if="(datalist && datalist.length>0) || showHeader">
  <table class="table-result table-gachle tree-grid-2" :id="id" style="height: calc(100% - 120px);">
      <thead class="center">
          <tr>
               <th class="w20"></th>
               <th></th>
               <th class="w20" v-if="checkheader">
                  <div class="check-action">
                      <input type="checkbox" class="check">
                      <span class="name"></span>
                  </div>
              </th>
              <th v-for="(h,hdx) in headerList" :key="hdx"   :class="{'w30':hdx==headerList.length-1,'stickylastcol':(hdx==headerList.length-1) && flcol}">
                {{ h.text }}
              </th>
               <th>Xem/Tải về</th>
          </tr>
          <tr>
               <th class="w20"></th>
               <th></th>
              <th v-if="checkheader">
                  <div class="check-action">
                      <input type="checkbox" disabled class="check">
                      <span class="name"></span>
                  </div>
              </th>
              <th v-for="(h,hdx) in headerList" :key="hdx" :class="{'w30':hdx==headerList.length-1,'stickylastcol':(hdx==headerList.length-1) && flcol}">
                  <div class="input-icon-right">
                      <input type="text" class="form-control" v-model="h.value" @change="filterDataList">
                      <span class="icon nc-icon-outline ui-1_zoom"></span>
                  </div>
              </th>
              <th></th>
          </tr>
      </thead>
      <tbody>
          <tr :class="{ 'highlight4' : d.i===(currentrecord%recordperpage) }"  v-for="(d,idx) in pagedDataList" :key="idx"  @click="oneClick(d.i,true,$event)">
             <template v-if="d.type==0">
                 <td :colspan="headerList.length+3">
                     <div class="tree-arrow"></div> {{groupConfig.keyName}}: {{d.data}}
                  </td>
             </template>
             <template v-if="d.type==1">
              <td class="tree-first w20">
                    <div class="tree-line"></div>
                    <div class="tree-indent"></div>
               </td>
               <td class="w20 center">
                    <span class="fa fa-play text-main"></span>
               </td>
               <td class="selected" v-if="checkheader">
                  <div class="check-action">
                      <input type="checkbox" class="check" v-model="d.data.ischecked">
                      <span class="name"></span>
                  </div>
              </td>
              <td  v-for="(h,hdx) in headerList" :key="hdx">
                <template v-if="(hdx<headerList.length-1) || (!lastcoltemplate) || (lastcoltemplate=='0')">
                  <span>{{ d.data[h.name] }}</span>
                </template>
                <template v-if="(hdx==headerList.length-1) && lastcoltemplate && (lastcoltemplate=='1')">
                  <a :href="d.data[h.name]" class="act">
                      <span class="icon nc-icon-outline ui-1_eye-17"></span>
                  </a>
                  <a :href="d.data[h.name]" class="act">
                      <span class="icon nc-icon-outline arrows-2_hit-up"></span>
                  </a>
                </template>
                <template v-if="(hdx==headerList.length-1) && lastcoltemplate && (lastcoltemplate=='2')">
                  <a href="#" class="btn btn-second" :class="{'checkedbox':d.data[h.name]}" @click.prevent="d.data[h.name]=!d.data[h.name]">
                      <span class="nc-icon-glyph ui-1_check-circle-08"></span>
                  </a>
                </template>
              </td>
             </template>
             

          </tr>
      </tbody>
  </table>
</div>
<div class="tc-pagination" v-if="tabledata && tabledata.length>0">
  <a href="#" class="btn  one-arrow-left-forward" @click.prevent="changePage(0,true)"></a>
  <a href="#" class="btn fa fa-angle-double-left" @click.prevent="changePage(-1)"></a>
  <a href="#" class="btn fa fa-angle-left" @click.prevent="changeRecord(-1)"></a>
  <span class="text">Bản ghi</span>
  <div class="dropdown inline">
      <div class="fw6 input-number" data-toggle="dropdown">
          <span class="text-main">{{ currentrecord + 1 }}</span>/{{ totalrecord }}
      </div>
      <div class="dropdown-menu">
          <a href="#" class="dropdown-item">10</a>
          <a href="#" class="dropdown-item">20</a>
      </div>
  </div>

  <a href="#" class="btn fa fa-angle-right" @click.prevent="changeRecord(1)"></a>
  <a href="#" class="btn fa fa-angle-double-right" @click.prevent="changePage(1)"></a>
  <a href="#" class="btn one-arrow-right-forward" @click.prevent="changePage(totalpage,true)"></a>
</div>
 <div v-if="!datalist || datalist.length<=0">
    <slot name="nodata"><p class="text-center">Không có số liệu ...</p></slot>
</div>
</div>
</template>
<style scoped>
.stickylastcol {
  position:-webkit-sticky;
  position:sticky;
  right:0;
  background:inherit;
}
.table-result { border-collapse: separate; border-spacing: 0; }
.table-result tbody tr:nth-child(2n+1) { background-color:#fff;}
.btn-second.checkedbox { color:#0176FF; }
.btn-second {
    background-color: #fff;
    border-color: #0176FF;
    /* color: #0176FF; */
    color:#ccc;
    font-weight: 600;
}
.table-result .btn {
    padding: 2px 5px;
}
.btn-second:hover {
    background: #0176FF;
}
</style>
<script>
export default {
  name: 'treegridview',
  props: [ 'id', 'datalist', 'headers', 'flcol', 'filegroup', 'lastcoltemplate', 'showheader', 'checkheader', 'styleObject', 'groupKey' ],
  watch: {
    datalist: function (newval, oldval) {
      this.tabledata = newval
      this.totalrecord = (this.tabledata && this.tabledata.length > 0) ? this.tabledata.length : 0
      this.currentrecord = 0
      this.totalpage = Math.floor(this.totalrecord / this.recordperpage)
      this.currentpage = Math.floor(this.currentrecord / this.recordperpage)
      //
    }
  },
  data () {
    return {
      page: 0, // Page number
      currentrecord: 0, // Current record
      totalrecord: 0,
      currentpage: Math.floor(this.currentrecord / this.recordperpage),
      recordperpage: 10,
      totalpage: Math.floor(this.totalrecord / this.recordperpage),
      headerList: [],
      fullDataList: [],
      oriData: true,
      tabledata: [],
      checkAll: false,
      showHeader: true,
      groupKeys: [],
      groupConfig: this.groupKey,
      result: [],
      delay: 700,
      clicks: 0,
      timer: null

    }
  },
  mounted () {
    this.tabledata = this.datalist
    this.totalrecord = this.tabledata ? this.tabledata.length : 0
    this.groupKeys = this.getGroupKey()
    this.currentrecord = 0
    this.totalpage = Math.floor(this.totalrecord / this.recordperpage)
    this.currentpage = Math.floor(this.currentrecord / this.recordperpage)
    //console.log(this.flcol, !this.flcol)
    this.headerList = this.getHeaderList()
    this.groupConfig = this.groupKey
    
  },
  computed: {
    headerClasses (idx) {
      return {
        'w20': idx <= 1
      }
    },
    pagedDataList () {
      let data = this.datalist.slice(this.currentpage * this.recordperpage, (this.currentpage + 1) * this.recordperpage)
      // alert(this.tabledata.length)
      var keys = []
      var pagerData = []
      let keyVal = this.groupConfig.keyValue
      data.forEach(function (v) {
        if (keys.filter(f => f === v[keyVal]).length === 0) {
          keys.push(v[keyVal])
        }
      })
      let i = 0
      keys.forEach(function (key) {
        var groupKey = {type: 0, data: key, i: -999999}
        pagerData.push(groupKey)
        let databyKey = data.filter(d1 => d1[keyVal] === key)
        databyKey.forEach(function (item) {
          var dataitem = {type: 1, data: item, i: i}
          pagerData.push(dataitem)
          i++
        })
      })
      return pagerData
    },
    rowSelecteds () {
      return this.tabledata.filter(function (e) {
        return e.ischecked === true
      })
    }
  },
  methods: {
    oneClick: function (i, val, event) {
      this.clicks++
      if (this.clicks === 1) {
        this.timer = setTimeout(() => {
          this.result.push(event.type)
          this.clicks = 0
        }, this.delay)
      } else {
        clearTimeout(this.timer)
        this.result.push('dblclick')
        this.clicks = 0
        this.$emit('dblclick', this.tabledata[this.currentrecord])
        console.log('dblclick event')
      }
      this.changeRecord(i, val)
    },
    changeRecord (numChange = 1, exact = false) {
      if (numChange !== -999999) {
        if (!exact) {
          if (this.currentrecord + numChange < 0) this.currentrecord = 0
          else if (this.currentrecord + numChange >= this.tabledata.length) this.currentrecord = this.tabledata.length - 1
          else this.currentrecord += numChange
        } else { this.currentrecord = numChange + this.currentpage * this.recordperpage }
        this.currentpage = Math.floor(this.currentrecord / this.recordperpage)
        this.$emit('recordChange', this.tabledata[this.currentrecord])
      }
    },
    changePage (numChange = 1, exact = false) {
      if (!exact) {
        if (this.currentpage + numChange < 0) this.currentpage = 0
        else if (this.currentpage + numChange > this.totalpage) this.currentpage = this.totalpage
        else this.currentpage += numChange
      } else { this.currentpage = numChange }
      this.currentrecord = this.currentpage * this.recordperpage
    },
    getHeaderList () {
      if (this.headers && this.headers.length > 0) {
        return this.headers
      }
      if (this.datalist && this.datalist.length > 0) {
        var list = Object.getOwnPropertyNames(this.datalist[0])
        var retlist = []
        for (var item of list) {
          if (item == '__ob__') continue // to resolve a problem with special prop name
          else { retlist.push({name: item, text: item, value: ''}) }
        }
        return retlist
      }
      return []
    },
    filterDataList () {
      if (this.oriData) {
        this.fullDataList = this.datalist
        this.oriData = false
      }
      var pagedData = this.fullDataList
      for (var header of this.headerList) {
        if (header.value && header.value.length > 0) {
          pagedData = pagedData.filter(a => {
            console.log(a, header.name)
            return a[header.name] && a[header.name].toString().toLowerCase().indexOf(header.value.toLowerCase()) >= 0
          })
        }
      }
      this.tabledata = pagedData
      this.totalpage = this.tabledata.length
    },
    checkedItemsEvent () {
      let selectedrows = this.tabledata.filter(function (e) {
        return e.ischecked === true
      })
      this.checkAll = (selectedrows.length === this.tabledata.length)
      this.$emit('checkedItemsEventTable', selectedrows)
      return selectedrows
    },
    checkAllEvent (event) {
      let checkVal = this.checkAll
      this.tabledata.forEach(function (part, index, theArray) {
        theArray[index].ischecked = checkVal
      })
    },
    getGroupKey: function () {
      var keys = []
      let keyVal = this.groupConfig.keyValue
      this.datalist.forEach(function (v) {
        if (keys.filter(f => f === v[keyVal]).length === 0) {
          keys.push(v[keyVal])
        }
      })
      return keys
    },


    btn_download(){
      console.log('download');
      this.$emit('download_function', this.tabledata[this.currentrecord]);
    }
  }
}
</script>
