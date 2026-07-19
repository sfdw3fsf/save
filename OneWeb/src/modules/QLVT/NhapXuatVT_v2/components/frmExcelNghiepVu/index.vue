<template>
  <div class="">
    <!-- Form -->
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="tsbtnNhapExcel.visible" :class="{ disabledInput: !tsbtnNhapExcel.enabled }" @click="tsbtnNhapExcel_Click">
          <a> <span class="icon one-excel"></span>Nhập Excel</a>
        </li>
        <li v-if="tsbtnFileMau.visible" :class="{ disabledInput: !tsbtnFileMau.enabled }" @click="tsbtnFileMau_Click">
          <a> <span class="icon one-excel"></span>File mẫu </a>
        </li>
        <li v-if="tsbtnDAIMS.visible" :class="{ disabledInput: !tsbtnDAIMS.enabled }" @click="tsbtnDAIMS_Click">
          <a> <span class="icon  one-file-search"></span>Tra cứu dự án IMS </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form row">
        <div class="col-sm-4 col-12">
          <div class="box-form">
            <p>Thông tin xuất</p>
            <div class="info-row condition-row" style>
              <div class="key w90">Loại CT</div>
              <div class="value">
                <SelectExt ref="cboLoaiChungTu" :disabled="!cboLoaiChungTu.enabled" v-model="cboLoaiChungTu.value" :dataSource="cboLoaiChungTu.list" dataTextField="loaiCt" dataValueField="loaiCtId" />
              </div>
            </div>
            <div class="info-row condition-row">
              <div class="key w90">Kiểu phiếu</div>
              <div class="value">
                <SelectExt ref="cboKieuPhieu" :disabled="!cboKieuPhieu.enabled" v-model="cboKieuPhieu.value" :dataSource="cboKieuPhieu.list" dataTextField="mucDich" dataValueField="mucDichId" />
              </div>
            </div>
            <div class="info-row condition-row">
              <div class="key w90">Mục đích</div>
              <div class="value">
                <SelectExt ref="cboMucDich" :disabled="!cboMucDich.enabled" v-model="cboMucDich.value" :dataSource="cboMucDich.list" dataTextField="mucDich" dataValueField="mucDichId" />
              </div>
            </div>
            <div class="info-row condition-row">
              <div class="key w90">Nghiệp vụ</div>
              <div class="value">
                <SelectExt ref="cboNghiepVu" :disabled="!cboNghiepVu.enabled" v-model="cboNghiepVu.value" :dataSource="cboNghiepVu.list" dataTextField="mucDich" dataValueField="mucDichId" />
              </div>
            </div>
            <div class="info-row condition-row">
              <div class="key w90">Ngày gửi</div>
              <div class="value">
                <vue-date :disabled="!dtpNgayGuiTT.enabled" v-model="dtpNgayGuiTT.value" format="DD/MM/YYYY HH:mm:ss" type="datetime" />
                <!-- <SelectExt ref="cboLoaiChungTu" :disabled="!cboLoaiChungTu.enabled" v-model="cboLoaiChungTu.value" :dataSource="cboLoaiChungTu.list" dataTextField="loaiCt" dataValueField="loaiCtId" /> -->
              </div>
            </div>
            <div class="info-row condition-row">
              <div class="key w90">Ngày nhận</div>
              <div class="value">
                <vue-date :disabled="!dtpNgayNhanTT.enabled" v-model="dtpNgayNhanTT.value" format="DD/MM/YYYY HH:mm:ss" type="datetime" />
                <!-- <SelectExt ref="cboLoaiChungTu" :disabled="!cboLoaiChungTu.enabled" v-model="cboLoaiChungTu.value" :dataSource="cboLoaiChungTu.list" dataTextField="loaiCt" dataValueField="loaiCtId" /> -->
              </div>
            </div>
          </div>
        </div>
        <div class=" col-sm-8 col-12">
          <div class="box-form">
            <p>Tra cứu HĐ PO</p>
            <div class="box-form">
              <p>Dự án</p>
              <DataGrid
                ref="grcMa_PDA"
                :columns="grcMa_PDA.header"
                :dataSource="grcMa_PDA.list"
                :selectionSettings="{ checkboxOnly: true }"
                :editSettings="{ allowEditing: true, mode: 'Batch' }"
                :showColumnCheckbox="true"
                :enabledSelectFirstRow="false"
                :allowPaging="true"
                :enablePagingServer="false"
                @rowSelected="grcMa_PDA_rowSelected"
                panelDataHeight="200px"
              />
            </div>
            <div class="box-form">
              <p>HĐ PO</p>
              <DataGrid
                ref="grcHDPO"
                :columns="grcHDPO.header"
                :dataSource="grcHDPO.list"
                :selectionSettings="{ checkboxOnly: true }"
                :editSettings="{ allowEditing: true, mode: 'Batch' }"
                :showColumnCheckbox="true"
                :enabledSelectFirstRow="false"
                :allowPaging="true"
                :enablePagingServer="false"
                @rowSelected="grcHDPO_rowSelected"
                panelDataHeight="200px"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import moment from 'moment'
import Vue from 'vue'
import api from './api'
import { grcMa_PDA_btnChonTemplate } from './components'

export default {
  components: {},
  emits: ['afterXacNhan'],
  props: {},
  data() {
    return {
      localLoading: 0,
      tsbtnNhapExcel: { visible: true, enabled: true },
      tsbtnFileMau: { visible: true, enabled: true },
      tsbtnDAIMS: { visible: true, enabled: true },
      cboLoaiChungTu: { visible: true, enabled: true, list: [], value: null },
      cboKieuPhieu: { visible: true, enabled: true, list: [], value: null },
      cboMucDich: { visible: true, enabled: true, list: [], value: null },
      cboNghiepVu: { visible: true, enabled: true, list: [], value: null },
      dtpNgayGuiTT: { visible: true, enabled: true, list: [], value: null },
      dtpNgayNhanTT: { visible: true, enabled: true, list: [], value: null },
      grcMa_PDA: {
        visible: true,
        enabled: true,
        header: [
          { width: 80, headerText: '', allowFiltering: false, template: grcMa_PDA_btnChonTemplate(this) },
          { fieldName: 'ProjectCode', headerText: 'Mã DA', allowFiltering: true },
          { fieldName: 'ProjectName', headerText: 'Tên dự án', allowFiltering: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      grcHDPO: {
        visible: true,
        enabled: true,
        header: [
          { fieldName: 'PO', headerText: 'PO', allowFiltering: true },
          { fieldName: 'CONTRACTNAME', headerText: 'ContractName', allowFiltering: true },
          { fieldName: 'SIGNOFFDATE', headerText: 'SignOffDate', allowFiltering: true },
          { fieldName: 'CONTRACTINFOID', headerText: 'ContractInfoID', allowFiltering: true },
          { fieldName: 'PARENTCONTRACTINFO', headerText: 'ParentContractInfo', allowFiltering: true },
          { fieldName: 'CONTRACTNUMBER', headerText: 'ContractNumber', allowFiltering: true },
          { fieldName: 'STATUS', headerText: 'STATUS', allowFiltering: true }
        ],
        list: [],
        selected: [],
        value: null
      }
    }
  },
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    },
    async 'cboLoaiChungTu.value'(val) {
      await this.cboLoaiChungTu_EditValueChanged()
    }
  },
  computed: {},
  async mounted() {
    for (let i = 0; i < 100; i++) this.grcMa_PDA.list.push({ ProjectCode: `${i}` })
    await this.frmExcelNghiepVu_Load()
  },
  methods: {
    async callApiWrapper(action, body) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        return result.data.data != null ? result.data.data : null
      } catch (e) {
        console.log('LOI ROI')
        console.log(e)
        if (e.data != null) {
          this.$toast.error(`${e.data.message}`)
        } else {
          this.$toast.error(`Có lỗi gọi API`)
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },

    grcMa_PDA_rowSelected(e) {
      console.log('grcMa_PDA_rowSelected')
      console.log(e)
    },
    grcHDPO_rowSelected(e) {
      console.log('grcHDPO_rowSelected')
      console.log(e)
    },
    async frmExcelNghiepVu_Load() {
      this.cboLoaiChungTu.enabled = false
      this.cboLoaiChungTu.list = await this.callApiWrapper(api.danh_muc_loai_chung_tu, { tag: this.tag })
      this.cboLoaiChungTu.value = null
      this.cboLoaiChungTu.enabled = true
      this.cboMucDich.list = await this.callApiWrapper(api.ds_muc_dich_nhap_xuat, { nhommd_id: 3 })
      this.cboNghiepVu.list = await this.callApiWrapper(api.ds_nghiep_vu, { nhomMdId: [292, 293] })
      this.cboNghiepVu.value = null
      this.cboLoaiChungTu.value = 2
      this.cboLoaiChungTu.enabled = false
    },
    async cboLoaiChungTu_EditValueChanged() {
      if (this.cboLoaiChungTu.value == null) return
      if (this.cboLoaiChungTu.value == 99) {
        // bangts.HT_KIEUPHIEU(cboKieuPhieu, 1);
        this.cboKieuPhieu.list = await this.callApiWrapper(api.lay_ds_kieu_phieu, { loaict_id: 1 })
      } else {
        // bangts.HT_KIEUPHIEU(cboKieuPhieu, Convert.ToInt32(cboLoaiChungTu.value));
        this.cboKieuPhieu.list = await this.callApiWrapper(api.lay_ds_kieu_phieu, { loaict_id: this.cboLoaiChungTu.value })
      }
    },

    async tsbtnFileMau_Click() {
      // try
      // {
      //     let f = new frmDownloadDialog();
      //     f.fURL = "http://10.70.52.164:8081/FILES/NET/VATTU/ImportXuat_IMS_HDPO (Khong ai Xoa nhe).xlsx";
      //     Uri uri = new Uri(f.fURL);
      //     let filename = Path.GetFileName(uri.LocalPath);
      //     let sFileExt = Path.GetExtension(filename);
      //     SaveFileDialog odlg = new SaveFileDialog();
      //     if (odlg.ShowDialog() == DialogResult.OK)
      //     {
      //         if (!odlg.FileName.EndsWith(sFileExt))
      //         {
      //             f.fDes = odlg.FileName + sFileExt;
      //             f.fName = odlg.FileName + sFileExt;
      //         }
      //         else
      //         {
      //             f.fDes = odlg.FileName;
      //             f.fName = odlg.FileName;
      //         }
      //         f.DownloadCompleted += (o, v) =>
      //         {
      //             if (f.ThanhCong)
      //             {
      //                 this.$toast.success("Tải file thành công");
      //                 try
      //                 {
      //                     if (Message_Box.ShowQuestion("Bạn muốn mở file vừa tải về không ?") == DialogResult.Yes)
      //                         try
      //                         {
      //                             let prc = new Process();
      //                             prc.StartInfo.FileName = f.fName;
      //                             prc.StartInfo.Verb = "Open";
      //                             prc.StartInfo.WindowStyle = ProcessWindowStyle.Maximized;
      //                             prc.Start();
      //                         }
      //                         catch
      //                         {
      //                             this.$toast.error("Không tìm thấy ứng dụng để mở file vừa tải !");
      //                         }
      //                     System.Diagnostics.Process.Start(Path.GetDirectoryName(f.fName));
      //                 }
      //                 catch ( ex)
      //                 {
      //                     this.$toast.warning("Lỗi mở file: " + ex);
      //                 }
      //             }
      //         };
      //         f.Start();
      //     }
      // }
      // catch ( ex)
      // {
      //     this.$toast.error("Có lỗi trong quá trình dowload và mở file " + ex);
      // }
    },

    async tsbtnNhapExcel_Click() {
      // try {
        if (this.cboLoaiChungTu.value == null) {
          this.$toast.warning('Hãy chọn loại chứng từ !')
          // this.$refs.cboLoaiChungTu.focus();
          return
        }
        if (this.cboNghiepVu.value == null) {
          this.$toast.warning('Hãy chọn nghiệp vụ !')
          // this.$refs.cboNghiepVu.focus();
          return
        }
        if (this.dtpNgayGuiTT.value == null) {
          this.$toast.warning('Hãy chọn ngày gửi !')
          // this.$refs.dtpNgayGuiTT.focus();
          return
        }
        if (this.dtpNgayNhanTT.value == null) {
          this.$toast.warning('Hãy chọn ngày nhận !')
          // this.$refs.dtpNgayNhanTT.focus();
          return
        }

        // let fdg = new OpenFileDialog
        // {
        //     RestoreDirectory = false,
        //     Multiselect = false,
        //     Filter = @"Excel Files(*.XLS;*.XLSX)|*.XLS;*.XLSX",
        //     FilterIndex = 1,
        //     Title = @"Cấu trúc file gồm: {MA_KHO_GIAO, MA_KHO_NHAN, MA_VT, SOLUONG, SERIAL, MA_DV_SD, MA_DV_QL, MA_CSHT, CONTRACT_INFO_ID, MA_DUAN, ND_GIAO, HD_PO_GOC, LOHANG}"
        // };
        // if (fdg.ShowDialog() != DialogResult.OK) return;
        // let path = fdg.FileNames[0];
        // let exten = Path.GetExtension(path);

        // if (exten == null)
        // {
        //     this.$toast.warning("Không lấy được kiểu file");
        //     return;
        // }
        // if (path == null)
        // {
        //     this.$toast.warning("Không lấy được đường dẫn");
        //     return;
        // }
        // let dt_file = bangts.DocFile_Excel(exten, path);

        // #region Kiểm tra dữ liệu
        // if (dt_file != null)
        //     if (dt_file.Columns.Count <= 1)
        //     {
        //         bangts.closeLoading(this);
        //         this.$toast.warning("Số lượng cột phải = 13");
        //         return;
        //     }

        // if (dt_file.Columns[0].ColumnName != "MA_KHO_GIAO")
        // {
        //     bangts.closeLoading(this);
        //     this.$toast.warning("Tên cột đầu tiên trong file không đúng định dạng : MA_KHO_GIAO");
        //     return;
        // }
        // if (dt_file.Columns[1].ColumnName != "MA_KHO_NHAN")
        // {
        //     bangts.closeLoading(this);
        //     this.$toast.warning("Tên cột thứ 2 trong file không đúng định dạng : MA_KHO_NHAN");
        //     return;
        // }
        // if (dt_file.Columns[2].ColumnName != "MA_VT")
        // {
        //     bangts.closeLoading(this);
        //     this.$toast.warning("Tên cột thứ 3 trong file không đúng định dạng : MA_VT");
        //     return;
        // }
        // if (dt_file.Columns[3].ColumnName != "SOLUONG")
        // {
        //     bangts.closeLoading(this);
        //     this.$toast.warning("Tên cột thứ 4 trong file không đúng định dạng : SOLUONG");
        //     return;
        // }
        // if (dt_file.Columns[4].ColumnName != "SERIAL")
        // {
        //     bangts.closeLoading(this);
        //     this.$toast.warning("Tên cột thứ 5 trong file không đúng định dạng : SERIAL");
        //     return;
        // }
        // if (dt_file.Columns[5].ColumnName != "MA_DV_SD")
        // {
        //     bangts.closeLoading(this);
        //     this.$toast.warning("Tên cột thứ 6 trong file không đúng định dạng : MA_DV_SD");
        //     return;
        // }
        // if (dt_file.Columns[6].ColumnName != "MA_DV_QL")
        // {
        //     bangts.closeLoading(this);
        //     this.$toast.warning("Tên cột thứ 7 trong file không đúng định dạng : MA_DV_QL");
        //     return;
        // }
        // if (dt_file.Columns[7].ColumnName != "MA_CSHT")
        // {
        //     bangts.closeLoading(this);
        //     this.$toast.warning("Tên cột thứ 8 trong file không đúng định dạng : MA_CSHT");
        //     return;
        // }
        // if (dt_file.Columns[8].ColumnName != "CONTRACT_INFO_ID")
        // {
        //     bangts.closeLoading(this);
        //     this.$toast.warning("Tên cột thứ 9 trong file không đúng định dạng : CONTRACT_INFO_ID");
        //     return;
        // }
        // if (dt_file.Columns[9].ColumnName != "MA_DUAN")
        // {
        //     bangts.closeLoading(this);
        //     this.$toast.warning("Tên cột thứ 10 trong file không đúng định dạng : MA_DUAN");
        //     return;
        // }
        // if (dt_file.Columns[10].ColumnName != "ND_GIAO")
        // {
        //     bangts.closeLoading(this);
        //     this.$toast.warning("Tên cột thứ 11 trong file không đúng định dạng : ND_GIAO");
        //     return;
        // }
        // if (dt_file.Columns[11].ColumnName != "HD_PO_GOC")
        // {
        //     bangts.closeLoading(this);
        //     this.$toast.warning("Tên cột thứ 12 trong file không đúng định dạng : HD_PO_GOC");
        //     return;
        // }
        // if (dt_file.Columns[12].ColumnName != "LOHANG")
        // {
        //     bangts.closeLoading(this);
        //     this.$toast.warning("Tên cột thứ 13 trong file không đúng định dạng : LOHANG");
        //     return;
        // }
        // #endregion

        // let js_dt = Newtonsoft.Json.JsonConvert.SerializeObject(dt_file);

        // let res = chungtu.GET_VALUE_FUNC_V2("{?DB12}.sinh_chungtu_xuatims", "vkieu", 1, "vds", js_dt, "vnghiepvu_id",
        //     cboNghiepVu.value, "vmay_cn", ttnd.may_cn, "vip_cn", ttnd.ip, "vnguoi_cn", ttnd.ma_nd, "vloaict_id",
        //     cboLoaiChungTu.value, "vloaiphieu_id", cboKieuPhieu.value, "vmucdich_id", cboMucDich.value,
        //     "vngaygui", dtpNgayGuiTT.value.ToString(), "vngaynhan", dtpNgayNhanTT.value.ToString(), "vngayky", "").ToString();
        let res = await this.callApiWrapper(api.sinh_chungtu_xuatims, {})
        if (res != '1') this.$toast.warning(res)
        else {
          this.$toast.success('Tạo chứng từ xuất ims thành công')
          this.$emit('afterXacNhan', { xacnhan: true })
        }
      // } catch (ex) {
      //   this.$toast.error(' ' + ex)
      // }
    },
    async btnChon_PDA_ButtonClick(row) {
      try {
        let zloi = ''
        // let dt_hdpo = ims.Get_ContractInfo_byProjectCode_Station(grvMa_PDA.GetFocusedRowCellValue("ProjectCode").ToString(), "", out zloi);
        if (zloi != '') {
          this.$toast.warning(zloi)
          this.grcHDPO.list = null
          return
        }
        if (dt_hdpo == null) {
          this.$toast.warning('Không có thông tin hợp đồng - PO')
          this.grcHDPO.list = null
          return
        }

        let json = JSON.parse(dt_hdpo)
        // let temp = chungtu.GET_DATA_PROC('{?DB12}.lay_tt_dongbo_hdms_ims', 'vds', json)
        let temp = await this.callApiWrapper(api.lay_tt_dongbo_hdms_ims, { vds: json })

        this.grcHDPO.list = temp
      } catch (ex) {
        this.$toast.warning(`Có lỗi khi chọn PDA ${ex}`)
      }
    },
    async tsbtnDAIMS_Click() {
      console.log('abc')
      let dt = await this.callApiWrapper(api.ds_ref_ims, {
        mucDichId: this.cboNghiepVu.value,
        kieuGoiId: '',
        maPda: '',
        khoId: ''
      })
      this.grcMa_PDA.list = dt
    }
  }
}
</script>
<style scoped>
.condition-row {
  margin: 0 0 20px 0;
}
</style>
