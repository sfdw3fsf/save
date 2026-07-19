<template src="./index.template.html"></template>
<script>
import Vue from 'vue';
import { Marker, MapsTooltip,
 ILoadEventArgs,NavigationLine,
  MarkerSettings, Zoom , Print } from '@syncfusion/ej2-vue-maps';
import {ButtonPlugin}  from "@syncfusion/ej2-vue-buttons";
import { ContextMenuPlugin } from "@syncfusion/ej2-vue-navigations";
Vue.use(ContextMenuPlugin);
Vue.use(ButtonPlugin);
let latitude = [];
let longitude = [];
let navigationLines = [];
export default {
  provide: {
    maps: [Marker, MapsTooltip, NavigationLine, Zoom,Print]
},
created: async function () {

  },
  methods: {
load: function(args) {
      let selectedTheme = location.hash.split("/")[1];
      selectedTheme = selectedTheme ? selectedTheme : "Bootstrap4";
      args.maps.theme =
        selectedTheme.charAt(0).toUpperCase() + selectedTheme.slice(1);
    },
    markerChange: function(args){
        this.markerCheck = args.checked;
        if(args.checked){
           this.dropDisabled = true; 
        } else {
            this.dropDisabled = false; 
        }
    },
    connectChange : function(args){
        this.connectCheck = args.checked;
        if(!args.checked){
            this.emptySavedLinePositions();
            this.dropPolyDisabled=false;
        }else
        this.dropPolyDisabled=true;
    },
    
    widthChange : function(args){
        this.textValue = args.value;
    },
    
    click: function(args){
        let maps = this.$refs.maps.ej2Instances;
        if(this.markerCheck){
           this.addMarker(args); 
        }
        if(this.lineCheck && !this.connectCheck) {
            this.addLine(args, this.textValue);
        }
        if (this.connectCheck && !this.ConnectDisabled) {
            this.addLine(args, this.textValue, true);
        }
        if (this.markerCheck || this.lineCheck || this.connectCheck) {
            maps.refresh();
            if (this.disabled && (maps.layers[0].markerSettings.length ||
                    maps.layers[0].navigationLineSettings.length)) {
                this.disabled = false;
            }
        }
    },
    btnClick: function(args){
        let maps = this.$refs.maps.ej2Instances;
        maps.layers[0].markerSettings = [];
        maps.layers[0].navigationLineSettings = [];
        navigationLines = [];
        this.emptySavedLinePositions();
        maps.refresh();
        this.disabled = true;
    },
    lineChange: function(args) {
       this.lineCheck = args.checked;
       if(args.checked){
           this.ConnectDisabled= false;
           this.widthDisabled = true;
       } else {
           this.emptySavedLinePositions();
           this.ConnectDisabled= true;
           this.widthDisabled = false;
       } 
    },
    emptySavedLinePositions: function() {
        latitude = [];
        longitude = [];
    },
    
    addMarker: function(args){
        let marker; 
        let markerShape = this.$refs.type.ej2Instances;
        if (args["latitude"] !== null && args["longitude"] !== null) {
             let vido= args["latitude"]
             let kinhdo = args["longitude"]
            let layerIndex = (args.target.indexOf('_LayerIndex_') !== -1) ?
                parseFloat(args.target.split('_LayerIndex_')[1].split('_')[0]) : 0;
            let maps = this.$refs.maps.ej2Instances;
            let dynamicMarker = maps.layersCollection[layerIndex].markerSettings;
            dynamicMarker.push(new MarkerSettings(maps, 'markerSettings', marker));
            let markerIndex = dynamicMarker.length - 1;
            dynamicMarker[markerIndex].visible = true;
            dynamicMarker[markerIndex].dataSource = [
                { latitude: args["latitude"], longitude: args["longitude"], name: 'dynamicmarker' }
            ];
            dynamicMarker[markerIndex].animationDuration = 0;
            dynamicMarker[markerIndex].fill = '#DB4537';
            dynamicMarker[markerIndex].height = (markerShape.value !== 'Image') ? 12 : 20;
            dynamicMarker[markerIndex].width = (markerShape.value !== 'Image') ? 12 : 20;
            dynamicMarker[markerIndex].imageUrl = (markerShape.value !== 'Image') ? '' : "";
            dynamicMarker[markerIndex].shape = (markerShape.value !== 'Image') ? markerShape.value : 'Image';
            dynamicMarker[markerIndex].tooltipSettings={
                            visible: true,
                            format: '<b>One BSS</b><br>Tọa độ:<br>'+ vido+', '+ kinhdo
                  }                    
        }
    },
    addLine : function(lineArgs, lineWidth, connectiveLine){
        let maps = this.$refs.maps.ej2Instances; 
        let poly = this.$refs.poly.ej2Instances; 
        if (lineArgs.latitude != null && lineArgs.longitude != null) {
            latitude.push(lineArgs.latitude);
            longitude.push(lineArgs.longitude);
        } 
        if(latitude.length>=2){
            if(poly.value==1){
            navigationLines.push({
            "visible": true,
            "latitude": [latitude[(latitude.length - 2)], latitude[(latitude.length - 1)]],
            "longitude":[longitude[(longitude.length - 2)], longitude[(longitude.length - 1)]],
            "angle": 0,
            "width": (lineWidth > 5) ? 5 : (((5 >= lineWidth) && (lineWidth >= 1)) ? lineWidth : 1),
            "color": "blue"
        });       
        maps.layers[0].navigationLineSettings = navigationLines;
        if (!connectiveLine) {
            this.emptySavedLinePositions();
        }}
        else if(connectiveLine && poly.value==2){
            navigationLines.push({
            "visible": true,
            "latitude": [latitude[(latitude.length - 2)], latitude[(latitude.length - 1)], latitude[(latitude.length - 1)],latitude[(latitude.length - 2)]],
            "longitude":[longitude[(longitude.length - 2)], longitude[(longitude.length - 1)], longitude[(longitude.length - 1)],longitude[(longitude.length - 2)]],
            "angle": -180,
            "width": (lineWidth > 5) ? 5 : (((5 >= lineWidth) && (lineWidth >= 1)) ? lineWidth : 1),
            "color": "blue"
        });
         maps.layers[0].navigationLineSettings = navigationLines;
         this.emptySavedLinePositions();
        }
        else  if(connectiveLine && poly.value>2){
            navigationLines.push({
            "visible": true,
            "latitude": [latitude[(latitude.length - 2)], latitude[(latitude.length - 1)]],
            "longitude":[longitude[(longitude.length - 2)], longitude[(longitude.length - 1)]],
            "angle": 0,
            "width": (lineWidth > 5) ? 5 : (((5 >= lineWidth) && (lineWidth >= 1)) ? lineWidth : 1),
            "color": "blue"
        });
         if(latitude.length==poly.value)  {
            navigationLines.push({
            "visible": true,
            "latitude": [latitude[0], latitude[(latitude.length - 1)]],
            "longitude":[longitude[0], longitude[(longitude.length - 1)]],
            "angle": 0,
            "width": (lineWidth > 5) ? 5 : (((5 >= lineWidth) && (lineWidth >= 1)) ? lineWidth : 1),
            "color": "blue"
            });       
            this.emptySavedLinePositions();}      
          maps.layers[0].navigationLineSettings = navigationLines;        
        }
     }
    },
    onSelect: function(args ){
       if(args.item.text === 'Đây là đâu?') {
         this.isWhere=true
         console.log(args)
       }
    },
  markerClick: function(args) { 
      console.log(args);
         this.toado.vido = args.data.latitude
         this.toado.kinhdo=args.data.longitude
    } ,
    onBeforeItemRender: function(args){
        this.toado.kinhdo=""
         this.toado.vido=""
    }
  },
  data: function () {
    return {
     isWhere:false,
      zoomSettings: {
            enable: true
        },
        labelswidth: 120,
        localFields: { text: 'text', value: 'value' },
        labelsdata:[
            {value: 'Image', text: 'Image'},
            {value: 'Circle', text: 'Circle'},
            {value: 'Diamond', text: 'Diamond'},
            {value: 'Star', text: 'Star'},
            {value: 'Triangle', text: 'Triangle'},
        ],
        dsPoly:[
            {value: '1', text: '--N/A--'},
            {value: '2', text: 'Circle'},
            {value: '3', text: 'Triagle'},
            {value: '4', text: 'Rectangle'},
            {value: '5', text: 'Polygon'},
           
        ],
        allowPrint:true,
        markerDisabled:false,
        disabled:true,
        markerCheck:true,
        lineCheck:false,
        connectCheck:false,
        togglebtnCheck:false,
        ConnectDisabled: true,
        textValue: 1,
        widthDisabled: false,
        dropDisabled: true,
        dropPolyDisabled:false,
        toado:{
            kinhdo:"",
            vido:""
        },
            menuItems: [
        {
          text: "Đây là đâu?",
          iconCss: "icon text-main f20 one-light-question"
        },
        {
          text: "Liên kết tới cột tiếp theo",
          iconCss: "icon text-main f20 one-taocap"
        },  
         {
          text: "Liên kết tới cột thuộc tuyến khác",
          iconCss: "icon text-main f20 one-taokn"
        },    
        {
          text: "Xóa cột",
          iconCss: "icon text-main f20 one-trash"
        },      
        {
          separator: true
        },
        {
          text: "Thuộc tính",
          iconCss: "icon text-main f20 -ap icon-info"
        },
        {
          text: "New Comment",
          iconCss: "e-cm-icons e-cm-comment"
        }
      ]
    };
  },
};
</script>
<style scoped>
    .property-text {
        font-family: "Roboto", "Segoe UI", "GeezaPro", "DejaVu Serif", "sans-serif";
        font-size: 13px;
        font-weight: 400;
    }
    #width {
        border-bottom-color: inherit;
        background-image: none;
    }
</style>