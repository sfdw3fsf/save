<template src="./ArisingScatteredDebts.html"></template>
<style src="./ArisingScatteredDebts.scss"></style>
<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import moment from 'moment'
import API from './API.js'
import XLSX from 'xlsx'
export default {
    name:'ArisingScatteredDebts',
    components:{
        breadcrumb, ActionTop, KDataGrid
    },
    data(){
        return {
            header: {
                title: "Danh sách phát sinh công nợ rải rác",
                list: [
                    {
                        name: "Cước nóng",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ]
            },
            actions:[
                {
                    id:'nhapmoi',
                    name:'Nhập mới',
                    active: true,
                    icon_class:'one-file-plus'
                },
                {
                    id:'nhaptufile',
                    name:'Nhập theo file',
                    active: true,
                    icon_class:'one-xlsx-import'
                },
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'huy',
                    name:'Huỷ',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_circle-remove'
                },
                {
                    id:'xoa',
                    name:'Xoá',
                    active: true,
                    icon_class:'one-trash'
                },
                {
                    id:'file_mau',
                    name:'File mẫu',
                    active: true,
                    icon_class:'icon one-excel'
                }
            ],
            input_ma_tt:'',
            input_chuky_xuat_hd:'0',
            thang_bdtc:moment().format('MM/YYYY'),
            input_luot_xuat_hd:'',
            thang_bdhd:moment().format('MM/YYYY'),
            ngayxuat_hd:new Date(),
            thang_kthd:moment().format('MM/YYYY'),
            selectIndex:0,
            columns:[
                {
                    fieldName:'ma_tt',
                    headerText:'Mã thanh toán',
                    allowFiltering:true,
                    allowSorting:true
                },
                {
                    fieldName:'ngay_xuathd',
                    headerText:'Ngày xuất HĐ',
                    allowFiltering:true,
                    allowSorting:true
                },
                {
                    fieldName:'thang_bd',
                    headerText:'Tháng BĐ xuất HĐ',
                    allowFiltering:true,
                    allowSorting:true
                },
                {
                    fieldName:'thang_kt',
                    headerText:'Tháng KT xuất HĐ',
                    allowFiltering:true,
                    allowSorting:true
                },
                {
                    fieldName:'chuki',
                    headerText:'Chu kỳ xuất HĐ',
                    allowFiltering:true,
                    allowSorting:true
                },
                {
                    fieldName:'luot',
                    headerText:'Lượt xuất HĐ',
                    allowFiltering:true,
                    allowSorting:true
                },
                {
                    fieldName:'xuat_hoadon',
                    headerText:'Trạng thái xuất HĐ',
                    allowFiltering:true,
                    allowSorting:true
                },
                {
                    fieldName:'nguoinhap',
                    headerText:'Người nhập',
                    allowFiltering:true,
                    allowSorting:true
                },
                {
                    fieldName:'ngaynhap',
                    headerText:'Ngày nhập',
                    allowFiltering:true,
                    allowSorting:true
                },
                // {
                //     fieldName:'kieu',
                //     headerText:'Kiểu nhập TTTC',
                //     allowFiltering:true,
                //     allowSorting:true
                // },
            ],
            dataSources:[],
            commands:[
                {
                    name: 'khongro',
                    cssClass: 'btn btn-main -ap icon-eye',
                    width: 40,
                    text: '',
                    title: '',
                },
                {
                    name: 'edit',
                    cssClass: 'btn btn-main -ap icon-pen',
                    width: 40,
                    text: '',
                    title: 'Sửa dữ liệu',
                },
                {
                    name: 'xoa_dulieu',
                    cssClass: 'btn btn-main -ap icon-trash',
                    width: 40,
                    text: '',
                    title: 'Xóa dữ liệu',
                }
            ],
            file:null,
            danhsach_excel:[],
            enabledThangKT:true
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='nhapmoi'){
                this.setActiveActions(1)
                this.dataSources=[]
            }else if(action.id=='nhaptufile'){
                this.$confirm('File excel import gồm các cột theo thứ tự sau: PHANVUNG_ID, MA_TT, THANG_BD, THANG_KT, LUOT, CHUKI, NGAY_TC, NGUOI_CN. \n Bạn có muốn tiếp tục?', 'Thông báo', {
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Không'
                }).then(async () => {
                    this.$refs.fileInput.value=null
                    document.getElementById("file").click()
                })
            }else if(action.id=='ghilai'){
                this.ghiLai()
            }else if(action.id=='huy'){
                this.setActiveActions(0)
            }else if(action.id=='xoa'){
                this.xoaDuLieu()
            }else if(action.id=='file_mau'){
                this.taiFileMau()
            }
        },
        clear(){
            this.input_ma_tt=''
            this.input_chuky_xuat_hd=''
            this.thang_bdtc=moment().format('MM/YYYY')
            this.input_luot_xuat_hd=''
            this.thang_bdhd=moment().format('MM/YYYY')
            this.ngayxuat_hd=new Date()
            this.thang_kthd=moment().format('MM/YYYY')
            this.selectIndex=0
        },
        setActiveActions(kieu){
            for(let i=0;i<this.actions.length;i++){
                if(this.actions[i].id!='nhaptufile'&&this.actions[i].id!='file_mau'){
                    this.actions[i].active=false
                }
            }
            if(kieu==-1){//bắt đầu
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
            }else if(kieu==0){//bắt đầu
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.clear()
            }else if(kieu==1){//thêm mới
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.$refs.inputMaTT.focus()
                this.clear()

            }else if(kieu==2){//Huỷ
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
                this.clear()
            }else if(kieu==3){//edit
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
            }
        },
        async xoaDuLieu(){
            let result = await this.confirm('Bạn chắc chắn muốn xóa dữ liệu?','Thông báo')
            if(result==0) return
            let dataCheckeds=this.$refs.gridDs.getSelectedRecords()
            if(dataCheckeds.length==0){
                this.$toast.error('Hãy chọn dữ liệu cần xóa')
                return
            }
            let rs=await API.xoaChukyCNRR(this, {
                p_ma_thanhtoan:dataCheckeds[0].ma_tt,
                p_luot:dataCheckeds[0].luot,
                p_thangbd:dataCheckeds[0].thang_bd,
                p_ngayxoa:moment().format('DD/MM/YYYY')
            })
            if(rs=='OK'){
                this.$toast.success('Xóa thành công!')
                this.HienThiDuLieu()
            }else{
                this.$toast.error(rs)
            }
        },
        async confirm(message, title){
            try{
                let result = await this.$confirm(message,title,{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Hủy'
                })
                return 1
            }catch(e){
                return 0
            }
        },
        isNumber(evt) {
            evt = evt ? evt : window.event;
            var charCode = evt.which ? evt.which : evt.keyCode;
            if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                evt.preventDefault();
            } else {
                return true;
            }
        },
        taiFileMau(){
            window.open("/static/files/MauFile_Import_Danh_Sach_CN_Rai_Rac.xlsx")
        },
        onChangeFile(event){
            this.file=event.target.files[0]
            this.readFileExcel()
        },
        readFileExcel(){
            this.danhsach_excel=[]
            let columns
            try {
                this.loading(true)
                if (this.checkfile()) {
                    const reader = new FileReader()
                    reader.onload = (e) => {
                        /* Parse data */
                        const bstr = e.target.result
                        const wb = XLSX.read(bstr, { type: "binary" })
                        /* Get first worksheet */
                        const wsname = wb.SheetNames[0];
                        const ws = wb.Sheets[wsname];
                        /* Convert array of arrays */
                        const data = XLSX.utils.sheet_to_json(ws, { header: 1 });
                        columns=data[0]
                        //PHANVUNG_ID, MA_TT, THANG_BD, THANG_KT, LUOT, CHUKI, NGAY_TC, NGUOI_CN
                        if(this.kiemTraDuLieu(columns)){
                            for(var i=1;i<data.length;i++){
                                this.danhsach_excel.push({
                                    PHANVUNG_ID:data[i][0]!=undefined?data[i][0]:null,
                                    MA_TT:data[i][1]!=undefined?data[i][1]:null,
                                    THANG_BD:data[i][2]!=undefined?data[i][2]:null,
                                    THANG_KT:data[i][3]!=undefined?data[i][3]:null,
                                    LUOT:data[i][4]!=undefined?data[i][4]:null,
                                    CHUKI:data[i][5]!=undefined?data[i][5]:null,
                                    NGAY_TC:data[i][6]!=undefined?data[i][6]:null,
                                    NGUOI_CN:data[i][7]!=undefined?data[i][7]:null
                                })
                                
                            }
                            if(this.danhsach_excel.length>0){
                                this.docfile_ps_congno_rairac(this.danhsach_excel)
                            }else{
                                this.$toast.error('Danh sách trống!')
                            }
                        }
                        
                    }
                    reader.readAsBinaryString(this.file)
                }
            } catch (error) {
                this.$toast.error('Có lỗi xảy ra khi đọc file')
            } finally {
                this.loading(false)
            }
        },
        async docfile_ps_congno_rairac(data){
            console.log('docfile_ps_congno_rairac', data)
            let ds=await API.importChukyCNRR(this,{
                p_data:JSON.stringify(data)
            })
            
            if(ds instanceof Object){
                this.$alert(`Tổng số row dữ liệu thành công: ${ds.thanhcong?ds.thanhcong:0}, thất bại: ${ds.thatbai?ds.thatbai:0}`, "Thông báo", {
                    dangerouslyUseHTMLString: true,
                    confirmButtonText: "OK",
                    type: "info"
                })
            }else{
                this.$toast.error(ds)
            }
        },
        checkfile: function () {
            if (this.file == null) {
                this.$toast.error('Chưa chọn file excel')
                return false
            }
            if (this.file.type != "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"){
                this.$toast.error('File upload không đúng định dạng')
                return false
            }
            return true
        },
        kiemTraDuLieu: function (columns) {
            if (columns == null) {
                this.$toast.error('File có cấu trúc không hợp lệ!')
                return false
            }
            if(columns.length<=1){
                this.$toast.error('File excel gồm 8 trường:  PHANVUNG_ID, MA_TT, THANG_BD, THANG_KT, LUOT, CHUKI, NGAY_TC, NGUOI_CN!')
                return false
            }
            if(!columns[0]||columns[0].toString().toUpperCase() != "PHANVUNG_ID"){
                this.$toast.error('Tên cột đầu tiên trong file không đúng định dạng : PHANVUNG_ID')
                return false
            }
            if(!columns[1]||columns[1].toString().toUpperCase() != "MA_TT"){
                this.$toast.error('Tên cột thứ 2 trong file không đúng định dạng : MA_TT')
                return false
            }
            if(!columns[2]||columns[2].toString().toUpperCase() != "THANG_BD"){
                this.$toast.error('Tên cột thứ 3 trong file không đúng định dạng : THANG_BD')
                return false
            }
            if(!columns[3]||columns[3].toString().toUpperCase() != "THANG_KT"){
                this.$toast.error('Tên cột thứ 4 trong file không đúng định dạng : THANG_KT')
                return false
            }
            if(!columns[4]||columns[4].toString().toUpperCase() != "LUOT"){
                this.$toast.error('Tên cột thứ 5 trong file không đúng định dạng : LUOT')
                return false
            }
            if(!columns[5]||columns[5].toString().toUpperCase() != "CHUKI"){
                this.$toast.error('Tên cột thứ 6 trong file không đúng định dạng : CHUKI')
                return false
            }
            if(!columns[6]||columns[6].toString().toUpperCase() != "NGAY_TC"){
                this.$toast.error('Tên cột thứ 7 trong file không đúng định dạng : NGAY_TC')
                return false
            }
            if(!columns[7]||columns[7].toString().toUpperCase() != "NGUOI_CN"){
                this.$toast.error('Tên cột thứ 8 trong file không đúng định dạng : NGUOI_CN')
                return false
            }
            return true
        },
        async ghiLai(){
            let isInsert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active?false:true
            if(this.input_ma_tt.trim()==''){
                this.$toast.error('Hãy nhập mã thanh toán!')
                this.$refs.inputMaTT.focus()
                return
            }

            let dt=await API.getDsMucCuocCNRR(this,{
                p_ma_thanhtoan:this.input_ma_tt.trim()
            })
            console.log('dt', dt)
            if(dt.length>0&&dt[0].muccuoc_id==2){
                if(Number(moment(this.ngayxuat_hd,'DD/MM/YYYY').format('DD'))!=1){
                    this.$toast.error('Thuê bao với mức cước lưu lượng thì bắt buộc phải chọn ngày xuất hóa đơn là ngày đầu tiên của tháng')
                    return
                }
            }

            if(isInsert){
                let rs=await API.insChukyCNRR(this,{
                    p_ma_thanhtoan:this.input_ma_tt.trim(),
                    p_sothang:this.input_chuky_xuat_hd.trim(),
                    p_ngay_tc:this.ngayxuat_hd?moment(this.ngayxuat_hd).format('DD/MM/YYYY'):'',
                    p_thang_bd:this.thang_bdhd?moment(this.thang_bdhd,'MM/YYYY').format('YYYYMM'):null,
                    p_thang_kt:this.thang_kthd?moment(this.thang_kthd,'MM/YYYY').format('YYYYMM'):null
                })
                if(rs=='OK'){
                    this.$toast.success('Thêm mới thành công!')
                    this.HienThiDuLieu()
                }else{
                    this.$toast.error(rs)
                }
            }else{
                let rs=await API.capNhatChukyCNRR(this,{
                    p_ma_thanhtoan:this.input_ma_tt.trim(),
                    p_luot:this.input_luot_xuat_hd.trim(),
                    p_chuki:this.input_chuky_xuat_hd.trim(),
                    p_ngay_tc:this.ngayxuat_hd?moment(this.ngayxuat_hd).format('DD/MM/YYYY'):'',
                    p_thang_bd:this.thang_bdhd?moment(this.thang_bdhd,'MM/YYYY').format('YYYYMM'):null,
                    p_thang_kt:this.thang_kthd?moment(this.thang_kthd,'MM/YYYY').format('YYYYMM'):null,
                    p_ngaysua:moment().format('DD/MM/YYYY')
                })
                if(rs=='OK'){
                    this.$toast.success('Cập nhật thành công!')
                    this.HienThiDuLieu()
                }else{
                    this.$toast.error(rs)
                }
            }
        },
        async onEnterMaTT(){
            if(this.input_ma_tt.trim()==''){
                return
            }
            //hiển thị tháng bdtc
            //getThangBDTC_Theo_MaTT
            let ds=await API.getThangBDTC_Theo_MaTT(this,{
                p_matt:this.input_ma_tt.trim()
            })
            if(ds.length>0){
                this.thang_bdtc=ds[0].thang_bdtc?moment(ds[0].thang_bdtc, 'YYYYMM').format('MM/YYYY'):null
            }else{
                this.thang_bdtc=null
            }

            //
            await this.HienThiDuLieu()
        },
        changeThangBDTC(event){
            if(!event.value){
                this.thang_bdtc=null
                return
            }
            this.thang_bdtc=moment(event.value).format('MM/YYYY')
        },
        changeThangBDHD(event){
            if(!event.value){
                this.thang_bdhd=null
                return
            }
            this.thang_bdhd=moment(event.value).format('MM/YYYY')
            console.log('thang_bdhd', this.thang_bdhd)
            var chuki=moment(this.thang_kthd, 'MM/YYYY').diff(moment(this.thang_bdhd, 'MM/YYYY'),'months')+1
            this.input_chuky_xuat_hd=chuki?chuki.toString():''
        },
        changeNgayXuatHĐ(event){
            console.log('changeNgayXuatHĐ',event )
            if(!event.value){
                this.ngayxuat_hd=null
                return
            }
            this.ngayxuat_hd=moment(event.value).format('DD/MM/YYYY')
            console.log('ngayxuat_hd', this.ngayxuat_hd)
        },
        changeThangKTHD(event){
            if(!event.value){
                this.thang_kthd=null
                return
            }
            this.thang_kthd=moment(event.value).format('MM/YYYY')
            //var test=moment(this.thang_bdhd, 'MM/YYYY').diff(moment(), 'months')
             var chuki=moment(this.thang_kthd, 'MM/YYYY').diff(moment(this.thang_bdhd, 'MM/YYYY'),'months')+1
            this.input_chuky_xuat_hd=chuki?chuki.toString():''
        },
        async onSelectedRow(data){
            if(!data){
                this.clear()
                return
            }
            let item=Array.isArray(data)?data[0]:data

            console.log('onSelectedRow', item)

            this.input_ma_tt=item.ma_tt?item.ma_tt.toString():''
            this.thang_kthd=item.thang_kt?moment(item.thang_kt,'YYYYMM').format('MM/YYYY'):null
            this.thang_bdhd=item.thang_bd?moment(item.thang_bd,'YYYYMM').format('MM/YYYY'):null
            this.input_chuky_xuat_hd=item.chuki?item.chuki.toString():''
            this.ngayxuat_hd=item.ngay_xuathd?moment(item.ngay_xuathd, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY'):null
            this.input_luot_xuat_hd=item.luot?item.luot.toString():''

            let ds=await API.getThangBDTC_Theo_MaTT(this,{
                p_matt:this.input_ma_tt.trim()
            })
            if(ds.length>0){
                this.thang_bdtc=ds[0].thang_bdtc?moment(ds[0].thang_bdtc, 'YYYYMM').format('MM/YYYY'):null
            }else{
                this.thang_bdtc=null
            }
            

            this.setActiveActions(3)
        },
        blurChuKyHD(){
            this.enabledThangKT=true
            if(this.input_chuky_xuat_hd.trim()!=''&&Number(this.input_chuky_xuat_hd.trim())!=0){
                this.thang_kthd=moment(this.thang_bdhd, 'MM/YYYY').add(Number(this.input_chuky_xuat_hd.trim()-1), 'M').format('MM/YYYY')
                this.enabledThangKT=false
            }else{
                this.thang_kthd=moment().format('MM/YYYY')
            }
        },
        rowGridSelected(args){
            this.selectIndex=args.rowIndex
        },
        async commandClicked(cmdName, rowData){
          console.log('commandClicked', cmdName)
          console.log('commandClicked', rowData)
        },
        async HienThiDuLieu(){
            this.dataSources=await API.getDsChuKyCNRR(this,{
                p_ma_thanhtoan:this.input_ma_tt.trim()
            })
            this.setActiveActions(-1)
        },
        async initDuLieu(){
            this.HienThiDuLieu()
        }
    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        }, 1000)
    }

}
</script>