<template>
  <ejs-dialog :enableResize="true" :isModal="true" allowDragging="true" zIndex="1000" :visible="false" ref="dialogHuyHocSinhHBDT" :header="this.titleName" showCloseIcon="true" target="#app .main-wrapper">
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
            <div class="legend-title mart20">Danh sách học sinh đăng ký hủy</div>

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
                    <input type="text" v-model="namhoc" class="form-control" disabled />
                  </div>
                </div>
              </div>
            </div>
            <DataGrid :panelDataHeight="'45vh'" ref="DSHSHuyDangKy" v-bind:columns="grcDSHocSinhDangKy.cols" v-bind:dataSource="grcDSHocSinhDangKy.list" :enable-paging-server="false" :allowPaging="false" :showFilter="true" :showColumnCheckbox="true" :enabledSelectFirstRow="false"> </DataGrid>
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
            <div class="legend-title mart20">Danh sách học sinh đã đăng ký</div>

            <div class="info-row">
              <div class="key w90">
                <span class="name">Lớp học</span>
              </div>

              <div class="value">
                <ComboboxGrid class="select-custom" :columns="[{ fieldName: 'ten_lop', headerText: 'Tên lớp học', allowFiltering: true, width: '300' }]" :dataSource="cboLopHoc.list" textField="ten_lop" valueField="ten_lop" @selectedChanged="comboxChangeLopHoc"> </ComboboxGrid>
              </div>
            </div>

            <DataGrid :panelDataHeight="'45vh'" ref="DSHSDangKy" v-bind:columns="grcDanhSachHocSinh.cols" v-bind:dataSource="grcDanhSachHocSinh.list" :enable-paging-server="false" :allowPaging="false" :showFilter="true" :showColumnCheckbox="true" :enabledSelectFirstRow="false"> </DataGrid>

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
      cboLopHoc: {
        list: [],
        value: ''
      },
      id_truong: '',
      hdtb_id: '',
      thuebao_id: '',
      tongso_hs: 0,
      ds_hsdadk: [],
      sl_hs_dk_tren_hd: '',
      kieuld_id: '',
      is_disabled: true,
      tongso_hs_trong_db: '',
      namhoc: '',
      titleName: ''
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
    'cboLopHoc.value'(newValue, oldValue) {
      console.log('Giá trị mới: ' + newValue + ' Giá trị cũ: ' + oldValue)
      if (newValue != oldValue && newValue != null) this.LayDBHocSinhDaDK(newValue)
    }
  },

  methods: {
    async openDialog(data) {
      if (!data || !data.hdtb_id) {
        return
      }
      console.log(this.$refs)
      console.log(this.$refs['dialogHuyHocSinhHBDT'])
      this.hdtb_id = data.hdtb_id
      this.id_truong = data.id_truong
      this.thuebao_id = data.thuebao_id
      this.sl_hs_dk_tren_hd = data.sl_hocsinh
      this.kieuld_id = data._kieuld_id
      this.titleName = data.titleName
      this.$refs['dialogHuyHocSinhHBDT'].show()
      await this.formLoad()
    },
    async get_tongso_hd() {
      this.tongso_hs = this.grcDSHocSinhDangKy.list.length
    },
    async formLoad() {
      try {
        this.loading(true)
        await this.LayDSLop()
        await this.LayDBHocSinhDaDK('Tất cả')
        await this.LayDSHsDaDk()
        await this.get_tongso_hd()
        await this.Lay_NamHoc_DangKy()
      } catch (err) {
        this.loading(false)
        this.$toast.error(err)
      }
      this.loading(false)
    },
    async LayDSLop() {
      const rs = await this.ConvertResApi(
        api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
          p_nghiepvu: 'GET_TENLOP_DB_THUEBAO_CT',
          p_ds_para: '{"THUEBAO_ID":' + this.thuebao_id + '}'
        })
      )
      if (rs) this.cboLopHoc.list = rs
    },
    async LayDBHocSinhDaDK(data) {
      const rs = await this.ConvertResApi(
        api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
          p_nghiepvu: 'GET_DB_THUEBAO_CT_THEO_THUEBAO_ID',
          p_ds_para: '{"THUEBAO_ID":' + this.thuebao_id + '}'
        })
      )
      if (data === 'Tất cả') {
        if (rs) {
          this.grcDanhSachHocSinh.list = rs
          this.tongso_hs_trong_db = rs.length
        }
      } else {
        if (rs) this.grcDanhSachHocSinh.list = rs.filter((item) => item.ten_lop == data)
      }
    },
    async LayDSHsDaDk() {
      const rs = await this.ConvertResApi(api.lay_ds_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.hdtb_id }))
      if (rs) this.grcDSHocSinhDangKy.list = rs
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
    comboxChangeLopHoc(item) {
      this.cboLopHoc.value = item.ten_lop
    },

    btnDangKy_Click() {
      this.loading(true)
      try {
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
          this.loading(false)
        }
      } catch (err) {
        this.loading(false)
        this.$toast.error(err.message)
      } finally {
        this.loading(false)
      }
    },
    btnHuyDK_Click() {
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
          const rs = this.ConvertResApi(api.them_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.hdtb_id, vds_hdtb_ct: this.grcDSHocSinhDangKy.list }))
          this.loading(false)
        }
      } catch (err) {
        this.loading(false)
        this.$toast.error(err.message)
      } finally {
        this.loading(false)
      }
    },
    async btnGhiLai_ItemClick() {
      try {
        this.loading(true)
        if (this.grcDSHocSinhDangKy.list.length <= 0) {
          this.$toast.error('Không có học sinh đăng ký')
          return
        } else {
          const rs = await this.ConvertResApi(api.them_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.hdtb_id, vds_hdtb_ct: this.grcDSHocSinhDangKy.list }))
          console.log(rs)
          debugger
          this.loading(false)
          if (rs == 'ok') {
            this.$toast.success('Cập nhật thành công')
            return
          } else {
            this.$toast.error('Cập nhật không thành công: ' + rs.data)
            return
          }
        }
      } catch (err) {
        this.loading(false)
        this.$toast.error(err.message)
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
            this.get_tongso_hd()
            return
          } else {
            this.$toast.error('Xóa danh sách không thành công')
            return
          }
        }
      } catch (err) {
        this.loading(false)
        this.$toast.error(err.message)
      } finally {
        this.loading(false)
      }
    },
    async Lay_NamHoc_DangKy() {
      try {
        this.loading(true)
        const data = await api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
          p_nghiepvu: 'LAY_NAMHOC_HOCBADIENTU',
          p_ds_para: '{"vhdtb_id":' + this.hdtb_id + '}'
        })
        console.log('Lay_NamHoc_DangKy')
        console.log(data)
        console.log(data.data.data[0].thoihan_id)
        // console.log(data.data[0].thoihan_id)

        this.namhoc = data.data.data[0].thoihan_id
      } catch (err) {
        this.$toast.error('Đã có lỗi xả ra: ' + err.message)
      } finally {
        this.loading(false)
      }
    },
    async btnKichHoat_ItemClick() {
      try {
        this.loading(true)
        if (this.sl_hs_dk_tren_hd * 1 != this.tongso_hs * 1) {
          this.$toast.error('Tổng số học sinh đăng ký trên hợp đồng là: ' + this.sl_hs_dk_tren_hd + '.Hãy chọn đúng số lượng học sinh như đã đăng ký trên hợp đồng')
          return
        } else {
          /*13189: Chuyến dùng chính thức*/
          if (this.kieuld_id == 13189) {
            if (this.tongso_hs_trong_db != this.tongso_hs) {
              this.$toast.error('Vui lòng chuyển dùng chính thức tất cả các học sinh trong danh bạ')
              return
            }
            const res = await this.ConvertResApi(api.kichhoat_dv_hocbadientu(this.axios, { hdtb_id: this.hdtb_id }))
            const data_res = JSON.parse(res)
            if (data_res.ErrorCode == '00') {
              this.$toast.success('Chuyển dùng chính thức thành công ' + this.tongso_hs + ' học sinh')
              this.is_disabled = !this.is_disabled
            } else {
              this.$toast.error('Chuyển dùng chính thức không thành công: ' + data_res.TransactionId + data_res.data)
            }
          } else {
            const res = await this.ConvertResApi(api.huyhocsinh(this.axios, { hdtb_id: this.hdtb_id }))
            this.loading(false)
            const data_res = JSON.parse(res)
            if (data_res.ErrorCode == '00') {
              this.$toast.success('Hủy học sinh thành công')
              this.is_disabled = !this.is_disabled
            } else {
              this.$toast.error('Kích hoạt không thành công: ' + data_res.TransactionId)
            }
          }
        }
      } catch (err) {
        this.loading(false)
        this.$toast.error(err.message)
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
