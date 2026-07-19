<template>
    <div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="javascript: void(0)" @click="onGuiSMSClicked()">
                        <span class="icon one-email"></span> Gửi SMS
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)" @click="onHuyBoClicked()">
                        <span
                            class="icon nc-icon-glyph ui-1_circle-remove"
                        ></span>
                        Hủy bỏ
                    </a>
                </li>
            </ul>
        </div>
        <div class="popup-body">
            <div class="box-form">
                <div class="info-row">
                    <div class="key w100">Số điện thoại<BssRequiredMarker/></div>
                    <div class="value">
                        <input
                            type="text"
                            class="form-control"
                            v-model="pSDT"
                        />
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w100">Nội dung<BssRequiredMarker/></div>
                    <div class="value">
                        <textarea
                            class="form-control"
                            style="height: 120px"
                            v-model="pNoiDung"
                        ></textarea>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">Danh sách tin nhắn đã gửi</div>
                <DataGrid
                    :columns="fieldsSmsGrid"
                    :dataSource="dsTinNhan"
                    :showColumnCheckbox="false"
                    :showFilter="true"
                    dataKeyField="STT"
                    ref="gridNoiDungKS"
                    :allowPaging="true"
                    :enablePagingServer="false"
                ></DataGrid>
            </div>
        </div>
    </div>
</template>
<script>
import moment from "moment";
import GiamThinhPhieuKhaoSatApi from "../../api/GiamThinhPhieuKhaoSatApi";
import BssRequiredMarker from "@/components/BssRequiredMarker"
export default {
  name: "GuiSMSChoKhachHang",
  components: {BssRequiredMarker},
  props: [ 'vbtlId', 'vHdkhId', 'vBaoHongId', 'vSoDt' ],
  data () {
      return {
        fieldsSmsGrid: [
          {
            fieldName: "TEN_NV",
            headerText: "Người gửi",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "SO_DT",
            headerText: "Số ĐT",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NGAY_TH",
            headerText: "Ngày gửi",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "SMS",
            headerText: "Nội dung",
            allowFiltering: true,
            allowSorting: false,
          },
        ],
        fields: [
          {
            key: "selectedColumn",
            label: "",
            tdClass: "w20 center",
          },
          {
            key: "TEN_NV",
            label: "Người gửi",
            isSearch: true,
          },
          {
            key: "SO_DT",
            label: "Số ĐT",
            isSearch: true,
          },
          {
            key: "NGAY_TH",
            label: "Ngày gửi",
            formatter: value => moment(value).format('DD/MM/YYYY hh:mm:ss'),
            isSearch: true,
          },
          {
            key: "SMS",
            label: "Nội dung",
            isSearch: true,
          },

        ],
        dsTinNhan: [],
        pSDT:'',
        pNoiDung: ''
      }
  },
  async mounted() {
    this.pSDT = this.vSoDt
    await this.fetchData()
  },
  methods: {
    onHuyBoClicked() {
      this.pSDT = ''
      this.pNoiDung = ''
    },
    async onGuiSMSClicked() {
      if (!this.pSDT) {
        this.$root.toastError("Chưa có SĐT")
        return
      }
      if (!this.pNoiDung) {
        this.$root.toastError("Chưa nhập nội dung")
        return
      }

      try {
        this.$root.showLoading(true)
        let response = await this.$root.context.post('/web-cskh/api/giam-thinh-phieu-khao-sat/gui-sms', {
                "tbtlId": this.vbtlId,
                "hdkhId": this.vHdkhId,
                "baoHongId": this.vBaoHongId,
                "sdt": this.pSDT,
                "noiDung": this.pNoiDung
              })
        if (response.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Gửi tin nhắn thành công")
        }
      }
      catch (e) {
        console.log(e)
        this.$root.toastError("Có lỗi: " + e)
      }
      finally {
        this.$root.showLoading(false)
      }
    },
    async fetchData() {
      this.loading(true);
      let response = null;
      try {
        let postData = {
          pageNo: 1,
          pageSize: 100000,
          tbtlId: this.vbtlId,
          hdkhId: this.vHdkhId,
          baoHongId: this.vBaoHongId
        };
        // let postData = {
        //   pageNo: pageNo,
        //   pageSize: pageSize,
        //   tbtlId: 250218,
        //   hdkhId: 0,
        //   baoHongId: 1973496
        // };
        response = await this.$root.context.get('/web-cskh/api/giam-thinh-phieu-khao-sat/lich-su-gui-sms',
          postData
        );
        if (response.error_code === "BSS-00000000") {
          this.dsTinNhan = response.data.data || []
          this.dsTinNhan.forEach((e,i) => e.STT = i + 1)
        }
      } catch (e) {
          console.log(e)
      } finally {
        this.loading(false);
      }

      return response;
    },
  },
};
</script>
