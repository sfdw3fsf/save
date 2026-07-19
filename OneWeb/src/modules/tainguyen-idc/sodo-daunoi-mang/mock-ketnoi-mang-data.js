// Mock data cho sơ đồ kết nối mạng
// Cấu trúc: Internet/Edge Layer -> Firewall -> Core Layer -> Distribution Layer -> Access Layer

export const mockNetworkConnectionData = {
  // Định nghĩa các containers (nếu cần)
  containers: [],

  devices: [
    // ========== LEVEL 0 - Internet/Edge Layer ==========
    {
      ID: 'router_1',
      NAME: 'Router',
      TYPE: 'ROUTER',
      HIERARCHY_LEVEL: 0,
      PARENT_ID: null,
      TRANGTHAI: 'HOATDONG'
    },

    // ========== LEVEL 1 - Firewall Layer ==========
    {
      ID: 1,
      NAME: 'Firewall01',
      TYPE: 'FIREWALL',
      HIERARCHY_LEVEL: 1,
      PARENT_ID: 'router_1',
      TRANGTHAI: 'HOATDONG'
    },
    {
      ID: 2,
      NAME: 'Firewall02',
      TYPE: 'FIREWALL',
      HIERARCHY_LEVEL: 1,
      PARENT_ID: 'router_1',
      TRANGTHAI: 'HOATDONG'
    },
    {
      ID: 3,
      NAME: 'Firewall03',
      TYPE: 'FIREWALL',
      HIERARCHY_LEVEL: 1,
      PARENT_ID: 'router_1',
      TRANGTHAI: 'HOATDONG'
    },

    // ========== LEVEL 2 - Core Layer ==========
    {
      ID: 'core_switch_1',
      NAME: 'Switch 1',
      TYPE: 'CORE_SWITCH',
      HIERARCHY_LEVEL: 2,
      PARENT_ID: 1,
      TRANGTHAI: 'HOATDONG'
    },
    {
      ID: 'core_switch_2',
      NAME: 'Switch 2',
      TYPE: 'CORE_SWITCH',
      HIERARCHY_LEVEL: 2,
      PARENT_ID: 2,
      TRANGTHAI: 'HOATDONG'
    },

    // ========== LEVEL 3 - Distribution Layer ==========
    {
      ID: 'distribution_sw_1',
      NAME: 'Distribution 1',
      TYPE: 'DISTRIBUTION_SWITCH',
      HIERARCHY_LEVEL: 3,
      PARENT_ID: 'core_switch_1',
      TRANGTHAI: 'HOATDONG'
    },
    {
      ID: 'distribution_sw_2',
      NAME: 'Distribution 2',
      TYPE: 'DISTRIBUTION_SWITCH',
      HIERARCHY_LEVEL: 3,
      PARENT_ID: 'core_switch_2',
      TRANGTHAI: 'HOATDONG'
    },

    // ========== LEVEL 4 - Access Layer ==========
    {
      ID: 'access_sw_1',
      NAME: 'Access 1',
      TYPE: 'ACCESS_SWITCH',
      HIERARCHY_LEVEL: 4,
      PARENT_ID: 'distribution_sw_1',
      TRANGTHAI: 'HOATDONG'
    },
    {
      ID: 'access_sw_2',
      NAME: 'Access 2',
      TYPE: 'ACCESS_SWITCH',
      HIERARCHY_LEVEL: 4,
      PARENT_ID: 'distribution_sw_2',
      TRANGTHAI: 'HOATDONG'
    }
  ],

  connections: [
    // Router -> Firewalls
    { FROM: 'router_1', TO: 1, TYPE: 'DUONG_QUANG' },
    { FROM: 'router_1', TO: 2, TYPE: 'DUONG_QUANG' },
    { FROM: 'router_1', TO: 3, TYPE: 'DUONG_DU_PHONG' },

    // Firewalls -> Core Switches
    { FROM: 1, TO: 'core_switch_1', TYPE: 'DUONG_QUANG' },
    { FROM: 2, TO: 'core_switch_2', TYPE: 'DUONG_QUANG' },
    { FROM: 3, TO: 'core_switch_2', TYPE: 'DUONG_DU_PHONG' },

    // Core Switches -> Distribution Switches
    { FROM: 'core_switch_1', TO: 'distribution_sw_1', TYPE: 'SINGLE_MODE' },
    { FROM: 'core_switch_2', TO: 'distribution_sw_2', TYPE: 'SINGLE_MODE' },
    { FROM: 'core_switch_1', TO: 'distribution_sw_2', TYPE: 'MULTI_MODE' }, // Backup connection

    // Distribution Switches -> Access Switches
    { FROM: 'distribution_sw_1', TO: 'access_sw_1', TYPE: 'DUONG_DONG' },
    { FROM: 'distribution_sw_2', TO: 'access_sw_2', TYPE: 'DUONG_DONG' }
  ]
}
