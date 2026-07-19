<template>
  <b-modal
    ref="popupLichSuBaoHong"
    size="lg"
    hide-header
    hide-header-close
    no-close-on-backdrop
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title"><span class="icon one-notepad"></span>Lịch sử báo hỏng</div>
            <div class="close -ap icon-close" @click="hideModal">
            </div>
        </div>
        <div class="popup-body">
            <div class="box-form">
                <KDataGrid
                    :columns="columns"
                    :dataSource="dataSources"
                    :enable-paging-server="false"
                    :allowPaging="false"
                    :showFilter="true"
                    :showColumnCheckbox="false"
                    :enabledSelectFirstRow="true"/>
            </div>
        </div>
    </div>
  </b-modal>
</template>
<script>
import KDataGrid from '@/components/kylq_components/KDataGrid'
export default {
    name:'LichSuBaoHongModal',
    props:{
        thuebao_id:{
            type:Number,
            default:0
            //default:7209491
        },
    },
    components:{
        KDataGrid
    },
    data(){
        return {
            columns:[
                {
                    fieldName: "LOAI_PHIEU",
                    headerText: "Loại phiếu",
                    allowFiltering: true,
                    width:100,
                },
                {
                    fieldName: "MA_TB",
                    headerText: "Số máy/Acc",
                    allowFiltering: true,
                    width:100,
                },
                {
                    fieldName: "TRANGTHAI_BH",
                    headerText: "Trạng thái",
                    allowFiltering: true,
                    width:100,
                },
                {
                    fieldName: "NGAY_BH",
                    headerText: "Ngày báo hỏng",
                    allowFiltering: true,
                    width:100,
                },
                {
                    fieldName: "NGAY_HT",
                    headerText: "Ngày nghiệm thu",
                    allowFiltering: true,
                    width:100,
                },
                {
                    fieldName: "NGUOI_BH",
                    headerText: "Người báo hỏng",
                    allowFiltering: true,
                    width:100,
                },
                {
                    fieldName: "DIENTHOAI_LH",
                    headerText: "Số ĐT",
                    allowFiltering: true,
                    width:100,
                },
                {
                    fieldName: "DIENTHOAI_BH",
                    headerText: "Điện thoại BH",
                    allowFiltering: true,
                    width:100,
                },
                {
                    fieldName: "GHICHU_HONG",
                    headerText: "Ghi chú BH",
                    allowFiltering: true,
                    width:100,
                },
                {
                    fieldName: "TINHTRANG",
                    headerText: "Tình trạng",
                    allowFiltering: true,
                    width:100,
                },
                {
                    fieldName: "TEN_PLKH",
                    headerText: "Phân loại KH",
                    allowFiltering: true,
                    width:100,
                },
                {
                    fieldName: "CHITIEU_TG",
                    headerText: "Chỉ tiêu TG",
                    allowFiltering: true,
                    width:100,
                },
                {
                    fieldName: "KHOAPHIEU",
                    headerText: "Nghiệm thu",
                    allowFiltering: true,
                    width:100,
                },
                {
                    fieldName: "GHICHU_HONG",
                    headerText: "Ghi chú hỏng",
                    allowFiltering: true,
                    width:100,
                },
                {
                    fieldName: "GHICHU_XL",
                    headerText: "Ghi chú XL",
                    allowFiltering: true,
                    width:100,
                },
                {
                    fieldName: "NGUOI_CN",
                    headerText: "Người tạo",
                    allowFiltering: true,
                    width:100,
                },
                {
                    fieldName: "NHANVIEN_KT",
                    headerText: "Nhân viên KT",
                    allowFiltering: true,
                    width:100,
                }
            ],
            dataSources:[]
        }
    },
    methods:{
        showModal() {
            this.$refs["popupLichSuBaoHong"].show()
        },
        hideModal() {
            this.$refs["popupLichSuBaoHong"].hide()
        },
        handleShowModal(){
            this.dataSources=[]
            setTimeout(()=>{
                this.initDuLieu()
            }, 500)
        },
        async initDuLieu(){
            let dt=await this.lay_lichsu_baohong_thuebao(this.thuebao_id)
            //uppercase key json
            for(var i = 0; i < dt.length; i++){ 
                for (var key in dt[i]) {
                    if(key.toUpperCase() !== key){
                        dt[i][key.toUpperCase()] = dt[i][key];
                        delete dt[i][key];
                    }
                }
            }
            this.dataSources=dt

        },
        async lay_lichsu_baohong_thuebao(thuebao_id){
            try{
                this.loading(true)
                let response = await this.axios.get('/web-ccdv/tien_trinh_bao_hong/Lay_lichsu_baohong_thuebao/'+thuebao_id)
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
    },

}
</script>