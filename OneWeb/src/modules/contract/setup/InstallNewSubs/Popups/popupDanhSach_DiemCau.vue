<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>DANH SÁCH ĐIỂM CẦU</div>
        <div class="close -ap icon-close hidden" data-dismiss="modal">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="tsbtnTimKiem_Click">
                    <span class="icon one-search"></span>Tìm kiếm
                </a>
            </li>
            <li v-if="tsbtnNhapMoi.Visible">
                <a href="#" @click.prevent="tsbtnNhapMoi_Click" :class="{disabled: !tsbtnNhapMoi.Enabled}">
                    <span class="icon one-file-plus"></span>Nhập mới
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="tsbtnChapNhan_ItemClick">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
                </a>
            </li>
            <li v-if="tsbtnGhiLai">
                <a href="#" @click.prevent="tsbtnGhiLai_Click">
                    <a href="#"> <span class="icon one-save"></span>Ghi lại </a>
                </a>
            </li>
            <li v-if="tsbtnHuyBo">
                <a href="#" @click.prevent="terminateForm">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Huỷ
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="terminateForm">
                    <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">
        <div class="row">
            <div class="legend-title mart20">
                Loại điểm cầu
            </div>
            <div class="box-form row">
                <div class="col-sm-3 col-12">                
                    <div class="info-row">
                        <div class="key w90">
                            <span class="name">Loại điểm cầu</span>
                        </div>
                        <div class="value">
                            <SelectExt class="select-custom" v-model="cboLoaiDiemCau.value" :dataSource="cboLoaiDiemCau.list" dataTextField="TEXT" dataValueField="ID" @change="txtDiemCauChinh.Text=''"></SelectExt>
                        </div>
                    </div>
                </div>
            
                <div class="col-sm-3 col-12">
                    <div class="info-row">
                        <div class="key w100">
                            <span class="name">Điểm cầu chính</span>
                        </div>
                        <div class="value" :class="{disabled:cboLoaiDiemCau.value==1}">
                            <div class="input-group">
                                <input type="text" class="form-control" aria-describedby="basic-addon2" v-model="txtDiemCauChinh.Text">
                                <div class="input-group-append" @click.prevent="txtDiemCauChinh_ButtonClick">
                                    <span class="input-group-text" id="basic-addon2">...</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3 col-12">
                    <div class="info-row">
                        <div class="key w100">
                            <span class="name">Tên điểm cầu</span>
                        </div>
                        <div class="value" :class="{disabled: true}">
                            <input type="text" class="form-control" v-model="txtTenDC.Text">
                        </div>
                    </div>
                </div>
                <div class="col-sm-3 col-12">
                    <div class="info-row">
                        <div class="key w100">
                            <span class="name">Địa chỉ LĐ</span>
                        </div>
                        <div class="value" :class="{disabled: true}">
                            <input type="text" class="form-control" v-model="txtDiaChiLD.Text">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="legend-title mart20">
                Thông tin tìm kiếm (Đường truyền)
            </div>
            <div class="col-sm-12 col-12">
                <div class="box-form">
                    <div class="info-row">
                        <div class="key w90 ">
                            <div class="check-action">
                                <span class="name">Tỉnh/TP</span>
                            </div>
                        </div>
                        <div class="value" :class="{disabled:cboTinhThanh.ReadOnly}">
                            <SelectExt class="select-custom" v-model="cboTinhThanh.value" :dataSource="cboTinhThanh.list" dataTextField="TENTINH" dataValueField="TINH_ID"></SelectExt>
                        </div>
                        <div class="key">
                            Tra cứu theo
                        </div>
                        <div class="value">
                            <div class="check-action">
                                <input type="radio" class="check" :value="true" v-model="rdbHopDong">
                                <span class="name">Hợp đồng</span>
                            </div>
                            <div class="check-action">
                                <input type="radio" class="check" :value="false" v-model="rdbHopDong">
                                <span class="name">Danh bạ</span>
                            </div>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w90 ">
                            Tra cứu theo
                        </div>
                        <div class="value">
                            <SelectExt class="select-custom" v-model="cboDKTraCuu.value" :dataSource="cboDKTraCuu.list" dataTextField="kieu_tk" dataValueField="kieutk_id"></SelectExt>
                        </div>
                        <div class="value">
                            <input type="text" class="form-control" v-model="txtTraCuu" @keypress.enter="TimKiem">
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w90 ">
                            <div class="check-action">
                                <input type="checkbox" class="check" v-model="chbDichVuVT" @change="cboTKDichVu.Enabled=chbDichVuVT">
                                <span class="name">Dịch vụ</span>
                            </div>
                        </div>
                        <div class="value" :class="{disabled:!cboTKDichVu.Enabled}">
                            <SelectExt class="select-custom" v-model="cboTKDichVu.value" :dataSource="cboTKDichVu.list" dataTextField="TEN_DVVT" dataValueField="DICHVUVT_ID"></SelectExt>
                        </div>
                    </div>                                        
                </div>
                <div class="box-form">
                    <div class="legend-title mart20">Danh sách thuê bao của phiên</div>
                    <DataGrid :columns="gridTBDaGan.cols"
                        :dataSource="gridTBDaGan.list"
                        :enable-paging-server="false"
                        :allowPaging="true"
                        :showFilter="true"
                        :showColumnCheckbox="false"
                        @selectedRowChanged="grvTBDaGan_FocusedRowChanged">
                    </DataGrid>                   
                </div>
                <div class="info-row">
                    <div class="box-form">
                        <div class="legend-title mart20">Danh sách khách hàng</div>
                        <DataGrid :columns="gridKhachHang.cols"
                            :dataSource="gridKhachHang.list"
                            :enable-paging-server="false"
                            :allowPaging="true"
                            :showFilter="true"
                            :showColumnCheckbox="false"
                            @selectedRowChanged="grvKhachHang_FocusedRowChanged">
                        </DataGrid>
                    </div>
                </div>
                <div class="box-form">
                    <div class="legend-title mart20">Danh sách thuê bao (Tra cứu)</div>
                    <DataGrid :columns="gridThueBao.cols"
                        :dataSource="gridThueBao.list"
                        :enable-paging-server="false"
                        :allowPaging="true"
                        :showFilter="true"
                        :showColumnCheckbox="false">
                    </DataGrid>
                </div>
            </div>
        </div>
    </div>
    <b-modal id="popupComponents" size="xl"
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0">
        <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr" isPopup="true" v-on="popupComponentEvts"></component> <!--v-dynamic-events="popupComponentEvents"-->
    </b-modal>
</div>
</template>
<style scoped>

</style>
<script>
import {
  LoaiHinhTB,
  DatabaseConstants,
} from "../Enums";
export default {
    name: 'popupDanhSach_DiemCau',
    props: [ 'dulieu' ],
    watch: {
        'rdbHopDong': async function(val) {
            // var rs1 = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
            //     "command":`select dieukien from admin.KIEU_TK where nhom_id=${val?1:2} order by thutu`
            // });//DataTable dt_sql = tdan.GET_DATA_SQL(sql);
            var rs1 = await this.$root.context.post("/web-hopdong/lapdatmoi/dieukien_kieutk",{"id":val?1:2});
            this.cboDKTraCuu.list = rs1.data;//rs1.data.data_json;
            if(this.cboDKTraCuu.list && this.cboDKTraCuu.list.length>0 && this.cboDKTraCuu.list.filter(x=>x.kieutk_id==this.cboDKTraCuu.value).length<0) {
                this.cboDKTraCuu.value = this.cboDKTraCuu.list[0].kieutk_id;
            }
        }
    },
    data() {
        return {
            gridTBDaGan: {
                list: [],
                cols: [
                    // {fieldName: 'hdtb_id', headerText: 'HDTB_ID', allowFiltering: false, allowSorting: false},
                    // {fieldName: 'thuebao_id', headerText: 'THUEBAO_ID', allowFiltering: false, allowSorting: false},
                    {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: false, allowSorting: false},
                    {fieldName: 'ten_diemcau', headerText: 'Tên điểm cầu', allowFiltering: false, allowSorting: false},
                    {fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: false, allowSorting: false},
                    {fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: false, allowSorting: false},
                    // {fieldName: 'madoicap', headerText: 'Mã đối cáp', allowFiltering: false, allowSorting: false},
                ],
                keycol: "",
                value: {},
            },
            gridKhachHang: {
                list: [],
                cols: [
                    {fieldName: 'hdkh_id', headerText: 'HDKH_ID', allowFiltering: false, allowSorting: false},
                    {fieldName: 'khachhang_id', headerText: 'KHACHHANG_ID', allowFiltering: false, allowSorting: false},
                    {fieldName: 'ma_kh', headerText: 'Mã KH', allowFiltering: false, allowSorting: false},
                    {fieldName: 'ten_kh', headerText: 'Tên KH', allowFiltering: false, allowSorting: false},
                    {fieldName: 'diachi_kh', headerText: 'Địa chỉ KH', allowFiltering: false, allowSorting: false},    
                ],
                keycol: "",
                value: {},
            },
            gridThueBao: {
                list: [],
                cols: [
                    {fieldName: 'hdtb_id', headerText: 'HDTB_ID', allowFiltering: false, allowSorting: false},
                    {fieldName: 'thuebao_id', headerText: 'THUEBAO_ID', allowFiltering: false, allowSorting: false},
                    {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: false, allowSorting: false},
                    {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: false, allowSorting: false},
                    {fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: false, allowSorting: false},
                    {fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: false, allowSorting: false},
                    {fieldName: 'madoicap', headerText: 'Mã đối cáp', allowFiltering: false, allowSorting: false},
                ],
                keycol: "",
                value: {},
            },
            xem: 0,
            tsbtnNhapMoi: {Visible: true, Enabled: true},
            tsbtnGhiLai: true,
            tsbtnHuyBo: true,
            rdbHopDong: false,
            chbDichVuVT: false,
            cboTKDichVu: {
                list: [],
                value: 0,
                Enabled: true,
            },
            cboTinhThanh: {
                list: [],
                value: 0,
                ReadOnly: false,
            },
            cboDKTraCuu: {
                list: [],
                value: '',
            },
            txtTenDC: {
                Text: ''
            },
            txtDiaChiLD: {
                Text: ''
            },
            cboLoaiDiemCau: {
                list: [],
                value: 1,
            },
            txtDiemCauChinh: {
                Text: '',
                Enabled: true,
            },
            tinh_tc: {},
            vhdtb_cha_id: 0,
            vthuebao_cha_id: 0,
            vhdtb_con_id: 0,
            vthuebao_con_id: 0,
            vhdtb_sub_id: 0,
            txtTraCuu: '',
            vhdkh_id: 0,
            vloaitb_id: 0,
            vhdtb_id: 0,
            ma_gd: '',
            // btnThoat: false,
            // loaitb_id: 0,
            // append_name: '',
            // saveList: [],
            // ItemList: [],
            // DicInputControl_giahan: [],
            // dsControl: [],
            // dynamicLayout: [],
            popupComponent: null,
            popupComponentData: null,
            popupComponentAttr: {},
            popupComponentEvts: {
                'form-close':this.popupClosed,
                'acceptChangeCTV':this.popupClosed,
                'acceptChangeNGT':this.popupClosed,
                'PopUpCongTacVien_Thoat':this.popupClosed,
            },
            // popupComponentName: '',
            // curIdx: -1,
            // retVal: null,
            // loaihinh_tb: 0,
            // field_name: '',
            // api_url: '',
            // method: '',
            // hienthi_bang: '',
            // giatri_trave: '',
            // ItemList: [],
            // Text: 'Lấy dữ liệu từ API',
            // DicInputControl: [],
            // dynamicLayout: [],
            // thamsocontrol: [],
        }
    },
    async mounted() {
        if(this.dulieu.vtinh_id) this.cboTinhThanh.value = this.dulieu.vtinh_id;
        if(this.dulieu.vhdtb_id) this.vhdtb_id = this.dulieu.vhdtb_id;
        if(this.dulieu.vhdkh_id) this.vhdkh_id = this.dulieu.vhdkh_id;
        if(this.dulieu.ma_gd) this.ma_gd = this.dulieu.ma_gd;
        if(this.dulieu.vloaitb_id) this.vloaitb_id = this.dulieu.vloaitb_id;
        await this.frmDanhSach_DiemCau_Load();
    },
    computed: {
        vtinh_id: {
            get() {
                return this.cboTinhThanh.value;
            },
            set(val) { this.cboTinhThanh.value = val; }
        },
        vloaiso: function() { return this.cboLoaiDiemCau.value }
    },
    methods: {
        closeForm() {
            this.$emit('form-close',{ "dsHDTB_CT":this.saveList } );
        },
        tsbtnChapNhan_ItemClick() {
            this.closeForm();
        },
        terminateForm() {
            this.$emit('form-close',null);
        },
        async frmDanhSach_DiemCau_Load() {
            this.rdbHopDong = true;
            this.chbDichVuVT = false;
            this.cboTKDichVu.Enabled = false;
            this.cboTinhThanh.ReadOnly = true;
            await this.Load_Combobox();
            await this.HienThi_SoChinh_DaGan();

            if (this.xem == 1)
            {
                this.tsbtnGhiLai = false;
                this.tsbtnHuyBo = false;
            }
            if (this.vloaitb_id == LoaiHinhTB.TDTH_THUYENHINH)
            {
                this.tsbtnNhapMoi.Visible = true;
                this.tsbtnNhapMoi.Enabled = false;
                await this.HTDS_HDTB_DAGAN()
            }
            else
            {
                this.tsbtnNhapMoi.Visible = false;
            }
        },
        async Load_Combobox() {
            if (this.cboTinhThanh.value == 0)
            {
                this.cboTinhThanh.value = this.$root.token.getPhanVungID();
                ///web-quantri/danhmuc-chung/CSS_TINH
                var rs = await this.$root.context.get("/web-quantri/danhmuc-chung/CSS_TINH",{ });
                this.cboTinhThanh.list = rs.data;
                // dtTinh = GlobalVar.layTinh_diadanh().Select("", "tentinh asc").CopyToDataTable();
                // bangts.HT_DS_GRIDLOOKUP(cboTinhThanh, dtTinh, "TENTINH", "TINH_ID", true, 2, cboTinhThanh.Width);
                // cboTinhThanh.EditValue = vtinh_id;

            }
            else
            {
                var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/lay_ds_tinh_thicong_combobox",{ });
                this.cboTinhThanh.list = rs.data;
                this.cboTinhThanh.value = this.$root.token.getPhanVungID(); // Co ve sai sai??? Sao khong lay value luon nhi?????
                // bangts.HT_DS_Tinh_Thicong_Combobox(cboTinhThanh);
                // cboTinhThanh.EditValue = tt_nd.tinh_id;
            }
            var rs1 = await this.$root.context.post("/web-hopdong/lapdatmoi/sp_lay_tinh_tc_theo_tinh_id",{"tinh_id": this.vtinh_id});
            this.tinh_tc = rs1.data;

            //cboDKTraCuu
            // rs1 = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
            //     "command":`select dieukien from admin.KIEU_TK where nhom_id=1 order by thutu`
            // });//DataTable dt_sql = tdan.GET_DATA_SQL(sql);
            rs1 = await this.$root.context.post("/web-hopdong/lapdatmoi/dieukien_kieutk",{"id":1});
            this.cboDKTraCuu.list = rs1.data;//.data_json;
            // select dieukien from admin.KIEU_TK where nhom_id=p_nhom_id order by thutu
            // bangts.HT_Kieu_TK_Cbo_Dev(cboDKTraCuu, 1);

            //cboTKDichVu
            rs1 = await this.$root.context.get("/web-quantri/danhmuc-chung/CSS_DICHVU_VT",{ });
            this.cboTKDichVu.list = rs1.data;
            if((this.cboTKDichVu.list && this.cboTKDichVu.list.length>0) && (!this.cboTKDichVu.value || this.cboTKDichVu.value==0)) {
                this.cboTKDichVu.value = this.cboTKDichVu.list[0].DICHVUVT_ID;
            }
            // bangts.HT_DS_LOOKUPEDIT(cboTKDichVu, bangts.HT_DS_DichVuVT(), "TEN_DVVT", "DICHVUVT_ID");

            //cboLoaiDiemCau
            var dtLoaiSo = [{ "ID":1,"TEXT":"Điểm cầu chính" },{ "ID":2,"TEXT":"Điểm cầu phụ" },]
            this.cboLoaiDiemCau.list = dtLoaiSo;
            this.cboLoaiDiemCau.value = 1;
            this.txtDiemCauChinh.Enabled = false;
        },
        async HienThi_SoChinh_DaGan() {
            var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/ds_tb_hoinghith",{
                "vdk": this.vhdtb_id,
                "vkieu": 0,
                "vloai": 3,
                "vid_tk": this.cboDKTraCuu.value,
                "vdichvuvt_id": this.cboTKDichVu.Enabled?this.cboTKDichVu.value:0,
            });
            var dt = rs.data ? this.LowerCasePropertyList(rs.data) : [];
            if (dt && dt.length  > 0)
            {
                if (dt[0]["thuebao_cha_id"])
                {
                    this.vthuebao_cha_id = dt[0]["thuebao_cha_id"];
                }
                if (dt[0]["hdtb_cha_id"])
                {
                    this.vhdtb_cha_id = dt[0]["hdtb_cha_id"];
                }
                if (dt[0]["thuebao_con_id"])
                {
                    this.vthuebao_con_id = dt[0]["thuebao_con_id"];
                }
                if (dt[0]["hdtb_con_id"])
                {
                    this.vhdtb_con_id = dt[0]["hdtb_con_id"];
                }

                if (this.vthuebao_cha_id || this.vhdtb_cha_id)
                {
                    this.cboLoaiDiemCau.value = 2;
                    this.txtDiemCauChinh.Text = dt[0]["ma_tb"];
                }
                if (this.vhdtb_con_id || this.vthuebao_con_id)
                {
                    this.HienThi_DuongTruyen_DaGan();
                }

            }
        },
        async HienThi_DuongTruyen_DaGan() {
            var dt = [];
            if (this.vhdtb_con_id)
            {
                var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/ds_tb_hoinghith",{
                    "vdk": this.vhdtb_con_id,
                    "vkieu": 1,
                    "vloai": 2,
                    "vid_tk": this.cboDKTraCuu.value,
                    "vdichvuvt_id": this.cboTKDichVu.Enabled?this.cboTKDichVu.value:0,
                });
                dt = rs.data ? this.LowerCasePropertyList(rs.data) : [];
            }
            else if (this.vthuebao_con_id)
            {
                var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/ds_tb_hoinghith",{
                    "vdk": this.vthuebao_con_id,
                    "vkieu": 2,
                    "vloai": 2,
                    "vid_tk": this.cboDKTraCuu.value,
                    "vdichvuvt_id": this.cboTKDichVu.Enabled?this.cboTKDichVu.value:0,
                });
                dt = rs.data ? this.LowerCasePropertyList(rs.data) : [];
            }
            if (dt.length > 0)
                dt[0]["chon"] = "s1";
            this.gridThueBao.list = dt;
        },
        tsbtnTimKiem_Click() {
            this.TimKiem();
        },
        async TimKiem() {
            this.gridThueBao.list = [];
            var rs1 = await this.$root.context.post("/web-hopdong/lapdatmoi/sp_lay_tinh_tc_theo_tinh_id",{"tinh_id": this.vtinh_id});
            this.tinh_tc = rs1.data;
            var vdk = "";
            if (this.cboDKTraCuu.value != "" && this.txtTraCuu.trim() != "")
                vdk = " and " + this.cboDKTraCuu.value.replace(":values", this.txtTraCuu);
            if (this.chbDichVuVT)
                vdk += " and tb.dichvuvt_id = " + this.cboTKDichVu.value;
            if (vdk == "")
                vdk = " and 1 = 0 ";

            try
            {
                if (this.rdbHopDong)
                {
                    var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/ds_tb_hoinghith",{
                        "vdk": this.txtTraCuu,//vdk,
                        "vkieu": 1,
                        "vloai": 1,
                        "vid_tk": this.cboDKTraCuu.value,
                        "vdichvuvt_id": this.cboTKDichVu.Enabled?this.cboTKDichVu.value:0,
                    });
                    this.gridKhachHang.list = rs.data ? this.LowerCasePropertyList(rs.data) : [];
                }
                else
                {
                    var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/ds_tb_hoinghith",{
                        "vdk": this.txtTraCuu,//vdk,
                        "vkieu": 2,
                        "vloai": 1,
                        "vid_tk": this.cboDKTraCuu.value,
                        "vdichvuvt_id": this.cboTKDichVu.Enabled?this.cboTKDichVu.value:0,
                    });
                    this.gridKhachHang.list = rs.data ? this.LowerCasePropertyList(rs.data) : [];
                }
                if(this.gridKhachHang.list && this.gridKhachHang.list.length>0)
                    await this.grvKhachHang_FocusedRowChanged(this.gridKhachHang.list[0]);
            }
            catch (ex)
            {
                this.$toast.error(''+ex);
            }
        },
        async grvKhachHang_FocusedRowChanged(item) {
            try
            {
                if (!item) return;

                var vdk = "";
                if (this.cboDKTraCuu.value && this.txtTraCuu.trim())
                    vdk += " and " + this.cboDKTraCuu.value.replace(":values", this.txtTraCuu);
                if (this.chbDichVuVT)
                    vdk += " and tb.dichvuvt_id = " + this.cboTKDichVu.value;

                if (this.rdbHopDong)
                    vdk += " and kh.hdkh_id = '" + item["hdkh_id"] + "'";
                else
                    vdk += " and kh.khachhang_id = '" + item["khachhang_id"] + "'";
                var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/ds_tb_hoinghith",{
                    "vdk": this.txtTraCuu,//vdk,
                    "vkieu": this.rdbHopDong?1:2,
                    "vloai": 2,
                    "vid_tk": this.cboDKTraCuu.value,
                    "vdichvuvt_id": this.cboTKDichVu.Enabled?this.cboTKDichVu.value:0,
                });
                var dt_ThueBao = rs.data;
                this.gridThueBao.list = ((dt_ThueBao && dt_ThueBao.length > 0) ? dt_ThueBao : []);

            }
            catch (ex)
            {
                this.$toast.error(JSON.stringify(ex));
            }
        },
        HTDS_HDTB_DAGAN: async function () {
            try {
                this.gridTBDaGan.list = [];
                var rs = await this.$root.context.get(`/web-thicong/tram-thicong-dv-cntt/lay_thongtin_hdtb_cntt_sub?hdtb_id=${this.vhdtb_id}`);
                if (rs.data && rs.data.length > 0)
                    this.gridTBDaGan.list = rs.data;
            } catch (e) {
                this.$toast.error('HTDS_HDTB_DAGAN' + JSON.stringify(e))
            }
        },
        async grvTBDaGan_FocusedRowChanged (item) {
            if (!item) return;
            this.txtDiaChiLD.Text = item["diachi_ld"]
            this.txtTenDC.Text = item["ten_diemcau"]
            this.vhdtb_sub_id = Number(item["hdtb_sub_id"]);
            this.tsbtnNhapMoi.Enabled = true;
        },
        tsbtnNhapMoi_Click() {
            this.txtDiaChiLD.Text = this.txtTenDC.Text = "";
            this.tsbtnNhapMoi.Enabled = false;
        },
        async tsbtnGhiLai_Click() {
            try
            {               
                this.vthuebao_con_id = 0;
                this.vhdtb_con_id = 0;
                var vmadoicap = 0;
                var vmatb_tn = "";
                if (this.vloaitb_id == LoaiHinhTB.TDTH_THUYENHINH) {
                    for (var item of this.gridThueBao.list) {
                        if (item["chon"]=="s1") {
                            this.vhdtb_con_id = item["hdtb_id"];
                            this.vthuebao_con_id = item["thuebao_id"];
                            vmatb_tn = item["ma_tb"];
                            break;
                        }
                    }
                    if (vmatb_tn == "" || this.txtTenDC.Text == "" || this.txtDiaChiLD.Text == "") {
                        this.$toast.error("Phải nhập tên điểm cầu, địa chỉ lắp đặt và chọn thuê bao dưới lưới.");
                        return;
                    }
                    var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/capnhat_tdth_truyenhinh",{
                        "vhdtb_cha_id": this.vhdtb_cha_id,
                        "vhdtb_con_id": this.vhdtb_con_id,
                        "vhdtb_id": this.vhdtb_id,
                        "vkieu": this.tsbtnNhapMoi.Enabled ? 1 : 2, //1 tạo mới 2 Cập nhật đường truyền
                        "vloaiso": this.vloaiso,
                        "vmadoicap": vmadoicap,
                        "vmatb_tn": vmatb_tn,
                        "vthuebao_cha_id": this.vthuebao_cha_id,
                        "vthuebao_con_id": this.vthuebao_con_id,
                        "vtinh_id": this.vtinh_id,
                        "diachi_ld": this.txtDiaChiLD.Text,
                        "ten_diemcau": this.txtTenDC.Text,
                        "vhdtb_sub_id": this.vhdtb_sub_id,
                    });
                    var s = rs.data;
                    if (s != "OK")
                    {
                        this.$toast.error(''+s);
                        return;
                    }

                    this.$toast.success("Cập nhật thành công!");
                    await this.HTDS_HDTB_DAGAN();
                } else {
                    if (this.vloaiso == 2)
                    {
                        if (this.vthuebao_cha_id == 0 && this.vhdtb_cha_id == 0 && this.txtDiemCauChinh.Text.trim() == "")
                        {
                            this.$toast.error("Bạn chưa chọn điểm cầu chính cho điểm cầu phụ này");
                            return;
                        }
                    }
                    for (var item of this.gridThueBao.list)
                    {
                        if (item["chon"]=="s1")
                        {
                            this.vhdtb_con_id = item["hdtb_id"];
                            this.vthuebao_con_id = item["thuebao_id"];
                            vmatb_tn = item["ma_tb"];
                            vmadoicap = item["madoicap"];
                            break;
                        }
                    }
                    if (this.vhdtb_con_id == 0 && this.vthuebao_con_id == 0)
                    {
                        var dmg = await this.$bvModal.msgBoxConfirm(`Không có thuê bao (đường truyền) nào được chọn. Bạn có muốn tiếp tục?`, {
                            title: 'Xác nhận hành động',
                            size: 'sm',
                            okTitle: 'Đồng ý',
                            cancelTitle: 'Không đồng ý',
                        }).then(v => {
                            return v;
                        });
                        if (!dmg) return;
                    }
                    var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/capnhat_hoinghith",{
                        "vhdtb_cha_id": this.vhdtb_cha_id,
                        "vhdtb_con_id": this.vhdtb_con_id,
                        "vhdtb_id": this.vhdtb_id,
                        "vkieu": 1, //Cập nhật đường truyền
                        "vloaiso": this.vloaiso,
                        "vmadoicap": vmadoicap,
                        "vmatb_tn": vmatb_tn,
                        "vthuebao_cha_id": this.vthuebao_cha_id,
                        "vthuebao_con_id": this.vthuebao_con_id,
                        "vtinh_id": this.vtinh_id,
                        "diachi_ld": this.txtDiaChiLD.Text,
                        "ten_diemcau": this.txtTenDC.Text,
                        "vhdtb_sub_id": this.vhdtb_sub_id,
                    });
                    var s = rs.data;
                    if (s != "OK")
                    {
                        this.$toast.error(''+s);
                        return;
                    }

                    this.$toast.success("Cập nhật thành công!");
                }
            }
            catch (ex)
            {
                this.$toast.error("Có lỗi khi cập nhật dữ liệu: " + JSON.stringify(ex));
            }
        },
        async txtDiemCauChinh_ButtonClick() {
            try {
                //Kieu o day khong thay truyen, tuy nhien lay tam tu kieu phia tren de test.
                var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/fn_lay_tt_load_dssochinh",{ "ds_para": JSON.stringify({"VDICHVUVT_ID":DichVuVienThong.HOINGHI_TRUYENHINH,"VID":this.vhdkh_id,"VLOAI":2,"VLOAITB_ID":this.vloaitb_id,"VKIEU":this.rdbHopDong?1:2}) });
                var ds = rs.data;
                this.popupComponent = ()=>import('./popupChonGrid');
                this.popupComponentName = "popupDSSoChinh";
                this.popupComponentData = {"ds":ds,cols:[
                    {fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: false, allowSorting: false},
                    {fieldName: 'ma_tb', headerText: 'Mã thuê bao', allowFiltering: false, allowSorting: false},
                    {fieldName: 'trangthai_tb', headerText: 'Trạng thái', allowFiltering: false, allowSorting: false},
                    {fieldName: 'tocdo', headerText: 'Tốc độ', allowFiltering: false, allowSorting: false},
                    {fieldName: 'diachi_ld', headerText: 'Địa chỉ LĐ', allowFiltering: false, allowSorting: false},
                    {fieldName: 'mucuoc_tb', headerText: 'Mức cước', allowFiltering: false, allowSorting: false},
                    {fieldName: 'id_sochinh', headerText: 'ID Số chính', allowFiltering: false, allowSorting: false},
                    {fieldName: 'loai', headerText: 'kieuld_id', allowFiltering: false, allowSorting: false},
                ],keycol: "",allowExit:false,tieude:tieude,tieudebang:"",}
                this.Popup('popupComponents');
            } catch(ex) { this.$toast.error(JSON.stringify(ex)); }
        },
        Popup(modalId) {
            this.$bvModal.show(modalId);
        },
        ClosePopup(modalId) {
            this.$bvModal.hide(modalId);
            this.popupComponent = null;
        },
        async popupClosed(val) {
            if(!val) {
                this.ClosePopup('popupComponents');
                return;
            }
            this.loading(true);
            try {
                switch(this.popupComponentName) {
                    case "popupDSSoChinh":
                        this.txtDiemCauChinh.Text = val.ma_tb;
                        if (val.loai == 1)
                            this.vthuebao_cha_id = val.id_sochinh;
                        else
                            this.vhdtb_cha_id = val.id_sochinh;
                        break;
                }
            } catch(ex) { 
                this.$toast.error(JSON.stringify(ex));
            }
            finally {
                this.ClosePopup('popupComponents');
            }
            this.loading(false);
        },
        LowerCasePropertyList(obj) {
            return obj.map(function (item) {
                for (var key in item) {
                var upper = key.toLowerCase();
                // check if it already wasn't uppercase
                if (upper !== key) {
                    item[upper] = item[key];
                    delete item[key];
                }
                }
                return item;
            });
        },
        UpperCasePropertyList(obj) {
            return obj.map(function (item) {
                for (var key in item) {
                var upper = key.toUpperCase();
                // check if it already wasn't uppercase
                if (upper !== key) {
                    item[upper] = item[key];
                    delete item[key];
                }
                }
                return item;
            });
        },
    }
}
</script>
