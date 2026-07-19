<template>
    
    <!-- D/S HDDC chua thanh toan -->
     <DataGrid :dataSource="gridTB_HDDC_ChuaThanhToan.dataSources" :allowPaging="true"
        :columns="gridTB_HDDC_ChuaThanhToan.columns" ref="refGridTBHDDCChuaThanhToan" :allowSorting="true"
        :enablePagingServer="false" panelDataHeight="220px" 
        @queryCellInfo="dsTBCTT_QueryCellInfo"
        />
   
 
</template>

<script>
import api from '../../../API';
import apiHelper from '../../../api.helper';
export default {
    name: 'HDChuaThanhToanComponent',
    props: {
       

        khachhang_id: {
            type: Number || String,
            default: 0,
            require: true
        },
    },

    data() {
        return {
            
            
            gridTB_HDDC_ChuaThanhToan: {
                columns: [
                    
                    { fieldName: 'ma_tb', headerText: 'Số máy/Acc', width: '130px' },
                    { fieldName: 'ten_tb', headerText: 'Tên TB', width: '150px' },
                    { fieldName: 'ma_gd', headerText: 'Mã giao dịch', width: '130px' },
                    { fieldName: 'diachi_ld', headerText: 'Địa chỉ ld', width: '250px' },
                    { fieldName: 'ngayhen_tt', headerText: 'Ngày hẹn TT', width: '120px' },
                    { fieldName: 'ngayqua_hen', headerText: 'Số ngày quá hẹn', width: '80px' },
                    { fieldName: '', headerText: 'Chuyển đặt cọc', width: '80px',
                    template: this.buttonRedirectTemplate(this)},
        
                   ],
                dataSources: []
            },
            tab_active: 1,

        }
    },
    methods: {

        buttonRedirectTemplate(parent) {
            return function () {
                return {
                    template: {
                        template: `<div class="text-center"><button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnRedirectClick" disabled="!isActiveBtn" >
                                    <span class="fa fa-angle-right"></span>
                                    </button></div>`,
                        data: function () {
                            return { data: {} }
                        },
                        methods: {
                          
                            btnRedirectClick() {  
                                // console.log("data", this.data);
                                parent.handleHDDCNotPaymentClick(this.data);
                            }

                        },
                        computed:{
                            isActiveBtn(){
                                if (this.data != undefined & this.data.ngayqua_hen >= 3) 
                                    return true;
                                return false;
                            }
                        }
                    }
                }
            }
        },

        dsTBCTT_QueryCellInfo(args){
           const {data} = args;

           console.log(data.ngayqua_hen);
           if(data && data.ngayqua_hen >= 3){ 
            args.cell.style.color = 'red';
            args.cell.style.fontWeight = "bold";
           }
        },
        handleHDDCNotPaymentClick(data){
           const   query = { ma_gd: data.ma_gd }
            const  routeData = this.$router.resolve({
                name: 'Cap-Nhat-Thue-Bao-Dat-Coc',
                query: query,
                path: '/ext-hopdong/Cap-Nhat-Thue-Bao-Dat-Coc'
              })
            window.open(routeData.href, '_blank')
        },
        


        
        async getListDS_HD_Chua_ThanhToan() {

            try {

                this.gridTB_HDDC_ChuaThanhToan.dataSources = [];
                const prams  = this.khachhang_id //9793668 // this.khachhang_id
                let res = await api.getDSHddChuaThanhToan(this.axios, prams);
                this.gridTB_HDDC_ChuaThanhToan.dataSources = apiHelper.getDataFromResponseApiReturnArray(res);
          
            } catch (error) {
                console.log(error);
            }
        },

    },
    watch: {

        async khachhang_id(val) {
            if (val) {
                
                await Promise.all([
         
                    this.getListDS_HD_Chua_ThanhToan(),
                ]).then(() => {
                    this.loading(false)
                    console.log('getListDS_HD_Chua_ThanhToan success')
                }).catch(err => {
                    console.log(err)

                })

            }

        }

    },



}
</script>
<style scoped>

</style>