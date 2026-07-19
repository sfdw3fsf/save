<template>
  <b-modal
    id="popup-nhanVienXuLy"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    @show="onShow"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Danh sách nhân viên thực hiện
        </div>
        <div class="close -ap icon-close" data-dismiss="modal"  @click="$bvModal.hide('popup-nhanVienXuLy')"></div>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="table-content">
            <DataGrid
              :columns='[
                            { fieldName: "NGAY_GIAO", headerText: "Ngày giao", allowFiltering: true, allowSorting: false, },
                            { fieldName: "NV_GIAO", headerText: "Người giao", allowFiltering: true, allowSorting: false, },
                            { fieldName: "MA_NV", headerText: "Mã NV TH", allowFiltering: true, allowSorting: false, },
                            { fieldName: "NV_THUCHIEN", headerText: "Người thực hiện", allowFiltering: true, allowSorting: false, },
                            { fieldName: "MA_ND_TH", headerText: "User người TH", allowFiltering: true, allowSorting: false, },
                            { fieldName: "SO_DT", headerText: "Điện thoại", allowFiltering: true, allowSorting: false, },
                            { fieldName: "NHIEMVU", headerText: "Nhiệm vụ", allowFiltering: true, allowSorting: false, },
                            { fieldName: "KQXL_ID", headerText: "Kết quả XL", allowFiltering: true, allowSorting: false, },
                            { fieldName: "TRANGTHAI", headerText: "Trạng thái", allowFiltering: true, allowSorting: false, },
                            { fieldName: "GHICHU", headerText: "Ghi chú", allowFiltering: true, allowSorting: false, },
                        ]'
              :dataSource="dsNhanVienThucHien"
              :showColumnCheckbox="false"
              :showFilter="true"
              :allowPaging="true"
              :enablePagingServer="false"
            ></DataGrid>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import XacNhanPhanHoiNVKTApi from "../../api/XacNhanPhanHoiNVKTApi"
export default {
  name: "NhanVienXuLy",
  components: {},
  props: {
    baoHongId: {
      type: Number,
      default :0,
    },
    hdtbId: {
      type: Number,
      default: 0,
    }
  },
  data() {
    return {
      dsNhanVienThucHien: []
    };
  },
  created() {},
  computed: {
  },
  mounted() {},
  methods: {
    async onShow() {
      try {
        this.$root.showLoading(true)
        this.dsNhanVienThucHien = []
        let data = {
          kieuId: this.baoHongId != 0 ? 1 : 0,
          id: this.baoHongId != 0 ? this.baoHongId : this.hdtbId,
          pageSize: 10000,
          pageNo: 1
        }

        let response = await XacNhanPhanHoiNVKTApi.getDsNhanVienThucHien(this.axios, data)
        if (response.data.error_code == "BSS-00000000") {
          this.dsNhanVienThucHien = response.data.data.data || []
        }
      }
      catch (e) {
        this.dsNhanVienThucHien = []
      }
      finally {
        this.$root.showLoading(false)
      }
    }
  }
};
</script>
