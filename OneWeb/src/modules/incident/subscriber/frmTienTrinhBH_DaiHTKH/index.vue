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
          <li>
            <a @click="onBtnChiTietClicked"> <span class="icon one-file-detail1"></span>Chi tiết</a>
          </li>
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
            <a @click="tsbtnGiaoNhanh_Click"> <span class="icon one-file-arrow-right1"></span>Giao nhanh</a>
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
                          <input v-model="cbCheckAllTinh.value" type="checkbox" class="check" @input="onAllTinhCheckedChanged" />
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
                  <template>
                    <tbody v-for="(item, index) of treeTinh.list" :key="index">
                      <!-- @click="item.check=!item.check" -->
                      <tr class="highlight4 fw6 tree-open">
                        <td colspan="8">
                          <div class="tree-arrow"></div>
                          <div class="check-action">
                            <input :ref="`input-vung-mien-${item.id}`" v-model="item.checked" type="checkbox" class="check" @change="($event) => onVungMienCheckedChanged($event, item)" />
                            <span class="name"></span>
                          </div>
                          {{ item.name }}
                        </td>
                      </tr>
                      <!-- v-show="item.checked" -->
                      <tr class="highlight" v-for="(itemChild, itemIndex) of item.children" :key="itemIndex">
                        <td class="tree-first w20 bg-white">
                          <div class="tree-line"></div>
                          <div class="tree-indent"></div>
                        </td>
                        <td class="w20 center">
                          <div class="check-action">
                            <input v-model="itemChild.checked" type="checkbox" class="check" />
                            <span class="name"></span>
                          </div>
                        </td>
                        <td>{{ itemChild.name }}</td>
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
                    <SelectExt class="" v-model="ttbh.value" :dataSource="ttbh.list" dataTextField="name" dataValueField="ttbh_id"></SelectExt>
                    <!-- <vue-select labelWidth="'100'" :options="dsTrangThaiTimKiem" :valueField="'STATUS_ID'" :labelField="'STATUS'" v-model="ttbh.value"></vue-select> -->
                  </div>
                </div>
              </div>
              <div class="legend-title">Thông tin thuê bao</div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w90">Số máy/Acc</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="gridPhieuYeuCau.selected.ma_tb" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w70">Số ảo</div>
                    <div class="value">
                      <input type="text" v-model="gridPhieuYeuCau.selected.ma_lt" class="form-control" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w90">Tên thuê bao</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="gridPhieuYeuCau.selected.ten_tb" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w70">Mã BH</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="gridPhieuYeuCau.selected.ma_bh" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Địa chỉ LD</div>
                <div class="value">
                  <input type="text" v-model="gridPhieuYeuCau.selected.diachi_ld" class="form-control" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Ghi chú</div>
                <div class="value">
                  <textarea name="" id="" cols="30" rows="10" class="form-control" style="height: 72px;resize: none;" :value="gridPhieuYeuCau.selected.ghichu_hong"></textarea>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <!-- <TableTienTrinhBaoHong :selectedRow="selectedPhieuYeuCau" /> -->
            <div class="box-form">
              <div class="legend-title">Tiến trình báo hỏng</div>
              <DataGrid
                v-bind:columns="grdTienTrinhBaoHong.header"
                v-bind:dataSource="grdTienTrinhBaoHong.list"
                :totalRecords="grdTienTrinhBaoHong.list.length"
                :pageSize="20"
                :showColumnCheckbox="false"
                :allowSelection="false"
                :allowPaging="true"
                :showFilter="true"
                :enabledSelectFirstRow="false"
                :enable-paging-server="false"
                @selectedRowChanged="onPhieuYeuCauSelectedRowChanged"
              />
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách phiếu yêu cầu</div>
          <div class="table-content">
            <!-- <TablePhieuYeuCau class="table-result" :dsPhieuYeuCau="dsPhieuYeuCau" :selectedRow.sync="selectedPhieuYeuCau"></TablePhieuYeuCau> -->
            <DataGrid
              v-bind:columns="gridPhieuYeuCau.header"
              v-bind:dataSource="dsPhieuYeuCau"
              :totalRecords="dsPhieuYeuCau.length"
              :pageSize="20"
              :showColumnCheckbox="false"
              :allowSelection="false"
              :allowPaging="true"
              :showFilter="true"
              :enabledSelectFirstRow="false"
              :enable-paging-server="false"
              @selectedRowChanged="onPhieuYeuCauSelectedRowChanged"
            />
            <!-- @rowSelected="rowSelected" -->
          </div>
        </div>
      </div>
      <footer class="footer">
        VNPT-IT &copy; 2021
      </footer>
    </div>

    <XuatFile id="popupXuatFile" :data="dsPhieuYeuCau" :tenFileXuat="xuatFile.tenFileXuat" :thuocTinh="gridPhieuYeuCau.header" />
    <PopupTestPort ref="popupTestPort" :selectedRow="this.gridPhieuYeuCau.selected" />
    <PopupGiaoPhieu ref="popupGiaoPhieu" :selectedRow="this.gridPhieuYeuCau.selected" />
    <PopupChiTietXuLy ref="popupChiTietXuLy" :selectedRow="gridPhieuYeuCau.selected" />
    <frmPhanAnhBH ref="dialogfrmPhanAnhBH" :params="frmPhanAnhBHInput"> </frmPhanAnhBH>

    <ejs-dialog :isModal="false" :enableResize="true" :visible="false" ref="checkPort" :header="'Kiểm tra Port thuê bao'" showCloseIcon="true" target=".main-wrapper" width="60%" height="500" allowDragging="true" @close="checkPortOnClose" :animationSettings="animationSettings">
      <div>
        <CheckPort v-if="checkPortDialog.isVisiable" type="modal" v-bind="checkPortDialog.input" />
      </div>
    </ejs-dialog>
    <ejs-dialog :enableResize="true" :visible="false" :header="'Tra cứu chi tiết phiếu báo hỏng'" :position="{ X: 'center', Y: 'top' }" :cssClass="'popup-ttpbh'" ref="dlg_tracuuphieubh" showCloseIcon="false" width="90%" height="800">
      <frmTraCuuPhieuBH ref="frmTraCuuPhieuBH" />
    </ejs-dialog>
  </div>
</template>

<script>
// import TablePhieuYeuCau from './components/TablePhieuYeuCau'
import TableTienTrinhBaoHong from './components/TableTienTrinhBaoHong'
import XuatFile from '@/modules/CSKH/utils/XuatFile.vue'
import PopupTestPort from './components/PopupTestPort'
import PopupGiaoPhieu from './components/PopupGiaoPhieu'
import PopupChiTietXuLy from './components/PopupChiTietXuLy'
import frmPhanAnhBH from '@/modules/incident/subscriber/SearchIncidentUpdateAppointment/components/frmPhanAnhBH.vue'
import frmTraCuuPhieuBH from '@/modules/incident/subscriber/SearchIncidentUpdateAppointment'
import api from './api'
import { donViVNP } from './data'
import XLSX from 'xlsx'
import moment from 'moment'
import CheckPort from '@/modules/search/subscriber/CheckPort'

export default {
  name: 'frmTienTrinhBH_DaiHTKH',
  components: {
    // TablePhieuYeuCau,
    TableTienTrinhBaoHong,
    XuatFile,
    PopupTestPort,
    PopupGiaoPhieu,
    PopupChiTietXuLy,
    frmPhanAnhBH,
    CheckPort,
    frmTraCuuPhieuBH
  },
  data() {
    return {
      isShowChildDonVi: false,
      searchDonViVung: '',
      cbCheckAllTinh: {
        value: false
      },
      donViVung: null,
      donViVNP,
      dsLoaiHinhTB: [],
      dsDichVuVT: [],
      search: {
        status: null
      },
      ttbh: {
        list: [],
        value: null
      },
      dsPhieuYeuCau: [],
      selectedPhieuYeuCau: {},
      xuatFile: {
        tenFileXuat: 'file_phieu_yeu_cau' + new Date().getTime(),
        thuocTinh: [{ fieldName: 'tentat' }]
      },
      frmPhanAnhBHInput: {},
      treeTinh: {
        list: [],
        search: ''
      },
      gridPhieuYeuCau: {
        header: [
          { fieldName: 'tentat', headerText: 'Tỉnh', allowFiltering: true },
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true },
          { fieldName: 'ma_lt', headerText: 'Số ảo', allowFiltering: true },
          {
            fieldName: 'ten_dv',
            headerText: 'Đơn vị tiếp nhận',
            allowFiltering: true
          },
          {
            fieldName: 'trangthai_bh',
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
            fieldName: 'loai_hinh',
            headerText: 'Loại hình',
            allowFiltering: true
          },
          {
            fieldName: 'dienthoai_lh',
            headerText: 'Điện thoại KH',
            allowFiltering: true
          },
          { fieldName: 'luot_bh', headerText: 'Lượt BH', allowFiltering: true },
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
        ],
        list: [],
        selected: {}
      },
      grdTienTrinhBaoHong: {
        header: [
          {
            fieldName: 'nguoi_cn',
            headerText: 'Người CN',
            allowFiltering: true
          },
          {
            fieldName: 'ngay_cn',
            headerText: 'Ngày CN',
            allowFiltering: true
          },
          {
            fieldName: 'noidung',
            headerText: 'Nội dung',
            allowFiltering: true
          },
          {
            fieldName: 'ten_dv',
            headerText: 'Đơn vị',
            allowFiltering: true
          },
          {
            field: 'sms',
            headerText: 'Sms',
            width: 80,
            template: function() {
              return {
                template: {
                  template: `
                    <div style="width: 100%; display: flex; justify-content: center; align-items: center;">
                      <button
                        class="btn btn-main lh16 -ap icon-message"
                        @click="xoaTienTrinhBaoHong(data)"
                      />
                    </div>
                  `,
                  data() {
                    return {
                      data: {}
                    }
                  },
                  methods: {
                    xoaTienTrinhBaoHong(e) {
                      console.log('xoaTienTrinhBaoHong')
                      console.log(e)
                    }
                  }
                }
              }
            }
          },
          {
            field: 'sua',
            headerText: 'Sửa',
            width: 80,
            template: function() {
              return {
                template: {
                  template: `
                    <div style="width: 100%; display: flex; justify-content: center; align-items: center;">
                      <button
                        class="btn btn-main lh16 -ap icon-pencil"
                        @click="xoaTienTrinhBaoHong(data)"
                      />
                    </div>
                  `,
                  data() {
                    return {
                      data: {}
                    }
                  },
                  methods: {
                    xoaTienTrinhBaoHong(e) {
                      console.log('xoaTienTrinhBaoHong')
                      console.log(e)
                    }
                  }
                }
              }
            }
          },
          {
            field: 'xoa',
            headerText: 'Xóa',
            width: 80,
            template: function() {
              return {
                template: {
                  template: `
                    <div style="width: 100%; display: flex; justify-content: center; align-items: center;">
                      <button
                        class="btn btn-main lh16 -ap icon-close"
                        @click="xoaTienTrinhBaoHong(data)"
                      />
                    </div>
                  `,
                  data() {
                    return {
                      data: {}
                    }
                  },
                  methods: {
                    xoaTienTrinhBaoHong(e) {
                      console.log('xoaTienTrinhBaoHong')
                      console.log(e)
                    }
                  }
                }
              }
            }
          }
        ],
        list: []
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
  watch: {},
  methods: {
    // filterDonViCon(listAllDonVi) {
    //   let parent = listAllDonVi.map((dv) => {
    //     let donViCon = this.filterDonViCon(
    //       listAllDonVi.filter((e) => e.parent_id == dv.id)
    //     )
    //     dv.children = donViCon

    //     return dv
    //   })
    //   return parent
    // },
    filterDonViCon(listAllDonVi, donViCap2) {
      donViCap2 = donViCap2.map((dv) => {
        var donViCon = this.filterDonViCon(
          listAllDonVi,
          listAllDonVi.filter((e) => e.parent_id == dv.id)
        )
        // if (donViCon.length < 2)
        dv.children = donViCon
        // else dv.children = donViCon.slice(0, 4)

        return dv
      })
      return donViCap2
    },
    async getLoaiHinhTB() {
      this.$root.showLoading(true)
      const response = await api.getDanhSachLoaiHinhTB(this.$root.axios)
      this.$root.showLoading(false)
      let { data, error_code, message } = response.data
      if (error_code === 'BSS-00000000') {
        this.dsLoaiHinhTB = data
      }
    },
    async getDichVuVT() {
      this.$root.showLoading(true)
      const response = await api.getDichVuVTList(this.$root.axios)
      this.$root.showLoading(false)
      let { data, error_code, message } = response.data
      if (error_code === 'BSS-00000000') {
        this.dsDichVuVT = data
      }
    },
    async searchPhieuYeuCau() {
      this.dsPhieuYeuCau = []
      this.selectedPhieuYeuCau = {}
      let donViDaChon = []
      this.treeTinh.list.map(e => {
        e.children.map(child => {
          if(child.checked) {
            donViDaChon.push(child)
          }
        })
      })
      try {
        if (donViDaChon.length == 0) {
          this.$toast.error('Chưa chọn đơn vị')
          return
        }
        this.loading(true)
        const data = await api.sp_lay_danhsach_tientrinh_baohong(this.$root.axios, {
          phanvung_id: this.$root.token.getPhanVungID(),
          ma_nd: `${this.$root.token.getMaNhanVien()}`,
          vtinh_para: donViDaChon.map((e) => e.id).join(', '),
          ttbh_id: this.ttbh.value
        })
        this.loading(false)
        if (data.data.data && data.data.data.length > 0) {
          this.dsPhieuYeuCau = data.data.data
        } else {
          this.dsPhieuYeuCau = []
        }
      } catch (error) {
        console.log(error)
      }
    },
    async initTrangThaiTimKiem() {
      this.ttbh.list = [
        {
          name: 'Mới tiếp nhận',
          ttbh_id: 1
        },
        {
          name: 'Đang xử lý',
          ttbh_id: 4
        },
        {
          name: 'Chờ nghiệm thu',
          ttbh_id: 5
        }
      ]
      this.ttbh.value = this.ttbh.list[0].ttbh_id
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
    onClickDonViRow(row) {},
    checkPort() {
      // if (!this.selectedPhieuYeuCau.baohong_id) {
      //   this.$toast.error('Chưa chọn phiếu yêu cầu')
      //   return
      // }
      // this.$refs.popupCheckPort.show()
      this.checkPortDialog.input.vma_tb = this.gridPhieuYeuCau.selected.ma_tb
      this.checkPortOnClick()
    },
    async testPort() {
      if (this.gridPhieuYeuCau.selected.baohong_id) {
        this.$refs.popupTestPort.show()
      } else {
        this.$toast.error('Chưa chọn phiếu yêu cầu')
      }
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
      if (this.gridPhieuYeuCau && this.gridPhieuYeuCau.selected.baohong_id) {
        this.$refs.popupGiaoPhieu.show()
      } else this.$toast.error('Bạn chưa chọn phiếu yêu cầu')
    },
    async tsbtnGiaoNhanh_Click() {
      // this.loading(true)
      // if(this.itemDanhSachFocused != null) {
      //   try {
      //     var p_ds_data = {
      //       BAOHONG_ID: `${this.itemDanhSachFocused.baohong_id}`,
      //       TINH_ID: `${this.itemDanhSachFocused.tinh_id}`
      //     }

      //     var  p_ds_ttnd = {
      //       NHANVIEN_ID: this.$root.token.getNhanVienID(),
      //       DONVI_ID: this.$root.token.getDonViID(),
      //       NGUOI_CN: this.$root.token.getNhanVienID(),
      //       MAY_CN: this.$root.token.getMaNhanVien(),
      //       IP_CN: await this.$root.token.getIP()
      //     }

      //     var data = await API.fn_tudong_giaophieu_baohong_dai(this.axios, {
      //       p_ds_para: JSON.stringify(p_ds_data),
      //       p_ds_ttnd: JSON.stringify(p_ds_ttnd)
      //     })

      //     data = JSON.parse(data.data.data)

      //     if(data.ERROR_CODE == '1') {
      //       this.$toast.success(`Giao nhanh thành công!`);
      //       await this.HienThi_DanhSach_Tb_BaoHong()
      //     } else {
      //       this.$toast.error(`${data.MESSAGE}`);
      //     }
      //   } catch(e) {
      //     this.$toast.error(`${e}`);
      //   }
      // } else {
      //   this.$toast.warning(`Bạn cần chọn phiếu để giao nhanh!`);
      // }
      // this.loading(false)

      this.$toast.warning(`Tính năng sẽ được update!`)
    },
    khachHangGoi() {
      if (this.gridPhieuYeuCau.selected && this.gridPhieuYeuCau.selected.baohong_id) {
        const { ma_tb, baohong_id, thuebao_id, dienthoai_lh } = this.gridPhieuYeuCau.selected
        this.frmPhanAnhBHInput.vbaohong_id_t = baohong_id || ''
        this.frmPhanAnhBHInput.thuebao_id = thuebao_id || ''
        this.frmPhanAnhBHInput.kieu_nhac = 2
        this.frmPhanAnhBHInput.ma_tb = ma_tb || ''
        this.frmPhanAnhBHInput.dienthoai_lh = dienthoai_lh
        this.$refs.dialogfrmPhanAnhBH.openDialog()
      } else this.$toast.error('Bạn chưa chọn phiếu yêu cầu')
    },
    chiTietXuLy() {
      if (this.gridPhieuYeuCau.selected && this.gridPhieuYeuCau.selected.baohong_id) {
        this.$refs.popupChiTietXuLy.show()
      } else this.$toast.error('Bạn chưa chọn phiếu yêu cầu')
    },
    async checkPortOnClick() {
      this.checkPortDialog.isVisiable = true
      this.$refs.checkPort.show()
    },
    async checkPortOnClose() {
      this.checkPortDialog.isVisiable = false
    },
    async onPhieuYeuCauSelectedRowChanged(e) {
      this.gridPhieuYeuCau.selected = e
      this.loading(true)
      const data = await api.sp_lay_ds_tientrinh_baohong(this.$root.axios, { baohong_id: this.gridPhieuYeuCau.selected.baohong_id })
      this.loading(false)
      if (data.data.data && data.data.data.length > 0) {
        this.grdTienTrinhBaoHong.list = data.data.data.map((e) => {
          e.sms = 1
          e.sua = 1
          e.xoa = 1
          return e
        })
      } else {
        this.grdTienTrinhBaoHong.list = []
      }
    },
    async onBtnChiTietClicked() {
      if (this.gridPhieuYeuCau && this.gridPhieuYeuCau.selected.baohong_id) {
        let vma_tb = String(this.gridPhieuYeuCau.selected.ma_tb)
        let baohong_id = Number(this.gridPhieuYeuCau.selected.baohong_id)
        this.$refs.dlg_tracuuphieubh.show()
        await this.$refs.frmTraCuuPhieuBH.HienThi_LichSu_BaoHong(vma_tb, 0, '', baohong_id)
      } else this.$toast.error('Bạn chưa chọn phiếu yêu cầu')
    },
    onAllTinhCheckedChanged(event, val) {
      this.treeTinh.list = this.treeTinh.list.map((e) => {
        e.children = e.children.map((child) => {
          child.checked = event.target.checked
          return child
        })
        e.checked = event.target.checked
        return e
      })
    },
    onVungMienCheckedChanged(event, item) {
      this.treeTinh.list = this.treeTinh.list.map((e) => {
        if (e.id == item.id) {
          e.children = e.children.map((child) => {
            child.checked = event.target.checked
            return child
          })
          e.checked = event.target.checked
        }
        return e
      })
    }
  },
  async mounted() {
    console.log('mounted-hihihaha')
    this.donViVNP = this.donViVNP.map((e) => {
      e.checked = false
      return e
    })
    // this.donViVNP = this.donViVNP.map((dvc) => {
    //   dvc.child.map((e) => {
    //     e.checked = true
    //     return e
    //   })
    //   return dvc
    // })
    this.treeTinh.list = this.filterDonViCon(
      this.donViVNP,
      this.donViVNP.filter((e) => e.parent_id == null)
    )
    console.log(this.treeTinh.list)
    await this.initTrangThaiTimKiem()
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
