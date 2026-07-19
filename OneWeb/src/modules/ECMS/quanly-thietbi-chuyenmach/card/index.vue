<template src="./index.template.html"></template>
<script>
var portTemplate = function () {
  return {
    template: {
      template: `
      <div>
        <div class="key vcenter padt0 w30" v-if="data.TT_PORT_ID == 0">
          <span class="shape inline w17 h17" style="border-radius: 2px;background-color: #219653;margin-right: 100px;
    margin-left: 20px;"></span>
          <span>{{data.VITRI}}</span>
        </div>
        <div class="key vcenter padt0 w30" v-else-if="data.TT_PORT_ID == 1">
          <span class="shape inline w17 h17" style="border-radius: 2px;background-color: #0176FF;margin-right: 100px;
    margin-left: 20px;"></span>
          <span>{{data.VITRI}}</span>
        </div>
        <div class="key vcenter padt0 w30" v-if="data.TT_PORT_ID == 3">
          <span class="shape inline w17 h17" style="border-radius: 2px;background-color: #219223;margin-right: 100px;
    margin-left: 20px;"></span>
          <span>{{data.VITRI}}</span>
        </div>
        <div class="key vcenter padt0 w30" v-else-if="data.TT_PORT_ID == 4">
          <span class="shape inline w17 h17" style="border-radius: 2px;background-color: #0176BF;margin-right: 100px;
    margin-left: 20px;"></span>
          <span>{{data.VITRI}}</span>
        </div>
        <div class="key vcenter padt0 w30" v-if="data.TT_PORT_ID == 5">
          <span class="shape inline w17 h17" style="border-radius: 2px;background-color: #2196A3;margin-right: 100px;
    margin-left: 20px;"></span>
          <span>{{data.VITRI}}</span>
        </div>
        
      </div>`,
      data() {
        return {
          data: { }
        }
      },
      
      // computed: {
      //   parent() {
      //     return this.$parent.$parent.$parent.$parent.$parent
      //   },
      //   id(){
      //     return `${this.data.CONG_VAO}`
      //   }
      // },
      // methods:{
      //   onClick() {
      //     this.parent.xemPopupQuanLyCap(this.data.CAPVAO_ID)
      //   }
      // }
    }
  }
}
import { required, numeric, minValue } from "vuelidate/lib/validators";
import XLSX from "xlsx";
export default {
  components: {},
  data: function () {
    return {
      txtSearch: "",
      portTemplate: portTemplate,
      dsDonVi: [],
      dsShelfEwsdFull: [],
      dsCardEwsdFull: [],
      dsPort: [],
      dsNhomCard: [],
      dsLoaiCard: [],
      dsTTPort: [],
      currentItem: {},
      id_shelf: -1,
      enabledSave: false,
      enabledLapCard: false,
      enabledDelete: false,
      enabledExcel: false,
      enabledDoiTT: false,
      enabledTrangThai: true,
      isShowDiv: false,
      ok: true,
      isError: false,
      dataExcelEx: [],
      isCreateNew: false,
      dsSelectedPort: [],
      card_id: -1,
      vitri: "",
      shelf_ewsd_id: 0,
      cardUsed: 0,
      loaicard_id: 0,
      card_ewsd_id:0,
      vnevl_id:0,
      isViTriQU:false,
      test:0
    };
  },
  computed: {},
  watch: {
  },
  validations: {
    currentItem: {
      TEN_SHELF: {
        required,
      },
      SO_SLOT: {
        required,
        numeric,
        minValue: minValue(0),
      },
    },
  },
  created: async function () {},
  methods: {
    bindData: async function (data) {
      try{
      this.iNitForm()
      if(data!=null){

      let  itemID = data.shelf_ewsd_id
      this.card_ewsd_id = data.card_ewsd_id
       this.currentItem.VITRI_QUYUOC =  0
      if(itemID.toString().split('_').length > 1){
      this.shelf_ewsd_id = itemID.toString().split('_')[0]
      this.vitri = itemID.toString().split('_')[1]
      }
      else {
        this.vitri ="0"
        this.shelf_ewsd_id = itemID
        }    
      this.currentItem.VITRI_CAM = "Slot" + " #" + this.vitri
      await this.layDsNhomCard(15);
      
      if(this.dsNhomCard!=null && this.dsNhomCard.length > 0){
      this.currentItem.NHOMCARD_ID = this.dsNhomCard[0].NHOMCARD_ID
      await  this.loadLoaiCard()
      }
      
       if(data.card_ewsd_id != 0){
       await this.ViewCardInfo()
       }else   
        this.currentItem.CARD_EWSD_ID = 0 
       this.currentItem.VITRI =  this.vitri
       this.HT_COMBO_TRANGTHAI()
      }
      }catch(e){
          console.log(e);
      }
    },
    iNitForm: function(){
      this.dsPort = []
      this.dsNhomCard= []
      this.dsLoaiCard= []
      this.dsTTPort= []
      this.currentItem= {}
       this.isViTriQU = false
    },
    ViewCardInfo: async function(){
       let itemData = await this.getDsCardEwsdID( this.card_ewsd_id);
       this.currentItem.VITRI =  this.vitri
       this.vitri=   this.pad(itemData[0].VITRI, 2)
       this.currentItem.NHOMCARD_ID = itemData[0].NHOMCARD_ID
      this.loaicard_id =  itemData[0].LOAICARD_ID
       await this.layDsLoaiCard(this.currentItem.NHOMCARD_ID, 15);
        this.currentItem.VITRI_CAM = "Slot" + " #" + this.vitri
       this.currentItem.LOAICARD_ID =  this.loaicard_id 
       this.currentItem.SOLUONG_PORT =  itemData[0].SO_PORT 
       this.currentItem.VITRI_QUYUOC =  itemData[0].VITRI_QU
       this.currentItem.TEN_CARD =  itemData[0].LOAI_CARD      
       this.currentItem.DANGSUDUNG  =  (await this.getPortInCard(this.card_ewsd_id)).USED;
       await  this.HT_COMBO_TRANGTHAI();
       await  this.HienThiDanhSach();      
    },
    HT_COMBO_TRANGTHAI: async function(){
        this.dsTTPort = await this.layDsTrangThai();
    },
    HienThiDanhSach: async function(){
       this.isViTriQU = true   
      this.dsPort = await this.layPortTheoCard(this.card_ewsd_id);
      if (this.dsPort.length > 0) {
       let tt_port_id = this.dsPort[0].TT_PORT_ID;
        this.currentItem.TT_PORT= tt_port_id,
        this.enabledExcel = true;
      }
    },
    formNhapMoi: function () {
         this.currentItem.VITRI_CAM = ""      
    },
    formGhiLai: async function () {
      try {
        let result = false;
          this.currentItem.TEN_CARD   = this.dsLoaiCard.filter(x=>x.LOAICARD_ID == this.currentItem.LOAICARD_ID)[0].LOAI_CARD
          if (this.Kiemtra_dulieu()) {
            if (this.currentItem.CARD_EWSD_ID == 0) {
              let rs = await this.them(this.currentItem);
              if (rs != null) {
                result = true;
                this.currentItem.CARD_EWSD_ID = rs.CARD_EWSD_ID;
                let r1 = this.dsLoaiCard.find(x => x.LOAICARD_ID == this.currentItem.LOAICARD_ID);
                if(r1 != null)
                  this.currentItem.TEN_CARD = r1.LOAI_CARD;
                await this.$root.context.post(`/web-ecms/thietbichuyenmach/tao-port-ewsd?card_ewsd_id=${this.currentItem.CARD_EWSD_ID}&so_port=${this.currentItem.SOLUONG_PORT}`)
                this.$root.toastSuccess("Thêm mới thành công");
              }
            } else {
              let rs = await this.capNhat(this.currentItem);
              if (rs.result) {
                result = true;
                 this.$root.toastSuccess("Cập nhật thành công");
              }
            }
            // if (this.loaicard_id != this.currentItem.LOAICARD_ID){
            //   let rs = await this.$root.context.post(`/web-ecms/thietbichuyenmach/tao-port-ewsd?card_ewsd_id=${this.currentItem.CARD_EWSD_ID}&so_port=${this.currentItem.SOLUONG_PORT}`)
            // }
          }      
        return result;
      } catch (error) {
        this.$root.toastError("Có lôi khi lắp card ewsd");
      }
    },
    formHuy: function () {
      if (this.currentItem.SHELF_EWSD_ID != undefined) {
        this.currentItem = {};
        this.dsPort = [];
        this.enabledExcel = false;
      } else {
        this.$root.toastError("Bạn chưa chọn thiết bị");
      }
    },
    formXoa: async function () {
      let result = false;
      if (
        !(
          this.shelf_ewsd_id == undefined ||
          this.shelf_ewsd_id == 0
        )
      ) {
        let checkDel = await this.kiemtraXoa(this.card_ewsd_id);
        if(checkDel){
        let rs = await this.xoa(this.card_ewsd_id);
        if (rs.result) {
          result = true;
          this.$root.toastSuccess("Xóa thành công");
        }else{
           this.$root.toastSuccess("Xóa thất bại");
        }
        this.dsPort = [];
        this.enabledExcel = false;
        } 
      } else {
        this.$root.toastError("Bạn chưa chọn thiết bị");
      }
      return result;
    },
    pad: function (str, max) {
      return (Array(max + 1).join("0") + str).slice(-max);
    },
    
    layPortTheoCard: async function (cardEwsdId) {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/layPortTheoCard",
        {
          cardEwsdId: cardEwsdId,
        }
      );
      return result.data;
    },
    layDsNhomCard: async function (id) {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/lay-ds-nhom-card",
        {
          loaitbi_id: id,
        }
      );
      this.dsNhomCard = result.data;
      if(result.data!=null && result.data.length>0){
       this.currentItem.NHOMCARD_ID = result.data[0].NHOMCARD_ID
       await this.loadLoaiCard()
       }
      //return result.data;
    },
    layDsLoaiCard: async function (id1, id2) {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/lay-ds-loai-card",
        {
          nhomcard_id: id1,
          loaitbi_id: id2,
        }
      );
      this.dsLoaiCard = result.data;
      if(result.data!=null && result.data.length>0){
       this.currentItem.LOAICARD_ID = result.data[0].LOAICARD_ID
       this.currentItem.TEN_CARD = result.data[0].LOAI_CARD
      await this.changeLoaiCard();
      }
     // return result.data;
    },
    LayThongTin_Loai_Card: async function (loaicard_id) {
       try{
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/LayThongTin_Loai_Card",
        {
          loaicard_id: loaicard_id,
        }
      );
      if(result.data !=null && result.data.length > 0)
      this.currentItem.SOLUONG_PORT = result.data[0].SO_PORT
      else
      this.currentItem.SOLUONG_PORT = 0
       }catch(e){
           console.log(e)
       }
    },
    changeLoaiCard: async function () {
       try{
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/LayThongTin_Loai_Card",
        {
          loaicard_id: this.currentItem.LOAICARD_ID,
        }
      );
      if(result.data !=null && result.data.length > 0)
      this.currentItem.SOLUONG_PORT = result.data[0].SO_PORT
      else
      this.currentItem.SOLUONG_PORT = 0
      this.test = this.currentItem.SOLUONG_PORT
       }catch(e){
           console.log(e)
       }
     // await  this.LayThongTin_Loai_Card(loaicard_id);
    },
    loadLoaiCard: async function () {
      try{
        await this.layDsLoaiCard(this.currentItem.NHOMCARD_ID, 15);                
      }catch(e){
        console.log(e)
      }
    },
    layDsTrangThai: async function () {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/lay-ds-trangthai-port"
      );
      return result.data;
    },
    getDsCardEwsdID: async function (id) {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/ds-card-theo-shelf-id",
        { shelf_id: id }
      );
      return result.data;
    },
    getDsCardEwsdFull: async function () {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsCardEwsd"
      );
      return result.data;
    },
    getPortInCard: async function (id) {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/lay-soluong-port-card-id",
        { card_id: id }
      );
      return result.data;
    },
    xoa: async function (id) {
      let result = await this.$root.context.post(
        `/web-ecms/thietbichuyenmach/xoaewsdcard?cardEwsdId=${id}`
      );
      return result.data;
    },
    kiemtraXoa: async function (id) {
      try{
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/check-port-logic-delete-card",
        {
          card_ewsd_id: id,
        }
      );
      if (result.data[0].KQ > 0) {
          this.$root.toastError(
            "Card này đã có port được ánh xạ logic. Không thể xóa !"
          );
          return false
        }
      return true;
      }catch(e){
        return false
      }
      
    },
    capNhat: async function (itemData) {
      try{
      let result = await this.$root.context.post(
        "/web-ecms/thietbichuyenmach/capnhatewsdcard",
        {
          loaicard_id: itemData.LOAICARD_ID,
          ten_card: itemData.TEN_CARD,
          shelf_ewsd_id: this.shelf_ewsd_id,
          vitri_qu: itemData.VITRI_QUYUOC,
          card_ewsd_id: itemData.CARD_EWSD_ID,
          vitri: itemData.VITRI,
        }
      );
      return result.data;
      }catch(e){
        this.$root.toastError('Cập nhật thất bại!')
      }
    },
    them: async function (itemData) {
       try{
         let obj = new Object()
         obj  =  {
          card_ewsd_id: 0,
          loaicard_id: itemData.LOAICARD_ID,
          ten_card: itemData.TEN_CARD,
          shelf_ewsd_id: this.shelf_ewsd_id,
          vitri_qu: itemData.VITRI_QUYUOC,
          vitri: itemData.VITRI,
        }
      let result = await this.$root.context.post(
        "/web-ecms/thietbichuyenmach/themewsdcard",obj);
      return result.data;
       }catch(e){
        this.$root.toastError('Thêm mới thất bại!')
      }
    },
    selectedItem: function (donViID) {
      let itemDonVi = this.dsDonVi.find((x) => x.DONVI_ID == donViID);
      if (itemDonVi != null) {
        if (itemDonVi.DONVI_CHA_ID != null) {
          let ps = this.getParents(itemDonVi.DONVI_ID);
          if (!(ps == null || ps.length == 0)) {
            for (let i = 0; i < ps.length; i++) {
              ps[i].Expanded = true;
            }
          }
        }
        itemDonVi.Selected = true;
      }
      let filters = this.dsDonVi.filter(
        (x) => x.Selected == true && x.DONVI_ID != itemDonVi.DONVI_ID
      );
      if (!(filters == null || filters.length == 0)) {
        for (let i = 0; i < filters.length; i++) {
          filters[i].Selected = false;
        }
      }
    },
    btnNhapMoi_Click: function () {
      if (this.currentItem.EWSD_ID != undefined) {
        let ewsdID = this.currentItem.EWSD_ID;
        this.currentItem = {
          SHELF_EWSD_ID: 0,
          TEN_SHELF: "",
          EWSD_ID: ewsdID,
          SO_SLOT: 0,
          DADUNG: 0,
          VITRI: 0,
        };
        this.dsPort = [];
        this.enabledExcel = false;
      } else {
        this.$root.toastError("Bạn chưa chọn thiết bị");
      }
    },
    btnGhiLai_Click: async function () {
      try {
       
        if (this.Kiemtra_dulieu()) {
         
          if (this.currentItem.CARD_EWSD_ID === undefined) {
            this.loading(true);
           
            let rs = await this.them(this.currentItem);
            if (rs != null) {
              this.currentItem.CARD_EWSD_ID = rs.CARD_EWSD_ID;
              let itemID =
                "CARD_EWSD_" + this.currentItem.CARD_EWSD_ID.toString();
              let shelfEwsdData = {
                DONVI_ID: itemID,
                TEN_DV: this.currentItem.TEN_CARD,
                DONVI_CHA_ID: "SHELF_" + this.id_shelf,
                HasChildren: false,
                Expanded: false,
                Selected: false,
                ITEM_TYPE: "CARD_EWSD",
              };
              this.dsDonVi.push(shelfEwsdData);
              //this.selectedItem(itemID);
              this.$root.toastSuccess("Thêm mới thành công");
            }
          } else {
            this.loading(true);

            let rs = await this.capNhat(this.currentItem);
            if (rs.result) {
              let itemData = this.dsCardEwsdFull.find(
                (x) => x.CARD_EWSD_ID == this.currentItem.CARD_EWSD_ID
              );
              if (itemData != null) {
                itemData.CARD_EWSD_ID = this.currentItem.CARD_EWSD_ID;
                itemData.LOAICARD_ID = this.currentItem.LOAICARD_ID;
                itemData.LOAI_CARD = this.currentItem.TEN_CARD;
                itemData.NHOMCARD_ID = this.currentItem.NHOMCARD_ID;
                itemData.SHELF_EWSD_ID = this.currentItem.SHELF_EWSD_ID;
                itemData.VITRI = this.currentItem.VITRI_CAM;
                itemData.VITRI_QU = this.currentItem.VITRI_QUYUOC;
              }
              let itemID =
                "CARD_EWSD_" + this.currentItem.CARD_EWSD_ID.toString();
              let itemDonVi = this.dsDonVi.find((x) => x.DONVI_ID == itemID);
              if (itemDonVi != null) {
                itemDonVi.TEN_DV = this.currentItem.TEN_CARD;
                //this.selectedItem(itemID);
              }
              this.$root.toastSuccess("Cập nhật thành công");
            }
          }
        }
      } finally {
        this.loading(false);
      }
    },
    btnLapCard_Click: async function () {
      this.isShowDiv = true;
      this.isCreateNew = true;
      this.currentItem.SHELF_EWSD_ID = this.shelf_id;
      this.dsPort = [];
      this.enabledExcel = false;
      this.enabledSave = true;
      await this.layDsNhomCard(15);
      if (this.dsNhomCard.length > 0) {
        this.currentItem.NHOMCARD_ID = this.dsNhomCard[0].NHOMCARD_ID;
        this.loadLoaiCard();
      }
      this.enabledDelete = false;
    },
    btnXoa_Click: async function () {
      this.loading(true);
      try {
        if (
          !(
            this.currentItem.CARD_EWSD_ID == undefined ||
            this.currentItem.CARD_EWSD_ID == 0
          )
        ) {
          this.$bvModal
            .msgBoxConfirm("Bạn chắc chắn muốn xóa?", {
              size: "sm",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            })
            .then(async (v) => {
              if (v) {
                let checkDel = await this.kiemtraXoa(
                  this.currentItem.CARD_EWSD_ID
                );
                if (checkDel[0].KQ > 0) {
                  this.$root.toastError(
                    "Card này đã có port được ánh xạ logic. Không thể xóa !"
                  );
                }
                let rs = await this.xoa(this.currentItem.CARD_EWSD_ID);
                if (rs.result) {
                  this.dsCardEwsdFull = this.dsCardEwsdFull.filter(
                    (x) => x.CARD_EWSD_ID != this.currentItem.CARD_EWSD_ID
                  );
                  let itemID =
                    "CARD_" + this.currentItem.CARD_EWSD_ID.toString();
                  this.dsDonVi = this.dsDonVi.filter(
                    (x) => x.DONVI_ID != itemID
                  );
                  this.currentItem = {};
                  this.$root.toastSuccess("Xóa thành công");
                }
                this.dsPort = [];
                this.enabledExcel = false;
              } else {
                this.$root.toastError("Bạn chưa chọn thiết bị");
              }
            })
            .catch((err) => {
              this.$toast.toastError("Xóa thất bại");
            });
        }
      } finally {
        this.loading(false);
      }
    },
    Kiemtra_dulieu: function () {

      if (
        this.currentItem.NHOMCARD_ID === null ||
        this.currentItem.NHOMCARD_ID === -1
      ) {
        this.$root.toastError("Chưa chọn nhóm card !");
        this.$refs.cboNhomCard.focus();
        this.isError = true;
        return false;
      }
      if (
        this.currentItem.LOAICARD_ID === null ||
        this.currentItem.LOAICARD_ID === -1
      ) {
        this.$root.toastError("Chưa chọn loại card !");
        this.$refs.cboLoaiCard.focus();
        this.isError = true;
        return false;
      }
      return true;
    },
    btnExcel_Click: function () {
      try {
        if (this.dsPort.length === 0) {
          this.$toast.error("Không có dữ liệu để xuất Excel !");
          return;
        }
        var ma_thuebao = XLSX.utils.json_to_sheet(Object.values(this.dsPort));
        var wb = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(wb, ma_thuebao, "EWSD_CARD");
        XLSX.writeFile(wb, "export.xlsx");
      } catch (ex) {
        this.$toast.error("Có lỗi trong quá trình xuất file Excel !" + ex);
      }
    },
    btnDoiTT_Click: function () {
      this.capnhatPort();
    },
  
    selectedItemsPort: async function (data) {
      if (data.length > 0) {
        this.enabledDoiTT = true;
        this.enabledTrangThai = false;
      }
      this.dsSelectedPort = [];
      let dsSelected = [];
      dsSelected = [...this.dsPort];
      for (let i = 0; i < data.length; i++) {
        let row = data[i];
        const check = (obj) => obj.NE === row;
        if (dsSelected.some(check)) {
          //Find index of specific object using findIndex method.
          let objIndex = dsSelected.findIndex(check);
          //Update object's name property.
          dsSelected[objIndex].CHON = "1";
        }
      }
      var filtered = dsSelected.filter(function (el) {
        return el.CHON != "0";
      });
      this.dsSelectedPort = filtered;
    },
    capnhatPort: async function () {
      try {
        this.loading(true);
        if (this.dsSelectedPort.length == 0) {
          this.$root.toastError("Bạn chưa chọn port.");
          return;
        }

        let tbkq = "";
        for (let i = 0; i < this.dsSelectedPort.length; i++) {
          let vnevl_id = this.dsSelectedPort[i].NEVL_ID;
          let kq = await this.$root.context.post(
            `/web-ecms/thietbichuyenmach/capnhat-trangthai-port?nevl_id=${vnevl_id}&tt_port_id=${this.currentItem.TT_PORT}`
          );

          if (kq.error_code === "BSS-00000000") {
            if (kq.data !== "OK") tbkq += kq.message_detail;
          }
        }
        if (tbkq === "") {
          this.$root.toastSuccess("Cập nhật trạng thái port thành công !");
        } else {
          this.$root.toastError(tbkq);
        }
        await this.HienThiDanhSach();
      } catch (ex) {
        this.$root.toastError(" " + ex);
      } finally {
        this.loading(false);
      }
    },
    // HienThiDanhSach: async function () {
    //   this.dsTTPort = await this.layDsTrangThai();
    //   this.dsPort = await this.layPortTheoCard(this.shelf_ewsd_id);
    //   let tt_port_id = -1;
    //   if (this.dsPort.length > 0) {
    //     tt_port_id = this.dsPort[0].TT_PORT_ID;
    //     this.enabledExcel = true;
    //   }
    // },
  },
};
</script>
<style scoped> 
.grid-ext .e-grid .e-columnheader .column-check-selected .e-checkbox-wrapper .e-frame.e-stop, .grid-ext .e-grid .e-columnheader .column-check-selected .e-css.e-checkbox-wrapper .e-frame.e-stop {
    background-color: transparent;
    color: #007bff;
    line-height: 15px;
    font-size: 120%;
}
.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
