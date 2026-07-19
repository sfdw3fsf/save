<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Chọn số CNTT</div>
        <div class="close -ap icon-close" data-dismiss="modal" @click.prevent="closeForm">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="tsbtnTimKiem_Click">
                    <span class="icon one-search"></span>Tìm kiếm
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="chapnhan">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
                </a>
            </li>
            <!-- <li>
                <a href="#" :class="{disabled:!tsbtnIn}">
                    <span class="icon one-print"></span> In số
                </a>
            </li> -->
            <li>
                <a href="#" @click.prevent="tsbtnGiuSo_Click">
                    <span class="icon one-luu-so"></span> Giữ số
                </a>
            </li>
            <!-- <li>
                <a href="#">
                    <span class="icon one-xlsx-import"></span> Xuất Excel
                </a>
            </li> -->
            
        </ul>
    </div>
    <div class="popup-body">     
        <div class="box-form">    
            <div class="col-12">            
                <div class="row">               
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w70">Số máy</div>
                            <div class="value">
                                <input type="text" class="form-control highlight" v-model="somay.value">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w70">
                                <span class="name">Nhóm số</span>
                            </div>
                            <div class="value">
                                <select2 v-model="nhomso.value" class="select-custom" :options="nhomso.list ? nhomso.list.map(ls=>({id:ls.NHOMSO_ID,text:ls.NHOM_SO})) : []"></select2>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w70">
                                <span class="name">Kiểu số</span>
                            </div>
                            <div class="value">
                                <select2 v-model="kieuso.value" class="select-custom" :options="nhomso.value && kieuso.list ? kieuso.list.map(ls=>({id:ls.KIEUSO_ID,text:ls.KIEU_SO})) : []" @change="HT_DS_SOMAY"></select2>
                            </div>
                        </div>
                    </div>
                </div>
            </div>    
        </div>
        </div>
            <div class="col-12">
                <div class="box-form">
                    <div class="legend-title mart20">Danh sách số máy</div>
                    <DataGrid2 
                            v-bind:columns="columns" 
                        v-bind:dataSource="filteredSoMay" :showFilter="true" :showColumnCheckbox="true" 
                        dataKeyField="somay" :defaultColumnCheckboxChecked="false" :panelDataHeight="200" 
                        :selectedRow="somay.idx" @rowClicked="dsSoMayChanged" 
                        @selectedItemsChanged="selectedItemsChanged"> 
                    </DataGrid2>
                </div>
            </div>
        </div>
    </div>
</div>
</template>
<style scoped>
    input.disabled, select.disabled, .disabled input, .disabled select, .disabled /deep/ .select2-selection { width: 100%; background-color: #EEE; color:lightgray;}
</style>
<script>
import {
  THUESO,
} from "../Enums";
export default {
    name: 'popupChonSo_CNTT',
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            loaitb_id: 0,
            dichvuvt_id: 0,
            somay: '',
            cuoc_sodep: 0,
            bancheo: {},
            isCapNhat: false,
            tt_nd: {},
            nhomso: { list: [], value: -1, ischecked:false, },
            kieuso: { list: [], value: 0, ischecked:false, },
            somay: { list: [], value: "", idx: 0, valuearr:[] },
            donvi_ql_id: 0,
            columns: [ 
                            {fieldName: 'somay', headerText: 'Số máy', allowFiltering: true, allowSorting: true}, 
                            {fieldName: 'kieu_so', headerText: 'Kiểu số', allowFiltering: true, allowSorting: false}, 
                            {fieldName: 'tien_hm', headerText: 'Tiền số đẹp', allowFiltering: true, allowSorting: false}, 
                            {fieldName: 'mota', headerText: 'Mô tả', allowFiltering: true, allowSorting: true}, 
            ]
            
        }
    },
    async mounted() {
        this.loading(true);
        try {
            //Load default data
            {
                this.somay.value = this.dulieu.vsomay ? this.dulieu.vsomay : ''
                this.loaitb_id = this.dulieu.vloaitb_id ? this.dulieu.vloaitb_id : this.dulieu.loaitb_id ? this.dulieu.loaitb_id : 0;
                this.dichvuvt_id = this.dulieu.vdichvuvt_id ? this.dulieu.vdichvuvt_id : this.dulieu.dichvuvt_id ? this.dulieu.dichvuvt_id : 0;
                this.hdkh_id = this.dulieu.hdkh_id ? this.dulieu.hdkh_id : 0;
                this.donvi_ql_id = this.dulieu.donvi_ql_id ? this.dulieu.donvi_ql_id : 0;
            }

            await this.frmChonSo_CNTT_Load()
            if (this.dulieu.vloaitb_id == 369 || this.dulieu.vloaitb_id == 2117) this.columns = this.columns.filter(x => x.fieldName != "tien_hm");
            this.loading(false);
        } catch (e) {
            this.loading(false);
        }
    },
    computed: {
        filteredSoMay: function() {
            var newlist = this.somay.list;
            if(newlist && newlist.length>0) this.dsSoMayChanged(0,newlist[0]);
            return newlist?newlist:[];
        }
    },
    methods: {
        closeForm() {
            this.$emit('form-close',null);
        },
        // HienThiDanhSach: async function() {
        //     this.loading(true);
        //     try {
        //         var rs = await this.$root.context.post("/web-bancheo/danh-muc/lay_ds_khoso_cntt", {
        //             "vsomay" : this.somay.value,
        //             "vnguoidung_id": Number(await this.$root.token.getNguoiDungID()),
        //             'vtinh_id': Number(await this.$root.token.getPhanVungID()),              
        //             "vdichvuvt_id": Number(this.dichvuvt_id),    
        //             "vloaitb_id": Number(this.loaitb_id),   
        //             "vkieuso_id": Number(this.kieuso.value),             
        //             "vnhomso_id": Number(this.nhomso.value),
        //         });
        //         this.loading(false);
        //         if(rs.data && rs.data.length>0) {
        //             this.somay.list = rs.data;
        //             if(this.somay.list && this.somay.list.length>0) this.dsSoMayChanged(0,this.somay.list[0]);
        //         }
        //         else 
        //         {
        //             this.somay.list = [];
        //         }
        //     } catch (e) {
        //         this.loading(false);
        //     }
        // },
        selectedItemsChanged(items) {
            this.somay.valuearr = items;
            console.log(items);
            this.tiensodep = items.map(x => x.tien_hm).reduce((a, b) => a + b, 0)
            //this.this.kieuso.value = items.kieuso
        },
        chapnhan: function () {
            var retData = {
                somay:this.somay.value,
                arraySoMay:this.somay.valuearr,
                nhomso:this.nhomso.value,
                tiensodep: this.tiensodep,
                kieuso: this.kieuso.value
            };
            
            this.$emit("form-close", retData);
            this.$bvModal.hide(this.modalId);
        },
        async dsSoMayChanged(idx,item) {
            if (item) this.somay.value = item.somay;
            console.log(item);
            //this.kieuso.value = this.kieuso.list.filter(x => x.kieu_so == item.kieu_so).kieuso_id;
        },
        
        // async TimKiemEnter() {
        //     if(this.timkiem.length <= 5) {
        //         this.$toast.error("Số máy phải lớn hơn 5 ký tự !");
        //         return;
        //     }
        //     var ktra = false;
        //     var kw = this.timkiem;
        //     var dauso = this.ds_dauso.filter(x=>kw.substring(0,6).indexOf(x.prefix)==0);
        //     if(dauso && dauso.length>0) {
        //         this.dauso = dauso[0].prefix;
        //         //TODO Chon row cua dau so
        //         var listsm = this.somay.list.filter(x=>x.somay==kw.trim());
        //         if(listsm && listsm.length>0) {
        //             //TODO chon row cua somay
        //             this.somay.value = this.timkiem.trim();
        //             ktra = true;
        //         }
        //     }
        //     if(!ktra) {
        //         this.$toast.error("Số máy : '" + this.timkiem.trim() + "' không có trong kho số hoặc đã được cấp !");
        //         return;
        //     }
        //     this.tsbtnIn = true;
        // },
        tsbtnBoQua_Click()
        {
            this.closeForm();
        },
        ConvertUpper(arr) {
            return arr.map(function(item) {
                return Object.fromEntries(
                    Object.entries(item).map(([k, v]) => [k.toUpperCase() , v])
                )
            });
        },
        frmChonSo_CNTT_Load: async function () {
            try {
                var load4 = this.$root.context.get("/web-quantri/danhmuc-chung/CSS_NHOM_SO_CNTT");
                var load5 = this.$root.context.get("/web-quantri/danhmuc-chung/CSS_KIEU_SO_CNTT");
                await Promise.all([load4,load5]).then(([data4,data5])=>{
                    if(data4 && data4.data) this.nhomso.list = this.ConvertUpper(data4.data);
                    if (data5 && data5.data) this.kieuso.list = this.ConvertUpper(data5.data);
                    console.log(this.dulieu.vloaitb_id)
                    if (this.dulieu.vloaitb_id == 369) 
                    {
                        this.nhomso.list = this.nhomso.list.filter(x => x.NHOMSO_ID == 213);
                        this.kieuso.list = this.kieuso.list.filter(x => x.NHOMSO_ID == 213);
                        return;
                    }
                    else if ( this.dulieu.vloaitb_id == 2117) 
                    {
                        this.nhomso.list = this.nhomso.list.filter(x => x.NHOMSO_ID == 214);
                        this.kieuso.list = this.kieuso.list.filter(x => x.NHOMSO_ID == 214);
                        return;
                    }

                    else if ( this.dulieu.vloaitb_id == 359) 
                    {
                        this.nhomso.list = this.nhomso.list.filter(x => x.NHOMSO_ID == 216 || x.NHOMSO_ID == 217 || x.NHOMSO_ID == 218 );
                        this.kieuso.list = this.kieuso.list.filter(x =>  x.NHOMSO_ID == 216 || x.NHOMSO_ID == 217 || x.NHOMSO_ID == 218 );
                        return;
                    }

                    // if (this.nhomso.list.length > 0) {
                    //     this.nhomso.value = this.nhomso.list[0].NHOMSO_ID
                    //     var ds_kieuso = this.kieuso.list.filter(e => e.NHOMSO_ID == this.nhomso.value)
                    //     this.kieuso.value = ds_kieuso.length > 0 ? ds_kieuso[0].KIEUSO_ID : 0
                    // }
                    // console.log(this.kieuso.list)
                     this.HT_DS_SOMAY()
                }).finally(()=>{
                    this.loading(false);
                });

                if (this.somay.value) await this.HT_DS_SOMAY()
            } catch (e) {}
        },
        HT_DS_SOMAY: async function() {
            try {
                var rs = await this.$root.context.post("/web-bancheo/danhmuc/lay_ds_khoso_cntt", {
                    "somay" : this.somay.value,
                    "nguoidung_id": Number(await this.$root.token.getNguoiDungID()),
                    'tinh_id': Number(await this.$root.token.getPhanVungID()),              
                    "dichvuvt_id": Number(this.dichvuvt_id),    
                    "loaitb_id": Number(this.loaitb_id),   
                    "kieuso_id": Number(this.kieuso.value),             
                    "nhomso_id": Number(this.nhomso.value),
                });
                if(rs.data && rs.data.length>0) {
                    this.somay.list = rs.data;
                    console.log("rs.data.data", rs.data)
                    if(this.somay.list && this.somay.list.length>0) this.dsSoMayChanged(0,this.somay.list[0]);
                }
                else 
                {
                    this.somay.list = [];
                }
            } catch (e) {}
        },
        tsbtnTimKiem_Click: async function()
        {
            // if (!this.somay.value) {
            //     this.$toast.error("Số máy không được bỏ trống!");
            //     return
            // }
            // if (!this.nhomso.list || !this.nhomso.value) {
            //     this.$toast.error("Chưa chọn nhóm số!");
            //     return
            // }
            // if (!this.kieuso.list || !this.kieuso.value) {
            //     this.$toast.error("Chưa chọn kiểu số!");
            //     return
            // }

            await this.HT_DS_SOMAY();
        },

        // private void tsbtnCapNhat_Click(object sender, EventArgs e)
        // {
        //     isCapNhat = true;
        //     this.Close();
        // }
        async tsbtnGiuSo_Click() {
            this.loading(true);
            var ketqua = await this.$root.context.post("/web-hopdong/lapdatmoi/sp_capnhat_giuso",{
                "dichvuvt_id": this.dichvuvt_id,
                "js_somay": JSON.stringify((this.somay.valuearr && this.somay.valuearr.length>0?this.somay.valuearr:[this.somay.value]).map(x=>({"SOMAY":x}))),//JSON.stringify(this.somay.valuearr),
                "nguoidung_id": this.$root.token.getNguoiDungID()
            });
            if(ketqua && ketqua.message.toLowerCase() == "success")
                this.$toast.success(ketqua.message);
            else this.$toast.error(ketqua.message);
            this.loading(false);
            if(rs.data && rs.data.length>0) {
                this.popupComponent = ()=>import('./popupGiuSo');
                this.popupComponentName = "popupGiuSo";
                this.popupComponentData = {
                    "dichvuvt_id": this.dichvuvt_id, 
                    "loaitb_id": this.loaitb_id,
                    "nguoidung_id": this.$root.token.getNguoiDungID()
                }
                this.Popup('popupComponents');
            } else this.$toast.error(rs.message+ (rs.message_detail?': '+rs.message_detail:''));
        },
        // private void tsbtnGiuSo_Click(object sender, EventArgs e)
        // {
        //     string sqlStatement = " UPDATE {?DB2}.khoso_cntt Set trangthaiso_id = 6, ngay_gs = sysdate,  thoigian_gs = 1, tinh_id = "
        //                         + tt_nd.tinh_id + ", nguoidung_id = " + tt_nd.nguoidung_id + ", tt_nguoidung = '" + tt_nd.ma_nd + "-" + tt_nd.ten_nd + "' "
        //                         + " WHERE SOMAY = '" + vsomay + "' and loaitb_id = " + vloaitb_id;

        //     bancheo.EXECUTE_SQL(bancheo.tinh_thicong(100), sqlStatement);

        //     frmGiuSoBC f = new frmGiuSoBC();
        //     f.vdichvuvt_id = vdichvuvt_id;
        //     f.vloaitb_id = vloaitb_id;
        //     f.ShowDialog(this);
        //     HT_DS_SOMAY();

        // }

        grvChonSo_FocusedRowChanged(item)
        {
            if (!item) return;
            this.somay = item["SOMAY"];
            this.cuoc_sodep = item["CUOC_SODEP"];
        },
    }
}
</script>