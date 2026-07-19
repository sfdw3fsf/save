
<template src="./DeclareChannelInfo.html"></template>
<style src="./DeclareChannelInfo.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import Vue from 'vue';
import VueFlatPickr from 'vue-flatpickr-component';
import api from './API'
import moment from "moment";
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import {
    required,
    minLength
} from 'vuelidate/lib/validators'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import SearchAccountModal from './Popups/SearchAccountModal.vue'
//import { LoaiHopDong } from '../DeclareLandline/define';
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
import popupSegment from '@/modules/channel/Segment/popupSegment.vue'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import XemBaoCao from '@/modules/print/XemBaoCao/XemBaoCao'
import GiaoPhieuView from '@/modules/contract/setup/HandoverTicket/giao_phieu_view.vue';
import GiaophieuModal from  './Popups/GiaophieuModal.vue' 
var colThutuPhandoan = function() {
                    return {
                    template: {
                        template: `
                                        <div @dblclick="onDoubleClick"  @click="onNullClick">
                                            <span v-if="!isEditing">{{ data.thutu_pd?data.thutu_pd:0 }}</span>
                                            <input
                                                v-else
                                                type="text"
                                                class="form-controls"              
                                                @input="onChangeProcessed"
                                                style="width: 100px;"
                                                ref="editInput"
                                                :value="data.thutu_pd?data.thutu_pd:0"/>
                                           <button v-if="isEditing" @click="onCheckButtonClick"><i class="fa fa-check-circle text-success"></i></button>    
                                            <span class="name"></span>
                                        </div>
                                    `,
                        data() {
                            return {
                                data: {},
                                isEditing: false,
                                thutu_new: "0"
                            }
                        },
                        mounted(){
                            console.log(this.parent)
                            console.log(this.data)
                        },
                        computed: {
                            parent() {
                                return this.$parent.$parent.$parent
                            }
                        },
                        methods: {
                            onDoubleClick() {
                                this.isEditing = !this.isEditing;
                            },
                            onNullClick() {
                                console.log(111111)
                            // Hiển thị input khi ô null được click
                                this.isEditing = true;
                            },
                            async onChangeProcessed(event) {
                                console.log(3333)
                                console.log(event.target.value)
                                this.thutu_new = event.target.value
                            },
                            async onCheckButtonClick(event) {
                                console.log(222222)
                                console.log(event)
                                console.log(this.$refs.editInput.value)
                                const editedValue = this.$refs.editInput.value;
                                if(editedValue){
                                    let ds = await api.sapXepPhanDoan(this.axios,    {
                                        "phanvung_id": this.parent.tinh_id,
                                        "hdtb_ct_id": this.data.hdtb_ct_id,
                                        "thuebao_ct_id": this.data.thuebao_ct_id,
                                        "thutu_pd":editedValue,
                                    });
                                    this.parent.layDsPhandoan(); 
                                    this.isEditing = !this.isEditing;
                                }else{
                                    this.$toast.warning("Hãy nhập số thứ tự phân đoạn");
                                }
                            },
                        },
                    }
                }
            }
var colThaotác = function() {
                    return {
                    template: {
                        template: `
                                        <div>
                                            <button v-if="parent.anhien_nut" @click="onViewButtonClick"  class="pad3 mar5 lh14 icon -ap one-eye btn-info btn-view-details" title="Xem chi tiết"></button>
                                            <button v-if="data.trang_thai_an == 1&&data.trangthaitb_id == 1 && parent.anhien_nut" @click="onEditButtonClick" class="pad3 mar5 lh14 icon -ap one-edit btn-primary btn-search" title="Cập nhật phân đoạn"></button>
                                            <button v-if="data.trang_thai_an == 1&&data.trangthai_pd_id == 0&&data.trangthaitb_id == 1&& parent.anhien_nut" @click="onDeleteButtonClick" class="pad3 mar5 lh14 icon -ap one-trash btn-danger btn-trash" title="Xóa phân đoạn"></button>
                                            <button v-if="data.trang_thai_an == 1&&data.trangthai_pd_id == 1&&data.trangthaitb_id == 1&& parent.anhien_nut" @click="onCheckButtonClick" class="pad3 mar5 lh14 icon -ap one-check btn-success btn-search rounded-circle" title="Hủy kích hoạt"></button>
                                            <button v-if="data.trang_thai_an == 0&&data.trangthai_pd_id == 1&&data.trangthaitb_id == 1&& parent.anhien_nut" @click="onStatusButtonClick"  class="pad3 mar5 lh14 icon -ap one-check btn-secondary btn-search rounded-circle" title="Kích hoạt"></button>
                                            <span class="name"></span>
                                        </div>
                                    `,
                        data() {
                            return {
                                data: {},
                                isEditing: false,
                                thutu_new: "0"
                            }
                        },
                        mounted(){
                            console.log(this.parent)
                            console.log(this.data)
                        },
                        computed: {
                            parent() {
                                return this.$parent.$parent.$parent
                            }
                        },
                        methods: {
                            onViewButtonClick() {
                                this.parent.gridCommand_Thaotac('viewDetails',this.data);
                            },
                            onEditButtonClick() {
                                this.parent.gridCommand_Thaotac('save',this.data);
                            },
                             onDeleteButtonClick() {
                                this.parent.gridCommand_Thaotac('trash',this.data);
                            },
                            onCheckButtonClick() {
                                this.parent.gridCommand_Thaotac('on',this.data);
                            },
                            onStatusButtonClick() {
                                this.parent.gridCommand_Thaotac('off',this.data);
                            },
                        },
                    }
                }
            }
export default {
    components: {
        breadcrumb,
        VueFlatPickr,
        SearchContractModal,
        ModalChonDiaChi,
        ExportDataModal,
        KDatePicker,
        XemBaoCao,
        GiaoPhieuView,
        SearchAccountModal,
        KRequiredMarker,
        popupSegment,
        GiaophieuModal,
        frmDSHoSoBC: () => import('@/modules/contract/setup/LookupNavigateContract/popup/FrmXemHS')
        
    },
    name: 'DeclareChannelInfo',
    data() {
        return {
            items: {
                "phuong_id": null,
                "sonha": null,
                "khu_id": null,
                "tocdo_id": 48,
                "ip_wan": null,
                "vido": null,
                "ten_kh": "",
                "loaikh_id": 1,
                "loai_trungke_id": null,
                "so_congvan": null,
                "tang": null,
                "dichvuvt_id": 9,
                "makenh_khac": null,
                "ma_tb_sub": "0",
                "daucuoi_id": 1,
                "ma_tb": "0",
                "ma_bss": null,
                "tinh_id": 60,
                "quan_id": null,
                "block": null,
                "ipv6_wan": null,
                "bundle": null,
                "ngay_sd": "15/11/2022 10:01:25",
                "sd_tbi_net": null,
                "ap_id": null,
                "ten_tb": "",
                "tinh_dc_id": 1,
                "phanvung_id": null,
                "gdkn_id": null,
                "ma_hd": "11022",
                "phong": null,
                "pho_id": null,
                "ma_crm": null,
                "diachi": null,
                "kinhdo": null,
                "loaikenhluong": "Kênh KTR",
                "ghichu_net": null,
                "ngay_cat": null,
                "donvi_chutri": null,
                "dacdiem_id": null,
                "ma_vnpt": null,
                "loaitb_id": 66,
                "trangthaitb_id": 1,
                "mota": null,
                "so_as": null,
                "pos_id": null,
                "data": null,
                "moc_int": null,
                "ma_yc": null,
                "ten_goinho": "",
                "diachi_id": 2183966,
                "loai_kl_id": 2,
                "sohieu_asn_dt": null, 
                "ipv6_wan": null,
                "ipv4": null,
                "donvi_sd_id": null,
                "ip_pop": null,
                "bundle": null,
                "ip_wan": null,
                "donvi_sudung_id": 0,
                "loai_kenhtrungke_id": 0
            },
            switchValue: false,
            frmDSHoSoBC: {
                input: {
                    visible: false
                }
            },
            tinh_id: 97,
            tinhcq_id: 0,
            thuebao_id: 0,
            lstLoaiHinhTB: [],
            lstTinhdau: [],
            lstTinhcuoi: [],
            lstDSDichvu: [],
            dsDonviChutri: [],
            dsDonviPhoihop: [],
            dsDonviSudung: [],
            dsDonviSD: [],
            dsTrangthaiTB: [],
            lstLoaikenhTK: [],
            dsData: [],
            lstTocdo: [],
            hdtb_id: 0,
            lstLoaiKH: [],
            lstGiaodienCong: [],
            lstMakenhKhac:[],
            dataSelected: {},
            diemdau: true,
            diemcuoi: false,
            anhien_nut: true,
            tag: 0,
            status: 0,
            phieu_id: 0,
            tsbtnNhapMoi: {
                Enabled: true
            },
            tsbtnGhiLai: {
                Enabled: true
            },
            rowData: {},
            tsbtnHuyBo: {
                Enabled: true
            },
            tsbtnGiaophieu: {
                Enabled: false
            },
            header: {
                title: 'KHAI BÁO THÔNG TIN KÊNH CHUNG',
                list: [{
                        name: 'Lập hợp đồng',
                        link: {
                            name: 'Ui.cards'
                        }
                    },
                    {
                        name: 'Lắp đặt mới',
                        link: {
                            name: 'Ui.cards'
                        }
                    },
                ]
            },
            
            TTKenhluong: {
                checked: [],
                headers: [
                    {
                        fieldName: 'thutu_pd',
                        headerText: 'Thứ tự phân đoạn',
                        allowFiltering: true,
                        textAlign: 'center',
                        width: 50, 
                        template: colThutuPhandoan
                    },
                    {
                        fieldName: 'loai_pd',
                        headerText: 'Loại phân đoạn',
                        allowFiltering: true,
                    },
                    {
                        fieldName: 'nguoi_xuli',
                        headerText: 'Người xử lí',
                        allowFiltering: true,
                    },
                     {
                        fieldName: 'donvi_xl',
                        headerText: 'Đơn vị xử lí',
                        allowFiltering: true,
                    },
                     {
                        fieldName: 'sapxep_phandoan',
                        headerText: 'Sắp xếp phân đoạn',
                        allowFiltering: true,
                        width: 150
                    },
                     {
                        fieldName: 'trangthai_phandoan',
                        headerText: 'Trạng thái',
                        allowFiltering: true,
                    },
                    {
                        fieldName: 'trangthai_phandoan',
                        headerText: 'Thao tác',
                        allowFiltering: true,
                        template: colThaotác
                    }
                   
                ],
                data: [],
            }
        }
    },
    async created() {
    },
    validations: {
    },
    async mounted() {
        if (this.$route.query.tag && this.$route.query.tag !== '') {
            this.tag = this.$route.query.tag.replaceAll(' ', '+')
        }
        if (this.$route.query.tinh_id && this.$route.query.tinh_id !== '') {
            this.tinh_id = this.$route.query.tinh_id.replaceAll(' ', '+')
        }
        if (this.$route.query.phieu_id && this.$route.query.phieu_id !== '') {
            this.phieu_id = this.$route.query.phieu_id.replaceAll(' ', '+')
        }
         if (this.$route.query.status && this.$route.query.status !== '') {
            this.status = this.$route.query.status.replaceAll(' ', '+')
        }
        await this.getdsDichVu();
        await this.getdsDanhmucChung();
        if (this.$route.query.hdtb_id && this.$route.query.hdtb_id !== '') {
            this.hdtb_id = this.$route.query.hdtb_id.replaceAll(' ', '+')
            await this.layThongTinChung_();
            await this.layDsPhandoan();
        }
        if (this.$route.query.tinhcq_id && this.$route.query.tinh_id) {
            this.tinhcq_id = this.$route.query.tinhcq_id.replaceAll(' ', '+')
            this.tinh_id = this.$route.query.tinh_id.replaceAll(' ', '+')
             console.log(66666)
             console.log(this.tinhcq_id ) 
            if(this.tinhcq_id !=0 ){
                console.log(88888)
                if(this.tinh_id != this.tinhcq_id &&this.tinhcq_id != 'null' &&this.tinhcq_id != 0){
                    this.anhien_nut = false
                }else{
                    if(this.phieu_id != 0 && this.status == 1){
                        this.anhien_nut = false
                    }
                }
            }
        }
        if (this.$route.query.thuebao_id && this.$route.query.thuebao_id !== '') {
            this.thuebao_id = this.$route.query.thuebao_id.replaceAll(' ', '+')
            await this.layThongTinChung_();
            await this.layDsPhandoan();
        }
    },
    methods: {
        
        async layThongTinChung_() {
            try {
                this.loading(true)
                await api.layThongTinChung(this.axios, {"hdtb_id":parseInt(this.hdtb_id),"phanvung_id": this.tinh_id,"thuebao_id":this.thuebao_id,"phieu_id":this.phieu_id})
                    .then((res) => {
                        if (
                            res.data.error_code === "BSS-00000000" &&
                            res.data.data !== undefined &&
                            res.data.data.length > 0
                        ) {
                            this.header.title = 'KHAI BÁO THÔNG TIN KÊNH CHUNG - '+res.data.data[0].loaikenhluong
                            this.dsData = res.data.data
                            this.getdsDanhmucChung();
                            if(res.data.data.length > 1){ 
                                this.items = this.diemdau?res.data.data[0]:res.data.data[1]
                                this.items.diachi_dau = res.data.data[0].diachi
                                this.items.diachi_cuoi = res.data.data[1].diachi
                                this.items.tinhdau = res.data.data[0].tinh_id
                                this.items.tinhcuoi = res.data.data[1].tinh_id
                            }else{
                                this.items = res.data.data[0]
                            }
                            console.log(8888888888) 
                            console.log(this.items)
                            console.log(this.items.dichvuvt_id)
                            this.getdsDichVu()
                            this.items.dichvuvt_id = res.data.data[0].dichvuvt_id
                            this.getLoaiHinhTB(this.items.dichvuvt_id)
                            this.items.loaitb_id = res.data.data[0].loaitb_id
                            //this.getTocdo(this.items.dichvuvt_id,this.items.loaitb_id)
                            this.lstMakenhKhac = JSON.parse(res.data.data[0].makenh_khac)
                            if(this.lstMakenhKhac.length > 0){
                                this.items.makenh_khac = this.lstMakenhKhac[0].MAKENH 
                            }
                            
                            
                        }
                    });
                 this.loading(false)
            } catch (err) {
                this.$toast.error("Lỗi lấy thông tin chung: " + err.message);
            }
        },
        async layDsPhandoan() {
            try {
                 this.loading(true)
                await api.laydsPhanDoan(this.axios, {"hdtb_id":parseInt(this.hdtb_id),"phanvung_id": this.tinh_id,"thuebao_id": this.thuebao_id,"phieu_id":this.phieu_id})
                    .then((res) => {
                        if (
                            res.data.error_code === "BSS-00000000"&&res.data.error === "200"
                        ) {
                            this.TTKenhluong.data = res.data.data;
                        }
                    });
                 this.loading(false)
            } catch (err) {
                this.$toast.error("Lỗi lấy thông tin chung: " + err.message);
            }
        },
        updateDiemCuoi() {
            this.diemcuoi = true
            this.diemdau = !this.diemcuoi;
            this.layThongTinChung_()
        },
        updateDiemDau() {
            this.diemdau = true
            this.diemcuoi = !this.diemdau;
            this.layThongTinChung_()
        },
        gridCommand_Thaotac: async function (type, dataItem) {
            dataItem.hdtb_id = this.hdtb_id
            if(type =='save'){
                this.$refs.popupSegment.showDialog(dataItem,this.items.loai_kl_id,this.tinh_id,'edit');
            }
            if(type =='trash'){
                this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa thông tin phân đoạn này không ?', {
                    title: '',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then( async  v => {
                    if(v) {
                        let ds = await api.xoaPhanDoan(this.axios,  {
                            "hdtb_ct_id":dataItem.hdtb_ct_id,
                            "thuebao_ct_id":dataItem.thuebao_ct_id,
                            "phieu_id":this.phieu_id,
                            "phanvung_id": this.tinh_id
                        });
                        if(ds.data.error == 200 && ds.data.error_code == 'BSS-00000000'){
                            this.$root.$toast.success("Xóa thành công!");
                            this.layDsPhandoan(); 
                        }else{
                            this.$toast.error("Lỗi kích hoạt phân đoạn: " + ds.data.message);
                        }
                        
                    }
                });
            }
            if (type == 'viewDetails') {
                console.log('Xem chi tiết')
                this.$refs.popupSegment.showDialog(dataItem,this.items.loai_kl_id,this.tinh_id,'view');
            }
            if (type == 'on') {
                this.$bvModal.msgBoxConfirm('Bạn có muốn hủy kích hoạt phân đoạn này không ?', {
                    title: '',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then( async  v => {
                    if(v) {
                        let ds = await api.anHienPhanDoan(this.axios,  {
                            "hdtb_ct_id":dataItem.hdtb_ct_id,
                            "thuebao_ct_id":dataItem.thuebao_ct_id,
                            "phieu_id":this.phieu_id,
                            "trang_thai_an": 0,
                            "phanvung_id": this.tinh_id
                        });
                        if(ds.data.error == 200 && ds.data.error_code == 'BSS-00000000'){
                            this.$root.$toast.success("Hủy kích hoạt phân đoạn thành công!");
                            this.layDsPhandoan(); 
                        }else{
                            this.$toast.error("Lỗi hủy kích hoạt phân đoạn: " + ds.data.message);
                        }
                        
                    }
                });
                
            }
            if (type == 'off') {
                this.$bvModal.msgBoxConfirm('Bạn có muốn kích hoạt phân đoạn này không ?', {
                    title: '',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then( async  v => {
                    if(v) {
                        let ds = await api.anHienPhanDoan(this.axios,  {
                            "hdtb_ct_id":dataItem.hdtb_ct_id,
                            "thuebao_ct_id":dataItem.thuebao_ct_id,
                            "phieu_id":this.phieu_id,
                            "trang_thai_an": 1,
                            "phanvung_id": this.tinh_id
                        });
                        if(ds.data.error == 200 && ds.data.error_code == 'BSS-00000000'){
                            this.$root.$toast.success("Kích hoạt phân đoạn thành công!");
                            this.layDsPhandoan(); 
                        }else{
                            this.$toast.error("Lỗi kích hoạt phân đoạn: " + ds.data.message);
                        }
                        
                    }
                });
            }
        },
        async getdsDanhmucChung() {
            try {
                await api.laydsDanhMucChung(this.axios, {})
                    .then((res) => {
                        console.log(res)
                        if (
                            res.data.error_code == "BSS-00000000" 
                        ) {
                            console.log(1111111) 
                            console.log(res.data.data.LOAI_KH)
                            if(res.data.data.LOAI_KH.length >0){
                                this.lstLoaiKH = res.data.data.LOAI_KH;
                                //this.items.loaikh_id = this.lstLoaiKH[0].loaikh_id
                            }
                            if(res.data.data.GIAODIEN_CONG.length >0){
                                this.lstGiaodienCong = res.data.data.GIAODIEN_CONG;
                               // this.items.gdkn_id = this.lstGiaodienCong[0].gdkn_id
                            }
                            if(res.data.data.TRANGTHAI_TB.length >0){
                                this.dsTrangthaiTB = res.data.data.TRANGTHAI_TB;
                                //this.items.trangthaitb_id = this.dsTrangthaiTB[0].trangthaitb_id
                            }
                            if(res.data.data.DONVI.length >0){
                                this.dsDonviChutri = this.list2treeNew(res.data.data.DONVI);
                               // this.items.donvi_chutri_id = this.dsDonviChutri[0].donvi_id
                                this.dsDonviPhoihop = this.list2treeNew(res.data.data.DONVI);
                               // this.items.donvi_phoihop_id = this.dsDonviPhoihop[0].donvi_id;
                                this.dsDonviSudung = this.list2treeNew(res.data.data.DONVI);
                                //this.items.donvi_sudung_id = this.dsDonviSudung[0].donvi_id;
                                
                            }
                            if(res.data.data.LOAI_KENHTRUNGKE.length >0){
                                this.lstLoaikenhTK = res.data.data.LOAI_KENHTRUNGKE;
                                //this.items.loai_kenhtrungke_id = this.lstLoaikenhTK[0].loai_kenhtrungke_id
                                
                            }
                            if(res.data.data.TINH.length >0){
                                this.lstTinhdau = res.data.data.TINH;  
                                //this.items.tinhdau = this.lstTinhdau[0].tinh_id
                                this.lstTinhcuoi = res.data.data.TINH;
                                //this.items.tinhcuoi = this.lstTinhcuoi[1].tinh_id
                            }
                            
                        }
                    });
            } catch (err) {
                this.$toast.error("Lỗi lấy danh mục chung: " + err.message);
            }
        },
        async changeFileUpload() {
            await this.tsbtnFileNT_Click()
        },
        async callApiWrapper(action, body, options = { showError: true }) {
            let result = null
            try {
                this.localLoading++
                result = await action(this.axios, body)
                return result.data.data != null ? result.data.data : null
            } catch (e) {
                if (options.showError) {
                if (e.data != null) {
                    this.$toast.error(`${e.data.message}`)
                } else {
                    this.$toast.error(`Có lỗi gọi API`)
                }
                }
                return 'error'
            } finally {
                setTimeout(() => {
                this.localLoading--
                }, 500)
            }
            return null
        },
        async tsbtnFileNT_Click() {
            let formData = new FormData()
            for (var file of this.$refs.files.files) {
                formData.append('files', file)
            }
            let file_id = await this.callApiWrapper(api.get_keys, {
                phanvung_id: this.tinh_id,
                keyname: 'FILEHOSO_ID',
                numblocksize: 1,
                numretry: 10
            })
                let kq = await this.callApiWrapper(api.upload_file, formData, { headers: { 'Content-Type': 'multipart/form-data' } })
                if (kq && kq.length) {
                    let rs = await this.callApiWrapper(api.tsbtnDownloadFileNT_Click, {
                    vphanvung_id: this.tinh_id,
                    vurl: kq[0],
                    vfile_name: kq[0].substring(kq[0].lastIndexOf('/') + 1, kq[0].length),
                    vthuebao_id: this.thuebao_id,
                    vfile_id: file_id,
                    vthanhtoan_id: this.items.thanhtoan_id,
                    vma_tb: this.items.ma_tb,
                    vhdtb_id: this.hdtb_id
                    })
                    if (rs == 1) {
                    this.$toast.success('Cập nhật thành công!')
                    await this.layThongTinChung_()
                    } else {
                    this.$toast.error(`Có lỗi khi upload file`)
                    }
                }
        },
        onToolbarClick(args) {
             this.$refs.popupSegment.showDialog({hdtb_id: this.hdtb_id,thuebao_id: this.thuebao_id,phieu_id: this.phieu_id},this.items.loai_kl_id,this.tinh_id,'add');
        },
        capnhat(){
            console.log("cập nhật xong")
            this.layDsPhandoan();
        },
        async tsbtnDownloadFileNT_Click() {
            let hdtb_tc = 0
            this.frmDSHoSoBC.input.visible = true
            setTimeout(() => {
                this.$refs.frmDSHoSoBC.showModal()
                this.$refs.frmDSHoSoBC.vloaifile_id = 2
                this.$refs.frmDSHoSoBC.hdtb_tc = this.hdtb_id
                this.$refs.frmDSHoSoBC.tinh_id = this.tinh_id
                this.$refs.frmDSHoSoBC.khoiTao() 
                }, 500)
        },
        async tsbtnGhiLai_Click()
        {
            
            let input = {
                "hdtb_id":this.hdtb_id,
                "thuebao_id":this.thuebao_id,
                "data": this.changeKeysToUpper(this.items),
                "phanvung_id": this.tinh_id
            }
            let cn = await api.capNhatThongTinChung(this.axios,input);
            if(cn.data.error == 200 && cn.data.error_code == 'BSS-00000000') {
                this.$root.$toast.success('Cập nhật thành công');
                this.tsbtnGiaophieu.Enabled =  true;
                await this.layThongTinChung_();
            }   
            else{
                this.$toast.error("Lỗi cập nhật: "+cn.data.message);
            }   
        },
   
        async tsbtnXoa_Click()
        {
            try
            {
                this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa thông tin kênh chung này không ?', {
                    title: '',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then( async  v => {
                    if(v) {
                        let ds = this.GetData(await api.xoaThongTinChung(this.axios,  {
                            "hdtb_id":this.hdtb_id,
                            "thuebao_id":this.thuebao_id,
                            "phanvung_id": this.tinh_id
                        }));
                        this.$root.$toast.success("Xóa thành công!");
                        this.Clear(); 
                    }
                });
            }
            catch (err)
            {
                this.$toast.error("Lỗi xóa hợp đồng khách hàng: "+ err.message);
            }
        },
        Giaophieu_Click(index, rowData) {
            this.$refs.popupGiaophieu.showModal();
        },
        giaophieu(){
            console.log(123456)
            this.tsbtnGiaophieu.Enabled =  true
        },
        changeKeysToUpper(obj) {
            var key, upKey;
            for (key in obj) {
                if (obj.hasOwnProperty(key)) {
                    upKey = key.toUpperCase();
                    if (upKey !== key) {
                        obj[upKey] = obj[key];
                        delete(obj[key]);
                    }
                    if (typeof obj[upKey] === "object") {
                        this.changeKeysToUpper(obj[upKey]);
                    }
                }
            }
            return obj;
        },
        async getdsDichVu() {
            try {
                await api.laydsDichVu(this.axios, {})
                    .then((res) => {
                        if (
                            res.data.error_code === "BSS-00000000" &&
                            res.data.data !== undefined &&
                            res.data.data.length > 0
                        ) {
                            this.lstDSDichvu = res.data.data;
                            // if (this.TTKenhluong.data.length == 0) {
                            //     this.items.dichvuvt_id = this.lstDSDichvu[0].dichvuvt_id;
                            // }
                        }
                    });
                this.getLoaiHinhTB(this.items.dichvuvt_id)
            } catch (err) {
                this.$toast.error("Lỗi lấy danh sách dịch vụ viễn thông: " + err.message);
            }
        },
        async getLoaiHinhTB(dvvtid) {
            try {
              let input ={"dichvuvt_id" : dvvtid}
                await api.laydsLoaiHinh(this.axios, input)
                    .then((res) => {
                        if (
                            res.data.error_code === "BSS-00000000" &&
                            res.data.data !== undefined &&
                            res.data.data.length > 0
                        ) {
                            this.lstLoaiHinhTB = res.data.data;
                            // if (this.TTKenhluong.data.length == 0) {
                            //     this.items.loaitb_id = this.lstLoaiHinhTB[0].loaitb_id;
                            // }
                        }
                    });
                    await this.getTocdo(this.items.dichvuvt_id,this.items.loaitb_id)
                
            } catch (err) {
                this.$toast.error("Lỗi lấy danh sách loại hình: " + err.message);
            }
        },
        async getTocdo(dvvtid,loaitbid) {
            try {
              let input ={"dichvuvt_id" : dvvtid,"loaitb_id" : loaitbid}
                await api.laydsTocDo(this.axios, input)
                    .then((res) => {
                        if (
                            res.data.error_code === "BSS-00000000" &&
                            res.data.data !== undefined &&
                            res.data.data.length > 0
                        ) {
                            this.lstTocdo = res.data.data;
                            // if (this.TTKenhluong.data.length == 0) {
                            //     this.items.tocdo_id = this.lstTocdo[0].tocdo_id;
                            // }
                        }
                    });
            } catch (err) {
                this.$toast.error("Lỗi lấy danh sách loại hình: " + err.message);
            }
        },
        async txtMaTB_KeyPress() {
            
            if (this.txtMaTB != "") {
                let ds = this.GetData(await api.lay_db_theo_matb(this.axios, {
                    "p_ma_tb": this.txtMaTB,
                    "p_donvi_dl_id": 0,
                    "p_dichvuvt_id": 0
                }));
                if (ds.length > 0) {
                    //this.Clear();
                    //await this.HienThiTT_DanhBa_dt(ds);
                    // this.$refs.txtMaKH.focus();
                } else {
                    this.$toast.error("Không tìm thấy thông tin khách hàng!");
                }
            }
        }, 
        list2treeNew(list) {
            let rootList = list.filter(x => (x.donvi_cha_id == null));
            console.log(rootList)
            let openList = [];
            rootList.forEach(item => {
                item.id = item.donvi_id;
                item.label = item.ten_dv ? item.ten_dv : "Null";
                item.level = 0;
                openList.push(item);
                let children = list.filter(x => x.donvi_cha_id == item.donvi_id);
                if (children.length > 0) {
                    item.children = children;
                    children.forEach(childItem => {
                        childItem.level = item.level + 1;
                        childItem.id = childItem.donvi_id;  
                        childItem.label = childItem.ten_dv ? childItem.ten_dv : "Null";
                        openList.push(childItem);
                        let grandchildren = list.filter(x => x.donvi_cha_id == childItem.donvi_id);
                        if (grandchildren.length > 0) {
                            childItem.children = grandchildren;
                            grandchildren.forEach(grandchildItem => {
                                grandchildItem.level = childItem.level + 1;
                                grandchildItem.id = grandchildItem.donvi_id;
                                grandchildItem.label = grandchildItem.ten_dv ? grandchildItem.ten_dv : "Null";
                                openList.push(grandchildItem);
                                let greatGrandchildren = list.filter(x => x.donvi_cha_id == grandchildItem.donvi_id);
                                if (greatGrandchildren.length > 0) {
                                    grandchildItem.children = greatGrandchildren;
                                    greatGrandchildren.forEach(greatGrandchildItem => {
                                        greatGrandchildItem.level = grandchildItem.level + 1;
                                        greatGrandchildItem.id = greatGrandchildItem.donvi_id;
                                        greatGrandchildItem.label = greatGrandchildItem.ten_dv ? greatGrandchildItem.ten_dv : "Null";
                                        openList.push(greatGrandchildItem);
                                    });
                                }
                            });
                        }
                    });
                }
            });
            let rootList_new = openList.filter(x => (x.donvi_cha_id == null && x.level < 4));
            return rootList_new;
        },
        
        onChangeDonviChutri(item){
            this.items.donvi_chutri_id = item.donvi_id
        },
        onChangeDonviSudung(item){
            this.items.donvi_sudung_id = item.donvi_id
        },
        onChangeDonviPhoihop(item){
            this.items.donvi_phoihop_id = item.donvi_id
        },
        onChangeLoaikenhTK(item){
            this.items.loai_trungke_id = item.loai_kenhtrungke_id
        },
        async acceptSearchAccount(item) {
            this.items.ma_tb = item.ma_tb;
        },
        showPopupSearchAccount() {
            this.$refs.searchAccountModal.showModal();
        },
        onChangeLoaiKH(agrs) {
            this.items.loaikh_id = agrs;
        },
        onChangeTocdo(agrs) {
            this.items.tocdo_id = agrs;
        },
        onChangeMakenh(agrs) {
            this.items.makenh = agrs;
        },
        onChangeLoaihinh(agrs) { 
            this.items.loaitb_id = agrs;
            this.getTocdo(this.items.dichvuvt_id,this.items.loaitb_id)
        },
        onChangeDichVuVT(agrs) {
            this.items.dichvuvt_id = agrs;
            this.getLoaiHinhTB(agrs);
        },
        gview_TTKenhluong_actionComplete(){
        },
        gridTTKenhluongSelected(data){
            this.dataSelected = data.data
            this.rowData = data.data
        },
        GetDataList: function (response) {
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data
            } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
            } else {
                return response.data.data
            }
            return []
        },
        GetData: function (response) {
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data
            } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
                return []
            } else {
                return response.data.data
            }
            return []
        },
        isNullOrEmpty: function (value) {
            return (
                value == undefined || value == null || value == ""
            );
        },
    },
};
</script>
<style scoped>
.info-row .value{
    padding-left: 0px !important;
}
.select2-container .select2-selection--single{
    border: 1px solid #ced4da !important
}
.pd-l0{
    padding-left: 0px !important;
}
.greyColor {
    background-color: #e1e1e1 !important;
}
.bd-cl0{
    border-color: gray !important
}
.bd-0{
    border-color: gray !important
}
.active {
  background-color: green;
}
.inactive {
  background-color: gray;
}
.switch-container {
  width: 60px;
  height: 30px;
  cursor: pointer;
}
.switch {
  position: relative;
  width: 24px;
  height: 24px;
  background-color: white;
  border-radius: 50%;
  transition: transform 0.3s;
}
</style>