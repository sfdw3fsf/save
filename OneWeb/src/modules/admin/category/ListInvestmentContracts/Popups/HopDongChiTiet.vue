<template>
    <div class="modal-content popup-box">
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="#" @click.prevent="btnLayTT">
                        <span class="icon one-file-attach"></span> Lấy TT
                    </a>
                </li>
                <li>
                    <a href="#" @click.prevent="btnNhapMoi" :class="{disabled: !isNhapMoi}">
                        <span class="icon one-file-plus"></span> Nhập mới
                    </a>
                </li>
                <li>
                    <a href="#" @click.prevent="btnGhiLai" :class="{disabled: !isGhiLai}">
                        <span class="icon one-save"></span> Ghi lại
                    </a>
                </li>
                <li>
                    <a href="#" @click.prevent="btnHuy" :class="{disabled: !isHuy}">
                        <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy
                    </a>
                </li>
                <li>
                    <a href="#" @click.prevent="btnXoa" :class="{disabled: !isXoa}">
                        <span class="icon one-trash"></span> Xóa
                    </a>
                </li>
            </ul>
        </div>
        <div class="popup-body">
            <div class="box-form">
                <div class="legend-title">Thông tin chi tiết hợp đồng</div>
                <div class="info-row">
                    <div class="key w130">Hợp đồng</div>
                    <div class="value">
                        <input disabled v-model="hop_dong" type="text" class="form-control highlight">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w130">Chủ đầu tư/Đối tác</div>
                    <div class="value">
                        <div class="row">
                            <div class="col-sm-4 col-12">
                                <div class="select-custom">
                                    <select v-model="loai_dtdt" class="form-control" @select="selectLoai" @change="changeLoai">
                                        <option selected value="1">Đối tác</option>
                                        <option value="2">Chủ đầu tư</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-sm-8 col-12">
                                <div class="select-custom">
                                    <SelectExt v-model="cp_params.chudt_dt" :options="ds_chudt_dt"
							                @select="selectChuDtDt" tabindex="1"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w130">Dịch vụ VT</div>
                    <div class="value">
                        <div class="select-custom">
                            <SelectExt placeholder="Chọn Dịch vụ VT" v-model="cp_params.dvvt_id" :options="ds_dv_vienthong"
							                @select="selectDVVienThong" />
                        </div>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w130">Loại hình TB</div>
                    <div class="value">
                        <div class="select-custom">
                            <SelectExt placeholder="Chọn Loại hình TB" v-model="cp_params.loaitb_id" :options="ds_lh_thuebao"
							    @select="selectLHThueBao" tabindex="1"/>
                        </div>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w130">Tỷ lệ hoa hồng (%)</div>
                    <div class="value">
                        <div class="input-icon-right">
                            <vue-numeric class="form-control tright red" separator="," v-model="tile_hoahong"
                                output-type="String" v-bind:max="999999999"  v-bind:precision="2">
                            </vue-numeric>
                            <span class="icon one-calculator" @click="isCalculatorShow = !isCalculatorShow"></span>
                            <Calculator @display="tile_hoahong = $event" :value="tile_hoahong" :is-show="isCalculatorShow" @close="isCalculatorShow = !isCalculatorShow"></Calculator>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">Danh sách chi tiết hợp đồng</div>
                <div class="table-content">
                    <DataGrid 
                        v-bind:columns="ds_chitiet.header"                    
                        v-bind:dataSource="ds_chitiet.list"
                        :enable-paging-server="false"                                        
                        :allowPaging="true"
                        :showFilter="true"
                        :showColumnCheckbox="false"
                        ref="grid"
                        @rowClicked = "rowClicked" 
                        @selectedRowChanged="gridChanged"
                        >
                    </DataGrid>
                </div>
            </div>
        </div>
    </div>
</template>
<style scoped>
    .dropdown-menu{ margin: 0px 0px 0px 700px !important }
</style>
<script>
    import DataGrid from "@/components/Controls/DataGrid"
    import SelectExt from "@/components/Controls/SelectExt"
    import api from './API'
    import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
    import moment from 'moment';
    import VueNumeric from 'vue-numeric'
    import Calculator from '@/components/Calculator'
    export default {
        components: {
            DataGrid,
            SelectExt,
            api,
            KDatePicker,
            VueNumeric,
            Calculator,
        },
        name: 'HopDongChiTiet',
        props: [ 'dulieu',],
        data(){
            return{
                isCalculatorShow:false,
                isNhapMoi:false,
                isGhiLai:true,
                isHuy:true,
                isXoa:false,
                ma_hd: "",
                ten_hd: "",
                hop_dong: "",
                loai_dtdt: 1,
                hddtu_id: 1,
                ctdtu_id: 0,
                page_num: 1,
                page_sz: 10000,
                totalRecords: 1,
                index: 0,
                ds_dv_vienthong: [],
                ds_lh_thuebao: [],
                ds_chudt_dt: [],
                tile_hoahong: 0,
                params:{
                    dvvt_id: -1,
                    loaitb_id: -1,
                    chudt_dt: -1
                },
                ds_chitiet: {
                    list: [],
                    header: [
                        { fieldName: "loaidt", headerText: "Loại", allowFiltering: true,},
                        { fieldName: "ma_dt", headerText: "Mã", allowFiltering: true,},
                        { fieldName: "ten_dt", headerText: "Chủ đầu tư/Đối tác", allowFiltering: true,},
                        { fieldName: "ten_dvvt", headerText: "Dịch vụ", allowFiltering: true,},
                        { fieldName: "loaihinh_tb", headerText: "Loại hình", allowFiltering: true},
                        { fieldName: "tyle_hh", headerText: "Tỷ lệ hoa hồng", allowFiltering: true},
                    ],
                    value: {},
                    isEnabled: true,
                    checked: [],
                },
            };
        },
        async mounted() {
            this.ma_hd = this.dulieu.ma_hd;
            this.hddtu_id = this.dulieu.hddtu_id;
            this.hop_dong = this.dulieu.ma_hd == null ? this.dulieu.ten_hd : this.dulieu.ma_hd + " -- " + this.dulieu.ten_hd;
            await this.LoadDSChiTietHD(this.hddtu_id, this.page_num, this.page_sz, this.totalRecords); 
            if (this.ds_chitiet.list.length>0)
                this.SetButton(0);
            else
                this.SetButton(1);
            this.loai_dtdt = 1;
            this.getDsDTDT(1);
        },
        created() {
            this.Loading = true;
            try {
                Promise.all([this.getDsDVVienThong()
                            ]);
                this.Loading = false;
            } catch (ex) {
                this.Loading = false;
            }
            this.Loading = false;
        },
        computed:{
            cp_params: {
                get() {
                    return this.params;
                },
                set(value) {
                    return (this.params = value);
                },
            },
        },
        methods:{
            getDsDVVienThong: async function () {
                var ds_dv_vienthong = [];
                let data = this.GetData(await api.get_ds_dv_vienthong(this.axios));
                if (data && data.length > 0) {
                    data.forEach((e) => {
                        var temp = {
                            id: e.DICHVUVT_ID,
                            text: e.TEN_DVVT,
                        };
                        ds_dv_vienthong.push(temp);
                    });
                } else{
                    // console.log("Không có dữ liệu DV Viễn thông");
                }
                this.ds_dv_vienthong = ds_dv_vienthong;
            },
            getDsLHThuebao: async function (dvvtid) {
                var ds_lh_thuebao = [];
                let data = this.GetData(await api.get_ds_lh_thuebao(this.axios, dvvtid));
                if (data && data.length > 0) {
                    data.forEach((e) => {
                        var temp = {
                            id: e.ID,
                            text: e.NAME,
                        };
                        ds_lh_thuebao.push(temp);
                    });
                } else{
                    // console.log("Không có dữ liệu LH thuê bao");
                }
                this.ds_lh_thuebao = ds_lh_thuebao;
            },
            getDsDTDT: async function (dtdtid) {
                var ds_chudt_dt = [];
                var input = {
                        "loai": dtdtid
                    };
                let data = this.GetData(await api.get_ds_doitac_chudt(this.axios, input));
                if (data && data.length > 0) {
                    data.forEach((e) => {
                        var temp = {
                            id: e.dt_id,
                            text: e.ten_dt,
                        };
                        ds_chudt_dt.push(temp);
                    });
                } else{
                }
                this.ds_chudt_dt = ds_chudt_dt;
            },
            selectDVVienThong: function (value) {
                this.params.dvvt_id = value.id;
                this.getDsLHThuebao(this.params.dvvt_id);
            },
            selectLHThueBao: function (value) {
                this.params.loaitb_id = value.id;
            },
            selectLoai: function (value) {
                this.loai_dtdt = value.id;
            },
            changeLoai: function () {
                this.getDsDTDT(this.loai_dtdt);
            },
            selectChuDtDt: function (value) {
                this.params.chudt_dt = value.id;
            },
            SetButton(kieu) {
                this.isNhapMoi = false;
                this.isGhiLai = false;
                this.isHuy = false;
                this.isXoa = false;
                switch (kieu) {
                    case 0: //Bat dau
                        this.isNhapMoi = true;
                        this.isGhiLai = true;
                        this.isHuy = true;
                        this.isXoa = true;
                        break;
                    case 1: //Them moi
                        this.Clear();
                        this.isGhiLai = true;
                        this.isHuy = true;
                        break;
                    case 2: //huy
                        this.isNhapMoi = true;
                        this.Clear();
                        break;
                    case 3: //edit (Gridview change data)
                        this.isNhapMoi = true;
                        this.isXoa = true;
                        this.isGhiLai = true;
                        this.isHuy = true;
                        break;
                }
            },
            btnLayTT()
            {
                this.LoadDSChiTietHD(this.hddtu_id, this.page_num, this.page_sz, this.totalRecords);
                if (this.ds_chitiet.list.length>0)
                    this.SetButton(0);
                else
                    this.SetButton(1);
            },
            btnNhapMoi()
            {
                this.SetButton(1);
            },
            btnHuy()
            {
                this.SetButton(2);
            },
            btnGhiLai: async function ()
            {
                this.Loading = true;
                var checked = await this.validated();
                if (checked) {
                    var kieu = 0;
                    if(!this.isNhapMoi)
                        kieu = 1;
                    var input = {
                        "chk_new": kieu,
                        "data": [
                            {
                                "CTDTU_ID": this.ctdtu_id,
                                "HDDTU_ID": this.hddtu_id,
                                "LOAI": this.loai_dtdt,
                                "DT_ID": this.params.chudt_dt,
                                "DICHVUVT_ID": this.params.dvvt_id,
                                "LOAITB_ID": this.params.loaitb_id,
                                "TYLE_HH": parseFloat(this.tile_hoahong)
                            }
                        ]
                    };
                    // console.log(input);
                    await this.ins_upd_chitiet(input);
                    await this.LoadDSChiTietHD(this.hddtu_id, this.page_num, this.page_sz, this.totalRecords); 
                    if (this.ds_chitiet.list.length>0)
                        this.SetButton(0);
                    else
                        this.SetButton(1);
                }
                this.Loading = false;
            },
            btnXoa: async function ()
            {
                this.$bvModal.msgBoxConfirm("Bạn chắc chắn muốn xóa?", {
                    title: "Thông báo",
                    size: "m",
                    okTitle: "Đồng ý",
                    cancelTitle: "Hủy",
                }).then(async v => {
                    if(!v) {
                        return;
                    }
                    this.Loading = true;
                    var input = {
                        "ctdtu_id": this.ctdtu_id
                    };
                    await this.del_chitiet(input);
                    await this.LoadDSChiTietHD(this.hddtu_id, this.page_num, this.page_sz, this.totalRecords); 
                    if (this.ds_chitiet.list.length>0)
                        this.SetButton(0);
                    else
                        this.SetButton(1);
                    this.Loading = false;
                });
            },
            LoadDSChiTietHD: async function ( hddtu_id , page_num, page_sz, total_row)
            {
                var input = {
                    hddtu_id: hddtu_id,
                    page_num: page_num,
                    page_sz: page_sz,
                    total_row: total_row,
                };
                this.loading(true);
                let data = this.GetData(await api.get_ds_chitiet(this.axios, input));
                this.ds_chitiet.list = data.out;
                if (this.ds_chitiet.list.length>0)
                {
                    this.$refs.grid.setCurrentSelectedRow(this.index);
                }
                this.loading(false);
            },
            ShowSuccess: function (msg) {
                this.$toast.success(msg);
            },
            ShowWarning: function (msg) {
                this.$toast.warning(msg);
            },
            ShowError: function (msg) {
                this.$toast.error(msg);
            },
            rowClicked(i,obj)
            {
                if(!obj) { 
                    return; 
                }
                this.index = i;                
            },
            gridChanged(obj)
            {
                if(!obj) { 
                    return; 
                }
                // this.hop_dong = obj.ten_hddtu == null ? this.ma_hd : this.ma_hd + " -- " + obj.ten_hddtu;
                this.loai_dtdt = obj.loai;
                this.getDsDTDT(this.loai_dtdt);
                this.params.chudt_dt = obj.dt_id;
                this.params.dvvt_id = obj.dichvuvt_id;
                this.getDsLHThuebao(this.params.dvvt_id);
                this.params.loaitb_id = obj.loaitb_id;
                this.tile_hoahong = obj.tyle_hh == null ? 0 : parseFloat(obj.tyle_hh);
                this.ctdtu_id = obj.ctdtu_id;    
                this.SetButton(3);           
            },
            Clear: function ()
            {
                this.loai_dtdt = 0;
                this.params.chudt_dt = -1;
                this.params.dvvt_id = -1;
                this.params.loaitb_id = -1;
                this.tile_hoahong = 0;
            },
            ins_upd_chitiet: async function (input) {
                let response = await api.ins_upd_chitiet(this.axios, input);
                if (response.data.error === 200 || response.data.error === "200") {
                    if(input["chk_new"] == 1)
                        this.ShowSuccess("Nhập mới thành công");
                    else
                        this.ShowSuccess("Cập nhật thành công");
                } else {
                    this.ShowError(response.data.message);
                }
            },
            del_chitiet: async function (input) {
                let response = await api.del_chitiet(this.axios, input);
                if (response.data.error === 200 || response.data.error === "200") {
                    this.ShowSuccess("Xóa dữ liệu thành công!");
                } else {
                    this.ShowError(response.data.message);
                }
            },
            validated: async function () {
                if (this.params.chudt_dt == -1) {
                    this.ShowWarning("Hãy chọn Chủ đầu tư/Đối tác!");
                    return false;
                }
                if (this.params.dvvt_id == -1) {
                    this.ShowWarning("Hãy chọn Dịch vụ viễn thông!");
                    return false;
                }
                if (this.params.loaitb_id == -1) {
                    this.ShowWarning("Hãy chọn Loại hình thuê bao!");
                    return false;
                }
                if (this.tile_hoahong < 0 || this.tile_hoahong > 100)
                {
                    this.ShowWarning("Tỷ lệ hoa hồng từ 0.00% -> 100.00%");
                    this.tile_pcdt = 0;
                    return false;
                }
                return true;
            },
            GetData: function (response) {
                if (
                    response.data.error === 200 ||
                    response.data.error === "200" ||
                    response.data.error === 204 ||
                    response.data.error === "204"
                ) {
                    return response.data.data;
                } else {
                    // console.log(response.data.message);
                    this.ShowError(response.data.message);
                }
                return [];
            },
        },
    }
</script>
