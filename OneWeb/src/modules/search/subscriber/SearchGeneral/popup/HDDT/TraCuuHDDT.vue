<template>
  <div class="modal-content popup-box">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript:;" @click.prevent="btnTimKiem_Click"> <span class="icon one-search"></span> Tìm kiếm </a>
        </li>
        <li disabled="true">
          <input type="text" v-model="this.mst_pro" :disabled="true" ref="txtMSTTimKiem" class="form-control" />
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title"><span class="icon fa fa-angle-up"></span>Thông tin Backend</div>
        <div class="grid-stack-box">
          <div class="box-col box-form">
            <div class="info-row">
              <div class="key">Mã thuê bao</div>
              <div class="value">
                <input type="text" :value="is_check ? gdvcontent.list[0].MA_TB : ''" :disabled="true" class="form-control" />
              </div>
            </div>
            <div class="info-row">
              <div class="key">Tên khách hàng</div>
              <div class="value">
                <input type="text" :value="is_check ? gdvcontent.list[0].TEN_KH : ''" :disabled="true" class="form-control" />
              </div>
            </div>
            <div class="info-row">
              <div class="key">Mã số thuế</div>
              <div class="value">
                <input type="text" :value="is_check ? gdvcontent.list[0].MST : ''" :disabled="true" class="form-control" />
              </div>
            </div>
            <div class="info-row">
              <div class="key">Địa chỉ KH</div>
              <div class="value">
                <input type="text" :value="is_check ? this.gdvcontent.list[0].DIACHI_KH : ''" :disabled="true" class="form-control" />
              </div>
            </div>
            <div class="info-row">
              <div class="key">Số điện thoại</div>
              <div class="value">
                <input type="text" :value="is_check ? this.gdvcontent.list[0].SO_DT : ''" :disabled="true" class="form-control" />
              </div>
            </div>
            <div class="info-row">
              <div class="key">Email</div>
              <div class="value">
                <input type="text" :value="is_check ? this.gdvcontent.list[0].EMAIL : ''" :disabled="true" class="form-control" />
              </div>
            </div>
            <div class="info-row">
              <div class="key">Trạng thái</div>
              <div class="value">
                <input type="text" :value="is_check ? this.gdvcontent.list[0].LOAITB_ID_ACTIVE : ''" :disabled="true" class="form-control" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title"><span class="icon fa fa-angle-up"></span>Thông tin dịch vụ Hệ sinh thái hóa đơn điện tử (VNPT Invoice - Inbot)</div>
        <div class="table-content">
          <DataGrid class="table-result table-gachle" ref="gdvMauHD" v-bind:columns="gdvcontent.header" v-bind:dataSource="gdvcontent.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="false"> </DataGrid>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import DataGrid from '@/components/Controls/DataGrid'
import { required, minLength, integer, maxValue } from 'vuelidate/lib/validators'
import { LoaiHopDong, TrangThaiHD, DichVuVienThong, listDichVuCNTT, TRANGBI, KieuLapDat, KHOANMUC_TT, LoaiHinhTB, Loai_KM, NHOM_LNV, LOAI_DV } from '@/modules/admin/category/MultiServicePackage/enum.js'
import api from '../../API'

export default {
  name: 'frmChuanHoaHDDT',
  components: {
    required,
    minLength,
    integer,
    maxValue,
    DatePicker,
    VueFlatPickr,
    breadcrumb,
    DataGrid
  },
  props: ['dulieu'],
  data() {
    return {
      mst_pro: '',
      is_check: false,
      is_thongbao: '',
      slhd_damua: 0,
      slhd_dasd: 0,
      slhd_conlai: 0,
      gdvcontent: {
        list: [],
        header: [
          {
            fieldName: 'PACKAGE_NAME',
            headerText: 'Tên gói cước',
            allowFiltering: true
          },
          {
            fieldName: 'PACKAGE_NUMBER',
            headerText: 'Số lượng hóa đơn đăng ký',
            allowFiltering: false
          },
          {
            fieldName: 'PACKAGE_NUMBER_USE',
            headerText: 'Số lượng hóa đơn đã sử dụng',
            allowFiltering: false
          },
          {
            fieldName: 'PACKAGE_NUMBER_STILL',
            headerText: 'Số lượng hóa đơn còn lại',
            allowFiltering: false
          },
          {
            fieldName: 'CREATE_DATE',
            headerText: 'Ngày khởi tạo dịch vụ',
            allowFiltering: false
          },
          {
            fieldName: 'startDate',
            headerText: 'Ngày bắt đầu sử dụng',
            allowFiltering: false
          },
          {
            fieldName: 'END_DATE',
            headerText: 'Ngày kết thúc',
            allowFiltering: false
          }
        ],
        value: {},
        isEnabled: true,
        checked: []
      },
      list_data: {}
    }
  },
  computed: {},
  methods: {
    closeForm: function(val) {
      if (val) this.$emit('form-close', val)
    },
    ShowSuccess: function(msg) {
      this.$toast.success(msg)
    },
    ShowError: function(msg) {
      this.$toast.error(msg)
    },

    frmChuanHoaHDDT_Load: async function() {
      this.mst_pro = this.dulieu[0].mst
      if (this.mst_pro != null || this.mst_pro != 0) {
        try {
          await this.btnTimKiem_Click()
        } catch (ex) {
          console.log(ex)
        }
      }
    },
    format_Date: function(dateString) {
      if (dateString) {
        const date = new Date(dateString)
        const day = date
          .getDate()
          .toString()
          .padStart(2, '0')
        const month = (date.getMonth() + 1).toString().padStart(2, '0')
        const year = date.getFullYear()
        return `${day}-${month}-${year}`
      }
      return null
    },
    btnTimKiem_Click: async function() {
      try {
        let response = await api.tracuuinvoiceinbot(this.axios, { mst: this.mst_pro })
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.error == 200) {
          this.list_data = JSON.parse(response.data.data)
          this.gdvcontent.list = this.list_data.content
          if (this.gdvcontent.list.length > 0) {
            this.is_check = true
            const newData = this.gdvcontent.list.map((item) => {
              const newItem = { ...item }
              newItem.startDate = this.format_Date(item.startDate)
              newItem.CREATE_DATE = this.format_Date(item.CREATE_DATE)
              newItem.END_DATE = this.format_Date(item.END_DATE)
              newItem.LOAITB_ID_ACTIVE = item.LOAITB_ID_ACTIVE = 1 ? 'Đang hoạt động' : 'Không hoạt động'
              return newItem
            })
            this.gdvcontent.list = newData
            this.gdvcontent.list.forEach(item => {
            this.slhd_damua += (item.PACKAGE_NUMBER*1) || 0; // Đảm bảo giá trị không phải là null hoặc undefined
            this.slhd_dasd += (item.PACKAGE_NUMBER_USE*1) || 0;
            this.slhd_conlai += (item.PACKAGE_NUMBER_STILL*1) || 0;
            });
            const data_test = {}
            data_test.PACKAGE_NAME = 'Tổng'
            data_test.PACKAGE_NUMBER = this.slhd_damua
            data_test.PACKAGE_NUMBER_USE = this.slhd_dasd
            data_test.PACKAGE_NUMBER_STILL = this.slhd_conlai

            this.gdvcontent.list.push(data_test)
        
          }
        } else {
          this.list_data = {}
        }
      } catch (ex) {
        console.log(ex)
        this.list_data = {}
      }
    }
  }
}
</script>
