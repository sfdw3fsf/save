<template>
  <div class="box-move-select-table">
    <div class="box-col marb0">
      <div class="legend-title">Danh sách mục đích đã gắn</div>
      <div class="box-form h-auto">
        <div class="info-row">
          <div class="input-icon-right">
            <input type="text" class="form-control" @keyup="filterGan_Keyup" v-model="input_search_gan"  placeholder="Nhập thông tin tìm kiếm"/>
            <span class="icon nc-icon-outline ui-1_zoom"></span>
          </div>
        </div>
        <div class="tree-plus tree-icon" style="height: 250px; overflow: auto">
          <ejs-treeview  id='treeviewGan'  ref="treeObjGan" 
              :fields='danhsach_mucdich_gan_tree'
              :showCheckBox='true' 
              :allowMultiSelection='true'
              :nodeChecked="nodeCheckedGan"
              :expandedNodes="expandedNodesGan"
              />
        </div>
      </div>
    </div>
    <div class="actions">
      <button class="btn" @click="boGan">
        <span class="-ap icon-chevron-thin-right"></span>
      </button>
      <button class="btn" @click="gan">
        <span class="-ap icon-chevron-thin-left"></span>
      </button>
    </div>
    <div class="box-col marb0">
      <div class="legend-title">Danh sách mục đích chưa gắn</div>
      <div class="box-form h-auto">
        <div class="info-row">
          <div class="input-icon-right">
            <input type="text" class="form-control" @keyup="filterChuaGan_Keyup" v-model="input_search_chuagan"  placeholder="Nhập thông tin tìm kiếm"/>
            <span class="icon nc-icon-outline ui-1_zoom"></span>
          </div>
        </div>
        <div class="tree-plus tree-icon" style="height: 250px; overflow: auto">
          <ejs-treeview  id='treeviewChuaGan'  ref="treeObjChuaGan" 
              :fields='danhsach_mucdich_chuagan_tree'
              :showCheckBox='true'
              :allowMultiSelection='true'
              :nodeChecked="nodeCheckedChuaGan"
              :expandedNodes="expandedNodesChuaGan"
              />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import SuppliesAPI from './SuppliesAPI'
export default {
    name:'ThongTinMucDich',
    props:{
        vattu_id:{
            type:Number,
            default:-1
        }
    },
    computed:{
      danhsach_mucdich_gan_tree(){
        //nodeId:-1 phải khác 0 với root
        let tree= this.cleanTree(this.buildTree({ nodeId:-1, nodeText:'Root', nodeChild:[], expanded: true}, this.danhsach_gan, 'mucdich_id','mucdich','unitlevel'))
        let danhsach_tree= this.searchTree([tree], node =>node.nodeText.toLowerCase().includes(this.value_search_gan))
        return { dataSource: danhsach_tree, id: 'nodeId', text: 'nodeText', child: 'nodeChild'}
      },
      danhsach_mucdich_chuagan_tree(){
        //nodeId:-1 phải khác 0 với root
        let tree= this.cleanTree(this.buildTree({ nodeId:-1, nodeText:'Root', nodeChild:[], expanded: true}, this.danhsach_chuagan, 'mucdich_id','mucdich','unitlevel'))
        let danhsach_tree= this.searchTree([tree], node =>node.nodeText.toLowerCase().includes(this.value_search_chuagan))
        return { dataSource: danhsach_tree, id: 'nodeId', text: 'nodeText', child: 'nodeChild'}
      }
    },
    data(){
      return {
        danhsach_gan:[],
        danhsach_chuagan:[],
        expandedNodesGan:[],
        expandedNodesChuaGan:[],
        dataCheckedGan:[],
        dataCheckedChuaGan:[],

        input_search_chuagan:'',
        value_search_chuagan:'',
        timeOutChuaGan:null,
        input_search_gan:'',
        value_search_gan:'',
        timeOutGan:null
      }
    },
    methods:{
      nodeCheckedGan(args){
        var treeObj = document.getElementById('treeviewGan').ej2_instances[0];
        this.dataCheckedGan=treeObj.checkedNodes
        console.log('checkGan', this.dataCheckedGan)
      },
      nodeCheckedChuaGan(args){
        var treeObj = document.getElementById('treeviewChuaGan').ej2_instances[0];
        this.dataCheckedChuaGan=treeObj.checkedNodes
        console.log('checkChuaGan', this.dataCheckedChuaGan)
      },
      boGan(){
        if(this.dataCheckedGan.length==0){

          return
        }
        let ds_md=[]
        this.dataCheckedGan.forEach((item)=>{
          if(item!=-1){
            ds_md.push({
              MUCDICH_ID:Number(item)
            })
          }
        })
        console.log('ds_md_bogan', ds_md)
        this.fn_del_vattu_md(this.vattu_id, ds_md)

      },
      gan(){
        if(this.dataCheckedChuaGan.length==0){

          return
        }
        let ds_md=[]
        this.dataCheckedChuaGan.forEach((item)=>{
          if(item!=-1){
            ds_md.push({
              MUCDICH_ID:Number(item)
            })
          }
        })
        console.log('ds_md_gan', ds_md)
        this.fn_ins_vattu_md(this.vattu_id,ds_md)
      },
      filterChuaGan_Keyup(args){
        clearTimeout(this.timeOutChuaGan);
        if (args.code == "Enter") {
          this.value_search_chuagan=this.input_search_chuagan.toLowerCase().trim()
        }else{
          this.timeOutChuaGan=setTimeout(function(){
            this.value_search_chuagan=this.input_search_chuagan.toLowerCase().trim()
          }.bind(this), 800);
        }
      },
      filterGan_Keyup(args){
        clearTimeout(this.timeOutChuaGan);
        if (args.code == "Enter") {
          this.value_search_gan=this.input_search_gan.toLowerCase().trim()
        }else{
          this.timeOutChuaGan=setTimeout(function(){
            this.value_search_gan=this.input_search_gan.toLowerCase().trim()
          }.bind(this), 800);
        }
      },
      searchTree(nodes, cb) {
          return nodes.map(node => {
              if (cb(node)) return node;
              let nodeChild = this.searchTree(node.nodeChild || [], cb);
              return nodeChild.length && { ...node,  nodeChild, expanded:true};
          }).filter(Boolean);
      },
      buildTree(tree,ds,idfield,textfield,levelfield) {
        if(!ds || ds.length<=0) return tree;
        var i = 0;
        var level = 1;
        var lastparentitem = tree;
        for(;i<ds.length;) {
          if(ds[i][levelfield]==level)
          {
            lastparentitem.nodeChild.push({ nodeId:ds[i][idfield], nodeText:ds[i][textfield], nodeChild:[], parent:lastparentitem, expanded: false});//ds[i][levelfield]==1
            i++;
            continue;
          }
          if(ds[i][levelfield]>level) {
            lastparentitem = lastparentitem.nodeChild[lastparentitem.nodeChild.length-1];
            level = ds[i][levelfield];
            continue;
          }
          if(ds[i][levelfield]<level) {
            lastparentitem = lastparentitem.parent;
            level--;
            continue;
          }
        }
        return tree;
      },
      cleanTree(item) {
        if(item.nodeChild && item.nodeChild.length>0)
          for(var i of item.nodeChild)
            i = this.cleanTree(i);
        if(item.parent)
          delete item.parent;
        //item.isSelected=checked;
        return item;
      },
      async clearData(){
        this.danhsach_gan=[]
        this.danhsach_chuagan=[]
        this.dataCheckedGan=[]
        this.dataCheckedChuaGan=[]
        
        this.input_search_chuagan=''
        this.value_search_chuagan=''
        this.timeOutChuaGan=null
        this.input_search_gan=''
        this.value_search_gan=''
        this.timeOutGan=null
      },
      async initDuLieu(){
        if(this.vattu_id==-1){
          return
        }
        this.clearData()
        this.danhsach_chuagan=await this.sp_ht_ds_mucdich_vt(this.vattu_id,1)
        this.danhsach_gan=await this.sp_ht_ds_mucdich_vt(this.vattu_id,2)
      },
      async sp_ht_ds_mucdich_vt(vattu_id, kieu){
          try{
              this.loading(true)
              let response = await SuppliesAPI.sp_ht_ds_mucdich_vt(this.axios, vattu_id, kieu)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  return response.data.data
              }else{
                  return []
              }
          }catch(e){
            this.loading(false)
            return []
          }
      },
      async fn_ins_vattu_md(vattu_id, ds_md){
          try{
            this.loading(true)
            let response = await SuppliesAPI.fn_ins_vattu_md(this.axios, vattu_id, ds_md)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                let result=JSON.parse(response.data.data)
                if(result&&result.ERROR_CODE==1){
                    this.$toast.success('Gán thành công!')
                    await this.initDuLieu()
                }else{
                  if(result&&result.MESSAGE){
                    this.$toast.error(result.MESSAGE)
                  }else{
                    this.$toast.error('Gán mục đích vật tư không thành công!')
                  }
                }
            }else{
                if(response && response.data && response.data.message){
                  this.$toast.error(response.data.message)
                }else{
                  this.$toast.error('Gán mục đích vật tư không thành công!')
                }   
            }
          }catch(error){
            this.loading(false)
            if(error.data&&error.data.message){
              this.$toast.error(error.data.message)
            }else if(error.response&&error.response.data&&error.response.data.message){
              this.$toast.error(error.response.data.message)
            }else{
              this.$toast.error('Gán mục đích vật tư không thành công!')
            }
          }
      },
      async fn_del_vattu_md(vattu_id, ds_md){
          try{
            this.loading(true)
            let response = await SuppliesAPI.fn_del_vattu_md(this.axios, vattu_id, ds_md)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                let result=JSON.parse(response.data.data)
                if(result&&result.ERROR_CODE==1){
                    this.$toast.success('Bỏ gán thành công!')
                    this.initDuLieu()
                }else{
                  if(result&&result.MESSAGE){
                    this.$toast.error(result.MESSAGE)
                  }else{
                    this.$toast.error('Bỏ gán mục đích vật tư không thành công!')
                  }
                }
            }else{
                if(response && response.data && response.data.message){
                    this.$toast.error(response.data.message)
                }else{
                    this.$toast.error('Bỏ gán mục đích vật tư không thành công!')
                }   
            }
          }catch(error){
            this.loading(false)
            if(error.data&&error.data.message){
              this.$toast.error(error.data.message)
            }else if(error.response&&error.response.data&&error.response.data.message){
              this.$toast.error(error.response.data.message)
            }else{
              this.$toast.error('Bỏ gán mục đích vật tư không thành công!')
            }
          }
      }
    }
}
</script>