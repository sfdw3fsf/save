<template src="./ChannelSubscription.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import moment from 'moment';
import api from './api'
import apiHelper from "./api.helper"
import MaTB from './Modals/MaTB/MaTB.vue';
import TraCuuThongSoKTModal from './Modals/TraCuuThongSoKT/TraCuuThongSoKTModal.vue'
export default {
    name : "ChannelSubscription",
    components: {
        breadcrumb,
        ActionTop,
        moment,
        api,
        apiHelper,
        MaTB,
        TraCuuThongSoKTModal
    },
    data(){
        return {
            header: {
                title: "Tra cứu thuê bao kênh luồng",
                list: [
                    {
                        name: "Tra cứu thuê bao kênh luồng", link: { name: "Ui.cards"}
                    },
                ]
            },
            actions: [
                {
                    id: "nhapmoi",
                    name: "Nhập mới",
                    active: true,
                    icon_class: "Icon one-file-plus"
                },
                {
                    id: "timkiem",
                    name: "Tìm kiếm",
                    active: true,
                    icon_class: "one-search"
                    
                },
                {
                    id: "chitiet",
                    name: "Chi tiết",
                    active: true,
                    icon_class: "one-file-noti"
                },
            ],
            rdoInfoChannel: 
            {
                options:[
                    {
                        label: 'Chưa tạo thông tin kênh luồng',
                        value: 'Notcreate',
                        name: 'rdoPBHSelected'
                    },
                    {
                        label: 'Đã tạo thông tin kênh luồng',
                        value: 'created',
                        name: 'rdoPBHSelected'
                    }
                ],
                value: "Notcreate"
            },
            cboDichVu:{
                text: "Dịch vụ",
                list: [],
                value: "",
                check: false,
                disabled: true
            },
            cboLoaiHinh:{
                text: "Loại hình",
                list: [],
                value: "",
                check: false,
                disabled: true
            },
            cboLoaiKenh:{
                text: "Loại kênh",
                list: [],
                value: ""
            },
            cboTinh:{
                text: "Tỉnh",
                list: [],
                value: ""
            },
            txtMaTB: {
                text: "Mã thuê bao",
                value: ""
            },
            txtMaKH: {
                text: "Mã khách hàng",
                value: ""
            },
            txtTenKH: {
                text: "Tên khách hàng",
                value: ""
            },
            txtMaKenh: {
                text: "Mã kênh khác",
                value: ""
            },
            txtMaHD: {
                text: "Mã hợp đồng",
                value: ""
            },
            DsThueBao: {
                columns: [
                    {
                        fieldName: "tinhcq", headerText: "Tỉnh chủ quản"
                    },
                    {
                        fieldName: "tinhld", headerText: "Tỉnh thi công"
                    },
                    {
                        fieldName: "ma_tb", headerText: "Số máy/Acc"
                    },
                    {
                        fieldName: "ma_lt", headerText: "Số ảo"
                    },
                    {
                        fieldName: "loaihinh_tb", headerText: "Loại hình"
                    },
                    {
                        fieldName: "tocdo", headerText: "Tốc độ"
                    },
                    {
                        fieldName: "ten_tb", headerText: "Tên thuê bao"
                    },
                    {
                        fieldName: "diachi_tb", headerText: "Địa chỉ thuê bao"
                    },
                    {
                        fieldName: "diachi_ld", headerText: "Địa chỉ lắp đặt"
                    },
                    {
                        fieldName: "trangthai_tb", headerText: "Trạng thái thuê bao"
                    }
                ],
                data: [],
                thuebao_id: "",
                tag: "",
                tinh_id: "",
                tinhcq_id: "",
            },
            formData: {
                dichvuvt_id: "",
                thuebao_id: "",
                ma_tb: "",
                phanvung_id: ""
            }
        }
    },
    watch:{
        'cboDichVu.value':{
            async handler(value){
                await this.getDsLoaiHinhTB(value)
            }
        },
        // 'cboLoaiHinh.value':{
        //     async handler(value){
        //         await this.getDsLoaiKenh(this.cboDichVu.value,value)
        //         this.reset()
        //     }
        // },
        'cboDichVu.check':{
            handler(value){
                if(value == true){
                    this.cboDichVu.disabled = false
                }
                else{
                    this.cboDichVu.disabled = true
                    this.cboLoaiHinh.disabled = true
                    this.cboLoaiHinh.check = false

                }
            }
        },
        'cboLoaiHinh.check':{
            handler(value){
                if(value == true){
                    this.cboLoaiHinh.disabled = false
                    this.cboDichVu.check = true
                    this.cboDichVu.disabled = false
                    
                }
                else{
                    this.cboLoaiHinh.disabled = true
                }
            }
        }
        // 'cboLoaiKenh.value': {
        //     handler(value){
        //         this.tag = value
        //     }
        // }
    },
    mounted() {
        this.getDsDichVu()
        this.getDsTinh()
    },
    methods: {
        reset(){
            this.txtMaTB.value = ""
            this.txtMaKH.value = ""
            this.txtTenKH.value = ""
            this.txtMaKenh.value = ""
            this.txtMaHD.value = ""
            this.rdoInfoChannel.value = "Notcreate"
            this.cboTinh.value = this.$root.token.getPhanVungID()
            this.thuebao_id = ""
            this.DsThueBao.data = []

        },
        onActionClick(action){
            switch(action.id){
                case "nhapmoi":
                    this.NhapMoi()
                    break;
                case "timkiem":
                    this.TimKiem()
                    break;
                case "chitiet":
                    this.ChiTiet()
                    break;
                default: 
                    break;
            }
        },
        async getDsDichVu(){
            this.$root.showLoading(true);
            try{
                const response = await api.Danhsach_dichvu(this.axios)
                this.cboDichVu.list = apiHelper.getDataFromResponseApiReturnArray(response).map((e) => ({
                    id: e.dichvuvt_id,
                    text: e.ten_dvvt
                })) || []
                this.cboDichVu.value = this.cboDichVu.list[0].id
            }
            catch(e){
            }
            finally{
                this.$root.showLoading(false);
            }
        },
        async getDsLoaiHinhTB(dichvuvt){
            this.$root.showLoading(true);
            try{
                let input = {
                    dichvuvt_id: dichvuvt
                }
                const response = await api.Danhsach_loaihinhtb(this.axios,input)
                this.cboLoaiHinh.list = apiHelper.getDataFromResponseApiReturnArray(response).map((e)=> ({
                    id: e.loaitb_id,
                    text: e.loaihinh_tb
                })) || []
                this.cboLoaiHinh.value = this.cboLoaiHinh.list[0].id
            }
            catch(e){}
            finally{
                this.$root.showLoading(false);
            }
        },
        async getDsLoaiKenh(dichvuvt,loaihinhtb){
            this.$root.showLoading(true);
            try{
                let input = {
                    dichvuvt_id: dichvuvt,
                    loaihinhtb_id: loaihinhtb
                }
                const response = await api.Danhsach_loaikenh(this.axios,input)
                this.cboLoaiKenh.list = apiHelper.getDataFromResponseApiReturnArray(response).map((e)=> ({
                    id: e.loaikenhluong_id,
                    text: e.loaikenhluong
                })) || []
                this.cboLoaiKenh.value = this.cboLoaiKenh.list[0].id
            }
            catch(e){}
            finally{
                this.$root.showLoading(false);
            }
        },
        async getDsTinh(){
            this.$root.showLoading(true);
            try{
                const response = await api.Danhsach_tinh(this.axios)
                this.cboTinh.list = apiHelper.getDataFromResponseApiReturnArray(response).map((e)=> ({
                    id: e.phanvung_id,
                    text: e.tentinh
                }))
                this.cboTinh.value = this.$root.token.getPhanVungID()
            }
            catch(e){}
            finally{
                this.$root.showLoading(false);
            }
        },
        openModalMaTB(){
            this.$refs['modalMaTB'].openModal()
            this.$refs['modalMaTB'].dichvuvt_id = this.cboDichVu.value
            this.$refs['modalMaTB'].loaihinh_id = this.cboLoaiHinh.value
            this.$refs['modalMaTB'].phanvung_id = this.cboTinh.value
        },
        NhapMoi(){
            this.txtMaTB.value = ""
            this.txtMaKH.value = ""
            this.txtTenKH.value = ""
            this.txtMaKenh.value = ""
            this.txtMaHD.value = ""
        },
        async TimKiem(){
            this.$root.showLoading(true);
            try{
                if(this.cboTinh.value == ""){
                    // if(this.cboDichVu.value == ""){
                    //     this.$toast.error("Vui lòng chọn dịch vụ")
                    //     this.$refs['cboDichVu'].focus()
                    // }
                    // else if(this.cboLoaiHinh.value == ""){
                    //     this.$toast.error("Vui lòng chọn loại hình")
                    //     this.$refs['cboLoaiHinh'].focus()
                    // }
                    // if(this.cboTinh.value == ""){
                    this.$toast.error("Vui lòng chọn tỉnh")
                    this.$refs['cboTinh'].focus()
                    // }
                }
                else {
                    let input = {
                        dichvu_id: this.cboDichVu.check == true ? this.cboDichVu.value : 0,
                        loaihinh_id: this.cboLoaiHinh.check == true ? this.cboLoaiHinh.value : 0,
                        // loaikenh: this.cboLoaiKenh.value,
                        loaikenh: "",
                        dataottkenh: this.rdoInfoChannel.value == "created" ? 1 : 0,
                        numbSize: 1000,
                        numbPage: 0,
                        tinh: this.cboTinh.value,
                        mathuebao: this.txtMaTB.value,
                        makhachhang: this.txtMaKH.value,
                        makenhkhac: this.txtMaKenh.value,
                        mahopdong: this.txtMaHD.value,
                        tenkhachhang: this.txtTenKH.value
                    }
                    const response = await api.Lay_Danhsach_thuebao(this.axios,input)
                    this.DsThueBao.data = apiHelper.getDataFromResponseApiReturnArray(response) || []
                    if(this.DsThueBao.data.length > 0){
                        this.$toast.success("Tìm kiếm thành công")
                        for(const item of this.DsThueBao.data){
                            if(item['tinhcq_id'] == null){
                                item['tinhcq'] = item['tinhld']
                            }
                            if(item['tinhcq_id'] == ""){
                                item['tinhcq'] = item['tinhld']
                            }
                        }
                    }
                    else{
                        this.$toast.info("Không có dữ liệu")
                    }
                }
            }
            catch(e){}
            finally{
                this.$root.showLoading(false);
            }
        },
        select_selectedItemsChangedDsThueBao(value){
            console.log(value[0]);
            if(value[0]){
                this.txtMaTB.value = value[0].ma_tb
                this.txtTenKH.value = value[0].ten_tb
                this.txtMaKH.value = value[0].ma_kh
                this.txtMaHD.value = value[0].ma_hd
                this.thuebao_id = value[0].thuebao_id
                this.tag = value[0].tag
                this.cboDichVu.value = value[0].dichvuvt_id
                this.tinhcq_id = value[0].tinhcq_id
                setTimeout(() => {
                    this.cboLoaiHinh.value = value[0].loaitb_id
                },500)
                 console.log("Value:",value[0])
            }
            // Double Click
            // var self = this
            // this.click ++
            // if(this.click == 1){
            //     this.timer = setTimeout(function() {
            //         self.click = 0
            //     }, 200); 
            // }
            // else{
                
            //     clearTimeout(this.timer);
            //     self.click = 0;
            // }
            
        },
        ThongSoKT_DoubleClick(index, rowData){
            setTimeout(() => {
                this.$refs['traCuuThongSoKTModal'].showModal()
            },500)
            this.formData.dichvuvt_id = rowData.dichvuvt_id
            this.formData.thuebao_id = rowData.thuebao_id
            this.formData.ma_tb = rowData.ma_tb
            this.formData.phanvung_id = rowData.phanvung_id
        },
        loadMaTB(data){
            console.log(data);
            this.cboDichVu.value = data.dichvuvt
            this.cboLoaiHinh.value = data.loaihinh
            setTimeout(() => {
                this.txtMaTB.value = data.somay
                this.txtMaKH.value = data.makh
                this.txtTenKH.value = data.tenkh
                this.txtMaHD.value = data.mahd
                this.cboTinh.value = data.phanvung
            },1000)
            
        },
        ChiTiet(){
            if(this.thuebao_id === undefined || this.thuebao_id == "" || this.thuebao_id == null){
                this.$toast.info("Chưa chọn thuê bao")
            }
            else{
                this.tinh_id = this.cboTinh.value
                let routeData = this.$router.resolve(`/htkh/DeclareChannelInfo?thuebao_id=${this.thuebao_id}&tinh_id=${this.tinh_id}&tinhcq_id=${this.tinhcq_id}&tag=${this.tag}`)
                window.open(routeData.href, '_blank')
                // this.$router.push(`/htkh/DeclareChannelInfo?hdtb_id=${this.hdtb_id}&tag=${this.tag}`)
            }
        }
    }
}
</script>

<style scoped>

</style>