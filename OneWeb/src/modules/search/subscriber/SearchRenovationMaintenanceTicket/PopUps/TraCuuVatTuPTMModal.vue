<template>
    <b-modal
        ref="popupTraCuuVatTuPTM"
        size="xl"
        hide-header
        hide-header-close
        hide-footer
        body-class="p-0 vattu-ptm-body"
        no-close-on-backdrop
        @show="handleShowModal"
    >
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                <span class="icon one-notepad"></span> Thông tin cấp vật tư cho thuê bao
                </div>
                <div class="close -ap icon-close" @click="hideModal"></div>
            </div>
            <ActionTop :actions="actions" @onActionClick="onActionClick"/>
            <div class="popup-body">
                <div class="legend-title">Danh sách vật tư</div>
                <div class="table-content">
                  <DataGrid 
                    :columns="columns" 
                    :dataSource="dataSources"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="true"
                  />
                  <div v-show="showZoomImg" class="panel-zoom-img"><img :src="zoomImg" alt="" style="height: 100%; width: 100%;" /></div>
                </div>
                
            </div>
        </div>
    </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import XLSX from 'xlsx'
export default {
    name:'TraCuuVatTuPTMModal',
    props:{
        chitietcc_id:{
            type:[Number, String],
            default:0
        },
    },
    components:{
        ActionTop
    },
    data(){
        return {
            actions:[
                {
                    id:'xuatexcel',
                    name:'Xuất Excel',
                    active: true,
                    icon_class:'one-excel'
                }
            ],
            columns:[
                {
                  fieldName: 'stt',
                  headerText: 'Stt',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'ma_tbi',
                  headerText: 'Mã tbị',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'loai_tbi',
                  headerText: 'Tên vật tư',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'seri',
                  headerText: 'Seri',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'soluong',
                  headerText: 'Số lượng',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'dongia',
                  headerText: 'Đơn giá',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'lohang',
                  headerText: 'Lô hàng',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                //Load popup image
                {
                  fieldName: '',
                  headerText: 'Mô tả',
                  allowFiltering: true,
                  allowSorting: true,
                  width:100
                },
                {
                  fieldName: 'trangbi',
                  headerText: 'Trang bị',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'kieu_tbi',
                  headerText: 'Kiểu thiết bị',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'tinhtrang_tbi',
                  headerText: 'Tình trạng',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'mucdich_cap',
                  headerText: 'Lý do cấp',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'ngay_th',
                  headerText: 'Ngày cấp',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'nguoi_cn',
                  headerText: 'Người CN',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'ten_dv',
                  headerText: 'Đơn vị',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'ten_dv',
                  headerText: 'Đơn vị',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'ten_kho',
                  headerText: 'Kho cấp',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'so_phieu',
                  headerText: 'Số phiếu',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: 'trangthai',
                  headerText: 'Trạng thái',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: '',
                  headerText: 'Hình ảnh',
                  textAlign: 'Center',
                  allowFiltering: false,
                  freeze: 'Right',
                  width: 150,
                  clipMode: 'Ellipsis',
                  template: this.getColumnTemplateHinhAnh(this) 
                },
            ],
            dataSources:[],
            SERVER_IMAGES:{
                IP: '10.70.52.164:8081',
                BASE_URL:'http://10.70.52.164:8081/FILE/',
                NO_IMAGE_URL:'http://10.70.52.164:8081/FILE/no-image-available.jpg',
                URL:'http://10.70.52.164:8081/FILE/UploadFile.aspx?version=v2'
            },
            tentat_ccbs:'',
            showZoomImg:false,
            zoomImg:null
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='xuatexcel'){
              if(this.dataSources.length<=0){
                this.$toast.error('Không có dữ liệu để xuất Excel !')
                return
              }
              let data = XLSX.utils.json_to_sheet(this.dataSources);
              let wb = XLSX.utils.book_new(); // make Workbook of Excel
              XLSX.utils.book_append_sheet(wb, data, 'Sheet1'); // sheetAName is name of Worksheet
              // export Excel file
              let fileFullName='danh-sach-vat-tu.xlsx'
              XLSX.writeFile(wb, fileFullName);
            }
        },
        handleShowModal(){
            this.dataSources=[]
            setTimeout(()=>{
                this.initDuLieu()
            }, 500)
        },
        showModal() {
            this.$refs["popupTraCuuVatTuPTM"].show()
        },
        hideModal() {
            this.$refs["popupTraCuuVatTuPTM"].hide()
        },
        async initDuLieu(){
          this.tt_nd=await this.thongtin_nguoidung()
          console.log('tt_nd', this.tt_nd)
          this.tentat_ccbs=this.tt_nd?this.tt_nd.tentat_ccbs:''
          console.log('tentat_ccbs', this.tentat_ccbs)
          this.dataSources=await this.lay_ds_vattu_chitietcc_id(this.chitietcc_id)
          //this.dataSources=await this.lay_ds_vattu_chitietcc_id(1062)
          //sp_get_dshinhanh
        },
        async lay_ds_vattu_chitietcc_id(chitietcc_id){
            try{
                this.loading(true)
                let response=await this.axios.post('/web-tracuu/tracuu-phieucaitao-baoduong/lay_ds_vattu_chitietcc_id', {
                    chitietcc_id: chitietcc_id
                })
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                console.log(e)
                this.loading(false)
                return []
            }
        },
        async sp_get_dshinhanh(phieuvt_id){
            try{
                this.loading(true)
                let response=await this.axios.post('/web-tracuu/tracuu-phieucaitao-baoduong/sp_get_dshinhanh', {
                    phieuvt_id: phieuvt_id
                })
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async thongtin_nguoidung(){
            try{
                this.loading(true)
                let response = await this.axios.post('/quantri/user/thongtin_nguoidung2', {})
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async link_view_file(list_file){
            try{
                this.loading(true)
                let response = await this.axios.post('/web-quantri/upload/link_view_file',{
                  list_file: list_file
                })
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        //Create template list image vật tư
        getColumnTemplateHinhAnh(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <div style="position: relative;">
                                <button ref="ref-btn-show-img" class="btn btn-sm btn-second" @click="toggleImgPanel">...</button>
                                <div ref="ref-panel-anh-vattu" v-show="isShown" class="panel-anh-vattu">
                                    <div style="position: absolute; height: 35px; text-align: left; top: 0; padding: 5px 0px">
                                        <span style="color: black;">Hình ảnh vật tư</span>
                                    </div>
                                    <button type="button" class="close" @click="closeImgPanel" style="position: absolute; right: 4px; top: 0;">×</button>
                                    <div style="border: 1px solid #ccc; height: 100%; overflow: auto;">
                                        <div v-for="image in images" style="width: 50%; height: 100%; float: left; padding: 5px; position: relative;">
                                            <img 
                                                v-on:mouseover="onMouseOver(image.url)" 
                                                v-on:mouseleave="onMouseLeave" 
                                                :src="image.url" alt="" style="height: 100%; width: 100%;" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        `,
                        data() {
                            return {
                                parent: parent,
                                data: {},
                                images: [],
                                isShown:false
                            }
                        },
                        computed: {
                            
                        },
                        watch: {
                            async isShown(newVal) {
                                if (newVal) {
                                    let top = this.$refs['ref-btn-show-img'].getBoundingClientRect().top
                                    let height = this.$refs['ref-btn-show-img'].clientHeight
                                    this.$refs['ref-panel-anh-vattu'].style.top = `${top + height + 3}px`
                                }
                                if (!newVal || this.images.length > 0) {
                                    return
                                }
                                this.images=await this.parent.getListImages(this.data.phieuvt_id)
                                console.log('images', this.images)
                            }
                        },
                        methods: {
                            toggleImgPanel() {
                                this.isShown=!this.isShown
                            },
                            closeImgPanel() {
                              this.isShown=false
                            },
                            onMouseOver(src) {
                                this.parent.zoomImg = src
                                this.parent.showZoomImg = true
                            },
                            onMouseLeave() {
                                this.parent.zoomImg = null
                                this.parent.showZoomImg = false
                            },
                        }
                    }
                }
            }
        },
        async getListImages(phieuvt_id){
          let result=await this.sp_get_dshinhanh(phieuvt_id)
          if(result&&result.length>0&&result[0].mota){
              //cũ
              var ds_hinhhanh_temp=[]
              //chuỗi hệ thống mới
              var chuoi_moi=''
              let words = result[0].mota.split('|')
              for(let i=0;i<words.length;i++){
                if(words[i]!=''){
                    console.log('words[i]', words[i])
                    //Hình ảnh từ hệ thống mới
                    if(words[i].includes('DHSX/')){
                        chuoi_moi+=words[i]+','
                    }else{
                        //hình ảnh hệ thống cũ
                        var imgLink=words[i]
                        if(!words[i].includes(this.SERVER_IMAGES.IP)){
                            imgLink=this.SERVER_IMAGES.BASE_URL+this.tentat_ccbs+'_VT/'+words[i]
                        }
                        ds_hinhhanh_temp.push({
                            name:words[i],
                            url:imgLink
                        })
                    }
                }
              }
              if(chuoi_moi!=''){
                //link_view_file
                let resultImageMoi=await this.link_view_file(chuoi_moi)
                for(let i=0;i<resultImageMoi.length;i++){
                    ds_hinhhanh_temp.push({
                        name:resultImageMoi[i].file_name,
                        url:resultImageMoi[i].file_url
                    })
                }
              }

              return ds_hinhhanh_temp
              
          }
          return []
        },

    }
}
</script>
<style>
.vattu-ptm-body .panel-anh-vattu {
    width: 380px; 
    height: 180px; 
    background-color: white; 
    position: fixed; 
    right: 10px;
    z-index: 1;
    padding: 34px 5px 5px 5px;
    border: 1px solid #ccc;
}
.vattu-ptm-body .panel-zoom-img {
    position: fixed;
    width: 500px;
    height: 500px;
    background-color: transparent;
    z-index: 99999;
    margin: auto auto;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
}
</style>