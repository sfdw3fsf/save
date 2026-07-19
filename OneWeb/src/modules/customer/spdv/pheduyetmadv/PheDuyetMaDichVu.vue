<template src='./PheDuyetMaDichVu.html'>

</template>
<style>
/* Border giữa các ô */
.e-treegrid .e-rowcell {
  border: 0.01px solid #ccc;
}
.e-grid .e-altrow {
  background-color: #FFFFE0 !important;
}
.e-headercelldiv {
    text-align: center !important; 
}

</style>
<script>
import { TreeGridComponent, ColumnDirective, ColumnsDirective, Page, Sort, Filter } from "@syncfusion/ej2-vue-treegrid";
import breadcrumb from '@/components/breadcrumb'
import '@/assets/vendor/jquery/split'

export default {
  name: 'PheDuyetMaDichVu',
  components: {
    breadcrumb,
    'ejs-treegrid': TreeGridComponent,
    'e-column': ColumnDirective,
    'e-columns': ColumnsDirective      
  },
  provide: {
    treegrid: [Page, Sort, Filter]
  }, 
  data() {
    return {
      header: {
        title: 'Phê duyệt mã dịch vụ',
        list: [
          { name: 'Sản phẩm dịch vụ', link: { name: 'Ui.cards' } },
          { name: 'Phê duyệt mã dịch vụ', link: { name: 'Ui.buttons' } }
        ]
      },
      initialData: {
        NHOM_SPDV: [],
        DONVI: [],
        PHUONGTHUC_PT: [],
        DM_VONGDOI_SPDV: [],
        DM_DICHVU_CLVT: [],
        NHOM_GIAIPHAP: [],
        TRANGTHAI: []
      },
      tag: 1, //Đề nghị cấp mã  dịch vụ
      frmCapNhatCom: null,
      dv_clvt_id: null,
      nhomspdv_id: 0,
      phuongthucpt_id: 0,
      donvi_id: 0,
      trangthai_id: 0,
      ma_dt: null,
      ten_chitieu: null,
      dsDichVu: {
        data: [],
        columns: [
          {
            fieldName: 'nhomgiaiphap',
            headerText: 'Nhóm giải pháp',
            width: 120,
            isGroupedColumn:true
          },
          {
            fieldName: 'ten_chitieu',
            headerText: 'SPDV',
            width: 120
          },
          {
            fieldName: 'ten_tienganh',
            headerText: 'Tên tiếng Anh',
            width: 120
          },
          {
            fieldName: 'ten_thuongmai',
            headerText: 'Tên thương mại',
            width: 120
          },          
          {
            fieldName: 'dv_chuquan',
            headerText: 'Đơn vị chủ quản'
          },
          {
            fieldName: 'ma_dt',
            headerText: 'Mã định danh'
          },
          {
            fieldName: 'ten_thuongmai',
            headerText: 'Tên thương mại'
          },
          {
            fieldName: 'ten_viettat',
            headerText: 'Tên viết tắt'
          },
          {
            fieldName: 'trangthai_spdv',
            headerText: 'Trạng thái'
          }
        ]
      },
      animationSettings: { effect: 'None' },
      selectedItem: null,
      dsDeNghiCapMaSP:[],
      sortSettings: { columns: [ ]  },
      pageSettings: { pageSize: 10 },
      dsChuaGan: [],
      dsDaGan: [],
      lydo_koduyet:'',
      history: {
        data: [],
        columns: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            width: 70
          },
          {
            fieldName: 'thaotac',
            headerText: 'Thao tác'
          }
        ]
      }
    }
  },
  async mounted() {
    await this.initData();
    await this.timkiem();
  },
  methods: {
    async initData() {
      let rs = await this.axios.post('/web-qlspdv/ql_dichvu/sp_lay_danhmuc_chung', {})
      if (rs.data.data) {
        let initialData_temp = {
          NHOM_SPDV: [],
          DONVI: [],
          PHUONGTHUC_PT: [],
          DM_VONGDOI_SPDV: [],
          DM_DICHVU_CLVT: [],
          NHOM_GIAIPHAP: [],
          TRANGTHAI: []
        }
        initialData_temp = rs.data.data
        
        let items = [];
        items.push({ nhomspdv_id: 0, ten_nhomspdv: "-Chọn tất cả-" });
        initialData_temp.NHOM_SPDV.forEach(function (item) {
            items.push({
                nhomspdv_id: item.nhomspdv_id,
                ten_nhomspdv: item.ten_nhomspdv,
            });
        });
        initialData_temp.NHOM_SPDV = items;
        
        //PT phát triển
        items = [];
        items.push({ phuongthuc_id: 0, ten_phuongthuc: "-Chọn tất cả-" });
        initialData_temp.PHUONGTHUC_PT.forEach(function (item) {
            items.push({
                phuongthuc_id: item.phuongthuc_id,
                ten_phuongthuc: item.ten_phuongthuc,
            });
        });
        initialData_temp.PHUONGTHUC_PT = items;
        //đơn vị chủ quản
        items = [];
        items.push({ donvi_id: 0, ten_dv: "-Chọn tất cả-" });
        initialData_temp.DONVI.forEach(function (item) {
            items.push({
                donvi_id: item.donvi_id,
                ten_dv: item.ten_dv,
            });
        });
        initialData_temp.DONVI = items;
        // this.searchInfo.donvi_id = this.dsDonVi[0].donvi_id;
        //trạng thái
        items = [];
        items.push({ trangthai_id: 0, trangthai_spdv: "-Chọn tất cả-" });
        initialData_temp.TRANGTHAI.forEach(function (item) {
            items.push({
                trangthai_id: item.trangthai_id,
                trangthai_spdv: item.trangthai_spdv,
            });
        });
        initialData_temp.TRANGTHAI = items;

        this.initialData = initialData_temp
      }
      await this.timkiem();
    },
    selectedRowChanged(item) {      
      this.selectedItem = item
    },
    async timkiem() {
      try {
        this.dsDichVu.data = []
        this.loading(true)
        let rs = await this.axios.post('/web-qlspdv/ql_dichvu/sp_ds_dichvu', {
          tag: 1,
          nhomspdv_id: this.nhomspdv_id,
          phuongthuc_id: this.phuongthucpt_id,
          donvi_id: this.donvi_id,
          trangthai_id: this.trangthai_id,
          ma_dt: this.ma_dt,
          ten_chitieu: this.ten_chitieu
        })

        if (rs.data.data) {
          this.dsDichVu.data = rs.data.data          
          // this.dsDeNghiCapMaSP = this.buildTree(rs.data.data)          
        }
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async xem() {
      try {
        
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async nhapmoi() {
      this.selectedItem = null
      this.$refs.popupEdit.openDialog(this.selectedItem)
    },
    async sua() {
      if (this.selectedItem == null) {
        this.$toast.error('Vui lòng chọn dịch vụ để sửa')
        return
      }
      this.$refs.popupEdit.openDialog(this.selectedItem)
    },
    async xoa() {
      if (this.selectedItem == null) {
        this.$toast.error('Vui lòng chọn dịch vụ')
        return
      }

      if (!confirm('Bạn có chắc chắn muốn xóa dịch vụ không?')) return

      let rs = await this.axios.post('/web-qlspdv/ql_dichvu/fn_xoa_dichvu', { madt_id: this.selectedItem.madt_id })
      if (rs.data.error === '200' && rs.data.data === 'ok') {
        this.$toast.success('Xóa dịch vụ thành công')
        await this.timkiem()
      } else {
        this.$toast.error(rs.data.message)
      }
    },
    async lichsu() {
      if (this.selectedItem == null) {
        this.$toast.error('Vui lòng chọn dịch vụ')
        return
      }
      let rs = await this.axios.post('/web-qlspdv/ql_dichvu/sp_lay_ls_dichvu', { madt_id: this.selectedItem.madt_id })
      if (rs.data.data) {
        this.history.data = rs.data.data.map((item, index) => (
          {
            stt: index + 1,
            ...item
          }
        ))

        this.$refs.popupHistory.show()
      }
    },
    async PheDuyet() {
      if (this.selectedItem == null) {
        this.$toast.error('Vui lòng chọn dịch vụ')
        return
      }

      if (!confirm('Bạn có chắc chắn muốn phê duyệt dịch vụ không?')) return

      let rs = await this.axios.post('/web-qlspdv/ql_dichvu/fn_duyet_dichvu_td', { madt_id: this.selectedItem.madt_id })
      if (rs.data.error === '200' && rs.data.data === 'ok') {
        this.$toast.success('Phê duyệt dịch vụ thành công')        
        await this.timkiem()
      } else {
        this.$toast.error(rs.data.message)
      }
    },
    async handleKoDuyet_Hidden() {
        this.lydo_koduyet = '';
    },   
    async handleKoDuyet_Ok() {      
      let rs = await this.axios.post('/web-qlspdv/ql_dichvu/fn_tuchoi_dichvu_td', { madt_id: this.selectedItem.madt_id,lydo: this.lydo_koduyet })
      if (rs.data.error === '200' && rs.data.data === 'ok') {
        this.$toast.success('Từ chối phê duyệt dịch vụ thành công')        
        await this.timkiem()
      } else {
        this.$toast.error(rs.data.message)
      }
    }, 
    async KhongDuyet() {
      if (this.selectedItem == null) {
        this.$toast.error('Vui lòng chọn dịch vụ')
        return
      }
      this.$bvModal.show('popupKoDuyet');
      
    },
    async GuiTapDoan() {
      if (this.selectedItem == null) {
        this.$toast.error('Vui lòng chọn dịch vụ')
        return
      }

      if (!confirm('Bạn có chắc chắn muốn gửi tập đoàn phê duyệt dịch vụ không?')) return

      let rs = await this.axios.post('/web-qlspdv/ql_dichvu/fn_gui_duyet_dichvu_td', { madt_id: this.selectedItem.madt_id })
      if (rs.data.error === '200' && rs.data.data === 'ok') {
        this.$toast.success('Gửi tập đoàn phê duyệt dịch vụ thành công')        
        await this.timkiem()
      } else {
        this.$toast.error(rs.data.message)
      }
    },
    buildTree(data, parentId = null) {
      const tree = [];
      for (const item of data) {
          if (item.madt_cha_id == parentId) {
              const children = this.buildTree(data, item.madt_id);
              if (children.length > 0) {
                  item.children = children;
              }
              tree.push(item);
          }
      }
      return tree;
    },
  }
}
</script>

<style scoped>

</style>
