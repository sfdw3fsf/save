
<template src="./SearchDeliveryTicket.html"></template>
<style src="./SearchDeliveryTicket.scss"></style>
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
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
var colThaotac = function() {
                    return {
                    template: {
                        template: `
                                        <div>
                                            <button @click="onViewButtonClick"  class="pad3 mar5 lh14 icon -ap one-eye btn-info btn-view-details" title="Xem chi tiết"></button>
                                            <button  v-if="data.huonggiao =='PHIEUNHAN'" v-if="data.huonggiao =='PHIEUNHAN'&&data.check_ht_phandoan=='DAHOANTHANH'" @click="onHoanthanhClick" class="pad3 mar5 lh14 icon -ap one-check btn-success btn-search rounded-circle" title="Hoàn thành"></button>
                                            <button :disabled="data.check_ht_phandoan=='CHUAHOANTHANH'" v-if="data.huonggiao =='PHIEUNHAN'&&data.check_ht_phandoan=='CHUAHOANTHANH'" @click="onStatusButtonClick"  class="pad3 mar5 lh14 icon -ap one-check btn-secondary btn-search rounded-circle" title="Kích hoạt"></button>
                                            <span class="name"></span>
                                        </div>
                                    `,
                        data() {
                            return {
                                data: {},
                                isEditing: false,
                                thutu_new: "0",
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
                                this.parent.xemChitiet(this.data);
                            },
                            onHoanthanhClick() {
                                this.parent.tsbtnHoanthanh_Click(this.data);
                            },
                        },
                    }
                }
            }
var colThaotacDaHT = function() {
                    return {
                    template: {
                        template: `
                                        <div>
                                            <button @click="onViewButtonClick"  class="pad3 mar5 lh14 icon -ap one-eye btn-info btn-view-details" title="Xem chi tiết"></button>
                                            <span class="name"></span>
                                        </div>
                                    `,
                        data() {
                            return {
                                data: {},
                                isEditing: false,
                                thutu_new: "0",
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
                                this.parent.xemChitiet(this.data);
                            }
                        },
                    }
                }
            }
export default {
    components: {
        breadcrumb,
        VueFlatPickr,
        KDatePicker,
        KRequiredMarker
        
    },
    name: 'SearchDeliveryTicket',
    data() {
        return {
            switchValue: false,
            frmDSHoSoBC: {
                input: {
                    visible: false
                }
            },
            dataSelect: {},
             headerChoxuly: [
                                    {fieldName: 'phieu_id', headerText: 'Mã phiếu', allowFiltering: true, allowSorting: false, width: 90, cssClass: 'text-blue'},
                                    {fieldName: 'ma_kh', headerText: 'Mã khách hàng', allowFiltering: true, allowSorting: false, width: 150, cssClass: 'text-bold'},
                                    {fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowFiltering: true, allowSorting: false, width: 150, cssClass: 'text-bold'},
                                    {fieldName: 'ngaygiao', headerText: 'Ngày giao phiếu', allowFiltering: true, allowSorting: false, width: 90, cssClass: 'text-red'},
                                    {fieldName: 'dvi_chutri', headerText: 'Đơn vị chủ trì', allowFiltering: true, allowSorting: false, width: 90, cssClass: 'text-red'},
                                    {fieldName: 'dvi_phoihop', headerText: 'Đơn vị phối hợp', allowFiltering: true, allowSorting: false, width: 110},
                                    {fieldName: 'trangthai_ph', headerText: 'Trạng thái phiếu', allowFiltering: true, allowSorting: false, width: 110},
                                    {
                                        fieldName: 'trangthai_phandoan',
                                        headerText: 'Xử lý phiếu',
                                        allowFiltering: true,
                                        width: 90,
                                        template: colThaotac 
                                    }
                                ],
            headerDaHT: [
                        {fieldName: 'phieu_id', headerText: 'Mã phiếu', allowFiltering: true, allowSorting: false, width: 90, cssClass: 'text-blue'},
                        {fieldName: 'ma_kh', headerText: 'Mã khách hàng', allowFiltering: true, allowSorting: false, width: 150, cssClass: 'text-bold'},
                        {fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowFiltering: true, allowSorting: false, width: 150, cssClass: 'text-bold'},
                        {fieldName: 'ngaygiao', headerText: 'Ngày giao phiếu', allowFiltering: true, allowSorting: false, width: 90, cssClass: 'text-red'},
                        {fieldName: 'dvi_chutri', headerText: 'Đơn vị chủ trì', allowFiltering: true, allowSorting: false, width: 90, cssClass: 'text-red'},
                        {fieldName: 'dvi_phoihop', headerText: 'Đơn vị phối hợp', allowFiltering: true, allowSorting: false, width: 110},
                        {fieldName: 'trangthai_ph', headerText: 'Trạng thái phiếu', allowFiltering: true, allowSorting: false, width: 110},
                        {
                                        fieldName: '',
                                        headerText: 'Thao tác',
                                        allowFiltering: true,
                                        width: 90,
                                        template: colThaotacDaHT 
                        }
            ],
            dsDonviPhoihop: [],
            DsphieuChoxuly: [],
            DsphieuDahoanthanh: [],
            trangthai_ph: "CHOXULY",
            txtMaphieu: "",
            txtMaKH: "",
            Ngaygiaophieu: null,
            donvi_phoihop_id:null,
            header: {
                title: 'TRA CỨU DANH SÁCH XỬ LÝ PHIẾU',
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
        }
    },
    async created() {
    },
    validations: {
    },
    async mounted() {
        await this.getdsDanhmucChung();
        
    },
    methods: {
         async getdsDanhmucChung() {
            try {
                await api.laydsDanhMucChung(this.axios, {})
                    .then((res) => {
                        console.log(res)
                        if (
                            res.data.error_code == "BSS-00000000" 
                        ) {
                            if(res.data.data.DONVI.length >0){
                                this.dsDonviPhoihop = this.list2treeNew(res.data.data.DONVI);
                            }
                            
                        }
                    });
            } catch (err) {
                this.$toast.error("Lỗi lấy danh mục chung: " + err.message);
            }
        },
        changetab_ChoXL(){
            this.trangthai_ph = "CHOXULY"
            //this.layDanhsanhphieu()
        },
        changetab_DaHT(){
            this.trangthai_ph = "DAHOANTHANH"
            //this.layDanhsanhphieu()
        },
        tsbtnChitiet_Click(){ 
            let item  =  this.dataSelect
            if(item.thuebao_id != 0 && item.hdtb_id == 0) { 
                this.$router.push('/htkh/DeclareChannelInfo?thuebao_id='+item.thuebao_id+'&tinh_id='+item.tinh_id+'&tinhcq_id='+item.tinh_id+'&phieu_id='+item.phieu_id+'&status='+item.status)
            }
            if(item.hdtb_id != 0 && item.thuebao_id == 0) {
                this.$router.push('/htkh/DeclareChannelInfo?hdtb_id='+item.hdtb_id+'&tinh_id='+item.tinh_id+'&tinhcq_id='+item.tinh_id+'&phieu_id='+item.phieu_id+'&status='+item.status)
            }
            
        },
        xemChitiet(item){ 

            if(item.thuebao_id != 0 && item.hdtb_id == 0) { 
                this.$router.push('/htkh/DeclareChannelInfo?thuebao_id='+item.thuebao_id+'&tinh_id='+item.tinh_id+'&tinhcq_id='+item.tinh_id+'&phieu_id='+item.phieu_id+'&status='+item.status)
            }
            if(item.hdtb_id != 0 && item.thuebao_id == 0) {
                this.$router.push('/htkh/DeclareChannelInfo?hdtb_id='+item.hdtb_id+'&tinh_id='+item.tinh_id+'&tinhcq_id='+item.tinh_id+'&phieu_id='+item.phieu_id+'&status='+item.status)
            }
            
        },
        async layDanhsanhphieu(trangthai) {
            try {
                this.loading(true)
                let input =  {
                    "p_maphieu":this.txtMaphieu,
                    "p_makhachhang": this.txtMaKH,
                    "p_ngaygiaophieu":this.Ngaygiaophieu,
                    "p_donviphoihop":this.donvi_phoihop_id,
                    "p_trangthai_ph": trangthai
                }
                await api.traCuuDanhSachXuLyPhieu(this.axios,input )
                    .then((res) => {
                        if (
                            res.data.error_code === "BSS-00000000" &&
                            res.data.data !== undefined &&
                            res.data.data.length > 0
                        ) {
                            if(trangthai == "CHOXULY"){
                                this.DsphieuChoxuly = res.data.data
                            }else{
                                this.DsphieuDahoanthanh = res.data.data
                            }
                        }
                    });
                 this.loading(false)
            } catch (err) {
                this.$toast.error("Lỗi lấy thông tin chung: " + err.message);
            }
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
        async tsbtnHoanthanh_Click(data)
        {
            try
            {
                this.$bvModal.msgBoxConfirm('Xác nhận hoàn thành phiếu ?', {
                    title: '',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then( async  v => {
                    if(v) { 
                        let ds = this.GetData(await api.hoanThanhPhieu(this.axios,  {
                            "p_phieu_id":data.phieu_id
                        }));
                        this.$root.$toast.success("Hoàn thành phiếu thành công!");

                        this.layDanhsanhphieu(); 
                    }
                });
            }
            catch (err)
            {
                this.$toast.error("Lỗi xóa hợp đồng khách hàng: "+ err.message);
            }
        },
        tsbtnNhapMoi_Click(){
            this.txtMaphieu = ""
            this.txtMaKH = ""
            this.Ngaygiaophieu = moment().format('DD/MM/YYYY')
            this.donvi_phoihop_id = null
        },
        tsbtnTimkiem_Click(){
            this.layDanhsanhphieu("CHOXULY")
            this.layDanhsanhphieu("DAHOANTHANH")
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
        onChangeDonviPhoihop(item){
            this.donvi_phoihop_id = item.donvi_id
        },
        onPhieuChoXLSelected(data){
            this.dataSelect = data.data
            
        },
        onPhieuDaHTSelected(){
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