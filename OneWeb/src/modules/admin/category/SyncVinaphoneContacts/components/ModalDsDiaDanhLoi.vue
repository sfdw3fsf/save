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
        @shown="frmLoad">
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title"><span class="icon one-notepad"></span> Danh sách địa danh lỗi</div>
                <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide(modalId)">
                </div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="#" @click.prevent="tongHop">
                            <span class="icon one-file-pin"></span>Tổng hợp
                        </a>
                    </li>
                    <li>
                        <a href="#" @click.prevent="chiTiet">
                            <span class="icon nc-icon-glyph ui-1_eye-19"></span> Chi tiết
                        </a>
                    </li>
                    <li>
                        <a href="#" @click.prevent="chuanHoa">
                            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chuẩn hóa
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
                <div class="box-form">
                    <div class="table-content">
                        <DataGrid2 v-bind:columns="dsDiaDanh.header" v-bind:dataSource="dsDiaDanh.list"
                            :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true"
                            ref="grid" :enabledSelectFirstRow="false" dataKeyField="stt">
                        </DataGrid2>
                    </div>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<script>
import DataGrid2 from '@/components/Controls/DataGrid2'
import {Workbook} from "@syncfusion/ej2-excel-export/index"
import API from './../API.js'
export default {
    name: "ModalDsDiaDanhLoi",
    components: { DataGrid2 },
    props: ["modalId", "donViId", "tuNgay", "denNgay", "danhSach", "chuyenForm"],
    data() {
        return {
            dsDiaDanh: {
                list: [],
                header: [
                    {fieldName: 'STT', headerText: 'STT', allowFiltering: true}, 
                    {fieldName: 'TEN_QUAN', headerText: 'Quận/Huyện', allowFiltering: true}, 
                    {fieldName: 'TEN_PHUONG', headerText: 'Phường/Xã', allowFiltering: true}, 
                    {fieldName: 'TEN_PHO', headerText: 'Phố/Thôn', allowFiltering: true}, 
                    {fieldName: 'QUAN_ID', headerText: 'Quan_ID VNP', allowFiltering: true}, 
                    {fieldName: 'PHUONG_ID', headerText: 'Phuong_ID VNP', allowFiltering: true}, 
                    {fieldName: 'PHO_ID', headerText: 'Pho_ID VNP', allowFiltering: true}, 
                ],
                value: {}
            },
            dsChiTiet: []
        }
    },
    methods: {
        onModalHidden(){
            if(this.$parent.daDongBo)
                this.$parent.hienThiDanhSachDaDongBo()
            else
                this.$parent.hienThiDanhSach()
        },
        async frmLoad(){
            if(this.chuyenForm)
                this.dsDiaDanh.list = this.danhSach
            else
                this.dsDiaDanh.list = await this.layDuLieu(1)
        },
        tongHop(){
            if (!this.dsDiaDanh.list || this.dsDiaDanh.list.length == 0) {
                this.$toast.error('Không có dữ liệu để xuất Excel !')
                return
            }
            this.xuatExcelTongHop()
        },
        async chiTiet(){
            try{
                this.dsChiTiet = await this.layDuLieu(0)
                if (!this.dsChiTiet || this.dsChiTiet.length == 0) {
                    this.$toast.error('Không có dữ liệu để xuất Excel !')
                    return
                }
                this.xuatExcelChiTiet()
            } catch(e){
                this.$toast.error('Có lỗi ' + e)
            }
        },
        async layDuLieu(vloai){ // 1 tong hop, 0 chi tiet
            this.loading(true)
            try{
                return await API.kiemtra_dl_laytu_vina(this.axios, {
                    "vdonvi_id": this.donViId,
                    "vkieu": vloai,
                    "vtungay": this.tuNgay,
                    "vdenngay": this.denNgay
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        return response.data.data
                    }
                    else //BSS-00000204 -> no conten
                        return []
                })
            } catch(e){
                this.loading(false)
                throw e.data.message_detail
            }
        },
        chuanHoa(){
            this.$bvModal.msgBoxConfirm(
                `Bạn có chắc muốn chuẩn hoá tự động địa danh không ?`, {
                title: 'Thông báo',
                size: 'md',
                okTitle: 'Yes',
                cancelTitle: 'No',
            }).then(async v => {
                if(!v) {
                    return;
                }
                const thanhCong = await this.guiYcChuanHoa()
                if(thanhCong){
                    this.dsDiaDanh.list = await this.layDuLieu(1)
                    if(this.dsDiaDanh.list.length === 0)
                        this.$bvModal.hide(this.modalId)
                }
            });
        },
        async guiYcChuanHoa(){
            if(this.dsDiaDanh.list.length === 0){
                this.$toast.error('Không có dữ liệu để chuẩn hóa!')
                return
            }
                
            this.loading(true)
            try {
                return await API.chuanhoa_dl_diadanh_vina(this.axios, {
                    "vdonvi_id": this.donViId,
                    "vtungay": this.tuNgay,
                    "vdenngay": this.denNgay
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        return true
                    }
                    else
                        this.$toast.error(response.data.message_detail)
                })
            } catch(e){
                this.$toast.error(e.data.message_detail)
                this.loading(false)
            }
            return
        },
        thoat(){
            this.$bvModal.hide(this.modalId)
        },
        xuatExcelTongHop() {
            let columns = [
                { index: 1, width: 130 },
                { index: 2, width: 130 },
                { index: 3, width: 130 },
                { index: 4, width: 130 },
                { index: 5, width: 130 },
                { index: 6, width: 130 },
                { index: 7, width: 130 },
            ]

            let propName = {
                STT: 'STT',
                TEN_QUAN: 'TEN_QUAN',
                TEN_PHUONG: 'TEN_PHUONG', 
                TEN_PHO: 'TEN_PHO', 
                QUAN_ID: 'QUAN_ID', 
                PHUONG_ID: 'PHUONG_ID',
                PHO_ID: 'PHO_ID'
            }

            let rows = []

            let headerRow = {
                index: 1,
                cells: []
            }

            Object.keys(propName).forEach((item, index) => {
                headerRow.cells.push({
                    index: index + 1,
                    value: propName[item],
                    style: {
                        borders: {
                            color: '#00000000',
                            lineStyle: 'thin'
                        }
                    }
                })
            })

            rows.push(headerRow)

            this.dsDiaDanh.list.forEach((item, index) => {

                let row = {
                    index: index + 2,
                    cells: []
                }

                Object.keys(propName).forEach((element, index) => {
                    row.cells.push({
                        index: index + 1,
                        value: item[element],
                        style: {
                            borders: {
                                color: '#00000000',
                                lineStyle: 'thin'
                            }
                        }
                    })
                })
                rows.push(row)
            })

            let worksheets = [{ columns, rows }]
            let workbook = new Workbook({worksheets}, 'xlsx')
            workbook.save('TongHop.xlsx')
        },
        xuatExcelChiTiet() {
            let columns = [
                { index: 1, width: 130 },
                { index: 2, width: 130 },
                { index: 3, width: 130 },
                { index: 4, width: 130 },
                { index: 5, width: 130 },
                { index: 6, width: 130 },
                { index: 7, width: 130 },
                { index: 8, width: 130 },
                { index: 9, width: 130 },
                { index: 10, width: 130 },
                { index: 11, width: 130 },
                { index: 12, width: 130 },
            ]

            let propName = {
                ID: 'ID',
                SOMAY: 'SOMAY',
                TEN_KH: 'TEN_KH',
                DIACHI: 'DIACHI',
                TEN_PHUONG: 'TEN_PHUONG', 
                TEN_PHO: 'TEN_PHO', 
                QUAN_ID: 'QUAN_ID', 
                PHUONG_ID: 'PHUONG_ID',
                PHO_ID: 'PHO_ID',
                FLAGS: 'FLAGS',
                DATE_SYNC: 'DATE_SYNC',
                LOAI_DL: 'LOAI_DL'
            }

            let rows = []

            let headerRow = {
                index: 1,
                cells: []
            }

            Object.keys(propName).forEach((item, index) => {
                headerRow.cells.push({
                    index: index + 1,
                    value: propName[item],
                    style: {
                        borders: {
                            color: '#00000000',
                            lineStyle: 'thin'
                        }
                    }
                })
            })

            rows.push(headerRow)

            this.dsChiTiet.forEach((item, index) => {

                let row = {
                    index: index + 2,
                    cells: []
                }

                Object.keys(propName).forEach((element, index) => {
                    row.cells.push({
                        index: index + 1,
                        value: item[element],
                        style: {
                            borders: {
                                color: '#00000000',
                                lineStyle: 'thin'
                            }
                        }
                    })
                })
                rows.push(row)
            })
            
            let worksheets = [{ columns, rows }]
            let workbook = new Workbook({worksheets}, 'xlsx')
            workbook.save('ChiTiet.xlsx')
        }
    }
}
</script>