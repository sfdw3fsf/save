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
                <div class="title"><span class="icon one-notepad"></span> Chuẩn hóa tuyến thu, mã nhân viên thu cước</div>
                <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide(modalId)">
                </div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="#" @click.prevent="capNhat">
                            <span class="icon one-reload1"></span>Cập nhật (F5)
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
                    <div class="legend-title">Thông tin thanh toán</div>
                    <div class="row">
                        <div class="col-sm-4 col-12">
                            <div class="info-row">
                                <div class="key w90">Mã TT</div>
                                <div class="value">
                                    <input v-model="maKh" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w90">Mã NVTC (*)</div>
                                <div class="value">
                                    <PopupTimKiemNhanVienThuCuoc :input="arrDiaChiTt" :dvql="donViId" target="body" @acceptChange="getNvTCProcess($event)" ref="dlgTimKiemNVTC"></PopupTimKiemNhanVienThuCuoc>
                                    <div class="input-more-button">
                                        <button class="btn" @click.prevent="timKiemThuCuoc">
                                            <span class="-ap icon-more_horiz"></span>
                                        </button>
                                        <div class="row">
                                            <div class="col-sm-6 col-12">
                                                <input v-model.trim="maNvtc" type="text" class="form-control highlight">
                                            </div>
                                            <div class="col-sm-6 col-12">
                                                <input v-model.trim="maTuyenThu" type="text" class="form-control highlight">
                                            </div>
                                        </div>
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-8 col-12">
                            <div class="info-row">
                                <div class="key w100">Tên thanh toán</div>
                                <div class="value">
                                    <input v-model="tenTt" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w100">Địa chỉ TT</div>
                                <div class="value">
                                    <input v-model="diaChiTt" type="text" class="form-control">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="box-form">
                    <div class="legend-title">
                        <div class="pull-left">
                            Danh sách thanh toán
                        </div>
                        <div class="pull-right red">
                            Tổng số: {{tongSo}}
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="table-content">
                        <DataGrid2 v-bind:columns="dsThanhToan.header" v-bind:dataSource="dsThanhToan.list"
                            :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                            ref="grid" :enabledSelectFirstRow="true" @selectedRowChanged="rowChanged">
                        </DataGrid2>
                    </div>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<script>
import DataGrid2 from '@/components/Controls/DataGrid2'
import PopupTimKiemNhanVienThuCuoc from './PopupTimKiemNhanVienThuCuoc'
import API from './../API.js'
export default {
    name: "ModalChuanHoaTuyenThu",
    components: { DataGrid2, PopupTimKiemNhanVienThuCuoc },
    props: ["modalId", "donViId", "tuNgay", "denNgay", "danhSach", "chuyenForm"],
    data() {
        return {
            maKh: "",
            maNvtc: "",
            maTuyenThu: "",
            tenTt: "",
            diaChiTt: "",
            arrDiaChiTt: [],
            dsThanhToan: {
                list: [],
                header: [ 
                    {fieldName: 'MA_KH', headerText: 'Mã TT', allowFiltering: true}, 
                    {fieldName: 'TEN_TT', headerText: 'Tên thanh toán', allowFiltering: true}, 
                    {fieldName: 'DIACHI_TT', headerText: 'Địa chỉ thanh toán', allowFiltering: true}, 
                    {fieldName: 'SODAIDIEN', headerText: 'Mã đại diện', allowFiltering: true}, 
                    {fieldName: 'MA_NV', headerText: 'Mã NVTC', allowFiltering: true}, 
                    {fieldName: 'MA_T', headerText: 'Mã tuyến thu', allowFiltering: true}, 
                ]
            }
        }
    },
    computed: {
        tongSo(){
            return this.dsThanhToan.list.length
        }
    },
    methods: {
        onModalHidden(){
            if(this.$parent.daDongBo)
                this.$parent.hienThiDanhSachDaDongBo()
            else
                this.$parent.hienThiDanhSach()
        },
        frmLoad(){
            if(this.chuyenForm)
                this.dsThanhToan.list = this.danhSach
            else
                this.layDl()
        },
        async layDl(){
            this.loading(true)
            try{
                await API.lay_ds_chuanhoa_tuyenthu_vnp(this.axios, {
                    "donvi_id_neo": this.donViId
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
                        this.dsThanhToan.list = response.data.data
                    }
                    else {
                        this.dsThanhToan.list = []
                    }
                })
            } catch(e){
                this.$toast.error(e.data.message_detail)
                this.loading(false)
            }
        },
        async capNhat(){
            const valid = this.ktTruocCapNhat()
            if(valid) {
                this.loading(true)
                try{
                    await API.capnhat_danhsach_thuebao(this.axios, {
                        "p_ma_nv": this.maNvtc,
                        "p_ma_tuyen": this.maTuyenThu,
                        "p_ma_kh": this.maKh
                    }).then(async response => {
                        this.loading(false)
                        if (response && response.data && response.data.error_code === 'BSS-00000000') {
                            this.$toast.success('Cập nhật dữ liệu thành công!')
                            await this.layDl()
                            if(this.dsThanhToan.list.length == 0)
                                this.$bvModal.hide(this.modalId)
                        }
                    })
                } catch(e){
                    this.$toast.error(e.data.message_detail)
                    this.loading(false)
                }
            }
        },
        ktTruocCapNhat(){
            if(this.dsThanhToan.list.length > 0){
                if(this.maNvtc != "" || this.maTuyenThu != ""){
                    this.$toast.error('Bạn chưa chọn nhân viên/mã tuyến thu !')
                    return false
                }
                if(this.$refs.grid.getCurrentSelectedRow().MA_NV.trim() != this.maNvtc){
                    this.$toast.error('Bạn không được phép thay đổi nhân viên thu cước!')
                    return false
                }
                return true
            }
            return false
        },
        chuanHoa(){
            this.$bvModal.msgBoxConfirm(
                `Bạn có chắc muốn chuẩn hoá tự động tuyến thu không ?`, {
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
                    await this.layDl()
                    if(this.dsThanhToan.list.length == 0)
                        this.$bvModal.hide(this.modalId)
                }
            });
        },
        async guiYcChuanHoa(vBuocTh = '0'){
            if(this.dsThanhToan.list.length === 0){
                this.$toast.error('Không có dữ liệu để chuẩn hóa!')
                return
            }
                
            this.loading(true)
            try {
                let ds_para = {
                    BUOC_TH: vBuocTh,
                    TUNGAY: this.tuNgay,
                    DENNGAY: this.denNgay,
                    DONVI_ID_NEO: this.donViId
                }
                return await API.fn_chuanhoa_tuyenthu_dongbo_vnp(this.axios, {
                    "ds_para": JSON.stringify(ds_para)
                }).then(async response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        let res = JSON.parse(response.data.data)
                        if (res.ERROR_CODE === -2){
                            return this.$bvModal.msgBoxConfirm(
                                `${res.MESSAGE}`, {
                                title: 'Thông báo',
                                size: 'md',
                                okTitle: 'Yes',
                                cancelTitle: 'No',
                            }).then(async v => {
                                if(!v) {
                                return;
                                }                
                                return this.guiYcChuanHoa('THUCHIEN_CHUANHOA')
                            });
                        }
                        else if(res.ERROR_CODE === 1) {
                            //thanh cong
                            return true
                        }
                        else if(res.ERROR_CODE === 0 || res.ERROR_CODE === -1)
                            throw 'Có lỗi trong quá trình chuẩn hoá'
                    }
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
        timKiemThuCuoc(){
            if(this.dsThanhToan.list.length > 0)
                this.$refs.dlgTimKiemNVTC.openDialog()
            else
                this.$toast.error('Danh sách trống, bạn không thể chọn nhân viên thu cước!')
        },
        rowChanged: async function(obj) {
            if(!obj) { 
                return; 
            }
            this.maKh = obj.MA_KH
            this.maNvtc = obj.MA_NV
            this.maTuyenThu = obj.MA_T
            this.tenTt = obj.TEN_TT
            this.diaChiTt = obj.DIACHI_TT

            this.arrDiaChiTt['QUAN_ID'] = obj.QUANTT_ID
            this.arrDiaChiTt['PHUONG_ID'] = obj.PHUONGTT_ID
            this.arrDiaChiTt['PHO_ID'] = obj.PHOTT_ID
        },
        getNvTCProcess(value){
            this.maNvtc = value.maNVTC
            this.maTuyenThu = value.rowData.MA_TUYENTHU
        }
    }
}
</script>