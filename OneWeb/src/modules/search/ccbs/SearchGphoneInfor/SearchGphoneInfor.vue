
<template src='./SearchGphoneInfor.html'></template>

<style scoped src='./SearchGphoneInfor.scss'></style>

<script>
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import collums from "./define/collums.js";
import DataGridCustom from "./components/DataGrid";
import DatePicker from 'vue2-datepicker' 
import 'vue2-datepicker/locale/vi.js' 
import 'vue2-datepicker/index.css'
import moment from 'moment'
import {
    required,
    minLength
} from 'vuelidate/lib/validators'
export default {
    components: {
        VueElement,
        DataGridCustom,
    },
    data() {
        return {
            dateconfig: {
                altFormat: 'd/m/Y',
                altInput: true,
                dateFormat: 'd/m/Y',
                allowInput: true,
            },
            dateFormat: "DD/MM/YYYY",
            ...collums,
            header: {
                title: "Tra cứu thông tin Gphone",
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
            ckeGoiDi:0, 
            ckeGoiDen:0, 
            txtLoaiTB:"",
            txtTinh:"",
            txtSoMSIN:"",
            txtNgayKichHoat:"",
            txtPin1:"",
            txtPin2:"",
            txtPUK1:"",
            txtPUK2:"",
            txtTrangThai:"",
            txtTaiKhoanChinh:"",
            txtTaiKhoanPhu:"",
            txtNgayTao:"",
            txtNgayKH:"",
            txtHanGoiDi:"",
            txtTrangThaiTruoc:"",
            txtZoneCode:"",
            txtMaxCell:"",
            txtStatus:"",
            ngay_gv: "",
            Fn: {
                NhapMoi: false,
                GhiLai: false,
                HuyBo: false,
                Xoa: false,
            },
            nhanvienId: this.$root.token.getNhanVienID(),
            donviId: this.$root.token.getDonViID(),
            dataSelected: {
              
            },
            options: {
                LSThuebao:[],
                lstCellList:[],
                DichvuSD: [],
                LSCuocgoi: [],
                LSLocation: [],
                LSNapthe: [], 
                LSNhantin:[]
            },
        };
    },
    mounted() {
        this.init();
    },
    computed: {

    },
    validations: {

    },
    methods: {
        async init() {
            try {
              
            } catch (e) {
                console.log(e);
            }
        },
        //Lấy thông tin thuê bao
        async TimKiem()
        {
            try
            {
                let response = await api.Ts_tracuu_laytt_tb(this.axios, {"so_tb":"84"+this.txtSoMay});
                if ((response.data.error === 200 || response.data.error === '200' ) && response.data.data == null) {
                    this.$toast.warning(response.data.message);
                    return
                }
                let tttb = response.data.data//this.GetData(await api.Ts_tracuu_laytt_tb(this.axios, {"so_tb":"84"+this.txtSoMay}));
                if (tttb[0].KHOSO == 'ERR-001')
                {
                    this.$toast.warning("Không tìm thấy thông tin thuê bao");
                   
                }
                else
                {
                    let tthtGPhone = this.GetData(await api.Gphone_tracuu_tt_tb(this.axios, {"so_tb":"84"+this.txtSoMay}));
            
                    let dvDangKy = this.GetData(await api.Gphone_tracuu_tt_dv(this.axios, {"so_tb":"84"+this.txtSoMay}));
            
                    let ttCell = this.GetData(await api.Gphone_tracuu_tt_cell(this.axios, {"so_tb":"84"+this.txtSoMay}));
            
                    let lstb = this.GetData(await api.Gphone_reset_huy_ls_tb(this.axios, {"so_tb":"84"+this.txtSoMay}));

                    let tttb_dv = this.GetData(await api.Ts_tracuu_laytt_tb_dv(this.axios, {"so_tb":"84"+this.txtSoMay}));

                    this.options.DichvuSD = tttb_dv; 
                    this.options.LSThuebao = lstb;
                
                    if(tttb[0].GOI_DI)
                        this.ckeGoiDi = tttb[0].GOI_DI;
                    if(tttb[0].GOI_DEN)
                        this.ckeGoiDen = tttb[0].GOI_DEN;
                    if(tttb[0].LOAI_TB)
                        this.txtLoaiTB = tttb[0].LOAI_TB;
                    if(tttb[0].MA_TINH)
                        this.txtTinh = tttb[0].MA_TINH;
                    if(tttb[0].SO_MSIN)
                        this.txtSoMSIN = tttb[0].SO_MSIN;
                    else if (tttb[0].IMSI)
                        this.txtSoMSIN = tttb[0].IMSI.substring(5);
                    if(tttb[0].NGAY_KH)
                        this.txtNgayKichHoat = tttb[0].NGAY_KH;
                    if(tttb[0].PIN)
                        this.txtPin1 = tttb[0].PIN;
                    if(tttb[0].PIN2)
                        this.txtPin2 = tttb[0].PIN2;
                    if(tttb[0].PUK)
                        this.txtPUK1 = tttb[0].PUK;
                    if(tttb[0].PUK2)
                        this.txtPUK2 = tttb[0].PUK2;
                    //Thông tin hệ thông GPHONE-IN
                    if (tthtGPhone != null)
                        for (let i = 0; i < tthtGPhone.length; i++)
                            {
                                this.txtTrangThai = tthtGPhone[0].CURRENTSTATE;
                                this.txtTaiKhoanChinh = tthtGPhone[0].COREBALANCE;
                                this.txtTaiKhoanPhu = tthtGPhone[0].PROMOBALANCE;
                                this.txtNgayTao = tthtGPhone[0].CREATIONDATE;
                                this.txtNgayKH = tthtGPhone[0].DATEENTERACTIVE;
                                this.txtHanGoiDi = tthtGPhone[0].LASTTRANSDATE;
                                this.txtTrangThaiTruoc = tthtGPhone[0].PREVIOUSSTATE;
                            }
                    //Thông tin Cell
                    if (ttCell != null)
                        for (let i = 0; i < ttCell.length; i++)
                            {
                                    this.txtZoneCode = ttCell[0].ZONE_CODE;
                                    this.txtMaxCell = ttCell[0].MAX_CELL;
                                    this.txtStatus = ttCell[0].STATUS;
                                    this.ckbCellList = ttCell[0].CELL_LIST;
                            } 
                }
            }
            catch (err)
            {
                this.$toast.error("Lỗi: " + err.message);
            }
        },
        txtSoMay_KeyPress()
        { 
                if (this.txtSoMay.trim() != "")
                {
                    this.TimKiem();
                }
        },

        tsbtnLayTT_Click()
        {
            this.TimKiem();
        },
        GetData: function (response) {
            if (response.data.error === 200 || response.data.error === '200') {
                return response.data.data
            } else {
                console.log(response.data.error_code)
            }
            return []
        },
        
    },
    watch: {
      'options.DichvuSD':function(newval) {
        api.Ts_tracuu_laytt_tb_dv(this.axios, {"so_tb":"84"+this.txtSoMay}).then((response) => {
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.options.DichvuSD = response.data.data.map(x=>x.MA_DV + ' - ' +x.TEN_DV).join("\n");
        }
      });
    },
    }
};
</script>
