<template src="./UpdateBirthdayByFile.html"></template>
<style src="./UpdateBirthdayByFile.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import UpdateBirthdayByFileAPI from './UpdateBirthdayByFileAPI'
import gridview from '@/components/Shared/gridview'
import VueFlatPickr from 'vue-flatpickr-component';
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: {breadcrumb,UpdateBirthdayByFileAPI,VueFlatPickr,gridview },
  name: 'UpdateBirthdayByFile',
  mounted () {
    
  },
  data () {
    return {
      showPopup:false,
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput:true,
      },
      header: {
        title: 'CẬP NHẬT NGÀY SINH CHO THUÊ BAO TRẢ TRƯỚC THEO FILE',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date(),
      abc: 21,
      listbox: {
        ds_file: {
          list: [],
          header:[],
          value: {url:""},
          isEnabled: false,
          data: {},
        },
        ds_tbao_success: {
          list: [],
          header:[ {fieldName: 'STT', headerText: 'Số TT', allowFiltering: true}, 
                      {fieldName: 'SO_TB', headerText: 'Số máy', allowFiltering: true},
                      {fieldName: 'NGAYSINH', headerText: 'Ngày sinh', allowFiltering: true , allowHtml : true},
                      ],
          value: {},
          isEnabled: false,
        },
        ds_tbao_error: {
          list: [],
          header:[ {fieldName: 'STT', headerText: 'Số TT', allowFiltering: true}, 
                      {fieldName: 'MESSAGE', headerText: 'Lỗi', allowFiltering: true},
                      {fieldName: 'SO_TB', headerText: 'Số máy', allowFiltering: true , allowHtml : true},
                      {fieldName: 'NGAYSINH', headerText: 'Ngày sinh', allowFiltering: true , allowHtml : true},
                      ],
          value: {},
          isEnabled: false,
        },
      },
      id_upload:"",
      kich_thuoc_file:"",
      so_tbao_thanhcong:"",
      so_tbao_thatbai:"",
      showResult:true,
      isUploaded:false,
    }
  },

  methods: {
    ShowAlert(mesage,val)
    {
      if (val==0)
      {
        this.$toast.success(mesage);
      }
      else if (val==1)
      {
        this.$toast.warning(mesage);
      }
      else if (val==2)
      {
        this.$toast.error(mesage);
      }
    },
    btnPopupClick: function (item) {
      this.$bvModal.show('PopUp');
    },
    async downloadItem({ url }) {
      var element = document.createElement('a');
      element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent("84918881232,12/01/1978\n84918881235,12/01/1978\n84999999999,12/01/1978"));
      element.setAttribute('download', url);
      element.style.display = 'none';
      document.body.appendChild(element);
      element.click();
      document.body.removeChild(element);
    },
    
    changeFileUpload() {
      console.log(this.$refs.files.files[0].name);
      if (this.$refs.files.files[0].name.length>0)
      {
        let result = this.$refs.files.files[0].name.substring(this.$refs.files.files[0].name.indexOf(".") + 1, this.$refs.files.files[0].name.length);
        if (result != "txt")
        {
          this.ShowAlert("File bạn chọn phải có định dạng .txt!",2);
          return;
        }
      }
      this.listbox.ds_file.value.url = this.$refs.files.files[0].name;
    },
    UploadFile() {
      let formData = new FormData();
      for(var file of this.$refs.files.files) {
        formData.append('files', file);
      }
      this.kich_thuoc_file=parseFloat((this.$refs.files.files[0].size)/1024).toFixed(2) + " MB";
      UpdateBirthdayByFileAPI.upload_file(this.axios,formData,{ headers: { 'Content-Type': 'multipart/form-data' } }).then((response) => {
        if(response.data.error_code === 'BSS-00000000') {
          this.listbox.ds_file.value.url = response.data.data.join('|');
          this.isUploaded = true;
          this.$refs.files.value=null;
        }
        }).catch(function(){
          console.log('File upload failed!');
        });
    },
    btnUpdateClicked()
    {
      var pdata = {};
      pdata.file_url= this.listbox.ds_file.value.url;
      pdata.upload_type="26253";
      console.log(pdata)
      this.loading(true);
      UpdateBirthdayByFileAPI.upload_ngay_sinh(this.axios,pdata).then((response) => {
        this.loading(false);
        if(response.data.error_code === 'BSS-00000000') {
          this.id_upload = response.data.data.upload_id;
          this.loadResult(response.data.data.upload_id); 
          this.showResult=true;
          this.isUploaded=false;
          this.ShowAlert("Cập nhật ngày sinh thành công!",0);
        } 
        else 
        {
          this.ShowAlert('Cập nhật ngày sinh không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.',2);
          console.log(response);
          this.loadResult(response.data.data.upload_id); 
        }
        }).catch(function(){
          this.ShowAlert('Cập nhật ngày sinh không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.',2);
        });
    },
    loadResult(val)
    {
    //Lấy kết quả thành công
      this.loading(true);
      UpdateBirthdayByFileAPI.get_success(this.axios,{"upload_id": val}).then((response) => {
        this.loading(false);
        if(response.data.error_code === 'BSS-00000000') {
          console.log(response.data.data);
          this.listbox.ds_tbao_success.list = response.data.data;
          this.so_tbao_thanhcong=this.listbox.ds_tbao_success.list.length;
          for(var i in this.listbox.ds_tbao_success.list) this.listbox.ds_tbao_success.list[i].STT=(parseInt(i)+1).toString();
            //this.listbox.ds_tbao_success.header = [{name:'STT',text:'Số TT'},{name:'SO_TB',text:'Số máy'},{name:'NGAYSINH',text:'Ngày sinh'},]
        }
        else 
        {
          this.listbox.ds_tbao_success.list = [];
          this.so_tbao_thanhcong=0;
        }
      });
    //Lấy kết quả không thành công
      this.loading(true);
      UpdateBirthdayByFileAPI.get_error(this.axios,{"upload_id": val}).then((response) => {
        this.loading(false);
        if(response.data.error_code === 'BSS-00000000') {
          this.listbox.ds_tbao_error.list = response.data.data;
          this.so_tbao_thatbai=this.listbox.ds_tbao_error.list.length;
          for(var j in this.listbox.ds_tbao_error.list) this.listbox.ds_tbao_error.list[j].STT= (parseInt(j) +1).toString();
          //this.listbox.ds_tbao_error.header = [{name:'STT',text:'Số TT'},{name:'MESSAGE',text:'Lỗi'},{name:'SO_TB',text:'Số máy'},{name:'NGAYSINH',text:'Ngày sinh'},]
        }
        else {
          this.listbox.ds_tbao_error.list = [];
          this.so_tbao_thatbai=0;
        }
      });
    }
  }
  
};
</script>
