<template>
  <div class="box-form">
    <div class="legend-title">Danh sách khu vực</div>
    <div class="input-icon-right">
      <input type="text" v-model="input_search" @keypress.enter="enterSearch($event)" class="form-control" />
      <span class="icon nc-icon-outline ui-1_zoom"></span>
    </div>
    <div class="tree-plus ovauto" style="height: 300px">
      <ejs-treeview
        id="treeview"
        ref="treeObj"
        :fields="field_tree"
        @nodeSelected="tvKhuVuc_FocusedNodeChanged"
        :expandedNodes="expandedNodes"
        :selectedNodes="selectedNodes"/>
    </div>
  </div>
</template>
<script>

export default {
    name:'DanhSachKhuVuc',
    props:{
      ds_khuvuc:{
        type:Array,
        default:()=>[]
      }
    },
    data(){
        return {
            input_search:'',
            selectedNodes:[],
            expandedNodes:[],
            danhsach_khuvuc:this.ds_khuvuc,
            oldKhuVucID:-1,
            oldInputSeach:''
        }
    },
    computed:{
        field_tree(){
            //create data tree
            let danhmuc_khuvuc_tree=this.createTreeKhuVuc(this.danhsach_khuvuc)
            console.log('danhmuc_khuvuc_tree', danhmuc_khuvuc_tree)
            return { dataSource: danhmuc_khuvuc_tree, id: 'khuvuc_id', text: 'ten_kv', parentID: 'khuvuc_cha_id', hasChildren: 'hasChild' }
        }
    },
    methods:{
        enterSearch(event){
           let value=event.target.value.trim()
           if(value.length>0){
               if(this.oldInputSeach!=value){
                    this.oldInputSeach=value
                    let result =this.danhsach_khuvuc.filter(x=>x.ten_kv.toLowerCase().includes(value.toLowerCase()))
                    if(result.length>0){
                        this.oldKhuVucID=result[0].khuvuc_id
                        //selected node focus
                        this.focusNodeKhuVuc(result[0])
                    }else{
                        this.oldInputSeach=''
                        this.oldKhuVucID=-1
                        this.$toast.error('Không có thông tin tìm kiếm!')
                        return
                    }
               }else{
                    let result =this.danhsach_khuvuc.filter(x=>x.ten_kv.toLowerCase().includes(value.toLowerCase()))
                    if(result.length>0){
                        const index=result.findIndex(x=>x.khuvuc_id==this.oldKhuVucID)
                        let indexFocus=index+1
                        if(indexFocus==result.length-1){
                            this.oldKhuVucID=result[0].khuvuc_id
                            this.$toast.error('Đã qua hết các giá trị tìm được')
                        }else{
                            this.oldKhuVucID=result[indexFocus].khuvuc_id
                        }
                        //selected node focus
                        this.focusNodeKhuVuc(result[indexFocus])
                    }else{
                        this.oldInputSeach=''
                        this.oldKhuVucID=-1
                        this.$toast.error('Không có thông tin tìm kiếm!')
                        return
                    }
               }
               

           }else{
                this.oldInputSeach=''
                this.oldKhuVucID=-1
                this.$toast.error('Hãy nhập dữ liệu để tìm kiếm!')
                return
           }
        },
        focusNodeKhuVuc(khuvuc){
            this.selectedNodes=[khuvuc.khuvuc_id]
            //find array parent expandedNodes
            this.expandedNodes=[]
            this.expandedNodes.push(khuvuc.khuvuc_id)
            var khuvuc_id=khuvuc.khuvuc_cha_id
            while(khuvuc_id!=null&&khuvuc_id!=-1){
                const index=this.danhsach_khuvuc.findIndex(x=>x.khuvuc_id==khuvuc_id)
                if(index>-1){
                    this.expandedNodes.push(khuvuc_id)
                    khuvuc_id=this.danhsach_khuvuc[index].khuvuc_cha_id
                }else{
                    khuvuc_id=-1
                }
            }
        },
        tvKhuVuc_FocusedNodeChanged(node){
            let khuvuc=this.danhsach_khuvuc.find(x=>x.khuvuc_id==node.nodeData.id)
            //this.input_search=khuvuc.ten_kv
            this.$emit('focusedNodeChanged', khuvuc)
        },
        createTreeKhuVuc(dsKhuVuc){
            let tree = []
            let khuvucChaArray = dsKhuVuc.map((item) => {
                return item['khuvuc_cha_id']
            })
            let khuvucArray = dsKhuVuc.map(item => (item['khuvuc_id']))
            dsKhuVuc.forEach(item => {
                //check item la parent node
                if(khuvucChaArray.includes(item['khuvuc_id'])){
                    if(!khuvucArray.includes(item['khuvuc_cha_id'])){
                        delete item.khuvuc_cha_id
                    }
                    tree.push( {...item, hasChild:true, expanded: true})
                }else{
                    tree.push(item)
                }
            })
            return tree
        },

    },
    watch:{
      ds_khuvuc(val){
        this.danhsach_khuvuc=val
      }
    }
}
</script>