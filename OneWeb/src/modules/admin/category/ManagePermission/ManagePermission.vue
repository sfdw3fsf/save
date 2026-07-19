<template>
    <div class="main-wrapper">
        <breadcrumb :header="header"/>
        <div class="page-content" style="top: 50px;">
            <div class="box-form">
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row marb0">
                            <div class="key w80">Loại CT</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_loai_ct">
                                    <select2 :settings="{ dropdownParent: $refs['ds_loai_ct'] }" ref="cboLoaiCT"
                                        v-model="loai_ct_selected" class="select2"
                                        :options="danhsach_loai_ct.map(e=> ({id: e.LOAI_CT_ID, text: e.TENLOAI_CT}))"
                                        @change="changeLoaiCT"
                                    >
                                    </select2>
                                </div>  
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row marb0">
                            <div class="key w120">Quản lý quyền</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_quyen">
                                    <select2 :settings="{ dropdownParent: $refs['ds_quyen'] }" ref="cboQuyen"
                                        v-model="quyen_selected" class="select2"
                                        :options="danhsach_quyen.map(e=> ({id: e.QUYEN_ID, text: e.TEN_QUYEN}))"
                                        @change="changeQuyen"
                                    >
                                    </select2>
                                </div>  
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="nav tabs tab-over">
                <a href="#tab1" data-toggle="tab" @click.prevent="changeTab(1)" :class="{active: currentTab==1}">Phân quyền menu</a>
                <a href="#tab2" data-toggle="tab" @click.prevent="changeTab(2)" :class="{active: currentTab==2}">Phân quyền báo cáo</a>
                <a href="#tab3" data-toggle="tab" @click.prevent="changeTab(3)" :class="{active: currentTab==3}">Phân quyền gọi hàm</a>
                <a href="#tab4" data-toggle="tab" @click.prevent="changeTab(4)" :class="{active: currentTab==4}">Phân quyền dữ liệu từ điển</a>
            </div>
            <div class="tab-content">
                <div class="tab-pane" :class="{active: currentTab==1}" id="tab1" >
                    <PhanQuyenMenu ref="phanquyenmenu" :loai_ct_id="loai_ct_id" :quyen_id="quyen_id" />
                </div>
                <div class="tab-pane" :class="{active: currentTab==2}" id="tab2">
                    <PhanQuyenBaoCao ref="phanquyenbaocao" :loai_ct_id="loai_ct_id" :quyen_id="quyen_id"/>
                </div>
                <div class="tab-pane" :class="{active: currentTab==3}" id="tab3">
                    <PhanQuyenGoiHam ref="phanquyengoiham" :quyen_id="quyen_id"/>
                </div>
                <div class="tab-pane" :class="{active: currentTab==4}" id="tab4">
                    <PhanQuyenDuLieu ref="phanquyendulieu" :quyen_id="quyen_id"/>
                </div>
            </div>
        </div>
        <Footer/>
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import PhanQuyenMenu from './PhanQuyenMenu.vue'
import PhanQuyenBaoCao from './PhanQuyenBaoCao.vue'
import PhanQuyenGoiHam from './PhanQuyenGoiHam.vue'
import PhanQuyenDuLieu from './PhanQuyenDuLieu.vue'
import ManagePermissionAPI from './ManagePermissionAPI'

export default {
    name:'ManagePermission',
    components:{
        Footer,
        breadcrumb,
        PhanQuyenMenu,
        PhanQuyenBaoCao,
        PhanQuyenGoiHam,
        PhanQuyenDuLieu
    },
    computed:{
        loai_ct_id(){
            if(this.loai_ct_selected==null){
                return -1
            }
            return this.loai_ct_selected
        },
        quyen_id(){
            if(this.quyen_selected==null){
                return -1
            }
            return this.quyen_selected
        }
    },
    data(){
        return{
            header: {
                title: "Quản lý quyền",
                list: [
                    {
                        name: "Quản lý người dùng", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Quản lý quyền",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ],
            },
            currentTab:1,
            danhsach_loai_ct:[],
            loai_ct_selected:null,
            danhsach_quyen:[],
            quyen_selected:null
        }
    },
    methods:{
        changeTab(tab){
            if(this.currentTab==tab){
                return
            }
            this.currentTab=tab
            if(this.currentTab==1){
                this.$refs.phanquyenmenu.initData()
            }else if(this.currentTab==2){
                this.$refs.phanquyenbaocao.initData()
            }else if(this.currentTab==3){
                this.$refs.phanquyengoiham.initData()
            }else if(this.currentTab==4){
                this.$refs.phanquyendulieu.initData()
            }
        },
        changeLoaiCT(){
            if(this.currentTab==1){
                this.$nextTick(()=>{
                    this.$refs.phanquyenmenu.initData()
                })
            }else if(this.currentTab==2){
                this.$nextTick(()=>{
                    this.$refs.phanquyenbaocao.initData()
                })
            }
        },
        changeQuyen(){
            if(this.currentTab==1){
                this.$nextTick(()=>{
                    this.$refs.phanquyenmenu.changeQuyen(this.quyen_selected)
                })
            }else if(this.currentTab==2){
                this.$nextTick(()=>{
                    this.$refs.phanquyenbaocao.changeQuyen(this.quyen_selected)
                })
            }else if(this.currentTab==3){
                this.$nextTick(()=>{
                    this.$refs.phanquyengoiham.changeQuyen(this.quyen_selected)
                })
            }else if(this.currentTab==4){
                this.$nextTick(()=>{
                    this.$refs.phanquyendulieu.changeQuyen(this.quyen_selected)
                })
            }
        },
        async lay_danhsach_loai_ct(){
            try{
                this.loading(true)
                let response=await ManagePermissionAPI.lay_danhsach_loai_ct(this.axios)
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
                    this.$toast.error('Đã có lỗi khi lấy danh sách loại chương trình')
                }
                return []
            }
        },
        async lay_danhsach_quyen(){
            try{
                this.loading(true)
                let response=await ManagePermissionAPI.lay_danhsach_quyen(this.axios)
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
                    this.$toast.error('Đã có lỗi khi lấy danh sách quyền')
                }
                return []
            }
        },
        async initDuLieu(){
            this.danhsach_loai_ct = await this.lay_danhsach_loai_ct()
            if(this.danhsach_loai_ct.length>0){
                this.loai_ct_selected=this.danhsach_loai_ct[0].LOAI_CT_ID
            }
            this.danhsach_quyen=await this.lay_danhsach_quyen()
            if(this.danhsach_quyen.length>0){
                this.quyen_selected=this.danhsach_quyen[0].QUYEN_ID
            }
            setTimeout(()=>{
                this.$refs.phanquyenmenu.initData()
            }, 300)
        }
    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        },200)
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