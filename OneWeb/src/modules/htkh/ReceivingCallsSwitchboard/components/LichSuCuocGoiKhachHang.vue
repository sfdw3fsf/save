<template>
  <div>
    <!-- <ul class="list">
      <li>
        <a href="javascript: void(0)" @click="gridCommand_History('detail')">
          <span class="icon one-vt"></span>Chi tiết cuộc gọi
        </a>
      </li>
    </ul> -->
    <KDataGrid
      id="gview_History"
      ref="gview_History"
      v-bind:columns="grid_History.config"
      v-bind:dataSource="grid_History.DataSource"
      :visibleHeader="true"
      :enable-paging-server="false"
      :allowPaging="true"
      :showFilter="true"
      :showColumnSelectFlag="false"
      :showColumnCheckbox="false"
      :enabledSelectFirstRow="false"
      :selectionSettings="{ enableToggle: false }"
      @rowSelected="gview_History_FocusedRowChanged"
      :commandColumn="{headerText: '', width: 50, cellCssClass: 'center', firstColumn: true, show: true}"
      :commands="grid_History.commands"
      @commandClicked="gridCommand_History"
    >
    </KDataGrid>

    <ChiTietXuLy ref="ChiTietXuLyModal" />
  </div>
</template>
<script>
import moment from 'moment'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import api from "../ReceivingCallsSwitchboardApi.js"

const ChiTietXuLy  = () => import("./ChiTietXuLy/ChiTietXuLy.vue")

export default {
  name: "LichSuCuocGoiKhachHang",
  components: { KDataGrid, ChiTietXuLy },
  props: ['dulieu','so_dt'],
  data: () => ({
    cuocgoi_id: 0,
    tt_nd: {},
    grid_History: {
      commands: [
        { name: 'detail', title: 'Chi tiết', cssClass: 'pad3 mar5 lh14 icon -ap one-eye btn-plus', iconCss: 'fa fa-times', width: 100 },
      ],
      config: [
        {
          fieldName: 'id',
          headerText: 'Cuộc gọi ID',
          allowFiltering: true
        },
        {
          fieldName: 'chuGoi',
          headerText: 'Số máy',
          isPrimaryKey: true,
          allowFiltering: true
        },
        {
          fieldName: 'tenNguoiNhan',
          headerText: 'Người nhận',
          allowFiltering: true
        },
        {
          fieldName: 'gioBD',
          headerText: 'Thời gian bắt đầu',
          allowFiltering: true
        },
        {
          fieldName: 'thoiLuong',
          headerText: 'Độ dài',
          allowFiltering: true
        },
        {
          fieldName: 'kieu_CG',
          headerText: 'Kiểu CG',
          allowFiltering: true
        },
        {
          fieldName: 'chiTiet.noiDung',
          headerText: 'Nội dung hỗ trợ',
          allowFiltering: true
        },
        {
          fieldName: 'donVi',
          headerText: 'Tỉnh',
          allowFiltering: true
        }
      ],
      DataSource: null
    },
  }),
  mounted: async function () {
    this.tt_nd = await this.$root.token.getThongTinNguoiDung()
    console.log(this.tt_nd)
    // await this.frm_Load()    
  },
  watch: {},
  methods: {
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return []
      } else {
        this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return []
      }
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return ''
      } else {
        this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return ''
      }
    },
    frm_Load: async function () {
      try {
        this.loading(true)
        var input = { max_row: 100, nhanvien_id: Number(this.tt_nd.nhanvien_id), sodienthoai: this.so_dt}
        await this.LayLichSuCuocGoi(input);
        
      } catch (e) {} finally { this.loading(false) }
    },
    LayLichSuCuocGoi: async function (input) {
      try {
        this.grid_History.DataSource = this.GetDataList(await api.sp_lay_LichSu_CuocGoi(this.axios, input));
      } catch (e) {
        if (e && e.data) {
          this.$toast.error('Lỗi sp_lay_LichSu_CuocGoi ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
        } else {
          this.$toast.error('Lỗi sp_lay_LichSu_CuocGoi ' + JSON.stringify(e))
        }
      } finally {

      }
    },
    gview_History_FocusedRowChanged: function (args) {
      try {
        this.loading(true)
        const cg = this.$refs.gview_History.$refs.grid.getSelectedRecords()
        if (cg.length > 0) {
          this.cuocgoi_id = cg[0].ID
        }
        this.$emit("rowSelected", cg);
        document.getElementById('gview_History').scrollIntoView(true);
      } catch (e) {} finally { this.loading(false) }
    },
    gridCommand_History: function (type, dataItem) {
      if (type == 'detail') {
        var startDate = ''
        var endDate = ''
        if (dataItem && dataItem['gioBD']) {
          var t = new Date(moment(dataItem['gioBD'], 'DD/MM/YYYY hh:mm:ss'));
          startDate = moment(t).format('DD/MM/YYYY hh:mm:ss')
          endDate = moment(t.setSeconds(t.getSeconds() + Number(dataItem['thoiLuong']))).format('DD/MM/YYYY hh:mm:ss');
        }
        this.$refs.ChiTietXuLyModal.startDate = startDate
        this.$refs.ChiTietXuLyModal.endDate = endDate
        this.$refs.ChiTietXuLyModal.data_id = dataItem && dataItem['id'] ? dataItem['id'] : 0
        this.$refs.ChiTietXuLyModal.showModal();     
      }
    }
  }
}
</script>
