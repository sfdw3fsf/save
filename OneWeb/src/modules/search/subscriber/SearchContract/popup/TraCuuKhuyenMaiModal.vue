<template>
<b-modal
    centered
    ref="popupTraCuuKM"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
   <div class="modal-content popup-box">
                <div class="popup-header">
                    <div class="title"><span class="icon one-notepad"></span>Danh sách khuyến mại, đặt cọc</div>
                    <div class="close -ap icon-close" @click="hideModal" data-dismiss="modal">
                    </div>
                </div>
                
                <div class="popup-body">
                    <div class="box-form">
                        <div class="table-content">
                            <DataGrid
                            v-bind:dataSource="dataSourcesKM.list"
                            v-bind:columns="dataSourcesKM.cols"
                            ref="dataSourcesKM"
                            :panelDataHeight= "'300'"
                            :showFilter="true"
                            :enablePagingServer="false"               
                            :enabledSelectFirstRow="true"
                            :allowPaging="true"
                            @queryCellInfo = "queryCellInfo"
                        />
                    </div>
                </div>
            </div>
        </div>
  </b-modal>
</template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from "@/components/breadcrumb";
import api from '../api'
import "@/assets/vendor/jquery/split";
import Vue from "vue";
import moment from "moment";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
import {LoaiHopDong, DichVuVienThong,TrangThaiHD,LOAI_DV} from "@/const/enums";
Vue.use(DialogPlugin);

export default {
    props:{
        hdtb_id:{
            type:Number,
            default:0
        },
        //Dịch vụ
        vkieu:{
            type:Number,
            default:0
        },
    },
    data(){
        return {
            dataSourcesKM:{
                list:[],
                cols: [
                {
                    fieldName: "loai_km",
                    headerText: "Loại KM",
                    allowFilter: true,
                    allowSorting:false,
                    
                },
                {
                    fieldName: "ten_chitietkm",
                    headerText: "Chi tiết KM",
                    allowFilter: true,
                    allowSorting:false,
                    
                },
                {
                    fieldName: "ngay_dkdc",
                    headerText: "Ngày ĐK",
                    allowFilter: true,
                    allowSorting:false,
                    
                },
                {
                    fieldName: "thang_bd",
                    headerText: "Tháng BĐ",
                    allowFilter: true,
                    allowSorting:false,
                    
                },
                {
                    fieldName: "thang_kt",
                    headerText: "Tháng KT",
                    allowFilter: true,
                    allowSorting:false,
                    
                },
                {
                    fieldName: "camket",
                    headerText: "Cam kết (tháng)",
                    allowFilter: true,
                    allowSorting:false,
                    
                },
                {
                    fieldName: "thang_bddc",
                    headerText: "Tháng BĐĐC",
                    allowFilter: true,
                    allowSorting:false,
                    
                },
                {
                    fieldName: "thang_ktdc",
                    headerText: "Tháng KTĐC",
                    allowFilter: true,
                    allowSorting:false,
                    
                },
                {
                    fieldName: "datcoc_csd",
                    headerText: "Tiền đặt cọc",
                    type: 'number',
                    format: 'N',
                    allowFilter: true,
                    allowSorting:false,
                    
                },
                {
                    fieldName: "tyle_sd",
                    headerText: "Tỷ lệ SD",
                    allowFilter: true,
                    allowSorting:false,
                    
                },
                {
                    fieldName: "tien_sd",
                    headerText: "Tiền SD",
                    type: 'number',
                    format: 'N',
                    allowFilter: true,
                    allowSorting:false,
                    
                },
                {
                    fieldName: "cuoc_sd",
                    headerText: "Giảm cước SD",
                    allowFilter: true,
                    allowSorting:false,
                    
                },
                 {
                    fieldName: "tyle_tb",
                    headerText: "Tỷ lệ TB",
                    allowFilter: true,
                    allowSorting:false,
                    
                },
                 {
                    fieldName: "tien_tb",
                    headerText: "Tiền TB",
                    allowFilter: true,
                    allowSorting:false,
                    
                },
                 {
                    fieldName: "cuoc_tb",
                    headerText: "Giảm cước TB",
                    allowFilter: true,
                    allowSorting:false,
                    
                },
                 {
                    fieldName: "noidung",
                    headerText: "Nội dung",
                    allowFilter: true,
                    allowSorting:false,
                    
                }, {
                    fieldName: "thang_thoaitra",
                    headerText: "Tháng thoái trả",
                    allowFilter: true,
                    allowSorting:false,
                    
                },
                 {
                    fieldName: "thang_huy",
                    headerText: "Tháng hủy",
                    allowFilter: true,
                    allowSorting:false,
                    
                },
                ]
            }
        }
    },
    methods:{
       async handleShowModal(){
         let response = await api.lay_ds_chitiet_km_theo_tbid(this.axios,{
             "vkieu": this.vkieu,
             "vthuebao_id": this.hdtb_id
         })
         if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data){
             this.dataSourcesKM.list = response.data.data;
         }

        },
        showModal() {
            this.$refs["popupTraCuuKM"].show();
        },
        hideModal() {
            this.$refs["popupTraCuuKM"].hide();
        },
        queryCellInfo: function (args) {
            //     if (args.column.field === "thang_bd"){
            //         var result = args.data.thang_bd;
            //         console.log(result);
            //             var setVal = moment(result,"YYYYMM").format("MM/YYYY").toString();
            //             args.cell.innerText = setVal;
            //     }
            // if (args.column.field === "thang_kt"){
            //     var result = args.data.thang_kt;
            //         var setVal = moment(result,"YYYYMM").format("MM/YYYY");
                   
            //         args.cell.innerText = setVal;
            // }
            // if (args.column.field === "thang_bddc"){
            //     var result = args.data.thang_bddc;
            //     if(result != null){
            //          var setVal = moment(result,"YYYYMM").format("MM/YYYY");       
            //         args.cell.innerText = setVal;
            //     }         
            // }
            // if (args.column.field === "thang_ktdc"){
            //     var result = args.data.thang_ktdc;
            //     if(result != null){
            //         var setVal = moment(result,"YYYYMM").format("MM/YYYY");     
            //         args.cell.innerText = setVal;
            //     }     
            // }
            // if (args.column.field === "thang_thoaitra"){
            //     var result = args.data.thang_thoaitra;
            //     if(result != null){
            //         var setVal = moment(result,"YYYYMM").format("MM/YYYY");
            //         args.cell.innerText = setVal;
            //     }      
            // }
            // if (args.column.field === "thang_huy"){
            //     var result = args.data.thang_huy;
            //     if(result != null){
            //         var setVal = moment(result,"YYYYMM").format("MM/YYYY");
            //         args.cell.innerText = setVal;
            //     }       
            // }
         },
    }
}
</script>