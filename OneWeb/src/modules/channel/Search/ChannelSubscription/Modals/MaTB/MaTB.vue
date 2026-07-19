<template src="./MaTB.html"></template>

<script>
import api from "../../api";
import apiHelper from "../../api.helper";
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import { log } from 'pdfmake/build/pdfmake';

export default {
    name: "MaTB",
    components:{
        ActionTop,
        api,
        apiHelper
    },
    data(){
        return{
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
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
                    id: "chapnhan",
                    name: "Chấp nhận",
                    active: true,
                    icon_class: "one-check"    
                }
            ],
            DsThueBao: {
                columns: [
                    // {
                    //     fieldName: "tinh", headerText: "Tỉnh"
                    // },
                    {
                        fieldName: "ma_tt", headerText: "Mã TT"
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
                        fieldName: "ngay_sd", headerText: "Ngày SD"
                    },
                    {
                        fieldName: "trangthai_tb", headerText: "Trạng thái"
                    }
                ],
                data: []
            },
            txtTraCuuTheo : {
                text: "Tra cứu theo",
                value_v1: "",
                value_v2: "",
                list: []
            },
            ckbDichVu: {
                value_v1: "",
                value_v2: "",
                list: [],
                disabled: true
            },
            ckbDonVi: {
                value_v1: "",
                value_v2: "",
                list: [],
                disabled: true
            },
            ckbTraCuuTheo_v1: {
                value_v1: "",
                value_v2: "",
                value_v3: "",
                list: [],
                disabled: true
            },
            ckbTraCuuTheo_v2: {
                value_v1: "",
                value_v2: "",
                value_v3: "",
                list: [],
                disabled: true
            },
            txtMaKH: {
                text: "Mã KH",
                value: ""
            },
            txtSoMay: {
                text: "Số máy/Acc",
                value: ""
            },
            txtSoAo: {
                text: "Số ảo",
                value: ""
            },
            ckbLocTheoDV: {
                value_v1: "",
                value_v2: "",
                list: [],
                disabled: true
            },
            ckbLocTheoLH: {
                value_v1: "",
                value_v2: "",
                list: [],
                disabled: true
            },
            DataOrigin: [],
            ten_kh: "",
            ma_hd: "",
            dichvuvt_id: "",
            loaihinh_id: "",
            phanvung_id: ""
        }
    },
    watch:{
        'ckbLocTheoDV.value_v2':{
            async handler(value){
                let input = {
                    dichvuvt_id: value
                }
                const response = await api.Danhsach_loaihinhtb(this.axios,input)
                this.ckbLocTheoLH.list = apiHelper.getDataFromResponseApiReturnArray(response).map((e)=> ({
                    id: e.loaitb_id,
                    text: e.loaihinh_tb
                })) || []
                this.ckbLocTheoLH.value_v2 = this.ckbLocTheoLH.list[0].id
                if(this.ckbLocTheoDV.value_v1 == true){
                    this.LocTheoDichVu(value)
                }
            }
        },
        'ckbDichVu.value_v1': {
            handler(value){
                if(value == true){
                    this.ckbDichVu.disabled = !this.ckbDichVu.disabled
                }
                else{
                    this.ckbDichVu.disabled = !this.ckbDichVu.disabled
                }
            }
        },
        'ckbDonVi.value_v1': {
            handler(value){
                if(value == true){
                    this.ckbDonVi.disabled = !this.ckbDonVi.disabled
                }
                else{
                    this.ckbDonVi.disabled = !this.ckbDonVi.disabled
                }
            }
        },
        'ckbTraCuuTheo_v1.value_v1': {
            handler(value){
                if(value == true){
                    this.ckbTraCuuTheo_v1.disabled = !this.ckbTraCuuTheo_v1.disabled
                }
                else{
                    this.ckbTraCuuTheo_v1.disabled = !this.ckbTraCuuTheo_v1.disabled
                }
            }
        },
        'ckbTraCuuTheo_v2.value_v1': {
            handler(value){
                if(value == true){
                    this.ckbTraCuuTheo_v2.disabled = !this.ckbTraCuuTheo_v2.disabled
                }
                else{
                    this.ckbTraCuuTheo_v2.disabled = !this.ckbTraCuuTheo_v2.disabled
                }
            }
        },
        'ckbLocTheoDV.value_v1': {
            handler(value){
                if(value == true){
                    this.ckbLocTheoDV.disabled = !this.ckbLocTheoDV.disabled
                    this.LocTheoDichVu(this.ckbLocTheoDV.value_v2);
                }
                else{
                    this.ckbLocTheoDV.disabled = !this.ckbLocTheoDV.disabled
                }
            }
        },
        'ckbLocTheoLH.value_v1': {
            handler(value){
                if(value == true){
                    this.ckbLocTheoLH.disabled = !this.ckbLocTheoLH.disabled
                    this.LocTheoLoaiHinh(this.ckbLocTheoLH.value_v2)
                }
                else{
                    this.ckbLocTheoLH.disabled = !this.ckbLocTheoLH.disabled
                }
            }
        },
        'ckbLocTheoLH.value_v2': {
            handler(value){
                if(this.ckbLocTheoLH.value_v1 == true){
                    this.LocTheoLoaiHinh(value)
                }
                
            }
        },
    },
    computed:{
    },
    methods: {
        reset(){
            this.txtTraCuuTheo.value_v1 = this.txtTraCuuTheo.list[0].id
            this.txtTraCuuTheo.value_v2 = ""


            this.ckbDichVu.value_v1 = false
            this.ckbDichVu.value_v2 = this.ckbDichVu.list[0].id
            this.ckbDichVu.disabled = false

            this.ckbDonVi.value_v1 = false
            this.ckbDonVi.value_v2 = this.ckbDonVi.list[0].id
            this.ckbDonVi.disabled = false

            this.ckbTraCuuTheo_v1.value_v1 = false
            this.ckbTraCuuTheo_v1.value_v2 = this.ckbTraCuuTheo_v1.list[0].id
            this.ckbTraCuuTheo_v1.value_v3 = ""
            this.ckbTraCuuTheo_v1.disabled = false


            this.txtMaKH.value = ""

            this.ckbTraCuuTheo_v2.value_v1 = false
            this.ckbTraCuuTheo_v2.value_v2 = this.ckbTraCuuTheo_v2.list[0].id
            this.ckbTraCuuTheo_v2.value_v3 = ""
            this.ckbTraCuuTheo_v2.disabled = false


            this.txtSoMay.value = ""
            this.txtSoAo.value = ""

            this.ckbLocTheoDV.value_v1 = false
            this.ckbLocTheoDV.value_v2 = this.ckbLocTheoDV.list[0].id
            this.ckbLocTheoDV.disabled = false

            this.ckbLocTheoLH.value_v1 = false
            this.ckbLocTheoLH.value_v2 = this.ckbLocTheoLH.list[0].id
            this.ckbLocTheoLH.disabled = false
            
            this.DsThueBao.data = []

        },
        async dialogOpen () {
            await this.getDsDichVu()
            await this.getDsDonVi()
            await this.getDsDTTK()
        },
        onActionClick(action){
            switch(action.id){
                case "nhapmoi":
                    this.NhapMoi()
                    break;
                case "timkiem":
                    this.TimKiem()
                    break;
                case "chapnhan":
                    this.ChapNhan()
                    break;
                default:
                    break;
            }
        },
        async getDsDichVu(){
            this.$root.showLoading(true)
            try{
                const response = await api.Danhsach_dichvu(this.axios)
                let data = apiHelper.getDataFromResponseApiReturnArray(response).map((e) => ({
                    id: e.dichvuvt_id,
                    text: e.ten_dvvt
                })) || []
                this.ckbDichVu.list = data
                this.ckbDichVu.value_v2 = this.ckbDichVu.list[0].id
                this.ckbLocTheoDV.list = data
                this.ckbLocTheoDV.value_v2 = this.ckbLocTheoDV.list[0].id
            }
            catch(e){}
            finally{
                this.$root.showLoading(false)
            }
        },
        async getDsDonVi(){
            this.$root.showLoading(true)
            try{
                const response = await api.Danhsach_donvi(this.axios)
                let data = apiHelper.getDataFromResponseApiReturnArray(response).map((e) => ({
                    id: e.donvi_id,
                    text: e.ten_dv
                })) || []
                this.ckbDonVi.list = data
                this.ckbDonVi.value_v2 = this.ckbDonVi.list[0].id
            }
            catch(e){}
            finally{
                this.$root.showLoading(false)
            }
        },
        async getDsDTTK(){
            this.$root.showLoading(true)
            try{
                let input = {
                    nhom_id: 2
                }
                const response = await api.Danhsach_dieukientimkiem(this.axios,input)
                let data = apiHelper.getDataFromResponseApiReturnArray(response).map((e) => ({
                    id: e.kieutk_id,
                    text: e.kieu_tk
                })) || []
                this.txtTraCuuTheo.list = data
                this.txtTraCuuTheo.value_v1 = this.txtTraCuuTheo.list[0].id

                this.ckbTraCuuTheo_v1.list = data
                this.ckbTraCuuTheo_v1.value_v2 = this.ckbTraCuuTheo_v1.list[0].id

                this.ckbTraCuuTheo_v2.list = data
                this.ckbTraCuuTheo_v2.value_v2 = this.ckbTraCuuTheo_v2.list[0].id
            }
            catch(e){}
            finally{
                this.$root.showLoading(false)
            }
        },
        NhapMoi(){
            this.txtMaKH.value = ""
            this.txtSoMay.value = ""
            this.txtSoAo.value = ""
        },
        async TimKiem(){
            this.$root.showLoading(true);
            try{
                let tracuutheo = []
                let dk1 = {
                    kieutk_id: this.txtTraCuuTheo.value_v1,
                    giatri: this.txtTraCuuTheo.value_v2
                }
                tracuutheo.push(JSON.parse(JSON.stringify(dk1)))
                if(this.ckbTraCuuTheo_v1.value_v1 == true){
                    let dk2 = {
                        kieutk_id: this.ckbTraCuuTheo_v1.value_v2,
                        giatri: this.ckbTraCuuTheo_v1.value_v3
                    }
                    tracuutheo.push(JSON.parse(JSON.stringify(dk2)))
                }
                if(this.ckbTraCuuTheo_v2.value_v1 == true){
                    let dk3 = {
                        kieutk_id: this.ckbTraCuuTheo_v2.value_v2,
                        giatri: this.ckbTraCuuTheo_v2.value_v3
                    }
                    tracuutheo.push(JSON.parse(JSON.stringify(dk3)))
                }
                // console.log(tracuutheo);
                let input = {
                    dichvu_id: this.ckbDichVu.value_v1 == true ? this.ckbDichVu.value_v2 : 0,
                    donvi_id: this.ckbDonVi.value_v1 == true ? this.ckbDonVi.value_v2 : 0,
                    data: tracuutheo
                }
                const response = await api.Lay_Danhsach_tracuu_thuebao_theodk(this.axios,input)
                this.DsThueBao.data = apiHelper.getDataFromResponseApiReturnArray(response) || []
                if(this.DsThueBao.data.length > 0){
                    this.$toast.success("Tìm kiếm thành công")
                    this.DataOrigin = this.DsThueBao.data
                }
                else{
                    this.$toast.info("Không có dữ liệu")
                    this.DataOrigin = ""
                }
            }
            catch(e){}
            finally{
                this.$root.showLoading(false);
            }
        },
        ChapNhan(){
            if(this.txtSoMay.value != ""){
                let data = {
                    somay: this.txtSoMay.value,
                    makh: this.txtMaKH.value,
                    tenkh: this.ten_kh,
                    mahd: this.ma_hd,
                    dichvuvt: this.dichvuvt_id,
                    loaihinh: this.loaihinh_id,
                    phanvung: this.phanvung_id
                }
                this.$emit("appect",data)
                this.closeModal()
            }
            else{
                this.$toast.info("Không có Số máy/Acc!")
                this.$refs.txtSoMay.focus()
            }
        },
        LocTheoDichVu(id){
            this.DsThueBao.data = this.DataOrigin.filter((e) => ( e.dichvuvt_id == id))
        },
        LocTheoLoaiHinh(id){
            this.DsThueBao.data = this.DataOrigin.filter((e) => ( e.loaitb_id == id))
        },
        select_selectedItemsChangedDsThueBao(value){
            // this.ten_kh = ""
            // this.ma_hd = ""
            // this.dichvuvt_id = ""
            // this.loaihinh_id = ""
            // this.phanvung_id = ""
            if(value[0]){
                this.txtMaKH.value = value[0].ma_kh
                this.txtSoMay.value = value[0].ma_tb
                this.txtSoAo.value = value[0].ma_lt
                this.ten_kh = value[0].ten_tb
                this.ma_hd = value[0].ma_hd
                this.dichvuvt_id = value[0].dichvuvt_id
                this.loaihinh_id = value[0].loaitb_id
                this.phanvung_id = value[0].phanvung_id
                // console.log(value[0])
            }
        },
        openModal() {
            this.$refs.popupMaTB.show()
        },
        closeModal () {
            this.reset()
            this.$refs.popupMaTB.hide()
        },
    }
}
</script>

<style scoped>

</style>