<template>
  <div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w80">Nhóm Hàm</div>
          <div class="value">
              <div class="select-custom" ref="ds_ham">
                  <select2 :settings="{ dropdownParent: $refs['ds_ham'] }" ref="cboHam"
                      v-model="ham_selected" class="select2"
                      :options="danhsach_ham.map(e=> ({id: e.NHOMHAM_ID, text: e.TEN_NHOM}))"
                      @change="changeHam"
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
          <ejs-treeview  id='treeviewHamChuaGan'
            ref="treeObjHamChuaGan" 
            :fields="tree_ham_chuagan"
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
          <ejs-treeview  id='treeviewHamGan'
              ref="treeObjHamGan" 
              :fields="tree_ham_gan"
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
    name:'PhanQuyenGoiHam',
     props:{
      quyen_id:{
        type:[Number, String],
        default:-1
      }
    },
    computed:{
      tree_ham_chuagan(){
        //let danhsach_tree=[]
        //nodeId:-1 phải khác 0 với root
        let tree_menu= this.cleanTree(this.buildTree({ nodeId:-1, nodeText:'Hàm chưa gán', nodeChild:[], expanded: true}, this.danhsach_chuagan, 'ham_id','ten_ham','muc'))
        //danhsach_tree.push(tree_menu)
        let danhsach_tree= this.searchTree([tree_menu], node =>node.nodeText.toLowerCase().includes(this.value_search_chuagan))
        return { dataSource: danhsach_tree, id: 'nodeId', text: 'nodeText', child: 'nodeChild'}
      },
      tree_ham_gan(){
        //let danhsach_tree=[]
        //nodeId:-1 phải khác 0 với root
        let tree_menu= this.cleanTree(this.buildTree({ nodeId:-1, nodeText:'Hàm đã gán', nodeChild:[], expanded: true}, this.danhsach_gan, 'ham_id','ten_ham','muc'))
        //danhsach_tree.push(tree_menu)
        let danhsach_tree= this.searchTree([tree_menu], node =>node.nodeText.toLowerCase().includes(this.value_search_gan))
        return { dataSource: danhsach_tree, id: 'nodeId', text: 'nodeText', child: 'nodeChild'}
      },
    },
    data(){
      return{
        danhsach_ham:[],
        ham_selected:null,
        dataCheckGan:[],
        dataCheckChuaGan:[],
        danhsach_gan:[],
        danhsach_chuagan:[],
        expandedNodesGan:[-1],
        expandedNodesChuaGan:[-1],

        input_search_chuagan:'',
        value_search_chuagan:'',
        timeOutChuaGan:null,
        input_search_gan:'',
        value_search_gan:'',
        timeOutGan:null
      }
    },
    methods:{
      changeHam(){
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
        var treeObj = document.getElementById('treeviewHamGan').ej2_instances[0];
        this.dataCheckGan=treeObj.checkedNodes
        console.log('checkGan', this.dataCheckGan)
      },
      nodeCheckedChuaGan(args){
        var treeObj = document.getElementById('treeviewHamChuaGan').ej2_instances[0];
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
        let ham_id_arr=[]
        this.dataCheckChuaGan.forEach((item)=>{
          if(item!='-1'){
            ham_id_arr.push(Number(item))
          }
        })
        if(ham_id_arr.length==0){
          return
        }
        this.sp_action_ds_quyen_ham(this.ham_selected, ham_id_arr, this.quyen_id, 1)
      },
      boGanQuyen(){
        if(this.dataCheckGan.length==0){
          return
        }
        //tạo dữ liệu
        //let data=this.getDanhSachQuyenBoGan()
        //this.sp_phanquyen_baocao_delete(data)
        let ham_id_arr=[]
        this.dataCheckGan.forEach((item)=>{
          if(item!='-1'){
            ham_id_arr.push(Number(item))
          }
        })
        if(ham_id_arr.length==0){
          return
        }
        this.sp_action_ds_quyen_ham(this.ham_selected, ham_id_arr, this.quyen_id, 0)
      },
      async danhsach_hienthi_gan_chuagan(quyen_id){
        this.danhsach_gan=[]
        this.danhsach_chuagan=[]
        let result =await this.sp_lay_ds_ham_quyen(this.ham_selected, quyen_id)
        if(result){
          this.danhsach_gan=result.ham?result.ham:[]
          this.danhsach_chuagan=result.ham1?result.ham1:[]
        }
      },
      buildTree(tree,ds,idfield,textfield,levelfield) {
        if(!ds || ds.length<=0) return tree;
        var i = 0;
        var level = 1;
        var lastparentitem = tree;
        for(;i<ds.length;) {
          if(ds[i][levelfield]==level)
          {
            lastparentitem.nodeChild.push({ nodeId:ds[i][idfield], nodeText:ds[i][textfield], nodeChild:[], parent:lastparentitem});
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
      async lay_danhsach_nhom_ham(){
          try{
              this.loading(true)
              let response=await ManagePermissionAPI.lay_danhsach_nhom_ham(this.axios)
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
              this.$toast.error('Đã có lỗi khi lấy danh sách hàm')
            }
            return []
          }
      },
      async sp_lay_ds_ham_quyen(nhomham_id, quyen_id){
        try{
              this.loading(true)
              let response=await ManagePermissionAPI.sp_lay_ds_ham_quyen(this.axios, nhomham_id, quyen_id)
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
                this.$toast.error('Đã có lỗi khi lấy danh sách hàm')
            }
            return null
          }
      },
      async sp_action_ds_quyen_ham(nhom_ham_id, ham_id_arr, quyen_id, kieu){
          try{
              this.loading(true)
              let response = await ManagePermissionAPI.sp_action_ds_quyen_ham(this.axios, nhom_ham_id, ham_id_arr, quyen_id, kieu)
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
                      this.$toast.success('Gán hàm thành công!')
                    }else{
                      this.$toast.success('Bỏ gán hàm thành công!')
                    }
                    if(response.data.data.ham){
                      this.$nextTick(()=>{
                        this.danhsach_gan=response.data.data.ham
                      })
                    }
                    if(response.data.data.ham1){
                      this.$nextTick(()=>{
                        this.danhsach_chuagan=response.data.data.ham1
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
        this.danhsach_ham=[]
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

        this.danhsach_ham=await this.lay_danhsach_nhom_ham()
        if(this.danhsach_ham.length>0){
          this.ham_selected=this.danhsach_ham[0].NHOMHAM_ID
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