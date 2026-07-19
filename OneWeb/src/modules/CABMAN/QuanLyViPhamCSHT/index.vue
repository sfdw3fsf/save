<template src="./index.template.html"></template>
<script>
import XLSX from "xlsx";
import DateTimeLib from "../../../utils/DateTimeLib";
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import {Workbook} from "@syncfusion/ej2-excel-export/index"
import PartnerListModal from '@/modules/admin/category/PartnerList/index'
import {
  minLength,
  maxLength,
  numeric,
  required,
} from "vuelidate/lib/validators";
export default {
  components: { XLSX ,BssErrorMarker,BssRequiredMarker,PartnerListModal},
  data: function () {
    return {
      dsViPham:[],
      dsDonviVp:[],
      dsDonviXL:[],
      dsKetquaXL:[],
      dataSelected:{
         vpcsht_id:0,
          donviVP:null,
          donviXL:null,
          kqXL:null,
          ngayVP:null,
          noidungVP:"",
          ngayXL: null,
          ghichu:'',
          bienphapXL:''
      },
      waterMark: "Chọn ngày",
      dateVal:  Date,
      dateFormat: "dd/MM/yyyy",
      timkiem:{
        txtTimKiem:"",
        tuNgay:null,
        denNgay:null
      },
      isDisableBtnNhapMoi: false,
      isDisableBtnGhiLai: true,
      isDisableBtnHuy: true,
      isDisableBtnXoa: true,
      json_name_tc:'dsViPham',
      kieu:0,
      tongphieu:0
    };
  },
  validations: {
    dataSelected: {
      donviVP: { required },
      noidungVP: { required } 
    }     
    },
  created: async function () {
  this.setButton(0) 
  
  await  this.load_cbb()
  await  this.hienThiDanhSach()
  this.timkiem.tuNgay = DateTimeLib.toDateDisplay(new Date())
  this.timkiem.denNgay = DateTimeLib.toDateDisplay(new Date())
  },
  watch: {
   
  },
  computed: {},
  methods: {
     hienThiDanhSach:async function(){
       try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.get(
          "/web-cabman/quanly/viphamcsht/napDsCsht"
        );
        if(rs.data!=null&&rs.data.length>0){
          this.tongphieu = rs.data.length
           //this.dsViPham = rs.data
          let temp = []
           for(let i=0;i<rs.data.length;i++){
           
             let item = rs.data[i]
            
             if(item.NGAY_VP!=null)
             item.NGAY_VP = DateTimeLib.toDateDisplay(item.NGAY_VP)
              if(item.NGAY_XL!=null)
             item.NGAY_XL = DateTimeLib.toDateDisplay(item.NGAY_XL)
              if(item.NGAY_CN!=null)
             item.NGAY_CN = DateTimeLib.toDateDisplay(item.NGAY_CN)
            temp.push(item)
           } 
           this.dsViPham = [...temp]         
        }
        
        
        else
        this.dsViPham  = []
      } catch (error) {
        console.log(error)
       this.$root.toastError('Lỗi khi lấy danh sách vi phạm!') 
      } finally {
         this.$root.showLoading(false)
      }
     },
     load_cbb:async function(){
     try {
         this.$root.showLoading(true)
        var rsDvXL = await this.$root.context.get(
          "/web-cabman/quanly/viphamcsht/napDonViXuLy"
        );
        let temp =[]
        this.dsDonviXL = rsDvXL.data       
        // if(rsDvXL.data!=null && rsDvXL.data.length>0)
        //  this.dataSelected.donviXL = this.dsDonviXL[0].DONVI_ID

          var rsKq = await this.$root.context.get(
          "/web-cabman/quanly/viphamcsht/napKetQuaXuLy"
        );
        this.dsKetquaXL = rsKq.data;
        // if(rsKq.data!=null&&rsKq.data.length>0)
        // this.dataSelected.kqXL = this.dsKetquaXL[0].KQXL_ID
          
        var rsDvVP = await this.$root.context.get(
          "/web-cabman/quanly/viphamcsht/napDonViViPham"
        );
        this.dsDonviVp = rsDvVP.data;
        // if(rsDvVP.data!=null&&rsDvVP.data.length>0)
        //  this.dataSelected.donviVP = this.dsDonviVp[0].DOITAC_ID

      } catch (error) {
       this.$root.toastError('Có lỗi: ',error.response.data.message_detail) 
      } finally {
         this.$root.showLoading(false)
      }
     },
     dsViPham_selectedRow:function(item){
      if(item!=null){
        this.dataSelected.vpcsht_id =  item.VPCSHT_ID
        this.dataSelected.donviVP = item.DOITAC_ID
        this.dataSelected.donviXL = item.DONVI_XL_ID
        this.dataSelected.kqXL=item.KQXL_ID
        console.log('vp:',item.NGAY_VP)
        if(item.NGAY_VP!=null)
        this.dataSelected.ngayVP =item.NGAY_VP //DateTimeLib.toDateDisplay(item.NGAY_VP)
        else
          this.dataSelected.ngayVP = DateTimeLib.toDateDisplay(new Date())

        if(item.NGAY_XL!=null)
        this.dataSelected.ngayXL =item.NGAY_XL  //DateTimeLib.toDateDisplay(item.NGAY_XL)
        else
          this.dataSelected.ngayXL = DateTimeLib.toDateDisplay(new Date())
        this.dataSelected.noidungVP= item.NOIDUNG_VIPHAM
        this.dataSelected.ghichu = item.GHICHU
        this.dataSelected.bienphapXL= item.KETQUA_XL
      }
      this.setButton(3)
     },
     setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa) {
      this.isDisableBtnNhapMoi = isNhapmoi;
      this.isDisableBtnGhiLai = isGhilai;
      this.isDisableBtnHuy = isHuy;
      this.isDisableBtnXoa = isXoa;
    },
    Clear:function()
        {
            this.$v.$reset()
            this.dataSelected.vpcsht_id = 0;
            this.dataSelected.bienphapXL= "";
            this.dataSelected.ghichu = ''
            this.dataSelected.noidungVP = ''
            this.dataSelected.ngayVP = DateTimeLib.toDateDisplay(new Date())
            this.dataSelected.ngayXL = DateTimeLib.toDateDisplay(new Date())
    },
    setButton(kieu){
           this.isDisableBtnNhapMoi = true;
            this.isDisableBtnGhiLai = true;
            this.isDisableBtnHuy = true;
            this.isDisableBtnXoa = true;

            if (kieu == -1)//Bat dau
            {
               this.isDisableBtnGhiLai = false;
                this.isDisableBtnHuy = false;
            }
            if (kieu == 0)//Bat dau
            {
                this.isDisableBtnNhapMoi = false;
                this.isDisableBtnHuy = false;
                this.Clear();
            }
            if (kieu == 1)//Them moi
            {

                this.isDisableBtnGhiLai  = false;
                this.isDisableBtnHuy = false;
                 this.Clear();
            }
            if (kieu == 2)//Huy
            {
                 this.isDisableBtnNhapMoi = false;
                 this.isDisableBtnXoa = false;
                this.Clear();
            }
            if (kieu == 3)//Edit
            {
                this.isDisableBtnNhapMoi = false;
                this.isDisableBtnXoa = false;
                this.isDisableBtnGhiLai  = false;
                this.isDisableBtnHuy = false;
            }

    },
    //#region  'CRUD'
    NHAP_MOI: async function(){
     this.setButton(1)
     this.kieu = 1
    },
    GHI_LAI: async function(){
     if(this.KiemTraDuLieu()) return
     if(this.kieu ==1)
      await this.taoMoi();
      else
      await this.capNhat();
      await this.hienThiDanhSach()
    },
    taoMoi: async function (){
      try{
    this.$root.showLoading(true)
    let data = this.TaoDuLieu()
        let rs = await this.$root.context.post(
          "/web-cabman/quanly/viphamcsht/themViPhamCsht",data
        ); 
        if (rs.error_code == 'BSS-00000000') {
            this.$root.toastSuccess('Thêm mới vi phạm thành công !!')            
          } else {
            this.$root.toastError(response.message_detail)
      }
      }catch(e){
        console.log(e)
       this.$root.toastError('Có lỗi xảy ra trong quá trình thêm mới vi phạm') 
      }finally{
     this.$root.showLoading(false)
      }
    },
    capNhat: async function (){
    try{
    this.$root.showLoading(true)
    let data = this.TaoDuLieu()
        let rs = await this.$root.context.post(
          "/web-cabman/quanly/viphamcsht/capNhatViPhamCsht",data
        ); 
        if (rs.error_code == 'BSS-00000000') {
            this.$root.toastSuccess('Cập nhật vi phạm thành công !')            
          } else {
            this.$root.toastError(response.message_detail)
      }
      }catch(e){
        console.log(e)
       this.$root.toastError('Có lỗi xảy ra trong quá trình cập nhật') 
      }finally{
     this.$root.showLoading(false)
      }
    },
    TaoDuLieu: function(){
     let dataIns = {
        vpcsht_id: this.dataSelected.vpcsht_id,
        kqXuLyId: this.dataSelected.kqXL,
        doiTacId: this.dataSelected.donviVP,
        donViXlId: this.dataSelected.donviXL,
        bienPhapXl: this.dataSelected.bienphapXL,       
        ngayVp: DateTimeLib.toDateDisplayWithFormat(this.dataSelected.ngayVP,'YYYY/MM/DD'),
        ghiChu: this.dataSelected.ghichu,
        mayCn: '',
        ngayCn: DateTimeLib.toDateDisplayWithFormat(new Date(),'YYYY/MM/DD'),
        ngayXl:  DateTimeLib.toDateDisplayWithFormat(this.dataSelected.ngayXL,'YYYY/MM/DD'),
        nguoiCn: this.$root.token.getUserName(),
        noiDungViPham: this.dataSelected.noidungVP
      }
     console.log('dataIns:',dataIns)
     return dataIns
    },
    HUY_BO: async function(){
     this.setButton(2)
     this.kieu = 2
     await this.load_cbb()
    },
    XOA: async function(){
      this.$confirm(
            `"Bạn thật sự muốn xóa dữ liệu không ?`,         
            {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
            }
          )
            .then(async () => {
            let rs = await this.$root.context.get(
          "/web-cabman/quanly/viphamcsht/XoaViPhamCsht?id="+this.dataSelected.vpcsht_id,
        ); 
        if (rs.error_code == 'BSS-00000000') {
            this.$root.toastSuccess('Xóa thành công !')   
            await this.hienThiDanhSach()         
          } else {
            this.$root.toastError(rs.message_detail)
          }
            }) 
        .catch((e) => {
          console.log(e)
           this.$root.toastError('Không thể xóa bản ghi này') 
        });
    },
    TRA_CUU: async function(){
     try{
     this.$root.showLoading(true)
     this.dsViPham = []
     let text= ''
     if(this.timkiem.txtTimKiem!='') 
      text= this.timkiem.txtTimKiem.trim()
     let rs = await this.$root.context.get(
          "/web-cabman/quanly/viphamcsht/tracuu-vipham-csht",{
            text: text,
            tuNgay: DateTimeLib.toDateDisplay(this.timkiem.tuNgay),
            denNgay:DateTimeLib.toDateDisplay(this.timkiem.denNgay)
          }
        ); 
      if(rs.data!=null&&rs.data.length>0){
           //this.dsViPham = rs.data
          let temp = []
           for(let i=0;i<rs.data.length;i++){
           
             let item = rs.data[i]
            
             if(item.NGAY_VP!=null)
             item.NGAY_VP = DateTimeLib.toDateDisplay(item.NGAY_VP)
              if(item.NGAY_XL!=null)
             item.NGAY_XL = DateTimeLib.toDateDisplay(item.NGAY_XL)
              if(item.NGAY_CN!=null)
             item.NGAY_CN = DateTimeLib.toDateDisplay(item.NGAY_CN)
            temp.push(item)
           } 
           this.dsViPham = [...temp]         
        }
     }catch(e){
       console.log(e)
     this.$root.toastError('Lỗi khi tìm kiếm danh sách vi phạm!') 
     }finally{
    this.$root.showLoading(false)
     }
    },
   xuatExcelDangGrid() {
            let columns = [
                { index: 1, width: 70 },
                { index: 2, width: 300 },
                { index: 3, width: 100 },
                { index: 4, width: 300 },
                { index: 5, width: 300 },
                { index: 6, width: 300 },
                { index: 7, width: 300 },
                { index: 8, width: 200 },
                { index: 9, width: 120 },
                { index: 10, width: 120 },
                { index: 11, width: 120 },
            ]
            let propName = {
                STT: 'STT',
                DONVI_VP_TEN: 'Đơn vị vi phạm',
                NGAY_VP: 'Ngày vi phạm', 
                NOIDUNG_VIPHAM: 'Nội dung vi phạm', 
                BIENPHAP_XL: 'Biện pháp xử lý', 
                KETQUA_XL:'Kết quả xử lý',
                DONVI_XL_TEN: 'Đơn vị xử lý',
                NGAY_XL: 'Ngày xử lý',
                GHICHU: 'Ghi chú',
                NGUOI_CN: 'Người cập nhật',
                NGAY_CN:'Ngày cập nhật'
            }

            let rows = []

            let headerRow = {
                index: 1,
                cells: []
            }

            Object.keys(propName).forEach((item, index) => {
                headerRow.cells.push({
                    index: index + 1,
                    value: propName[item],
                    style: {
                        borders: {
                            color: '#00000000',
                            lineStyle: 'thin'
                        }
                    }
                })
            })

            rows.push(headerRow)

            this.dsViPham.forEach((item, index) => {

                let row = {
                    index: index + 2,
                    cells: []
                }

                Object.keys(propName).forEach((element, index) => {
                    row.cells.push({
                        index: index + 1,
                        value: item[element],
                        style: {
                            borders: {
                                color: '#00000000',
                                lineStyle: 'thin'
                            }
                        }
                    })
                })
                rows.push(row)
            })

            let worksheets = [{ columns, rows }]
            let workbook = new Workbook({worksheets}, 'xlsx')
            workbook.save('Export.xlsx')
        },
    XUAT_EXCEL:function(){
   try {
     if(this.dsViPham== null || this.dsViPham.length== 0) {
       this.$root.toastError('Không có dữ liệu để xuất Excel !')
     }else{
       this.xuatExcelDangGrid()
      } 
     }catch (error) {
       console.log(error)
      } finally {
        this.$root.showLoading(false);
      }
    },
    TIM_KIEM: async function(e){
     if(e.code == 'Enter'){
       if(this.timkiem.txtTimKiem !='')
        await this.TRA_CUU()
     }
    },
    KiemTraDuLieu: function(){
       this.$v.$touch();
        let errorMessage = [];
        let flag=false
       if (!this.$v.dataSelected.donviVP.required) {
        errorMessage.push("Trường Đơn vị vị phạm bắt buộc nhập dữ liệu.");
        if(!flag)
         this.$refs['dv-vipham'].focus()
         flag=true
      }
      if (!this.$v.dataSelected.noidungVP.required) {
        errorMessage.push("Trường Nội dung vi phạm bắt buộc nhập dữ liệu.");
         if(!flag)
         this.$refs['noidungVP'].focus()
         flag=true
      }
      if(!this.KiemTraNgayThang( DateTimeLib.toDateDisplayWithFormat(this.dataSelected.ngayVP,'DD/MM/YYYY'),
                                 DateTimeLib.toDateDisplayWithFormat(this.dataSelected.ngayXL,'DD/MM/YYYY'))){
        errorMessage.push("Ngày vi phạm không lớn được hơn ngày xử lý.");
      }
       if (errorMessage.length > 0) {
         this.$toast.error(errorMessage.join("\n"));     
         return true
       } else 
        return false
    },
    modalDoiTac_Show:  function(){
     // this.$refs['doitac'].show();
    },
     modalDoiTac_Ok: async function(){
      let rsDvVP = await this.$root.context.get(
          "/web-cabman/quanly/viphamcsht/napDonViViPham"
        );
        this.dsDonviVp = rsDvVP.data;
    },
    showDoiTac:async  function(){
      this.$refs['doitac'].show();
    },
    KiemTraNgayThang:  function (thangnam_tt,  thangnam_sys){
     let result = false;
            if (parseInt(thangnam_tt.substring(6, thangnam_tt.length)) < parseInt(thangnam_sys.substring(6, thangnam_sys.length)))//So sánh năm
                return true;//Nếu năm thanh toán < năm hoàn thành thì true;
            else if (parseInt(thangnam_tt.substring(6, thangnam_tt.length )) == parseInt(thangnam_sys.substring(6, thangnam_sys.length )))//So sánh năm
            {
                //Nêu năm = nhau thì kiểm tra tiếp
                //Nếu tháng tt < thang ht -> true
                if (parseInt(thangnam_tt.substring(3, 5)) < parseInt(thangnam_sys.substring(3, 5)))
                    result = true;
                else if (parseInt(thangnam_tt.substring(3, 5)) == parseInt(thangnam_sys.substring(3, 5)))
                    //Nếu tháng & năm = nhau thì kiểm tra ngày
                    if (parseInt(thangnam_tt.substring(0, 2)) <= parseInt(thangnam_sys.substring(0, 2)))
                        return true;
                    else
                        return false;
                else
                    result = false;//tháng tt lớn hơn thang ht
            }
            else
                result = false;

            return result;
    },
    //#endregion
  },
};
</script>
<style>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
 .frm-doi-tac.modal-body .page-content {
      position: inherit !important;
  }
  #modal-doitachhh .modal-dialog {
		-webkit-transform: none;
		transform: none;
		width: 80%;
		max-width: 80%;
	
}
#modal-doitachhh .main-wrapper,#modal-doitachhh .page-content {
    position: initial;
   
}
</style>
