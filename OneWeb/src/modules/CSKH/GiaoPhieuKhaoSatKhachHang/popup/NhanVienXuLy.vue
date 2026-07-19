<template>
  <b-modal
    id="popup-nhanVienXuLy"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    show="onShow"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Nội dung đã khảo sát tại đài hỗ
          trợ
        </div>
        <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('popup-nhanVienXuLy')"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#"> <span class="icon fa fa-play"></span> Nghe ghi âm </a>
          </li>
          <li>
            <a href="#">
              <span class="icon one-circle-question"></span> Trợ giúp
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Nội dung khảo sát</div>
          <div class="info-row">
            <div class="key w60">Ghi chú</div>
            <div class="value">
              <input type="text" class="form-control" />
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="marb10">Chất lượng phục vụ của NVKT?</div>
          <div class="list-check-ver">
            <div class="item marb10">
              <div class="check-action">
                <input type="checkbox" class="check" />
                <span class="name"
                  >Nội dung phản ánh không liên quan ddeessn vụ việc lắp đặt, xử
                  lý đang khảo sát</span
                >
              </div>
            </div>
            <div class="item marb10">
              <div class="check-action">
                <input type="checkbox" class="check" />
                <span class="name"
                  >Vấn đề KH phản ánh không thuộc trách nhiệm phía VNPT</span
                >
              </div>
            </div>
            <div class="item marb10">
              <div class="check-action">
                <input type="checkbox" class="check" />
                <span class="name"
                  >MEDIA 1 - Phản ánh về tiện ích, nội dung dịch vụ MyTV (Its
                  kênh, không có các kênh hấp dẫn)</span
                >
              </div>
            </div>
            <div class="item marb10">
              <div class="check-action">
                <input type="checkbox" class="check" />
                <span class="name"
                  >KT1 - Lắp đặt/sửa chữa dịch vụ không đúng hẹn</span
                >
              </div>
            </div>
            <div class="info-row marb10">
              <div class="value padl0">
                <input type="text" class="form-control" />
              </div>
            </div>
            <div class="item marb10">
              <div class="check-action">
                <input type="checkbox" class="check" />
                <span class="name"
                  >KT2 - Thời gian lắp đặt/sửa chữa dịch vụ lâu</span
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import xlsx from "xlsx";
import { mapActions, mapState, mapGetters } from "vuex";
import DanhSachThueBaoKhongKhaoSatApi from "../../api/DanhSachThueBaoKhongKhaoSatApi";
import GanDVAPI from "../../api/GanNVChamSocKHLApi";
import CustomDataGrid from "../../utils/CustomDataGrid.vue";
import downloadexcel from "vue-json-excel";
export default {
  name: "NhanVienXuLy",
  components: { downloadexcel },
  data() {
    return {};
  },
  created() {},
  computed: {
    dataExcel() {
      return Object.values(this.dsLoi);
    }
  },
  mounted() {},
  methods: {
    onShow() {}
  }
};
</script>
