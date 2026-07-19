<template>
  <div>
    <vue-bread-crumb :header="header" />
    <!-- menu -->
    <vue-nav>
      <ul class="list">
        <li @click="() => nhapMoi()">
          <a style="cursor: pointer"> <span class="icon one-file-plus"></span>Nhập mới </a>
        </li>
        <li @click="() => traCuu()">
          <a style="cursor: pointer"> <span class="icon one-search"></span>Tra cứu </a>
        </li>
      </ul>
    </vue-nav>
    <div class="px-2 pt-3">
      <div class="nav tabs tab-over">
        <a id="lichsutab" href="#tab1" data-toggle="tab" class="minw0 active" @click="() => (activeTab = 1)">Lịch sử gọi 5 số</a>
        <a href="#tab2" data-toggle="tab" class="minw0" @click="() => (activeTab = 2)">Log</a>
      </div>
      <div class="tab-content" style="height: calc(100% - 46px);">
        <div class="tab-pane fullh active" id="tab1">
          <div class="box-form">
            <div class="legend-title">Thông tin tra cứu</div>
            <div class="row">
              <div class="col-sm-4 col-12">
                <div class="row">
                  <div class="col-sm-8 col-12">
                    <div class="info-row">
                      <div class="key">Số máy <span class="red">(*)</span></div>
                      <div class="value w20">
                        <span class="form-control padt6 fw6 text">
                          84
                        </span>
                      </div>
                      <div class="value">
                        <input v-model="thongTinTraCuu.somay" id="somay" type="number" class="form-control highlight" @keyup.enter="() => traCuu()" placeholder="VD: 0912345678" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-4 col-12">
                    <div class="info-row">
                      <div class="key w20">Tháng <span class="red">(*)</span></div>
                      <div class="value">
                        <date-picker v-model="thongTinTraCuu.thang" :format="$consts.monthFormat" :value-type="$consts.monthFormatValue" type="month" placeholder="MM/YYYY">
                          <template #icon-calendar>
                            <span class="icon one-calendar"></span>
                          </template>
                        </date-picker>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key">Số tìm kiếm 1</div>
                  <div class="value">
                    <input v-model="thongTinTraCuu.soTimKiems[0]" type="number" class="form-control" />
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key">Số tìm kiếm 2</div>
                  <div class="value">
                    <input v-model="thongTinTraCuu.soTimKiems[1]" type="number" class="form-control" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key">Số tìm kiếm 3</div>
                  <div class="value">
                    <input v-model="thongTinTraCuu.soTimKiems[2]" type="Number" class="form-control" />
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key">Số tìm kiếm 4</div>
                  <div class="value">
                    <input v-model="thongTinTraCuu.soTimKiems[3]" type="number" class="form-control" />
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key">Số tìm kiếm 5</div>
                  <div class="value">
                    <input v-model="thongTinTraCuu.soTimKiems[4]" type="number" class="form-control" />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="box-form">
            <div class="legend-title">Kết quả</div>
            <div class="table-content">
              <DataGrid :columns="gridLichSuThueBao.columns" :dataSource="gridLichSuThueBao.rows" allowPaging showFilter></DataGrid>
            </div>
          </div>
        </div>
        <div class="tab-pane fullh" id="tab2">
          <div class="box-form">
            <div class="legend-title">Thông tin tra cứu</div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="row">
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w100">Từ ngày <span class="red">(*)</span></div>
                      <div class="value">
                        <div class="input-icon-right">
                          <date-picker v-model="thongTinTraCuu.tuNgay" :format="$consts.dateFormat" :value-type="$consts.dateFormatValue" type="datetime" placeholder="DD/MM/YYYY">
                            <template #icon-calendar>
                              <span class="icon one-calendar"></span>
                            </template>
                          </date-picker>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w100">Đến ngày <span class="red">(*)</span></div>
                      <div class="value">
                        <div class="input-icon-right">
                          <date-picker v-model="thongTinTraCuu.denNgay" :format="$consts.dateFormat" :value-type="$consts.dateFormatValue" type="datetime" placeholder="DD/MM/YYYY">
                            <template #icon-calendar>
                              <span class="icon one-calendar"></span>
                            </template>
                          </date-picker>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12"></div>
            </div>
          </div>
          <div class="box-form">
            <div class="legend-title">
              Kết quả
            </div>
            <div class="table-content">
              <DataGrid :columns="gridLog.columns" ref="grvLog" :dataSource="gridLog.rows" allowPaging showFilter></DataGrid>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import { trimStart } from 'lodash'
import { SUCCESS_CODE } from '@/constants.js'

export default {
  prop: [],
  components: {
    moment,
    DatePicker
  },
  data() {
    return {
      //Tham số route tới trang tra cứu
      header: {
        title: 'Tra cứu 5 số',
        list: [
          {
            name: 'Tra cứu',
            link: {
              name: '/search'
            }
          },
          {
            name: 'Tra cứu 5 số',
            link: {
              name: '/search/SearchFiveNumbers'
            }
          }
        ]
      },

      //Thông tin tra cứu người dùng input vào
      thongTinTraCuu: {
        somay: '',
        soTimKiems: ['', '', '', '', ''],
        thang: moment().format('MMYYYY'),
        tuNgay: moment().format('DD/MM/YYYY'),
        denNgay: moment().format('DD/MM/YYYY')
      },

      //Grid kết quả tra cứu 5 số
      gridLichSuThueBao: {
        rows: [],
        columns: [
          // {
          //   fieldName: 'sogoi',
          //   headerText: 'Chủ gọi',
          //   allowFiltering: true,
          //   allowSorting: true,
          //   width: 'auto'
          // },
          {
            fieldName: 'msisdn',
            headerText: 'Bị gọi',
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
          },
          {
            fieldName: 'count',
            headerText: 'Tổng số',
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
          }
        ]
      },

      //Grid log
      gridLog: {
        rows: [],
        columns: [
          {
            fieldName: 'ROW_STT',
            headerText: 'User neo',
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
          },
          {
            fieldName: 'USER_ID',
            headerText: 'Mã thuê bao',
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
          },
          {
            fieldName: 'LOG_DATE',
            headerText: 'Ngày log',
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
          },
          {
            fieldName: 'IP_',
            headerText: 'IP',
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
          },
          {
            fieldName: 'NAME',
            headerText: 'Menu',
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
          },
          {
            fieldName: 'MA_TB',
            headerText: 'Mã thuê bao',
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
          },
          {
            fieldName: 'GHICHU',
            headerText: 'Ghi chú',
            allowFiltering: true,
            allowSorting: true,
            width: 'auto'
          }
        ]
      },

      activeTab: 1

      // txtThang: null,
      // txtTuNgay: null,
      // txtDenNgay: null
    }
  },
  mounted() {
    //Focus vào ô nhập số máy khi vừa vào form
    document.getElementById('somay').focus()
  },
  computed: {
    //Trim số 0 đầu tiên và thêm đầu 84 vào số máy người dùng nhập
    somayTrim() {
      return '84' + trimStart(this.thongTinTraCuu.somay, '0')
    }
    // //Nối chuỗi các số tìm kiếm
    // soTimKiems() {
    //   return this.thongTinTraCuu.soTimKiems.filter((s) => s.length > 0).join(',')
    // }
  },
  methods: {
    async traCuu() {
      //Kiểm tra nếu như chưa nhập số máy hoặc chưa chọn tháng thì cảnh báo cho người dùng
      if (this.thongTinTraCuu.somay.length === 0 || (this.activeTab === 1 && this.thongTinTraCuu.thang === null) || (this.activeTab === 2 && (this.thongTinTraCuu.tuNgay === null || this.thongTinTraCuu.denNgay === null))) {
        this.$toast.warning('Thông tin tra cứu không hợp lệ! Hãy nhập dữ liệu vào các ô có dấu (*)')
        document.getElementById('somay').focus()
        return
      }

      //Kiểm tra độ dài của số điện thoại tối đa 10 kí tự
      if (this.thongTinTraCuu.somay.length > 10) {
        this.$toast.warning('Số máy tối đa 10 kí tự!')
        document.getElementById('somay').focus()
        return
      }

      //Đang ở tab nào thì tra cứu theo tab đó
      if (this.activeTab === 1) await this.traCuuLichSu()
      else await this.traCuuLog()

      // if (this.thongTinTraCuu.somay == null) {
      //   this.$toast.warning('Chưa nhập số máy tìm kiếm!')
      //   return false
      // }
      //this.options.grvLSThueBao = null
      //this.options.grvLog = null
      //this.TraCuu5So()
      //this.TraCuuLog()
    },

    async traCuuLichSu() {
      //Gửi request để lấy thông tin tra cứu 5 số
      try {
        this.loading(true)
        const res = await this.axios.post('ccbs/tracuu/tracuu_5so', {
          month: this.thongTinTraCuu.thang,
          msisdn: this.somayTrim
        })

        //Quăng lỗi nếu có
        if (res.data.error_code !== SUCCESS_CODE) throw { message: res.data.message }
        this.gridLichSuThueBao.rows = res.data.data.map((d) => ({ ...d, sogoi: this.somayTrim }))
      } catch (e) {
        this.$toast.error(e.message || 'Lỗi')
        this.gridLichSuThueBao.rows = []
      } finally {
        this.loading(false)
      }
    },

    async traCuuLog() {
      //Gửi request để lấy thông tin tra cứu log
      try {
        this.loading(true)
        const res = await this.axios.post('ccbs/executor/ts_tracuu_log_5so', {
          p_page: '1',
          tu_ngay: this.thongTinTraCuu.tuNgay,
          den_ngay: this.thongTinTraCuu.denNgay,
          so_tb: this.somayTrim,
          p_record: '100'
        })

        //Quăng lỗi nếu có
        if (res.data.error_code !== SUCCESS_CODE) throw { message: res.data.message }
        this.gridLog.rows = res.data.data
      } catch (e) {
        this.$toast.error(e.message || 'Lỗi')
        this.gridLog.rows = []
      } finally {
        this.loading(false)
      }
    },
    // async TraCuu5So() {
    //   try {
    //     let thang = moment(this.txtThang, 'MM/YYYY').format('MMyyyy')
    //     let sotb = '84' + this.thongTinTraCuu.somay
    //     let numbers = ''
    //     if (this.thongTinTraCuu.sotimkiem1 != null) {
    //       numbers = this.thongTinTraCuu.sotimkiem1 + ','
    //     }
    //     if (this.thongTinTraCuu.sotimkiem2 != null) {
    //       numbers = numbers + this.thongTinTraCuu.sotimkiem2 + ','
    //     }
    //     if (this.thongTinTraCuu.sotimkiem3 != null) {
    //       numbers = numbers + this.thongTinTraCuu.sotimkiem3 + ','
    //     }
    //     if (this.thongTinTraCuu.sotimkiem4 != null) {
    //       numbers = numbers + this.thongTinTraCuu.sotimkiem4 + ','
    //     }
    //     if (this.thongTinTraCuu.sotimkiem5 != null) {
    //       numbers = numbers + this.thongTinTraCuu.sotimkiem5
    //     }

    //     let res5So = await this.axios.post('ccbs/tracuu/ts_tracuu_5so', {
    //       b_numbers: numbers,
    //       month: thang,
    //       sp_tb: sotb
    //     })
    //     if (res5So.data.error_code && res5So.data.error_code === this.success_code) {
    //       let Obj = res5So.data.data
    //       let tbdData = []
    //       for (let i = 0; i < Obj.length; i++) {
    //         let val = []
    //         val.sogoi = sotb
    //         val.msisdn = Obj[i].msisdn
    //         val.count = Obj[i].count
    //         tbdData.push(val)
    //       }
    //       this.options.grvLSThueBao = tbdData
    //       console.log(tbdData)
    //       if (this.options.grvLSThueBao != null) {
    //         this.options.grvLSThueBao = [...this.options.grvLSThueBao]
    //         this.loading(false)
    //       }
    //     } else {
    //       this.$toast.error('Lỗi', res5So.message)
    //       this.loading(false)
    //     }
    //   } catch (err) {
    //     this.$toast.error('Lỗi!', err)
    //     this.loading(false)
    //   }
    // },
    // async TraCuuLog() {
    //   try {
    //     let sotb = '84' + this.thongTinTraCuu.somay
    //     let tungay = ''
    //     let denngay = ''
    //     if (this.txtTuNgay != null) {
    //       tungay = this.txtTuNgay
    //     }
    //     if (this.txtDenNgay != null) {
    //       denngay = this.txtDenNgay
    //     }
    //     let resLog = await this.axios.post('ccbs/executor/ts_tracuu_log_5so', {
    //       p_page: '1',
    //       tu_ngay: tungay,
    //       so_tb: sotb,
    //       p_record: '100',
    //       den_ngay: denngay
    //     })
    //     if (resLog.data.error_code && resLog.data.error_code == this.success_code) {
    //       this.options.grvLog = resLog.data.data
    //       if (this.options.grvLog != null) {
    //         this.options.grvLog = [...this.options.grvLog]
    //         this.loading(false)
    //       }
    //     } else {
    //       this.$toast.error('Lỗi load log', resLog.message)
    //       this.loading(false)
    //     }
    //   } catch (err) {
    //     this.$toast.error('Lỗi!', err)
    //     this.loading(false)
    //   }
    // },
    // Clear() {
    //   this.thongTinTraCuu.somay = null
    //   this.thongTinTraCuu.sotimkiem1 = null
    //   this.thongTinTraCuu.sotimkiem2 = null
    //   this.thongTinTraCuu.sotimkiem3 = null
    //   this.thongTinTraCuu.sotimkiem4 = null
    //   this.thongTinTraCuu.sotimkiem5 = null
    //   this.options.grvLSThueBao = null
    //   this.options.grvLog = null
    //   this.setDateNow()
    // },
    // toggleTimePanel() {
    //   this.showTimePanel = !this.showTimePanel
    // },
    // handleOpenChange() {
    //   this.showTimePanel = false
    // },
    // handleRangeClose() {
    //   this.showTimeRangePanel = false
    // },
    nhapMoi() {
      this.thongTinTraCuu = {
        somay: '',
        soTimKiems: ['', '', '', '', ''],
        thang: moment().format('MMYYYY'),
        tuNgay: moment().format('DD/MM/YYYY'),
        denNgay: moment().format('DD/MM/YYYY')
      }
      this.gridLichSuThueBao.rows = []
      this.gridLog.rows = []
      document.getElementById('lichsutab').click()
      document.getElementById('somay').focus()
    }
  }
}
</script>
<style>
.mx-datepicker {
  width: 100%;
}
.mx-icon-calendar {
  font-style: normal !important;
}
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

input[type='number'] {
  -moz-appearance: textfield;
}
</style>
