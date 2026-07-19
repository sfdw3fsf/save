<template src="./UpdateProductMO.html"></template>
<style scoped src="./UpdateProductMO.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import gridview from '@/components/Shared/gridview1'
//import gridview from './gridview'
import UpdateProductMOAPI from './UpdateProductMOAPI'

export default {
  components: { breadcrumb,gridview },
  name: 'UpdateProductMO',
  props: [ 'kieuSPId', 'isPopup' ],
  mounted () {
    this.loadDanhSachSanPham();
    if(this.kieuSPId) this.kieu_sp_id=this.kieuSPId;
    if(this.isPopup) this.formPopup = this.isPopup;
  },
  destroyed () {},
  computed: {
    validateTien() {
      return this.listbox.value.tien.match(/^[\d,\.\s]+$/g);
    }
  },
  data () {
    return {
      kieu_sp_id: 1,
      formPopup:false,
      header: {
        title: 'Danh sách sản phẩm quay vòng',
        list: [
          { name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } },
        ]
      },
      listbox: {
        list:[],
        value: {"tien":0},
        header: [{name:'sanpham_id',text:'ID'},{name:'ten_sp',text:'Tên sản phẩm'},{name:'tien',text:'Số tiền',format:"number",align:"right"},{name:'chitiet_sp',text:'Chi tiết sản phẩm'},],
        header1: [
          {fieldName:'sanpham_id',headerText:'ID', allowFiltering: true, },
          {fieldName:'ten_sp',headerText:'Tên sản phẩm', allowFiltering: true, },
          {fieldName:'tien',headerText:'Số tiền',format : 'N0', type : "Number", allowFiltering: true, },
          {fieldName:'chitiet_sp',headerText:'Chi tiết sản phẩm', allowFiltering: true },
        ],
      },
      isAddNew: false,
      currentProduct: {"tien":0},
      // dateConfig: {
      //   altInput: true,
      //   dateFormat: 'd/m/Y',
      //   altFormat: 'd/m/Y'
      // },
      // from_date: new Date(),
      // to_date: new Date()
    }
  },
  methods: {
    loadDanhSachSanPham() {
      UpdateProductMOAPI.get_ds_san_pham_quay_vong(this.axios, {params:{"kieuSPId":this.kieu_sp_id}}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            var lst = response.data.data;
            if(lst && lst.length>0) {
              for(var item of lst)
                if(item.tien && item.tien>0)
                  item.tien = item.tien*1;//this.formatNumber(item.tien);
                else item.tien = 0;
              this.listbox.list = lst;
            } else this.listbox.list = [];
            // this.listbox.header = [{name:'sanpham_id',text:'ID'},{name:'ten_sp',text:'Tên sản phẩm'},{name:'tien',text:'Số tiền',format:"number",align:"right"},{name:'chitiet_sp',text:'Chi tiết sản phẩm'},]
          } else this.listbox.list = [];
        }
      );
    },
    Accept() {
      this.$emit('FormClose',{});
    },
    CancelItem() {
      this.listbox.value = Object.assign({},this.currentProduct);
      this.isAddNew=false;
    },
    NewItem() {
      this.listbox.value={'tien':0};
      this.currentProduct = {'tien':0};
      this.isAddNew=true;
      this.$refs.tensp.focus()
    },
    DeleteItem() {
      if(!this.listbox.value.sanpham_id) {
        this.$toast.error('Bạn cần chọn sản phẩm để xoá!');
        return;
      }
      this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa dữ liệu không?', {
          size: 'sm',
          centered: true,
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
      }).then(async val => {
        if(val) {
          var item = {
            "loai": 3,
            "sanPhamId": this.listbox.value.sanpham_id?this.listbox.value.sanpham_id:null,
          }
          UpdateProductMOAPI.post_nhap_san_pham_quay_vong(this.axios,item).then((response) => {
            if(response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.message) {
              this.$toast.success('Xoá dữ liệu thành công!');
              this.listbox.value = {"tien":0};
              this.currentProduct = {"tien":0};
              this.loadDanhSachSanPham();
              //this.listbox.list = response.data.data;
              for(var item of this.listbox.list)
                item.tien = item.tien*1;//this.formatNumber(item.tien);
            } else this.$toast.error(response.data.message);
          }).catch(function(){
            this.$toast.error('Có lỗi xảy ra trong quá trình xoá dữ liệu. Vui lòng thử lại hoặc liên hệ người quản trị.');
          });
        }
      });
    },
    SaveItem() {
      if(!this.listbox.value.tien || this.listbox.value.tien<=0) { this.$toast.error('Số tiền phải là số  > 0'); this.$refs.tien.$el.focus(); return; }

      var isvalid = true;
      document.getElementsByTagName('input').forEach((item)=>{
        if(isvalid && !item.validity.valid) {
          isvalid = false;
          this.$toast.error(item.getAttribute('validate-title'));
          item.focus();
        }
      });
      if(!isvalid) return;

      var item = {
        "chiTietSP": this.listbox.value.chitiet_sp?this.listbox.value.chitiet_sp.trim():'',
        "kieuSPId": this.kieu_sp_id,
        "loai": this.listbox.value.sanpham_id?2:1,
        "sanPhamId": this.listbox.value.sanpham_id?this.listbox.value.sanpham_id:null,
        "tenSanPham": this.listbox.value.ten_sp.trim(),
        "tien": this.listbox.value.tien,//this.stripNumber(this.listbox.value.tien),
      }
      UpdateProductMOAPI.post_nhap_san_pham_quay_vong(this.axios,item).then((response) => {
        if(response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.message) {
          this.$toast.success('Cập nhật thành công');
          this.listbox.value = {"tien":0};
          this.currentProduct = {"tien":0};
          this.loadDanhSachSanPham();
        } else this.$toast.error(response.data.message);
      }).catch(function(){
        this.$toast.error('Có lỗi xảy ra trong quá trình lưu sản phẩm. Vui lòng thử lại hoặc liên hệ người quản trị.');
      });
      this.isAddNew=false;
    },
    grdChanged(obj) {
      this.listbox.value=Object.assign({},obj);//(obj);
      this.currentProduct = Object.assign({},obj);
    },
    stripNumber(nStr) {
      var number = this.listbox.value.tien.replace(/[^\d]/g, '');
      return isNaN(number) ? 0 : number;
    },
    formatNumber(nStr)
    {
        nStr += '';
        var x = nStr.split('.');
        var x1 = x[0];
        var x2 = x.length > 1 ? ',' + x[1] : '';
        var rgx = /(\d+)(\d{3})/;
        while (rgx.test(x1)) {
            x1 = x1.replace(rgx, '$1' + ',' + '$2');
        }
        return x1 + x2;
    },
    clearBox() {
      if(this.listbox.value.tien==0)
      {
        this.listbox.value.tien=""
        return;
      }
      var number = this.listbox.value.tien.replace(/[^\d]/g, '');
      this.listbox.value.tien = isNaN(number) ? "" : number;
    },
    formatBox() {
      this.listbox.value.tien = this.formatNumber(this.listbox.value.tien);
    }
  }
}
</script>
