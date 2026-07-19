<template>
     <b-modal
    ref="popupLocDuLieu"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
    no-close-on-backdrop
  >
    <div class="modal-content popup-box">
      <ActionTop :actions="actions" @onActionClick="onActionClick"/>
      <div class="popup-body">
        
        <div class="row">
            <div v-if="Role==1" class="col-12" :class="{'col-sm-6': Role==1}">
                <div class="check-action" style="margin-bottom: 7px !important;">
                    <input type="checkbox" class="check" v-model="checkTimTheoFile"/>
                    <span class="name">Tìm theo file</span>
                </div>
            </div>
            <div class="col-12" :class="{'col-sm-6': Role==1}">
                <b-form-checkbox v-model="checkRaBanCheo" :indeterminate="indeterminate" @change="changeCheck">
                    Rà soát bán chéo
                </b-form-checkbox>
            </div>
        </div>
        <div class="box-form" v-show="checkTimTheoFile">
            <div class="legend-title">Tìm theo file excel</div>
            <div class="info-row">
                <div class="key w100">Danh sách file</div>
                <div class="value">
                    <div class="select-custom" ref="ds_files">
                        <select2 :settings="{ dropdownParent: $refs['ds_files'] }"
                            v-model="file_selected" class="select2"
                            :options="ds_files"
                            >
                        </select2>
                    </div>
                </div>
            </div>
        </div>
        <div class="box-form" v-show="!checkTimTheoFile">
            <div class="legend-title">Trạng thái</div>
            <div class="info-row">
              <div class="value">
                  <div class="select-custom" ref="ds_trangthai">
                      <select2 :settings="{ dropdownParent: $refs['ds_trangthai'] }" ref="cboTrangThai"
                          v-model="trangthai_selected" class="select2"
                          :options="ds_trangthai"
                          >
                      </select2>
                  </div>
              </div>
            </div>
        </div>
        <div class="box-form" v-show="!checkTimTheoFile">
            <div class="legend-title">Lọc theo loại hình</div>
            <KDataGrid
                ref="girdLoaiHinh"
                :columns="columns"
                :dataSource="dataSources"
                :enable-paging-server="false"
                :allowPaging="true"
                :showFilter="true"
                :showColumnCheckbox="true"
                panelDataHeight="160px"
                :enabledSelectFirstRow="false"
                :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"/>
        </div>
        <div class="box-form" v-show="!checkTimTheoFile&&Role==1">
            <div class="legend-title">Tỉnh lấy dữ liệu</div>
            <KDataGrid
                ref="girdTinh"
                :columns="columnsTinh"
                :dataSource="dataSourcesTinh"
                :enable-paging-server="false"
                :showFilter="true"
                :showColumnCheckbox="true"
                panelDataHeight="160px"
                :enabledSelectFirstRow="false"
                :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                pageSize=100
                />
        </div>
      </div>
    </div>
  </b-modal>   
</template>
<script>
import KDataGrid from '@/components/kylq_components/KDataGrid'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
export default {
    name:'LocDuLieuModal',
    props:{
        dsLoaiHinh:{
            type:Array,
            default:()=>[]
        },
        Role:{
            type:Number,
            default:2
        },
        dsDonViVNP:{
            type:Array,
            default:()=>[]
        }
    },
    components:{
        KDataGrid,
        ActionTop
    },
    data(){
        return {
            columns:[
                {
                    fieldName:'LOAIHINH',
                    headerText:'Loại hình',
                    allowFiltering:true
                }
            ],
            dataSources:[],
            columnsTinh:[
                {
                    fieldName:'TenHT',
                    headerText:'Tỉnh',
                    allowFiltering:true
                }
            ],
            dataSourcesTinh:[],
            actions:[
                {
                    id:'chapnhan',
                    name:'Tìm kiếm dữ liệu',
                    active: true,
                    icon_class:'ui-1_check-circle-08 nc-icon-glyph'
                },
                {
                    id:'huy',
                    name:'Huỷ',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_circle-remove'
                },

            ],
            ds_trangthai:[
                {
                    id:2,
                    text:'TTKD đang xử lý'
                },
                {
                    id:6,
                    text:'Đã chuẩn hóa'
                },
                {
                    id:7,
                    text:'Không nhận khách hàng'
                },
                {
                    id:0,
                    text:'Toàn bộ'
                },
            ],
            trangthai_selected:2,
            checkRaBanCheo:false,
            indeterminate:false,
            checkTimTheoFile:false,
            file_selected:null,
            ds_files:[]
        }
    },
    methods:{
        showModal() {
            this.$refs["popupLocDuLieu"].show()
        },
        hideModal() {
            this.$refs["popupLocDuLieu"].hide()
        },
        onActionClick(action){

           switch(action.id){
               case 'chapnhan':
                   this.$emit('accept', {
                       bancheo: this.indeterminate?0:this.checkRaBanCheo?3:1,
                       trangthai:this.trangthai_selected,
                       ds_loaihinh:this.$refs.girdLoaiHinh.getSelectedRecords(),
                       ds_tinhchon:this.Role==1?this.$refs.girdTinh.getSelectedRecords():[],
                       checkTimTheoFile:this.Role==1?this.checkTimTheFile:false,
                       file_id:this.Role==1&&this.checkTimTheFile?this.file_selected:0
                   })
                   this.hideModal()
                   break

                case 'huy':
                    this.hideModal()
                    break 
                default:
                    this.hideModal()
                    break   
              }
        },

        changeCheck(){
            this.$nextTick(()=>{
                this.indeterminate=false
            })
        },
        initDuLieu(){
            
            this.$nextTick(()=>{
                this.indeterminate=true
            })
            this.dataSources=this.dsLoaiHinh
            this.dataSourcesTinh=this.dsDonViVNP
        },
        handleShowModal(){
            this.dataSources=[]
            this.dataSourcesTinh=[]
            this.ds_files=[]
            this.checkTimTheoFile=false
            this.trangthai_selected=2
            this.checkTimTheoFile=false
            this.file_selected=null
            this.ds_files=[]
            setTimeout(()=>{
                this.initDuLieu()
            }, 500)
        }
    },
    
    
}
</script>
<style>
/* #idCheckRaBanCheo:indeterminate {
  box-shadow: 0 0 1px 1px red;
} */
</style>