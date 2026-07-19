<template>
  <div class="box-form">
    <div>
      <div class="legend-title">
        <div class="pull-left">
          <span class="title inline vcenter">Thông tin file</span>
          <button class="btn btn-second" @click="tsbtnLamMoi_Clicked"><span class="one-reload"></span>{{ tsbtnLamMoi.text }}</button>
          <button class="btn btn-second" @click="tsbtnLuuFile_Clicked"><span class="one-file-plus"></span> {{ tsbtnLuuFile.text }}</button>
        </div>
        <div class="pull-right">
          <a href="javascript:;" class="red btn f20 lh20" @click="$emit('tsbtnClosed_Clicked')">
            <span class="-ap icon-arrow-right3"></span>
          </a>
        </div>
        <div class="clearfix"></div>
      </div>

      <div class="nav tabs tab-over mart10">
        <a :href="item.href" data-toggle="tab" v-for="(item, index) in tab.list" :key="index" @click="tab.value = item.id">{{ item.text }}</a>
        <!-- <a href="#tab1" data-toggle="tab" class="active">Thông tin upload</a> :class="tab.value == item.id ? 'active' : ''"
        <a href="#tab2" data-toggle="tab">Danh sách đã upload</a> -->
      </div>

      <div class="tab-content marb0">
        <div id="tab1" class="tab-pane active">
          <div class="info-row">
            <div class="key w80">Nhóm file</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt v-model="cboNhomFile.value" :dataSource="cboNhomFile.list" dataTextField="NAME" dataValueField="ID" />
              </div>
            </div>
          </div>

          <div class="info-row">
            <div class="key w80">Loại File</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt v-model="cboLoaiFile.value" :dataSource="cboLoaiFile.list" dataTextField="loai_file" dataValueField="loaifile_id" />
              </div>
            </div>
          </div>

          <div class="info-row">
            <div class="key w80">Đường dẫn</div>
            <div class="value" :key="file_refresh">
              <div class="input-more-button -right">
                <label class="btn" for="upload-file">
                  <span class="-ap icon-more_horiz"></span>
                  <input type="file" ref="files" class="hidden" id="upload-file" @change="onFileChange" />
                </label>
                <input type="text" class="form-control highlight" v-model="txtDuongDan.value" />
              </div>
            </div>
          </div>
          <div class="info-row" v-if="txtDuongDan.value">
            <div style="color: blue">Import thành công: {{ pathFileUpload }}</div>
          </div>
          <div class="info-row">
            <div class="key w80">Ghi chú</div>
            <div class="value">
              <input type="text" class="form-control" v-model="txtGhiChu.value" />
            </div>
          </div>
          <div class="title-bg-main">
            <div class="pull-left">
              <span class="title mart5">Danh sách thuê bao</span>
              <a href="javascript:;" class="btn btn-second pad2 lh20 button-not-bg marl10" @click="NAP_DS_HOPDONGTB(vhdkh_id)">
                <span class="nc-icon-outline arrows-1_refresh-69 f20"></span>
              </a>
            </div>
            <div class="pull-right">
              <a href="javascript:;" class="btn btn-second pad2 lh20 button-not-bg marl10">
                <span class="nc-icon-outline ui-2_filter f20"></span>
              </a>
            </div>
            <div class="clearfix"></div>
          </div>
          <div class="table-content" style="max-height: 800px">
            <DataGrid
              ref="gridDanhSachThueBao"
              v-bind:columns="gridDanhSachThueBao.header"
              v-bind:dataSource="gridDanhSachThueBao.list"
              :showFilter="true"
              :enabledSelectFirstRow="true"
              :editSettings="editSettings"
              :showColumnPointer="false"
              :enable-paging-server="false"
              :allowPaging="true"
              @rowSelected="gridDanhSachThueBao_rowSelected"
            >
              <!-- @rowSelected="rowSelected_danhsachthuebao" -->
            </DataGrid>
          </div>
        </div>
        <div id="tab2" class="tab-pane">
          <div class="table-content" style="max-height: 800px">
            <DataGrid
              ref="gridDSDaUpload"
              v-bind:columns="gridDSDaUpload.header"
              v-bind:dataSource="gridDSDaUpload.list"
              :showFilter="true"
              :enabledSelectFirstRow="false"
              :editSettings="editSettings"
              :showColumnPointer="false"
              :showColumnCheckbox="true"
              :enable-paging-server="false"
              :allowPaging="true"
              @rowSelected="gridDSDaUpload_rowSelected"
            >
              <!-- @rowDeselected="rowDeselected_grcListUpGDV" -->
            </DataGrid>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import api from './api'
export default {
  emits: ['tsbtnClosed_Clicked', 'onUploadedListener'],
  props: {
    vhdkh_id: {
      type: [String, Number],
      require: true
    },
    vhoso_id: {
      type: [String, Number],
      require: true
    },
    vdanhsach_bhs: {
      type: [Array],
      require: true
    }
  },
  data() {
    return {
      tab: {
        value: 1,
        list: [
          { id: 1, href: '#tab1', text: 'Thông tin upload' },
          { id: 2, href: '#tab2', text: 'Danh sách đã upload' }
        ]
      },
      tsbtnLamMoi: { enabled: true, visible: true, text: 'Làm mới', icon: 'icon one-file-attach1' },
      tsbtnLuuFile: { enabled: true, visible: true, text: 'Lưu file', icon: 'icon one-file-attach1' },
      cboNhomFile: { visible: true, enabled: true, list: [], value: null, text: 'Nhóm file' },
      cboLoaiFile: { visible: true, enabled: true, list: [], value: null, text: 'Loại file' },
      txtDuongDan: { visible: true, enabled: true, value: null, text: 'Đường dẫn' },
      txtGhiChu: { visible: true, enabled: true, value: null, text: 'Ghi chú' },
      inputFile: { value: null },
      file_refresh: 0,
      editSettings: {
        allowEditing: false,
        allowAdding: false,
        allowDeleting: false,
        mode: 'Normal'
      },
      gridDanhSachThueBao: {
        header: [
          { fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true, width: 110 },
          { fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true, width: 120 },
          { fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái', allowFiltering: true, width: 110 },
          { fieldName: 'NGAY_BH', headerText: 'Ngày báo hỏng', allowFiltering: true, width: 120 },
          { fieldName: 'NGUOI_BH', headerText: 'Người báo hỏng', allowFiltering: true, width: 120 },
          { fieldName: 'NGAY_HT', headerText: 'Ngày nghiệm thu', allowFiltering: true, width: 120 },
          { fieldName: 'LOAI_HD', headerText: 'Loại HĐ', isGroupedColumn: true, width: 120 }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridDSDaUpload: {
        header: [
          {
            textAlign: 'left',
            fieldName: 'nhom_file',
            headerText: 'Nhóm file',
            allowFiltering: true,
            allowSorting: false,
            isGroupedColumn: true
          },
          {
            textAlign: 'left',
            fieldName: 'ma_tb',
            headerText: 'Số máy/ Acc',
            allowFiltering: true,
            allowSorting: false
          },

          {
            textAlign: 'left',
            fieldName: 'loai_file',
            headerText: 'Loại file',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'ten_file',
            headerText: 'Tên file',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'loai_hd',
            headerText: 'Loại HĐ/PL',
            allowSorting: 'true',
            allowFiltering: 'true'
          },

          {
            textAlign: 'left',
            fieldName: 'ma_gd',
            headerText: 'Mã GD',
            allowSorting: 'true',
            allowFiltering: 'true'
          },

          {
            textAlign: 'left',
            fieldName: 'ma_kh',
            headerText: 'Mã KH',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'ngay_yc',
            headerText: 'Ngày YC',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'url',
            headerText: 'Đường dẫn',
            allowSorting: 'true',
            allowFiltering: 'true'
          },

          {
            fieldName: '',
            headerText: 'Xem/Tải về',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'Center',
            width: 180,
            template: function () {
              return {
                template: Vue.component('columnTemplate', {
                  template: `<row>
                                <a class="btn btn-huylydo lh14" @click="onDownLoad"
                                style="background-color: #3684e0 !important; padding: 5px 15px; color: white">
                    <span class="fa fa-angle-down lh14"></span></a></row>
                    `,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    async onDownLoad() {
                      if (this.data) {
                        //this.downloadFile(this.data.url, this.data.ten_file)
                        this.downloadFile_old(this.data.url)
                      }
                    },
                    downloadFile(url, filename) {
                      var anchor = document.createElement('a')
                      anchor.setAttribute('download', filename)
                      anchor.setAttribute('href', url)
                      anchor.style.display = 'none'
                      document.body.appendChild(anchor)
                      anchor.click()
                      document.body.removeChild(anchor)
                    },

                    downloadFile_old(url) {
                      var downloadLink = document.createElement('a')
                      downloadLink.href = url
                      downloadLink.target = '_blank'
                      downloadLink.click()
                    }
                  }
                })
              }
            }
          },
          {
            textAlign: 'left',
            fieldName: 'nguoi_cn',
            headerText: 'Người Upload',
            allowSorting: 'true',
            allowFiltering: 'true'
          },
          {
            textAlign: 'left',
            fieldName: 'ngay_cn',
            headerText: 'Ngày Upload',
            allowSorting: 'true',
            allowFiltering: 'true'
          }
        ],
        list: [],
        selected: [],
        value: null
      },
      pathFileUpload: '',
      dtHSThueBao: [],
      batbuoc: -1
    }
  },
  methods: {
    async tsbtnLayTT_Clicked() {
      let kq_nhom_file = await api.nhom_file(this.axios, {})
      if (kq_nhom_file.data.data && kq_nhom_file.data.data.length) {
        this.cboNhomFile.list = kq_nhom_file.data.data
      } else {
        this.cboNhomFile.list = []
      }
      // let kq_loai_file = await api.loai_file(this.axios, {})
      // if (kq_loai_file.data.data && kq_loai_file.data.data.length) {
      //   this.cboLoaiFile.list = kq_loai_file.data.data.filter((e) => e.loaifile_id != -1)
      //   console.log(this.cboLoaiFile.list)
      // } else {
      //   this.cboLoaiFile.list = []
      // }
      await this.NAP_DS_HOPDONGTB(this.vhdkh_id)
    },
    async tsbtnLamMoi_Clicked() {
      this.cboNhomFile.value = null
      this.cboLoaiFile = null
      this.txtDuongDan.value = ''
      this.txtGhiChu.value = ''
      this.pathFileUpload = ''
      this.inputFile.value = []
      this.file_refresh += 1
      await this.tsbtnLayTT_Clicked()
    },
    async tsbtnLuuFile_Clicked() {
      console.log('tsbtnLuuFile_Clicked')
      console.log(this.tab.value)
      // try {
      // this.loading(true)
      if (this.gridDanhSachThueBao.list.length == 0) {
        this.$toast.warning('Không có thông tin thuê bao, không thể upload file!')
        return
      }
      if (this.tab.value == 1) {
        this.CAPNHAT_FILE()
      } else {
        this.GANFILEUPLOAD_BHS()
      }
      // } catch (e) {
      //   this.loading(false)
      // } finally {
      //   this.loading(false)
      // }
    },
    CAPNHAT_FILE() {
      //   try {
      // if (!this.grid_danhsachbohoso || this.grid_danhsachbohoso.length == 0) {
      //   this.$toast.error('Không tìm thấy bộ hồ sơ để thêm file.')
      //   return
      // }
      if (this.cboLoaiFile.value == null) {
        this.$toast.error('Bạn chưa chọn loại file.')
        return
      }
      if (!this.txtDuongDan.value || this.txtDuongDan.value.toString().trim() == '') {
        this.$toast.error('Bạn chưa nhập đường dẫn file')
        return
      }
      if (this.vdanhsach_bhs.length == 1) {
        //region Thực hiện upload theo 1 file
        if (!this.vdanhsach_bhs[0].bohs_id || this.vdanhsach_bhs[0].vbohs_id == 0) {
          this.$toast.error('Không tìm thấy bộ hồ sơ để thêm file.')
          return
        }

        this.GET_HS_THUEBAO()
        if (!this.dtHSThueBao || this.dtHSThueBao.length == 0) {
          this.$toast.error('Bạn bắt buộc phải gán thuê bao với loại file ' + this.CboLoaiFile)
          return
        }
        this.UploadFileHoSo()
      } else {
        //Thực hiện upload theo ds
        this.danhsach = true
        this.$confirm(`Bạn có muốn upload file này cho ` + this.vdanhsach_bhs.length + ` hợp đồng không?`, {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        }).then(async () => {
          if (this.button.themfile == false) {
            this.GET_HS_THUEBAO()
            if (!this.dtHSThueBao || this.dtHSThueBao.length == 0) {
              this.$toast.error('Bạn bắt buộc phải gán thuê bao với loại file ' + this.CboLoaiFile)
              return
            }
            this.UploadFileHoSo()
          } else {
            this.$toast.error('this.button.themfile')
          }
        })
      }
      //   } catch (e) {
      //     this.$toast.error(e)
      //     console.log(e)
      //   }
    },
    async UploadFileHoSo() {
      try {
        if (this.txtDuongDan && this.inputFile.value && this.inputFile.value.length > 0) {
          var formData = new FormData()
          for (var file of this.inputFile.value) {
            formData.append('files', file)
          }
          var response = await api.upload_file_hoso(this.axios, formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          })
          var kq = response.data.data ? response.data.data : ''
          this.pathFileUpload = kq.join('|')
          console.log('this.pathFileUpload')
          console.log(this.pathFileUpload)
          this.capnhat_file_hoso()
        }
      } catch (e) {
        this.$toast.error(e)
      }
    },
    UpperCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    async capnhat_file_hoso() {
      // try {
      if ([['', null]].includes(this.pathFileUpload)) {
        this.$toast.error('NULL URL RỒI!')
        return
      }
      if (this.vdanhsach_bhs.length == 1) {
        //Up lẻ
        //hot fix
        var input = {
          vkieu: 6,
          vhoso_id: this.vhoso_id,
          vloaifile_id: this.cboLoaiFile.value,
          vfile_cn: 0, //aka file_cn
          vbohs_id: this.vdanhsach_bhs[0].bohs_id,
          vurl: this.pathFileUpload,
          vghichu: this.txtGhiChu.value,
          vfile_clob: JSON.stringify(
            this.UpperCasePropertyList([
              {
                file_id: 0,
                hdtb_id: 0,
                thuebao_id: 0,
                trangthai: 0
              }
            ])
          ),
          vds_hs_thuebao: JSON.stringify(this.UpperCasePropertyList(this.dtHSThueBao))
        }
        var res = await api.fn_capnhat_file_hstb(this.axios, input)

        var kq = res.data ? res.data : ''
        if (!kq || kq.error_code != 'BSS-00000000' || kq.data.includes('ERROR') == true) {
          this.DeleteFileOnServer(this.pathFileUpload)
          var error = kq.data ? kq.data : kq.message_detail ? kq.message_detail : 'Lỗi ghi lại file'
          this.$toast.error(error)
          return
        }

        this.$toast.success('Thêm mới file hồ sơ thành công!')
        await this.tsbtnLayTT_Clicked()
        this.$emit('onUploadedListener')
        // var ma_gd = this.txtMaGD
        // this.btnClear_Click()
        // this.LOADFILE_HS()
        // await this.FocusGridViewRow('grid_danhsachbohoso', 'ma_gd', ma_gd)
      } else if (!this.button.themfile && this.danhsach) {
        // var ar = []
        // var dt_kt = this.$refs.grid_danhsachbohoso.getSelectedRecords()
        // for (var i = 0; i < dt_kt.length; i++) {
        //   ar.push({
        //     BOHS_ID: dt_kt[i].bohs_id,
        //     HDKH_ID: dt_kt[i].hdkh_id,
        //     MA_KH: dt_kt[i].ma_kh,
        //     MA_HD: dt_kt[i].ma_hd
        //   })
        // }
        // var res = await api.capnhat_filehs_ds(this.axios, {
        //   vbohs_id: ar,
        //   vfile_clob: '',
        //   vghichu: this.txtGhiChu,
        //   vkieu: 1,
        //   vloaifile_id: this.CboLoaiFile,
        //   vma_kh: ar[0].ma_hd,
        //   vurl: this.pathFileUpload
        // })
        // //hot fix
        // var kq = res.data ? res.data : ''
        // if (!kq || kq.error_code != 'BSS-00000000' || kq.data.includes('ERROR') == true) {
        //   this.DeleteFileOnServer(this.pathFileUpload)
        //   var error = kq.data ? kq.data : kq.message_detail ? kq.message_detail : 'Lỗi ghi lại file'
        //   this.$toast.error(error)
        //   return
        // }
        // this.$toast.success('Thêm mới file hồ sơ thành công!')
        // var ma_gd = this.txtMaGD
        // this.btnClear_Click()
        // this.LOADFILE_HS()
        // await this.FocusGridViewRow('grid_danhsachbohoso', 'ma_gd', ma_gd)
      }
      // } catch (ex) {
      //   this.DeleteFileOnServer(this.pathFileUpload)
      //   this.$toast.error('Lưu file thất bại: ' + kq)
      //   return
      // }
    },
    async DeleteFileOnServer(url) {
      var res = await api.xoa_file(this.axios, {
        list_file: url
      })
      var kq = res.data.error_code ? res.data.error_code : 0
      if (kq == 'BSS-00000000') {
        return true
      } else {
        return false
      }
    },
    async GANFILEUPLOAD_BHS() {
      //duy123123123
      if (this.vdanhsach_bhs.length == 0) {
        this.$toast.error('Không tìm thấy bộ hồ sơ để thêm file.')
        return
      }

      if (this.gridDSDaUpload.length == 0) {
        this.$toast.error('Không tìm thấy thông tin danh sách đã upload')
        return
      }
      if (!this.array_file_id || this.array_file_id.length == 0) {
        this.$toast.error('Bạn chưa chọn file upload để gán vào bộ hồ sơ')
        return
      }

      this.list_file_id = ''
      for (var i = 0; i < this.array_file_id.length; i++) {
        this.list_file_id = this.list_file_id + this.array_file_id[i].file_id + ','
      }
      this.list_file_id = this.list_file_id.substring(0, this.list_file_id.length - 1)

      var res = await api.sp_capnhat_file_hs(this.axios, {
        bohs_id: this.vbohs_id,
        list_file_id: this.list_file_id
      })

      this.array_file_id = []
      this.NAP_DS_HOPDONGTB(this.vhdkh_id_luoi)
      this.LOADFILE_HS()
      this.$toast.success('Đã gán file thành công')
    },
    GET_HS_THUEBAO() {
      //   if (this.grid_danhsachthuebao.length == 0) {
      //     return
      //   }
      let ds = [this.gridDanhSachThueBao.value]
      console.log('GET_HS_THUEBAO')
      console.log(ds)
      var kt_baohong = false
      if (ds.includes('baohong_id') == true) {
        kt_baohong = true
      }
      this.dtHSThueBao = []
      for (var i = 0; i < ds.length; i++) {
        this.dtHSThueBao.push({
          thuebao_id: !ds[i].THUEBAO_ID ? null : ds[i].THUEBAO_ID,
          thanhtoan_id: !ds[i].THANHTOAN_ID ? null : ds[i].THANHTOAN_ID,
          ma_tb: !ds[i].HDKH_ID || ds[i].HDKH_ID.toString() == '0' ? ds[i].MA_TB : '',
          hdtb_id: !ds[i].HDTB_ID ? null : ds[i].HDTB_ID,
          ma_kh: ds[i].MA_KH && ds[i].MA_KH.toString() != '0' ? ds[i].MA_KH : '',
          baohong_id: kt_baohong && ds[i].baohong_id ? ds[i].baohong_id : null,
          hdkh_id: !ds[i].HDKH_ID ? null : ds[i].HDKH_ID
        })
      }
    },
    async NAP_DS_HOPDONGTB(vhdkh_id) {
      this.cboLoaiFile.value = null
      this.cboNhomFile.value = null
      this.txtDuongDan.value = ''
      this.txtGhiChu.value = ''
      this.gridDanhSachThueBao.list = []

      var res = await api.lay_ds_hd_tb_theo_hdkh_id(this.axios, { vhdkh_id: vhdkh_id })
      if (res && (res.data.error_code == 'BSS-00000000' || res.data.error_code == 'BSS-00000204')) {
        this.gridDanhSachThueBao.list = res.data.data ? res.data.data : []
      } else {
        this.$toast.error(res.data.message)
        this.gridDanhSachThueBao.list = []
      }

      ///duy26/04

      this.gridDSDaUpload.list = []
      var res1 = await api.lay_ds_filehs_theo_kh(this.axios, { params: { vhdkh_id: vhdkh_id } })
      if (res1 && (res1.data.error_code == 'BSS-00000000' || res1.data.error_code == 'BSS-00000204')) {
        this.gridDSDaUpload.list = res1.data.data ? res1.data.data : []
      } else {
        this.$toast.error(res1.data.message)
        this.gridDSDaUpload.list = []
      }

      console.log('gridDSDaUpload.list', this.gridDSDaUpload.list)
      ///end duy 26/04

      this.LOC_LUOI_TB()
    },

    async LOC_LUOI_TB() {
      // gridDanhSachThueBao.list = gridDanhSachThueBao.list.filter(function (ds) {
      //       return ds.chk == 0
      // });

      if (this.cboLoaiFile.value == -1) {
        this.batbuoc = -1
      }

      //   if (this.button.themfile == false) {
      if (this.batbuoc == 1) {
        console.log('this.vnhomfile_id', this.vnhomfile_id)
        //Nếu ko phải bắt buộc
        if ((this.vnhomfile_id == 1 || this.vnhomfile_id <= 0) && this.vnhomfile_id != -1) {
          this.gridDanhSachThueBao.list = this.gridDanhSachThueBao.list.filter(function (ds) {
            return ds.LOAIHD_ID != 0 && ds.MA_KH == 0
          })
        } else if (this.vnhomfile_id == -1) {
        } else {
          this.gridDanhSachThueBao.list = this.gridDanhSachThueBao.list.filter(function (ds) {
            return ds.LOAIHD_ID == 0 && ds.MA_KH == 0
          })
        }
      }

      if (this.batbuoc == 2) {
        console.log('this.vnhomfile_id_2', this.vnhomfile_id)
        var ind = []
        for (var i = 0; i < this.gridDanhSachThueBao.list.length; i++) {
          if (this.gridDanhSachThueBao.list[i].hdkh_id != null && this.gridDanhSachThueBao.list[i].hdkh_id != 0) {
            ind.push(i)
          }
        }
        this.$refs.gridDanhSachThueBao.flagSelectedRowIndexes = ind
        //Nếu ko phải bắt buộc
        if ((this.vnhomfile_id == 1 || this.vnhomfile_id <= 0) && this.vnhomfile_id != -1) {
          this.gridDanhSachThueBao.list = this.gridDanhSachThueBao.list.filter(function (ds) {
            return ds.LOAIHD_ID != 0 && ds.MA_KH != 0
          })
        } else if (this.vnhomfile_id == -1) {
        } else {
          this.gridDanhSachThueBao.list = this.gridDanhSachThueBao.list.filter(function (ds) {
            return ds.LOAIHD_ID == 0 && ds.MA_KH != 0
          })
        }
        // }
      }
    },
    async get_loai_file() {
      console.log('get_loai_file')
      console.log(this.cboNhomFile.value)
      let kq = await api.loai_file(this.axios, {})

      this.cboLoaiFile.list = kq.data.data.filter((e) => {
        return e.nhomfile_id == -1 || e.nhomfile_id == this.cboNhomFile.value
      })
      this.cboLoaiFile.value = this.cboLoaiFile.list > 0 ? this.cboLoaiFile.list[0].loaifile_id : null
    },
    async Load_dstb(bien_hdkh_id) {
      var res = await api.lay_ds_hd_tb_theo_hdkh_id(this.axios, {
        vhdkh_id: bien_hdkh_id
      })

      this.gridDanhSachThueBao.list = res.data.data ? res.data.data : []
      this.LOC_LUOI_TB()
    },
    onFileChange(e) {
      //   try {
      //   this.file_refresh += 1
      this.txtDuongDan.value = e.target.files[0].name
      this.inputFile.value = e.target.files

      //   } catch (e) {
      //     this.$toast.error('Lỗi import file: ' + e)
      //   }
    },
    async gridDanhSachThueBao_rowSelected(e) {
      console.log('gridDanhSachThueBao_rowSelected')
      console.log(e)
      this.gridDanhSachThueBao.value = e != null ? e.data : null
    },
    async gridDSDaUpload_rowSelected(e) {
      console.log('gridDSDaUpload_rowSelected')
      console.log(e)
      this.gridDSDaUpload.value = e != null ? e.data : null
    }
  },
  watch: {
    'cboNhomFile.value'(val) {
      this.vnhomfile_id = val
      this.NAP_DS_HOPDONGTB(this.vhdkh_id)
      this.cboNhomFile.value = val
      this.get_loai_file(val)
      this.cboLoaiFile.value = null
      //   this.grid_danhsachthuebao = []
    },
    'cboLoaiFile.value'(newval) {
      this.batbuoc = this.cboLoaiFile.list.find((e) => e.loaifile_id == this.cboLoaiFile.value)?.kieu
      console.log('this.batbuoc')
      console.log(this.batbuoc)
      this.cboLoaiFile.value = newval
      this.Load_dstb(this.vhdkh_id)
    },
    'gridDanhSachThueBao.list'(val) {
      this.gridDanhSachThueBao.value = null
    }
  },
  async mounted() {
    console.log('frmUploadFile')
    console.log(this.vhoso_id)
    console.log(this.vdanhsach_bhs)
    console.log(this.vhdkh_id)
    await this.tsbtnLayTT_Clicked()
  }
}
</script>
<style lang=""></style>
