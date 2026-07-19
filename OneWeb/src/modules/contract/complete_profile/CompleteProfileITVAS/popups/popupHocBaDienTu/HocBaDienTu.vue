<template>
  <ejs-dialog :enableResize="true" :isModal="true" allowDragging="true" zIndex="1000" :visible="false" ref="dialogHocBaDienTu" :header=this.titleName showCloseIcon="true" target="#app .main-wrapper">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="btnGhiLai_ItemClick" :class="{ disabled: !is_disabled }"> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Ghi lại </a>
        </li>

        <li>
          <a href="#" @click.prevent="btnXoa_ItemClick" :class="{ disabled: !is_disabled }"> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Xoá </a>
        </li>

        <li>
          <a href="#" @click.prevent="btnKichHoat_ItemClick" :class="{ disabled: !is_disabled }"> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> kích hoạt </a>
        </li>
      </ul>
    </div>

    <div class="popup-body">
      <div class="box-move-select-table">
        <div class="box-col">
          <div class="box-form box-full-height">
            <div class="legend-title mart20">Danh sách học sinh đăng ký</div>
            <!----<div class="value">
              <span class="name">Tổng số lượng học sinh đăng ký</span>
              <input type="text" name="" id="" v-model="tongso_hs" readonly style="margin-bottom: 10px;" />
            </div>
            <div class="value">
              <span class="name">Năm học đăng ký</span>
              <input type="text" name="" id="" readonly style="margin-bottom: 10px;" />
            </div>-->
            <div class="row">
              <div class="col-12 col-sm-6">
                <div class="info-row">
                  <div class="key">Tổng số lượng học sinh đăng ký:</div>
                  <div class="value">
                    <input v-model="tongso_hs" type="text" class="form-control" disabled />
                  </div>
                </div>
              </div>
              <div class="col-12 col-sm-6">
                <div class="info-row">
                  <div class="key">Năm học đăng ký:</div>
                  <div class="value">
                    <input type="text" v-model="cboNamHoc.value_hd" class="form-control" disabled />
                  </div>
                </div>
              </div>
            </div>
            <DataGrid :panelDataHeight="'40vh'" ref="DSHSHuyDangKy" v-bind:columns="grcDSHocSinhDangKy.cols" v-bind:dataSource="grcDSHocSinhDangKy.list" :enable-paging-server="false" :allowPaging="false" :showFilter="true" :showColumnCheckbox="true" :enabledSelectFirstRow="false"> </DataGrid>
          </div>
        </div>

        <div class="actions padt0 block">
          <button class="btn btn-main" @click="btnDangKy_Click">
            <span class="-ap  icon-arrow-left"></span>
          </button>

          <button class="btn  btn-main" @click.prevent="btnHuyDK_Click">
            <span class="-ap icon-arrow-right"></span
            ><!-- </span><span class="-ap icon-arrow-right"></span> -->
          </button>
        </div>

        <div class="box-col">
          <div class="box-form">
            <div class="legend-title mart20">Danh sách học sinh theo lớp</div>

            <div class="info-row">
              <div class="key w90">
                <span class="name">Năm học</span>
              </div>

              <div class="value">
                <ComboboxGrid
                  class="select-custom"
                  :columns="[
                    { fieldName: 'namhoc_id', headerText: 'năm học id', allowFiltering: true, width: '100' },

                    { fieldName: 'namhoc', headerText: 'Năm học', allowFiltering: true, width: '300' }
                  ]"
                  :dataSource="cboNamHoc.list"
                  textField="namhoc"
                  valueField="namhoc_id"
                  @selectedChanged="comboxChangeNamHoc"
                >
                </ComboboxGrid>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">
                <span class="name">Khối học</span>
              </div>

              <div class="value">
                <div class="value">
                  <ComboboxGrid
                    class="select-custom"
                    :columns="[
                      { fieldName: 'id', headerText: 'Khối học ID', allowFiltering: true, width: '70' },

                      { fieldName: 'ten', headerText: 'Tên khối học', allowFiltering: true, width: '300' }
                    ]"
                    :dataSource="cboKhoiHoc.list"
                    textField="ten"
                    valueField="id"
                    @selectedChanged="comboxChangeKhoiHoc"
                  >
                  </ComboboxGrid>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">
                <span class="name">Lớp học</span>
              </div>

              <div class="value">
                <ComboboxGrid
                  class="select-custom"
                  :columns="[
                    { fieldName: 'id', headerText: 'ID lớp', allowFiltering: true, width: '100' },

                    { fieldName: 'ten', headerText: 'Tên lớp học', allowFiltering: true, width: '300' },

                    { fieldName: 'khoi', headerText: 'khối', allowFiltering: true, width: '70' }
                  ]"
                  :dataSource="cboLopHoc.list"
                  textField="ten"
                  valueField="id"
                  @selectedChanged="comboxChangeLopHoc"
                >
                </ComboboxGrid>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">
                <span class="name">Lọc</span>
              </div>
              <div class="value">
                <ComboboxGrid
                  class="select-custom"
                  :columns="[
                    { fieldName: 'id', headerText: 'ID', allowFiltering: true, width: '100' },

                    { fieldName: 'ten', headerText: 'Trạng thái học sinh', allowFiltering: true, width: '300' }
                  ]"
                  :dataSource="cbbTrangThai.list"
                  textField="ten"
                  valueField="id"
                  @selectedChanged="comboxChangeLoc"
                >
                </ComboboxGrid>
              </div>
            </div>

            <DataGrid :panelDataHeight="'34vh'" ref="DSHSDangKy" v-bind:columns="grcDanhSachHocSinh.cols" v-bind:dataSource="grcDanhSachHocSinh.list" :enable-paging-server="false" :allowPaging="false" :showFilter="true" :showColumnCheckbox="true" :enabledSelectFirstRow="false"> </DataGrid>

            <!-- @rowDoubleClicked="gdvMauHoaDon_DoubleClick">  -->
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import api from './HocBaDienTuApi'
export default {
  name: 'PopupHocBaDienTu',
  data() {
    return {
      width: 0,
      grcDSHocSinhDangKy: {
        list: [],
        cols: [
          { fieldName: 'full_name', headerText: 'Tên học sinh', allowFiltering: true, allowSorting: false },
          { fieldName: 'ten_lop', headerText: 'Lớp học', allowFiltering: true, allowSorting: false },
          { fieldName: 'ngay_sinh_text', headerText: 'Ngày sinh', allowFiltering: true, allowSorting: false },
          { fieldName: 'gioi_tinh', headerText: 'Giới tính', allowFiltering: true, allowSorting: false },
          { fieldName: 'statusHs_text', headerText: 'Trạng thái', allowFiltering: false, allowSorting: false }
        ],
        keycol: '',
        value: {}
      },
      grcDanhSachHocSinh: {
        list: [],
        cols: [
          { fieldName: 'full_name', headerText: 'Tên học sinh', allowFiltering: true, allowSorting: false },
          { fieldName: 'ten_lop', headerText: 'Lớp học', allowFiltering: true, allowSorting: false },
          { fieldName: 'ngay_sinh_text', headerText: 'Ngày sinh', allowFiltering: true, allowSorting: false },
          { fieldName: 'gioi_tinh', headerText: 'Giới tính', allowFiltering: true, allowSorting: false },
          { fieldName: 'statusHs_text', headerText: 'Trạng thái', allowFiltering: false, allowSorting: false }
        ],
        keycol: '',
        value: {}
      },
      id_truong: '',
      hdtb_id: '',
      cboKhoiHoc: {
        list: [],
        value: ''
      },
      cboLopHoc: {
        list: [],
        value: ''
      },
      cboNamHoc: {
        list: [],
        value: '',
        value_hd: ''
      },
      cbbTrangThai: {
        list: [
          { id: 3, ten: 'Chưa đăng ký' },
          //{ id: 0, ten: 'Tất cả học sinh' },
          { id: 1, ten: 'Đã kích hoạt' },
          { id: 2, ten: 'Chờ duyệt' }
        ],
        value: ''
      },
      tongso_hs: 0,
      ds_hsdadk: [],
      sl_hs_dk_tren_hd: '',
      titleName:'',
      is_disabled: true
    }
  },
  created: async function() {},
  mounted: function() {
    try {
    } catch (error) {
      this.$toast.error(error)
      return
    }
  },
  watch: {
    'cboKhoiHoc.value'(newValue, oldValue) {
      if (newValue != oldValue && newValue != null) this.LayDSLopHoc({ truong_id: this.id_truong, khoi_id: newValue, namhoc: this.cboNamHoc.value })
    },
    'cboLopHoc.value'(newValue, oldValue) {
      if (newValue != oldValue && newValue != null) this.LayDSHocSinh({ truong_id: this.id_truong, lop_id: newValue, loc_id: this.cbbTrangThai.value, namhoc: this.cboNamHoc.value })
    },
    'cbbTrangThai.value'(newValue, oldValue) {
      console.log('Giá trị mới: ' + newValue + ' Giá trị cũ: ' + oldValue)
      if (newValue != oldValue && newValue != null) this.LayDSHocSinh({ truong_id: this.id_truong, lop_id: this.cboLopHoc.value, loc_id: newValue, namhoc: this.cboNamHoc.value })
    },
    'cboNamHoc.value'(newValue, oldValue) {
      console.log('Giá trị mới: ' + newValue + ' Giá trị cũ: ' + oldValue)
      if (newValue != oldValue && newValue != null) this.LayDSLopHoc({ truong_id: this.id_truong, khoi_id: this.cboKhoiHoc.value, namhoc: newValue })
    }
  },

  methods: {
    async openDialog(data) {
      if (!data || !data.hdtb_id) {
        return
      }
      this.hdtb_id = data.hdtb_id
      this.id_truong = data.id_truong
      this.sl_hs_dk_tren_hd = data.sl_hocsinh
      this.titleName = data.titleName
      this.$refs['dialogHocBaDienTu'].show()
      await this.formLoad()
    },
    TaoDSNamHoc() {
      try {
        const currentDate = new Date()
        const currentYear = currentDate.getFullYear()

        for (let year = 1970; year <= currentYear; year++) {
          let obj_new = {}
          obj_new.namhoc_id = year
          obj_new.namhoc = year + ' - ' + (year * 1 + 1)
          this.cboNamHoc.list.unshift(obj_new)
        }
        console.log(this.cboNamHoc.list)
      } catch (error) {
        this.$toast.error(error.message)
      }
    },
    async Lay_NamHoc_DangKy() {
      try {
        this.loading(true)
        const data = await api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
          p_nghiepvu: "LAY_NAMHOC_HOCBADIENTU",
          p_ds_para: '{"vhdtb_id":' + this.hdtb_id + '}'
        })
        console.log('Lay_NamHoc_DangKy')
        console.log(data)
        console.log(data.data.data[0].thoihan_id)
       // console.log(data.data[0].thoihan_id)
        
        this.cboNamHoc.value_hd = data.data.data[0].thoihan_id
      } catch (err) {
        this.$toast.error('Đã có lỗi xả ra: ' + err.message)
      } finally {
        this.loading(false)
      }
    },
    async get_tongso_hd() {
      this.tongso_hs = this.grcDSHocSinhDangKy.list.length
    },
    async formLoad() {
      try {
        this.loading(true)
        this.TaoDSNamHoc()
        await this.Lay_NamHoc_DangKy()
        await this.LayDskhoi()
        await this.LayDSHsDaDk()
        await this.get_tongso_hd()
      } catch (err) {
        this.loading(false)
        this.$toast.error("Đã có lỗi xả ra: "+err.message)
      } finally {
        this.loading(false)
      }
    },
    async LayDskhoi() {
      try {
        console.log('ID trường:' + this.id_truong)
        const json_data = '{"truong_id":' + this.id_truong + '}'
        this.loading(true)
        const rs = await this.ConvertResApi(api.LayDSKhoi(this.axios, json_data))
        this.loading(false)
        const data = JSON.parse(rs)
        this.cboKhoiHoc.list = data.data
      } catch (ex) {
        this.loading(false)
        this.$toast.error("Đã có lỗi xả ra: "+ ex.message)
      } finally {
        this.loading(false)
      }
    },
    async LayDSHsDaDk() {
      try {
        this.loading(true)
        if (this.hdtb_id) {
          this.loading(true)
          const rs = await this.ConvertResApi(api.lay_ds_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.hdtb_id }))
          this.loading(false)
          if (rs) this.grcDSHocSinhDangKy.list = rs
        }
      } catch (ex) {
        this.loading(false)
        this.$toast.error("Đã có lỗi sảy ra: "+ ex.message)
      } finally {
        this.loading(false)
      }
    },

    async ConvertResApi(response_input) {
      let response = await response_input
      if (response.data.errorCode && response.data.errorCode != '' && response.data.errorCode != '0' && response.data.faultString && response.data.faultString != '') {
        throw response.data.faultString
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

    comboxChangeKhoiHoc(item) {
      this.cboKhoiHoc.value = item.id
      //this.LayDSHocSinh({ truong_id: '2599687637', khoi_id: this.cboKhoiHoc.value })
    },
    comboxChangeLopHoc(item) {
      this.cboLopHoc.value = item.id
    },
    comboxChangeNamHoc(item) {
      this.cboNamHoc.value = item.namhoc_id
    },
    comboxChangeLoc(item) {
      this.cbbTrangThai.value = item.id
    },
    async LayDSLopHoc(data_ip) {
      try {
        const rs = await this.ConvertResApi(api.LayDSLopHoc(this.axios, JSON.stringify(data_ip)))
        const data = JSON.parse(rs)
        console.log(data.data.length > 0)
        if (data.data.length > 0) {
          this.cboLopHoc.list = data.data
        } else {
          this.cboLopHoc.list = []
          this.grcDanhSachHocSinh.list = []
        }
      } catch (ex) {
        this.loading(false)
        this.$toast.error("Đã có lỗi sảy ra: "+ex.message)
      } finally {
        this.loading(false)
      }
    },
    async LayDSHocSinh(data_ip) {
      try {
        console.log(data_ip)
        this.loading(true)
        const rs = await this.ConvertResApi(api.LayDSHocSinhTheoDK(this.axios, JSON.stringify(data_ip)))
        this.loading(false)
        const data = JSON.parse(rs)
        if (data.data.items) {
          const data_convert = data.data.items.map((p) => (p.gioi_tinh * 1 === 1 ? { ...p, gioi_tinh: 'Nam' } : { ...p, gioi_tinh: 'Nữ' }))
          //this.grcDanhSachHocSinh.list = data.data.items
          this.grcDanhSachHocSinh.list = data_convert
        } else {
          this.grcDanhSachHocSinh.list = []
        }
      } catch (ex) {
        this.loading(false)
        this.$toast.error(ex)
      } finally {
        this.loading(false)
      }
    },

    async btnDangKy_Click() {
      try {
        this.loading(true)
        if (this.cboNamHoc.value == null || this.cboNamHoc.value == undefined) {
          this.$toast.error('Hãy chọn năm học trước khi đăng ký học sinh')
          return
        }
        if(this.cboNamHoc.value_hd != 0)
        {
          if(this.cboNamHoc.value_hd != this.cboNamHoc.value)
          {
            this.$toast.error('Vui lòng không chọn các học sinh trong các năm học khác nhau')
            return
          }
        }
        let ds_chon = []
        let ds_chon_add = []
        ds_chon = this.$refs.DSHSDangKy.getSelectedRecords()
        if (ds_chon.length <= 0) {
          this.$toast.error('Hãy chọn học sinh trước khi đăng ký')
          return
        } else {
          //ds_chon_add = this.ds_chon_add.filter((x) => x.id != this.grcDSHocSinhDangKy.list.id)
          ds_chon_add = ds_chon.filter((x) => {
            let kq = !this.grcDSHocSinhDangKy.list.find((y) => y.hoc_sinh_id === x.hoc_sinh_id)
            return kq
          })
          this.grcDSHocSinhDangKy.list = [...this.grcDSHocSinhDangKy.list, ...ds_chon_add]
          this.get_tongso_hd()
          const rs = await this.ConvertResApi(api.them_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.hdtb_id, vds_hdtb_ct: this.grcDSHocSinhDangKy.list }))
          await this.ConvertResApi(api.update_hdtb_cntt(this.axios, { vhdtb_id: this.hdtb_id, vkey: 'CAPNHAT_NAMHOC_HOCBADIENTU', vdata: { thoihan_id: this.cboNamHoc.value } }))
          await this.Lay_NamHoc_DangKy()
          this.loading(false)
        }
      } catch (ex) {
        this.loading(false)
        this.$toast.error(ex)
      } finally {
        this.loading(false)
      }
    },
    async btnHuyDK_Click() {
      try {
        this.loading(true)
        let ds_chon = []
        let ds_chon_huy = []
        ds_chon = this.$refs.DSHSHuyDangKy.getSelectedRecords()

        if (ds_chon.length <= 0) {
          this.$toast.error('Hãy chọn học sinh trước khi hủy đăng ký')
          return
        } else {
          ds_chon_huy = this.grcDSHocSinhDangKy.list.filter((x) => {
            let kq = !ds_chon.find((y) => y.hoc_sinh_id === x.hoc_sinh_id)
            return kq
          })
          this.grcDSHocSinhDangKy.list = [...ds_chon_huy]
          this.get_tongso_hd()
          const rs = await this.ConvertResApi(api.them_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.hdtb_id, vds_hdtb_ct: this.grcDSHocSinhDangKy.list }))
          if(this.grcDSHocSinhDangKy.list.length == 0)
          {
            await this.ConvertResApi(api.update_hdtb_cntt(this.axios, { vhdtb_id: this.hdtb_id, vkey: 'CAPNHAT_NAMHOC_HOCBADIENTU', vdata: { thoihan_id: 0 } }))
            this.Lay_NamHoc_DangKy()
          }
          this.loading(false)
        }
      } catch (err) {
        this.loading(false)
        this.$toast.error(err)
      } finally {
        this.loading(false)
      }
    },
    async btnGhiLai_ItemClick() {
      try {
        this.loading(true)
        if (this.cboNamHoc.value == null || this.cboNamHoc.value == undefined) {
          this.$toast.error('Hãy chọn năm học trước khi đăng ký học sinh')
          return
        }
        if (this.grcDSHocSinhDangKy.list.length <= 0) {
          this.$toast.error('Không có học sinh đăng ký')
          return
        } else {
          const rs = await this.ConvertResApi(api.them_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.hdtb_id, vds_hdtb_ct: this.grcDSHocSinhDangKy.list }))
          await this.ConvertResApi(api.update_hdtb_cntt(this.axios, { vhdtb_id: this.hdtb_id, vkey: 'UPDATE_NAMHOC_HOCBADIENTU', vdata: { thoihan_id: this.cboNamHoc.value } }))
          this.loading(false)
          if (rs == 'ok') {
            this.$toast.success('Cập nhật thành công')
            return
          } else {
            this.$toast.error('Cập nhật không thành công: ' + rs)
            return
          }
        }
      } catch (err) {
        this.loading(false)
        this.$toast.error(err)
      } finally {
        this.loading(false)
      }
    },
    async btnXoa_ItemClick() {
      try {
        this.loading(true)
        if (this.grcDSHocSinhDangKy.list.length <= 0) {
          this.$toast.error('Không có danh sách học sinh đăng ký cần xóa')
          return
        } else {
          const rs = await this.ConvertResApi(api.them_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.hdtb_id, vds_hdtb_ct: null }))
          this.loading(false)
          if (rs == 'ok') {
            this.$toast.success('Xóa danh sách thành công')
            this.grcDSHocSinhDangKy.list = []
            return
          } else {
            this.$toast.error('Xóa danh sách không thành công')
            return
          }
        }
      } catch (err) {
        this.loading(false)
        this.$toast.error(err)
      } finally {
        this.loading(false)
      }
    },
    async btnKichHoat_ItemClick() {
      try {
        this.loading(true)
        if(this.cboNamHoc.value_hd == 0 )
        {
          this.$toast.error('Không thể kích hoạt năm học đăng ký bằng 0. Vui lòng hãy chọn lại số lượng học sinh')
          return
        }
        if (this.sl_hs_dk_tren_hd * 1 != this.tongso_hs * 1) {
          this.$toast.error('Tổng số học sinh đăng ký trên hợp đồng là: ' + this.sl_hs_dk_tren_hd + '.Hãy chọn đúng số lượng học sinh như đã đăng ký trên hợp đồng')
          return
        } else {
          const res = await this.ConvertResApi(api.kichhoat_dv_hocbadientu(this.axios, { hdtb_id: this.hdtb_id }))
          this.loading(false)
          const data_res = JSON.parse(res)
          if (data_res.ErrorCode == '00') {
            this.$toast.success('Đăng ký thành công')
            this.is_disabled = !this.is_disabled
          } else {
            this.$toast.error('Kích hoạt không thành công: ' + data_res.TransactionId + data_res.data)
          }
        }
      } catch (err) {
        this.loading(false)
        this.$toast.error(err)
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>
<style>
.chuyenTo .page-content {
  top: 130px !important;
}

.box-full-height {
  height: 100%;
}

.e-gridcontent {
  height: 100% !important;
}
</style>
