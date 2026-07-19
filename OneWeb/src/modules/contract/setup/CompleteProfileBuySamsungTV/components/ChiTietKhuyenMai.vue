<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjKhuyenMai" :position="position" :close="closeDialogKM" :header="'Danh sách khuyến mại, đặt cọc'" showCloseIcon="true" width="80%" target="#app .main-wrapper"> 
      <div>
           <DataGrid v-bind:columns="DsKm.headers" v-bind:dataSource="DsKm.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="false" :showFilter="true" :enabledSelectFirstRow="false">
              </DataGrid>
      </div>
  </ejs-dialog>
</template>
<script>
import api2 from '@/modules/contract/setup/CompleteProfileBuySamsungTV/components/PopUpApi'
export default {
  components: {
    api2
  },
  name: 'PopUpKM',
  data() {
    return {
      Loading: false,
      dateBEFormat: 'YYYY-MM-DD HH:mm:ss',
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      DsKm:{
           headers: [
          {
            fieldName: 'loai_km',
            headerText: 'Loại KM',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'left',
            isGroupedColumn: true
          },
          {
            fieldName: 'tenchitiet_km',
            headerText: 'Chi tiết KM',
            allowFiltering: true,
            allowSorting: false,
            width: 90,
            textAlign: 'left'
          },
          {
            fieldName: 'ngay_dkdc',
            headerText: 'Ngày ĐK',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'thang_bd',
            headerText: 'Tháng BĐ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'thang_kt',
            headerText: 'Tháng KT',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'camket',
            headerText: 'Cam kết (tháng)',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'thang_bddc',
            headerText: 'Tháng BĐĐC',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'thang_ktdc',
            headerText: 'Tháng KTĐC',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'datcoc_csd',
            headerText: 'Tiền đặt cọc',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'tyle_sd',
            headerText: 'Tỷ lệ SD',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'tien_sd',
            headerText: 'Tiền SD',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'cuoc_sd',
            headerText: 'Giảm cước SD',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'tyle_tb',
            headerText: 'Tỷ lệ TB',
            allowFiltering: true,
            allowSorting: false
          },
           {
            fieldName: 'tien_tb',
            headerText: 'Tiền TB',
            allowFiltering: true,
            allowSorting: false
          },
           {
            fieldName: 'cuoc_tb',
            headerText: 'Giảm cước TB',
            allowFiltering: true,
            allowSorting: false
          },
           {
            fieldName: 'noidung',
            headerText: 'Nội dung',
            allowFiltering: true,
            allowSorting: false
          },
           {
            fieldName: 'thang_thoaitra',
            headerText: 'Tháng thoái trả',
            allowFiltering: true,
            allowSorting: false
          },
           {
            fieldName: 'thang_huy',
            headerText: 'Tháng hủy',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data:[]
      }
    }
  },
  methods: {
    openDialog(thuebaoId, kieu) {
      Promise.all([
          this.lay_ds_chitiet_km_by_id(thuebaoId,kieu)
      ])
      .catch((error) => {
          console.log(`Error in promises ${error}`)
        })
        .finally((e) => {
          this.Loading = false
          this.$refs.dialogObjKhuyenMai.show()
        })
     
    },
    lay_ds_chitiet_km_by_id: async function (thuebaoId, kieu) {
      var input = { thuebaoId: thuebaoId, kieu: kieu }
      let data = this.GetData(await api2.lay_ds_chitiet_km_by_id(this.axios, input))
      this.DsKm.data=data
      return data
    },
    closeDialogKM: function () {
      this.$emit('close',0)
      this.$emit('closeKM', 0)
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  }
}
</script>
<style scoped>
.e-grid .e-gridheader {
  position: -webkit-sticky;
  position: sticky;
  top: 0px; /* The height of top nav menu. */
  z-index: 1;
}

</style>