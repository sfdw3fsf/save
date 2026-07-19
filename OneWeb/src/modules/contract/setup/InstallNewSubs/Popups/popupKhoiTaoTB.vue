<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Khởi tạo thuê bao</div>
        <div class="close -ap icon-close" data-dismiss="modal" @click.prevent="closeForm">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="Clear">
                    <span class="icon one-reload1"></span> Làm mới
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="btnCapNhat_Click">
                    <span class="icon one-luu-so"></span> Khởi tạo</a>
            </li>
        </ul>
    </div>
    <div class="popup-body">
        <div class="row">
            <div class="legend-title mart20">Thông tin thuê bao</div>
            <div class="col-sm-12 col-12">
                <div class="box-form">
                    <div class="info-row">
                        <div class="key w90">
                            <!-- <div class="check-action"> -->
                                <span class="name">Số máy (*)</span>
                            <!-- </div> -->
                        </div>
                        <div class="value">
                            <div class="input-more-button">
                                <button class="btn">
                                    84
                                </button>
                                <input type="text" class="form-control " required :value="txtSoMay" @change="v=>txtSoMay=v.target.value">
                            </div>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w90">Số MSIN (*)</div>
                        <div class="value">
                            <input type="text" class="form-control highlight" required :value="txtSoMsin" @change="v=>txtSoMsin=v.target.value" pattern="^\d*$" title="chỉ được nhập số!">
                        </div>
                    </div>
                    <div class="info-row" v-if="kho_id == 14">
                        <div class="key w90">Mã xác thực</div>
                        <div class="value">
                            <input type="text" class="form-control highlight" required :value="txtMaXT" @change="v=>txtMaXT=v.target.value">
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w90">Loại MSIN</div>
                        <div class="value">
                            <input type="radio" id="one" value="64" v-model="rdgLoaiMsin" /> Sim 64k
                            <input type="radio" id="one" value="128" v-model="rdgLoaiMsin" /> Sim 128k
                        </div>
                    </div>

                    <div class="info-row">
                        <div class="key w90">
                            <!-- <div class="check-action"> -->
                                <span class="name">Loại TB</span>
                            <!-- </div> -->
                        </div>
                        <div class="value">

                            <SelectExt dataTextField="TEXT" dataValueField="ID" :dataSource="cboLoaiTB.list" v-model="cboLoaiTB.value">
                                <!-- <option disabled value="0">Select one</option>  -->
                            </SelectExt>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w90">
                                <span class="name">Ghi chú</span>
                        </div>
                        <div class="value">
                            <textarea class="form-control" rows="3" width="100" v-model="txtGhiChu"></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</template>
<style scoped>
    input.disabled, select.disabled, .disabled input, .disabled select, .disabled /deep/ .select2-selection { background-color: #EEE; color:lightgray;}
</style>
<script>
import {
  LoaiHinhTB,
  DatabaseConstants,
  DS_KHOITAO_CHONSO_TS,
} from "../Enums";
import moment from 'moment'
export default {
    name: 'popupKhoiTaoTB',
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            kieu: 0,
            chonso: false,
            sotb_goc: null,
            khoid: null,
            ttnd: {},
            KHO_TT_TINH_ID: 4,
            loaitb: "",
            pmeg: "0",
            kho_id:"",
            txtMaXT: "",
            txtSoMay: "",
            txtSoMsin: "",
            txtGhiChu: "",
            cboLoaiTB: {
                list: [],
                value: 0,
            },
            rdgLoaiMsin: "64",
        }
    },
    async mounted() {
        if(this.dulieu.kieu) this.kieu = this.dulieu.kieu;
        if(this.dulieu.chonso) this.chonso = this.dulieu.chonso;
        if(this.dulieu.sotb_goc) this.sotb_goc = this.dulieu.sotb_goc;
        if(this.dulieu.khoid) this.khoid = this.dulieu.khoid;
        console.info('khoitao',this.dulieu);
        // this.ttnd = await this.$root.token.getThongTinNguoiDung();
        // if(!this.ttnd.ma_nv_ccbs) this.$toast.error('Không lấy được thông tin ["ma_nv_ccbs"] của người dùng hiện tại, chức năng này có thể không hoạt động được bình thường. Vui lòng liên hệ Admin!');
        await this.NAP_THONGTIN();

    },
    computed: {

        // matinh: function() {
        //     return this.$root.token.getMaTinh();
        // }
    },
    methods: {
        async NAP_THONGTIN()
        {
            console.info('NAP_THONGTIN',this.sotb_goc);
            try
            {
                if (!this.sotb_goc)
                    return;
                var tb = this.sotb_goc+'';

                if (!this.khoid)
                    return;
                this.kho_id = this.khoid*1;
                // if (this.kho_id == "14")
                // {
                //     labelControl3.Visible = true;
                //     txtMaXT.Visible = true;
                //     txtMaXT.Enabled = true;
                // }

                this.txtSoMay = tb.substring(2);

                if (this.kieu == 0 && this.chonso == true) // trả trước chon so 0210
                {
                    // #region COMBO loại hình
                    // DataTable tmp01 = ccbs.ts_khoitao_tratruoc_ds_loaitb(DatabaseConstants.DB2.Substring(4).ToUpper());
                    var sql01 = await this.$root.context.post("/ccbs/chonSo/app_ds_loaitb_kt",{"tratruoc_tq":1});
                    var tmp01 = sql01.data;
                    // string sql02 = "select tentat_neo value,tengoicuoc ID from " + DatabaseConstants.DB2 + ".goicuoc_dd where loaitb_id=21 and tentat_neo is not null ";
                    // DataTable tmp02 = tdan.GET_DATA_SQL(sql02);
                    var sql2 =  await this.$root.context.post("/web-quantri/lay-dulieu/sp_tt_goicuoc_dd",{type:2,param:21});
                    var tmp2 = sql2.data;

                    // var linq_resutl = from row2 in tmp02.AsEnumerable()
                    //                   join row1 in tmp01.AsEnumerable()
                    //                        on row2.Field<string>("VALUE") equals row1.Field<string>("LOAITB_ID")
                    //                   select row2;
                    // DataTable dt_goicuoc = null;
                    // if (linq_resutl.Any())
                    //     dt_goicuoc = linq_resutl.CopyToDataTable();
                    var linq_resutl = [];
                    if(tmp2 && tmp2.length > 0)
                    for(var item of tmp2)
                        if(tmp01.find(x=>x.LOAITB_ID == item.tentat_neo))
                            linq_resutl.push({ID:item.tentat_neo,TEXT:item.tengoicuoc});
                    this.cboLoaiTB.list = linq_resutl;
                    this.cboLoaiTB.value = linq_resutl && linq_resutl.length>0 && linq_resutl[0].ID?linq_resutl[0].ID:0;
                    // bts.HT_DS_LOOKUPEDIT(cboLoaiTB, dt_goicuoc, "ID", "VALUE");

                    // #endregion

                }
                if (this.kieu == 0 && this.chonso == false) // vuth update fix 0210
                {
                    // DataTable tmp01 = ccbs.ts_khoitao_ds_loaitb(DatabaseConstants.DB2.Substring(4).ToUpper(), KHO_TT_TINH_ID);
                    var sql01 = await this.$root.context.post("/ccbs/chonSo/app_ds_loaitb_kt",{"tratruoc_tq":0,kho:this.KHO_TT_TINH_ID+''});
                    var tmp01 = sql01.data;
                    // string sql02 = "select tentat_neo value,tengoicuoc ID from " + DatabaseConstants.DB2 + ".goicuoc_dd where loaitb_id=21 and tentat_neo is not null ";
                    // DataTable tmp02 = tdan.GET_DATA_SQL(sql02);
                    var sql2 =  await this.$root.context.post("/web-quantri/lay-dulieu/sp_tt_goicuoc_dd",{type:2,param:21});
                    var tmp2 = sql2.data;
                    // var linq_resutl = from row2 in tmp02.AsEnumerable()
                    //                   join row1 in tmp01.AsEnumerable()
                    //                        on row2.Field<string>("VALUE") equals row1.Field<string>("LOAITB_ID")
                    //                   select row2;
                    // DataTable dt_goicuoc = null;
                    var linq_resutl = [];
                    if(tmp2 && tmp2.length > 0)
                    for(var item of tmp2)
                        if(tmp01.find(x=>x.LOAITB_ID == item.tentat_neo))
                            linq_resutl.push({ID:item.tentat_neo,TEXT:item.tengoicuoc});
                    if (linq_resutl && linq_resutl.length>0)
                    {
                        this.cboLoaiTB.list = linq_resutl;
                        this.cboLoaiTB.value = linq_resutl && linq_resutl.length>0?linq_resutl[0].ID:0;
                        // dt_goicuoc = linq_resutl.CopyToDataTable();
                        // bts.HT_DS_LOOKUPEDIT(cboLoaiTB, dt_goicuoc, "ID", "VALUE");
                    }
                    else
                    {
                        this.$toast.error("Không lấy được giá trị loại thuê bao! Hãy thử lại!");
                        return;
                    }

                }
                if (this.kieu == 1)//trả sau
                {
                    // #region COMBO loại hình
                    // DataTable tmp01 = ccbs.ts_khoitao_ds_loaitb(DatabaseConstants.DB2.Substring(4).ToUpper(), khoid);
                    var sql01 = await this.$root.context.post("/ccbs/chonSo/app_ds_loaitb_kt",{"tratruoc_tq":0,kho:this.khoid+''});
                    var tmp01 = sql01.data;
                    // string sql02 = "select tentat_neo value,tengoicuoc ID from " + DatabaseConstants.DB2 + ".goicuoc_dd where loaitb_id=20 and tentat_neo is not null ";
                    // DataTable tmp02 = tdan.GET_DATA_SQL(sql02);
                    var sql2 =  await this.$root.context.post("/web-quantri/lay-dulieu/sp_tt_goicuoc_dd",{type:2,param:20});
                    var tmp2 = sql2.data;
                    // var linq_resutl = from row2 in tmp02.AsEnumerable()
                    //                   join row1 in tmp01.AsEnumerable()
                    //                        on row2.Field<string>("VALUE") equals row1.Field<string>("LOAITB_ID")
                    //                   select row2;
                    // DataTable dt_goicuoc = null;
                    var linq_resutl = [];
                    if(tmp2 && tmp2.length > 0)
                    for(var item of tmp2)
                        if(tmp01.find(x=>x.LOAITB_ID == item.tentat_neo))
                            linq_resutl.push({ID:item.tentat_neo,TEXT:item.tengoicuoc});
                    if (linq_resutl && linq_resutl.length>0)
                    {
                        this.cboLoaiTB.list = linq_resutl;
                        this.cboLoaiTB.value = linq_resutl && linq_resutl.length>0?linq_resutl[0].ID:0;
                        // dt_goicuoc = linq_resutl.CopyToDataTable();
                        // bts.HT_DS_LOOKUPEDIT(cboLoaiTB, dt_goicuoc, "ID", "VALUE");
                    }
                    else
                    {
                        this.$toast.error("Không lấy được giá trị loại thuê bao! Hãy thử lại!");
                        return;
                    }

                    // #endregion
                }
            }
            catch (ex)
            {
                this.$toast.error("Có lỗi khi lây thông tin " + ex);
            }
        },
        Clear()
        {
            var tb = this.sotb_goc+'';
            this.txtSoMay = tb.substring(2);
            this.txtSoMsin = "";
            this.txtGhiChu = "";
        },
        async KHOITAO_TT()
        {
            try
            {
                if (!this.cboLoaiTB.value)
                {
                    this.$toast.error("Chưa có dữ liệu loại thuê bao");
                    return;
                }
                // #region vuth update fix 1609 : bỏ zem để check db số máy đã khởi tạo chưa cho nhanh.
                // var d = tdan.GET_DATA_SQL("select count(*) from {?DB2}.goicuoc_dd where tentat_neo=:vtt", "vtt",
                //                             cboLoaiTB.EditValue.ToString());
                var d = await this.$root.context.post("/web-quantri/lay-dulieu/sp_tt_goicuoc_dd",{ type: 3, param: this.cboLoaiTB.value });

                if (!d.data || d.data == 0 || d.data.length==0)
                {
                    this.$toast.error(`Loại thuê bao ${this.cboLoaiTB.list.find(x=>x.ID==this.cboLoaiTB.value)?this.cboLoaiTB.list.find(x=>x.ID==this.cboLoaiTB.value).TEXT:""} (${this.cboLoaiTB.value}) chưa được khai báo dưới tỉnh`);
                    return;
                }
                if (!this.txtSoMsin)
                {
                    this.$toast.error("Bạn chưa nhập số SIM");
                    return;
                }

                var ds = await this.$root.context.post("/web-quantri/khoitao_tb/lay_tt_khoso_dd",{ vsomay:this.txtSoMay, vsosim:this.txtSoMsin });//tdan.GET_DATA_SQL("select count(*) from {?DB2}.khoso_dd where nguoidung_id=:vnguoidung_id and somay=:vsomay and simcard=:vsosim",
                        //"vnguoidung_id", ttnd.nguoidung_id, "vsomay", txtSoMay.Text, "vsosim", txtSoMsin.Text);
                if (!(!ds.data || ds.data == 0 || ds.data.length==0))
                {
                    this.$toast.error("Số máy này đã được khởi tạo. Bạn hãy kiểm tra lại");
                    return;
                }
                // #endregion

                var cfr = await this.$bvModal.msgBoxConfirm(`Bạn có muốn khởi tạo số 84${this.txtSoMay} với loại thuê bao ${this.cboLoaiTB.list.find(x=>x.ID==this.cboLoaiTB.value)?this.cboLoaiTB.list.find(x=>x.ID==this.cboLoaiTB.value).TEXT:""} không?`, {
                    title: 'Xác nhận hành động',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    return v;
                });
                if (!cfr)
                    return;
                // if (DialogResult.No == Message_Box.ShowQuestion("Bạn có muốn khởi tạo số 84" + txtSoMay.Text + " với loại thuê bao " + cboLoaiTB.Text + " không?"))
                //     return;
                //Lamnt: Bổ sung thêm trwongf ma_tinh - 15/03/2018
                this.loaitb = this.cboLoaiTB.value;
                switch (this.loaitb)
                {
                    case "MEG":
                        this.pmeg = "1";
                        break;
                    case "STK":
                        this.pmeg = "2";
                        break;
                    case "VCC":
                        this.pmeg = "3";
                        break;
                    case "KARO":
                        this.pmeg = "4";
                        break;
                    default:
                        this.pmeg = "0";
                        break;
                }

                // var kq = ccbs.tt_1_khoitao_chonso_trasau("", ttnd.USER_NEO, DatabaseConstants.DB2.Substring(4).ToUpper(),
                //                         txtSoMsin.Text.Trim(), loaitb, KHO_TT_TINH_ID, "84" + txtSoMay.Text.Trim(), pmeg, txtGhiChu.Text.Trim());
                var res = await this.$root.context.post("web-hopdong/hopdong/fn_didong",
                {
                  vinput: {
                    "ghichu": this.txtGhiChu,
                    "imsi": this.txtSoMsin,
                    "kho": this.KHO_TT_TINH_ID,
                    "ma_xac_thuc": this.txtMaXT,
                    "msisdn": "84" + (this.txtSoMay?this.txtSoMay.trim():""),
                    "ploai": this.loaitb,
                    "pmeg": this.pmeg,
                    "tratruoc_tq": "0",
                    "channel": "WEB"
                  },
                  vtype: 8,
                  vhdtb_id: this.dulieu.hdtb_id
                });
                console.info('KHOITAO_TT',res.data);
                var kq = res.message?res.message:"";
                // if (kq == null || kq.Rows[0][0] == null)
                if (!kq)
                {
                    this.$toast.error("Không có dữ liệu trả về: "+res.message);
                    return;
                }
                // if (kq.Rows[0][0].ToString() == "3")

                if (kq == "Success")
                {
                    // vuth bỏ zem để update số khởi tạo thành công vào khoso_dd  --update 1709 : insert them user_neo
                    // var z = tdan.EXECUTE_SQL(@"
                    //          Insert into {?DB2}.khoso_dd (somay, simcard, donvi_id, trangthaiso_id, ghichu,
                    //                          ngay_cn, nguoidung_id, nguoi_cn, may_cn, ip_cn,user_neo, goicuoc_id)
                    //         select :vsomay,:vsimcard,:vdonvi_id,1,:vghichu,sysdate,:vnguoidung_id,:vnguoi_cn,:vmay_cn,:vip_cn,:vuser_neo,
                    //          (select goicuoc_id from {?DB2}.goicuoc_dd where tentat_neo=:vtentat_neo and loaitb_id=21) from dual",
                    //    "vsomay", txtSoMay.Text, "vsimcard", txtSoMsin.Text,
                    //    "vdonvi_id", ttnd.donvi_id, "vghichu", txtGhiChu.Text, "vnguoidung_id", ttnd.nguoidung_id, "vnguoi_cn", ttnd.ma_nd,
                    //    "vmay_cn", ttnd.may_cn, "vip_cn", ttnd.ip, "vuser_neo", ttnd.USER_NEO, "vtentat_neo", cboLoaiTB.EditValue.ToString());
                    var res = await this.$root.context.post("/web-quantri/khoitao_tb/sp_them_khoso_cd",{ "vsomay":this.txtSoMay, "vsimcard":this.txtSoMsin, "vghichu":this.txtGhiChu, "vtentat_neo":this.cboLoaiTB.value, "loaitb_id": 21 });

                    if (res && res.data == "ok")
                    {
                        this.$toast.success("Khởi tạo thành công");
                        // xacnhan = true;
                        // Close();
                        this.closeForm();
                    }
                    else
                    {
                        this.$toast.error("Đã khởi tạo trên CCBS thành công. Lưu dữ liệu về tỉnh có lỗi");
                    }
                }
                else
                {
                    var error = kq+'';
                    if (DS_KHOITAO_CHONSO_TS[error])
                        this.$toast.error(DS_KHOITAO_CHONSO_TS[error]);
                    else
                        this.$toast.error(error);//(HttpUtility.HtmlDecode(error));
                }
            }
            catch (ex)
            {
                this.$toast.error("Có lỗi khi khởi tạo " + ex);
            }
        },

        closeForm() {
            this.$emit('form-close',null);
        },


        async KHOITAO_CHONSO_TT() // vuth update them 0210
        {
            try
            {

                if (!this.cboLoaiTB.value)
                {
                    this.$toast.error("Chưa có dữ liệu loại thuê bao");
                    return;
                }
                // #region vuth update fix 1609 : bỏ zem để check db số máy đã khởi tạo chưa cho nhanh.
                // var d = tdan.GET_DATA_SQL("select count(*) from {?DB2}.goicuoc_dd where tentat_neo=:vtt", "vtt",
                //                             cboLoaiTB.EditValue.ToString());
                var d = await this.$root.context.post("/web-quantri/lay-dulieu/sp_tt_goicuoc_dd",{ type: 3, param: this.cboLoaiTB.value });

                if (!d.data || d.data == 0 || d.data.length==0)
                {
                    this.$toast.error(`Loại thuê bao ${this.cboLoaiTB.list.find(x=>x.ID==this.cboLoaiTB.value)?this.cboLoaiTB.list.find(x=>x.ID==this.cboLoaiTB.value).TEXT:""} (${this.cboLoaiTB.value}) chưa được khai báo dưới tỉnh`);
                    return;
                }
                if (!this.txtSoMsin)
                {
                    this.$toast.error("Bạn chưa nhập số SIM");
                    return;
                }

                var ds = await this.$root.context.post("/web-quantri/khoitao_tb/lay_tt_khoso_dd",{ vsomay:this.txtSoMay, vsosim:this.txtSoMsin });//tdan.GET_DATA_SQL("select count(*) from {?DB2}.khoso_dd where nguoidung_id=:vnguoidung_id and somay=:vsomay and simcard=:vsosim",
                        //"vnguoidung_id", ttnd.nguoidung_id, "vsomay", txtSoMay.Text, "vsosim", txtSoMsin.Text);
                if (!(!ds.data || ds.data == 0 || ds.data.length==0))
                {
                    this.$toast.error("Số máy này đã được khởi tạo. Bạn hãy kiểm tra lại");
                    return;
                }
                // #endregion

                var cfr = await this.$bvModal.msgBoxConfirm(`Bạn có muốn khởi tạo số 84${this.txtSoMay} với loại thuê bao ${this.cboLoaiTB.list.find(x=>x.ID==this.cboLoaiTB.value)?this.cboLoaiTB.list.find(x=>x.ID==this.cboLoaiTB.value).TEXT:""} không?`, {
                    title: 'Xác nhận hành động',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    return v;
                });
                var loaiSim = this.rdgLoaiMsin;//rdgLoaiMsin.SelectedIndex == 0 ? "64" : "128"; // vuth update fix 3009
                // var kq = ccbs.tt_1_khoitao_chonso_tratruoc("", ttnd.USER_NEO, DatabaseConstants.DB2.Substring(4).ToUpper(),
                //                                         txtSoMsin.Text.Trim(), cboLoaiTB.EditValue.ToString(), "84" + txtSoMay.Text.Trim(), loaiSim, txtGhiChu.Text.Trim(), ttnd.ip);
                var res = await this.$root.context.post("web-hopdong/hopdong/fn_didong",
                {
                  vinput: {
                    "ghichu": this.txtGhiChu,
                    "imsi": this.txtSoMsin,
                    "kho": this.KHO_TT_TINH_ID,
                    "ma_xac_thuc": this.txtMaXT,
                    "msisdn": "84" + (this.txtSoMay?this.txtSoMay.trim():""),
                    "ploai": this.loaitb,
                    "pmeg": this.pmeg,
                    "tratruoc_tq": "1",
                    "channel": "WEB"
                  },
                  vtype: 8,
                  vhdtb_id: this.dulieu.hdtb_id
                }
                );
                console.info('KHOITAO_CHONSO_TT',res.data);
                var kq = res.message?res.message:"";
                if (!kq || kq!="Success")// == null || kq.Rows[0][0] == null)
                {
                    this.$toast.error("Không có dữ liệu trả về: "+res.message);
                    return;
                }
                if (kq == "Success")
                {
                    // vuth bỏ zem để update số khởi tạo thành công vào khoso_dd  --update 1709 : insert them user_neo
                    var res = await this.$root.context.post("/web-quantri/khoitao_tb/sp_them_khoso_cd",{ "vsomay":this.txtSoMay, "vsimcard":this.txtSoMsin, "vghichu":this.txtGhiChu, "vtentat_neo":this.cboLoaiTB.value, "loaitb_id": 21 });

                    if (res && res.data == "ok")
                    {
                        this.$toast.success("Khởi tạo thành công");
                        // xacnhan = true;
                        // Close();
                        this.closeForm();
                    }
                    else
                    {
                        this.$toast.error("Đã khởi tạo trên CCBS thành công. Lưu dữ liệu về tỉnh có lỗi");
                    }

                    // var z = tdan.EXECUTE_SQL(@"
                    //          Insert into {?DB2}.khoso_dd (somay, simcard, donvi_id, trangthaiso_id, ghichu,
                    //                          ngay_cn, nguoidung_id, nguoi_cn, may_cn, ip_cn,user_neo, goicuoc_id)
                    //         select :vsomay,:vsimcard,:vdonvi_id,1,:vghichu,sysdate,:vnguoidung_id,:vnguoi_cn,:vmay_cn,:vip_cn,:vuser_neo,
                    //          (select goicuoc_id from {?DB2}.goicuoc_dd where tentat_neo=:vtentat_neo and loaitb_id=21) from dual",
                    //    "vsomay", txtSoMay.Text, "vsimcard", txtSoMsin.Text,
                    //    "vdonvi_id", ttnd.donvi_id, "vghichu", txtGhiChu.Text, "vnguoidung_id", ttnd.nguoidung_id, "vnguoi_cn", ttnd.ma_nd,
                    //    "vmay_cn", ttnd.may_cn, "vip_cn", ttnd.ip, "vuser_neo", ttnd.USER_NEO, "vtentat_neo", cboLoaiTB.EditValue.ToString());
                    // if (z == 1)
                    // {
                    //     Message_Box.ShowTB("Khởi tạo thành công");
                    //     xacnhan = true;
                    //     Close();
                    // }
                    // else
                    // {
                    //     Message_Box.ShowWarning("Đã khởi tạo trên CCBS thành công. Lưu dữ liệu về tỉnh có lỗi");
                    // }
                }
                else
                {
                    var error = kq+'';
                    if (DS_KHOITAO_CHONSO_TS[error])
                        this.$toast.error(DS_KHOITAO_CHONSO_TS[error]);
                    else
                        this.$toast.error(error);//(HttpUtility.HtmlDecode(error));
                }
                // else
                // {
                //     string error = kq.Rows[0][0].ToString();
                //     if (CCBS_RESULT.DS_KHOITAO_CHONSO_TT.ContainsKey(error))
                //         Message_Box.ShowTB(CCBS_RESULT.DS_KHOITAO_CHONSO_TT[error].ToString());
                //     else
                //         Message_Box.ShowTB(HttpUtility.HtmlDecode(error));

                // }
            }
            catch (ex)
            {
                this.$toast.error("Có lỗi khi khởi tạo " + ex);
            }
        },
        async KHOITAO_TS()
        {
            try
            {
                if (!this.cboLoaiTB.value)
                {
                    this.$toast.error("Chưa có dữ liệu loại thuê bao");
                    return;
                }
                // #region vuth update fix 1609 : bỏ zem để check db số máy đã khởi tạo chưa cho nhanh.
                // var d = tdan.GET_DATA_SQL("select count(*) from {?DB2}.goicuoc_dd where tentat_neo=:vtt", "vtt",
                //                             cboLoaiTB.EditValue.ToString());
                var d = await this.$root.context.post("/web-quantri/lay-dulieu/sp_tt_goicuoc_dd",{ type: 3, param: this.cboLoaiTB.value });

                if (!d.data || d.data == 0 || d.data.length==0)
                {
                    this.$toast.error(`Loại thuê bao ${this.cboLoaiTB.list.find(x=>x.ID==this.cboLoaiTB.value)?this.cboLoaiTB.list.find(x=>x.ID==this.cboLoaiTB.value).TEXT:""} (${this.cboLoaiTB.value}) chưa được khai báo dưới tỉnh`);
                    return;
                }
                if (!this.txtSoMsin)
                {
                    this.$toast.error("Bạn chưa nhập số SIM");
                    return;
                }

                var ds = await this.$root.context.post("/web-quantri/khoitao_tb/lay_tt_khoso_dd",{ vsomay:this.txtSoMay, vsosim:this.txtSoMsin });//tdan.GET_DATA_SQL("select count(*) from {?DB2}.khoso_dd where nguoidung_id=:vnguoidung_id and somay=:vsomay and simcard=:vsosim",
                        //"vnguoidung_id", ttnd.nguoidung_id, "vsomay", txtSoMay.Text, "vsosim", txtSoMsin.Text);
                if (!(!ds.data || ds.data == 0 || ds.data.length==0))
                {
                    this.$toast.error("Số máy này đã được khởi tạo. Bạn hãy kiểm tra lại");
                    return;
                }
                // #endregion

                var cfr = await this.$bvModal.msgBoxConfirm(`Bạn có muốn khởi tạo số 84${this.txtSoMay} với loại thuê bao ${this.cboLoaiTB.list.find(x=>x.ID==this.cboLoaiTB.value)?this.cboLoaiTB.list.find(x=>x.ID==this.cboLoaiTB.value).TEXT:""} không?`, {
                    title: 'Xác nhận hành động',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    return v;
                });
                if (!cfr)
                    return;
                // if (DialogResult.No == Message_Box.ShowQuestion("Bạn có muốn khởi tạo số 84" + txtSoMay.Text + " với loại thuê bao " + cboLoaiTB.Text + " không?"))
                //     return;
                //Lamnt: Bổ sung thêm trwongf ma_tinh - 15/03/2018
                this.loaitb = this.cboLoaiTB.value;
                switch (this.loaitb)
                {
                    case "MEG":
                        this.pmeg = "1";
                        break;
                    case "STK":
                        this.pmeg = "2";
                        break;
                    case "VCC":
                        this.pmeg = "3";
                        break;
                    case "KARO":
                        this.pmeg = "4";
                        break;
                    default:
                        this.pmeg = "0";
                        break;
                }
                var pxacthuc = "00000000";
                if (this.kho_id == "1") pxacthuc = "";
                if (this.kho_id == "14") pxacthuc = this.txtMaXT?this.txtMaXT.trim():"";
                // var kq = ccbs.tt_1_khoitao_chonso_trasau("", ttnd.USER_NEO, DatabaseConstants.DB2.Substring(4).ToUpper(),
                //                         txtSoMsin.Text.Trim(), loaitb, kho_id, "84" + txtSoMay.Text.Trim(), pmeg, txtGhiChu.Text.Trim());
                var res = await this.$root.context.post("web-hopdong/hopdong/fn_didong",
                {
                  vinput: {
                    "ghichu": this.txtGhiChu,
                    "imsi": this.txtSoMsin,
                    "kho": this.kho_id,
                    "ma_xac_thuc": pxacthuc,
                    "msisdn": "84" + (this.txtSoMay?this.txtSoMay.trim():""),
                    "ploai": this.loaitb,
                    "pmeg": this.pmeg,
                    "tratruoc_tq": "0",
                    "channel": "WEB"
                  },
                  vtype: 8,
                  vhdtb_id: this.dulieu.hdtb_id
                }
                );
                console.info('KHOITAO_TS',res.data);
                var kq = res.message?res.message:"";
                // if (kq == null || kq.Rows[0][0] == null)
                if (!kq)
                {
                    this.$toast.error("Không có dữ liệu trả về: "+ res.message);
                    return;
                }
                // if (kq.Rows[0][0].ToString() == "3")

                // if (kq[0][0] == "3")
                if (kq == "Success")
                {
                    // vuth bỏ zem để update số khởi tạo thành công vào khoso_dd  --update 1709 : insert them user_neo
                    // var z = tdan.EXECUTE_SQL(@"
                    //          Insert into {?DB2}.khoso_dd (somay, simcard, donvi_id, trangthaiso_id, ghichu,
                    //                          ngay_cn, nguoidung_id, nguoi_cn, may_cn, ip_cn,user_neo, goicuoc_id)
                    //         select :vsomay,:vsimcard,:vdonvi_id,1,:vghichu,sysdate,:vnguoidung_id,:vnguoi_cn,:vmay_cn,:vip_cn,:vuser_neo,
                    //          (select goicuoc_id from {?DB2}.goicuoc_dd where tentat_neo=:vtentat_neo and loaitb_id=21) from dual",
                    //    "vsomay", txtSoMay.Text, "vsimcard", txtSoMsin.Text,
                    //    "vdonvi_id", ttnd.donvi_id, "vghichu", txtGhiChu.Text, "vnguoidung_id", ttnd.nguoidung_id, "vnguoi_cn", ttnd.ma_nd,
                    //    "vmay_cn", ttnd.may_cn, "vip_cn", ttnd.ip, "vuser_neo", ttnd.USER_NEO, "vtentat_neo", cboLoaiTB.EditValue.ToString());
                    var res = await this.$root.context.post("/web-quantri/khoitao_tb/sp_them_khoso_cd",{ "vsomay":this.txtSoMay, "vsimcard":this.txtSoMsin, "vghichu":this.txtGhiChu, "vtentat_neo":this.cboLoaiTB.value, "loaitb_id": 20 });

                    if (res && res.data == "ok")
                    {
                        this.$toast.success("Khởi tạo thành công");
                        // xacnhan = true;
                        // Close();
                        this.closeForm();
                    }
                    else
                    {
                        this.$toast.error("Đã khởi tạo trên CCBS thành công. Lưu dữ liệu về tỉnh có lỗi");
                    }
                }
                else
                {
                    var error = kq+'';
                    if (DS_KHOITAO_CHONSO_TS[error])
                        this.$toast.error(DS_KHOITAO_CHONSO_TS[error]);
                    else
                        this.$toast.error(error);//(HttpUtility.HtmlDecode(error));
                }
            }
            catch (ex)
            {
                this.$toast.error("Có lỗi khi khởi tạo " + ex);
            }
        },
        btnCapNhat_Click() {

            if (this.kieu == 0 && this.chonso == true) // vuth update 0210
                this.KHOITAO_CHONSO_TT();

            if (this.kieu == 0 && this.chonso == false) // vuth update 0210
                this.KHOITAO_TT();

            if (this.kieu == 1)
                this.KHOITAO_TS();
        },
    },

}
</script>
