<template src="./index.template.html"></template>
<style src="./styles.scss" scoped></style>

<script>
export default {
  name: 'ChiTietKetNoiDien',
  components: {},
  props: {
    rackID: {
      type: [String, Number],
      required: true
    },
    type: {
      // 1: mặt trước, 2: mặt sau
      type: [Number],
      type: String,
      default: 1,
    }
  },
  data() {
    return {
      // Rack Information
      rackInfo: {
        name: 'RACK-S1',
        id: 'rack-s1',
        totalU: 42,
        usedU: 27,
        totalDevices: 17,
        totalPower: 4810, // in watts
        availablePower: 1190 // in watts
      },
      
      rackData: {
        totalU: 42,
        usedU: 0
      },
      
      // Fake data mẫu cho các thiết bị mặt trước
      frontDevices: [
        {
          startU: 36,
          uCount: 2,
          name: 'DB Server 1',
          details: '2U (36-37)',
          type: 'server',
          thietbiId: 1,
          loaiTbi: 'Máy chủ',
          trangThai: 'ON',
          congSuat: 420,
          deviceCode: 'SRV-005',
          deviceDescription: 'Dell R740, 4x Xeon Gold, 512GB RAM, RAID 10',
          deviceImage: 'https://icons.veryicon.com/png/o/miscellaneous/open-ncloud/the-server-4.png',
          status: 'active',
          powerConnections: {
            pduA: ['A5', 'A6'],
            pduB: ['B5', 'B6', 'B7']
          }
        },
        {
          startU: 34,
          uCount: 2,
          name: 'DB Server 2',
          details: '2U (34-35)',
          type: 'server',
          thietbiId: 2,
          loaiTbi: 'Máy chủ',
          trangThai: 'ON',
          congSuat: 420,
          deviceCode: 'SRV-006',
          deviceDescription: 'Dell R740, 4x Xeon Gold, 512GB RAM, RAID 10',
          deviceImage: 'https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/value-server-512.png',
          status: 'active',
          powerConnections: {
            pduA: ['A7', 'A8'],
            pduB: ['B8', 'B9']
          }
        },
        {
          startU: 32,
          uCount: 1,
          name: 'Switch Core',
          details: '1U (32)',
          type: 'network',
          thietbiId: 3,
          loaiTbi: 'Switch',
          trangThai: 'ON',
          congSuat: 150,
          deviceCode: 'SW-001',
          deviceDescription: 'Cisco Catalyst 9300, 48 Ports, 10GbE',
          deviceImage: 'https://image.dienthoaivui.com.vn/x,webp,q90/https://dashboard.dienthoaivui.com.vn/uploads/dashboard/editor_upload/hwBRYjCsCgQae7GtdZHYsiHMUsi4V1cnRWTT3Kne.jpg',
          status: 'active',
          powerConnections: {
            pduA: ['A9'],
            pduB: ['B10']
          }
        },
        {
          startU: 28,
          uCount: 4,
          name: 'Storage Array',
          details: '4U (28-31)',
          type: 'storage',
          thietbiId: 4,
          loaiTbi: 'Storage',
          trangThai: 'ON',
          congSuat: 800,
          deviceCode: 'STO-001',
          deviceDescription: 'EMC VNX 5400, 120TB Capacity',
          deviceImage: 'https://icons.veryicon.com/png/o/miscellaneous/open-ncloud/the-server-4.png',
          status: 'active',
          powerConnections: {
            pduA: ['A10', 'A11'],
            pduB: ['B11', 'B12']
          }
        },
        {
          startU: 24,
          uCount: 2,
          name: 'Web Server 1',
          details: '2U (24-25)',
          type: 'server',
          thietbiId: 5,
          loaiTbi: 'Máy chủ',
          trangThai: 'ON',
          congSuat: 350,
          deviceCode: 'SRV-007',
          deviceDescription: 'HP ProLiant DL380, 2x Xeon, 128GB RAM',
          deviceImage: 'https://icons.veryicon.com/png/o/miscellaneous/open-ncloud/the-server-4.png',
          status: 'active',
          powerConnections: {
            pduA: ['A12'],
            pduB: ['B13']
          }
        },
        {
          startU: 20,
          uCount: 1,
          name: 'Router Main',
          details: '1U (20)',
          type: 'network',
          thietbiId: 6,
          loaiTbi: 'Router',
          trangThai: 'ON',
          congSuat: 120,
          deviceCode: 'RT-001',
          deviceDescription: 'Cisco ASR 1001-X, 10GbE',
          deviceImage: 'https://icons.veryicon.com/png/o/miscellaneous/open-ncloud/the-server-4.png',
          status: 'active',
          powerConnections: {
            pduA: ['A13'],
            pduB: ['B14']
          }
        },
        {
          startU: 15,
          uCount: 3,
          name: 'App Server Cluster',
          details: '3U (15-17)',
          type: 'server',
          thietbiId: 7,
          loaiTbi: 'Máy chủ',
          trangThai: 'ON',
          congSuat: 600,
          deviceCode: 'SRV-008',
          deviceDescription: 'Dell PowerEdge R640, 2x Xeon Silver, 256GB RAM',
          deviceImage: 'https://icons.veryicon.com/png/o/miscellaneous/open-ncloud/the-server-4.png',
          status: 'active',
          powerConnections: {
            pduA: ['A14', 'A15'],
            pduB: ['B15', 'B16']
          }
        },
        {
          startU: 10,
          uCount: 2,
          name: 'Backup Server',
          details: '2U (10-11)',
          type: 'server',
          thietbiId: 8,
          loaiTbi: 'Máy chủ',
          trangThai: 'ON',
          congSuat: 400,
          deviceCode: 'SRV-009',
          deviceDescription: 'Dell PowerEdge R740, 2x Xeon Gold, 512GB RAM',
          deviceImage: 'https://icons.veryicon.com/png/o/miscellaneous/open-ncloud/the-server-4.png',
          status: 'active',
          powerConnections: {
            pduA: ['A16'],
            pduB: ['B1']
          }
        }
      ],
      
      // Fake data cho rPDU ports
      pduPorts: {
        pduA: [],
        pduB: []
      },
      
      // PDU Information
      pduAInfo: {
        nguon: 'PDU1A/0/6',
        dungLuong: '32A / 7.3kW',
        dangDung: 21,
        tongSo: 21
      },
      pduBInfo: {
        nguon: 'PDU1B/3/8',
        dungLuong: '32A / 7.3kW',
        dangDung: 21,
        tongSo: 21
      }
    }
  },
  computed: {
    frontRackPositions() {
      return this.buildRackPositions(this.frontDevices)
    },
    pduAPorts() {
      return this.buildPDUPorts('A', 16)
    },
    pduBPorts() {
      return this.buildPDUPorts('B', 16)
    },
    // Computed để lấy danh sách thiết bị hiển thị (có thể filter/sort)
    displayDevices() {
      return this.frontDevices.filter(d => d.thietbiId && d.type !== 'empty')
    },
    // Computed để tính tổng công suất
    computedTotalPower() {
      return this.frontDevices.reduce((sum, device) => {
        return sum + (device.congSuat || 0)
      }, 0)
    },
    // Computed để tính số U đã sử dụng
    computedUsedU() {
      return this.frontDevices.reduce((sum, device) => {
        return sum + (device.uCount || 0)
      }, 0)
    }
  },
  methods: {
    buildRackPositions(devices) {
      const totalU = this.getTotalU()
      const positions = []

      for (let i = totalU; i >= 1; i--) {
        const device = this.getDeviceAtU(i, devices)
        const isFirstU = device && i === device.startU

        positions.push({
          position: i,
          uCount: 1,
          device: device,
          type: device ? device.type : 'empty',
          emptyText: device ? '' : '',
          isFirstU: isFirstU,
          showName: isFirstU
        })
      }

      return positions
    },

    getTotalU() {
      return this.rackData.totalU || 42
    },

    getFrontUsedU() {
      let usedU = 0
      this.frontDevices.forEach((device) => {
        if (device.type !== 'empty' && device.thietbiId !== 0) {
          usedU += device.uCount
        }
      })
      return usedU
    },

    getFrontUsagePercentage() {
      const total = this.getTotalU()
      const used = this.getFrontUsedU()
      return Math.round((used / total) * 100 * 10) / 10
    },

    getDeviceAtU(uPosition, devices) {
      return devices.find((device) => {
        const endU = device.startU + device.uCount - 1
        return uPosition >= device.startU && uPosition <= endU
      })
    },

    getUHeight(u) {
      return (u.uCount || 1) * 20
    },

    getSlotClass(u) {
      if (!u.device) {
        return 'slot-empty'
      }
      return 'slot-' + u.type
    },

    handleDeviceClick(u) {
      // Xử lý click vào thiết bị (có thể mở modal chi tiết)
      if (u.device && u.device.thietbiId !== 0 && u.device.type !== 'empty') {
        console.log('Device clicked:', u.device)
        // Có thể thêm logic mở modal chi tiết thiết bị ở đây
      }
    },
    
    handlePDUPortClick(pduType, port) {
        // Xử lý click vào rPDU port
        console.log(`rPDU ${pduType} Port clicked:`, port)
    },

    buildPDUPorts(prefix, totalPorts) {
      const ports = []
      const phaseLabels = ['L1', 'L2', 'L3']
      const phaseColors = ['red', 'blue', 'orange']
      
      for (let i = 1; i <= totalPorts; i++) {
        const phaseIndex = (i - 1) % 3
        ports.push({
          id: `${prefix}${i}`,
          label: phaseLabels[phaseIndex],
          color: phaseColors[phaseIndex],
          phaseIndex: phaseIndex
        })
      }
      
      return ports
    },

    getPDUPortClass(port) {
      return `pdu-port-${port.color}`
    },

    getPDUUsageClass(dangDung, tongSo) {
      if (dangDung === tongSo) {
        return 'pdu-usage-full'
      } else if (dangDung / tongSo >= 0.8) {
        return 'pdu-usage-warning'
      }
      return 'pdu-usage-normal'
    },
    
    // Format công suất với đơn vị
    formatPower(watts) {
      return `${watts}W`
    },
    
    // Format vị trí U
    formatPosition(device) {
      if (device.uCount === 1) {
        return `U${device.startU} (${device.uCount}U)`
      }
      return `U${device.startU} (${device.uCount}U)`
    }
  }
};
</script>
