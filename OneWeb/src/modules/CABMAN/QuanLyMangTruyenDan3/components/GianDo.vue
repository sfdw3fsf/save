<template>
     <div></div>
</template>
<script>
import Vue from 'vue'
import EventBus from '@/utils/eventBus'

//  <network ref="network" class="wrapper"
//       :nodes="Points"
//       :edges="Links"
//       :style="{width: '100%',cursor:canvasCursor, border: 'lightgray 1px solid'}"
//       :options="options"
//     >
//   </network>
// import { Network } from 'vue-vis-network'
// Vue.component('network', Network)

export default Vue.extend({
  data: function () {
    return {
      canvasCursor: 'default',
      Points: [],
      Links: [],
      nodes: [
        {id: 1, label: 'circle', shape: 'circle' },
        {id: 2, label: 'circle', shape: 'circle'},
        {id: 3, label: 'circle', shape: 'circle'},
        {id: 4, label: 'circle', shape: 'circle' },
        {id: 5, label: 'circle', shape: 'circle'},
        {id: 6, label: 'circle', shape: 'circle'},
        {id: 7, label: 'circle', shape: 'circle'},
        {id: 8, label: 'circle', shape: 'circle'},
        {id: 11, label: 'circle', shape: 'circle' },
        {id: 12, label: 'circle', shape: 'circle'},
        {id: 13, label: 'circle', shape: 'circle'},
        {id: 14, label: 'circle', shape: 'circle' },
        {id: 15, label: 'circle', shape: 'circle'},
        {id: 16, label: 'circle', shape: 'circle'},
        {id: 17, label: 'circle', shape: 'circle'},
        {id: 18, label: 'circle', shape: 'circle'}
      ],
      edges: [
        {from: 1, to: 2},
        {from: 2, to: 3},
        {from: 2, to: 4},
        {from: 2, to: 5},
        {from: 5, to: 6},
        {from: 5, to: 7},
        {from: 6, to: 8},
        {from: 11, to: 8},
        {from: 11, to: 8},
        {from: 12, to: 8},
        {from: 8, to: 13},
        {from: 13, to: 14},
        {from: 14, to: 15},
        {from: 12, to: 16},
        {from: 16, to: 17},
        {from: 1, to: 11},
        {from: 2, to: 18},
        {from: 1, to: 8},
        {from: 2, to: 13},
        {from: 3, to: 14},
        {from: 4, to: 1},
        {from: 12, to: 6},
        {from: 11, to: 1},
        {from: 1, to: 5},
        {from: 18, to: 5}
      ],
      options: {
        layout: {
          // hierarchical: {
          //   enabled: true,
          //   direction: 'LR',
          //   nodeSpacing: 30,
          //   treeSpacing: 30,
          //   levelSeparation: 150,
          //   blockShifting: true,
          //   edgeMinimization: true,
          //   sortMethod: 'hubsize',
          //   shakeTowards: 'roots'
          // }
          // randomSeed: 1,
          // improvedLayout: false,
          // hierarchical: {
          //   enabled: true,
          //   levelSeparation: 150,
          //   nodeSpacing: 110,
          //   treeSpacing: 200,
          //   blockShifting: false,
          //   edgeMinimization: true,
          //   parentCentralization: true,
          //   direction: 'LR',
          //   sortMethod: 'directed',
          //   shakeTowards: 'roots'
          // }
          randomSeed: undefined,
          improvedLayout: true,
          hierarchical: {
            enabled: true,
            levelSeparation: 350,
            nodeSpacing: 210,
            treeSpacing: 200,
            blockShifting: false,
            edgeMinimization: true,
            parentCentralization: true,
            direction: 'LR',
            sortMethod: 'hubsize' // hubsize, directed
          }
        },
        nodes: {
          borderWidth: 1,
          color: '#333',
          font: {
            color: 'white'
          },
          shape: 'image'
        },
        edges: {
          color: 'gray',
          smooth: {
            type: 'cubicBezier',
            forceDirection: 'horizontal',
            roundness: 0.4
            // enabled: true,
            // type: 'curvedCW',
            // roundness: 0.2
          }
        },
        physics: true
      }
    }
  },
  provide: {

  },
  watch: {

  },
  computed: {

  },
  mounted: function () {

  },
  created () {

  },
  methods: {
    TimPoint (Id) {
      var idx = this.Points.findIndex(x => x.id === Id)
      if (idx > -1) return this.Points[idx]
      else return {}
    },
    TimLink (Id) {
      var idx = this.Links.findIndex(x => x.id === Id)
      if (idx > -1) return this.Links[idx]
      else return {}
    },
    TaoPoint  (nodeID, text, stringStyle, tooltip) {
      return { id: nodeID, label: '', shape: 'image', image: '/static/icons/icon100x100/HOST.jpg', size: 25 }
    },
    CapNhatPoint (Point) {
      this.Points.push(Point)
    },
    TaoLink (id, ketcuoi_n, ketcuoi_d) {
      var Link = { id: id, from: ketcuoi_n.id, to: ketcuoi_d.id, color: { color: '#ff0000', highlight: 'blue' } }
      this.Links.push(Link)
      return Link
    },
    Clear () {
      // diagramInstance.clear()
      this.Links = []
      this.Points = []
    },
    DoLayout () {
      // diagramInstance.doLayout()
    }
  }
})
</script>
<style>
.wrapper{
  min-height: 75vh;
  min-width: 120vh;
  border: 1px solid black;
  background-color: #F9FBFA ;
  padding: 10px;
  height: 75vh;
  width: auto;
}
 .network{
  min-height:55vh;/* 95vh;*/
  border: 1px solid black;
  background: linear-gradient(to bottom, rgba(215, 215, 255), rgba(250, 250, 170));
  padding: 10px;
  height: 95vh;
}
.vis-label{
  color: black;
}
</style>
