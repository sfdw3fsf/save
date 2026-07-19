<template>
  <div class="box-form">
      <div class="legend-title">
        <div class="pull-left">Danh mục vật tư</div>
        <div class="pull-right">
          <div class="list-checks normal red">
            <div class="item">
              <div class="check-action">
                <input type="radio" name="vattu" value="1" checked @change="changeRadioVattu($event)" class="check" />
                <span class="name">Vật tư</span>
              </div>
            </div>
            <div class="item">
              <div class="check-action">
                <input type="radio"  name="vattu" value="2"  @change="changeRadioVattu($event)" class="check" />
                <span class="name">CCDC</span>
              </div>
            </div>
          </div>
        </div>
        <div class="clearfix"></div>
      </div>
      <div class="info-row">
        <div class="value padl0">
            <div class="select-custom" ref="ds_vattu">
                <select2 :settings="{ dropdownParent: $refs['ds_vattu'] }" ref="cboVatTu"
                    v-model="vattu_cha_selected" class="select2"
                    :options="dsVatTu"
                    @change="changeDroplistVatTu"
                >
                </select2>
            </div>
        </div>
      </div>
      <div class="info-row" v-click-outside="closeTieuChiTimKiem">
        <div class="value padl0 dropdown" :class="showDropdown">
          <div class="select-custom" @click="toggleDropdown">
            <input type="text" class="form-control" placeholder="Tiêu chỉ tìm kiếm bổ sung"/>
          </div>
          <div class="dropdown-menu fullw pad10" :class="showDropdown">
            <div class="title-bg-main">
              <span class="title">Điều kiện lọc</span>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w90">
                    <div class="check-action">
                      <input type="checkbox"  @change="check1Change" v-model="timkiembosung.check1" class="check" />
                      <span class="name">Lĩnh vực</span>
                    </div>
                  </div>
                  <div class="value">
                    <div class="select-custom" ref="ds_linhvuc">
                        <select2 :settings="{ dropdownParent: $refs['ds_linhvuc'] }"
                            v-model="timkiembosung.linhvuc_selected" class="select2"
                            :options="timkiembosung.linhvuc.map(e=> ({id: e.LINHVUC_ID, text: e.LINHVUC}))"
                            :disabled="!timkiembosung.check1"
                            @change="changeLinhVuc"
                        >
                        </select2>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w90">Hệ thống</div>
                  <div class="value">
                    <div class="select-custom" ref="ds_hethong">
                        <select2 :settings="{ dropdownParent: $refs['ds_hethong'] }"
                            v-model="timkiembosung.hethong_selected" class="select2"
                            :options="timkiembosung.hethong.map(e=> ({id: e.hethong_id, text: e.ten_ht}))"
                            :disabled="!timkiembosung.check1"
                            @change="changeHeThong"
                        >
                        </select2>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w90">
                    <div class="check-action">
                      <input type="checkbox" @change="check2Change" v-model="timkiembosung.check2" class="check" />
                      <span class="name">Hãng SX</span>
                    </div>
                  </div>
                  <div class="value">
                    <div class="select-custom" ref="ds_hangsx">
                        <select2 :settings="{ dropdownParent: $refs['ds_hangsx'] }"
                            v-model="timkiembosung.hangsx_selected" class="select2"
                            :options="timkiembosung.hangsx.map(e=> ({id: e.HANGSX_ID, text: e.TEN_HANGSX}))"
                            :disabled="!timkiembosung.check2"
                            @change="changeHangSX"
                        >
                        </select2>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w90">Dòng thiết bị</div>
                  <div class="value">
                    <div class="select-custom" ref="ds_dongtb">
                        <select2 :settings="{ dropdownParent: $refs['ds_dongtb'] }"
                            v-model="timkiembosung.dongthietbi_seleted" class="select2"
                            :options="timkiembosung.dongthietbi.map(e=> ({id: e.DONGTBI_ID, text: e.TEN_TBI}))"
                            :disabled="!timkiembosung.check2"
                        >
                        </select2>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="group-buttons tright">
              <button class="btn btn-second" @click="clickSearch" style="background-color: #fff; border-color: #0176FF;color: #0176FF;font-weight: 600;">
                <span class="one-search f20 inline vcenter"></span> Tìm kiếm
              </button>
            </div>
          </div>
        </div>
      </div>

      <div class="form-control h-auto text">
        <div class="tree-plus ovauto" style="height: 900px;">
            <ejs-treeview  id='treeview'  ref="treeObj" 
              @nodeClicked="nodeClicked" :fields='danhmuc_vattu_tree' 
              @nodeSelected="tvVatTu_FocusedNodeChanged" 
              :allowMultiSelection='true'
              :selectedNodes="selectedNodes"
              :expandedNodes="expandedNodes"/>
        </div>
      </div>
  </div>
</template>
<script>
import { DataManager,Query,ODataV4Adaptor,Predicate } from "@syncfusion/ej2-data"
import SuppliesAPI from './SuppliesAPI'
import directive from './VClickOutside/v-click-outside'
import { columnSelectionComplete } from '@syncfusion/ej2-grids'
export default {
    name:'DanhMucVatTu',
    props:{
      danhsach_vattu_cha:{
        type:Array,
        default:()=>[]
      },
      danhsach_danhmuc_vattu:{
        type:Array,
        default:()=>[]
      }
    },
    components:{

    },
    data(){
        return{
            showDropdown: '',
            timkiembosung:{
              check1:false,
              check2:false,
              linhvuc_selected:null,
              linhvuc:[],
              hethong_selected:null,
              hethong:[],
              hangsx_selected:null,
              hangsx:[],
              dongthietbi:[],
              dongthietbi_seleted:null
            },
            radioVattu:1,
            vattu_cha_selected:null,
            ds_danhmuc_vattu:this.danhsach_danhmuc_vattu,
            selectedNodes:[],
            expandedNodes:[-1],
            isChangedEventDrop:true,
            isExpandedFocus:false,
            //fields:'',

            identify: Date.now() + '-' + Math.floor(Math.random() * 1000) + '-' + Math.floor(Math.random() * 1000),
        }
    },
    methods:{
        toggleDropdown () {
            this.showDropdown = !this.showDropdown ? 'show' : ''
        },
        clickSearch(){
          this.showDropdown = !this.showDropdown ? 'show' : ''
          this.$emit('clickFilter')
        },
        changeRadioVattu(event){
          this.radioVattu=event.target.value
          this.$emit('changeRadioDanhMuc',this.radioVattu)
          //console.log('changeRadioVattu', event.target.value)
        },
        onFilteringVatTuCha: function(e) {
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text !== ''){
                predicate  = new Predicate('ten_vt', 'contains', e.text, true);
                //predicate = predicate.or('TEN_KHO', 'contains', e.text);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.danhsach_vattu_cha, query);
        },
        async changeLinhVuc(){
          if(this.timkiembosung.linhvuc_selected!=null){
            this.timkiembosung.hethong_selected=null
            this.timkiembosung.hethong=await this.lay_tt_hethong_theo_linhvuc(this.timkiembosung.linhvuc_selected)
            if(this.timkiembosung.hethong.length>0){
              this.timkiembosung.hethong_selected=this.timkiembosung.hethong[0].hethong_id
              await this.changeHeThong()
            }
          }
        },
        async check1Change(){
          if(this.timkiembosung.hangsx_selected!=null){
              await this.NAP_DS_DONG_TBI()
          }
        },
        async check2Change(){
          if(this.timkiembosung.check2){
            if(this.timkiembosung.hangsx_selected!=null){
              await this.NAP_DS_DONG_TBI()
            }
          }
        },
        async changeHangSX(){
          if(!this.timkiembosung.check2) return
          if(this.timkiembosung.hangsx_selected!=null){
            await this.NAP_DS_DONG_TBI()
          }
        },
        async changeHeThong(){
          if(!this.timkiembosung.check2) return
          if(this.timkiembosung.hangsx_selected!=null){
            await this.NAP_DS_DONG_TBI()
          }
        },
        changeDroplistVatTu(){
            let value=this.vattu_cha_selected
            // if(!this.isChangedEventDrop){
            //     this.isChangedEventDrop=true
            //     return
            // }
            if(value==null){
              return
            }
            //search expand node and focus
            const index=this.ds_danhmuc_vattu.findIndex(x=>x.vattu_id==value)
            if(index>-1){
                //this.expandedNodes.push(value)
                //selected node focus
                this.selectedNodes=[value]

                //find array parent expandedNodes
                var item=this.ds_danhmuc_vattu[index]
                this.expandedNodes=[-1]
                this.expandedNodes.push(value)
                var vattu_id=item.vattu_cha_id
                while(vattu_id!=null&&vattu_id!=-1){
                    const index1=this.ds_danhmuc_vattu.findIndex(x=>x.vattu_id==vattu_id)
                    if(index1>-1){
                        this.expandedNodes.push(vattu_id)
                        vattu_id=this.ds_danhmuc_vattu[index1].vattu_cha_id
                    }else{
                        vattu_id=-1
                    }
                }

            }else{
                this.$toast.error('Không tìm thấy thông tin vật tư trên TreeView!')
                return
            }
        },
        focusNode(value){
           const index=this.ds_danhmuc_vattu.findIndex(x=>x.vattu_id==value)
           if(index>-1){
            this.selectedNodes=[value]
            this.expandedNodes=[-1]
            let item=this.ds_danhmuc_vattu[index]
            var vattu_id=item.vattu_cha_id
            this.expandedNodes.push(value)
            while(vattu_id!=null&&vattu_id!=-1){
                const index1=this.ds_danhmuc_vattu.findIndex(x=>x.vattu_id==vattu_id)
                if(index1>-1){
                    this.expandedNodes.push(vattu_id)
                    vattu_id=this.ds_danhmuc_vattu[index1].vattu_cha_id
                }else{
                    vattu_id=-1
                }
            }
           }
        },
        nodeClicked(args){
            //console.log('nodeClicked',args.node.dataset.uid)
            //this.vattu_cha_selected= Number(args.node.dataset.uid)
        },
        tvVatTu_FocusedNodeChanged(node){
            let vattu_id=Number(node.nodeData.id)
            //vattu_id=-1 root
            //this.isChangedEventDrop=false
            this.vattu_cha_selected= vattu_id
            let vattu=this.ds_danhmuc_vattu.find(x=>x.vattu_id==vattu_id)
            this.$emit('tvVatTuFocusedNodeChanged', vattu)
        },
        createTreeVatTu(dsVatTu){
            //
            let tree = []
            let vattuChaChaArray = dsVatTu.map((item) => {
                return item['vattu_cha_id']
            })
            let vattuArray = dsVatTu.map(item => (item['vattu_id']))
            dsVatTu.forEach(item => {
                //check item la parent node
                if(vattuChaChaArray.includes(item['vattu_id'])){
                    //check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id
                    if(!vattuArray.includes(item['vattu_cha_id'])){
                        delete item.vattu_cha_id
                    }
                    tree.push( {...item, hasChild:true, expanded: false})
                }else{
                    tree.push(item)
                }
            })
            //console.log('tree', tree)
            return tree
        },
        //{db_tbi, ht_id}
        getObjectTieuChi(){
          if(!this.timkiembosung.check1&&!this.timkiembosung.check2){
            return {
              db_tbi:-1,
              ht_id:-1
            }
          }else{
            var ht_id = -1
            var db_tbi = -1
            if(this.timkiembosung.check1){
              ht_id=this.timkiembosung.hethong_selected
            }
            if(this.timkiembosung.check2){
              db_tbi=this.timkiembosung.dongthietbi_seleted
            }
            return {
              db_tbi:db_tbi,
              ht_id:ht_id
            }
          }
        },
        closeTieuChiTimKiem(){
          this.showDropdown=''
        },
        async lay_danhsach_hang_sx(){
            try{
                this.loading(true)
                let response = await SuppliesAPI.lay_danhsach_hang_sx(this.axios)
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
                  this.$toast.error('Không load được danh sách hãng sản xuất')
              }
              return []
            }
        },
        async lay_danhsach_linhvuc(){
            try{
                this.loading(true)
                let response = await SuppliesAPI.lay_danhsach_linhvuc(this.axios)
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
                  this.$toast.error('Không load được danh sách lĩnh vực')
              }
              return []
            }
        },
        async lay_tt_hethong_theo_linhvuc(linhvuc_id){
            try{
              this.loading(true)
              let response = await SuppliesAPI.lay_tt_hethong_theo_linhvuc(this.axios, linhvuc_id)
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
                  this.$toast.error('Không load được danh sách hệ thống')
              }
              return []
            }
        },
        async sp_nap_ds_dong_tbi(param){
            try{
                this.loading(true)
                let response = await SuppliesAPI.sp_nap_ds_dong_tbi(this.axios, param)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return JSON.parse(response.data.data)
                }else{
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        async NAP_DS_DONG_TBI(){
          let result=await this.sp_nap_ds_dong_tbi({
                HANGSX_ID: this.timkiembosung.hangsx_selected,
                HETHONG_TK_ID: this.timkiembosung.check1?this.timkiembosung.hethong_selected:null
          })
          if(result&&result.ERROR_CODE==1&&result.RESULT&&result.RESULT.DS_DONG_TBI){
            this.timkiembosung.dongthietbi=result.RESULT.DS_DONG_TBI
            if(this.timkiembosung.dongthietbi.length>0){
              this.timkiembosung.dongthietbi_seleted=this.timkiembosung.dongthietbi[0].DONGTBI_ID
            }else{
              this.timkiembosung.dongthietbi=[]
              this.timkiembosung.dongthietbi_seleted=null
            }
          }else{
            this.timkiembosung.dongthietbi=[]
            this.timkiembosung.dongthietbi_seleted=null
          }
        },
        async initDuLieu(){
          this.timkiembosung.linhvuc=await this.lay_danhsach_linhvuc()
          if(this.timkiembosung.linhvuc.length>0){
            this.timkiembosung.linhvuc_selected=this.timkiembosung.linhvuc[0].LINHVUC_ID
            this.timkiembosung.hethong=await this.lay_tt_hethong_theo_linhvuc(this.timkiembosung.linhvuc_selected)
            if(this.timkiembosung.hethong.length>0){
              this.timkiembosung.hethong_selected=this.timkiembosung.hethong[0].hethong_id
            }
          }
          this.timkiembosung.hangsx=await this.lay_danhsach_hang_sx()
          if(this.timkiembosung.hangsx.length>0){
            this.timkiembosung.hangsx_selected=this.timkiembosung.hangsx[0].HANGSX_ID
          }
          await this.NAP_DS_DONG_TBI()
          await this.initFocusFirstNode()
        },
        async initFocusFirstNode(){
          setTimeout(()=>{
            const vattu=this.ds_danhmuc_vattu.find(x=>x.unitlevel==1)
            this.selectedNodes=[vattu!=undefined?vattu.vattu_id:-1]
          },600)
        }

    },
    mounted(){

    },
    computed:{
      danhmuc_vattu_tree(){
        let danhmuc_vattu=[]
        danhmuc_vattu.push({
          unitlevel: 0, 
          ten_vt: "Root",
          ten_ht:'Root',
          vattu_id: -1,
          vattu_cha_id: null,
          ma_vt: "",
        })
        this.ds_danhmuc_vattu.forEach((item)=>{
            if(item.unitlevel==1){
                item.vattu_cha_id=-1
            }
            danhmuc_vattu.push(Object.assign(item, {
                ten_ht:item.ma_vt +' - '+item.ten_vt
            }))
        })
        let vattu_tree=this.createTreeVatTu(danhmuc_vattu)
        return { dataSource: vattu_tree, id: 'vattu_id', text: 'ten_ht', parentID: 'vattu_cha_id', hasChildren: 'hasChild' }
      },
      dsVatTu(){
        return this.ds_danhmuc_vattu.map(x=>{
         // e=> ({id: e.vattu_id, text: e.ten_vt})
          let ma_vt=x.ma_vt?x.ma_vt.toString():''
          let ten_vt=x.ten_vt?x.ten_vt.toString():''
          return {
            id:x.vattu_id,
            text:ma_vt+' - '+ten_vt
          }
        })
      }
    },
    watch:{
      danhsach_danhmuc_vattu(val){
        this.vattu_cha_selected=null
        this.ds_danhmuc_vattu=val
        if(val.length>0){
          setTimeout(()=>{
            this.expandedNodes=[-1, val[0].vattu_id]
          },200)
        }
      }
    },
    directives:{
      'click-outside':directive
    }
}
</script>
<style>
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
</style>