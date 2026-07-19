<template src="./index.html"></template>
<script>
import moment from 'moment'
import breadcrumb from '@/components/breadcrumb'
import gridview from '@/components/Shared/gridview'
import api from './index.js'
import state from './define.js'
import XLSX from "xlsx";

export default {
  components: {
    moment,
    breadcrumb,
    gridview
  },
  name: '',
  validations: {},
  data() {
    return {
      header: {
        title: 'Danh sách kho số CNTT',
        list: [
          { name: 'Danh mục', link: { name: 'Ui.cards' } },
          {
            name: 'Danh sách kho số CNTT',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      ...state
    }
  },
  created() {
    this.init()
    this.layDS()
  },
  watch: {
    'formTT.tiento' : function (value) { 
      if(value != '')
      this.formTT.somay = '';
    }
  },
  methods: {
    async init() {
      try {
        this.tagForm = this.$route.query.tag;
        console.log('Tag form : ', this.tagForm);
        this.SetButton(1)
        const response = await api.load_danhmuc_frm_KhoSoCNTT(this.axios, {tag: this.tagForm})
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.options.ds_tinh = response.data.data[0]
          this.options.ds_trangthaiso = response.data.data[1]
          this.options.ds_kieuso = response.data.data[2]
          this.options.ds_nhomso = response.data.data[3]
          this.options.ds_loaihinh_tb = response.data.data[4]
        }
      } catch (error) {
        console.log(error)
      }
    },
    async layDS() {
      try {
        const nguoidung_id = this.$root.token.getNguoiDungID();
        let loaitb_id = null;
        let kieuso_id = null;
        let nhomso_id = null;
        if(this.tagForm == 1) {
          loaitb_id = 358;
          kieuso_id = 326;
          nhomso_id = 215;
        }

        await api.lay_ds_khoso_cntt(this.axios, {somay: '', nguoidung_id, tinh_id: 0, loaitb_id: loaitb_id, kieuso_id: kieuso_id , nhomso_id: nhomso_id}).then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.gvDanhSach.tableData = response.data.data
          }
        })
      } catch (error) {
        console.log(error)
      }
    },
    selectItem(item) {
      if (!item || !item.data) return
      this.SetButton(3)
      let formdata = item.data
      this.formTT.phanvung_id = formdata.phanvung_id
      this.formTT.somay = formdata.somay
      this.formTT.trangthaiso_id = formdata.trangthaiso_id
      this.formTT.kieuso_id = formdata.kieuso_id
      this.formTT.loaitb_id = formdata.loaitb_id
      this.formTT.nhomso_id = formdata.nhomso_id
      this.formTT.ghichu = formdata.ghichu
    },
    async btnGhilai_Click() {
      if (!this.KiemTraDL()) return false
      let index = this.actions.findIndex((item) => item.id === 'nhapmoi')
      let isNew = this.actions[index].active == false
      if (isNew) this.capnhat(1)
      else this.capnhat(2)
    },
    async btnTaoDaiSo_Click() {
      this.capnhat(4)
    },
    async capnhat(loai) {
      this.loading(true)
      try {
        const p_data = {
          PHANVUNG_ID: this.formTT.phanvung_id,
          SOMAY: this.formTT.somay,
          TRANGTHAISO_ID: this.formTT.trangthaiso_id,
          KIEUSO_ID: this.formTT.kieuso_id,
          LOAITB_ID: this.formTT.loaitb_id,
          NHOMSO_ID: this.formTT.nhomso_id,
          GHICHU: this.formTT.ghichu,
          TIENTO: this.formTT.tiento,
          SO_BD: this.formTT.so_bd, 
          SO_KT: this.formTT.so_kt
        }

        let input = {
          ds_para: JSON.stringify({ MODE: loai, SOMAY: p_data })
        }
        await api.fn_ghilai_khoso_cntt(this.axios, input).then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.$toast.success('Cập nhật thông tin thành công.')
            this.layDS()
            this.loading(false)
          } else if (response && response.data && response.data.message) {
            this.$toast.error(response.data.message)
            this.loading(false)
          } else {
            this.$toast.error('Cập nhật thông tin thất bại.')
            this.loading(false)
          }
        })
        this.loading(false)
      } catch (ex) {
        this.loading(false)
        this.$toast.error('Có lỗi khi cập nhật thông tin!' + ex)
      }
    },
    async btnXoa_Click() {
      if (this.formTT.loainha_id == 0) {
        this.$toast.error('Vui lòng chọn dòng cần xóa!')
        return
      }
      if (!window.confirm('Bạn thật sự muốn xóa không ?')) {
        return
      }
      this.capnhat(3)
    },
    chooseFiles() {
      this.$refs.fileUpload.click()
    },

    onChange(event) {
        console.log(event)
      this.loading(true)
      try {
        this.file = event.target.files ? event.target.files[0] : null;

        if (this.file) {
          const reader = new FileReader();

          reader.onload = async (e) => {
       
            const bstr = e.target.result;
            const wb = XLSX.read(bstr, { type: 'binary' });
            const wsname = wb.SheetNames[0];
            const ws = wb.Sheets[wsname];
            const data = XLSX.utils.sheet_to_json(ws, 
            { header: ['SOMAY', 'TRANGTHAISO_ID', 'KIEUSO_ID', 'LOAITB_ID', 'NHOMSO_ID', 'GHICHU'] });
    
            data.map(async item => {
              if(item.SOMAY == 'SOMAY') return; //bo qua header
              console.log(item)
              let input = {
               ds_para: JSON.stringify({ MODE: 1, SOMAY: item })
              }
              await api.fn_ghilai_khoso_cntt(this.axios, input)
            })
            this.$toast.success("Import thành công")
            this.btnLayTT_Click()
          }
          reader.readAsBinaryString(this.file);
        }
      }catch(err) {
          console.log(err);
      }

      this.loading(false)
    },
    btnLayTT_Click() {
      this.SetButton(1)
      this.layDS()
    },
    onActionClick(action) {
      if (action.id === 'nhapmoi') {
        this.btnThemmoi_Click()
      } else if (action.id === 'ghilai') {
        this.btnGhilai_Click()
      } else if (action.id === 'xoa') {
        this.btnXoa_Click()
      } else if (action.id === 'laytt') {
        this.btnLayTT_Click()
      } else if (action.id === 'tao_giai_so') {
        this.btnTaoDaiSo_Click()
      } else if (action.id === 'import-file') {
        this.chooseFiles();
      }
    },
    setActiveButton(id, status) {
      let index = this.actions.findIndex((item) => item.id === id)
      if (index >= 0) {
        this.actions[index].active = status
      }
    },
    btnThemmoi_Click() {
      console.log('btnThemmoi_Click')
      this.SetButton(1)
    },
    SetButton(kieu) {
      this.setActiveButton('nhapmoi', false)
      this.setActiveButton('ghilai', false)
      this.setActiveButton('ghilai-all', false)
      this.setActiveButton('xoahd', false)
      if (kieu == -1) {
        //Bat dau
        this.setActiveButton('ghilai', true)
        this.setActiveButton('ghilai-all', true)
      } else if (kieu == 0) {
        //Bat dau
        this.setActiveButton('nhapmoi', true)
        this.ClearGUI()
      } else if (kieu == 1) {
        //Them moi
        $('#ma_hd').focus()
        this.setActiveButton('ghilai', true)
        this.setActiveButton('ghilai-all', true)
        this.ClearGUI()
      } else if (kieu == 2) {
        //Huy
        this.setActiveButton('nhapmoi', true)
        this.ClearGUI()
      } else if (kieu == 3) {
        //Edit
        this.setActiveButton('nhapmoi', true)
        this.setActiveButton('ghilai', true)
        this.setActiveButton('ghilai-all', true)
        this.setActiveButton('xoa', true)
      }
    },

    KiemTraDL() {
      // if (this.formTT.ten_loainha == null || this.formTT.ten_loainha == '') {
      //   this.$toast.error('Hãy nhập tên loại nhà!')
      //   $('#ten_loainha').focus()
      //   return false
      // }
      return true
    },
    ClearGUI() {
      this.formTT = {
        somay: '',
        trangthaiso_id: 0,
        kieuso_id: 0,
        loaitb_id: 0,
        nhomso_id: 0,
        ghichu: ''
      }
    }
  }
}
</script>

<style scoped>
.input-icon-right.disabled input {
  cursor: default;
  pointer-events: none;
  text-decoration: none !important;
  color: grey !important;
}
</style>
