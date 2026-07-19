<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Chọn số máy</div>
        <div class="close -ap icon-close" data-dismiss="modal" @click.prevent="closeForm">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="chapnhan">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận (F5)
                </a>
            </li>
            <!-- <li>
                <a href="#" :class="{disabled:!tsbtnIn}">
                    <span class="icon one-print"></span> In số
                </a>
            </li> -->
            <li>
                <a href="#" @click.prevent="btnGiuSoClicked">
                    <span class="icon one-luu-so"></span> Giữ số
                </a>
            </li>
            <li>
                <vue-excel-xlsx
                    :data="filteredSoMay"
                    :columns="[ 
                            {field: 'somay', label: 'Số máy', }, 
                            {field: 'kieu_so', label: 'Kiểu số', }, 
                            {field: 'tien_hm', label: 'Tiền số đẹp', }, 
                            {field: 'mota', label: 'Mô tả', }, 
                            ]"
                    :file-name="'DsSoMay'"
                    :file-type="'xlsx'"
                    :sheet-name="'SoMay'"
                    class="hide"
                    ref="btnExportExcel"
                    >
                    Xuất Excel
                </vue-excel-xlsx>

                <a href="#" @click.prevent="$refs.btnExportExcel.exportExcel()">
                    <span class="icon one-xlsx-import"></span> Xuất Excel
                </a>
            </li>
            
        </ul>
    </div>
    <div class="popup-body">
        <div class="row">
            <div class="col-sm-6 col-12">
                <div class="box-form">
                    <div class="info-row">
                        <div class="key w90">
                            <!-- <div class="check-action"> -->
                                <span class="name">Nhóm số</span>
                            <!-- </div> -->
                        </div>
                        <div class="value" :class="{disabled:!nhomso.ischecked}">
                            <select2 v-model="nhomso.value" class="select-custom" :options="nhomso.list.map(ls=>({id:ls.NHOMSO_ID,text:ls.NHOM_SO}))"></select2>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w90">
                            <!-- <div class="check-action"> -->
                                <span class="name">Kiểu số</span>
                            <!-- </div> -->
                        </div>
                        <div class="value" :class="{disabled:!kieuso.ischecked}">
                            <select2 v-model="kieuso.value" class="select-custom" :options="ckieuso" @change="HienThiDanhSach"></select2>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w90">
                            <!-- <div class="check-action"> -->
                                <span class="name">Vệ tinh</span>
                            <!-- </div> -->
                        </div>
                        <div class="value" :class="{disabled:!vetinh.ischecked}">
                            <select2 v-model="vetinh.value" class="select-custom" :options="vetinh.list.map(ls=>({id:ls.donvi_id,text:ls.ten_dv}))" @change="getHosts"></select2>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w90">Host</div>
                        <div class="value">
                            <select2 v-model="hosts.value" class="select-custom" :options="hosts.list.map(ls=>({id:ls.donvi_id,text:ls.ten_dv}))" @change="HienThiDanhSach"></select2>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="box-form">
                    <div class="box-form" style="min-height: 260px;">
                        <div class="info-row">
                            <div class="key w70">Số máy</div>
                            <div class="value disabled">
                                <input type="text" class="form-control highlight" readonly v-model="somay.value">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w70">Tìm kiếm</div>
                            <div class="value">
                                <input type="text" class="form-control highlight" v-model="timkiem" @keypress.enter="TimKiemEnter">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w70">Loại tìm kiếm</div>
                            <div class="value">
                                <select class="form-control highlight" v-model="loaitimkiem">
                                    <option value="1">Kết thúc (vd: %123)</option>
                                    <option value="2">Chứa số (vd: %123%)</option>
                                    <option value="3">Bắt đầu (vd: 123%)</option>
                                </select>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w70">&nbsp;</div>
                            <div class="value">
                                <ul>
                                    <li>Số thường tìm kiếm theo HOST</li>
                                    <li>Số đẹp tìm kiếm trên toàn TP</li>
                                    <li>Check chọn để thực hiện giữ số</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-12">
                <div class="box-form">
                    <div class="legend-title mart20">Danh sách số máy</div>
                    <DataGrid2 
                            v-bind:columns="[ 
                            {fieldName: 'somay', headerText: 'Số máy', allowFiltering: true, allowSorting: true}, 
                            {fieldName: 'kieu_so', headerText: 'Kiểu số', allowFiltering: true, allowSorting: false}, 
                            {fieldName: 'tien_hm', headerText: 'Tiền số đẹp', allowFiltering: true, allowSorting: false}, 
                            {fieldName: 'mota', headerText: 'Mô tả', allowFiltering: true, allowSorting: true}, 
                            ]" 
                        v-bind:dataSource="filteredSoMay" :showFilter="true" :showColumnCheckbox="true" 
                        dataKeyField="somay" :defaultColumnCheckboxChecked="false" :panelDataHeight="200" :selectedRow="somay.idx" @rowClicked="dsSoMayChanged" @selectedItemsChanged="selectedItemsChanged"> 
                    </DataGrid2>
                </div>
            </div>
        </div>
    </div>
</div>
</template>
<style scoped>
    input.disabled, select.disabled, .disabled input, .disabled select, .disabled /deep/ .select2-selection { background-color: #EEE; color:lightgray;}
    .hide { display:none; }
</style>
<script>
import {
  THUESO,
} from "../Enums";

import VueExcelXlsx from "vue-excel-xlsx";
import Vue from "vue";

Vue.use(VueExcelXlsx);
export default {
    name: 'popupChonSMTheoHost',
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            diachi: {
                diachi_id: null,
                tinh_id:null ,
                quan_id: null,
                phuong_id: null,
                pho_id: null,
                ap_id: null,
                khu_id:null,
                dac_diem_id: null,
                so_nha: "",
                daydiachi: "",
                diachimoi: "",
            },
            kieuthue: "truotso",
            thongbao: "",
            truotso: "",
            tgianthue: 0,
            dsTinh: [],
            dsQuan: [],
            dsPhuong: [],
            tentinh: "",
            tenquan: "",
            tenphuong: "",
            loaiso: { list: [], value: 0 },
            nhomso: { list: [], value: -1, ischecked:true, },
            kieuso: { list: [], value: 0, ischecked:true, },
            vetinh: { list: [], value: 0, ischecked:true, },
            somay: { list: [], value: "", idx: 0, valuearr:[] },
            hosts: {list: [], value: 0,},
            thueso: false,
            loaitb_id: 0,
            donvi_ql_id: 0,
            dichvuvt_id: 0,
            hdkh_id: 0,
            ds_dauso: [],
            dauso: "",
            tongdai_id: 0,
            timkiem: "",
            loaitimkiem: 2,
            tsbtnIn: false,
            tiensodep: 0,
        }
    },
    async mounted() {
        this.loading(true);
        //Load default data
        {
            this.diachi.quan_id = this.dulieu._quan_id?this.dulieu._quan_id:0;
            this.diachi.phuong_id = this.dulieu._phuong_id?this.dulieu._phuong_id:0;
            this.diachi.pho_id = this.dulieu._pho_id?this.dulieu._pho_id:0;
            this.diachi.ap_id = this.dulieu._ap_id?this.dulieu._ap_id:0;
            this.diachi.khu_id = this.dulieu._khu_id?this.dulieu._khu_id:0;
            this.loaiso.value = this.dulieu._loaiso_id?this.dulieu._loaiso_id:0;
            this.thueso = this.dulieu._thueso;
            this.loaitb_id = this.dulieu.loaitb_id;
            this.donvi_ql_id = this.dulieu.donvi_ql_id;
            this.dichvuvt_id = this.dulieu.dichvuvt_id;
            this.hdkh_id = this.dulieu.hdkh_id;
        }

        // var load3 = this.$root.context.get("/web-quantri/danhmuc-chung/CSS_LOAI_SO");
        var load4 = this.$root.context.get("/web-quantri/danhmuc-chung/CSS_NHOM_SO");
        var load5 = this.$root.context.get("/web-quantri/danhmuc-chung/CSS_KIEU_SO");
        var load6 = this.getVeTinh();
        var load7 = this.getHosts();
        //if(loaiso && loaiso.data) this.loaiso.list = loaiso.data;
        Promise.all([load4,load5,load6,load7]).then(([data4,data5,data6,data7])=>{
            // if(data3 && data3.data) this.loaiso.list = data3.data;
            if(data4 && data4.data) this.nhomso.list = data4.data;
            if(data5 && data5.data) this.kieuso.list = data5.data;
            if(this.nhomso.list && this.nhomso.list.length>0)
                this.nhomso.value = this.nhomso.list[0].NHOMSO_ID;
            if(this.kieuso.list && this.ckieuso.length>0)
                this.kieuso.value = this.ckieuso[0].KIEUSO_ID;
        }).finally(()=>{
            this.loading(false);
        });
    },
    computed: {
        filteredSoMay: function() {
            var newlist = this.somay.list.filter(x=>x.somay.indexOf(this.timkiem)>=0);
            if(newlist && newlist.length>0) this.dsSoMayChanged(0,newlist[0]);
            return newlist?newlist:[];
        },
        ckieuso: function() {
            var ds = this.kieuso.list.filter(x=>x.NHOMSO_ID==this.nhomso.value).map(ls=>({id:ls.KIEUSO_ID,text:ls.KIEU_SO}))
            if(ds && ds.length>0 && !ds.find(x=>x.id==this.kieuso.value)) {
                this.kieuso.value = ds[0].id;
                this.HienThiDanhSach();
            }
            return ds;
        }
    },
    methods: {
        closeForm() {
            this.$emit('form-close',null);
        },
        getVeTinh: async function() {
            var vetinh = await this.$root.context.post("/web-hopdong/lapdatmoi/sp_lay_ds_vetinh_diadanh",{ "phuong_id": this.diachi.phuong_id, "quan_id": this.diachi.quan_id, "pho_id": this.diachi.pho_id,"ap_id": this.diachi.ap_id, "khu_id": this.diachi.khu_id });
            if(vetinh && vetinh.data) {
                this.vetinh.list = vetinh.data;
                if(this.vetinh.list && this.vetinh.list.length>0) {
                    this.vetinh.value = this.vetinh.list[0]['donvi_id'];
                    this.getHosts();
                }
            }
        },
        getHosts: async function() {
            var hosts = await this.$root.context.post("/web-hopdong/lapdatmoi/lay_ds_host_theo_vetinh",{ "tramtb_id": this.vetinh.value });
            if(hosts && hosts.data) { 
                this.hosts.list = hosts.data; 
                if(this.hosts.list && this.hosts.list.length>0) this.hosts.value = this.hosts.list[0]['donvi_id'];
                this.HienThiDanhSach();
            }
        },
        HienThiDanhSach: async function() {
            this.loading(true);
            var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/lay_ds_khoso_host", {
                "vdichvuvt_id": this.dichvuvt_id,
                "vhost_id": this.hosts.value,
                "vkieuso_id": this.kieuso.value,
                "vloaiso_id": this.loaiso.value,
                "vloaitb_id": this.loaitb_id,
                "vnguoidung_id": this.$root.token.getNguoiDungID(),
                "vnhomso_id": this.nhomso.value,
                "vphuong_id": this.diachi.phuong_id,
                "vquan_id": this.diachi.quan_id,
            });
            this.loading(false);
            if(rs.data && rs.data.length>0) {
                this.somay.list = rs.data;
                if(this.somay.list && this.somay.list.length>0) this.dsSoMayChanged(0,this.somay.list[0]);
            }
            else 
            {
                this.somay.list = [];
            }
        },
        selectedItemsChanged(items) {
            this.somay.valuearr = items;
            this.tiensodep = items.map(x=>x.tien_hm).reduce((a, b) => a + b, 0)
        },
        chapnhan: function () {
            var retData = {somay:this.somay.value,loaiso:this.loaiso.value,arraySoMay:this.somay.valuearr,nhomso:this.nhomso.value,tiensodep:this.tiensodep};
            
            this.$emit("form-close", retData);
            this.$bvModal.hide(this.modalId);
        },
        async dsDauSoChanged(idx,item) {
            if(!this.ds_dauso || this.ds_dauso.length<=0) {
                this.somay.value = "";
                this.somay.list = [];
            } else {
                this.tongdai_id = item.tongdai_id;
                //TODO continue line 589, dang thieu API 256_010
                this.dauso = item.prefix;
                this.loading(true);
                var rs = await this.$root.context.post("/web-quantri/dauso/layds_dai_so_ims", {
                    "dichvu_vienthong_id": this.dichvuvt_id,
                    "kieuso_id": this.kieuso.value,
                    "loaiso_id": this.loaiso.value,
                    "nhomso_id": this.nhomso.value==-1?0:this.nhomso.value,
                    "prefix": this.dauso,
                    "so_batdau": item.tuso,
                    "so_ketthuc": item.denso
                });
                this.loading(false);
                if(rs.data && rs.data.length>0) {
                    this.somay.list = rs.data;
                    if(this.somay.list && this.somay.list.length>0) this.dsSoMayChanged(0,this.somay.list[0]);
                }
                else 
                {
                    // if(rs.error=="200") this.$toast.warning("Không có thông tin số máy!");
                    // else this.$toast.error("Có lỗi: "+rs.message);
                    this.somay.list = [];
                }
                this.tsbtnIn = true;
            }
        },
        async dsSoMayChanged(idx,item) {
            if(item) {
                this.somay.value = item.somay;
                this.tiensodep = item.tien_hm;
            }
        },
        async btnGiuSoClicked() {
            this.loading(true);
            var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/sp_capnhat_giuso",{
                "dichvuvt_id": this.dichvuvt_id,
                "js_somay": JSON.stringify((this.somay.valuearr && this.somay.valuearr.length>0?this.somay.valuearr:[this.somay.value]).map(x=>({"SOMAY":x}))),//JSON.stringify(this.somay.valuearr),
                "nguoidung_id": this.$root.token.getNguoiDungID()
            });
            if(rs && rs.message.toLowerCase() == "success")
                this.$toast.success(rs.message);
            else this.$toast.error(rs.message);
            this.loading(false);
            if(rs.data && rs.data.length>0) {
                this.popupComponent = ()=>import('./popupGiuSo');
                this.popupComponentName = "popupGiuSo";
                this.popupComponentData = {"dichvuvt_id": this.dichvuvt_id, "nguoidung_id": this.$root.token.getNguoiDungID()}
                this.Popup('popupComponents');
            } else this.$toast.error(rs.message+ (rs.message_detail?': '+rs.message_detail:''));
        },
        async TimKiemEnter() {
            this.loading(true);
            try {
                var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/sp_timkiem_khoso_host",{
                    "vphuong_id": this.diachi.phuong_id,
                    "vquan_id": this.diachi.quan_id,
                    "vloaitb_id": this.loaitb_id,
                    "vloaiso_id":this.loaiso.value,
                    "vdichvuvt_id": this.dichvuvt_id,
                    "vhost_id": this.hosts.value,
                    "vnhomso_id":this.nhomso.value==-1?0:this.nhomso.value,
                    "vkieuso_id":this.kieuso.value,
                    "vnguoidung_id":this.$root.token.getNguoiDungID(),
                    "vkieu_tk": this.loaitimkiem,
                    "vsomay_tk":this.timkiem
                });
                if(rs && rs.message.toLowerCase() != "success")
                    this.$toast.error(rs.message);
                else 
                if(rs.data && rs.data.length>0) {
                    this.somay.list = rs.data;
                    if(this.somay.list && this.somay.list.length>0) this.dsSoMayChanged(0,this.somay.list[0]);
                }
                else {
                    this.$toast.error("Không tìm thấy số máy thoả mãn điều kiện tìm kiếm");
                    this.somay.list = [];
                }
            } catch(ex) { this.$toast.error(ex+''); }
            finally {
                this.loading(false);
            }
            // if(this.timkiem.length <= 5) {
            //     this.$toast.error("Số máy phải lớn hơn 5 ký tự !");
            //     return;
            // }
            // var ktra = false;
            // var kw = this.timkiem;
            // var dauso = this.ds_dauso.filter(x=>kw.substring(0,6).indexOf(x.prefix)==0);
            // if(dauso && dauso.length>0) {
            //     this.dauso = dauso[0].prefix;
            //     //TODO Chon row cua dau so
            //     var listsm = this.somay.list.filter(x=>x.somay==kw.trim());
            //     if(listsm && listsm.length>0) {
            //         //TODO chon row cua somay
            //         this.somay.value = this.timkiem.trim();
            //         ktra = true;
            //     }
            // }
            // if(!ktra) {
            //     this.$toast.error("Số máy : '" + this.timkiem.trim() + "' không có trong kho số hoặc đã được cấp !");
            //     return;
            // }
            this.tsbtnIn = true;
        },
    }
}
</script>