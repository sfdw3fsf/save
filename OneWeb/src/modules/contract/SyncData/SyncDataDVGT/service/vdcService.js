import Vue from "vue";
let root = {}
let login = {}
export default {
    setupRoot:function(_root){
    root = _root
    },
    setLogin: function(_login){
      Object.assign(login,_login)
    },
    ChangeBillingInfo:async function(account, billingInfo, note){
      try {
        let rs = await root.context.post(
          "/tichhop/visa-app/changeBillingInfo",
          {
            accountModel: {accountName:account},
            billingInfo: billingInfo,
            login:login,
            note: note,
          }
        );
        if(rs.errorCode == 0 || rs.faultString.includes("Không có sự thay đổi"))
        return true;
        else{
          root.toastError(rs.faultString);
          return false;        
        }
      } catch (error) {
        return false;
      } finally {
      }
    },
    Kiemtra_doituong_vdc: async function (hdtb_id, loaihinh_id) {
        try {
          let rs = await root.context.get(
            "/web-ccdv/dongbo-visa-vasc/kiemtra_doituong_vdc",
            {
              hdtb_id: hdtb_id,
              loaihinh_id: loaihinh_id,
            }
          );
          console.log('Kiemtra_doituong_vdc',rs)
         if(rs.data !=null && rs.data.length >0){
          if(rs.data[0].rs ==0)
          return false;
          else 
          return true;
         }
         return false;
        } catch (error) {
          return false;
        } finally {
        }
      },
      // Hàm này chưa có trên tích hợp
    ChangeCustomerType: async function (account,cusType) {
      try {
        let rs = await root.context.post(
          "/tichhop/visa-app/changeCustomerType",
          {
            customerModel: {accountName:account},
            login:login,
            note: cusType,
          }
        );
        
        if(rs.errorCode == 0 || rs.faultString.includes("Không có sự thay đổi"))
        return true;
        else{
          root.toastError(rs.faultString);
          return false;        
        }
      } catch (error) {
        return false;
      } finally {
      }
      },
      ThaydoiGoiCuoc: async function(account, tocdo_id, ip, tucthi, autoIP, giuIP){
        try
        {
          console.log('ThaydoiGoiCuoc',tucthi)
            //Thay doi tuc thi
            if (tucthi == 0)
            {
              return await this.modifyServiceTypeMegaVNN(account, tocdo_id, ip, autoIP, giuIP)
            }
            //Sang thang sau moi ap dung
            if (tucthi == 1)
            {
              return  await this.modifyServiceTypeMegaVNN1(account, tocdo_id, ip, autoIP, giuIP)
            }
            return true;
        }
        catch (ex)
        {
           return false
        }
      },
    async  modifyServiceTypeMegaVNN(account, tocdo_id, ip, autoIP, giuIP){
        try {
          let rs = await root.context.post(
            "/tichhop/visa-app/modifyServiceTypeMegaVNN",
            {
              accountModel: {accountName:account},
              accountServiceModel:{serviceId : tocdo_id},
              autoIpFlag: autoIP,
              ip:ip,
              oldIp:giuIP,
              login: login
            }
          );
          if(rs.errorCode == 0 || rs.faultString.includes("Không có sự thay đổi"))
          return true;
          else{
              root.toastError(rs.faultString);
             return false;
          }

          // if(rs.data == null)
          //  {
          //    if(rs.faultString.includes("Không có sự thay đổi"))
          //    return true;
          //    else{
          //     root.toastError(rs.faultString);
          //    return false;
          //    }
          //  }
          // else
          // return rs.data == "ok"

        } catch (error) {
          console.log(error)
          return false;
        } finally {
        }
       // return true
      },
    async  modifyServiceTypeMegaVNN1(account, tocdo_id, ip, autoIP, giuIP){
        return true
      },
      updateStatusVNN: async function(myaccount, lydo, ghichu){
        try {
          let rs = await root.context.post(
            "/tichhop/visa-app/updateStatusVNN",
            {
              accountModel: myaccount,
             // login:login,
              note: ghichu,
              reason:lydo
            }
          );
          if(rs.errorCode == 0 || rs.faultString.includes("Không có sự thay đổi"))
          return true;
          else{
              root.toastError(rs.faultString);
             return false;
          }
        } catch (error) {
          return false;
        } finally {
        }
      },
      HuyDatChoMegaVNN: async function(accountmoi){
       try {
          let rs = await root.context.post(
            "tichhop/visa-app/unReservedAccount",
            {
              accountName: accountmoi,
              login:login
            }
          );
          if(rs.errorCode == 0 || rs.faultString.includes("chưa được đặt chỗ"))
          return true;
          else{
              root.toastError(rs.faultString);
             return false;
          }
        } catch (error) {
          return false;
        } finally {
        }
      },
      ModifyAccountNameVNN: async function(accountcu, accountmoi){
        try {
          let rs = await root.context.post(
            "/tichhop/visa-app/modifyAccountNameVNN",
            {
              accountModel: {
                accountName:accountcu,
                newAccountName:accountmoi},
              login:login
            }
          );
          if(rs.errorCode == 0 || rs.faultString.includes("Không có sự thay đổi"))
          return true;
          else{
              root.toastError(rs.faultString);
             return false;
          }
        } catch (error) {
          return false;
        } finally {
        }
      },
      ModifyMailRASName: async function(vmail_cu, accountmoi){
          try {
          let rs = await root.context.post(
            "/tichhop/visa-app/modifyMailRASName",
            {
              accountModel: {accountName:vmail_cu,
                newAccountName:accountmoi,
                accType:"M",
                reason:"Doi mail"},
              newAccountName: accountmoi,
              login:login,
            }
          );
          if(rs.errorCode == 0 || rs.faultString.includes("Không có sự thay đổi"))
          return true;
          else{
              root.toastError(rs.faultString);
             return false;
          }
        } catch (error) {
          return false;
        } finally {
        }
      },
      RemoveVNN: async function(account,  lydohuy,  chuthich){
        try {
          let rs = await root.context.post(
            "/tichhop/visa-app/removeVNN",
            {
              accountModel: {accountName:account},
              login:login,
              note: chuthich,
              reason:lydohuy
            }
          );
          if(rs.errorCode == 0 || rs.faultString.includes("Không có sự thay đổi"))
          return true;
          else{
              root.toastError(rs.faultString);
             return false;
          }
        } catch (error) {
          return false;
        } finally {
        }
    },
    changePortMegaVNN: async function(account, attributes){
      try {
        let rs = await root.context.post(
          "/tichhop/visa-app/changePortMegaVNN",
          {
            accountModel: {accountName:account},
            login:login,
            attributes: attributes
          }
        );
        if(rs.errorCode == 0 || rs.faultString.includes("Không có sự thay đổi")) 
          return true;
          else{
              root.toastError(rs.faultString);
             return false;
          }
      } catch (error) {
        return false;
      } finally {
      }
    },
    getIps: async function(brasId, serviceId){
      try {
        let rs = await root.context.post(
          "/tichhop/visa-report/getIps",
          {
           // accountModel: {accountName:account},
            login:login,
            brasId: brasId,
            serviceId:serviceId        
          }
        );
        console.log('ip:',rs.data)
        return rs.data;
      } catch (error) {
        return null;
      } finally {
      }
    },
    getIpsV6: async function(brasId, serviceId){
      try {
        let rs = await root.context.post(
          "/tichhop/visa-report/getIpsV6",
          {
           // accountModel: {accountName:account},
            login:login,
            brasId: brasId ,
            serviceId:serviceId    
          }
        );
        return rs.data;
      } catch (error) {
        return null;
      } finally {
      }
    },
    modifyTariffType: async function(account, loaicuoc_id, tucthi){
      try {
        let rs = await root.context.post(
          "/tichhop/visa-app/modifyTariffType",
          {
            accountModel: {accountName:account},
            login:login,
            accountServiceModel: {tariffType:loaicuoc_id},
            effectType:tucthi
          }
        );
        console.log('modifyTariffType',rs)
        if(rs.errorCode == 0 || rs.faultString.includes("Không có sự thay đổi"))
        return true;
        else{
            root.toastError(rs.faultString);
           return false;
        }
      } catch (error) {
        return false;
      } finally {
      }
    },
    RestoreAccount: async function(account){
      try {
        let obj = [];
        let accountModel = new Object();
        obj[0] = 1539070;
        accountModel.historyIds = obj;
        accountModel.accountName = account;
        let rs = await root.context.post(
          "/tichhop/visa-app/restoreAccount",
          {
            accountModel: accountModel,
            login:login
          }
        );
        console.log('restoreAccount',rs)
        if(rs.errorCode == 0 || rs.faultString.includes("Không có sự thay đổi"))
        return true;
        else{
            root.toastError(rs.faultString);
           return false;
        }
      } catch (error) {
        return false;
      } finally {
      }
    }
}