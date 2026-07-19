<template src="./index.html"> </template>

<script>
import EventBus from "../../../../utils/eventBus";
export default {
  data() {
    return {
      txtSearch: "",
      dataSource: [],
      dsDonVi: [],
      dsTuRack: [],
      dsBRAS: [],
      dsBRAS_Card: [],
      dsBRAS_Port: [],
      dsDSLAM: [],
      dsGPon: [],
      dsGPON_Card: [],
      dsGPON_Port: [],
      dsMane: [],
      dsMANE_Card: [],
      dsMane_Port: [],
      dsMANE_Module: [],
      dsModule_PortMane: [],
      dsSwitch: [],
      dsSWITCH_Card: [],
      dsSwitch_Port: [],
      dsSwitch_CardPort: [],
      dsDSLAM_Frame: [],
      dsDSLAM_Shelf: [],
      dsDSLAM_Card: [],
      dsDSLAM_Port: [],
      dsDSLAM_Module: [],
      dsModule_Port: [],
      dsFRAMETD: [],
      dsFrame_Card: [],
      dsODF: [],
      dsSwitch_Port: [],
      dsSwitch_CardPort: [],
      dsDSLAM_Frame: [],
      dsDSLAM_Shelf: [],
      view: 0,
      isLoadDb: false,
      nodesId: [],
      currentItem: {}
    };
  },
  computed: {
    treeFields: function() {
      return {
        dataSource: this.dsDonViFiltered,
        id: "DONVI_ID",
        text: "TEN_DV",
        parentID: "DONVI_CHA_ID",
        hasChildren: "HasChildren",
        expanded: "Expanded"
      };
    },
    dsDonViFiltered: function() {
      if (this.dsDonVi != null) {
        let items = this.dsDonVi.filter(item => {
          return item.TEN_DV.toLowerCase().includes(
            this.txtSearch.toLowerCase()
          );
        });
        let rs = [];
        if (!(items == null || items.length == 0)) {
          for (let i = 0; i < items.length; i++) {
            const item = items[i];
            let ck = rs.find(x => x.DONVI_ID == item.DONVI_ID);
            if (ck == null) {
              rs.push(item);
              if (item.DONVI_CHA_ID != null) {
                let ps = this.getParents(item.DONVI_CHA_ID);
                if (!(ps == null || ps.length == 0)) {
                  for (let j = 0; j < ps.length; j++) {
                    let ck2 = rs.find(x => x.DONVI_ID == ps[j].DONVI_ID);
                    if (!ck2) rs.push(ps[j]);
                  }
                }
              }
            }
          }
        }
        return rs;
      } else return [];
    }
  },
  created: async function() {
    this.$root.showLoading(true);
    try {
      this.dsTuRack = await this.getDsTuRack();
      this.dsBRAS = await this.getDsBras()
      this.dsBRAS_Card = await this.getDsBrasCard();
      this.dsBRAS_Port = await this.getDsBrasPort();
      this.dsGPon = await this.getDslamGponSwitch(3);
      this.dsGPON_Card =await  this.getDsGponCard();
     // this.dsGPON_Port =await  this.getDsGponPort();
      this.dsMane = await this.getDslamGponSwitch(17);
      this.dsMANE_Card =await  this.getDsManeCard();
      this.dsMane_Port = await this.getDsManePort();
      this.dsMANE_Module =await  this.getDsManeModule();
      this.dsSwitch = await this.getDslamGponSwitch(2);
       this.dsSWITCH_Card =await  this.getDsSwitchCard();
    //   this.dsSwitch_Port = await this.getDsSwitchPort();
       this.dsSwitch_CardPort =await  this.getDsSwitchPortCard();
       this.dsDSLAM = await this.getDslamGponSwitch(1);
      this.dsDSLAM_Frame =await  this.getDsdsLamFrame();
     this.dsDSLAM_Shelf = await this.getDsdsLamShelf();
      this.dsDSLAM_Card =await  this.getDsdsLamCard();
      this.dsDSLAM_Port = await this.getDsdsLamPort();
      this.dsDSLAM_Module =await  this.getDsdsLamModule();
      this.dsModule_Port = await this.getDsdsLamPortMudule();
       this.dsFRAMETD =await  this.getDsFrameTruyenDan();
       this.dsFrame_Card = await this.getDsFrameCard();
       this.dsODF =await  this.getDsOdf();
      await this.loadDonVi();
    }catch(e){
     console.log(e)
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    sleep: function(ms) {
        return new Promise((resolve) => {
        setTimeout(resolve, ms);
        });
    },
    reloadDonVi: async function() {
      this.$root.showLoading(true);
      try {
        let m = await this.loadDonVi();
      } finally {
        this.$root.showLoading(false);
      }
    },
    loadDonVi: async function() {
      try{
      //let ds = await this.getDsDonVi();
      let ds = await this.getDsDonVi();
      if (!(ds == null || ds.length == 0)) {
        this.dsDonVi= ds
        // Load Tủ rack
        let dsTuRacks=[]
        let tuRacks = this.getDsTuRackFromList(this.dsTuRack);
        if (!(tuRacks == null || tuRacks.length == 0)) {
          for (let i = 0; i < tuRacks.length; i++) {
            const element = tuRacks[i];
           // ds.push(element);
           dsTuRacks.push(element);
          }
            this.dsDonVi= this.dsDonVi.concat(dsTuRacks)
        }
       
        // Load Bras
        let dsBras=[]
        let bras = this.getDsFromList(this.dsBRAS,"BRAS_ID","TENBRAS","TURACK_ID","TURACK","BRAS");
        if (!(bras == null || bras.length == 0)) {
          for (let i = 0; i < bras.length; i++) {
            const element = bras[i];
           // ds.push(element);
            dsBras.push(element);
          }
           this.dsDonVi= this.dsDonVi.concat(dsBras)
        }
      

        let dsBRAS_Card=[]
        
        let brasCard = this.getDsFromList(this.dsBRAS_Card,"CARDBRAS_ID","TEN_CARD","BRAS_ID","BRAS","BRASCARD");
        if (!(brasCard == null || brasCard.length == 0)) {
          for (let i = 0; i < brasCard.length; i++) {
            const element = brasCard[i];
            //ds.push(element);
          dsBRAS_Card.push(element)
          }
           this.dsDonVi= this.dsDonVi.concat(dsBRAS_Card)
        }
       
        let dsBRAS_Port=[]
        
        let brasPort = this.getDsFromList(this.dsBRAS_Port,"PORTBRAS_ID","VITRI","CARDBRAS_ID","BRASCARD","BRASPORT");
        if (!(brasPort == null || brasPort.length == 0)) {
          for (let i = 0; i < brasPort.length; i++) {
            const element = brasPort[i];
           // ds.push(element);
           dsBRAS_Port.push(element);
          }
            this.dsDonVi= this.dsDonVi.concat(dsBRAS_Port)
        }
      
       //   this.sleep(300)
        //GPon
        let dsGPon =[]
        
        let gpon = this.getDsFromList(this.dsGPon,"DSLAM_ID","TENDSLAM","TURACK_ID","TURACK","GPON");
        if (!(gpon == null || gpon.length == 0)) {
          for (let i = 0; i < gpon.length; i++) {
            const element = gpon[i];
           // ds.push(element);
            dsGPon.push(element);
          }
            this.dsDonVi= this.dsDonVi.concat(dsGPon)
        }
      

        let dsGPON_Card=[]
        
        let gpon_card = this.getDsFromList(this.dsGPON_Card,"CARDGP_ID","TEN_CARD","GPON_ID","GPON","GPONCARD");
        if (!(gpon_card == null || gpon_card.length == 0)) {
          for (let i = 0; i < gpon_card.length; i++) {
            const element = gpon_card[i];
           // ds.push(element);
           dsGPON_Card.push(element);
          }
           this.dsDonVi= this.dsDonVi.concat(dsGPON_Card)
        }
        
        let dsGPON_Port=[]
        
        let gpon_port = this.getDsFromList(this.dsGPON_Port,"PORTVL_ID","VITRI","CARDGP_ID","GPONCARD","GPONPORT");
        if (!(gpon_port == null || gpon_port.length == 0)) {
          for (let i = 0; i < gpon_port.length; i++) {
            const element = gpon_port[i];
           // this.dsDonVi.push(element);
          dsGPON_Port.push(element);
          }
        this.dsDonVi= this.dsDonVi.concat(dsGPON_Port)
        }        
        

        //Mane
        let dsMane =[]
       
        let mane = this.getDsFromList(this.dsMane,"DSLAM_ID","TENDSLAM","TURACK_ID","TURACK","MANE");
        if (!(mane == null || mane.length == 0)) {
          for (let i = 0; i < mane.length; i++) {
            const element = mane[i];
            //ds.push(element);
            dsMane.push(element);
          }
           this.dsDonVi= this.dsDonVi.concat(dsMane)
        }
       

        let dsMane_Card=[]
        
        let mane_card = this.getDsFromList(this.dsMANE_Card,"CARDMANE_ID","LOAI_CARD","DSLAM_ID","MANE","MANECARD");
        if (!(mane_card == null || mane_card.length == 0)) {
          for (let i = 0; i < mane_card.length; i++) {
            const element = mane_card[i];
          //  ds.push(element);
           dsMane_Card.push(element);
          }
           this.dsDonVi= this.dsDonVi.concat(dsMane_Card)
        }
       

        let dsManePort =[]
       
        let mane_port = this.getDsFromList(this.dsMane_Port,"PORTVL_ID","VITRI","CARDMANE_ID","MANECARD","MANEPORT");
        if (!(mane_port == null || mane_port.length == 0)) {
          for (let i = 0; i < mane_port.length; i++) {
            const element = mane_port[i];
            //ds.push(element);
            dsManePort.push(element);
          }
           this.dsDonVi= this.dsDonVi.concat(dsManePort)
        }
       
        let dsMane_Module=[]
       
        let mane_module = this.getDsFromList(this.dsMANE_Module,"MODULEMANE_ID","SERIAL","CARDMANE_ID","MANECARD","MANEMODULE");
        if (!(mane_module == null || mane_module.length == 0)) {
          for (let i = 0; i < mane_module.length; i++) {
            const element = mane_module[i];
          //  ds.push(element);
           dsMane_Module.push(element);
          }
            this.dsDonVi= this.dsDonVi.concat(dsMane_Module)
        }
      
        let dsPortManeModule =[]
        this.dsModule_PortMane = await this.getDsManePortMudule();
        let port_manemodule = this.getDsFromList(this.dsModule_PortMane,"PORTVL_ID","VITRI","MODULEMANE_ID","MANEMODULE","MANEPORTMODULE");
        if (!(port_manemodule == null || port_manemodule.length == 0)) {
          for (let i = 0; i < port_manemodule.length; i++) {
            const element = port_manemodule[i];
            //ds.push(element);
            dsPortManeModule.push(element);
          }
           this.dsDonVi= this.dsDonVi.concat(dsPortManeModule)
        }
       

        // Switch
        let dsSwitch =[]
        let switchtbi = this.getDsFromList(this.dsSwitch,"DSLAM_ID","TENDSLAM","TURACK_ID","TURACK","SWITCH");
        if (!(switchtbi == null || switchtbi.length == 0)) {
          for (let i = 0; i < switchtbi.length; i++) {
            const element = switchtbi[i];
            //ds.push(element);
            dsSwitch.push(element);
          }
          this.dsDonVi= this.dsDonVi.concat(dsSwitch)
        }
        

        let dsSwitch_Card=[]
        let switch_card = this.getDsFromList(this.dsSWITCH_Card,"CARDSW_ID","TEN_CARD","SWITCH_ID","SWITCH","SWITCHCARD");
        if (!(switch_card == null || switch_card.length == 0)) {
          for (let i = 0; i < switch_card.length; i++) {
            const element = switch_card[i];
          //  ds.push(element);
           dsSwitch_Card.push(element);
          }
           this.dsDonVi= this.dsDonVi.concat(dsSwitch_Card)
        }
       
        let dsSwitchPort =[]
        let switch_port = this.getDsFromList(this.dsSwitch_Port,"PORTVL_ID","VITRI","SWITCH_ID","SWITCH","SWITCHPORT");
        if (!(switch_port == null || switch_port.length == 0)) {
          for (let i = 0; i < switch_port.length; i++) {
            const element = switch_port[i];
            //ds.push(element);
            dsSwitchPort.push(element);
          }
        }
       this.dsDonVi= this.dsDonVi.concat(dsSwitchPort)

        let dsPortCardSwitch=[]
        let port_cardswitch = this.getDsFromList(this.dsSwitch_CardPort,"PORTVL_ID","VITRI","CARDSW_ID","SWITCHCARD","SWITCHCARDPORT");
        if (!(port_cardswitch == null || port_cardswitch.length == 0)) {
          for (let i = 0; i < port_cardswitch.length; i++) {
            const element = port_cardswitch[i];
          //  ds.push(element);
           dsPortCardSwitch.push(element);
          }
          this.dsDonVi= this.dsDonVi.concat(dsPortCardSwitch)
        }

        // DSLAM
        let dsDslam =[]
        let dsl = this.getDsFromList(this.dsDSLAM,"DSLAM_ID","TENDSLAM","TURACK_ID","TURACK","DSLAM");
        if (!(dsl == null || dsl.length == 0)) {
          for (let i = 0; i < dsl.length; i++) {
            const element = dsl[i];
            //ds.push(element);
            dsDslam.push(element);
          }
           this.dsDonVi= this.dsDonVi.concat(dsDslam)
        }
       
        let dsDslFrame=[]
    
        let dslFrame = this.getDsFromList(this.dsDSLAM_Frame,"FRAMEDSL_ID","TEN_FRAME","DSLAM_ID","DSLAM","DSLAMFRAME");
        if (!(dslFrame == null || dslFrame.length == 0)) {
          for (let i = 0; i < dslFrame.length; i++) {
            const element = dslFrame[i];
          //  ds.push(element);
           dsDslFrame.push(element);
          }
           this.dsDonVi= this.dsDonVi.concat(dsDslFrame)
        }
       

        let dslShelfs =[]
        
        let dslshelf = this.getDsFromList(this.dsDSLAM_Shelf,"SHELFDSL_ID","TEN_SHELF","FRAMEDSL_ID","DSLAMFRAME","DSLAMSHELF");
        if (!(dslshelf == null || dslshelf.length == 0)) {
          for (let i = 0; i < dslshelf.length; i++) {
            const element = dslshelf[i];
            //ds.push(element);
            dslShelfs.push(element);
          }
           this.dsDonVi= this.dsDonVi.concat(dslShelfs)
        }
       
        let dslCards=[]
        
        let dslCard = this.getDsFromList(this.dsDSLAM_Card,"CARDDSL_ID","TEN_CARD","SHELFDSL_ID","DSLAMSHELF","DSLAMCARD");
        if (!(dslCard == null || dslCard.length == 0)) {
          for (let i = 0; i < dslCard.length; i++) {
            const element = dslCard[i];
          //  ds.push(element);
           dslCards.push(element);
          }
            this.dsDonVi= this.dsDonVi.concat(dslCards)
        }
      
        let dslPorts =[]
      
        let dslport = this.getDsFromList(this.dsDSLAM_Port,"PORTVL_ID","VITRI","CARDDSL_ID","DSLAMCARD","DSLAMPORT");
        if (!(dslport == null || dslport.length == 0)) {
          for (let i = 0; i < dslport.length; i++) {
            const element = dslport[i];
            //ds.push(element);
            dslPorts.push(element);
          }
           this.dsDonVi= this.dsDonVi.concat(dslPorts)
        }
        
        let dslModules=[]
        
        let dslModule = this.getDsFromList(this.dsDSLAM_Module,"MODULEDSL_ID","LOAI_MODULE","CARDDSL_ID","DSLAMCARD","DSLAMMODULE");
        if (!(dslModule == null || dslModule.length == 0)) {
          for (let i = 0; i < dslModule.length; i++) {
            const element = dslModule[i];
          //  ds.push(element);
           dslModules.push(element);
          }
            this.dsDonVi= this.dsDonVi.concat(dslModules)
        }
      
        let dslModulePorts =[]
       
        let dslModulePort = this.getDsFromList(this.dsDSLAM_Port,"PORTVL_ID","VITRI","MODULEDSL_ID","DSLAMMODULE","DSLAMMODULEPORT");
        if (!(dslModulePort == null || dslModulePort.length == 0)) {
          for (let i = 0; i < dslModulePort.length; i++) {
            const element = dslModulePort[i];
            //ds.push(element);
            dslModulePorts.push(element);
          }
           this.dsDonVi= this.dsDonVi.concat(dslModulePorts)
        }
        
        //FRAME TRUYỀN DẪN
        let frameTds=[]
        
         let frametd = this.getDsFromList(this.dsFRAMETD,"FRAMETD_ID","TEN_FRAME","TURACK_ID","TURACK","FRAMETD");
        if (!(frametd == null || frametd.length == 0)) {
          for (let i = 0; i < frametd.length; i++) {
            const element = frametd[i];
          //  ds.push(element);
           frameTds.push(element);
          }
            this.dsDonVi= this.dsDonVi.concat(frameTds)
        }
      
        let frameCards =[]

        let frameCard = this.getDsFromList(this.dsFrame_Card,"CARDTD_ID","LOAI_CARD","FRAMETD_ID","FRAMETD","FRAMECARD");
        if (!(frameCard == null || frameCard.length == 0)) {
          for (let i = 0; i < frameCard.length; i++) {
            const element = frameCard[i];
            //ds.push(element);
            frameCards.push(element);
          }
           this.dsDonVi= this.dsDonVi.concat(frameCards)
        }
        //ODF
        let dsOdfs=[]
        
         let odf = this.getDsFromList(this.dsODF,"ODF_ID","TEN_ODF","TURACK_ID","TURACK","ODF");
        if (!(odf == null || odf.length == 0)) {
          for (let i = 0; i < odf.length; i++) {
            const element = odf[i];
          //  ds.push(element);
           dsOdfs.push(element);
          }
            this.dsDonVi= this.dsDonVi.concat(dsOdfs)
        }
      }
      
      for (let i = 0; i < this.dsDonVi.length; i++) {
        const item = this.dsDonVi[i];
        let ck = this.dsDonVi.findIndex(f => {
          return f.DONVI_CHA_ID == item.DONVI_ID;
        });
        if (ck >= 0) item.HasChildren = true;
      }
      }
      catch(e){
        console.log('Err:',e)
      }finally{
       this.$root.showLoading(false);
      }
     // this.dsDonVi = ds; //HasChildren
    },
    getParents: function(parentID) {
      let rs = [];
      let parent = this.dsDonVi.find(x => x.DONVI_ID == parentID);
      if (parent != null) {
        rs.push(parent);
        if (parent.DONVI_CHA_ID != null) {
          let ps = this.getParents(parent.DONVI_CHA_ID);
          if (!(ps == null || ps.length == 0)) {
            for (let i = 0; i < ps.length; i++) {
              rs.push(ps[i]);
            }
          }
        }
      }
      return rs;
    },
    getDsDonVi: async function() {
      let result = await this.$root.context.post(
        "/web-ecms/danhmuc/layCayDonVi"
      );
      if (!(result.data == null || result.data.length == 0)) {
        let d = [];
        //let items = result.data
        let items = result.data.filter(f => {
          return f.DONVI_CHA_ID == null;
        });
        for (let i = 0; i < items.length; i++) {
          const item = items[i];
          let childs = result.data.filter(f => {
            return f.DONVI_CHA_ID == item.DONVI_ID;
          });
          let itemData = {
            DONVI_ID: item.DONVI_ID.toString(),
            TEN_DV: item.TEN_DV,
            DONVI_CHA_ID: null,
            HasChildren: false,
            Expanded: false,
            ITEM_TYPE: "DONVI"
          };
          d.push(itemData);
          if (!(childs == null || childs.length == 0)) {
            itemData.HasChildren = true;

            for (let k = 0; k < childs.length; k++) {
              const child = childs[k];
              let childData = {
                DONVI_ID: child.DONVI_ID.toString(),
                TEN_DV: child.TEN_DV,
                DONVI_CHA_ID: item.DONVI_ID,
                HasChildren: false,
                Expanded: false,
                ITEM_TYPE: "DONVI"
              };
              d.push(childData);
              let r = this.getDsDonViRecursive(childData, result.data);
              if (r == null || r.length > 0) {
                for (let j = 0; j < r.length; j++) {
                  const element = r[j];
                  d.push(element);
                }
              }
            }
          }
        }
        return d;
      } else return [];
    },
    getDsDonViRecursive: function(parentItem, listItems) {
      let rs = [];
      let items = listItems.filter(f => {
        return f.DONVI_CHA_ID == parentItem.DONVI_ID;
      });
      for (let i = 0; i < items.length; i++) {
        const item = items[i];
        let childData = {
          DONVI_ID: item.DONVI_ID.toString(),
          TEN_DV: item.TEN_DV,
          DONVI_CHA_ID: parentItem.DONVI_ID.toString(),
          HasChildren: false,
          Expanded: false,
          ITEM_TYPE: "DONVI"
        };
        rs.push(childData);
        let childs = this.getDsDonViRecursive(childData, listItems);
        if (!(childs == null || childs.length == 0)) {
          for (let j = 0; j < childs.length; j++) {
            const child = childs[j];
            rs.push(child);
          }
        }
      }
      return rs;
    },
    treeDonVi_nodeClicked: async function(args) {
      let item = this.dsDonVi.find(x => x.DONVI_ID == args.node.dataset.uid);
      if (item != null) {
        console.log('node:',item)
        //Xử lý các tác vụ khi chọn node
        if (item.ITEM_TYPE == "TURACK"){

        }
        if (item.ITEM_TYPE == "BRAS"){
          
        } 
        if (item.ITEM_TYPE == "BRASCARD"){
          
        }
        if (item.ITEM_TYPE == "BRASPORT"){
          
        }
         if (item.ITEM_TYPE == "GPON"){
          
        }
         if (item.ITEM_TYPE == "GPONCARD"){
          
        }
         if (item.ITEM_TYPE == "GPONPORT"){
          
        }
         if (item.ITEM_TYPE == "MANE"){
          
        }
         if (item.ITEM_TYPE == "MANECARD"){
          
        } 
         if (item.ITEM_TYPE == "MANEPORT"){
          
        }
         if (item.ITEM_TYPE == "MANEMODULE"){
          
        }

        if (item.ITEM_TYPE == "MANEPORTMODULE"){
          
        }
        if (item.ITEM_TYPE == "SWITCH"){
          
        }
        if (item.ITEM_TYPE == "SWITCHCARD"){
          
        }
        if (item.ITEM_TYPE == "SWITCHPORT"){
          
        }
        if (item.ITEM_TYPE == "SWITCHCARDPORT"){
          
        }
        if (item.ITEM_TYPE == "DSLAM"){
          
        }
        if (item.ITEM_TYPE == "DSLAMFRAME"){
          
        }
        if (item.ITEM_TYPE == "DSLAMSHELF"){
          
        }
        if (item.ITEM_TYPE == "DSLAMCARD"){
          
        }
        if (item.ITEM_TYPE == "DSLAMPORT"){
          
        }
        if (item.ITEM_TYPE == "DSLAMMODULE"){
          
        }
        if (item.ITEM_TYPE == "DSLAMMODULEPORT"){
          
        }
        if (item.ITEM_TYPE == "FRAMETD"){
          
        }
         if (item.ITEM_TYPE == "FRAMECARD"){
          
        }
        if (item.ITEM_TYPE == "ODF"){
          
        }
        // if (item.ITEM_TYPE == "SHELF_EWSD") {
        //   this.$root.showLoading(true);
        //   try {
        //     let itemID = item.DONVI_ID.replace(item.ITEM_TYPE + "_", "");
        //     let itemData = this.dsShelfEwsdFull.find(
        //       x => x.SHELF_EWSD_ID == itemID
        //     );
        //     if (itemData != null) {
        //       this.currentItem = {
        //         SHELF_EWSD_ID: itemData.SHELF_EWSD_ID,
        //         TEN_SHELF: itemData.TEN_SHELF,
        //         EWSD_ID: itemData.EWSD_ID,
        //         SO_SLOT: itemData.SO_SLOT,
        //         DADUNG: itemData.DADUNG,
        //         VITRI: itemData.VITRI
        //       };
        //     }
        //     this.dsCard = await this.getDsCard(itemID);
        //   } finally {
        //     this.$root.showLoading(false);
        //   }
        // } else {
        //   this.currentItem = {};
        // }
      } else {
        this.currentItem = {};
      }
    },
    treeDonVi_expanded: function(args){
      if(args!=null){
       const type=  args.nodeData.id.split('_')[0]
       const id=  args.nodeData.id.split('_')[1]
       if(type=="GPONCARD1"){
         if(this.nodesId[args.nodeData.id]==null){
         let dsPortGpon = this.dsGPON_Port.filter(x=>x.CARDGP_ID==id)
         let dsGPON_Port=[]
         let gpon_port = this.getDsFromList(dsPortGpon,"PORTVL_ID","VITRI","CARDGP_ID","GPONCARD","GPONPORT");
         console.log('gpon_port:',gpon_port)
        if (!(gpon_port == null || gpon_port.length == 0)) {
          for (let i = 0; i < gpon_port.length; i++) {
            const element = gpon_port[i];
          dsGPON_Port.push(element);
          }
        this.dsDonVi= this.dsDonVi.concat(dsGPON_Port)
        }else{
        let ck = this.dsDonVi.find(f => 
           f.DONVI_ID == args.nodeData.id);
        
        if (ck >= 0) ck.HasChildren = false;
        }
        this.nodesId[args.nodeData.id]=true
        }
       }
      }
    },
    //#region  "Hàm lấy dữ liêu"
    getDsTuRack: async function() {
      try{
      let result = await this.$root.context.get(
        "/web-ecms/danhmuc/turack",{kieutu:2}
      );
      return result.data;}catch(e){
        return []
      }
    },
    //Bras
    getDsBras: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsBras"
      );
      return result.data;
    },
    getDsBrasCard: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsBrasCard"
      );
      return result.data;
    },
    getDsBrasPort: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsBrasPort"
      );
      return result.data;
    },
    //dsLam:1; Switch:2; Gpon:3; Mane:17
    //Gpon
    getDslamGponSwitch: async function(loai_tbi) {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsDslamGponSwitch",{
          loaiTBiId:loai_tbi
        }
      );
      return result.data;
    },
    getDsGponCard: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsGponCard"
      );
      return result.data;
    },
    getDsGponPort: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsGponPort"
      );
      return result.data;
    },
    //Mane
    getDsManeCard: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsCardMane"
      );
      return result.data;
    },
    getDsManePort: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsPortMane"
      );
      return result.data;
    },   
    getDsManeModule: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/DslamMane"
      );
      return result.data;
    },
    getDsManePortMudule: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/PortModuleMane"
      );
      return result.data;
    },
    //Switch
    getDsSwitchCard: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsCardSwitch"
      );
      return result.data;
    },  
    getDsSwitchPort: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsSwitchPort"
      );
      // let switch_id=[]
      // switch_id= this.dsSwitch.map(x=>x.SWITCH_ID)
      // return result.data.filter(x=>switch_id.includes(x.SWITCH_ID));
      return result.data;
    },
    getDsSwitchPortCard: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsSwitchCardPort"
      );
      return result.data;
    },
    //dsLam
    getDsdsLamFrame: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/danhmuc/dslam-frame/getDsDsLamFrame"
      );
      return result.data;
    },
    getDsdsLamShelf: async function() {
       try{
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsDslShelf"
      );
      return result.data;
      }catch(e){
        return []
      }
    },
    getDsdsLamCard: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/danhmuc/dslam-card/danh-sach"
      );
      return result.data;
    },
    getDsdsLamPort: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsPortDslam"
      );
      return result.data;
    },  
    getDsdsLamModule: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsDslamModule"
      );
      return result.data;
    },
    getDsdsLamPortMudule: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsPortModule"
      );
      return result.data;
    },
    //frameTruyenDan
    getDsFrameTruyenDan: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/frame-truyendan/get-frametd"
      );
      return result.data;
    },
    getDsFrameCard: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsFrameCard"
      );
      return result.data;
    },  
    //ODF
    getDsOdf: async function() {
      let result = await this.$root.context.get(
        "/web-ecms/thietbichuyenmach/dsOdf"
      );
      return result.data;
    },  

    getDsTuRackFromList: function(listItems) {
      let result = [];
      if (!(listItems == null || listItems.length == 0)) {
        for (let i = 0; i < listItems.length; i++) {
          const tuRack = listItems[i];
          let donViID = "TURACK_" + tuRack.TURACK_ID.toString();
          let ck = result.findIndex(x => x.DONVI_ID == donViID);
          if (ck < 0) {
            let tuRackData = {
              DONVI_ID: donViID,
              TEN_DV: tuRack.TEN_TU,
              DONVI_CHA_ID: tuRack.DONVI_ID,
              HasChildren: false,
              Expanded: false,
              ITEM_TYPE: "TURACK"
            };
            result.push(tuRackData);
          }
        }
      }
      return result;
    },
    getDsFromList: function(listItems,myID,myName,idParent,typeParent,myType) {
      try{
      let result = [];
      if (!(listItems == null || listItems.length == 0)) {
        for (let i = 0; i < listItems.length; i++) {
          const data = listItems[i];
          let donViID = myType+"_"+ data[myID].toString();
          let name= data[myName]!=null?data[myName].toString():""
          let ck = result.findIndex(x => x.DONVI_ID == donViID);
          if (ck < 0) {
            let objData = {
              DONVI_ID: donViID,
              TEN_DV: name,
              DONVI_CHA_ID: typeParent+"_" +  data[idParent],
              HasChildren: false,
              Expanded: false,
              ITEM_TYPE: myType
            };
            result.push(objData);
          }
        }
      }
      return result;}
      catch(e){
        console.log(myType,e)
      }
    },
    //#endregion

  }
};
</script>
