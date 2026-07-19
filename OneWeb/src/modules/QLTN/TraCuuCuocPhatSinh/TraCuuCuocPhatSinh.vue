<template src="./TraCuuCuocPhatSinh.html"></template>
<style scoped src="./TraCuuCuocPhatSinh.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import KyCuoc from '@/components/KyCuoc'
import API from '../api/TraCuuCuocPhatSinhAPI'
import { mapActions, mapState, mapGetters } from 'vuex'
import EventBus from '@/utils/eventBus'
import moment from 'moment'
import Vue from 'vue'

export default {
  components: { breadcrumb, appKyCuoc: KyCuoc },
  name: 'TraCuuCuocPhatSinh',
  async created () {
    EventBus.$on('chukycuocChange', this.chukycuocChangeHandler);
    if (this.danhSachNhanVienThuCuocGetter == null || this.danhSachNhanVienThuCuocGetter.length === 0)
      await this.getDanhSachNhanVienThuCuoc();
    if (this.danhSachNhanVienThuCuocGetter.length > 0) {
      this.danhSachNhanVienThuCuocGetter.forEach(item => {
        this.params.nhanVienTC_Options.push({ id: item.MA_NV, text: item.TEN_NV })
      });
      this.params.nhanVienTC_Selected = this.params.nhanVienTC_Options[0].id;
    }    
  },  
  destroyed () {
    EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
  },
  computed: {
    ...mapGetters('qltnCommon', ['danhSachNhanVienThuCuocGetter']),
    aggregatesColumns () {
      return [
        { field: 'TIEN_DK', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
        { field: 'TIEN_PS', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
        { field: 'TONGNO', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate }
      ]
    },
  },

  data () {
    return {
      thongtinKyHoaDon: {
        kyHoaDonSelected : null,
        chuKySelected : null
      },
      params: {
        nhanVienTC_Options: [],
        nhanVienTC_Selected: null,
        nhanVienTC_Checked: true,
        kieuNo_Checked: false,
        kieuNo_Options: [
          { id: 0, text: 'Nợ đầu kỳ' },
          { id: 5, text: 'Nợ phát sinh' },
          { id: 1, text: 'Tổng nợ' }          
        ],
        kieuNo_Selected: 1,
        tuTien_Checked: false,
        tuTien: 0,
        tuDau_Selected: '>',
        denTien_Checked: false,      
        denTien: 0,
        denDau_Selected: '<'
      },      
      thanhToan_grid: {
        items: [],
        pageinfo : {
          page: 0,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          sort: null,
          propertiesSort: null,
          currentRow: 0
        },
        key: 1
      },
      exportExcel: {      
        sessionId: -1,
        interval: null
      },
      sumTemplate: function() {
        return { template: Vue.component('sumTemplate', {
            template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data: function () {return {data: {data: {}}};}
          })
        }
      }      
    }
  },

  methods: {
    ...mapActions('qltnCommon', ['getDanhSachNhanVienThuCuoc']),    

    async onSearch() {
      this.resetPaging();
      if (this.params.nhanVienTC_Selected === null)
        this.$toast.error('Chưa chọn nhân viên thu cước!');
      else {        
        await this.getDoanhThuPS();
        if (this.thanhToan_grid.items.length <= 0)
          this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
      }
    },

    async onExcelExport() {
      if (this.thanhToan_grid.items.length <= 0)
        this.$toast.error('Chưa có dữ liệu trên lưới để xuất excel!');
      else {
        this.exportExcel.sessionId = await this.getXuatFileXlsx();
        if (this.exportExcel.sessionId && this.exportExcel.sessionId !== -1) {
          this.loading(true);
          //0 - 4999 rows: 5s
          //5000 - 49999 rows: 15s
          //>= 50000 rows: 30s
          let interval = this.thanhToan_grid.pageinfo.totalElement > 5000 ? (this.thanhToan_grid.pageinfo.totalElement > 50000 ? 30000 : 15000) : 5000;
          console.log('interval', interval)
          this.exportExcel.interval = setInterval(async () => {
            await this.axios({
              url: "https://api-onebss-dev.vnptit3.vn/web-thuno/api/thu-no/session/" + this.exportExcel.sessionId,
              method: "GET",
            }).then((response) => {
              if (response.data.data.status === 'COMPLETED') {
                clearInterval(this.exportExcel.interval);
                var fURL = document.createElement("a");
                fURL.href = response.data.data.description;
                fURL.setAttribute("download", "DanhSachCuocPhatSinh.xlsx");
                document.body.appendChild(fURL);
                fURL.click();
                this.loading(false);
              }
            });
          }, interval);
        }
      }
    },
    
    chukycuocChangeHandler(e) {
      this.thongtinKyHoaDon.kyHoaDonSelected =moment(e.p_kyhoadon).format('YYYYMM') + '01'
      this.thongtinKyHoaDon.chuKySelected = e.p_chuky_no
    },

    thanhToanGridpageChangeHandler(e) {
      this.thanhToan_grid.pageinfo.page = e.pageIndex;
      this.thanhToan_grid.pageinfo.maxSize = e.pageSize;
      this.getDoanhThuPS();
    },

    resetPaging() {
      this.thanhToan_grid.items = [];
      this.thanhToan_grid.pageinfo = {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      }
    },

    async getDoanhThuPS() {
      try {
        this.loading(true);
        this.thanhToan_grid.key++;
        let apiParams = {
          kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
          maNVTC: this.params.nhanVienTC_Checked ? this.params.nhanVienTC_Selected : '',
          tuTien: this.params.tuTien_Checked ? this.params.tuTien : null,
          tuDau: this.params.tuTien_Checked ? this.params.tuDau_Selected : null,
          denTien: this.params.denTien_Checked ? this.params.denTien : null,
          denDau: this.params.denTien_Checked ? this.params.denDau_Selected : null,
          kieuNo: this.params.kieuNo_Checked ? this.params.kieuNo_Selected : null,
          pageNum: this.thanhToan_grid.pageinfo.page + 1,
          pageSize: this.thanhToan_grid.pageinfo.maxSize,
          totalRow: 1
        }
        let response = await API.getDSDoanhThuCuocPSTheoNhanVien(this.axios, apiParams);
        if (response.data.error_code === 'BSS-00000000' && response.data.data != null && response.data.data.data.length > 0) {
          this.thanhToan_grid.items = response.data.data.data;
          this.thanhToan_grid.pageinfo = {
            page: response.data.data.page,
            maxSize: response.data.data.maxSize,
            totalElement: response.data.data.totalElement,
            totalPages: response.data.data.totalPages,
            sort: response.data.data.sort,
            propertiesSort: response.data.data.propertiesSort,
            currentRow: 0
          };
        } else this.resetPaging();
      }
      catch (error) {
        console.log(error);
        this.resetPaging();
      }
      finally {
        this.loading(false);
      }        
    },

    async getXuatFileXlsx() {
      try {
        this.loading(true);
        let apiParams = {
          kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
          maNVTC: this.params.nhanVienTC_Checked ? this.params.nhanVienTC_Selected : '',
          tuTien: this.params.tuTien_Checked ? this.params.tuTien : null,
          tuDau: this.params.tuTien_Checked ? this.params.tuDau_Selected : null,
          denTien: this.params.denTien_Checked ? this.params.denTien : null,
          denDau: this.params.denTien_Checked ? this.params.denDau_Selected : null,
          kieuNo: this.params.kieuNo_Checked ? this.params.kieuNo_Selected : null
        }
        let response = await API.getXuatFileXlsx(this.axios, apiParams);
        if (response.data.error_code === 'BSS-00000000' && response.data.data != null)
          return response.data.data.sessionId;
        else return -1;
      }
      catch (error) {
        if (error.data.message_detail)
          this.$toast.error(error.data.message_detail);
        return -1;
      }
      finally {
        this.loading(false);
      }        
    }
  },

  watch: {
    'params.kieuNo_Checked': async function(newVal, oldVal) {
      if (!newVal) {
        this.params.tuTien_Checked = false;
        this.params.denTien_Checked = false;
      }
    }
  }
}
</script>
