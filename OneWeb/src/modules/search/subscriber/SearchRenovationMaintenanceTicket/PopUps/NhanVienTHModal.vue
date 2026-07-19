<template>
    <b-modal
        ref="popupNhanVienTH"
        size="xl"
        hide-header
        hide-header-close
        hide-footer
        body-class="p-0"
        no-close-on-backdrop
        @show="handleShowModal"
    >
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                <span class="icon one-notepad"></span> Danh sách nhân viên thực hiện
                </div>
                <div class="close -ap icon-close" @click="hideModal"></div>
            </div>
            <div class="popup-body">
                <div class="legend-title">Danh sách nhân viên đã giao nhiệm vụ</div>
                <DataGrid 
                    :columns="columns" 
                    :dataSource="dataSources"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="true"
                />
            </div>
        </div>
    </b-modal>
</template>
<script>
//Link khảo sát
//https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1240614515&range=E80
export default {
    name:'NhanVienTHModal',
    props:{
        phieu_id:{
            type:[Number, String],
            default:0
        },
        phieu_bh_id:{
            type:[Number, String],
            default:0
        },
        phieu_kn_id:{
            type:[Number, String],
            default:0
        },
        baohong_id:{
            type:[Number, String],
            default:0
        },
        hdtb_id:{
            type:[Number, String],
            default:0
        },
        chitietcc_id:{
            type:[Number, String],
            default:0
        },
        giaophieu_vt_id:{
            type:[Number, String],
            default:0
        }
    },
    data(){
        return {
            columns:[
                {
                  fieldName: 'ngaygiao',
                  headerText: 'Ngày giao',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'nv_giao',
                  headerText: 'Người giao',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'ten_dv',
                  headerText: 'Đơn vị',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'ma_nv',
                  headerText: 'Mã NV TH',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'ten_nv',
                  headerText: 'Người thực hiện',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'so_dt',
                  headerText: 'Điện thoại',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'nhiemvu',
                  headerText: 'Nhiệm vụ',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'kqxl_id',
                  headerText: 'Kết quả XL',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'trangthai',
                  headerText: 'Trạng thái',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'ghichu',
                  headerText: 'Ghi chú',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
            ],
            dataSources:[]
        }
    },
    methods:{
        handleShowModal(){
            this.dataSources=[]
            setTimeout(()=>{
                this.initDuLieu()
            }, 500)
        },
        showModal() {
            this.$refs["popupNhanVienTH"].show()
        },
        hideModal() {
            this.$refs["popupNhanVienTH"].hide()
        },
        async initDuLieu(){
            this.dataSources=await this.sp_lay_ds_nhanvien_thuchien({
                phieu_id:this.phieu_id,
                phieu_bh_id:this.phieu_bh_id,
                phieu_kn_id:this.phieu_kn_id,
                baohong_id:this.baohong_id,
                hdtb_id:this.hdtb_id,
                chitietcc_id:this.chitietcc_id,
                giaophieu_vt_id:this.giaophieu_vt_id,
                nhanvien_id:this.$root.token.getNhanVienID()
            })
            // this.dataSources=await this.sp_lay_ds_nhanvien_thuchien({
            //     phieu_id:this.phieu_id,
            //     phieu_bh_id:this.phieu_bh_id,
            //     phieu_kn_id:this.phieu_kn_id,
            //     baohong_id:this.baohong_id,
            //     hdtb_id:4544445,
            //     chitietcc_id:0,
            //     giaophieu_vt_id:this.giaophieu_vt_id,
            //     nhanvien_id:0
            // })
        },
        async sp_lay_ds_nhanvien_thuchien(data){
            try{
                this.loading(true)
                let response=await this.axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_ds_nhanvien_thuchien', data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                console.log(e)
                this.loading(false)
                return []
            }
        },
    }
}
</script>