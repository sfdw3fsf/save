<template src="./SynchronizeElectronicInvoices.html"></template>
<style scoped src="./SynchronizeElectronicInvoices.scss"></style>
<script>
import API from './API.js'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import DataGrid2 from '@/components/Controls/DataGrid2'
import EventBus from '@/utils/eventBus'
import Vue from 'vue'

export default {
    name: 'SynchronizeElectronicInvoices',
    components: {
        breadcrumb,
        DatePicker,
        DataGrid2
    },
    data() {
        return {
            dateFormat: "DD/MM/YYYY",
            animationSettings: { effect: 'Zoom' },
            header: {
                title: "Tra cứu hoá đơn điện tử khách hàng",
                list: [
                    //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
                    { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
                    { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } },
                ]
            },
            tt78: false,
            fkey: "",
            pattern: "",
            serial: "",
            daThanhToan: false,
            frm: {
                loaiTimKiem: 'matt',
                maTT: "",
                fkey: "",
                tuNgay: "",
                denNgay: "",
                chkTuNgay: false,
                chkDenNgay: false
            },
            dsHoaDon: {
                list: [],
                value: {}
            },
            downloadInvTemplate: function () {
                return { template : Vue.component('columnTemplate', {
                    template: `<div><button class="btn btn-primary lh14 pad3" @click="onDownloadInv" title="Tải file .inv">
                                    <span class="-ap icon-download"></span>
                                </button></div>`,                        
                    data () { 
                        return { data: {} }; 
                    },
                    methods: {
                        onDownloadInv() {                  
                            EventBus.$emit('onDownloadInv', this.data);
                        }
                    }})
                }
            },
            downloadPdfTemplate: function () {
                return { template : Vue.component('columnTemplate', {
                    template: `<div><button class="btn btn-primary lh14 pad3" @click="onDownloadPdf" title="Tải file .pdf">
                                    <span class="-ap icon-download"></span>
                                </button></div>`,                        
                    data () { 
                        return { data: {} }; 
                    },
                    methods: {
                        onDownloadPdf() {                  
                            EventBus.$emit('onDownloadPdf', this.data);
                        }
                    }})
                }
            },
            viewInvTemplate: function () {
                return { template : Vue.component('columnTemplate', {
                    template: `<div><a href="javascript: void(0)" @click="onViewInv" title="Xem chi tiết"><span></span>Xem chi tiết</a></div>`,
                    data () { 
                        return { data: {} }; 
                    },
                    methods: {
                        onViewInv() {                  
                            EventBus.$emit('onViewInv', this.data);
                        }
                    }})
                }
            },
            newContent() {
                return {
                index: "",
                invToken: "",
                fkey: "",
                name: "",
                publishDate: "",
                signStatus: "",
                signStatus_chu: "",
                total: "",
                amount: "",
                pattern: "",
                serial: "",
                invNum: "",
                status: "",
                status_chu: "",
                cusname: "",
                payment: "",
                payment_chu: "",
                detail: "Xem chi tiết",
                };
            },
        }
    },
    async created () {
        EventBus.$on('onDownloadInv', this.onDownloadInvHandler);
        EventBus.$on('onDownloadPdf', this.onDownloadPdfHandler);
        EventBus.$on('onViewInv', this.onViewInvHandler);
    },

    destroyed () {      
        EventBus.$off('onDownloadInv', this.onDownloadInvHandler);      
        EventBus.$off('onDownloadPdf', this.onDownloadPdfHandler);
        EventBus.$off('onViewInv', this.onViewInvHandler);
    },
    computed: {
        tuNgay(){
            if(this.frm.chkTuNgay == true)
                return this.frm.tuNgay
            else
                return ""
        },
        denNgay(){
            if(this.frm.chkDenNgay == true)
                return this.frm.denNgay
            else
                return ""
        }
    },
    watch: {
        'frm.loaiTimKiem': function(){
            if(this.frm.loaiTimKiem == 'matt'){
                this.$refs.maTT.disabled = false
                this.$refs.fkey.disabled = true
            }
            else{
                this.$refs.maTT.disabled = true
                this.$refs.fkey.disabled = false
            }
        }
    },
    async mounted(){
        this.loading(true)
        try{
            let params = {
                "ds_ma_ts": [
                    "HDDT_THONGTU_78"
                ]
            }
            await API.lay_ds_thamso_md_ds_mats(this.axios, params)
            .then(response => {
                this.loading(false)
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    if(response.data.data.length > 0 && response.data.data[0].ten_ts == '1')
                        this.tt78 = true
                }
            })
        } catch(e){
            this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            this.loading(false)
        }
    },
    methods: {
        traCuu(){
            if(this.frm.loaiTimKiem == 'matt'){
                if(this.frm.maTT != ""){
                    this.traCuuTheoMaTT()
                }
                else{
                    this.$toast.error('Chưa nhập mã thanh toán !')
                    this.$refs.maTT.focus()
                }
            }
            else{
                if(this.frm.fkey != ""){
                    this.traCuuTheoFkey()
                }
                else{
                    this.$toast.error('Chưa nhập Fkey !')
                    this.$refs.fkey.focus()
                }
            }
        },
        async chuyenTT(){
            if(this.fkey == "")
                return 

            this.loading(true)
            try{
                let params = {
                    "fKey": this.fkey,
                    // "metaClass": {},
                    // "pattern": this.pattern,
                    // "serial": this.serial
                }
                await API.confirmPaymentFkey(this.axios, params)
                .then(response => {
                    this.loading(false)
                    if(response.data.errorCode != 0){
                        this.$toast.error(response.data.faultString)
                        return
                    }
                    else{
                        if(this.frm.loaiTimKiem == 'matt')
                            this.traCuuTheoMaTT()
                        else
                            this.traCuuTheoFkey()

                        this.$toast.success('Cập nhật trạng thái hóa đơn thành công!')
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
                this.loading(false)
            }
        },
        async huy(){
            if(this.fkey == "")
                return 

            this.loading(true)
            try{
                let params = {
                    "lstFkey": this.fkey,
                }
                await API.unConfirmPaymentFkey(this.axios, params)
                .then(response => {
                    this.loading(false)
                    if (response.data.errorCode != 0) {
                        this.$toast.error('Có lỗi trong quá trình cập nhật trạng thái hoá đơn!')
                    }
                    else{
                        if(this.frm.loaiTimKiem == 'matt')
                            this.traCuuTheoMaTT()
                        else
                            this.traCuuTheoFkey()

                        this.$toast.success('Cập nhật trạng thái hóa đơn thành công!')
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
                this.loading(false)
            }
        },
        selectedRowChanged(item){
            if (item.payment == 1){//Đã thanh toán
                this.daThanhToan = true
            }
            else{
                this.daThanhToan = false
            }

            if(this.frm.loaiTimKiem == 'matt'){
                this.fkey = item.fkey
            }
            else{
                this.fkey = this.frm.fkey
            }
            this.pattern = item.pattern
            this.serial = item.serial
        },
        timTheoMaTT(){
            if(this.frm.loaiTimKiem == 'matt'){
                if(this.frm.maTT != ""){
                    this.traCuuTheoMaTT()
                }
                else{
                    this.$toast.error('Chưa nhập mã thanh toán !')
                    this.$refs.maTT.focus()
                }
            }
        },
        timTheoFkey(){
            if(this.frm.loaiTimKiem == 'fkey'){
                if(this.frm.fkey != ""){
                    this.traCuuTheoFkey()
                }
                else{
                    this.$toast.error('Chưa nhập Fkey !')
                    this.$refs.fkey.focus()
                }
            }
        },
        async layHdkhId(maTT, defaultValue){
            try{
                let response = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_khachhang', {
                    param: maTT,
                    type: 7
                })
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                return defaultValue
            }
        },
        async traCuuTheoMaTT(){
            let hdkh_id = Number(await this.layHdkhId(this.frm.maTT, '0'))

            if (hdkh_id > 0){
                this.$toast.error("Không thực hiện thay đổi trạng thái HĐĐT của hợp đồng ở chức năng này")
                return;
            }

            if(!this.tt78) 
                return
                
            this.loading(true)
            this.dsHoaDon.list = []
            try{
                let params = {
                    "cusCode": this.frm.maTT,
                    "fromDate": this.tuNgay,
                    "toDate": this.denNgay
                }
                let kq = await API.listInvByCus(this.axios, params)
                this.loading(false)
                // if(kq.data.data.substring(0,3) == "ERR"){
                if(kq.data.errorCode != 0){
                    this.$toast.error(kq.data.faultString)
                    return
                }
                else{
                    let objKetQua = this.ObjectToXML(kq.data.data)
                    let obj = this.newContent()

                    if(objKetQua.getElementsByTagName('Item').length > 0){
                        let stt = 0
                        for(var i =0;i<objKetQua.getElementsByTagName('Item').length;i++){
                            stt = Number(stt) + 1
                            obj.index = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('index')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('index')[0].innerHTML : '')
                            obj.invToken = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('invToken')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('invToken')[0].innerHTML : '')
                            obj.fkey = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('fkey')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('fkey')[0].innerHTML : '')
                            obj.name = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('name')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('name')[0].innerHTML : '')
                            obj.publishDate = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('publishDate')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('publishDate')[0].innerHTML : '')
                            obj.signStatus = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('signStatus')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('signStatus')[0].innerHTML : '')
                            obj.signStatus_chu = (obj.signStatus == 0 ? "Chưa xem" : "Đã xem"),
                            obj.total = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('total')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('total')[0].innerHTML : '')
                            obj.amount = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('amount')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('amount')[0].innerHTML : '')
                            obj.pattern = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('pattern')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('pattern')[0].innerHTML : '')
                            obj.serial = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('serial')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('serial')[0].innerHTML : '')
                            obj.invNum = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('invNum')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('invNum')[0].innerHTML : '')
                            obj.status = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('status')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('status')[0].innerHTML : '')
                            obj.status_chu = (obj.status == 1 ? "Hóa đơn đã phát hành" : obj.status == 3 ? "Hóa đơn đã thay thế" : obj.status == 4 ? "Hóa đơn đã sửa đổi" : "Hóa đơn đã hủy"),
                            obj.cusname = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('cusname')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('cusname')[0].innerHTML : '')
                            obj.payment = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('payment')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('payment')[0].innerHTML : '')
                            obj.payment_chu = (obj.payment == 1 ? "Đã thanh toán" : "Chưa thanh toán")

                            let a = obj.cusname.replace('<![CDATA[','')
                            let b = a.replace(']]>','')

                            this.dsHoaDon.list.push({ 
                                "stt": stt,
                                "index":obj.index,
                                "invToken":obj.invToken,
                                "fkey":obj.fkey,
                                "name":obj.name,
                                "publishDate":obj.publishDate !== '' ? moment(obj.publishDate, "MM/DD/YYYY h:mm:ss A").format("DD/MM/YYYY HH:mm:ss") : '',
                                "signStatus":obj.signStatus,
                                "signStatus_chu" : obj.signStatus_chu,
                                "total":obj.total,
                                "amount":obj.amount,
                                "pattern":obj.pattern,
                                "serial":obj.serial,
                                "invNum":obj.invNum,
                                "status":obj.status,
                                "status_chu":obj.status_chu,
                                "cusname":b,
                                "payment":obj.payment,
                                "payment_chu":obj.payment_chu,
                                "detail":obj.detail,
                            })
                        }
                    }
                    else{
                        this.$toast.error("Không tìm thấy thông tin hóa đơn !")
                        return
                    }  
                }
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
                this.loading(false)
            }
        },
        async traCuuTheoFkey(){
            this.loading(true)
            this.dsHoaDon.list = []
            try{
                let params = {
                    "key": this.frm.fkey,
                    "fromDate": this.tuNgay,
                    "toDate": this.denNgay
                }
                let kq = await API.listInvByCusFkey(this.axios, params)
                this.loading(false)
                // if(kq.data.data.substring(0,3) == "ERR"){
                if(kq.data.errorCode != 0){
                    this.$toast.error(kq.data.faultString)
                    return
                }
                else{
                    let objKetQua = this.ObjectToXML(kq.data.data)
                    let obj = this.newContent()

                    if(objKetQua.getElementsByTagName('Item').length > 0){
                        let stt = 0
                        for(var i =0;i<objKetQua.getElementsByTagName('Item').length;i++){
                            stt = Number(stt) + 1
                            obj.index = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('index')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('index')[0].innerHTML : '')
                            obj.invToken = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('invToken')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('invToken')[0].innerHTML : '')
                            obj.fkey = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('fkey')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('fkey')[0].innerHTML : '')
                            obj.name = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('name')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('name')[0].innerHTML : '')
                            obj.publishDate = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('publishDate')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('publishDate')[0].innerHTML : '')
                            obj.signStatus = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('signStatus')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('signStatus')[0].innerHTML : '')
                            obj.signStatus_chu = (obj.signStatus == 0 ? "Chưa xem" : "Đã xem"),
                            obj.total = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('total')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('total')[0].innerHTML : '')
                            obj.amount = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('amount')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('amount')[0].innerHTML : '')
                            obj.pattern = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('pattern')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('pattern')[0].innerHTML : '')
                            obj.serial = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('serial')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('serial')[0].innerHTML : '')
                            obj.invNum = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('invNum')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('invNum')[0].innerHTML : '')
                            obj.status = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('status')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('status')[0].innerHTML : '')
                            obj.status_chu = (obj.status == 1 ? "Hóa đơn đã phát hành" : obj.status == 3 ? "Hóa đơn đã thay thế" : obj.status == 4 ? "Hóa đơn đã sửa đổi" : "Hóa đơn đã hủy"),
                            obj.cusname = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('cusname')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('cusname')[0].innerHTML : '')
                            obj.payment = (objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('payment')[0] ? objKetQua.getElementsByTagName('Item')[i].getElementsByTagName('payment')[0].innerHTML : '')
                            obj.payment_chu = (obj.payment == 1 ? "Đã thanh toán" : "Chưa thanh toán")

                            let a = obj.cusname.replace('<![CDATA[','')
                            let b = a.replace(']]>','')

                            this.dsHoaDon.list.push({ 
                                "stt": stt,
                                "index":obj.index,
                                "invToken":obj.invToken,
                                "fkey":obj.fkey,
                                "name":obj.name,
                                "publishDate":obj.publishDate !== '' ? moment(obj.publishDate, "MM/DD/YYYY h:mm:ss A").format("DD/MM/YYYY HH:mm:ss") : '',
                                "signStatus":obj.signStatus,
                                "signStatus_chu" : obj.signStatus_chu,
                                "total":obj.total,
                                "amount":obj.amount,
                                "pattern":obj.pattern,
                                "serial":obj.serial,
                                "invNum":obj.invNum,
                                "status":obj.status,
                                "status_chu":obj.status_chu,
                                "cusname":b,
                                "payment":obj.payment,
                                "payment_chu":obj.payment_chu,
                                "detail":obj.detail,
                            })
                        }
                    }
                    else{
                        this.$toast.error("Không tìm thấy thông tin hóa đơn !")
                        return
                    }  
                }
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
                this.loading(false)
            }
        },
        ObjectToXML(filexml){
            // var parser = new DOMParser()
            // var xmlDoc = parser.parseFromString(filexml,"text/xml")
            // return xmlDoc.getElementsByTagName(type)
            let xmlDoc
            if (window.DOMParser) {
                const parser = new DOMParser()
                xmlDoc = parser.parseFromString(filexml.trim(), 'text/xml')
            } else {
                xmlDoc = new ActiveXObject('Microsoft.XMLDOM')
                xmlDoc.async = false
                xmlDoc.loadXML(filexml.trim())
            }
            return xmlDoc
        },
        async onDownloadInvHandler(item) {
            let rs = await this.postDownloadInvFkeyNoPayXml(item.fkey);
            if (rs !== null) {
                let content = rs;
                var FileSaver = require("file-saver");
                var file = new File([content], item.invNum + '.inv', {
                    type: "application/inv"
                });
                FileSaver.saveAs(file);
            }
        },

        async onDownloadPdfHandler(item) {
            let rs = await this.postDownloadInvPDFFkeyNoPay(item.fkey);
            if (rs !== null) {
                let content = Buffer.from(rs, 'base64');
                var FileSaver = require("file-saver");
                var file = new File([content], item.invNum, {
                    type: "application/pdf"
                });
                FileSaver.saveAs(file);
            }
        },        

        async onViewInvHandler(item) {
                let rs = await this.getInvViewNoPay(item.invToken);
            if (rs !== null) {
                var newTab = window.open('', '_blank');
                newTab.document.body.innerHTML = rs;
            }
        },

        async postDownloadInvFkeyNoPayXml(fkey) {
            try {
                this.loading(true);
                let apiBody = {
                    fkey: fkey,
                    phanvung_id: this.$auth.getPhanVungID()
                };
                let response = await API.postDownloadInvFkeyNoPayXml(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000') {
                    return response.data.data.response;
                } else return null;
            }
            catch (error) {
                if (error.data.message) {
                    this.$toast.error(error.data.message);
                } else this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                return null;
            }
            finally {
                this.loading(false);
            }
        },

        async postDownloadInvPDFFkeyNoPay(fkey) {
            try {
                this.loading(true);
                let apiBody = {
                    fkey: fkey,
                    phanvung_id: this.$auth.getPhanVungID()
                };
                let response = await API.postDownloadInvPDFFkeyNoPay(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000') {
                    return response.data.data.response;
                } else return null;
            }
            catch (error) {
                if (error.data.message)
                    this.$toast.error(error.data.message);
                else this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                return null;
            }
            finally {
                this.loading(false);
            }
        },

        async getInvViewNoPay(invToken) {
            try {
                this.loading(true);
                let apiBody = {
                    invToken: invToken,
                    phanvung_id: this.$auth.getPhanVungID()
                };
                let response = await API.getInvViewNoPay(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000') {
                    return response.data.data.response;
                } else return null;
            }
            catch (error) {
                if (error.data.message)
                    this.$toast.error(error.data.message);
                else this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                return null;
            }
            finally {
                this.loading(false);
            }
        }
    }
}
</script>