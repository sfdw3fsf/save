<template src='./ChiTiet.html'>

</template>

<script>
import breadcrumb from '@/components/breadcrumb'
import { Browser } from '@syncfusion/ej2-base'
import '@/assets/vendor/jquery/split'
import moment from 'moment'
import {
  ChartComponent,
  SeriesCollectionDirective,
  SeriesDirective,
  LineSeries,
  Category,
  Legend,
  Tooltip,
  AccumulationChartComponent,
  AccumulationSeriesCollectionDirective,
  AccumulationSeriesDirective,
  AccumulationTooltip,
  PieSeries,
  AccumulationDataLabel,
  AccumulationLegend
} from '@syncfusion/ej2-vue-charts'

export default {
  name: 'ChiTiet',
  components: {
    breadcrumb,
    'ejs-chart': ChartComponent,
    'e-series-collection': SeriesCollectionDirective,
    'e-series': SeriesDirective,
    'ejs-accumulationchart': AccumulationChartComponent,
    'e-accumulation-series-collection': AccumulationSeriesCollectionDirective,
    'e-accumulation-series': AccumulationSeriesDirective
  },
  provide: {
    chart: [LineSeries, Category, Legend, Tooltip],
    accumulationchart: [AccumulationLegend, PieSeries, AccumulationTooltip, AccumulationDataLabel]
  },
  async mounted() {
    if (this.$route.query.taxId != null && this.$route.query.taxId != '' && this.$route.query.taxId.length > 0) {
      this.identity = this.$route.query.taxId
      let rs = await this.axios.post(`/web-pthvkh/pthvkh/Xem_ChiTiet_KhachHang?tax_id=${this.identity}`)

      if (rs.data.data && rs.data.data.length > 0) {
        this.info = rs.data.data[0]

        this.extractCustomerInitials()
        await this.Lay_ChiTiet_DichVu_Vnpt()
        await this.Lay_DoanhThu_DichVu_TheoThang()
        // this.extractPolicyCares()
        await this.extractComplains()
        await this.extractIncidents()
        // this.extractSupports()
        // this.extractSatisfyLevel()
        // this.extractOneSme()
        // this.extractManagementInfoVnpt()
        // this.extractOtherInfos()
        this.extractServices()
        // this.extractCustomerRequests()
      }
    }
  },
  data() {
    return {
      header: {
        title: 'THÔNG TIN CHI TIẾT KHÁCH HÀNG DOANH NGHIỆP'
      },
      popupSettings: {
        header: '',
        data: [],
        columns: []
      },
      animationSettings: { effect: 'None' },
      identity: '',
      info: {},
      customerInitials: '',
      policyCares: {
        number: 0,
        value: 0,
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'name',
            headerText: 'Tên chương trình CSKH'
          },
          {
            fieldName: 'duration',
            headerText: 'Thời gian thực hiện'
          },
          {
            fieldName: 'object',
            headerText: 'Đối tượng chăm sóc'
          },
          {
            fieldName: 'numberOfObject',
            headerText: 'Số lượng nhân sự chăm sóc'
          },
          {
            fieldName: 'careMethod',
            headerText: 'Hình thức thực hiện'
          },
          {
            fieldName: 'careCost',
            headerText: 'Chi phí CSKH'
          },
          {
            fieldName: 'careItem',
            headerText: 'Độ hài lòng KH'
          }
        ]
      },
      complains: {
        number: 0,
        services: [],
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'ten_dvvt',
            headerText: 'Dịch vụ khiếu nại'
          },
          {
            fieldName: 'tenchude',
            headerText: 'Loại khiếu nại'
          },
          {
            fieldName: 'nguyennhan_kn',
            headerText: 'Nguyên nhân'
          },
          {
            fieldName: 'nd_khieunai',
            headerText: 'Nội dung'
          },
          {
            fieldName: 'trangthai_kn',
            headerText: 'Trạng thái xử lý'
          },
          {
            fieldName: 'noidung_gq',
            headerText: 'Phương án xử lý'
          }
        ]
      },
      incidents: {
        number: 0,
        services: [],
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'ten_dvvt',
            headerText: 'Dịch vụ sự cố'
          },
          {
            fieldName: 'diachi_tb',
            headerText: 'Địa chỉ sự cố'
          },
          {
            fieldName: 'ct_hong',
            headerText: 'Nguyên nhân'
          },
          {
            fieldName: 'duration',
            headerText: 'Thời gian xử lý'
          },
          {
            fieldName: 'status',
            headerText: 'Kết quả xử lý'
          }
        ]
      },
      supports: {
        number: 0,
        services: [],
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'serviceName',
            headerText: 'Dịch vụ yêu cầu hỗ trợ'
          },
          {
            fieldName: 'duration',
            headerText: 'Thời gian hỗ trợ'
          },
          {
            fieldName: 'classification',
            headerText: 'Loại hỗ trợ'
          },
          {
            fieldName: 'content',
            headerText: 'Nội dung'
          },
          {
            fieldName: 'status',
            headerText: 'Trạng thái'
          }
        ]
      },
      levelOfProviderService: {
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'serviceNameProviderService',
            headerText: 'Dịch vụ'
          },
          {
            fieldName: 'surveyDateProviderService',
            headerText: 'Ngày khảo sát'
          },
          {
            fieldName: 'avgPointProviderService',
            headerText: 'Điểm đánh giá bình quân'
          },
          {
            fieldName: 'progressPointProviderService',
            headerText: 'Điểm đánh giá tiến độ cung cấp'
          },
          {
            fieldName: 'qualityPointCC',
            headerText: 'Điểm đánh giá Chất lượng dịch vụ sau cung cấp'
          },
          {
            fieldName: 'qualityPointNV',
            headerText: 'Điểm đánh giá về chất lượng phục vụ của KTV'
          }
        ]
      },
      levelOfCareCustomer: {
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'serviceNameCareCustomer',
            headerText: 'Dịch vụ'
          },
          {
            fieldName: 'surveyDateCareCustomer',
            headerText: 'Ngày khảo sát'
          },
          {
            fieldName: 'avgPointCareCustomer',
            headerText: 'Điểm đánh giá bình quân'
          },
          {
            fieldName: 'responseTimePoint',
            headerText: 'Điểm đánh giá thời gian CSKH'
          },
          {
            fieldName: 'programQualityPoint',
            headerText: 'Điểm đánh giá Chất lượng chương trình CSKH'
          },
          {
            fieldName: 'serviceQualityAMPoint',
            headerText: 'Điểm đánh giá về chất lượng phục vụ của AM'
          }
        ]
      },
      levelOfGQKN: {
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'serviceNameGQKN',
            headerText: 'Dịch vụ'
          },
          {
            fieldName: 'surveyDateGQKN',
            headerText: 'Ngày khảo sát'
          },
          {
            fieldName: 'avgPointGQKN',
            headerText: 'Điểm đánh giá bình quân'
          },
          {
            fieldName: 'progressHandlingComplaintsPoint',
            headerText: 'Điểm đánh giá tiến độ GQKN'
          },
          {
            fieldName: 'qualityHandlingComplaintsPoint',
            headerText: 'Điểm đánh giá Chất lượng GQKN'
          },
          {
            fieldName: 'serviceQualityPoint',
            headerText: 'Điểm đánh giá về chất lượng phục vụ'
          }
        ]
      },
      levelOfFailure: {
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'serviceNameFailure',
            headerText: 'Dịch vụ'
          },
          {
            fieldName: 'surveyDateFailure',
            headerText: 'Ngày khảo sát'
          },
          {
            fieldName: 'avgPointFailure',
            headerText: 'Điểm đánh giá bình quân'
          },
          {
            fieldName: 'progressRepairServicePoint',
            headerText: 'Điểm đánh giá tiến độ XLSC'
          },
          {
            fieldName: 'qualityAfterRepairServicePoint',
            headerText: 'Điểm đánh giá Chất lượng dịch vụ sau XLSC'
          },
          {
            fieldName: 'serviceQualityTechnicianPoint',
            headerText: 'Điểm đánh giá về chất lượng phục vụ của KTV'
          }
        ]
      },
      levelOfSupport: {
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'serviceNameSupport',
            headerText: 'Dịch vụ'
          },
          {
            fieldName: 'surveyDateSupport',
            headerText: 'Ngày khảo sát'
          },
          {
            fieldName: 'avgPointSupport',
            headerText: 'Điểm đánh giá bình quân'
          },
          {
            fieldName: 'supportProgressPoint',
            headerText: 'Điểm đánh giá tiến độ'
          },
          {
            fieldName: 'supportQualityPoint',
            headerText: 'Điểm đánh giá Chất lượng'
          },
          {
            fieldName: 'operatorServiceQualityPoint',
            headerText: 'Điểm đánh giá về chất lượng phục vụ của ĐTV'
          }
        ]
      },
      levelOfEmployee: {
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'serviceNameEmployee',
            headerText: 'Dịch vụ'
          },
          {
            fieldName: 'surveyDateEmployee',
            headerText: 'Ngày khảo sát'
          },
          {
            fieldName: 'avgPointEmployee',
            headerText: 'Điểm đánh giá bình quân'
          },
          {
            fieldName: 'progressDeliveryServicePoint',
            headerText: 'Điểm đánh giá tiến độ cung cấp dịch vụ'
          },
          {
            fieldName: 'qualityAfterDeliveryServicePoint',
            headerText: 'Điểm đánh giá Chất lượng dịch vụ sau cung cấp'
          },
          {
            fieldName: 'technicianServiceQualityPoint',
            headerText: 'Điểm đánh giá về chất lượng phục vụ của KTV'
          }
        ]
      },
      onesme: {
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'timeAccess',
            headerText: 'Thời gian truy cập'
          },
          {
            fieldName: 'timeOnsite',
            headerText: 'Thời gian onsite'
          },
          {
            fieldName: 'turn',
            headerText: 'Lượt thao tác'
          },
          {
            fieldName: 'numberOfAccess',
            headerText: 'Số trang truy cập'
          },
          {
            fieldName: 'desPage',
            headerText: 'Trang đích'
          },
          {
            fieldName: 'exitPage',
            headerText: 'Trang thoát'
          },
          {
            fieldName: 'actionShowPrice',
            headerText: 'Thao tác xem báo giá'
          },
          {
            fieldName: 'actionSearchSupportedInfo',
            headerText: 'Thao tác tìm kiếm thông tin hỗ trợ'
          }
        ]
      },
      managementInfoVnpt: {
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'saleAssociate',
            headerText: 'Đơn vị bán hàng'
          },
          {
            fieldName: 'department',
            headerText: 'Phòng/Ban'
          },
          {
            fieldName: 'leaderOfSaleAssociate',
            headerText: 'Lãnh đạo đơn vị bán hàng'
          },
          {
            fieldName: 'leaderOfDepartment',
            headerText: 'Lãnh đạo phòng/ban'
          },
          {
            fieldName: 'sale',
            headerText: 'Nhân viên bán hàng (AM)'
          },
          {
            fieldName: 'carer',
            headerText: 'Nhân viên CSKH (AS)'
          },
          {
            fieldName: 'productSpecialist',
            headerText: 'Nhân viên tư vấn giải pháp (PS/SI)'
          },
          {
            fieldName: 'updateTime',
            headerText: 'Ngày cập nhật thông tin khách hàng'
          }
        ]
      },
      deciderInfos: {
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'name',
            headerText: 'Họ tên'
          },
          {
            fieldName: 'gender',
            headerText: 'Giới tính'
          },
          {
            fieldName: 'dateOfBirth',
            headerText: 'Ngày sinh'
          }, {
            fieldName: 'phoneNumber',
            headerText: 'Điện thoại'
          },
          {
            fieldName: 'email',
            headerText: 'Email'
          },
          {
            fieldName: 'position',
            headerText: 'Chức vụ'
          }
        ]
      },
      relevantInfos: {
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'name',
            headerText: 'Họ tên'
          },
          {
            fieldName: 'gender',
            headerText: 'Giới tính'
          },
          {
            fieldName: 'dateOfBirth',
            headerText: 'Ngày sinh'
          }, {
            fieldName: 'phoneNumber',
            headerText: 'Điện thoại'
          },
          {
            fieldName: 'email',
            headerText: 'Email'
          },
          {
            fieldName: 'position',
            headerText: 'Chức vụ'
          }
        ]
      },
      serviceVnptNotUses: {
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 30
          },
          {
            fieldName: 'dichvu',
            headerText: 'Dịch vụ'
          },
          {
            fieldName: 'loaihinh_merge',
            headerText: 'Loại hình'
          }
        ]
      },
      serviceVnpt: {
        totalMoney: 0,
        totalService: 0,
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'loaihinh_tb',
            headerText: 'Tên dịch vụ'
          },
          {
            fieldName: 'so_hd',
            headerText: 'Số hợp đồng'
          },
          {
            fieldName: 'loai_hd',
            headerText: 'Loại hợp đồng'
          },
          {
            fieldName: 'ngay_hd',
            headerText: 'Ngày ký hợp đồng'
          },
          {
            fieldName: 'package',
            headerText: 'Gói cước'
          },
          {
            fieldName: 'totalRevenue',
            headerText: 'Doanh thu',
            type: 'number',
            format: 'N2'
          },
          {
            fieldName: 'ngay_hh',
            headerText: 'Ngày hết hạn'
          },
          {
            fieldName: 'camket_sla',
            headerText: 'Cam kết SLA'
          }
        ]
      },
      serviceOther: {
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'serviceName',
            headerText: 'Tên dịch vụ'
          },
          {
            fieldName: 'servicePrice',
            headerText: 'Cước phí dịch vụ'
          },
          {
            fieldName: 'providerName',
            headerText: 'Tên Nhà cung cấp'
          },
          {
            fieldName: 'policy',
            headerText: 'Chính sách chăm sóc KH'
          },
          {
            fieldName: 'feedback',
            headerText: 'Phản hồi về chất lượng SPDV của NCC khác'
          }
        ]
      },
      customerRequests: {
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'program',
            headerText: 'Chương trình'
          },
          {
            fieldName: 'content',
            headerText: 'Nội dung'
          },
          {
            fieldName: 'reflectionTime',
            headerText: 'Thời gian phản án'
          },
          {
            fieldName: 'solution',
            headerText: 'Giải pháp đáp ứng'
          }
        ]
      },
      currentYear: moment(new Date()).format('YYYY'),
      keysDoanhThu: [],
      seriesDataDoanhThu: [],
      marker: { visible: true, width: 5, opacity: 0.6, height: 5 },
      seriesDataTyle: [],
      dataLabel: {
        visible: true,
        position: 'Outside', name: 'DataLabelMappingName',
        font: {
          fontWeight: '600'
        },
        connectorStyle: { length: '20px', type: 'Curve' }
      }
    }
  },
  methods: {
    extractCustomerInitials() {
      let string = this.info.customerName
      if (string) {
        string = string.toUpperCase()
        this.customerInitials = string.match(/\b[A-Z]/g).slice(0, 2).join('')
      }
    },
    formatDate(string) {
      return moment(string).format('DD/MM/YYYY')
    },
    getAddress() {
      if (this.info && this.info.enterpriseInfo && this.info.addressInfo) {
        let { addressInfo } = this.info.enterpriseInfo
        let { ward, district, city } = addressInfo

        return ward + ', ' + district + ', ' + city
      }

      return ''
    },
    extractPolicyCares() {
      if (this.info && this.info.policyCares && this.info.policyCares.length > 0) {
        let { policyCares } = this.info
        this.policyCares.data = policyCares.map((r, index) => ({ stt: index + 1, ...r }))
        for (const tmp of policyCares) {
          this.policyCares.number += 1
          this.policyCares.value += tmp.careCost
        }
      } else {
        this.policyCares.data = []
        this.policyCares.number = 0
        this.policyCares.value = 0
      }
    },
    async extractComplains() {
      let rs = await this.axios.post(`web-pthvkh/pthvkh/Lay_DanhSach_ChiTiet_PhanAnh_KhieuNai?khachhang_id=${this.info.khachhang_id}&ma_tinh=${this.info.ma_tinh}&ngay_bat_dau=1970-01-01&ngay_ket_thuc=2024-02-10`)
      let { data } = rs.data
      if (data && data.length > 0) {
        this.complains.data = data.map((r, index) => ({
          stt: index + 1,
          ...r
        }))
        for (const tmp of data) {
          this.complains.number += 1
          if (!this.complains.services.includes(tmp.name)) {
            this.complains.services.push(tmp.name)
          }
        }
      } else {
        this.complains.data = []
        this.complains.services = []
        this.complains.number = 0
      }
    },
    async extractIncidents() {
      let rs = await this.axios.post(`web-pthvkh/pthvkh/Lay_DanhSach_ChiTiet_BaoHong?khachhang_id=${this.info.khachhang_id}&ma_tinh=${this.info.ma_tinh}&ngay_bat_dau=1970-01-01&ngay_ket_thuc=2024-02-10`)
      let { data } = rs.data
      if (data && data.length > 0) {
        this.incidents.data = data.map((r, index) => ({
          stt: index + 1,
          ...r
        }))
        for (const tmp of data) {
          this.incidents.number += 1
          if (!this.incidents.services.includes(tmp.ten_dvvt)) {
            this.incidents.services.push(tmp.ten_dvvt)
          }
        }
      } else {
        this.incidents.data = []
        this.incidents.services = []
        this.incidents.number = 0
      }
    },
    getAddressIncident(incident) {
      let { address } = incident
      let {
        cityName,
        districtName,
        wardName,
        areaName,
        streetName,
        hamletName,
        homeNumber,
        characteristic,
        additionalAddress
      } = address

      return `${homeNumber}, ${hamletName}, ${streetName}, ${areaName}, ${wardName}, ${districtName}, ${cityName}`
    },
    extractSupports() {
      if (this.info && this.info.supportRequests && this.info.supportRequests.length > 0) {
        let { supportRequests } = this.info
        this.supports.data = supportRequests.map((r, index) => ({
          stt: index + 1,
          ...r
        }))
        for (const tmp of supportRequests) {
          this.supports.number += 1
          if (!this.supports.services.includes(tmp.name)) {
            this.supports.services.push(tmp.name)
          }
        }
      } else {
        this.supports.data = []
        this.supports.services = []
        this.supports.number = 0
      }
    },
    extractSatisfyLevel() {
      if (this.info && this.info.satisfyLevel) {
        let { satisfyLevel } = this.info
        let {
          levelOfProviderService,
          levelOfCareCustomer,
          levelOfGQKN,
          levelOfFailure,
          levelOfSupport,
          levelOfEmployee
        } = satisfyLevel

        if (levelOfProviderService && levelOfProviderService.length > 0) {
          this.levelOfProviderService.data = levelOfProviderService.map((r, index) => ({
            stt: index + 1,
            ...r
          }))
        } else {
          this.levelOfProviderService.data = []
        }

        if (levelOfCareCustomer && levelOfCareCustomer.length > 0) {
          this.levelOfCareCustomer.data = levelOfCareCustomer.map((r, index) => ({
            stt: index + 1,
            ...r
          }))
        } else {
          this.levelOfCareCustomer.data = []
        }

        if (levelOfGQKN && levelOfGQKN.length > 0) {
          this.levelOfGQKN.data = levelOfGQKN.map((r, index) => ({
            stt: index + 1,
            ...r
          }))
        } else {
          this.levelOfGQKN.data = []
        }

        if (levelOfFailure && levelOfFailure.length > 0) {
          this.levelOfFailure.data = levelOfFailure.map((r, index) => ({
            stt: index + 1,
            ...r
          }))
        } else {
          this.levelOfFailure.data = []
        }

        if (levelOfSupport && levelOfSupport.length > 0) {
          this.levelOfSupport.data = levelOfSupport.map((r, index) => ({
            stt: index + 1,
            ...r
          }))
        } else {
          this.levelOfSupport.data = []
        }

        if (levelOfEmployee && levelOfEmployee.length > 0) {
          this.levelOfEmployee.data = levelOfEmployee.map((r, index) => ({
            stt: index + 1,
            ...r
          }))
        } else {
          this.levelOfEmployee.data = []
        }
      }
    },
    showGrid(type) {
      if (type === 'dsCSKH') {
        this.popupSettings = {
          header: 'Danh sách Chính sách CSKH của VNPT',
          data: this.policyCares.data,
          columns: this.policyCares.columns
        }
      } else if (type === 'dsComplains') {
        this.popupSettings = {
          header: 'Danh sách Phản ánh của khách hàng',
          data: this.complains.data,
          columns: this.complains.columns
        }
      } else if (type === 'dsIncidents') {
        this.popupSettings = {
          header: 'Danh sách Phản ánh sự cố của khách hàng',
          data: this.incidents.data,
          columns: this.incidents.columns
        }
      } else if (type === 'dsSupports') {
        this.popupSettings = {
          header: 'Danh sách Phản ánh sự cố của khách hàng',
          data: this.supports.data,
          columns: this.supports.columns
        }
      } else if (type === 'levelOfGQKN') {
        this.popupSettings = {
          header: 'Mức độ hài lòng của KH về công tác GQKN',
          data: this.levelOfGQKN.data,
          columns: this.levelOfGQKN.columns
        }
      } else if (type === 'levelOfProviderService') {
        this.popupSettings = {
          header: 'Mức độ hài lòng của KH về công tác cung cấp dịch vụ',
          data: this.levelOfProviderService.data,
          columns: this.levelOfProviderService.columns
        }
      } else if (type === 'levelOfFailure') {
        this.popupSettings = {
          header: 'Mức độ hài lòng của KH về công tác xử lý sự cố và báo hỏng',
          data: this.levelOfFailure.data,
          columns: this.levelOfFailure.columns
        }
      } else if (type === 'levelOfCareCustomer') {
        this.popupSettings = {
          header: 'Mức độ hài lòng của KH về công tác CSKH',
          data: this.levelOfCareCustomer.data,
          columns: this.levelOfCareCustomer.columns
        }
      } else if (type === 'levelOfSupport') {
        this.popupSettings = {
          header: 'Mức độ hài lòng của KH về công tác hỗ trợ',
          data: this.levelOfSupport.data,
          columns: this.levelOfSupport.columns
        }
      } else if (type === 'levelOfEmployee') {
        this.popupSettings = {
          header: 'Mức độ hài lòng về chất lượng phục vụ của nhân viên',
          data: this.levelOfEmployee.data,
          columns: this.levelOfEmployee.columns
        }
      } else if (type === 'serviceVnpt') {
        this.popupSettings = {
          header: 'Danh sách dịch vụ của VNPT',
          data: this.serviceVnpt.data,
          columns: this.serviceVnpt.columns
        }
      } else if (type === 'serviceOther') {
        this.popupSettings = {
          header: 'Danh sách dịch vụ của NCC khác',
          data: this.serviceOther.data,
          columns: this.serviceOther.columns
        }
      }

      this.$refs.popupDataGrid.show()
    },
    extractOneSme() {
      let { behaviorOnesme } = this.info
      if (behaviorOnesme) {
        if (Array.isArray(behaviorOnesme)) {
          this.onesme.data = behaviorOnesme.map((r, index) => ({
            stt: index + 1,
            ...r
          }))
        } else {
          this.onesme.data.push({
            stt: 1,
            ...behaviorOnesme
          })
        }
      } else {
        this.onesme.data = []
      }
    },
    extractManagementInfoVnpt() {
      let { managementInfoVnpt } = this.info
      if (managementInfoVnpt) {
        if (Array.isArray(managementInfoVnpt)) {
          this.managementInfoVnpt.data = managementInfoVnpt.map((r, index) => ({
            stt: index + 1,
            ...r
          }))
        } else {
          this.managementInfoVnpt.data.push({
            stt: 1,
            ...managementInfoVnpt
          })
        }
      } else {
        this.managementInfoVnpt.data = []
      }
    },
    extractOtherInfos() {
      let { deciderInfos, relevantInfos } = this.info
      if (deciderInfos) {
        this.deciderInfos.data = deciderInfos.map((r, index) => ({
          stt: index + 1,
          ...r
        }))
      } else {
        this.deciderInfos.data = []
      }

      if (relevantInfos) {
        this.relevantInfos.data = relevantInfos.map((r, index) => ({
          stt: index + 1,
          ...r
        }))
      } else {
        this.relevantInfos.data = []
      }
    },

    extractServices() {
      let { service_not_uses } = this.info
      if (service_not_uses) {

        this.serviceVnptNotUses.data = service_not_uses.map((r, index) => ({
          stt: index + 1,
          loaihinh_merge: r.loaihinh.join(', '),
          ...r
        }))

      } else {
        this.serviceVnptNotUses.data = []
      }
    },

    extractCustomerRequests() {
      let { customerRequests } = this.info
      if (customerRequests) {
        this.customerRequests.data = customerRequests.map((r, index) => ({
          stt: index + 1,
          ...r
        }))
      } else {
        this.customerRequests.data = []
      }
    },

    async Lay_ChiTiet_DichVu_Vnpt() {
      let rs = await this.axios.post(`/web-pthvkh/pthvkh/Lay_ChiTiet_DichVu_Vnpt?khachhang_id=${this.info.khachhang_id}&ma_tinh=${this.info.ma_tinh}`)
      if (rs.data.data) {
        this.serviceVnpt.data = rs.data.data.map((r, index) => ({
          stt: index + 1,
          ...r
        }))
        for (const item of rs.data.data) {
          this.serviceVnpt.totalMoney += item.totalRevenue
          this.serviceVnpt.totalService += 1
        }
      } else {
        this.serviceVnpt.data = []
        this.serviceVnpt.totalMoney = 0
        this.serviceVnpt.totalService = 0
      }
    },

    async Lay_DoanhThu_DichVu_TheoThang() {
      let sixPreviousMonth = moment().subtract(6, 'months').format('YYYYMM')

      let rs = await this.axios.post(`/web-pthvkh/pthvkh/Lay_DoanhThu_DichVu_TheoThang?` +
        `khachhang_id=${this.info.khachhang_id}` +
        `&ma_tinh=${this.info.ma_tinh}` +
        `&month_start=${sixPreviousMonth}` +
        `&month_end=${moment(new Date()).format('YYYYMM')}`
      )
      this.seriesDataDoanhThu = []
      let { data } = rs.data
      if (data == null || data.length == 0) {
        this.$toast.info('Không có dữ liệu, Biểu đồ sử dụng dữ liệu tạm')
        data = [
          {
            dichVu: 'Thuê bao SIP',
            result: 185421,
            month: 202406
          },
          {
            dichVu: 'Truyền hình Internet',
            result: 225000,
            month: 202406
          },
          {
            dichVu: 'Thuê bao SIP',
            result: 322294,
            month: 202407
          },
          {
            dichVu: 'Truyền hình Internet',
            result: 225000,
            month: 202407
          },
          {
            dichVu: 'Thuê bao SIP',
            result: 58461,
            month: 202404
          },
          {
            dichVu: 'Truyền hình Internet',
            result: 195000,
            month: 202404
          },
          {
            dichVu: 'Thuê bao SIP',
            result: 284612,
            month: 202405
          },
          {
            dichVu: 'Truyền hình Internet',
            result: 175000,
            month: 202405
          }

        ]
      }
      let tmp = []
      let keys = []
      let tmpPie = []
      let total = 0

      if (data && data.length > 0) {
        for (const item of data) {
          let { month, result, dichVu } = item
          if (!(month in tmp)) {
            tmp[month] = {}
          }
          if (!keys.includes(dichVu)) {
            keys.push(dichVu)
          }
          tmp[month][dichVu] = result

          if (!(dichVu in tmpPie)) {
            tmpPie[dichVu] = 0
          }
          tmpPie[dichVu] += result
          total += result
        }

        this.keysDoanhThu = keys
        for (const thang in tmp) {
          this.seriesDataDoanhThu.push({ thang: moment(thang).format('MM/YYYY'), ...tmp[thang] })
        }

        for (const dichVu in tmpPie) {
          let percent = (Math.round(tmpPie[dichVu] * 100) / total).toFixed(2)
          this.seriesDataTyle.push({
            dichVu: dichVu,
            percent: percent,
            DataLabelMappingName: ` ${dichVu}: ${percent}% `
          })
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
