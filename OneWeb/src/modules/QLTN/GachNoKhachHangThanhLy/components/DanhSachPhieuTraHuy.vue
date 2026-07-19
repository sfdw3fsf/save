<template src="./DanhSachPhieuTraHuy.html"></template>
<script>
import Vue from "vue"
import { mapActions, mapState } from "vuex"
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import GachNoKhachHangThanhLy from "../../api/GachNoKhachHangThanhLy"
import GachNoAPI from '../../api/GachNoAPI'
import moment from 'moment'
import { DialogUtility } from '@syncfusion/ej2-popups'
import {previewPrint} from "@/utils/util"

Vue.use(DialogPlugin)
Vue.use(DialogUtility)
let DialogObj = undefined

 export default Vue.extend({
    components : {
    },
    props: {
    },
    computed : {
      ...mapState("qltnCommon", [
        "kyHoaDonHienTai"
      ]),
      p_ngay_ttoan : {
        get() {
          return this.params.p_ngay_ttoan
        },
        set(value) {
          return this.params.p_ngay_ttoan = value
        }
      }
    },
    async created () {
      await this.getKyHoaDonHienTai()
      if (this.kyHoaDonHienTai !== undefined) {
        this.params.p_kyhoadon = this.kyHoaDonHienTai.KYCUOC
      } else
      {
        this.params.p_kyhoadon = moment(new Date()).subtract(1,'months').startOf('month').format('YYYYMMDD')
      }
    },
    destroyed () {
    },
    data () {
      return {
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' },
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        },
        params: {
          p_ngay_ttoan: new Date(),
          p_kyhoadon: null
        },
        dsPhieuTra: [],
        pageinfoPhieuTra: {
          page: 0,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          sort: null,
          propertiesSort: null,
          currentRow: 0
        },
        dsPhieuTheoThuNgan: [],
        pageinfoThuNgan: {
          page: 0,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          sort: null,
          propertiesSort: null,
          currentRow: 0
        },
        //totalItemsThuNgan: 0,
        dsPhieuHuy: [],
        pageinfoPhieuHuy: {
          page: 0,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          sort: null,
          propertiesSort: null,
          currentRow: 0
        },
        keyPhieuTra: 0,
        keyPhieuTheoThuNgan: 0,
        keyPhieuHuy: 0,
        //totalItemsPhieuHuy: 0
      }
    },
    methods : {
      ...mapActions("qltnCommon", [
          "getKyHoaDonHienTai",
      ]),
      openDialog() {

        this.$refs.dlgDSPhieuTraHuy.show()
      },
      closeDialog() {
        this.clearResult()
        this.clearForm()
        this.$refs.dlgDSPhieuTraHuy.hide()
      },
      onDialogClose() {
        this.clearForm()
        this.clearResult()
      },
      onDialogOpen() {
        this.doSearch()
      },
      clearForm() {

      },
      clearResult() {
        this.dsPhieuTra = []
        this.pageinfoPhieuTra.page = 0
        this.pageinfoPhieuTra.totalElement = 0
        this.dsPhieuTheoThuNgan = []
        this.pageinfoThuNgan.page = 0
        this.pageinfoThuNgan.totalElement = 0
        this.dsPhieuHuy = []
        this.pageinfoPhieuHuy.page = 0
        this.pageinfoPhieuHuy.totalElement = 0
      },
      async doSearch(){

        this.loading(true)


        await Promise.all[
          this.getPhieuTra(0,0),
          this.getPhieuHuy(0,0),
          this.getPhieuTraTheoThuNgan(0,0)
        ]

        /*

        let res1 = await this.getPhieuTra(0,0)

        if(res1 !== null
          && res1.data.error_code === 'BSS-00000000'
          && res1.data.data !== undefined)
          {
            this.keyPhieuTra++
            this.dsPhieuTra = res1.data.data
            this.pageinfoPhieuTra.page = 0
            this.pageinfoPhieuTra.totalElement = res1.data.data.length
          }

        let res2 = await this.getPhieuHuy(0,0)
        if(res2 !== null
          && res2.data.error_code === 'BSS-00000000'
          && res2.data.data !== undefined)
          {
            this.keyPhieuHuy++
            this.dsPhieuHuy = res2.data.data
            this.pageinfoPhieuHuy.page = 0
            this.pageinfoPhieuHuy.totalElement = res2.data.data.length
          }

        let res3 = await this.getPhieuTraTheoThuNgan(0,0)
        if(res3 !== null
          && res3.data.error_code === 'BSS-00000000'
          && res3.data.data !== undefined)
          {
            this.keyPhieuTheoThuNgan++
            this.dsPhieuTheoThuNgan = res3.data.data
            this.pageinfoThuNgan.page = 0
            this.pageinfoThuNgan.totalElement = res3.data.data.length
          }
        */

        this.loading(false)

      },
      async getPhieuTra(pageSize, pageNo){
        try{
          const results = await GachNoKhachHangThanhLy.getPhieuTra(this.axios, { ngayTT : moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY') })
          const data = await results.data
          if(data.error_code === 'BSS-00000000' && data.data !== undefined)
          {
            this.keyPhieuTra++
            this.dsPhieuTra = data.data
            this.pageinfoPhieuTra.page = 0
            this.pageinfoPhieuTra.totalElement = data.data.length
          }
          
        } catch(error)
        {
          if(error.data !== undefined && error.data.message_detail !== undefined)
            this.$toast.error(error.data.message_detail)
          else
            this.$toast.error(error)
        }

      },
      async getPhieuTraTheoThuNgan(pageSize, pageNo){
        try{
          const results = await GachNoKhachHangThanhLy.getPhieuTraTheoThuNgan(this.axios, { ngayTT : moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY') })
          const data = await results.data
          if(data.error_code === 'BSS-00000000' && data.data !== undefined)
          {
            this.keyPhieuTheoThuNgan++
            this.dsPhieuTheoThuNgan = data.data
            this.pageinfoThuNgan.page = 0
            this.pageinfoThuNgan.totalElement = data.data.length
          }
        } catch(error)
        {
          if(error.data !== undefined && error.data.message_detail !== undefined)
            this.$toast.error(error.data.message_detail)
          else
            this.$toast.error(error)
        }

      },
      async getPhieuHuy(pageSize, pageNo){
        try{
          const results = await GachNoKhachHangThanhLy.getPhieuHuy(this.axios, { ngayTT : moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY') })
          const data = await results.data
          if(data.error_code === 'BSS-00000000' && data.data !== undefined)
          {
            this.keyPhieuHuy++
            this.dsPhieuHuy = data.data
            this.pageinfoPhieuHuy.page = 0
            this.pageinfoPhieuHuy.totalElement = data.data.length
          }
        } catch(error)
        {
          console.log(error)
          if(error.data !== undefined && error.data.message_detail !== undefined)
            this.$toast.error(error.data.message_detail)
          else
            this.$toast.error(error)
        }

      },

      doPrint()
      {
        this.selectedRows = this.$refs.refPhieuTra.getSelectedRecords()


        if(this.selectedRows.length <= 0)
        {
          this.$toast.error('Chưa chọn phiếu để in.')
          return false
        }
        /*
        let account = this.$auth.getUserName()

        this.selectedRows.forEach((item) => {
          if(item.NGUOI_CN.toUpperCase() !== account.toUpperCase())
          {
            this.$toast.error('Bạn không in phiếu thanh toán của user ' + item.NGUOI_CN)
            return false
          }
        })
        */

        //this.$toast.error('Chức năng In đang được phát triển. Vui lòng thử lại sau.')
        //this.$refs.refPopupInPhieuThu.openDialog()
        let dsPhieuCanIn = []
        //this.dsPhieuCanIn.push(this.phieuSelected[0].PHIEU_ID)
        this.selectedRows.forEach((item) => {
          dsPhieuCanIn.push(item.PHIEU_ID)
        })

        //this.$refs.refPrintPreview.openDialog();
        let postData = {
          kyCuoc: this.params.p_kyhoadon ,
          dsPhieu : dsPhieuCanIn
        }
        this.loading(true)
        try{
          const baseUrl = process.env.API;
          this.axios.post(baseUrl + "/web-thuno/api/thu-no/gach-no/in-phieu-thu-thanh-ly", postData, {responseType: 'blob'})
          .then( (response) => {
            previewPrint(response.data)
          })
          .catch(error => {
            console.log(error)
            this.$toast.error('Đã phát sinh lỗi khi in.')
          })
        }catch(err)
        {
          //console.log(err)
          this.$toast.error(err.data.message_detail)
        }finally{
          this.loading(false)
        }
      },
      doDelete(){
        let selected = this.$refs.refPhieuTra.getSelectedRecords()

        if(selected.length <= 0)
        {
          this.$toast.error('Chọn phiếu cần xóa!')
          return
        }

        DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:10px'>Bạn có chắc chắn hủy phiếu này ?</div>",
          okButton: {  text: 'Đồng ý', click: this.confirmDelete},
          cancelButton: {  text: 'Hủy'},
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
        });


      },

      confirmDelete()
      {
        DialogObj.hide()
        this.loading(true)
        let selected = this.$refs.refPhieuTra.getSelectedRecords()
        GachNoKhachHangThanhLy.deletePhieuTra(this.axios, selected[0].PHIEU_ID)
          .then( (res) => {
            if(res.data.error_code === 'BSS-00000000')
            {
              this.$toast.success('Đã xóa phiếu thành công.')
              this.doSearch()
            }
          })
          .catch(error => { this.$toast.error(error.dada.message_detail)})
          .finally( () => { this.loading(false)})
      },
      async acceptPrintPhieuThuHandler(e){
        //let phieu_id = this.selectedRow.PHIEU_ID
        let selectedRows = this.$refs.refPhieuTra.getSelectedRecords()
        let phieuCount = 0
        let seri = parseInt(e.so_seri)

        if(e.is_print === 1)
        {
          let dsPhieuCanIn = []
          //this.dsPhieuCanIn.push(this.phieuSelected[0].PHIEU_ID)
          selectedRows.forEach((item) => {
            dsPhieuCanIn.push(item.PHIEU_ID)
          })

          //this.$refs.refPrintPreview.openDialog();
          let postData = {
            kyCuoc: this.params.p_kyhoadon ,
            dsPhieu : dsPhieuCanIn
          }
          this.loading(true)
          try{
            const baseUrl = process.env.API;
            this.axios.post(baseUrl + "/web-thuno/api/thu-no/gach-no/bien-nhan", postData, {responseType: 'blob'})
            .then( (response) => {
              previewPrint(response.data)
            })
          }catch(err)
          {
            this.$toast.error(err.data.message_detail)
          }finally{
            this.loading(false)
          }
        }
        else
        {
          let apiBody = {
            "pKyCuoc": this.params.p_kyhoadon ,
            "pSeri": e.seri,
            "pQuyen": e.thung,
            "pSoSeri": 0,
            "pPhieuId": 0
          };

          for (let item of selectedRows)
          {
            apiBody.pPhieuId = item.PHIEU_ID
            apiBody.pSoSeri = seri + phieuCount

            let result = await this.updateSeriPhieuThu(apiBody);
            if (result) {
              phieuCount ++;
            }
            else {
              this.$toast.error('Không thể cập nhật phiếu thanh toán có mã TT:' + item.MA_TT);
            }
          };

          if (phieuCount === selectedRows.length) {
            this.$toast.success('Cập nhật phiếu thanh toán thành công!');
          };
          await this.getDanhSach();
        }
      },
      async updateSeriPhieuThu(apiBody) {
        this.loading(true)
        try {
          let response = await GachNoAPI.updateSeriThanhToan(this.axios, apiBody);
          return response.data.error_code === 'BSS-00000000' ? true : false;
        }
        catch (error) {
          this.$toast.error(error.data.message_detail)
          return false;
        }
        finally {
          this.loading(false)
        }
      },
    },
    watch : {

    }
  })
</script>
