<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Đăng ký sổ liên lạc</div>
        <div class="close -ap icon-close hidden" data-dismiss="modal">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="btnGhiLai_ItemClick">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Ghi lại
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="btnXoa_ItemClick">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Xoá
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">
        <div class="box-move-select-table">
            <div class="box-col">
                <div class="box-form">
                    <div class="legend-title mart20">Danh sách danh bạ</div>
                    <DataGrid 
                        v-bind:columns="grcDSDanhBa.cols" 
                        v-bind:dataSource="grcDSDanhBa.list" :showFilter="false" :showColumnCheckbox="false" 
                        :dataKeyField="grcDSDanhBa.keycol" :defaultColumnCheckboxChecked="false" :panelDataHeight="'200'"> 
                    </DataGrid>
                    <!-- @rowClicked="gdvDanhMucLoaiHoaDon_FocusedRowChanged" -->
                </div>
                <div class="box-form">
                    <button class="btn btn-main" @click.prevent="btnThemDsHuy_Click">
                        <span class="-ap icon-arrow-down"></span>
                    </button>
                    <button class="btn  btn-main" @click.prevent="btnBoDSHuy_Click">
                        <span class="-ap icon-arrow-up"></span>
                    </button>
                </div>
                <div class="box-form">
                    <div class="legend-title mart20">Danh sách học sinh đã đăng ký</div>
                    <DataGrid 
                        v-bind:columns="grcDSHocSinhDangKy.cols" 
                        v-bind:dataSource="grcDSHocSinhDangKy.list" :showFilter="false" :showColumnCheckbox="false" 
                        :dataKeyField="grcDSHocSinhDangKy.keycol" :defaultColumnCheckboxChecked="false" :panelDataHeight="'200'"> 
                    </DataGrid>
                    <!-- @rowClicked="gdvDanhMucLoaiHoaDon_FocusedRowChanged" -->
                </div>
            </div>
            <div class="actions padt0 block">
                <button class="btn btn-main" @click.prevent="btnDangKy_Click">
                    <span class="-ap  icon-arrow-left"></span>
                </button>
                <button class="btn  btn-main" @click.prevent="btnHuyDK_Click">
                    <span class="-ap icon-arrow-32"></span><!-- </span><span class="-ap icon-arrow-right"></span> -->
                </button>
            </div>
            <div class="box-col">
                <div class="box-form">
                    <div class="legend-title mart20">Danh sách học sinh theo lớp</div>
                    <div class="info-row">
                        <div class="key w90">
                            <span class="name">Khối học</span>
                        </div>
                        <div class="value">
                            <SelectExt class="form-control" v-model="cboKhoi.value" :dataSource="cboKhoi.list" dataTextField="Value" dataValueField="Key"></SelectExt>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w90">
                            <span class="name">Lớp học</span>
                        </div>
                        <div class="value">
                            <ComboboxGrid  class="select-custom"
                                :columns="[
                                {fieldName: 'lop_hoc_id', headerText: 'Mã lớp học', allowFiltering: true, width:'70'},
                                {fieldName: 'ten_lop', headerText: 'Tên lớp học', allowFiltering: true, width:'300'}
                                ]"
                                :dataSource="lopThuocKhoi" textField="ten_lop" valueField="lop_hoc_id"
                                @selectedChanged="(item)=>{cboLopHoc.value=item.lop_hoc_id;}">
                            </ComboboxGrid>
                        </div>
                    </div>
                    <div class="info-row">
                        <button @click.prevent="simpleButton1_Click">Lấy danh sách</button>
                        <input type="checkbox" @change="ckbSelectAll_CheckedChanged_1" v-model="ckbSelectAll" /> Chọn tất cả
                    </div>
                    <DataGrid 
                        v-bind:columns="grcDanhSachHocSinh.cols" 
                        v-bind:dataSource="grcDanhSachHocSinh.list" :showFilter="false" :showColumnCheckbox="false" 
                        :dataKeyField="grcDanhSachHocSinh.keycol" :defaultColumnCheckboxChecked="false" :panelDataHeight="'500'">
                    </DataGrid>
                    <!-- @rowDoubleClicked="gdvMauHoaDon_DoubleClick">  -->
                </div>
            </div>
        </div>
    </div>
</div>
</template>
<style scoped>

</style>
<script>
import API from '../InstallNewSubsAPI'
import {
  DatabaseConstants,
} from "../Enums";
export default {
    name: 'popupChonMauHDDT',
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            grcDSHocSinhDangKy: {
                list: [],
                cols: [
                    {fieldName: 'TEN_TB_CT', headerText: 'Tên học sinh', allowFiltering: false, allowSorting: false},
                    {fieldName: 'DT_GIAMHO', headerText: 'Số ĐT', allowFiltering: false, allowSorting: false},
                    {fieldName: 'DAIDIEN', headerText: 'Lớp', allowFiltering: false, allowSorting: false},
                    {fieldName: 'IP_CT', headerText: 'Giá tiền', allowFiltering: false, allowSorting: false},
                    {fieldName: 'WEBSITE_CT', headerText: 'Mạng', allowFiltering: false, allowSorting: false},
                ],
                keycol: "",
                value: {},
            },
            grcDSDanhBa: {
                list: [],
                cols: [
                    {fieldName: 'TEN_TB_CT', headerText: 'Tên học sinh', allowFiltering: false, allowSorting: false},
                    {fieldName: 'DT_GIAMHO', headerText: 'Số ĐT', allowFiltering: false, allowSorting: false},
                    {fieldName: 'DAIDIEN', headerText: 'Lớp', allowFiltering: false, allowSorting: false},
                    {fieldName: 'IP_CT', headerText: 'Giá tiền', allowFiltering: false, allowSorting: false},
                    {fieldName: 'WEBSITE_CT', headerText: 'Mạng', allowFiltering: false, allowSorting: false},
                ],
                keycol: "",
                value: {},
            },
            grcDanhSachHocSinh: {
                list: [],
                cols: [
                    {fieldName: 'TEN_TB_CT', headerText: 'Tên học sinh', allowFiltering: false, allowSorting: false},
                    {fieldName: 'DT_GIAMHO', headerText: 'Số ĐT', allowFiltering: false, allowSorting: false},
                    {fieldName: 'DAIDIEN', headerText: 'Lớp', allowFiltering: false, allowSorting: false},
                ],
                keycol: "",
                value: {},
            },
            id_truong: '',
            // dshs_dadk: [],
            ds_hsdadk: [],
            ds_hschuadk: [],
            tocdo_id: 0,
            tongso_hs: 0,
            tong_tien: 0,
            cboLopHoc: {
                list: [],
                value: 0,
            },
            tocdo_adsl: [],
            thuebao_id: 0,
            cboKhoi: {
                list: [],
                value: 0,
            },
            trangthai_biendong: 0, // 0 = them, dang ky, 1 = chuyen mang, 2 = huy so lien lac
            tongsoSMS_NOIM: 0,
            tongsoSMS_NGOM: 0,
            ckbSelectAll: false,
        }
    },
    mounted() {
        if(this.dulieu.id_truong) this.id_truong = this.dulieu.id_truong;
        if(this.dulieu.ds_hsdadk) this.dshs_dadk = this.dulieu.dshs_dadk;
        if(this.dulieu.tocdo_id) this.tocdo_id = this.dulieu.tocdo_id;
        if(this.dulieu.trangthai_biendong) this.trangthai_biendong = this.dulieu.trangthai_biendong;
        this.frmDangKySoLienLac_Load();
    },
    computed: {
        lopThuocKhoi() {
            if(this.cboKhoi.value<=0 || !this.cboLopHoc.list || this.cboLopHoc.list.length<=0) return [];
            var ds = this.cboLopHoc.list.filter(x=>x.khoi_hoc==this.cboKhoi.value);
            if(ds && ds.length>0 && ds.indexOf(this.cboLopHoc.value)<0)
                this.cboLopHoc.value = ds[0].lop_hoc_id;
            return ds;
        }
    },
    methods: {
        closeForm() {
            this.$emit('form-close',{"tong_tien":this.tong_tien,"tongso_hs":this.tongso_hs});
        },
        terminateForm() {
            this.$emit('form-close',null);
        },
        rowChanged(idx,item) {
            this.ds.value = item;
        },
        async frmDangKySoLienLac_Load() {
            await this.$root.context.post('/web-hopdong/lapdatmoi/fn_init_properties_cntt',{"ds_para":JSON.stringify({
                "LOAITB_ID":this.thuebao_id,
                "FIELDNAME":this.id_truong,
                "TENBANG":"",
                "LOAIHD_ID":0,
                "KIEULD_ID":0,
                "ITEM_LIST":[]
            })});
            this.LoadDSKhoi();
            // this.LoadDSLop();//bo vi load cung danh sach khoi luon!!
            this.LoadTocDo();
            this.LoadDSDaDangKy();
            this.LoadDSDanhBa();
            this.tongso_hs = 0;
            this.tong_tien = 0;
        },
        async LoadDSKhoi() {
            var rs = await this.$root.context.get('/tichhop/edu/l1?call=app.doisoat.serv.api.getListLopTruong&truong_id='+this.id_truong,{});//_eduServices.LayDanhSachKhoiThuocTruong(Convert.ToInt64(id_truong));
            this.cboLopHoc.list = (rs && rs.data && rs.data.aRoot?rs.data.aRoot:[]);
            this.cboKhoi.list = this.cboLopHoc.list.map(x=>x.khoi_hoc).sort().map(x=>({"Key":x,"Value":x}));
            // cboKhoi.DisplayMember = "Value";
            // cboKhoi.ValueMember = "Key";
        },
        // LoadDSLop() {
        //     var rs = await this.$root.context.get('/tichhop/edu/l1?call=app.doisoat.serv.api.getListLopTruong&truong_id='+this.id_truong,{});//_eduServices.LayDanhSachLopThuocTruong(Convert.ToInt64(id_truong));
        //     var lophocs = (rs && rs.data && rs.data.Data?rs.data.Data:[]);
        //     this.cboLopHoc.list = lophocs;
        //     // cboLopHoc.Properties.View.Columns[0].Visible = false;
        //     // cboLopHoc.Properties.View.Columns[2].Visible = false;
        //     // cboLopHoc.Properties.View.OptionsView.ShowColumnHeaders = false;
        //     // cboLopHoc.Properties.View.OptionsView.ShowAutoFilterRow = true;
        //     // cboLopHoc.Properties.DisplayMember = "ten_lop";
        //     // cboLopHoc.Properties.ValueMember = "lop_hoc_id";
        // },
        async LoadTocDo() {
            var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/tocdo_adsl',{"id":this.tocdo_id});// await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                //"command":`SELECT * FROM ${DatabaseConstants.DB2}.TOCDO_ADSL a where a.TOCDO_ID = ${this.tocdo_id}`
            //})
            // var dt_sql = tdan.GET_DATA_SQL(sql);
            this.tocdo_adsl = rs.data;
        },
        LoadDSDaDangKy() {
            this.grcDSHocSinhDangKy.list = this.ds_hsdadk;
        },
        async LoadDSDanhBa() {
            // var sql = `select  so_dt_ct,
            //     ten_tb_ct,
            //     email_ct,
            //     diachi_ld,
            //     mst_ct,
            //     dt_giamho,
            //     daidien,
            //     loaitb_id,
            //     parent_field,
            //     ip_ct,
            //     domain_ct,
            //     website_ct,
            //     ma_phu_ct,
            //     ma_chuan_ct  from ${DatabaseConstants.DB2}.db_thuebao_ct where thuebao_id = ${this.thuebao_id}`;
            // var rs = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
            //     "command":sql
            // });
            var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/db_thuebao_ct_dbdt",{"id":this.thuebao_id});
            // var dt_sql = tdan.GET_DATA_SQL(sql);
            this.grcDSDanhBa.list = rs.data; //Cho phep edit all tru field Chon
        },
        btnDangKy_Click() {
            for (var item of this.ds_hschuadk.filter(x => x.chon == true))
            {
                item.WEBSITE_CT = "Nội mạng";
                item.IP_CT = "0";
                item.DOMAIN_CT = "1";// nội mạng
                if (this.ds_hsdadk.filter(x => x.MA_PHU_CT == item.MA_PHU_CT).length>0)
                {
                    item.chon = false;
                    this.ds_hsdadk.push(item);
                }
            }
            this.ds_hschuadk = ds_hschuadk.filter(x => x.chon == false);
            this.grcDSHocSinhDangKy.list = this.ds_hsdadk;
        },
        async LoadDSHSTheoLop(id_lop)
        {
            var rs = await this.$root.context.get(`/tichhop/edu/l1?call=app.doisoat.serv.api.getListHocSinhLop&truong_id=${this.id_truong}&lop_hoc_id=${id_lop}`,{});//_eduServices.LayDanhSachHSTrongLop(Convert.ToInt64(id_truong), id_lop);
            this.ds_hschuadk = (rs && rs.data && rs.data.aRoot ? rs.data.aRoot : []);
            this.grcDanhSachHocSinh.list = this.ds_hschuadk;//new BindingList<HD_THUEBAO_CT>(ds_hschuadk);
        },
        async LoadDSHSTheoKhoi(KhoiID)
        {
            var rs = await this.$root.context.get(`/tichhop/edu/l1?call=app.doisoat.serv.api.getListHocSinhByKhoi&truong_id=${this.id_truong}&khoi_hoc_id=${KhoiID}`,{});//_eduServices.LayDanhSachHSTheoKhoi(Convert.ToInt64(id_truong), KhoiID);
            this.ds_hschuadk = (rs && rs.data && rs.data.aRoot ? rs.data.aRoot : []);
            this.grcDanhSachHocSinh.list = this.ds_hschuadk;//new BindingList<HD_THUEBAO_CT>(ds_hschuadk);
        },
        async KiemTraNoiMang()
        {
            if (this.ds_hsdadk.length > 0)
            {
                //FIXME { "data": [ "string" ] } //Input hơi dị, để tạm sau test check sau!
                var rs = await this.$root.context.post(`/tichhop/edu/checkmang`,{"data":this.ds_hsdadk.map(x=>JSON.stringify(x))});//_eduServices.KiemTraNhaMang(ds_hsdadk);
                var dc = rs;
                for (var item of this.ds_hsdadk)
                {
                    item.DOMAIN_CT = dc[item.DT_GIAMHO];
                    if (dc[item.DT_GIAMHO] == 0)
                    {
                        item.WEBSITE_CT = "Ngoại mạng";
                        item.IP_CT = this.tocdo_adsl[0]["CP_ID"];
                    }
                    else if (dc[item.DT_GIAMHO] == 1)
                    {
                        item.WEBSITE_CT = "Nội mạng";
                        item.IP_CT = this.tocdo_adsl[0]["CA_ID"];
                    }
                    else
                    {
                        item.WEBSITE_CT = "Không hợp lệ";
                    }
                }
            }
            // Khong can xu ly cai nay!!!
            // gdvDSHocSinhDK.CloseEditor();
            // grcDSHocSinhDangKy.Refresh();
        },
        async btnGhiLai_ItemClick() {
            // var rs = await API.post_sp_huonggiao_runsql(this.axios, { "command": `select ${DatabaseConstants.DB2}.TINH_TONGTIEN_SLL_CHUYENMANG(${this.thuebao_id}) kq from dual` }).then(async (response) => {
            //     if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            //     return response.data.data.data_json;
            //     }
            //     else return [];
            // });
            var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/tinh_tongtien_sll_chuyenmang",{"vthuebao_id":this.thuebao_id});
            var tongtien_cu = 0;
            if (rs && rs.data)
                tongtien_cu = rs.data*1;// Convert.ToDecimal(tdan.GET_VALUE_FUNC("{?DB2}.TINH_TONGTIEN_SLL_CHUYENMANG", "vthuebao_id", thuebao_id).ToString());
            else tongtien_cu = 0;
            for (var item of this.ds_hsdadk)
            {
                if (item.DOMAIN_CT == "-1")
                {
                    this.$toast.error("Tồn tại số điện thoại không hợp lệ (bôi đỏ)");
                    return;
                }
            }
            await this.KiemTraNoiMang();
            for (var item of this.ds_hsdadk)
            {
                if (!(item.DT_GIAMHO))
                {
                    this.$toast.error("Có học sinh chưa cung cấp số điện thoại");
                    return;
                }
            }
            // this.ds_hsdadk = []; // bo phan lien quan cai nay vi thuc hien tren bien global luon
            if (this.trangthai_biendong != 2)
            {
                for (var item of this.ds_hsdadk)
                {
                    item.chon = false;
                    // this.ds_hsdadk.push(item);
                }
            }
            else // trang thai huy so lien lac
            {
                for (var item of ds_hsdadk)
                {
                    item.chon = false;
                    // this.ds_hsdadk.push(item);
                }
            }

            this.$toast.success("Đăng ký thành công cho: " + this.ds_hsdadk.length + " học sinh");

            for (var item of this.ds_hsdadk)
            {
                this.tong_tien = this.tong_tien + item.IP_CT;
                if (item.DOMAIN_CT == "1")
                {
                    this.tongsoSMS_NOIM = this.tongsoSMS_NOIM + this.tocdo_adsl[0]["SOLUONG_IP"];
                }
                if (item.DOMAIN_CT == "0")
                {
                    this.tongsoSMS_NGOM = this.tongsoSMS_NGOM + this.tocdo_adsl[0]["SL_IP_MP"];
                }
            }
            if (this.trangthai_biendong == 1) // chuyen mang
            {
                this.tong_tien = this.tong_tien - tongtien_cu;
                if (this.tong_tien <= 0)
                {
                    this.tong_tien = 0;
                }
            }
            if (this.trangthai_biendong == 2) // bot hoc sinh ra kho so lien lac
            {
                this.tong_tien = 0;
            }

            //tbTongTien.EditValue = tong_tien;
            this.tongso_hs = this.ds_hsdadk.length;
            this.closeForm();
        },
        cboLopHoc_ButtonClick() {
            //if (e.Button.Kind == ButtonPredefines.Delete) // chi co 1 chuc nang nay -> khong can xu ly!
            this.cboLopHoc.value = 0;
        },
        simpleButton1_Click() {
            if (this.cboLopHoc.value == 0)
            {
                this.LoadDSHSTheoKhoi(this.cboKhoi.value);
            }
            else
            {
                this.LoadDSHSTheoLop(this.cboLopHoc.value);
            }
        },
        btnXoa_ItemClick() {
            this.ds_hsdadk = this.ds_hsdadk.filter(x => x.chon != true);
            this.grcDSHocSinhDangKy.list = this.ds_hsdadk;//new BindingList<HD_THUEBAO_CT>(ds_hsdadk);
        },
        btnHuyDK_Click() {
            this.ds_hsdadk = [];
            this.grcDSHocSinhDangKy.list = this.ds_hsdadk;
        },
        ckbSelectAll_CheckedChanged_1() {
            for (var item of ds_hschuadk)
            {
                item.chon = this.ckbSelectAll;
            }

            this.grcDanhSachHocSinh.list = this.ds_hschuadk;
        },
        btnThemDsHuy_Click()
        {
            for (var item of this.grcDSDanhBa.list)//this.ds_danhba)
            {
                if (item.chon)
                {
                    this.ds_hsdadk.push(item);
                }
            }
            this.grcDSHocSinhDangKy.list = this.ds_hsdadk;
        },
        btnBoDSHuy_Click()
        {
            this.ds_hsdadk = this.ds_hsdadk.filter(x => x.chon == false);
            this.grcDSHocSinhDangKy.list = this.ds_hsdadk;
        }
    }
}
</script>