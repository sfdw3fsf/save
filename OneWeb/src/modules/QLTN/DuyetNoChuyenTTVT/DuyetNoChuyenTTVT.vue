<template src="./DuyetNoChuyenTTVT.html"></template>

<script>
import Vue from 'vue'
import moment from 'moment'
import KyCuoc2 from '@/components/KyCuoc2'
import DuyetNoChuyenTTVTAPI from '../api/DuyetNoChuyenTTVT.js'
import PopupDSLoi from '../components/PopupDSLoi/PopupDSLoi'


import XLSX from 'xlsx'

import { DialogUtility } from '@syncfusion/ej2-popups'
Vue.use(DialogUtility)
let DialogObj = undefined

export default Vue.extend({
  components: {
    appKyCuoc2: KyCuoc2,
    appPopupDSLoi: PopupDSLoi
  },
  computed : {
  },
  created ()
  {

  },
  data ()
  {
    return {
      configs: {
        selectionSettings: {checkboxOnly: true, type: 'Multiple'},
        f_duyetchuyen_visible: true,
        f_duyetchuyen_enable: true
      },
      params: {
        p_DS_NHANPHIEU_THEO_AM: -1,
        p_current_tab: 0, // 0: đang chờ duyệt, 1: đã chuyển TTVT
        p_kyhoadon: null
      },
      customTemplate: function() {
        return  { template : Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="font-weight:bold;margin: 7px 0px 7px 0px;" />`,
            data () { return { data: {data: {}}}; }
          })
        }
      },
      dsPhieuChoPheDuyet: [],
      dsDaChuyenTTVT: [],
      selectedTotalTienNo: 0,
      totalTienNo: 0,
      dsErrors: [],
      count: 0
    }
  },
  methods: {

    customAggTienNo() {
      return currency(this.selectedTotalTienNo) + '/' + currency(this.totalTienNo);
    },

    /* button actions */
    doLoadDS()
    {
      this.HienThiDanhSach()
    },

    doDuyetChuyen()
    {
      if(this.dsPhieuChoPheDuyet.length <= 0)
      {
        this.$toast.error('Không có số liệu trong danh sách duyệt chuyển')
        return false
      }

      let selectedRows = this.$refs.gridPhieuChoPheDuyet.getSelectedRecords()

      if(selectedRows.length <= 0)
      {
        this.$toast.error('Chưa chọn trong danh sách duyệt chuyển')
        return false
      }

      let ktraMaTT = []

      selectedRows.forEach((item) => {
        let solan_nn = parseInt(item.SL)
        if(!isNaN(solan_nn) && solan_nn < 3)
        {
          ktraMaTT.push(item.MA_TT)
        }
      })


      if(ktraMaTT.length >= 0)
      {
        DialogObj = DialogUtility.confirm({
            title: 'Thông báo',
            content: "<div style='padding:10px'>Có Mã TT chưa đủ 3 kết quả nhắc nợ (" + ktraMaTT.join(',') + "). Bạn có muốn tiếp tục?</div>",
            okButton: {  text: 'Đồng ý', click: this.confirmChuyenDuyet },
            cancelButton: {  text: 'Hủy', click: function(){ DialogObj.hide(); return false}},
            showCloseIcon: true,
            closeOnEscape: true,
            animationSettings: { effect: 'Zoom' }
        });
      } else
      {
        this.confirmChuyenDuyet()
      }

    },

    confirmChuyenDuyet()
    {
      if(DialogObj !== undefined) DialogObj.hide()

      let selectedRows = this.$refs.gridPhieuChoPheDuyet.getSelectedRecords()

      let postData = {
        "kyCuoc": this.params.p_kyhoadon,
        "hoaDonId": null,
        "nhanVienId": this.$auth.getNhanVienID(),
        "donViId": this.$auth.getDonViID()
      }

      let promises = []
      this.count = 0

      this.loading(true)

      selectedRows.forEach((item) => {
        postData.hoaDonId = item.HOADON_ID
        promises.push(this.giaophieuXacNhanTTVT(postData).then((res) => {
          if(res.data.error_code === 'BSS-0000000')
          {
            this.count++
          }
        }))
      })

      Promise.all(promises).then(() => {
        this.loading(false)
        console.log(this.count)
        this.$toast.success('Duyệt danh sách nợ chuyển TTVT thành công!')
        // reload lại tab Đã giao TTVT
        this.params.p_current_tab = 1
        this.HienThiDanhSach()
      });

    },

    async giaophieuXacNhanTTVT(postData)
    {
      try
      {
        const res = await DuyetNoChuyenTTVTAPI.giaophieuXacNhanTTVT(this.axios, postData)
        return res
      }catch(error)
      {

      }
    },

    doReadExcel()
    {
      if(this.params.p_current_tab === 0)
      {
        /*
        if(this.dsPhieuChoPheDuyet.length <= 0)
        {
          this.$toast.error('Danh sách phiếu không được để trống!')
          return
        }
        */

        document.getElementById("chuyenduyet_upload_excel").click()
      }
    },

    uploadFileChuyenDuyet(e)
    {


      var files = e.target.files, f = files[0];
      if (!files.length) {
          return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
          return  this.$root.toastWarning("File excel không đúng định dạng!");
      }
      var reader = new FileReader();
      const vm = this
      reader.onload = function(e) {
        var data = new Uint8Array(e.target.result);
        var workbook = XLSX.read(data, {type: 'array'});
        let sheetName = workbook.SheetNames[0]
        /* DO SOMETHING WITH workbook HERE */
        //console.log(workbook);
        let worksheet = workbook.Sheets[sheetName];
        let exceldata = XLSX.utils.sheet_to_json(worksheet)

        console.log(exceldata)

        if(exceldata.length <= 0)
        {
          vm.$toast.error('Không tìm thấy dữ liệu trong file đã upload.')
          document.getElementById('chuyenduyet_upload_excel').value= null;
          return false
        }

        if(!exceldata[0].hasOwnProperty('MA_TT'))
        {
          vm.$toast.error('File gồm có 1 cột: MA_TT')
          document.getElementById('chuyenduyet_upload_excel').value= null;
          return false
        }

        vm.CheckMaTT(exceldata)

      };
      reader.readAsArrayBuffer(f);
      document.getElementById('gannhom_upload_excel').value= null;
    },

    CheckMaTT(ds)
    {
      this.dsErrors = []
      let foundData = []
      let gridObj = this.$refs.gridPhieuChoPheDuyet.$refs.grid.ej2Instances

      gridObj.clearSelection()

      ds.forEach((item) => {
        let index = this.dsPhieuChoPheDuyet.findIndex(x => x.MA_TT == item.MA_TT)

        if(index > 0)
          foundData.push(index)
        else
          this.dsErrors.push({
            MA_TT: item.MA_TT,
            LOI: 'Không có trong danh sách'
          })
      })

      if(this.dsErrors.length > 0)
      {
        // show form lỗi
        this.$refs.refPopupDSLoi.openDialog()
      } else
      {
        gridObj.selectRows(foundData)
      }
    },

    doExportExcel()
    {

    },

    HienThiDanhSach()
    {
      if (this.params.p_current_tab === 0)
      {
        if (this.params.p_DS_NHANPHIEU_THEO_AM === 1)
          this.getDSDuyetXnKhoaMay()
      }
    },

    getDSDuyetXnKhoaMay()
    {
      let postData  = {
        kyCuoc: this.params.p_kyhoadon,
        nhanVienId: this.$auth.getNhanVienID()
      }

      this.loading(true)
      this.dsPhieuChoPheDuyet = []

      DuyetNoChuyenTTVTAPI.getDSDuyetXnKhoaMay(this.axios, postData)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000'
              && res.data.data !== undefined
              && res.data.data.length > 0) {
              this.dsPhieuChoPheDuyet = res.data.data

              this.totalTienNo = this.dsPhieuChoPheDuyet.reduce((a, b) => +a + +b.TIEN, 0)
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false)})
    },

    /* event handler */


    onPhieuChoDuyetRowChanged(args)
    {
      let selectedRows = this.$refs.gridPhieuChoPheDuyet.getSelectedRecords()
      if(selectedRows.length <= 0)
        this.selectedTotalTienNo = 0
      else
      {
        selectedRows.forEach((item) => {
          this.selectedTotalTienNo += item.TIEN
        })
      }
    },


    chukycuocChangeHandler(e)
    {
      if(e.p_chuky_no !== null)
      {
        this.params.p_kyhoadon = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
      }
      else
        this.params.p_kyhoadon = null
    }

  } // end of methods
})

</script>
