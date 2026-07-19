<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Chọn hướng kết nối</div>
        <div class="close -ap icon-close" data-dismiss="modal" @click.prevent="closeForm">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="TimKiemEnter">
                    <span class="icon one-search"></span> Tìm kiếm
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="chapnhan">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
                </a>
            </li>
            <li v-if="1==0">
                <a href="#" @click.prevent="closeForm">
                    <span class="icon one-luu-so"></span> Thoát
                </a>
            </li>
        </ul>
        
    </div>
    <div class="popup-body">
        <div class="box-form">
            <div class="legend-title">
                <div class="pull-left">Thông tin tìm kiếm</div>
                <div class="pull-right">
                    <div class="info-row">
                        <div class="key w80">Tỉnh kết nối</div>
                        <div class="value">
                            <div class="select-custom" ref="ds_tinh">
                                <select2 v-model="tinhkn.value" :settings="{ dropdownParent: $refs['ds_tinh'] }" class="select2" :options="tinhkn.list.map(ls=>({id:ls.TINH_ID,text:ls.TENTINH}))"></select2>
                            </div> 
                            
                            
                        </div>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="row">
                <div class="col-sm-5 col-12">
                    <div class="box-form">
                        <div class="info-row">
                            <div class="key w120">
                                <span class="name">Tra cứu theo</span>
                            </div>
                            <div class="value">
                                <select2 v-model="kieutk.value" class="select-custom" :options="dktracuu" @change="x=>kieutk.valueid=dktracuu.find(y=>y.id==x).id_tk"></select2>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w120">
                                <span class="name">Thông tin tra cứu</span>
                            </div>
                            <div class="value">
                                <input type="text" class="form-control" ref="inputTTTC" v-model="thongtintc">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-7 col-12">
                    <div class="box-form">
                        <div class="info-row">
                            <div class="key w90 no-wrap">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="dvvt.isenabled">
                                    <span class="name">Dịch vụ</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="row">
                                    <div class="col-sm-7 col-12">
                                        <select2 v-model="dichvuvt" class="select-custom" :disabled="!dvvt.isenabled" :options="dvvt.list.map(ls=>({id:ls.DICHVUVT_ID,text:ls.TEN_DVVT}))"></select2>
                                    </div>
                                    <div class="col-sm-5 col-12" style="padding-top: 7px !important;">
                                        <div class="info-row">
                                            <div class="value">
                                                <div class="check-action">
                                                    <input type="radio" class="check" value="2" v-model="isHopDong">
                                                    <span class="name">Hợp đồng</span>
                                                </div>
                                            </div>
                                        </div>
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90 no-wrap">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="daivt.isenabled">
                                    <span class="name">Đơn vị</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="row">
                                    <div class="col-sm-7 col-12">
                                        <select2 v-model="donvi" class="select-custom" :disabled="!daivt.isenabled" :options="daivt.list.map(ls=>({id:ls.DONVI_ID,text:ls.TEN_DV}))"></select2>
                                    </div>
                                    <div class="col-sm-5 col-12" style="padding-top: 7px !important;">
                                        <div class="info-row">
                                            <div class="value">
                                                <div class="check-action">
                                                    <input type="radio" class="check" value="1" v-model="isHopDong">
                                                    <span class="name">Danh bạ</span>
                                                </div>
                                            </div>
                                        </div>
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <div class="box-form">
            <div class="legend-title mart20">Danh sách khách hàng</div>
            <DataGrid :columns="khachhang.cols" 
                :dataSource="khachhang.list" 
                :enable-paging-server="false"                                        
                :allowPaging="true"
                :showFilter="true"
                :showColumnCheckbox="false" 
                @selectedRowChanged="khachhang_selectedRowChanged">
            </DataGrid>
        </div>
        <div class="box-form">
            <div class="legend-title mart20">Danh sách hướng kết nối</div>
            <DataGrid :columns="huongkn.cols" 
                :dataSource="huongkn.list" 
                :enable-paging-server="false"                                        
                :allowPaging="true"
                :showFilter="true"
                :showColumnCheckbox="false" 
                @selectedRowChanged="huongkn_selectedRowChanged">
            </DataGrid>
        </div>
    </div>
</div>
</template>
<style scoped>
    input.disabled, select.disabled, .disabled input, .disabled select, .disabled /deep/ .select2-selection { background-color: #EEE; color:lightgray;}
</style>
<script>
// import {
//   THUESO,
// } from "../Enums";
export default {
    name: 'popupDSHuongKNV2',
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            tinhkn: { list: [], value: 0 },
            dvvt: { list: [], value: 0, isenabled:false, },
            daivt: { list: [], value: 0, isenabled:false, },
            kieutk: { hd: [], db:[], value: '', valueid: 0, },
            thongtintc: '',
            isHopDong: 1,
            khachhang: { 
                list:[], 
                cols: [            
                    {fieldName: 'STT', headerText: 'Stt', allowFiltering: true,cellCssClass: 'text-right',},
                    // {fieldName: 'KHACHHANG_ID', headerText: 'KHACHHANG ID', allowFiltering: true, cellCssClass: 'text-right',},
                    // {fieldName: 'HDKH_ID', headerText: 'HDKH ID', allowFiltering: true, cellCssClass: 'text-right',},
                    {fieldName: 'MA_KH', headerText: 'Mã KH', allowFiltering: true,},                            
                    {fieldName: 'MA_HD', headerText: 'Mã HĐ/PL', allowFiltering: true,},
                    {fieldName: 'TEN_KH', headerText: 'Tên khách hàng', allowFiltering: true,},
                    {fieldName: 'DIACHI_KH', headerText: 'Địa chỉ khách hàng', allowFiltering: true,},
                ],
                value: {}, 
            },
            huongkn: { 
                list:[], 
                cols: [            
                    {fieldName: 'vrf_vfi', headerText: 'VRF/VFI', allowFiltering: true,},
                    {fieldName: 'ma_tb', headerText: 'Mã thuê bao', allowFiltering: true, },
                    {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, },
                    {fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true,},                            
                    {fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true,},
                    {fieldName: 'tocdo', headerText: 'Tốc độ', allowFiltering: true,},
                    {fieldName: 'ma_lt', headerText: 'Số ảo', allowFiltering: true,},
                    {fieldName: 'trangthai_tb', headerText: 'Trạng thái', allowFiltering: true,},
                    {fieldName: 'ma_crm', headerText: 'Mã CRM', allowFiltering: true,},
                    {fieldName: 'ma_erp', headerText: 'Mã ERP', allowFiltering: true,},
                    {fieldName: 'ma_vtt', headerText: 'Mã VTT', allowFiltering: true,},
                ],
                value: {}, 
            },
        }
    },
    async mounted() {
        this.loading(true);
        try {
            var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/fn_lay_tt_load_huongkn", {"ds_para": JSON.stringify({"SD_DSHUONGKN_V2":1})});
            if(rs.data && rs.data.length>0) {
                var apidata = JSON.parse(rs.data);
                if(apidata['ERROR_CODE']!=1) { this.$toast.error(apidata['MESSAGE']); return; }
                apidata = apidata['RESULT'];
                this.tinhkn.list = apidata['TINH_KN'];
                if(this.tinhkn.list && this.tinhkn.list.length>0) this.tinhkn.value = this.tinhkn.list.filter(x=>x.TINH_ID>0)[0].TINH_ID;
                if(this.dulieu.tinhkn_id && this.tinhkn.list.filter(x=>x.TINH_ID==this.dulieu.tinhkn_id).length>0) this.tinhkn.value = this.dulieu.tinhkn_id;
                this.dvvt.list = apidata['DVVT'];
                if(this.dvvt.list && this.dvvt.list.length>0) this.dvvt.value = this.dvvt.list[0].DICHVUVT_ID;
                if(this.dulieu.dichvuvt_id && this.dvvt.list.filter(x=>x.DICHVUVT_ID==this.dulieu.dichvuvt_id).length>0) this.dvvt.value = this.dulieu.dichvuvt_id;
                this.kieutk.hd = apidata['KIEU_TK_HD'];
                this.kieutk.db = apidata['KIEU_TK_DB'];
                if(this.kieutk.hd && this.kieutk.hd.length>0) this.kieutk.value = this.kieutk.hd[0].KIEU_TK;
                this.daivt.list = apidata['DAI_VT'];
                if(this.daivt.list && this.daivt.list.length>0) this.daivt.value = this.daivt.list[0].DONVI_ID;
            }
        } catch(ex) { this.$toast.error(''+ex); }
        finally {
            this.loading(false);
        }
        
    },
    computed: {
        donvi: {
            get() {
                if(this.daivt.isenabled) return this.daivt.value;
                else return 0;
            },
            set(value) { this.daivt.value = value; }
        },
        dichvuvt: {
            get() {
                if(this.dvvt.isenabled) {
                    return this.dvvt.value;
                }
                else return 0;
            },
            set(value) { this.dvvt.value = value; }
        },
        dktracuu: function() {
            if(this.isHopDong==2) return this.kieutk.hd.map(ls=>({id:ls.KIEU_TK,text:ls.KIEU_TK, id_tk:ls.KIEUTK_ID}));
            else return this.kieutk.db.map(ls=>({id:ls.KIEU_TK,text:ls.KIEU_TK, id_tk:ls.KIEUTK_ID}));
        }
    },
    methods: {
        closeForm() {
            this.$emit('form-close',null);
        },
        chapnhan() {
            var obj = this.huongkn.value;
            if((!obj || !obj['id_sochinh'] || obj['id_sochinh'] < 0) && (!this.tinhkn.value || this.tinhkn.value <= 0)) {
                this.$toast.error('Bạn phải chọn tỉnh kết nối đối với hướng kết nối tới tổng đài !');
                return;
            }
            if(!obj || !obj['id_sochinh']) this.$emit('form-close',null);
            else
                this.$emit('form-close',{iid_sochinh:obj['id_sochinh'],iloai:obj['loai'],shuongkn:obj['huong_kn'],vma_tb:obj['ma_tb'],diachi_ld:obj['diachi_ld'],itinh_kn_id:this.tinhkn.value,vrf:obj['vrf_vfi']});
        },
        huongkn_selectedRowChanged(items) {
            if(!items) { this.huongkn.value = {}; return; }
            this.huongkn.value = items;
        },
        async khachhang_selectedRowChanged(items) {
            if(!items) return;
            this.khachhang.value = items;
            var vid = 0;
            if(items['KHACHHANG_ID'] && items['KHACHHANG_ID']!=0) vid = items['KHACHHANG_ID'];
            if(items['HDKH_ID'] && items['HDKH_ID']!=0) vid = items['HDKH_ID'];

            //var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/khdn_lay_ds_sochinh_bss", {
            var rs = await this.$root.context.post("/web-bancheo/tracuu/khdn_lay_ds_sochinh_bss", {
                "tinhthicong_id": this.tinhkn.value,
                "vdichvuvt_id": this.dichvuvt,
                "vid": vid,
                // "vkieu": this.dulieu.kieu?this.dulieu.kieu:0,
                "vloai": this.isHopDong,
            });
            if(rs.data && rs.data.length>0) {
                this.huongkn.list = rs.data;
            } else this.huongkn.list = [];
        },
        async TimKiemEnter() {
            if(this.thongtintc.trim()==''){
                this.$toast.error('Hãy nhập giá trị tìm kiếm!')
                this.$refs.inputTTTC.focus()
                return
            }
            this.loading(true);
            try {
                var dieukien = "";
                var lst = [];
                if(this.isHopDong==2) lst = this.kieutk.hd;
                else lst = this.kieutk.db;
                dieukien = lst.filter(x=>x.KIEU_TK==this.kieutk.value)[0].DIEUKIEN;
                //var rs = await this.$root.context.post("/web-bancheo/tracuu/fn_timkiem_huongkn_v2", {"tinhthicong_id":this.tinhkn.value,"p_ds_para": JSON.stringify({"DK_TRACUU":this.kieutk.value,"RDO_DB":this.isHopDong==1,"RDO_HD":this.isHopDong==2,"DICHVUVT_ID":this.dichvuvt,"TXT_TC":this.thongtintc,"LOAI":this.isHopDong,"DIEUKIEN":dieukien,"TINH_TC":this.tinhkn.value,"DONVI_ID":this.donvi})});
                var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/fn_timkiem_huongkn_v2", {"ds_para": JSON.stringify({"DK_TRACUU":this.kieutk.value,"RDO_DB":this.isHopDong==1,"RDO_HD":this.isHopDong==2,"DICHVUVT_ID":this.dichvuvt,"TXT_TC":this.thongtintc,"LOAI":this.isHopDong,"DIEUKIEN":dieukien,"TINH_TC":this.tinhkn.value,"DONVI_ID":this.donvi})}); //DONVI_ID fix code cu = 0, 
                if(rs.data && rs.data.length>0) {
                    
                    var apidata = JSON.parse(rs.data);
                    console.info('1',apidata,rs.data);
                    if(apidata['ERROR_CODE']==-2) {
                        if(apidata['MESSAGE']=="TRACUU_DANHBA_COMMON" || this.kieutk.value == "Số ảo") {
                            var rs1 = await this.$root.context.post("/web-bancheo/tracuu/lay_db_theo_matb", { tinhthicong_id:this.tinhkn.value, ma_tb:this.thongtintc, donvi_dl_id:this.donvi, dichvuvt_id:this.dvvt.value });
                            if(rs1.data && rs1.data.length>0) {
                                this.khachhang.list = this.UpperCasePropertyList(rs1.data);
                                this.huongkn.list = [];
                                return;
                            }
                        }
                        if((apidata['MESSAGE']=='BANCHEO' && this.kieutk.value=='VRF/VFI') || apidata['MESSAGE']=='LAY_DS_DB_THEO_VRF') {
                            var rs1 = await this.$root.context.post("/web-bancheo/tracuu/lay_ds_db_theo_vrf", { tinhthicong_id:this.tinhkn.value, vrf:this.thongtintc });
                            if(rs1.data && rs1.data.length>0) {
                                this.khachhang.list = this.UpperCasePropertyList(rs1.data);
                                this.huongkn.list = [];
                                return;
                            }
                        }
                        else if(apidata['MESSAGE']=='TRACUU_DANHBA.TRA_CUU_DB_HD_KHACHHANG') {
                            console.info('2',apidata,rs.data);
                            var rs1 = await this.$root.context.post("/web-bancheo/tracuu/tra_cuu_db_hd_khachhang", { tinhthicong_id:this.tinhkn.value, vdieukien:this.thongtintc, vloai_id: this.isHopDong, vdonvi_id:this.donvi, vdichvuvt_id:this.dvvt.value });
                            //var rs1 = await this.$root.context.post("/web-bancheo/tracuu/sp_tra_cuu_db_hd_khachhang", { tinhthicong_id:this.tinhkn.value, vdieukien:this.thongtintc, vloai_id: this.isHopDong, vdonvi_id:this.donvi, vdichvuvt_id:this.dvvt.value, vkieutk_id:this.kieutk.valueid });
                            console.info('3',rs1,rs1.data);
                            if(rs1.data && rs1.data.length>0) {
                                this.khachhang.list = this.UpperCasePropertyList(rs1.data);
                                this.huongkn.list = [];
                                return;
                            }
                        }
                        else if(apidata['MESSAGE']=='SP_TRA_CUU_DB_HD_KHACHHANG') {
                            console.info('2',apidata,rs.data);
                            //var rs1 = await this.$root.context.post("/web-bancheo/tracuu/tra_cuu_db_hd_khachhang", { tinhthicong_id:this.tinhkn.value, vdieukien:this.thongtintc, vloai_id: this.isHopDong, vdonvi_id:this.donvi, vdichvuvt_id:this.dvvt.value });
                            var rs1 = await this.$root.context.post("/web-bancheo/tracuu/sp_tra_cuu_db_hd_khachhang", { tinhthicong_id:this.tinhkn.value, vdieukien:this.thongtintc, vloai_id: this.isHopDong, vdonvi_id:this.donvi, vdichvuvt_id:this.dvvt.value, vkieutk_id:this.kieutk.valueid });
                            console.info('3',rs1,rs1.data);
                            if(rs1.data && rs1.data.length>0) {
                                this.khachhang.list = this.UpperCasePropertyList(rs1.data);
                                this.huongkn.list = [];
                                return;
                            }
                        }
                        else if(this.kieutk.value=='Mã CRM' || this.kieutk.value=='Mã VTN' || this.kieutk.value=='Mã ERP' || this.kieutk.value=='Mã VNPT') {
                            // Cho` API cua DuyBN
                            var vvtype = 0;
                            if(this.kieutk.value=='Mã CRM') vvtype = 1;
                            else if(this.kieutk.value=='Mã VTN') vvtype = 2;
                            else if(this.kieutk.value=='Mã ERP') vvtype = 3;
                            else if(this.kieutk.value=='Mã VNPT') vvtype = 4;
                            var rs1 = await this.$root.context.post("/web-bancheo/tracuu/sp_tracuu", { tinhthicong_id:this.tinhkn.value, txTextBoxTC:this.thongtintc, type: vvtype, });
                            if(rs1.data && rs1.data.length>0) {
                                this.khachhang.list = this.UpperCasePropertyList(rs1.data);
                                this.huongkn.list = [];
                                return;
                            }
                        }
                    }
                    else if(apidata['ERROR_CODE']!=1) { this.$toast.error(apidata['MESSAGE']); return; }
                    apidata = apidata['RESULT'];
                    this.khachhang.list = apidata && apidata['DS']?apidata['DS']:[];
                    this.huongkn.list = [];
                    //TODO xu ly tiep
                }
            } catch(ex) { this.$toast.error(''+ex); }
            finally {
                this.loading(false);
            }
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
        },
    }
}
</script>