<template>
  <div>
    <div class="main-wrapper">
      <div class="breadcrumb-top">
        <div class="main-title">Đài htkh quản lý tiến trình báo hỏng</div>
        <ul class="breadcrumb">
          <li class="breadcrumb-item">
            <a> <span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ</a>
          </li>
          <li class="breadcrumb-item"><a>Lập hợp đồng</a></li>
          <li class="breadcrumb-item active">Lắp đặt mới</li>
        </ul>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a @click="searchPhieuYeuCau"> <span class="icon one-search"></span>Tìm kiếm</a>
          </li>
          <!-- <li>
            <a> <span class="icon one-file-detail1"></span>Chi tiết</a>
          </li> -->
          <li>
            <a @click="checkPort"> <span class="icon one-clipart-arrow-right"></span>Show port</a>
          </li>
          <li>
            <a @click="testPort"> <span class="icon nc-icon-glyph arrows-1_simple-up"></span>Test Port</a>
          </li>
          <li>
            <a @click="exportFile"> <span class="icon one-download"></span>Xuất file</a>
          </li>
          <li>
            <a @click="exportExcel"> <span class="icon one-xlxs-import"></span>Xuất Excel</a>
          </li>
          <li>
            <a @click="khachHangGoi"> <span class="icon one-call-out"></span>Khách hàng gọi</a>
          </li>
          <li>
            <a @click="giaoPhieu"> <span class="icon one-file-arrow-right1"></span>Giao phiếu</a>
          </li>
          <li>
            <a @click="chiTietXuLy"> <span class="icon one-search-arrow-right"></span>Chi tiết xử lý</a>
          </li>
        </ul>
        <div class="more dropdown">
          <a data-toggle="dropdown">
            <span class="icon -ap icon-dots-three-vertical"></span>
          </a>
        </div>
      </div>
      <div class="page-content">
        <div class="row" style="margin-top: 30px">
          <div class="col-sm-3 col-12">
            <div class="box-form">
              <div class="legend-title">Chọn đơn vị</div>
              <div class="table-content table-dovi">
                <table class="table-result tree-grid-2">
                  <thead class="center">
                    <tr>
                      <th class="w20"></th>
                      <th class="w20">
                        <div class="check-action">
                          <input type="checkbox" disabled="" class="check" />
                          <span class="name"></span>
                        </div>
                      </th>
                      <th>
                        <div class="input-icon-right">
                          <input type="text" class="form-control" v-model="searchDonViVung" />
                          <span class="icon nc-icon-outline ui-1_zoom"></span>
                        </div>
                      </th>
                    </tr>
                  </thead>
                  <template v-if="searchDonViVung.length">
                    <tbody v-for="(item, index) of donViVNP" :key="index">
                      <tr class="highlight4 fw6 tree-open">
                        <td colspan="8">
                          <div class="tree-arrow"></div>
                          {{ item.name }}
                        </td>
                      </tr>
                      <tr class="highlight" v-for="(itemSearch, itemIndex) of donViVungSearchResults.filter((dv) => dv.vung === item.id)" :key="itemIndex">
                        <td class="tree-first w20 bg-white">
                          <div class="tree-line"></div>
                          <div class="tree-indent"></div>
                        </td>
                        <td class="w20 center">
                          <div class="check-action">
                            <input type="checkbox" class="check" @input="selectedDonVi = itemSearch" />
                            <span class="name"></span>
                          </div>
                        </td>
                        <td>{{ itemSearch.name }}</td>
                      </tr>
                    </tbody>
                  </template>
                  <template v-else>
                    <tbody v-for="(item, index) of donViVNP" :key="index">
                      <tr class="highlight4 fw6 tree-open" @click="showDonViVung(index)">
                        <td colspan="8">
                          <div class="tree-arrow"></div>
                          {{ item.name }}
                        </td>
                      </tr>
                      <tr class="highlight" v-for="(itemChild, indexChild) of item.child" :key="indexChild" v-show="isShowChildDonVi && donViVung == index" @click="onClickDonViRow(itemChild)">
                        <td class="tree-first w20 bg-white">
                          <div class="tree-line"></div>
                          <div class="tree-indent"></div>
                        </td>
                        <td class="w20 center">
                          <div class="check-action">
                            <input type="checkbox" class="check" @input="selectedDonVi = itemChild" />
                            <span class="name"></span>
                          </div>
                        </td>
                        <td>
                          {{ itemChild.name }}
                        </td>
                      </tr>
                    </tbody>
                  </template>
                </table>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="box-form">
              <div class="legend-title">Thông tin tìm kiếm</div>
              <div class="info-row">
                <div class="key w90">Trạng thái</div>
                <div class="value">
                  <div class="select-custom">
                    <vue-select labelWidth="'100'" :options="dsTrangThaiTimKiem" :valueField="'STATUS_ID'" :labelField="'STATUS'" v-model="search.status"></vue-select>
                  </div>
                </div>
              </div>
              <div class="legend-title">Thông tin thuê bao</div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w90">Số máy/Acc</div>
                    <div class="value">
                      <input type="text" class="form-control" :value="selectedPhieuYeuCau ? selectedPhieuYeuCau.ma_tb : ''" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w70">Số ảo</div>
                    <div class="value">
                      <input type="text" :value="selectedPhieuYeuCau ? selectedPhieuYeuCau.ma_lt : ''" class="form-control" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w90">Tên thuê bao</div>
                    <div class="value">
                      <input type="text" class="form-control" :value="selectedPhieuYeuCau ? selectedPhieuYeuCau.ten_tb : ''" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w70">Mã BH</div>
                    <div class="value">
                      <input type="text" class="form-control" :value="selectedPhieuYeuCau ? selectedPhieuYeuCau.ma_bh : ''" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Địa chỉ LD</div>
                <div class="value">
                  <input type="text" :value="selectedPhieuYeuCau ? selectedPhieuYeuCau.diachi_ld : ''" class="form-control" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Ghi chú</div>
                <div class="value">
                  <textarea name="" id="" cols="30" rows="10" class="form-control" style="height: 72px;resize: none;" :value="selectedPhieuYeuCau ? selectedPhieuYeuCau.ghichu_hong : ''"></textarea>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <TableTienTrinhBaoHong :selectedRow="selectedPhieuYeuCau" />
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách phiếu yêu cầu</div>
          <div class="table-content">
            <TablePhieuYeuCau class="table-result" :dsPhieuYeuCau="dsPhieuYeuCau" :selectedRow.sync="selectedPhieuYeuCau"></TablePhieuYeuCau>
            <!-- <DataGrid
              v-bind:columns="columns"
              v-bind:dataSource="dsPhieuYeuCau"
              :totalRecords="dsPhieuYeuCau.length"
              :pageSize="20"
              :showColumnCheckbox="false"
              :allowSelection="false"
              :allowPaging="true"
              :showFilter="true"
              :enabledSelectFirstRow="false"
              :enable-paging-server="false"
              @rowSelected="rowSelected"
            >
            </DataGrid> -->
          </div>
        </div>
      </div>
      <footer class="footer">
        VNPT-IT &copy; 2021
      </footer>
    </div>

    <XuatFile id="popupXuatFile" :data="dsPhieuYeuCau" :tenFileXuat="xuatFile.tenFileXuat" :thuocTinh="xuatFile.thuocTinh" />

    <PopupTestPort ref="popupTestPort" :selectedRow="selectedPhieuYeuCau" />
    <!-- <PopupCheckPort ref="popupCheckPort" :selectedRow="selectedPhieuYeuCau" /> -->
    <PopupGiaoPhieu ref="popupGiaoPhieu" :selectedRow="selectedPhieuYeuCau" />
    <PopupChiTietXuLy ref="popupChiTietXuLy" :selectedRow="selectedPhieuYeuCau" />
    <frmPhanAnhBH ref="dialogfrmPhanAnhBH" :params="frmPhanAnhBHInput"> </frmPhanAnhBH>
    <ejs-dialog :isModal="false" :enableResize="true" :visible="false" ref="checkPort" :header="'Kiểm tra Port thuê bao'" showCloseIcon="true" target=".main-wrapper" width="60%" height="500" allowDragging="true" @close="checkPortOnClose" :animationSettings="animationSettings">
      <div>
        <CheckPort v-if="checkPortDialog.isVisiable" type="modal" v-bind="checkPortDialog.input" />
      </div>
    </ejs-dialog>
  </div>
</template>

<script>
import TablePhieuYeuCau from './components/TablePhieuYeuCau'
import TableTienTrinhBaoHong from './components/TableTienTrinhBaoHong'
import XuatFile from '@/modules/CSKH/utils/XuatFile.vue'
import PopupTestPort from './components/PopupTestPort'
import PopupGiaoPhieu from './components/PopupGiaoPhieu'
import PopupChiTietXuLy from './components/PopupChiTietXuLy'
import frmPhanAnhBH from '@/modules/incident/subscriber/SearchIncidentUpdateAppointment/components/frmPhanAnhBH.vue'
import api from './api'
import { donViVNP, phieuYeuCauColumns } from './index'
import XLSX from 'xlsx'
import moment from 'moment'
import CheckPort from '@/modules/search/subscriber/CheckPort'

export default {
  name: 'IncidentProgressManagementStation',
  components: {
    TablePhieuYeuCau,
    TableTienTrinhBaoHong,
    XuatFile,
    PopupTestPort,
    PopupGiaoPhieu,
    PopupChiTietXuLy,
    frmPhanAnhBH,
    CheckPort
  },
  data() {
    return {
      isShowChildDonVi: false,
      searchDonViVung: '',
      donViVung: null,
      selectedDonVi: {},
      donViVNP,
      dsLoaiHinhTB: [],
      dsDichVuVT: [],
      search: {
        status: null
      },
      dsTrangThaiTimKiem: [],
      dsPhieuYeuCau: [],
      selectedPhieuYeuCau: {},
      xuatFile: {
        tenFileXuat: 'file_phieu_yeu_cau' + new Date().getTime(),
        thuocTinh: []
      },
      frmPhanAnhBHInput: {},
      gridPhieuYeuCau: {
        header: [
          { fieldName: 'tinhThanh', headerText: 'Tỉnh', allowFiltering: true },
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true },
          { fieldName: 'ma_lt', headerText: 'Số ảo', allowFiltering: true },
          {
            fieldName: 'ten_dv',
            headerText: 'Đơn vị tiếp nhận',
            allowFiltering: true
          },
          {
            fieldName: 'trangThaiBH',
            headerText: 'Trạng thái BH',
            allowFiltering: true
          },
          { fieldName: 'ngay_bh', headerText: 'Ngày BH', allowFiltering: true },
          {
            fieldName: 'thoigian_ck',
            headerText: 'Thời gian CK',
            allowFiltering: true
          },
          {
            fieldName: 'chitieu_tg',
            headerText: 'Chỉ tiêu TG',
            allowFiltering: true
          },
          {
            fieldName: 'loaiHinh',
            headerText: 'Loại hình',
            allowFiltering: true
          },
          {
            fieldName: 'dienthoai_lh',
            headerText: 'Điện thoại KH',
            allowFiltering: true
          },
          { fieldName: 'luot_bh', headerText: 'Lượt KH', allowFiltering: true },
          { fieldName: 'lan_pa', headerText: 'KH Gọi', allowFiltering: true },
          {
            fieldName: 'ghichu_hong',
            headerText: 'Ghi chú hỏng',
            allowFiltering: true
          },
          { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true },
          {
            fieldName: 'phanloaikh_id',
            headerText: 'Phân loại KH',
            allowFiltering: true
          }
        ]
      },
      checkPortDialog: {
        isVisiable: false,
        input: {
          vma_tb: null,
          vthuebao_id: null,
          vkieu: null
        }
      },
      animationSettings: { effect: 'None' }
    }
  },
  computed: {
    donViVungSearchResults() {
      const tinhThanh = donViVNP.map((vung) => vung.child).flat()
      return tinhThanh.filter((tinhThanh) =>
        tinhThanh.name
          .toLocaleLowerCase()
          .normalize('NFD')
          .replace(/[\u0300-\u036f]/g, '')
          .includes(
            this.searchDonViVung
              .toLocaleLowerCase()
              .normalize('NFD')
              .replace(/[\u0300-\u036f]/g, '')
          )
      )
    }
  },
  watch: {
    selectedPhieuYeuCau: {
      handler() {
        document.querySelector('.legend-title').scrollIntoView()
      },
      deep: true
    }
  },
  methods: {
    async getLoaiHinhTB() {
      const response = await api.getDanhSachLoaiHinhTB(this.$root.axios)
      let { data, error_code, message } = response.data
      if (error_code === 'BSS-00000000') {
        this.dsLoaiHinhTB = data
      }
    },
    async getDichVuVT() {
      const response = await api.getDichVuVTList(this.$root.axios)
      let { data, error_code, message } = response.data
      if (error_code === 'BSS-00000000') {
        this.dsDichVuVT = data
      }
    },
    async searchPhieuYeuCau() {
      this.$root.showLoading(true)
      this.dsPhieuYeuCau = []
      this.selectedPhieuYeuCau = {}
      try {
        const { id: phanvung_id } = this.selectedDonVi
        const { status: ttbh_id } = this.search
        const payload = {
          ma_nd: `${this.$root.token.getUserName()}`,
          phanvung_id,
          ttbh_id
        }
        if (!phanvung_id) {
          this.$toast.error('Chưa chọn đơn vị')
          return
        }
        const response = await api.getDSPhieuYeuCau(this.$root.axios, payload)
        let { data, error_code, message } = response.data
        if (error_code === 'BSS-00000000') {
          if (data.length > 0) {
            data = data.map((row) => {
              const { tinh_id, ttbh_id, chitieu_tg, loaitb_id, ngay_bh, thoigian_ck } = row

              // ten tinh thanh
              const dsTinhThanh = donViVNP.map((donVi) => donVi.child).flat()
              const tinh = dsTinhThanh.find((tinhThanh) => tinhThanh.id === parseInt(tinh_id))
              row.tinhThanh = tinh.name

              // ten trang thai bh
              const status = this.dsTrangThaiTimKiem.find((tinhThanh) => tinhThanh.STATUS_ID === parseInt(ttbh_id))
              row.trangThaiBH = status.STATUS

              // loai hinh
              let dsLoaiHinhTB = this.dsLoaiHinhTB
              let dsDichVuVT = this.dsDichVuVT

              const getLoaiHinhTB = (loaitb_id) => {
                const loaiHinh = dsLoaiHinhTB.find((loaiHinhTB) => loaiHinhTB.LOAITB_ID === loaitb_id)
                if (loaiHinh) {
                  const { LOAIHINH_TB, DICHVUVT_ID } = loaiHinh
                  const dichVuVT = dsDichVuVT.find((item) => item.dichvuvt_id === DICHVUVT_ID)
                  const { ten_dvvt } = dichVuVT
                  let text = `${ten_dvvt} - ${LOAIHINH_TB}`

                  return text
                }

                return ''
              }
              const loaiHinh = getLoaiHinhTB(loaitb_id)
              row.loaiHinh = loaiHinh

              // Ngay BH
              const ngayBH = moment(ngay_bh).format('DD/MM/YYYY')
              row.ngay_bh = ngayBH

              // Thoi gian CK
              const thoiGianCK = moment(thoigian_ck).format('DD/MM/YYYY HH:MM:SS')
              row.thoigian_ck = thoiGianCK

              return row
            })
            this.dsPhieuYeuCau = data
          } else {
            this.$toast.error('Không tìm thấy kết quả')
          }
        } else {
          if (error_code === 'BSS-00000204') {
            this.$toast.error('Không tìm thấy dữ liệu')
          } else this.$toast.error(message)
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    async initTrangThaiTimKiem() {
      this.dsTrangThaiTimKiem = [
        {
          STATUS: 'Mới tiếp nhận',
          STATUS_ID: 1
        },
        {
          STATUS: 'Chờ nghiệm thu',
          STATUS_ID: 5
        },
        {
          STATUS: 'Đang xử lý',
          STATUS_ID: 6
        }
      ]
      this.search.status = this.dsTrangThaiTimKiem[0]['STATUS_ID']
    },
    // Don vi
    showDonViVung(index) {
      if (this.donViVung !== index) {
        this.donViVung = index
        if (!this.isShowChildDonVi) this.isShowChildDonVi = true
      } else {
        this.donViVung = null
        this.isShowChildDonVi = false
      }
    },
    onClickDonViRow(row) {
    },
    checkPort() {
      // if (!this.selectedPhieuYeuCau.baohong_id) {
      //   this.$toast.error('Chưa chọn phiếu yêu cầu')
      //   return
      // }
      // this.$refs.popupCheckPort.show()
      // console.log(this.selectedPhieuYeuCau)
      this.checkPortDialog.input.vma_tb = this.selectedPhieuYeuCau.ma_tb
      this.checkPortOnClick()
    },
    async testPort() {
      if (!this.selectedPhieuYeuCau.baohong_id) {
        this.$to/ast.error('Chưa chọn phiếu yêu cầu')
        return
      }
      this.$refs.popupTestPort.show()
    },

    // Export excel
    exportExcel() {
      if (this.dsPhieuYeuCau.length) {
        const nameReport = 'danh_sach_phieu_yeu_cau'
        const data = XLSX.utils.json_to_sheet(this.dsPhieuYeuCau)
        const wb = XLSX.utils.book_new()
        XLSX.utils.book_append_sheet(wb, data, 'data')
        const name = nameReport + '.xlsx'
        XLSX.writeFile(wb, name)
      } else this.$toast.error('Không có dữ liệu để xuất file')
    },
    exportFile() {
      if (this.dsPhieuYeuCau.length) {
        this.$bvModal.show('popupXuatFile')
      } else this.$toast.error('Không có dữ liệu để xuất file')
    },
    giaoPhieu() {
      if (this.selectedPhieuYeuCau && this.selectedPhieuYeuCau.baohong_id) {
        this.$refs.popupGiaoPhieu.show()
      } else this.$toast.error('Bạn chưa chọn phiếu yêu cầu')
    },
    khachHangGoi() {
      if (this.selectedPhieuYeuCau && this.selectedPhieuYeuCau.baohong_id) {
        const { ma_tb, baohong_id, thuebao_id, dienthoai_lh } = this.selectedPhieuYeuCau
        this.frmPhanAnhBHInput.vbaohong_id_t = baohong_id || ''
        this.frmPhanAnhBHInput.thuebao_id = thuebao_id || ''
        this.frmPhanAnhBHInput.kieu_nhac = 2
        this.frmPhanAnhBHInput.ma_tb = ma_tb || ''
        this.frmPhanAnhBHInput.dienthoai_lh = dienthoai_lh
        this.$refs.dialogfrmPhanAnhBH.openDialog()
      } else this.$toast.error('Bạn chưa chọn phiếu yêu cầu')
    },
    chiTietXuLy() {
      if (this.selectedPhieuYeuCau && this.selectedPhieuYeuCau.baohong_id) {
        this.$refs.popupChiTietXuLy.show()
      } else this.$toast.error('Bạn chưa chọn phiếu yêu cầu')
    },
    async checkPortOnClick() {
      this.checkPortDialog.isVisiable = true
      this.$refs.checkPort.show()
    },
    async checkPortOnClose() {
      this.checkPortDialog.isVisiable = false
    }
  },
  async created() {
    this.$root.showLoading(true)

    // init status
    setTimeout(async () => {
      await this.initTrangThaiTimKiem()
      this.xuatFile.thuocTinh = phieuYeuCauColumns.map((thuocTinh) => {
        const { fieldName, headerText: name } = thuocTinh
        return {
          fieldName,
          name
        }
      })
    }, 0)

    this.$root.showLoading(false)

    await this.getDichVuVT()
    await this.getLoaiHinhTB()
  }
}
</script>

<style lang="css" scoped>
.list-actions-top ul.list > li {
  cursor: pointer;
}
.table-content.table-dovi {
  max-height: 35vh;
}
</style>
