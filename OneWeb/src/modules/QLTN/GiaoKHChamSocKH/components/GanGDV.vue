<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='configs.animationSettings'
    ref="dlgGanGDV"
    :header='"Cập nhật nhân viên thu cước phát sinh"'
    showCloseIcon=true
    width='1200px'
    height='700'
    :target='configs.target'
    :close="beforeDialogClose"
    :open="beforeDialogOpen"
    >
    <div class="">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" v-on:click.prevent="doUpdate()"><span class="icon one-search"></span>Cập nhật</a>
          </li>
          <li>
            <a href="javascript: void(0)" @click="closeDialog()">
              <span class="icon one-export"></span> Thoát
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-move-select-table">
          <div class="box-col box-form">
            <div class="legend-title">GDV đã gán</div>
            <DataGrid
              v-bind:columns="[
                {fieldName: 'NHANVIEN_ID', visible: false},
                {fieldName: 'TEN_NV', headerText: 'Tên NV', allowFiltering: true, width: 120},
                {fieldName: 'SO_DT', headerText: 'Số ĐT', allowFiltering: true, width: 100},
                {fieldName: 'TEN_DV', headerText: 'Đơn vị', allowFiltering: true, width: 200}
              ]"
              v-bind:dataSource="dsGDVDaGan"
              :showFilter="true"
              :allowPaging="false"
              :allowExcelExport="false"
              :showColumnCheckbox="true"
              :enabledSelectFirstRow="false"
              ref="grdDSGDVDaGan"
              >
            </DataGrid>
          </div>
          <div class="actions">
            <button class="btn" @click="unmove()">
              <span class="fa fa-angle-right"></span>
            </button>
            <button class="btn" @click="unmoveAll()">
              <span class="fa fa-angle-double-right"></span>
            </button>
            <button class="btn" @click="move()">
              <span class="fa fa-angle-left"></span>
            </button>
            <button class="btn" @click="moveAll()">
              <span class="fa fa-angle-double-left"></span>
            </button>
          </div>
          <div class="box-col box-form">
            <div class="legend-title">GDV chưa gán</div>
            <DataGrid
              v-bind:columns="[
                {fieldName: 'NHANVIEN_ID', visible: false},
                {fieldName: 'TEN_NV', headerText: 'Tên NV', allowFiltering: true, width: 120},
                {fieldName: 'SO_DT', headerText: 'Số ĐT', allowFiltering: true, width: 100},
                {fieldName: 'TEN_DV', headerText: 'Đơn vị', allowFiltering: true, width: 200}
              ]"
              v-bind:dataSource="dsGDVChuaGan"
              :showFilter="true"
              :allowPaging="false"
              :allowExcelExport="false"
              :showColumnCheckbox="true"
              :enabledSelectFirstRow="false"
              ref="grdDSGDVChuaGan"
              >
            </DataGrid>
          </div>
        </div>

      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import Vue from 'vue'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import GiaoKHChamSocKHAPI from '../../api/GiaoKHChamSocKH.js'

Vue.use(DialogPlugin)

import { DialogUtility } from '@syncfusion/ej2-popups'
Vue.use(DialogUtility)
let DialogObj = undefined

export default Vue.extend({
  components : { },
  props: {
    kehoach_id: {
      type: Number,
      required: false
    },
    soluong_gdv: {
      type: Number,
      required: false
    },
  },
  computed : {
  },
  data () {
    return {
      configs:{
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' },
      },
      dsGDVChuaGan: [],
      dsGDVDaGan: [],
    }
  },
  methods :
  {
    /* Button actions */
    move()
    {
      if(this.dsGDVChuaGan.length <= 0)
      {
        this.$toast.error('Không có nhân viên để gán')
        return
      }

      let selectedRows = this.$refs.grdDSGDVChuaGan.getSelectedRecords()
      if(selectedRows.length <= 0)
      {
        this.$toast.error('Chưa chọn nhân viên để gán')
        return
      }

      let arrChuaGan = []

      this.dsGDVChuaGan.forEach((item) => {
        let found = selectedRows.filter(x => x.NHANVIEN_ID === item.NHANVIEN_ID)
        if(found.length <= 0)
          arrChuaGan.push(item)
        else
          this.dsGDVDaGan.push(item)
      })


      this.dsGDVChuaGan = [...arrChuaGan]
      this.dsGDVDaGan = [...this.dsGDVDaGan]
    },

    moveAll()
    {
      if(this.dsGDVChuaGan.length <= 0)
      {
        this.$toast.error('Không có nhân viên để gán')
        return
      }

      let arrChuaGan = []

      this.dsGDVChuaGan.forEach((item) => {
        this.dsGDVDaGan.push(item)
      })

      this.dsGDVChuaGan = []
      this.dsGDVDaGan = [...this.dsGDVDaGan]
    },

    unmove()
    {
      if(this.dsGDVDaGan.length <= 0)
      {
        this.$toast.error('Không có nhân viên để bỏ gán')
        return
      }

      let selectedRows = this.$refs.grdDSGDVDaGan.getSelectedRecords()
      if(selectedRows.length <= 0)
      {
        this.$toast.error('Chưa chọn nhân viên để bỏ gán')
        return
      }

      let arrDaGan = []

      this.dsGDVDaGan.forEach((item) => {
        let found = selectedRows.filter(x => x.NHANVIEN_ID === item.NHANVIEN_ID)
        if(found.length <= 0)
          arrDaGan.push(item)
        else
          this.dsGDVChuaGan.push(item)
      })

      this.dsGDVDaGan = [...arrDaGan]
      this.dsGDVChuaGan = [...this.dsGDVChuaGan]
    },

    unmoveAll()
    {
      if(this.dsGDVDaGan.length <= 0)
      {
        this.$toast.error('Không có nhân viên để bỏ gán')
        return
      }

      let arrDaGan = []

      this.dsGDVDaGan.forEach((item) => {
        this.dsGDVChuaGan.push(item)
      })

      this.dsGDVDaGan = []
      this.dsGDVChuaGan = [...this.dsGDVChuaGan]
    },

    doUpdate()
    {
      /*
      if(this.dsGDVDaGan.length <= 0)
      {
        this.$toast.error('Chưa có nhân viên nào được gán.')
        return false
      }
      */

      if(this.dsGDVDaGan.length < this.soluong_gdv)
      {
        DialogObj = DialogUtility.confirm({
            title: 'Thông báo',
            content: "<div style='padding:10px'>Số lượng nhân viên được gán ít hơn số lượng đã khai báo (SL khai báo: " + this.soluong_gdv + " nhân viên). Bạn có muốn tiếp tục?</div>",
            okButton: {  text: 'Đồng ý', click: this.confirmSave },
            cancelButton: {  text: 'Hủy', click: function(){ DialogObj.hide(); return false}},
            showCloseIcon: true,
            closeOnEscape: true,
            animationSettings: { effect: 'Zoom' }
        });
      } else
      {
        this.confirmSave()
      }
    },


    confirmSave()
    {
      if(DialogObj !== undefined)
        DialogObj.hide()

      let ds = []

      if(this.dsGDVDaGan.length > 0)
        ds = this.dsGDVDaGan.map(x => x.NHANVIEN_ID)

      let postData = {
        "dsNhanVienId": ds,
        "keHoachId": this.kehoach_id
      }


      this.loading(true)

      GiaoKHChamSocKHAPI.capnhatKeHoachGiaoGDV(this.axios, postData)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            this.$toast.success(res.data.data)
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {this.loading(false)})

    },

    openDialog()
    {
      this.$refs.dlgGanGDV.show()
    },
    beforeDialogOpen()
    {
      this.getDSGiaoDichVien(1)
      this.getDSGiaoDichVien(0)
    },
    beforeDialogClose()
    {

    },
    closeDialog()
    {
      this.$refs.dlgGanGDV.hide()
    },

    getDSGiaoDichVien(kieu)
    {
      let postData = {
        donViId: this.$auth.getDonViID(),
        kieu: kieu  /* 1: đã gán, 0: chưa gán */
      }

      if(kieu === 1)
        this.dsGDVDaGan = []
      else
        this.dsGDVChuaGan = []

      this.loading(true)
      GiaoKHChamSocKHAPI.getDSGiaoDichVien(this.axios, postData)
        .then((res) => {

          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0
          )
          {
            if(kieu === 1)
            {
              this.dsGDVDaGan = res.data.data
            } else
            {
              this.dsGDVChuaGan = res.data.data
            }

          }

        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    }

  },
  provide: {
  },
  watch: {
  }
})
</script>
