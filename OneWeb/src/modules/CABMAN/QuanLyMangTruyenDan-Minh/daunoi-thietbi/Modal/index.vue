<template src="./index.html"></template>
<script>
import DanDoModal from '../../../daunoi-thietbi/DDCTruyenDan/Modal'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import Vue from 'vue';
export default {
  components: {DanDoModal,BssErrorMarker,BssRequiredMarker},
  name: 'Modal',
  props: {
    modalShow: Boolean,
    doiTuong: Object,
    id: String
  },

  computed: {
       getField: function()
        {
            return { 
            dataSource: this.tree, 
            id: 'ID', 
            text: 'NAME',
            iconCss:'IMAGE',
            parentID: 'PID',
            hasChildren: 'hasChild' ,
            expanded:'expanded',
            selected: "selected",
        }}
  },
  data () {
    var demoVue = Vue.component("demo", {
    template: "<div><div class='inline' style ='float:left'>{{data.NAME}}</div>"+
                      "<div v-show ='showDIv' style ='float:right;'>"+                  
                      "<div class='inline'><span style ='float:none !important'>{{data.SL_THUEBAO}}</span><div class='e-list-icon' :class='data.ACC' style ='float:none !important' :title='data.TITLE_ACC'></div></div>"+
                      "<div class='inline e-list-icon' :class='data.STATUS' :title='data.TITLE_STS' style ='float:none !important'></div>"+
                      "</div></div>",
    data() {
        return {
        data: {},
         showDIv: false
        };
    },
  async created () {
      if(this.data.STATUS!='')
         this.showDIv =true
        }
      
    });
    return {
        key: 1,
        isShown: false,
        ok:true,
        isError:false,
        checkrdo: 'rdoDon',
        rdgKieuDauNoi: "0",
        ReadOnly: false,
        checkKyHieu: false,
        isDisableBtnNhapMoi: false,
        isDisableBtnCapNhat: false,
        isDisableBtnHuy: false,
        isDisableBtnXoa: false,
        isDisableBtnDanDo: false,
        isDisableBtnHD: false,
        selectedNode: [],
        data1: {},
        Visible: {
            tlpDayNhay: false,
            tlpThuocTinh: false,
            disableTlp: false,
            tlpSoi_1: false,
            tlpSoi_2: false,
        },
        dataCbo: {
            cboTramTB: [],
            cboToKT: [],
            cboTTVT: [],
            cboLoaiTbi: [] ,
            cboLoaiTbi1: [] ,
            cboLoaiTbi2: [] ,
            cboKieuDC: [],
            cboLoaiCap: [],
            cboNoiSX: [],
            cboKyHieu:[],
            speChieuDai: [],
            cboThietbi: [],
            cboThietbi2: [],
            cboCong: [],
            cboCong2: [],
            cboModule2: [],
            cboModule: [],
            cboCard2: [],
            cboCard: [],
            cboShelf2: [],
            cboShelf: [],
            cboFrame2: [],
            cboFrame: []
        },
        modelCbo: {
            mTramTB: -1,
            mToKT: -1,
            mTTVT: -1,
            mLoaiTbi: -1,
            mLoaiTbi1: -1,
            mLoaiTbi2: -1,
            mKieuDc: -1,
            mLoaiCap: -1,
            mNoiSX: -1,
            mKyHieu: -1,
            mChieuDai : -1,
            mCong: '',
            mCong2: '',
            mThietbi: -1,
            mThietbi2: -1,
            mModule2: -1,
            mModule: -1,
            mCard2: -1,
            mCard: -1,
            mShelf2: -1,
            mShelf: -1,
            mFrame2: -1,
            mFrame: -1
        },
        alwaysLoad: false,
        txtTenCap: '',
        txtKyhieu:'',
        dtpNgayLD: new Date(),
        chieudai: 0,
        txtGhiChuDN: '',
        dsThietbi: [],
        dsTBiCon: [],
        dsCongCon: [],
        tree: [],
        fields:'',
        chkModule2: false,
        chkModule: false,
        EnabledChkModule1: true,
        EnabledChkModule2: true,
        radioLoai1: false,
        radioLoai2: false,
        isCreateNew: false,
        port1: '',
        port2: '',
        hidden: {
            port2: false,
            port1: false,
            module2: false,
            module1: false,
            card2: false,
            card1: false,
            shelf2: false,
            shelf1: false,
            frame2: false,
            frame1: false,
            thietbi2: false,
            thietbi1: false,
        },
        error:{ 
          mKyHieu:false,
          txtKyhieu:false,
          txtTenCap :false,
          mLoaiTbi1: false,
          mLoaiTbi2: false,
          mKieuDc: false,
          mLoaiCap: false,
          mNoiSX: false,
          mKyHieu: false,
          mCong: false,
          mCong2: false,
          mThietbi: false,
          mThietbi2: false,
          mModule2: false,
          mModule: false,
          mCard2: false,
          mCard: false,
          mShelf2: false,
          mShelf: false,
          mFrame2: false,
          mFrame: false
        },
        checkVisibleElement: -1,
       // selectedNodes: [],
        expandedNodes: [],
          Template: function(e) {
            return {
                template: demoVue
            };
        }
    }
  },
  watch: {
    'modelCbo.mThietbi': async function(val){
        if(val!=-1)        
      await this.cboThietBi_1_EditValueChanged()      
    },
    'modelCbo.mThietbi2': async function(val){
        if(val!=-1)          
        await this.cboThietBi_2_EditValueChanged()       
    },
    'modelCbo.mFrame': async function(val){
        if(val!=-1)
        await this.cboFrame_1_EditValueChanged()
    },
    'modelCbo.mFrame2': async function(val){
        if(val!=-1)
        await this.cboFrame_2_EditValueChanged()
    },
    'modelCbo.mShelf': async function(val){
        if(val!=-1)
        await this.cboShelf_1_EditValueChanged()
    },
    'modelCbo.mShelf2': async function(val){
        if(val!=-1)
        await this.cboShelf_2_EditValueChanged()
    },
    'modelCbo.mCard': async function(val){
        if(val!=-1)
        await this.cboCard_1_EditValueChanged()
    },
    'modelCbo.mCard2': async function(val){
        if(val!=-1)
        await this.cboCard_2_EditValueChanged()
    },
    'modelCbo.mModule': async function(val){
        if(val!=-1)
        await this.cboModule_1_EditValueChanged()
    },
    'modelCbo.mModule2': async function(val){
        if(val!=-1)
        await this.cboModule_2_EditValueChanged()
    }
  },
  
  methods: {

   //#region "form"
    onHiddenModal() {
      this.isShown = false
      this.key++
      this.resetVisible()
    },

    async onShownModal() {
        this.resetVisible()
        this.isShown = true
        try{
            this.loading(true)
             if(this.doiTuong!=null)
             await this.getDonVi(this.doiTuong.tramtb_id)
             this.NAP_DS_TTVT()
             this.NAP_DS_KIEU_DC()
             this.NAP_DS_LOAI_CAP()
             this.NAP_DS_NOI_SX()           
        }catch(error){

        }finally{
            this.loading(false)
        }
    },

    getDonVi: async function(tram_tbi_id){
         let exch_id = tram_tbi_id
        if (exch_id !== 0)
        {
            let rs = await this.$root.context.post(`/web-cabman/daunoi-thietbi/load-daunoi-tbi?donvi_id=${exch_id}`)
            console.log(rs.data)
            if (rs.data.length > 0)
            {
                 this.modelCbo.mTramTB = rs.data[0].TRAM_ID
                 this.modelCbo.mToKT = rs.data[0].TOKT_ID
                 this.modelCbo.mTTVT = rs.data[0].TTVT_ID
            }
        }
    },
    setVisibleButton: function(isbtnTaoMoi, isbtnGhiLai, isbtnHuy, isbtnXoa, isbtnDanDo){
        this.isDisableBtnNhapMoi = isbtnTaoMoi
        this.isDisableBtnCapNhat = isbtnGhiLai
        this.isDisableBtnHuy = isbtnHuy
        this.isDisableBtnXoa = isbtnXoa
        this.isDisableBtnDanDo = isbtnDanDo
    },
    //#endregion

    //#region "Cổng đi"
    NAP_DS_TTVT: async function(){
        try {
            this.XOA_CAY_THIETBI()
            this.cboTramTB = []
            this.cboToKT = []
            this.cboTTVT = []
          let rs=  await this.$root.context.get('/web-cabman/chon-ketcuoi/lay-ds-ttvt-theo-nv',
            {nhanvien_id: this.$root.token.getNhanVienID()})
                 this.dataCbo.cboTTVT = rs.data
                if(rs.data!=null && rs.data.length>0){               
                if(this.doiTuong.tramtb_id!=null)
                this.modelCbo.mTTVT = rs.data[0].DONVI_ID
                 await this.NAP_DS_TOKT()
                }
           
        } catch (error) {
            this.$root.toastError("Có lỗi xảy ra khi tải danh sách trung tâm viễn thông")
        }
        finally{
        }
    },
    changeTTVT: async function() {
        this.NAP_DS_TOKT()
    },
    changeToKT: async function() {
        this.NAP_DS_TRAMTB()
    },
    changeTramTbi: async function() {
        this.NAP_DS_LOAI_TBI()
    },
    NAP_DS_TOKT: async function(){
        try {
            this.loading(true)
            this.XOA_CAY_THIETBI()
            this.cboTramTB = []
            this.cboToKT = []
            if (this.modelCbo.mTTVT !== -1) {
             let rs=   await this.$root.context.get('/web-cabman/chon-ketcuoi/lay-ds-tokt-theo-nv',
                {ttvt_id: this.modelCbo.mTTVT, nhanvien_id: this.$root.token.getNhanVienID()})
                    this.dataCbo.cboToKT = rs.data
                    if(rs.data!=null && rs.data.length>0){                      
                        if(this.doiTuong.tramtb_id!=null)
                        this.modelCbo.mToKT = rs.data[0].DONVI_ID
                        await this.NAP_DS_TRAMTB()
                    }
            }
        } catch (error) {
            this.$root.toastError("Có lỗi xảy ra khi tải danh sách tổ kỹ thuật")
        }
        finally{
            this.loading(false)
        }
    },
    NAP_DS_TRAMTB: async function(){
        try {
            this.loading(true)
            this.XOA_CAY_THIETBI()
            this.cboTramTB = []
            if (this.modelCbo.mToKT !== -1) {
                let tokt_id = this.modelCbo.mToKT
              let rs=  await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-tram-tbi',
                {tokt_id: tokt_id})
                  this.dataCbo.cboTramTB = rs.data
                    if(rs.data!=null && rs.data.length>0){
                  
                    if(this.doiTuong.tramtb_id!=null)
                    this.modelCbo.mTramTB = rs.data[0].DONVI_ID
                    await this.NAP_DS_LOAI_TBI()
                    }
             
            }
        } catch (error) {
            this.$root.toastError("Có lỗi xảy ra khi tải danh sách trạm thiết bị")
        }
        finally{
            this.loading(false)
        }
    },
  
    NAP_DS_LOAI_TBI: async function(){
        try {
            this.loading(true)
            this.XOA_CAY_THIETBI()
            this.dataCbo.cboLoaiTbi = []
            this.dataCbo.cboLoaiTbi1 = []
            this.dataCbo.cboLoaiTbi2 = []
            if (this.modelCbo.mTramTB !== -1)
            {
                let donvi_id = this.modelCbo.mTramTB
                let rs = await this.$root.context.get(`/web-cabman/daunoi-thietbi/lay-ds-loai-tbi?donvi_id=${donvi_id}`)
                // Loại thiết bị cổng đi
                this.dataCbo.cboLoaiTbi = rs.data
                //Minh thêm
                this.dataCbo.cboLoaiTbi1 = rs.data
                this.dataCbo.cboLoaiTbi2 = rs.data
                if(rs.data){
                    if (rs.data.length > 0) {                    
                        this.modelCbo.mLoaiTbi = rs.data[0].LOAITBI_ID
                        this.modelCbo.mLoaiTbi2 = rs.data[0].LOAITBI_ID
                        this.changeLoaiThietBi()
                    }
                }
            }
        } catch (error) {
            this.$root.toastError(''+error)
        }
        finally{
            this.loading(false)
        }
    },
    addTree:function(items){
      let ds =[]
      if(items!=null){
          for(let i=0;i<items.length;i++){
              let node=new Object
              let haschild = (items[i].HAS_CHILD==1 || items[i].HAS_PORT==1)||
              (items[i].CO_CON==1 || items[i].CO_PORT==1)
              let sl_thuebao = ''
              let icon_status = ''
              let icon_acc = ''             
              let title_status = 'Trạng thái đấu nối\n'
              let title_acc =''
              let image = items[i].IMAGE !=null ? items[i].IMAGE:""
              if(image.startsWith("PORT")){
                  if(items[i].STATUS != null){
                    
                   icon_status = "STATUS_"+items[i].STATUS
                   let sts = items[i].STATUS.split('')
                    title_status+= sts[0] =='1'? "Đã đấu cáp vào\n" : "Chưa đấu cáp vào\n"
                    title_status+= sts[1] =='1'? "Đã đấu cáp ra\n" : "Chưa đấu cáp ra\n"
                   }
                  if(items[i].ACC != null){
                   icon_acc = 'ACCOUNT'
                   sl_thuebao = items[i].ACC.toString().split(',').length
                   
                   title_acc += 'Danh sách thuê bao\n'+ items[i].ACC
                  }
              }
              
              node.ID =     items[i].ID
              node.PID=     items[i].PID
              node.IMAGE =  image.toLowerCase()
              node.NAME =   items[i].NAME
              node.ACC  =   icon_acc.toLowerCase()
              node.SL_THUEBAO = sl_thuebao
              node.TITLE_STS = title_status
              node.TITLE_ACC = title_acc
              node.STATUS = icon_status.toLowerCase()
              node.hasChild= haschild
              if(this.tree.findIndex(x=>x.ID ==node.ID)==-1)
              ds.push(node)
          }
          this.tree =this.tree.concat(ds)
      }
    },
    NAP_DS_THIETBI: async function(){
        try {
            this.XOA_CAY_THIETBI()
            this.cboTramTB = []
            this.cboToKT = []
            this.cboTTVT = []
            let tramtb_id = this.modelCbo.mTramTB
            let loaitbi_id = this.modelCbo.mLoaiTbi
            await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-tbi',
            {donvi_id: tramtb_id,loaitbi_id:loaitbi_id })
            .then(rs =>{
                this.dsThietbi = rs.data
            })
        } catch (error) {
            this.$root.toastError(error)
        }
        finally{
        }
    },
    changeLoaiThietBi: async function(){
        try {
            this.loading(true)
            await this.NAP_DS_THIETBI()
            let dsThietbi = this.dsThietbi
            this.addTree(dsThietbi)
             if (dsThietbi != null && dsThietbi.length > 0) {
                 await this.getDuLieuThietBiConVaCong(dsThietbi)              
             }
        } catch (error) {
            this.$root.toastError(' '+error)
        }
        finally{
            this.loading(false)
        }
    },
    XOA_CAY_THIETBI: function(){
        this.dsThietbi=[]
        this.dataCbo.cboKyHieu = []
        this.tree=[]
        this.XOA_THONGTIN_DAYNHAY();
    },
    tree_nodeExpanding: async function (args) {
        try{
      }
     finally{
     }
    },
    resetSoi1:function(){
            this.modelCbo.mCong= -1
             this.modelCbo.mThietbi= -1
             this.modelCbo.mModule= -1
             this.modelCbo.mCard= -1
             this.modelCbo.mShelf= -1
             this.modelCbo.mFrame= -1
    },
     resetSoi2:function(){
             this.modelCbo.mCong2= -1
             this.modelCbo.mThietbi2= -1
             this.modelCbo.mModule2= -1
             this.modelCbo.mCard2= -1
             this.modelCbo.mShelf2= -1
             this.modelCbo.mFrame2= -1
    },
    nodeclicked: async function(args) {
         this.XOA_THONGTIN_DAYNHAY()
         this.resetError()
        var pid = args.nodeData.id.split('-')
        this.isCreateNew = false
        if (pid.length == 3)
        {
            let currentNode = args.nodeData
            let arraySelected = this.tree
            this.selectedNode = []
            const check = (obj => obj.ID === currentNode.id)
            if (arraySelected.some(check)){
                let objIndex = arraySelected.findIndex(check)
                this.selectedNode.push(arraySelected[objIndex])
            }
            await this.NAP_DS_DAYNHAY(pid);
          
        }
        else
        {
            this.checkKyHieu = false
            this.Visible.tlpDayNhay = false
            this.Visible.tlpThuocTinh = false
            this.dataCbo.cboKyHieu = []         
            this.setVisibleButton(false,false, false, false, false)
        }
    },
    getDuLieuThietBiCon: async function(id){
       let node = this.getNode(id)
       if(node){
             if (node.HAS_CHILD === 1 || node.CO_CON === 1){
                await this.NAP_DS_THIETBI_CON(node)
                let dsTbicon= [...this.dsTBiCon]
                if (this.dsTBiCon.length > 0) {
                    this.addTree(dsTbicon)
                    // await this.getDuLieuThietBiConVaCong(dsTbicon)      
                }
            }
            if (node.HAS_PORT === 1 || node.CO_PORT === 1){
                const [loaitbi_id, thietbi_id] =  node.ID.split('-')
                await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-cong-con',
                { kieu: 1, loaitbi_id: loaitbi_id, thietbi_id:thietbi_id })
                .then(res =>{
                    if(res.data != null && res.data.length > 0){
                         this.addTree(res.data)
                    }
                })
                await this.NAP_DS_THUEBAO_CONG(node.ID)
            }   
       }
    },
    getDuLieuThietBiConVaCong: async function(dsTbiTram){
        for(let i = 0; i < dsTbiTram.length;i++){
             if (dsTbiTram[i].HAS_CHILD === 1 || dsTbiTram[i].CO_CON === 1){
                await this.NAP_DS_THIETBI_CON(dsTbiTram[i])
                let dsTbicon= [...this.dsTBiCon]
                if (this.dsTBiCon.length > 0) {
                    this.addTree(dsTbicon)
                     await this.getDuLieuThietBiConVaCong(dsTbicon)      
                }
            }
            if (dsTbiTram[i].HAS_PORT === 1 || dsTbiTram[i].CO_PORT === 1){
                const [loaitbi_id, thietbi_id] =  dsTbiTram[i].ID.split('-')
                await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-cong-con',
                { kieu: 1, loaitbi_id: loaitbi_id, thietbi_id:thietbi_id })
                .then(res =>{
                    if(res.data != null && res.data.length > 0){
                         this.addTree(res.data)
                    }
                })
            }   
        }
    },
    createTreeThietbi(ds){
      let tree = []
      let idChaArray = ds.map(item => item.PID)
      let idArray = ds.map(item => (item.ID))
      ds.forEach(item => {
        //check item la parent node
        if(idChaArray.includes(item.ID)){
          //check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id
          if(!idArray.includes(item.PID)){
            delete item.PID
          }
          tree.push( {...item, hasChild:true, expanded: false})
        }else{
          tree.push(item)
        }
      })
      return tree
    },
    loadThongTinThuocTinh: function(){
        // Hiển thị thông tin cáp
        if (this.dataCbo.cboKyHieu.length > 0) {
            let selectedCap = this.dataCbo.cboKyHieu[0]
            this.txtTenCap = selectedCap.TEN_CAPTD
            this.rdgKieuDauNoi = selectedCap.SODOIDAY == 1 ? "0" : "1"
            this.modelCbo.mKieuDc = selectedCap.KIEUDC_ID
            this.modelCbo.mLoaiCap = selectedCap.LOAICAP_ID
            this.modelCbo.mNoiSX = selectedCap.NOISX_ID
            this.chieudai = selectedCap.CHIEUDAI
            this.dtpNgayLD = selectedCap.NGAY_LD
            this.txtGhiChuDN = selectedCap.GHICHU
            if (!this.isCreateNew){
                if (this.rdgKieuDauNoi === '0') {
                    this.radioLoai2 = true
                    this.radioLoai1 = false
                }
                else if (this.rdgKieuDauNoi === '1') {
                    this.radioLoai2 = false
                    this.radioLoai1 = true
                }
            }
        }
        
    },   
    NAP_DS_THIETBI_CON: async function(node){
        try {
            if (node !== null)
            {
                const [loaitbi_id, thietbi_id] =  node.ID.split('-')
                await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-tbi-con',
                {loaitbi_id: loaitbi_id,thietbi_id:thietbi_id })
                .then(rs =>{
                    this.dsTBiCon = rs.data
                })
            }
        }
        catch (error) {
            this.$root.toastError(error)
        }
        finally{
        }
    },
    NAP_DS_CONG_CON: async function(node){
      try {
            if (node !== null)
            {
                const [loaitbi_id, thietbi_id] =  node.ID.split('-')
                if (node.HAS_PORT === "1")
                {
                    let rs = await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-cong-con',{ kieu: 1, loaitbi_id: loaitbi_id, thietbi_id:thietbi_id })
                    if (rs.data.length > 0) {
                        this.dsCongCon = rs.data
                        return rs.data
                    }
                  //  await this.NAP_DS_THUEBAO_CONG(pid);
                }
            }

            //tvThietBi.MakeNodeVisible(node);
            //tvThietBi.FocusedNode = node;
        }
        catch (error) {
            this.$root.toastError(error)
        }
        finally{
        }
    },
    NAP_LAI_CONG: async function(p0, p1, p2){
        try {
            this.loading(true)
            let data_json = []
            if (p0.length == 3)
            {
                const [lt, tb, vt] = p0
                let data = {
                    lt: lt,
                    tb: tb,
                    vt: vt
                }
                data_json.push(data)
            }
            if (p1.length == 3)
            {
                const [lt, tb, vt] = p1
                let data = {
                    lt: lt,
                    tb: tb,
                    vt: vt
                }
                data_json.push(data)
            }
            if (p2.length == 3)
            {
                const [lt, tb, vt] = p2
                let data = {
                    lt: lt,
                    tb: tb,
                    vt: vt
                }
                data_json.push(data)
            }
            
            let rs = await this.$root.context.post('/web-cabman/daunoi-thietbi/lay-thongtin-cong1',data_json)
            if (rs.data.length > 0){
                for(let dr in rs.data){
                    let arraySelected = this.tree
                    const check = (obj => obj.ID === dr.ID)
                    if (arraySelected.some(check)){
                        let objIndex = arraySelected.findIndex(check)
                        let node = arraySelected[objIndex]
                        if (node !== null){
                            //update node infor
                            // for (int i = 0; i < tvThietBi.Columns.Count; i++)
                            // {
                            //     var field = tvThietBi.Columns[i].FieldName;
                            //     node.SetValue(field, dr[field]);
                            // }
                            }
                        }
                    }
            }
        } catch (error) {
            this.$root.toastError(error)
        }
        finally{
            this.loading(false)
        }
    },
    getNode:  function(id){
    return this.dsThietbi.filter(x=>x.ID == id)[0]
    },
    //#endregion

    //#region "CRUD"
    btnTaoMoi: async function(){
       // this.resetVisible()
        this.checkKyHieu = false
        this.isCreateNew = true
        this.isDisableBtnNhapMoi = false
        this.isDisableBtnCapNhat = true
        this.isDisableBtnHuy = true
        this.XOA_THONGTIN_DAYNHAY()
        this.Visible.tlpDayNhay =true
        this.Visible.tlpThuocTinh = true
        // Chọn loại dây nhảy
        let currentNode = this.selectedNode 
        let kieu_dn=''
        if(currentNode[0].IMAGE.split('_').length==3)
          kieu_dn=currentNode[0].IMAGE.split('_')[2]
        if (kieu_dn =='2')
        {
            this.rdgKieuDauNoi = "1"
            this.Visible.tlpSoi_2  = true
        }
        else
        {
            this.rdgKieuDauNoi = "0"
            this.Visible.tlpSoi_2 = false
        }

        // Reset khung chọn thiết bị 1 về mặc định
        this.Visible.tlpSoi_1 = true;
        this.dataCbo.cboLoaiTbi1 = []
        this.hidden.thietbi1 = true
        this.hidden.frame1 = true
        this.hidden.shelf1 = true
        this.hidden.card1 = true
        this.hidden.module1 = true
        this.hidden.port1 = true
        this.dataCbo.cboThietbi = []
        this.dataCbo.cboCong = []
        this.dataCbo.cboModule = []
        this.dataCbo.cboCard = []
        this.dataCbo.cboShelf = []
        this.dataCbo.cboFrame = []
        this.chkModule = true

        // Reset khung chọn thiết bị 2 về mặc định
        this.dataCbo.cboLoaiTbi2 = []
        this.hidden.thietbi2 = true
        this.hidden.frame2 = true
        this.hidden.shelf2 = true
        this.hidden.card2 = true
        this.hidden.module2 = true
        this.hidden.port2 = true
        this.dataCbo.cboThietbi2 = []
        this.dataCbo.cboCong2 = []
        this.dataCbo.cboModule2 = []
        this.dataCbo.cboCard2 = []
        this.dataCbo.cboFrame2 = []
        this.dataCbo.cboShelf2 = []
        this.chkModule2 = true

        //this.NAP_DS_LOAI_TBI()
        this.LOAD_TBI_CONG1()
        this.LOAD_TBI_CONG2()
        // Tắt chọn trên combo ký hiệu và focus
        this.txtKyhieu = ''
    },
    resetError: function(){
    this.error={ 
          mKyHieu:false,
          txtKyhieu:false,
          txtTenCap :false,
          mLoaiTbi1: false,
          mLoaiTbi2: false,
          mKieuDc: false,
          mLoaiCap: false,
          mNoiSX: false,
          mKyHieu: false,
          mCong: false,
          mCong2: false,
          mThietbi: false,
          mThietbi2: false,
          mModule2: false,
          mModule: false,
          mCard2: false,
          mCard: false,
          mShelf2: false,
          mShelf: false,
          mFrame2: false,
          mFrame: false
        }
    },
    KIEMTRA: function(){
        this.resetError()
        let mess = []
        let flag = false
        if (!this.checkKyHieu) {
            if (this.txtKyhieu === '')
            {
                if(!flag){
                this.$refs.refKyhieu2.focus()
                flag = true
                }
                this.error.txtKyhieu = true
                mess.push("Bạn chưa nhập ký hiệu dây nhảy");
            }
        }
        
        if (this.txtTenCap === '')
        {
            if(!flag){
               this.$refs.refTencap.focus()
                flag = true
            }          
            this.error.txtTenCap = true
            mess.push("Bạn chưa nhập tên dây nhảy");
        }

        if (this.modelCbo.mKieuDc === -1)
        {
            if(!flag){
            this.$refs.refKieudc.focus()
              flag = true
            }    
            this.error.mKieuDc = true
            mess.push("Bạn chưa chọn kiểu đầu cắm");
        }

        if (this.modelCbo.mLoaiCap === -1)
        {
            if(!flag){
            this.$refs.refLoaicap.focus()
            flag = true
            }  
             this.error.mLoaiCap = true
            mess.push("Bạn chưa chọn loại dây nhảy");
        }

        if (this.modelCbo.mNoiSX === -1)
        {
              if(!flag){
            this.$refs.refNoisx.focus()
            flag = true
            }  
            this.error.mNoiSX = true
           mess.push("Bạn chưa chọn nơi sản xuất");
        }

        if (this.Visible.tlpSoi_1)
        {
            if (this.modelCbo.mLoaiTbi1 === -1) {
                  if(!flag){
                this.$refs.refloaithietbi1.focus()
                flag = true
            }  
                mess.push("Bạn chưa chọn loại thiết bị kết nối từ sợi 1")
                this.error.mLoaiTbi1 = true
            }
            if (this.hidden.thietbi1 && this.modelCbo.mThietbi === -1) {
                if(!flag){
                this.$refs.refthietbi1.focus()
                flag = true
            }  
               mess.push("Bạn chưa chọn thiết bị kết nối từ sợi 1")
                this.error.mThietbi = true
            }
            if (this.hidden.frame1 && this.modelCbo.mFrame === -1) {
             if(!flag){
                this.$refs.refframe1.focus()
                 flag = true
               }  
              mess.push("Bạn chưa chọn Frame kết nối từ sợi 1")
                this.error.mFrame = true
            }
            if (this.hidden.shelf1 && this.modelCbo.mShelf === -1) {
             mess.push("Bạn chưa chọn Shelf kết nối từ sợi 1")
                this.error.mShelf = true
                 if(!flag){
                this.$refs.refshelf1.focus()
                 flag = true
                }  
            }
            if (this.hidden.card1 && this.modelCbo.mCard === -1) {
                 if(!flag){
                this.$refs.refcard1.focus()
                flag = true
                } 
             mess.push("Bạn chưa chọn Card kết nối từ sợi 1")
                this.error.mCard = true
            }
            if (this.hidden.module1 && this.modelCbo.mModule === -1) {
                 if(!flag){
                this.$refs.refmodule1.focus()
                flag = true
                } 
            mess.push("Bạn chưa chọn Module kết nối từ sợi 1")
                this.error.mModule = true
            }
            if (this.hidden.port1 && (this.modelCbo.mCong === -1||this.modelCbo.mCong =="")) {
                 if(!flag){
                this.$refs.refcong1.focus()
                 flag = true
                } 
            mess.push("Bạn chưa chọn Cổng kết nối từ sợi 1");
                this.error.mCong = true
            }
        }

        if (this.Visible.tlpSoi_2)
        {
            if (this.modelCbo.mLoaiTbi2 === -1) {
                    if(!flag){
                this.$refs.refloaithietbi2.focus()
                 flag = true
                } 
              mess.push("Bạn chưa chọn loại thiết bị kết nối từ sợi 2");
                this.error.mLoaiTbi2 = true
            }
            if (this.hidden.thietbi2 && this.modelCbo.mThietbi2 === -1) {
                    if(!flag){
                this.$refs.refthietbi2.focus()
                 flag = true
                } 
             mess.push("Bạn chưa chọn thiết bị kết nối từ sợi 2");
                this.error.mThietbi2 = true
            }
            if (this.hidden.frame2 && this.modelCbo.mFrame2 === -1) {
              if(!flag){
                this.$refs.refframe2.focus()
                 flag = true
                } 
              mess.push("Bạn chưa chọn Frame kết nối từ sợi 2");
                this.error.mFrame2 = true
            }
            if (this.hidden.shelf1 && this.modelCbo.mShelf2 === -1) {
                if(!flag){
                this.$refs.refshelf2.focus()
                 flag = true
                } 
             mess.push("Bạn chưa chọn Shelf kết nối từ sợi 2");
                this.error.mShelf2 = true
            }
            if (this.hidden.card2 && this.modelCbo.mCard2 === -1) {
                 if(!flag){
                this.$refs.refcard2.focus()
                 flag = true
                } 
                mess.push("Bạn chưa chọn Card kết nối từ sợi 2")
                this.error.mCard2 = true
            }
            if (this.hidden.module2 && this.modelCbo.mModule2 === -1) {
                 if(!flag){
                this.$refs.refmodule2.focus()
                  flag = true
                } 
             mess.push("Bạn chưa chọn Module kết nối từ sợi 2");
                this.error.mModule2 = true
            }
            if (this.hidden.port2 && (this.modelCbo.mCong2 === -1||this.modelCbo.mCong2 =="")) {
                 if(!flag){
                this.$refs.refcong2.focus()
                  flag = true
                } 
             mess.push("Bạn chưa chọn Cổng kết nối từ sợi 2");
                this.error.mCong2 = true
            }
        }
        if(mess.length>0) {
            this.$root.toastError(mess.join("\n"))
            return false}
        return true;
    },
    resetVisible: function(){
        this.Visible.tlpDayNhay = false
        this.Visible.tlpThuocTinh = false
        this.Visible.disableTlp = false
        this.Visible.tlpSoi_1 = false
        this.Visible.tlpSoi_2 = false
    },
    btnGhiLai: async function(){
        if (this.KIEMTRA())
            this.GHILAI()
    },
    GHILAI: async function(){
        try
            {
                let data_json = []
                let id_cap = ''
                let kyhieu = ''
                if (this.checkKyHieu){
                    id_cap = this.modelCbo.mKyHieu
                    kyhieu = this.dataCbo.cboKyHieu.filter(x=>x.CAPTD_ID ==this.modelCbo.mKyHieu)[0].KYHIEU
                }
                else {
                    id_cap = -1
                    kyhieu = this.txtKyhieu
                }
                let data = {
                    id: id_cap,
                    kh: kyhieu,
                    td: this.txtTenCap,
                    dd: this.rdgKieuDauNoi,
                    dc: this.modelCbo.mKieuDc,
                    ld: this.modelCbo.mLoaiCap,
                    sx: this.modelCbo.mNoiSX,
                    cd: this.chieudai,
                    nl: this.convertDate2String(this.dtpNgayLD),
                    gc: this.txtGhiChuDN,
                }
                let p0 = this.selectedNode[0].ID.split('-')
                const [lt, tb,po] =  this.selectedNode[0].ID.split('-')
                if (p0.length == 3)
                {
                    data["lt1"] = lt
                    data["tb1"] = tb
                    data["vt1"] = po
                }
                else {
                    data["lt1"] = ''
                    data["tb1"] = ''
                    data["vt1"] = ''
                }
                
                let p1 = []
                if (this.Visible.tlpSoi_1 && this.dataCbo.cboCong.length > 0)
                {
                    p1 = this.modelCbo.mCong !== '' ? this.modelCbo.mCong.split('-') : ''
                    if (p1.length == 3)
                    {
                        const [lt, tb, vt] = p1
                        data["lt2"] = lt
                        data["tb2"] = tb
                        data["vt2"] = vt
                    }
                    else {
                        data["lt2"] = ''
                        data["tb2"] = ''
                        data["vt2"] = ''
                    }
                    
                }

                var p2 = []
                if (this.Visible.tlpSoi_2 && this.dataCbo.cboCong2.length > 0)
                {
                    p2 = this.modelCbo.mCong2 !== '' ? this.modelCbo.mCong2.split('-') : ''
                    if (p2.length == 3)
                    {
                        const [lt, tb, vt] = p2
                        data["lt3"] = lt
                        data["tb3"] = tb
                        data["vt3"] = vt
                    }
                    else {
                        data["lt3"] = ''
                        data["tb3"] = ''
                        data["vt3"] = ''
                    }
                    
                }
                data_json.push(data)
                await this.$root.context.post('/web-cabman/daunoi-thietbi/capnhat-dl-daynhay',data_json)
                if (!this.isDisableBtnNhapMoi)
                    this.$root.toastSuccess("Tạo dây nhảy thành công")
                else
                    this.$root.toastSuccess("Cập nhật dữ liệu thành công")

                this.NAP_LAI_CONG(p0, p1, p2)
                this.REFOCUS_CONG();
            }
            catch (ex)
            {
                if (ex.response.data.message_detail.startsWith("ORA-20105"))
                    this.$root.toastError(ex.response.data.message_detail.substring(0,11))
                else
                    this.$root.toastError("Lỗi khi cập nhật dữ liệu dây nhảy\r\n" + ex.response.data.message_detail);
            }
    },
    btnHuy: async function(){
       this.resetError()
       this.Visible.tlpDayNhay = false
       this.Visible.tlpThuocTinh = false
       this.Visible.tlpSoi_2  = false
       this.Visible.tlpSoi_1  = false
    },
    btnXoa: async function(){
        this.$bvModal.msgBoxConfirm("Bạn có thực sự muốn xóa dây nhảy đang chọn không ?",
            {
                title: "Xác nhận hành động",
                centered: true,
                size: "sm",
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
                }).then( async (value) => {
                if (value)
                {
                   await this.XOA()
                }
                else{
                    return
                }
            })
    },
    XOA: async function(){
        try {
            this.loading(true)
            // Xóa dây nhảy
            let captd_id = this.modelCbo.mKyHieu
            await this.$root.context.post(`/web-cabman/daunoi-thietbi/xoa-cap-td?captd_id=${captd_id}`)
            this.$root.toastSuccess("Xóa dây nhảy thành công")

            // Nạp lại cổng
            let currentNode = this.selectedNode
            let p0 = currentNode[0].ID.split('-')
            let p1 = this.port1 !== '' ? this.port1.split('-') : ''
            let p2 = this.port2  !== '' ? this.port2.split('-') : ''

            await this.NAP_LAI_CONG(p0, p1, p2);
            this.REFOCUS_CONG();
        } catch (error) {
            this.$root.toastError("Xóa dây nhảy thất bại")
        }
        finally{
            this.loading(false)
        }
    },
    btnDanDo: function(){
        var pid = this.selectedNode[0].ID.split('-')
        const [pid0, pid1, pid2] = this.selectedNode[0].ID.split('-')
        if (pid.length == 3)
        {
            this.data1 = {
                pid0: pid0,
                pid1: pid1,
                pid2: pid2,
                tramtb: this.modelCbo.mTramTB,
                tokt: this.modelCbo.mToKT,
                ttvt: this.modelCbo.mTTVT,
                loaitbid: this.modelCbo.mLoaiTbi
            }
            this.$bvModal.show('popupDanDo')
        }
    },
    btnHuongDan: function(){
        this.$root.toastSuccess("Hướng dẫn sử dụng")
    },
    //#endregion
    
    //#region "Cổng đến"
    LOAD_TBI_CONG1: function(){
        // Loại thiết bị cổng đến 1
        let loaitb1 = this.dataCbo.cboLoaiTbi.map(item => { return {...item}}) //[...ds1]
        if (loaitb1.length > 0) {
            this.dataCbo.cboLoaiTbi1 = loaitb1
        }
    },
    LOAD_TBI_CONG2: function(){
        // Loại thiết bị cổng đến 2
        let loaitb2 = this.dataCbo.cboLoaiTbi.map(item => { return {...item}}) //[...ds1]
        if (loaitb2.length > 0) {
            this.dataCbo.cboLoaiTbi2 = loaitb2
        }
    },
    REFOCUS_CONG: async function(){
        //this.nodeclicked()
        this.NAP_DS_LOAI_TBI()
    },
    XOA_THONGTIN_DAYNHAY: async function(){
        this.txtTenCap =  ''
        this.rdgKieuDauNoi = "0"
        this.modelCbo.mLoaiCap =null
        this.modelCbo.mKieuDc=null
        this.modelCbo.mNoiSX=null
        this.chieudai = 0
        this.dtpNgayLD = null
        this.txtGhiChuDN = ''
        this.modelCbo.mLoaiTbi1=-1
        this.modelCbo.mLoaiTbi2=-1
        this.Visible.tlpSoi_1 = false
        this.Visible.tlpSoi_2 = false
       
    },
    NAP_DS_KIEU_DC: async function(){
        try {
           let rs= await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-kieu-dc')
                this.dataCbo.cboKieuDC = rs.data
        } catch (error) {
            this.$root.toastError(error)
        }
        finally{
        }

    },
    NAP_DS_LOAI_CAP: async function(){
        try {
        let rs=    await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-loai-cap')
                this.dataCbo.cboLoaiCap = rs.data
        } catch (error) {
            this.$root.toastError(error)
        }
        finally{
        }
    },
    NAP_DS_NOI_SX: async function(){
        try {
          let rs=  await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-noi-sx')
                this.dataCbo.cboNoiSX = rs.data
        } catch (error) {
            this.$root.toastError(error)
        }
        finally{
        }
    },
    NAP_DS_DAYNHAY: async function(pid){
        try {
            this.XOA_THONGTIN_DAYNHAY()
            this.dataCbo.cboKyHieu= []
            const [loaitbi_id, thietbi_id,vitri] =  pid
          let rs=  await this.$root.context.get(`/web-cabman/daunoi-thietbi/lay-ds-daynhay-cong?loaitbi_id=${loaitbi_id}&thietbi_id=${thietbi_id}&vitri=${vitri}`)
            if(rs.data!=null && rs.data.length>0){
                this.dataCbo.cboKyHieu = rs.data
                if (rs.data.length > 0){
                  this.modelCbo.mKyHieu = rs.data[0].CAPTD_ID
                    this.Visible.tlpDayNhay = this.dataCbo.cboKyHieu.length > 0 ? true : false
          //  this.loadThongTinThuocTinh()
                   this.Visible.tlpThuocTinh = this.Visible.tlpDayNhay
                    this.checkKyHieu = true
                    this.setVisibleButton(true,this.Visible.tlpDayNhay,
                                     this.Visible.tlpDayNhay,
                                     this.Visible.tlpDayNhay,
                                     this.Visible.tlpDayNhay)
                    this.changeKyHieu()
                }
            }
        } catch (error) {
            this.$root.toastError(' '+error)
        }
        finally{
        }
    },
    changeKyHieu: async function(){
            try
            {
                let dsKyHieu = this.dataCbo.cboKyHieu
                let selectedDslam = dsKyHieu.filter(x=>x.CAPTD_ID==this.modelCbo.mKyHieu)[0]
                if (dsKyHieu.length > 0) {
                    if (selectedDslam != null) {
                    console.log('selectedDslam',selectedDslam)
                        // Hiển thị thông tin cáp
                        this.txtTenCap = selectedDslam.TEN_CAPTD
                        this.rdgKieuDauNoi = selectedDslam.SODOIDAY ==2 ? '1' : '0'
                        this.modelCbo.mKieuDc = selectedDslam.KIEUDC_ID
                        this.modelCbo.mLoaiCap = selectedDslam.LOAICAP_ID
                        this.modelCbo.mNoiSX = selectedDslam.NOISX_ID
                        this.chieudai = selectedDslam.CHIEUDAI
                        this.dtpNgayLD = selectedDslam.NGAY_LD
                        this.txtGhiChuDN = selectedDslam.GHICHU
                        // Hiển thị danh sách đấu nối sợi
                        this.Visible.tlpSoi_1 = true
                        this.Visible.tlpSoi_2 = this.rdgKieuDauNoi == '1'

                        // Hiển thị thiết bị kết nối đến
                        const [loaitbi_id, thietbi_id, vitri] =  this.selectedNode[0].ID.split('-')
                        let pid = this.selectedNode[0].ID.split('-')
                        if (pid.length == 3)
                        {
                            let data = {
                                loaitbi_id: loaitbi_id,
                                thietbi_id: thietbi_id,
                                vitri: vitri,
                                captd_id: selectedDslam.CAPTD_ID,
                                soi: 1
                            }
                            let d1 = await this.$root.context.post('/web-cabman/daunoi-thietbi/lay-tt-tbi-kn', data)
                            this.dataCbo.loaitb1 = []
                            this.chkModule = false
                            this.EnabledChkModule1 = false
                             this.resetSoi1()
                            if (d1.data.length > 0)
                            {
                                let item = d1.data
                               this.modelCbo.mLoaiTbi1= item[0].LOAITBI_ID
                             await   this.cboLoaiTbi_1_EditValueChanged()
                                if (this.hidden.thietbi1){
                                    this.modelCbo.mThietbi = item[0].LOAITBI_ID +'-'+ item[0].THIETBI_ID
                                }
                                this.convertCboThietBi1(this.checkVisibleElement,item)
                                // Set port
                                let po = item.filter(function (e) {
                                    return e.VITRI  !=null
                                })
                                this.modelCbo.mCong = this.port1 = po[0].LOAITBI_ID + "-" + po[0].THIETBI_ID + "-" + po[0].VITRI
                            }
                            else
                            {
                               
                                this.modelCbo.mCong = 0
                                this.port1 = ""
                            }

                            if (this.Visible.tlpSoi_2)
                            {
                                let data = {
                                    loaitbi_id: loaitbi_id,
                                    thietbi_id: thietbi_id,
                                    vitri: vitri,
                                    captd_id: selectedDslam.CAPTD_ID,
                                    soi: 2
                                }
                                let d2 = await this.$root.context.post('/web-cabman/daunoi-thietbi/lay-tt-tbi-kn', data)
                                  this.resetSoi2()
                                this.dataCbo.loaitb2 = []
                                this.chkModule2 = false
                                this.EnabledChkModule2 = false
                                if (d2.data.length > 0)
                                {
                                    let item2= d2.data
                                    this.modelCbo.mLoaiTbi2 = item2[0].LOAITBI_ID
                                    await   this.cboLoaiTbi_2_EditValueChanged()
                                    if (this.hidden.thietbi2){
                                        this.modelCbo.mThietbi2 = item2[0].LOAITBI_ID +'-'+ item2[0].THIETBI_ID
                                    }
                                    this.convertCboThietBi2(this.checkVisibleElement,item2)
                                   
                                    let po = item2.filter(function (e) {
                                        return e.VITRI !=null
                                    })
                                    this.modelCbo.mCong2 = this.port2 = po[0].LOAITBI_ID + "-" 
                                                              + po[0].THIETBI_ID + "-" + po[0].VITRI                                
                                }
                                else
                                {
                                    
                                    this.modelCbo.mCong2 = 0
                                    this.port2 = "";
                                }
                            }
                        }
                    }
                    
                }
                
                if (!this.isDisableBtnNhapMoi)
                    this.XOA_THONGTIN_DAYNHAY();
            }
            catch (ex)
            {
                this.$root.toastError(' '+ex)
            }
    },
    convertDate2String(date){
      let dd = String(date.getDate()).padStart(2, '0');
      let mm = String(date.getMonth() + 1).padStart(2, '0'); //January is 0!
      let yyyy = date.getFullYear();
      return dd + '/' + mm + '/' + yyyy;
    },
    NAP_DS_THUEBAO_CONG: async function(pid){
        const [loaitbi_id, thietbi_id] =  pid.ID.split('-')
        let rs = await this.$root.context.get(`/web-cabman/daunoi-thietbi/lay-ds-cong-con?kieu=2&loaitbi_id=${loaitbi_id}&thietbi_id=${thietbi_id}`)
        if (rs.data!=null && rs.data.length > 0) {
            for (let dr in rs.data)
            {
                let node = this.tree.findIndex(x=>x.ID == dr.ID);
                if (node != null && node.length>0)
                {
                    node[0].ACC= dr.ACC
                }
            }
        }

    },
    convertCboThietBi1: function(type,item){
      switch (type) {
          case 1:            
            this.modelCbo.mFrame = item[1].LOAITBI_ID +'-'+ item[1].THIETBI_ID 
            this.modelCbo.mShelf = item[2].LOAITBI_ID +'-'+ item[2].THIETBI_ID
            this.modelCbo.mCard = item[3].LOAITBI_ID +'-'+ item[3].THIETBI_ID
            this.modelCbo.mModule = item[4].LOAITBI_ID +'-'+ item[4].THIETBI_ID
            this.chkModule = false                                    
              break;
        case 3:
               this.modelCbo.mCard = item[1].LOAITBI_ID +'-'+ item[1].THIETBI_ID
              break;
        case 4:
            this.modelCbo.mCard = item[1].LOAITBI_ID +'-'+ item[1].THIETBI_ID
            this.modelCbo.mModule = item[2].LOAITBI_ID +'-'+ item[2].THIETBI_ID
              break;
        case 5:
              this.modelCbo.mCard = item[1].LOAITBI_ID +'-'+ item[1].THIETBI_ID
              break;
         case 6:
              this.modelCbo.mShelf = item[1].LOAITBI_ID +'-'+ item[1].THIETBI_ID
              this.modelCbo.mCard = item[2].LOAITBI_ID +'-'+ item[2].THIETBI_ID
              break;
      }
    },
     convertCboThietBi2: function(type,item){
      switch (type) {
          case 1:            
            this.modelCbo.mFrame2 = item[1].LOAITBI_ID +'-'+ item[1].THIETBI_ID 
            this.modelCbo.mShelf2 = item[2].LOAITBI_ID +'-'+ item[2].THIETBI_ID
            this.modelCbo.mCard2 = item[3].LOAITBI_ID +'-'+ item[3].THIETBI_ID
            this.modelCbo.mModule2 = item[4].LOAITBI_ID +'-'+ item[4].THIETBI_ID
            this.chkModule2 = false                                    
              break;
        case 3:
               this.modelCbo.mCard2 = item[1].LOAITBI_ID +'-'+ item[1].THIETBI_ID
              break;
        case 4:
            this.modelCbo.mCard2 = item[1].LOAITBI_ID +'-'+ item[1].THIETBI_ID
            this.modelCbo.mModule2 = item[2].LOAITBI_ID +'-'+ item[2].THIETBI_ID
              break;
        case 5:
              this.modelCbo.mCard2 = item[1].LOAITBI_ID +'-'+ item[1].THIETBI_ID
              break;
         case 6:
              this.modelCbo.mShelf2 = item[1].LOAITBI_ID +'-'+ item[1].THIETBI_ID
              this.modelCbo.mCard2 = item[2].LOAITBI_ID +'-'+ item[2].THIETBI_ID
              break;
      }
    },
    HIENTHI_CONTROL: async function(type, cbo){
        let dr = []
        if (cbo == 'thietbi1')
        {
            dr = this.dataCbo.cboLoaiTbi1
            let value = this.modelCbo.mLoaiTbi1
            dr = dr.filter(ob=>ob.LOAITBI_ID == value)
            
        } 
        else if (cbo == 'thietbi2'){
            dr = this.dataCbo.cboLoaiTbi2
            let value = this.modelCbo.mLoaiTbi2
            dr = dr.filter(ob=>ob.LOAITBI_ID == value)
        }  
        if (dr != null && dr.length>0)
        {
            // Xóa hết dữ liệu của các combo ở dưới
            if (type == 'tlpSoi_1') {
                switch (dr[0].MA_LOAITBI)
                {
                    case "DSLAM":
                        this.hidden.thietbi1 = true
                        this.hidden.frame1 = true
                        this.hidden.shelf1 = true
                        this.hidden.card1 = true
                        this.hidden.module1 = true
                        this.hidden.port1 = true
                        this.checkVisibleElement = 1
                        break;
                    case "SWITCH":
                        this.hidden.thietbi1 = true
                        this.hidden.frame1 = false
                        this.hidden.shelf1 = false
                        this.hidden.card1 = false
                        this.hidden.module1 = false
                        this.hidden.port1 = true
                        this.checkVisibleElement = 2
                        break;
                    case "GPON":
                        this.hidden.thietbi1 = true
                        this.hidden.frame1 = false
                        this.hidden.shelf1 = false
                        this.hidden.card1 = true
                        this.hidden.module1 = false
                        this.hidden.port1 = true
                        this.checkVisibleElement = 3
                        break;
                    case "MANE":
                        this.hidden.thietbi1 = true
                        this.hidden.frame1 = false
                        this.hidden.shelf1 = false
                        this.hidden.card1 = true
                        this.hidden.module1 = true
                        this.hidden.port1 = true
                        this.checkVisibleElement = 4
                        break;
                    case "FRAME_TD":
                        this.hidden.thietbi1 = true
                        this.hidden.frame1 = false
                        this.hidden.shelf1 = false
                        this.hidden.card1 = true
                        this.hidden.module1 = false
                        this.hidden.port1 = true
                        this.checkVisibleElement = 5
                        break;
                    case "EWSD":
                        this.hidden.thietbi1 = true
                        this.hidden.frame1 = false
                        this.hidden.shelf1 = true
                        this.hidden.card1 = true
                        this.hidden.module1 = false
                        this.hidden.port1 = true
                        this.checkVisibleElement = 6
                        break;
                    case "ODF":
                        this.hidden.thietbi1 = true
                        this.hidden.frame1 = false
                        this.hidden.shelf1 = false
                        this.hidden.card1 = false
                        this.hidden.module1 = false
                        this.hidden.port1 = true
                        this.checkVisibleElement = 2
                        break;
                    case "PHIEN_DOC":
                        this.hidden.thietbi1 = true
                        this.hidden.frame1 = false
                        this.hidden.shelf1 = false
                        this.hidden.card1 = false
                        this.hidden.module1 = false
                        this.hidden.port1 = true
                        this.checkVisibleElement = 2
                        break;
                    case "PHIEN_NGANG":  
                        this.hidden.thietbi1 = true
                        this.hidden.frame1 = false
                        this.hidden.shelf1 = false
                        this.hidden.card1 = false
                        this.hidden.module1 = false
                        this.hidden.port1 = true
                        this.checkVisibleElement = 2
                        break;
                }
            }
            else{
                switch (dr[0].MA_LOAITBI)
                {
                    case "DSLAM": 
                        this.hidden.thietbi2 = true
                        this.hidden.frame2 = true
                        this.hidden.shelf2 = true
                        this.hidden.card2 = true
                        this.hidden.module2 = true
                        this.hidden.port2 = true
                        this.checkVisibleElement = 1
                        break;
                    case "SWITCH":
                        this.hidden.thietbi2 = true
                        this.hidden.frame2 = false
                        this.hidden.shelf2 = false
                        this.hidden.card2 = false
                        this.hidden.module2 = false
                        this.hidden.port2 = true
                        this.checkVisibleElement = 2
                        break;
                    case "GPON":
                        this.hidden.thietbi2 = true
                        this.hidden.frame2 = false
                        this.hidden.shelf2 = false
                        this.hidden.card2 = true
                        this.hidden.module2 = false
                        this.hidden.port2 = true
                        this.checkVisibleElement = 3
                        break;
                    case "MANE":
                        this.hidden.thietbi2 = true
                        this.hidden.frame2 = false
                        this.hidden.shelf2 = false
                        this.hidden.card2 = true
                        this.hidden.module2 = true
                        this.hidden.port2 = true
                        this.checkVisibleElement = 4
                        break;
                    case "FRAME_TD":
                        this.hidden.thietbi2 = true
                        this.hidden.frame2 = false
                        this.hidden.shelf2 = false
                        this.hidden.card2 = true
                        this.hidden.module2 = false
                        this.hidden.port2 = true
                        this.checkVisibleElement = 5
                        break;
                    case "EWSD":
                        this.hidden.thietbi2 = true
                        this.hidden.frame2 = false
                        this.hidden.shelf2 = true
                        this.hidden.card2 = true
                        this.hidden.module2 = false
                        this.hidden.port2 = true
                        this.checkVisibleElement = 6
                        break;
                    case "ODF":
                        this.hidden.thietbi2 = true
                        this.hidden.frame2 = false
                        this.hidden.shelf2 = false
                        this.hidden.card2 = false
                        this.hidden.module2 = false
                        this.hidden.port2 = true
                        this.checkVisibleElement = 2
                        break;
                    case "PHIEN_DOC":
                        this.hidden.thietbi2 = true
                        this.hidden.frame2 = false
                        this.hidden.shelf2 = false
                        this.hidden.card2 = false
                        this.hidden.module2 = false
                        this.hidden.port2 = true
                        this.checkVisibleElement = 2
                        break;
                    case "PHIEN_NGANG":  
                        this.hidden.thietbi2 = true
                        this.hidden.frame2 = false
                        this.hidden.shelf2 = false
                        this.hidden.card2 = false
                        this.hidden.module2 = false
                        this.hidden.port1 = true
                        this.checkVisibleElement = 2
                        break;
                }
            }
            
        }
        else
        {
            this.hidden.thietbi2 = false
            this.hidden.frame2 = false
            this.hidden.shelf2 = false
            this.hidden.card2 = false
            this.hidden.module2 = false
            this.hidden.port2 = false
        }
    },
    NAP_DS_THIETBI_GOC: async function(cbo, donvi_id, loaitbi_id){
        try {
            this.loading(true)
         let rs=   await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-tbi',
                           {donvi_id: donvi_id,loaitbi_id:loaitbi_id })
                if(rs.data!=null){
                     if (cbo === 'thietbi1'){
                        this.dataCbo.cboThietbi = rs.data
                        if (rs.data.length > 0) {
                            this.modelCbo.mThietbi = rs.data[0].ID
                        }
                    }
                    else if (cbo === 'thietbi2'){
                        this.dataCbo.cboThietbi2 = rs.data
                        if (rs.data.length > 0) {
                            this.modelCbo.mThietbi2 = rs.data[0].ID
                        }
                    }                       
                }
        } catch (error) {
            
        }
        finally{
            this.loading(false)
        }
    },
   //#endregion

    //#region "onChange"
    cboLoaiTbi_1_EditValueChanged: async function(){
        await this.HIENTHI_CONTROL('tlpSoi_1', 'thietbi1')
        await this.NAP_DS_THIETBI_GOC('thietbi1', this.modelCbo.mTramTB,
                               this.modelCbo.mLoaiTbi1)
    },
    getdsThietBiCon: async function(loaitbi_id, thietbi_id){
        let ds=[]
        try{
     let rs=  await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-tbi-con',
                              {loaitbi_id:loaitbi_id,thietbi_id:thietbi_id})
         
            if(rs.data!=null &&rs.data.length>0)
            ds= rs.data
            return ds
            }
        catch(e){
             return ds
        }
    },
    cboThietBi_1_EditValueChanged: async function(){
        try
        {
            if(this.modelCbo.mThietbi!=-1){
            const [loaitbi_id, thietbi_id] = this.modelCbo.mThietbi.split('-')
            if (this.checkVisibleElement === 1){
              await  this.fillCboFrame(1, loaitbi_id, thietbi_id)
            }
            else if (this.checkVisibleElement === 2){
              await  this.fillCboPort(1, this.modelCbo.mThietbi.split('-')[0], 
                this.modelCbo.mThietbi.split('-')[1])
            }
            else if (this.checkVisibleElement === 3 || 
                     this.checkVisibleElement === 4 ||
                     this.checkVisibleElement === 5){
             await   this.fillCboCard(1, this.modelCbo.mThietbi.split('-')[0], 
                this.modelCbo.mThietbi.split('-')[1])
            }
            else if (this.checkVisibleElement === 6){
              await  this.fillCboShelf(1, this.modelCbo.mThietbi.split('-')[0],
                 this.modelCbo.mThietbi.split('-')[1])
            }
            }
        }
        catch (ex)
        {
         //   console.log(ex)
            //this.$root.toastError(' '+ex.response.data.message_detail)
        }
    },
    cboFrame_1_EditValueChanged: async function(){
        try
        {
            const [loaitbi_id, thietbi_id] = this.modelCbo.mFrame.split('-')
            if (this.checkVisibleElement === 1){
              await  this.fillCboShelf(1, loaitbi_id, thietbi_id)
            }
        }
        catch (ex)
        {
        //    console.log(ex)
           // this.$root.toastError(' '+ex.response.data.message_detail)
        }
    },
    cboShelf_1_EditValueChanged: async function(){
        try
        {
            const [loaitbi_id, thietbi_id] = this.modelCbo.mShelf.split('-')
            if (this.checkVisibleElement === 1 || this.checkVisibleElement === 6){
            await    this.fillCboCard(1, loaitbi_id, thietbi_id)
            }
        }
        catch (ex)
        {
          //  console.log(ex)
           // this.$root.toastError(' '+ex.response.data.message_detail)
        }
    },
    cboCard_1_EditValueChanged: async function(){
        try
        {
            const [loaitbi_id, thietbi_id] = this.modelCbo.mCard.split('-')
            if (this.checkVisibleElement === 1){
                if (!this.chkModule){
             await       this.fillCboModule(1, loaitbi_id, thietbi_id)
                }
                else {
                    this.dataCbo.cboCong = []
                    await this.$root.context.get(`/web-cabman/daunoi-thietbi/lay-ds-cong-con?kieu=3&loaitbi_id=${loaitbi_id}&thietbi_id=${thietbi_id}`)
                    .then(rs => {
                        this.dataCbo.cboCong = rs.data
                        if (rs.data.length > 0) {
                            this.modelCbo.mCong =  rs.data[0].ID
                        }
                        
                    })
                }
            }
            else if (this.checkVisibleElement === 3  || this.checkVisibleElement === 4 || this.checkVisibleElement === 5 || this.checkVisibleElement === 6){
           await     this.fillCboPort(1, loaitbi_id, thietbi_id)
            }
        }
        catch (ex)
        {
           // console.log(ex)
           // this.$root.toastError(' '+ex.response.data.message_detail)
        }
    },
    cboModule_1_EditValueChanged: async function(){
        try
        {
            const [loaitbi_id, thietbi_id] = this.modelCbo.mModule.split('-')
            if ((this.checkVisibleElement === 1  || this.checkVisibleElement === 4) 
                            && !this.chkModule) {
             await   this.fillCboPort(1, loaitbi_id, thietbi_id)
            }
        }
        catch (ex)
        {
           // this.$root.toastError(' '+ex)
        }
    },
    cboLoaiTbi_2_EditValueChanged: async function(){
        await this.HIENTHI_CONTROL('tlpSoi_2', 'thietbi2')
        await this.NAP_DS_THIETBI_GOC('thietbi2', this.modelCbo.mTramTB, this.modelCbo.mLoaiTbi2)
    },
    cboThietBi_2_EditValueChanged: async function(){
        try
        {
            if(this.modelCbo.mThietbi2!=-1){
            const [loaitbi_id, thietbi_id] = this.modelCbo.mThietbi2.split('-')
            if (this.checkVisibleElement === 1){
              await  this.fillCboFrame(2, loaitbi_id, thietbi_id)
            }
            else if (this.checkVisibleElement === 2){
             await   this.fillCboPort(2, this.modelCbo.mThietbi2.split('-')[0], this.modelCbo.mThietbi2.split('-')[1])
            }
            else if (this.checkVisibleElement === 3 || this.checkVisibleElement === 4 || this.checkVisibleElement === 5){
             await   this.fillCboCard(2, this.modelCbo.mThietbi2.split('-')[0], this.modelCbo.mThietbi2.split('-')[1])
            }
            else if (this.checkVisibleElement === 6){
              await  this.fillCboShelf(2, this.modelCbo.mThietbi2.split('-')[0], this.modelCbo.mThietbi2.split('-')[1])
            }
            }
        }
        catch (ex)
        {
           // console.log(ex)
           // this.$root.toastError(' '+ex.response.data.message_detail)
        }
    },
    cboFrame_2_EditValueChanged: async function(){
        try
        {
            const [loaitbi_id, thietbi_id] = this.modelCbo.mFrame2.split('-')
            if (this.checkVisibleElement === 1){
             await   this.fillCboShelf(2, loaitbi_id, thietbi_id)
            }
        }
        catch (ex)
        {
         // await  this.$root.toastError(' '+ex.response.data.message_detail)
        }
    },
    cboShelf_2_EditValueChanged: async function(){
        try
        {
            const [loaitbi_id, thietbi_id] = this.modelCbo.mShelf2.split('-')
            if (this.checkVisibleElement === 1 || this.checkVisibleElement === 6){
            await    this.fillCboCard(2, loaitbi_id, thietbi_id)
            }
        }
        catch (ex)
        {
          //  this.$root.toastError(' '+ex.response.data.message_detail)
        }
    },
    cboCard_2_EditValueChanged: async function(){
        try
        {
            const [loaitbi_id, thietbi_id] = this.modelCbo.mCard2.split('-')
            if (this.checkVisibleElement === 1){
                if (!this.chkModule2){
             await       this.fillCboModule(2, loaitbi_id, thietbi_id)
                }
                else {
                    this.dataCbo.cboCong2 = []
           let rs = await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-cong-con',
                              {kieu:3,loaitbi_id:loaitbi_id,thietbi_id:thietbi_id})
               this.dataCbo.cboCong2 = rs.data
               if(rs.data!=null){
                       
                        if (rs.data.length > 0) {
                            this.modelCbo.mCong2 =  rs.data[0].ID
                        }
                        
                    }
                }
            }
            else if (this.checkVisibleElement === 3  || this.checkVisibleElement === 4 || this.checkVisibleElement === 5 || this.checkVisibleElement === 6){
             await   this.fillCboPort(2, loaitbi_id, thietbi_id)
            }
        }
        catch (ex)
        {
            this.$root.toastError(' '+ex.response.data.message_detail)
        }
    },
    cboModule_2_EditValueChanged: async function(){
        try
        {
            const [loaitbi_id, thietbi_id] = this.modelCbo.mModule2.split('-')
            if ((this.checkVisibleElement === 1  || this.checkVisibleElement === 4) && !this.chkModule) {
             await   this.fillCboPort(2, loaitbi_id, thietbi_id)
            }
        }
        catch (ex)
        {
            this.$root.toastError(' '+ex)
        }
    },
    fillCboFrame:  async function(type, loaitbi_id, thietbi_id){
        if (type === 1){
            this.dataCbo.cboFrame = []
           let rs=       await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-tbi-con',
                              {loaitbi_id:loaitbi_id,thietbi_id:thietbi_id})
                  if(rs.data!=null){
                    this.dataCbo.cboFrame = rs.data
                    if (rs.data.length > 0) {
                        if(this.isCreateNew)
                        this.modelCbo.mFrame =  rs.data[0].ID
                      await  this.cboFrame_1_EditValueChanged()
                    }
                    
                }
        }
        else {
            this.dataCbo.cboFrame2 = []
             let rs=   await this.$root.context.get(`/web-cabman/daunoi-thietbi/lay-ds-tbi-con?loaitbi_id=${loaitbi_id}&thietbi_id=${thietbi_id}`)
               if(rs.data!=null){
                    this.dataCbo.cboFrame2 = rs.data
                    if (rs.data.length > 0) {
                          if(this.isCreateNew)
                        this.modelCbo.mFrame2 =  rs.data[0].ID
                     await   this.cboFrame_2_EditValueChanged()
                    }
                    
                }
        }
    },
    fillCboShelf:  async function(type, loaitbi_id, thietbi_id){
        if (type === 1) {
            this.dataCbo.cboShelf = []
             let rs=      await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-tbi-con',
                              {loaitbi_id:loaitbi_id,thietbi_id:thietbi_id})
                if(rs.data!=null){
                    this.dataCbo.cboShelf = rs.data
                    if (rs.data.length > 0) {
                          if(this.isCreateNew)
                        this.modelCbo.mShelf =  rs.data[0].ID
                       await this.cboShelf_1_EditValueChanged()
                    }
                    
                }
        }
        else {
            this.dataCbo.cboShelf2 = []
            let rs=    await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-tbi-con',
                              {loaitbi_id:loaitbi_id,thietbi_id:thietbi_id})
                 if(rs.data!=null){
                    this.dataCbo.cboShelf2 = rs.data
                    if (rs.data.length > 0) {
                        if(this.isCreateNew)
                        this.modelCbo.mShelf2 =  rs.data[0].ID
                      await  this.cboShelf_2_EditValueChanged()
                    }
                    
                }
        }
    },
    fillCboModule:  async function(type, loaitbi_id, thietbi_id){
        if (type === 1) {
            this.dataCbo.cboModule = []
        let rs=    await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-tbi-con',
                              {loaitbi_id:loaitbi_id,thietbi_id:thietbi_id})
            if(rs.data!=null){
                    this.dataCbo.cboModule = rs.data
                    if (rs.data.length > 0) {
                        if(this.isCreateNew)
                        this.modelCbo.mModule =  rs.data[0].ID
                      await  this.cboModule_1_EditValueChanged()
                    }
                    
                }
        }
        else {
            this.dataCbo.cboModule2 = []
              let rs=  await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-tbi-con',
                              {loaitbi_id:loaitbi_id,thietbi_id:thietbi_id})
                    if(rs.data!=null){
                    this.dataCbo.cboModule2 = rs.data

                    if (rs.data.length > 0) {
                       if(this.isCreateNew)
                       this.modelCbo.mModule2 =  rs.data[0].ID
                      await  this.cboModule_2_EditValueChanged()
                    }
                    
                }
        }
    },
    fillCboCard:  async function(type, loaitbi_id, thietbi_id){
        if (type === 1) {
            this.dataCbo.cboCard = []
         let rs=   await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-tbi-con',
                              {loaitbi_id:loaitbi_id,thietbi_id:thietbi_id})
              if(rs.data!=null){
                    this.dataCbo.cboCard = rs.data
                    if (rs.data.length > 0) {
                        if(this.isCreateNew)
                        this.modelCbo.mCard =  rs.data[0].ID
                      await  this.cboCard_1_EditValueChanged()
                    }
                    
                }
        }
        else {
            this.dataCbo.cboCard2 = []
               let rs=     await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-tbi-con',
                              {loaitbi_id:loaitbi_id,thietbi_id:thietbi_id})
                if(rs.data!=null){
                    this.dataCbo.cboCard2 = rs.data
      
                    if (rs.data.length > 0) {
                        if(this.isCreateNew)
                        this.modelCbo.mCard2 =  rs.data[0].ID
                      await  this.cboCard_2_EditValueChanged()
                    }
                    
                }
        }
    },
    fillCboPort:  async function(type, loaitbi_id, thietbi_id){
        if (type === 1) {
            this.dataCbo.cboCong = []
         let rs=await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-cong-con',
                              {kieu:3,loaitbi_id:loaitbi_id,thietbi_id:thietbi_id})
             this.dataCbo.cboCong = rs.data
               if(rs.data!=null){                   
                    if (rs.data.length > 0) {
                          if(this.isCreateNew)
                        this.modelCbo.mCong =  rs.data[0].ID
                    }                   
               }
        }
        else {
            this.dataCbo.cboCong2 = [] 
            let rs=await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-cong-con',
                              {kieu:3,loaitbi_id:loaitbi_id,thietbi_id:thietbi_id})             
                // await this.$root.context.get('/web-cabman/daunoi-thietbi/lay-ds-tbi-con',
                //               {loaitbi_id:loaitbi_id,thietbi_id:thietbi_id})
                .then(rs => {
                    this.dataCbo.cboCong2 = rs.data
                    if (rs.data.length > 0) {
                      if(this.isCreateNew)
                       this.modelCbo.mCong2 =  rs.data[0].ID
                    }
                    
                })
        }
    }
    //#endregion
  },
  async created () {
    try {
        this.loading(true)
        this.setVisibleButton(false, false, false, false, false, false)
        
        // let exch_id = this.doiTuong.tramtb_id
        // if (exch_id !== 0)
        // {
        //     let rs = await this.$root.context.post(`/web-cabman/daunoi-thietbi/load-daunoi-tbi?donvi_id=${exch_id}`)
        //     if (rs.data.length > 0)
        //     {

        //     }
        // }
    } catch (error) {
        this.$root.toastError(error)
    }
    finally{
        this.loading(false)
    }
  }
}
</script>
<style>
.inline { 
display: inline-block;  

}
a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
.cay-sodo-tb .e-list-icon {
    float: left;
     margin: 5px 5px 5px 5px !important; 
}
.icon-right{
    float: right; 
}
.cay-sodo-tb .ewsd { background-image: url('/static/icons/daunoi-tbi/EWSD.png');}
.cay-sodo-tb .account { background-image: url('/static/icons/daunoi-tbi/ACCOUNT.png');}
.cay-sodo-tb .card_dsl { background-image: url('/static/icons/daunoi-tbi/CARD_DSL.png');}
.cay-sodo-tb .card_ewsd { background-image: url('/static/icons/daunoi-tbi/CARD_EWSD.png');}
.cay-sodo-tb .card_gpon { background-image: url('/static/icons/daunoi-tbi/CARD_GPON.png');}
.cay-sodo-tb .card_mane { background-image: url('/static/icons/daunoi-tbi/CARD_MANE.png');}
.cay-sodo-tb .card_td { background-image: url('/static/icons/daunoi-tbi/CARD_TD.png');}
.cay-sodo-tb .dslam { background-image: url('/static/icons/daunoi-tbi/DSLAM.png');}
.cay-sodo-tb .frame_dsl { background-image: url('/static/icons/daunoi-tbi/FRAME_DSL.png');}
.cay-sodo-tb .frame_td { background-image: url('/static/icons/daunoi-tbi/FRAME_TD.png');}
.cay-sodo-tb .gpon { background-image: url('/static/icons/daunoi-tbi/GPON.png');}
.cay-sodo-tb .mane { background-image: url('/static/icons/daunoi-tbi/MANE.png');}
.cay-sodo-tb .module_dsl { background-image: url('/static/icons/daunoi-tbi/MODULE_DSL.png');}
.cay-sodo-tb .module_mane { background-image: url('/static/icons/daunoi-tbi/MODULE_MANE.png');}
.cay-sodo-tb .odf { background-image: url('/static/icons/daunoi-tbi/ODF.png');}
.cay-sodo-tb .phien_doc { background-image: url('/static/icons/daunoi-tbi/PHIEN_DOC.png');}
.cay-sodo-tb .phien_ngang { background-image: url('/static/icons/daunoi-tbi/PHIEN_NGANG.png');}
.cay-sodo-tb .port_1_1 { background-image: url('/static/icons/daunoi-tbi/PORT_1_1.png');}
.cay-sodo-tb .port_1_2 { background-image: url('/static/icons/daunoi-tbi/PORT_1_2.png');}
.cay-sodo-tb .port_2_1 { background-image: url('/static/icons/daunoi-tbi/PORT_2_1.png');}
.cay-sodo-tb .port_2_2 { background-image: url('/static/icons/daunoi-tbi/PORT_2_2.png');}
.cay-sodo-tb .port_3_1 { background-image: url('/static/icons/daunoi-tbi/PORT_3_1.png');}
.cay-sodo-tb .port_3_2 { background-image: url('/static/icons/daunoi-tbi/PORT_3_2.png');}
.cay-sodo-tb .port_4_1 { background-image: url('/static/icons/daunoi-tbi/PORT_4_1.png');}
.cay-sodo-tb .port_4_2 { background-image: url('/static/icons/daunoi-tbi/PORT_4_2.png');}
.cay-sodo-tb .port_5_1 { background-image: url('/static/icons/daunoi-tbi/PORT_5_1.png');}
.cay-sodo-tb .port_5_2 { background-image: url('/static/icons/daunoi-tbi/PORT_5_2.png');}
.cay-sodo-tb .port_6_1 { background-image: url('/static/icons/daunoi-tbi/PORT_6_1.png');}
.cay-sodo-tb .port_6_2 { background-image: url('/static/icons/daunoi-tbi/PORT_6_2.png');}
.cay-sodo-tb .shelf_dsl { background-image: url('/static/icons/daunoi-tbi/SHELF_DSL.png');}
.cay-sodo-tb .shelf_ewsd { background-image: url('/static/icons/daunoi-tbi/SHELF_EWSD.png');}
.cay-sodo-tb .status_00 { background-image: url('/static/icons/daunoi-tbi/STATUS_00.png');}
.cay-sodo-tb .status_01 { background-image: url('/static/icons/daunoi-tbi/STATUS_01.png');}
.cay-sodo-tb .status_10 { background-image: url('/static/icons/daunoi-tbi/STATUS_10.png');}
.cay-sodo-tb .status_11 { background-image: url('/static/icons/daunoi-tbi/STATUS_11.png');}
.cay-sodo-tb .switch { background-image: url('/static/icons/daunoi-tbi/TRAM_TB.png');}
.cay-sodo-tb .tram_tb { background-image: url('/static/icons/daunoi-tbi/PHIEN_DOC.png');}
.cay-sodo-tb .e-list-icon + .e-list-text{
    width:200px
}
</style>