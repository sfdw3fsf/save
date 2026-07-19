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
                <div class="legend-title">Thông tin tiến trình đầu tư</div>
                <div class="info-row">
                    <div class="key">Hợp đồng</div>
                    <div class="value">
                        <input disabled v-model="hop_dong" type="text" class="form-control highlight">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">ĐV triển khai <span class="red">(*)</span></div>
                    <div class="value">
                        <div class="select-custom">
                            <SelectExt placeholder="Chọn ĐV triển khai" v-model="cp_params.dv_trienkhai_id" :options="ds_dv_trienkhai"
							                @select="selectDVTrienKhai" />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Ngày giao NV<span class="red">(*)</span></div>
                            <div class="value">
                                <div class="input-icon-right">
                                    <k-date-picker v-model="ngay_giao"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key w80">Số công văn</div>
                            <div class="value">
                                <input ref="scvFocus" v-model="so_congvan" type="text" class="form-control">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key">Dự án XDHT</div>
                    <div class="value">
                        <input ref="daFocus" v-model="du_an" type="text" class="form-control">
                    </div>
                </div>
                
            </div>
            <div class="box-form">
                <div class="legend-title">Danh sách tiến trình đầu tư hợp đồng</div>
                <div class="table-content">
                    <DataGrid 
                        v-bind:columns="ds_tientrinh.header"                    
                        v-bind:dataSource="ds_tientrinh.list"
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
<script>
    import DataGrid from "@/components/Controls/DataGrid"
    import SelectExt from "@/components/Controls/SelectExt"
    import api from './API'
    import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
    import moment from 'moment';
    export default {
        components: {
            DataGrid,
            SelectExt,
            api,
            KDatePicker,
        },
        name: 'HopDongTienTrinh',
        props: [ 'dulieu',],
        data(){
            return{
                isNhapMoi:false,
                isGhiLai:true,
                isHuy:true,
                isXoa:false,
                ten_hd: "",
                ma_hd: "",
                hop_dong: "",
                ngay_giao: "",
                so_congvan: "",
                du_an: "",
                ds_dv_trienkhai: [],
                ttdt_id: 1,
                hddtu_id: 1,
                page_num: 1,
                page_sz: 10000,
                totalRecords: 1,
                index: 0,
                params: {
                    dv_trienkhai_id: -1,
                },
                ds_tientrinh: {
                    list: [],
                    header: [
                        { fieldName: "ten_hddtu", headerText: "Hợp đồng", allowFiltering: true,},
                        { fieldName: "ten_dv", headerText: "Đơn vị triển khai", allowFiltering: true,},
                        { fieldName: "so_cv_giaonv", headerText: "Số công văn", allowFiltering: true,},
                        { fieldName: "ngay_giaonv", headerText: "Ngày giao NV", allowFiltering: true, type: "Date", format: "dd/MM/yyyy",},
                        { fieldName: "duan_xdht", headerText: "Dự án XDHT", allowFiltering: true},
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
            await this.LoadDSTienTrinh(this.hddtu_id, this.page_num, this.page_sz, this.totalRecords); 
            if (this.ds_tientrinh.list.length>0)
                this.SetButton(0);
            else
                this.SetButton(1);
        },
        created() {
            this.Loading = true;
            try {
                Promise.all([this.getDsDVTrienKhai()
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
            getDsDVTrienKhai: async function () {
                var ds_dv_trienkhai = [];
                let data = this.GetData(await api.get_ds_dv_trienkhai(this.axios));
                if (data && data.length > 0) {
                    data.forEach((e) => {
                        var temp = {
                            id: e.donvi_id,
                            text: e.ten_dv,
                        };
                        ds_dv_trienkhai.push(temp);
                    });
                } else{
                    // console.log("Không có dữ liệu DS Nhóm dự án");
                }
                // console.log(ds_dv_trienkhai);
                this.ds_dv_trienkhai = ds_dv_trienkhai.sort(function(a, b) {
                                                const nameA = a.text.toUpperCase();
                                                const nameB = b.text.toUpperCase();
                                                    if (nameA < nameB) {
                                                        return -1;
                                                    }
                                                    if (nameA > nameB) {
                                                        return 1;
                                                    }
                                                    return 0;
                                                });
            },
            selectDVTrienKhai: function (value) {
                this.params.dv_trienkhai_id = value.id;
            },
            btnLayTT()
            {
                this.LoadDSTienTrinh(this.hddtu_id, this.page_num, this.page_sz, this.totalRecords);
                if (this.ds_tientrinh.list.length>0)
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
                        "kieu": kieu,
                        "ttdt_id": this.ttdt_id,
                        "hddt_id": this.hddtu_id,
                        "donvi_tk_id": this.params.dv_trienkhai_id,
                        "so_cv_giaonv": this.so_congvan,
                        "ngay_giaonv": this.ngay_giao,
                        "duan_xdht": this.du_an
                    };
                    console.log(input);
                    await this.ins_upd_del_tientrinh(input);
                    await this.LoadDSTienTrinh(this.hddtu_id, this.page_num, this.page_sz, this.totalRecords); 
                    if (this.ds_tientrinh.list.length>0)
                        this.SetButton(0);
                    else
                        this.SetButton(1);
                }
                this.Loading = false;
            },
            btnXoa: async function ()
            {
                this.$bvModal.msgBoxConfirm("Bạn thật sự muốn xoá dữ liệu không?", {
                    title: "Thông báo",
                    size: "m",
                    okTitle: "Đồng ý",
                    cancelTitle: "Hủy",
                }).then(async v => {
                    if(!v) {
                        return;
                    }
                    this.Loading = true;
                    var kieu = 3;
                    var input = {
                        "kieu": kieu,
                        "ttdt_id": this.ttdt_id,
                        "hddt_id": this.hddtu_id,
                        "donvi_tk_id": this.params.dv_trienkhai_id,
                        "so_cv_giaonv": this.so_congvan,
                        "ngay_giaonv": this.ngay_giao,
                        "duan_xdht": this.du_an
                    };
                    await this.ins_upd_del_tientrinh(input);
                    await this.LoadDSTienTrinh(this.hddtu_id, this.page_num, this.page_sz, this.totalRecords); 
                    if (this.ds_tientrinh.list.length>0)
                        this.SetButton(0);
                    else
                        this.SetButton(1);
                    this.Loading = false;
                });
            },
            validated: async function () {
                if (this.params.dv_trienkhai_id == -1) {
                    this.ShowWarning("Hãy nhập Đơn vị triển khai");
                    return false;
                }
                if (this.ngay_giao == null) {
                    this.ShowWarning("Hãy nhập Ngày giao nhiệm vụ");
                    return false;
                }
                if (this.so_congvan.trim().length > 200) {
                    this.ShowWarning("Số công văn không được lớn hơn 200 ký tự");
                    this.$refs.scvFocus.focus();
                    return false;
                }
                if (this.du_an.trim().length > 200) {
                    this.ShowWarning("Dự án XDHT không được lớn hơn 200 ký tự");
                    this.$refs.daFocus.focus();
                    return false;
                }
                return true;
            },
            LoadDSTienTrinh: async function ( hddtu_id , page_num, page_sz, total_row)
            {
                var input = {
                    hddtu_id: hddtu_id,
                    page_num: page_num,
                    page_sz: page_sz,
                    total_row: total_row,
                };
                this.loading(true);
                let data = this.GetData(await api.get_ds_tientrinh(this.axios, input));
                this.ds_tientrinh.list = data.out;
                if (this.ds_tientrinh.list.length>0)
                {
                    this.$refs.grid.setCurrentSelectedRow(this.index);
                }
                this.loading(false);
            },
            rowClicked(i,obj)
            {
                if(!obj) { 
                    return; 
                }
                this.index = i;
                // this.hop_dong = obj.ten_hddtu == null ? this.ma_hd : this.ma_hd + " -- " + obj.ten_hddtu;
                // this.params.dv_trienkhai_id = obj.donvi_tk_id;
                // this.ngay_giao = moment(obj.ngay_giaonv).format("DD/MM/YYYY");
                // this.so_congvan = obj.so_cv_giaonv;
                // this.du_an = obj.duan_xdht;
                // this.ttdt_id = obj.ttdt_id;
                // this.SetButton(3);  
                
            },
            gridChanged(obj)
            {
                if(!obj) { 
                    return; 
                }
                // this.hop_dong = obj.ten_hddtu == null ? this.ma_hd : this.ma_hd + " -- " + obj.ten_hddtu;
                this.params.dv_trienkhai_id = obj.donvi_tk_id;
                this.ngay_giao = moment(obj.ngay_giaonv).format("DD/MM/YYYY");
                this.so_congvan = obj.so_cv_giaonv;
                this.du_an = obj.duan_xdht;  
                this.ttdt_id = obj.ttdt_id;
                this.SetButton(3);             
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
            Clear: function ()
            {
                this.so_congvan = "";
                this.du_an = "";
                this.params.dv_trienkhai_id = -1;
                this.ngay_giao = moment(new Date()).format('DD/MM/YYYY');
            },
            ins_upd_del_tientrinh: async function (input) {
                let response = await api.ins_upd_del_tientrinh(this.axios, input);
                if (response.data.error === 200 || response.data.error === "200") {
                    if(input["kieu"] == 1)
                        this.ShowSuccess("Nhập mới thành công");
                    else if(input["kieu"] == 0)
                        this.ShowSuccess("Cập nhật thành công");
                    else
                        this.ShowSuccess("Xóa dữ liệu thành công");
                } else {
                    this.ShowError(response.data.message);
                }
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