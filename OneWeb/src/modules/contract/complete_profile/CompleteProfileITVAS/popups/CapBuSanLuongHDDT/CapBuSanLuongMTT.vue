<template>
  <ejs-dialog :enableResize="true" :isModal="true" allowDragging="true" zIndex="1000" :width="width" :visible="false" ref="dialogCapBuSanLuong" header="Cấp bù/Cắt giảm sản lượng dịch vụ hóa đơn điện tử máy tính tiền" showCloseIcon="true" target="#app .main-wrapper">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="btnCapNhat_Click"> <span class="icon one-file-plus"></span> Cập Nhật </a>
        </li>
        <li>
          <a href="#" :class="{ disabled: !data_common.btnKichHoat }" @click.prevent="btnKichHoat_Click"> <span class="icon one-calendar-sync"></span> kích hoạt </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="row">
        <div class="box-form">
          <div class="legend-title">Thông tin đăng ký</div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w120">Thao tác (*)</div>
                <div class="value">
                  <ComboboxGrid
                    class="select-custom"
                    :columns="[
                      { fieldName: 'id', headerText: 'ID', allowFiltering: true, width: '100' },

                      { fieldName: 'ten', headerText: 'Thông tin', allowFiltering: true, width: '300' }
                    ]"
                    :dataSource="data_common.thaotac"
                    textField="ten"
                    valueField="id"
                    :value="data_common.thaotac_id"
                    v-model="data_common.thaotac_id"
                    :enabledSelectFirstRow="false"
                    @selectedChanged="comboxChange"
                  >
                  </ComboboxGrid>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w90">{{ data_common.thaotac_id === 1 ? 'Số lượng cấp bù (*)' : data_common.thaotac_id === 2 ? 'Số lượng cắt giảm (*)' : 'Số lượng (*)' }}</div>
                <div class="value">
                  <input type="text" v-model="data_common.so_luong" class="form-control" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w90">Số EOffice (*)</div>
                <div class="value">
                  <input type="text" v-model="data_common.so_eoffice" class="form-control" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w90">{{ data_common.thaotac_id === 1 ? 'Tổng SL sau cấp bù (*)' : data_common.thaotac_id === 2 ? 'Tổng SL sau cắt giảm (*)' : 'Tổng (*)' }}</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="tong_hd" disabled />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="popup-body">
      <div class="row">
        <div class="box-form">
          <div class="legend-title">Thông tin khách hàng: Số liệu được lấy trực tiếp từ hệ thống hóa đơn điện tử</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">MST</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="data_common.kq_tc_hddtn.taxcode" disabled />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w90">Email</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="data_common.kq_tc_hddtn.email" disabled />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">Số điện thoại</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="data_common.kq_tc_hddtn.tel" disabled />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w90">Id hệ thống</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="data_common.kq_tc_hddtn_convert.id_hethong_tt78" disabled />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">SLHĐ đã đăng ký</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="data_common.kq_tc_hddtn_convert.sl_hd_dadk" disabled />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w90">SLHĐ đã SD</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="data_common.kq_tc_hddtn_convert.sl_hd_dasd" disabled />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">Tên miền</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="data_common.kq_tc_hddtn_convert.domain" disabled />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w90">Địa chỉ</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="data_common.kq_tc_hddtn.address" disabled />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import { columnSelectionBegin } from '@syncfusion/ej2-vue-grids/index.js'
import api from './api.js'
export default {
  data() {
    return {
      width: '75%',
      openDialog_data: {},
      data_common: {
        btnKichHoat: false,
        status: 0,
        kq_tc_hddtn: {},
        thaotac: [
          {
            id: 1,
            ten: 'Cấp bù số lượng hóa đơn'
          },
          {
            id: 2,
            ten: 'Cắt giảm số lượng hóa đơn'
          }
        ],
        thaotac_selected: {},
        thaotac_id: 1,
        so_luong: 0,
        so_luong_dk: 0,
        tong: 0,
        so_eoffice: '',
        kq_tc_hddtn_convert: {
          sl_hd_dasd: '',
          sl_hd_dadk: '',
          id_hethong_tt78: '',
          ten_cty_viettat: '',
          domain: '',
          sl_hd_truoc_20062023: 0,
          sl_hd_sau_20062023: 0
        }
      }
    }
  },
  computed: {
    tong_hd() {
      if (this.data_common.thaotac_id == 1) return this.data_common.so_luong * 1 + this.data_common.tong * 1
      return this.data_common.tong * 1 - this.data_common.so_luong * 1
    }
  },
  methods: {
    ResetForm() {
      try {
        this.data_common.status = 0
        this.data_common.kq_tc_hddtn = {}
        this.data_common.thaotac_id = 1
        this.data_common.so_luong = 0
        this.data_common.so_luong_dk = 0
        this.data_common.tong = 0
        this.data_common.so_eoffice = ''
        this.data_common.thaotac_selected = {}
        this.data_common.kq_tc_hddtn_convert.sl_hd_dasd = ''
        this.data_common.kq_tc_hddtn_convert.sl_hd_dadk = ''
        this.data_common.kq_tc_hddtn_convert.id_hethong_tt78 = ''
        this.data_common.kq_tc_hddtn_convert.ten_cty_viettat = ''
        this.data_common.kq_tc_hddtn_convert.domain = ''
        this.data_common.kq_tc_hddtn_convert.sl_hd_truoc_20062023 = ''
        this.data_common.kq_tc_hddtn_convert.sl_hd_sau_20062023 = ''
      } catch (err) {
      } finally {
      }
    },
    async openDialog(data) {
      this.openDialog_data = data
      this.$refs['dialogCapBuSanLuong'].show()
      await this.pageLoad()
    },
    // Hàm tạo độ trễ
    delay(ms) {
      return new Promise((resolve) => setTimeout(resolve, ms))
    },
    async pageLoad() {
      try {
        this.loading(true)
        this.ResetForm()
        await this.Lay_TrangThai_kichHoat()
        await this.TraCuu_HDDT_Theo_MST(this.openDialog_data.hdtb_id)
        await this.layThongTinDaDangKy()
      } catch (err) {
        console.log(err)
      } finally {
        console.log('============== ' + this.data_common.thaotac_id)
        this.loading(false)
      }
    },

    async Lay_TrangThai_kichHoat() {
      try {
        let response = await api.fn_get_status_hdtb(this.axios, { vhdtb_id: this.openDialog_data.hdtb_id })
        console.log('Lay_TrangThai_kichHoat =>>>> response')
        console.log(response)
        if (response.data.error == 200 || response.data.errorCode === 0) {
          this.data_common.status = response.data.data
        }
      } catch (err) {
        console.log(err)
      } finally {
      }
    },
    async TraCuu_HDDT_Theo_MST() {
      try {
        this.loading(true)
        const get_ketqua_dynamic = await api.call_dynamic_api(this.axios, { hdtb_id: this.openDialog_data.hdtb_id, vhdtb_id: this.openDialog_data.hdtb_id, vdem: 0, vtype: 'TRACUU_HOADON_DIENTU' })
        this.data_common.kq_tc_hddtn = get_ketqua_dynamic.data.data.data
        this.TongSL_HD_DADK()
        this.TongSL_HD_DASD()
        this.LAY_ID_HETHONG()
        this.LAY_TEN_CT_VIETTAT()
        this.LAY_DOMAIN()
        this.SLHD_Truoc_20062023()
        this.SLHD_Sau_20062023()
      } catch (err) {
        console.log(err)
      } finally {
        this.loading(false)
      }
    },
    TongSL_HD_DADK() {
      const filteredServices = this.data_common.kq_tc_hddtn.invoices_services.filter((service) => service.service_type == 2)
      const total = filteredServices.reduce((acc, service) => acc + service.totalNumber, 0)
      console.log('Total : ' + total)
      this.data_common.kq_tc_hddtn_convert.sl_hd_dadk = total
      this.data_common.tong = total
    },
    TongSL_HD_DASD() {
      const filteredServices = this.data_common.kq_tc_hddtn.invoices_services.filter((service) => service.service_type == 2)
      const total = filteredServices.reduce((acc, service) => acc + service.totalPublished, 0)
      this.data_common.kq_tc_hddtn_convert.sl_hd_dasd = total
    },
    SLHD_Truoc_20062023() {
      const filteredServices = this.data_common.kq_tc_hddtn.invoices_services.filter((service) => service.service_type == 2)
      const total = filteredServices.reduce((acc, service) => acc + service.InvBeforeJun20, 0)
      this.data_common.kq_tc_hddtn_convert.sl_hd_truoc_20062023 = total
    },
    SLHD_Sau_20062023() {
      this.data_common.kq_tc_hddtn_convert.sl_hd_sau_20062023 = this.data_common.kq_tc_hddtn_convert.sl_hd_dadk * 1 - this.data_common.kq_tc_hddtn_convert.sl_hd_truoc_20062023 * 1
    },

    LAY_ID_HETHONG() {
      const version = this.data_common.kq_tc_hddtn.Versions.find((version) => version.type === 'Hệ thống HDDT TT78')
      console.log(version)
      this.data_common.kq_tc_hddtn_convert.id_hethong_tt78 = version ? version.cus_id : null
      console.log(version ? version.cus_id : null)
      console.log(this.data_common.kq_tc_hddtn_convert.id_hethong_tt78)
    },
    LAY_TEN_CT_VIETTAT() {
      const data = this.data_common.kq_tc_hddtn.Versions.find((version) => version.type === 'Hệ thống HDDT TT78')
      this.data_common.kq_tc_hddtn_convert.ten_cty_viettat = data ? data.short_name : null
    },
    LAY_DOMAIN() {
      const data = this.data_common.kq_tc_hddtn.Versions.find((version) => version.type === 'Hệ thống HDDT TT78')
      this.data_common.kq_tc_hddtn_convert.domain = data ? data.domain : null
    },
    comboxChange(item) {
      console.log(item)
      this.data_common.thaotac_id = item.id
    },
    async btnCapNhat_Click() {
      this.loading(true)
      // Thêm độ trễ 2 giây trước khi thực hiện API
      await this.delay(1000)
      if (this.data_common.status == 6) {
        return this.$toast.warning('Thuê bao đã kích hoạt thành công, không được phép thực hiện chức năng')
      }
      const check = this.validateForm()
      if (!check) {
        this.loading(false)
        return
      }
      const jsonData = {
        HDTB_ID: this.openDialog_data.hdtb_id,
        MST_CT: this.openDialog_data.mst,
        MA_PHU_CT: this.data_common.kq_tc_hddtn_convert.id_hethong_tt78,
        PARENT_FIELD: this.data_common.thaotac_id, //1 - Thêm 2 cắt giảm
        SL_CHUYENDOI: this.data_common.so_luong, // Số lượng hóa đơn đăng bổ sung/cắt giảm
        SL_TONG: this.tong_hd, //Tổng số lượng hóa đơn
        LOAITB_ID: 122,
        MA_CH: this.data_common.so_eoffice.trim()
      }
      console.log(jsonData)
      try {
        const res = await api.them_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.openDialog_data.hdtb_id, vds_hdtb_ct: [jsonData] })

        this.loading(false)
        console.log(res.data.data)
        //todo check res
        if (res.data.data == 'ok') {
          this.$toast.success('Cập nhật thông tin cấp bù/cắt giảm hóa đơn thành công')
          this.data_common.btnKichHoat = true
        } else {
          this.$toast.error('Cập nhật thông tin cấp bù/cắt giảm hóa đơn không thành công.')
          this.data_common.btnKichHoat = false
        }
      } catch (err) {
        this.loading(false)
        this.$toast.error('Cập nhật thông tin cấp bù/cắt giảm hóa đơn không thành công.')
      } finally {
        this.layThongTinDaDangKy()
        this.loading(false)
      }
    },
    async layThongTinDaDangKy() {
      const res_data = await api.lay_thong_tin_da_dang_ky(this.axios, this.openDialog_data.hdtb_id)
      if (res_data && res_data.data && res_data.data.data && res_data.data.data.length > 0) {
        console.log('layThongTinDaDangKy')
        const tb = res_data.data.data
        this.data_common.so_luong = tb[0].so_luong
        this.data_common.so_luong_dk = tb[0].so_luong
        this.data_common.so_eoffice = tb[0].so_eoffice
        this.data_common.thaotac_selected = this.data_common.thaotac.find((data) => data.id === tb[0].thaotac_id)
        this.data_common.thaotac_id = tb[0].thaotac_id
        if (this.data_common.so_luong > 0) this.data_common.btnKichHoat = true
        else this.data_common.btnKichHoat = false
        console.log('>>>>>>>>>>>>>>>>>>>>>>this.data_common.so_eoffice')
        console.log(this.data_common.so_eoffice)
      }
    },
    validateForm() {
      try {
        if (this.data_common.so_luong == 0) {
          this.$toast.warning('Số lượng hóa đơn Cấp bù/Cắt giảm phải lớn hơn 0, Vui lòng kiểm tra lại số lượng')
          return false
        }

        this.Lay_TrangThai_kichHoat()
        if (this.data_common.status == 6) {
          this.$toast.warning('Thuê bao đã được kích hoạt thành công. Bạn không được phép thực hiện thao tác này')
          return false
        }
        console.log('this.data_common.so_eoffice')
        console.log(this.data_common.so_eoffice)
        console.log(this.data_common.so_eoffice.trim())
        if (this.data_common.so_eoffice === null || this.data_common.so_eoffice.trim() === '') {
          this.$toast.warning('Số EOffice không được để trống')
          return false
        }
        if (this.data_common.so_eoffice.length >= 50) {
          this.$toast.warning('Số EOffice không được dài quá 50 ký tự')
          return false
        }
        if (isNaN(this.data_common.so_eoffice)) {
          this.$toast.warning('Số EOffice phải là số')
          return false
        }
        return true
      } catch (err) {
        console.error(err)
      } finally {
      }
    },
    async btnKichHoat_Click() {
      try {
        this.data_common.btnKichHoat = false
        this.loading(true)
        // Thêm độ trễ 2 giây trước khi thực hiện API
        await this.delay(1000)
        if (this.data_common.status == 6) {
          return this.$toast.warning('Thuê bao đã kích hoạt thành công, không được phép thực hiện chức năng này')
        }
        const check = this.validateForm()
        if (!check) {
          this.loading(false)
          return
        }
        this.layThongTinDaDangKy()
        if (this.data_common.so_luong_dk != 0 && this.data_common.so_luong != this.data_common.so_luong_dk) {
          this.$toast.warning('Bạn đã thay đổi lại số lượng hóa đơn cấp bù/cắt giảm , Vui lòng nhấn cập nhật để cập nhật lại số lượng hóa đơn trước khi kích hoạt')
          this.loading(false)
          return
        }
        const get_ketqua_dynamic = await api.call_dynamic_api(this.axios, { hdtb_id: this.openDialog_data.hdtb_id, vhdtb_id: this.openDialog_data.hdtb_id })
        console.log(get_ketqua_dynamic)
        console.log(get_ketqua_dynamic.data.data.message)
        if (get_ketqua_dynamic.data.data.status == '00') {
          this.$toast.success('Kích hoạt thành công')
          this.data_common.status = 6
          this.data_common.btnKichHoat = false
        } else {
          this.$toast.error('Kích hoạt không thành công. ' + get_ketqua_dynamic.data.data.message)
          this.data_common.btnKichHoat = true
        }
        this.loading(false)
      } catch (error) {
        this.data_common.btnKichHoat = true
        console.log(error)
        this.loading(false)
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>

<style></style>
