<template src='./TraCuuPhieuTra.html'></template>
<style scoped src='./TraCuuPhieuTra.scss'></style>
<script>

import Vue from 'vue'
import { mapActions, mapState } from "vuex"
import Calculator from '@/components/Calculator'
import moment from 'moment'
import CommonsAPI from '../api/Commons'
import TraCuuPhieuTraAPI from '../api/TraCuuPhieuTra'
import PopupTraCuuDanhBa from '../components/PopupTraCuuDanhBa/PopupTraCuuDanhBa.vue'
import { DialogUtility } from '@syncfusion/ej2-popups'


Vue.use(DialogUtility)
let DialogObj = undefined


export default {
    components: {
      PopupTraCuuDanhBa
    },
  async created () {
      this.getDSChuKyNo()
  },
  mounted () {
  },
  destroyed () {
  },
  computed: {
      chk_tu_ngay ()
      {
          return this.params.p_chk_tu_ngay === '1'
      },
      chk_den_ngay ()
      {
          return this.params.p_chk_den_ngay === '1'
      },
      p_tu_ngay : {
        get() {
            return this.params.p_tu_ngay
        },
        set(value) {
            return this.params.p_tu_ngay = value
        }
      },
      p_den_ngay : {
        get() {
            return this.params.p_den_ngay
        },
        set(value) {
            return this.params.p_den_ngay = value
        }
      },
  },
  data () {
    return {
      selectionSettings: {checkboxOnly: true, type: 'Multiple'},
      configs: {
        dateConfig: {
          default: new Date(),
          maxDate:  new Date(),
          format: 'dd/MM/yyyy',
          maxKyhoadon: moment(new Date()).subtract(1,'months').startOf('month').toDate()
        },
        fieldsKyHoaDon: {text: 'DANGCHU', value: 'CHUKYNO_VNP'},
      },
      params: {
          p_chk_tu_ngay: '0',
          p_chk_den_ngay: '0',
          p_tu_ngay: new Date(),
          p_den_ngay: new Date(),
          p_tu_kyhoadon_options: [],
          p_tu_kyhoadon: null,
          p_den_kyhoadon_options: [],
          p_den_kyhoadon: null,
          p_ma_tt_vnp: null,
          p_ma_tt: null,
          p_somay: null,
          p_ten_tt: null,
          p_diachi_tt: null,
          p_luot_tt: null,
          p_ma_nv: null,
          p_thungan: null,
          p_so_phieu: null,
          p_tien_huy: null,

      },
      pageInfo : {
        page: 0,
        maxSize: 10,
        totalElement: 0,
      },
      dsPhieuTra: [],
      keyDSPhieuTra: 1,
    }
  },
  methods: {

    getDSChuKyNo () {

      this.params.p_tu_kyhoadon_options = []
      this.params.p_tu_kyhoadon = null
      this.params.p_den_kyhoadon_options = []
      this.params.p_den_kyhoadon = null

      CommonsAPI.getDSChuKyNo(this.axios)
      .then((response) => {
        if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
          {
              let kyHDHienTai = moment(new Date()).subtract(1,'months').startOf('month').format('YYYYMMDD')
              let in_kyhd = parseInt(kyHDHienTai.substring(3, 4)) * 13 + parseInt(kyHDHienTai.substr(0, 2))
              //console.log(in_kyhd)
            this.params.p_tu_kyhoadon_options = [...response.data.data]
            this.params.p_tu_kyhoadon = this.params.p_tu_kyhoadon_options[0].CHUKYNO_VNP
            this.params.p_den_kyhoadon_options = [...response.data.data]
            this.params.p_den_kyhoadon = this.params.p_tu_kyhoadon_options[0].CHUKYNO_VNP
          }
      })
      .catch(error => {})
      .finally(() => {
      })
    },


    tsbnTimKiem_Click()
    {
        this.LayDS_PhieuTra()
    },
    tsbtnXuatExcel_Click()
    {
        if(this.dsPhieuTra.length <= 0)
        {
            this.$toast.error('Không có dữ liệu để xuất excel!')
            return false
        }
        let excelExportProperties = {
            dataSource: this.dsPhieuTra,
            fileName: 'DanhSachPhieuTra.xlsx'
        };
        this.$refs.gridDSPhieuTra.excelExport(excelExportProperties)
    },

    tsbtnXuatFile_Click()
    {

    },
    tsbnXoaTrang_Click()
    {
        this.params.p_diachi_tt = null
        this.params.p_ma_nv = null
        this.params.p_luot_tt = null
        this.params.p_ma_tt = null
        this.params.p_somay = null
        this.params.p_so_phieu = null
        this.params.p_ten_tt = null
        this.params.p_thungan = null
        this.params.p_tien_huy = null
        this.params.p_ma_tt_vnp = null
        this.dsPhieuTra = []
        this.pageInfo.totalElement = 0
        this.pageInfo.page = 0
    },


    btnTraCuuMaTB_Click()
    {
        this.$refs.dlgTraCuuDanhBa.showModal();
    },

    txtMaTT_VNP_KeyPress()
    {
        if (this.params.p_ma_tt_vnp !== null && this.params.p_ma_tt_vnp.trim() !== '')
        {
            this.LayDS_PhieuTra()
        }
        else
        {
            this.$toast.error("Chưa nhập mã thanh toán !")
            this.$refs.ref_ma_tt_vnp.focus()
        }
    },

    txtMaTT_KeyPress()
    {
        if (this.params.p_ma_tt !== null && this.params.p_ma_tt.trim() !== '')
        {
            this.params.p_ma_tt = this.params.p_ma_tt.trim()

            //string chukygoc = "01" + Convert.ToDateTime(kyHDHienTai).ToString("MMyyyy");

            //string chukygoc = a.ToString().Substring(4, 2) + a.ToString().Substring(0, 4);
            this.NAP_DS_THUEBAO_THEOMTT();
            //tsbnTimKiem.PerformClick();
        }
        else
        {
            this.$toast.error("Chưa nhập mã thanh toán !")
            this.$refs.ref_ma_tt.focus()
        }
    },

    txtSoMay_KeyPress()
    {
        if (this.params.p_somay !== null && this.params.p_somay.trim() !== '')
        {
            this.LayDS_PhieuTra()
        }
        else
        {
            this.$toast.error("Chưa nhập số máy !")
            this.$refs.ref_somay.focus()
        }
    },

    LayDS_PhieuTra()
    {
        this.dsPhieuTra = []
        this.pageInfo.totalElement = 0
        this.pageInfo.page = 0

        let postData = {
            "den_kyhoadon": 26288, //this.params.p_den_kyhoadon,
            "denngay": this.params.p_chk_den_ngay === '1' ? moment(this.params.p_den_ngay).format('DD/MM/YYYY') : '',
            "diachi_tt": this.params.p_diachi_tt,
            "luottt": this.params.p_luot_tt,
            "ma_kh": null, //this.params.p_ma_tt,
            "ma_nv": this.params.p_ma_nv,
            "ma_tuyen": this.params.p_thungan,
            "page_num": 1,
            "page_rec": 10,
            "phieus": this.params.p_so_phieu,
            "so_tb": '846927555', //this.params.p_somay === null ? '' : this.params.p_somay,
            "ten_tt": this.params.p_ten_tt,
            "tu_kyhoadon": 26288, //this.params.p_tu_kyhoadon,
            "tungay": this.params.p_chk_tu_ngay === '1' ? moment(this.params.p_tu_ngay).format('DD/MM/YYYY') : '',
        }

        this.loading(true)

        TraCuuPhieuTraAPI.ts_tracuu_ls_phieutra(this.axios, postData)
            .then((res) => {
                if(res.data.error_code === 'BSS-00000000'
                    && res.data.data !== undefined
                    && res.data.data.length > 0
                )
                {
                    this.dsPhieuTra = res.data.data
                    this.pageInfo.totalElement = res.data.data.length
                }
            })
            .catch(error => {
                console.log(error)
            })
            .finally(() => {
                this.loading(false)
            })

    },

    NAP_DS_THUEBAO_THEOMTT()
    {
        let f = this.params.p_tu_kyhoadon_options.filter(x => x.CHUKYNO_VNP === this.params.p_tu_kyhoadon)
        let postData = {
            kyCuoc: f[0].CHUKYNO,
            ma: this.params.p_ma_tt,
            chiTiet: 0
        }
        TraCuuPhieuTraAPI.getMaTTNeoVNP(this.axios, postData)
            .then((res) => {
                if(res.data.error_code === 'BSS-00000000'
                    && res.data.data !== undefined
                    && res.data.data.length > 0)
                    {
                        this.params.p_ma_tt_vnp = res.data.data[0].MA_TT_NEO
                        this.LayDS_PhieuTra()
                    }
            })
            .catch(error => {
                console.log(error)
            })
            .finally(() => {
                this.loading(false)
            })
    },
    async acceptMaTT(args) {
        if (args) {
            this.params.p_ma_tt = args.ma_tt
            this.params.p_ma_tt_vnp = args.ma_kh
        }
    },
    async acceptMaTB(args) {
        if (args) {
            this.params.p_somay = args.ma_tb
        }
    },
  },
  watch: {}
}
</script>
<style>
.grid-status--1:before{
  content:'\e761';
}
.grid-status-0:before{
  content:'\e718';
}
.grid-status--1{
  color: red!important;
  font-weight: bold!important;
}
.grid-status-0{
  color: green!important;
  font-weight: bold!important;
}
</style>
