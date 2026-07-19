<template>
  <div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w90">Bảng từ điển</div>
          <div class="value">
              <div class="select-custom" ref="ds_bang">
                  <select2 :settings="{ dropdownParent: $refs['ds_bang'] }" ref="cboBang"
                      v-model="bang_selected" class="select2"
                      :options="danhsach_bang.map(e=> ({id: e.TENBANG, text: e.TENBANG}))"
                      @change="changeBang"
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
          <ejs-treeview  id='treeviewBangChuaGan'
            ref="treeObjBangChuaGan" 
            :fields="tree_giatri_chuagan"
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
            <ejs-treeview  id='treeviewBangGan'
              ref="treeObjBangGan" 
              :fields="tree_giatri_gan"
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
    name:'PhanQuyenDuLieu',
    props:{
      quyen_id:{
        type:[Number, String],
        default:-1
      }
    },
    computed:{
      tree_giatri_chuagan(){
        console.log('bang_object_selected', this.bang_object_selected)
        if(this.bang_object_selected==null||this.bang_object_selected==undefined){
          return { dataSource: [{ nodeId:-1, nodeText:'Giá trị đã gán', nodeChild:[]}], id: 'nodeId', text: 'nodeText', child: 'nodeChild'}
        }
        //hard do model không thống nhất, khóa chỉnh bảng NHANVIEN_TC(NHANVIEN_ID, TEN_NV) api trả ds (id, ten) không có muc
        //lúc thì muc, loai,  unitlevel code cũ lấy vị trí đầu json object thì không theo thứ tự
        var tree_menu={}
        if(this.bang_object_selected.TENBANG=='NHANVIEN_TC'){
          tree_menu= this.cleanTree(this.buildTree({ nodeId:-1, nodeText:'Giá trị chưa gán', nodeChild:[], expanded: true}, this.danhsach_chuagan, 'id', 'ten','loai'))
        }else{
          var nameLevel='muc'
          if(this.danhsach_chuagan.length>0){
            if(this.danhsach_chuagan[0].muc){
              nameLevel='muc'
            }else if(this.danhsach_chuagan[0].unitlevel){
              nameLevel='unitlevel'
            }
          }
          tree_menu= this.cleanTree(this.buildTree({ nodeId:-1, nodeText:'Giá trị chưa gán', nodeChild:[], expanded: true}, this.danhsach_chuagan, this.bang_object_selected.KHOACHINH.toLowerCase(), this.bang_object_selected.TRUONG_HT.toLowerCase(),nameLevel))
        }

        //let danhsach_tree=[]
        //nodeId:-1 phải khác 0 với root

        //danhsach_tree.push(tree_menu)
        let danhsach_tree= this.searchTree([tree_menu], node =>node.nodeText.toLowerCase().includes(this.value_search_chuagan))
        return { dataSource: danhsach_tree, id: 'nodeId', text: 'nodeText', child: 'nodeChild'}
      },
      tree_giatri_gan(){
        if(this.bang_object_selected==null||this.bang_object_selected==undefined){
          return { dataSource: [{ nodeId:-1, nodeText:'Giá trị đã gán', nodeChild:[]}], id: 'nodeId', text: 'nodeText', child: 'nodeChild'}
        }
        //hard
        var tree_menu={}
        if(this.bang_object_selected.TENBANG=='NHANVIEN_TC'){
          tree_menu= this.cleanTree(this.buildTree({ nodeId:-1, nodeText:'Giá trị đã gán', nodeChild:[], expanded: true}, this.danhsach_gan, 'id', 'ten','loai'))
        }else{
          var nameLevel='muc'
          if(this.danhsach_gan.length>0){
            if(this.danhsach_gan[0].muc){
              nameLevel='muc'
            }else if(this.danhsach_gan[0].unitlevel){
              nameLevel='unitlevel'
            }
          }
          tree_menu= this.cleanTree(this.buildTree({ nodeId:-1, nodeText:'Giá trị đã gán', nodeChild:[], expanded: true}, this.danhsach_gan, this.bang_object_selected.KHOACHINH.toLowerCase(), this.bang_object_selected.TRUONG_HT.toLowerCase(), nameLevel))
        }

        
        //let danhsach_tree=[]
        //nodeId:-1 phải khác 0 với root
       
        //danhsach_tree.push(tree_menu)
        let danhsach_tree= this.searchTree([tree_menu], node =>node.nodeText.toLowerCase().includes(this.value_search_gan))
        return { dataSource: danhsach_tree, id: 'nodeId', text: 'nodeText', child: 'nodeChild'}
      },
    },
    data(){
      return{
        danhsach_bang:[],
        bang_selected:null,
        bang_object_selected:null,
        isFirstChangeBang:false,
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
        timeOutGan:null,
        pub_data:[]
      }
    },
    methods:{
      changeBang(){
        this.dataCheckGan=[]
        this.dataCheckChuaGan=[]
        this.input_search_chuagan=''
        this.value_search_chuagan=''
        this.timeOutChuaGan=null
        this.input_search_gan=''
        this.value_search_gan=''
        this.timeOutGan=null
        this.bang_object_selected=this.danhsach_bang.find(x=>x.TENBANG==this.bang_selected)
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
        var treeObj = document.getElementById('treeviewBangGan').ej2_instances[0];
        
        this.dataCheckGan=treeObj.checkedNodes
        // this.dataCheckGan=args.data.filter(x=>x.id!='-1').map(x=>x.id)
        console.log('checkGan', this.dataCheckGan)
      },
      nodeCheckedChuaGan(args){
        console.log('nodeCheckedChuaGan args', args)
        let dataMixed=args.data.filter(x=>x.id!='-1'&&x.isChecked=='mixed').map(x=>x.id)
        let dataChekeds=args.data.filter(x=>x.id!='-1'&&x.isChecked=='true').map(x=>x.id)
        this.pub_data=this.pub_data.concat(dataChekeds.map(x=>{
          return {
            nodeId:x,
            mixed:dataMixed
          }
        }))
        console.log('pub_data', this.pub_data)
        var treeObj = document.getElementById('treeviewBangChuaGan').ej2_instances[0];
        console.log('nodeCheckedChuaGan', treeObj)
        this.dataCheckChuaGan=treeObj.checkedNodes
        //this.dataCheckChuaGan=treeObj.checkedNodes.concat(dataMixed)
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
        var arr_id_chon=[]
        if(this.bang_object_selected.TENBANG!='NHANVIEN_TC'){
          arr_id_chon=[].concat(this.danhsach_gan.map(x=>x[this.bang_object_selected.KHOACHINH.toLowerCase()]?x[this.bang_object_selected.KHOACHINH.toLowerCase()].toString():'0'))
        }else{
          arr_id_chon=[].concat(this.danhsach_gan.map(x=>x['id']?x['id'].toString():'0'))
        }

        //mixed parent
        var arrayMixed=[]
        for(let i=0;i<this.dataCheckChuaGan.length;i++){
          let mixeds=this.pub_data.find(x=>x.nodeId==this.dataCheckChuaGan[i])
          if(mixeds){
            arrayMixed=arrayMixed.concat(mixeds.mixed)
          }
        }
        arrayMixed=arrayMixed.filter(function (value, index, array) { 
          return array.indexOf(value) === index
        })
        console.log('arrayMixed', arrayMixed)

        arr_id_chon=arr_id_chon.concat(this.dataCheckChuaGan.filter(x=>x!='-1')).concat(arrayMixed)
        //remove duplicate
        arr_id_chon=arr_id_chon.filter(function (value, index, array) { 
          return array.indexOf(value) === index
        })

        //map nodeId=-2-->0 thực
        arr_id_chon=arr_id_chon.map(x=>x=='-2'?'0':x)
        

        

        // this.dataCheckChuaGan.forEach((item)=>{
        //   if(item!='-1'){
        //     arr_id_chon.push(Number(item))
        //   }
        // })
        if(arr_id_chon.length==0){
          return
        }
        this.sp_action_ds_quyen_bang(this.bang_selected, arr_id_chon.toString(), this.quyen_id, 1)
      },
      boGanQuyen(){
        if(this.dataCheckGan.length==0){
          return
        }
        //tạo dữ liệu
        var arr_id_chon=[]
        if(this.bang_object_selected.TENBANG!='NHANVIEN_TC'){
          arr_id_chon=[].concat(this.danhsach_gan.map(x=>x[this.bang_object_selected.KHOACHINH.toLowerCase()]?x[this.bang_object_selected.KHOACHINH.toLowerCase()].toString():'0'))
        }else{
          arr_id_chon=[].concat(this.danhsach_gan.map(x=>x['id']?x['id'].toString():'0'))
        }
       
        for(let i=0;i<this.dataCheckGan.length;i++){
          let item=this.dataCheckGan[i]
          if(item!='-1'){
            const index=arr_id_chon.findIndex(x=>x==item)
            if(index>-1){
              arr_id_chon.splice(index, 1)
            }
          }
        }

        //map nodeId=-2-->0 thực
        arr_id_chon=arr_id_chon.map(x=>x=='-2'?'0':x)

        this.sp_action_ds_quyen_bang(this.bang_selected, arr_id_chon.toString(), this.quyen_id, 0)
      },
      async danhsach_hienthi_gan_chuagan(quyen_id){
        //danh sách gán/chưa gán
        this.danhsach_gan=[]
        this.danhsach_chuagan=[]
        if(this.bang_selected==null){
          return
        }
        
        let result=await this.sp_lay_ds_bang_quyen(this.bang_selected, quyen_id)
        if(result){
          if(result.bang&&result.bang.length>0){
            //check phần tử đầu
            // if(result.bang[0].muc&&result.bang[0].muc!='Khong co du lieu'){
            //   this.danhsach_gan=result.bang
            // }
            //map id nếu nodeId=0 lỗi không expanded dc==>map tạm về -2
            this.danhsach_gan=result.bang.map(x=>{
              if(x[this.bang_object_selected.KHOACHINH.toLowerCase()]==0){
                x[this.bang_object_selected.KHOACHINH.toLowerCase()]=-2
              }
              return x
            })
          }
          if(result.bang1&&result.bang1.length>0){
            //map id nếu nodeId=0 lỗi không expanded dc==>map tạm về -2
            this.danhsach_chuagan=result.bang1.map(x=>{
              if(x[this.bang_object_selected.KHOACHINH.toLowerCase()]==0){
                x[this.bang_object_selected.KHOACHINH.toLowerCase()]=-2
              }
              return x
            })
            // if(result.bang1[0].muc&&result.bang1[0].muc!='Khong co du lieu'){
            //   this.danhsach_chuagan=result.bang1
            // }
          }
        }
        console.log('danhsach_hienthi_gan_chuagan', result)

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
      async lay_danhsach_bang(){
          try{
              this.loading(true)
              let response=await ManagePermissionAPI.lay_danhsach_bang(this.axios)
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
            } else if(e.response&&e.response.data&&e.response.data.message){
                this.$toast.error(e.response.data.message)
            }else{
                this.$toast.error('Đã có lỗi khi lấy danh sách bảng')
            }
            return []
          }
      },
      async sp_lay_ds_bang_quyen(tenbang, quyen_id){
          try{
              this.loading(true)
              let response=await ManagePermissionAPI.sp_lay_ds_bang_quyen(this.axios, tenbang, quyen_id)
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
                this.$toast.error('Đã có lỗi khi lấy danh sách')
            }
            return null
          }
      },
      async sp_action_ds_quyen_bang(tenbang, string_arr_id_chon, quyen_id, kieu){
          try{
              this.loading(true)
              let response = await ManagePermissionAPI.sp_action_ds_quyen_bang(this.axios, tenbang, string_arr_id_chon, quyen_id, kieu)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.danhsach_gan=[]
                    this.danhsach_chuagan=[]
                    this.dataCheckGan=[]
                    this.dataCheckChuaGan=[]
                    this.pub_data=[]

                    this.input_search_chuagan=''
                    this.value_search_chuagan=''
                    this.timeOutChuaGan=null
                    this.input_search_gan=''
                    this.value_search_gan=''
                    this.timeOutGan=null

                    if(kieu==1){
                      this.$toast.success('Gán giá trị bảng thành công!')
                    }else{
                      this.$toast.success('Bỏ gán giá trị bảng thành công!')
                    }
                    //sài tạm cột columns
                    if(response.data.data.bang){
                      this.$nextTick(()=>{
                          this.danhsach_gan=response.data.data.bang.map(x=>{
                            if(x[this.bang_object_selected.KHOACHINH.toLowerCase()]==0){
                              x[this.bang_object_selected.KHOACHINH.toLowerCase()]=-2
                            }
                            return x
                          })
                      })
                    }else{
                      this.danhsach_gan=[]
                    }
                    if(response.data.data.bang1){
                      this.$nextTick(()=>{
                          this.danhsach_chuagan=response.data.data.bang1.map(x=>{
                            if(x[this.bang_object_selected.KHOACHINH.toLowerCase()]==0){
                              x[this.bang_object_selected.KHOACHINH.toLowerCase()]=-2
                            }
                            return x
                          })
                      })
                    }else{
                      this.danhsach_chuagan=[]
                    }
              }else{
                  if(response && response.data &&response.data.message){
                      this.$toast.error(response.data.message)
                  }else {
                    if(kieu==1){
                      this.$toast.error('Đã xảy ra lỗi, gán giá trị không thành công')
                    }else{
                      this.$toast.error('Đã xảy ra lỗi, bỏ gán giá trị không thành công')
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
                    this.$toast.error('Đã xảy ra lỗi, gán giá trị không thành công')
                  }else{
                    this.$toast.error('Đã xảy ra lỗi, bỏ gán giá trị không thành công')
                  }
              }
          }
      },
      async initData(){
        this.danhsach_bang=[]
        this.danhsach_gan=[]
        this.danhsach_chuagan=[]
        this.dataCheckGan=[]
        this.dataCheckChuaGan=[]
        this.pub_data=[]
        this.bang_object_selected=null

        this.input_search_chuagan=''
        this.value_search_chuagan=''
        this.timeOutChuaGan=null
        this.input_search_gan=''
        this.value_search_gan=''
        this.timeOutGan=null
        
        this.danhsach_bang=await this.lay_danhsach_bang()
        if(this.danhsach_bang.length>0){
          this.bang_object_selected=this.danhsach_bang[0]
          this.bang_selected=this.danhsach_bang[0].TENBANG
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