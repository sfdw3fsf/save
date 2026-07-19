<template>
    <div class="box-form">
        <div class="legend-title">Danh sách đơn vị</div>
        <div class="info-row">
          <div class="value">
              <div class="select-custom" ref="ds_donvi">
                  <select2 :settings="{ dropdownParent: $refs['ds_donvi'] }" ref="cboDonVi"
                      class="select2"
                      v-model="donvi_selected"
                      :options="danhsach_donvi.map(e=> ({id: e.donvi_id, text: e.ten_dv}))"
                      @change="findDonVi"
                      >
                  </select2>
              </div>
          </div>
        </div>
        <div class="tree-plus ovauto" style="height: 800px;">
            <ejs-treeview  id='treeview'  ref="treeObj" 
                :fields="danhmuc_donvi_tree" 
                @nodeSelected="tvDonVi_FocusedNodeChanged" 
                :allowMultiSelection='true'
                :selectedNodes="selectedNodes"
                :expandedNodes="expandedNodes"/>
        </div>
    </div>
</template>
<script>
import API from './API'
export default {
    name:'DanhSachDonVi',
    data(){
        return {
            selectedNodes:[],
            expandedNodes:[],
            danhsach_donvi:[],
            donvi_id:-1,
            donvi_bc:-1,
            muc_id_cha:-1,
            donvi_selected:null
        }
    },
    computed:{
      danhmuc_donvi_tree(){
        let donvi_tree=this.createTreeDonVi(this.danhsach_donvi)
        return { dataSource: donvi_tree, id: 'donvi_id', text: 'ten_dv', parentID: 'donvi_cha_id', hasChildren: 'hasChild' }
      }
    },
    methods:{
        tvDonVi_FocusedNodeChanged(node){
          let donvi_id=Number(node.nodeData.id)
          let donvi=this.danhsach_donvi.find(x=>x.donvi_id==donvi_id)
          this.donvi_selected=donvi_id
          this.$emit('tvDonViChanged', donvi)
        },
        findDonVi(){
          console.log('findDonVi', this.donvi_selected)
          let donvi=this.danhsach_donvi.find(x=>x.donvi_id==this.donvi_selected)
          if(donvi){
            //selected node focus
            this.selectedNodes=[donvi.donvi_id]
            //find array parent expandedNodes
            this.expandedNodes=this.donvi_id==900?[900]:[]
            this.expandedNodes.push(donvi.donvi_id)
            var donvi_id=donvi.donvi_cha_id
            while(donvi_id!=null&&donvi_id!=-1){
                const index=this.danhsach_donvi.findIndex(x=>x.donvi_id==donvi_id)
                if(index>-1){
                    this.expandedNodes.push(donvi_id)
                    donvi_id=this.danhsach_donvi[index].donvi_cha_id
                }else{
                    donvi_id=-1
                }
            }
          }else {
            this.$toast.error('Không có kết quả tìm kiếm!')
            return
          }
        },
        createTreeDonVi(dsDonVi){
            //
            let tree = []
            let donviChaChaArray = dsDonVi.map((item) => {
                return item['donvi_cha_id']
            })
            let donviArray = dsDonVi.map(item => (item['donvi_id']))
            dsDonVi.forEach(item => {
                //check item la parent node
                if(donviChaChaArray.includes(item['donvi_id'])){
                    //check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id
                    if(!donviArray.includes(item['donvi_cha_id'])){
                        delete item.donvi_cha_id
                    }
                    tree.push( {...item, hasChild:true, expanded: false})
                }else{
                    tree.push(item)
                }
            })
            return tree
        },
        async sp_load_treeview(){
            try{
                this.loading(true)
                let response=await API.sp_load_treeview(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return null
                }
            }catch(e){
              this.loading(false)
              return null
            }
        },
        async initDulieu(){
          //treeview đơn vị, hàm gom (đã xử lý user_map) tài khoản admin load chậm nhiều record
          let result=await this.sp_load_treeview()
          if(!result){
            return
          }
          this.donvi_id=result.donvi_bc
          this.donvi_bc=result.donvi_bc
          this.muc_id_cha=result.muc_id_cha
          //list node child
          let childs=result.ls

          let nodeRoot={
            ten_dv: result.tendv_cha,
            unitlevel: childs.length>0?childs[0].unitlevel-1:0,
            donvi_id: this.donvi_id,
            donvi_cha_id: null
          }
          this.danhsach_donvi=[nodeRoot].concat(childs)

          setTimeout(()=>{
            if(this.danhsach_donvi.length>0){
              this.expandedNodes=this.donvi_id==900?[900]:[]
              //focus node first
              this.expandedNodes.push(this.danhsach_donvi[0].donvi_id)
              this.selectedNodes=[this.danhsach_donvi[0].donvi_id]
            }else{
              this.expandedNodes=[]
            }
          },500)
        },

    }
}
</script>