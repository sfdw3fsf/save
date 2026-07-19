<template src="./index.template.html"></template>
<style scoped src="../TiepNhanThongTinKhoaMoNoCuoc/style.scss"></style>
<script>
import API from '../api/HoaDonDienTuAPI.js'
import EventBus from '@/utils/eventBus'
import moment from 'moment'
import Vue from 'vue'

export default {
    components: {},

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

    mounted() {
        this.$refs.maTT.focus();
    },

    data() {
        return {
            header: {
                title: 'THAY ĐỔI TRẠNG THÁI HÓA ĐƠN ĐIỆN TỬ',
                list: [
                    { name: 'Hóa đơn', link: { name: 'Ui.cards' } },
                    { name: 'Hóa đơn điện tử', link: { name: 'Ui.buttons' } },
                    { name: 'Thay đổi trạng thái hóa đơn điện tử', link: { name: 'Ui.buttons' } },
                ]
            },
            timKiem: 0, //Tìm kiếm theo: 0 - maTT; 1 - fkey
            maTT: '',
            fkey: '',
            today: new Date(),
            tuNgay_Checked: false,
            tuNgay: new Date(),
            denNgay_Checked: false,
            denNgay: new Date(),
            common_params: {
                chuyenTTBtn_Enable: true,
                huyTTBtn_Enable: true,
                fkey: ''
            },
            hoaDon_grid: {
                items: [],
                selected: {}
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
            downloadXmlTemplate: function (){
                return { template : Vue.component('columnTemplate', {
                    template: `<div><button class="btn btn-primary lh14 pad3" @click="onDownloadXml" title="Tải file .xml">
                                    <span class="-ap icon-download"></span>
                                </button></div>`,
                    data () {
                        return { data: {} };
                    },
                    methods: {
                        async onDownloadXml() {
                            await this.$parent.$parent.$parent.downloadXML(this.data)
                        }
                    }})
                }
            },
            viewInvTemplate: function () {
                return { template : Vue.component('columnTemplate', {
                    template: `<div><a class="text-danger" href="javascript: void(0)" @click="onViewInv" title="Xem chi tiết hóa đơn điện tử"><span></span>Xem chi tiết</a></div>`,
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
        }
    },
    computed: {
        returnDanhSachHoaDon(){
            let array = this.hoaDon_grid.items.sort(this.sortFunction)
            array = array.map((item,index)=>({...item,stt:++index}))
            return array
        }
    },
    methods: {
        sortFunction(a,b){
            var dateA = new Date(a.publishDate).getTime();
            var dateB = new Date(b.publishDate).getTime();
            return dateA < dateB ? 1 : -1;
        },
        async onMaTTPress() {
            this.maTT = this.maTT.trim();
            if (this.maTT !== '') {
                if (this.onValidate()) {
                    let rs = await this.getId(this.maTT);
                    if (rs !== null && rs > 0)
                        return this.$toast.error('Không thực hiện thay đổi trạng thái HĐĐT của hợp đồng ở chức năng này!');
                    await this.getDSHoaDonByMaTT();
                }
            } else {
                this.$toast.error('Chưa nhập mã thanh toán!');
                this.$refs.maTT.focus();
            }
        },

        async onFKeyPress() {
            this.fkey = this.fkey.trim();
            if (this.fkey !== '') {
                if (this.onValidate())
                    await this.getDSHoaDonByFkey();
            } else {
                this.$toast.error('Chưa nhập Fkey!');
                this.$refs.fkey.focus();
            }
        },

        async onTraCuu() {
            if (this.timKiem === 0)
                await this.onMaTTPress();
            else await this.onFKeyPress();
        },

        onValidate() {
            let errors = [];
            if (this.tuNgay_Checked && this.tuNgay === null)
                errors.push('Giá trị Từ ngày không hợp lệ!');
            if (this.denNgay_Checked && this.denNgay === null)
                errors.push('Giá trị Đến ngày không hợp lệ!');
            if (this.tuNgay_Checked && this.denNgay_Checked && this.tuNgay && this.denNgay) {
                //Datepicker đang để định dạng 'dd/MM/yyyy' nên nếu user click chọn thì hour:minute:second sẽ bằng 0,
                //Nếu từ ngày để giá trị mặc định -> Lấy từ today -> hour:minute:second = today,
                //Nếu đến ngày chọn từ datepicker -> hour:minute:second = 0,
                // => So sánh bị sai => set lại hour:minute:second = 0 khi so sánh 2 giá trị Date
                this.tuNgay.setHours(0, 0, 0, 0);
                this.denNgay.setHours(0, 0, 0, 0);
                if (this.tuNgay.getTime() > this.denNgay.getTime()) {
                    errors.push('Từ ngày không được lớn hơn Đến ngày!');
                }
            }

            if (errors.length > 0) {
                this.$toast.error(errors.join('\r\n'))
                return false
            }
            return true
        },

        onRowSelect(item) {
            this.hoaDon_grid.selected = item.data;
            if (item !== null && Object.keys(item.data).length > 0) {
                if (item.data.payment === '1') {
                    this.common_params.chuyenTTBtn_Enable = false;
                    this.common_params.huyTTBtn_Enable = true;
                } else {
                    this.common_params.chuyenTTBtn_Enable = true;
                    this.common_params.huyTTBtn_Enable = false;
                }
                this.common_params.fkey = this.timKiem === 0 ? item.data.fkey : this.fkey;
            }
        },

        async onChuyenTT() {
            if (this.common_params.fkey !== '') {
                await this.postConfirmPaymentFkey(this.common_params.fkey, this.hoaDon_grid.selected.pattern, this.hoaDon_grid.selected.serial);
                await this.onTraCuu();
            }
        },

        async onHuyTT() {
            if (this.common_params.fkey !== '') {
                await this.postUnConfirmPaymentFkey(this.common_params.fkey);
                await this.onTraCuu();
            }
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

        parseXmlDoc(xml) {
            let xmlDoc;
            if (window.DOMParser) {
                const parser = new DOMParser();
                xmlDoc = parser.parseFromString(xml.trim(), 'text/xml');
            } else {
                xmlDoc = new ActiveXObject('Microsoft.XMLDOM');
                xmlDoc.async = false;
                xmlDoc.loadXML(xml.trim());
            }
            return xmlDoc != null ? Array.from(xmlDoc.getElementsByTagName('Item')) : [];
        },

        async getId(maGD) {
            try {
                this.loading(true);
                let apiBody = {
                    tenId: 'ma_gd',
                    ma: maGD
                };
                let response = await API.getId(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    return response.data.data[0].HDKH_ID;
                } else return ''
            }
            catch (error) {
                console.log(error);
                return ''
            }
            finally {
                this.loading(false);
            }
        },

        async getDSHoaDonByMaTT() {
            try {
                this.loading(true);
                this.hoaDon_grid.items = [];
                let apiBody = {
                    cusCode: this.maTT,
                    fromDate: this.tuNgay_Checked ? moment(this.tuNgay).format("DD/MM/YYYY") : "",
                    toDate: this.denNgay_Checked ? moment(this.denNgay).format("DD/MM/YYYY") : ""
                };
                let response = await API.listInvByCus(this.axios, apiBody);
                if (response.status === 200) {
                    if  (response.data.faultString === 'success' && response.data.data != null) {
                        let items = this.parseXmlDoc(response.data.data);
                        if (items.length > 0) {
                            let count = 1;
                            for (let item of items) {
                                let status = item.getElementsByTagName('status')[0].textContent;
                                let payment = item.getElementsByTagName('payment')[0].textContent;
                                let signStatus = item.getElementsByTagName('signStatus')[0].textContent;
                                let publishDate = item.getElementsByTagName('publishDate')[0].textContent;
                                this.hoaDon_grid.items.push({
                                    stt: count++,
                                    index: item.getElementsByTagName('index')[0].textContent,
                                    invToken: item.getElementsByTagName('invToken')[0].textContent,
                                    fkey: item.getElementsByTagName('fkey')[0].textContent,
                                    name: item.getElementsByTagName('name')[0].textContent,
                                    total: item.getElementsByTagName('total')[0].textContent,
                                    amount: item.getElementsByTagName('amount')[0].textContent,
                                    pattern: item.getElementsByTagName('pattern')[0].textContent,
                                    serial: item.getElementsByTagName('serial')[0].textContent,
                                    invNum: item.getElementsByTagName('invNum')[0].textContent,
                                    cusname: item.getElementsByTagName('cusname')[0].textContent,
                                    cusCode: this.maTT,
                                    publishDate: publishDate,
                                    signStatus: signStatus,
                                    payment: payment,
                                    status: status,
                                    status_chu: status === '1' ? 'Hóa đơn đã phát hành' : status === '3' ? 'Hóa đơn đã thay thế' : status === '4' ? 'Hóa đơn đã sửa đổi' : 'Hóa đơn đã hủy',
                                    payment_chu: payment === '1' ? 'Đã thanh toán' : 'Chưa thanh toán',
                                    signStatus_chu: signStatus === '0' ? 'Chưa xem' : 'Đã xem',
                                    publish_Date: publishDate !== '' ? moment(publishDate, "MM/DD/YYYY h:mm:ss A").format("DD/MM/YYYY HH:mm:ss") : ''
                                });
                            }
                        } else this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                    } else this.$toast.error(response.data.faultString);
                }
                this.hoaDon_grid.items = [...this.hoaDon_grid.items];
            }
            catch (error) {
                console.log(error)
                this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!');
            }
            finally {
                this.loading(false);
            }
        },

        async getDSHoaDonByFkey() {
            try {
                this.loading(true);
                this.hoaDon_grid.items = [];
                let apiBody = {
                    key: this.fkey,
                    fromDate: this.tuNgay_Checked ? moment(this.tuNgay).format("DD/MM/YYYY") : "",
                    toDate: this.denNgay_Checked ? moment(this.denNgay).format("DD/MM/YYYY") : ""
                };
                let response = await API.listInvByCusFkey(this.axios, apiBody);
                if (response.status === 200) {
                    if  (response.data.faultString === 'success' && response.data.data != null) {
                        let items = this.parseXmlDoc(response.data.data);
                        if (items.length > 0) {
                            let count = 1;
                            for (let item of items) {
                                let status = item.getElementsByTagName('status')[0].textContent;
                                let payment = item.getElementsByTagName('payment')[0].textContent;
                                let signStatus = item.getElementsByTagName('signStatus')[0].textContent;
                                let publishDate = item.getElementsByTagName('publishDate')[0].textContent;
                                let pattern = item.getElementsByTagName('pattern')[0].textContent;
                                let serial = item.getElementsByTagName('serial')[0].textContent;
                                let invNum = item.getElementsByTagName('invNum')[0].textContent;
                                this.hoaDon_grid.items.push({
                                    stt: count++,
                                    index: item.getElementsByTagName('index')[0].textContent,
                                    cusCode: item.getElementsByTagName('cusCode')[0].textContent,
                                    fkey: this.fkey,
                                    month: item.getElementsByTagName('month')[0].textContent,
                                    name: item.getElementsByTagName('name')[0].textContent,
                                    amount: item.getElementsByTagName('amount')[0].textContent,
                                    pattern: pattern,
                                    serial: serial,
                                    invNum: invNum,
                                    cusname: item.getElementsByTagName('cusname')[0].textContent,
                                    converted: item.getElementsByTagName('converted')[0].textContent,
                                    publishDate: publishDate,
                                    signStatus: signStatus,
                                    payment: payment,
                                    status: status,
                                    status_chu: status === '1' ? 'Hóa đơn đã phát hành' : status === '3' ? 'Hóa đơn đã thay thế' : status === '4' ? 'Hóa đơn đã sửa đổi' : 'Hóa đơn đã hủy',
                                    payment_chu: payment === '1' ? 'Đã thanh toán' : 'Chưa thanh toán',
                                    signStatus_chu: signStatus === '0' ? 'Chưa xem' : 'Đã xem',
                                    invToken: `${pattern};${serial};${invNum}`,
                                    publish_Date: publishDate !== '' ? moment(publishDate, "MM/DD/YYYY h:mm:ss A").format("DD/MM/YYYY HH:mm:ss") : ''
                                });
                            }
                        } else this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                    } else this.$toast.error(response.data.faultString);
                }
                this.hoaDon_grid.items = [...this.hoaDon_grid.items];
            }
            catch (error) {
                console.log(error)
                this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!');
            }
            finally {
                this.loading(false);
            }
        },

        async postConfirmPaymentFkey(fkey, pattern, serial) {
            try {
                this.loading(true);
                let apiBody = {
                    pattern: pattern,
                    serial: serial,
                    fKey: fkey,
                    metaClass: null
                };
                let response = await API.confirmPaymentFkey(this.axios, apiBody);
                if (response.status === 200)
                    this.$toast.success('Cập nhật trạng thái hóa đơn thành công!');
                else this.$toast.error(response.data.faultString);
            }
            catch (error) {
                console.log(error);
                this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!');
            }
            finally {
                this.loading(false);
            }
        },

        async postUnConfirmPaymentFkey(fkey) {
            try {
                this.loading(true);
                let apiBody = {
                    lstFkey: fkey
                };
                let response = await API.unConfirmPaymentFkey(this.axios, apiBody);
                if (response.status === 200 && response.data.faultString === 'success' && response.data.data === 'OK')
                    this.$toast.success('Cập nhật trạng thái hóa đơn thành công!');
                else this.$toast.error(response.data.faultString);
            }
            catch (error) {
                console.log(error);
                this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!');
            }
            finally {
                this.loading(false);
            }
        },

        async postDownloadInvFkeyNoPayXml(fkey) {
            try {
                this.loading(true);
                let apiBody = {
                    fkey: fkey
                };
                let response = await API.downloadInvFkeyNoPay(this.axios, apiBody);
                if (response.status === 200 && response.data.faultString === 'success' && response.data.data != null)
                    return response.data.data;
                else {
                    this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                    return null;
                }
            }
            catch (error) {
                console.log(error);
                this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
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
                    fkey: fkey
                };
                let response = await API.downloadInvPDFFkeyNoPay(this.axios, apiBody);
                if (response.status === 200 && response.data.faultString === 'success' && response.data.data != null)
                    return response.data.data;
                else {
                    this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                    return null;
                }
            }
            catch (error) {
                console.log(error);
                this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
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
                    invToken: invToken
                };
                let response = await API.getInvView(this.axios, apiBody);
                if (response.status === 200 && response.data.faultString === 'success' && response.data.data != null)
                    return response.data.data;
                else {
                    this.$toast.error(response.data.faultString);
                    return null;
                }
            }
            catch (error) {
                console.log(error)
                this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                return null;
            }
            finally {
                this.loading(false);
            }
        },
        async downloadXML(item){
            if(item.fkey == 'null'){
                this.$toast.error('Hóa đơn không có fkey !');
                return;
            }
            let rs = await this.postDownloadInvFkeyNoPayXml(item.fkey);
            if (rs !== null) {
                let content = rs;
                var FileSaver = require("file-saver");
                var file = new File([content], item.invNum + '.xml', {
                    type: "application/xml"
                });
                FileSaver.saveAs(file);
            }
        },
    }
}
</script>
