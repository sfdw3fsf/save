import DateTimeLib from "../../../../../utils/DateTimeLib";
let root = {}
export default  {
   setupRoot:function(_root){
    root = _root
    },
    GetPackageEPG:async function(ma_tb,ngay_epg){ // đã chốt
        try {
            let rs = await root.context.post(
              "/tichhop/vasc/getPackageEPG",
              {
                endtime: DateTimeLib.toDateDisplay(new Date()),
                iptvaccount:ma_tb,
                starttime:ngay_epg,//
                strAll:"0"
              }
            );
            return rs;
          } catch (error) {
            return null;
          } finally {
          }
    },
    ChangePromotions:async function(IPTVAccount, sPromo_id){ // đã chốt
      try {
        let rs = await root.context.post(
          "/tichhop/vasc/changePromotions",
          {
            iptvaccount:IPTVAccount,
            spromo_ID:sPromo_id

          }
        );
        if (rs.data != null 
            && rs.data.result == 0 
            && rs.data.errordesc == "Thao tác thực hiện thành công" ) {
              return 0
        }
        else
          return 1
      } catch (error) {
        return null;
      } finally {
      }
    },
    ChangePackage: async function( tb_mytv,
        vmatocdo,
        ServiceCode,
        ServiceVASCode,
        lydo){
          try {
            let rs = await root.context.post(
              "/tichhop/vasc/changePackage",
              {
                iptvaccount: tb_mytv,
                packageNo: vmatocdo,
                reason: lydo,
                serviceCD: ServiceCode,
                serviceVAS:ServiceVASCode
              }
            );
    if (rs.errorCode == 0)
     return 0
   else {
    console.log(root)
    root.toastError(rs.faultString);
     return rs.errorCode;
    }
          } catch (error) {
            console.log(error)
            return 1;
          } finally {
          }
        },
    ChangeMegaVNN: async function(IPTVAccount, megaMyTV, vCombo_FiberCD){
        try {
          let rs = await root.context.post(
            "/tichhop/vasc/changeMegaVNN",
            {
              combo_FiberCD: vCombo_FiberCD,
              iptvaccount: IPTVAccount,
              megaMyTV: megaMyTV
            }
          );
          return rs;
        //   if (rs.data != 'ok')
        //   return 0
        // else
        // return 1
          } catch (error) {
            return null;
          } finally {
          }
   },
   STB_Rent_UpdateInfo: async function(request){
    try {
        //Gọi api ngoài
        let rs = await root.context.post(
          "/tichhop/vasc/sTB_Rent_UpdateInfo",{
           stbDTO: {
            rent_STATUS: request.RENT_STATUS,
            username: request.USERNAME
          }
        }
        );
      return rs.data;
      } catch (error) {
        return null;
      } finally {
      }
   },
   MethodChangePay:async function(tb_mytv, vhinhthuc, vthang){
    try {
        //Gọi api ngoài
        let rs = await root.context.post(
          "/tichhop/vasc/methodChangePay",{
            iptvaccount:tb_mytv,
            method: vhinhthuc,
            no_Month: vthang
          }

        );
        if (rs.errorCode == 0)
        return 0
      else
      return 1
      } catch (error) {
        return null;
      } finally {
      }
   },
   SuspendSubscriberV2: async function(IPTVAccount, ReasonID){
    try {
        let rs = await root.context.post(
          "/tichhop/vasc/suspendSubscriberV2",{
            iptvaccount:IPTVAccount,
            reasonID: ReasonID
          }
        );
      return rs;
      } catch (error) {
        return null;
      } finally {
      }
   },
   ResumeSubscriber: async function(IPTVAccount, Reason){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/resumeSubscriber",{
          iptvaccount:IPTVAccount,
          reason: Reason
        }
      );
      return rs;
    } catch (error) {
      return null;
    } finally {
    }
   },
   GetSubscriberInfoV2: async function(objRequest){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/getSubscriberInfoV2",{
          objRequest: objRequest
        }
      );
      return rs.data;
    } catch (error) {
      return null;
    } finally {
    }
   },
   ChangePackageV3: async function(objRequest){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/changePackageV3",{
          objRequest: objRequest
        }
      );
      if (rs.errorCode != 0){
        root.toastError(rs.faultString)
      }
      return rs.errorCode
    } catch (error) {
      return null;
    } finally {
    }
   },
   TotalMyTVPlus :async function(objRequest){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/totalMyTVPlus",{
          objRequest: objRequest
        }
      );
     //return rs.data;
     if (rs.errorCode != 0){
      root.toastError(rs.faultString)
    }
    return rs.errorCode
    } catch (error) {
      return null;
    } finally {
    }
    },
   PayTVFull_Register:async function(MYTVAccount, month){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/payTVFull_Register",{
          month: month,
          myTVAccount:MYTVAccount
        }
      );
     // return rs.data;
     if (rs.errorCode != 0){
      root.toastError(rs.faultString)
    }
    return rs.errorCode
    } catch (error) {
      return null;
    } finally {
    }
   },
   ComboFAF_Update:async function(MyTVAccount, MonthFree, Operator){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/comboFAF_Update",{
          monthFree: MonthFree,
          myTVAccount:MyTVAccount,
          operator:Operator
        }
      );
      if (rs.errorCode != 0){
        root.toastError(rs.faultString)
      }
      return rs.errorCode
    } catch (error) {
      return null;
    } finally {
    }
   },
   B2B_Room_RegistAddOn:async function(room){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/b2B_Room_RegistAddOn",{
          raddOnDTO:room
        }
      );
     // return rs.data;
     if (rs.errorCode != 0){
      root.toastError(rs.faultString)
    }
    return rs.errorCode
    } catch (error) {
      return null;
    } finally {
    }
   },
   B2B_Room_CancelAddOn:async function(room){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/b2B_Room_CancelAddOn",{
          raddOnDTO:room
        }
      );
      //return rs.data;
      if (rs.errorCode != 0){
        root.toastError(rs.faultString)
      }
      return rs.errorCode
    } catch (error) {
      return null;
    } finally {
    }
   },
   CancelSubscriberV2:async function(account, lydohuy_id){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/cancelSubscriberV2",{
          iptvaccount:account,
          reasonID:lydohuy_id
        }
      );
      console.log('cancelSubscriberV2',rs.data)
      if (rs.errorCode != 0){
        root.toastError(rs.faultString)
      }
      return rs.errorCode
      
    } catch (error) {
      console.log('error',error)
      return null;
    } finally {
    }
   },
   getSubscriberInfo: async function(iptvaccount){
    try {
      let rs = await root.context.post(
        "/tichhop/vasc/getSubscriberInfo",{
          iptvaccount: iptvaccount
        }
      );
      return rs.data;
    } catch (error) {
      return null;
    } finally {
    }
   },
  UpdateDeviceInfo: async function(IPTVAccount, strDeviceName, strPortID){
     try {
       let result = await root.context.post("/tichhop/vasc/updateDeviceInfo",{
         myTVAccount:IPTVAccount,
         strDeviceName:strDeviceName,
         strPortID:strPortID
       })
       if (result.errorCode != 0){
        root.toastError(result.faultString)
      }
      return result.errorCode
     }catch (e) {
       console.log(e)
     }
  },
  ChangePackageAndMegaVNN: async function(IPTVAccount,  packageNo,  serviceCode,  serviceVASCode,  megaMyTV,  reason,  vCombo_FiberCD){
    try {
      let result = await root.context.post("/tichhop/vasc/ChangePackageAndMegaVNN",{
        iptvaccount: IPTVAccount,
        megaMyTV:megaMyTV ,
        packageNo:packageNo ,
        reason: reason,
        serviceCD: serviceCode,
        serviceVAS: serviceVASCode
      })
      if (result.errorCode != 0){
        root.toastError(result.faultString)
      }
      return result.errorCode
    }catch (e) {
      console.log(e)
    }
 },
}
