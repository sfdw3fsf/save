<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span>Thông tin cấp vật tư cho thuê bao</div>
      <div
        class="close -ap icon-close"
        data-dismiss="modal"
        @click="$bvModal.hide('popup-vattu')"
      ></div>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="">
            <span class="icon one-file-plus"></span> Xuất Excel
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w120">KQ xử lý</div>
              <div class="value">
                <input type="text" class="form-control" readonly v-model="ketQuaXuLy">
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w120">Mục đích cấp</div>
              <div class="value">
                <input type="text" class="form-control" readonly v-model="mucDichCap">
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w120">Lý do TH</div>
              <div class="value">
                <input type="text" class="form-control" readonly v-model="lyDoTH">
              </div>
            </div>
          </div>
        </div>
      </div>
      <DataGrid v-bind:columns="gridHeaders" :show-filter="false"
                v-bind:dataSource="gridData" :showColumnCheckbox="false"></DataGrid>
    </div>
  </div>
</template>

<script>
import api from "../api";
import _ from "lodash";

// Tham chiếu UR2.1.008 - Thông tin cấp vật tư cho thuê bao
export default {
  name: "VatTuPopup",
  components: {

  },

  props: {
    hdtb_id: { type: Number }
  },

  mounted () {
    this.loadDsVatTu();
  },

  data() {
    return {
      ketQuaXuLy: "",
      mucDichCap: "",
      lyDoTH: "",

      gridData: [],
      gridHeaders: [
        { fieldName: 'ma_tbi', headerText: 'Mã tbị' },
        { fieldName: 'loai_tbi', headerText: 'Tên vật tư' },
        { fieldName: 'seri', headerText: 'Seri' },
        { fieldName: 'soluong', headerText: 'Số lượng' },
        { fieldName: 'dongia', headerText: 'Đơn giá' },
        { fieldName: 'lohang', headerText: 'Lô hàng' },
        { fieldName: '', headerText: 'Mô tả' },
        { fieldName: 'trangbi', headerText: 'Trang bị' },
        { fieldName: 'kieu_tbi', headerText: 'Kiểu thiết bị' },
        { fieldName: 'tinhtrang_tbi', headerText: 'Tình trạng' },
        { fieldName: 'mucdich_cap', headerText: 'Lý do cấp' },
        { fieldName: 'ngay_th', headerText: 'Ngày cấp' },
        { fieldName: 'nguoi_cn', headerText: 'Người CN' },
        { fieldName: 'ten_dv', headerText: 'Đơn vị' },
        { fieldName: 'ten_kho', headerText: 'Kho cấp' },
        { fieldName: 'so_phieu', headerText: 'Số phiếu' },
        { fieldName: 'trangthai', headerText: 'Trạng thái' }
      ],
    };
  },

  methods: {
    loadDsVatTu() {
      let hdtb_id = this.hdtb_id;
      if (!hdtb_id) {
        hdtb_id = 2314957;
        this.$toast(`hdtb_id test ${hdtb_id}`);
      }

      api.sp_lay_kqxl_huonggiao_thuhoi(this.axios, { hdtb_id }).then(ret => {
        this.ketQuaXuLy = _.get(ret.data.data, [0, "ketqua"]);
        this.lyDoTH = _.get(ret.data.data, [0, "lydo"]);
      }).catch(e => this.$toast("Có lỗi xảy ra khi lấy kết quả xử lý"));

      api.sp_lay_ds_vattu_hdtb(this.axios, { hdtb_id }).then(ret => {
        this.gridData = ret.data.data;
        this.mucDichCap = _.get(ret.data.data, [0, "lydoto"]);
      }).catch(e => this.$toast("Có lỗi xảy ra khi lấy danh sách vật tư"));
    }
  },
}
</script>
