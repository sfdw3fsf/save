<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Chọn mã thuê bao lắp kèm</div>
        <div class="close -ap icon-close hidden" data-dismiss="modal">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="SearchClient">
                    <span class="icon one-search"></span>Tìm kiếm
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="closeForm" :class="{ disabled:!Acc || !khachhang.list || khachhang.list.length<=0 }">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận (F5)
                </a>
            </li>
            <li :class="{hidden:!btnThoat}">
                <a href="#" @click.prevent="terminateForm">
                    <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">
        <div class="page-content">
            <div class="box-form">
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w90">Thông tin tìm kiếm</div>
                            <div class="value">
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="select-custom">
                                            <select2 v-model="dk1" class="select-custom" :options="dsDKTraCuu.list.map(ld=>({id:ld.KIEUTK_ID,text:ld.KIEU_TK}))"></select2>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <input type="text" class="form-control" v-model="valuedk1">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="dsDichVuVT.isenabled">
                                    <span class="name">Dịch vụ</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <select2 v-model="valuedvvt" class="select-custom" :options="dsDichVuVT.list.map(ld=>({id:ld.DICHVUVT_ID,text:ld.TEN_DVVT}))"></select2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="dsDonVi.isenabled">
                                    <span class="name">Đơn vị</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <select2 v-model="valuedonvi" class="select-custom" :options="dsDonVi.list.map(ld=>({id:ld.donvi_id,text:ld.ten_dv}))"></select2>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w90">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="dsDKTraCuu.isenabled2">
                                    <span class="name">Tra theo</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="row" :class="{disabled:!dsDKTraCuu.isenabled2}">
                                    <div class="col-sm-6 col-12">
                                        <div class="select-custom">
                                            <select2 v-model="dk2" class="select-custom" :options="dsDKTraCuu.list.map(ld=>({id:ld.KIEUTK_ID,text:ld.KIEU_TK}))"></select2>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <input type="text" class="form-control" v-model="valuedk2">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                Mã KH
                            </div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="maKH">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w90">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="dsDKTraCuu.isenabled3">
                                    <span class="name">Tra theo</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="row" :class="{disabled:!dsDKTraCuu.isenabled3}">
                                    <div class="col-sm-6 col-12">
                                        <div class="select-custom">
                                            <select2 v-model="dk3" class="select-custom" :options="dsDKTraCuu.list.map(ld=>({id:ld.KIEUTK_ID,text:ld.KIEU_TK}))"></select2>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <input type="text" class="form-control" v-model="valuedk3">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                Số máy/Acc
                            </div>
                            <div class="value disabled">
                                <input type="text" class="form-control" v-model="Acc">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                Số ảo
                            </div>
                            <div class="value disabled">
                                <input type="text" class="form-control" v-model="SoAo">
                            </div>
                        </div>
                    </div>
                </div>

            </div>

            <div class="box-form">
                <div class="legend-title">Danh sách khách hàng</div>
                <DataGrid2
                    v-bind:columns="khachhang.cols"
                    v-bind:dataSource="khachhang.list" :showFilter="false" :showColumnCheckbox="false"
                    :dataKeyField="khachhang.keycol" :defaultColumnCheckboxChecked="false" :panelDataHeight="200" @rowClicked="rowChanged">
                </DataGrid2>
            </div>
            <div class="box-form">
                <div class="legend-title">Danh sách thuê bao</div>
                <DataGrid2
                    v-bind:columns="thuebao.cols"
                    v-bind:dataSource="thuebao.list" :showFilter="false" :showColumnCheckbox="false"
                    :dataKeyField="thuebao.keycol" :defaultColumnCheckboxChecked="false" :panelDataHeight="200" @rowClicked="thuebaorowChanged">
                </DataGrid2>
            </div>
        </div>
    </div>
</div>
</template>
<style scoped>

</style>
<script>
export default {
    name: 'popupDSTBCungDoiCap_KH_Khac',
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            dsDichVuVT: {
                list: [],
                value: 0,
                isenabled: false,
            },
            dsDKTraCuu: {
                list: [],
                value1: "",
                dk1: 0,
                isenabled1: true,
                value2: "",
                dk2: 0,
                isenabled2: false,
                value3: "",
                dk3: 0,
                isenabled3: false,
            },
            dsDonVi: {
                list: [],
                value: 0,
                isenabled:false,
            },
            maKH: "",
            Acc: "",
            SoAo: "",
            khachhang: {
                list: [],
                value: {},
                cols: [
                    {fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'MA_KH', headerText: 'Mã KH', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'MAKH_CU', headerText: 'Mã KH cũ', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'MA_HD', headerText: 'Mã HĐ/PL', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'TEN_KH', headerText: 'Tên khách hàng', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'DIACHI_KH', headerText: 'Địa chỉ khách hàng', allowFiltering: true, allowSorting: true, },
                ],
                keycol: "STT",
            },
            thuebao: {
                list: [],
                value: {},
                cols: [
                    {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'MA_LT', headerText: 'Số ảo', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'DIACHI_LD', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'DICHVUVT_ID', headerText: 'DICHVUVT_ID', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'LOAITB_ID', headerText: 'LOAITB_ID', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'SITE_ID', headerText: 'SITE ID', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'MATB_TN', headerText: 'Mã truy nhập', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'MADOICAP', headerText: 'Mã đôi cáp', allowFiltering: true, allowSorting: true, },
                ],
                keycol: "MA_TB",
            },
            loaitb_id: 0,
            kieuld_id: 0,
            btnThoat:this.dulieu.btnThoat?this.dulieu.btnThoat:false,
        }
    },
    async mounted() {
        // if(this.dulieu.khachhang_id) this.khachhang_id = this.dulieu.khachhang_id;
        if(this.dulieu.kieuld_id) this.kieuld_id = this.dulieu.kieuld_id;
        if(this.dulieu.loaitb_id) this.loaitb_id = this.dulieu.loaitb_id;
        // if(this.dulieu.dichvuvt_id) this.dichvuvt_id = this.dulieu.dichvuvt_id;


        this.loading(true);
        var api1 = this.$root.context.get("/web-quantri/danhmuc-chung/CSS_DICHVU_VT",{ });
        var api2 = this.$root.context.post("/web-hopdong/lapdatmoi/sp_ht_ds_daivt",{"donvi_ql_id":0});
        var api3 = this.$root.context.post("/web-hopdong/lapdatmoi/lay_kieutk_theonhom",{"p_nhom_id":2 });//?nhomId=2 gia tri nay fix trong code cu
        Promise.all([api1,api2,api3]).then(([rs1,rs2,rs3])=>{
            if(rs1.data) {
                this.dsDichVuVT.list = rs1.data;
            }
            if(rs2.data) {
                this.dsDonVi.list = rs2.data;
            }
            if(rs3.data) {
                this.dsDKTraCuu.list = rs3.data;
            }
        }).finally(()=>{
            this.loading(false);
        });
    },
    computed: {
        dk1: {
            get: function() { if(this.dsDKTraCuu.isenabled1) return this.dsDKTraCuu.dk1; else return 0; },
            set: function(value) { this.dsDKTraCuu.dk1=value; }
        },
        valuedk1: {
            get: function() { if(this.dsDKTraCuu.isenabled1) return this.dsDKTraCuu.value1; else return ""; },
            set: function(value) { this.dsDKTraCuu.value1=value; }
        },
        dk2: {
            get: function() { if(this.dsDKTraCuu.isenabled2) return this.dsDKTraCuu.dk2; else return 0; },
            set: function(value) { this.dsDKTraCuu.dk2=value; }
        },
        valuedk2: {
            get: function() { if(this.dsDKTraCuu.isenabled2) return this.dsDKTraCuu.value2; else return ""; },
            set: function(value) { this.dsDKTraCuu.value2=value; }
        },
        dk3: {
            get: function() { if(this.dsDKTraCuu.isenabled3) return this.dsDKTraCuu.dk3; else return 0; },
            set: function(value) { this.dsDKTraCuu.dk3=value; }
        },
        valuedk3: {
            get: function() { if(this.dsDKTraCuu.isenabled3) return this.dsDKTraCuu.value3; else return ""; },
            set: function(value) { this.dsDKTraCuu.value3=value; }
        },
        valuedonvi: {
            get: function() { if(this.dsDonVi.isenabled) return this.dsDonVi.value; else return 0; },
            set: function(value) { this.dsDonVi.value = value; }
        },
        valuedvvt: {
            get: function() { if(this.dsDichVuVT.isenabled) return this.dsDichVuVT.value; else return 0; },
            set: function(value) { this.dsDichVuVT.value = value; }
        },
    },
    methods: {

        searchCondition() {
            var dieukiens = [];
            for(var i=1;i<=3;i++)
                if(this['dk'+i] && this['valuedk'+i])
                dieukiens.push({dieukien_id:this['dk'+i],dieukien_value:this['valuedk'+i]});
            return {
                dichvu_vienthong_id: this.valuedvvt,
                donvi_id:this.valuedonvi,
                ma_kh:this.maKH,
                dieukiens:dieukiens,
            };
        },
        async SearchClient() {
            var reqData = this.searchCondition();
            var rs = await this.$root.context.post('/web-tracuu/danhba/tracuu', reqData)
            if(rs.data) {
                this.khachhang.list = rs.data??[];
                for(var i=0;i<this.khachhang.list.length;i++)
                    this.khachhang.list[i].stt = i+1;
                const UpperCasePropertyList = (obj) => {
                    return obj.map(function(item) {
                        for (var key in item) {
                            var upper = key.toUpperCase()
                            // check if it already wasn't uppercase
                            if (upper !== key) {
                                item[upper] = item[key]
                                delete item[key]
                            }
                        }
                        return item
                    })
                };
                this.khachhang.list = UpperCasePropertyList(this.khachhang.list);
            }
            else {
                this.$toast.error(`Có lỗi xảy ra trong quá trình tìm kiếm: ${rs.message_detail}`);
                this.khachhang.list = [];
                return;
            }
            if(this.khachhang.list && this.khachhang.list.length>0) this.rowChanged(0, this.khachhang.list[0]);
            else {
                this.$toast.error("Không tìm thấy dữ liệu thoả mãn yêu cầu tìm kiếm!");
                this.khachhang.list = [];
            }
        },
        async closeForm() {
            try {
                var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/fn_kt_tt_dstb_cung_doicap_khac_kh",{ "ds_para": JSON.stringify({"LOAITB_ID":this.loaitb_id,"MA_DOICAP":this.thuebao.value['MADOICAP']?this.thuebao.value['MADOICAP']:null,"MA_TB":this.thuebao.value['MA_TB']?this.thuebao.value['MA_TB']:null,"TRANGTHAITB_ID":this.thuebao.value['TRANGTHAITB_ID']?this.thuebao.value['TRANGTHAITB_ID']:0,"KIEULD_ID":this.kieuld_id,"TRANGTHAI_TB":this.thuebao.value['TRANGTHAI_TB']?this.thuebao.value['TRANGTHAI_TB']:null}) });
                var apidata = JSON.parse(rs.data);
                if(apidata['ERROR_CODE']==1) {
                    this.$emit('form-close',this.thuebao.value);
                }
                else if(apidata['ERROR_CODE']==-2) {
                    var cfr = await this.$bvModal.msgBoxConfirm(apidata['MESSAGE'], {
                        title: 'Xác nhận hành động',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Hủy',
                    }).then(async v => {
                        return v;
                    });
                    if(cfr)
                        this.$emit('form-close',this.thuebao.value);
                }
                else {
                    this.$toast.error(apidata['MESSAGE']);
            }
            } catch(ex) { this.$toast.error(''+ex); }
        },
        terminateForm() {
            this.$emit('form-close',null);
        },
        async rowChanged(idx,item) {
            if(item['MA_TB']) this.Acc = item['MA_TB'];
            if(item['MA_LT']) this.SoAo = item['MA_LT'];
            this.khachhang.value = item;
            this.$root.showLoading(true);
            try {
                var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/lay_ds_tb_cungdoicap_theo_khid",{ "vkhachhang_id": item['KHACHHANG_ID'],"vkieuld_id": this.kieuld_id });
                if(rs && rs.data && rs.data.length>0)
                    this.thuebao.list = this.UpperCasePropertyList(rs.data);
                else {
                    this.thuebao.list = [];
                    this.thuebao.value = {};
                    this.Acc = '';
                    this.SoAo = '';
                }
                if(this.thuebao.list && this.thuebao.list.length>0) this.thuebaorowChanged(0, this.thuebao.list[0]);
            } catch(ex) {

            }
            finally {
                this.$root.showLoading(false);
            }
        },
        thuebaorowChanged(idx,item) {
            if(item['MA_TB']) this.Acc = item['MA_TB'];
            if(item['MA_LT']) this.SoAo = item['MA_LT'];
            this.thuebao.value = item;
        },
        UpperCasePropertyList(obj) {
            return obj.map( function( item ){
                for(var key in item){
                    var upper = key.toUpperCase();
                    // check if it already wasn't uppercase
                    if( upper !== key ){
                        item[ upper ] = item[key];
                        delete item[key];
                    }
                }
                return item;
            });
        }
    }
}
</script>
