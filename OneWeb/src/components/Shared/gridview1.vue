<template>
<DataGrid
    :columns="columns"                    
    :dataSource="datalist"
    :enable-paging-server="false"                                        
    :allowPaging="true"
    :showFilter="true"
    @selectedRowChanged="grid_selectedRowChanged">
</DataGrid>
</template>
<style scoped>
.stickylastcol {
  position:-webkit-sticky;
  position:sticky;
  right:0;
  /* background:inherit; */
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
  props: [ 'id','datalist','headers','flcol','filegroup','lastcoltemplate','checkheader','height' ],
  computed:{
    columns() {
      var grp = this.filegroup;
      return this.getHeaderList().map((x) => ({fieldName:x.name,headerText:x.text,allowFiltering: true,isGroupedColumn: x.name==grp,textAlign:x.align}));
    }
  },
  methods: {
    grid_selectedRowChanged(selectedData) {
      this.$emit('recordChange',selectedData);
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
    data() {
      return {
        group: this.filegroup,
      }
    }
  }
}
</script>