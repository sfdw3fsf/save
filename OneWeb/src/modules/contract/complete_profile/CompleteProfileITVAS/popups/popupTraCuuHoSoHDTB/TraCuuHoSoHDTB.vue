<style scoped>
.list-actions-top .list > li > div.button {
  display: block;
  padding: 2px 15px;
  color: #0176ff;
  border-left: 1px solid #bae7ff;
  text-decoration: none;
  font-weight: 600;
  cursor: pointer;
}
</style>

<template>
  <ejs-dialog @close="close" :isModal="true" :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogTraCuuHoSoHDTB" :position="position" :header="'Tra cứu hồ sơ hợp đồng'" showCloseIcon="true" width="75%" target="#app .main-wrapper">
    <div class="">
      <div class="popup-body">
        <div class="row">
          <div class="box-form">
            <div class="legend-title">Danh sách hồ sơ</div>
            <b-tabs content-class="mt-3">
              <b-tab title="Hồ sơ thuê bao" active>
                <div class='row mb-1'>
                  <div class='col-lg-3 col-md-4 col-sm-6 col-xs-12'>
                    <button class='btn btn-success' @click='kiemtra_hoso_ekyc'>Kiểm tra bằng OCR</button>
                  </div>
                </div>
                <div class='row'>
                <div class="table-content">
                  <DataGrid class="table-result table-gachle" v-bind:columns="controls.grvFileHS.headers" v-bind:dataSource="controls.grvFileHS.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true"> </DataGrid>
                </div>
                </div>
              </b-tab>
              <b-tab title="Hồ sơ EKYC">
                <div class='row'>
                  <DataGrid
                    class="table-result table-gachle"
                    v-bind:columns="controls.grvFileHSEkyc.headers"
                    v-bind:dataSource="controls.grvFileHSEkyc.list"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :commands="[
                      { name: 'TAI_VE', cssClass: 'btn btn-primary one-download', title: 'Tải về' },
                      { name: 'DUYET_HS', cssClass: 'btn btn-danger one-check', title: 'Duyệt hồ sơ' }
                    ]"
                    :commandColumn="{ headerText: 'Tải về/ Duyệt', width: 200, firstColumn: false, show: true }"
                    :showFilter="true"
                    @commandClicked="thaotacfile"
                  >
                  </DataGrid>
                </div>
              </b-tab>
            </b-tabs>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import fileDownload from 'js-file-download'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment'
import api from './PopUpApi'
import commonFn from '@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions'
import axios2 from 'axios'
export default {
  components: {
    moment,
    DatePicker,
    api
  },
  name: 'TraCuuHoSoHDTB',
  data() {
    return {
      commands: [{ name: 'TAO_CAP', cssClass: 'btn btn-main one-taocap f15', title: 'Tạo cáp tới kết cuối' }],
      Loading: false,
      dateFormat: 'DD/MM/YYYY',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      showTimePanel: false,
      tabIndex: 0,
      state: {},
      tt_nd: {
        nhanvien_id: this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: this.$root.token.getMachine(),
        ip_cn: this.$root.token.getIP(),
        ngay_cn: moment(new Date()).format('DD/MM/YYYY hh:mm A'),
        ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format('DD/MM/YYYY hh:mm A'),
        ten_dv: '',
        nguoi_cn: this.$root.token.getUserName(),
        ma_nd: this.$root.token.getUserName()
      },

      controls: {
        grvFileHS: {
          headers: [
            { fieldName: 'kq_ekyc', headerText: 'Kết quả EKYC', allowHtml: true, allowFiltering: false },
            { fieldName: 'loai_file', headerText: 'Loại file', allowFiltering: true },
            { fieldName: 'ten_file', headerText: 'Loại file', allowFiltering: true },
            { fieldName: 'loai_hd', headerText: 'Loại HĐ/PL', allowFiltering: true },
            { fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true, type: 'datetime', format: 'dd/MM/yyyy HH:mm:ss', textAlign: 'right', width: '148' },
            { fieldName: 'nhom_file', headerText: 'Nhóm file', allowFiltering: true },
            { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true },
            { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true, type: 'datetime', format: 'dd/MM/yyyy HH:mm:ss', textAlign: 'right', width: '148' },
            { fieldName: 'tai_ve', headerText: 'Xem/Tải về', allowFiltering: true, allowHtml: true, textAlign: 'center', width: '148' }
          ],
          list: []
        },

        grvFileHSEkyc: {
          headers: [
            { fieldName: 'url', headerText: 'Đường link', allowFiltering: false },
            { fieldName: 'name', headerText: 'Loại file', allowFiltering: false },
            { fieldName: 'status', headerText: 'Trạng thái', allowFiltering: false }
          ],
          list: []
        }
      }
    }
  },
  created: async function () {},
  mounted: function () {},
  watch: {},
  methods: {
    b64toBlob(b64Data, contentType = '', sliceSize = 512) {
      const byteCharacters = atob(b64Data)
      const byteArrays = []

      for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
        const slice = byteCharacters.slice(offset, offset + sliceSize)

        const byteNumbers = new Array(slice.length)
        for (let i = 0; i < slice.length; i++) {
          byteNumbers[i] = slice.charCodeAt(i)
        }

        const byteArray = new Uint8Array(byteNumbers)
        byteArrays.push(byteArray)
      }

      const blob = new Blob(byteArrays, { type: contentType })
      return blob
    },
    async thaotacfile(name, dataItem) {
      console.log('thaotacfile')
      console.log('name')
      if (name === 'TAI_VE') {
        try {
          let kq = await api.tai_file_ekyc(this.axios, {
            StrRequest: dataItem.url
          })
          // let kq = axios2.post(`http://10.70.52.244:6061/api/SmartCA/DownFileEKYC`, {
          //   StrRequest: "/ekyc_images/upload/017089003196/20230224000302rear_preview.jpg"
          // });
          const byteCharacters = this.b64toBlob(kq.data.data.dulieu, kq.data.data.mime_type)

          var filename = dataItem.url.substring(dataItem.url.lastIndexOf('/') + 1)
          console.log(kq.data.data)
          fileDownload(byteCharacters, filename, kq.data.data.mime_type)
        } catch (ex) {
          throw ex
        }
        console.log(dataItem)
        return
      }
      if (name === 'DUYET_HS') {
        alert('Duyệt hồ sơ')
        return
      }
    },
    dialogClose() {},
    openDialog(data) {
      if (!data) {
        return
      }

      this.state = {
        hdtb_id: data.hdtb_id ? data.hdtb_id : -1,
        loaitb_id: data.loaitb_id ? data.loaitb_id : -1,
        batbuocXemToanBohoSo: data.batbuocXemToanBohoSo ? data.batbuocXemToanBohoSo : false,
        isToanTrinh: data.isToanTrinh ? data.isToanTrinh : false,
        onClose: data.onClose
      }
      console.log(this.state)
      this.$refs.dialogTraCuuHoSoHDTB.show()
      this.pageLoad()
    },
    hideDialog() {
      this.$refs.dialogTraCuuHoSoHDTB.hide()
    },

    async pageLoad() {
      try {
        this.loading(true)
        this.tt_nd.may_cn = await this.$root.token.getMachine()
        this.tt_nd.ip_cn = await this.$root.token.getIP()

        let dt = []
        if (this.state.isToanTrinh == true) {
          dt = this.GetData(await api.lay_ds_file_hdtb_duyet_toantrinh(this.axios, this.state.hdtb_id))
        } else {
          dt = this.GetData(await api.lay_ds_file_hdtb_v2(this.axios, this.state.hdtb_id))
        }
        if (dt && dt.length > 0) {
          this.controls.grvFileHS.list = dt.map((x) => {
            const uri = x.url
            const encoded = encodeURI(uri)
            x.tai_ve = `<a target="_blank" href="${encoded}">Tải về</a>`
            x.kq_ekyc = 'Chờ kiểm tra'
            return x
          })
        }

        let dsHoSoDaXem = []
        if (dt && dt.length > 0) {
          for (const row of dt) {
            dsHoSoDaXem.push(row['ten_file'])
          }
        }
        await this.lay_ds_ekyc()
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      this.loading(false);
    },
    async kiemtra_hoso_ekyc()
    {
     /* this.controls.grvFileHS.list = this.controls.grvFileHS.list.map(async (x) => {
        const uri = x.url
        const encoded = encodeURI(uri)
        if(x.loaifile_id == 3 || x.loaifile_id == 25)
        {
          let up_file = this.GetData(await api.tai_hoso_kiemtra_ekyc(this.axios, {
            url: encoded
          }));
          console.log(up_file);
        }
        console.log(x)
        return x
      })*/
      try {
        this.loading(true)
        let dl = [...this.controls.grvFileHS.list]

        for (let x of dl) {
          const uri = x.url
          const encoded = encodeURI(uri)
          if (x.loaifile_id == 3 || x.loaifile_id == 25) {
            try {
              let up_file = await api.tai_hoso_kiemtra_ekyc(this.axios, {
                url: encoded
              });
              if (up_file.data.data['object']['hash']) {
                if (x.loaifile_id == 3) {
                  let obj_gtt = {
                    file_hash: up_file.data.data['object']['hash'],
                    file_type: "pdf",
                    token: "e206e1ff-4df6-268d-e053-63199f0a3731",
                    client_sessio: "",
                    details: false
                  }
                  let kt_cccd = await api.kiemtra_giayto_tuy_than(this.axios, obj_gtt);
                  console.log(kt_cccd.data.data)
                  if (kt_cccd.data.data['object'].SO_CCCD_CMND || kt_cccd.data.data['object'].SO_HO_CHIEU) {
                    let str_kt = kt_cccd.data.data['object'].SO_CCCD_CMND + '.' + kt_cccd.data.data['object'].SO_HO_CHIEU
                    if (str_kt.includes(x.so_gt)) {
                      if(kt_cccd.data.data['object'].HO_VA_TEN)
                      {
                        if(kt_cccd.data.data['object'].HO_VA_TEN.trim().toUpperCase() == x.ten_tb.trim().toUpperCase())
                        {
                          x.kq_ekyc = `<span class='badge bg-success'>Số giấy tờ khớp</span>`
                        }
                        else {
                          x.kq_ekyc = `<span class='badge bg-danger'>Tên thuê bao </br> không khớp giấy tờ</span>`;
                        }
                      }
                      else {
                        x.kq_ekyc = `<span class='badge bg-success'>Số giấy tờ khớp</span>`
                      }

                    } else {
                      x.kq_ekyc = `<span class='badge bg-danger'>Số giấy tờ không khớp</span>`
                    }
                  } else {
                    x.kq_ekyc = `<span class='badge bg-warning'>Giấy tờ quá mờ</span>`
                  }
                }

                if (x.loaifile_id == 25) {
                  let obj_gtt = {
                    file_hash: up_file.data.data['object']['hash'],
                    file_type: "pdf",
                    token: "e206e1ff-4df6-268d-e053-63199f0a3731",
                    client_sessio: "",
                    details: false
                  }
                  let kt_cccd = await api.kiemtra_dkkd(this.axios, obj_gtt);
                  console.log(kt_cccd.data.data)
                  if (kt_cccd.data.data['object'].MA_SO_DOANH_NGHIEP) {
                    let str_kt = kt_cccd.data.data['object'].MA_SO_DOANH_NGHIEP
                    if (str_kt.includes(x.mst)) {
                      x.kq_ekyc = `<span class='badge bg-success'>MST tờ khớp</span>`
                    } else {
                      x.kq_ekyc = `<span class='badge bg-danger'>MST không khớp</span>`
                    }
                  } else {
                    x.kq_ekyc = `<span class='badge bg-warning'>Giấy tờ quá mờ</span>`
                  }
                }
              }
            } catch (ex) {
              x.kq_ekyc = "Kiểm tra thất bại"
            }
          }
          else
          {
            x.kq_ekyc = ''
          }

        }
        this.controls.grvFileHS.list = [...dl]
      }
      catch (exc)
      {

      }
      finally {
        this.loading(false)
      }


    },
    async lay_ds_ekyc() {
      let self = this
      let dt = await api.lay_ds_hoso_ekyc(this.axios, {
        vhdtb_id: self.state.hdtb_id
      })
      self.controls.grvFileHSEkyc.list = dt.data.data
    },
    async close() {
      if (this.state.onClose == true) {
        console.log('onClose')
        this.$emit('onClose', true)
      }
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200' || response.data.error === '204' || response.data.errorCode === 0) {
        return response.data.data
      } else {
        console.log(response.data.error_code)
        console.log(response.data.faultString)
        if (response.data && response.data.faultString) {
          throw response.data.faultString
        }
      }
      return null
    },

    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    },
    LowerCasePropertyList(obj) {
      if (!obj) return []
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toLowerCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    UpperCasePropertyList(obj) {
      if (!obj) return []
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
    }
  }
}
</script>

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>
