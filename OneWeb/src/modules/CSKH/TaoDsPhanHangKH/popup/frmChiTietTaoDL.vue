<template>
  <ejs-dialog
        :enableResize="true"
        :allowDragging="true"
        :header="'Chi tiết các lần tạo dữ liệu'"
        ref="frmChiTietTaoDL"
        showCloseIcon="true"
        width="90%"
        :visible="false"
        :open="dialogOpen"
        :close="closeDialog"
        :overlayClick="closeDialog"
        :isModal="true"
    >
  <!-- <b-modal
    id="popup-fromChiTietTaoDL"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    @show="onShow"
  > -->
    <div class="modal-content popup-box">
      <!-- <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Chi tiết các lần tạo dữ liệu
        </div>
        <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('popup-fromChiTietTaoDL')"></div>
      </div> -->
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript: void(0)">
              <downloadexcel :fields="exportExcelFields" :data="this.dsKhachHang" worksheet="export"
                  name="export.xls" :before-generate="onStartDownload">
                  <span class="icon one-xlsx-import"></span> Xuất Excel
              </downloadexcel>
            </a>
            
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="table-content">
              <DataGrid v-bind:columns="[
                    {fieldName: 'MA_KH', headerText: 'Mã KH', allowSorting: true,allowFiltering: true},
                    {fieldName: 'TEN_KH', headerText: 'Tên KH', allowSorting: true,allowFiltering: true},
                    {fieldName: 'DIACHI_KH', headerText: 'Địa chỉ khách hàng', allowSorting: true, allowFiltering: true},
                    {fieldName: 'BINHQUAN_DT', headerText: 'Bình quân DT', allowSorting: true, allowFiltering: true, cssClass: 'text-currency', type: 'number', format: 'N0'},
                    {fieldName: 'MA_PLKH', headerText: 'Phân loại KH', allowSorting: true, allowFiltering: true},
                    {fieldName: 'THOIGIAN_SD', headerText: 'Thời gian SD (năm)', allowSorting: true, allowFiltering: true},
                    {fieldName: 'TONGDIEM', headerText: 'Điểm', allowSorting: true, allowFiltering: true},
                    {fieldName: 'PHANHANGKH_ID', headerText: 'Phân hạng', allowSorting: true, allowFiltering: true},
                ]"
                :enablePagingServer="false"
                :allowPaging="true"
                v-bind:dataSource="dsKhachHang" :showFilter="true" >
            </DataGrid> 
          </div>
        </div>
      </div>
    </div>
  <!-- </b-modal> -->
  </ejs-dialog>
</template>
<script>
import moment from 'moment'
import downloadexcel from "vue-json-excel";
import { currency } from '@/filters/currency'
export default {
  name: "frmChiTietTaoDL",
  components: {downloadexcel},
  props: {
    // ngayCN: {
    //   type: String,
    //   default: moment(new Date).format('DD/MM/YYYY'),
    // },
    // kyPH: {
    //   type: Number,
    //   default: 0,
    // },
    // kieu: {
    //   type: Number,
    //   default: 0,
    // },
  },
  data() {
    return {
        dsKhachHang: [],
        exportExcelFields: {
            "Mã KH": "MA_KH",
            "Tên KH": "TEN_KH",
            "Địa chỉ KH": "DIACHI_KH",
            "Bình quân DT": "BINHQUAN_DT",
            "Phân loại KH": "MA_PLKH",
            "Thời gian SD (năm)": "THOIGIAN_SD",
            "Điểm": "TONGDIEM",
            "Phân hạng": "PHANHANGKH_ID",
        },
        ngayCN: '',
        kyPH: 0,
        kieu: 0,
    };
  },
  methods: {
    async dialogOpen() {
        await this.onShow();
    },
    openDialog() {
        this.$refs.frmChiTietTaoDL.show();
    },
    closeDialog() {
        this.$refs.frmChiTietTaoDL.hide();
    },
    async onShow() {
        await this.getLanTaoChamSocKHL()
    },
    onStartDownload() {
        if (this.dsKhachHang.length == 0) {
            this.$root.toastError('Không có dữ liệu để xuất Excel !')
            return
        }
    },
    async getLanTaoChamSocKHL() {
        try {
            this.$root.showLoading(true)
            if (this.kieu == 1) {
            // ds_tdl = cs_khl_facade.Lay_CT_LanTao_Chamsoc_KHL(Ngay_CN.ToString("yyyyMMdd"), KHLON_ID);
            }
            else if (this.kieu == 2) {
                // ds_tdl = cs_khl_facade.Lay_CT_LanTao_PHANLOAIKH(Ngay_CN.ToString("yyyyMMdd"), PHANLOAIKH_ID);
            }
            else {
                this.dsKhachHang = []
                console.log(this.kyPH)
                let response = await this.$root.context.post('/web-cskh/api/danh-sach-phan-hang/chi-tiet-lan-tao?pageNo=1&pageSize=10000',
                {
                    ngayCn: this.ngayCN,
                    kyPh: this.kyPH,
                    pageNo: 1,
                    pageSize: 100
                })
                if (response.error_code === "BSS-00000000") {
                    this.dsKhachHang = response.data.data || []
                    // this.dsKhachHang.forEach(e => {
                    //   e.BINHQUAN_DT = currency(Math.round(e.BINHQUAN_DT))
                    // })
                }
            }
        }
        catch (e) {

        }
        finally {
            this.$root.showLoading(false)
        }
        
    },
  },
};
</script>
