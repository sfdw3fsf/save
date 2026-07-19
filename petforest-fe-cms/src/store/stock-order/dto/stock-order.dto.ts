import { STOCK_ORDER_MATERIAL } from '@/utils/enums/funeral/stock-mngt.enum';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import dayjs from 'dayjs';

export type StockOrderListSupply = {
  columbarium?: number;
  home?: number;
  emotional?: number;
  sophisticated?: number;
  frame?: number;
  monite?: number;
  homeColumbarium?: number;
};

export type StockOrderListDTO = StockOrderListSupply & {
  key: number;
  id: number;
  stockOrderStatus: ENUM_STATUS;
  applicationDate: dayjs.Dayjs | null;
  funeralDate: dayjs.Dayjs | null;
  guardianName: string;
  childName: string;
  guardianContact: string;
  columbariumPlace: string;
  material: STOCK_ORDER_MATERIAL;
  note: string;
  contactFrequencyFirst?: string;
  contactFrequencySecond?: string;
  contactFrequencyThird?: string;
  reservation?: {
    id: number;
    code: string;
  };
};
