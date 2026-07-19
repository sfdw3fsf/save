<template src='./MoHinhBaoHanhThietBi.html'></template>
<style scoped src='./MoHinhBaoHanhThietBi.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import breadcrumb from '@/components/breadcrumb'
import MoHinhBaoHanhThietBiAPI from  '../api/MoHinhBaoHanhThietBiAPI'
import { getterName,actionName,statePropertyName,mutationName} from '../store/mohinhbaohanhthietbi'
import select2 from '@/components/Select2.vue'
import {
  ConnectorConstraints,
  NodeConstraints,
  SnapConstraints,
} from "@syncfusion/ej2-vue-diagrams";
import { Freeze, Group, Page, Filter, Sort, Resize, ForeignKey} from "@syncfusion/ej2-vue-grids";
//Initializes the nodes for the diagram
let nodes = []
let connectors  = []
let moveToRight = 100;
let alignWidth = -22
let alignHeight = 30
export default {
  components: {breadcrumb, appSelect2: select2 },
  name: "MoHinhBaoHanhThietBi",

  mounted() {
  },
  provide: {
    grid: [ Freeze, Group, Page,Filter,Sort,Resize, ForeignKey ]
  },
  computed: {
    ...mapState("mohinhbaohanhthietbi", statePropertyName),
    ...mapGetters("mohinhbaohanhthietbi", getterName),
    p_quytrinh_id: {
      get() {
        return this.params.p_quytrinh_id
      },
      set(value) {
        return this.params.p_quytrinh_id = value
      }
    },
    p_luongphieu_id: {
      get() {
        return this.params.p_luongphieu_id
      },
      set(value) {
        return this.params.p_luongphieu_id = value
      }
    },
  },
  data() {
    return {
      header: {
        title: "MÔ HÌNH BẢO HÀNH THIẾT BỊ",
        list: [
          { name: "Quản lý vật tư", link: { name: "Ui.cards" } },
          {
            name: 'Bảo hành thiết bị',
            link: { name: 'Ui.buttons' }
          },
          {
            name: "Mô hình bảo hành thiết bị",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      isShowSideBar: false,
      width: "100%",
      height: "100%",
      nodes: [],
      connectors: [],
      getNodeDefaults: (node) => {
        let obj = {
          style: { fill: "#BAE7FF", strokeColor: "#0176FF", strokeWidth: 1, },
          shape: { cornerRadius: 4 },
          constraints: NodeConstraints.Select | NodeConstraints.PointerEvents,
          shad: 2
        };
        return obj;
      },
      //Sets the default values of a connector
      getConnectorDefaults: (obj) => {
        obj.constraints = ConnectorConstraints.ReadOnly;
        obj.sourceDecorator = { shape: 'None' };
        obj.style = {
            strokeColor: '#0176FF',
            fill: '#0176FF',
            strokeWidth: 2
        },
        obj.strokeColor = '#0176FF',
        obj.fill = '0176FF',
        obj.strokeWidth = 2

      },
      snapSettings: { constraints: SnapConstraints.None },
      params: {
        p_quytrinh_id: '',
        p_luongphieu_id: ''

      },
      selectLuongPhieuTheoQT: [],
      isPin: false,
    };
  },
  methods: {
    ...mapActions("mohinhbaohanhthietbi", actionName),
    ...mapMutations("mohinhbaohanhthietbi", mutationName),
    ...mapMutations("xulythietbibaohanh",["setXuLyTBBH"]),
    // cboQuynhTrinh_EditValueChanged
    async onQuyTrinhChange(){
      if(!this.p_quytrinh_id){ return}
      await this.getDSLuongPhieuTheoQT(parseInt(this.p_quytrinh_id))
      this.selectLuongPhieuTheoQT = []
      this.DSLuongPhieuTheoQT.forEach(a=>this.selectLuongPhieuTheoQT.push({'id': parseInt(a.LUONG_ID),'text' : a.HUONGGIAO}))
      let s1 = this.DSLuongPhieuTheoQT.filter(a=>a.TTBH_ID.toString() == "3")
      if (s1.length){
        this.p_luongphieu_id = parseInt(s1[0].LUONG_ID)
        await this.onLuongPhieuChange()
      }
    },
    // cboLuongPhieu_EditValueChanged
    async onLuongPhieuChange(){
      
      if (!this.p_luongphieu_id){return}
      await this.HienThiMoHinh()
    },
    // HienThiMoHinh
    async HienThiMoHinh(){
      await this.getDSBaoHanhThietBiV2({
        quyTrinhId: parseInt(this.p_quytrinh_id) ,
        luongId: parseInt(this.p_luongphieu_id),
        nhanVienId: this.$auth.getNhanVienID()
         })
      let lstTrangThai = [];
      let lstLine = [];
      let lstPic = [];
      let lstbutton = [];
      let lstPanelLine = [];
      let lstPicbox = [];
      this.nodes = [];
      this.connectors = []
      
      await this.getDSLine(this.p_luongphieu_id)
      // await this.getDSLine(748)
      for (let i in this.DSLine){
        lstLine.push({
          key: parseInt(this.DSLine[i].rlId),
          x: parseInt(this.DSLine[i].toaDoX),
          y: parseInt(this.DSLine[i].toaDoY),
          height: parseInt(this.DSLine[i].doCao),
          width: parseInt(this.DSLine[i].doRong),
        })
      }
            
      lstLine.forEach(a=>{
        let w = a.width > 2 ? a.width : 0;
        let h = a.height > 2 ? a.height : 0;
        let targetX = a.x +w;
        let targetY = a.y + h;
          
        this.connectors.push({
          id: a.key.toString(),
          sourcePoint: {x:a.x - alignWidth,y:a.y  -alignHeight},
          targetPoint: {x:targetX - alignWidth,y: targetY - alignHeight},
          type: "Orthogonal",
          targetDecorator:  { shape: 'None' }
        })
      })

      await this.getDSPic(this.p_luongphieu_id)
      // await this.getDSPic(748)
      for (let i in this.DSPic){
        lstPic.push({
          key: parseInt(this.DSPic[i].rlId),
          image_index: parseInt(this.DSPic[i].imageIndex),
          x: parseInt(this.DSPic[i].toaDoX),
          y: parseInt(this.DSPic[i].toaDoY),
          height: parseInt(this.DSPic[i].doCao),
          width: parseInt(this.DSPic[i].doRong),

        })
      }
      lstPic.forEach(c=>{
        let targetX = c.x 
        let targetY = c.y
        let plusX = 0
        let plusY = 0
        let subX = 0
        let subY = 0
        // arrow from top to bot
        if (c.image_index == 2){
          plusY = c.height;
          targetX += 4
        }
        // arrow from left to right
        else if(c.image_index == 1){
          plusX = c.width;
          targetY += 4
        }
        // arrow from right to left
        else if (c.image_index == 3){
          targetY += 4
          targetX += 15
          subX = c.width
        }
        
        let targetDecorator = {
            style: {
                  strokeColor: '#0176FF',
                  fill: '#0176FF', 
                  strokeWidth: 2
              }, 
            shape: 'Arrow',
            width: c.width,
            height: c.height
          }
        this.connectors.push({
          id: c.key.toString(),
          sourcePoint: {x: targetX -alignWidth ,y: targetY -alignHeight },
          targetPoint: {x: targetX + plusX - subX -alignWidth ,y: targetY  + plusY - subY -alignHeight},
          type: "Straight",
          targetDecorator: targetDecorator
        })
      })

      for (let i in this.DSBaoHanhThietBiV2){
        lstTrangThai.push({
          key: parseInt(this.DSBaoHanhThietBiV2[i].RL_ID) ,
          trangthai_id: parseInt(this.DSBaoHanhThietBiV2[i].TTBH_ID), 
          tentrangthai: this.DSBaoHanhThietBiV2[i].TTBH_ID + ". " +this.DSBaoHanhThietBiV2[i].TRANGTHAI_BH.toString() + "(" + this.DSBaoHanhThietBiV2[i].SL.toString() + ")" , 
          x: parseInt(this.DSBaoHanhThietBiV2[i].TOADO_X),
          y:parseInt(this.DSBaoHanhThietBiV2[i].TOADO_Y),
          height: parseInt(this.DSBaoHanhThietBiV2[i].DOCAO),
          width: parseInt(this.DSBaoHanhThietBiV2[i].DORONG),
        })
      }
      lstTrangThai.forEach(a=>this.nodes.push({
          id: a.trangthai_id.toString(),
          offsetX: a.x + moveToRight,
          offsetY: a.y,
          annotations: [{ content: a.tentrangthai },],
          width: a.width,
          height: a.height
        }))

    },
    async doubleClickNode(node){
      console.log('A',node)
      if (node.source.annotations){
        this.$router.push('XuLyThietBiBaoHanh')
        await this.setXuLyTBBH({
          luong_id: parseInt(this.p_luongphieu_id),
          quytrinh_id: parseInt(this.p_quytrinh_id),
          trangthaibh_id_n: node.source.id,
          trangthai_bh_n: node.source.annotations[0].content
        })
      }
    }

  },
  created: async function(){
    this.clearData();
     
    await this.getQuyTrinhBHTB()
    this.p_quytrinh_id = this.$route.query.quytrinh_id || "" 
    this.p_luongphieu_id = this.$route.query.luongphieu_id || ""
    
    if (!this.p_quytrinh_id){
      this.p_quytrinh_id = this.DanhSachQuyTrinh.length ? this.DanhSachQuyTrinh[0].id : 0;
      await this.onQuyTrinhChange()
    }
    else{
      await this.getDSLuongPhieuTheoQT(parseInt(this.p_quytrinh_id))
      await this.onLuongPhieuChange()
    }
    
  },
  watch: {
    
  },
  destroyed () {
    this.$refs.diagramControl ? this.$refs.diagramControl.destroy() : ''
  }
};

//creation of Port for Node.
// function getPorts(obj)  {
//   if (obj.id === "3") {
//     return [portOut1,portOut2];
//   } else if (obj.id === "4") {
//     return [portIn1,portOut1,portOut2];
//   } else if (obj.id === "5" ) {
//     return [portIn3,portOut1,portOut2]
//   }
//   else if (obj.id === "6") {
//     return [portIn3,portOut1,portOut2]
//   }
//   else if (obj.id === "7") {
//     return [portIn1,portIn3,portOut3,portOut2]
//   }
//   else if (obj.id === "8") {
//     return [portIn1,portOut4]
//   }
//   else if (obj.id === "9") {
//     return [portIn1,portIn7,portOut3]
//   }
//   else if (obj.id === "10") {
//     return [portIn1,portIn3,portOut5]
//   }
//   else if (obj.id === "16") {
//     return [portIn2,portOut3,portOut4]
//   }
//   else if (obj.id === "22") {
//     return [portIn2,portOut4]
//   }
//   else if (obj.id === "17") {
//     return [portIn7,portOut4]
//   }
//   else if (obj.id === "18") {
//     return [portIn2,portOut4]
//   }
//   else if (obj.id === "23") {
//     return [portIn2,portIn4,portIn5,portIn6,portIn7]
//   }
  
// }
</script>
