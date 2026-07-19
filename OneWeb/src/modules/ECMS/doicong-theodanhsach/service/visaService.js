let root = {}
let login = {}
export default {
    setupRoot:function(_root){
    root = _root
    },
    setLogin: function(_login){
      Object.assign(login,_login)
     // console.log("🚀 ~ file: visaService.js ~ line 10 ~ login", login)
    },
    changePortMegaVNN: async function (account, brasId, dslamId, port, slot, vci, vpi)
    {
      let data = {
        accountModel: {
            accountName: account
        },
        attributes: [
            {
                type: "PORT",
                value: port
            },
            {
                type: "VCI",
                value: vci
            },
            {
                type: "VPI",
                value: vpi
            },
            {
                type: "SLOT",
                value: slot
            },
            {
                type: "DSLAM-ID",
                value: dslamId
            },
            {
                type: "BRAS-ID",
                value: brasId
            },
            {
                type: "PARAMETER",
                value: "VISANEW"
            }
        ]
      }

      try {
        let rs = await root.context.post("/tichhop/visa-app/changePortMegaVNN", data);
        return rs.data;
      }
       catch (error) {
          return false;
      }
      finally {
      }
    },
     lookupRASAccount: async function (account) {
      try {
        let rs = await root.context.post(
          "/tichhop/visa-app/lookupRASAccount",
          {
            accountName: account,
          }
        );
        return !rs.data ? "Lỗi lấy mật khẩu trên visa: " + rs.faultString : rs.data[0].accountPassword;
      } catch (error) {
        return false;
      } finally {
      }
      },
  async UpdateDeviceInfo(IPTVAccount, strDeviceName, strPortID, context) {
    try {
      let response = await context.$root.context.post(`/tichhop/vasc/updateDeviceInfo`, {
        myTVAccount: IPTVAccount,
        strDeviceName: strDeviceName,
        strPortID: strPortID
      })
      return response.errorCode;
    } catch (err) {
      return 1
    }
  }
}
