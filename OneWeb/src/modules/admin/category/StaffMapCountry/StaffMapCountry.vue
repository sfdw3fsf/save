<template src="./StaffMapCountry.html"></template>
<style src="./StaffMapCountry.css"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import moment from 'moment';
import api from './api'
import apiHelper from "./api.helper"
import themmoi from "./popup/themmoi.vue"
import axios from 'axios';
import { x } from 'caniuse-lite/data/browserVersions';
import { async } from 'pdfmake/build/pdfmake';

 
export default {
    name: "StaffMapCountry",
    components: {
        api,
        ActionTop,
        apiHelper,
        moment,
        breadcrumb,
        themmoi,
    },
    data(){
        return{
            header: {
                title: 'KHAI BÁO NHÓM NHÂN VIÊN - TỈNH/THÀNH PHỐ',
                list: [
                    { name: 'KHAI BÁO NHÓM NHÂN VIÊN - TỈNH/THÀNH PHỐ', link: { name: 'Ui.cards' } },
                    {
                        name: 'KHAI BÁO NHÓM NHÂN VIÊN - TỈNH/THÀNH PHỐ',
                        link: { name: 'Ui.buttons' }
                    }
                ]
            },
            Loading: false,
            actions: [
                {
                    id: "tracuu",
                    name: "Tra cứu",
                    active: true,
                    icon_class: "one-search"
                },
                {
                    id: "them",
                    name: "Thêm mới",
                    active: true,
                    icon_class: "icon one-file-plus"
                },
                {
                    id: "capnhat",
                    name: "Cập nhật",
                    active: true,
                    icon_class: "one-edit"
                },
                {
                    id: "xoa",
                    name: "Xóa",
                    active: true,
                    icon_class: "one-trash"
                },
                {
                    id: "tim",
                    name: "Tìm nhân viên",
                    active: false,
                    icon_class: "one-search"
                }
            ],
            NhanVienDaGan: {
                columns: [
                    {
                        fieldName: 'nhanvien_id', headerText: "Nhân viên ID",allowFiltering: true, allowSorting: false, width: "120"
                    },
                    {
                        fieldName: 'ma_nv', headerText: "Mã nhân viên",allowFiltering: true, allowSorting: false, width: "130"
                    },
                    {
                        fieldName: 'ten_nv', headerText: "Tên nhân viên",allowFiltering: true, allowSorting: false, width: "140"
                    },
                    {
                        fieldName: 'email', headerText: "Email",allowFiltering: true, allowSorting: false
                    },
                    {
                        fieldName: 'so_dt', headerText: "Số điện thoại",allowFiltering: true, allowSorting: false, width: "140"
                    },
                    {
                        fieldName: 'tentinh', headerText: "Tên tỉnh",allowFiltering: true, allowSorting: false, width: "140"
                    },
                    {
                        fieldName: 'ten_nhom', headerText: "Tên nhóm",allowFiltering: true, allowSorting: false, width: "140"
                    },
                ],
                data: [],
                selectedItems: []
            },
            NhanVienChuaGan: {
                columns: [
                    {
                        fieldName: 'nhanvien_id', headerText: "Nhân viên ID",allowFiltering: true, allowSorting: false, width: "120"
                    },
                    {
                        fieldName: 'ma_nv', headerText: "Mã nhân viên",allowFiltering: true, allowSorting: false, width: "130"
                    },
                    {
                        fieldName: 'ten_nv', headerText: "Tên nhân viên",allowFiltering: true, allowSorting: false, width: "140"
                    },
                    {
                        fieldName: 'email', headerText: "Email",allowFiltering: true, allowSorting: false
                    },
                    {
                        fieldName: 'so_dt', headerText: "Số điện thoại",allowFiltering: true, allowSorting: false, width: "140"
                    },
                    {
                        fieldName: 'tentinh', headerText: "Tên tỉnh",allowFiltering: true, allowSorting: false, width: "140"
                    },
                    {
                        fieldName: 'ten_dv', headerText: "Tên đơn vị",allowFiltering: true, allowSorting: false, width: "140"
                    },
                ],
                data: [],
                selectedItems: [],
            },
            ThongTinTraCuu: {
                columns: [
                    {
                        fieldName: 'nhanvien_id', headerText: "ID Nhóm",allowFiltering: true, allowSorting: false, width: 90
                    },
                    {
                        fieldName: 'ten_nhom', headerText: "Tên nhóm",allowFiltering: true, allowSorting: false, width: 90
                    },
                    {
                        fieldName: 'nguoi_cn', headerText: "Người cập nhật",allowFiltering: true, allowSorting: false, width: 90
                    },
                    {
                        fieldName: 'may_cn', headerText: "Máy cập nhật",allowFiltering: true, allowSorting: false, width: 90
                    },
                    {
                        fieldName: 'ngay_cn', headerText: "Ngày cập nhật",allowFiltering: true, allowSorting: false, width: 90
                    },
                    {
                        fieldName: 'ip_cn', headerText: "IP cập nhật",allowFiltering: true, allowSorting: false, width: 90
                    },
                ],
                data: [],
                value :""
            },
            cboThongTinTraCuu:{
                value :"",
                list: []
            },
            txtDuLieuTraCuu: {
                text: "",
                value: ""
            },
            txtTenNhom: {
                value: "",
                text: "Tên nhóm:"
            },
            txtMaNV: {
                value: ""
            },
            ID_nhom: "",
            thongTinNhom: []
        }
    },
    watch: {
        'txtMaNV.value': {
            handler(value){
                if(value.trim() != ""){
                    this.actions[4].active = true
                }
                else{
                    this.actions[4].active = false
                }
            }
        }
            
    },
    created(){
        // this.getDsNhanVien()
        this.getLoaiTraCuu()
    },
    methods: {
        onActionClick(action){
            switch(action.id){
                case "tracuu":
                    this.TraCuu()
                    break;
                case "capnhat":
                    this.CapNhat()
                    break;
                case "them":
                    this.Them()
                    break;
                case "xoa":
                    this.Xoa()
                    break;
                case "tim":
                    this.TimNhom()
                    break;
                case "default":
                    break;
            }
        },
        async getLoaiTraCuu(){
            this.$root.showLoading(true);
            try{
                const response = await api.Ds_tt_tracuu(this.axios)
                this.cboThongTinTraCuu.list = apiHelper.getDataFromResponseApiReturnArray(response) || []
            }
            catch(e){}
            finally{
                this.$root.showLoading(false);
            }
        },
        // async getDsNhanVien(){
        //     let data = {
        //         phanvung_id: this.$root.token.getPhanVungID()
        //     }
        //     try{
        //         this.$root.showLoading(true);
        //         const response = await api.Lay_ds_nv_chuagan(this.axios,data)
        //         this.NhanVienChuaGan.data = apiHelper.getDataFromResponseApiReturnArray(response) || []
        //         this.$root.showLoading(false);
        //     }
        //     catch(e){
        //         console.log("Error",e);
        //     }
        // },
        select_selectedItemsChangedDagan: function(selectedItems) {
            this.NhanVienDaGan.selectedItems = selectedItems
        },
        select_selectedItemsChangedChuagan: function(selectedItems) {
            // console.log(selectedItems);
            this.NhanVienChuaGan.selectedItems = selectedItems
        },
        async RightOne(){
            this.$root.showLoading(true)
            try{
                if (this.NhanVienDaGan.data.length > 0) {
                    var self = this
                    let selectedItems = this.NhanVienDaGan.selectedItems
                    if (selectedItems.length === 0) {
                        self.ShowError("Chưa chọn nhân viên")
                        
                    }
                    else{
                        var arr=[]
                        for(var i=0;i<selectedItems.length;i++){
                            // console.log(selectedItems[i]["nhanvien_id"]);
                            arr.push(selectedItems[i]["nhanvien_id"]);
                        }
                        // console.log(arr);
                        const arr2 = arr.map((x) => `{\"NHANVIEN_ID\": ${x}}` )
                        // console.log(arr2);
                        const stringData =  "["+ arr2.toString() + "]"
                        // console.log(stringData);
                        let input = {
                            nhanvien_id: this.thongTinNhom[0].nhanvien_id,
                            p_data: stringData
                        }
                        let res = []
                        const response = await api.Xoa_nhanvien(this.axios,input)
                        res = apiHelper.getDataFromResponseApiReturnArray(response) || []
                        if(res == "OK"){
                            this.$toast.success("Thành công")
                            this.NhanVienDaGan.data.checked = []
                            if(selectedItems.length == this.NhanVienDaGan.data.length){
                                this.ThongTinTraCuu.data = []
                            }
                            selectedItems.forEach(function(item) {
                                if(self.NhanVienChuaGan.data.filter((x) => x.nhanvien_id === item.nhanvien_id).length===0){
                                    // self.NhanVienChuaGan.data.push(item);
                                    item.ischecked = false;
                                    self.NhanVienChuaGan.data.unshift(item);
                                }
                            })
                        }
                        else{
                            this.$toast.info("Thất bại")
                        }
                        
                    }
                    self.NhanVienDaGan.data = self.NhanVienDaGan.data.filter((y) => selectedItems.filter((x) => x.nhanvien_id === y.nhanvien_id).length === 0)
                    let a = this.NhanVienDaGan.data.slice(0)
                    this.NhanVienDaGan.data = a.slice(0)
                    return 
                }
                else{
                    this.ShowError("Không có dữ liệu");
                    this.NhanVienChuaGan.data = []
                }
            }
            catch(e){
                console.log("Error",e);
                this.$toast.info("Lỗi")
            }
            finally{
                this.$root.showLoading(false)
            }
        },
        
        LeftOne: async function(){
            this.$root.showLoading(true)
            try{

                var self = this;
                if(this.NhanVienChuaGan.data.length > 0){
                    let selectedItems = this.NhanVienChuaGan.selectedItems;
                    if (selectedItems.length === 0) {
                        self.ShowError("Chưa chọn nhân viên")
                    }
                    else{
                        if(this.ThongTinTraCuu.data.length > 0){
                            // const sameMaNV = this.findSameMaNV(this.NhanVienDaGan.data,selectedItems)
                            // if(sameMaNV.length <= 0){
                            //     this.$toast.error("Đã có nhân viên khác trong nhóm")
                            //     return
                            // }
                            // const sameTentinh  = this.findSameTentinh(this.NhanVienDaGan.data,selectedItems)
                            // selectedItems = selectedItems.filter(item => !sameTentinh.includes(item.tentinh))
                            // if(sameTentinh.length > 0 && selectedItems.length == 0){
                            //     this.$toast.error("Không thể gán do trùng tỉnh")
                            //     return
                            // }
                            var arr=[]
                            for(var i=0;i<selectedItems.length;i++){
                                // console.log(selectedItems[i]["nhanvien_id"]);
                                let data = {
                                    MA_NV: selectedItems[i]["ma_nv"],
                                    NHANVIEN_ID: selectedItems[i]["nhanvien_id"],
                                    PHANVUNG_ID: selectedItems[i]["tinh_id"]
                                }
                                arr.push(JSON.parse(JSON.stringify(data)));
                            }
                            // console.log(arr);
                            // const arr2 = arr.map((x) => `{\"NHANVIEN_ID\": ${x},\"PHANVUNG_ID": ${this.$root.token.getPhanVungID()}}` )
                            // console.log(arr2);
                            const stringData = JSON.stringify(arr)
                            // console.log(stringData);
                            let input = {
                                p_phanvung_id: this.$root.token.getPhanVungID(),
                                p_tennhom: this.thongTinNhom[0].ten_nhom,
                                p_data: stringData,
                                p_nhanvien_id: this.thongTinNhom[0].nhanvien_id,
                                p_ngay_cn: this.thongTinNhom[0].ngay_cn,
                                nguoi_cn: this.thongTinNhom[0].nguoi_cn,
                                ip_cn: this.thongTinNhom[0].ip_cn,
                                may_cn: this.thongTinNhom[0].may_cn,
                            }
                            
                            let res = []
                            const response = await api.Chuyen_doi(this.axios,input)
                            res = apiHelper.getDataFromResponseApiReturnArray(response) || []
                            if(res == "OK"){
                                this.$toast.success("Thành công")
                                this.NhanVienChuaGan.data.checked = []
                                selectedItems.forEach(function(item) {
                                    if(self.NhanVienDaGan.data.filter((x) => x.nhanvien_id === item.nhanvien_id).length === 0){
                                        item.ischecked = false
                                        self.NhanVienDaGan.data.unshift(item);
                                    }
                                });
                                self.NhanVienChuaGan.data = self.NhanVienChuaGan.data.filter((y) => selectedItems.filter((x) => x.nhanvien_id === y.nhanvien_id).length === 0)
                                let a = this.NhanVienChuaGan.data.slice(0);
                                this.NhanVienChuaGan.data = a.slice(0);
                                return
                            }
                            else{
                                let msg = apiHelper.getSrtCodeError(response) || ""
                                this.$toast.error(msg)
                            }
                            
                        }
                        else{
                            this.$toast.error("Không có nhóm để gán")
                        }
                    }
                }
                else{
                    this.ShowError("Không có dữ liệu");
                    this.NhanVienChuaGan.data = []
                }
            }
            catch(e){
                console.log("Error",e);
                this.$toast.info("Lỗi")
            }
            finally{
                this.$root.showLoading(false)
            }
        },
        RightAll: async function(){
            this.$root.showLoading(true)
            try{

                var self = this
                
                if(this.NhanVienDaGan.data.length > 0){
                    
                    const confirm = await this.$confirm(
                        `Bạn có chắc chắn muốn bỏ gán toàn bộ nhân viên trong nhóm`,
                        `Bỏ gán nhân viên`,
                        {
                            confirmButtonText: 'Đồng ý',
                            cancelButtonText: 'Hủy',
                            type: 'warning',
                        },
                    )
                    if(confirm){
                        var arr=[]
                        for(var i=0;i<this.NhanVienDaGan.data.length;i++){
                            // console.log(this.NhanVienDaGan.data[i]["nhanvien_id"]);
                            arr.push(this.NhanVienDaGan.data[i]["nhanvien_id"]);
                        }
                        // console.log(arr);
                        const arr2 = arr.map((x) => `{\"NHANVIEN_ID\": ${x}}` )
                        // console.log(arr2);
                        const stringData =  "["+ arr2.toString() + "]"
                        // console.log(stringData);
                        let input = {
                            nhanvien_id: this.thongTinNhom[0].nhanvien_id,
                            p_data: stringData
                        }
                        let res = []
                        const response = await api.Xoa_nhanvien(this.axios,input)
                        res = apiHelper.getDataFromResponseApiReturnArray(response) || []
                        // console.log(res);
                        if(res == "OK"){
                            this.$toast.success("Thành công")
                            let listItems = this.NhanVienDaGan.data
                            for(const item of listItems){
                                self.NhanVienChuaGan.data.unshift(item)
                            }
                            this.NhanVienDaGan.data = []
                            this.ThongTinTraCuu.data = []
                        }
                        else{
                            this.$toast.error("Error")
                        }
                    }  
                }
                else{
                    this.ShowError("Không có dữ liệu");
                    this.NhanVienChuaGan.data = []
                }
            }
            catch(e){
                console.log("Error",e);
            }
            finally{
                this.$root.showLoading(false)
            }
            
        },
        LeftAll: async function(){
            this.$root.showLoading(true)
            try{

                var self = this
                if(this.NhanVienChuaGan.data.length > 0){
                    const confirm = await this.$confirm (
                        `Bạn có chắc chắn muốn gán toàn bộ nhân viên cho nhóm`,
                        `Gán nhân viên`,
                        {
                            confirmButtonText: "Đồng ý",
                            cancelButtonText: "Hủy",
                            type: "warning"
                        }
                    )
                    
                    if(confirm){
                        if(this.ThongTinTraCuu.data.length > 0){
                            // const sameMaNV = this.findSameMaNV(this.NhanVienDaGan.data,this.NhanVienChuaGan.data)
                            // if(sameMaNV.length <= 0){
                            //     this.$toast.error("Đã có nhân viên khác trong nhóm")
                            //     return
                            // }

                            // const sameTentinh_v2  = this.findSameTentinh(this.NhanVienDaGan.data,selectedItems)
                            // this.NhanVienChuaGan.data = this.NhanVienChuaGan.data.filter(item => !sameTentinh_v2.includes(item.tentinh))
                            // if(sameTentinh_v2.length > 0 && this.NhanVienChuaGan.data.length == 0){
                            //     this.$toast.error("Không thể gán do trùng tỉnh")
                            //     return
                            // }
                            var arr=[]
                            for(var i=0;i<this.NhanVienChuaGan.data.length;i++){
                                // console.log(this.NhanVienDaGan.data[i]["nhanvien_id"]);
                                let data = {
                                    MA_NV: this.NhanVienChuaGan.data[i]["ma_nv"],
                                    NHANVIEN_ID: this.NhanVienChuaGan.data[i]["nhanvien_id"],
                                    PHANVUNG_ID: this.NhanVienChuaGan.data[i]["tinh_id"]
                                    // PHANVUNG_ID: this.$root.token.getPhanVungID()
                                }
                                // arr.push(this.NhanVienChuaGan.data[i]["nhanvien_id"]);
                                arr.push(JSON.parse(JSON.stringify(data)))
                            }
                            // const arr2 = arr.map((x) => `{\"NHANVIEN_ID\": ${x},\"PHANVUNG_ID": ${this.$root.token.getPhanVungID()}}` )
                            // const stringData =  "["+ arr2.toString() + "]"
                            const stringData = JSON.stringify(arr)
                            // console.log(stringData);
                            let input = {
                                p_phanvung_id: this.$root.token.getPhanVungID(),
                                p_tennhom: this.thongTinNhom[0].ten_nhom,
                                p_data: stringData,
                                p_nhanvien_id: this.thongTinNhom[0].nhanvien_id,
                                p_ngay_cn: this.thongTinNhom[0].ngay_cn,
                                nguoi_cn: this.thongTinNhom[0].nguoi_cn,
                                ip_cn: this.thongTinNhom[0].ip_cn,
                                may_cn: this.thongTinNhom[0].may_cn,
                                
            
                            }
                            let res = []
                            const response = await api.Chuyen_doi(this.axios,input)
                            res = apiHelper.getDataFromResponseApiReturnArray(response) || []
                            // console.log(res);
                            if(res == "OK"){
                                this.$toast.success("Thành công")
                                let listItems = this.NhanVienChuaGan.data
                                for(const item of listItems){
                                    self.NhanVienDaGan.data.unshift(item)
                                }
                                this.NhanVienChuaGan.data = []
                            }
                            else{
                                let msg = apiHelper.getSrtCodeError(response) || ""
                                this.$toast.error(msg)
                            }
                                
                        }
                        else{
                            this.$toast.error("Không có nhóm để gán")
                        }
                    }
                }
                else{
                    this.ShowError("Không có dữ liệu");
                    this.NhanVienChuaGan.data = []
                }
            }
            catch(e){
                console.log("Error",e);
                
            }
            finally{
                this.$root.showLoading(false)
            }
        },
        findSameMaNV(arr1, arr2) {
            const ma_nvSet1 = new Set(arr1.map(item => item.ma_nv));
            const ma_nvSet2 = new Set(arr2.map(item => item.ma_nv));

            const intersection = [...ma_nvSet1].filter(ma_nv => ma_nvSet2.has(ma_nv));
            return intersection;
        },
        findSameTentinh(arr1, arr2) {
            const tentinhSet1 = new Set(arr1.map(item => item.tentinh));
            const tentinhSet2 = new Set(arr2.map(item => item.tentinh));

            const intersection = [...tentinhSet1].filter(tentinh => tentinhSet2.has(tentinh));
            return intersection;
        },
        async TraCuu(){
            this.$root.showLoading(true);
            try{
                if(this.cboThongTinTraCuu.value == "" || this.txtDuLieuTraCuu.value.trim() == ""){
                    if(this.cboThongTinTraCuu.value == ""){
                        this.$toast.error("Bạn phải chọn loại tra cứu")
                        document.getElementById("cboThongTinTraCuu").focus()
                    }
                    else{
                        this.$toast.error("Bạn phải nhập dữ liệu cần tra cứu")
                        this.$refs.txtDuLieuTraCuu.focus()
                    }``
                }
                else{ 
                    let input = {
                         p_nhom_id: this.cboThongTinTraCuu.value,
                         p_noidung: this.txtDuLieuTraCuu.value.trim()
                    }
                    const response = await api.Tracuu_cbo(this.axios,input)
                    this.ThongTinTraCuu.data = apiHelper.getDataFromResponseApiReturnArray(response) || []
                    if(this.ThongTinTraCuu.data.length > 0){
                        this.$toast.success("Tra cứu thành công")
                    }
                    else{
                            this.$toast.info("Không có dữ liệu")
                            this.ThongTinTraCuu.data = []
                            this.NhanVienDaGan.data = []
                            this.NhanVienChuaGan.data = []
                    }
                }
            }
            catch(e){}
            finally{
                this.$root.showLoading(false);
            }
        },

        async TraCuu_NoMessage(){
            this.$root.showLoading(true);
            try{
                if(this.cboThongTinTraCuu.value == "" || this.txtDuLieuTraCuu.value == ""){
                    if(this.cboThongTinTraCuu.value == ""){
                        this.$toast.error("Bạn phải chọn loại tra cứu")
                        
                    }
                    else{
                        this.$toast.error("Bạn phải nhập dữ liệu cần tra cứu")
                        this.$refs.txtDuLieuTraCuu.focus()
                    }
                }
                else{ 
                    let input = {
                         p_nhom_id: this.cboThongTinTraCuu.value,
                         p_noidung: this.txtDuLieuTraCuu.value
                    }
                    const response = await api.Tracuu_cbo(this.axios,input)
                    this.ThongTinTraCuu.data = apiHelper.getDataFromResponseApiReturnArray(response) || []
                    if(this.ThongTinTraCuu.data.length > 0){
                    }
                    else{
                            this.ThongTinTraCuu.data = []
                            this.NhanVienDaGan.data = []
                            this.NhanVienChuaGan.data = []
                    }
                }
            }
            catch(e){}
            finally{
                this.$root.showLoading(false);
            }
        },
        async Them(){
            this.$refs['refThemMoi'].showModal()
        },
        async loadThemMoi(value){
            this.txtMaNV.value = ""
            this.NhanVienChuaGan.data = []
            let input = {
                p_nhom_id: 2,  
                p_noidung: value
            }
            try{
                const response = await api.Tracuu_cbo(this.axios,input)
                this.ThongTinTraCuu.data = apiHelper.getDataFromResponseApiReturnArray(response) || []
                if(this.ThongTinTraCuu.data.length > 0){
                    // this.getDsNhanVien()
                    let nhanvien_id = {
                        nhom_id: this.ThongTinTraCuu.data[0].nhanvien_id
                    }
                    const response = await api.Lay_ds_nv_dagan(this.axios,nhanvien_id)
                    this.NhanVienDaGan.data = apiHelper.getDataFromResponseApiReturnArray(response) || []
                    this.txtTenNhom.value = this.NhanVienDaGan.data[0].ten_nhom
                    this.$root.showLoading(false);
                }
                else{
                    this.NhanVienDaGan.data = []
                    this.ThongTinTraCuu.data = []
                    this.$root.showLoading(false);
                }
            }
            catch(e){
                this.NhanVienDaGan.data = []
                this.ThongTinTraCuu.data = []
                // console.log("Error",e);
                this.$toast.error("Không có dữ liệu")
                this.$root.showLoading(false);
            }
        },
        async Xoa(){
            try{
                if(this.ThongTinTraCuu.data <= 0){
                    this.$toast.info("Không có thông tin nhóm để xóa")
                }
                else{
                    if(this.ID_nhom != ""){
                        const confirm = await this.$confirm(
                        `Bạn có chắc chắn muốn xóa nhóm`,
                        'Xóa nhóm',
                            {
                                confirmButtonText: 'Đồng ý',
                                cancelButtonText: 'Hủy',
                                type: 'warning',
                            },
                        );
                        if(confirm){
                            let input = {
                                nhom_id: this.ID_nhom
                            }
                            const response = await api.Xoa_nhom(this.axios,input)
                            let result = apiHelper.getDataFromResponseApiReturnArray(response) || []
                            if(result == "OK"){
                                this.$toast.success("Xóa thành công")
                                this.NhanVienChuaGan.data = []
                                this.NhanVienDaGan.data = []
                                this.ThongTinTraCuu.data = []
                                this.txtDuLieuTraCuu.value = ""
                            }
                            else{
                                this.$toast.info("Xóa thất bại")
                                this.thongTinNhom = []
                            }   
                        }
                    }
                    else{
                        this.$toast.error("Bạn hãy chọn 1 nhóm muốn xóa")
                    }
                }
            }
            catch(e){
                console.log("Error");
                
            }
            
        },
        async select_selectedItemsChangedThongTinTraCuu(selectedItem){
            this.$root.showLoading(true);
            this.ID_nhom = selectedItem[0].nhanvien_id
            this.thongTinNhom = []
            this.thongTinNhom = selectedItem
            try{
                // this.getDsNhanVien()
                let nhanvien_id = {
                    nhom_id: this.ID_nhom
                }
                const response = await api.Lay_ds_nv_dagan(this.axios,nhanvien_id)
                this.NhanVienDaGan.data = apiHelper.getDataFromResponseApiReturnArray(response) || []
                this.txtTenNhom.value = selectedItem[0].ten_nhom
                this.$root.showLoading(false);
            }
            catch(e){
                console.log("Error",e);
                this.$root.showLoading(false);
            }
        },
        async CapNhat(){
            try{
                this.$root.showLoading(true);
                if(this.NhanVienDaGan.data.length > 0){
                    if( this.ID_nhom != ""){

                        if(this.txtTenNhom.value != "" && this.txtTenNhom.value != null){

                            const confirm = await this.$confirm(
                            `Bạn có chắc chắn muốn đổi tên nhóm thành: ${this.txtTenNhom.value}`,
                            'Cập nhật tên nhóm',
                                {
                                    confirmButtonText: 'Đồng ý',
                                    cancelButtonText: 'Hủy',
                                    type: 'warning',
                                },
                            );
                            if(confirm){
                                let input = {
                                    p_nhanvien_id:  this.ID_nhom, 
                                    p_tennhom: this.txtTenNhom.value
                                }
                                const response = await api.Capnhat(this.axios,input)
                                let result = apiHelper.getDataFromResponseApiReturnArray(response) || []
                                if(result == "OK"){
                                    this.txtDuLieuTraCuu.value = this.txtTenNhom.value
                                    this.cboThongTinTraCuu.value = "2"
                                    this.$toast.success(`Cập nhật thành công`)
                                    this.$root.showLoading(false);
                                    this.TraCuu_NoMessage()
                                }
                                else{
                                    this.$toast.info(`Cập nhật thất bại`)
                                    this.$root.showLoading(false);
                                }
                            }
                            this.$root.showLoading(false);
                        }
                        else{
                            this.$toast.error("Bạn phải nhập tên nhóm muốn cập nhật")
                            this.$root.showLoading(false);
                            this.$refs.txtTenNhom.focus()
                        }
                    }
                    else{
                        this.$toast.error("Chọn nhóm muốn cập nhật")
                    }
                }
                else{
                    this.$toast.error("Bạn hãy tra cứu trước")
                    this.$refs.txtDuLieuTraCuu.focus()
                    this.$root.showLoading(false);
                }
                
            }
            catch(e){
                console.log("Error",e);
                this.$root.showLoading(false);
            }
        },
        async TimNhom(){
            this.$root.showLoading(true)
            try{
                if(this.txtMaNV.value.trim() != ""){
                    let input = {
                        ma_nv: this.txtMaNV.value.trim()
                    }
                    const response = await api.Lay_ds_nv_chuagan(this.axios,input)
                    this.NhanVienChuaGan.data = apiHelper.getDataFromResponseApiReturnArray(response) || []
                    if(this.NhanVienChuaGan.data.length > 0){
                        this.$toast.success("Tìm nhóm nhân viên thành công!")
                    }
                    else{
                        this.$toast.info("Không có dữ liệu")
                        this.NhanVienChuaGan.data = []
                        this.thongTinNhom = []
                    }
                }
                else{
                    this.$toast.info("Chưa nhập Mã nhân viên!")
                }
            }
            catch(e){
                console.log("Error",e);
            }
            finally{
                this.$root.showLoading(false)
            }
        },
        ShowSuccess(message){
            this.$toast.success(message);
        },
        ShowError(message){
            this.$toast.error(message)
        }


    }
}

</script>

