<template>
  <div>
    <div class="table-content" style="max-height: 580px;" v-if="(datalist && datalist.length > 0) || showHeader">
      <table class="table-result table-gachle tree-grid-2" :id="id">
        <thead class="center">
          <tr>
            <th class="w20"></th>
            <th class="w20"></th>
            <th v-for="(h, hdx) in headerList" :key="hdx" :class="{ w30: hdx == headerList.length - 1 && lastcoltemplate != -1, stickylastcol: hdx == headerList.length - 1 && flcol }">
              {{ h.text }}
            </th>
          </tr>
          <tr>
            <th class="w20"></th>
            <th class="w20"></th>
            <th v-for="(h, hdx) in headerList" :key="hdx" :class="{ w30: hdx == headerList.length - 1 && lastcoltemplate != -1, stickylastcol: hdx == headerList.length - 1 && flcol }">
              <div class="input-icon-right">
                <input type="text" class="form-control" v-model="h.value" @change="filterDataList" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-show="d.display" :class="{ highlight: idx == currentrecord % recordperpage }" v-for="(d, idx) in pagedDataList" :key="idx" @click="changeRecord(idx, true)">
            <td class="tree-first w20">
              <div class="tree-line"></div>
              <div class="tree-indent"></div>
            </td>
            <td class="w20 center">
              <span class="fa fa-play text-main" v-if="idx == currentrecord % recordperpage"></span>
            </td>
            <td :class="{ highlight1: idx == currentrecord % recordperpage, 'nowrap w30 center': hdx == headerList.length - 1 && lastcoltemplate != -1, stickylastcol: hdx == headerList.length - 1 && flcol }" v-for="(h, hdx) in headerList" :key="hdx">
              <template v-if="hdx === 0">
                <div v-for="index in d.level" :key="index" class="treegrid-indent"></div>
                <a v-if="d.children && d.children.length > 0" href="#" style="margin-left:-23px;" @click.prevent="clickRowExpand(d)">
                  <div v-if="d.isfile != 1" class="tree-arrow marr5"></div>
                </a>
                <a v-else>
                  <div v-if="d.isfile == 1" class="tree-arrow2 marr5"></div>
                </a>

                <span class="icon nc-icon-glyph files_single-content-02 text-main"></span>
              </template>
              <template v-if="hdx < headerList.length - 1 || !lastcoltemplate || lastcoltemplate == '0' || lastcoltemplate == -1">
                <span v-if="h.type && h.type === 'bool'">
                  <input type="checkbox" :value="d[h.name] === true || d[h.name] == 'true'" />
                </span>
                <span v-else>{{ d[h.name] }}</span>
              </template>

              <template v-if="hdx == headerList.length - 1 && lastcoltemplate && lastcoltemplate == '1'">
                <a :href="d[h.name]" class="act">
                  <span class="icon nc-icon-outline ui-1_eye-17"></span>
                </a>
                <a :href="d[h.name]" class="act">
                  <span class="icon nc-icon-outline arrows-2_hit-up"></span>
                </a>
              </template>
              <template v-if="hdx == headerList.length - 1 && lastcoltemplate && lastcoltemplate == '2'">
                <a href="#" class="btn btn-second" :class="{ checkedbox: d[h.name] }" @click.prevent="d[h.name] = !d[h.name]">
                  <span class="nc-icon-glyph ui-1_check-circle-08"></span>
                </a>
              </template>
              <template v-if="hdx === 0 && (d.isfile == 1||d.full_url)">
                <div class="pull-right">
                  <button class="btn btn-second button-note-bg pad4" title="Xem trước" @click="viewFile(d)">
                    <span class="lh14 one-search"></span>
                  </button>
                  <a href="#" class="btn btn-second button-note-bg pad4" title="Tải về" @click="dowloadFile(d)">
                    <span class="lh14 one-save"></span>
                  </a>
                </div>
                <div class="clearfix"></div>
              </template>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="tc-pagination" v-if="tabledata && tabledata.length > 0">
      <a href="#" class="btn  one-arrow-left-forward" @click.prevent="changePage(0, true)"></a>
      <a href="#" class="btn fa fa-angle-double-left" @click.prevent="changePage(-1)"></a>
      <a href="#" class="btn fa fa-angle-left" @click.prevent="changeRecord(-1)"></a>
      <span class="text">Bản ghi</span>
      <div class="dropdown inline">
        <div class="fw6 input-number" data-toggle="dropdown">
          <span class="text-main">{{ currentrecord + 1 }}</span
          >/{{ totalrecord }}
        </div>
        <div class="dropdown-menu">
          <a href="#" class="dropdown-item">10</a>
          <a href="#" class="dropdown-item">20</a>
        </div>
      </div>

      <a href="#" class="btn fa fa-angle-right" @click.prevent="changeRecord(1)"></a>
      <a href="#" class="btn fa fa-angle-double-right" @click.prevent="changePage(1)"></a>
      <a href="#" class="btn one-arrow-right-forward" @click.prevent="changePage(totalpage, true)"></a>
    </div>
    <div v-if="!datalist || datalist.length <= 0">
      <slot name="nodata"> <p class="text-center">Không có số liệu ...</p> </slot>
    </div>
  </div>
</template>
<style scoped>
.stickylastcol {
  position: -webkit-sticky;
  position: sticky;
  right: 0;
  background: inherit;
}
.table-result {
  border-collapse: separate;
  border-spacing: 0;
}
.table-result tbody tr:nth-child(2n + 1) {
  background-color: #fff;
}
.btn-second.checkedbox {
  color: #0176ff;
}
.btn-second {
  background-color: #fff;
  border-color: #0176ff;
  /* color: #0176FF; */
  color: #ccc;
  font-weight: 600;
}
.table-result .btn {
  padding: 2px 5px;
}
.btn-second:hover {
  background: #0176ff;
}
.tree-grid-2 .tree-arrow2 {
  width: 16px;
  height: 16px;
  color: #0176ff;
  font-family: 'ap';
  font-size: 16px;
  cursor: pointer;
  text-align: center;
  line-height: 20px;
  display: inline-block;
}
</style>
<script>
import axios from 'axios'
axios.defaults.withCredentials = true
export default {
  components: {
    axios
  },
  name: 'gridview',
  props: ['id', 'datalist', 'treedata', 'headers', 'flcol', 'filegroup', 'lastcoltemplate', 'showheader', 'styleObject', 'showCheck'],
  watch: {
    datalist: function(newval, oldval) {
      this.datalist = newval
      this.tabledata = newval
      this.totalrecord = this.tabledata && this.tabledata.length > 0 ? this.tabledata.length : 0
      this.currentrecord = 0
      this.totalpage = Math.floor(this.totalrecord / this.recordperpage)
      this.currentpage = Math.floor(this.currentrecord / this.recordperpage)
    }
  },
  data() {
    return {
      page: 0, // Page number
      currentrecord: 0, // Current record
      totalrecord: 0,
      currentpage: Math.floor(this.currentrecord / this.recordperpage),
      recordperpage: 30,
      totalpage: Math.floor(this.totalrecord / this.recordperpage),
      headerList: [],
      fullDataList: [],
      oriData: true,
      tabledata: [],
      checkAll: false,
      showHeader: true,
      mapNode: {}
    }
  },
  mounted() {
    this.tabledata = this.datalist
    this.totalrecord = this.tabledata ? this.tabledata.length : 0
    this.currentrecord = 0
    this.totalpage = Math.floor(this.totalrecord / this.recordperpage)
    this.currentpage = Math.floor(this.currentrecord / this.recordperpage)
    console.log(this.flcol, !this.flcol)
    this.headerList = this.getHeaderList()
  },
  created() {
    this.$parent.$on('displayEvent', this.clickRowExpand)
  },
  computed: {
    headerClasses(idx) {
      return {
        w20: idx <= 1
      }
    },
    pagedDataList() {
      return this.tabledata.slice(this.currentpage * this.recordperpage, (this.currentpage + 1) * this.recordperpage)
    },
    rowSelecteds() {
      return this.tabledata.filter(function(e) {
        return e.ischecked === true
      })
    }
  },
  methods: {
    changeRecord(numChange = 1, exact = false) {
      try {
        if (!exact) {
          if (this.currentrecord + numChange < 0) this.currentrecord = 0
          else if (this.currentrecord + numChange >= this.tabledata.length) this.currentrecord = this.tabledata.length - 1
          else this.currentrecord += numChange
        } else {
          this.currentrecord = numChange + this.currentpage * this.recordperpage
        }
        this.currentpage = Math.floor(this.currentrecord / this.recordperpage)
        this.$emit('recordChange', this.tabledata[this.currentrecord])
      } catch (err) {
        console.log('🚀 ~ file: gridviewhs.vue:227 ~ changeRecord ~ err:', err)
      }
    },
    changePage(numChange = 1, exact = false) {
      if (!exact) {
        if (this.currentpage + numChange < 0) this.currentpage = 0
        else if (this.currentpage + numChange > this.totalpage) this.currentpage = this.totalpage
        else this.currentpage += numChange
      } else {
        this.currentpage = numChange
      }
      this.currentrecord = this.currentpage * this.recordperpage
    },
    getHeaderList() {
      if (this.headers && this.headers.length > 0) {
        return this.headers
      }
      if (this.datalist && this.datalist.length > 0) {
        var list = Object.getOwnPropertyNames(this.datalist[0])
        var retlist = []
        for (var item of list) {
          if (item == '__ob__') continue
          // to resolve a problem with special prop name
          else {
            retlist.push({ name: item, text: item, value: '' })
          }
        }
        return retlist
      }
      return []
    },
    filterDataList() {
      if (this.oriData) {
        this.fullDataList = this.datalist
        this.oriData = false
      }
      var pagedData = this.fullDataList
      for (var header of this.headerList) {
        if (header.value && header.value.length > 0) {
          pagedData = pagedData.filter((a) => {
            console.log(a, header.name)
            return (
              a[header.name] &&
              a[header.name]
                .toString()
                .toLowerCase()
                .indexOf(header.value.toLowerCase()) >= 0
            )
          })
        }
      }
      this.tabledata = pagedData
      this.totalpage = this.tabledata.length
    },
    checkedItemsEvent() {
      let selectedrows = this.tabledata.filter(function(e) {
        return e.ischecked === true
      })
      this.checkAll = selectedrows.length === this.tabledata.length
      this.$emit('checkedItemsEventTable', selectedrows)
      return selectedrows
    },
    checkAllEvent(event) {
      let checkVal = this.checkAll
      this.tabledata.forEach(function(part, index, theArray) {
        theArray[index].ischecked = checkVal
      })
    },
    downloadItem: function(item) {
      axios
        .get(item.url, { responseType: 'blob' })
        .then((response) => {
          const blob = new Blob([response.data], { type: 'application/pdf' })
          const link = document.createElement('a')
          link.href = URL.createObjectURL(blob)
          link.download = item.name
          link.click()
          URL.revokeObjectURL(link.href)
        })
        .catch(console.error)
    },
    downloadURI: function(item) {
      let uri = item.url
      let name = 'test'
      var link = document.createElement('a')
      link.download = name
      link.href = uri
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link)
    },
    download: function(item) {
      axios({
        url: item.url,
        method: 'GET',
        responseType: 'blob'
      }).then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', item.name)
        document.body.appendChild(link)
        link.click()
        document.body.removeChild(link)
      })
    },
    clickRowExpand: function(item, fix) {
      try {
        if (item == null || item.id==null) {
          console.log('clickRowExpand ~ item null')
          return
        }
        console.log('clickRowExpand ~ item:', item, fix)
        var newval = true
        var a = this.mapNode[item.id]
        if (a != null) {
          this.mapNode[item.id] = !this.mapNode[item.id]
        } else {
          this.mapNode[item.id] = !item.display
        }
        newval = this.mapNode[item.id]
        if (fix != null) {
          newval = fix
        }
        var children = this.findChildItems(item)
        if (children != null && children.length > 0) {
          this.tabledata
            .filter((x) => children.filter((y) => y.id === x.id).length > 0)
            .forEach(function(item) {
              item.display = newval
            })
        }
        this.changeRecord(1)
        this.changeRecord(-1)
      } catch (err) {
        console.log('🚀 ~ file: gridviewhs.vue:354 ~ err:', err)
      }
    },
    viewFile: function(item) {
      //this.$emit('viewFileEvent',item, this.tabledata[this.currentrecord])
      this.$emit('viewFileEvent', item)
    },
    findChildItems: function(item) {
      var stack = []
      var result = []
      this.tabledata
        .filter((x) => x.cha_id == item.id)
        .forEach(function(item) {
          stack.push(item)
        })
      while (stack.length > 0) {
        let node = stack.pop()
        result.push(node)
        if (this.tabledata.filter((x) => x.cha_id == node.id).length > 0) {
          this.tabledata
            .filter((x) => x.cha_id == node.id)
            .forEach(function(item) {
              if (item != null) stack.push(item)
            })
        }
      }
      return result
    },
    dowloadFile: function(item) {
      this.$emit('dowloadFileEvent', item)
    }
  }
}
</script>
