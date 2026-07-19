<template>
    <b-modal 
        :id="modalId"
        size="xl"
        no-close-on-backdrop
        hide-footer 
        hide-header 
        hide-header-close 
        body-class="modal-body p-0"
        @hidden="onModalHidden"
        @shown="getData">
        <div class="modal-content popup-box">
            <ExportDataModal :data="dsXuatDl" ref="popupExportData"></ExportDataModal>
            <div class="popup-header">
                <div class="title"><span class="icon one-notepad"></span> Tổng hợp tình hình đồng bộ dữ liệu Vinaphone về tỉnh</div>
                <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide(modalId)">
                </div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="#" @click="chiTiet">
                            <span class="icon nc-icon-glyph ui-1_eye-17"></span>Chi tiết
                        </a>
                    </li>
                    <li>
                        <a href="#" @click.prevent="thoat">
                            <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Thoát
                        </a>
                    </li> 
                </ul>
            </div>
            <div class="popup-body">
                <div class="nav tabs tab-over">
                    <a href="#tab1" data-toggle="tab" @click="tab1Click" class="active">Dữ liệu thuê bao</a>
                    <a href="#tab2" data-toggle="tab" @click="tab2Click">Dữ liệu thanh toán</a>
                </div>
                <div class="tab-content">
                    <div id="tab1" class="tab-pane active">
                        <div class="table-content">
                            <DataGrid2 v-bind:columns="dsThueBao.header" v-bind:dataSource="dsThueBao.list"
                                :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                                ref="gridThueBao" :enabledSelectFirstRow="true">
                            </DataGrid2>
                        </div>
                    </div>
                    <div id="tab2" class="tab-pane">
                        <div class="table-content">
                            <DataGrid2 v-bind:columns="dsThanhToan.header" v-bind:dataSource="dsThanhToan.list"
                                :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                                ref="gridThanhToan" :enabledSelectFirstRow="true">
                            </DataGrid2>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<script>
import DataGrid2 from '@/components/Controls/DataGrid2'
import API from './../API.js'
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal'
export default {
    name: "ModalTongHop",
    components: { DataGrid2, ExportDataModal },
    props: ["modalId", "time"],
    data() {
        return {
            loaiDl: 1,
            kieu: 0,
            dsXuatDl: [],
            dsThueBao: {
                list: [],
                header: [
                    {fieldName: 'STT', headerText: 'Stt', allowFiltering: true, width: 50}, 
                    {fieldName: 'TEN_DV', headerText: 'Đơn vị', allowFiltering: true}, 
                    {fieldName: 'DENNGAY', headerText: 'Đến ngày', allowFiltering: true}, 
                    {fieldName: 'SL_TON', headerText: 'Sl tồn', allowFiltering: true}, 
                ]
            },
            dsThanhToan: {
                list: [],
                header: [
                    {fieldName: 'STT', headerText: 'Stt', allowFiltering: true, width: 50}, 
                    {fieldName: 'TEN_DV', headerText: 'Đơn vị', allowFiltering: true}, 
                    {fieldName: 'DENNGAY', headerText: 'Đến ngày', allowFiltering: true}, 
                    {fieldName: 'SL_TON', headerText: 'Sl tồn', allowFiltering: true}, 
                ]
            }
        }
    },
    watch: {
        async loaiDl(){
            await this.HT_DanhSach()
        }
    },
    methods: {
        onModalHidden(){
            // alert('dong modal')
        },
        async getData(){
            await this.HT_DanhSach()
        },
        async HT_DanhSach(vloai_dl, vkieu, time_vnp){
            this.loading(true)
            try{
                //vloai_dl    in number,   -- 1<->Du lieu thue bao; 2<-> Du lieu thanh toan
                //vkieu       in number,   -- 0<->Lay tong hop; 1<->Lay chi tiet
                await API.sp_ht_ds_th_dongbo_vnp(this.axios, {
                    "time": this.time,
                    "loai_dl": this.loaiDl,
                    "kieu": this.kieu
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
                        // TODO chua co dl DB - Cuong noi form nay co che cu k dung, de chinh sau
                        // console.log(response.data.data)
                        let data = response.data.data

                        if(this.kieu == 0){
                            if(this.loaiDl == 1)
                                this.dsThueBao.list = data
                            else
                                this.dsThanhToan.list = data
                        }
                        else{
                            this.dsXuatDl = data
                            this.$refs.popupExportData.showModal()
                        }                        
                    }
                    else{
                        if(this.kieu == 0){
                            his.dsThueBao.list = []
                            his.dsThanhToan.list = []
                        }
                        else
                            this.$toast.error('Không có dữ liệu. Bạn hãy kiểm tra lại điều kiện lấy báo cáo !')
                    }
                })
            } catch(e){
                this.$toast.error(e.data.message_detail)
                this.loading(false)
            }
        },
        async chiTiet(){
            this.kieu = 1
            await this.HT_DanhSach()
        },
        thoat(){
            this.$bvModal.hide(this.modalId)
        },
        tab1Click(){
            this.loaiDl = 1
        },
        tab2Click(){
            this.loaiDl = 2
        }
    }
}
</script>