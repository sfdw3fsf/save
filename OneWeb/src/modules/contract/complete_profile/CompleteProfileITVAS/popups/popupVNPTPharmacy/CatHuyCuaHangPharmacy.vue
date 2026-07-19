<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :isModal="true" ref="CatHuyCuaHangPharmacy" :header="'Cắt giảm số lượng cửa hàng'" showCloseIcon="true" width="75%" target="#app .main-wrapper">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="btnGhiLai_Click"> <span class="icon one-save"></span>Ghi lại</a>
        </li>
        <li>
          <a href="#"> <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Xóa </a>
        </li>

        <li>
          <a href="#" @click.prevent="btnKichHoat_Click"> <span class="icon one-luong"></span>Kích hoạt</a>
        </li>
      </ul>
    </div>
    <div class="row">
      <div class="col-sm-12 col-12">
        <div class="box-move-select-table">
          <div class="box-col box-form">
            <div class="legend-title">Danh sách cửa hàng đã đăng ký cắt hủy</div>
            <div class="table-content" style="height: 250px;">
              <DataGrid v-bind:columns="DsCuaHangCatHuy.cols" v-bind:dataSource="DsCuaHangCatHuy.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabledSelectFirstRow="false" :showColumnCheckbox="true" ref="DsCuaHangCatHuy"> </DataGrid>
            </div>
          </div>
          <div class="actions">
            <button @click.prevent="btnHuyCuaHang_click" class="btn">
              <span class="fa fa-angle-right"></span>
            </button>

            <button @click.prevent="btnDangKyCuaHang_click" class="btn">
              <span class="fa fa-angle-left"></span>
            </button>
          </div>
          <div class="box-col box-form">
            <div class="legend-title">Danh sách cửa hàng</div>
            <div class="table-content" style="height: 250px;">
              <DataGrid v-bind:columns="DsCuaHang.cols" v-bind:dataSource="DsCuaHang.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabledSelectFirstRow="false" :showColumnCheckbox="true" ref="DsCuaHang"> </DataGrid>
            </div>
          </div>
        </div>

        <div class="box-move-select-table">
          <div class="box-col box-form">
            <div class="legend-title">Danh sách User đã đăng ký căt hủy</div>
            <div class="table-content" style="height: 250px;">
              <DataGrid v-bind:columns="DsTaiKhoanCatHuy.cols" v-bind:dataSource="DsTaiKhoanCatHuy.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabledSelectFirstRow="false" :showColumnCheckbox="true" ref="DsTaiKhoanCatHuy"> </DataGrid>
            </div>
          </div>
          <div class="actions">
            <button @click.prevent="btnHuyTaiKhoan_click" class="btn">
              <span class="fa fa-angle-right"></span>
            </button>

            <button @click.prevent="btnDangKyTaiKhoan_click" class="btn">
              <span class="fa fa-angle-left"></span>
            </button>
          </div>
          <div class="box-col box-form">
            <div class="legend-title">Danh sách User</div>
            <div class="table-content" style="height: 250px;">
              <DataGrid v-bind:columns="DsTaiKhoan.cols" v-bind:dataSource="DsTaiKhoan.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabledSelectFirstRow="false" :showColumnCheckbox="true" ref="DsTaiKhoan"> </DataGrid>
            </div>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import api from '@/modules/contract/complete_profile/CompleteProfileITVAS/API/CompleteProfileAPI.js'
export default {
  data() {
    return {
      hdtb_id: '',
      ma_tb: '',
      status: '',
      DsCuaHang: {
        list: [],
        cols: [
          { fieldName: 'MA_CH', headerText: 'Mã cửa hàng', allowFiltering: false, allowSorting: false },
          { fieldName: 'TEN', headerText: 'Tên cửa hàng', allowFiltering: false, allowSorting: false },
          { fieldName: 'DIACHI_CT', headerText: 'Địa chỉ', allowFiltering: false, allowSorting: false },
          { fieldName: 'MOBILE', headerText: 'SĐT', allowFiltering: false, allowSorting: false },
          { fieldName: 'EMAIL', headerText: 'Email', allowFiltering: false, allowSorting: false },
          { fieldName: 'MS_THUE', headerText: 'MST', allowFiltering: false, allowSorting: false }
        ],
        value: {},
        isEnabled: true
      },
      DsCuaHangCatHuy: {
        list: [],
        cols: [
          { fieldName: 'MA_CH', headerText: 'Mã cửa hàng', allowFiltering: false, allowSorting: false },
          { fieldName: 'TEN', headerText: 'Tên cửa hàng', allowFiltering: false, allowSorting: false },
          { fieldName: 'DIACHI_CT', headerText: 'Địa chỉ', allowFiltering: false, allowSorting: false },
          { fieldName: 'MOBILE', headerText: 'SĐT', allowFiltering: false, allowSorting: false },
          { fieldName: 'EMAIL', headerText: 'Email', allowFiltering: false, allowSorting: false },
          { fieldName: 'MS_THUE', headerText: 'MST', allowFiltering: false, allowSorting: false }
        ],
        value: {},
        isEnabled: true
      },

      DsTaiKhoan: {
        list: [],
        cols: [
          { fieldName: 'USERID', headerText: 'User ID', allowFiltering: false, allowSorting: false },
          { fieldName: 'USER_NAME', headerText: 'Tên', allowFiltering: false, allowSorting: false },
          { fieldName: 'DOB', headerText: 'Ngày sinh', allowFiltering: false, allowSorting: false },
          { fieldName: 'ADDRESS', headerText: 'Đaih chỉ', allowFiltering: false, allowSorting: false },
          { fieldName: 'TEL', headerText: 'SĐT', allowFiltering: false, allowSorting: false },
          { fieldName: 'EMAIL', headerText: 'Email', allowFiltering: false, allowSorting: false },
          { fieldName: 'LOGIN_USERID', headerText: 'User Login', allowFiltering: false, allowSorting: false }
        ],
        value: {},
        isEnabled: true
      },
      DsTaiKhoanCatHuy: {
        list: [],
        cols: [
          { fieldName: 'USERID', headerText: 'User ID', allowFiltering: false, allowSorting: false },
          { fieldName: 'USER_NAME', headerText: 'Tên', allowFiltering: false, allowSorting: false },
          { fieldName: 'DOB', headerText: 'Ngày sinh', allowFiltering: false, allowSorting: false },
          { fieldName: 'ADDRESS', headerText: 'Đaih chỉ', allowFiltering: false, allowSorting: false },
          { fieldName: 'TEL', headerText: 'SĐT', allowFiltering: false, allowSorting: false },
          { fieldName: 'EMAIL', headerText: 'Email', allowFiltering: false, allowSorting: false },
          { fieldName: 'LOGIN_USERID', headerText: 'User Login', allowFiltering: false, allowSorting: false }
        ],
        value: {},
        isEnabled: true
      }
    }
  },
  methods: {
    async ConvertResApi(response_input) {
      let response = await response_input
      if (response.data.errorCode && response.data.errorCode != '') {
        throw response.data.message_detail
      }

      if (response.data.error === 200 || response.data.error === '200' || response.data.errorCode === 0) {
        if (response.data.error_code === 'BSS-00000000') {
          return response.data.data
        }
      } else {
        if (response.data.error === '204' || response.data.error === 204) {
          return []
        } else return response.data.message
      }
    },
    async openPupup(data) {
      console.log(data)
      this.hdtb_id = data.vhdtb_id
      this.$refs.CatHuyCuaHangPharmacy.show()
      await this.pageLoad()
    },
    async pageLoad() {
      try {
        console.log('pageLoad')
        this.loading(true)
        await this.LayDSCuaHang()
        await this.LayDsTaiKhoan()
        await this.LayStatus()
        await this.LayDSTaiKhoanVaCuaHangDaDangKy()
        this.loading(false)
      } catch (error) {
        this.loading(false)
      } finally {
        this.loading(false)
      }
    },
    async LayDSTaiKhoanVaCuaHangDaDangKy() {
      console.log('LayDSTaiKhoanDaDangKy')
      const rs = await this.ConvertResApi(api.lay_thong_tin_da_dang_ky(this.axios, { vhdtb_id: this.hdtb_id }))
      var newData = rs.map(function(obj) {
        var newObj = {}
        Object.keys(obj).forEach(function(key) {
          newObj[key.toUpperCase()] = obj[key]
        })
        return newObj
      })
      console.log(newData)
      this.DsTaiKhoanCatHuy.list = newData.filter((x) => x.V_KEY === 'US')
      this.DsCuaHangCatHuy.list = newData.filter((x) => x.V_KEY === 'CH')
    },
    async LayStatus() {
      try {
        let response = await api.fn_get_status_hdtb(this.axios, { vhdtb_id: this.hdtb_id })
        if (response.data.error == 200 || response.data.errorCode === 0) {
          this.status = response.data.data
        }
      } catch (error) {
        this.$toast.error('Có lỗi: ' + error.message)
        return
      }
    },
    async LayDSCuaHang() {
      try {
        console.log('Nhẩy vào LayDSCuaHang')
        // const data = '{"transId":"123456789","sme":{"subscriptionCode":"hcm_pharmacy_00000439","provinceCode":"HCM"}}'
        const kq = await api.cntt_dynamic_api(this.axios, { hdtb_id: this.hdtb_id, vdem: 0, vtype: 'LS' })
        if (kq.data.data && kq.data.data != '') {
          this.DsCuaHang.list = kq.data.data.data.map.rs
        }
      } catch (error) {
        this.$toast.error(error.message)
        return
      }
    },
    async LayDsTaiKhoan() {
      try {
        console.log('Nhẩy vào lấy danh LayDsTaiKhoan')
        //const data = '{"transId":"123456789","sme":{"subscriptionCode":"hcm_pharmacy_00000439","provinceCode":"HCM"}}'
        const kq = await api.cntt_dynamic_api(this.axios, { hdtb_id: this.hdtb_id, vdem: 0, vtype: 'LUS' })
        if (kq.data.data && kq.data.data != '') {
          kq.data.data.data.map.rs.forEach(function(obj) {
            // Xóa các trường không mong muốn, bị chặn do thằng API Onebss nên phải xóa đi
            delete obj.CREATED_DATE
            delete obj.MODIFIED_DATE
            delete obj.CREATED_BY
          })
          this.DsTaiKhoan.list = kq.data.data.data.map.rs
        }
      } catch (error) {
        this.$toast.error(error.message)
        return
      }
    },
    async btnDangKyCuaHang_click() {
      try {
        this.loading(true)
        this.LayStatus()
        if (this.status == 6) {
          this.$toast.error('Thuê bao đã được kích hoạt thành công')
          return
        }
        let ds_chon = []
        let ds_chon_add = []
        ds_chon = this.$refs.DsCuaHang.getSelectedRecords()
        console.log(ds_chon)
        if (ds_chon.length <= 0) {
          this.$toast.error('Hãy chọn cửa hàng trước khi đăng ký hủy')
          return
        } else {
          //ds_chon_add = this.ds_chon_add.filter((x) => x.id != this.grcDSHocSinhDangKy.list.id)
          ds_chon_add = ds_chon.filter((x) => {
            let kq = !this.DsCuaHangCatHuy.list.find((y) => y.MA_CH === x.MA_CH)
            return kq
          })
          this.DsCuaHangCatHuy.list = [...this.DsCuaHangCatHuy.list, ...ds_chon_add]
          const rs = await this.ConvertResApi(api.them_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.hdtb_id, vds_hdtb_ct: this.DsCuaHangCatHuy.list, vkey: 'CH' }))
          this.loading(false)
        }
      } catch (ex) {
        this.loading(false)
        this.$toast.error(ex.message)
      }
    },
    async btnHuyCuaHang_click() {
      try {
        this.loading(true)
        this.LayStatus()
        if (this.status == 6) {
          this.$toast.error('Thuê bao đã được kích hoạt thành công')
          return
        }
        let ds_chon = []
        let ds_chon_huy = []
        ds_chon = this.$refs.DsCuaHangCatHuy.getSelectedRecords()
        if (ds_chon.length <= 0) {
          this.$toast.error('Hãy chọn cửa hàng trước khi đăng ký hủy')
          return
        } else {
          //ds_chon_add = this.ds_chon_add.filter((x) => x.id != this.grcDSHocSinhDangKy.list.id)
          ds_chon_huy = this.DsCuaHangCatHuy.list.filter((x) => {
            let kq = !ds_chon.find((y) => y.MA_CH === x.MA_CH)
            return kq
          })
          this.DsCuaHangCatHuy.list = [...ds_chon_huy]
          const rs = await this.ConvertResApi(api.them_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.hdtb_id, vds_hdtb_ct: this.DsCuaHangCatHuy.list, vkey: 'CH' }))
          this.loading(false)
        }
      } catch (ex) {
        this.loading(false)
        this.$toast.error(ex.message)
      }
    },
    async btnDangKyTaiKhoan_click() {
      try {
        this.loading(true)
        this.LayStatus()
        if (this.status == 6) {
          this.$toast.error('Thuê bao đã được kích hoạt thành công')
          return
        }
        let ds_chon = []
        let ds_chon_add = []
        ds_chon = this.$refs.DsTaiKhoan.getSelectedRecords()
        if (ds_chon.length <= 0) {
          this.$toast.error('Hãy chọn tài khoản trước khi đăng ký hủy')
          return
        } else {
          //ds_chon_add = this.ds_chon_add.filter((x) => x.id != this.grcDSHocSinhDangKy.list.id)
          ds_chon_add = ds_chon.filter((x) => {
            let kq = !this.DsTaiKhoanCatHuy.list.find((y) => y.USERID === x.USERID)
            return kq
          })
          this.DsTaiKhoanCatHuy.list = [...this.DsTaiKhoanCatHuy.list, ...ds_chon_add]
          this.loading(true)
          const rs = await this.ConvertResApi(api.them_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.hdtb_id, vds_hdtb_ct: this.DsTaiKhoanCatHuy.list, vkey: 'US' }))
          this.loading(false)
        }
      } catch (ex) {
        this.loading(false)
        this.$toast.error(ex.message)
      }
    },
    async btnHuyTaiKhoan_click() {
      try {
        this.loading(true)
        this.LayStatus()
        if (this.status == 6) {
          this.$toast.error('Thuê bao đã được kích hoạt thành công')
          return
        }
        let ds_chon = []
        let ds_chon_huy = []
        ds_chon = this.$refs.DsTaiKhoanCatHuy.getSelectedRecords()
        console.log(ds_chon)
        if (ds_chon.length <= 0) {
          this.$toast.error('Hãy chọn tài khoản trước khi hủy đăng ký hủy')
          return
        } else {
          //ds_chon_add = this.ds_chon_add.filter((x) => x.id != this.grcDSHocSinhDangKy.list.id)
          ds_chon_huy = this.DsTaiKhoanCatHuy.list.filter((x) => {
            let kq = !ds_chon.find((y) => y.USERID === x.USERID)
            return kq
          })
          this.DsTaiKhoanCatHuy.list = [...ds_chon_huy]
          this.loading(true)
          const rs = await this.ConvertResApi(api.them_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.hdtb_id, vds_hdtb_ct: this.DsTaiKhoanCatHuy.list, vkey: 'US' }))
          this.loading(false)
        }
      } catch (ex) {
        this.loading(false)
        this.$toast.error(ex.message)
      }
    },
    async btnGhiLai_Click() {
      try {
        this.loading(true)
        this.LayStatus()
        const rs = await this.ConvertResApi(api.them_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.hdtb_id, vds_hdtb_ct: this.DsTaiKhoanCatHuy.list, vkey: 'US' }))
        if (rs == 'ok') {
          const rs1 = await this.ConvertResApi(api.them_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.hdtb_id, vds_hdtb_ct: this.DsCuaHangCatHuy.list, vkey: 'CH' }))
          this.loading(false)
          if (rs1 == 'ok') {
            this.$toast.success('Cập nhật thành công')
            return
          } else {
            this.$toast.error('Cập nhật không thành công: ' + rs1)
            return
          }
        } else {
          this.$toast.error('Cập nhật không thành công: ' + rs)
          return
        }
      } catch (error) {
        this.loading(false)
        this.$toast.error('Cập nhật không thành công: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async btnKichHoat_Click() {
      try {
        this.loading(true)
        this.LayStatus()
        if (this.status == 6) {
          this.$toast.error('Thuê bao đã được kích hoạt thành công')
          return
        }
        if (this.DsCuaHangCatHuy.list.length * 3 < this.DsTaiKhoanCatHuy.list.length) {
          this.$toast.error('Số lượng tài khoản cắt hủy không được lớn hơn ' + this.DsCuaHangCatHuy.list.length * 3)
          return
        }
        const kq = await api.cntt_dynamic_api(this.axios, { hdtb_id: this.hdtb_id, vdem: 0, vtype: 'CGSLCH' })
        this.loading(false)
        if (kq.data.data.status == '00') {
          this.$toast.success('Cắt tài khoản hàng thành công')
          return
        } else {
          this.$toast.error('Hủy tài khoản không thành công: ' + kq.data.data.message)
          return
        }
      } catch (error) {
        this.$toast.error('Có lỗi: ' + error.message)
        this.loading(false)
        return
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>

<style></style>
