<template>
  <div v-bind:class="[ispopup ? popupClass.popup : popupClass.nopopup]">
    <div class="breadcrumb-top">
      <div class="main-title">TRA cứu HỒ SƠ ĐIỆN TỬ</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a>
        </li>
        <li class="breadcrumb-item"><a href="#">Tra cứu</a></li>
        <li class="breadcrumb-item active">Tra cứu hồ sơ điện tử</li>
      </ul>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="TraCuu"> <span class="icon one-file-search"></span>Tra cứu </a>
        </li>
        <li>
          <a href="#" @click.prevent="XacNhan_Click"> <span class="icon one-file-search"></span>Xác nhận </a>
        </li>
        <li>
          <a href="#" @click.prevent="XemChiTiet"> <span class="icon nc-icon-glyph arrows-1_simple-down"></span>Xem CT </a>
        </li>
        <li>
          <a href="#" @click.prevent="ThuLai"> <span class="icon nc-icon-glyph arrows-1_simple-left"></span>Thu lại </a>
        </li>
      </ul>
    </div>
    <div v-bind:class="{ 'page-content': !ispopup, 'page-content-popup': ispopup }">
      <div class="box-form">
        <div class="legend-title">Thông tin tra cứu</div>
        <div class="input-more-button">
          <button class="btn" @click="PopUpTraCuu">
            <span class="-ap icon-more_horiz"></span>
          </button>
          <input type="text" class="form-control highlight" ref="refMaTb" v-model.trim="MaTb" v-on:keyup.enter="TraCuu" />
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">File hồ sơ</div>
        <gridView :headers="DsFile.headers" :datalist="DsFile.treeData2" :flcol="DsFile.flcol" :showCheck="true" :showHeader="true" @recordChange="recordChange" @viewFileEvent="viewFileEvent" @dowloadFileEvent="dowloadFileEvent">
          <div slot="nodata"><p></p></div>
        </gridView>
      </div>
    </div>
    <b-modal id="my-modal1" class="modal-dialog" size="lg" hide-footer :no-close-on-backdrop="true">
      <div slot="modal-title" class="popup-header">
        <div class="title">Xem trước nội dung file</div>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title mart0">
            <div v-if="needToBase64">
              <div v-if="extFile === 'pdf'"></div>
              <div v-else-if="extFile === 'image'">
                <img :src="base64Data" style="width: 1100px; min-height: 500px; min-width: 1100px;scroll-behavior: auto;" />
              </div>
            </div>
            <div v-else>
              <div v-if="extFile === 'pdf'">
                <iframe :src="previewFile" style="width: 1100px; min-height: 500px; min-width: 1100px" />
              </div>
              <div v-else-if="extFile === 'image'" style=" max-height: 800px;overflow: auto; ">
                <img :src="previewFile" style="width: 100%;width: auto;" />
                <!-- <iframe :src="previewFile" style="width: 1100px; min-height: 500px; min-width: 1100px" /> -->
                <!-- <img :src="previewFile" style="width: 1100px; min-height: 500px; min-width: 1100px"/> -->
              </div>
              <div v-else>
                <h2>Không hỗ trợ xem trước</h2>
                <a download :href="previewFile">Tải về </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </b-modal>
    <b-modal id="my-modal-search-account" class="modal-dialog" size="lg" hide-header hide-footer :no-close-on-backdrop="true">
      <!-- <div slot="modal-title" class="popup-header">
        <div class="title">Thông tin tra cứu</div>
      </div> -->
      <div style="min-height: 650px">
        <searchAccount :isPopup="true" @form-close="selectedAccount"></searchAccount>
      </div>
    </b-modal>
  </div>
</template>
<script>
import searchAccount from '@/modules/search/subscriber/SearchAccount/SearchAccount.vue'
import gridView from '@/modules/complaint/ResovleComplaintAfterSales/components/gridviewhs.vue'
import api from './Api'
export default {
  components: {
    searchAccount,
    gridView,
    api
  },
  name: 'SearchEProfile',
  created() {
    try {
      if (this.matb != null && this.matb != '') {
        this.MaTb = this.matb
      }
      if (this.$route.query.matb != null && this.$route.query.matb && this.$route.query.matb.length > 0) {
        this.MaTb = this.$route.query.matb
      }
      if (this.$route.params.matb != null && this.$route.params.matb && this.$route.params.matb.length > 0) {
        this.MaTb = this.$route.params.matb
      }
    } catch (err) {}
  },
  async mounted() {
    // if (this.MaTb != '') {
    //   setTimeout(await this.TraCuu(), 5000)
    // }
  },
  watch: {
    matb: function(val) {
      this.MaTb = val
    },
    Loading: function(val, oldval) {
      this.$root.showLoading(val)
    }
  },
  props: {
    matb: {
      type: String,
      default: ''
    },
    ispopup: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      header: {
        title: 'Tra cứu',
        list: [
          {
            name: 'TRA cứu HỒ SƠ ĐIỆN TỬ',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      popupClass: {
        popup: 'main-wrapper1',
        nopopup: 'main-wrapper'
      },
      Loading: false,
      dateFormat: 'DD/MM/YYYY',
      typeFormat: 'date',
      MaTb: '',
      selectedRow: false,
      CurrentItem: {},
      popupComponent: null,
      // selectedRow: {},
      DsFile: {
        headers: [
          { name: 'name', text: 'Tên File', value: '' },
          { name: 'loai_file', text: 'Loại File', value: '' },
          { name: 'nguoi_th', text: 'Người TH', value: '' },
          { name: 'ngay_cn', text: 'Ngày TH', value: '' },
          { name: 'loai_hd', text: 'Loại HĐ', value: '' }
        ],
        data: [],
        treeData: [],
        treeData2: []
      },
      previewFile: '',
      previewFileBase64: '',
      extFile: '',
      proxyUrl: 'https://api-onebss.vnpt.vn/tichhop/util/download?fileType=base64',
      needToBase64: false,
      base64Data: ''
    }
  },
  methods: {
    getDsFileTheoMaTb: async function() {
      try {
        var input = { dichvuvt_id: -1, ma_tb: this.MaTb }
        let data = this.GetData(await api.getDsFileTheoMaTb(this.axios, input))
        console.log('getDsFileTheoMaTb', data)
        if (data && data.length > 0) {
          this.DsFile.data = data
          this.DsFile.treeData = this.list_to_tree(this.DsFile.data)
          console.log('🚀this.DsFile.treeData', this.DsFile.treeData)
          this.DsFile.treeData2 = this.convertTreeToList(this.DsFile.treeData)
          console.log('🚀this.DsFile.treeData2', this.DsFile.treeData2)
        } else {
          this.ShowAlert(`Không tìm thấy hồ sơ tài liệu của thuê bao ${this.MaTb}`)
          console.log(`Không tìm thấy hồ sơ tài liệu của thuê bao ${this.MaTb}`)
        }
      } catch (err) {
        console.log('🚀 ~ file: index.vue ~ line 177 ~ err', err)
      }
    },
    traCuuHoSo: async function(matb) {
      var input = { ma_tk: this.MaTb }
      return this.GetData(await api.traCuuHoSo(this.axios, input))
    },
    TraCuu: async function() {
      if (this.MaTb == '') {
        this.ShowAlert(`Bạn chưa nhập mã thuê bao cần tìm kiếm `)
        this.$refs.refMaTb.focus()
        return
      }
      this.Loading = true
      try {
        await this.getDsFileTheoMaTb()
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    list_to_tree(list) {
      var map = {}
      var node = {}
      var roots = []
      var i = 0
      let parentsless = []
      for (i = 0; i < list.length; i += 1) {
        map[list[i].id] = i // initialize the map
        list[i].children = [] // initialize the children
        let notexitParent = list.filter((x) => x.id == list[i].cha_id).length == 0
        if (notexitParent) {
          parentsless.push(list[i])
        }
      }
      if (parentsless.length > 0) {
        let otherFolder = {
          id: parentsless[0].cha_id,
          cha_id: null,
          name: 'Hồ sơ ',
          url: null,
          ghichu: null,
          loai_file: null,
          trangthai_hs: null,
          isfile: -1,
          hs: parentsless.length,
          hoso_id: 0,
          nguoi_th: null,
          ngay_cn: null,
          children: parentsless
        }
        list.push(otherFolder)
      }
      for (i = 0; i < list.length; i += 1) {
        node = list[i]
        if (node.cha_id !== null) {
          try {
            // if you have dangling branches check that map[node.parentId] exists
            if (map[node.cha_id] != null && list[map[node.cha_id]].children == null) list[map[node.cha_id]].children = []
            list[map[node.cha_id]].children.push(node)
          } catch (err) {
            console.log('🚀 ~ file: index.vue ~ line 215 ~ list_to_tree ~ err', err)
            console.log('node:khong tim thay cha', node, map[node.cha_id])
          }
        } else {
          roots.push(node)
        }
      }
      return roots
    },
    //Start Thitv.hue add button
    XacNhan_Click() {
      this.$emit('XacnhanHoso', this.CurrentItem)
    },
    //End Thitv.hue add button
    convertTreeToList(arr) {
      var array = []
      var hashMap = {}
      var stack = []
      var level = 1
      for (const root of arr.filter((x) => x.cha_id == null)) {
        stack.push(root)
        level = 1
        while (stack.length !== 0) {
          var node = stack.pop()
          this.visitNode(node, hashMap, array, level)
          if (node.children === null || node.children.length === 0) {
            // this.visitNode(node, hashMap, array)
          } else {
            for (var i = node.children.length - 1; i >= 0; i--) {
              level = level + 1
              stack.push(node.children[i])
            }
          }
        }
      }
      return array
    },
    visitNode: function(node, hashMap, array, level) {
      if (!hashMap[node.id]) {
        hashMap[node.id] = true
        node.level = level
        node.display = true
        array.push(node)
      }
    },
    XemChiTiet: function() {
      if (this.selectedRow) {
        this.$emit('displayEvent', this.CurrentItem, true)
      }
    },
    ThuLai: function() {
      if (this.selectedRow) {
        this.$emit('displayEvent', this.CurrentItem, false)
      }
    },
    PopUpTraCuu: function() {
      this.$bvModal.show('my-modal-search-account')
    },
    recordChange: function(item) {
      this.selectedRow = true
      this.CurrentItem = item
      this.$emit('recordChange', item)
    },
    viewFileEvent: async function(item) {
      console.log(item)

      if (item == null) return

      this.previewFile = item.full_url
      var ext = item.url.split('.').pop()

      if (ext === 'pdf') {
        this.extFile = 'pdf'
      } else if (item.url.match(/.(jpg|jpeg|png|gif)$/i)) {
        this.extFile = 'image'
      } else if (ext === 'xlsx') {
        this.extFile = 'xlsx'
      } else {
        this.extFile = 'other'
      }
      if (item.full_url.indexOf('//10.70') > 0) {
        this.needToBase64 = true
        let base64 = await this.getFile(item.full_url)

        if (base64 == null || base64 == '' || this.extFile == 'xlsx') {
          this.ShowError('Không hỗ trợ xem trước !. Vui lòng thực hiện tải về ')
          return
        }

        if (this.extFile == 'image') {
          this.base64Data = `data:image/png;base64,${base64}`
        } else if (this.extFile == 'pdf') {
          this.base64Data = `data:application/pdf;base64, ${base64}`
          let pdfWindow = window.open('')
          pdfWindow.document.write("<iframe width='100%' height='100%' src='data:application/pdf;base64, " + encodeURI(base64) + "'></iframe>")
          return
        } else {
          this.needToBase64 = false
          this.base64Data = null
          this.extFile = 'other'
        }
      } else {
        this.needToBase64 = false
        this.base64Data = null
      }

      this.$bvModal.show('my-modal1')
    },
    dowloadFileEvent: async function(item) {
      /* var contentType = 'application/vnd.ms-excel';
          var blob1 = this.b64toBlob(base64, contentType);
          var blobUrl1 = URL.createObjectURL(blob1);
          window.open(blobUrl1, 'FileDocument.xlsx'); */

      /* var link = document.createElement('a');
      link.href = 'data:application/vnd.ms-excel;base64,' + base64;
      link.download = 'FileDocument.xlsx';
      link.dispatchEvent(new MouseEvent('click')); */

      //window.location.href = 'data:application/vnd.ms-excel;base64,' + base64;

      console.log(item)
      if (item.full_url == null) return

      var filename = item.full_url.replace(/^.*[\\\/]/, '')

      if (item.full_url.indexOf('//10.70') > 0) {
        let ext = item.full_url.split('.').pop()

        let base64 = await this.getFile(item.full_url)

        if (base64 == null || base64 == '') return

        let link = document.createElement('a');
        link.download = filename;

        if (item.url.match(/.(jpg|jpeg|png|gif)$/i)) {
          link.href = `data:image/png;base64,${base64}`
        } else if (ext === 'pdf') {
          link.href = `data:application/pdf;base64, ${base64}`
        } else if (ext === 'xlsx') {
          link.href = 'data:application/vnd.ms-excel;base64,' + base64;
        }

        link.dispatchEvent(new MouseEvent('click'));

      } else {
        window.open(item.full_url,"_blank");
      }

      return
    },
    selectedAccount: async function(account) {
      this.$bvModal.hide('my-modal-search-account')
      console.log('selectedAccount:', account)
      this.MaTb = account.ma_tb
      if (this.MaTb.length > 0) {
        this.Loading = true
        try {
          await this.getDsFileTheoMaTb()
          this.Loading = false
        } catch (ex) {
          this.Loading = false
        }
      }
    },
    base64ToArrayBuffer(base64) {
      let binaryString = window.atob(base64)
      let len = binaryString.length
      let bytes = new Uint8Array(len)
      for (let i = 0; i < len; i++) {
        bytes[i] = binaryString.charCodeAt(i)
      }
      return bytes.buffer
    },
    GetData: function(response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    async getFile(fileUrl) {
      try {
        this.Loading = true
        console.log(fileUrl)
        if(fileUrl.includes('http://10.70.52.164:8081/bbntsmca_'))
        {
          console.log("down ekyc")
          let url = fileUrl.replace('http://10.70.52.164:8081/bbntsmca_', '')
          let kq = await  api.tai_file_ekyc(this.axios,{
            StrRequest: url
          });
          // let kq = axios2.post(`http://10.70.52.244:6061/api/SmartCA/DownFileEKYC`, {
          //   StrRequest: "/ekyc_images/upload/017089003196/20230224000302rear_preview.jpg"
          // });
          console.log(kq.data.data.dulieu);
       //   const byteCharacters = this.b64toBlob(kq.data.dulieu, kq.data.data.mime_type);

        //  var filename = dataItem.url.substring(dataItem.url.lastIndexOf('/')+1);
          return kq.data.data.dulieu?kq.data.data.dulieu:'';
          console.log(kq.data);
        }
        else
        {
          var input = { dataUrl: encodeURIComponent(fileUrl) }
          const response = await api.getFileBase64(this.axios, input)
          this.Loading = false
          // if (response == null || response.data == null || response.data.error_code == 'BSS-00000000') {
          //   //this.ShowError('Không hỗ trợ xem trước !. Vui lòng thực hiện tải về ')
          //   return ''
          // }
          if (!response || !response.data || response.data.error_code) {
            //this.ShowError('Không hỗ trợ xem trước !. Vui lòng thực hiện tải về ')
            console.log('Loi xem truoc file: '+response.data.message+ response.data.message_detail);
            return ''
          }
          return response.data?response.data:''
        }

      } catch (err) {
        console.log('~ getFile ~ err:', err)
        this.Loading = false
        //this.ShowError('Không hỗ trợ xem trước !. Vui lòng thực hiện tải về ')
        return ''
      }
      finally {
        this.Loading = false
      }
    },

    ShowAlert: function(message) {
      this.$toast.error(message)
    },
    ShowError: function(message) {
      this.$toast.error(message)
    },
    ShowSuccess: function(message) {
      this.$toast.success(message)
    }
  }
}
</script>
<style>
.mx-datepicker {
  width: 100% !important;
}
.mx-icon-calendar {
  font-style: normal !important;
}
.modal-dialog {
  min-width: 1200px;
}
.main-wrapper1 {
  left: 0px;
  right: 0px;
  bottom: 0px;
}
.main-wrapper1 .breadcrumb-top {
  display: none;
}
.breadcrumb-top .breadcrumb {
  position: absolute;
  right: 15px;
}
</style>
