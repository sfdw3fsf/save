<template>
  <div class="box-form">
    <div class="legend-title">Danh sách đơn vị</div>
    <div class="info-row">
      <div class="value padl0">
        <div class="input-more-button -right">
          <button class="btn" @click="sortDonVi" >
            <span class="one-sort f20"></span>
          </button>
          <input type="text" @keypress.enter="onEnterSearch($event)" class="form-control" />
        </div>
      </div>
    </div>

    <div class="tree-plus ovauto" style="height: 970px;">
        <ejs-treeview  id='treeview'  ref="treeObj" 
          @nodeClicked="nodeClicked"
          :fields="danhmuc_donvi_tree" 
          @nodeSelected="tvDonVi_FocusedNodeChanged" 
          :allowMultiSelection='true'
          :selectedNodes="selectedNodes"
          :expandedNodes="expandedNodes"/>
    </div>

  </div>
</template>
<script>
import UserAPI from './UserAPI'
export default {
    name:'DanhSachDonVi',
    computed:{
      danhmuc_donvi_tree(){
        let danhmuc_donvi=[]
        let level=this.findLevel()
        //fix đơn vị 900 -->admin tập đoàn
        if(this.donvi_id==900){
          danhmuc_donvi.push({
            unitlevel: level-1,
            donvi_id:900,
            ten_dv: "Tập đoàn",
            donvi_cha_id: null
          })
        }
        //console.log('findLevel', level)
        this.danhsach_donvi.forEach((item)=>{
            if(this.donvi_id!=900&&item.unitlevel==level){
                item.donvi_cha_id=null
            }
            danhmuc_donvi.push(item)
        })
        //console.log('danhmuc_donvi', danhmuc_donvi)
        let donvi_tree=this.createTreeDonVi(danhmuc_donvi)
        return { dataSource: donvi_tree, id: 'donvi_id', text: 'ten_dv', parentID: 'donvi_cha_id', hasChildren: 'hasChild' }
      }
    },
    data(){
        return {
            selectedNodes:[],
            expandedNodes:[],
            danhsach_donvi:[],
            sort_direction:'none',
            donvi_id:-1
        }
    },
    methods:{
        nodeClicked(args){
            //console.log('nodeClicked',args.node.dataset.uid)
            //this.vattu_cha_selected= Number(args.node.dataset.uid)
        },
        tvDonVi_FocusedNodeChanged(node){
          let donvi_id=Number(node.nodeData.id)
          //Tập đoàn
          if(donvi_id==900){
            this.$emit('tvDonViChanged', {
              unitlevel: 0,
              donvi_id:900,
              ten_dv: "Tập đoàn",
              donvi_cha_id: null
            })
            return
          }
          let donvi=this.danhsach_donvi.find(x=>x.donvi_id==donvi_id)
          this.$emit('tvDonViChanged', donvi)
        },
        onEnterSearch(event){
           let value=event.target.value.trim()
           if(value.length>0){
               let result =this.danhsach_donvi.filter(x=>x.ten_dv.toLowerCase().includes(value.toLowerCase()))
               if(result.length>0){
                    let donvi=result[0]
                
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
               }else{
                    this.$toast.error('Không có kết quả tìm kiếm!')
                    return
               }

           }else{
                this.$toast.error('Hãy nhập dữ liệu để tìm kiếm!')
                return
           }
        },
        findFocusDonViId(donvi_id){
          console.log('findFocusDonViId', donvi_id)
          if(donvi_id==-1){
            this.$toast.error('Không tìm thấy thông tin về mã người dùng vừa nhập!')
            return
          }
          //
          console.log('findFocusDonViId', donvi_id)
          const index =this.danhsach_donvi.findIndex(x=>x.donvi_id==donvi_id)
          if(index>-1){
            let donvi=this.danhsach_donvi[index]
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
          }else{
            this.$toast.error('Không tìm thấy thông tin về mã người dùng vừa nhập!')
          }
        },
        sortDonVi(){
          if(this.sort_direction=='none'){
            this.sort_direction='asc'
            //sắp xếp tăng dần
            this.danhsach_donvi.sort((a, b)=>{
              return a.ten_dv.toLowerCase().localeCompare(b.ten_dv.toLowerCase())
            })

          }else if(this.sort_direction=='desc'){
            this.sort_direction='asc'
            //sắp xếp tăng dần
            this.danhsach_donvi.sort((a, b)=>{
              return a.ten_dv.toLowerCase().localeCompare(b.ten_dv.toLowerCase())
            })

          }else {
            this.sort_direction='desc'
            //sắp xếp giảm dần
            this.danhsach_donvi.sort((a, b)=>{
              return b.ten_dv.toLowerCase().localeCompare(a.ten_dv.toLowerCase())
            })
          }

          setTimeout(()=>{
            this.expandedNodes=[900]
            this.selectedNodes=[]
           // this.$emit('clearThongTin')
            let donviND=Number(this.$root.token.getDonViID())
            //admin id = 900
            if(donviND==900){
              this.$emit('clearThongTin')
              return
            }
            if(this.danhsach_donvi.length>0){
              this.expandedNodes=this.donvi_id==900?[900]:[]

              //
              let level=this.findLevel()
              console.log('findLevel', level)
              if(level==-1){
                return
              }
              //focus node first
              const index=this.danhsach_donvi.findIndex(x=>x.unitlevel==level)
              if(index>-1){
                this.expandedNodes.push(this.danhsach_donvi[index].donvi_id)
                this.selectedNodes=[this.danhsach_donvi[index].donvi_id]
              }    
            }else{
              this.expandedNodes=this.donvi_id==900?[900]:[]
            }


          },500)
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
            //console.log('tree', tree)
            return tree
        },
        async initDulieu(){
          var user_map=await this.fn_map_id({
            id_neo: "user_map",
            table: "admin.nguoidung",
            dk:"where nguoidung_id = "+this.$root.token.getNguoiDungID()
          })
          if(user_map=='-1'){
            user_map=''
          }
          if(user_map!=''){
            this.donvi_id=await this.sp_lay_donvi_id_theo_ma_nd(user_map)
          }else{
            this.donvi_id=this.$root.token.getDonViID()
          }
          this.danhsach_donvi=await this.lay_danhsach_donvi_treeview(this.donvi_id)
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
        findLevel(){
          let donviND=Number(this.donvi_id)
          const index=this.danhsach_donvi.findIndex(x=>x.donvi_id==this.donvi_id)
          if(index>-1){
            return this.danhsach_donvi[index].unitlevel
          }else{
            //Đơn vị 900 -> admin
            // {
            //   "ten_dv": "Viễn thông Hải Phòng",
            //   "unitlevel": 1,
            //   "donvi_id": 0,
            //   "donvi_cha_id": 900
            // }
            console.log('donviND', donviND)
            const indexCha=this.danhsach_donvi.findIndex(x=>x.donvi_cha_id==donviND)
            console.log('indexCha', indexCha)
            if(indexCha>-1){
              return this.danhsach_donvi[indexCha].unitlevel
            }
          }
          // if(this.danhsach_donvi.length>0){
          //   return this.danhsach_donvi[0].unitlevel
          // }
          return -1
        },
        //CommonUtilities.LoadTreeview(tvDonvi, objGiaoSIM.get_TreeView(donviIDCHA), objGiaoSIM.get_RootName(donviIDCHA), donviIDCHA);
        async lay_danhsach_donvi_treeview(donvicha_id){
            try{
                this.loading(true)
                let response=await UserAPI.lay_danhsach_donvi_treeview(this.axios, donvicha_id)
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
        async fn_map_id(data){
            try{
                this.loading(true)
                let response=await UserAPI.fn_map_id(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return '-1'
                }
            }catch(e){
              this.loading(false)
              return '-1'
            }
        },
        //donviIDCHA = objGiaoSIM.get_donviID(user_map);
        async sp_lay_donvi_id_theo_ma_nd(ma_nd){
            try{
                this.loading(true)
                let response=await UserAPI.sp_lay_donvi_id_theo_ma_nd(this.axios, ma_nd)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data!=undefined&&response.data.data!=null) {
                    return response.data.data
                }else{
                    return '-1'
                }
            }catch(e){
              this.loading(false)
              return '-1'
            }
        },
        //
        async sp_lay_donvi_theo_id(donvi_id){
            try{
                this.loading(true)
                let response=await UserAPI.sp_lay_donvi_theo_id(this.axios, donvi_id)
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
      
    }
}
</script>