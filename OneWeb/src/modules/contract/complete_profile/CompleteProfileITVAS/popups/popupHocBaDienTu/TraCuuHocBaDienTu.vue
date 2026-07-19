<template>
  <div class="main-wrapper">
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title mart0">
          <div class="pull-left mart10">
            Thông tin tìm kiếm
          </div>
          <div class="clearfix"></div>
        </div>
        <div class="row">
          <div class="col-xs-6 col-sm-6 col-lg-6 col-md-6">
            <div class="e-input-group">
              <input class="e-input" type="text" v-model="IdTruong" />
              <button type="button" class="btn btn-primary ml-2" style="width: 10vh;margin: 2px 2px 3px 3px" @click.prevent="LayIDtruong_Click">Lấy ID</button>
            </div>
          </div>
        </div>
      </div>
      <!-- ---- -->
      <div class="row">
        <div class=" p-1">
          <div class="box-form">
            <div class="legend-title">Danh mục chức năng</div>
            <div class="nav tabs tab-over">
              <a href="#tabGV" data-toggle="tab" class="active">DS học sinh</a>
              <a href="#tabDB" data-toggle="tab" @click="handleDanhBa(0)">Danh bạ</a>
            </div>
            <div class="tab-content box-form" style="margin-bottom: 0px;">
              <template>
                <div id="tabGV" class="tab-pane active">
                  <!-- <div class="row">
                    <div class="info-row">
                      <div class="key w90">Tìm kiếm</div>
                      <div style="width: 40vh;">
                        <ComboboxGrid
                          class="select-custom"
                          :columns="[
                            { fieldName: 'id', headerText: 'ID', allowFiltering: true, width: '100' },
                            { fieldName: 'ma_tb', headerText: 'Mã thuê bao', allowFiltering: true, width: '300' }
                          ]"
                          :dataSource="DsThueBaoTheoIDTruong.list"
                          textField="ma_tb"
                          valueField="ma_tb"
                          @selectedChanged="cboSelectMaTB"
                        ></ComboboxGrid>
                      </div>
                    </div>
                  </div> -->
                  <div class="row p-1">
                    <div class="box-form">
                      <div class="legend-title">Danh sách thuê bao</div>
                      <DataGrid
                        ref="DsThueBaoTheoIDTruong"
                        v-bind:dataSource="DsThueBaoTheoIDTruong.list"
                        v-bind:columns="DsThueBaoTheoIDTruong.columns"
                        :enabledSelectFirstRow="false"
                        :allowPaging="true"
                        :enablePagingServer="false"
                        :panelDataHeight="'15vh'"
                        :showFilter="true"
                        :allowScrolling="true"
                        @selectedRowChanged="GridDsThueBaoTheoIDTruong_selectedRowChanged"
                      >
                      </DataGrid>
                    </div>
                    <div class="box-form">
                      <div class="legend-title">Danh sách học sinh theo thuê bao</div>
                      <DataGrid
                        ref="DsHSTheoMaTB"
                        v-bind:columns="[
                          { fieldName: 'idhs', headerText: 'ID học sinh', allowFiltering: true, allowSorting: true },
                          { fieldName: 'tenhs', headerText: 'Tên học sinh', allowFiltering: true, allowSorting: true },
                          { fieldName: 'lop', headerText: 'Lớp', allowFiltering: true, allowSorting: true },
                          { fieldName: 'gioitinh', headerText: 'Giới tính', allowFiltering: true, allowSorting: true },
                          { fieldName: 'ngaysinh', headerText: 'Ngày sinh', allowFiltering: true, allowSorting: true }
                        ]"
                        :enabledSelectFirstRow="false"
                        :allowPaging="true"
                        :enablePagingServer="false"
                        v-bind:dataSource="DsHSTheoMaTB.list"
                        :panelDataHeight="'25vh'"
                        :showFilter="true"
                        :allowScrolling="true"
                      >
                      </DataGrid>
                    </div>
                  </div>
                </div>
              </template>
              <template v-if="isShowBbThueBao">
                <div id="tabDB" class="tab-pane active">
                  <div class="row">
                    <div class="info-row">
                      <div class="key" style="width: 10px;">
                        Tìm kiếm
                      </div>
                      <div class="value" style="width: 40vh;">
                        <ComboboxGrid
                          style="width: 40vh;"
                          class="select-custom"
                          :columns="[
                            { fieldName: 'id', headerText: 'ID', allowFiltering: true, width: '100' },

                            { fieldName: 'trangthai', headerText: 'Trạng thái thuê bao', allowFiltering: true, width: '300' }
                          ]"
                          v-bind:dataSource="cbbTrangThaiThueBao.list"
                          textField="trangthai"
                          valueField="id"
                          @selectedChanged="cboSelectTTThueBao"
                        >
                        </ComboboxGrid>
                      </div>
                    </div>
                  </div>
                  <div class="row p-1">
                    <div class="box-form">
                      <div class="legend-title">Danh sách thuê bao</div>
                      <DataGrid
                        ref="gridDbThueBao"
                        v-bind:dataSource="DbThueBao.list"
                        :allowExcelExport="true"
                        :columns="DbThueBao.columns"
                        :enabledSelectFirstRow="false"
                        :allowPaging="true"
                        :enablePagingServer="false"
                        :panelDataHeight="'50vh'"
                        :showFilter="true"
                        :allowScrolling="true"
                        :allowSelection="true"
                      >
                      </DataGrid>
                    </div>
                  </div>
                </div>
              </template>
            </div>
          </div>
        </div>
      </div>
    </div>
    <ejs-dialog :isModal="true" :enableResize="true" :visible="false" ref="LayDuLieuAPI" showCloseIcon="true" target=".main-wrapper" width="70%" allowDragging="true">
      <!-- :animationSettings="animationSettings" -->
      <popupLayDuLieuTuAPI
        :dulieu="{
          loaihinh_tb: 370,
          field_name: 'MA_PHU',
          api_url: 'https://api-onebss.vnpt.vn/tichhop/solienlac/laydstruongthuoctinhdynamic',
          method: 'GET',
          hienthi_bang: '',
          giatri_trave: 'truong_id',
          idx: 3
        }"
        @form-close="emitChapnhanClick"
      />
    </ejs-dialog>
  </div>
</template>

<script>
import popupLayDuLieuTuAPI from '@/modules/contract/setup/InstallNewSubs/Popups/popupLayDuLieuTuAPI'
import api from './HocBaDienTuApi'
export default {
  name: 'TraCuuHocBaDienTu',
  data() {
    return {
      DsThueBaoTheoIDTruong: {
        list: [],
        columns: [
          { fieldName: 'ma_tb', headerText: 'Mã thuê bao', allowFiltering: true, allowSorting: true },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: true },
          { fieldName: 'ten_kh', headerText: 'Tên KH', allowFiltering: true, allowSorting: true },
          { fieldName: 'diachi_kh', headerText: 'Địa chỉ KH', allowFiltering: true, allowSorting: true },
          { fieldName: 'email', headerText: 'Email', allowFiltering: true, allowSorting: true },
          { fieldName: 'so_dt', headerText: 'Số ĐT', allowFiltering: true, allowSorting: true },
          { fieldName: 'thuonghieu', headerText: 'Tên trường', allowFiltering: true, allowSorting: true },
          { fieldName: 'ngay_bd', headerText: 'Ngày bắt đầu', allowFiltering: true, allowSorting: true },
          { fieldName: 'ngay_kt', headerText: 'Ngày kết thúc', allowFiltering: true, allowSorting: true },
          { fieldName: 'sohs', headerText: 'Số lượng học sinh', allowFiltering: true, allowSorting: true },
          { fieldName: 'trangthai', headerText: 'Trạng thái', allowFiltering: true, allowSorting: true }
        ],
        keycol: '',
        value: ''
      },
      DsHSTheoMaTB: {
        list: [],
        keycol: '',
        value: ''
      },
      DbThueBao: {
        list: [],
        columns: [
          { fieldName: 'ma_tb', headerText: 'Mã thuê bao', allowFiltering: true, allowSorting: true },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: true },
          { fieldName: 'ten_kh', headerText: 'Tên KH', allowFiltering: true, allowSorting: true },
          { fieldName: 'diachi_kh', headerText: 'Địa chỉ KH', allowFiltering: true, allowSorting: true },
          { fieldName: 'email', headerText: 'Email', allowFiltering: true, allowSorting: true },
          { fieldName: 'so_dt', headerText: 'Số ĐT', allowFiltering: true, allowSorting: true },
          { fieldName: 'thuonghieu', headerText: 'Tên trường', allowFiltering: true, allowSorting: true },
          { fieldName: 'ngay_bd', headerText: 'Ngày bắt đầu', allowFiltering: true, allowSorting: true },
          { fieldName: 'ngay_kt', headerText: 'Ngày kết thúc', allowFiltering: true, allowSorting: true },
          { fieldName: 'sohs', headerText: 'Số lượng học sinh', allowFiltering: true, allowSorting: true },
          { fieldName: 'trangthai', headerText: 'Trạng thái', allowFiltering: true, allowSorting: true }
        ],
        keycol: '',
        value: ''
      },
      cbbTrangThaiThueBao: {
        list: [
          { id: 0, trangthai: 'Tất cả' },
          { id: 6, trangthai: 'Thuê bao đã hoàn thiện' },
          { id: 7, trangthai: 'Thuê bao đã hủy' },
          { id: 5, trangthai: 'Thuê bao đã kích hoạt thành công, chưa hoàn thiện' },
          { id: 4, trangthai: 'Thuê bao lắp mới chưa kích hoạt' },
          { id: 3, trangthai: 'Thuê bao hết hạn sử dụng, chưa thanh lý' }
        ],
        value: ''
      },
      IdTruong: 0,
      isShowTaoHDHuy: false,
      isShowBbThueBao: false,
      DsHDTrungHs: {
        list: [],
        columns: [
          { fieldName: 'ma_gd', headerText: 'Mã giao dịch', allowFiltering: true, allowSorting: true },
          { fieldName: 'ma_tb', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: true },
          { fieldName: 'ten_tb', headerText: 'Tên KH', allowFiltering: true, allowSorting: true },
          { fieldName: 'diachi_tb', headerText: 'Địa chỉ KH', allowFiltering: true, allowSorting: true },
          { fieldName: 'ma_phu', headerText: 'ID trường', allowFiltering: true, allowSorting: true },
          { fieldName: 'so_dt', headerText: 'Số ĐT', allowFiltering: true, allowSorting: true },
          { fieldName: 'email', headerText: 'Email', allowFiltering: true, allowSorting: true },
          { fieldName: 'thuonghieu', headerText: 'Tên trường', allowFiltering: true, allowSorting: true },
          { fieldName: 'ngay_bd', headerText: 'Ngày bắt đầu', allowFiltering: true, allowSorting: true },
          { fieldName: 'ngay_kt', headerText: 'Ngày kết thúc', allowFiltering: true, allowSorting: true },
          { fieldName: 'so_thang', headerText: 'Số tháng', allowFiltering: true, allowSorting: true }
        ],
        value: ''
      },
      DsHSTrungTheoHD: {
        list: [],
        columns: [
          { fieldName: 'id_text', headerText: 'ID học sinh', allowFiltering: true, allowSorting: true },
          { fieldName: 'ten_tb_ct', headerText: 'Tên học sinh', allowFiltering: true, allowSorting: true },
          { fieldName: 'daidien', headerText: 'Lớp', allowFiltering: true, allowSorting: true },
          { fieldName: 'trangthai_ct', headerText: 'Giới tính', allowFiltering: true, allowSorting: true },
          { fieldName: 'khonggian_ct', headerText: 'Ngày sinh', allowFiltering: true, allowSorting: true }
        ],
        value: ''
      }
    }
  },
  watch: {
    IdTruong(newvalue, oldvalue) {
      this.LayDSThueBaoTheoIdTruong()
    },
    'DsThueBaoTheoIDTruong.value'(newvalue, oldvalue) {
      this.LayDSHocSinhTheoMaTB(newvalue)
    },
    'cbbTrangThaiThueBao.value'(newvalue, oldvalue) {
      if (newvalue != oldvalue && oldvalue != null) {
        this.handleDanhBa(newvalue)
      }
    }
  },
  components: { popupLayDuLieuTuAPI },
  methods: {
    LayIDtruong_Click() {
      this.$refs.LayDuLieuAPI.show()
    },
    cboSelectTTThueBao(item) {
      this.cbbTrangThaiThueBao.value = item.id
    },

    emitChapnhanClick(data) {
      this.IdTruong = data.giatrichon
      this.$refs.LayDuLieuAPI.hide()
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
    async LayDSThueBaoTheoIdTruong() {
      const getDsTB = await this.ConvertResApi(
        api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
          p_nghiepvu: 'LAYDSTHUEBAOTHEOIDTRUONG',
          p_ds_para: '{"TRUONG_ID":' + this.IdTruong + '}'
        })
      )
      this.DsThueBaoTheoIDTruong.list = getDsTB ? getDsTB : []
    },
    async LayDSHocSinhTheoMaTB(data) {
      const getDsHS = await this.ConvertResApi(
        api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
          p_nghiepvu: 'LAYDSHSTHEOMATB',
          p_ds_para: '{"MA_TB":"' + data + '"}'
        })
      )
      this.DsHSTheoMaTB.list = getDsHS ? getDsHS : []
    },
    async handleDanhBa(data) {
      this.isShowBbThueBao = true
      const getDb = await this.ConvertResApi(
        api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
          p_nghiepvu: 'LAYDSDBHocBaDienTu',
          p_ds_para: ''
        })
      )
      if (data === 0 && getDb) {
        this.DbThueBao.list = getDb
      } else {
        this.DbThueBao.list = getDb.filter((item) => item.idtrangthai === data) ? getDb.filter((item) => item.idtrangthai === data) : []
      }
    },
    GridDsThueBaoTheoIDTruong_selectedRowChanged(args) {
      this.DsThueBaoTheoIDTruong.value = args.ma_tb
    }
  }
}
</script>

<style></style>
