<template>
    <div class="main-wrapper">
        <breadcrumb :header="header"/>

        <ActionTop :actions="actions" @onActionClick="onActionClick"/>

        <div class="page-content">
            <div class="grid-stack-box">
                <ThongTinGiaoDich @InputMaGDChange="inputMaGDChange" @onEnterMaGD="onEnterMaGD"  @showPopupSearchContract="showPopupSearchContract"/>
                <ThongTinToThiCongMoi ref="thongTinToThiCongMoi" @success_update="successUpdate"/>
            </div>

            <div class="box-form">
                <div class="legend-title">Danh sách thuê bao</div>
                <KTableV3 :columns="columns"
                    :dataSources="ds_hopdong_theo_hdkhid_v2"
                    :rowSelectedId="rowSelectedId"
                    :allowFilter="true"
                    id="hdtb_id"
                    @onSelectedRow="onSelectedRow" />
            </div>
            <!-- Modal popup SearchAccount -->
            <SearchContractModal ref="popupSearchContract" @accept="acceptSearchContract"
                :donvi_id="0"
                :nhanvien_id="0"
                :donvi_dl_id="donvi_dl_id"
            />
            <!-- Test -->
            <!-- <SearchContractModal ref="popupSearchContract" @accept="acceptSearchContract"
                :donvi_id="temp.donvi_id"
                :nhanvien_id="temp.nhanvien_id"
                :donvi_dl_id="temp.donvi_dl_id"
                :trangthai_hd="temp.trangthai_hd"
                :loai_hd_id="temp.loai_hd_id"
                :dichvu_id="temp.dichvu_id"
                :ngay_yc="temp.ngay_yc"
                :ngay_kt="temp.ngay_kt"

            /> -->

            <!-- Modal popup  DanhSachCacBienDong-->
            <DanhSachCacBienDong ref="popupDsBD" @accept="acceptDsBD" :dataSources="ds_hopdong_theo_mathuebao"/>
        </div>
        
        <Footer/>
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import ThongTinGiaoDich from './ThongTinGiaoDich.vue'
import ThongTinToThiCongMoi from './ThongTinToThiCongMoi.vue'
import DanhSachCacBienDong from './popup/DanhSachCacBienDong.vue'
// import SearchContractModal from './popup/SearchContract/SearchContractModal.vue'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";

import ChangeInstallUnitAPI from './ChangeInstallUnitAPI'
import EventBus from '@/utils/eventBus'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'

//this.$bvModal.show('popupSearchAccount')
export default {
    name:'ChangeInstallUnit',

    components:{
        breadcrumb,
        Footer,
        ActionTop,
        KTableV3,
        ThongTinGiaoDich,
        ThongTinToThiCongMoi,
        SearchContractModal,
        DanhSachCacBienDong
    },
    data(){
        return {
            header: {
                title: "Sửa đơn vị thi công",
                list: [
                    {   
                        name: "Cập nhật KQTC", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {   
                        name: "Thay đổi, khoá mở, thanh lý", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Sửa đơn vị thi công",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ],
            },
            actions:[
                {
                    id:'tracuu',
                    name:'Tra cứu',
                    active: true,
                    icon_class:'one-file-search'
                },
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
            ],
            columns:[
                {
                    field: "ma_tb",
                    label: "Số máy/Acc",
                    allowFilter: true
                },
                {
                    field: "ten_tb",
                    label: "Tên TB",
                    allowFilter: true
                },
                {
                    field: "diachi_ld",
                    label: "Địa chỉ LĐ",
                    allowFilter: true
                }

            ],
            inputMaGD:'',
            hopdong_theo_mathuebao:{},
            hopdong_theo_hdkhid_v2:{},
            ds_hopdong_theo_mathuebao:[],
            ds_hopdong_theo_hdkhid_v2:[],
            rowSelectedId:-1,
            donvi_dl_id:0,
            //input yêu cầu
            // temp:{ 
            //     loai_hd_id: 28,
            //     dichvu_id: 0,
            //     ngay_yc: "23/06/2022",
            //     ngay_kt: "",
            //     trangthai_hd: 1,
            //     donvi_dl_id: 0,
            //     donvi_id: 13431,
            //     nhanvien_id: 13431 
            // }

        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='tracuu'){
                this.TraCuu()
            }else if(action.id=='ghilai'){
                this.GhiLai()
            }
        },
        TraCuu(){
            if(this.inputMaGD&&this.inputMaGD.length>0){
                this.lay_ds_hopdong_theo_mathuebao(this.inputMaGD)
            } 
        },

        GhiLai(){
            this.$refs.thongTinToThiCongMoi.CapNhat()
        },
        showPopupSearchContract(){   
            this.$refs.popupSearchContract.showModal()
        },
        acceptSearchContract(item){
            this.lay_ds_hopdong_theo_mathuebao(item.ma_gd)
        },
        acceptDsBD(data){
            this.hopdong_theo_mathuebao=data
            this.ds_hopdong_theo_hdkhid_v2=[]
            this.lay_ds_hopdong_theo_hdkhid_v2(this.hopdong_theo_mathuebao.hdkhId)
        },
        inputMaGDChange(value){
            this.inputMaGD=value
        },
        onEnterMaGD(value){
            if(value&&value.length>0){
                this.lay_ds_hopdong_theo_mathuebao(value)
            }
        },
        onSelectedRow(item){
            this.hopdong_theo_hdkhid_v2=item
            EventBus.$emit('hopdong_theo_hdkhid', {hopdong_theo_mathuebao: this.hopdong_theo_mathuebao, hopdong_theo_hdkhid_v2: this.hopdong_theo_hdkhid_v2})
        },
        successUpdate(){
            if(this.inputMaGD&&this.inputMaGD.length>0){
                this.lay_ds_hopdong_theo_mathuebao(this.inputMaGD)
            }
            
            //this.onSelectedRow(this.hopdong_theo_hdkhid_v2)
        },
        async lay_ds_hopdong_theo_mathuebao(maTb){
            try{
                this.loading(true)
                let response=await ChangeInstallUnitAPI.lay_ds_hopdong_theo_mathuebao(this.axios, maTb)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.ds_hopdong_theo_mathuebao=response.data.data
                    if(this.ds_hopdong_theo_mathuebao.length>0){
                        if(this.ds_hopdong_theo_mathuebao.length>1){
                            this.$refs.popupDsBD.showModal()
                        }else{
                             this.hopdong_theo_mathuebao=this.ds_hopdong_theo_mathuebao[0]
                             this.ds_hopdong_theo_hdkhid_v2=[]
                             await this.lay_ds_hopdong_theo_hdkhid_v2(this.hopdong_theo_mathuebao.hdkhId)
                        }
                    }else{
                        this.$toast.error('Không tìm thấy hợp đồng!')
                    }
                    
                }else{
                    this.ds_hopdong_theo_mathuebao=[]
                    this.$toast.error('Không tìm thấy hợp đồng!')
                }
            }catch(e){
                this.loading(false)
                this.$toast.error('Đã xảy ra lỗi. Không lấy được thông tin hợp đồng')
            }
        },
        async lay_ds_hopdong_theo_hdkhid_v2(hdkhId){
            try{
                this.loading(true)
                let response=await ChangeInstallUnitAPI.lay_ds_hopdong_theo_hdkhid_v2(this.axios, hdkhId)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.ds_hopdong_theo_hdkhid_v2=response.data.data
                    if(this.ds_hopdong_theo_hdkhid_v2.length>0){
                        this.rowSelectedId=this.ds_hopdong_theo_hdkhid_v2[0].hdtb_id
                        this.onSelectedRow(this.ds_hopdong_theo_hdkhid_v2[0])
                        
                    }
                }else{
                    this.ds_hopdong_theo_hdkhid_v2=[]
                    this.$toast.error('Không tìm thấy hợp đồng!')
                }
            }catch(e){
                this.loading(false)
                this.$toast.error('Đã xảy ra lỗi. Không lấy được thông tin hợp đồng')
            }
        }

    },
    mounted(){
        // Split boxes
        Split(['#boxLeft', '#boxRight'], {
            sizes: [50, 50],
            gutterSize: 16,
            onDragEnd: function (sizes) {}
        })
        this.donvi_dl_id=Number(this.$root.token.getDonViDuLieuID())
    }
}
</script>