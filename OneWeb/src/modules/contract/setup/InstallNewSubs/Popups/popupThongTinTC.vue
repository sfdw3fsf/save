<template>
<div class="modal-content popup-box">
    <div class="popup-header"> 
        <div class="title"><span class="icon one-notepad"></span> Thông tin tính cước thuê bao</div> 
        <div class="close -ap icon-close" data-dismiss="modal" @click="closeForm"> 
        </div> 
    </div> 
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="acceptForm">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp nhận
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">
        <div class="box-form">
            <div class="legend-title">Thông tin tính cước thuê bao</div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w140">Cước thuê bao</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <vue-numeric class="form-control tright" separator="," :value="txtcuoctb" @change="v=>txtcuoctb=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                        </div>
                        <div class="value w70 nowrap padt7">(VNĐ)</div>
                    </div>
                    <div class="info-row">
                        <div class="key w140">Cước trọn gói</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <vue-numeric class="form-control tright" separator="," :value="txtcuoctg" @change="v=>txtcuoctg=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                        </div>
                        <div class="value w70 nowrap padt7">(VNĐ)</div>
                    </div>
                    <div class="info-row">
                        <div class="key w140">Ngày giảm</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <div class="input-icon-right">
                                <date-picker input-class="form-control" :format="dateFormat" :value-type="'format'" v-model="txtngiam"></date-picker>
                                <span class="icon one-calendar"></span>
                            </div>
                        </div>
                        <div class="value w70 nowrap padt7"></div>
                    </div>
                    <div class="info-row">
                        <div class="key w140">Miễn phí IP tĩnh</div>
                        <div class="value" :class="{disabled:(thongso_tc!=0 && thongso_tc!=1) }">
                            <vue-numeric class="form-control tright" separator="," :value="txtmienip" @change="v=>txtmienip=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                        </div>
                        <div class="value w70 nowrap padt7">(Số lượng)</div>
                    </div>
                    <div class="info-row">
                        <div class="key w140">Ngày giảm IP</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <div class="input-icon-right">
                                <date-picker input-class="form-control" :format="dateFormat" :value-type="'format'" v-model="txtngiamip"></date-picker>
                                <span class="icon one-calendar"></span>
                            </div>
                        </div>
                        <div class="value w70 nowrap padt7"></div>
                    </div>
                    <div class="info-row">
                        <div class="key w140">Email thuê thêm</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <vue-numeric class="form-control tright" separator="," :value="txtslemail" @change="v=>txtslemail=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                        </div>
                        <div class="value w70 nowrap padt7">(Số lượng)</div>
                    </div>
                    <div class="info-row">
                        <div class="key w140">Tỷ lệ giảm Email</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <vue-numeric class="form-control tright" separator="," :value="txttlgiamemail" @change="v=>txttlgiamemail=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                        </div>
                        <div class="value w70 nowrap padt7">(%)</div>
                    </div>
                    <div class="info-row">
                        <div class="key w140">Thời gian giảm Email</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <vue-numeric class="form-control tright" separator="," :value="txtthgiamemail" @change="v=>txtthgiamemail=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                        </div>
                        <div class="value w70 nowrap padt7">(Tháng)</div>
                    </div>
                    <div class="info-row" v-if="!(this.loaitb_id == LoaiHinhTB.INTERNET_FTTH || this.loaitb_id == LoaiHinhTB.WIFI_FIBER)">
                        <div class="key w140">MB miễn phí</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <vue-numeric class="form-control tright" separator="," :value="txtmbmphi" @change="v=>txtmbmphi=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                        </div>
                        <div class="value w70 nowrap padt7">(Số lượng)</div>
                    </div>
                    <div class="info-row" v-if="!(this.loaitb_id == LoaiHinhTB.INTERNET_FTTH || this.loaitb_id == LoaiHinhTB.WIFI_FIBER)">
                        <div class="key w140">Tiền giảm</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <vue-numeric class="form-control tright" separator="," :value="txttiengiam" @change="v=>txttiengiam=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                        </div>
                        <div class="value w70 nowrap padt7">(VNĐ)</div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w140">Cước khoán</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <vue-numeric class="form-control tright" separator="," :value="txtcuockh" @change="v=>txtcuockh=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                        </div>
                        <div class="value w70 nowrap padt7">(VNĐ)</div>
                    </div>
                    <div class="info-row">
                        <div class="key w140">Tỷ lệ giảm cước</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <vue-numeric class="form-control tright" separator="," :value="txttlgiam" @change="v=>txttlgiam=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                        </div>
                        <div class="value w70 nowrap padt7">(%)</div>
                    </div>
                    <div class="info-row">
                        <div class="key w140">Thời gian giảm</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <vue-numeric class="form-control tright" separator="," :value="txtthgiam" @change="v=>txtthgiam=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                        </div>
                        <div class="value w70 nowrap padt7">(Tháng)</div>
                    </div>
                    <div class="info-row">
                        <div class="key w140">Tỷ lệ giảm IP</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <vue-numeric class="form-control tright" separator="," :value="txttlgiamip" @change="v=>txttlgiamip=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                        </div>
                        <div class="value w70 nowrap padt7">(%)</div>
                    </div>
                    <div class="info-row">
                        <div class="key w140">Thời gian giảm IP</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <vue-numeric class="form-control tright" separator="," :value="txtthgiamip" @change="v=>txtthgiamip=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                        </div>
                        <div class="value w70 nowrap padt7">(Tháng)</div>
                    </div>
                    <div class="info-row">
                        <div class="key w140">Đơn giá 1 Email</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <vue-numeric class="form-control tright" separator="," :value="txtdgemail" @change="v=>txtdgemail=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                        </div>
                        <div class="value w70 nowrap padt7">(VNĐ)</div>
                    </div>
                    <div class="info-row">
                        <div class="key w140">Ngày BĐ giảm Email</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <div class="input-icon-right">
                                <date-picker input-class="form-control" :format="dateFormat" :value-type="'format'" v-model="txtngiamemail"></date-picker>
                                <span class="icon one-calendar"></span>
                            </div>
                        </div>
                        <div class="value w70 nowrap padt7"></div>
                    </div>
                    <div class="info-row" v-if="!(this.loaitb_id == LoaiHinhTB.INTERNET_FTTH || this.loaitb_id == LoaiHinhTB.WIFI_FIBER)">
                        <div class="key w140">Cước 1 MB</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <vue-numeric class="form-control tright" separator="," :value="txtcuocmb" @change="v=>txtcuocmb=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                        </div>
                        <div class="value w70 nowrap padt7">(VNĐ)</div>
                    </div>
                    <div class="info-row" v-if="!(this.loaitb_id == LoaiHinhTB.INTERNET_FTTH || this.loaitb_id == LoaiHinhTB.WIFI_FIBER)">
                        <div class="key w140">Cước miễn phí</div>
                        <div class="value" :class="{disabled:thongso_tc!=1 }">
                            <vue-numeric class="form-control tright" separator="," :value="txtcuocmphi" @change="v=>txtcuocmphi=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                        </div>
                        <div class="value w70 nowrap padt7">(VNĐ)</div>
                    </div>
                    
                </div>
            </div>
        </div>
    </div> 
</div>
</template>
<style scoped>

</style>
<script>
import {
  LoaiHinhTB,
} from "../Enums";
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
export default {
    name: 'popupThongTinTC',
    components: {DatePicker,},
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            LoaiHinhTB: LoaiHinhTB,
            page: 0, //Page number
            txtcuoctb: 0,
            txtcuoctg: 0,
            txtngiam: null,//moment(new Date()).format("DD/MM/YYYY"),
            txtmienip: 0,
            txtngiamip: null,//moment(new Date()).format("DD/MM/YYYY"),
            txtslemail: 0,
            txttlgiamemail: 0,
            txtthgiamemail: 0,
            txtmbmphi: 0,
            txttiengiam: 0,
            txtcuockh: 0,
            txttlgiam: 0,
            txtthgiam: 0,
            txtthgiamip: 0,
            txtdgemail: 0,
            txtngiamemail: null,//moment(new Date()).format("DD/MM/YYYY"),
            txtcuocmb: 0,
            txtcuocmphi: 0,
            txttlgiamip: 0,
            dateFormat: "DD/MM/YYYY",
            datetimeFormat: "DD/MM/YYYY HH:mm",
            thongso_tc: 0,
            thongtintc: '',
            loaitb_id: 0,
            muccuoctb_id: 0,
        }
    },
    async mounted() {
        if(this.dulieu['cuoc_tb']) this.txtcuoctb = this.dulieu['cuoc_tb'];
        if(this.dulieu['cuoc_tg']) this.txtcuoctg = this.dulieu['cuoc_tg'];
        if(this.dulieu['cuockhoan']) this.txtcuockhoan = this.dulieu['cuockhoan'];
        if(this.dulieu['cuoc_ll']) this.txtcuoc_ll = this.dulieu['cuoc_ll'];
        if(this.dulieu['muccuoctb_id']) this.muccuoctb_id = this.dulieu['muccuoctb_id'];
        if(this.dulieu['mienip']) this.txtmienip = this.dulieu['mienip'];
        if(this.dulieu['loaitb_id']) this.loaitb_id = this.dulieu['loaitb_id'];
        if(this.dulieu['thongtintc']) this.thongtintc = this.dulieu['thongtintc'];

        this.$root.showLoading(true);
        var rs = await this.$root.context.post("/web-thicong/hoancongmegawan/lay_ds_thamso_md_mats",{ "ma_ts": "GDV_THONGSO_TC" });
        if(rs.data && rs.data[0] && rs.data[0].ten_ts) this.thongso_tc = rs.data[0].ten_ts;
        this.$root.showLoading(false);
        
        // Doan thongso_tc nay da xu ly tren giao dien
        //thongso_tc == 0 -> txtmienip.Enabled = true, còn lại enable = false
        // if (this.thongso_tc == 1)
        // {
        //     txtcuoctb.Enabled = true;
        //     txtcuoctg.Enabled = true;
        //     txtngiam.Enabled = true;
        //     txtmienip.Enabled = true;
        //     txtngiamip.Enabled = true;
        //     txtslemail.Enabled = true;
        //     txttlgiamemail.Enabled = true;
        //     txtthgiamemail.Enabled = true;
        //     txtmbmphi.Enabled = true;
        //     txttiengiam.Enabled = true;
        //     txtcuockh.Enabled = true;
        //     txttlgiam.Enabled = true;
        //     txtthgiam.Enabled = true;
        //     txtthgiamip.Enabled = true;
        //     txtdgemail.Enabled = true;
        //     txtngiamemail.Enabled = true;
        //     txtcuocmb.Enabled = true;
        //     txtcuocmphi.Enabled = true;
        //     txttlgiamip.Enabled = true;
        // }
        // if (this.thongso_tc == 2)//tất cả enable = false
        // { 
        //     txtmienip.Enabled = false;
        // }
        // if (this.loaitb_id == LoaiHinhTB.INTERNET_FTTH || this.loaitb_id == LoaiHinhTB.WIFI_FIBER)
        // {
        //     label28.Visible = false;
        //     label29.Visible = false;
        //     label30.Visible = false;
        //     label31.Visible = false;
        //     label32.Visible = false;
        //     label33.Visible = false;
        //     label34.Visible = false;
        //     label35.Visible = false;
        //     txtcuocmb.Visible = false;
        //     txtcuocmphi.Visible = false;
        //     txtmbmphi.Visible = false;
        //     txttiengiam.Visible = false;
        // }
        // else
        // {
        //     label28.Visible = true;
        //     label29.Visible = true;
        //     label30.Visible = true;
        //     label31.Visible = true;
        //     label32.Visible = true;
        //     label33.Visible = true;
        //     label34.Visible = true;
        //     label35.Visible = true;
        //     txtcuocmb.Visible = true;
        //     txtcuocmphi.Visible = true;
        //     txtmbmphi.Visible = true;
        //     txttiengiam.Visible = true;
        // }
        if (this.thongtintc)
            this.TachXau(this.thongtintc);
        // else // Case else nay da xu ly default o tren
        // {
        //     txtcuoctb.Text = cuoc_tb.ToString();
        //     txtcuockh.Text = cuockhoan.ToString();
        //     txtcuoctg.Text = cuoc_tg.ToString();
        //     txtcuocmb.Text = cuoc_ll.ToString();
        //     if (mienip > 0)
        //     {
        //         txtmienip.Text = mienip.ToString();
        //     }
        //     else
        //     {
        //         txtmienip.Text = "";
        //     }
        // }
    },
    computed: {
    },
    methods: {
        closeForm() {
            this.$emit('form-close',null);
        },
        acceptForm() {
            if(!this.KiemTraDuLieu()) return;
            this.thongtintc = "";
            this.thongtintc += "cuoctb:" + this.txtcuoctb + ";";                          //1 cuoctb
            this.thongtintc += "cuockh:" + this.txtcuockh + ";";                          //2 cuockh
            this.thongtintc += "cuoctg:" + this.txtcuoctg + ";";                          //3 cuoctg
            this.thongtintc += "tlgiam:" + this.txttlgiam + ";";                          //4 tlgiam

            if (this.txtngiam)
                this.thongtintc += "ngiam:" + this.txtngiam + ";";           //5 ngiam
            else
                this.thongtintc += "ngiam:;";           //5 ngiam
            this.thongtintc += "thgiam:" + this.txtthgiam + ";";                          //6 thgiam
            // if (this.txtmienip > 0)
            // {
            //     this.thongtintc += "mienip:" + this.txtmienip + ";";
            // }
            // else
            this.thongtintc += "mienip:" + this.txtmienip + ";"; ;                        //7 mienip
            this.thongtintc += "slemail:" + this.txtslemail + ";";                        //8 slemail
            this.thongtintc += "dgemail:" + this.txtdgemail + ";";                        //9 dgemail
            this.thongtintc += "tlgiamemail:" + this.txttlgiamemail + ";";                //10 tlgiamemail

            if (this.txtngiamemail)
                this.thongtintc += "ngiamemail:" + this.txtngiamemail + ";"; //11 ngiamemail     dtp
            else
                this.thongtintc += "ngiamemail:;"; //11 ngiamemail     dtp
            this.thongtintc += "thgiamemail:" + this.txtthgiamemail + ";";                //12 thgiamemail
            this.thongtintc += "tlgiamip:" + this.txttlgiamip + ";";                      //13 tlgiamip
            if (this.txtngiamip)
                this.thongtintc += "ngiamip:" + this.txtngiamip + ";";       //14 ngiamip        dtp
            else
                this.thongtintc += "ngiamip:;";       //14 ngiamip        dtp
            this.thongtintc += "thgiamip:" + this.txtthgiamip + ";";                      //15 thgiamip
            //if (loaitb_id == LoaiHinhTB.INTERNET_ADSL)
            //{
            this.thongtintc += "cuocmb:" + this.txtcuocmb + ";";                          //16 cuocm
            this.thongtintc += "mbmphi:" + this.txtmbmphi + ";";                          //17 mbmphi
            this.thongtintc += "cuocmphi:" + this.txtcuocmphi + ";";                      //18 cuocmphi
            this.thongtintc += "tiengiam:" + this.txttiengiam + ";";                      //19 tiengiam
            this.$emit('form-close',this.thongtintc);
        },
        TachXau(st) {
            // TODO chua lam
            var dsso = st;
            dsso = dsso.replace(" ", "");
            
            var strArr2 = dsso.split(';');
            for (var i = 0; i <= strArr2.length - 1; i++)
            {
                var temp = "";
                temp = strArr2[i].substring(strArr2[i].indexOf(":") + 1);

                if (i == 0)
                {
                    this.txtcuoctb = temp;
                    continue;
                }
                if (i == 1)
                {
                    this.txtcuockh = temp;
                    continue;
                }
                if (i == 2)
                {
                    this.txtcuoctg = temp;
                    continue;
                }
                if (i == 3)
                {
                    this.txttlgiam = temp;
                    continue;
                }
                if (i == 4)
                {
                    //if (!string.IsNullOrEmpty(temp))
                        //dtpngiam.Value = Convert.ToDateTime(temp);
                    this.txtngiam = temp;
                    continue;
                }
                if (i == 5)
                {
                    this.txtthgiam = temp;
                    continue;
                }
                if (i == 6)
                {
                    this.txtmienip = temp;
                    continue;
                }
                if (i == 7)
                {
                    this.txtslemail = temp;
                    continue;
                }
                if (i == 8)
                {
                    this.txtdgemail = temp;
                    continue;
                }
                if (i == 9)
                {
                    this.txttlgiamemail = temp;
                    continue;
                }
                if (i == 10)
                {
                    //if (!string.IsNullOrEmpty(temp))
                    //    dtpngiamemail.Value = Convert.ToDateTime(temp);
                    this.txtngiamemail = temp;
                    continue;
                }
                if (i == 11)
                {
                    this.txtthgiamemail = temp;
                    continue;
                }
                if (i == 12)
                {
                    this.txttlgiamip = temp;
                    continue;
                }
                if (i == 13)
                {
                    //if (!string.IsNullOrEmpty(temp))
                    //    dtpngiamip.Value = Convert.ToDateTime(temp);
                    this.txtngiamip = temp;
                    continue;
                }
                if (i == 14)
                {
                    this.txtthgiamip = temp;
                    continue;
                }
                if (i == 15)
                {
                    this.txtcuocmb = temp;
                    continue;
                }

                if (i == 16)
                {
                    this.txtmbmphi = temp;
                    continue;
                }
                if (i == 17)
                {
                    this.txtcuocmphi = temp;
                    continue;
                }
                if (i == 18)
                {
                    this.txttiengiam = temp;
                    continue;
                }
            }
        },
        //TODO tao validate
        async KiemTraDuLieu()
        {
            //  DataTable dt = bangts.Lay_tttc_theo_mucuoctb_id(muccuoctb_id);
            this.$root.showLoading(true);
            var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/kiemtra_dl_thongtintc",{ "id": this.muccuoctb_id });
            var dt = rs.data;
            this.$root.showLoading(false);
            if (dt.length > 0)
            {
                var cuoc_tb_tu = dt[0]["cuoc_tb_tu"]?dt[0]["cuoc_tb_tu"]:0;
                var cuoc_tb_den = dt[0]["cuoc_tb_den"]?dt[0]["cuoc_tb_den"]:0;
                var cuockhoan_tu = dt[0]["cuockhoan_tu"]?dt[0]["cuockhoan_tu"]:0;
                var cuockhoan_den = dt[0]["cuockhoan_den"]?dt[0]["cuockhoan_den"]:0;
                var cuoc_tg_tu = dt[0]["cuoc_tg_tu"]?dt[0]["cuoc_tg_tu"]:0;
                var cuoc_tg_den = dt[0]["cuoc_tg_den"]?dt[0]["cuoc_tg_den"]:0;

                if (cuoc_tb_tu != 0 && this.txtcuoctb < cuoc_tb_tu)
                {
                    this.$toast.error("Cước thuê bao không được nhỏ hơn " + cuoc_tb_tu);
                    return false;
                }
                if (cuoc_tb_den != 0 && this.txtcuoctb > cuoc_tb_den)
                {
                    this.$toast.error("Cước thuê bao không được lớn hơn " + cuoc_tb_den);
                    return false;
                }

                if (cuockhoan_tu != 0 && this.txtcuockh < cuockhoan_tu)
                {
                    this.$toast.error("Cước khoán không được nhỏ hơn " + cuockhoan_tu);
                    return false;
                }
                if (cuockhoan_den != 0 && this.txtcuockh > cuockhoan_den)
                {
                    this.$toast.error("Cước khoán không được lớn hơn " + cuockhoan_den);
                    return false;
                }

                if (cuoc_tg_tu != 0 && this.txtcuoctg < cuoc_tg_tu)
                {
                    this.$toast.error("Cước trọn gói không được nhỏ hơn " + cuoc_tg_tu);
                    return false;
                }
                if (cuoc_tg_den != 0 && this.txtcuoctg > cuoc_tg_den)
                {
                    this.$toast.error("Cước trọn gói không được lớn hơn " + cuoc_tg_den);
                    return false;
                }
            }

            return true;
        }
    }
}
</script>