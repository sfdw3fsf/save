<template src="../TraCuuVDC/index.html">
</template>
<style scoped>
input[type=number] {
  text-align:right;
}
input[type=text] {
  text-align:left;
}
</style>
<script>
import service from './service'

import LichSuTacDong from './LichSuTacDong/lsmain'
export default {
   props: {
   },
  components:{
    LichSuTacDong
  },
   data : function () {
     return {
       matKhauView:"",
       greetnetstatus:null,
       tt_nd:[],
       AccountModel: [],
       CustomerModel: [],
       AcountServiceModel: [],
       logObject:[],
       lblCGNAT:'',
       resolvePromise :{},
       rejectPromise :{},
       txtAccount: '',
       txtLoaiTaiKhoan: '',
       txtMaAccount: '',
       txtEmail: '',
       txtNgayBanGiao: '',
       txtNgayTao: '',

       //Thông tin khách hàng
       txtMaHD: '',
       txtTinh: '',
       txtLoaiKH: '',
       txtTenKH: '',
       txtDiaChiKH: '',
       txtSoDT: '',

       //Thông tin chi tiết dịch vụ
       txtBras: '',
       txtBrasIP: '',
       txtDSLam: '',
       txtTrangThai: '',
       txtHinhThucTT: '',
       txtGoiCuoc: '',
       txtGhiChu: '',
       txtDichVuSuDung: '',
       txtNasPortID: '',
       txtSlotPort: '',
       txtIPTinh: '',
       txtMaTN: '',
       txtTTCuoc: '',
       disableCheckGreennet:false,
       tag : 0,
       dtService:[],
       userId :'',
       password:'',
       visiblRoll:false,
       visiblResend:false
     }
   },
  methods: {
    show (data) {
      return new Promise((resolve, reject) => {
        this.txtAccount = data
        if (this.$auth.getUserName() != "admin" || this.$auth.getTinh() != "HNI")
          this.disableCheckGreennet = true;

        this.$bvModal.show("tracuuVdc")

        if(this.txtAccount){
          this.TraCuuTB_ADSL()
        }
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },clear: function (){
      this.txtAccount= ''
      this.txtLoaiTaiKhoan= ''
      this.txtMaAccount= ''
      this.txtEmail= ''
      this.txtNgayBanGiao= ''
      this.txtNgayTao= ''

      //Thông tin khách hàng
      this.txtMaHD= ''
      this.txtTinh= ''
      this.txtLoaiKH= ''
      this.txtTenKH= ''
      this.txtDiaChiKH= ''
      this.txtSoDT= ''

      //Thông tin chi tiết dịch vụ
      this.txtBras= ''
      this.txtBrasIP= ''
      this.txtDSLam= ''
      this.txtTrangThai= ''
      this.txtHinhThucTT= ''
      this.txtGoiCuoc= ''
      this.txtGhiChu= ''
      this.txtDichVuSuDung= ''
      this.txtNasPortID= ''
      this.txtSlotPort= ''
      this.txtIPTinh= ''
      this.txtMaTN= ''
      this.txtTTCuoc= ''
    },
    lay_thongtin_nguoidung: async function () {
      try {
        this.tt_nd = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/thongtin_nguoidung"
        );
        if (rs.data != null) {

        }
      } catch (error) {
        return null;
      } finally {
      }
    },
    TraCuuTB_ADSL: async function () {
       await this.lay_thongtin_nguoidung()
      let login = null
      if (this.tag === 1) {
         login = {
          userId : "khdn_hcm_dhsxkd",
          password :""
        }
      } else {
         login = {
          userId : this.tt_nd.data[0].user_visa,
          password :this.tt_nd.data[0].pass_visa
        }
      }

      let rs = await service.TraCuuThueBao(this,this.txtAccount,login)

      if(rs.errorCode ==1 && rs.faultString){
        this.$root.toastError(rs.faultString);
        return
      }


      if (rs.data.Length <= 1) {
        if (rs.data[0].includes("The system cannot find the file specified"))
          this.$root.toastError("Hệ thống chưa được cài đặt chứng chỉ kết nối VISA");
        else
          this.$root.toastError("Đối tượng dịch vụ không tồn tại!");
      }

      // this.AccountModel = rs.data.lookupRASAccountReturn[0]
      // this.CustomerModel = rs.data.lookupRASAccountReturn[1]
      // this.AcountServiceModel = rs.data.lookupRASAccountReturn[2]
      // this.logObject = rs.data.lookupRASAccountReturn[4]
      this.AccountModel = rs.data[0]
      this.CustomerModel = rs.data[1]
      this.AcountServiceModel = rs.data[2]
      this.logObject = rs.data[4]

      //Hiển thị thông tin lên Control

      //Thông tin tài khoản
      this.txtLoaiTaiKhoan = this.AcountServiceModel.serviceName
      this.txtMaAccount = this.AcountServiceModel.accountId
      this.txtEmail = this.AccountModel.accountName + "@vnn.vn"
      this.txtNgayBanGiao = ""
      this.txtNgayTao = this.AccountModel.createDate
      let pass = this.AccountModel.accountPassword

      //Thông tin khách hàng
      this.txtMaHD = "";
      this.txtTinh = this.CustomerModel.city;
      let customerType = await service.getCustomerType(this,this.CustomerModel.customerType)
      this.txtLoaiKH = customerType.data[0].doituong_id == "-1" ? "" : customerType.data[0].ten_dt;
      this.txtTenKH = this.CustomerModel.firstName;
      this.txtDiaChiKH = this.CustomerModel.address;
      this.txtSoDT = this.CustomerModel.phone;

      //Thông tin chi tiết dịch vụ
      //Hiện chưa ánh xạ được serviceID của hệ thống VDC
      this.dtService = []
      if (this.dtService.length <= 0) {
        //comment ở đây nhớ mở ra nhé ba
        this.dtService = await service.getDtService(this)
      }

      let attribute = this.AcountServiceModel.attributes
      console.log('AcountServiceModel',this.AcountServiceModel.attributes)
      let slot = "";
      let port = "";
      let vpi = "";
      let vci = "";
     for (const item of attribute) {
          let att = item
     //  let result =   this.dtService.includes(item1 )
     //   let result = this.dtService.data.filter(function(elem){
     //     if(elem.key === att.serviceId) {
     //       return elem;
     //     }
     //   })
       //  if (result != null)
       //    att.type = result.type;

        if (att.type === "HD_VDC")
        {
          this.txtMaHD = att.value;
        }
        else if (att.type === "BRAS-ID")
        {
          let bras = att.value;
          let brasitem = await service.getTenBras(this,bras)
          if(brasitem.data.length > 0)
            this.txtBras = brasitem.data[0].tenbras
         //   checkdata.MAP_ID("tenbras", DatabaseConstants.DB2 + ".bras", "where bras_id = '" + bras + "'");
        }
        else if (att.type === "BRAS-IPADDRESS")
        {
          this.txtBrasIP = att.value;
        }
        else if (att.type === "DSLAM-ID")
        {
          let dslam = att.value;
          let dslamItem = await service.getTenDslam(this,dslam)
          if(dslamItem.data.length > 0)
            this.txtDSLam = dslamItem.data[0].tendslam
          //checkdata.MAP_ID("tendslam", DatabaseConstants.DB2 + ".dslam", "where dslam_vdc_id = '" + dslam + "' and dslam_cha_id is null");
        }
        else if (att.type === "SLOT")
        {
          slot = att.value;
        }
        else if (att.type === "PORT")
        {
          port = att.value;
        }
        else if (att.type === "VPI")
        {
          vpi = att.value;
        }
        else if (att.type === "VCI")
        {
          vci = att.value;
        }
        else if (att.type === "STRBILLING")
        {
          this.txtTTCuoc = att.value;
        }
        else if (att.type === "NAS-PORT-ID" || att.type === "NAS-PORT-ID-ID")
        {
          if (att.value !== "0")
            this.txtNasPortID = att.value;
        }

        else if (att.type === "FRAME-IP-ADDRESS" || att.serviceId === 100005738 || att.type === "FRAMED-ROUTE" )
        {
          this.txtIPTinh = att.value;
        }

      }

      let tariffType = this.AcountServiceModel.tariffType;
        let tariffTypeItem= await service.getTariffType(this,tariffType)
      console.log("tariffType",tariffTypeItem)
      this.txtHinhThucTT = tariffTypeItem.data[0].tenmuc
        //checkdata.MAP_ID("tenmuc", DatabaseConstants.DB2 + ".muccuoc", "where muccuoc_id_vdc = " + tariffType);
      this.txtTrangThai = this.AcountServiceModel.status;
      this.txtSlotPort = slot + "/" + port + "/" + vpi + "/" + vci;
      this.txtDichVuSuDung = this.AcountServiceModel.serviceName;

      this.visiblRoll = false

      if (this.AcountServiceModel.cgnat == 1)
      {
        this.lblCGNAT = "Thuê bao chuyển đổi CGNAT";
        this.visiblRoll = false;
      }
      else
        this.lblCGNAT = "";

    },
    clickLichSu : function (){
      if(this.logObject.length >0){
          this.$refs.lsmain.show(this.logObject).then(result => {
          })
        }else {
        this.$root.toastError("Không tồn tại lịch sử tác động.")
      }
    },
    showModal: function() {
      this.TraCuuTB_ADSL()
    },
    tsbtnTraCuu_Click: function (){
      this.TraCuuTB_ADSL()
    },
    tsbtnXemMatKhau_Click: async function () {
      try {
        let login = {}
        if (this.tag === 1) {
          login = {
            userId: "khdn_hcm_dhsxkd",
            password: ""
          }
        } else {
          login = {
            userId: this.tt_nd.data[0].user_visa,
            password: this.tt_nd.data[0].pass_visa
          }
        }


        let rs = await service.TraCuuThueBao(this, this.txtAccount, login)
        if(rs.errorCode ==1 && rs.faultString){
          this.$root.toastError(rs.faultString);
          return
        }

        this.logObject = rs.data[4]
        console.log(this.logObject)
        if(this.logObject.length == 0 ){
          this.$root.toastError("Không thể lấy thông tin mật khẩu.");
          return
        }
        let sPass = ""
        let sPassDangKy = ""
        for(let i = 0 ; i <this.logObject.length ; i ++ ){
          let item = this.logObject[i]
          if(item.actionId == 2812 || item.actionId == 29112025 ){
            sPass = item.actionDesc
            break
          }

          if (item.actionId == 29112018)//action dang ky - cũ
          {
            sPassDangKy = item.actionDesc;
            break

          }
          if (item.actionId == 2823)//action dang ky
          {
            sPassDangKy = item.actionDesc;
            break
          }
        }

        console.log("spass ",sPass)
        console.log("spass dg",sPassDangKy)

         if(sPass == null ){
          return
        }else if(sPass !=""){
           if(sPass.includes("Mật khẩu mới:")){
           //  this.$root.toastSuccess("Mật khẩu của tài khoản là : " + sPass.substring(sPass.indexOf("i: [") +4, sPass.length -1));
             this.matKhauView = sPass.substring(sPass.indexOf("i: [") +4, sPass.length -1) ;

           }

           if(sPass.includes("Mật khẩu thay đổi thành:")){
          //   this.$root.toastSuccess("Mật khẩu của tài khoản là : " + sPass.substring(sPass.indexOf("h: [") +4, sPass.length -1));
             this.matKhauView = sPass.substring(sPass.indexOf("h: [") +4, sPass.length -1) ;
           }
         }else{

         }
        this.$refs['ref-xem-mat-khau'].show()

      } catch (err) {
        console.log(err)
      }
    },tsbtnGetStatus_GRN_Click : async function () {
      let st = await service.GetStatus(this, this.txtAccount)
      console.log('st', JSON.stringify(st.data))
      this.greetnetstatus =  JSON.stringify(st.data)
      this.$refs['ref-xem-green-net'].show()
    },
    closeForm(){
      this.$bvModal.hide("tracuuVdc")
    },
    grid_selectedRowChanged: function(dataItem){
    },
    mutiSplit : function(str,delimiter){
      if (!(delimiter instanceof Array))
        return str.split(delimiter);
      if (!delimiter || delimiter.length == 0)
        return [str];
      var hashSet = new Set(delimiter);
      if (hashSet.has(""))
        return str.split("");
      var lastIndex = 0;
      var result = [];
      for(var i = 0;i<str.length;i++){
        if (hashSet.has(str[i])){
          result.push(str.substring(lastIndex,i));
          lastIndex = i+1;
        }
      }
      result.push(str.substring(lastIndex));
      return result;
    }
  },
  mounted: async function () {


  },
  watch : {

  }
}
</script>

<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

#table-scroll {
  height: 410px;
  overflow: auto;
  margin-top: 20px;
}

</style>
