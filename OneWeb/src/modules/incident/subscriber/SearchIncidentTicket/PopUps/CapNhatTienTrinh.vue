<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjCapNhatTienTrinh" :position="position" :close="closeDialog" :header="'Cập nhật tiến trình'" showCloseIcon="true" width="60%" target="#app .main-wrapper">
    <div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" @click.prevent="btnGhiLaiTT_Click"> <span class="icon one-file-attach"></span> Ghi lại </a>
          </li>
           <li>
            <a href="#" @click.prevent="Thoat"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát</a>
          </li>
          <!-- <li>
            <a> <span class="icon one-reload1"></span> Hướng dẫn </a>
          </li> -->
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Nội dung tiến trình sự cố</div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key w140">Nội dung nhắc mẫu</div>
                <div class="value">
                  <Select2 v-model.trim="cboSMSTempId" :options="DmSMSTemp"> </Select2>
                  <!-- <ComboboxGrid :valueField="NoiDung.valueField" :textField="NoiDung.textField"
                                v-model="cboSMSTempId" :value="cboSMSTempId" :columns="NoiDung.headers"
                                :panelDataHeight="NoiDung.panelDataHeight" :enabledSelectFirstRow="true"
                                :dataSource="NoiDung.data">
                            </ComboboxGrid> -->
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <textarea type="text" v-model="txtNoiDung" rows="10" class="form-control" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import moment from 'moment'
import LOAI_DV from '@/modules/contract/profile/CompleteProfileChangeAccount/Enum/LOAI_DV.js'
import api from '../Api.js'
export default {
  components: {
    moment,
    LOAI_DV
  },
  name: 'CapNhatTienTrinh',
  data () {
    return {
      Loading: false,
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      txtNoiDung: '',
      cboSMSTempId: '',
      DmSMSTemp: [],
      DmSMSTemp1: [
        { id: -1, text: '' },
        { id: 1, text: 'KHDN cần sửa trước...' },
        { id: 2, text: 'KH đang chờ CNVT đến sửa chữa, đề nghị đơn vị sửa chữa ngay, liên hệ anh/chị...SDT...' },
        { id: 3, text: 'VNPT HN kính báo: KTV đến sửa chữa dịch vụ... Thuê bao khóa cửa. Vui lòng liên hệ lại KTV………….khi có nhà để được hỗ trợ. Trân trọng!' },
        { id: 4, text: 'VNPT HN kính báo: KTV đã sửa chữa bên ngoài, Thuê bao khóa cửa. Vui lòng kiểm tra lại tín hiệu. LH KTV ...để được hỗ trợ thêm. Trân trọng!' },
        { id: 5, text: 'VNPT HN kính báo: Dịch vụ báo hỏng của TB hiện đang mất tín hiệu do điện lực tiến hành thành thải, chỉnh trang tuyến phố. VNPT HN sẽ cung cấp tín hiệu trở lại ngay sau khi ĐL bàn giao mặt bằng. Vui lòng LH KTV... để biết thêm chi tiết. Trân trọng!' },
        { id: 6, text: 'VNPT HN kính báo: Dịch vụ báo hỏng của TB hiện đang mất tín hiệu do sự cố...VNPT HN đang tiến hành xử lý, sẽ cung cấp tín hiệu sớm nhất, mong Quý KH thông cảm. Vui lòng LH KTV... để được hỗ trợ. Trân trọng!' },
        { id: 7, text: 'Nội dung khác' }
      ],
      NoiDung: {
        data: [],
        textField: 'text',
        valueField: 'id',
        panelDataHeight: 'auto',
        headers: [
          {
            fieldName: 'id',
            headerText: 'ID',
            allowFiltering: true,
            allowSorting: false,
            width:20,
          },
          {
            fieldName: 'text',
            headerText: 'SMS',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        DonViId: '',
        NoiDung: ''
      },
      vtientrinhsc_id: 0,
      vsuco_id: 0,
      vtientrinhbh_id: 0,
      vbaohong_id: 0,
      vnoidung_goc: '',
      vnd_baohong: '',
      vnhom_id: 0,
      tag: ''
    }
  },
  watch: {
    cboSMSTempId: function (val) {
      if (this.vtientrinhbh_id != null) {
        return
      }
      if (val == -1) {
        this.txtNoiDung = this.vnoidung_goc
      } else {
        let temps = this.DmSMSTemp.filter((x) => x.id == val)
        this.txtNoiDung = temps[0].text + ' ' + this.vnd_baohong
      }
    },
    Loading: function (val) {
      this.$root.showLoading(val)
    }
  },
  created: function () {
    let matinh = this.$root.token.getMaTinh()
    this.DmSMSTemp = [
      { id: -1, text: '' },
      { id: 1, text: 'KHDN cần sửa trước...' },
      { id: 2, text: 'KH đang chờ CNVT đến sửa chữa, đề nghị đơn vị sửa chữa ngay, liên hệ anh/chị...SDT...' },
      { id: 3, text: `VNPT ${matinh} kính báo: KTV đến sửa chữa dịch vụ... Thuê bao khóa cửa. Vui lòng liên hệ lại KTV………….khi có nhà để được hỗ trợ. Trân trọng!` },
      { id: 4, text: `VNPT ${matinh} kính báo: KTV đã sửa chữa bên ngoài, Thuê bao khóa cửa. Vui lòng kiểm tra lại tín hiệu. LH KTV ...để được hỗ trợ thêm. Trân trọng!` },
      { id: 5, text: `VNPT ${matinh} kính báo: Dịch vụ báo hỏng của TB hiện đang mất tín hiệu do điện lực tiến hành thành thải, chỉnh trang tuyến phố. VNPT ${matinh} sẽ cung cấp tín hiệu trở lại ngay sau khi ĐL bàn giao mặt bằng. Vui lòng LH KTV... để biết thêm chi tiết. Trân trọng!` },
      { id: 6, text: `VNPT ${matinh} kính báo: Dịch vụ báo hỏng của TB hiện đang mất tín hiệu do sự cố...VNPT HN đang tiến hành xử lý, sẽ cung cấp tín hiệu sớm nhất, mong Quý KH thông cảm. Vui lòng LH KTV... để được hỗ trợ. Trân trọng!` },
      { id: 7, text: 'Nội dung khác' }
    ]
    this.NoiDung.data = this.DmSMSTemp
  },
  methods: {
    fn_ghilai_noidungtt: async function () {
      let tinhId = this.$root.token.getPhanVungID()
      var input = {
        noidung: this.txtNoiDung,
        suco_id: this.vsuco_id,
        tag: this.tag,
        baohong_id: this.vbaohong_id,
        tientrinhbh_id: this.vtientrinhbh_id,
        tientrinhsc_id: this.vtientrinhsc_id,
        tinhthicong_id: tinhId
      }
      try {
        this.Loading = true
        let response = await api.fn_ghilai_noidungtt(this.axios, input)
        console.log('response', response)
        if (response.data.error == 200 || response.data.error == '200') {
          return true
        } else {
          console.log('ShowError', response)
          this.ShowError(response.data.data.error_code)
          return false
        }
      } catch (ex) {
        console.log('err-fn_ghilai_noidungtt:', ex)
        this.ShowError(ex.data.message)
        return false
      } finally {
        this.Loading = false
      }
    },
    btnGhiLaiTT_Click: async function () {
      if (this.txtNoiDung.length <= 0) {
        this.ShowAlert(`Bạn phải nhập nội dung tiến trình !`)
        return
      }
      this.Loading = true
      try {
        if (await this.fn_ghilai_noidungtt()) {
          this.ShowSuccess('Cập nhật tiến trình thành công !')
          this.$refs.dialogObjCapNhatTienTrinh.hide()
        }
        this.Loading = false
      } catch (ex) {
        this.Loading = false
        this.ShowAlert(ex)
      }
    },
    openDialog: function (p_tag, p_vtientrinhbh_id, p_vnhom_id, p_vbaohong_id, p_vnoidung_goc, p_vnd_baohong) {
      // console.log('openDialog')
      this.vnoidung_goc = p_vnoidung_goc
      this.vnd_baohong = p_vnd_baohong
      this.vtientrinhbh_id = p_vtientrinhbh_id
      this.vbaohong_id = p_vbaohong_id
      this.vnhom_id = p_vnhom_id
      this.tag = p_tag
      this.$refs.dialogObjCapNhatTienTrinh.show()
    },
    Thoat: function () {
        this.$refs.dialogObjCapNhatTienTrinh.hide()
    },
    closeDialog: function () {
      this.$emit('closeCapNhatTienTrinh')
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
</style>
