<template src="./SearchVNPPayment.html"></template>

<style scoped src='./SearchVNPPayment.scss'></style>

<script>
import Vue from "vue"
import {
    mapActions,
    mapState
} from "vuex"
import moment from 'moment'
import api from './api/index.js'
import collums from "./define/collums.js";
import {
    DialogUtility
} from '@syncfusion/ej2-popups'
Vue.use(DialogUtility)
let DialogObj = undefined
export default {
    mounted() {
        this.init();
    },
    computed: {},
    data() {
        return {
            hideTable: true,
             ...collums,
            header: {
                title: "TRA CỨU ĐỐI CHIẾU TRẢ CƯỚC VINAPHONE",
                list: [{
                        name: "Lập hợp đồng",
                        link: {
                            name: "Ui.cards"
                        }
                    },
                    {
                        name: "Lắp đặt mới",
                        link: {
                            name: "Ui.buttons"
                        },
                    },
                ],
            },
            configs: {
                selectionSettings: {
                    checkboxOnly: true,
                    type: 'Multiple'
                },
                fieldsKyHoaDon: {
                    text: 'THANGNO',
                    value: 'CHUKYNO'
                },
                f_gachno_visible: false,
                f_xoaphieu_visible: false,
                f_dongbo_visible: false,
            },
            tt_nd: {
                ngay_cn: this.$auth.getNgayCapNhat(),
                tentat: "",
                donvi_id: this.$auth.getDonViID(),
                nhanvien_id: this.$auth.getNhanVienID(),
                may_cn: "",
                nguoi_cn: "",
                ma_tinh: this.$root.token.getMaTinh(),
                ip: "",
                user_neo: this.$root.token.getUserName()
            },
            params: {
                p_tab_kieu: 1, // 1: danh sách VNP, 2: danh sách trả dưới tỉnh
                p_tab_kieuno: 1, // 1: tháng nợ, 2: thuê bao
                p_loai_timkiem: 0, // 0: ma_tt, 1: so_dd
                p_kieu_thaotac: 1, // tra cứu
                p_kyhoadon_options: [],
                p_kyhoadon: null,
                p_ma_tt: null,
                p_ma_kh: null,
                p_so_dd: null,
                p_chuky: null,
                p_kyhoadon_vnp: null,
                p_hoadon: null,
                p_user_neo: null,
                p_neo_ma_tt: null,
                p_ma_dd: null,
                p_ten_tt: null,
                p_diachi_tt: null,
                chukygoc: null,
                p_Log: null,
                p_so_phieu: null,
                p_status: null // trạng thái kỳ hóa đơn
            },
            chkMaTT: {
                Checked: true,
            },
            chkSoMay: {
                Checked: false,
            },
            chkUserNeo: {
                Checked: false,
            },
            sum: 1245,
            dsTraVNP: [],
            keyDSTraVNP: 1,
            dsTraVTT: [],
            dsTraVTT_Tonghop: [],
            keyDSTraVTT: 1,
            hienthi_chitiet: true,
            dsThueBao: [],
            keyDSThueBao: 1,
            dsThangNo: [],
            keyDSThangNo: 1,
            p_thanhtoan_options: []
        }
    },
    methods: {
        ...mapActions("qltnCommon", [
            "getKyHoaDonHienTai",
        ]),
        formatPrice(value) {
            let val = (value / 1).toFixed(0).replace(',', '.')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
        },
        footerSum () {
                let sum = this.dsTraVNP.reduce((a,v) =>  a = parseInt(a) + parseInt(v.tientra.replaceAll(',','')) , 0 );

                return {
                    template: Vue.component('sumTemplate', {
                        template: "<span>"+this.formatPrice(sum)+"</span>",
                        data() {
                            return {
                                data: {}
                            };
                        }
                    })
                }
            },
        loaddata(){
          this.doSearchAll()
        },
        async init() {
            this.tt_nd.may_cn = await this.$root.token.getMachine();
            this.tt_nd.nguoi_cn = await this.$root.token.getUserName();
             this.tt_nd.ip = await this.$root.token.getIP();
            try {
                this.getTSMacDinh();
                this.getDSChuKyNo();
                this.params.p_kieu_thaotac = 1 // tra cứu, tạm thời set 1
                if (this.params.p_kieu_thaotac === 1) {
                    this.configs.f_xoaphieu_visible = false
                    this.configs.f_gachno_visible = false
                    this.configs.f_dongbo_visible = true
                } else {
                    this.configs.f_xoaphieu_visible = true
                }
            } catch (e) {
                console.log(e);
            }
        },
        doSearch() {
            this.TIMKIEM()
        },
        delete_log(){
            this.params.p_Log = ''
        },
        async doSync() {
            if (this.params.p_status === -1) {
                this.$toast.error('Không lấy được trạng thái của kỳ hóa đơn')
                return
            }
            // if (this.params.p_status === 1) {
            //     this.$toast.error("Kỳ hóa đơn " + this.params.p_kyhoadon + " không được phép đồng bộ")
            //     return
            // }
            if (this.dsTraVNP.length === 0) {
                this.$toast.error("Không có phiếu thu cần đồng bộ.")
                return
            }

            let selectedRows = this.$refs.gridDSTraVNP.getSelectedRecords()

            if (selectedRows.length === 0) {
                this.$toast.error("Bạn chưa chọn phiếu cần đồng bộ.")
                return
            }
            let vmakh = "0";
            if (this.params.p_ma_kh != "")
                vmakh = this.params.p_ma_kh;
            if (this.dsTraVNP.length <= 0) {
                this.$toast.error("Lỗi lấy danh sách phiếu: " + this.dsTraVNP);
            }
            let vChuky = this.params.p_chuky;
            let vHoadon = this.params.p_hoadon;
            this.GACHNO_VNP(this.tt_nd.user_neo, 1, vHoadon, '01' + vHoadon, this.params.p_ma_dd,
                vmakh, "", this.dsTraVNP)
        },
        SET_TEXT(noidung, kieu) {
            if (kieu == 0) //Loi
            {
                this.params.p_Log = this.params.p_Log + " \r\n " + noidung;
            }
        },
        async GACHNO_VNP(p_user_neo, kieu, chukygoc, kyhd, p_matb, makh, khongxacdinh, data) {
            try {
                let dt = [];
                let data_input_ob = {};
                let data_input_arr = [];
                let matb = "";
                if (makh == "0")
                    matb = p_matb.substring(2)
                else
                    matb = "0";

                let vkyhd = "0";
                vkyhd = "01" + chukygoc;
                for(var i = 0;i<data.length;i++){
                    data_input_ob.PH = data[i].phieu_id;
                    data_input_ob.SP = data[i].sophieu;
                    data_input_ob.MKH = data[i].ma_kh;
                    data_input_ob.NG = data[i].nguoigach;
                    data_input_ob.NTHUC = data[i].ngay_thuc;
                    data_input_ob.HTTT = data[i].hinhthuc;
                    data_input_ob.HTTT_ID = data[i].httt_id;
                    data_input_ob.NGAYTT = data[i].ngay_tt;
                    data_input_ob.TGOC = data[i].tragoc;
                    data_input_ob.TTHUE = data[i].trathue;
                    data_input_ob.CHKY = data[i].chukyno;
                    data_input_arr.push(data_input_ob)
                }
                let input = {
                    "vuser_neo": p_user_neo,
                    "vflags": 1,
                    "vma_tb": matb,
                    "vma_kh": makh,
                    "vdata": JSON.stringify(data_input_arr),
                    "khd": this.params.chukyno
                }
                dt = this.GetData(await api.lay_ds_phieu_gn_vinaphone(this.axios, input)) == null ? [] : this.GetData(await api.lay_ds_phieu_gn_vinaphone(this.axios, input));
                console.log(dt)
                if (dt.length > 0) {
                    let ketqua_ = this.GetData(await api.sp_insert_bss_ct_tras(this.axios, {}));
                    if (ketqua_.data != "1") {
                        this.SET_TEXT(this.tt_nd.ngay_cn + " - " + ketqua_, 0);
                    }
                    let sl = 0;
                    let kyhd = chukygoc;
                    let hd = kyhd;
                    kyhd = hd.substring(0, 2) + hd.substring(2, 6) ;
                    let ghichu = "";
                    let a = this.GetData(await api.lay_thang_no(this.axios, {}));
                    if (a == null) {
                        this.$toast.warning("Không lấy được kỳ hóa đơn để đồng bộ");
                        return;
                    }
                    let kyhd_ht = a.toString().substring(6, 4) + a.toString().substring(0, 4);
                    if (kyhd_ht != chukygoc.toString())
                    {
                        this.$toast.warning( "Kỳ hóa đơn đã bị thay đổi hãy kiểm tra lại");
                        return;
                    }
                    // Kiểm tra kỳ hóa đơn gạch so với kỳ hóa đơn hiện tại
                    if (parseInt(kyhd_ht) - parseInt(chukygoc) > 1)
                    {
                        this.$toast.warning( "Bạn không được phép đồng bộ dữ liệu cho kỳ hóa đơn này");
                        return;

                    }
                    let date = moment().format('DD/MM/YYYY HH:mm:ss');
                    if (kyhd_ht != kyhd) // nẾu khác là thì gạch lùi
                    {
                        ghichu = "DONGBO_LUI_CCBS";
                        this.GetData(await api.fn_update_phieu_id_bangphieutra_01(this.axios, {
                            "vkyhd": hd.substring(0, 2) + hd.substring(2, 6)
                        }))
                    } else {
                        ghichu = "GACHNO_CCBS";
                        
                    }
                    for (let i = 0; i < dt.length; i++) {
                        let dr = dt[i];
                        let input = {
                            "vuser_neo": dr.user_vnp,
                            "vflags": 1,
                            "vchuky": "01",
                            "vkhd": this.params.p_kyhoadon.toString(),
                            "vhoadon": this.params.p_hoadon.toString(),
                            "vhtttid_neo": 0,
                            "vma_tinh": this.tt_nd.ma_tinh,
                            "vmay_cn": this.tt_nd.may_cn,
                            "vnguoi_cn": this.tt_nd.nguoi_cn,
                            "vip_cn": this.tt_nd.ip,
                            "vhinhthuc_tt": dr.hinhthuc_tt,
                            "vhttt_id_neo": dr.httt_id_neo,
                            "vphieu_neo_id": dr.phieu_id,
                            "vma_kh": dr.ma_kh,
                            "vngay_tt": dr.ngay_tt,
                            "vkieu": 1,
                            "vghichu_cn": ghichu,
                            "vngay_cn": date
                        }
                        console.log(input)
                        let k = this.GetData(await api.dongbo_dl_vnp_ccbs(this.axios, input));
                        if (k.toString() == "1") {
                            sl += 1;
                            this.$root.$toast.success("Đã đồng bộ thành công")
                        } else {
                            this.SET_TEXT(this.tt_nd.ngay_cn + " - " + k.toString(), 0);
                        }
                    }
                }else{
                    this.SET_TEXT("Không tìm thấy dữ liệu để đồng bộ.",0);
                }
            } catch (err) { 
                this.$toast.error('Có lỗi trong quá trình đồng bộ:' + err)
            }

        },
        TIMKIEM() {
            this.doSearchAll()
        },

        validateSearch() {
            let errors = []
            if (this.params.p_kyhoadon === null)
                errors.push('Không lấy được kỳ hóa đơn')

            if (this.params.p_loai_timkiem === 0 && (this.params.p_ma_tt === null || ('' + this.params.p_ma_tt).trim() === '')) {
                errors.push('Chưa nhập mã khách hàng')
            }
            if (this.params.p_loai_timkiem === 1 && (this.params.p_so_dd === null || ('' + this.params.p_so_dd).trim() === '')) {
                errors.push('Chưa nhập số máy')
            }
            if (this.params.p_loai_timkiem === 2 && (this.params.p_user_neo === null || ('' + this.params.p_user_neo).trim() === '')) {
                errors.push('Chưa nhập user Neo')
            }

            if (errors.length > 0) {
                this.$toast.error(errors.join('\r\n'))
                return false
            }

            return true

        },
        changeSearchType(type) {
            this.params.p_loai_timkiem = type
            if (type == 0) {
                this.chkUserNeo.Checked = false;
                this.chkSoMay.Checked = false;
                this.chkMaTT.Checked = true;
                this.configs.f_dongbo_visible = true;
                this.params.p_so_dd = '';
                this.params.p_user_neo = '';
            } else if (type == 1) {
                this.chkSoMay.Checked = true;
                this.chkUserNeo.Checked = false;
                this.chkMaTT.Checked = false;
                this.configs.f_dongbo_visible = true;
                this.params.p_ma_tt = '';
                this.params.p_user_neo = '';
            } else if (type == 2) {
                this.chkSoMay.Checked = false;
                this.chkMaTT.Checked = false;
                this.chkUserNeo.Checked = true;
                this.configs.f_dongbo_visible = false;
                this.params.p_ma_tt = '';
                this.params.p_so_dd = '';
            }
        },

        clear() {
            this.p_thanhtoan_options = []
            this.params.p_neo_ma_tt = null
            this.params.p_ma_dd = null
            this.params.p_ten_tt = null
            this.params.p_diachi_tt = null
        },

        doSearchAll() {
            if (!this.validateSearch()) return
            let keyword = ''
            if (this.params.p_loai_timkiem === 0)
                keyword = this.params.p_ma_tt.trim()
            else if (this.params.p_loai_timkiem === 1)
                keyword = ('' + this.params.p_so_dd).trim()
            else if (this.params.p_loai_timkiem === 2)
                keyword = this.params.p_user_neo.trim()

            if (this.params.p_loai_timkiem === 2)
                this.configs.f_dongbo_visible = false
            else
                this.configs.f_dongbo_visible = true;
            console.log(this.params.p_chuky+this.params.p_hoadon)
            let postData = {
                "vma": keyword,
                "vchuky": this.params.chukyno,
            }
            this.loading(true);
            this.clear();
            api.getMaTTNeoVNP(this.axios, postData)
                .then((res) => {
                    if (res.data.error_code === 'BSS-00000000' &&
                        res.data.data.tonghop !== undefined &&
                        res.data.data.tonghop.length > 0) {
                        if (res.data.data.tonghop.length > 1) {
                            this.p_thanhtoan_options = res.data.data
                            $("#ds-thanhtoan").show();
                        } else {
                            this.params.p_ma_kh = res.data.data.tonghop[0].ma_tt_neo
                            this.params.p_ma_tt = res.data.data.tonghop[0].ma_tt_neo
                            this.params.p_neo_ma_tt = res.data.data.tonghop[0].ma_tt_neo
                            this.params.p_ma_dd = res.data.data.tonghop[0].matb_dd
                            this.params.p_ten_tt = res.data.data.tonghop[0].ten_tb
                            this.params.p_diachi_tt = res.data.data.tonghop[0].chungtu

                            this.NAP_DS_TRACUU()

                        }
                    } else {
                        this.$toast.error('Không tìm thấy mã thanh toán này')
                    }
                })
                .catch(error => {
                    this.$toast.error("Không tìm thấy thông tin mã thuê bao tại chu kỳ nợ:" + this.params.p_kyhoadon)
                })
                .finally(() => {
                    this.loading(false)
                })
        },

        NAP_DS_TRACUU() {
           
            this.getLSPhieuTra()
        },

        async getTSMacDinh() {
            try{
                 let input =  {
                "vma_ts": "GACHNO_CHUAN",
                "vma_nd": "admin.btn"
                }
                let ds_tsmd = this.GetData(await api.sp_lay_ct_tsmd_nguoidung(this.axios, input));
                if (ds_tsmd == null)
                {
                    this.$toast.warning("Không lấy được phương thức gạch di động ");
                    return;
                }
                let gachno_chuan = parseInt(ds_tsmd[0].giatri.toString());
                if (gachno_chuan == -1)
                {
                    this.$toast.warning("Không lấy được phương thức gạch di động ");
                     return;
                }
            }catch(err){
                this.$toast.error("Không lấy được phương thức gạch di động "+err.message)
            }
        },

        getDSChuKyNo() {

            this.params.p_kyhoadon_options = []
            this.params.p_kyhoadon = null

            api.getDSChuKyNo(this.axios)
                .then((response) => {
                    console.log(response.data.data)
                    if (response.data.error_code === 'BSS-00000000' &&
                        response.data.data !== undefined &&
                        response.data.data.length > 0) {
                        this.params.p_kyhoadon_options = response.data.data;
                        this.params.p_chuky = response.data.data[0].CHUKY;
                        this.params.p_kyhoadon = response.data.data[0].CHUKYNO;
                        this.params.p_kyhoadon_vnp = response.data.data[0].CHUKYNO_VNP;
                        this.params.p_hoadon = response.data.data[0].DANGCHU.replace('/', '');
                    }
                })
                .catch(error => {})
                .finally(() => {})
        },

        getLSPhieuTra() {
            this.dsTraVNP = []
            this.dsThangNo = []
            this.dsThueBao = []
            let postData = {
                "den_kyhoadon": this.params.p_kyhoadon_vnp,
                "denngay": null,
                "diachi_tt": null,
                "luottt": null,
                "ma_kh": this.params.p_loai_timkiem === 0 ? this.params.p_ma_tt.trim() : null,
                "ma_nv": this.params.p_loai_timkiem === 2 ? this.params.p_user_neo.trim() : null,
                "ma_tuyen": null,
                "page_num": 1,
                "page_rec": 10,
                "phieus": this.params.p_so_phieu,
                "so_tb": this.params.p_loai_timkiem === 1 ? this.params.p_so_dd.trim() : null,
                "ten_tt": null,
                "tu_kyhoadon": this.params.p_kyhoadon_vnp,
                "tungay": null,
                "ma_tinh":this.tt_nd.ma_tinh
            }
            api.getLSPhieuTra(this.axios, postData)
                .then((res) => {
                     console.log(res.data.data)
                    if (res.data.error_code === 'BSS-00000000') {
                        this.dsTraVNP = res.data.data;
                        let postData = {
                            "kyhoadon": res.data.data[0].chukyno,
                            "phieu_id": res.data.data[0].phieu_id
                        }
                        
                        this.getLSNoTTTraPhieu(postData);

                        if (!this.chkUserNeo.Checked) {
                            if (this.params.p_so_dd != null &&this.params.p_so_dd != "") {
                                this.NAP_DS_PHIEUTRA(2, this.params.p_so_dd, "");
                            } else {
                                this.NAP_DS_PHIEUTRA(1, "", this.params.p_neo_ma_tt);
                            }
                        }
                    }
                })
                .catch(error => {
                     console.log(error)
                    this.$toast.error(error.message)
                })
                .finally(() => {})
        },

        getThangNobyVNP(key) {
            if (this.params.p_kyhoadon_options === null || this.params.p_kyhoadon_options.length <= 0) return null
            let found = this.params.p_kyhoadon_options.filter(x => x.CHUKYNO_VNP == key)
            if (found.length > 0)
                return found[0].THANGNO
            else
                return null
        },

        getLSNoTTTraPhieu(postData) {
            this.loading(true)
            api.getLSNoTTTraPhieu(this.axios, postData)
                .then((res) => {
                    if (res.data.error_code === 'BSS-00000000' &&
                        res.data.data !== undefined &&
                        res.data.data.length > 0
                    ) {
                        let result = res.data.data.reduce(function (r, a) {
                            r[a.chukyno] = r[a.chukyno] || [];
                            r[a.chukyno].push(a);
                            return r;
                        }, Object.create(null));
                            
                        let arrTN = []
                        let arrTB = []
                        for (const [key, value] of Object.entries(result)) {
                           
                            let thangno = this.getThangNobyVNP(key)

                            if (thangno !== null) {
                                let a = ({
                                    chukyno: thangno,
                                    tongtra: 0,
                                    tientra: 0,
                                    trathue: 0,
                                    chihoahong: 0
                                });
                                value.forEach((item) => {
                                    a.tongtra += parseInt(item.tragoc) + parseInt(item.trathue)
                                    a.tientra += parseInt(item.tragoc)
                                    a.trathue += parseInt(item.trathue)
                                    a.chihoahong += parseInt(item.chihoahong)
                                })
                                a.tongtra = this.formatPrice(a.tongtra)
                                a.tientra = this.formatPrice(a.tientra)
                                a.trathue = this.formatPrice(a.trathue)
                                a.chihoahong = this.formatPrice(a.chihoahong)
                                arrTN.push(a)
                            }
                        }
                        
                        res.data.data.forEach((item) => {
                            arrTB.push({
                                ma_tb: item.ma_tb,
                                tientra: this.formatPrice(parseInt(item.tragoc) + parseInt(item.trathue)),
                                tragoc: this.formatPrice(parseInt(item.tragoc)),
                                trathue: this.formatPrice(parseInt(item.trathue)),
                                chihoahong: this.formatPrice(parseInt(item.chihoahong)),
                                chukyno: this.getThangNobyVNP(item.chukyno)
                            })
                        })
                        this.dsThangNo = [...arrTN]
                        this.dsThueBao = [...arrTB]
                        this.keyDSThangNo++
                        this.keyDSThueBao++

                    }
                })
                .catch(error => {
                    this.$toast.error(error.data.message_detail)
                })
                .finally(() => {
                    this.loading(false)
                })
        },

        NAP_DS_PHIEUTRA(kieu, ma_tb, ma_kh) {
            let user_neo = ""

            if (kieu !== 1) // 1 là tra cứu
                user_neo = this.$auth.getMaCCBS();

            let input = {
                "vkieu_tk": kieu,
                "vuser_neo": user_neo,
                "vma_tb": ma_tb,
                "vma_kh": ma_kh,
                "kyhoadon": this.params.p_kyhoadon
            }
            api.lay_tt_phieutra_ccbs(this.axios, input)
                .then((response) => {
                     console.log(response.data.data)
                    if (response.data.error_code === 'BSS-00000000') {
                        this.dsTraVTT = this.changeKeysToUpper(response.data.data.tonghop);
                        this.dsTraVTT_Tonghop = this.changeKeysToUpper(response.data.data);
                        this.getTraVTT_ThueBao(this.dsTraVTT[0]);
                        this.getTraVTT_ThangNo(this.dsTraVTT[0]);

                    }
                })
                .catch(error => {})
                .finally(() => {})
        },

        phieuTraVNPRecordClick(args) {
            this.dsThangNo = []
            this.dsThueBao = []

            if (args === null) return
            let postData = {
                "kyhoadon": args.rowData.chukyno,
                "phieu_id": args.rowData.phieu_id
            }
            this.hienthi_chitiet = !this.hienthi_chitiet;
            this.getLSNoTTTraPhieu(postData)

        },

        phieuTraVTTRecordClick(args) {
            this.dsThangNo = []
            this.dsThueBao = []
            if (args === null) return
            let q = this.dsTraVTT_Tonghop.CHITIET.filter(x => x.PHIEU_ID === args.rowData.PHIEU_ID);

            if (q.length > 0) {
                this.getTraVTT_ThueBao(q)
                this.getTraVTT_ThangNo(q)
            }
        },

        getTraVTT_ThangNo(data) {
            let arrTN = [];
            for (let i = 0; i < data.length; i++) {
                let a = ({
                    chukyno: data[i].CHUKYNO,
                    tongtra: 0,
                    tientra: 0,
                    trathue: 0,
                    chihoahong: 0
                })

                data.forEach((item) => {
                    a.tongtra += parseInt(item.TONGTRA)
                    a.tientra += parseInt(item.TIENTRA)
                    a.trathue += parseInt(item.TRATHUE)
                    a.chihoahong += parseInt(item.CHIHOAHONG)
                })
                a.tongtra = this.formatPrice(a.tongtra)
                a.tientra = this.formatPrice(a.tientra)
                a.trathue = this.formatPrice(a.trathue)
                a.chihoahong = this.formatPrice(a.chihoahong)
                arrTN.push(a);
            }
            this.dsThangNo = [...arrTN]
            this.keyDSThangNo++
        },

        getTraVTT_ThueBao(data) {
            let arrTB = []
            data.forEach((item) => {
                arrTB.push({
                    ma_tb: item.MA_TB,
                    tientra: this.formatPrice(parseInt(item.TIENTRA)),
                    tragoc: this.formatPrice(parseInt(item.TONGTRA)),
                    trathue: this.formatPrice(parseInt(item.TRATHUE)),
                    chihoahong: this.formatPrice(parseInt(item.CHIHOAHONG)),
                    chukyno: this.getThangNobyVNP(item.CHUKYNO)
                })
            })
            this.dsThueBao = [...arrTB];
            this.keyDSThueBao++
        },
        queryCellInfoTraVNP(args) {
            let field = args.column.field
            if(field==="chitiet"){
                let id= args.data.dapan_id
                let ketqua = args.data.ketqua
                let check = ""
                if (ketqua ==1){
                    check = "checked"
                }else{
                    check = ""
                }
                args.cell.innerHTML = '<button  style = "background-color:lightgrey ; width: 40px !important" class="btn btn-main pad4 w-auto lh14 h-auto" > <span class="-ap icon-arrow_drop_down"></span> </button>'       
            }
            if (args.column.field === 'TIENTRA') {
                args.cell.classList.add('grid-redbold');
            }
        },
        queryCellInfoChiTietThangNo(args) {
            if (args.column.field === 'TIENTRA') {
                args.cell.classList.add('grid-yellowbold');
            } else if (args.column.field === 'TRAGOC') {
                args.cell.classList.add('grid-redbold');
            } else if (args.column.field === 'TRATHUE') {
                args.cell.classList.add('grid-bluebold');
            }
        },
        queryCellInfoChiTietThueBao(args) {
            if (args.column.field === 'TIENTRA') {
                args.cell.classList.add('grid-yellowbold');
            } else if (args.column.field === 'TRAGOC') {
                args.cell.classList.add('grid-redbold');
            } else if (args.column.field === 'TRATHUE') {
                args.cell.classList.add('grid-bluebold');
            }
        },
        onChangeCustomDropDown(id) {
            const isHIdden = $(id).is(":hidden");
            if (isHIdden) {
                $(id).show();

            } else {
                $(id).hide();
            }
        },

        onChangeMaTTNeo(args) {
            if (args === null) return

            $("#ds-thanhtoan").hide();

            this.params.p_ma_kh = args.MA_TT_NEO
            this.params.p_ma_tt = args.MA_TT_NEO
            this.params.p_neo_ma_tt = args.MA_TT_NEO
            this.params.p_ma_dd = args.MATB_DD
            this.params.p_ten_tt = args.TEN_TB
            this.params.p_diachi_tt = args.DIACHI_TT

            this.NAP_DS_TRACUU()

        },

        onChangeKyHoaDon(args) {
            if (args === null) {
                this.params.p_status = null
                return
            }
            console.log(this.params.p_kyhoadon_options)
            let f = this.params.p_kyhoadon_options.filter(x => x.CHUKYNO === args.value);
             console.log(f )
            if (f.length > 0) {
                this.params.p_status = f[0].STATUS;
                this.params.chukygoc = f[0].CHUKY + f[0].DANGCHU.replace("/", "");
                this.params.p_kyhoadon_vnp = f[0].CHUKYNO_VNP;
                this.params.p_hoadon = f[0].DANGCHU.replace("/", "")
                this.params.chukyno = f[0].CHUKYNO
            } else {
                this.params.p_status = null
            }
        },
        GetData: function (response) {
            console.log(response)
            if (response.data.error === 200 || response.data.error === '200') {
                return response.data.data
            } else {
                console.log(response.data.error_code)
            }
            return []
        },
        changeKeysToUpper(obj) {
            var key, upKey;
            for (key in obj) {
                if (obj.hasOwnProperty(key)) {
                    upKey = key.toUpperCase();
                    if (upKey !== key) {
                        obj[upKey] = obj[key];
                        delete(obj[key]);
                    }
                    // recurse
                    if (typeof obj[upKey] === "object") {
                        this.changeKeysToUpper(obj[upKey]);
                    }
                }
            }
            return obj;
        },

    }
}
</script>
<style>
.tragoc-color{
    color: red !important;
    font-weight: bold !important;
  }
  .tientra-color{
    color: orange !important;
  }
  .trathue-color{
    color: blue !important;
  }
  .thangtra-color{
    color: blue !important;
  }
  .chukyno-color{
    color: red !important;
  }
</style>

