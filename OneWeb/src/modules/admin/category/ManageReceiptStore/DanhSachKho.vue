<template>
  <div class="col-sm-3 col-12">
      <div class="box-form">
        <div class="legend-title">Danh sách kho</div>
        <div class="info-row">
            <div class="value padl0">
                <div class="input-icon-right">
                <input type="text" v-model="input_search" @keypress.enter="enterSearch($event)" class="form-control" />
                <span class="icon one-search"></span>
                </div>
            </div>
        </div>
        <div class="tree-plus ovauto" style="height: 780px">
            <ejs-treeview
                id="treeview"
                ref="treeObj"
                :fields="field_tree"
                @nodeSelected="tvVatTu_FocusedNodeChanged"
                :selectedNodes="selectedNodes"
                :expandedNodes="expandedNodes"
            />
        </div>

      </div>
  </div>
</template>
<script>
import ManageReceiptStoreAPI from './ManageReceiptStoreAPI'
export default {
    name:'DanhSachKho',
    data(){
        return {
            selectedNodes:[],
            expandedNodes:[],
            danhsach_kho_chuagan:[],
            input_search:''
        }
    },
    methods:{
        tvVatTu_FocusedNodeChanged(node){
            // let vattu_id=Number(node.nodeData.id)
            let kho_id=Number(node.nodeData.id)
            let kho=this.danhsach_kho_chuagan.find(x=>x.kho_id==kho_id)
            this.input_search=kho.ten_kho

            this.$emit('focusedNodeChanged', kho)
        },
        createTreeKho(dsKho){
            //
            let tree = []
            let khoChaArray = dsKho.map((item) => {
                return item['kho_cha_id']
            })
            let khoArray = dsKho.map(item => (item['kho_id']))
            dsKho.forEach(item => {
                //check item la parent node
                if(khoChaArray.includes(item['kho_id'])){
                    if(!khoArray.includes(item['kho_cha_id'])){
                        delete item.kho_cha_id
                    }
                    tree.push( {...item, hasChild:true, expanded: false})
                }else{
                    tree.push(item)
                }
            })
            return tree
        },
        async sp_ds_kho_chuagan(){
            try{
                this.loading(true)
                this.danhsach_kho_chuagan=[]
                let response=await ManageReceiptStoreAPI.sp_ds_kho_chuagan(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_kho_chuagan=response.data.data
                   
                }else{
                    this.danhsach_kho_chuagan=[]
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã có lỗi khi lấy danh sách kho')
                }
            }finally{
                this.loading(false)
            }
        },
        async initDanhSachKho(){
            this.input_search=''
            await this.sp_ds_kho_chuagan()
        },
        async deleteKho(p_kho_id){
            await this.initDanhSachKho()

            let result =this.danhsach_kho_chuagan.filter(x=>x.kho_id==p_kho_id)
            if(result.length>0){
                let kho=result[0]
                
                //find array parent expandedNodes
                this.expandedNodes=[]
                this.expandedNodes.push(kho.kho_id)
                var kho_id=kho.kho_cha_id
                while(kho_id!=null&&kho_id!=-1){
                    const index=this.danhsach_kho_chuagan.findIndex(x=>x.kho_id==kho_id)
                    if(index>-1){
                        this.expandedNodes.push(kho_id)
                        kho_id=this.danhsach_kho_chuagan[index].kho_cha_id
                    }else{
                        kho_id=-1
                    }
                }
            }
            //selected node focus
            if(this.expandedNodes.length>0){
                this.expandedNodes.splice(0,1)
            }
            if(this.expandedNodes.length>0){
                this.selectedNode=[this.expandedNodes[0]]
            }  
        },
        async findFocusTreeViewAfterInit(p_kho_id){
            await this.initDanhSachKho()
            let result =this.danhsach_kho_chuagan.filter(x=>x.kho_id==p_kho_id)
            if(result.length>0){
                let kho=result[0]
                
                //selected node focus
                this.selectedNodes=[kho.kho_id]

                //find array parent expandedNodes
                this.expandedNodes=[]
                this.expandedNodes.push(kho.kho_id)
                var kho_id=kho.kho_cha_id
                while(kho_id!=null&&kho_id!=-1){
                    const index=this.danhsach_kho_chuagan.findIndex(x=>x.kho_id==kho_id)
                    if(index>-1){
                        this.expandedNodes.push(kho_id)
                        kho_id=this.danhsach_kho_chuagan[index].kho_cha_id
                    }else{
                        kho_id=-1
                    }
                }
            }
        },

        checkMaKhoTonTai(ma_kho){
            const index=this.danhsach_kho_chuagan.findIndex(x=>x.ma_kho.trim()==ma_kho.trim())
            if(index>-1){
                return true
            }
            return false
        },
        enterSearch(event){
           let value=event.target.value.trim()
           if(value.length>0){
               let result =this.danhsach_kho_chuagan.filter(x=>x.ten_kho.toLowerCase().includes(value.toLowerCase()))
               if(result.length>0){
                    let kho=result[0]
                
                    //selected node focus
                    this.selectedNodes=[kho.kho_id]

                    //find array parent expandedNodes
                    this.expandedNodes=[]
                    this.expandedNodes.push(kho.kho_id)
                    var kho_id=kho.kho_cha_id
                    while(kho_id!=null&&kho_id!=-1){
                        const index=this.danhsach_kho_chuagan.findIndex(x=>x.kho_id==kho_id)
                        if(index>-1){
                            this.expandedNodes.push(kho_id)
                            kho_id=this.danhsach_kho_chuagan[index].kho_cha_id
                        }else{
                            kho_id=-1
                        }
                    }
               }else{
                    this.$toast.error('Không có kết quả tìm kiếm!')
                    return
               }

           }else{
                this.$toast.error('Hãy nhập dữ liệu để tìm kiếm!')
                return
           }
        }
    },
    computed:{
        field_tree(){
            //create data tree
            let danhmuc_kho_tree=this.createTreeKho(this.danhsach_kho_chuagan)
            return { dataSource: danhmuc_kho_tree, id: 'kho_id', text: 'ten_kho', parentID: 'kho_cha_id', hasChildren: 'hasChild' }
        }
    }
}
</script>