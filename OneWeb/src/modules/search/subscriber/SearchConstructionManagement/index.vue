<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li @click="clickButton('laythongtin')" :class="this.button.laythongtin ? 'active' : 'non-active'">
          <a> <span class="icon one-file-attach"></span>Lấy thông tin (F5)</a>
        </li>
      </ul>
    </vue-nav>

    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Thông tin tổng hợp</div>
        <div class="row">
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w90">Đơn vị</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt v-model="ddlDonVi" :options="this.options.donvi">
                    <option disabled value="0">Select one</option>
                  </SelectExt>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w120">Dịch vụ VT</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt v-model="ddlDichVuVT" :options="this.options.dichvuvienthong">
                    <option disabled value="0">Select one</option>
                  </SelectExt>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w90">Mới tiếp nhận</div>
              <div class="value">
                <input type="text" v-model="txtMoiTiepNhan" class="form-control tright" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w120">Chưa phân đơn vị</div>
              <div class="value">
                <input type="text" v-model="txtChuaPhanDonVi" class="form-control tright" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w120">Khai báo tổng đài</div>
              <div class="value">
                <input type="text" v-model="txtKhaiBaoTongDai" class="form-control tright" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w160">Tổng số chưa hoàn thành</div>
              <div class="value">
                <input type="text" v-model="txtTongSoChuaHoanThanh" class="form-control tright" />
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w90">Đã thanh toán</div>
              <div class="value">
                <input type="text" v-model="txtDaThanhToan" class="form-control tright" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w120">Đã phân đơn vị</div>
              <div class="value">
                <input type="text" v-model="txtDaPhanDonVi" class="form-control tright" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w120">Đã thi công xong</div>
              <div class="value">
                <input type="text" v-model="txtDaThiCongXong" class="form-control tright" value="0" />
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="box-form">
        <div class="legend-title">Thông tin theo loại hợp đồng</div>

        <div class="table-content">
          <DataGrid :columns="this.options.cols_grid_hopdong" v-bind:dataSource="this.options.danhsach_grid_hopdong" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabledSelectFirstRow="true"> </DataGrid>
        </div>
      </div>

      <div class="box-form">
        <div class="legend-title">Thông tin chi tiết</div>
        <div class="row">
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w80">Loại HĐ/PL</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt v-model="ddlLoaiHD_PL" :options="this.options.LoaiHopDong_PhuLuc">
                    <option disabled value="0">Select one</option>
                  </SelectExt>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="table-content">
          <DataGrid :columns="this.options.cols_grid_chitiet" v-bind:dataSource="this.options.danhsach_grid_chitiet" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabledSelectFirstRow="true"> </DataGrid>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import VueInput from '../../../search/subscriber/components/form/VueInput.vue'
import api from './api'
import define from './define'

export default {
  data() {
    return {
      ...define,
      txtMoiTiepNhan: 0,
      txtChuaPhanDonVi: 0,
      txtKhaiBaoTongDai: 0,
      txtTongSoChuaHoanThanh: 0,
      txtDaThanhToan: 0,
      txtDaPhanDonVi: 0,
      txtDaThiCongXong: 0,
      ddlDonVi: 0,
      ddlDichVuVT: 0,
      ddlLoaiHD_PL: 0,

      load: false,
      flag: false,
      dsHDTBChuaHoanThanh: [],
      dsTongHDTBChuaHoanThanh: [],
      dsDieuHanhThiCong: [],

      options: {
        donvi: [],
        dichvuvienthong: [],
        LoaiHopDong_PhuLuc: [],
        danhsach_grid_hopdong: [],
        danhsach_grid_chitiet: [],
        cols_grid_hopdong: [
          {
            fieldName: 'ten_loaihd',
            headerText: 'Loại HĐ/PL',
            allowFiltering: true,
            textAlign: 'left',
            allowSorting: true
          },
          {
            fieldName: 'moi_tn',
            headerText: 'Mới tiếp nhận',
            allowFiltering: true,
            textAlign: 'left',
            allowSorting: true
          },
          {
            fieldName: 'da_tt',
            headerText: 'Đã thanh toán',
            allowFiltering: true,
            textAlign: 'left',
            allowSorting: true
          },
          {
            fieldName: 'chua_pdv',
            headerText: 'Chưa phân ĐV',
            allowFiltering: true,
            textAlign: 'left',
            allowSorting: true
          },
          {
            fieldName: 'da_pdv',
            headerText: 'Đã phân ĐV',
            allowFiltering: true,
            textAlign: 'left',
            allowSorting: true
          },
          {
            fieldName: 'khaibao_td',
            headerText: 'Khai báo tổng đài',
            allowFiltering: true,
            textAlign: 'left',
            allowSorting: true
          },
          {
            fieldName: 'da_tc',
            headerText: 'Đã thi công',
            allowFiltering: true,
            textAlign: 'left',
            allowSorting: true
          },
          {
            fieldName: 'khac',
            headerText: 'Khác',
            allowFiltering: true,
            textAlign: 'left',
            allowSorting: true
          },
          {
            fieldName: 'tc',
            headerText: 'Tổng cộng',
            allowFiltering: true,
            textAlign: 'left',
            allowSorting: true
          }
        ],
        cols_grid_chitiet: [
          {
            fieldName: 'huonggiao',
            headerText: 'Hướng giao',
            allowFiltering: true,
            textAlign: 'left',
            allowSorting: true,
            width: '50'
          },
          {
            fieldName: 'ten_dv',
            headerText: 'Tên đơn vị',
            allowFiltering: true,
            textAlign: 'left',
            allowSorting: true
          },
          {
            fieldName: 'soluong',
            headerText: 'Số lượng',
            allowFiltering: true,
            textAlign: 'left',
            allowSorting: true
          }
        ]
      }
    }
  },

  mounted() {
    //this.LayTongHDTBChuaHoanThanh();
  },
  async created() {
    await this.getDanhSachDichVuVT()
    await this.getDanhSach_LoaiHopDong_PhuLuc()
    await this.getDanhSach_DonVi()
    await this.initLoadData()
    //this.LayTongHDTBChuaHoanThanh()
    //this.LayDanhSachHDTBChuaHoanThanh()
    //this.LayDanhSachDieuHanhThiCong()
  },
  watch: {
    ddlDichVuVT(newdata) {
      this.ddlDichVuVT = newdata
      this.LayTongHDTBChuaHoanThanh()
      this.LayDanhSachHDTBChuaHoanThanh()
      this.LayDanhSachDieuHanhThiCong()
    },
    ddlDonVi(newdata) {
      this.ddlDonVi = newdata
      this.LayTongHDTBChuaHoanThanh()
      this.LayDanhSachHDTBChuaHoanThanh()
      this.LayDanhSachDieuHanhThiCong()
    },
    ddlLoaiHD_PL(newdata) {
      this.ddlLoaiHD_PL = newdata
      this.LayDanhSachHDTBChuaHoanThanh()
    }
  },

  methods: {
    async getDanhSachDichVuVT() {
      const res = await api.getDanhSachDichVuVT(this.axios, {})

      let items = []
      res.data.data.forEach(function(item) {
        items.push({ id: item.DICHVUVT_ID, text: item.TEN_DVVT })
      })
      this.options.dichvuvienthong = items
      this.ddlDichVuVT = items[0].id
    },

    async getDanhSach_LoaiHopDong_PhuLuc() {
      const res = await api.getDanhSach_LoaiHopDong_PhuLuc(this.axios, {})

      let items = []
      items.push({ id: 0, text: 'Tất cả' })
      res.data.data.forEach(function(item) {
        items.push({ id: item.LOAIHD_ID, text: item.TEN_LOAIHD })
      })
      this.options.LoaiHopDong_PhuLuc = items
      this.ddlLoaiHD_PL = items[0].id
    },

    async getDanhSach_DonVi() {
      const res = await api.getDanhSach_DonVi(this.axios, {})

      let items = []
      res.data.data.forEach(function(item) {
        items.push({ id: item.donvi_id, text: item.ten_dv })
      })
      this.options.donvi = items
      this.ddlDonVi = items[0].id
    },

    async LayTongHDTBChuaHoanThanh() {
      try {
        let danhsach = this.dsTongHDTBChuaHoanThanh
        let temp = this.ddlDonVi
        let dvvt = this.ddlDichVuVT
        //console.log('this.ddlDonVi',this.ddlDonVi,'ds.dichvuvt_id',this.ddlDichVuVT)
        //console.log(danhsach)
        if (danhsach.length > 0) {
          let ds_ar = []
          let dsmoitiepnhan = []
          let chuaphandonvi = []
          let khaibaotongdai = []
          let dathanhtoan = []
          let daphandonvi = []
          let dathicongxong = []

          ds_ar = danhsach.filter(function(ds) {
            return ds.donvi_id == temp && ds.dichvuvt_id == dvvt
          })

          dsmoitiepnhan = ds_ar.filter(function(ds) {
            return ds.tthd_id == 1
          })

          console.log('dsmoitiepnhan', dsmoitiepnhan)

          chuaphandonvi = ds_ar.filter(function(ds) {
            return ds.tthd_id == 3
          })
          khaibaotongdai = ds_ar.filter(function(ds) {
            return ds.tthd_id == 9
          })
          dathanhtoan = ds_ar.filter(function(ds) {
            return ds.tthd_id == 2
          })
          daphandonvi = ds_ar.filter(function(ds) {
            return ds.tthd_id == 4
          })
          dathicongxong = ds_ar.filter(function(ds) {
            return ds.tthd_id == 5
          })

          this.txtMoiTiepNhan = dsmoitiepnhan.length
          this.txtChuaPhanDonVi = chuaphandonvi.length
          this.txtKhaiBaoTongDai = khaibaotongdai.length
          this.txtTongSoChuaHoanThanh = ds_ar.length
          this.txtDaThanhToan = dathanhtoan.length
          this.txtDaPhanDonVi = daphandonvi.length
          this.txtDaThiCongXong = dathicongxong.length + daphandonvi.length
        }
      } catch (e) {
        this.$toast.error('Có lỗi: ' + e)
      }
    },

    async LayDanhSachHDTBChuaHoanThanh() {
      let temp = this.ddlDonVi
      let dvvt = this.ddlDichVuVT
      let lhd = this.ddlLoaiHD_PL
      let kq = this.dsHDTBChuaHoanThanh
      let ar = kq.filter((element) => {
        return element.donvi_id == temp && element.dichvuvt_id == dvvt
      })
      //console.log('this.ddlLoaiHD_PL',this.ddlLoaiHD_PL)
      if (lhd != 0) {
        let ar_new = ar.filter((element) => {
          return element.loaihd_id == lhd
        })

        ar = ar_new
      }

      this.options.danhsach_grid_chitiet = ar
    },

    async LayDanhSachDieuHanhThiCong() {
      let temp = this.ddlDonVi
      let dvvt = this.ddlDichVuVT

      let kq = this.dsDieuHanhThiCong
      console.log('kq', temp, dvvt, kq)

      let ar = kq.filter((element) => {
        return element.donvi_id == temp && element.dichvuvt_id == dvvt
      })

      this.options.danhsach_grid_hopdong = ar
      console.log('danhsach_grid_hopdong-', temp, dvvt, this.options.danhsach_grid_hopdong)
    },

    async clickButton(key) {
      if (key == 'laythongtin') {
        await this.initLoadData()
      }
    },

    async initLoadData() {
      this.loading(true)
      const [resDieuHanhThiCong, resTongChuaHoanThanh, resHDTBChuaHoanThanh] = await Promise.all([
        api.LayDanhSachDieuHanhThiCong(this.axios, {
          dichvuvt_id: 0,
          donvi_id: 0
        }),
        api.LayTongHDTBChuaHoanThanh(this.axios, {
          vdichvuvt_id: 0,
          vdonvi_id: 0,
          vtthd_id: 0
        }),
        api.LayDanhSachHDTBChuaHoanThanh(this.axios, {
          vdichvuvt_id: 0,
          vdonvi_id: 0,
          vloaihd_id: 0
        })
      ])

      this.dsDieuHanhThiCong = resDieuHanhThiCong.data.data ? resDieuHanhThiCong.data.data : []
      this.dsTongHDTBChuaHoanThanh = resTongChuaHoanThanh.data.data ? resTongChuaHoanThanh.data.data : []
      this.dsHDTBChuaHoanThanh = resHDTBChuaHoanThanh.data.data ? resHDTBChuaHoanThanh.data.data : []

      this.LayTongHDTBChuaHoanThanh()
      this.LayDanhSachDieuHanhThiCong()
      this.LayDanhSachHDTBChuaHoanThanh()
      this.loading(false)
    }
  }
}
</script>
<style>
li.non-active a {
  color: #d3d3d3 !important;
}
</style>
