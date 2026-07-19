<template>
  <div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w100">Nhóm Menu</div>
          <div class="value">
              <div class="select-custom" ref="ds_nhom_menu">
                  <select2 :settings="{ dropdownParent: $refs['ds_nhom_menu'] }" ref="cboNhomMenu"
                      v-model="nhommenu_selected" class="select2"
                      :options="danhsach_nhommenu.map(e=> ({id: e.NHOM_MENU_ID, text: e.NHOM_MENU}))"
                      @change="changeNhomMenu"
                  >
                  </select2>
              </div>  
          </div>
        </div>
      </div>
    </div>
    <div class="box-move-select-table mart10">
      <div class="box-col box-form">
        <div class="info-row">
          <div class="input-icon-right">
            <input type="text" class="form-control" @keyup="filterChuaGan_Keyup" v-model="input_search_chuagan"  placeholder="Nhập thông tin tìm kiếm"/>
            <span class="icon nc-icon-outline ui-1_zoom"></span>
          </div>
        </div>
        <div class="tree-plus tree-icon" style="height: 600px; overflow: auto">
          <ejs-treeview  id='treeviewChuaGan'
            ref="treeObjChuaGan" 
            :fields="tree_menu_chuagan"
            :showCheckBox='true' 
            :allowMultiSelection='true'
            :nodeChecked="nodeCheckedChuaGan"
            :expandedNodes="expandedNodesChuaGan"
          />
        </div>
      </div>
      <div class="actions">
        <button class="btn btn-main" @click="ganQuyen">
          <span class="-ap icon-plus3"></span>
        </button>
        <button class="btn btn-main" @click="boGanQuyen">
          <span class="-ap icon-minus3"></span>
        </button>
      </div>
      <div class="box-col box-form">
        <div class="info-row">
          <div class="input-icon-right">
            <input type="text" class="form-control" @keyup="filterGan_Keyup" v-model="input_search_gan"  placeholder="Nhập thông tin tìm kiếm"/>
            <span class="icon nc-icon-outline ui-1_zoom"></span>
          </div>
        </div>
        <div class="tree-plus tree-icon" style="height: 600px; overflow: auto">
            <ejs-treeview  id='treeviewGan'
              ref="treeObjGan" 
              :fields="tree_menu_gan"
              :showCheckBox='true' 
              :allowMultiSelection='true'
              :nodeChecked="nodeCheckedGan"
              :expandedNodes="expandedNodesGan"
            />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import ManagePermissionAPI from './ManagePermissionAPI'

export default {
    name:'PhanQuyenMenu',
    props:{
      loai_ct_id:{
        type:[Number, String],
        default:-1
      },
      quyen_id:{
        type:[Number, String],
        default:-1
      }
    },
    computed:{
      tree_menu_chuagan(){
        //let danhsach_tree=[]
        //nodeId:-1 phải khác 0 với root
        let tree_menu= this.cleanTree(this.buildTree({ nodeId:-1, nodeText:'Menu chưa gán', nodeChild:[], expanded: true}, this.danhsach_chuagan, 'menu_id','ten_hienthi','unitlevel'))
        //console.log('tree_menu_chuagan', tree_menu)
        //let tree_search=
        //danhsach_tree.push(tree_menu)
        let danhsach_tree= this.searchTree([tree_menu], node =>node.nodeText.toLowerCase().includes(this.value_search_chuagan))
        //console.log('temp', temp)
        return { dataSource: danhsach_tree, id: 'nodeId', text: 'nodeText', child: 'nodeChild'}
      },
      tree_menu_gan(){
        //let danhsach_tree=[]
        //nodeId:-1 phải khác 0 với root
        let tree_menu= this.cleanTree(this.buildTree({ nodeId:-1, nodeText:'Menu đã gán', nodeChild:[], expanded: true}, this.danhsach_gan, 'menu_id','ten_hienthi','unitlevel'))
        //danhsach_tree.push(tree_menu)
        let danhsach_tree= this.searchTree([tree_menu], node =>node.nodeText.toLowerCase().includes(this.value_search_gan))
        return { dataSource: danhsach_tree, id: 'nodeId', text: 'nodeText', child: 'nodeChild'}
      },
    },
    data(){
      return{
        danhsach_nhommenu:[],
        nhommenu_selected:null,
        isFirstChangeNhomMenu:false,
        dataCheckGan:[],
        dataCheckChuaGan:[],
        danhsach_gan:[],
        danhsach_chuagan:[],
        expandedNodesGan:[],
        expandedNodesChuaGan:[],
        input_search_chuagan:'',
        value_search_chuagan:'',
        timeOutChuaGan:null,
        input_search_gan:'',
        value_search_gan:'',
        timeOutGan:null

      }
    },
    methods:{
      changeNhomMenu(){
        this.dataCheckGan=[]
        this.dataCheckChuaGan=[]
        this.input_search_chuagan=''
        this.value_search_chuagan=''
        this.timeOutChuaGan=null
        this.input_search_gan=''
        this.value_search_gan=''
        this.timeOutGan=null

        //lấy danh sách gán chưa gán
        this.danhsach_hienthi_gan_chuagan(this.quyen_id)

      },
      changeQuyen(value){
        this.dataCheckGan=[]
        this.dataCheckChuaGan=[]
        this.input_search_chuagan=''
        this.value_search_chuagan=''
        this.timeOutChuaGan=null
        this.input_search_gan=''
        this.value_search_gan=''
        this.timeOutGan=null
        //lấy danh sách gán chưa gán
        this.danhsach_hienthi_gan_chuagan(value)
      },
      nodeCheckedGan(args){
        var treeObj = document.getElementById('treeviewGan').ej2_instances[0];
        this.dataCheckGan=treeObj.checkedNodes
        console.log('checkGan', this.dataCheckGan)
      },
      nodeCheckedChuaGan(args){
        var treeObj = document.getElementById('treeviewChuaGan').ej2_instances[0];
        this.dataCheckChuaGan=treeObj.checkedNodes
        console.log('checkChuaGan', this.dataCheckChuaGan)
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
      ganQuyen(){
        if(this.dataCheckChuaGan.length==0){
          return
        }
        //tạo dữ liệu
        // let data=this.getDanhSachQuyenGan()
        // this.sp_phanquyen_baocao_update(data)
        let menu_id_arr=[]
        this.dataCheckChuaGan.forEach((item)=>{
          if(item!='-1'){
            menu_id_arr.push(Number(item))
          }
        })
        if(menu_id_arr.length==0){
          return
        }
        this.sp_action_ds_quyen_menu(this.nhommenu_selected, menu_id_arr, this.quyen_id, 1)
      },
      boGanQuyen(){
        if(this.dataCheckGan.length==0){
          return
        }
        //tạo dữ liệu
        //let data=this.getDanhSachQuyenBoGan()
        //this.sp_phanquyen_baocao_delete(data)
        let menu_id_arr=[]
        this.dataCheckGan.forEach((item)=>{
          if(item!='-1'){
            menu_id_arr.push(Number(item))
          }
        })
        if(menu_id_arr.length==0){
          return
        }
        this.sp_action_ds_quyen_menu(this.nhommenu_selected, menu_id_arr, this.quyen_id, 0)
      },
      //expanded: true
      buildTree(tree,ds,idfield,textfield,levelfield) {
        if(!ds || ds.length<=0) return tree;
        var i = 0;
        var level = 1;
        var lastparentitem = tree;
        for(;i<ds.length;) {
          if(ds[i][levelfield]==level)
          {
            lastparentitem.nodeChild.push({ nodeId:ds[i][idfield], nodeText:ds[i][textfield], nodeChild:[], parent:lastparentitem, expanded: ds[i][levelfield]==1});
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
      async danhsach_hienthi_gan_chuagan(quyen_id){
        if(this.nhommenu_selected==null){
          return
        }
        this.danhsach_gan=[]
        this.danhsach_chuagan=[]
        let result=await this.sp_lay_ds_menu_quyen(this.nhommenu_selected, quyen_id)
        if(result){
          this.danhsach_gan=result.menu?result.menu:[]
          this.danhsach_chuagan=result.menu1?result.menu1:[]
        }
        
      },
      async lay_danhsach_nhom_menu(){
          try{
              this.loading(true)
              let response=await ManagePermissionAPI.lay_danhsach_nhom_menu(this.axios)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  return response.data.data
              }else{
                  return []
              }
          }catch(e){
            this.loading(false)
            if(e.data&&e.data.message){
                this.$toast.error(e.data.message)
            }else if(e.response&&e.response.data&&e.response.data.message){
                this.$toast.error(e.response.data.message)
            }else{
                this.$toast.error('Đã có lỗi khi lấy danh sách nhóm menu')
            }
            return []
          }
      },
      async sp_lay_ds_menu_quyen(nhom_menu_id, quyen_id){
        try{
              this.loading(true)
              let response=await ManagePermissionAPI.sp_lay_ds_menu_quyen(this.axios, nhom_menu_id, quyen_id)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  return response.data.data
              }else{
                  return null
              }
          }catch(e){
            this.loading(false)
            if(e.data&&e.data.message){
                this.$toast.error(e.data.message)
            }else if(e.response&&e.response.data&&e.response.data.message){
                this.$toast.error(e.response.data.message)
            }else{
                this.$toast.error('Đã có lỗi khi lấy danh sách menu')
            }
            return null
          }
      },
      async sp_action_ds_quyen_menu(nhom_menu_id, menu_id_arr, quyen_id, kieu){
          try{
              this.loading(true)
              let response = await ManagePermissionAPI.sp_action_ds_quyen_menu(this.axios, nhom_menu_id, menu_id_arr, quyen_id, kieu)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.danhsach_gan=[]
                    this.danhsach_chuagan=[]
                    this.dataCheckGan=[]
                    this.dataCheckChuaGan=[]

                    this.input_search_chuagan=''
                    this.value_search_chuagan=''
                    this.timeOutChuaGan=null
                    this.input_search_gan=''
                    this.value_search_gan=''
                    this.timeOutGan=null
                    
                    if(kieu==1){
                      this.$toast.success('Gán quyền menu thành công!')
                    }else{
                      this.$toast.success('Bỏ gán quyền menu thành công!')
                    }
                    if(response.data.data.menu){
                      this.$nextTick(()=>{
                        this.danhsach_gan=response.data.data.menu
                      })
                    }
                    if(response.data.data.menu1){
                      this.$nextTick(()=>{
                        this.danhsach_chuagan=response.data.data.menu1
                      })
                    }
                    
              }else{
                  if(response && response.data &&response.data.message){
                      this.$toast.error(response.data.message)
                  }else {
                    if(kieu==1){
                      this.$toast.error('Đã xảy ra lỗi, gán quyền menu không thành công')
                    }else{
                      this.$toast.error('Đã xảy ra lỗi, bỏ gán quyền menu không thành công')
                    }
                  }  
              }
          }catch(error){
              this.loading(false)
              if(error.data&&error.data.message){
                  this.$toast.error(error.data.message)
              }else if(error.response&&error.response.data&&error.response.data.message){
                  this.$toast.error(error.response.data.message)
              }else{
                  if(kieu==1){
                    this.$toast.error('Đã xảy ra lỗi, gán quyền menu không thành công')
                  }else{
                    this.$toast.error('Đã xảy ra lỗi, bỏ gán quyền menu không thành công')
                  }
              }
          }
      },
      async initData(){
        this.danhsach_nhommenu=[]
        this.danhsach_gan=[]
        this.danhsach_chuagan=[]
        this.dataCheckGan=[]
        this.dataCheckChuaGan=[]
        this.input_search_chuagan=''
        this.value_search_chuagan=''
        this.timeOutChuaGan=null
        this.input_search_gan=''
        this.value_search_gan=''
        this.timeOutGan=null
        this.nhommenu_selected=null

        let result = await this.lay_danhsach_nhom_menu()
        if(this.loai_ct_id==-1){
          this.danhsach_nhommenu=result
        }else{
          this.danhsach_nhommenu=result.filter(x=>x.LOAI_CT_ID==this.loai_ct_id)
        }
        if(this.danhsach_nhommenu.length>0){
          this.nhommenu_selected=this.danhsach_nhommenu[0].NHOM_MENU_ID
        }

        await this.danhsach_hienthi_gan_chuagan(this.quyen_id)

      }
    },
}
</script>
<style>
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
</style>
