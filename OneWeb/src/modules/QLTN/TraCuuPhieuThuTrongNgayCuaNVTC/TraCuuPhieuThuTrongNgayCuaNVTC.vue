<template src="./TraCuuPhieuThuTrongNgayCuaNVTC.html"></template>
<style scoped src="../TraCuuPhieuTraTheoNgay/TraCuuPhieuTraTheoNgay.scss"></style>
<script>
import Calculator from '@/components/Calculator'
import KyCuoc from '@/components/KyCuoc'
import SuaNgayTT from './components/SuaNgayTT'
import PopupThayDoiThongTinThanhToan from '../components/PopupInXoaSuaPhieuThanhToan/PopupThayDoiThongTinThanhToan'
import XoaPhieu from './components/XoaPhieu'
import TCAPI from '../api/TraCuuPhieuTraTheoNgayAPI.js'
import XLAPI from '../api/XuLyPhieuThanhToanAPI.js'
import DAPI from '../api/DSThueBaoKhongKhoaMoAPI.js'
import CAPI from '../api/Commons.js'
import { currency } from '@/filters/currency'
import EventBus from '@/utils/eventBus'
import moment from 'moment'
import Vue from 'vue'
import { SplitterPlugin } from '@syncfusion/ej2-vue-layouts'
import {Splitter} from '@syncfusion/ej2-layouts'
Vue.use(SplitterPlugin)

export default {
  components: {
    Calculator,
    SuaNgayTT,
    PopupThayDoiThongTinThanhToan,
    XoaPhieu,
    appKyCuoc: KyCuoc
  },
  name: 'TraCuuPhieuThuTrongNgayCuaNVTC',

  async created () {
    EventBus.$on('chukycuocChange', this.chukycuocChangeHandler);
    EventBus.$on('onPhieuCheckboxChanged', this.onPhieuCheckboxChangedHandler);
    // await this.getKyCuocHienHanh();
    // await this.getQuyenGachNo();
    // await this.getDanhSachThamSoMD(0);
    await Promise.all([
      this.getKyCuocHienHanh(),
      this.getQuyenGachNo(),
      this.getDanhSachThamSoMD(0)
    ]);
    //Lấy ds quyền di động
  },

  mounted() {
    this.$refs.maTT.focus();
  },

  destroyed () {
    EventBus.$off('chukycuocChange', this.chukycuocChangeHandler);
    EventBus.$off('onPhieuCheckboxChanged', this.onPhieuCheckboxChangedHandler);
  },

  computed: {
    kyCuoc () {
      return moment(this.thongtinKyHoaDon.kyHoaDonSelected, 'YYYYMMDD').format('MM/YYYY')
    },
    TNAggregatesColumns () {
      return [
        { field: 'TEN_NVTC', type: 'Count', format: 'N0', footerTemplate: this.countTemplate },
        { field: 'LUOT_TT', type: 'Count', format: 'N0', footerTemplate: this.countTemplate },
        { field: 'TONGPHIEU', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
        { field: 'TONGTIEN', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate }
      ]
    },
    PTAggregatesColumns () {
      return [
        { field: 'TIEN_TRA', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.customAggregate }
      ]
    },
  },

  data () {
    return {
      params: {
        today: new Date(),
        ngayTT: new Date(),
        denNgay_Checked: false,
        denNgay: new Date(),
        maTT: '',
        tongTra: 0,
        isCalculatorShow: false,
        xoaBtn_Enable: false,
        capNhatBtn_Enable: false
      },
      thongtinKyHoaDon: {
        kyHoaDonSelected : null,
        chuKySelected : null
      },
      common_params: {
        kyHoaDonHienTai: moment(new Date()).subtract(1,'months').startOf('month').format('YYYYMMDD').toString(),
        ngay_BD: '',
        ngay_KT: '',
        ngayXoa_LuiTT: -1,
        ngayXoa_Lui: -1,
        kiemTra_MK: true,
        quyenGN_Ngay: false,
        ds_Quyen: [],
        tienTra_Sum: 0,
        tienTra_Checked: 0
      },
      NVTC_grid: {
        items: [],
        selected: {},
        key: 1
      },
      hinhThucTT_grid: {
        items: [],
        selected: {},
        key: 1
      },
      phieuTT_grid: {
        items: [],
        selected: {},
        checked: [],
        key: 1,
        itemsTemp: []
      },
      sumTemplate: function() {
        return { template: Vue.component('sumTemplate', {
            template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data: function () {return {data: {data: {}}};}
          })
        }
      },
      countTemplate: function() {
        return { template: Vue.component('countTemplate', {
            template: `<input type="text" class="form-control tright" :value="data.Count" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data: function () {return {data: {data: {}}};}
          })
        }
      },
      customTemplate: function() {
        return  { template : Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data () { return { data: {data: {}}}; }
          })
        }
      },
      checkboxColumnTTTemplate: function () {
        return {
          template: {
            template: `<div class="check-action" style="vertical-align: middle;">
                        <input type="checkbox" class="check" @change="onChanged" v-model="data.CHON"/>
                        <span class="name"></span>
                      </div>`,
            data() { return { data: {}}},
            methods: {
              onChanged() {
                EventBus.$emit('onPhieuCheckboxChanged', this.data);
              }
            }
      }}},
      statusTemplate () {
        return { template : Vue.component('columnTemplate', {
          template:   `<div>
                        <span v-if="data.TRANGTHAI === '0'" class="one-alert f20 text-warning"></span>
                        <span v-else-if="data.TRANGTHAI === '1'" class="one-check f20 text-success"></span>
                      </div>`,
          data () { return { data: {} }; }})
        }
      }
    }
  },

  methods: {
    onCreate(e) {
      this.splitterObj = new Splitter({
        paneSettings: [
            { size: '50%', min: '20%' },
            { size: '50%', min: '20%'}
        ],
        orientation: 'Vertical'
      });
      this.splitterObj.appendTo('#vertical_splitter');
    },

    customAggregate() {
      return currency(this.common_params.tienTra_Checked) + '/' + currency(this.common_params.tienTra_Sum);
    },

    onMaTTKeyPress() {
      this.params.maTT = this.params.maTT.trim();
      this.phieuTT_grid.key++;

      if (this.params.maTT !== '') {
        let idx = this.phieuTT_grid.itemsTemp.findIndex(e => e.MA_TT === this.params.maTT);
        if (idx === -1) {
          this.phieuTT_grid.items = [];
          this.phieuTT_grid.checked = [];
        } else {
          this.phieuTT_grid.itemsTemp[idx].CHON = true;
          this.phieuTT_grid.items =  [...this.phieuTT_grid.itemsTemp];
          this.onPhieuCheckboxChangedHandler(this.phieuTT_grid.items[idx]);

          //Select row:
          let timeOut = setInterval(() => {
            this.$refs.phieuTraGrid.setCurrentSelectedRow(idx);
            clearInterval(timeOut);
          }, 200);
        }
      } else {
        this.phieuTT_grid.items =  [...this.phieuTT_grid.itemsTemp];
        this.phieuTT_grid.checked = [];
        this.phieuTT_grid.items.forEach(item => {
          if (item.CHON) {
            this.onPhieuCheckboxChangedHandler(item);
          }
        });
      }
    },

    onPhieuCheckboxChangedHandler(args) {
      let index = this.phieuTT_grid.items.findIndex(e => e.PHIEU_ID === args.PHIEU_ID);
      if (index !== -1) {
        //CHECKED
        if (args.CHON) {
          this.phieuTT_grid.items[index].CHON = true;
          this.common_params.tienTra_Checked += this.phieuTT_grid.items[index].TIEN_TRA;
        //   this.phieuTT_grid.checked.push(args);
          this.phieuTT_grid.checked.push(this.phieuTT_grid.items[index]);
        } else {
        //UN-CHECKED
          this.phieuTT_grid.items[index].CHON = false;
          this.common_params.tienTra_Checked -= this.phieuTT_grid.items[index].TIEN_TRA;
          let indexChecked = this.phieuTT_grid.checked.findIndex(e => e.PHIEU_ID === args.PHIEU_ID);
            if (indexChecked !== -1)
              this.phieuTT_grid.checked.splice(indexChecked, 1);
        }

        //REFRESH DATASOURCE & FOOTER
        let dataGridObj = this.$refs.phieuTraGrid.$refs.grid.ej2Instances;
        dataGridObj.aggregateModule.refresh();
        this.phieuTT_grid.itemsTemp = [...this.phieuTT_grid.items];
      }
    },

    async onSearch() {
      await this.getDSNVTC();
      if (this.NVTC_grid.items.length <= 0)
        this.clear();
    },

    clear() {
      this.params.tongTra = 0;
      this.NVTC_grid.items = [];
      this.hinhThucTT_grid.items = [];
      this.phieuTT_grid.items = [];
      this.params.xoaBtn_Enable = false;
      this.params.capNhatBtn_Enable = false;
    },

    async onNVTCSelect(item) {
      if (item !== null && item !== {}) {
        this.NVTC_grid.selected = item;
        this.params.tongTra = (item.TONGTIEN !== null && item.TONGTIEN !== undefined) ? item.TONGTIEN : 0;
        await this.getDSHTTT(item.MANV_TC);
        if (this.hinhThucTT_grid.items.length > 0) {
          this.params.xoaBtn_Enable = true;
          this.params.capNhatBtn_Enable = true;
        } else {
          this.params.xoaBtn_Enable = false;
          this.params.capNhatBtn_Enable = false;
        }
      }
    },

    async onHTTTSelect(item) {
      if (item !== null && item !== {}) {
        this.hinhThucTT_grid.selected = item;
        await this.getDSPhieuThu(this.NVTC_grid.selected.MANV_TC, item.HTTT_ID, item.LANTRA);
      }
    },

    async onDelete() {
      if (this.checkDataOnGrid(1)) {
        if (this.common_params.kiemTra_MK) {
          if (this.phieuTT_grid.checked.length > 0)
            this.$refs.dlgXoaPhieuTT.openDialog();
        } else {
          try {
            await this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn xóa phiếu thanh toán không?', {
              "title": 'Thông báo',
              "size": 'lg',
              "buttonSize": 'lg',
              "okVariant": 'primary',
              "okTitle": 'Đồng ý',
              "cancelTitle": 'Hủy bỏ',
              "hideHeaderClose": false,
              "centered": true,
              "modal-class": ["f18","text-center"],
            })
            .then(async(value) => {
              if (value){
                await this.deleteProcess();
              }
            })
            .catch(err => {
              // An error occurred
            })
          } catch (error) {
            this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
          }
        }
      }
    },

    async deletePhieuThuHandler(args) {
      if (args)
        await this.onSearch();
    },

    async deleteProcess() {
      let count = 0;
      for (let item of this.phieuTT_grid.checked) {
        if (!this.checkDate(item, 1)) continue;
        let ngayTT = moment(item.NGAY_TT, 'DD/MM/YYYY').toDate();
        let ngayHT = new Date();
        //Cùng tháng:
        if (ngayTT.getFullYear() === ngayHT.getFullYear() && ngayTT.getMonth() === ngayHT.getMonth()) {
          if (!this.checkQuyenGach('XOAGACH', '0', ngayTT)) return;
        } else {
          //Khác tháng:
          if (!this.checkQuyenGach('XOAGACH', '1', ngayTT)) return;
        }

        if (item.PHIEU_ID_NEO != null && item.PHIEU_ID_NEO > 0) {
          if (item.NGUOI_CN !== this.$auth.getUserName())
            return this.$toast.error('Không được xóa phiếu thanh toán đã đồng bộ lên VNP của người khác!');
        }

        //Check quyen_dd
        //{}

        let result = await this.deletePhieuThu(item.PHIEU_ID);
        if (result) count++;
      };

      if (count === this.phieuTT_grid.checked.length)
        this.$toast.success('Xóa phiếu thanh toán thành công!');
      await this.onSearch();
    },

    onChangeDate() {
      if (this.checkDataOnGrid(0)) {
        let ngayTT = moment(this.phieuTT_grid.checked[0].NGAY_TT, 'DD/MM/YYYY').toDate();
        if (this.common_params.ngayXoa_LuiTT > 0) {
          let today = new Date();
          let ngayGach_Max = new Date(today.getFullYear(), today.getMonth(), this.common_params.ngayXoa_LuiTT);
          if (today > ngayGach_Max && this.common_params.kyHoaDonHienTai !== this.thongtinKyHoaDon.kyHoaDonSelected)
            return this.$toast.error('Bạn không được thay đổi ngày thanh toán do ngày hiện tại không thuộc khoảng thời gian cho phép!');
          if (this.common_params.kyHoaDonHienTai !== this.thongtinKyHoaDon.kyHoaDonSelected) {
            let thang_TT = new Date(today.getFullYear(), today.getMonth() - 1, this.common_params.ngayXoa_LuiTT);
            if (thang_TT.getFullYear() !== ngayTT.getFullYear() || thang_TT.getMonth() !== ngayTT.getMonth())
              return this.$toast.error(`Bạn chỉ được phép sửa ngày thanh toán của tháng ${thang_TT.getMonth() + 1}`);
          }
        }

        this.$refs.dlgThayDoiNgayTT.openDialog();
      }
    },

    async updateDatePhieuThuHandler(args) {
      if (args)
        await this.onSearch();
    },

    //Chuyển sang thực hiện trên Popup thay đổi ngày thanh toán nên không còn dùng hàm này
    async changeDateProcess(args) {
      let value = args.ngay_ttoan_moi
      let password = args.password
      let count = 0;
      if (value !== null && value !== undefined && value !== '') {
        for (let item of this.phieuTT_grid.checked) {
          let phieu = this.phieuTT_grid.items.find(e => e.MA_TT === item);
          let ngayHT = new Date();
          let ngayTT_Cu = moment(phieu.NGAY_TT, 'DD/MM/YYYY').toDate();

          //Cùng tháng
          if (ngayTT_Cu.getFullYear() === ngayHT.getFullYear() && ngayTT_Cu.getMonth() === ngayHT.getMonth()) {
            if (value.getFullYear() !== ngayTT_Cu.getFullYear() ||  value.getMonth() !== ngayTT_Cu.getMonth())
              return this.$toast.error('Bạn chỉ được sửa ngày thanh toán trong tháng!');
            if (ngayHT.getDate() < value.getDate())
              return this.$toast.error('Bạn chỉ được sửa ngày thanh toán nhỏ hơn hoặc bằng ngày hiện tại!');
            if (value < ngayHT) {
              let days = this.differenceDates(value, ngayHT);
              if (this.common_params.ngayXoa_Lui > -1 && this.common_params.ngayXoa_Lui < days)
                return this.$toast.error(`Bạn không được phép cập nhật ngày thanh toán của mã TT: ${phieu.MA_TT} .Thời điểm cho phép cập nhật là cách ngày hiện tại ${days} ngày!`);
            }
          } else {
            //Khác tháng
            if (this.common_params.kyHoaDonHienTai !== this.thongtinKyHoaDon.kyHoaDonSelected) {
              let firstDateOfMonth = new Date(ngayHT.getFullYear(), ngayHT.getMonth() - 1, 1);
              if (value.getFullYear() !== firstDateOfMonth.getFullYear() || value.getMonth() !== firstDateOfMonth.getMonth())
                return this.$toast.error('Bạn không được sửa ngày thanh toán sang kỳ khác!');
            } else
              return this.$toast.error('Bạn không được sửa ngày thanh toán sang kỳ khác!');
          }

          if (phieu.PHIEU_ID_NEO != null && phieu.PHIEU_ID_NEO > 0)
            return this.$toast.error('Bạn không được sửa thông tin với dịch vụ di động. Do đã được gạch trên VNP!');

          let result = await this.updateNgayThanhToan(phieu.PHIEU_ID, moment(value).format('DD/MM/YYYY').toString(), password);
          if (result) count++;
        }

        if (count === this.phieuTT_grid.checked.length)
          this.$toast.success('Sửa ngày thanh toán thành công!');
        await this.getDSPhieuThu(this.NVTC_grid.selected.MANV_TC, this.hinhThucTT_grid.selected.HTTT_ID, this.hinhThucTT_grid.selected.LANTRA);
      }
    },

    onChangeDetail() {
      if (this.checkDataOnGrid(0)) {
        this.phieuTT_grid.checked[0].NHOM_HTTT_ID = this.hinhThucTT_grid.selected.NHOM_HTTT_ID;
        this.phieuTT_grid.checked[0].HTTT_ID = this.hinhThucTT_grid.selected.HTTT_ID;
        this.phieuTT_grid.checked[0].THUNGAN_ID = 0;
        this.phieuTT_grid.checked[0].LANTRA = this.hinhThucTT_grid.selected.LANTRA;
        this.$refs.dlgThayDoiThongTinTT.openDialog();
      }
    },

    async changeDetailProcess(args) {
      if (args) {
        let count = 0;
        for (let item of this.phieuTT_grid.checked) {
          if (item.PHIEU_ID_NEO != null && item.PHIEU_ID_NEO > 0)
            return this.$toast.error(`Bạn không được sửa thông tin với dịch vụ di động. Do đã được gạch trên VNP!`);

          let apiBody = {
            ghiChu: args.ghiChuChecked ? args.ghiChu : '',
            htttId: args.thanhToanChecked ? args.HTTTId : this.hinhThucTT_grid.HTTT_ID,
            kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
            lanTra: args.thanhToanChecked ? args.lanTra : item.LANTRA,
            matKhau: args.matKhau,
            phieuId: item.PHIEU_ID,
            thuNganId: args.thanhToanChecked ? args.thuNganId : this.thuNgan_grid.THUNGAN_ID
          };
          let rs = await this.updateThongTinThanhToan(apiBody);
          if (rs) count++;
        };
        if (count === this.phieuTT_grid.checked.length)
          this.$toast.success('Sửa thông tin thanh toán thành công!');
        await this.onSearch();
      }
    },

    onEditGhiChu(arg) {
      let key = arg.rowData.MA_TT;
      let idx = this.phieuTT_grid.items.findIndex(e => e.MA_TT === key);
      if (idx !== -1)
        this.phieuTT_grid.items[idx].GHICHU = arg.value;
      if(this.phieuTT_grid.checked.length > 0) {
        let idx2 = this.phieuTT_grid.checked.findIndex(e => e.MA_TT === key);
        if (idx2 !== -1)
            this.phieuTT_grid.checked[idx2].GHICHU = arg.value;
      }
    },

    async onUpdate() {
      if (this.checkDataOnGrid(0)) {
        try {
          await this.$bvModal.msgBoxConfirm('Bạn thật sự muốn sửa thông tin ghi chú không?', {
            "title": 'Thông báo',
            "size": 'lg',
            "buttonSize": 'lg',
            "okVariant": 'primary',
            "okTitle": 'Đồng ý',
            "cancelTitle": 'Hủy bỏ',
            "hideHeaderClose": false,
            "centered": true,
            "modal-class": ["f18","text-center"],
          })
          .then(async(value) => {
            if (value){
              let count = 0;
              for (let item of this.phieuTT_grid.checked) {
                if (!this.checkDate(item, 0)) continue;
                if (item.PHIEU_ID_NEO != null && item.PHIEU_ID_NEO > 0)
                  return this.$toast.error('Bạn không được sửa thông tin với dịch vụ di động. Do đã được gạch trên VNP!');
                let rs = await this.updateGhiChu(item.PHIEU_ID, item.GHICHU);
                if (rs) count++;
                else return;
              }
              if (count === this.phieuTT_grid.checked.length)
                this.$toast.success('Cập nhật ghi chú thành công!');
            }
          })
          .catch(err => {
            // An error occurred
            console.log(err);
          })
        } catch (error) {
          this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
        }
      }
    },

    onExcelExport() {
      if (this.phieuTT_grid.items.length <= 0)
        this.$toast.error("Chưa có danh sách để xuất excel!");
      else {
        let excelExportProperties = {
          fileName:"DSPhieuThanhToan.xlsx",
          dataSource: this.phieuTT_grid.items
        };
        this.$refs.phieuTraGrid.excelExport(excelExportProperties);
      }
    },

    async onSyncVNP() {
      if (this.phieuTT_grid.items.length <= 0)
        return this.$toast.error(`Không có dữ liệu phiếu trả đồng bộ!`);
      if (this.phieuTT_grid.checked.length <= 0)
        return this.$toast.error(`Bạn chưa check chọn phiếu trả để đồng bộ VNP!`);
      let ds = this.phieuTT_grid.checked.filter(e => (e.PHIEU_ID_NEO == null || e.PHIEU_ID_NEO === 0) && (e.TRANGTHAI === '-1' || e.TRANGTHAI === '1'));
      if (ds.length > 0)
        return this.$toast.error(`Bạn không được chọn phiếu đã đồng bộ hoặc phiếu không phải dịch vụ di động!`);

      let count = 0;
      for (let item of this.phieuTT_grid.checked) {
        let dt = [];
        dt.push({
          kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
          httt: item.HTTT_ID,
          maTT: item.MA_TT,
          nguoiCN: item.NGUOI_CN,
          phieuId: item.PHIEU_ID,
          phieuIdNeo: item.PHIEU_ID_NEO,
          trangThai: item.TRANGTHAI
        });
        let rs = await this.updateDongBoCCBS(dt, this.hinhThucTT_grid.selected.NHOM_HTTT_ID);
        if (rs) count++;
      }

      if (count === this.phieuTT_grid.checked.length)
        this.$toast.success("Đồng bộ VNP thành công!");

      await this.onSearch();
    },

    async getThongTinChuKyNo() {
      try {
        this.loading(true);
        let response = await CAPI.getThongTinChuKyNo(this.axios, this.thongtinKyHoaDon.kyHoaDonSelected);
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.common_params.ngay_BD = response.data.data[0].NGAY_BD;
          this.common_params.ngay_KT = response.data.data[0].NGAY_KT;
        }
      }
      catch (error) {
        console.log(error);
      }
      finally {
        this.loading(false);
      }
    },

    async getKyCuocHienHanh() {
      try {
        this.loading(true);
        let response = await CAPI.getKyCuocHienHanh(this.axios);
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.common_params.kyHoaDonHienTai = response.data.data[0].KYCUOC.toString();
        }
      }
      catch (error) {
        console.log(error);
      }
      finally {
        this.loading(false);
      }
    },

    async getQuyenGachNo() {
      try {
        this.loading(true);
        let response = await TCAPI.getQuyenGachNo(this.axios);
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          if (response.data.data[0].NGAY_GL !== null)
            this.common_params.ngayXoa_LuiTT = response.data.data[0].NGAY_GL;
          if (response.data.data[0].NGAY_XL !== null)
            this.common_params.ngayXoa_Lui = response.data.data[0].NGAY_XL;
        }
      }
      catch (error) {
        console.log(error);
      }
      finally {
        this.loading(false);
      }
    },

    async getDanhSachThamSoMD(id) {
      try {
        this.loading(true);
        let response = await DAPI.getDanhSachThamSoMD(this.axios, id);
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          response.data.data.forEach(item => {
            if (item.MA_TS === 'KHONG_KT_MATKHAU_XOA_GACHNO' && item.TEN_TS.trim() === '1')
              this.common_params.kiemTra_MK = false;
            if (item.MA_TS === 'QUYENGN_NGAY' && item.TEN_TS.trim() === '1') {
              this.common_params.quyenGN_Ngay = true;
            }
          })
        }
      }
      catch (error) {
        console.log(error);
      }
      finally {
        this.loading(false);
      }
    },

    async getDSNVTC() {
      try {
        this.loading(true);
        this.NVTC_grid.items = [];
        this.NVTC_grid.key++;
        let apiParams = {
          kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
          ngayTT: moment(this.params.ngayTT).format('DD/MM/YYYY'),
          denNgay: this.params.denNgay_Checked ? moment(this.params.denNgay).format('DD/MM/YYYY') : null
        };
        let response = await TCAPI.getDSNVTC(this.axios, apiParams);
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.NVTC_grid.items = response.data.data;
        }
      }
      catch (error) {
        console.log(error);
      }
      finally {
        this.loading(false);
      }
    },

    async getDSHTTT(NVTCId) {
      try {
        this.loading(true);
        this.hinhThucTT_grid.items = [];
        this.hinhThucTT_grid.key++;
        let apiParams = {
          kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
          ngayTT: moment(this.params.ngayTT).format('DD/MM/YYYY'),
          denNgay: this.params.denNgay_Checked ? moment(this.params.denNgay).format('DD/MM/YYYY') : null,
          maNVTC: NVTCId
        }
        let response = await TCAPI.getDSHinhThucTT2(this.axios, apiParams);
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.hinhThucTT_grid.items = response.data.data;
        }
      }
      catch (error) {
        console.log(error);
      }
      finally {
        this.loading(false);
      }
    },

    async getDSPhieuThu(NVTCId, HTTTId, lanTra) {
      try {
        this.loading(true);
        this.phieuTT_grid.key++;
        this.phieuTT_grid.items = [];
        this.phieuTT_grid.checked = [];
        this.phieuTT_grid.itemsTemp = [];
        this.common_params.tienTra_Sum = 0;
        this.common_params.tienTra_Checked = 0;
        let apiParams = {
          kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
          ngayTT: moment(this.params.ngayTT).format('DD/MM/YYYY'),
          denNgay: this.params.denNgay_Checked ? moment(this.params.denNgay).format('DD/MM/YYYY') : null,
          maNVTC: NVTCId,
          htttId: HTTTId,
          nguoiCN: this.$auth.getUserName(),
          lanTra: lanTra
        };
        let response = await TCAPI.getDSPhieuThu(this.axios, apiParams);
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.common_params.tienTra_Sum = 0;
          response.data.data = response.data.data.map((item) => ({
            ...item,
            CHON: false
          }));
          this.phieuTT_grid.items = response.data.data;
          this.phieuTT_grid.itemsTemp = response.data.data;
          response.data.data.forEach(item => {
            this.common_params.tienTra_Sum += item.TIEN_TRA;
          })
        }
        this.phieuTT_grid.items = [...this.phieuTT_grid.items];
      }
      catch (error) {
        console.log(error);
      }
      finally {
        this.loading(false);
      }
    },

    /* return: true/false = successful/failed */
    async updateNgayThanhToan(phieuId, ngayTT_Moi, password) {
      try {
        let apiBody = {
          pKyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
          pPhieuId: phieuId,
          pNgayThanhToan: ngayTT_Moi,
          pPassword: password
        };
        let response = await XLAPI.updateNgayThanhToan(this.axios, apiBody);
        return response.data.error_code === 'BSS-00000000' ? true : false;
      }
      catch (error) {
        this.$toast.error(error.data.message_detail);
        return false;
      }
      finally {}
    },
    /* Params: apiBody: { kyCuoc: Number, phieuId: Number, thuNganId: Number, htttId: Number, lanTra: Number, ghiChu: String, matKhau: String } */
    /* Return: true/false = successful/failed */
    async updateThongTinThanhToan(apiBody) {
      try {
        this.loading(true);
        let response = await XLAPI.updateThongTinThanhToan(this.axios, apiBody);
        return response.data.error_code === 'BSS-00000000' ? true : false;
      }
      catch(error) {
        this.$toast.error(error.data.message_detail);
        return false;
      }
      finally {
        this.loading(false);
      }
    },

    /* Return: true/false = successful/failed */
    async deletePhieuThu(phieuId) {
      try {
        let apiBody = {
          pKyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
          pMayHuy: '-',
          pNguoiHuy: this.$.$auth.getUserName(),
          pPhieuId: phieuId,
          pPassword: ''
        };
        let response = await XLAPI.deletePhieuThanhToan(this.axios, apiBody);
        return response.data.error_code === 'BSS-00000000' ? true : false;
      }
      catch (error) {
        this.$toast.error(error.data.message_detail);
        return false;
      }
      finally {}
    },

    /* Return: true/false = successful/failed */
    async updateGhiChu(phieuId, ghiChu) {
      try {
        let apiBody = {
          phieuId: phieuId,
          ghiChu: ghiChu,
          kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected
        };
        let response = await XLAPI.updateGhiChuPhieuThanhToan(this.axios, apiBody);
        return response.data.error_code === 'BSS-00000000' ? true : false;
      }
      catch (error) {
        this.$toast.error(error.data.message_detail);
        return false;
      }
      finally {}
    },

    async updateDongBoCCBS(data, nhomHTTT) {
      try {
        this.loading(true);
        let apiBody = {
          data: data,
          nhomHTTT: nhomHTTT
        };
        let response = await TCAPI.updateDongBoCCBS(this.axios, apiBody);
        return response.data.error_code === 'BSS-00000000' ? true : false;
      }
      catch (error) {
        this.$toast.error(error.data.message_detail);
        return false;
      }
      finally {
        this.loading(false)
      }
    },

    checkDataOnGrid(type) {
      let action = '';
      if (type === 0) action = 'cập nhật';
      else if (type === 1) action = 'xóa';

      if (this.phieuTT_grid.items.length <= 0) {
        this.$toast.error(`Chưa có danh sách phiếu thanh toán để ${action}!`);
        return false;
      }
      if (this.phieuTT_grid.checked.length <= 0) {
        this.$toast.error(`Chưa chọn phiếu thanh toán để ${action}!`);
        return false;
      }
      if (this.common_params.kyHoaDonHienTai !== this.thongtinKyHoaDon.kyHoaDonSelected
        && this.common_params.ngayXoa_LuiTT === -1) {
          this.$toast.error(`Bạn không được ${action} phiếu của kỳ hóa đơn khác!`);
          return false;
      }
      return true;
    },

    /* params[1]: phieu - Thông tin phiếu trả
     * params[2]: kieu - Kiểu thực hiện: 0 - Cập nhật ghi chú; 1 - Xóa phiếu
     * return: true/false
     */
    checkDate(phieu, kieu) {
      let ngayTT = moment(phieu.NGAY_TT, 'DD/MM/YYYY').toDate();
      let ngayHT = new Date();

      let action = '';
      if (kieu === 0) action = 'cập nhật thông tin';
      else if (kieu === 1) action = 'xóa';

      //Khác tháng
      if (ngayTT.getFullYear() !== ngayHT.getFullYear() || ngayTT.getMonth() !== ngayHT.getMonth()) {
        if (this.common_params.ngayXoa_LuiTT === -1) {
          this.$toast.error(`Bạn không được phép ${action} phiếu của của mã TT: ${phieu.MA_TT}. Do chưa được thiết lập ngày cho phép xóa lùi`);
          return false;
        }

        let ngayGach_Max = new Date(ngayHT.getFullYear(), ngayHT.getMonth(), this.common_params.ngayXoa_LuiTT);
        if (ngayHT > ngayGach_Max) {
          this.$toast.error(`Bạn không được phép ${action} phiếu của của mã TT: ${phieu.MA_TT}. Do ngày hiện tại không thuộc khoảng thời gian cho phép`);
          return false;
        }

        let thangTT = new Date(ngayHT.getFullYear(), ngayHT.getMonth() - 1, this.common_params.ngayXoa_LuiTT);
        let firstDateOfMonth = new Date(thangTT.getFullYear(), thangTT.getMonth(), 1);
        let lastDateOfMonth = new Date(thangTT.getFullYear(), thangTT.getMonth() + 1, 0);
        if (ngayTT < firstDateOfMonth || ngayTT > lastDateOfMonth) {
          this.$toast.error(`Bạn không được phép ${action} phiếu của của mã TT: ${phieu.MA_TT} do ngày thanh toán khác tháng ${thangTT.getMonth() + 1}`);
          return false;
        }
      } else {
        //Cùng tháng
        let days = this.differenceDates(ngayTT, ngayHT);
        if (ngayTT < ngayHT) {
          if (this.common_params.ngayXoa_Lui > -1 && this.common_params.ngayXoa_Lui < days) {
            this.$toast.error(`Bạn không được phép ${action} phiếu của của mã TT: ${phieu.MA_TT}. Thời điểm cho phép cập nhật là cách ngày hiện tại ${days} ngày!`);
            return false;
          }
        }
      }
      return true;
    },

    /* params[1]: kieuGach - '0': trong tháng; '1': tháng trước
     * params[2]: kieuGach - 'GACHNO'; 'XOALUI'
     * params[3]: ngayTT - Ngày thành toán của phiếu
     * return: true/false
     */
    checkQuyenGach(kieuGach, kieu, ngayTT) {
      if (this.common_params.quyenGN_Ngay) {
        let kieuQuyen = 0;  //1 - quyenGN_Thang; 2 - quyenXoa_Thang; 3 - quyenGN_Lui_Thang; 4 - quyenXoa_Lui_Thang
        // Get
        // lay_ds_quyen_gachno
        //
        if (kieu === 'GACHNO')
          kieuQuyen = kieuGach === '0' ? 1 : 3;
        else //'XOAGACH'
          kieuQuyen = kieuGach === '0' ? 2 : 4;
        let quyen = this.getQuyenGach(kieuQuyen);
        return this.onCheckQuyen(kieuGach, kieu, kieuQuyen, quyen, ngayTT);
      } else return true;
    },

    onCheckQuyen(kieuGach, kieu, kieuQuyen, quyen, ngayTT) {
      let action = kieu === 'XOAGACH' ? 'xóa phiếu' : 'gạch lùi';
      let time = kieuGach === '0' ? 'của tháng hiện tại' : 'của tháng trước';
      let today = new Date();

      if (ngayTT.getDate() < today.getDate()) {
        if (this.common_params.ds_Quyen.length <= 0) {
          this.$toast.error(`Bạn chưa có quyền được ${action} ${time}!`);
          return false;
        }

        let day = ngayTT.getDate().toString().padStart(2, '0');
        if (quyen === -1) {
          this.$toast.error(`Bạn chưa có quyền được ${action} ${time}!`);
          return false;
        } else if (quyen === 1) {
          if (this.common_params.ds_Quyen.filter(e => e.KIEU === kieuQuyen && e.ND === 0 && e.NGAY===day).length <= 0) {
            this.$toast.error(`Bạn không có quyền ${action} vào ngày ${day} ${time}!`);
            return false;
          }
        } else {  // === 2
          if (this.common_params.ds_Quyen.filter(e => e.KIEU === kieuQuyen && e.ND === 1 && e.NGAY===day).length <= 0) {
            this.$toast.error(`Bạn không có quyền ${action} vào ngày ${day} ${time}!`);
            return false;
          }
        }
        return true;
      }
    },

    /* kieu: 1 - quyenGN_Thang; 2 - quyenXoa_Thang; 3 - quyenGN_Lui_Thang; 4 - quyenXoa_Lui_Thang */
    getQuyenGach(kieuQuyen) {
      if (this.common_params.ds_Quyen.length > 0) {
        //Lấy quyền ND trước:
        let dsND_DayOfMonth = this.common_params.ds_Quyen.filter(e => e.KIEU === kieuQuyen && e.ND === 1);
        if (dsND_DayOfMonth.length <= 0) {
          //Nếu không có quyền ND thì lấy quyền nhóm ND:
          let dsNND_DayOfMonth = this.common_params.ds_Quyen.filter(e => e.KIEU === kieuQuyen && e.ND === 0);
          return dsNND_DayOfMonth.length <= 0 ? -1 : 1 ;
        } else return 2;
      } else return -1;
    },

    async chukycuocChangeHandler(e) {
      this.thongtinKyHoaDon.kyHoaDonSelected = moment(e.p_kyhoadon).format('YYYYMM') + '01';
      this.thongtinKyHoaDon.chuKySelected = e.p_chuky_no;
      await this.getThongTinChuKyNo();
    },

    /* params[1]: date1 - Date object
     * params[2]: date1 - Date object
     * return: Number
     */
    differenceDates(date1, date2) {
      let diff = Math.abs(date1 - date2);
      return diff / (1000 * 3600 * 24);
    }
  },

  watch: {
  }
}
</script>
