
<template src='./WaitingListPausedLiquidated.html'></template>


<script>

import api from "./api/index.js";
import state from "./define.js";
import DatePicker from 'vue2-datepicker' 
import 'vue2-datepicker/locale/vi.js' 
import 'vue2-datepicker/index.css' 
import moment from 'moment'
import ChangeService from '@/modules/contract/setup/ChangeService'
import Liquidate from '@/modules/contract/setup/Liquidate'
import XLSX from 'xlsx'
import {
    required,
    minLength
} from 'vuelidate/lib/validators'
import {
  LoaiHopDong
} from "./Enums";
export default {
    components: {
        ChangeService,
        Liquidate,
    },
    data() {
        return {
            dateconfig: {
                altFormat: 'd/m/Y H:i:S',
                altInput: true,
                dateFormat: 'd/m/Y H:i:S',
                allowInput: true,
            },
            dateFormat: "DD/MM/YYYY",
            ...state,
            header: {
                title: "Danh sách đợi tạm dừng/thanh lý",
                list: [{
                        name: "Lập hợp đồng",
                        link: {
                            name: "Ui.cards"
                        }
                    },
                    {
                        name: "Lắp đặt mới",
                        link: {
                            name: "Ui.buttons"
                        },
                    },
                ],
            },            
            nhanvienId: this.$root.token.getNhanVienID(),
            donviId: this.$root.token.getDonViID(),
            dataSelected: {
              
            },
            options: {
             
            },
        };
    },
    mounted() {
        this.init();
    },
    computed: {

    },
    validations: {

    },
    methods: {
        async init() {
            try {              
              this.HienThiDanhSach()
            } catch (e) {
                console.log(e);
            }
        },
        async HienThiDanhSach(){ 
            this.loading(true);
            try {
                await api.lay_ds_cho_td_thanhly(this.axios,
                {
                "vdenngay": moment(this.searchDate.den_ngay,"DD/MM/yyyy HH:mm:ss").format("DD/MM/yyyy"),
                "vdonvi_id": this.$root.token.getDonViID(),
                "vphanvung_id": this.$root.token.getPhanVungID(),
                "vtungay": moment(this.searchDate.ngay_tn,"DD/MM/yyyy HH:mm:ss").format("DD/MM/yyyy")
                }).then(response => {                    
                    if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.length>0){
                        this.gvDanhSach.tableData = response.data.data
                    }
                    this.loading(false);  
                })
            } catch (error) {
                console.log(error)  
                this.loading(false);      
            }
        },
        
        gvDSTB_rowSelected(data){
            console.log(data.data)
            if (!data.data) return
            let item =data.data
            this.btnLabel="Tạm dừng"
            this.loai_hd = item.LOAIHD_ID
            this.thuebao_id=item.MA_TB
            this.dichvuvt_id=item.DICHVUVT_ID

            if (item.LOAIHD_ID==LoaiHopDong.THAY_DOI_DV*1) { 
                this.btnLabel="Tạm dừng"
            }
            else if (item.LOAIHD_ID==LoaiHopDong.CHAMDUT_SD*1) {
                this.btnLabel="Thanh lý"
            }
        },
        btnTamdung_Click(){
            console.log('clicked')
            console.log(this.thuebao_id)
            if (this.loai_hd==0) return
            if (this.loai_hd==LoaiHopDong.THAY_DOI_DV*1) {
                this.$bvModal.show('formthaydoidichvu')
            }
            else if (this.loai_hd==LoaiHopDong.CHAMDUT_SD*1) this.$bvModal.show('formthanhly')
        },
        btnXuatfile_Click () {            
            if (this.gvDanhSach.tableData.length > 0) {
                let data = XLSX.utils.json_to_sheet(this.gvDanhSach.tableData)
                let wb = XLSX.utils.book_new() // make Workbook of Excel
                XLSX.utils.book_append_sheet(wb, data, 'DSTB') // sheetAName is name of Worksheet
                // export Excel file
                XLSX.writeFile(wb, 'DSTB.xls')
            } else this.$root.$toast('Không có dữ liệu!')
        },
    },
    watch: {       
        
    }
};
</script>
<style>
 .modal-content {height:600px !important;} 
 
 .thanhly-content .main-wrapper {position:initial}
</style>
