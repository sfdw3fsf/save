<template>
  <div class="main-wrapper">
    <div class="breadcrumb-top">
      <!-- <breadcrumb :header="header" /> -->
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click="btnNhapMoi" :class="{ disabled: !enabledBtnNhapMoi }"> <span class="icon one-file-plus"></span>Nhập mới </a>
        </li>

        <li>
          <a @click="btnGhiLai" :class="{ disabled: !enabledBtnGhiLai }"> <span class="icon one-save"></span>Ghi lại </a>
        </li>

        <li>
          <!-- <a @click="btnXoa" :class="{ disabled: !enabledBtnHuy }"> <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy </a> -->
        </li>

        <li>
          <a @click="btnXoa" :class="{ disabled: !enabledBtnDelete }"> <span class="icon one-trash"></span>Xóa </a>
        </li>
      </ul>
    </div>
    <!-- content page -->
    <div class="page-content">
      <!-- thong tin uu dai -->
      <div class="information-thue-bao-uu-dai">
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="box-form">
              <div class="legend-title">
                <div class="title-main">Thông tin sản phẩm</div>
              </div>

              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="col-sm-12 col-12">
                    <div class="info-row">
                      <div class="key w100">Mã SP</div>
                      <div class="value color-number-input">
                        <input type="text" class="form-control text-bold  disable-input" v-model="maSP" :class="{ disabled: true }" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 col-12">
                    <div class="info-row">
                      <div class="key w100">Tên Sản Phẩm</div>
                      <div class="value color-number-input">
                        <input type="text" class="form-control  text-bold" v-model="tenSP" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 col-12">
                    <div class="info-row">
                      <div class="key w100">Loại SP</div>
                      <div class="value color-number-input ">
                        <SelectExt :dataSource="categories" v-model="loaiSP" :allowFiltering="true" dataTextField="ten_loai_sp" dataValueField="ma_loai_sp" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 col-12">
                    <div class="info-row">
                      <div class="key w100">Mô tả</div>
                      <textarea v-model="ghiChuSP" name="" class="form-control" style="height: 100px; resize: none"></textarea>
                    </div>
                  </div>
                </div>
                <!-- Giá -->
                <div class="col-sm-6 col-12">
                  <div class="col-sm-12 col-12">
                    <div class="info-row">
                      <div class="key w100">Giá Sản phẩm</div>
                      <div class="value color-number-input">
                        <input type="text" class="form-control tright text-bold" v-model="giaSP" @blur="formatCurrencyGiaSP" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 col-12">
                    <div class="info-row">
                      <div class="key w100">Thuế</div>
                      <div class="value color-number-input">
                        <input type="number" class="form-control tright text-bold" v-model="thueSP" />
                      </div>
                    </div>
                  </div>

                  <div class="col-sm-12 col-12">
                    <div class="row">
                      <div class="col-sm-6 col-12">
                        <div class="info-row">
                          <div class="key w100">Chiết khấu</div>
                          <div class="value color-number-input">
                            <input type="number" class="form-control tright text-bold" v-model="chietKhauSP" />
                          </div>
                        </div>
                      </div>
                      <div class="col-sm-6 col-12">
                        <div class="info-row">
                          <div class="key w100">Số tiền giảm giá mặc định</div>
                          <div class="value color-number-input">
                            <input type="number" class="form-control tright text-bold" v-model="soTienGiam" />
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="col-sm-12 col-12">
                    <div class="row">
                      <div class="col-sm-6 col-12">
                        <div class="info-row">
                          <div class="key w100">Ngày có hiệu lực</div>
                          <div class="value color-number-input">
                            <date-picker style="width: 98%; margin-left: 4px" format="hh:mm:ss dd/MM/YYYY" v-model="ngayHieuLuc"></date-picker>
                          </div>
                        </div>
                      </div>
                      <div class="col-sm-6 col-12">
                        <div class="info-row">
                          <div class="key w100">Ngày hết hạn</div>
                          <div class="value color-number-input">
                            <date-picker style="width: 98%; margin-left: 4px" format="hh:mm:ss dd/MM/YYYY" v-model="ngayHetHieuLuc"></date-picker>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 col-12">
                    <div class="info-row">
                      <vue-select v-model="isActive" :labelWidth="'100'" :options="statusList" labelField="name" valueField="value" label="Trạng Thái" :disable="isInsertRecord === 1 ? true : false"></vue-select>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="box-form">
        <div class="col-sm-12 col-12">
          <div class="legend-title">
            <div class="title-main">Danh sách sản phẩm</div>

            <!-- danh sach thue bao -->
            <div class="tab-pane active" id="tab1">
              <div class="table-content">
                <DataGrid
                  :columns="products.columns"
                  :dataSource="products.dataSource"
                  :showFilter="true"
                  :allowPaging="true"
                  :showColumnCheckbox="false"
                  :enabledSelectFirstRow="true"
                  :enablePagingServer="false"
                  :totalRecords="products.dataSource.length"
                  @selectedItemsChanged="selectedItemsChangedCG"
                >
                </DataGrid>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>



<script>
import DatePicker from 'vue2-datepicker'
import api from './api.js'
import moment from 'moment'
import util from './UtilsJS'
// import ThongTinTimKiemSP from './ThongTinTimKiem.vue'
export default {
  components: {
    DatePicker
  },
  data() {
    return {
      /* data header action */
      header: {
        title: 'Danh sach danh muc san pham',
        list: [
          { name: 'Danh mục sản phẩm', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      products: {
        columns: [
          {
            fieldName: 'ma_sp',
            headerText: 'Mã Sản Phẩm',
            allowFiltering: true
          },
          {
            fieldName: 'ten_sp',
            headerText: 'Tên Sản Phẩm',
            allowFiltering: true
          },
          {
            fieldName: 'mo_ta_sp',
            headerText: 'Mô tả',
            allowFiltering: true
          },

          {
            fieldName: 'trang_thai',
            headerText: 'Trạng Thái',
            allowFiltering: true
          },
          {
            fieldName: 'ngay_tao',
            headerText: 'Ngày Tạo',
            allowFiltering: true
          },
          {
            fieldName: 'ma_nguoi_cn',
            headerText: 'Người cập nhât',
            allowFiltering: true
          },
          {
            fieldName: 'ngay_cn',
            headerText: 'Ngày Cập nhật',
            allowFiltering: true
          }
        ],
        dataSource: []
      },
      categories: [],
      statusList: [
        { name: 'Active', value: 1 },
        { name: 'DeActive', value: 0 }
      ],
      isActive: 1,
      isInsertRecord: 1,
      enabledBtnNhapMoi: true,
      enabledBtnDelete: true,
      enabledBtnGhiLai: true,

      //data
      ngayHieuLuc: null,
      ngayHetHieuLuc: null,
      maSP: '',
      loaiSP: '',
      tenSP: '',
      ghiChuSP: '',
      giaSP: 0,
      thueSP: 0,
      chietKhauSP: 0,
      soTienGiam: 0,
      currentRecord: null,
      numberGiaSP : 0,
    }
  },
  async created() {
    await this.getListProducts()
    await this.getListCateProduct()
  },
  watch: {
    isInsertRecord(val) {
      if (val === 1) {
        this.enabledBtnNhapMoi = false
        this.enabledBtnGhiLai = true
        // this.enabledBtnHuy = true
        this.enabledBtnDelete = false
      }
      if (val === 0) {
        // this.enabledBtnNhapMoi = true
        this.enabledBtnGhiLai = true
        // this.enabledBtnHuy = false
        this.enabledBtnDelete = true
        this.enabledBtnDelete = true
      }
    },
    currentRecord(val) {
      if (val) {
        this.maSP = val.ma_sp
        this.loaiSP = val.ma_loai_sp
        this.tenSP = val.ten_sp
        this.giaSP = util.formatCurrency(val.gia_sp) //hiển thị trên FE
        this.numberGiaSP = val.gia_sp //là sô
        this.ghiChuSP = val.mo_ta_sp
        this.chietKhauSP = val.chiet_khau
        this.thueSP = val.phan_tram_thue
        this.soTienGiam = val.so_tien_giam_gia
        this.ngayHieuLuc = new Date(val.ngay_hieu_luc)
        this.ngayHetHieuLuc = new Date(val.ngay_het_hieu_luc)
        this.isActive = val.trang_thai === 'Active' ? 1 : 0
      } else {
        this.maSP = ''
        this.loaiSP = ''
        this.tenSP = ''
        this.giaSP = 0
        this.ghiChuSP = ''
        this.chietKhauSP = 0
        this.thueSP = 0
        this.soTienGiam = 0
        this.ngayHieuLuc = null
        this.ngayHetHieuLuc = null
        this.isActive = 1
        //  return this.$toast.error('Không tìm thấy bản ghi nào! ')
      }
    }
  },
  computed: {},
  methods: {
    async btnGhiLai() {
      //check validate form
      if (!this.validateForm) {
        return
      }

      this.$root.loading(true) //loading

      let params = {}
      if (this.isInsertRecord === 1) {
        const responseMaSP = await api.getCodeProducts(this.axios)
        if (responseMaSP && responseMaSP.data && responseMaSP.data.error_code && responseMaSP.data.error_code === 'BSS-00000000') {
          const maSP = responseMaSP.data.data

          params = {
            is_insert: this.isInsertRecord,
            ma_loai_sp: this.loaiSP,
            ma_sp: maSP,
            ten_sp: this.tenSP,
            mo_ta_sp: this.ghiChuSP,
            gia_sp: this.numberGiaSP,
            phan_tram_thue: this.thueSP,
            chiet_khau: this.chietKhauSP,
            ngay_hieu_luc: moment(this.ngayHieuLuc).format('DD/MM/yyyy HH:mm:ss'),
            ngay_het_hieu_luc: moment(this.ngayHetHieuLuc).format('DD/MM/yyyy HH:mm:ss'),
            so_tien_giam_gia: this.soTienGiam
          }
        } else {
          S
          this.$toast.error(response.data.message)
          return
        }
      } else {
        params = {
          is_insert: this.isInsertRecord,
          ma_loai_sp: this.loaiSP,
          ma_sp: this.maSP,
          ten_sp: this.tenSP,
          mo_ta_sp: this.ghiChuSP,
          gia_sp: this.numberGiaSP,
          phan_tram_thue: this.thueSP,
          chiet_khau: this.chietKhauSP,
          ngay_hieu_luc: moment(this.ngayHieuLuc).format('DD/MM/yyyy HH:mm:ss'),
          ngay_het_hieu_luc: moment(this.ngayHetHieuLuc).format('DD/MM/yyyy HH:mm:ss'),
          so_tien_giam_gia: this.soTienGiam,
          trang_thai: this.isActive
        }
      }
      const response = await api.insertUpdateProduct(this.axios, params)

      this.$root.loading(false) //end loading

      if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
        await this.getListProducts()
        this.$toast.success('Ghi lại dữ liệu thành công')
      } else {
        this.$toast.error(response.data.message)
        return
      }
    },

    async btnNhapMoi() {
      this.maSP = ''
      this.loaiSP = ''
      this.tenSP = ''
      this.giaSP = ''
      this.ghiChuSP = ''
      this.chietKhauSP = 0
      this.thueSP = 0
      this.soTienGiam = 0
      this.ngayHieuLuc = null
      this.ngayHetHieuLuc = null
      this.isActive = 1
      this.isInsertRecord = 1
    },
    async btnXoa() {
      this.$bvModal
        .msgBoxConfirm(`Bạn có muốn xoá bản ghi hiện tại không?`, {
          title: 'Xác nhận hành động',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then((v) => {
          if (v) {
            api
              .deleteProduct(this.axios, { ma_sp: this.maSP })
              .then(async (response) => {
                if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  this.$toast.success('Xoa ban ghi thành công')
                  await this.getListProducts()
                } else {
                  this.$toast.error(response.data.message)
                }
              })
              .catch((error) => {
                console.log(error)
              })
          }

          return v
        })
    },

    async getListProducts() {
      try {
        const response = await api.getListProducts(this.axios)

        if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.products.dataSource = response.data.data

          if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            const data = response.data.data.map((item) => {
              if (item.trang_thai == 1) {
                item.trang_thai = 'Active'
              } else {
                item.trang_thai = 'Deactive'
              }
              return item
            })

            this.products.dataSource = data
          }
        }
      } catch (error) {
        console.log(error)
      }
    },
    async getListCateProduct() {
      const response = await api.getListProductCategories(this.axios)
      if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
        const data = response.data.data.filter((item) => item.trang_thai == 1)

        this.categories = data
        console.log(this.categories)
      }
    },
    selectedItemsChangedCG(obj) {
      if (!obj) return
      this.isInsertRecord = 0
      this.currentRecord = obj[0]
    },
    validateForm() {
      if (!this.tenSP) {
        this.$toast.error('Tên không được bỏ trống!')
        return false
      }
      if (!this.loaiSP) {
        this.$toast.error('Loại sản phẩm không được bỏ trống!')
        return false
      }
      if (!this.giaSP) {
        this.$toast.error('Giá sản phẩm không được bỏ trống!')
        return false
      }
      if (!this.thueSP) {
        this.$toast.error('Thuế không được bỏ trống!')
        return false
      }
      if (!this.chietKhauSP) {
        this.$toast.error('Chiết không được bỏ trống!')
        return false
      }
      if (!this.soTienGiam) {
        this.$toast.error('Số tiền giảm không được bỏ trống!')
        return false
      }
      if (!this.ngayHieuLuc) {
        this.$toast.error('Ngày hiệu lực không được bỏ trống!')
        return false
      }
      if (!this.ngayHetHieuLuc) {
        this.$toast.error('Ngày hết hiệu lực không được bỏ trống!')
        return false
      }

      return true
    },
    /* format currency VND muc cuoc tb */
    formatCurrencyGiaSP() {
      const currency = util.formatCurrency(this.giaSP)
      if (currency === 'NaN') {
        this.$toast.error('Giá  SP  phải là số!')
        return
      }
      this.numberGiaSP = parseInt(this.giaSP)
      this.giaSP = currency
    }
  }
}
</script>
<style scoped>
  .disable-input{
    background-color:ivory;
  }
</style>