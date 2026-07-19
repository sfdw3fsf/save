<template src="./index.template.html"></template>
<script>
import {maxLength, numeric, required} from "vuelidate/lib/validators";
import BssRequiredMarker from '@/components/BssRequiredMarker'
import $ from 'jquery'

export default {
  components: { BssRequiredMarker },
  data: function () {
    return {
      dsDoiTuongThuocTinh :[],
      dsBang :[],
      dsCachNhapDl :[],
      dsKieuDl: [],
      tenBang:'',
      idCachNhap:'',
      idKieuDl:'',
      options: [
        { maOption: 0, content: 'Không' },
        { maOption: 1, content: 'Có' },
      ],
      maAllowNull:'',
      maUnique:'',
      tenTruong:'',
      tenTruongOld:'',
      moTa:'',
      kichThuoc:'',
      viTri:'',
      tu:'',
      den:'',
      truongPhuThuoc:'',
      bangCha:'',
      truongKhoa:'',
      truongHienThi:'',
      tuyChon:'',
      lenhSql:'',
      doDaiKhoa:'',
      buttonState: {
        btnNhapMoiEnabled: false,
        btnCapNhatEnabled: false,
        btnXoaEnabled: false,
        btnHuyBoEnabled: false,
      },
      modalXoa: false,
      indexFocus: 0
    };
  },validations: {
    tenTruong: {
      required,
      maxlength: maxLength(30)

    }, moTa: {
      required,
      maxlength: maxLength(50)
    }, kichThuoc: {
      maxlength: maxLength(4),
      required,numeric
    }, viTri: {
      numeric,
      maxlength: maxLength(3),
      required
    }, tuyChon: {
      numeric,
      required
    }
  },watch:{
    'tenBang':async function (val) {
      this.selectedRow = 0
      if (val) {
        await this.getDsDoiTuongTT(val);
      }
    },'tenTruong':async function (val) {
      if (val) {
      }
    }
  },
  created: async function () {
    this.getBang();
    this.getCachNhap();
    this.getKieu();
  },async mounted () {
    $("span.logout button").attr('tabindex',1);
    $("ul.breadcrumb li:first a").attr('tabindex',2);
    $("ul.breadcrumb li:nth-child(2) a").attr('tabindex',3);
    $("#FIELD_NAME_filterBarcell").attr('tabindex',26);

    $(".e-keyboard").attr('tabindex',-1);

    setTimeout(function() {
      document.getElementById("select2--container").classList.add("my-class");
      $("span.select2-selection--single").attr('tabindex',-1);
    }, 1000)

    // $("span.select2-selection").attr('tabindex',-1);
    // $("ul.breadcrumb li:sec").attr('tabindex',1);
    this.$refs.tenTruong.focus()
  },
  methods: {
    clearForm () {
      this.enableBtn(true,false,false,true)
      this.$v.$reset()
      this.tenTruong = '';
      this.moTa = '';
      this.tu = '';
      this.den ='';
      this.kichThuoc = '';
      this.viTri = '';
      this.idKieuDl = 0;
      this.doDaiKhoa = '';
      this.bangCha ='';
      this.truongHienThi = '';
      this.truongKhoa = '';
      this.truongPhuThuoc = '';
      this.maAllowNull = 0;
      this.idCachNhap = 0;
      this.tuyChon = 10000;
      this.maUnique = 0;
      this.lenhSql = '';
    },cancelFunc: function () {
      this.enableBtn(false,true,true,true)
      this.tenTruong = '';
      this.moTa = '';
      this.tu = '';
      this.den ='';
      this.kichThuoc = '';
      this.viTri = '';
      this.idKieuDl = 0;
      this.doDaiKhoa = '';
      this.bangCha ='';
      this.truongHienThi = '';
      this.truongKhoa = '';
      this.truongPhuThuoc = '';
      this.maAllowNull = 0;
      this.idCachNhap = 0;
      this.maUnique = 0;
      this.lenhSql = '';
    },validateData(data){
      this.enableBtn(true,false,false,true)
      this.$v.$touch();
      let errorMessage = []

      if(this.$v.kichThuoc.$error){
        this.$refs.kichThuoc.focus()
        if(!this.kichThuoc){
          errorMessage.unshift("Trường kích thước bắt buộc nhập dữ liệu.")

        }else if(this.kichThuoc >9999){
          errorMessage.unshift("Trường kích thước dữ liệu vượt quá độ dài cho phép.")
        }
      }

      if(this.$v.tuyChon.$error){
        this.$refs.tuyChon.focus()
        if(!this.tuyChon){
          errorMessage.unshift("Trường tuỳ chọn bắt buộc nhập dữ liệu.")}
        else if(typeof this.tuyChon !== numeric){
          errorMessage.unshift("Trường tuỳ chọn chỉ được phép nhập số.")
        }
      }

      if(this.$v.viTri.$error){
        this.$refs.viTri.focus()
        if(!this.viTri){

          errorMessage.unshift("Trường vị trí bắt buộc nhập dữ liệu.")}
        else if(this.viTri >999){
          errorMessage.unshift("Trường vị trí dữ liệu vượt quá độ dài cho phép.")
        }
      }
      if(this.$v.moTa.$error){
        this.$refs.moTa.focus()
        if(!this.moTa) {
          errorMessage.unshift("Trường mô tả bắt buộc nhập dữ liệu.")
        }else if(this.moTa.length > 50){
          errorMessage.unshift("Trường mô tả dữ liệu vượt quá độ dài cho phép.")
        }
      }

      if (this.$v.tenTruong.$error) {
        this.$refs.tenTruong.focus()
        if(!this.tenTruong){
          errorMessage.unshift("Trường tên trường bắt buộc nhập dữ liệu.")
        }else if(this.tenTruong.length > 30){
          errorMessage.unshift("Trường tên trường dữ liệu vượt quá độ dài cho phép.")
        }
      }

      if(this.truongPhuThuoc &&this.truongPhuThuoc.length > 30){
        errorMessage.unshift("Trường trường phụ thuộc dữ liệu vượt quá độ dài cho phép.")
      }

      if(this.den < this.tu){
        errorMessage.unshift("Giá trị trường miền giá trị không được lớn hơn giá trị trường đến.")
      }



      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
        return true
      }else {
        return false
      }
    },enableBtn : function (nhapMoi,capNhat,huy,xoa){
      this.buttonState.btnNhapMoiEnabled = nhapMoi
      this.buttonState.btnCapNhatEnabled = capNhat
      this.buttonState.btnHuyBoEnabled = huy
      this.buttonState.btnXoaEnabled = xoa
    },
    async grid1_selectedRowChanged(dataItem){
      try {
        this.enableBtn(false, false, false, false)
        if(dataItem){
          this.tenTruong = dataItem.FIELD_NAME;
          this.tenTruongOld = dataItem.FIELD_NAME;
          this.moTa = dataItem.CAPTION;
          this.idKieuDl = dataItem.DATA_TYPE;
          this.kichThuoc = dataItem.FIELD_LENGTH;
          this.viTri = dataItem.FIELD_POSITION;
          this.tu = dataItem.MIN_VALUE;
          this.den = dataItem.MAX_VALUE;
          this.truongPhuThuoc = dataItem.DEPEND_FIELD;
          this.bangCha = dataItem.PARENT_TABLE;
          this.truongKhoa = dataItem.PARENT_KEYFIELD;
          this.truongHienThi = dataItem.PARENT_DESCFIELD;
          this.maAllowNull = dataItem.FIELD_NULLABLE;
          this.tuyChon = dataItem.OPT;
          this.maUnique = dataItem.FIELD_UNIQUE;
          this.idCachNhap = dataItem.ATT;
          this.lenhSql = dataItem.LOOKUP_SQL;
        }
      } catch (err) {
        console.log(err)
      } finally {
      }
    },
    saveAs: async function () {
      let data = {
        field_name : this.tenTruongOld,
        field_name_new : this.tenTruong,
        caption:  this.moTa,
        field_length : this.kichThuoc,
        data_type: this.idKieuDl,
        table_name : this.tenBang,
        field_position :this.viTri ,
        min_value : this.tu ,
        max_value: this.den ,
        depend_field: this.truongPhuThuoc,
        parent_table :this.bangCha,
        parent_keyfield :this.truongKhoa ,
        parent_descfield : this.truongHienThi,
        field_nullable :this.maAllowNull,
        opt: this.tuyChon,
        field_unique: this.maUnique,
        att : this.idCachNhap ,
        lookup_sql : this.lenhSql,
      }

      if(!this.buttonState.btnCapNhatEnabled ){
        if (this.buttonState.btnNhapMoiEnabled) {
          await this.themmoiTT(data)
        } else {
          await this.capNhatTT(data)
        }

        this.enableBtn(false, false, false, false)
      }





    },themmoiTT: async function () {
      try {
        let data = {
          field_name : this.tenTruong,
          field_name_new : this.tenTruong,
          caption:  this.moTa,
          field_length : this.kichThuoc,
          data_type: this.idKieuDl,
          table_name : this.tenBang,
          field_position :this.viTri ,
          min_value : this.tu ,
          max_value: this.den ,
          depend_field: this.truongPhuThuoc,
          parent_table :this.bangCha,
          parent_keyfield :this.truongKhoa ,
          parent_descfield : this.truongHienThi,
          field_nullable :this.maAllowNull,
          opt: this.tuyChon,
          field_unique: this.maUnique,
          att : this.idCachNhap ,
          lookup_sql : this.lenhSql,
        }
        if(this.validateData(data)){
          return
        }
        this.loading(true)
        let rs = await this.$root.context.post(
          '/web-cabman/doituongthuoctinh/tao', data
        )
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success('Thêm mới thành công.')
          this.$v.$reset()
          await this.getDsDoiTuongTT(this.tenBang);
        } else {
          this.$toast.error('Có lỗi xảy ra.')
        }
      } catch (error) {
        let message = error.response.data.message
        let message_detail = error.response.data.message_detail
        if(message_detail){

          if(message_detail.includes('Đã tồn lại đối tượng thuộc tính này')){
            this.$toast.error('Trường tên trường đã tồn tại, vui lòng nhập lại.')
            this.$refs.tenTruong.focus()
          }else {
            this.$toast.error(message_detail)
          }
          return
        }
        if (message !== null) {
          this.$toast.error(message)
        }
      } finally {
        this.loading(false)
      }
    },capNhatTT: async function (data) {
      try {
        if(this.validateData(data)){
          return
        }

        this.loading(true)
        let rs = await this.$root.context.post(
          '/web-cabman/doituongthuoctinh/capnhat',data);
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success('Cập nhật thành công.')
          this.$v.$reset()
          await this.getDsDoiTuongTT(this.tenBang);
        } else {
          this.$toast.error('Có lỗi xảy ra.')
        }
      } catch (error) {
        let message = error.response.data.message
        if (message !== null) {
          this.$toast.error(message)
        }
      } finally {
        this.loading(false)
      }
    },
    xoaDTTT: function () {
      if (!this.buttonState.btnXoaEnabled) {
        this.modalXoa = !this.modalXoa
      }
    },
    async xacNhanXoaDTTT() {
      try {
        this.enableBtn(false, true, true, true)

        let data = {
          field_name : this.tenTruong,
          table_name : this.tenBang
        }
        let rs = await this.$root.context.post(
          '/web-cabman/doituongthuoctinh/xoa',data);
        if (rs.error_code === 'BSS-00000000') {
          this.clearForm()
          this.$toast.success('Xoá dữ liệu thành công.')
          await this.getDsDoiTuongTT(this.tenBang);
        } else {
          this.$toast.error('Có lỗi xảy ra.')
        }
      } catch (error) {
        let message = error.response.data.message
        if (message !== null) {
          this.$toast.error(message)
        }
      } finally {
        this.loading(false)
      }
    },
    getDsDoiTuongTT: async function (ten_bang) {
      try {
        this.loading(true)
        var rs = await this.$root.context.get(
          `/web-cabman/doituongthuoctinh/danhsach?ten_bang=${ten_bang}`
        );
        this.dsDoiTuongThuocTinh = rs.data;
        if(rs.data){
        }
      } catch (error) {
        let message = err.response.data.message
        if (message !== null) {
          this.$toast.error(message)
        }
      } finally {
        this.loading(false)
      }
    },setDoiTuong: function (dataItem){
      if(dataItem){

        this.tenTruong = dataItem.FIELD_NAME;
        this.moTa = dataItem.CAPTION;
        this.idKieuDl = dataItem.DATA_TYPE;
        this.kichThuoc = dataItem.FIELD_LENGTH;
        this.viTri = dataItem.FIELD_POSITION;
        this.tu = dataItem.MIN_VALUE;
        this.den = dataItem.MAX_VALUE;
        this.truongPhuThuoc = dataItem.DEPEND_FIELD;
        this.bangCha = dataItem.PARENT_TABLE;
        this.truongKhoa = dataItem.PARENT_KEYFIELD;
        this.truongHienThi = dataItem.PARENT_DESCFIELD;
        this.maAllowNull = dataItem.FIELD_NULLABLE;
        this.tuyChon = dataItem.OPT;
        this.maUnique = dataItem.FIELD_UNIQUE;
        this.idCachNhap = dataItem.ATT;
        this.lenhSql = dataItem.LOOKUP_SQL;
      }
    },getBang: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/doituongthuoctinh/danhsach/bang"
        );
        this.dsBang = rs.data;
        if(rs.data){
          this.tenBang = rs.data[0].TABLE_NAME
        }
      } catch (error) {
      } finally {
      }
    },getCachNhap: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/doituongthuoctinh/danhsach/cachnhaplieu"
        );
        this.dsCachNhapDl = rs.data;
      } catch (error) {
      } finally {
      }
    },getKieu: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/doituongthuoctinh/danhsach/kieudulieu"
        );
        this.dsKieuDl = rs.data;
      } catch (error) {
      } finally {
      }
    }
  },
};
</script>
<style>
.disable-btn:hover {
  background-color: white !important;
}

.disable-btn a {
  color: #6c757d !important;
}

.disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}
</style>
