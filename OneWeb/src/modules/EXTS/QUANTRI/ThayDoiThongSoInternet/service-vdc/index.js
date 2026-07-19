export default {
    async changePortMegaVNN(data, context) {
        try {
            let response = await context.$root.context.post(`/tichhop/visa-app/changePortMegaVNN`, data)

            if(response.errorCode == 0 || response.faultString.includes("Không có sự thay đổi")) {
                return {
                    hasError: false,
                    message: response.faultString
                }
            } else {
                return {
                    hasError: true,
                    message: response.faultString
                }
            }
        } catch (err) {
            return {
                hasError: true,
                message: ""
            }
        }
    },
    async DoiCongMegavnn4(account, PORT, VCI, VPI, SLOT, BrasId, DslamId, context) {
        let data = {
            accountModel: {
                accountName: account
            },
            attributes: [
                {
                    type: "PORT",
                    value: PORT
                },
                {
                    type: "VCI",
                    value: VCI
                },
                {
                    type: "VPI",
                    value: VPI
                },
                {
                    type: "SLOT",
                    value: SLOT
                },
                {
                    type: "DSLAM-ID",
                    value: DslamId
                },
                {
                    type: "BRAS-ID",
                    value: BrasId
                },
                {
                    type: "PARAMETER",
                    value: "VISANEW"
                }
            ]
        }
        return await this.changePortMegaVNN(data, context)
    },
    async DoiCongMegavnn7(account, PORT, VCI, VPI, SLOT, BrasId, DslamId, SYSTEMID, RACKID, SHELFID, context) {
        let data = {
            accountModel: {
                accountName: account
            },
            attributes: [
                {
                    type: "PORT",
                    value: PORT
                },
                {
                    type: "VCI",
                    value: VCI
                },
                {
                    type: "VPI",
                    value: VPI
                },
                {
                    type: "SLOT",
                    value: SLOT
                },
                {
                    type: "DSLAM-ID",
                    value: DslamId
                },
                {
                    type: "BRAS-ID",
                    value: BrasId
                },
                {
                    type: "RACKID",
                    value: RACKID
                },
                {
                    type: "SYSTEMID",
                    value: SYSTEMID
                },
                {
                    type: "SELFID",
                    value: SHELFID
                },
                {
                    type: "PARAMETER",
                    value: "VISANEW"
                }
            ]
        }
        return await this.changePortMegaVNN(data, context)
    },
    async DoiCongMegavnn9_Mega(account, PORT, VCI, VPI, SLOT, BrasId, DslamId, SYSTEMID, SELFID01, SELFID02, SELFID03, SELFID04, context) {
        let data = {
            accountModel: {
                accountName: account
            },
            attributes: [
                {
                    type: "PORT",
                    value: PORT
                },
                {
                    type: "VCI",
                    value: VCI
                },
                {
                    type: "VPI",
                    value: VPI
                },
                {
                    type: "SLOT",
                    value: SLOT
                },
                {
                    type: "DSLAM-ID",
                    value: DslamId
                },
                {
                    type: "BRAS-ID",
                    value: BrasId
                },
                {
                    type: "SYSTEMID",
                    value: SYSTEMID
                },
                {
                    type: "SELFID01",
                    value: SELFID01
                },
                {
                    type: "SELFID02",
                    value: SELFID02
                },
                {
                    type: "SELFID03",
                    value: SELFID03
                },
                {
                    type: "SELFID04",
                    value: SELFID04
                },
                {
                    type: "PARAMETER",
                    value: "VISANEW"
                }
            ]
        }
        return await this.changePortMegaVNN(data, context)
    },
    async DoiCongMegavnn9_FE(account, PORT, VLAND_ID, AXCI, SLOT, BrasId, DslamId, SYSTEMID, SELFID01, SELFID02, SELFID03, SELFID04, context) {
        let data = {
            accountModel: {
                accountName: account
            },
            attributes: [
                {
                    type: "PORT",
                    value: PORT
                },
                {
                    type: "VLAN_ID",
                    value: VLAND_ID
                },
                {
                    type: "AXCI",
                    value: AXCI
                },
                {
                    type: "SLOT",
                    value: SLOT
                },
                {
                    type: "DSLAM-ID",
                    value: DslamId
                },
                {
                    type: "BRAS-ID",
                    value: BrasId
                },
                {
                    type: "SYSTEMID",
                    value: SYSTEMID
                },
                {
                    type: "SELFID01",
                    value: SELFID01
                },
                {
                    type: "SELFID02",
                    value: SELFID02
                },
                {
                    type: "SELFID03",
                    value: SELFID03
                },
                {
                    type: "SELFID04",
                    value: SELFID04
                },
                
                {
                    type: "PARAMETER",
                    value: "VISANEW"
                }
            ]
        }
        return await this.changePortMegaVNN(data, context)
    },
    async DoiCongMegavnn9_ADSL(account, PORT, VCI, VPI, SLOT, BrasId, DslamId, SYSTEMID, SELFID01, SELFID02, SELFID03, SELFID04, context) {
        let data = {
            accountModel: {
                accountName: account
            },
            attributes: [
                {
                    type: "PORT",
                    value: PORT
                },
                {
                    type: "VLAN_ID",
                    value: VCI
                },
                {
                    type: "AXCI",
                    value: VPI
                },
                {
                    type: "SLOT",
                    value: SLOT
                },
                {
                    type: "DSLAM-ID",
                    value: DslamId
                },
                {
                    type: "BRAS-ID",
                    value: BrasId
                },
                {
                    type: "SYSTEMID",
                    value: SYSTEMID
                },
                {
                    type: "SELFID",
                    value: SELFID01
                },
                {
                    type: "OLT_SLOT",
                    value: SELFID02
                },
                {
                    type: "OLT_PORT",
                    value: SELFID03
                },
                {
                    type: "ONUID",
                    value: SELFID04
                },
                {
                    type: "PARAMETER",
                    value: "VISANEW"
                }
            ]
        }
        return await this.changePortMegaVNN(data, context)
    },
    async getPass(account, context) {
        try {
            let response = await context.$root.context.post(`/tichhop/visa-app/lookupRASAccountJson`, {
                accountName: account,
                login: {
                    userId: context.$root.context.user.getProperty('user_visa'),
                    password: context.$root.context.user.getProperty('pass_visa')
                }
            })
            
            if (!response.data)
            {
                throw response.faultString
            }

            let sPass = "", sPassDangKy = "";
            let logObject = response.data.lookupRASAccountReturn[4];

            for (let i = 0; i < logObject.length; i++)
            {
                let actionLog = logObject[i];

                if (actionLog.actionId == 2812 || actionLog.actionId == 29112025) // action doi mat khau
                {
                    sPass = actionLog.actionDesc;
                }

                if (actionLog.actionId == 29112018) // action dang ky - cũ
                {
                    sPassDangKy = actionLog.actionDesc;
                }

                if (actionLog.actionId == 2823) // action dang ky
                {
                    sPassDangKy = actionLog.actionDesc;
                }

                if (!!sPass || !!sPassDangKy) 
                {
                    break;
                }
            }

            if (sPass == null)
            {
                return "";
            }
            else if (sPass != "")
            {
                let s = sPass.split('\n');

                for (let i = 0; i < s.length; i++)
                {
                    if (s[i].includes("Mật khẩu mới:"))
                    {
                        sPass = s[i].replace("Mật khẩu mới: ", "").replace(" [", "").replace("[", "").replace("]", "").replace(" Qua webservice", "");
                    }

                    if (s[i].includes("Mật khẩu thay đổi thành:"))
                    {
                        sPass = s[i].replace("Mật khẩu thay đổi thành: ", "").replace(" [", "").replace("[", "").replace("]", "");
                    }
                }

                s = sPass.split('!');

                if (s.length == 2)
                {
                    sPass = s[1];
                }
                    
                return sPass;
            }
            else
            {
                if (sPassDangKy == null)
                {
                    return "";
                }

                if (sPassDangKy.startsWith("Password:"))
                {
                    let s = sPassDangKy.split(',');
                    sPassDangKy = s[0].replace("Password:", "");
                }
                else if (sPassDangKy.includes("Mật khẩu:"))
                {
                    let s = sPassDangKy.split('\n');

                    if (s.length <= 2)
                    {
                        s = sPassDangKy.split(',');
                    }

                    for (let i = 0; i < s.length; i++)
                    {
                        if (s[i].includes("Mật khẩu:"))
                        {
                            sPassDangKy = s[i].replace("Mật khẩu: ", "").replace("Mật khẩu:", "").replace(" [", "").replace("\t", "").replace("[", "").replace("]", "");
                        }
                    }
                }

                return sPassDangKy;
            }
        } catch (err) {
            throw err.toString()
        }
    }
}