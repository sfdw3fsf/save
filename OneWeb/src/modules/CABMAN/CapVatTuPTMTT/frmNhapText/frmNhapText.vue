<template src='./frmNhapText.html'></template>
<script>
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import select2 from '@/components/Select2.vue'
import { L10n } from '@syncfusion/ej2-base'

L10n.load({
    'vi-VN': {
        grid: {
          EmptyRecord: 'Không có bản ghi nào để hiển thị'
        }
    }
})

var DialogResultObj = {
    None: 0,
    OK: 1,
    Cancel: 2,
    Abort: 3,
    Retry: 4,
    Ignore: 5,
    Yes: 6,
    No: 7,
}

export default {

  components: {breadcrumb, appSelect2: select2 },
  name: "frmNhapText",
  mounted() {
  },
  provide: {
  },
  computed: {
    Output: {
      get() {
          console.log(this.params.txtNhapText)
        return this.params.txtNhapText
      },
    },
    DateOutput: {
      get() {
          console.log(this.params.dtpNgay)
        return this.params.dtpNgay
      },
    },
    CboOutPut: {
      get() {
          console.log(parseInt(this.params.p_VatTu))
        return parseInt(this.params.p_VatTu) || 0
      },
    },
    CboOutPut_Ten: {
      get() {
          console.log(this.$refs.cboVatTu.getDataByValue(this.params.p_VatTu).TEN_VT)
        return this.$refs.cboVatTu.getDataByValue(this.params.p_VatTu).TEN_VT || ''
      },
    },
    SpinOutput: {
      get() {
          console.log(this.params.spMuc)
        return parseInt(this.params.spMuc)
      },
    },
    vkieu_luachon: {
      get() {
          console.log(this.params.rdioCKb)
        return parseInt(this.params.rdioCKb)
      },
    },
    
  },
  data() {
    return {
        DialogResult: 0,
        cboVatTu: [],

        params: {
            txtNhapText: '',
            dtpNgay: new Date(),
            p_VatTu: '',
            rdioCKb: '',
            spMuc: ''
        },
        Visible: {
            lblTieuDe2: true,
            dtpNgay: true,
            cboVatTu: true,
            spMuc: true,
            txtTenVT: true,
            rdioCKb: true,
            lblCanhBao: false,
            txtNhapText: true,
            btnHuyThat: false
        },
        Text: {
            lblTieuDe: '',
            lblTieuDe2: '',
            title: '',
            btnHuyBo:"Hủy bỏ",
            btnXacNhan:"Xác nhận",
            btnHuyThat:"Bỏ qua"
        },
        // bổ sung
        // fields: {value:'VATTU_ID', text: 'TEN_VT'}
        functionName: ''

    
    };
  },
  methods: {
    
    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    // Nhập text
    async frmNhapText(tieuDe){
        this.Text.lblTieuDe = tieuDe
        this.Visible.lblTieuDe2 = false
        this.Visible.dtpNgay = false
        this.Visible.cboVatTu = false
        this.Visible.spMuc = false
        this.Visible.txtTenVT = false
        this.Visible.rdioCKb = false
    },
    // Nhập nội dung chứng từ
    async frmNhapText_2(tieuDe, tieuDe2){
        this.Text.lblTieuDe = tieuDe
        this.Text.lblTieuDe2 = tieuDe2
        this.Visible.cboVatTu = false
        this.Visible.spMuc = false
        this.Visible.txtTenVT = false
        this.Visible.rdioCKb = false
        console.log(this.Text.lblTieuDe)
    },
    // Lựa chọn xóa chứng từ
    async frmNhapText_3(tieuDe, x){
        this.Text.lblTieuDe = tieuDe
        this.Visible.lblTieuDe2 = false
        this.Visible.dtpNgay = false
        this.Visible.cboVatTu = false
        this.Visible.spMuc = false
        this.Visible.txtTenVT = false
        this.Visible.rdioCKb = true
        this.Visible.txtNhapText = false
    },
    // Nhập serial thu hồi
    async frmNhapText_4(tieuDe, tieuDe2, dt){
        this.Text.lblTieuDe = tieuDe
        this.Text.lblTieuDe2 = tieuDe2
        this.Visible.cboVatTu = true
        this.Visible.dtpNgay = false
        this.Visible.spMuc = false
        this.Visible.txtTenVT = false
        this.Visible.rdioCKb = false

        this.cboVatTu = dt


    },
    //Thay txtNhapText=1 control khac
    AddCustomControl(){

    },
    async btnXacNhan_Click(){
        if (!this.params.p_txtNhapText && this.Visible.spMuc == false){
            this.DialogResult = DialogResultObj.OK
            await this.$parent.$parent.XacNhan_frmNhapText()
            this.Close()
        }
        else if (this.Visible.spMuc){
            this.DialogResult = DialogResultObj.OK
            await this.$parent.$parent.XacNhan_frmNhapText()
            this.Close()
        }
        else if (this.Visible.rdioCKb){
            this.DialogResult = DialogResultObj.OK
            await this.$parent.$parent.XacNhan_frmNhapText()
            this.Close()
        }
        else if(!this.Visible.txtNhapText && !this.Visible.spMuc && this.Visible.dtpNgay && !this.Visible.txtTenVT ){
            this.DialogResult = DialogResultObj.OK
            await this.$parent.$parent.XacNhan_frmNhapText()
            this.Close()
        }
        else{
           this.Visible.lblCanhBao = true; 
        }
    },
    async btnHuyBo_Click(){
        this.DialogResult = DialogResultObj.Cancel
        await this.$parent.$parent.XacNhan_frmNhapText()
        this.Close()
    },
    async tmrAnCanhBao_Tick(){
        this.Visible.lblCanhBao = false
    },
    async btnHuyThat_Click(){
        this.DialogResult = DialogResultObj.Abort
        await this.$parent.$parent.XacNhan_frmNhapText()
        this.Close()
    },
    async Load_frmNhapText(){
        this.params.txtNhapText = ''
        this.params.dtpNgay = new Date()
        this.cboVatTu = []
        this.params.p_VatTu = ''
        this.params.rdioCKb = ''
        this.params.spMuc = ''
        this.Visible.lblTieuDe2 = true
        this.Visible.dtpNgay = true
        this.Visible.cboVatTu = true
        this.Visible.spMuc = true
        this.Visible.txtTenVT = true
        this.Visible.rdioCKb = true
        this.Visible.lblCanhBao = false
        this.Visible.txtNhapText = true
    },
    Close(){
        this.$parent.hide()
        // this.$destroy()
    }

  },
created: async function() {
    await this.Load_frmNhapText()
  },
  watch: {
    
  },
  destroyed () {
  }
};
</script>
<style>
</style>
