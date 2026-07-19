export default {
  MyFunctionController,
  TableFunc,

  colsWanSetup: [
    {fieldName: "wanIfname", headerText: "Interface", allowFiltering: true, allowSorting: false, width: 150},
    {fieldName: "vlanMuxID", headerText: "VlanMuxID", allowFiltering: true, allowSorting: false, width: 150},
    {fieldName: "wanType", headerText: "Wan Type", allowFiltering: true, allowSorting: false, width: 150},
    {fieldName: "connectionType", headerText: "Connection Type", allowFiltering: true, allowSorting: false, width: 150},
    {fieldName: "natEnable", headerText: "Nat", allowFiltering: true, allowSorting: false, width: 150},
    {fieldName: "firewallEnable", headerText: "Firewall", allowFiltering: true, allowSorting: false, width: 150},
    {fieldName: "ipv4Enable", headerText: "Status IPv4", allowFiltering: true, allowSorting: false, width: 150},
    {fieldName: "ipv6Enable", headerText: "Status IPv6", allowFiltering: true, allowSorting: false, width: 150},
    {fieldName: "externalIPAdress", headerText: "IPAddress", allowFiltering: true, allowSorting: false, width: 150},
  ],

  colsInterfaceGroup: [
    {fieldName: "index", headerText: "Index", allowFiltering: true, allowSorting: false, width: 150},
    {fieldName: "groupName", headerText: "Group name", allowFiltering: true, allowSorting: false, width: 200},
    {fieldName: "wanInterfaceName", headerText: "Wan Interface", allowFiltering: true, allowSorting: false, width: 200},
    {fieldName: "lanInterfaceName", headerText: "Lan Interface", allowFiltering: true, allowSorting: false, width: 200},
  ],

  objectWan: () => {
    return {
      "addressingType": "",
      "connectionID": 0,
      "connectionStatus": "",
      "connectionType": "",
      "defaultGateway": "",
      "defaultIPv6Gateway": "",
      "dhcp6cForAddress": "",
      "dhcp6cForPrefixDelegation": "",
      "dhcp6cPrefixDelegationEnable": "",
      "dnsIfName": "",
      "dnsServers": "",
      "enable": true,
      "externalIPAdress": "",
      "externalIPv6Address": "",
      "firewallEnable": true,
      "fullConeNatEnable": true,
      "igmpEnabled": true,
      "igmpSourceEnable": true,
      "ipv4Enable": true,
      "ipv6AddressingType": "",
      "ipv6DnsServer": "",
      "ipv6Enable": true,
      "ipv6Status": "",
      "mldEnabled": true,
      "mldSourceEnable": true,
      "natEnable": true,
      "password": "",
      "subnetMask": "",
      "unnumberedModel": true,
      "userName": "",
      "vciValue": 0,
      "vlanMuxID": 0,
      "vlanPriority": 0,
      "vlanTPID": 0,
      "vpiValue": 0,
      "wanIfname": "",
      "wanIndex": 0,
      "wanInstancePath": "",
      "wanName": "",
      "wanType": ""
    }
  },

  ResSIPObject: () => {
    return {
      enableVoiceService: false,
      boundIfName: '',
      ipAddressFamily: '',
      region: '',
      digitMap: '',
      userAgentDomain: '',
      proxyServer: '',
      proxyServerPort: '',
      registrarServer: '',
      registrarServerPort: '',
      outBoundProxy: '',
      outBoundProxyPort: '',
      dtmfMethod: '',
    }
  },

  ResLineObject: () => {
    return {
      id: -1,
      enable: '',
      callIDNamer: '',
      sipAuthUserName: '',
      sipAuthUserPass: '',
      sipUri: '',
      callState: '',
      status: '',
    }
  },

  ResVoice: () => {
    return {
      SIPObject: null,
      LineObject: null
    }
  }
}

export class MyFunctionController {
  constructor(ID, Code, Caption) {
    this.ID = ID;
    this.Code = Code;
    this.Caption = Caption;
    this.PlaceHolder = '';
    this.ActionName = '';
    this.Executed = null;
    this.Text = ID + ". " + Caption;
  }
}

export class TableFunc {
  constructor(name, value, shortname, id) {
    this.name = name;
    this.value = value;
    this.shortname = shortname;
    this.id = id;
  }
}
