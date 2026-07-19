<template>
<div>
<div class="table-content marb0" style="overflow-x: hidden;max-height: 400px;">
  <table class="table-result nowrap table-gachle" :id="id">
      <thead class="center">
          <tr>
              <th class="w20"></th>
              <th class="w20" v-if="checkheader">
                  <div class="check-action">
                      <input type="checkbox" class="check">
                      <span class="name"></span>
                  </div>
              </th>
              <th v-for="(h,hdx) in headerList" :key="hdx" :class="{'stickylastcol w30':(hdx==headerList.length-1) && flcol}">
                {{ h.text }}
              </th>
          </tr>
          <tr>
              <th></th>
              <th v-if="checkheader">
                  <div class="check-action">
                      <input type="checkbox" disabled class="check">
                      <span class="name"></span>
                  </div>
              </th>
              <th v-for="(h,hdx) in headerList" :key="hdx" :class="{'stickylastcol w30':(hdx==headerList.length-1) && flcol}">
                  <div class="input-icon-right">
                      <input type="text" class="form-control" v-model="h.value" @change="filterDataList" @keyup="filterDataList">
                      <span class="icon nc-icon-outline ui-1_zoom"></span>
                  </div>
              </th>
          </tr>
          
      </thead>
      <tbody v-if="!datalist || !datalist.length || datalist.length<=0"><tr><td :colspan="headerList.length+1+(checkheader?1:0)">Không có bản ghi nào để hiển thị</td></tr></tbody>
      <tbody v-if="datalist && datalist.length>0">
          <tr class="highlight" v-if="filegroup">
              <td colspan="5" class="fw6 ">
                  <span class="-ap  icon-chevron-thin-down text-main"></span> Nhóm file: {{ filegroup }}
              </td>
              <td></td>
          </tr>
          <tr :class="{ 'highlight' : idx==(currentrecord%recordperpage) }" v-for="(d,idx) in pagedDataList" :key="idx" @click="changeRecord(idx,true)">
              <td class="w20">
                  <span class="fa fa-play text-main" v-if="idx==(currentrecord%recordperpage)"></span>
              </td>
              <td class="selected" v-if="checkheader">
                  <div class="check-action">
                      <input type="checkbox" class="check" v-model="d.ischecked">
                      <span class="name"></span>
                  </div>
              </td>
              <td :class="{ 'highlight1' : idx==(currentrecord%recordperpage),'nowrap':hdx==headerList.length-1,'stickylastcol w30 center':(hdx==headerList.length-1) && flcol }" v-for="(h,hdx) in headerList" :key="hdx">
                <template v-if="(hdx<headerList.length-1) || (!lastcoltemplate) || (lastcoltemplate=='0')">
                  <span :class="h.align?h.align:'left'">{{ d[h.name] }}</span>
                </template>
                <template v-if="(hdx==headerList.length-1) && lastcoltemplate && (lastcoltemplate=='1')">
                  <a :href="d[h.name]" class="act">
                      <span class="icon nc-icon-outline ui-1_eye-17"></span>
                  </a>
                  <a :href="d[h.name]" class="act">
                      <span class="icon nc-icon-outline arrows-2_hit-up"></span>
                  </a>
                </template>
                <template v-if="(hdx==headerList.length-1) && lastcoltemplate && (lastcoltemplate=='2')">
                  <a href="#" class="btn btn-second" :class="{'checkedbox':d[h.name]}" @click.prevent="d[h.name]=!d[h.name]">
                      <span class="nc-icon-glyph ui-1_check-circle-08"></span>
                  </a>
                </template>
              </td>
          </tr>
      </tbody>
  </table>
</div>
<div class="tc-pagination" v-if="datalist && datalist.length>0">
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
<!-- <div v-if="!datalist || datalist.length<=0">
  No-data...
</div> -->
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
  name: 'gridview',
  props: [ 'id','datalist','headers','flcol','filegroup','lastcoltemplate','checkheader' ],
  watch: {
    datalist:function(newval,oldval) {
      this.datalist = newval;
      this.totalrecord = (this.datalist && this.datalist.length>0)?this.datalist.length:0;
      this.currentrecord = 0;
      this.totalpage = Math.floor(this.totalrecord/this.recordperpage);
      this.headerList = this.getHeaderList();
      this.currentpage = Math.floor(this.currentrecord/this.recordperpage);
    }
  },
  data() {
     return {
       page: 0, //Page number
       currentrecord: 0, //Current record
       totalrecord: 0,
       currentpage:Math.floor(this.currentrecord/this.recordperpage),
       recordperpage: 10,
       totalpage: Math.floor(this.totalrecord/this.recordperpage),
       headerList: [],
       fullDataList: [],
       oriData: true,
     }
  },
  mounted() {
    this.totalrecord = this.datalist?this.datalist.length:0;
    this.currentrecord = 0;
    this.totalpage = Math.floor(this.totalrecord/this.recordperpage);
    this.currentpage = Math.floor(this.currentrecord/this.recordperpage);
    //console.log(this.flcol,!this.flcol);
    this.headerList = this.getHeaderList();
  },
  computed: {
    headerClasses(idx){
      return {
        'w20':idx<=1,
      }
    },
    pagedDataList() {
      return this.datalist.slice(this.currentpage*this.recordperpage,(this.currentpage+1)*this.recordperpage);
    },
  },
  methods: {
    changeRecord(numChange=1,exact=false) {
      if(!exact)
      {
        if(this.currentrecord+numChange<0) this.currentrecord=0;
        else if(this.currentrecord+numChange>=this.datalist.length) this.currentrecord=this.datalist.length-1;
        else this.currentrecord+=numChange;
      }
      else
        this.currentrecord = numChange+this.currentpage*this.recordperpage;
      this.currentpage = Math.floor(this.currentrecord/this.recordperpage);
      this.$emit('recordChange',this.datalist[this.currentrecord]);
    },
    changePage(numChange=1,exact=false) {
      if(!exact)
      {
        if(this.currentpage+numChange<0) this.currentpage=0;
        else if(this.currentpage+numChange>this.totalpage) this.currentpage=this.totalpage;
        else this.currentpage += numChange;
      }
      else
        this.currentpage = numChange;
      this.currentrecord = this.currentpage*this.recordperpage;
    },
    getHeaderList() {
      if(this.headers && this.headers.length>0) {
        return this.headers;
      }
      if(this.datalist && this.datalist.length>0)
      {
        var list = Object.getOwnPropertyNames(this.datalist[0]);
        var retlist = [];
        for(var item of list)
          if(item=='__ob__') continue; // to resolve a problem with special prop name
          else
            retlist.push({name:item,text:item,value:''});
        return retlist;
      }
      return [];
    },
    filterDataList() {
      if(this.oriData)
      {
        this.fullDataList=this.datalist;
        this.oriData=false;
      }
      var pagedData = this.fullDataList;
      for(var header of this.headerList)
        if(header.value && header.value.length>0)
          pagedData = pagedData.filter(a=> {
            //console.log(a,header.name)
            return a[header.name] && a[header.name].toLowerCase().indexOf(header.value.toLowerCase())>=0
          });
      this.datalist = pagedData;
    },
  }
}
</script>