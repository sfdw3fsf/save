<template>
  <!-- <ejs-dialog :isModal="true" :enableResize='true' :visible="false" ref="frmTraPhieuKN_SLL" :header='"Trả phiếu khiếu nại"'
      showCloseIcon=true target=".main-wrapper" width='90%' height="auto"> -->
  <b-modal
    ref="frmTraPhieuKN_SLL"
    title="Trả phiếu khiếu nại"
    size="xl"
    hide-footer
    body-class="p-0"
    @show="show"
  >
      <div class="modal-content">
          <div class="list-actions-top">
            <ul class="list">
              <li>
                <a title="Xác nhận" ref="tsbtnXacNhan" @click="tsbtnXacNhan_click">
                  <span class="icon one-save"></span>Xác nhận
                </a>
              </li>
              <li>
                <a title="Copy Lý do trả" ref="tsbtnCopyLyDoTra" @click="tsbtnCopyLyDoTra_click">
                  <span class="icon one-copy"></span>Copy Lý do trả
                </a>
              </li>
              <li style="width: calc(70%) !important;">
                <input type="text" class="form-control" :value="txtLyDoTra" @change="e => txtLyDoTra = e.target.value">
              </li>
            </ul>
          </div>
          <div class="popup-body">
            <div class="box-form">
              <div class="legend-title">
                <div class="pull-left">Danh sách khiếu nại</div>
                <div class="clearfix"></div>
              </div>
              <div class="table-content">
                <KDataGrid v-bind:columns="dtKhieuNai.headers"
                  v-bind:dataSource="dtKhieuNai.data" :showColumnCheckbox="true"
                  :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                  @selectedItemsChanged="selectedItemsChanged_dtKhieuNai"
                  :selectionSettings="selectionOptions"
                  ref="dtKhieuNai"
                  panelDataHeight="300px"
                  :enabledSelectFirstRow="false"
                  :defaultColumnCheckboxChecked="true"
                >
                </KDataGrid>
              </div>
            </div>
          </div>
      </div>
  <!-- </ejs-dialog>     -->
  </b-modal>
</template>
<script>
import Select2Ext from "@/modules/contract/setup/DeclareLandline/components/Select2Ext";
import VueDateExt from "@/modules/contract/setup/DeclareLandline/components/VueDateExt";
import KDataGrid from '@/components/kylq_components/KDataGrid'
import moment from "moment";

export default {
  components: {
    Select2Ext,
    VueDateExt,
    KDataGrid
  },
  data() {
    return {
      txtLyDoTra: '',
      selectionOptions: {
        type: 'Multiple',
        checkBoxOnly: true
      },
      dtKhieuNai: {
        headers: [
          { fieldName: 'lydo_traphieu', headerText: 'Lý do trả' },
          { fieldName: 'khieunai_id', headerText: 'Khiếu nại ID' },
          { fieldName: 'ngay_tn', headerText: 'Ngày KN' },
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc' },
          { fieldName: 'ten_dvvt', headerText: 'Dịch vụ' },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình' },
          { fieldName: 'ten_tb', headerText: 'Tên TB' },
          { fieldName: 'diachi_tb', headerText: 'Địa chỉ TB' },
          { fieldName: 'trangthai_tb', headerText: 'Trạng thái TB' },
          { fieldName: 'tongno', headerText: 'Tổng nợ', type: 'Number', format: 'N0' },
          { fieldName: 'no_ps', headerText: 'Nợ phát sinh', type: 'Number', format: 'N0' },
          { fieldName: 'no_dk', headerText: 'Nợ đầu kỳ', type: 'Number', format: 'N0' },
          { fieldName: 'ma_tt', headerText: 'Mã thanh toán' },
          { fieldName: 'ma_tt_neo', headerText: 'Mã TT Neo' },
          { fieldName: 'ten_tt', headerText: 'Tên thanh toán' },
          { fieldName: 'diachi_tt', headerText: 'Địa chỉ thanh toán' },
          { fieldName: 'diachi_bc', headerText: 'Đia chỉ báo cước' },
          { fieldName: 'ten_dl', headerText: 'Đại lý' },
          { fieldName: 'nvtc', headerText: 'Mã NVTC' },
          { fieldName: 'tentuyen', headerText: 'Tuyến đường' },
          { fieldName: 'ngaygiao', headerText: 'Ngày giao' },
          { fieldName: 'nv_giao', headerText: 'Người YC' },
          { fieldName: 'donvi_tn', headerText: 'Đơn vị YC' },
          { fieldName: 'nd_khieunai', headerText: 'Nội dung khiếu nại' },
          { fieldName: 'phieukn_id', headerText: 'Phiếu KN ID' },
          { fieldName: 'nguoi_kn', headerText: 'Người KN' },
          { fieldName: 'dienthoai_lh', headerText: 'Điện thoại LH' },
          { fieldName: 'ky_tt', headerText: 'Kỳ TT' },
          { fieldName: 'tientra', headerText: 'Tiền trả', type: 'Number', format: 'N0' },
          { fieldName: 'hentra', headerText: 'Hẹn trả' },
          { fieldName: 'goicuoc', headerText: 'Gói cước' },
          { fieldName: 'ma_plkh', headerText: 'Mã PLKH' },
          { fieldName: 'dienthoai', headerText: 'SĐT Nhắc nợ' },
          { fieldName: 'ghichu_tra', headerText: 'Ghi chú' },
          { fieldName: 'nguoi_xl', headerText: 'Người XL' }
        ],
        data: [],
        selectedItem: {},
        selectedItems: []
      },
    }
  },
  methods: {
    async show() {
      this.$refs.frmTraPhieuKN_SLL.show()
      await this.frmTraPhieuKN_Load()
    },
    frmTraPhieuKN_Load() {
      this.txtLyDoTra = ''
      for (var item of this.dtKhieuNai.data) {
        item['lydo_traphieu'] = ''
      }
    },
    selectedItemsChanged_dtKhieuNai(selectedItems) {
      this.dtKhieuNai.selectedItems = selectedItems
      // if (selectedItems && selectedItems.length > 0) this.tsbtnCopyLyDoTra_click()
    },
    tsbtnCopyLyDoTra_click: function () {
      try {
        this.loading(true)
        if (!this.dtKhieuNai.selectedItems || this.dtKhieuNai.selectedItems.length == 0) {
          this.$toast.error('Bạn chưa chọn danh sách phiếu cần trả!')
          this.loading(false)
          return;
        }
        console.log('tsbtnCopyLyDoTra_click', this.txtLyDoTra)
        for (var item of this.dtKhieuNai.selectedItems) {
          var index = this.dtKhieuNai.data.findIndex(e => e.phieukn_id == item.phieukn_id)
          if (index != -1) {
            this.dtKhieuNai.data[index]['lydo_traphieu'] = this.txtLyDoTra
            item['lydo_traphieu'] = this.txtLyDoTra
          }
        }      
        this.dtKhieuNai.data = this.dtKhieuNai.data.concat([])
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    tsbtnXacNhan_click: async function () {
      try
      {
        this.loading(true)
        if (this.dtKhieuNai.selectedItems.length <= 0) {
          this.$toast.error('Bạn chưa chọn danh sách phiếu cần trả!')
          this.loading(false)
          return;
        }
        for (var item of this.dtKhieuNai.selectedItems) {
          if (!item.lydo_traphieu || item.lydo_traphieu.trim() == '') {
            this.$toast.error('Bạn chưa nhập nội dung trả!')
            this.loading(false)
            return;
          }
        }
        this.$bvModal
          .msgBoxConfirm(
            "Bạn thật sự muốn trả lại phiếu khiếu nại không ?",
            {
              title: "Thông báo",
              centered: true,
              size: "sm",
              okTitle: 'Đồng ý',
              cancelTitle: 'Không đồng ý',
            }
          )
          .then((value) => {
            if (value) {
              this.CapNhat();
            } else {
              this.loading(false)
              return;
            }
          });
      }
      catch (ex)
      {
        this.$root.toastError("Có lỗi: " +ex)
      }
      finally
      {
        this.loading(false)
      }
    },
    CapNhat: async function() {
      try
      {
        this.loading(true)
        var traphieu = await this.TraPhieu_KN_V2(this.dtKhieuNai.selectedItems)
        if (!traphieu) {
          return false
        }
        this.$root.toastSuccess('Đã trả phiếu thành công!')
        this.$emit('success', 'success')
      }
      catch (exp)
      {
        this.$root.toastError("Có lỗi trong quá trình trả phiếu !")
        this.loading(false)
      }
      finally {
        this.loading(false)
      }
    },
    TraPhieu_KN_V2: async function(ds) {
      try
      {
        var ds_selected = this.dtKhieuNai.selectedItems 
        var ds_data = this.dtKhieuNai.data 
        var nhanvien_tra_id = await this.$root.token.getNhanVienID()
        for (var item of ds) {  
          console.log(item)        
          //update khieu nai
          var dataKhieuNai = {
            phieukn_id: item.phieukn_id,
            ngaytra: moment(new Date()).format('DD/MM/YYYY'),
            nd_tra: item.lydo_traphieu,
            ttph_id: 3, //TRANGTHAI_PH.DATRALAI;
            nhanvien_tra_id: nhanvien_tra_id,
            ghichu_th: item.ghichu_tra
          }
          var res1 = await this.$root.context.post("/web-cabman/traphieukhieunai/capnhat-khieunai", dataKhieuNai);
          if (res1 && res1.error_code != 'BSS-00000000') {
            this.$root.toastError('Có lỗi xảy ra: Cập nhật KN ' + res1.data.message)
            this.loading(false)
            return false
          }
          //Update tthd = thanh toán của hopdong_tb
          let data_update = {
            phieukn_id: item.phieukn_id,
            khieunai_id: item.khieunai_id
          }
          var res2 = await this.$root.context.post("/web-cabman/traphieukhieunai/capnhat-tthd", data_update);
          if (res2 && res2.error_code != 'BSS-00000000') {
            this.$root.toastError('Có lỗi xảy ra: Cập nhật TTHD ' + res2.data.message)
            this.loading(false)
            return false
          }
          // Hủy giao phiếu hiện tại
          let data_huy = {
            phieukn_id: item.phieukn_id,
            nhanvien_tra_id: nhanvien_tra_id,
            noidung: item.lydo_traphieu
          }
          var res3 = await this.$root.context.post("/web-cabman/traphieukhieunai/huy-giaophieu", data_huy);
          if (res3 && res3.error_code != 'BSS-00000000') {
            this.$root.toastError('Có lỗi xảy ra: Hủy giao phiếu ' + res3.data.message)
            this.loading(false)
            return false
          }
          //#region "hàm nhắn tin trả phiếu"
          let data_send = {
            phieukn_id: item.phieukn_id,//166144,//this.phieukn_id,
            nhanvien_tra_id: nhanvien_tra_id,
            noidung: item.lydo_traphieu
          }
          await this.$root.context.post("/web-cabman/traphieukhieunai/xuly-thongtin-sms", data_send);
          // if (rs.data[0].DT != '0'){
          //   await this.SendSMS(rs.data[0].ND, rs.data[0].DT)            
            
            ds_selected = ds_selected.filter(e => e.phieukn_id != item.phieukn_id)
            ds_data = ds_data.filter(e => e.phieukn_id != item.phieukn_id)    

            this.dtKhieuNai.selectedItems = ds_selected
            this.dtKhieuNai.data = ds_data
          // } else {
          //   this.$root.toastError('Có lỗi gửi SMS')
          //   this.loading(false)
          //   return false
          // }             
        }
        return true
        //#endregion
      }
      catch (ex)
      {
        this.$root.toastError("Có lỗi trong quá trình trả phiếu: " + ex)
      }
      finally
      {
        this.loading(false)
      }
    },
    SendSMS: async function(content, msisdn){
      let data = {
        content: content,
        msisdn: msisdn,
        type: "reply"
      }
      await this.$root.context.post("/tichhop/sms/send",data);
    },
  }
}
</script>

<style>
    .legend-title {
        font-family: 'Roboto' !important;
    }
    a.disabled {
  color: #d3d3d3 !important;
  pointer-events: none;
  cursor: not-allowed;
  opacity: 0.6;
}

a {
  cursor: pointer;
}
</style>