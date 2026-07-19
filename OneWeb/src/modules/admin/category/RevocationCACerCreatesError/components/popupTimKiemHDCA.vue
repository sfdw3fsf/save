<template>
    <b-modal 
        :id="modalId"
        size="xl"
        no-close-on-backdrop
        hide-footer 
        hide-header 
        hide-header-close 
        body-class="modal-body p-0"
        @shown="getData">
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title"><span class="icon one-notepad"></span> Tìm kiếm HĐ CA</div>
                <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide(modalId)">
                </div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="#" @click.prevent="chapNhan">
                            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp nhận
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="table-content">
                    <DataGrid2 v-bind:columns="dsHopDong.header" v-bind:dataSource="dsHopDong.list"
                        :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                        ref="gridHopDong" :enabledSelectFirstRow="true"
                        dataKeyField="ma_tb" @selectedRowChanged="selectedRowChanged">
                    </DataGrid2>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<script>
import DataGrid2 from '@/components/Controls/DataGrid2'
import API from './../API.js'
export default {
    name: "ModalTimKiemHDCA",
    components: { DataGrid2 },
    props: ["modalId", "loaiHdId"],
    data() {
        return {
            dsHopDong: {
                list: [],
                header: [
                    {fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true}, 
                    {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true}, 
                    {fieldName: 'tthd', headerText: 'Trạng thái', allowFiltering: true}, 
                    {fieldName: 'diachi_tb', headerText: 'Địa chỉ', allowFiltering: true}, 
                ],
                value: {}
            }
        }
    },
    methods: {
        async getData(){
            this.loading(true)
            try{
                await API.lay_ds_hd_vnptca(this.axios, {
                    "vloaihd_id": Number(this.loaiHdId),
                    "vnguoi_cn": this.$root.token.getUserName()
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
                        this.dsHopDong.list = response.data.data                        
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
                this.loading(false)
            }
        },
        chapNhan(){
            if(this.dsHopDong.list.length > 0){
                this.$parent.hdtbIdCha = this.dsHopDong.value.hdtb_id
                this.$parent.frm.maTbCu = this.dsHopDong.value.ma_tb
                this.$parent.hienThiThongTinHDTBCu()
                this.$bvModal.hide(this.modalId)
            }
            else{
                this.$toast.error('Không có đối tượng để chọn')
            }
        },
        selectedRowChanged(obj){
            this.dsHopDong.value = {...obj}
        }
    }
}
</script>