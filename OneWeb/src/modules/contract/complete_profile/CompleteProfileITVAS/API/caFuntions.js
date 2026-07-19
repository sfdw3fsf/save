import moment from "moment";
import api from './CompleteProfileAPI'
import {YEUCAU_LAY_DULIEU_TOKEN, YEUCAU_DUYET_CMS, CA_CHOXULY, GUI_FILE_HOSO_CMS} from './ca_objects'
import {LOAI_HOPDONG} from '@/constants'
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions";


let CAConstanst = {
    ReqTypeCapMoi:  2,
    ReqTypeGiaHan:  8,
    ReqTypeTatCa:  -1,
    ReqTypeThayDoiThongTin:  49,
    ReqTypeThuHoi:  5,
    ReqTypeTamNgung:  6,
    ReqTypeKhoiPhuc: 60,
}
//TODO: Key fix tạm, sau đẩy lưu trên
//let key = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+PExpY2Vuc2U+PFBoYW5NZW0+Vk5QVC1DQSBQbHVnaW48L1BoYW5NZW0+PE5ndW9pQ2FwPlZOUFQgSVQ8L05ndW9pQ2FwPjxEb25WaUR1b2NDYXA+KjwvRG9uVmlEdW9jQ2FwPjxOZ2F5QmF0RGF1PjAyLzI0LzIwMjIgMDA6MDA6MDA8L05nYXlCYXREYXU+PE5nYXlLZXRUaHVjPjA1LzI0LzIwMjIgMDA6MDA6MDA8L05nYXlLZXRUaHVjPjxNYWNPUz4xPC9NYWNPUz48U2lnbmF0dXJlIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjIj48U2lnbmVkSW5mbz48Q2Fub25pY2FsaXphdGlvbk1ldGhvZCBBbGdvcml0aG09Imh0dHA6Ly93d3cudzMub3JnL1RSLzIwMDEvUkVDLXhtbC1jMTRuLTIwMDEwMzE1IiAvPjxTaWduYXR1cmVNZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjcnNhLXNoYTEiIC8+PFJlZmVyZW5jZSBVUkk9IiI+PFRyYW5zZm9ybXM+PFRyYW5zZm9ybSBBbGdvcml0aG09Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvMDkveG1sZHNpZyNlbnZlbG9wZWQtc2lnbmF0dXJlIiAvPjwvVHJhbnNmb3Jtcz48RGlnZXN0TWV0aG9kIEFsZ29yaXRobT0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnI3NoYTEiIC8+PERpZ2VzdFZhbHVlPnNuYTQ0ckEzTVBxdUh6QjJzYkw2SzR2MzU3Zz08L0RpZ2VzdFZhbHVlPjwvUmVmZXJlbmNlPjwvU2lnbmVkSW5mbz48U2lnbmF0dXJlVmFsdWU+YkZrdTV1SFliVHFBVkpMUjk0c1FySy9oMC9Tams2bmx1RVM0eVBRWHF5bndZNWxCQmRTMWR0MXlzd2p2SldkSHJ2RGRlYWI2QVBHSGlaUkl6dU94Q3lJRDE2a3RrWFRrMkI4dC85eXVGM1ByTEZqMEdSVVI5NEkyb2R5cjN6S2IxQVIvRldGNGFjNUJyMHJkZVljTDhTWU01WXh2YmJHTjlWOENEUmNrOVNFWjk0dmpISStWSTdxbjhDdzdnNlFxa0llWE4yMzlWbjg4Vzk5WlhaQnlHUXlpSE0vdzZVbE9KM04ySWJtbmF5dkcwUlp2bzlDNkIvVVhmVHUyRUgrU3ljQWV3UXkxb1ZPL3l3S0thdGdtOVlNSVFhaDRFZHRoaUxsVDg3bDY5N1pIbk5RSWdDaFBaTkNRbXVQUFErTU9vME9JNWQvT1ozWjZPN3BTbUtZK3pnPT08L1NpZ25hdHVyZVZhbHVlPjxLZXlJbmZvPjxLZXlWYWx1ZT48UlNBS2V5VmFsdWU+PE1vZHVsdXM+cDI4Y1VUbm9YRzlVbzc1NHBqd2RaR0g4bVg0VlpRa0F5a29xaHNBcHZnWVB5VVFFa0JFNmQ5cXNaTDM2R3YxakZaMXZLaG9uSTZmTEV4VnJiSERtOStEZUtlelZJWnFRdkxpczYzZzNocm16UnQrYy9vQXphNk9ScWVCVlZtUGhDWE93L0pEcnd4SDNDTFpybFIxWEd4bXNxbGppNytWTTBwQy9DSWtpZy81dEZ1L1BDdVRjVFkvTmYveWZtTVQ5N05peTlObkZOWDVjUXFERVk1bFNDRG9veTBocFNDbmNZUXN1TGNRSkFmWXp2enc5b29PakZqWU9Vdm1jZW1EQzEvMEVFOWRiV2xXZXRmblZOYjhzem9xc0xCckxBREIxQ2hTZm5sOW9RRnpHUVZhMFJpSjl4T3dQb0oyNWpRZFZpbUUxOHZUTVpXWnRtR05TMnIrK09RPT08L01vZHVsdXM+PEV4cG9uZW50PkFRQUI8L0V4cG9uZW50PjwvUlNBS2V5VmFsdWU+PC9LZXlWYWx1ZT48WDUwOURhdGE+PFg1MDlDZXJ0aWZpY2F0ZT5NSUlHUlRDQ0JDMmdBd0lCQWdJUVZBRWtqM3lYUkVJN3dLN0tSL2NIc2pBTkJna3Foa2lHOXcwQkFRVUZBREJwTVFzd0NRWURWUVFHRXdKV1RqRVRNQkVHQTFVRUNoTUtWazVRVkNCSGNtOTFjREVlTUJ3R0ExVUVDeE1WVms1UVZDMURRU0JVY25WemRDQk9aWFIzYjNKck1TVXdJd1lEVlFRREV4eFdUbEJVSUVObGNuUnBabWxqWVhScGIyNGdRWFYwYUc5eWFYUjVNQjRYRFRFM01ESXlOekE1TXpJd01Gb1hEVEU1TURJeU56SXhNekl3TUZvd2dZb3hDekFKQmdOVkJBWVRBbFpPTVJJd0VBWURWUVFJREFsSXc0QWdUdUc3bUVreEZUQVRCZ05WQkFjTURFUGh1cWQxSUVkcDRicWxlVEVzTUNvR0ExVUVBd3dqVms1UVZDQlRUMFpVVjBGU1JTQXRJRlpPVUZRZ1EwRWdMU0JVUlZOVUlGTkpSMDR4SWpBZ0Jnb0praWFKay9Jc1pBRUJEQkpOVTFRNk1UQXhOamcyT1Rjek9DMHdNVEl3Z2dFaU1BMEdDU3FHU0liM0RRRUJBUVVBQTRJQkR3QXdnZ0VLQW9JQkFRQ25ieHhST2VoY2IxU2p2bmltUEIxa1lmeVpmaFZsQ1FES1NpcUd3Q20rQmcvSlJBU1FFVHAzMnF4a3Zmb2EvV01Wblc4cUdpY2pwOHNURld0c2NPYjM0TjRwN05VaG1wQzh1S3pyZURlR3ViTkczNXorZ0ROcm81R3A0RlZXWStFSmM3RDhrT3ZERWZjSXRtdVZIVmNiR2F5cVdPTHY1VXpTa0w4SWlTS0QvbTBXNzg4SzVOeE5qODEvL0orWXhQM3MyTEwwMmNVMWZseENvTVJqbVZJSU9pakxTR2xJS2R4aEN5NHR4QWtCOWpPL1BEMmlnNk1XTmc1UytaeDZZTUxYL1FRVDExdGFWWjYxK2RVMXZ5ek9pcXdzR3NzQU1IVUtGSitlWDJoQVhNWkJWclJHSW4zRTdBK2duYm1OQjFXS1lUWHk5TXhsWm0yWVkxTGF2NzQ1QWdNQkFBR2pnZ0hGTUlJQndUQndCZ2dyQmdFRkJRY0JBUVJrTUdJd01nWUlLd1lCQlFVSE1BS0dKbWgwZEhBNkx5OXdkV0l1ZG01d2RDMWpZUzUyYmk5alpYSjBjeTkyYm5CMFkyRXVZMlZ5TUN3R0NDc0dBUVVGQnpBQmhpQm9kSFJ3T2k4dmIyTnpjQzUyYm5CMExXTmhMblp1TDNKbGMzQnZibVJsY2pBZEJnTlZIUTRFRmdRVWJINGh0RnNPYkxEMWswTm9DWGp5RlR2WkNRc3dEQVlEVlIwVEFRSC9CQUl3QURBZkJnTlZIU01FR0RBV2dCUUdhY0RWMVFLS0ZZMUdmZWw4NG1nS1ZheHFyekJvQmdOVkhTQUVZVEJmTUYwR0Rpc0dBUVFCZ2UwREFRRURBUUVDTUVzd0lnWUlLd1lCQlFVSEFnSXdGaDRVQUU4QVNRQkVBQzBBVUFCeUFDMEFNZ0F1QURBd0pRWUlLd1lCQlFVSEFnRVdHV2gwZEhBNkx5OXdkV0l1ZG01d2RDMWpZUzUyYmk5eWNHRXdNUVlEVlIwZkJDb3dLREFtb0NTZ0lvWWdhSFIwY0RvdkwyTnliQzUyYm5CMExXTmhMblp1TDNadWNIUmpZUzVqY213d0RnWURWUjBQQVFIL0JBUURBZ1R3TURRR0ExVWRKUVF0TUNzR0NDc0dBUVVGQndNQ0JnZ3JCZ0VGQlFjREJBWUtLd1lCQkFHQ053b0REQVlKS29aSWh2Y3ZBUUVGTUJ3R0ExVWRFUVFWTUJPQkVURmphSFZqZFhWQVoyMWhhV3d1WTI5dE1BMEdDU3FHU0liM0RRRUJCUVVBQTRJQ0FRQXlxaUpQdnZrQ01NRjNCQmlQWjNHbmVobnd2aHRtRFJKalpSUk5tYjUyMkx3OHlTcUlwbzU3Z1hJU25MZTNxV3lDMEdDV2FWcy9XSjZUSmczZkIxR2FIcDJ1Skc4aEdWMGc1TUpuZ2dBa1dJSEhia2hreVhsS2QveHh2RjdsQ1dzbGg3T0xvL0R3SnpQY0FRS2REbW9zRExrTHV0bGppcktnUDExOFhtVHBKZTljbmhUR1d4bVI0M1JYbzFwSzZNWkpTLzM1QTBhRWNVRndsa3lPSnBGdXorR3JqcTJxSzJZc1Rwdk5JbnczTFpEd1Jra1lqaG1KT0UrZnVrcWZwOFdGY0J0RUdxV0RhQmJtcWdadmNpdDlzK1JvaTNXZU9NeFVLcmkwZlNBS0RjdXhuZjRyeGlmUTBCYmFzOUhVbU5tNjVWRXNtK2Y1QjB2ZFNtNlM3WDhabzU0dVF0UmcvSGQxYUNQeTBBRmRmYUhpKzZhZHFyMlpxM3B0TUlTMTA3bENkcTI2S3lOM1JNeFl2aDVOWnNnODhKYzlJTmxHeEw1OFdEOUVCN016RmtXbW5HYjlKYzF1a0FFYVBaQTl2d1pXWlVBdHBXT0lkVHhObVV2c1pkbFZBOXZEYy9UbkpSYlVrMGFDd3ZxVldNYWpQUG96dDgvQ3Z6SHF3Y091Z2tGdWM3KzhNOVJGaStSdFpYQStrclROZ25US0pSSGQxNjNkV3N5M2ZweUZVOVBBY3lreVRLcUZmaWxGak5pRzg3WDBNWnJwNGxMUmNTdUZrZ0hZNnIvaUlrTHZGTTZESXJUSlN4RFAzYlhvOWpGbDlRT2FiU05mb1B4S200MXU1R3FwZUpYamxOTkFESStHTnRXUTZnM1oybXVZV3BTNTROVWRKTnJ5R1B5THhBPT08L1g1MDlDZXJ0aWZpY2F0ZT48L1g1MDlEYXRhPjwvS2V5SW5mbz48T2JqZWN0PjxTaWduYXR1cmVQcm9wZXJ0aWVzIHhtbG5zPSIiPjxTaWduYXR1cmVQcm9wZXJ0eSBJZD0iU2lnbmluZ1RpbWUiIFRhcmdldD0ic2lnbmF0dXJlUHJvcGVydGllcyI+PFNpZ25pbmdUaW1lPjIwMjItMDItMjRUMDM6MzM6NTdaPC9TaWduaW5nVGltZT48L1NpZ25hdHVyZVByb3BlcnR5PjwvU2lnbmF0dXJlUHJvcGVydGllcz48L09iamVjdD48L1NpZ25hdHVyZT48L0xpY2Vuc2U+"
//var key = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+PExpY2Vuc2U+PFBoYW5NZW0+Vk5QVC1DQSBQbHVnaW48L1BoYW5NZW0+PE5ndW9pQ2FwPlZOUFQgSVQ8L05ndW9pQ2FwPjxDQU1TPio8L0NBTVM+PE5nYXlCYXREYXU+MDUvMjUvMjAyMiAwMDowMDowMDwvTmdheUJhdERhdT48TmdheUtldFRodWM+MDcvMjUvMjAyMiAwMDowMDowMDwvTmdheUtldFRodWM+PFNpZ25hdHVyZSB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnIyI+PFNpZ25lZEluZm8+PENhbm9uaWNhbGl6YXRpb25NZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy9UUi8yMDAxL1JFQy14bWwtYzE0bi0yMDAxMDMxNSIgLz48U2lnbmF0dXJlTWV0aG9kIEFsZ29yaXRobT0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnI3JzYS1zaGExIiAvPjxSZWZlcmVuY2UgVVJJPSIiPjxUcmFuc2Zvcm1zPjxUcmFuc2Zvcm0gQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjZW52ZWxvcGVkLXNpZ25hdHVyZSIgLz48L1RyYW5zZm9ybXM+PERpZ2VzdE1ldGhvZCBBbGdvcml0aG09Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvMDkveG1sZHNpZyNzaGExIiAvPjxEaWdlc3RWYWx1ZT52THlIeXlraWhWUGlZbjZwZzVnV2VqMmtWVGs9PC9EaWdlc3RWYWx1ZT48L1JlZmVyZW5jZT48L1NpZ25lZEluZm8+PFNpZ25hdHVyZVZhbHVlPlVIT09uaHV2SVR0ZFVFUjlhVnhxRXdVSlM4Y0t2bzRYekRLZGQwUmlFL0tOd1puQXhTYk5XbHgwVmdia3kzMnpvWG52YmRKZ3dCeXBEb012RUlnVThjL2ZFYzZLcWZhdzFaMUNuM3BpUlFEdDYzS0lUdkZLUU1SNGE5VGdGeFJLdjFEdzJ2dzJ3NnlYbkUzVTZneklLYUJTbXpTQm14clFYWTlxd1R4cHR5M0luM3hmMC9iRWMzTXZhcVo5WkkrSmNTb1JYK2NvNUpMTGNMcTkwMy95djBLQnl0ZmNVOEVrK0RjRWkxb0NUSDEvMHhBTllFTVh3UU16UVJsckw2YlpwMzVEWkFMdkNnQW9BYmg4Q3RGaUV2WDF3R3RiblBSYTRabjlYazdrNUJ4YytvN3I0SUVrWUlaSkx4R0J0ZVJDV2JaZk9JaEhGdjQyWVltTU9FeVRXUT09PC9TaWduYXR1cmVWYWx1ZT48S2V5SW5mbz48S2V5VmFsdWU+PFJTQUtleVZhbHVlPjxNb2R1bHVzPnAyOGNVVG5vWEc5VW83NTRwandkWkdIOG1YNFZaUWtBeWtvcWhzQXB2Z1lQeVVRRWtCRTZkOXFzWkwzNkd2MWpGWjF2S2hvbkk2ZkxFeFZyYkhEbTkrRGVLZXpWSVpxUXZMaXM2M2czaHJtelJ0K2Mvb0F6YTZPUnFlQlZWbVBoQ1hPdy9KRHJ3eEgzQ0xacmxSMVhHeG1zcWxqaTcrVk0wcEMvQ0lraWcvNXRGdS9QQ3VUY1RZL05mL3lmbU1UOTdOaXk5Tm5GTlg1Y1FxREVZNWxTQ0Rvb3kwaHBTQ25jWVFzdUxjUUpBZll6dnp3OW9vT2pGallPVXZtY2VtREMxLzBFRTlkYldsV2V0Zm5WTmI4c3pvcXNMQnJMQURCMUNoU2ZubDlvUUZ6R1FWYTBSaUo5eE93UG9KMjVqUWRWaW1FMTh2VE1aV1p0bUdOUzJyKytPUT09PC9Nb2R1bHVzPjxFeHBvbmVudD5BUUFCPC9FeHBvbmVudD48L1JTQUtleVZhbHVlPjwvS2V5VmFsdWU+PFg1MDlEYXRhPjxYNTA5Q2VydGlmaWNhdGU+TUlJR1JUQ0NCQzJnQXdJQkFnSVFWQUVrajN5WFJFSTd3SzdLUi9jSHNqQU5CZ2txaGtpRzl3MEJBUVVGQURCcE1Rc3dDUVlEVlFRR0V3SldUakVUTUJFR0ExVUVDaE1LVms1UVZDQkhjbTkxY0RFZU1Cd0dBMVVFQ3hNVlZrNVFWQzFEUVNCVWNuVnpkQ0JPWlhSM2IzSnJNU1V3SXdZRFZRUURFeHhXVGxCVUlFTmxjblJwWm1sallYUnBiMjRnUVhWMGFHOXlhWFI1TUI0WERURTNNREl5TnpBNU16SXdNRm9YRFRFNU1ESXlOekl4TXpJd01Gb3dnWW94Q3pBSkJnTlZCQVlUQWxaT01SSXdFQVlEVlFRSURBbEl3NEFnVHVHN21Fa3hGVEFUQmdOVkJBY01ERVBodXFkMUlFZHA0YnFsZVRFc01Db0dBMVVFQXd3alZrNVFWQ0JUVDBaVVYwRlNSU0F0SUZaT1VGUWdRMEVnTFNCVVJWTlVJRk5KUjA0eElqQWdCZ29Ka2lhSmsvSXNaQUVCREJKTlUxUTZNVEF4TmpnMk9UY3pPQzB3TVRJd2dnRWlNQTBHQ1NxR1NJYjNEUUVCQVFVQUE0SUJEd0F3Z2dFS0FvSUJBUUNuYnh4Uk9laGNiMVNqdm5pbVBCMWtZZnlaZmhWbENRREtTaXFHd0NtK0JnL0pSQVNRRVRwMzJxeGt2Zm9hL1dNVm5XOHFHaWNqcDhzVEZXdHNjT2IzNE40cDdOVWhtcEM4dUt6cmVEZUd1Yk5HMzV6K2dETnJvNUdwNEZWV1krRUpjN0Q4a092REVmY0l0bXVWSFZjYkdheXFXT0x2NVV6U2tMOElpU0tEL20wVzc4OEs1TnhOajgxLy9KK1l4UDNzMkxMMDJjVTFmbHhDb01Sam1WSUlPaWpMU0dsSUtkeGhDeTR0eEFrQjlqTy9QRDJpZzZNV05nNVMrWng2WU1MWC9RUVQxMXRhVlo2MStkVTF2eXpPaXF3c0dzc0FNSFVLRkorZVgyaEFYTVpCVnJSR0luM0U3QStnbmJtTkIxV0tZVFh5OU14bFptMllZMUxhdjc0NUFnTUJBQUdqZ2dIRk1JSUJ3VEJ3QmdnckJnRUZCUWNCQVFSa01HSXdNZ1lJS3dZQkJRVUhNQUtHSm1oMGRIQTZMeTl3ZFdJdWRtNXdkQzFqWVM1MmJpOWpaWEowY3k5MmJuQjBZMkV1WTJWeU1Dd0dDQ3NHQVFVRkJ6QUJoaUJvZEhSd09pOHZiMk56Y0M1MmJuQjBMV05oTG5adUwzSmxjM0J2Ym1SbGNqQWRCZ05WSFE0RUZnUVViSDRodEZzT2JMRDFrME5vQ1hqeUZUdlpDUXN3REFZRFZSMFRBUUgvQkFJd0FEQWZCZ05WSFNNRUdEQVdnQlFHYWNEVjFRS0tGWTFHZmVsODRtZ0tWYXhxcnpCb0JnTlZIU0FFWVRCZk1GMEdEaXNHQVFRQmdlMERBUUVEQVFFQ01Fc3dJZ1lJS3dZQkJRVUhBZ0l3Rmg0VUFFOEFTUUJFQUMwQVVBQnlBQzBBTWdBdUFEQXdKUVlJS3dZQkJRVUhBZ0VXR1doMGRIQTZMeTl3ZFdJdWRtNXdkQzFqWVM1MmJpOXljR0V3TVFZRFZSMGZCQ293S0RBbW9DU2dJb1lnYUhSMGNEb3ZMMk55YkM1MmJuQjBMV05oTG5adUwzWnVjSFJqWVM1amNtd3dEZ1lEVlIwUEFRSC9CQVFEQWdUd01EUUdBMVVkSlFRdE1Dc0dDQ3NHQVFVRkJ3TUNCZ2dyQmdFRkJRY0RCQVlLS3dZQkJBR0NOd29EREFZSktvWklodmN2QVFFRk1Cd0dBMVVkRVFRVk1CT0JFVEZqYUhWamRYVkFaMjFoYVd3dVkyOXRNQTBHQ1NxR1NJYjNEUUVCQlFVQUE0SUNBUUF5cWlKUHZ2a0NNTUYzQkJpUFozR25laG53dmh0bURSSmpaUlJObWI1MjJMdzh5U3FJcG81N2dYSVNuTGUzcVd5QzBHQ1dhVnMvV0o2VEpnM2ZCMUdhSHAydUpHOGhHVjBnNU1KbmdnQWtXSUhIYmtoa3lYbEtkL3h4dkY3bENXc2xoN09Mby9Ed0p6UGNBUUtkRG1vc0RMa0x1dGxqaXJLZ1AxMThYbVRwSmU5Y25oVEdXeG1SNDNSWG8xcEs2TVpKUy8zNUEwYUVjVUZ3bGt5T0pwRnV6K0dyanEycUsyWXNUcHZOSW53M0xaRHdSa2tZamhtSk9FK2Z1a3FmcDhXRmNCdEVHcVdEYUJibXFnWnZjaXQ5cytSb2kzV2VPTXhVS3JpMGZTQUtEY3V4bmY0cnhpZlEwQmJhczlIVW1ObTY1VkVzbStmNUIwdmRTbTZTN1g4Wm81NHVRdFJnL0hkMWFDUHkwQUZkZmFIaSs2YWRxcjJacTNwdE1JUzEwN2xDZHEyNkt5TjNSTXhZdmg1TlpzZzg4SmM5SU5sR3hMNThXRDlFQjdNekZrV21uR2I5SmMxdWtBRWFQWkE5dndaV1pVQXRwV09JZFR4Tm1VdnNaZGxWQTl2RGMvVG5KUmJVazBhQ3d2cVZXTWFqUFBvenQ4L0N2ekhxd2NPdWdrRnVjNys4TTlSRmkrUnRaWEEra3JUTmduVEtKUkhkMTYzZFdzeTNmcHlGVTlQQWN5a3lUS3FGZmlsRmpOaUc4N1gwTVpycDRsTFJjU3VGa2dIWTZyL2lJa0x2Rk02RElyVEpTeERQM2JYbzlqRmw5UU9hYlNOZm9QeEttNDF1NUdxcGVKWGpsTk5BREkrR050V1E2ZzNaMm11WVdwUzU0TlVkSk5yeUdQeUx4QT09PC9YNTA5Q2VydGlmaWNhdGU+PC9YNTA5RGF0YT48L0tleUluZm8+PE9iamVjdD48U2lnbmF0dXJlUHJvcGVydGllcyB4bWxucz0iIj48U2lnbmF0dXJlUHJvcGVydHkgSWQ9IlNpZ25pbmdUaW1lIiBUYXJnZXQ9InNpZ25hdHVyZVByb3BlcnRpZXMiPjxTaWduaW5nVGltZT4yMDIyLTA1LTI1VDAzOjU1OjEzWjwvU2lnbmluZ1RpbWU+PC9TaWduYXR1cmVQcm9wZXJ0eT48L1NpZ25hdHVyZVByb3BlcnRpZXM+PC9PYmplY3Q+PC9TaWduYXR1cmU+PC9MaWNlbnNlPg==";
//thay key ngày 21/10/2022
var key = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+PExpY2Vuc2U+PFBoYW5NZW0+Vk5QVC1DQSBQbHVnaW48L1BoYW5NZW0+PE5ndW9pQ2FwPlZOUFQgSVQ8L05ndW9pQ2FwPjxDQU1TPio8L0NBTVM+PE5nYXlCYXREYXU+MDEvMTAvMjAyNCAwMDowMDowMDwvTmdheUJhdERhdT48TmdheUtldFRodWM+MDEvMTAvMjAzMCAyMzo1OTo1OTwvTmdheUtldFRodWM+PFNpZ25hdHVyZSB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnIyI+PFNpZ25lZEluZm8+PENhbm9uaWNhbGl6YXRpb25NZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzEwL3htbC1leGMtYzE0biMiPjxJbmNsdXNpdmVOYW1lc3BhY2VzIFByZWZpeExpc3Q9Imh0dHA6Ly93d3cudzMub3JnL1RSLzIwMDEvUkVDLXhtbC1jMTRuLTIwMDEwMzE1IiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMS8xMC94bWwtZXhjLWMxNG4jIiAvPjwvQ2Fub25pY2FsaXphdGlvbk1ldGhvZD48U2lnbmF0dXJlTWV0aG9kIEFsZ29yaXRobT0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnI3JzYS1zaGExIiAvPjxSZWZlcmVuY2UgVVJJPSIiPjxUcmFuc2Zvcm1zPjxUcmFuc2Zvcm0gQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjZW52ZWxvcGVkLXNpZ25hdHVyZSIgLz48L1RyYW5zZm9ybXM+PERpZ2VzdE1ldGhvZCBBbGdvcml0aG09Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvMDkveG1sZHNpZyNzaGExIiAvPjxEaWdlc3RWYWx1ZT5TQ1ZHbXR2OUNIVDJWdU5qRTRBRWtuNzNnTDQ9PC9EaWdlc3RWYWx1ZT48L1JlZmVyZW5jZT48L1NpZ25lZEluZm8+PFNpZ25hdHVyZVZhbHVlPlRYSUMxT3U1M1dYYjI1N2NzN1ZORy84RFJBRXdCYTgvSmVFRjRlbG54Wm1CRFhHZnBrY2dGbjZxMDRXMjJaaHpraE0rcFV3dEJaVGxBWnNucHlobDBBVmkvQk8rcjZWNlF4d25YbUdhcUpJY0NjalJkRmRIUjBtUXNCTGxWS3NRZ3pBdll5ZG1UU2JqeVJRNlJqMnQ3TkU4SmZUc0UzQzdHU01UVHE2OUdoVTZMUUkzMnBGU1hwT081cVlOeStMNkxlZ002bzd0RnRXazBBTUxFVzFKN0N1Z0Y4am1vWmpoSkJkd0xQN25HbVBiR0dkU1UxV1h2eUY3NGt3bHZRWlQxek9naWZyMk9sKzFTQ3VkUFd2S2dhT2hlMElaM2IrVitGQVpNeE5CeDJsNWtqUExxQ0JsZFUwMmIvYzZFWlZnMVZhbCtQYW9lVWREUnZidXZBMDd0QT09PC9TaWduYXR1cmVWYWx1ZT48S2V5SW5mbz48S2V5VmFsdWU+PFJTQUtleVZhbHVlPjxNb2R1bHVzPnAyOGNVVG5vWEc5VW83NTRwandkWkdIOG1YNFZaUWtBeWtvcWhzQXB2Z1lQeVVRRWtCRTZkOXFzWkwzNkd2MWpGWjF2S2hvbkk2ZkxFeFZyYkhEbTkrRGVLZXpWSVpxUXZMaXM2M2czaHJtelJ0K2Mvb0F6YTZPUnFlQlZWbVBoQ1hPdy9KRHJ3eEgzQ0xacmxSMVhHeG1zcWxqaTcrVk0wcEMvQ0lraWcvNXRGdS9QQ3VUY1RZL05mL3lmbU1UOTdOaXk5Tm5GTlg1Y1FxREVZNWxTQ0Rvb3kwaHBTQ25jWVFzdUxjUUpBZll6dnp3OW9vT2pGallPVXZtY2VtREMxLzBFRTlkYldsV2V0Zm5WTmI4c3pvcXNMQnJMQURCMUNoU2ZubDlvUUZ6R1FWYTBSaUo5eE93UG9KMjVqUWRWaW1FMTh2VE1aV1p0bUdOUzJyKytPUT09PC9Nb2R1bHVzPjxFeHBvbmVudD5BUUFCPC9FeHBvbmVudD48L1JTQUtleVZhbHVlPjwvS2V5VmFsdWU+PFg1MDlEYXRhPjxYNTA5U3ViamVjdE5hbWU+T0lELjAuOS4yMzQyLjE5MjAwMzAwLjEwMC4xLjE9TVNUOjEwMTY4Njk3MzgtMDEyLCBDTj1WTlBUIFNPRlRXQVJFIC0gVk5QVCBDQSAtIFRFU1QgU0lHTiwgTD1D4bqndSBHaeG6pXksIFM9SMOAIE7hu5hJLCBDPVZOPC9YNTA5U3ViamVjdE5hbWU+PFg1MDlDZXJ0aWZpY2F0ZT5NSUlHUlRDQ0JDMmdBd0lCQWdJUVZBRWtqM3lYUkVJN3dLN0tSL2NIc2pBTkJna3Foa2lHOXcwQkFRVUZBREJwTVFzd0NRWURWUVFHRXdKV1RqRVRNQkVHQTFVRUNoTUtWazVRVkNCSGNtOTFjREVlTUJ3R0ExVUVDeE1WVms1UVZDMURRU0JVY25WemRDQk9aWFIzYjNKck1TVXdJd1lEVlFRREV4eFdUbEJVSUVObGNuUnBabWxqWVhScGIyNGdRWFYwYUc5eWFYUjVNQjRYRFRFM01ESXlOekE1TXpJd01Gb1hEVEU1TURJeU56SXhNekl3TUZvd2dZb3hDekFKQmdOVkJBWVRBbFpPTVJJd0VBWURWUVFJREFsSXc0QWdUdUc3bUVreEZUQVRCZ05WQkFjTURFUGh1cWQxSUVkcDRicWxlVEVzTUNvR0ExVUVBd3dqVms1UVZDQlRUMFpVVjBGU1JTQXRJRlpPVUZRZ1EwRWdMU0JVUlZOVUlGTkpSMDR4SWpBZ0Jnb0praWFKay9Jc1pBRUJEQkpOVTFRNk1UQXhOamcyT1Rjek9DMHdNVEl3Z2dFaU1BMEdDU3FHU0liM0RRRUJBUVVBQTRJQkR3QXdnZ0VLQW9JQkFRQ25ieHhST2VoY2IxU2p2bmltUEIxa1lmeVpmaFZsQ1FES1NpcUd3Q20rQmcvSlJBU1FFVHAzMnF4a3Zmb2EvV01Wblc4cUdpY2pwOHNURld0c2NPYjM0TjRwN05VaG1wQzh1S3pyZURlR3ViTkczNXorZ0ROcm81R3A0RlZXWStFSmM3RDhrT3ZERWZjSXRtdVZIVmNiR2F5cVdPTHY1VXpTa0w4SWlTS0QvbTBXNzg4SzVOeE5qODEvL0orWXhQM3MyTEwwMmNVMWZseENvTVJqbVZJSU9pakxTR2xJS2R4aEN5NHR4QWtCOWpPL1BEMmlnNk1XTmc1UytaeDZZTUxYL1FRVDExdGFWWjYxK2RVMXZ5ek9pcXdzR3NzQU1IVUtGSitlWDJoQVhNWkJWclJHSW4zRTdBK2duYm1OQjFXS1lUWHk5TXhsWm0yWVkxTGF2NzQ1QWdNQkFBR2pnZ0hGTUlJQndUQndCZ2dyQmdFRkJRY0JBUVJrTUdJd01nWUlLd1lCQlFVSE1BS0dKbWgwZEhBNkx5OXdkV0l1ZG01d2RDMWpZUzUyYmk5alpYSjBjeTkyYm5CMFkyRXVZMlZ5TUN3R0NDc0dBUVVGQnpBQmhpQm9kSFJ3T2k4dmIyTnpjQzUyYm5CMExXTmhMblp1TDNKbGMzQnZibVJsY2pBZEJnTlZIUTRFRmdRVWJINGh0RnNPYkxEMWswTm9DWGp5RlR2WkNRc3dEQVlEVlIwVEFRSC9CQUl3QURBZkJnTlZIU01FR0RBV2dCUUdhY0RWMVFLS0ZZMUdmZWw4NG1nS1ZheHFyekJvQmdOVkhTQUVZVEJmTUYwR0Rpc0dBUVFCZ2UwREFRRURBUUVDTUVzd0lnWUlLd1lCQlFVSEFnSXdGaDRVQUU4QVNRQkVBQzBBVUFCeUFDMEFNZ0F1QURBd0pRWUlLd1lCQlFVSEFnRVdHV2gwZEhBNkx5OXdkV0l1ZG01d2RDMWpZUzUyYmk5eWNHRXdNUVlEVlIwZkJDb3dLREFtb0NTZ0lvWWdhSFIwY0RvdkwyTnliQzUyYm5CMExXTmhMblp1TDNadWNIUmpZUzVqY213d0RnWURWUjBQQVFIL0JBUURBZ1R3TURRR0ExVWRKUVF0TUNzR0NDc0dBUVVGQndNQ0JnZ3JCZ0VGQlFjREJBWUtLd1lCQkFHQ053b0REQVlKS29aSWh2Y3ZBUUVGTUJ3R0ExVWRFUVFWTUJPQkVURmphSFZqZFhWQVoyMWhhV3d1WTI5dE1BMEdDU3FHU0liM0RRRUJCUVVBQTRJQ0FRQXlxaUpQdnZrQ01NRjNCQmlQWjNHbmVobnd2aHRtRFJKalpSUk5tYjUyMkx3OHlTcUlwbzU3Z1hJU25MZTNxV3lDMEdDV2FWcy9XSjZUSmczZkIxR2FIcDJ1Skc4aEdWMGc1TUpuZ2dBa1dJSEhia2hreVhsS2QveHh2RjdsQ1dzbGg3T0xvL0R3SnpQY0FRS2REbW9zRExrTHV0bGppcktnUDExOFhtVHBKZTljbmhUR1d4bVI0M1JYbzFwSzZNWkpTLzM1QTBhRWNVRndsa3lPSnBGdXorR3JqcTJxSzJZc1Rwdk5JbnczTFpEd1Jra1lqaG1KT0UrZnVrcWZwOFdGY0J0RUdxV0RhQmJtcWdadmNpdDlzK1JvaTNXZU9NeFVLcmkwZlNBS0RjdXhuZjRyeGlmUTBCYmFzOUhVbU5tNjVWRXNtK2Y1QjB2ZFNtNlM3WDhabzU0dVF0UmcvSGQxYUNQeTBBRmRmYUhpKzZhZHFyMlpxM3B0TUlTMTA3bENkcTI2S3lOM1JNeFl2aDVOWnNnODhKYzlJTmxHeEw1OFdEOUVCN016RmtXbW5HYjlKYzF1a0FFYVBaQTl2d1pXWlVBdHBXT0lkVHhObVV2c1pkbFZBOXZEYy9UbkpSYlVrMGFDd3ZxVldNYWpQUG96dDgvQ3Z6SHF3Y091Z2tGdWM3KzhNOVJGaStSdFpYQStrclROZ25US0pSSGQxNjNkV3N5M2ZweUZVOVBBY3lreVRLcUZmaWxGak5pRzg3WDBNWnJwNGxMUmNTdUZrZ0hZNnIvaUlrTHZGTTZESXJUSlN4RFAzYlhvOWpGbDlRT2FiU05mb1B4S200MXU1R3FwZUpYamxOTkFESStHTnRXUTZnM1oybXVZV3BTNTROVWRKTnJ5R1B5THhBPT08L1g1MDlDZXJ0aWZpY2F0ZT48L1g1MDlEYXRhPjwvS2V5SW5mbz48T2JqZWN0PjxTaWduYXR1cmVQcm9wZXJ0aWVzIHhtbG5zPSIiPjxTaWduYXR1cmVQcm9wZXJ0eSBJZD0iU2lnbmluZ1RpbWUiIFRhcmdldD0ic2lnbmF0dXJlUHJvcGVydGllcyI+PFNpZ25pbmdUaW1lPjIwMjQtMDEtMTBUMDE6Mjc6MTNaPC9TaWduaW5nVGltZT48L1NpZ25hdHVyZVByb3BlcnR5PjwvU2lnbmF0dXJlUHJvcGVydGllcz48L09iamVjdD48L1NpZ25hdHVyZT48L0xpY2Vuc2U+";

export default{
  async LayThongTinSerialDynamicAPI($this, data)
  {
    //return;
    try {
      $this.loading(true)
      let kq = await api.call_dynamic_api($this.axios, {
        hdtb_id: data.hdtb_id,
        vdem: 50000,
        vtype: 'lay_dulieu_smca'
      });
      console.log(kq);
      if(kq.data.data.status == '00')
        return kq.data.data.message
      else
      {
        let loi = kq.data.data.message;
        throw new Error(loi);
      }
    }
    catch (e)
    {
      throw  e;
    }
    finally {
      $this.loading(false)
    }
  },
     async YeuCauDuLieuCA($this, data){

       // xu ly dich vu smartca v2
       let kt_thunghiem = await api.kiemtra_nangcap_phienban($this.axios, {ma_dv: 'smartca_v2', hdtb_id: data.hdtb_id})
       if (kt_thunghiem.data.data == 'ok_kh') {
         let kq = await this.LayThongTinSerialDynamicAPI($this, data);
         return kq;
       }


        let loaihd_id = data.loaihd_id;
        let kieuld_id = data.kieuld_id;
        let hdtb_id = data.hdtb_id;
        let thuebao_id = data.thuebao_id;

        let lst = this.GetData(await api.lay_dulieu_tao_thucthe_ca($this.axios, {params: {hdtb_id: hdtb_id}}))
        if(!lst || lst.length <= 0){
          throw 'Không tìm thấy dữ liệu thực thể CA!';
        }
        let ee_id = lst[0].ee_id ? lst[0].ee_id : 0;
        let req_id = lst[0].req_id ? lst[0].req_id : 0;
        let ten_tb_ca = lst[0].ten_tb;
        let status = lst[0].status;

        let yc_dulieu_token = YEUCAU_LAY_DULIEU_TOKEN;
        yc_dulieu_token.req_id = req_id;
        yc_dulieu_token = {
            root: Object.assign({}, yc_dulieu_token)
        }

        //if (ee_id != 0 && req_id != 0)
        {
            let yeucau_xml_chuaky =  this.OBJtoXML(yc_dulieu_token);
         //   let yeucau_xml = await this.KySoXMLSoft($this, yeucau_xml_chuaky);
            let yeucau_xml =  btoa(unescape(encodeURIComponent(yeucau_xml_chuaky)));
            let ca_choxl = {};
            ca_choxl.dulieu = yeucau_xml;
            ca_choxl.kieu_yc = CAConstanst.ReqTypeCapMoi;

            if (loaihd_id == 41 && (kieuld_id == 13177 || kieuld_id == 13248))
            {
                ca_choxl.kieu_yc = CAConstanst.ReqTypeGiaHan;
            }
            if (loaihd_id == 41 && kieuld_id == 13179)
            {
                ca_choxl.kieu_yc = CAConstanst.ReqTypeThayDoiThongTin;
            }

            if (loaihd_id == 41 && kieuld_id == 13261)
            {
                ca_choxl.kieu_yc = CAConstanst.ReqTypeThayDoiThongTin;
            }
            if (loaihd_id == 4)
            {
                ca_choxl.kieu_yc = CAConstanst.ReqTypeThuHoi;
            }
            ca_choxl.ma_tinh = lst[0].ma_tinh;
            ca_choxl.kieu_xl = 3; // yêu cầu lấy dữ liệu token
            ca_choxl.trangthai = 0;
            ca_choxl.hdtb_id = hdtb_id;
            ca_choxl.thuebao_id = thuebao_id;

            let res = this.GetData(await api.GuiYeuCau($this.axios, ca_choxl));
            if(res){
                let kq = await api.DongBoDuLieuCA($this.axios, { vhdtb_id: hdtb_id, vdulieu: res.DULIEU_CMS });
                if(kq.data.data != "ok")
                    throw (kq.data.data);
                else
                    return "yêu cầu dữ liệu thành công!";
            }
            else{
                throw ("Tạo yêu cầu thất bại: " + res);
            }
        }
      //  throw (`Dữ liệu tạo thực thể CA không hợp lệ ee_id: ${ee_id} req_id: ${req_id}`);
     },
     async Tao_Moi_CA($this, data){
        let loaihd_id = data.loaihd_id;
        let kieuld_id = data.kieuld_id;
        let hdtb_id = data.hdtb_id;
        let thuebao_id = data.thuebao_id;

        let lst = this.GetData(await api.lay_dulieu_tao_thucthe_ca($this.axios, {params: {hdtb_id: hdtb_id}}))
        if(!lst || lst.length <= 0){
          throw 'Không tìm thấy dữ liệu thực thể CA!';
        }

        let yeu_cau = {}
        yeu_cau.ca = lst[0].ca_id;
        yeu_cau.contact_address = lst[0].diachi_tb;
        yeu_cau.contact_email = lst[0].email;
        yeu_cau.contact_name = lst[0].ten_tb;
        yeu_cau.contact_phone = lst[0].so_dt;
        yeu_cau.cp_id = lst[0].cp_id;
        yeu_cau.group_id = lst[0].group_id;
        //  yeu_cau.hdtb_id = hdtb_id;
        yeu_cau.id = 0;
        // yeu_cau.ma_tinh = lst[0].ma_tinh;
        yeu_cau.parent = 0;
        yeu_cau.rc_id = lst[0].rc_id;
        yeu_cau.subject_an = lst[0].email;
        yeu_cau.subject_dn = lst[0].subject_dn;
        yeu_cau.old_req_id = lst[0].req_id;
        yeu_cau.username = lst[0].username_init;
        yeu_cau.ten_nhanvien = $this.$root.token.getMaNhanVien();
        yeu_cau.id_nhanvien = $this.$root.token.getUserName();

        //chứng thư
        let ct = {}

        ct.cert_id = -1;
        ct.contract_number = lst[0].ma_gd;
        ct.ee_id = -1;
        ct.old_serial = "";
        ct.sign_date = moment(lst[0].ngay_yc, "YYYY-MM-DD HH:mm:ss").format("YYYY-MM-DD"); //ngay_yc: "2021-05-18 11:03:40"
        ct.time_validity = lst[0].so_thang;
        ct.target_ids = lst[0].mucdich_ids ? lst[0].mucdich_ids.replaceAll(" ", "") : "";
        ct.type_request = CAConstanst.ReqTypeCapMoi;
        ct.validity_type = 0;
        ct.cp_id = lst[0].cp_id;
        ct.ca = lst[0].ca_id;
        //ct.chung_thu = Object.assign({}, ct); //root: chung_thu
        yeu_cau.chungthu = ct;
        yeu_cau = {
            root: Object.assign({}, yeu_cau) //root: root
        };
        console.log($this.$root.token.getThongTinNguoiDung());
        let yeucau_xml_chuaky = this.OBJtoXML(yeu_cau);
        yeucau_xml_chuaky = yeucau_xml_chuaky.replace('<root>', '<root xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">');
        //let yeucau_xml = await this.KySoXMLSoft($this, yeucau_xml_chuaky);
       let yeucau_xml =  btoa(unescape(encodeURIComponent(yeucau_xml_chuaky)));

        let ca_choxl = {}
        ca_choxl.dulieu = yeucau_xml;
        ca_choxl.kieu_yc = CAConstanst.ReqTypeCapMoi;
        ca_choxl.ma_tinh = lst[0].ma_tinh;
        ca_choxl.kieu_xl = 1;
        ca_choxl.trangthai = 0;
        ca_choxl.hdtb_id = hdtb_id;
        ca_choxl.thuebao_id = thuebao_id;

        let res = this.GetData(await api.GuiYeuCau($this.axios, ca_choxl));
        if(res){
           let kq = await api.DongBoRADatMoi($this.axios, { vhdtb_id: hdtb_id, vdulieu: res.DULIEU_CMS });
           if(kq.data.data != "ok")
             throw (kq.data.data);
           else
             return "Tạo yêu cầu cấp mới thành công!";
        }
        else{
            throw ("Tạo yêu cầu thất bại: " + res);
        }
     },
     async GiaHanCA($this, data){
        let loaihd_id = data.loaihd_id;
        let kieuld_id = data.kieuld_id;
        let hdtb_id = data.hdtb_id;
        let thuebao_id = data.thuebao_id;

        let lst = this.GetData(await api.lay_dulieu_tao_thucthe_ca($this.axios, {params: {hdtb_id: hdtb_id}}))
        if(!lst || lst.length <= 0){
          throw 'Không tìm thấy dữ liệu thực thể CA!';
        }

        let yeu_cau = {}
        yeu_cau.ca = lst[0].ca_id;
        yeu_cau.contact_address = lst[0].diachi_tb;
        yeu_cau.contact_email = lst[0].email;
        yeu_cau.contact_name = lst[0].ten_tb;
        yeu_cau.contact_phone = lst[0].so_dt;
        yeu_cau.cp_id = lst[0].cp_id;
        yeu_cau.group_id = lst[0].group_id;
        //  yeu_cau.hdtb_id = hdtb_id;
        yeu_cau.id = lst[0].ee_id;
        // yeu_cau.ma_tinh = lst[0].ma_tinh;
        yeu_cau.parent = 0;
        yeu_cau.rc_id = lst[0].rc_id;
        yeu_cau.subject_an = lst[0].email;
        yeu_cau.subject_dn = lst[0].subject_dn;
        yeu_cau.old_req_id = lst[0].req_id;
        yeu_cau.ten_nhanvien = $this.$root.token.getMaNhanVien();
        yeu_cau.id_nhanvien = $this.$root.token.getUserName();
      //  yeu_cau.username = lst[0].username_init;

        //chứng thư
        let ct = {}
        ct.ca = lst[0].ca_id;
        ct.cert_id = lst[0].cts_id;
        ct.contract_number = lst[0].ma_gd;
        ct.cp_id = lst[0].cp_id;
        ct.ee_id = -lst[0].ee_id;
        ct.old_serial = "";
        ct.sign_date = moment(lst[0].ngay_yc, "YYYY-MM-DD HH:mm:ss").format("YYYY-MM-DD"); //ngay_yc: "2021-05-18 11:03:40"
        ct.target_ids = lst[0].mucdich_ids ? lst[0].mucdich_ids.replaceAll(" ", "") : "";
        ct.time_validity = lst[0].so_thang;
        ct.type_request = CAConstanst.ReqTypeGiaHan;
        ct.validity_type = 0;
        //ct.chung_thu = Object.assign({}, ct); //root: chung_thu
        yeu_cau.chungthu = ct;
        yeu_cau = {
            root: Object.assign({}, yeu_cau) //root: root
        };

        let yeucau_xml_chuaky = this.OBJtoXML(yeu_cau);
       // let yeucau_xml = await this.KySoXML($this, yeucau_xml_chuaky);
        let yeucau_xml =  btoa(unescape(encodeURIComponent(yeucau_xml_chuaky)));
        let ca_choxl = {}
        ca_choxl.dulieu = yeucau_xml;
        ca_choxl.kieu_yc = CAConstanst.ReqTypeGiaHan;
        ca_choxl.ma_tinh = lst[0].ma_tinh;
        ca_choxl.kieu_xl = 1;
        ca_choxl.trangthai = 0;
        ca_choxl.hdtb_id = hdtb_id;
        ca_choxl.thuebao_id = thuebao_id;

       let res = this.GetData(await api.GuiYeuCau($this.axios, ca_choxl));
       if(res){
         let kq = await api.DongBoRAGiaHan($this.axios, { vhdtb_id: hdtb_id, vdulieu: res.DULIEU_CMS });
         if(kq.data.data != "ok")
           throw (kq.data.data);
         else
           return "Tạo yêu gia hạn thành công!";
       }
       else{
         throw ("Tạo yêu cầu thất bại: " + res);
       }
     },
     async THAY_DOI_UID_CA($this, data){
        let loaihd_id = data.loaihd_id;
        let kieuld_id = data.kieuld_id;
        let hdtb_id = data.hdtb_id;
        let thuebao_id = data.thuebao_id;

        let lst = this.GetData(await api.lay_dulieu_tao_thucthe_ca($this.axios, {params: {hdtb_id: hdtb_id}}))
        if(!lst || lst.length <= 0){
          throw 'Không tìm thấy dữ liệu thực thể CA!';
        }

        let yeu_cau = {} //CMS_YEUCAUCAPMOI
        yeu_cau.ca = lst[0].ca_id;
        yeu_cau.contact_address = lst[0].diachi_tb;
        yeu_cau.contact_email = lst[0].email;
        yeu_cau.contact_name = lst[0].ten_tb;
        yeu_cau.contact_phone = lst[0].so_dt;
        yeu_cau.cp_id = lst[0].cp_id;
        yeu_cau.group_id = lst[0].group_id;
        //  yeu_cau.hdtb_id = hdtb_id;
        yeu_cau.id = lst[0].ee_id;
        // yeu_cau.ma_tinh = lst[0].ma_tinh;
        yeu_cau.parent = 0;
        yeu_cau.rc_id = lst[0].rc_id;
        yeu_cau.subject_an = lst[0].email;
        yeu_cau.subject_dn = lst[0].subject_dn;
        yeu_cau.old_req_id = lst[0].req_id;
        yeu_cau.username = lst[0].username_init;
        yeu_cau.ten_nhanvien = $this.$root.token.getMaNhanVien();
        yeu_cau.id_nhanvien = $this.$root.token.getUserName();
        //chứng thư
        let ct = {} //CHUNG_THU
        ct.ca = lst[0].ca_id;
        ct.cert_id = 0;
        ct.contract_number = lst[0].ma_gd;
        ct.cp_id = lst[0].cp_id;
        ct.ee_id = lst[0].ee_id;
        ct.old_serial = lst[0].serial;
        ct.sign_date = moment(lst[0].ngay_yc, "YYYY-MM-DD HH:mm:ss").format("YYYY-MM-DD"); //ngay_yc: "2021-05-18 11:03:40"
        ct.target_ids = lst[0].mucdich_ids ? lst[0].mucdich_ids.replaceAll(" ", "") : "";
        ct.time_validity = lst[0].so_thang;
        ct.type_request = CAConstanst.ReqTypeThayDoiThongTin;
        ct.validity_type = 0;
        //ct.chung_thu = Object.assign({}, ct); //root: chung_thu
        yeu_cau.chungthu = ct;
        yeu_cau = {
            root: Object.assign({}, yeu_cau) //root: root
        };

        let yeucau_xml_chuaky = this.OBJtoXML(yeu_cau);
       // let yeucau_xml =await this.KySoXML($this, yeucau_xml_chuaky);
        let yeucau_xml =  btoa(unescape(encodeURIComponent(yeucau_xml_chuaky)));
        let ca_choxl = {}
        ca_choxl.dulieu = yeucau_xml;
        ca_choxl.kieu_yc = CAConstanst.ReqTypeThayDoiThongTin;
        ca_choxl.ma_tinh = lst[0].ma_tinh;
        ca_choxl.kieu_xl = 1;
        ca_choxl.trangthai = 0;
        ca_choxl.hdtb_id = hdtb_id;
        ca_choxl.thuebao_id = thuebao_id;

       let res = this.GetData(await api.GuiYeuCau($this.axios, ca_choxl));
       if(res){
         let kq = await api.DongBoRADatMoi($this.axios, { vhdtb_id: hdtb_id, vdulieu: res.DULIEU_CMS });
         if(kq.data.data != "ok")
           throw (kq.data.data);
         else
           return "Tạo yêu thay đổi thông tin giấy tờ mới thành công!";
       }
       else{
         throw ("Tạo yêu cầu thất bại: " + res);
       }
     },
     async ThayDoiThongTinCA($this, data){
        let loaihd_id = data.loaihd_id;
        let kieuld_id = data.kieuld_id;
        let hdtb_id = data.hdtb_id;
        let thuebao_id = data.thuebao_id;

        let lst = this.GetData(await api.lay_dulieu_tao_thucthe_ca($this.axios, {params: {hdtb_id: hdtb_id}}))
        if(!lst || lst.length <= 0){
          throw 'Không tìm thấy dữ liệu thực thể CA!';
        }

        let yeu_cau = {} //CMS_YEUCAUCAPMOI
        yeu_cau.ca = lst[0].ca_id;
        yeu_cau.contact_address = lst[0].diachi_tb;
        yeu_cau.contact_email = lst[0].email;
        yeu_cau.contact_name = lst[0].ten_tb;
        yeu_cau.contact_phone = lst[0].so_dt;
        yeu_cau.cp_id = lst[0].cp_id;
        yeu_cau.group_id = lst[0].group_id;
        //  yeu_cau.hdtb_id = hdtb_id;
        yeu_cau.id = lst[0].ee_id;
        // yeu_cau.ma_tinh = lst[0].ma_tinh;
        yeu_cau.parent = 0;
        yeu_cau.rc_id = lst[0].rc_id;
        yeu_cau.subject_an = lst[0].email;
        yeu_cau.subject_dn = lst[0].subject_dn;
        yeu_cau.old_req_id = lst[0].req_id;
        yeu_cau.username = lst[0].username_init;
        yeu_cau.ten_nhanvien = $this.$root.token.getMaNhanVien();
        yeu_cau.id_nhanvien = $this.$root.token.getUserName();
        //chứng thư
        let ct = {} //CHUNG_THU
        ct.ca = lst[0].ca_id;
        ct.cert_id = lst[0].cts_id;
        ct.contract_number = lst[0].ma_gd;
        ct.cp_id = lst[0].cp_id;
        ct.ee_id = lst[0].ee_id;
        ct.old_serial = lst[0].serial;
        ct.sign_date = moment(lst[0].ngay_yc, "YYYY-MM-DD HH:mm:ss").format("YYYY-MM-DD"); //ngay_yc: "2021-05-18 11:03:40"
        ct.target_ids = lst[0].mucdich_ids ? lst[0].mucdich_ids.replaceAll(" ", "") : "";
        ct.time_validity = lst[0].so_thang;
        ct.type_request = CAConstanst.ReqTypeThayDoiThongTin;
        ct.validity_type = 1;
        //ct.chung_thu = Object.assign({}, ct); //root: chung_thu
        yeu_cau.chungthu = ct;
        yeu_cau = {
            root: Object.assign({}, yeu_cau) //root: root
        };

        let yeucau_xml_chuaky = this.OBJtoXML(yeu_cau);
        //let yeucau_xml =await this.KySoXML($this, yeucau_xml_chuaky);
        let yeucau_xml =  btoa(unescape(encodeURIComponent(yeucau_xml_chuaky)));
        let ca_choxl = {}
        ca_choxl.dulieu = yeucau_xml;
        ca_choxl.kieu_yc = CAConstanst.ReqTypeThayDoiThongTin;
        ca_choxl.ma_tinh = lst[0].ma_tinh;
        ca_choxl.kieu_xl = 1;
        ca_choxl.trangthai = 0;
        ca_choxl.hdtb_id = hdtb_id;
        ca_choxl.thuebao_id = thuebao_id;

       let res = this.GetData(await api.GuiYeuCau($this.axios, ca_choxl));
       if(res){
         let kq = await api.DongBoRAGiaHan($this.axios, { vhdtb_id: hdtb_id, vdulieu: res.DULIEU_CMS });
         if(kq.data.data != "ok")
           throw (kq.data.data);
         else
           return "Tạo yêu cầu thay đổi thông tin thành công!";
       }
       else{
         throw ("Tạo yêu cầu thất bại: " + res);
       }
     },
     async TamDungCA($this, data){
        let loaihd_id = data.loaihd_id;
        let kieuld_id = data.kieuld_id;
        let hdtb_id = data.hdtb_id;
        let thuebao_id = data.thuebao_id;

        let lst = this.GetData(await api.lay_dulieu_tao_thucthe_ca($this.axios, {params: {hdtb_id: hdtb_id}}))
        if(!lst || lst.length <= 0){
          throw 'Không tìm thấy dữ liệu thực thể CA!';
        }

        let lydohuy = this.GetData(await api.ca_lay_ly_dohuy_hdtb($this.axios, {vhdtb_id: hdtb_id}))

        let yeu_cau = {} //CMS_YEUCAUCAPMOI
        yeu_cau.cert_id = lst[0].cts_id;
        yeu_cau.reason = lydohuy;
        yeu_cau.type_request = CAConstanst.ReqTypeTamNgung;
        yeu_cau.un_ban_date = lst[0].ngay_hh_td;

        yeu_cau = {
            root: Object.assign({}, yeu_cau) //root: root
        };

        let yeucau_xml_chuaky = this.OBJtoXML(yeu_cau);
       // let yeucau_xml =await this.KySoXML($this, yeucau_xml_chuaky);
        let yeucau_xml =  btoa(unescape(encodeURIComponent(yeucau_xml_chuaky)));
        let ca_choxl = {}
        ca_choxl.dulieu = yeucau_xml;
        ca_choxl.kieu_yc = CAConstanst.ReqTypeTamNgung;
        ca_choxl.ma_tinh = lst[0].ma_tinh;
        ca_choxl.kieu_xl = 1;
        ca_choxl.trangthai = 0;
        ca_choxl.hdtb_id = hdtb_id;
        ca_choxl.thuebao_id = thuebao_id;

       let res = this.GetData(await api.GuiYeuCau($this.axios, ca_choxl));
       if(res){
         let kq = await api.DongBoRAGiaHan($this.axios, { vhdtb_id: hdtb_id, vdulieu: res.DULIEU_CMS });
         if(kq.data.data != "ok")
           throw (kq.data.data);
         else
           return "Tạo yêu cầu tạm dừng thành công!";
       }
       else{
         throw ("Tạo yêu cầu thất bại: " + res);
       }
     },
     async KhoiPhucCA($this, data){
        let loaihd_id = data.loaihd_id;
        let kieuld_id = data.kieuld_id;
        let hdtb_id = data.hdtb_id;
        let thuebao_id = data.thuebao_id;

        let lst = this.GetData(await api.lay_dulieu_tao_thucthe_ca($this.axios, {params: {hdtb_id: hdtb_id}}))
        if(!lst || lst.length <= 0){
          throw 'Không tìm thấy dữ liệu thực thể CA!';
        }

        let lydohuy = this.GetData(await api.ca_lay_ly_dohuy_hdtb($this.axios, {vhdtb_id: hdtb_id}))

        let yeu_cau = {} //CMS_YEUCAUCAPMOI
        yeu_cau.cert_id = lst[0].cts_id;
        yeu_cau.reason = lydohuy;
        yeu_cau.type_request = CAConstanst.ReqTypeKhoiPhuc;
        //yeu_cau.un_ban_date = lst[0].ngay_hh_td;

        yeu_cau = {
            root: Object.assign({}, yeu_cau) //root: root
        };

        let yeucau_xml_chuaky = this.OBJtoXML(yeu_cau);
       // let yeucau_xml =await this.KySoXML($this, yeucau_xml_chuaky);
        let yeucau_xml =  btoa(unescape(encodeURIComponent(yeucau_xml_chuaky)));
        let ca_choxl = {}
        ca_choxl.dulieu = yeucau_xml;
        ca_choxl.kieu_yc = CAConstanst.ReqTypeKhoiPhuc;
        ca_choxl.ma_tinh = lst[0].ma_tinh;
        ca_choxl.kieu_xl = 1;
        ca_choxl.trangthai = 0;
        ca_choxl.hdtb_id = hdtb_id;
        ca_choxl.thuebao_id = thuebao_id;

       let res = this.GetData(await api.GuiYeuCau($this.axios, ca_choxl));
       if(res){
         let kq = await api.DongBoRAGiaHan($this.axios, { vhdtb_id: hdtb_id, vdulieu: res.DULIEU_CMS });
         if(kq.data.data != "ok")
           throw (kq.data.data);
         else
           return "Tạo yêu cầu khôi phục mới thành công!";
       }
       else{
         throw ("Tạo yêu cầu thất bại: " + res);
       }
     },
     async ThuHoiCA($this, data){
        let loaihd_id = data.loaihd_id;
        let kieuld_id = data.kieuld_id;
        let hdtb_id = data.hdtb_id;
        let thuebao_id = data.thuebao_id;

        let lst = this.GetData(await api.lay_dulieu_tao_thucthe_ca($this.axios, {params: {hdtb_id: hdtb_id}}))
        if(!lst || lst.length <= 0){
          throw 'Không tìm thấy dữ liệu thực thể CA!';
        }

        let lydohuy = this.GetData(await api.ca_lay_ly_dohuy_hdtb($this.axios, {vhdtb_id: hdtb_id}))

        let yeu_cau = {} //CMS_YEUCAUCAPMOI
        yeu_cau.cert_id = lst[0].cts_id;
        yeu_cau.reason = lydohuy;
        yeu_cau.type_request = CAConstanst.ReqTypeThuHoi;

        yeu_cau = {
            root: Object.assign({}, yeu_cau) //root: root
        };

        let yeucau_xml_chuaky = this.OBJtoXML(yeu_cau);
      //  let yeucau_xml =await this.KySoXML($this, yeucau_xml_chuaky);
        let yeucau_xml =  btoa(unescape(encodeURIComponent(yeucau_xml_chuaky)));
        let ca_choxl = {}
        ca_choxl.dulieu = yeucau_xml;
        ca_choxl.kieu_yc = CAConstanst.ReqTypeThuHoi;
        ca_choxl.ma_tinh = lst[0].ma_tinh;
        ca_choxl.kieu_xl = 1;
        ca_choxl.trangthai = 0;
        ca_choxl.hdtb_id = hdtb_id;
        ca_choxl.thuebao_id = thuebao_id;

       let res = this.GetData(await api.GuiYeuCau($this.axios, ca_choxl));
       if(res){
         let kq = await api.DongBoRAGiaHan($this.axios, { vhdtb_id: hdtb_id, vdulieu: res.DULIEU_CMS });
         if(kq.data.data != "ok")
           throw (kq.data.data);
         else
           return "Tạo yêu cầu thu hồi thành công!";
       }
       else{
         throw ("Tạo yêu cầu thất bại: " + res);
       }
     },
     async KySoXMLSoft($this, input){
        if(!vnpt_cams_plugin){
            throw 'Thư viện VNPT plugin chưa sẵn sàng Bạn vui lòng chờ!';
        }

        if(!base64){
            throw 'Thư viện VNPT plugin chưa sẵn sàng. Bạn vui lòng chờ!';
        }

        let dt_chungchi_ssl = this.GetData(await api.sp_lay_ds_chungchi_ssl($this.axios, {params: {chungchi_id: 1}}));
        if(!dt_chungchi_ssl || dt_chungchi_ssl.length <= 0){
            throw 'Không lấy được chứng chỉ SSL!';
        }

        let chung_chi_ssl_b64 = dt_chungchi_ssl[0]["command"]
        let ketqua = "";
        //TODO: ký số
        let data = {};
        try{
            console.log("Đang checkPlugin vnpt_cams_plugin");
            data = await vnpt_cams_plugin.checkPlugin();
            console.log("vnpt_cams_plugin checkPlugin", data);
            if(data !== "1"){
                throw "VNPT CA Plugin chưa sẵn sàng: " + data;
            }
            console.log("VNPT-Cams Plugin đã sẵn sàng");
        }catch(e){
            console.error(e);
            throw "VNPT-Cams Plugin chưa được cài đặt hoặc chưa được bật trên máy của bạn!";
        }



        await vnpt_cams_plugin.setLicenseKey(key).then(function (resData) {
            //console.log("setLicenseKey", resData);
            data = JSON.parse(resData);
        }).catch(function (e) {
            console.error(e)
        });

        console.log("setLicenseKey", data);
        if(data.code !=  1){
            throw "VNPT CA Plugin setLicenseKey lỗi: " + data.desc;
        }

        console.log("vnpt_cams_plugin.signXML");
        var dataInput = base64.encode(input);
        //var certBase64 = "MIIGRTCCBC2gAwIBAgIQVAEkj3yXREI7wK7KR/cHsjANBgkqhkiG9w0BAQUFADBpMQswCQYDVQQGEwJWTjETMBEGA1UEChMKVk5QVCBHcm91cDEeMBwGA1UECxMVVk5QVC1DQSBUcnVzdCBOZXR3b3JrMSUwIwYDVQQDExxWTlBUIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MB4XDTE3MDIyNzA5MzIwMFoXDTE5MDIyNzIxMzIwMFowgYoxCzAJBgNVBAYTAlZOMRIwEAYDVQQIDAlIw4AgTuG7mEkxFTATBgNVBAcMDEPhuqd1IEdp4bqleTEsMCoGA1UEAwwjVk5QVCBTT0ZUV0FSRSAtIFZOUFQgQ0EgLSBURVNUIFNJR04xIjAgBgoJkiaJk/IsZAEBDBJNU1Q6MTAxNjg2OTczOC0wMTIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCnbxxROehcb1SjvnimPB1kYfyZfhVlCQDKSiqGwCm+Bg/JRASQETp32qxkvfoa/WMVnW8qGicjp8sTFWtscOb34N4p7NUhmpC8uKzreDeGubNG35z+gDNro5Gp4FVWY+EJc7D8kOvDEfcItmuVHVcbGayqWOLv5UzSkL8IiSKD/m0W788K5NxNj81//J+YxP3s2LL02cU1flxCoMRjmVIIOijLSGlIKdxhCy4txAkB9jO/PD2ig6MWNg5S+Zx6YMLX/QQT11taVZ61+dU1vyzOiqwsGssAMHUKFJ+eX2hAXMZBVrRGIn3E7A+gnbmNB1WKYTXy9MxlZm2YY1Lav745AgMBAAGjggHFMIIBwTBwBggrBgEFBQcBAQRkMGIwMgYIKwYBBQUHMAKGJmh0dHA6Ly9wdWIudm5wdC1jYS52bi9jZXJ0cy92bnB0Y2EuY2VyMCwGCCsGAQUFBzABhiBodHRwOi8vb2NzcC52bnB0LWNhLnZuL3Jlc3BvbmRlcjAdBgNVHQ4EFgQUbH4htFsObLD1k0NoCXjyFTvZCQswDAYDVR0TAQH/BAIwADAfBgNVHSMEGDAWgBQGacDV1QKKFY1Gfel84mgKVaxqrzBoBgNVHSAEYTBfMF0GDisGAQQBge0DAQEDAQECMEswIgYIKwYBBQUHAgIwFh4UAE8ASQBEAC0AUAByAC0AMgAuADAwJQYIKwYBBQUHAgEWGWh0dHA6Ly9wdWIudm5wdC1jYS52bi9ycGEwMQYDVR0fBCowKDAmoCSgIoYgaHR0cDovL2NybC52bnB0LWNhLnZuL3ZucHRjYS5jcmwwDgYDVR0PAQH/BAQDAgTwMDQGA1UdJQQtMCsGCCsGAQUFBwMCBggrBgEFBQcDBAYKKwYBBAGCNwoDDAYJKoZIhvcvAQEFMBwGA1UdEQQVMBOBETFjaHVjdXVAZ21haWwuY29tMA0GCSqGSIb3DQEBBQUAA4ICAQAyqiJPvvkCMMF3BBiPZ3GnehnwvhtmDRJjZRRNmb522Lw8ySqIpo57gXISnLe3qWyC0GCWaVs/WJ6TJg3fB1GaHp2uJG8hGV0g5MJnggAkWIHHbkhkyXlKd/xxvF7lCWslh7OLo/DwJzPcAQKdDmosDLkLutljirKgP118XmTpJe9cnhTGWxmR43RXo1pK6MZJS/35A0aEcUFwlkyOJpFuz+Grjq2qK2YsTpvNInw3LZDwRkkYjhmJOE+fukqfp8WFcBtEGqWDaBbmqgZvcit9s+Roi3WeOMxUKri0fSAKDcuxnf4rxifQ0Bbas9HUmNm65VEsm+f5B0vdSm6S7X8Zo54uQtRg/Hd1aCPy0AFdfaHi+6adqr2Zq3ptMIS107lCdq26KyN3RMxYvh5NZsg88Jc9INlGxL58WD9EB7MzFkWmnGb9Jc1ukAEaPZA9vwZWZUAtpWOIdTxNmUvsZdlVA9vDc/TnJRbUk0aCwvqVWMajPPozt8/CvzHqwcOugkFuc7+8M9RFi+RtZXA+krTNgnTKJRHd163dWsy3fpyFU9PAcykyTKqFfilFjNiG87X0MZrp4lLRcSuFkgHY6r/iIkLvFM6DIrTJSxDP3bXo9jFl9QOabSNfoPxKm41u5GqpeJXjlNNADI+GNtWQ6g3Z2muYWpS54NUdJNryGPyLxA==";
        var certBase64 = chung_chi_ssl_b64;

        await vnpt_cams_plugin.signCms(dataInput, certBase64).then(function (resData) {
            //console.log("vnpt_cams_plugin.signCms", resData);
            let jsonData = JSON.parse(resData);
            data = jsonData;
            // base64 of signature
        }).catch(function (e) {
            console.error(e);
            throw e;
        });
        if(data.code == 1)
            ketqua = data.data;
        else if(data.code == 5)
            throw "Bạn đã hủy bỏ thao tác: " + data.desc;
        else
            throw data.desc;
        //console.log("ketqua", ketqua)
        return ketqua;
     },
     async encodeBase64($this, input){
        var dataInput = base64.encode(input);
        return dataInput
     },
     async KySoXML($this, input){
        if(!input || input.length == 0){
            throw 'Input không hợp lệ!';
        }

        if(!vnpt_cams_plugin){
            throw 'Thư viện VNPT plugin chưa sẵn sàng Bạn vui lòng chờ!';
        }

        if(!base64){
            throw 'Thư viện VNPT plugin chưa sẵn sàng. Bạn vui lòng chờ!';
        }

        let pkcs11DriverList = this.GetData(await api.ca_danhsach_driver($this.axios, {}));
        if(!pkcs11DriverList || pkcs11DriverList.length <= 0){
            throw 'Không lấy được Danh sách DRIVER!';
        }
        //console.log("pkcs11DriverList", pkcs11DriverList);
        let data = {}
        let tokenList = [];
        let dll_list = pkcs11DriverList;
        try{
            console.log("Đang checkPlugin vnpt_cams_plugin");
            data = await vnpt_cams_plugin.checkPlugin();
            console.log("vnpt_cams_plugin checkPlugin", data);
            if(data !== "1"){
                throw "VNPT-Cams Plugin chưa được cài đặt hoặc chưa được bật trên máy của bạn!";
            }
            console.log("VNPT-Cams Plugin đã sẵn sàng");
        }catch(e){
            console.error(e);
            throw "VNPT-Cams Plugin chưa được cài đặt hoặc chưa được bật trên máy của bạn!";
        }

        await vnpt_cams_plugin.setLicenseKey(key).then(function (resData) {
            //console.log("setLicenseKey", resData);
            data = JSON.parse(resData);
        }).catch(function (e) {
            console.error(e)
        });

        console.log("setLicenseKey", data);
        if(data.code !=  1){
            throw "VNPT-Cams Plugin setLicenseKey lỗi: " + data.desc;
        }

        //console.log("KySoXML dll_list", dll_list);
        //#region getTokenInfo
        console.log("Đang getTokenInfo...");
        await vnpt_cams_plugin.getTokenInfo(dll_list).then(function (resData) {
            resData = JSON.parse(resData);
            //console.log("vnpt_cams_plugin.getTokenInfo", resData);
            if(resData && resData.code == 1 && resData.data && resData.data.length > 0)
            {
                resData.data = JSON.parse(resData.data);
                tokenList = resData.data;
                return;
            }
            else{
                throw "Không lấy được thông tin Token, bạn vui lòng kiểm tra lại USB Token. " + resData.desc;
            }
        }).catch(function (e) {
            console.error("vnpt_cams_plugin.getTokenInfo", e);
            tokenList = [];
            throw e;
        });
        //#endregion

        if (!tokenList || tokenList.length == 0)
        {
            throw "Bạn không cắm token hoặc không có driver token phù hợp!";
        }

        //#region GetCertInfo
        let certList = [];
        //console.log("tokenList", tokenList);
        var dllName = tokenList[0].Driver;
        var tokenSerial = tokenList[0].SerialNumber;
        console.log(`Đang getCertInfo.. ${dllName} ${tokenSerial}`);
        await vnpt_cams_plugin.getCertInfo(dllName, tokenSerial).then(function (resData) {
            resData = JSON.parse(resData);
            //console.log("vnpt_cams_plugin.getCertInfo", resData);
            if(resData.code == 1 && resData.data){
                certList = JSON.parse(resData.data);
            }
            else{
                throw `Không lấy được CertInfo: code = ${resData.code}`;
            }
        }).catch(function (e) {
            console.error("vnpt_cams_plugin.getCertInfo", e);
            throw e;
        });
        //#endregion

        //console.log('certList', certList);
        if(!certList || certList.length <= 0){
            throw `Không lấy được chứng thư từ USB Token!`;
        }
        let certListModal = certList.map((x) => { return { serialnumber: x.serialNumber , subjectcn: `${x.subjectCN} - Serial: ${x.serialNumber} - Ngày hết hạn: ${x.notAfter}` } })
        //show popup Chọn chứng thư
        let dataModal = {};
        dataModal.type = "dataGrid"
        dataModal.title = "Chọn chứng thư"
        dataModal.list = certListModal;
        dataModal.headers = [
            {fieldName: 'subjectcn', headerText: 'Chủ thể', allowFiltering: false},
        ]

        //show modal chọn chứng thư
        $this.loading(false);
        let res =  await $this.$refs.modalPromise.show(dataModal);
        //console.log("modalPromise", res);
        if(!res || res == false)
        {
            throw 'Bạn đã hủy bỏ thao tác!'
        }

        let cert = certList.filter(x=>x.serialNumber === res[0].serialnumber);
        if(!cert || cert.length == 0){
            throw 'Chứng thư bạn chọn không còn tồn tại!'
        }
        cert = cert[0];
        var tkSerialNumber = tokenSerial;
        var pinType = 1; // 0: SO PIN, 1: USER PIN
        var userPIN = tokenList[0].Label;
        var soPIN =  "";//"12345678";

        while(true){
            //show modal nhập mã PIN token;
            res = null
            dataModal.type = "form"
            dataModal.title = "Nhập mã PIN token"
            dataModal.list = [];
            dataModal.list.push({
                id: 1,
                name: 'name',
                type: 'text',
                text: 'User Token',
                required: false,
                readonly: true,
                defaultValue: userPIN
            });
            dataModal.list.push({
                id: 2,
                name: 'pin',
                type: 'password',
                text: 'Nhập mã PIN',
                required: true
            });
            $this.loading(false);
            res = await $this.$refs.modalPromise.show(dataModal);
            if(!res || res == false)
            {
                throw 'Bạn đã hủy bỏ thao tác!'
            }
            if(!res || res.pin == "")
            {
                $this.$toast.error("Bạn chưa nhập mã PIN!")
            }

            soPIN = res.pin;
            if(!soPIN || !userPIN) {
                throw "Không tìm thấy token user hoặc bạn chưa nhập mã PIN!";
            }
            $this.loading(true);
            console.log(`Đang checkLoginToken... ${dllName} ${tkSerialNumber}`);
            await vnpt_cams_plugin.checkLoginToken(dllName, tkSerialNumber, soPIN, pinType).then(function (resData) {
                console.log("checkLoginToken", resData);
                data = JSON.parse(resData);
            }).catch(function (e) {
                console.error("checkLoginToken", e);
            });

            if(data.code !== 1){
                if(data.desc.indexOf('CKR_PIN_LOCKED') !== -1){
                    throw "Mã PIN đã bị khóa. " + data.desc;
                    //UnLock Token
                    // await vnpt_cams_plugin.unlockToken(dllName, tkSerialNumber, soPIN, userPIN).then(function (resData) {
                    //     console.log("unlockToken", resData);
                    // }).catch(function (e) {
                    //     console.error("unlockToken", e);
                    // });
                }

                $this.$toast.error("Bạn nhập sai mã PIN của token, vui lòng nhập lại: " + data.desc);
            }
            else{
                break;
            }
        }

        //console.log(`userPIN: ${userPIN} soPIN: ${soPIN} cert: ${ JSON.stringify(cert)}`)
        $this.loading(true);

        //SignXml

        var certBase64 = cert.certBase64
        var dataInput = base64.encode(input);

        console.log(`Đang signXml... ${dllName} input: ${input}`);
        await vnpt_cams_plugin.signXml(dllName, soPIN, certBase64, dataInput).then(function (resData) {
            console.log("signXml done!");
            data = JSON.parse(resData);
        }).catch(function (e) {
            console.error("signXml", e);
        });

        if(!data || data.code !== 1){
            throw "Lỗi: " + data.desc;
        }

        let ketqua = data.data;
        return ketqua;
     },
     async DongBoCMS($this, data){
        let kieu_xl = 0;
        let luong_id = data.luong_id;
        let luong_thaotac = data.luong_thaotac
        let hdtb_id = data.hdtb_id;
        let loaihd_id = data.loaihd_id;
        let ma_tinh = data.ma_tinh;//lấy từ token
        let kieuld_id = data.kieuld_id;

        if (await this.PHAILAM($this, luong_id, "RA_GUIYC", luong_thaotac))
        {
            kieu_xl = 1;
        }
        if (await this.PHAILAM($this, luong_id, "CA_GUIYC", luong_thaotac))
        {
            kieu_xl = 2;
        }
        if (await this.PHAILAM($this, luong_id, "TSYC_CATOKEN", luong_thaotac))
        {
            kieu_xl = 3;
        }

        let kt_dongbocms = this.GetData(await api.kiemtra_trangthai_dongbocms($this.axios, {params: {hdtb_id: hdtb_id}}));
        if (kt_dongbocms != "ok")
        {
            throw kt_dongbocms;
        }

        let dt_hd = this.GetData(await api.sp_ds_loai_hd_dongbocms($this.axios, {params: {hdtb_id: hdtb_id}}));
        let req =  {
            "vhdtb_id": hdtb_id,
            "vma_tinh": ma_tinh,
            "vloaihd_id": loaihd_id,
            "vthamso_ca": kieu_xl,
            "vkieuld_id": kieuld_id,
            "phanvung_id": 100
        }
        let dt =  this.GetData(await api.ca_dhsx_lay_ketqua_cms($this.axios, req));
        if(!dt || dt.length <= 0){
            throw "CMS chưa gửi kết quả";
        }

        if (dt[0]["trangthai"] == "3")
        {
            throw "ĐHSX chưa xử lý xong dữ liệu";
        }

        if (dt[0]["trangthai"] == "4")
        {
            let dulieu_loi = dt[0]["dulieu_cms"];
            let ketqua_loi = JSON.parse(dulieu_loi);
            throw ketqua_loi.description;
        }

        let ketqua = dt[0]["dulieu_cms"];
        let ca_tao_yc_result = JSON.parse(ketqua);
        console.log("ca_tao_yc_result", ca_tao_yc_result);
        let vsdObj =  [{
                "STATUS_CODE": ca_tao_yc_result.status_code , // ca_tao_yc_result.status_code
                "HDTB_ID": hdtb_id, //hdtb_id
                "ENDENTITY_ID": ca_tao_yc_result.meta_data.endentity_id, //ca_tao_yc_result.meta_data.endentity_id
                "REQ_ID": ca_tao_yc_result.meta_data.req_id,
                "USERNAME": ca_tao_yc_result.meta_data.username,
                "PASSWORD_GEN_CTS": ca_tao_yc_result.meta_data.password_gen_cts,
                "SERIALNUMBER": ca_tao_yc_result.meta_data.serialnumber,
                "EFFECTIVE_DATE": ca_tao_yc_result.meta_data.effective_date,
                "EXPIRE_DATE": ca_tao_yc_result.meta_data.expire_date,
                "OVERTIME": ca_tao_yc_result.meta_data.overtime,
                "CERT_ID": ca_tao_yc_result.meta_data.cert_id,
                "LUONG_ID": luong_id,
                "LOAIHD_ID": loaihd_id,
                "KIEU_XL": kieu_xl,
                "MATINH": ma_tinh,
                "LOAIHD_ID_ROW": dt_hd[0]["loaihd_id"],
                "KIEULD_ID": kieuld_id,
                "DESCRIPTION":  ca_tao_yc_result.description,
            }]
        req = {
            "vds": JSON.stringify(vsdObj)
        }
        let kq =  this.GetData(await api.sp_update_hd_thuebao_duyetcachuanhoa($this.axios, req));
        //1+Chưa cập nhật được chứng thư số cần thu hồi|Đồng bộ thành công
        if(kq.split('+')[0] == "1")
        {
            return kq.split('+')[1]
            // $this.$toast.success(kq.split('+')[1]);
            // return true;
        }
        throw kq.split('+')[1];
     },
     async KiemTraSmartCA(){
        //TODO: chờ api
        return "ok"
     },
     async KiemTraStatus(){
        //TODO: chờ api
        return "ok"
     },
     async LayThongTinCAHienHuu($this, serial, ten_chu_the, email, username, mst){
        //TODO
        let data = {
            serial: serial,
            cn: ten_chu_the,
            email: email,
            username: username,
            mst: mst
        }
        let lst = await this.GetData(await api.LayThongTinCAHienHuu($this.axios, data))
        return lst
     },
     async DuyetCA($this, data){
        let loaihd_id = data.loaihd_id;
        let kieuld_id = data.kieuld_id;
        let hdtb_id = data.hdtb_id;
        let thuebao_id = data.thuebao_id;

        let lst = this.GetData(await api.lay_dulieu_tao_thucthe_ca($this.axios, {params: {hdtb_id: hdtb_id}}))
        if(!lst || lst.length <= 0){
          throw 'Không tìm thấy dữ liệu thực thể CA!';
        }

        let files = this.GetData(await api.lay_ds_hoso_ca($this.axios, {params: {hdtb_id: hdtb_id}}));
        console.log(files);
        let ee_id = lst[0].ee_id;
        let req_id = lst[0].req_id;
        let ten_tb_ca = lst[0].ten_tb;
        let status = lst[0].status;

        let ycduyet_cms = Object.assign({}, YEUCAU_DUYET_CMS);
        ycduyet_cms.req_id = req_id;
        ycduyet_cms.type = 1;
        ycduyet_cms.reason = "";
       // ycduyet_cms.files = base64.encode(JSON.stringify(files));
       ycduyet_cms.files = btoa(unescape(encodeURIComponent(JSON.stringify(files))));

        ycduyet_cms.ma_tb = lst[0].ma_tb;
        ycduyet_cms.promotional_month = lst[0].promotional_month;
        ycduyet_cms.smartca_suffix = lst[0].smartca_suffix;
        ycduyet_cms.mst_dn = lst[0].mst_dn;
        ycduyet_cms.company_name = lst[0].ten_kh;
        if (loaihd_id == 1)
        {
            ycduyet_cms.smartca_suffix = lst[0].smartca_suffix;
            ycduyet_cms.mst_dn = lst[0].mst_dn;
        }
        if (loaihd_id == 41)
        {
            ycduyet_cms.smartca_request_type = lst[0].smartca_request_type;
            if (lst[0].smartca_request_type == 7)
            {
                ycduyet_cms.reason = "đổi khóa tài khoản/ đổi máy điện thoại";
            }
        }
        if (kieuld_id == 13179 || loaihd_id == 4 || loaihd_id == 7 || kieuld_id == 13261)
        {
            ycduyet_cms.promotional_month = 0;
        }

        if (loaihd_id == LOAI_HOPDONG.CHAMDUT_SD)
        {
            if (kieuld_id == 13263) // thu hồi CA tạo lỗi
            {
                ycduyet_cms.smartca_cancel_account = 0;
                let kt_huy_acc = this.GetData(await api.kiemtra_huy_acc_smartca_thuhoi_taoloi($this.axios, {hdtb_id: hdtb_id}));
                if (kt_huy_acc.toLowerCase() == "ok")
                {
                    ycduyet_cms.smartca_cancel_account = 1;
                }
            }
            else
            {
                ycduyet_cms.smartca_cancel_account = 1;
            }
        }

      //  ycduyet_cms.matinh_ca = lst[0].matinh_ca;
        ycduyet_cms = {
            root: Object.assign({}, ycduyet_cms) //root: root
        };


        let yeucau_xml_chuaky = this.OBJtoXML(ycduyet_cms);
        yeucau_xml_chuaky = yeucau_xml_chuaky.replace('<root>', '<root xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">');
        let yeucau_xml = await this.KySoXML($this, yeucau_xml_chuaky);
console.log(yeucau_xml)
        // if (!ee_id || !req_id || ee_id == 0 || req_id == 0)
        // {
        //     throw (`Dữ liệu tạo thực thể CA không hợp lệ ee_id: ${ee_id} req_id: ${req_id}`);
        // }

        if (status == 7)
        {
            throw "Yêu cầu đã được duyệt trước đó!";
        }
        if (status == 0)
        {
            throw "Yêu cầu đã hủy duyệt trước đó hoặc chưa kích hoạt yêu cầu!";
        }

        if(status == 3){
            let ca_choxl = Object.assign({}, CA_CHOXULY);
            ca_choxl.dulieu = yeucau_xml;
            ca_choxl.kieu_yc = CAConstanst.ReqTypeCapMoi;
            if (loaihd_id == 41 && (kieuld_id == 13177 || kieuld_id == 13248))
            {
                ca_choxl.kieu_yc = CAConstanst.ReqTypeGiaHan;
            }
            if (loaihd_id == 41 && kieuld_id == 13179)
            {
                ca_choxl.kieu_yc = CAConstanst.ReqTypeThayDoiThongTin;
            }
            if (loaihd_id == 41 && kieuld_id == 13261) // thay đổi giấy tờ
            {
                ca_choxl.kieu_yc = CAConstanst.ReqTypeThayDoiThongTin;
            }
            if (loaihd_id == 4)
            {
                ca_choxl.kieu_yc = CAConstanst.ReqTypeThuHoi;
            }
            if (loaihd_id == 7)
            {
                if (kieuld_id == 714)
                {
                    ca_choxl.kieu_yc = CAConstanst.ReqTypeTamNgung;
                }
                else
                {
                    ca_choxl.kieu_yc = CAConstanst.ReqTypeKhoiPhuc;
                }

            }
            ca_choxl.ma_tinh = lst[0].ma_tinh;
            ca_choxl.kieu_xl = 2; // yêu cầu duyệt
            ca_choxl.trangthai = 0;
            ca_choxl.hdtb_id = hdtb_id;
            ca_choxl.thuebao_id = thuebao_id;

          let res = this.GetData(await api.GuiYeuCau($this.axios, ca_choxl));
          if(res){
            let kq = await api.DongBoDuyetCA($this.axios, { vhdtb_id: hdtb_id, vdulieu: res.DULIEU_CMS });
            if(kq.data.data != "ok")
              throw (kq.data.data);
            else
              return "Duyệt thành công!";
          }
          else{
            throw ("Duyệt thất bại: " + res);
          }
        }
     },
     async KhongDuyetCA($this, data){
        let loaihd_id = data.loaihd_id;
        let kieuld_id = data.kieuld_id;
        let hdtb_id = data.hdtb_id;
        let thuebao_id = data.thuebao_id;

        let lst = this.GetData(await api.lay_dulieu_tao_thucthe_ca($this.axios, {params: {hdtb_id: hdtb_id}}))
        if(!lst || lst.length <= 0){
          throw 'Không tìm thấy dữ liệu thực thể CA!';
        }

        let ee_id = lst[0].ee_id;
        let req_id = lst[0].req_id;
        let ten_tb_ca = lst[0].ten_tb;
        let status = lst[0].status;

        let ycduyet_cms = Object.assign({}, YEUCAU_DUYET_CMS);
        ycduyet_cms.req_id = req_id;
        ycduyet_cms.type = 0; // không duyệt CA
        ycduyet_cms.reason = "Hủy duyệt trên OneBSS (Web)";
        ycduyet_cms.ma_tb = lst[0].ma_tb;
        ycduyet_cms.promotional_month = lst[0].promotional_month;
        ycduyet_cms.smartca_suffix = lst[0].smartca_suffix;
        ycduyet_cms.mst_dn = lst[0].mst_dn;
        ycduyet_cms.company_name = lst[0].ten_kh;

        if (loaihd_id == 1)
        {
            ycduyet_cms.smartca_suffix = lst[0].smartca_suffix;
            ycduyet_cms.mst_dn = lst[0].mst_dn;
        }
        if (loaihd_id == 41)
        {
            ycduyet_cms.smartca_request_type = lst[0].smartca_request_type;
            if (lst[0].smartca_request_type == 7)
            {
                ycduyet_cms.reason = "đổi khóa tài khoản/ đổi máy điện thoại";
            }
        }
        if (kieuld_id == 13179 || loaihd_id == 4 || loaihd_id == 7 || kieuld_id == 13261)
        {
            ycduyet_cms.promotional_month = 0;
        }

        if (loaihd_id == LOAI_HOPDONG.CHAMDUT_SD)
        {
            if (kieuld_id == 13263) // thu hồi CA tạo lỗi
            {
                ycduyet_cms.smartca_cancel_account = 0;
               // let kt_huy_acc = this.GetData(await api.kiemtra_huy_acc_smartca_thuhoi_taoloi($this.axios, {hdtb_id: hdtb_id}));
               // if (kt_huy_acc.toLowerCase() == "ok")
               // {
                //    ycduyet_cms.smartca_cancel_account = 0;
               // }
            }
            else
            {
                ycduyet_cms.smartca_cancel_account = 1;
            }
        }

        ycduyet_cms.matinh_ca = lst[0].matinh_ca;
        ycduyet_cms = {
            root: Object.assign({}, ycduyet_cms) //root: root
        };

        let yeucau_xml_chuaky = this.OBJtoXML(ycduyet_cms);
        let yeucau_xml =await this.KySoXML($this, yeucau_xml_chuaky);

        // if (ee_id == 0 || req_id == 0)
        // {
        //     throw (`Dữ liệu tạo thực thể CA không hợp lệ ee_id: ${ee_id} req_id: ${req_id}`);
        // }


        if(status == 3 || status == 7){
            let ca_choxl = Object.assign({}, CA_CHOXULY);
            ca_choxl.dulieu = yeucau_xml;
            ca_choxl.kieu_yc = CAConstanst.ReqTypeCapMoi;
            if (loaihd_id == 41 && (kieuld_id == 13177 || kieuld_id == 13248))
            {
                ca_choxl.kieu_yc = CAConstanst.ReqTypeGiaHan;
            }
            if (loaihd_id == 41 && kieuld_id == 13179)
            {
                ca_choxl.kieu_yc = CAConstanst.ReqTypeThayDoiThongTin;
            }
            if (loaihd_id == 4)
            {
                ca_choxl.kieu_yc = CAConstanst.ReqTypeThuHoi;
            }
            if (loaihd_id == 7)
            {
                if (kieuld_id == 714)
                {
                    ca_choxl.kieu_yc = CAConstanst.ReqTypeTamNgung;
                }
                else
                {
                    ca_choxl.kieu_yc = CAConstanst.ReqTypeKhoiPhuc;
                }

            }
            ca_choxl.ma_tinh = lst[0].ma_tinh;
            ca_choxl.kieu_xl = 2; // yêu cầu duyệt
            ca_choxl.trangthai = 0;
            ca_choxl.hdtb_id = hdtb_id;
            ca_choxl.thuebao_id = thuebao_id;

            let res = this.GetData(await api.GuiYeuCau($this.axios, ca_choxl));
            let json = JSON.parse(res.DULIEU_CMS)

            let statusCode = json.status_code
            let description = json.description
            if(statusCode == 200 || description === "Trạng thái yêu cầu đã là Không Duyệt"){
                try{
                    //validate Trạng thái yêu cầu đã là Không Duyệt
                    //let kq = await this.DongBoCMS($this, data);
                    await api.cap_nhat_status_hdtb($this.axios, {"vhdtb_id": hdtb_id, "vstatus": 0})
                    return {
                      des: "Hủy duyệt thành công!",
                      code: 200
                    };
                }catch(ex){
                    console.error("DongBoCMS", ex);
                    throw ex;
                }
            }
            else{
                // throw ("Tạo yêu thất bại: " + description);
                return {
                  des: "Tạo yêu cầu thất bại: " + description,
                  code: 400
                }
              ;
            }
        }
     },
     async GuiHSCMS($this, data){
        let loaihd_id = data.loaihd_id;
        let kieuld_id = data.kieuld_id;
        let hdtb_id = data.hdtb_id;
        let thuebao_id = data.thuebao_id;

        let kiemtra_file_nd = this.GetData(await api.kiemtra_bienban_nghiemthu_ca($this.axios, {params: {hdtb_id: hdtb_id}}));
        if (kiemtra_file_nd != "ok")
        {
            throw "Chưa có biên bản nghiệm thu để đồng bộ";
        }

        let lst = this.GetData(await api.lay_dulieu_tao_thucthe_ca($this.axios, {params: {hdtb_id: hdtb_id}}))
        if(!lst || lst.length <= 0){
          throw 'Không tìm thấy dữ liệu thực thể CA!';
        }

        let ee_id = lst[0].ee_id;
        let req_id = lst[0].req_id;
        let ten_tb_ca = lst[0].ten_tb;
        let status = lst[0].status;

        if (status != 5)
        {
            throw "Bạn chưa lấy thông tin từ chứng thư số mới về!";
        }

        let files = this.GetData(await api.lay_ds_hoso_ca_nghiemthu($this.axios, {params: {hdtb_id: hdtb_id}}));
       // console.log(files.data);
        let gui_hs = GUI_FILE_HOSO_CMS;
        gui_hs.serialnumber = lst[0].serial;
        gui_hs.files = btoa(unescape(encodeURIComponent(JSON.stringify(files))));
       // console.log(gui_hs.files);
        let yeucau_xml_chuaky = this.OBJtoXML(gui_hs);
        let yeucau_xml = await this.encodeBase64($this, yeucau_xml_chuaky);

        let ca_choxl = {}
        ca_choxl.DULIEU = yeucau_xml;
        ca_choxl.KIEU_YC = CAConstanst.ReqTypeCapMoi;
        if (loaihd_id == 41 && (kieuld_id == 13177 || kieuld_id == 13248))
        {
            ca_choxl.KIEU_YC = CAConstanst.ReqTypeGiaHan;
        }
        if (loaihd_id == 41 && (kieuld_id == 13179 || kieuld_id == 13261))
        {
            ca_choxl.KIEU_YC = CAConstanst.ReqTypeThayDoiThongTin;
        }
        if (loaihd_id == 4)
        {
            ca_choxl.KIEU_YC = CAConstanst.ReqTypeThuHoi;
        }
        ca_choxl.MA_TINH = lst[0].MA_TINH;
        ca_choxl.KIEU_XL = 4; // gửi hồ sơ cho CMS
        ca_choxl.TRANGTHAI = 0;
        ca_choxl.HDTB_ID = hdtb_id;
        ca_choxl.THUEBAO_ID = thuebao_id;

        let res = this.GetData(await api.GuiYeuCau($this.axios, ca_choxl));
        if(res){
            try{
                let kq = await this.DongBoHS($this, data);
                return kq;
            }catch(ex){
                console.error("DongBoHS", ex);
            }
            return "Tạo yêu cầu thành công!";
        }
        else{
            throw ("Tạo yêu cầu thất bại: " + res);
        }
     },
     async DongBoHS($this, data){
        let kieu_xl = 4;
        let luong_id = data.luong_id;
        let luong_thaotac = data.luong_thaotac
        let hdtb_id = data.hdtb_id;
        let loaihd_id = data.loaihd_id;
        let ma_tinh = data.ma_tinh;//lấy từ token
        let kieuld_id = data.kieuld_id;

        if (loaihd_id == LOAI_HOPDONG.CHAMDUT_SD)
        {
            if (kieuld_id == 13263 || kieuld_id == 13259)
            {
                let res = this.GetData(await api.cap_nhat_status_hdtb($this.axios, {vhdtb_id: hdtb_id, vstatus: 6}))
                throw "Đồng bộ dữ liệu thu hồi chứng thư lỗi/ toàn trình thành công";
            }
        }

        let dt_hd = this.GetData(await api.sp_ds_loai_hd_dongbocms($this.axios, {params: {hdtb_id: hdtb_id}}));
        let req =  {
            "vhdtb_id": hdtb_id,
            "vma_tinh": ma_tinh,
            "vloaihd_id": loaihd_id,
            "vthamso_ca": kieu_xl,
            "vkieuld_id": kieuld_id,
            "phanvung_id": 100
        }
        let dt =  this.GetData(await api.ca_dhsx_lay_ketqua_cms($this.axios, req));
        if(!dt || dt.length <= 0){
            throw "CMS chưa gửi kết quả";
        }

        if (dt[0]["trangthai"] == "3")
        {
            throw "ĐHSX chưa xử lý xong dữ liệu";
        }

        if (dt[0]["trangthai"] == "4")
        {
            let dulieu_loi = dt[0]["dulieu_cms"];
            let ketqua_loi = JSON.parse(dulieu_loi);
            throw ketqua_loi.description;
        }

        let ketqua = dt[0]["dulieu_cms"];
        let ca_tao_yc_result = JSON.parse(ketqua);
        console.log("ca_tao_yc_result", ca_tao_yc_result);
        let vsdObj =  [{
                "STATUS_CODE": ca_tao_yc_result.status_code , // ca_tao_yc_result.status_code
                "HDTB_ID": hdtb_id, //hdtb_id
                "KIEU_XL": kieu_xl,
                "MATINH": ma_tinh,
                "LOAIHD_ID_ROW": dt_hd[0]["loaihd_id"],
                "KIEULD_ID": kieuld_id,
                "DESCRIPTION":  ca_tao_yc_result.description,
            }]

        req = {
            "vds": JSON.stringify(vsdObj)
        }
        let kq =  this.GetData(await api.fn_thuchien_dongbohs($this.axios, req));
        if(kq  === "1")
        {
            return "Gửi hồ sơ thành công, bạn vui lòng cập nhật và hoàn thiện hợp đồng"
        }
        throw kq;
     },
     GetData: function (response) {
        //message "No Content"
        if (response.data.error === 200 || response.data.error === '200' || response.data.errorCode === 0 ) {
          return response.data.data
        } else {
            if(response.data.error_code){
                if (response.data.error_code !== "BSS-00000000" && response.data.error_code !== "BSS-00000204"){
                    throw response.data;
                }
                console.error(response.data)
            }
            else
                throw response.data;
        }
        return null
      },
      async PHAILAM($this, luong_id, code, ds_luong_thaotac) {
        let luong_thaotac = ds_luong_thaotac;
        if(!luong_thaotac) {
            await api.lay_luong_thaotac($this.axios, {luong_id: luong_id,}).then((res) => {
                luong_thaotac = res.data.data;
            });
        }

        if(!luong_thaotac){
            luong_thaotac = [];
            console.warn("PHAILAM luong_thaotac is empty!");
        }

        return luong_thaotac.findIndex(
            (item) => item.enable == 1 && item.code == code
            ) >= 0;
    },
    OBJtoXML(obj) {
        var toXML = function(obj){
            var xml = '';
            for (var prop in obj) {
                xml += obj[prop] instanceof Array ? '' : "<" + prop + ">";
                if (obj[prop] instanceof Array) {
                for (var array in obj[prop]) {
                    xml += "<" + prop + ">";
                    xml += toXML(new Object(obj[prop][array]));
                    xml += "</" + prop + ">";
                }
                } else if (typeof obj[prop] == "object") {
                xml += toXML(new Object(obj[prop]));
                } else {
                xml += obj[prop];
                }
                xml += obj[prop] instanceof Array ? '' : "</" + prop + ">";
            }
            var xml = xml.replace(/<\/?[0-9]{1,}>/g, '');
            return xml;
        }

        let xml = toXML(obj);

        return '<?xml version="1.0" encoding="UTF-8"?>' + xml
    }
}


