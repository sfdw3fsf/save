
<template src='./SearchDebtByMonth.html'></template>

<style scoped src='./SearchDebtByMonth.scss'></style>

<script>
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import collums from "./define/collums.js";
import DataGridCustom from "./components/DataGrid";
import moment from 'moment'
import { mapActions,mapGetters } from 'vuex'
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
                altFormat: 'd/m/Y H:i:S',
                altInput: true,
                dateFormat: 'd/m/Y H:i:S',
                allowInput: true,
            },
            dateFormat: "DD/MM/YYYY",
            ...collums,
            header: {
                title: "Tra cứu nợ theo tháng",
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
            Fn: {
                NhapMoi: false,
                GhiLai: false,
                HuyBo: false,
                Xoa: false,
            },
            ma_tt_neo: "",
            txtMaTB: "",
            txtMaKh:"",
            txtMaTuyen:"",
            txtMaTBDD:"",
            txtMST:"",
            txtMaNV:"",
            txtDiaChiKH:"",
            txtTenKH:"",
            ky_hoadon:'01'+moment(new Date()).subtract(1,'months').endOf('month').format('MMYYYY'),
            ky_hoadon_tn:moment(new Date()).subtract(1,'months').endOf('month').format('MMYYYY'),
            nhanvienId: this.$root.token.getNhanVienID(),
            donviId: this.$root.token.getDonViID(),
            dataSelected: {
              
            },
            options: {
                dsNo: []
            },
        };
    },
    mounted() {
        this.init();
    },
    computed:{
    },
    validations: {

    },
    methods: {
        ...mapActions("qltnCommon", [
            "getKyHoaDonHienTai",
        ]),
         ...mapGetters('admin', [
            'getKyhoadon',
            'getChuKy'
        ]),
        async init() {
            try {
                this.Ts_layds_chukyno();
            } catch (e) {
                console.log(e);
            }
        },
        async Ts_layds_chukyno()
        {
            await api.Ts_layds_chukyno(this.axios, { 
                "page_num": 0,
                "page_rec":5
                }).then((res) => {
                     let data = res.data.data;
                     let ds_kyhoadon = data.filter(item => item.TRANGTHAI == 'checked');
                     this.ky_hoadon = '01'+ds_kyhoadon[0].CHUKYNO.replace('/','').trim()
                     this.ky_hoadon_tn = ds_kyhoadon[0].CHUKYNO.replace('/','').trim();
                });

        },
        //919263988/HPG-03-947226
        async btnTraCuu_Click()
        {
            await api.Ts_tracuu_ls_notra(this.axios, { 
                "ma_kh": "",
                "so_tb": "84"+this.txtMaTB,
                "chukyno": this.ky_hoadon_tn
            }).then((res) => {
                    this.options.dsNo =res.data.data;
                });

        },
         griddataGridCellCreated:  function(args){
            $(args.cell).text(args.cell.innerText.replace('Thang no','Tháng').replace('Tong cong','Tổng cộng'));
            if(args.cell.innerText.includes('<')){
                 $(args.cell).css({fontWeight:"bold"});
                 $(args.cell).text(args.cell.innerText.replace('<b>','').replace('</b>',''));
            }
        },
        txtMaKh_KeyPress()
        {
            this.GetMa_TT_Neo();
            this.Fill_Info(this.txtMaKh);         
        },
        async GetMa_TT_Neo()
        {
            try
            {
                await api.GetMa_TT_Neo(this.axios, {
                    "vma":this.txtMaKh,
                    "vchuky":this.ky_hoadon
                }).then((res) => {
                    console.log(res.data.data)
                    if (res.data.data.tonghop.length > 0) {
                        this.ma_tt_neo = res.data.data.tonghop[0].ma_tt_neo.toString();
                        this.txtMaTB = res.data.data.tonghop[0].matb_dd.toString();
                    }else{
                        this.$toast.warning("Không thể tìm thấy thông tin về khách hàng với mã: " + this.txtMaKh);
                    }
                });
            }
            catch (err)
            {
                this.$toast.error("Không thể tìm thấy thông tin về khách hàng với mã: " + this.txtMaKh);
            }
        },

        txtMaTB_KeyPress()
        {
            this.Fill_Info(this.txtMaTB);
            this.GetMa_TT_Neo();

        },

        async Fill_Info(searchString)
        {
          console.log(2)
            try
            {
                
                await api.Fill_Info(this.axios, {
                    "p_kyhoadon": this.ky_hoadon,
                    "p_payment_code":searchString
                }).then((res) => {
                    if (res.data.error_code === "BSS-00000000") {
                        this.txtMaKh = res.data.data[0].ma_tt;
                        this.txtMaTuyen = res.data.data[0].ma_tuyen;
                        this.txtMaTBDD = res.data.data[0].matb_dd;
                        let mst = res.data.data[0].mst;
                        if (mst != "0"){
                            this.txtMST = mst;
                        }  
                        else
                        {
                            this.txtMST = "";
                        }
                        this.txtMaNV = res.data.data[0].manv_tc;
                        this.txtDiaChiKH = res.data.data[0].diachi_tt;
                        this.txtTenKH = res.data.data[0].ten_tt;
                    }
                });
            }
            catch (err)
            {
                 this.$toast.error("Lỗi: " + err.message);
            }
        }
        
    },
    watch: {}
};
</script>
