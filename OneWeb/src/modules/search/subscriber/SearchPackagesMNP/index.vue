<template>
  <div>
    <vue-bread-crumb :header="header" />
    <!-- menu -->
    <vue-nav>
      <ul class="list">
        <li :class="{ 'none-active': soTb.length === 0 }" @click="() => (soTb.length === 0 ? null : lamMoiFormNhap())">
          <a> <span class="one-slid"></span> Nhập mới </a>
        </li>
        <li :class="{ 'none-active': disableTraCuu }" @click="() => (disableTraCuu ? null : layThongTinGoiCuoc())">
          <a> <span class="one-search"></span> Tra cứu </a>
        </li>
        <!-- @click="btnTraCuu_Click" -->
      </ul>
    </vue-nav>
    <!-- form -->
    <div class="px-4 pt-3">
      <div class="row">
        <div class="box-form">
          <div class="legend-title">
            Thông tin tra cứu
          </div>
          <div class="row">
            <div class="col-sm-2 col-12">
              <div class="info-row">
                <div class="key w110">
                  <div class="check-action">
                    <!-- <input type="checkbox" v-model="isDoanhNghiep" class="check" @change="changeIsDoanhNghiep" /> -->
                    <input type="checkbox" v-model="isDoanhNghiep" class="check" :disabled="disableThongTinSoTb" />
                    <span class="name">Doanh nghiệp</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w110">Số thuê bao <span class="red">(*)</span></div>
                <div class="value w20">
                  <button class="btn btn-primary">84</button>
                </div>
                <div class="value">
                  <!-- <input v-on:keyup.enter="() => Check()" v-model="txtMaTB" type="number" class="form-control" /> -->
                  <input v-model="soTb" type="number" class="form-control" @keyup.enter="() => kiemTraTb()" :disabled="disableThongTinSoTb" placeholder="Nhập số điện thoại nhấn Enter để kiểm tra" />
                </div>
              </div>
            </div>

            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w90">Port In/Out</div>
                <div class="value">
                  <div class="select-custom">
                    <SelectExt v-model="port" dataTextField="text" dataValueField="value" :dataSource="ports" :disabled="disableTraCuu"></SelectExt>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w90">Phương án</div>
                <div class="value">
                  <div class="select-custom">
                    <SelectExt v-model="phuongAn" dataTextField="text" dataValueField="value" :dataSource="phuongAns" :disabled="disableTraCuu"> </SelectExt>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="box-form">
          <div class="legend-title">Thông tin chi tiết</div>
          <div class="table-content">
            <DataGrid :columns="gridChiTiet.header" ref="grvLog" :dataSource="gridChiTiet.data" :enable-paging-server="false" :allowPaging="true" :showFilter="true"> </DataGrid>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
//import define from './define'
import { trimStart } from 'lodash'
export default {
  data() {
    return {
      //...define,
      header: {
        title: 'TRA CỨU GÓI CƯỚC MNP',
        list: [
          {
            name: 'Tra cứu',
            link: '/search'
          },
          {
            name: 'Tra cứu gói cước mnp',
            link: '/search/SearchPackagesMNP'
          }
        ]
      },
      soTb: '',
      isDoanhNghiep: false,
      disableTraCuu: true,
      port: '1',
      phuongAn: '1',
      ports: [],
      phuongAns: [],
      gridChiTiet: {
        data: [],
        header: [
          { fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting: true, width: '80' },
          { fieldName: 'PACKAGE', headerText: 'Tên gói', allowFiltering: true, allowSorting: true, width: 'auto' },
          { fieldName: 'DESCRIPTION', headerText: 'Mô tả', allowFiltering: true, allowSorting: true, width: 'auto' }
        ]
      }
    }
  },
  mounted() {
    this.ports = [
      {
        text: 'PORT IN',
        value: '1'
      },
      {
        text: 'PORT OUT',
        value: '2'
      }
    ]
    this.phuongAns = [
      {
        text: 'Phương án 1',
        value: '1'
      },
      {
        text: 'Phương án 2',
        value: '2'
      },
      {
        text: 'Phương án 3',
        value: '3'
      }
    ]
  },

  computed: {
    //Trim số 0 của thuê bao nhập vào và thêm đầu 84
    soTbTrim() {
      return '84' + trimStart(this.soTb, '0')
    },
    //Url kiểm tra số thuê bao theo biến KHDN hoặc cá nhân
    urlKiemTraTb() {
      return this.isDoanhNghiep ? 'ccbs/executor/ts_mnp_check_tb_khdn' : 'ccbs/executor/ts_mnp_check_tb'
    },
    //Url kiểm tra thông tin gói cước theo biến KHDN hoặc cá nhân
    urlLayThongTinGoiCuoc() {
      return this.isDoanhNghiep ? 'ccbs/executor/ts_mnp_ds_khdn_goicuoc' : 'ccbs/executor/ts_mnp_ds_goicuoc'
    },
    //Tham số lấy thông tin gói cước, để watch thay đổi sẽ gọi api lấy thông tin
    thamSoLayThongTinGoiCuoc() {
      return {
        porttype: this.port,
        so_tb: this.soTbTrim,
        plantype: this.phuongAn
      }
    },
    //Khi thực hiện tra cứu thì không cho thay đổi số điện thoại tra cứu hoặc lựa chọn doanh nghiệp/cá nhân
    //Muốn thay đổi phải bấm nút nhập mới
    disableThongTinSoTb() {
      return !this.disableTraCuu
    }
  },

  //Watch thay đổi port hay phương án để gọi hàm lấy thông tin gói cước
  watch: {
    thamSoLayThongTinGoiCuoc: async function(n, o) {
      if (this.disableTraCuu) return
      await this.layThongTinGoiCuoc()
    }
  },

  methods: {
    //Nhập số thuê bao vào nhấn enter để kiểm tra
    async kiemTraTb() {
      if (this.soTb.length === 0) {
        this.$toast.warning('Vui lòng nhập thông tin tìm kiếm!')
        return
      }

      try {
        this.loading(true)
        const { data } = await this.axios.post(this.urlKiemTraTb, {
          so_tb: this.soTbTrim
        })

        if (!data.error_code || data.error_code !== this.success_code || data.data === null) {
          this.$toast.error(data.message)
          return
        }

        //Nếu kết quả trả về số thuê bao hợp lệ thì enable nút tra cứu và combobox chọn phương án đồng thời disable ô nhập số thuê bao cho đến khi nhấn nút nhập mới
        this.disableTraCuu = false
        this.port = data.data.result //Kết quả api trả về gán vào giá trị của port
      } catch (e) {
        this.$toast.error('Lỗi')
      } finally {
        this.loading(false)
      }
    },

    async layThongTinGoiCuoc() {
      try {
        this.loading(true)
        const { data } = await this.axios.post(this.urlLayThongTinGoiCuoc, this.thamSoLayThongTinGoiCuoc)

        if (!data.error_code || data.error_code !== this.success_code || data.data === null) {
          this.$toast.error(data.message)
          return
        }

        //Dữ liệu trả về đẩy vào trong grid, thêm property Số thứ tự vào
        this.gridChiTiet.data = data.data.map((d, idx) => ({ ...d, STT: idx + 1 }))
      } catch (e) {
        this.$toast.error('Lỗi!')
      } finally {
        this.loading(false)
      }
    },

    lamMoiFormNhap() {
      this.soTb = ''
      this.disableTraCuu = true
      this.gridChiTiet.data = []
    }

    // loadPort() {
    //   this.listPort = this.cboPort
    //   this.form.port = this.listPort[0].value
    // },
    // changeIsDoanhNghiep() {
    //   this.txtMaTB = null
    //   this.grvTTChiTiet.data = null
    //   this.cboPhuongAn = null
    //   this.isPhuongAn = false
    // },
    // onChangePhuongAn() {
    //   this.TimKiem()
    // },
    // btnTraCuu_Click() {
    //   this.TimKiem()
    // },
    // async TimKiem() {
    //   try {
    //     if (this.txtMaTB == null) {
    //       this.ShowWarning('Nhập thông tin tìm kiếm!')
    //       return false
    //     }
    //     this.loading(true)
    //     this.grvTTChiTiet.data = null
    //     let sotb = '84' + this.txtMaTB
    //     let resTimKiem = []
    //     if (this.isDoanhNghiep) {
    //       resTimKiem = await this.axios.post('ccbs/executor/ts_mnp_ds_khdn_goicuoc', {
    //         porttype: this.form.port,
    //         so_tb: sotb,
    //         plantype: this.form.phuongan
    //       })
    //     } else {
    //       resTimKiem = await this.axios.post('ccbs/executor/ts_mnp_ds_goicuoc', {
    //         porttype: this.form.port,
    //         so_tb: sotb,
    //         plantype: this.form.phuongan
    //       })
    //     }
    //     if (resTimKiem.data.error_code && resTimKiem.data.error_code === this.success_code) {
    //       let data = resTimKiem.data.data
    //       let dataGrid = []
    //       if (data != null) {
    //         for (let i = 0; i < data.length; i++) {
    //           dataGrid.push({
    //             STT: i + 1,
    //             PACKAGE: data[i].PACKAGE,
    //             DESCRIPTION: data[i].DESCRIPTION
    //           })
    //         }
    //         console.log('tra cuu:')
    //         this.grvTTChiTiet.data = dataGrid
    //         this.grvTTChiTiet.data = [...this.grvTTChiTiet.data]
    //       } else {
    //         this.ShowWarning('Không có dữ liệu tìm kiếm')
    //       }
    //     } else {
    //       this.ShowError('Không có dữ liệu tìm kiếm')
    //     }
    //     this.loading(false)
    //   } catch (err) {
    //     this.ShowError('Lỗi!')
    //     this.loading(false)
    //   }
    // },
    // async Check() {
    //   if (this.txtMaTB === null || this.txtMaTB.length === 0) {
    //     this.$toast.warning('Vui lòng nhập thông tin tìm kiếm!')
    //     return
    //   }
    //   this.Loading = true
    //   try {
    //     this.grvTTChiTiet.data = null
    //     const { data } = await this.axios.post(this.urlCheck, {
    //       so_tb: this.soMayTrim
    //     })
    //     if (data.data.result === '0') {
    //       this.$toast.error(data.message)
    //       return
    //     }
    //   } catch (e) {
    //     this.$toast.error('Lỗi!')
    //   } finally {
    //     this.Loading = false
    //   }
    //   // this.Loading = true
    //   // try {
    //   //   let vSoMay = ''
    //   //   if (this.txtMaTB != null) {
    //   //     vSoMay = '84' + this.txtMaTB
    //   //   }
    //   //   this.grvTTChiTiet.data = null
    //   //   let strCheck = ''
    //   //   let check = ''
    //   //   let msg = ''
    //   //   if (!this.isDoanhNghiep) {
    //   //     strCheck = await this.checkThueBao(vSoMay, false)
    //   //     check = strCheck[0].result
    //   //     msg = strCheck[0].msg
    //   //     switch (check) {
    //   //       case '1':
    //   //         this.isTimKiem = true
    //   //         this.form.port = 1
    //   //         this.isPhuongAn = true
    //   //         this.cboPhuongAn = this.cboPhuongAn12
    //   //         this.form.phuongan = this.cboPhuongAn[0].value
    //   //         break
    //   //       case '2':
    //   //         this.isTimKiem = true
    //   //         this.form.port = 2
    //   //         this.isPhuongAn = true
    //   //         this.cboPhuongAn = this.cboPhuongAn123
    //   //         this.form.phuongan = this.cboPhuongAn[0].value
    //   //         break
    //   //       default:
    //   //         this.ShowError(msg)
    //   //         break
    //   //     }
    //   //   } else {
    //   //     strCheck = await this.checkThueBao(vSoMay, true)
    //   //     check = strCheck[0].result
    //   //     msg = strCheck[0].msg
    //   //     switch (check) {
    //   //       case '1':
    //   //         this.isTimKiem = true
    //   //         this.form.port = 1
    //   //         this.isPhuongAn = true
    //   //         this.cboPhuongAn = this.cboPhuongAn12
    //   //         this.form.phuongan = this.cboPhuongAn[0].value
    //   //         break
    //   //       case '2':
    //   //         this.isTimKiem = true
    //   //         this.form.port = 2
    //   //         this.isPhuongAn = true
    //   //         this.cboPhuongAn = this.cboPhuongAn12
    //   //         this.form.phuongan = this.cboPhuongAn[0].value
    //   //         break
    //   //       default:
    //   //         this.ShowError(msg)
    //   //         break
    //   //     }
    //   //   }
    //   // } catch (err) {
    //   //   this.ShowError('Lỗi!')
    //   // }
    // },
    // async checkThueBao(thuebao, doanhnghiep) {
    //   let data = []
    //   if (doanhnghiep) {
    //     let res = await this.axios.post('ccbs/executor/ts_mnp_check_tb_khdn', {
    //       so_tb: thuebao
    //     })
    //     data.push({
    //       msg: res.data.message,
    //       result: res.data.data.result
    //     })
    //   } else {
    //     let rs = await this.axios.post('ccbs/executor/ts_mnp_check_tb', {
    //       so_tb: thuebao
    //     })
    //     data.push({
    //       msg: rs.data.message,
    //       result: rs.data.data.result
    //     })
    //   }
    //   return data
    // },
    // btnNhapMoi_Click() {
    //   this.isDoanhNghiep = false
    //   this.txtMaTB = null
    //   this.form.port = 1
    //   this.form.phuongan = null
    //   this.grvTTChiTiet.data = null
    // },
    // ShowError: function(msg) {
    //   this.$toast.error(msg)
    // },
    // ShowWarning: function(msg) {
    //   this.$toast.warning(msg)
    // },
    // ShowSuccess: function(msg) {
    //   this.$toast.success(msg)
    // }
  }
}
</script>
<style>
li.none-active a {
  color: #d3d3d3 !important;
}
button.none-active a {
  color: #d3d3d3 !important;
}
/* input::-webkit-outer-spin-button,
	input::-webkit-inner-spin-button {
	-webkit-appearance: none;
	margin: 0;
	}

	input[type=number] {
	-moz-appearance:textfield;
	} */
</style>
