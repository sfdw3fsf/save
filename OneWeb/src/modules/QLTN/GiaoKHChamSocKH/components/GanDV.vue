<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='configs.animationSettings'
    ref="dlgGanDV"
    :header='"Cập nhật loại hình thuê bao cho kế hoạch thu cước phát sinh"'
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
              <a href="#" class="pointer" v-on:click.prevent="doAddNew()"  :class="{ disabled: !configs.f_addnew_enabled }">
                  <span class="icon one-save"></span>Tạo mới
              </a>
          </li>
          <li>
              <a href="#" class="pointer" v-on:click.prevent="doSave()" :class="{ disabled: !configs.f_save_enabled }">
                  <span class="icon one-save"></span>Ghi lại
              </a>
          </li>
          <li>
              <a href="#" class="pointer" v-on:click.prevent="doCancel()" :class="{ disabled: !configs.f_cancel_enabled }">
                  <span class="icon one-xlsx-import"></span>Hủy
              </a>
          </li>
          <li>
            <a href="#" class="pointer" v-on:click.prevent="doDelete()" :class="{ disabled: !configs.f_delete_enabled }">
                <span class="icon one-xlsx-import"></span>Xóa
            </a>
          </li>
          <li>
            <a href="javascript: void(0)" @click="closeDialog()">
              <span class="icon one-export"></span> Thoát
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">
            <div class="title pull-left">Thông tin cập nhật</div>
            <div class="clearfix"></div>
          </div>
          <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w90">Dịch vụ</div>
                    <div class="value">
                      <ejs-dropdownlist @change="onChangeDichVu" :filtering='onFilteringDichVu' :allowFiltering="true" :dataSource='params.p_dichvu_options' :fields="configs.fieldsDichVu" v-model = "params.p_dichvu" :placeholder="'Chọn dịch vụ'"></ejs-dropdownlist>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w90">Loại hình TB</div>
                    <div class="value">
                      <ejs-dropdownlist :filtering='onFilteringLoaiHinh' :allowFiltering="true" :dataSource='params.p_loaihinh_options' :fields="configs.fieldsLoaiHinh" v-model = "params.p_loaihinh" :placeholder="'Chọn loại hình'"></ejs-dropdownlist>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="value">
                      <b-form-checkbox
                        v-model="params.p_chk_green_online"
                        class="check"
                        value="1"
                        unchecked-value="0"
                      >
                        <span class="name">Green Online</span>
                      </b-form-checkbox>
                    </div>
                </div>
            </div>

      </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            <div class="title pull-left">Thông tin dịch vụ</div>
            <div class="clearfix"></div>
          </div>
          <DataGrid
            v-bind:columns="[
              {fieldName: 'DICHVUVT_ID', visible: false},
              {fieldName: 'LOAITB_ID', visible: false},
              {fieldName: 'DICHVUGT_ID', visible: false},
              {fieldName: 'TEN_DVVT', headerText: 'Dịch vụ VT', allowFiltering: true, width: 200},
              {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình TB', allowFiltering: true, width: 200}
            ]"
            v-bind:dataSource="dsKeHoach"
            :showFilter="true"
            :allowPaging="false"
            :allowExcelExport="false"
            :showColumnCheckbox="false"
            :enabledSelectFirstRow="false"
            @selectedRowChanged="dsKeHoachRowChanged"
            @dataBound="dataBound"
            @actionComplete="actionComplete"
            ref="grdDSKeHoachTheoDV"
            >
          </DataGrid>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import Vue from 'vue'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import GiaoKHChamSocKHAPI from '../../api/GiaoKHChamSocKH.js'
import CommonsAPI from '../../api/Commons.js'

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
    }
  },
  computed : {
  },
  data () {
    return {
      configs:{
        f_addnew_enabled: true,
        f_save_enabled: true,
        f_delete_enabled: true,
        f_cancel_enabled: true,
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' },
        fieldsDichVu: {text: 'TEN_DVVT', value: 'DICHVUVT_ID' },
        fieldsLoaiHinh: {text: 'TEN_LOAIHINH', value: 'LOAITB_ID' },
      },
      params: {
        p_chk_green_online: '0',
        p_dichvu_options: [],
        p_dichvu: null,
        p_loaihinh: null,
        p_loaihinh_options: [],
        p_prev_loaihinh: null,
        f_auto_focus: false
      },
      dsKeHoach: []
    }
  },
  methods :
  {
    /* Button actions */
    doAddNew()
    {
      this.SetButton(1)
    },
    doSave()
    {

      if(this.params.p_loaihinh === null)
      {
        this.$toast.error('Chưa chọn loại hình thuê bao.')
        return
      }

      let postData = {
        "keHoachId": this.kehoach_id,
        "loaiTBId": this.params.p_loaihinh,
        "kieu": 1,
        "greenOnline": this.params.p_chk_green_online === '1' ? 1 : 0
      }

      this.loading(true)

      GiaoKHChamSocKHAPI.capnhatKTTheoLoaiTB(this.axios, postData)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Cập nhật dữ liệu thành công!')
            this.params.f_auto_focus = true
            this.HienThiDanhSach()

          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    doCancel()
    {
      this.SetButton(0)
    },
    doDelete()
    {
      if(this.params.p_loaihinh === null)
      {
        this.$toast.error('Chưa chọn loại hình thuê bao.')
        return
      }

      DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:10px'>Bạn chắc chắn muốn xóa không?</div>",
          okButton: {  text: 'Đồng ý', click: this.confirmDelete },
          cancelButton: {  text: 'Hủy', click: function(){ DialogObj.hide(); return false}},
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
      });
    },

    confirmDelete()
    {
      DialogObj.hide()

      let postData = {
        "keHoachId": this.kehoach_id,
        "loaiTBId": this.params.p_loaihinh,
        "kieu": 2,  // xóa
        "greenOnline": this.params.p_chk_green_online === '1' ? 420 : null
      }

      this.loading(true)
      GiaoKHChamSocKHAPI.capnhatKTTheoLoaiTB(this.axios, postData)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Xóa dữ liệu thành công!')
            this.HienThiDanhSach()
          }

        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },

    SetButton(kieu)
    {
      this.configs.f_addnew_enabled = false
      this.configs.f_save_enabled = false
      this.configs.f_delete_enabled = false
      this.configs.f_cancel_enabled = false

      if (kieu === -1)//Bat dau
      {
          this.configs.f_save_enabled = true;
          this.configs.f_cancel_enabled = true;
      }

      if (kieu === 0)//Bat dau
      {
          //txtMaNV.Focus();
          this.configs.f_addnew_enabled = true;
          this.Clear();
      }

      if (kieu === 1)//Them moi
      {
          this.configs.f_save_enabled = true;
          this.configs.f_cancel_enabled = true;
          this.Clear();
      }

      if (kieu === 2)//Huy
      {
          this.configs.f_addnew_enabled = true;
          this.configs.f_delete_enabled = true;
          this.Clear();
      }

      if (kieu === 3)//Edit
      {
          //txtTenNV.Focus();
          this.configs.f_addnew_enabled = true;
          this.configs.f_delete_enabled = true;
          this.configs.f_save_enabled = true;
          this.configs.f_cancel_enabled = true;
      }
    },


    Clear()
    {

    },

    HienThiDanhSach()
    {

      this.loading(true)
      this.dsKeHoach = []

      GiaoKHChamSocKHAPI.getKeHoachThuTheoLoaiTB(this.axios, this.kehoach_id)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0
          )
          {
            this.dsKeHoach = res.data.data

          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },

    openDialog()
    {
      this.$refs.dlgGanDV.show()
    },
    beforeDialogOpen()
    {
      if(this.params.p_dichvu_options.length <= 0)
        this.getDSDichVu()

      this.SetButton(-1)
      this.HienThiDanhSach()
    },
    beforeDialogClose()
    {

    },
    closeDialog()
    {
      this.$refs.dlgGanDV.hide()
    },

    getDSDichVu()
    {
      this.params.p_dichvu_options = []
      this.params.p_dichvu = null

      this.loading(true)
      CommonsAPI.getDanhSachDichVuVienThong(this.axios)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0
          )
          {
            this.params.p_dichvu_options = res.data.data
            //this.params.p_dichvu = null
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    getDSLoaiHinhTB(dichvu_id)
    {
      this.loading(true)
      CommonsAPI.getDanhSachLoaiHinhThueBao(this.axios, dichvu_id)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0
          )
          {
            this.params.p_loaihinh_options = res.data.data
            if(this.params.p_prev_loaihinh !== null)
              this.params.p_loaihinh = this.params.p_prev_loaihinh
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },

    findRowIndexByValue(ds, data)
    {
      console.log(ds)
      ds.forEach((item, index) => {
        console.log(index)
        if(item.DICHVUGT_ID === data.DICHVUGT_ID
          && item.DICHVUVT_ID == data.DICHVUVT_ID
          && item.LOAITB_ID == data.LOAITB_ID)
          return index
      })
      return -1
    },

    onFilteringDichVu(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TEN_DVVT', 'contains', e.text, true) : query;
      e.updateData(this.params.p_luot_tt_options, query);
    },
    onFilteringLoaiHinh(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TEN_LOAIHINH', 'contains', e.text, true) : query;
      e.updateData(this.params.p_luot_tt_options, query);
    },

    onChangeDichVu(args)
    {
      this.params.p_loaihinh_options = []
      this.params.p_loaihinh = null

      if(args === null) return
      this.getDSLoaiHinhTB(args.value)
    },

    dsKeHoachRowChanged(args)
    {
      if(args === null) return

      this.params.p_dichvu = args.DICHVUVT_ID
      this.params.p_prev_loaihinh = args.LOAITB_ID
      this.params.p_chk_green_online = (args.DICHVUGT_ID === 420) ? '1' : '0'

      this.SetButton(3)
    },
    actionComplete(args)
    {

    },
    dataBound(args)
    {

    }

  },
  provide: {
  },
  watch: {
  }
})
</script>
