<template>
    <div class="main-wrapper">
        <breadcrumb :header="header"/>
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>
        <div class="page-content">
            <div class="box-form">
                <div class="legend-title">
                    <div class="row">
                        <div class="col-sm-8 col-12">
                            <span class="padt7 block">Danh sách thuê bao dùng thử</span>
                        </div>
                        <div class="col-sm-4 col-12">
                            <div class="info-row">
                                <div class="key w80 normal bold red">Tìm kiếm</div>
                                <div class="value">
                                    <div class="input-icon-right">
                                        <input type="text" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key w80 normal">Khu vực</div>
                            <div class="value">
                                <!-- <ejs-multiselect id='multiselect'
                                    :dataSource="khuvuc"
                                    placeholder="Chọn khu vực"
                                    mode="CheckBox"
                                    v-model="khuvuc_selected"
                                    @change="changeKhuVuc"
                                    :allowFiltering="true"
                                    locale='vi-VN'
                                    :filtering="onFilteringKhuVuc"
                                    :fields="{text: 'NAME', value: 'ID' }"
                                    :showSelectAll="true"
                                    selectAllText="Chọn tất cả"
                                    unSelectAllText="Chọn tất cả"/> -->
                                    <ComboboxGrid ref="cboCongTrinh" :showColumnCheckbox="true" v-bind:columns="khuvuc_columns" v-bind:dataSource="khuvuc" v-model.trim="khuvuc_selected" textField="NAME" valueField="ID" :gridStyle="{left: 100}" :allowPaging="true" />
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80 normal">Đơn vị LHĐ</div>
                            <div class="value">
                                <ejs-multiselect id='multiselect'
                                    :dataSource="donvi_lhd"
                                    placeholder="Chọn đơn vị"
                                    mode="CheckBox"
                                    v-model="donvi_lhd_selected"
                                    :allowFiltering="true"
                                    locale='vi-VN'
                                    :filtering="onFilteringDonVi"
                                    :fields="{text: 'NAME', value: 'ID' }"
                                    :showSelectAll="true"
                                    selectAllText="Chọn tất cả"
                                    unSelectAllText="Chọn tất cả"/>
                            </div>
                        </div>
                    </div>
                </div>
                
            </div>
            <div class="tabs nav tab-over">
                <a style="cursor: pointer;" class="active">Phiếu chờ xử lý</a>
            </div>
            <div class="box-form tab-content">
                <DataGrid
                    :columns="columns"
                    :dataSource="dataSources"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="true"
                    @selectedItemsChanged="gridPhieuRowsSelected"/>
            </div>
        </div>
        <Footer/>
        <!-- Modal -->
        <ChonDoiTuongDungChinhThucModal ref="chonDoiTuongDungChinhThucModal" :data="object" @ketqua="ketqua"/>
        <LyDoHuyDungThuModal ref="lyDoHuyDungThuModal" @accept="onAcceptLyDoHuy" :loaitb_id="loaitb_id"/>
        <ExportDataModal ref="exportDataModal" :data="dataSources"  file_name="danh-sach-hopdong-dungthu"/>
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KSelect from '@/components/kylq_components/KSelect.vue'
import MonitorTrialContractAPI from './MonitorTrialContractAPI'
import moment from 'moment'
import ChonDoiTuongDungChinhThucModal from './ChonDoiTuongDungChinhThucModal.vue'
import LyDoHuyDungThuModal from './LyDoHuyDungThuModal.vue'
import { DataManager,Query,ODataV4Adaptor,Predicate } from "@syncfusion/ej2-data"
import {TRANGTHAI_PORT} from '../DeclareServiceSpeedITSecure/ThamSo'
import ExportDataModal from '../../profile/ExportData/ExportDataModal.vue'
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"
import { L10n } from '@syncfusion/ej2-base'
L10n.load({
  'vi-VN': {
    'dropdowns': {
      'noRecordsTemplate': "Không có dữ liệu",
      'actionFailureTemplate': ""
    }
  }
})
//frmChuyenHDThat.cs
//multi check https://ej2.syncfusion.com/vue/documentation/multi-select/checkbox/
export default {
    name:'MonitorTrialContract',
    components:{
        Footer,
        breadcrumb,
        KTableV3,
        ActionTop,
        KSelect,
        ChonDoiTuongDungChinhThucModal,
        LyDoHuyDungThuModal,
        ExportDataModal
    },
    provide: {
        multiselect: [CheckBoxSelection]
    },
    data(){
        return {
            header: {
                title: "Theo dõi hợp đồng dùng thử",
                list: [
                    { 
                        name: "Hợp đồng", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    { 
                        name: "Lập hợp đồng", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Theo dõi HĐ dùng thử",
                        link: { name: "Ui.buttons" },
                        active: true
                    }
                ],
            },
            actions:[
                {
                    id:'danhsach',
                    name:'Danh sách',
                    active: true,
                    icon_class:'nc-icon-glyph design_bullet-list-67',
                    tooltip:'Danh sách'
                    
                },
                {
                    id:'chuyenchinhthuc',
                    name:'Chuyển chính thức',
                    active: false,
                    icon_class:'nc-icon-glyph arrows-1_simple-right',
                    tooltip:'Chuyển sang hợp đồng dùng chính thức'
                },
                {
                    id:'huydungthu',
                    name:'Huỷ dùng thử',
                    active: false,
                    icon_class:'nc-icon-glyph ui-1_circle-remove',
                    tooltip:'Hủy dùng thử'
                },
                {
                    id:'xuat_excel',
                    name:'Xuất excel',
                    active: false,
                    icon_class:'one-excel',
                    tooltip:'Xuất excel'
                },
            ],
            columns:[
                {
                    fieldName: "ma_tb",
                    headerText: "Số máy/Acc",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "ten_tb",
                    headerText: "Tên TB",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "diachi_tb",
                    headerText: "Địa chỉ TB",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "ten_kv",
                    headerText: "Khu vực",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "loaihinh_tb",
                    headerText: "Loại hình TB",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "nguoi_cn",
                    headerText: "Người lập HĐ",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "songay",
                    headerText: "Số ngày",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "ngay_bd",
                    headerText: "Ngày BĐ",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "ngay_tb",
                    headerText: "Ngày TB",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "ngay_kt",
                    headerText: "Ngày KT",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "ten_nvgt",
                    headerText: "NV giới thiệu",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "dv_lhd",
                    headerText: "ĐV lập HĐ",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "trangthai_tv",
                    headerText: "Trạng thái tư vấn",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "nguoi_tv",
                    headerText: "Người tư vấn",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "ngay_tv",
                    headerText: "Ngày tư vấn",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "dichvuvt_id",
                    headerText: "Dịch vụ VT",
                    allowFiltering: true,
                    allowSorting:true
                },
            ],
            dataSources:[],
            khuvuc_columns:[
                {
                    fieldName: "NAME",
                    headerText: "NAME",
                    allowFiltering: true,
                    allowSorting:true
                },
            ],
            khuvuc:[],
            khuvuc_selected:[],
            donvi_lhd:[],
            donvi_lhd_selected:[],
            rowSelected:null,
            object:null,
            loaitb_id:-1,
            BATBUOC_NHAP_LYDOHUY:0,
            vthuebao_id:0
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='danhsach'){
                console.log('khuvuc_selected')
                console.log(this.khuvuc_selected)
                let khuvuc=(this.khuvuc_selected.length==0||this.khuvuc_selected.length==this.khuvuc.length)?'0':this.khuvuc_selected.map(x=>Number(x)).toString()
                let donvi=(this.donvi_lhd_selected.length==0||this.donvi_lhd_selected.length==this.donvi_lhd.length)?'0':this.donvi_lhd_selected.map(x=>Number(x)).toString()
                this.lay_ds_thuebao_dungthu(khuvuc,donvi)
            }else if(action.id=='chuyenchinhthuc'){
                this.chuyenChinhThuc()
            }else if(action.id=='huydungthu'){
                this.huyDungThu()
            }else if(action.id=='xuat_excel'){
                this.$refs.exportDataModal.showModal()                
            }
        },
        gridPhieuRowsSelected(ds){
            if(this.dataSources.length<=0||ds.length<=0){
                this.rowSelected=null
                return
            }
            this.rowSelected=ds[0]
            this.vthuebao_id=this.rowSelected.thuebao_id?this.rowSelected.thuebao_id:0
        },
        async confirm(message, title){
            try{
                let result = await this.$confirm(message,title,{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Hủy'
                })
                return 1
            }catch(e){
                return 0
            }
        },
        huyDungThu(){
            if(this.rowSelected==null||this.dataSources.length==0){
                return
            }
            if(this.BATBUOC_NHAP_LYDOHUY==1){
                this.$confirm('Bạn chắc chắn muốn thực hiện không?','Thông báo',{
                        confirmButtonText: 'Đồng ý',
                        cancelButtonText: 'Huỷ bỏ'
                }).then(() => {
                    //call 
                    //show modal chọn lý do
                    this.loaitb_id=this.rowSelected.loaitb_id
                    this.$nextTick(()=>{
                        this.$refs.lyDoHuyDungThuModal.showModal()
                    })
                })
            }else{
                this.$confirm('Bạn chắc chắn muốn thực hiện không?','Thông báo',{
                        confirmButtonText: 'Đồng ý',
                        cancelButtonText: 'Huỷ bỏ'
                }).then(() => {
                    this.HuyDungThu({
                        hdtb_id: this.rowSelected.hdtb_id,
                        lydohuy_id: 0,
                        thang_ktck: 0,
                        ghichu_huy: '',
                        ma_tb:this.rowSelected.ma_tb,
                        loaitb_id:this.rowSelected.loaitb_id,
                    })
                })
            }
            
        },
        async HuyDungThu(data){
            //check
            //kiemtra_datcoc_tb_dungthu
            let ktra_dc=await this.kiemtra_datcoc_tb_dungthu(this.vthuebao_id)
            if(ktra_dc!='ok'){
                let resultConfirm = await this.confirm(ktra_dc+'. Bạn chắc chắn muốn hủy dùng thử không?','Thông báo')
                if(resultConfirm==0){
                    return
                }
            }
            console.log('HuyDungThu', data)
            let result=await this.huy_hopdong_dungthu(data)
            if(result){
                //Cập nhật lại port khi hoàn công thanh lý-->Đã gom trong hàm huy_hopdong_dungthu phía trên
                // let dsadsl=await this.lay_ds_danhba_adsl(this.vthuebao_id)
                // if(dsadsl.length>0){
                //     let vport_id = dsadsl[0].port_id?dsadsl[0].port_id:0
                //     let vvci_vpi_id = dsadsl[0].vci_vpi_id?dsadsl[0].vci_vpi_id:0
                //     let account=this.rowSelected.ma_tb
                //     console.log('vport_id', vport_id)
                //     console.log('vvci_vpi_id', vvci_vpi_id)
                //     let dsktradb=await this.sp_ktra_dk_port({
                //         kieu: 1,
                //         ma_tb: account,
                //         port_id: vport_id,
                //         vci_vpi_id: vvci_vpi_id
                //     })
                //     let dsktrahd=await this.sp_ktra_dk_port({
                //         kieu: 2,
                //         ma_tb: account,
                //         port_id: vport_id,
                //         vci_vpi_id: vvci_vpi_id
                //     })
                //     if(dsktradb.length==0&&dsktrahd.length==0){
                //         //fn_capnhat_trangthai_port fn_capnhat_trangthai_portid fn_capnhat_ds_matb
                //         await this.fn_capnhat_trangthai_port({
                //             port_id:vport_id,
                //             vci_vpi_id:vvci_vpi_id,
                //             trangthai:TRANGTHAI_PORT.CHUA_SD
                //         })
                //         await this.fn_capnhat_trangthai_portid({
                //             port_id:vport_id,
                //             tt_port_id:TRANGTHAI_PORT.CHUA_SD,
                //             may_cn:"localhost",
                //             nguoi_cn:this.$root.token.getUserName()
                //         })
                //         await this.fn_capnhat_ds_matb({
                //             ds_matb:"",
                //             port_id:vport_id,
                //             vci_vpi_id:vvci_vpi_id
                //         })
                //         await this.Capnhat_port_vatly({
                //             port_id: vport_id,
                //             trangthai: TRANGTHAI_PORT.CHUA_SD
                //         })
                //     }else if(dsktradb.length!=0&&dsktrahd.length==0){
                //         var vds_matb=''
                //         for(let i=0;i<dsktradb.length;i++){
                //             vds_matb=vds_matb+dsktradb[i].ma_tb.toString()+'; '
                //             if(vds_matb.length>3000){
                //                 break
                //             }
                //         }
                //         if(vds_matb!=''){
                //             vds_matb=vds_matb.substring(0, vds_matb.length-2)
                //         }

                //         await this.fn_capnhat_trangthai_port({
                //             port_id:vport_id,
                //             vci_vpi_id:vvci_vpi_id,
                //             trangthai:TRANGTHAI_PORT.DA_SD
                //         })
                //         await this.fn_capnhat_trangthai_portid({
                //             port_id:vport_id,
                //             tt_port_id:TRANGTHAI_PORT.DA_SD,
                //             may_cn:"localhost",
                //             nguoi_cn:this.$root.token.getUserName()
                //         })
                //         await this.fn_capnhat_ds_matb({
                //             ds_matb:vds_matb,
                //             port_id:vport_id,
                //             vci_vpi_id:vvci_vpi_id
                //         })
                //         await this.Capnhat_port_vatly({
                //             port_id: vport_id,
                //             trangthai: TRANGTHAI_PORT.DA_SD
                //         })
                //     }else if(dsktradb.length==0&&dsktrahd.length!=0){
                //         var vds_matb=''
                //         for(let i=0;i<dsktrahd.length;i++){
                //             vds_matb=vds_matb+dsktrahd[i].ma_tb.toString()+'; '
                //             if(vds_matb.length>3000){
                //                 break
                //             }
                //         }
                //         if(vds_matb!=''){
                //             vds_matb=vds_matb.substring(0, vds_matb.length-2)
                //         }
                //         await this.fn_capnhat_trangthai_port({
                //             port_id:vport_id,
                //             vci_vpi_id:vvci_vpi_id,
                //             trangthai:TRANGTHAI_PORT.DA_SD
                //         })
                //         await this.fn_capnhat_trangthai_portid({
                //             port_id:vport_id,
                //             tt_port_id:TRANGTHAI_PORT.DA_SD,
                //             may_cn:"localhost",
                //             nguoi_cn:this.$root.token.getUserName()
                //         })
                //         await this.fn_capnhat_ds_matb({
                //             ds_matb:vds_matb,
                //             port_id:vport_id,
                //             vci_vpi_id:vvci_vpi_id
                //         })
                //         await this.Capnhat_port_vatly({
                //             port_id: vport_id,
                //             trangthai: TRANGTHAI_PORT.DA_SD
                //         })
                //     }else if(dsktradb.length!=0&&dsktrahd.length!=0){
                //         var vds_matb=''
                //         for(let i=0;i<dsktradb.length;i++){
                //             vds_matb=vds_matb+dsktradb[i].ma_tb.toString()+'; '
                //             if(vds_matb.length>3000){
                //                 break
                //             }
                //         }
                //         for(let i=0;i<dsktrahd.length;i++){
                //             vds_matb=vds_matb+dsktrahd[i].ma_tb.toString()+'; '
                //             if(vds_matb.length>3000){
                //                 break
                //             }
                //         }
                //         if(vds_matb!=''){
                //             vds_matb=vds_matb.substring(0, vds_matb.length-2)
                //         }
                //         await this.fn_capnhat_trangthai_port({
                //             port_id:vport_id,
                //             vci_vpi_id:vvci_vpi_id,
                //             trangthai:TRANGTHAI_PORT.DA_SD
                //         })
                //         await this.fn_capnhat_trangthai_portid({
                //             port_id:vport_id,
                //             tt_port_id:TRANGTHAI_PORT.DA_SD,
                //             may_cn:"localhost",
                //             nguoi_cn:this.$root.token.getUserName()
                //         })
                //         await this.fn_capnhat_ds_matb({
                //             ds_matb:vds_matb,
                //             port_id:vport_id,
                //             vci_vpi_id:vvci_vpi_id
                //         })
                //         await this.Capnhat_port_vatly({
                //             port_id: vport_id,
                //             trangthai: TRANGTHAI_PORT.DA_SD
                //         })
                //     }
                // }
                //Thông báo and refresh lưới dữ liệu
                this.$toast.success('Hủy hợp đồng dùng thử thành công!')
                let khuvuc=(this.khuvuc_selected.length==0||this.khuvuc_selected.length==this.khuvuc.length)?'0':this.khuvuc_selected.map(x=>Number(x)).toString()
                let donvi=(this.donvi_lhd_selected.length==0||this.donvi_lhd_selected.length==this.donvi_lhd.length)?'0':this.donvi_lhd_selected.map(x=>Number(x)).toString()
                await this.lay_ds_thuebao_dungthu(khuvuc,donvi)
            }
        },
        async onAcceptLyDoHuy(value){
            let data={
                hdtb_id: this.rowSelected.hdtb_id,
                lydohuy_id: value.lydohuy_id,
                thang_ktck: value.thang_ktck,
                ghichu_huy: value.ghichu_huy,
                ma_tb:this.rowSelected.ma_tb,
                loaitb_id:this.rowSelected.loaitb_id,
            }
            
            await this.HuyDungThu(data)
        },
        onFilteringKhuVuc(e){
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('NAME', 'contains', e.text.trim(), true);
                //predicate = predicate.or('TEN_KHO', 'contains', e.text);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.khuvuc, query);
        },
        onFilteringDonVi(e){
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('NAME', 'contains', e.text.trim(), true);
                //predicate = predicate.or('TEN_KHO', 'contains', e.text);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.donvi_lhd, query);
        },
        changeKhuVuc(){
            console.log('khuvuc_selected', this.khuvuc_selected)
            //$(".e-remain").text("...")
        },
        async chuyenChinhThuc(){
            if(this.rowSelected==null||this.dataSources.length==0){
                return
            }

            if(this.rowSelected.dichvuvt_id==13||this.rowSelected.dichvuvt_id==14
            ||this.rowSelected.dichvuvt_id==15||this.rowSelected.dichvuvt_id==16){
                //#note phụ thuộc UR2.1.039
                this.$toast.info('Đối với dichvuvt_id 13, 14, 15, 16 chuyển form GiaHanDVCNTT')
                let query={
                    ma_tb: this.rowSelected.ma_tb,
                    dichvuvt_id: this.rowSelected.dichvuvt_id,
                    chuyen_ct:1
                }
                console.log('query', query)
                let routeData = this.$router.resolve({name: 'ExtenChangeInformationITVATServices', query: query});
                window.open(routeData.href, '_blank')
                return
            }

            this.object={
                hdtb_id:this.rowSelected.hdtb_id,
                ma_tb:this.rowSelected.ma_tb,
                loaitb_id:this.rowSelected.loaitb_id,
                ngay_bd:this.rowSelected.ngay_bd,
                ngay_kt:this.rowSelected.ngay_kt,
                datcoc:this.rowSelected.datcoc=="1"?1:0,
                tocdo:this.rowSelected.tocdo?this.rowSelected.tocdo:'',
            }
            setTimeout(()=>{
                this.$refs.chonDoiTuongDungChinhThucModal.showModal()
            }, 500)  
        },
        ketqua(message){
            // this.$alert(message, 'Thông báo', {
            //     dangerouslyUseHTMLString: true,
            //     confirmButtonText: 'OK',
            //     type: 'info'
            // })
            this.$toast.success(message)
            

            let khuvuc=(this.khuvuc_selected.length==0||this.khuvuc_selected.length==this.khuvuc.length)?'0':this.khuvuc_selected.map(x=>Number(x)).toString()
            let donvi=(this.donvi_lhd_selected.length==0||this.donvi_lhd_selected.length==this.donvi_lhd.length)?'0':this.donvi_lhd_selected.map(x=>Number(x)).toString()
            this.lay_ds_thuebao_dungthu(khuvuc,donvi) 
        },
        async lay_ds_thamso_md(kieu){
            try{
                this.loading(true)
                let response = await MonitorTrialContractAPI.lay_ds_thamso_md(this.axios, kieu)
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
        async lay_ds_khuvuc(){
            try{
                this.loading(true)
                let response = await MonitorTrialContractAPI.lay_ds_khuvuc(this.axios)
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
        async lay_ds_donvi_lhd(){
            try{
                this.loading(true)
                let response = await MonitorTrialContractAPI.lay_ds_donvi_lhd(this.axios)
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
        async lay_ds_danhba_adsl(thuebao_id){
            try{
                this.loading(true)
                let response = await MonitorTrialContractAPI.lay_ds_danhba_adsl(this.axios, thuebao_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async lay_ds_thuebao_dungthu(khuvucId, donviId){
            try{
                this.loading(true)
                let response = await MonitorTrialContractAPI.lay_ds_thuebao_dungthu(this.axios, khuvucId, donviId)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    //moment
                    this.dataSources=response.data.data
                    //bỏ format api db đã format sẳn
                    // this.dataSources=response.data.data.map((x, index)=>Object.assign(x,{
                    //     id:index+1,
                    //     ngay_bd:x.ngay_bd?moment(x.ngay_bd, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):null,
                    //     ngay_tb:x.ngay_tb?moment(x.ngay_tb, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):null,
                    //     ngay_kt:x.ngay_kt?moment(x.ngay_kt, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):null,
                    // }))
                }else{
                    this.dataSources=[]
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Không tải được danh sách thuê bao dùng thử')
                }
            }
        },
        async huy_hopdong_dungthu(data){
            try{
                this.loading(true)
                let response=await MonitorTrialContractAPI.huy_hopdong_dungthu(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    if(response.data.data=='ok'){
                        return true
                    }else{
                        this.$toast.error(response.data.data)
                        return false
                    }
                }else {
                    if(response && response.data && response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã có lỗi xảy ra')
                    }
                    return false
                    
                }
            }catch(error){
                this.loading(false)
                if(error.data&&error.data.message){
                    this.$toast.error(error.data.message)
                }else if(error.response&&error.response.data&&error.response.data.message){
                    this.$toast.error(error.response.data.message)
                }else{
                    this.$toast.error('Đã có lỗi xảy ra')
                }
                return false
            }
        },
        async sp_ktra_dk_port(data){
            try{
                this.loading(true)
                let response = await MonitorTrialContractAPI.sp_ktra_dk_port(this.axios, data)
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
        async fn_capnhat_trangthai_port(data){
            try{
                this.loading(true)
                await MonitorTrialContractAPI.fn_capnhat_trangthai_port(this.axios, data)
                this.loading(false)
            }catch(e){
                this.loading(false)
            }
        },
        async fn_capnhat_trangthai_portid(data){
            try{
                this.loading(true)
                await MonitorTrialContractAPI.fn_capnhat_trangthai_portid(this.axios, data)
                this.loading(false)
            }catch(e){
                this.loading(false)
            }
        },
        async fn_capnhat_ds_matb(data){
            try{
                this.loading(true)
                await MonitorTrialContractAPI.fn_capnhat_ds_matb(this.axios, data)
                this.loading(false)
            }catch(e){
                this.loading(false)
            }
        },
        async Capnhat_port_vatly(data){
            try{
                this.loading(true)
                await MonitorTrialContractAPI.Capnhat_port_vatly(this.axios, data)
                this.loading(false)
            }catch(e){
                this.loading(false)
            }
        },
        async kiemtra_datcoc_tb_dungthu(thuebao_id){
            try{
                this.loading(true)
                let response = await MonitorTrialContractAPI.kiemtra_datcoc_tb_dungthu(this.axios, thuebao_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return ''
                }
            }catch(e){
                this.loading(false)
                return ''
            }
        },
        async initDuLieu(){
            //Khởi tạo tham số
            //lay_ds_thamso_md
            let ds=await this.lay_ds_thamso_md(0)
            let det=ds.find(x=>x.ma_ts=='BATBUOC_NHAP_LYDOHUY')
            if(det){
                this.BATBUOC_NHAP_LYDOHUY=det.ten_ts?Number(det.ten_ts):0
            }
            console.log('BATBUOC_NHAP_LYDOHUY', this.BATBUOC_NHAP_LYDOHUY)
            //
            this.khuvuc=await this.lay_ds_khuvuc()
            this.khuvuc_selected=this.khuvuc.map(x=>x.ID.toString())
            this.donvi_lhd=await this.lay_ds_donvi_lhd()
            this.donvi_lhd_selected=this.donvi_lhd.map(x=>x.ID.toString())
        }

    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        },1000) 
    },
    watch:{
        rowSelected(val){
            if(val!=null){
                this.actions[this.actions.findIndex(x=>x.id=='chuyenchinhthuc')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huydungthu')].active=true
            }else{
                this.actions[this.actions.findIndex(x=>x.id=='chuyenchinhthuc')].active=false
                this.actions[this.actions.findIndex(x=>x.id=='huydungthu')].active=false
            }
        },
        dataSources(val){
            if(val.length>0){
                this.actions[this.actions.findIndex(x=>x.id=='xuat_excel')].active=true
            }else{
                this.actions[this.actions.findIndex(x=>x.id=='xuat_excel')].active=false
            }
        }

    }
}
</script>